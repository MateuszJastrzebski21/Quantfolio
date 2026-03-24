package org.example.mas_zao_jastrzebski_s27397.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Rynek extends ObjectPlus implements Serializable {
    private String nazwa;
    private List<Aktywo> listaAktywow = new ArrayList<>();
    private LocalDate aktualnaData;

    public Rynek(String nazwa) {
        super();

        if (nazwa == null || nazwa.isBlank()) {
            throw new IllegalArgumentException("Nazwa rynku nie może być pusta");
        }
        this.nazwa = nazwa;
        this.aktualnaData = LocalDate.now();
    }

    public void dodajAktywo(Aktywo aktywo) {
        if (aktywo == null) {
            throw new IllegalArgumentException("Aktywo nie może być null");
        }
        aktywo.setRynek(this);
        listaAktywow.add(aktywo);
    }

    public List<Aktywo> pobierzAktywa() {
        return new ArrayList<>(listaAktywow);
    }

    public Aktywo znajdzAktywoPoNazwie(String nazwa) {
        return listaAktywow.stream()
                .filter(a -> a.getNazwa().equalsIgnoreCase(nazwa))
                .findFirst()
                .orElse(null);
    }

    public int getLiczbaAktywow() {
        return listaAktywow.size();
    }

    public void symulujZmianyCen() {
        for (Aktywo aktywo : listaAktywow) {
            aktywo.zmienCene();
        }
        aktualnaData = aktualnaData.plusDays(1);
    }

    public LocalDate getAktualnaData() {
        return aktualnaData;
    }

    public String getNazwa() {
        return nazwa;
    }
}
