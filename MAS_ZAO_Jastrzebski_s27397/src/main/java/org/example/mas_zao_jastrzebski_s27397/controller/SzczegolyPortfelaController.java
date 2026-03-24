package org.example.mas_zao_jastrzebski_s27397.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.example.mas_zao_jastrzebski_s27397.model.Portfel;
import org.example.mas_zao_jastrzebski_s27397.model.Pozycja;
import org.example.mas_zao_jastrzebski_s27397.model.Rynek;
import org.example.mas_zao_jastrzebski_s27397.model.Uzytkownik;

import java.io.IOException;
import java.math.BigDecimal;

public class SzczegolyPortfelaController {
    private Portfel portfel;
    private Rynek rynek;
    private Uzytkownik uzytkownik;

    @FXML
    private Button btnKup;

    @FXML
    private Button btnPowrot;

    @FXML
    private Button btnSprzedaj;

    @FXML
    private TableView<Pozycja> tabelaPozycji;

    @FXML
    private TableColumn<Pozycja, String> kolIlosc;

    @FXML
    private TableColumn<Pozycja, String> kolNazwa;

    @FXML
    private TableColumn<Pozycja, String> kolSredniaCena;

    @FXML
    private TableColumn<Pozycja, String> kolWartosc;

    @FXML TableColumn<Pozycja, String> kolAktualnaCena;

    @FXML
    private Label labelLacznaWartosc;

    @FXML
    private Label labelNazwaPortfela;

    @FXML
    private Label labelSaldo;

    public void init(Portfel portfel, Rynek rynek, Uzytkownik uzytkownik) {
        this.portfel = portfel;
        this.rynek = rynek;
        this.uzytkownik = uzytkownik;

        labelNazwaPortfela.setText(portfel.getNazwa());
        labelSaldo.setText(portfel.getSaldoGotowki().setScale(2, BigDecimal.ROUND_HALF_UP).toString() + " PLN");
        labelLacznaWartosc.setText(portfel.obliczWartosc().setScale(2, BigDecimal.ROUND_HALF_UP).toString() + " PLN");

        ObservableList<Pozycja> dane = FXCollections.observableArrayList(portfel.getPozycje());
        tabelaPozycji.setItems(dane);

        kolNazwa.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAktywo().getNazwa()));
        kolSredniaCena.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getSredniaCenaZakupu().setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
        kolAktualnaCena.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAktywo().getCenaAktualna().setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
        kolIlosc.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getIlosc().toString()));
        kolWartosc.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().obliczWartosc().setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
    }

    @FXML
    public void initialize() {
        btnPowrot.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/mas_zao_jastrzebski_s27397/lista-portfeli-view.fxml"));
                Scene scene = new Scene(loader.load(), 600, 400);

                ListaPortfeliController controller = loader.getController();
                controller.init(rynek, uzytkownik);

                Stage stage = (Stage) btnPowrot.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Lista Portfeli");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnKup.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/mas_zao_jastrzebski_s27397/kup-aktywo-view.fxml"));
                Scene scene = new Scene(loader.load());

                KupAktywoController controller = loader.getController();
                controller.init(portfel, rynek, uzytkownik);

                Stage stage = (Stage) btnKup.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Kup Aktywo");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnSprzedaj.setOnAction(event -> {
            //pusto
        });
    }
}
