package org.example.mas_zao_jastrzebski_s27397.model;

public class Amator extends Uzytkownik{
    public Amator(String nazwa) {
        super(nazwa);
    }

    @Override
    public void zalozPortfel(String nazwaPortfela, double saldo) throws Exception {
        if (getPortfele().containsKey(nazwaPortfela)) {
            throw new Exception("Portfel o tej nazwie już istnieje");
        }

        if (saldo > 1000000) {
            throw new Exception("Amator nie może stworzyć portfela z saldem większym niż 1 milion");
        }

        Portfel portfel = new Portfel(nazwaPortfela, saldo);
        getPortfele().put(nazwaPortfela, portfel);
        portfel._ustawUzytkownika(this);
    }
}
