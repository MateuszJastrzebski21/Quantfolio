package org.example.mas_zao_jastrzebski_s27397.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.mas_zao_jastrzebski_s27397.controller.*;
import org.example.mas_zao_jastrzebski_s27397.model.*;

import java.math.BigDecimal;

public class Main extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        //Ustawić na true, aby zacząć od czystego systemu
        boolean resetSystemu = false;

        if (resetSystemu) {
            ObjectPlus.resetSystemu();
        }

        ObjectPlus.read();

        Rynek rynek;
        Uzytkownik uzytkownik;

        boolean brakUzytkownika = !ObjectPlus.getExtent(Amator.class).iterator().hasNext();
        boolean brakRynku = !ObjectPlus.getExtent(Rynek.class).iterator().hasNext();

        if (brakUzytkownika || brakRynku) {
            System.out.println("Tworzenie danych startowych");
            uzytkownik = new Amator("Kamil");
            rynek = new Rynek("Moje ulubione aktywa");

            dodajDaneStartowe(rynek, uzytkownik);
        } else {
            uzytkownik = (Uzytkownik) ObjectPlus.getExtent(Amator.class).iterator().next();
            rynek = ObjectPlus.getExtent(Rynek.class).iterator().next();
        }


        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/org/example/mas_zao_jastrzebski_s27397/rynek-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        RynekController controller = fxmlLoader.getController();
        controller.init(rynek, uzytkownik);


        System.out.println("Aktualna data rynku po wczytaniu: " + rynek.getAktualnaData());
        stage.setTitle("Quantfolio");
        stage.setScene(scene);
        stage.show();
    }

    private void dodajDaneStartowe(Rynek rynek, Uzytkownik uzytkownik) throws Exception {
        // Akcje
        Aktywo akcja1 = new Akcja("CD Projekt", new BigDecimal("200"), "CDR", "Bezpieczne", "Polska");
        Aktywo akcja2 = new Akcja("Apple Inc.", new BigDecimal("191.55"), "AAPL", "Bezpieczne", "USA");
        Aktywo akcja3 = new Akcja("Tesla", new BigDecimal("720.30"), "TSLA", "Agresywne", "USA");

        // Kryptowaluty
        Aktywo krypto1 = new Kryptowaluta("Bitcoin", new BigDecimal("150000"), "BTC", "Bezpieczne", true);
        Aktywo krypto2 = new Kryptowaluta("Ethereum", new BigDecimal("11000"), "ETH", "Agresywne", true);

        // Surowce
        Aktywo surowiec1 = new Surowiec("Złoto", new BigDecimal("400"), "XAU", "Bezpieczne", true);
        Aktywo surowiec2 = new Surowiec("Srebro", new BigDecimal("30"), "XAG", "Bezpieczne", true);
        Aktywo surowiec3 = new Surowiec("Ropa naftowa", new BigDecimal("300"), "OIL", "Agresywne", false);


        rynek.dodajAktywo(akcja1);
        rynek.dodajAktywo(akcja2);
        rynek.dodajAktywo(akcja3);

        rynek.dodajAktywo(krypto1);
        rynek.dodajAktywo(krypto2);

        rynek.dodajAktywo(surowiec1);
        rynek.dodajAktywo(surowiec2);
        rynek.dodajAktywo(surowiec3);


        Portfel portfel1 = new Portfel("Mały Portfel", 10000);
        Portfel portfel2 = new Portfel("Średni Portfel", 20000);
        Portfel portfel3 = new Portfel("Duży Portfel", 50000);

        uzytkownik.dodajPortfel(portfel1);
        uzytkownik.dodajPortfel(portfel2);
        uzytkownik.dodajPortfel(portfel3);
    }

    @Override
    public void stop() throws Exception {
        ObjectPlus.save();
    }

    public static void main(String[] args) throws Exception {
        launch();
    }
}