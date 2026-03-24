package org.example.mas_zao_jastrzebski_s27397.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.mas_zao_jastrzebski_s27397.model.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

public class KupAktywoController {
    private Portfel portfel;
    private Rynek rynek;
    private Uzytkownik uzytkownik;

    ObservableList<Aktywo> aktywa;

    @FXML
    private Button btnKup;

    @FXML
    private Button btnPowrot;

    @FXML
    private TableColumn<Aktywo, String> kolCena;

    @FXML
    private TableColumn<Aktywo, String> kolNazwa;

    @FXML
    private TableColumn<Aktywo, String> kolRyzyko;

    @FXML
    private TableColumn<Aktywo, String> kolTyp;

    @FXML
    private Label labelKoszt;

    @FXML
    private Label labelNazwaPortfela;

    @FXML
    private Label labelSaldo;

    @FXML
    private TextField poleIlosc;

    @FXML
    private TableView<Aktywo> tabelaAktywow;

    public void init(Portfel portfel, Rynek rynek, Uzytkownik uzytkownik) {
        this.portfel = portfel;
        this.rynek = rynek;
        this.uzytkownik = uzytkownik;

        labelNazwaPortfela.setText(portfel.getNazwa());
        labelSaldo.setText(portfel.getSaldoGotowki().setScale(2, BigDecimal.ROUND_HALF_UP).toString());

        aktywa = FXCollections.observableArrayList(rynek.pobierzAktywa());
        tabelaAktywow.setItems(aktywa);

        kolNazwa.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getNazwa()));
        kolTyp.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getClass().getSimpleName()));
        kolCena.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getCenaAktualna().toPlainString()));
        kolRyzyko.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getRodzajRyzyka()));

        poleIlosc.textProperty().addListener((observable, oldValue, newValue) -> przeliczKoszt());

        btnKup.setOnAction(event -> kupAktywo());
        btnPowrot.setOnAction(event -> wrocDoPortfela());
    }

    private void przeliczKoszt() {
        Aktywo aktywo = tabelaAktywow.getSelectionModel().getSelectedItem();
        if (aktywo == null) {
            labelKoszt.setText("0.00 PLN");
            return;
        }

        try {
            BigDecimal ilosc = new BigDecimal(poleIlosc.getText());
            if (ilosc.compareTo(BigDecimal.ZERO) <= 0) throw new NumberFormatException();
            BigDecimal koszt = aktywo.getCenaAktualna().multiply(ilosc);
            labelKoszt.setText(koszt.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + " PLN");
        } catch (NumberFormatException e) {
            labelKoszt.setText("0.00 PLN");
        }
    }

    private void kupAktywo() {
        Aktywo aktywo = tabelaAktywow.getSelectionModel().getSelectedItem();
        if (aktywo == null) {
            alert("Nie wybrano aktywa");
            return;
        }

        BigDecimal ilosc;
        try {
            ilosc = new BigDecimal(poleIlosc.getText());
            if (ilosc.compareTo(BigDecimal.ZERO) <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            alert("Wprowadź poprawną liczbę jednostek");
            return;
        }

        BigDecimal koszt = aktywo.getCenaAktualna().multiply(ilosc).setScale(2, BigDecimal.ROUND_HALF_UP);
        if (portfel.getSaldoGotowki().compareTo(koszt) < 0) {
            alert("Niewystarczające środki");
            return;
        }

        portfel.dodajTransakcje(new Transakcja(portfel, TypTransakcji.KUPNO, ilosc.intValue(), aktywo.getCenaAktualna(), LocalDate.now(), aktywo));

        Pozycja istniejąca = portfel.getPozycje().stream()
                .filter(p -> p.getAktywo().equals(aktywo))
                .findFirst()
                .orElse(null);

        if (istniejąca != null) {
            istniejąca.zwiekszIlosc(ilosc, aktywo.getCenaAktualna());
        }
        else {
            portfel.dodajPozycje(new Pozycja(portfel, aktywo, ilosc, aktywo.getCenaAktualna()));
        }

        portfel.setSaldoGotowki(portfel.getSaldoGotowki().subtract(koszt));
        portfel._ustawUzytkownika(portfel.getUzytkownik());

        pokazSukces(aktywo, ilosc, aktywo.getCenaAktualna(), portfel.getSaldoGotowki());
    }

    private void wrocDoPortfela() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/mas_zao_jastrzebski_s27397/szczegoly-portfela-view.fxml"));
            Scene scene = new Scene(loader.load());

            SzczegolyPortfelaController controller = loader.getController();
            controller.init(portfel, rynek, uzytkownik);

            Stage stage = (Stage) btnPowrot.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Szczegóły portfela: " + portfel.getNazwa());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void alert(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    private void pokazSukces(Aktywo aktywo, BigDecimal ilosc, BigDecimal cenaJednostkowa, BigDecimal noweSaldo) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sukces!");
        alert.setHeaderText("Zakup aktywa się powiódł");

        String content = String.format(
                "Zakupiono %s jednostek\n%s po cenie %.2f\n\nNowe saldo: %s PLN",
                ilosc.stripTrailingZeros().toPlainString(),
                aktywo.getNazwa(),
                cenaJednostkowa,
                noweSaldo.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
        );

        alert.setContentText(content);

        alert.setOnHidden(event -> wrocDoPortfela());

        alert.show();
    }
}
