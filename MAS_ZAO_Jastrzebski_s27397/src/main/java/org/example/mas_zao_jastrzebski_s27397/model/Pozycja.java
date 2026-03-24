package org.example.mas_zao_jastrzebski_s27397.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Pozycja extends ObjectPlus implements Serializable {
    private BigDecimal ilosc;
    private Aktywo aktywo;
    private BigDecimal sredniaCenaZakupu;
    private BigDecimal lacznaWartoscZakupu;

    private Portfel portfel;

    public Pozycja(Portfel portfel,Aktywo aktywo, BigDecimal ilosc, BigDecimal cenaZakupu) {
        super();
        if (portfel == null) throw new IllegalArgumentException("Portfel nie może być null");
        if (aktywo == null) throw new IllegalArgumentException("Aktywo nie może być null");
        if (ilosc.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Ilość nie może być ujemna");
        if (cenaZakupu.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Cena musi być dodatnia");

        this.portfel = portfel;
        //portfel.dodajPozycje(this);

        this.aktywo = aktywo;
//        aktywo.dodajPozycje(this);
//        portfel.dodajPozycje(this);
        this.ilosc = ilosc;
        this.lacznaWartoscZakupu = cenaZakupu.multiply(ilosc);
        this.sredniaCenaZakupu = cenaZakupu;
    }

    public Aktywo getAktywo() {
        return aktywo;
    }

    public BigDecimal getIlosc() {
        return ilosc;
    }

    public BigDecimal getSredniaCenaZakupu() {
        return sredniaCenaZakupu;
    }

    public BigDecimal getLacznaWartoscZakupu() {
        return lacznaWartoscZakupu;
    }

    public BigDecimal obliczWartosc() {
        return aktywo.getCenaAktualna().multiply(ilosc);
    }

    public BigDecimal obliczZyskStrate() {
        return obliczWartosc().subtract(lacznaWartoscZakupu);
    }

    public void zwiekszIlosc(BigDecimal nowaIlosc, BigDecimal cena) {
        if (nowaIlosc.compareTo(BigDecimal.ZERO) <= 0 || cena.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Nieprawidłowa ilość");
        }

        BigDecimal nowaLacznaWartosc = cena.multiply(nowaIlosc);
        this.lacznaWartoscZakupu = this.lacznaWartoscZakupu.add(nowaLacznaWartosc);
        this.ilosc = this.ilosc.add(nowaIlosc);
        this.sredniaCenaZakupu = lacznaWartoscZakupu.divide(ilosc, BigDecimal.ROUND_HALF_UP);
    }

    public void zmniejszIlosc(BigDecimal iloscDoUsuniecia) {
        if (iloscDoUsuniecia.compareTo(BigDecimal.ZERO) <= 0 || iloscDoUsuniecia.compareTo(ilosc) > 0) {
            throw new IllegalArgumentException("Nieprawidłowa ilość do usunięcia");
        }

        BigDecimal usuwanaWartosc = sredniaCenaZakupu.multiply(iloscDoUsuniecia);
        this.lacznaWartoscZakupu = this.lacznaWartoscZakupu.subtract(usuwanaWartosc);
        this.ilosc = this.ilosc.subtract(iloscDoUsuniecia);
    }

    public Portfel getPortfel() {
        return portfel;
    }
}
