package server;

import org.json.JSONObject;

import java.util.ArrayList;

public interface KøreskolePriserInterface extends java.rmi.Remote {
	
	//--------adminstratorer-------------//
	boolean logIndAdmin(String adminBrugernavn, String adminKodeord) 											throws java.rmi.RemoteException;
	boolean sletKøreskole(String adminBrugernavn, String adminKodeord, String køreskole_id) 						throws java.rmi.RemoteException;
	boolean sletKøreskoleTilbud(String adminBrugernavn, String adminKodeord, int tilbud_id )						throws java.rmi.RemoteException;
	boolean opretKøreskole(String adminBrugernavn, String adminKodeord, Køreskole køreskole) 						throws java.rmi.RemoteException;
	
	
	//----------køreskole------------//
	boolean logInd(String brugernavn, String kodeord)										throws java.rmi.RemoteException;
	boolean opretTilbud(String brugernavn, String kodeord, Tilbud tilbud)						throws java.rmi.RemoteException;
	boolean aendreTilbud(String brugernavn, String kodeord, int tilbudID, Tilbud tilbud)		throws java.rmi.RemoteException;
	boolean sletTilbud(String brugernavn, String kodeord, int tilbudID)						throws java.rmi.RemoteException;
	ArrayList<Tilbud> getTilbudKøreskole(String brugernavn, String kodeord)					throws java.rmi.RemoteException;	
	
	//----------køreskoleelever--------//
	ArrayList<Tilbud> getTilbud(int postnummer)												throws java.rmi.RemoteException;
	String getAlleTilbud()																throws java.rmi.RemoteException;
	ArrayList<Tilbud> getTilbudPris(int postnummer, int minimumPris, int maximumPris)		throws java.rmi.RemoteException;
	boolean opretKommentarer(String kommentar, int rating)										throws java.rmi.RemoteException;
	

}
