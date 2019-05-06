package server;

/**
 * interface implementeres også hos klienten.
 * @author Alexander Kjeldsen */

public interface KøreskolePriserInterface extends java.rmi.Remote {
	
	//--------adminstratorer-------------//
	boolean logIndAdmin(String adminBrugernavn, String adminKodeord) 												throws java.rmi.RemoteException;
	boolean sletKøreskole(String adminBrugernavn, String adminKodeord, String køreskole_id) 						throws java.rmi.RemoteException;
	boolean sletKøreskoleTilbud(String adminBrugernavn, String adminKodeord, int tilbud_id )						throws java.rmi.RemoteException;
	boolean opretKøreskoleAdmin(String adminBrugernavn, String adminKodeord, Koreskole koreskole) 					throws java.rmi.RemoteException;
	String getAlleKøreskoler(String adminBrugernavn, String adminKodeord)					 						throws java.rmi.RemoteException;
	String getTilbudFraKøreskoler(String adminBrugernavn, String adminKodeord, String koreskoleid)					throws java.rmi.RemoteException;

	
	//----------koreskole------------//
	boolean logInd(String brugernavn, String kodeord)											throws java.rmi.RemoteException;
	boolean opretTilbud(String brugernavn, String kodeord, Tilbud tilbud)						throws java.rmi.RemoteException;
	boolean aendreTilbud(String brugernavn, String kodeord, int tilbudID, Tilbud tilbud)		throws java.rmi.RemoteException;
	boolean sletTilbud(String brugernavn, String kodeord, int[] tilbudID)						throws java.rmi.RemoteException;
	String getTilbudKøreskole(String brugernavn, String kodeord)								throws java.rmi.RemoteException;
	String getKøreskole(String brugernavn, String kodeord)									throws java.rmi.RemoteException;
	boolean opretKøreskole(String brugernavn, String kodeord, Koreskole koreskole) 				throws java.rmi.RemoteException;

	//----------køreskoleelever--------//
	String getTilbudFraPostnummer(int postnummer)												throws java.rmi.RemoteException;
	String getAlleTilbud()																		throws java.rmi.RemoteException;
	String getTilbudMellemPrisFraPostnummer(int postnummer, int minimumPris, int maximumPris)	throws java.rmi.RemoteException;
	boolean opretKommentarer(String kommentar, int rating, String køreskoleId)					throws java.rmi.RemoteException;
	

}
