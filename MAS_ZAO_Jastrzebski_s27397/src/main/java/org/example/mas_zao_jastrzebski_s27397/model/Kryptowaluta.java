package org.example.mas_zao_jastrzebski_s27397.model;

import java.math.BigDecimal;

public class Kryptowaluta extends Aktywo{
    private boolean czyZdecentralizowana;

    public Kryptowaluta(String nazwa, BigDecimal cenaAktualna, String symbol, String rodzajRyzyka, boolean czyZdecentralizowana, String opis) {
        super(nazwa, cenaAktualna, symbol, rodzajRyzyka, opis);
        this.czyZdecentralizowana = czyZdecentralizowana;
    }

    public Kryptowaluta(String nazwa, BigDecimal cenaAktualna, String symbol, String rodzajRyzyka, boolean czyZdecentralizowana) {
        super(nazwa, cenaAktualna, symbol, rodzajRyzyka);
        this.czyZdecentralizowana = czyZdecentralizowana;

    }


    @Override
    public void zmienCene() {
        BigDecimal zmiana = generujZmiane(0.10); //10%
        BigDecimal nowaCena = getCenaAktualna().multiply(BigDecimal.ONE.add(zmiana));
        setCenaAktualna(nowaCena);
    }

    public boolean isCzyZdecentralizowana() {
        return czyZdecentralizowana;
    }
}
