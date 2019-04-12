package server;

import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;

public class TilgængeligeDage extends UnicastRemoteObject implements Serializable{

	public TilgængeligeDage() throws Exception {

	}

	static final long serialVersionUID = 1234567891;


	int tilgængelig_mandag;
	int tilgængelig_tirsdag;
	int tilgængelig_onsdag;
	int tilgængelig_torsdag;
	int tilgængelig_fredag;
	int tilgængelig_lørdag;
	int tilgængelig_søndag;

}
