package org.example.mas_zao_jastrzebski_s27397.model;

public class Profesjonalista extends Uzytkownik{
    private String nrLicencji;

    public Profesjonalista(String nazwa, String nrLicencji) {
        super(nazwa);
        setNrLicencji(nrLicencji);
    }

    public String getNrLicencji() {
        return nrLicencji;
    }

    public void setNrLicencji(String nrLicencji) {
        if (nrLicencji == null || nrLicencji.isEmpty()) {
            throw new IllegalArgumentException("Wpisz numer licencji");
        }
        this.nrLicencji = nrLicencji;
    }

    @Override
    public void zalozPortfel(String nazwaPortfela, double saldo) throws Exception {
        if (getPortfele().containsKey(nazwaPortfela)) {
            throw new Exception("Portfel o tej nazwie już istnieje");
        }

        Portfel portfel = new Portfel(nazwaPortfela, saldo);
        getPortfele().put(nazwaPortfela, portfel);
        portfel._ustawUzytkownika(this);
    }

    public void przeniesPortfeleZ(Uzytkownik poprzedni) {
        this.getPortfele().putAll(poprzedni.getPortfele());
    }
}
