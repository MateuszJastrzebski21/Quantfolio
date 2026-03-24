package org.example.mas_zao_jastrzebski_s27397.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class Uzytkownik extends ObjectPlus implements Serializable {
    private String nazwa;
    private Map<String, Portfel> portfele = new HashMap<>();

    public Uzytkownik(String nazwa) {
        super();
        setNazwa(nazwa);
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        if (nazwa == null || nazwa.isBlank()) {
            throw new IllegalArgumentException("Nazwa nie może być pusta");
        }
        this.nazwa = nazwa;
    }

    public Portfel getPortfel(String nazwa) {
        return portfele.get(nazwa);
    }

    public Map<String, Portfel> getPortfele() {
        return Collections.unmodifiableMap(portfele);
    }

    public abstract void zalozPortfel(String nazwaPortfela, double saldo) throws Exception;

    public Profesjonalista zmianaNaProfesjonaliste(String nrLicencji) {
        ObjectPlus.removeFromExtent(this);

        Profesjonalista pro = new Profesjonalista(this.nazwa, nrLicencji);
        pro.przeniesPortfeleZ(this);
        return pro;
    }

    public void dodajPortfel(Portfel portfel) throws Exception {
        if (portfel == null) throw new IllegalArgumentException("Portfel nie może być null");
        if (portfele.containsKey(portfel.getNazwa())) {
            throw new Exception("Portfel o tej nazwie już istnieje");
        }
        portfele.put(portfel.getNazwa(), portfel);
        portfel._ustawUzytkownika(this);
    }
}
