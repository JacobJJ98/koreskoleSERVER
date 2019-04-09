package server;

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
//			System.out.println("-----------admin bruger-------------");
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void sletKøreskole(String adminBrugernavn, String adminKodeord, String køreskole_id) throws RemoteException {
		
		j.sletKøreskole(adminBrugernavn, køreskole_id);		
		
	}

	@Override
	public void sletKøreskoleTilbud(String adminBrugernavn, String adminKodeord, int tilbuds_id) throws RemoteException {
		
		j.sletTilbud(adminBrugernavn, tilbuds_id);
		
	}

	@Override
	public void opretKøreskole(String adminBrugernavn, String adminKodeord, Køreskole køreskole) throws RemoteException {
		
		j.opretkøreskole(køreskole);
		
//		
//		if (!oprettedeKøreskoler.contains(køreskole.id)) {
//
//			jdbc.opretkøreskole(køreskole);
//			System.out.println("køreskole oprettet " + køreskole.id);
//			oprettedeKøreskoler.add(køreskole.id);
//
//		}		


	}



	//---------------------køreskoler-----------------------//

	@Override
	public boolean logInd(String brugernavn, String kodeord) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void opretTilbud(String brugernavn, String kodeord, Tilbud tilbud) throws RemoteException {

		j.opretTilbud(tilbud);


	}

	@Override
	public void aendreTilbud(String brugernavn, String kodeord, int tilbudID, Tilbud tilbud) throws RemoteException {
		
		j.updateTilbud(tilbudID, tilbud);
		
	}

	@Override
	public void sletTilbud(String brugernavn, String kodeord, int tilbudID) throws RemoteException {
		
		j.sletTilbud(brugernavn, tilbudID);
		
	}

	@Override
	public ArrayList<Tilbud> getTilbudKøreskole(String brugernavn, String kodeord) throws RemoteException {
		
		ArrayListe tilbud= new ArrayListe();
		
//		tilbud = j.getTilbud(brugernavn);
		
//		System.out.println("størrelsen på array : "+tilbud.size());
		
		for (int i = 0; i < 10; i++) {
			try {
				tilbud.t.add(new Tilbud());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		for (int i = 0; i < 10; i++) {
		tilbud.t.get(i).beskrivelse="hejhejhejhejhej";
		}


		return tilbud.t;
	}

	@Override
	public ArrayList<Tilbud> getTilbud(int postnummer) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void opretKommentarer(String kommentar, int rating) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<Tilbud> getTilbudPris(int postnummer, int minimumPris, int maximumPris) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}


}
