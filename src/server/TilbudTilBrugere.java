package server;

import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;

public class TilbudTilBrugere extends UnicastRemoteObject implements Serializable {

    public Tilbud tilbud;
    public Køreskole køreskole;

    public TilbudTilBrugere() throws Exception {
        tilbud = new Tilbud();
        køreskole = new Køreskole();
    }


}
