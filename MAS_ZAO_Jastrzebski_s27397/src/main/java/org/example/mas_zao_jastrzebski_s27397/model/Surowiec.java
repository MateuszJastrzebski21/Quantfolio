package org.example.mas_zao_jastrzebski_s27397.model;

import java.math.BigDecimal;

public class Surowiec extends Aktywo {
    private boolean czyNaturalny;

    public Surowiec(String nazwa, BigDecimal cenaAktualna, String symbol, String rodzajRyzyka, boolean czyNaturalny, String opis) {
        super(nazwa, cenaAktualna, symbol, rodzajRyzyka, opis);
        this.czyNaturalny = czyNaturalny;
    }

    public Surowiec(String nazwa, BigDecimal cenaAktualna, String symbol, String rodzajRyzyka, boolean czyNaturalny) {
        super(nazwa, cenaAktualna, symbol, rodzajRyzyka);
        this.czyNaturalny = czyNaturalny;
    }


    @Override
    public void zmienCene() {
        BigDecimal zmiana = generujZmiane(0.01); //1%
        BigDecimal nowaCena = getCenaAktualna().multiply(BigDecimal.ONE.add(zmiana));
        setCenaAktualna(nowaCena);
    }

    public boolean isCzyNaturalny() {
        return czyNaturalny;
    }
}
