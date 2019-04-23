package server;

import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * objekt til JSON dataoverførsel
 * @author Alexander Kjeldsen */

public class Køreskole extends  UnicastRemoteObject implements Serializable {
	public Køreskole() throws Exception {
		// TODO Auto-generated constructor stub
	}

	static final long serialVersionUID = 1234567999;

	String id;
	String navn;
	String adresse;
	int postnummer;
	int telefonnummer;
	String mail;



	@Override
	public String toString() {
		String s = "";

		s+="id=";
		s+= id;

		s+="\nnavn=";
		s+=navn;

		s+="\nadresse=";
		s+=adresse;

		s+="\npostnummer=";
		s+=postnummer;

		s+="\ntelefonnummer=";
		s+=telefonnummer;

		s+="\nmail=";
		s+=mail;


		return s;
	}



}
