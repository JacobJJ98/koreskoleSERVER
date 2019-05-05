package server;

import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;

public class Tilbud extends UnicastRemoteObject implements Serializable {
	/**
	 * objekt til JSON dataoverførsel
	 * @author Alexander Kjeldsen */

	static final long serialVersionUID = 1234567890;

	public Tilbud() throws Exception {
		// TODO Auto-generated constructor stub
	}
	
	String koreskole_id;
	int pris;
	String korekort_type;
	int lynkursus;
	String bilmarke;
	String bilstorrelse;
	String kon;
	String beskrivelse;
	TilgangeligeDage tilgangeligeDage;
	int id;
	
	@Override
	public String toString() {
		String s = "";
		s+="køreskoleid: "+koreskole_id;
		s+="\npris: "+pris;
		s+="\nkørekort: type: "+korekort_type;
		s+="\nlynkursus: "+lynkursus;
		s+="\nbilmærke: "+bilmarke;
		s+="\nbilstorrelse: "+ bilstorrelse;
		s+="\nkon: "+ kon;
		s+="\nbeskrivelse: "+beskrivelse;
		
		return s;
	}


}
