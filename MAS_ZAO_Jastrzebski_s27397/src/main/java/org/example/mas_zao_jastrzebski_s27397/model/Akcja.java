package org.example.mas_zao_jastrzebski_s27397.model;

import java.math.BigDecimal;

public class Akcja extends Aktywo{
    private String kraj;

    public Akcja(String nazwa, BigDecimal cenaAktualna, String symbol, String rodzajRyzyka, String kraj, String opis) {
        super(nazwa, cenaAktualna, symbol, rodzajRyzyka, opis);
        this.kraj = kraj;
    }

    public Akcja(String nazwa, BigDecimal cenaAktualna, String symbol, String rodzajRyzyka, String kraj) {
        super(nazwa, cenaAktualna, symbol, rodzajRyzyka);
        this.kraj = kraj;
    }

    @Override
    public void zmienCene() {
        BigDecimal zmiana = generujZmiane(0.03); //3%
        BigDecimal nowaCena = getCenaAktualna().multiply(BigDecimal.ONE.add(zmiana));
        setCenaAktualna(nowaCena);
    }

    public String getKraj() {
        return kraj;
    }
}
