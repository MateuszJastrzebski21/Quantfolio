package org.example.mas_zao_jastrzebski_s27397.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Aktywo extends ObjectPlus implements Serializable {
    private String nazwa;
    protected BigDecimal cenaAktualna;
    private String symbol;
    private String opis; // opcjonalny
    private String rodzajRyzyka;
    private String ocenaRatingowa; //tylko dla Bezpieczne

    private Rynek rynek;
    private List<Pozycja> pozycje = new ArrayList<>();
    private List<Transakcja> transakcje = new ArrayList<>();

    public Aktywo(String nazwa, BigDecimal cenaAktualna, String symbol, String rodzajRyzyka, String opis) {
        super();

        if (nazwa == null || nazwa.isBlank()) throw new IllegalArgumentException("Nazwa nie może być pusta");
        if (cenaAktualna == null || cenaAktualna.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Cena musi być dodatnia");
        }
        if (symbol == null || symbol.isBlank()) {
            throw new IllegalArgumentException("Symbol nie może być pusty");
        }
        if (!rodzajRyzyka.equals("Bezpieczne") && !rodzajRyzyka.equals("Agresywne")) {
            throw new IllegalArgumentException("Aktywo musi mieć określony rodzaj ryzyka");
        }

        this.nazwa = nazwa;
        this.cenaAktualna = cenaAktualna;
        this.symbol = symbol;
        this.rodzajRyzyka = rodzajRyzyka;
        this.opis = opis;
    }

    public Aktywo(String nazwa, BigDecimal cenaAktualna, String symbol, String rodzajRyzyka) {
        this(nazwa, cenaAktualna, symbol, rodzajRyzyka, null);
    }

    public String getNazwa() {
        return nazwa;
    }

    public BigDecimal getCenaAktualna() {
        return cenaAktualna;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getOpis() {
        return opis;
    }

    public String getRodzajRyzyka() {
        return rodzajRyzyka;
    }

    public String getOcenaRatingowa() {
        if (!rodzajRyzyka.equals("Bezpieczne")) {
            throw new IllegalStateException("Ocena ratingowa jest dostępna tylko dla bezpiecznych aktywów");
        }
        return ocenaRatingowa;
    }

    public void potwierdzOceneRatingowa() {
        if (!rodzajRyzyka.equals("Bezpieczne")) {
            throw new IllegalStateException("Ocena ratingowa jest dostępna tylko dla bezpiecznych aktywów");
        }
        System.out.println("Potwierdzam ocena ratingowa: " + ocenaRatingowa);
    }

    public void pokazOstrzezenie() {
        if (!rodzajRyzyka.equals("Agresywne")) {
            throw new IllegalStateException("Ostrzeżenia dostępne są tylko dla agresywnych aktywów");
        }
        System.out.println("Uwaga! Aktywo o podwyższonym ryzyku.");
    }

    public abstract void zmienCene();

    protected BigDecimal generujZmiane(double bazowaZmiennosc) {
        double mnoznik = rodzajRyzyka.equals("Agresywne") ? 2.0 : 1.0;
        double maxZmiana = bazowaZmiennosc * mnoznik;

        //dajemy bias w górę +0.5%, ponieważ przy symetrycznym rozkładzie wahania, ceny by dążyły w stronę zera.
        double zmianaProcentowa = (Math.random() * 2 - 1) * maxZmiana + 0.005;

        return BigDecimal.valueOf(zmianaProcentowa).setScale(4, RoundingMode.HALF_UP);
    }

    protected void setCenaAktualna(BigDecimal nowaCena) {
        this.cenaAktualna = nowaCena.setScale(2, RoundingMode.HALF_UP);
    }


    public void setRynek(Rynek rynek) {
        if (this.rynek != null && this.rynek != rynek) {
            throw new IllegalStateException("Aktywo już należy do innego rynku");
        }
        this.rynek = rynek;
    }

    public Rynek getRynek() {
        return rynek;
    }


    public void dodajPozycje(Pozycja p) {
        if (pozycje == null) {
            throw new IllegalArgumentException("Pozycja nie może być null");
        }
        if (!pozycje.contains(p)) {
            pozycje.add(p);
        }
    }

    public List<Pozycja> getPozycje() {
        return Collections.unmodifiableList(pozycje);
    }


    public void dodajTransakcje(Transakcja t) {
        if (transakcje == null) {
            throw new IllegalArgumentException("Transakcja nie może być null");
        }
        if (!transakcje.contains(t)) {
            transakcje.add(t);
        }
    }

    public List<Transakcja> getTransakcje() {
        return Collections.unmodifiableList(transakcje);
    }

}
