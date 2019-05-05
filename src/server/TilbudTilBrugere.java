package server;

import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;
/**
 * objekt til JSON dataoverførsel
 * @author Alexander Kjeldsen */

public class TilbudTilBrugere extends UnicastRemoteObject implements Serializable {

    public Tilbud tilbud;
    public Koreskole koreskole;

    public TilbudTilBrugere() throws Exception {
        tilbud = new Tilbud();
        koreskole = new Koreskole();
    }


}
