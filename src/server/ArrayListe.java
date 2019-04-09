package server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ArrayListe extends UnicastRemoteObject implements Serializable {

	protected ArrayListe() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	ArrayList<Tilbud> t = new ArrayList<>();
	
	

}
