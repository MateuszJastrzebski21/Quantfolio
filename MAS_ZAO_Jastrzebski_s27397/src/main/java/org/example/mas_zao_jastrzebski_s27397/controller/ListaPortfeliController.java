package org.example.mas_zao_jastrzebski_s27397.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.example.mas_zao_jastrzebski_s27397.model.Portfel;
import org.example.mas_zao_jastrzebski_s27397.model.Rynek;
import org.example.mas_zao_jastrzebski_s27397.model.Uzytkownik;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class ListaPortfeliController {
    private Rynek rynek;
    private Uzytkownik uzytkownik;

    public void init(Rynek rynek , Uzytkownik uzytkownik) {
        this.rynek = rynek;
        this.uzytkownik = uzytkownik;
        tabelaPortfeli.getItems().setAll(uzytkownik.getPortfele().values());
    }

    @FXML
    private Button btnPowrot;

    @FXML
    private TableColumn<Portfel, String> kolNazwa;

    @FXML
    private TableColumn<Portfel, String> kolSzczegoly;

    @FXML
    private TableColumn<Portfel, String> kolWartosc;

//    @FXML
//    private TableColumn<Portfel, Void> kolZmiana;

    @FXML
    private TableView<Portfel> tabelaPortfeli;

    public void setPortfele(List<Portfel> lista) {
        tabelaPortfeli.getItems().setAll(lista);
    }

    @FXML
    public void initialize() {
        kolNazwa.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getNazwa()));

        kolWartosc.setCellValueFactory(cellData -> {
            String wartosc = String.valueOf(cellData.getValue().obliczWartosc().setScale(2, BigDecimal.ROUND_HALF_UP)) + " zł";
            return new ReadOnlyStringWrapper(wartosc);
                });

        btnPowrot.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/mas_zao_jastrzebski_s27397/rynek-view.fxml"));
                Scene scene = new Scene(loader.load(), 600, 400);

                RynekController controller = loader.getController();
                controller.init(rynek, uzytkownik);

                Stage stage = (Stage) btnPowrot.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Rynek");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        tabelaPortfeli.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Portfel wybrany = tabelaPortfeli.getSelectionModel().getSelectedItem();
                if (wybrany != null) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/mas_zao_jastrzebski_s27397/szczegoly-portfela-view.fxml"));
                        Scene scene = new Scene(loader.load(), 600, 400);

                        SzczegolyPortfelaController controller = loader.getController();
                        controller.init(wybrany, rynek, uzytkownik);

                        Stage stage = (Stage) tabelaPortfeli.getScene().getWindow();
                        stage.setScene(scene);
                        stage.setTitle("Szczegoly portfela: " + wybrany.getNazwa());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
