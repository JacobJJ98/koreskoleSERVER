package server;

import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;
/**
 * objekt til JSON dataoverførsel
 * @author Alexander Kjeldsen */

public class TilgangeligeDage extends UnicastRemoteObject implements Serializable{

	public TilgangeligeDage() throws Exception {

	}

	static final long serialVersionUID = 1234567891;


	int tilgangelig_mandag;
	int tilgangelig_tirsdag;
	int tilgangelig_onsdag;
	int tilgangelig_torsdag;
	int tilgangelig_fredag;
	int tilgangelig_lordag;
	int tilgangelig_sondag;

}
