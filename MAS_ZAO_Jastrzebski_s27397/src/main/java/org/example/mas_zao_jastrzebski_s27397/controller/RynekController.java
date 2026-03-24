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
import org.example.mas_zao_jastrzebski_s27397.model.Aktywo;
import org.example.mas_zao_jastrzebski_s27397.model.Rynek;
import org.example.mas_zao_jastrzebski_s27397.model.Uzytkownik;
import java.io.IOException;


public class RynekController {
    private Rynek rynek;
    private Uzytkownik uzytkownik;
    private ObservableList<Aktywo> aktywa;

    @FXML
    private Button btnPortfele;

    @FXML
    private Button btnSymulujDzien;

    @FXML
    private TableColumn<Aktywo, String> kolCena;

    @FXML
    private TableColumn<Aktywo, String> kolNazwa;

    @FXML
    private TableColumn<Aktywo, String> kolRyzyko;

    @FXML
    private TableColumn<Aktywo, String> kolSymbol;

    @FXML
    private Label labelData;

    @FXML
    private TableView<Aktywo> tabelaAktywow;

    public void init(Rynek rynek, Uzytkownik uzytkownik) {
        this.rynek = rynek;
        this.uzytkownik = uzytkownik;

        labelData.setText(rynek.getAktualnaData().toString());

        ObservableList<Aktywo> aktywa = FXCollections.observableArrayList(rynek.pobierzAktywa());

        kolNazwa.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getNazwa()));
        kolSymbol.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getSymbol()));
        kolCena.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getCenaAktualna().toPlainString()));
        kolRyzyko.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getRodzajRyzyka()));

        this.aktywa = FXCollections.observableArrayList(rynek.pobierzAktywa());
        tabelaAktywow.setItems(aktywa);
    }

    @FXML
    private void initialize() {
        btnSymulujDzien.setOnAction(event -> {
            rynek.symulujZmianyCen();
            labelData.setText(rynek.getAktualnaData().toString());
            tabelaAktywow.refresh();
        });

        btnPortfele.setOnAction(event -> przejdzDoPortfeli());
    }

    private void przejdzDoPortfeli() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/mas_zao_jastrzebski_s27397/lista-portfeli-view.fxml"));
            Scene scene = new Scene(loader.load(), 600, 400);

            ListaPortfeliController controller = loader.getController();
            controller.init(rynek, uzytkownik);

            Stage stage = (Stage) btnPortfele.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Lista Portfeli");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
