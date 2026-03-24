package org.example.mas_zao_jastrzebski_s27397.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Portfel extends ObjectPlus implements Serializable {
    public static final int MAX_LICZBA_PORTFELI = 100;

    private String nazwa;
    private BigDecimal saldoGotowki;
    private List<Transakcja> historiaTransakcji = new ArrayList<>();
    private List<Pozycja> pozycje = new ArrayList<>();

    private Uzytkownik uzytkownik;

    public Portfel(String nazwa, double saldoGotowki) {
        super();
        this.nazwa = nazwa;
        this.saldoGotowki = BigDecimal.valueOf(saldoGotowki);
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        if (nazwa == null || nazwa.isBlank()) {
            throw new IllegalArgumentException("Nazwa portfela nie może być pusta");
        }
        this.nazwa = nazwa;
    }

    public BigDecimal getSaldoGotowki() {
        return saldoGotowki;
    }

    public void setSaldoGotowki(BigDecimal saldoGotowki) {
        this.saldoGotowki = saldoGotowki;
    }

    public List<Transakcja> getHistoriaTransakcji() {
        return Collections.unmodifiableList(historiaTransakcji);
    }

    public List<Pozycja> getPozycje() {
        return Collections.unmodifiableList(pozycje);
    }

    public void dodajTransakcje(Transakcja transakcja) {
        if (transakcja == null) throw new IllegalArgumentException("Transakcja nie może być null");
        historiaTransakcji.add(transakcja);
    }

    public BigDecimal obliczWartosc() {
        BigDecimal wartosc = saldoGotowki;

        for (Pozycja p : pozycje) {
            wartosc = wartosc.add(p.obliczWartosc());
        }

        return wartosc;
    }

    public void _ustawUzytkownika(Uzytkownik uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    public Uzytkownik getUzytkownik() {
        return uzytkownik;
    }

    public void dodajPozycje(Pozycja p) {
        if (p == null) {
            throw new IllegalArgumentException("Pozycja nie może być null");
        }
        pozycje.add(p);
    }
}
