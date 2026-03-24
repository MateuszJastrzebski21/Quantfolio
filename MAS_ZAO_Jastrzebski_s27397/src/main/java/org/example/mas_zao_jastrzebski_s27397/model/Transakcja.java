package org.example.mas_zao_jastrzebski_s27397.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Transakcja extends ObjectPlus implements Serializable {
    private TypTransakcji typ;
    private int ilosc;
    private BigDecimal cenaJednostkowa;
    private LocalDate data;
    private Aktywo aktywo;
    private Portfel portfel;

    public Transakcja(Portfel portfel, TypTransakcji typ, int ilosc, BigDecimal cenaJednostkowa, LocalDate data, Aktywo aktywo) {
        super();
        if (portfel == null || typ == null || data == null || aktywo == null || cenaJednostkowa == null) {
            throw new IllegalArgumentException("Żaden argument nie może być null");
        }
        if (ilosc < 0) {
            throw new IllegalArgumentException("Ilość musi być dodatnia");
        }
        if (cenaJednostkowa.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Cena musi być dodatnia");
        }

        this.portfel = portfel;
        this.typ = typ;
        this.ilosc = ilosc;
        this.cenaJednostkowa = cenaJednostkowa;
        this.data = data;
        this.aktywo = aktywo;
        aktywo.dodajTransakcje(this);

        portfel.dodajTransakcje(this);
    }

    public TypTransakcji getTyp() {
        return typ;
    }

    public int getIlosc() {
        return ilosc;
    }

    public BigDecimal getCenaJednostkowa() {
        return cenaJednostkowa;
    }

    public LocalDate getData() {
        return data;
    }

    public Aktywo getAktywo() {
        return aktywo;
    }

    public Portfel getPortfel() {
        return portfel;
    }
}
