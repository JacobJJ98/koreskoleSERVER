package server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

public class KøreskolePriserImplementering extends UnicastRemoteObject implements KøreskolePriserInterface {

	HashMap<Integer, Køreskole> køreskoler = new HashMap<>();
	Integer antalKøreskoler = 0;
	ArrayList<String> oprettedeKøreskoler = new ArrayList<>();
	JDBC j = new JDBC();


	protected KøreskolePriserImplementering() throws RemoteException {super();}

	//-------------------------admin---------------------------------//	
	@Override
	public boolean logIndAdmin(String adminBrugernavn, String adminKodeord) throws RemoteException {
		if (adminBrugernavn.equals("s165477")&&adminKodeord.equals("kodekode")
				||adminBrugernavn.equals("s175132")&&adminKodeord.equals("DS2019")	) {
			System.out.println("-----------admin bruger-------------");
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean sletKøreskole(String adminBrugernavn, String adminKodeord, String køreskole_id) throws RemoteException {

		if (logIndAdmin(adminBrugernavn, adminKodeord)) {
			int i;
			try {
				i=j.sletKøreskole(køreskole_id);
				System.out.println("bruger:"+adminBrugernavn+" sletter køreskole:"+køreskole_id+" row(s) affected:"+i);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} else return false;

	}

	@Override
	public boolean sletKøreskoleTilbud(String adminBrugernavn, String adminKodeord, int tilbud_id) throws RemoteException {

		if (logIndAdmin(adminBrugernavn, adminKodeord)) {
			try {
				j.sletTilbud(adminBrugernavn, tilbud_id);
				System.out.println("bruger:"+adminBrugernavn+" sletter tilbud:"+tilbud_id);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} else return false;

	}

	@Override
	public boolean opretKøreskole(String adminBrugernavn, String adminKodeord, Køreskole køreskole) throws RemoteException {
		return false;
	}

	@Override
	public boolean logInd(String brugernavn, String kodeord) throws RemoteException {
		return false;
	}

	@Override
	public boolean opretTilbud(String brugernavn, String kodeord, Tilbud tilbud) throws RemoteException {
		return false;
	}

	@Override
	public boolean aendreTilbud(String brugernavn, String kodeord, int tilbudID, Tilbud tilbud) throws RemoteException {
		return false;
	}

	@Override
	public boolean sletTilbud(String brugernavn, String kodeord, int tilbudID) throws RemoteException {
		return false;
	}

	@Override
	public ArrayList<Tilbud> getTilbudKøreskole(String brugernavn, String kodeord) throws RemoteException {
		return null;
	}

	@Override
	public ArrayList<Tilbud> getTilbud(int postnummer) throws RemoteException {
		return null;
	}

	@Override
	public String getAlleTilbud() throws RemoteException {
		return null;
	}

	@Override
	public ArrayList<Tilbud> getTilbudPris(int postnummer, int minimumPris, int maximumPris) throws RemoteException {
		return null;
	}

	@Override
	public boolean opretKommentarer(String kommentar, int rating) throws RemoteException {
		return false;
	}


}
