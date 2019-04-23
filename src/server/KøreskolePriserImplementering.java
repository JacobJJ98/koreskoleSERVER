package server;


import brugerautorisation.data.Bruger;
import brugerautorisation.transport.rmi.Brugeradmin;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Implementering af interface.
 * laver forespørgsler til bruger adminstrationsmodulet via rmi.
 * sender JSON til REST interface
 * logger tidspunkt og id for alle metoder.
 *
 * @author Alexander Kjeldsen */


public class KøreskolePriserImplementering extends UnicastRemoteObject implements KøreskolePriserInterface {

	private JDBC jdbc = new JDBC();
	private Gson gson = new GsonBuilder().create();
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
	private Brugeradmin ba = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");


	protected KøreskolePriserImplementering() throws RemoteException, MalformedURLException, NotBoundException {

		super();
	}

	//-------------------------admin bruger---------------------------------//

	@Override
	public boolean logIndAdmin(String adminBrugernavn, String adminKodeord) throws RemoteException { //tjekker om kombinationen er adminstrator.
		if (adminBrugernavn.equals("s165477") && adminKodeord.equals("kodekode")||
				adminBrugernavn.equals("s165477") && adminKodeord.equals("DS2019")) {
			System.out.println(sdf.format(new Date())+" ---------admin login------------- "+adminBrugernavn);
			return true;
		} else return false;
	}

	@Override
	public boolean sletKøreskole(String adminBrugernavn, String adminKodeord, String køreskole_id) throws RemoteException { //admin kan slette køreskole
		int i ;
		if (logIndAdmin(adminBrugernavn, adminKodeord)){
			i=jdbc.sletKøreskole(køreskole_id);
			System.out.println(sdf.format(new Date())+" adminBruger:"+adminBrugernavn+" slet køreskole:"+ køreskole_id + " row(s) affected:" + i);
			if (i>=1){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean sletKøreskoleTilbud(String adminBrugernavn, String adminKodeord, int tilbud_id) throws RemoteException { //admin kan slette et tilbud
		int i = 0;
		if (logIndAdmin(adminBrugernavn, adminKodeord)){
			i=jdbc.sletTilbud(tilbud_id);
			System.out.println(sdf.format(new Date())+" adminBruger:"+adminBrugernavn+" slet tilbud:"+ tilbud_id + " row(s) affected:" + i);
			if (i>=1){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean opretKøreskoleAdmin(String adminBrugernavn, String adminKodeord, Køreskole køreskole) throws RemoteException {  //en adminstrator kan oprette en køreskole
		int i = 0;
		if (logIndAdmin(adminBrugernavn, adminKodeord)) {
			i=jdbc.opretkøreskole(køreskole);
			System.out.println(sdf.format(new Date())+" adminBruger:"+adminBrugernavn+" opret køreskole:"+ køreskole.id + " row(s) affected:" + i);
			if (i<=1){
				return true;
			}
		}
		return false;
	}

	@Override
	public String getAlleKøreskoler(String adminBrugernavn, String adminKodeord) throws RemoteException { //en adminstrator kan hente alle køreskoler
		ArrayList<Køreskole> køreskoler = new ArrayList<Køreskole>();
		if (logIndAdmin(adminBrugernavn, adminKodeord)) {
			køreskoler=jdbc.getAlleKøreskoler();
			System.out.println(sdf.format(new Date())+" adminBruger:"+adminBrugernavn+" get alle køreskole row(s) affected:" + køreskoler.size());
		}
		String str = gson.toJson(køreskoler);
		printJson(str);
		return str;
	}
	//----------------------------Admin slut-----------------------------//



	//----------------------------normal bruger--------------------------//

	@Override
	public boolean logInd(String brugernavn, String kodeord) throws RemoteException { //tjekker om en bruger er en bruger

		try {
			Bruger b = ba.hentBruger(brugernavn, kodeord);
			System.out.println(sdf.format(new Date())+" bruger:"+b.brugernavn+" studieretning:"+b.studeretning+" studieID:"+b.campusnetId+" ");
			return true;
		} catch (Exception e){
			e.printStackTrace();
			System.out.println(sdf.format(new Date())+" bruger:"+brugernavn+" er ikke en bruger eller har skrevet forkert kode ");
			return false;
		}

	}

	@Override
	public boolean opretTilbud(String brugernavn, String kodeord, Tilbud tilbud) throws RemoteException { //en bruger kan oprette et tilbud
		int i = 0;
		if (logInd(brugernavn, kodeord)){
			tilbud.koreskole_id = ba.hentBruger(brugernavn, kodeord).brugernavn;
			i = jdbc.opretTilbud(tilbud);
			return true;
		}
		System.out.println(sdf.format(new Date())+" bruger:"+brugernavn+" opret tilbud:"+ tilbud.koreskole_id +" row(s) affected:" + i);
		return false;
	}

	@Override
	public boolean aendreTilbud(String brugernavn, String kodeord, int tilbudID, Tilbud tilbud) throws RemoteException { //en bruger kan ændre en af sine tilbud
		int i = 0;
		String ejer = "";
		if (logInd(brugernavn, kodeord)){
			ejer=jdbc.getTilbudFraId(tilbudID).koreskole_id;
			if (ejer.equals(brugernavn)){
				i=jdbc.redigerTilbud(tilbudID, tilbud);
				System.out.println(sdf.format(new Date())+" bruger:"+brugernavn+" rediger tilbud:"+ tilbud.id +" row(s) affected:" + i);
				return true;
			}
			System.out.println(sdf.format(new Date())+" bruger:"+brugernavn+" prøver at redigere tilbud:"+ tilbud.id +" som tilhører:"+ ejer +" row(s) affected:" + i);

 			}
		return false;
	}

	@Override
	public boolean sletTilbud(String brugernavn, String kodeord, int[] tilbudID) throws RemoteException { //lader en bruger slette et eller flere af sine tilbud
		int slettet = 0;
		String ejer = "";
		if (logInd(brugernavn, kodeord)){
			for (int j = 0; j < tilbudID.length; j++) {
			ejer=jdbc.getTilbudFraId(tilbudID[j]).koreskole_id;
			if (ejer.equals(brugernavn)){
					int i=jdbc.sletTilbud(tilbudID[j]);
					slettet = slettet + i;
				}
				System.out.println(sdf.format(new Date())+" bruger:"+brugernavn+" slet tilbud:"+ tilbudID +" row(s) affected:" + slettet);
				return true;
			}
			System.out.println(sdf.format(new Date())+" bruger:"+brugernavn+" prøver at slette tilbud:"+ tilbudID +" som tilhører:"+ ejer +" row(s) affected:" + slettet);
		}
		return false;
	}

	@Override
	public String getTilbudKøreskole(String brugernavn, String kodeord) throws RemoteException { //en bruger kan hente sine tilbud

		ArrayList<Tilbud> tilbud = new ArrayList<Tilbud>();
		String ejer = "";
		if (logInd(brugernavn, kodeord)){
			tilbud = jdbc.getTilbudFraKøreskole(brugernavn);
			System.out.println(sdf.format(new Date())+" bruger:"+brugernavn+" henter sine tilbud: row(s) affected:" + tilbud.size());
		}

		String str = gson.toJson(tilbud);
		printJson(str);
		return str;
	}


	@Override
	public String getKøreskole(String brugernavn, String kodeord) throws RemoteException {
		int i = 0;
		Køreskole k = null;
		String ejer = "";
		if (logInd(brugernavn, kodeord)){
			k = jdbc.getKøreskole(brugernavn);
			System.out.println(sdf.format(new Date())+" bruger:"+brugernavn+" henter informationer om sin køreskole: row(s) affected:" + k.id);
		}
		System.out.println(gson.toJson(k));
		return gson.toJson(k);
	}


	@Override
	public boolean opretKøreskole(String brugernavn, String kodeord, Køreskole køreskole) throws RemoteException { //en bruger kan oprette en køreskole for sig selv
		int i;
		if (logInd(brugernavn, kodeord)){
			køreskole.id=brugernavn;
			//køreskole.mail=ba.hentBrugerOffentligt(brugernavn).email;
			i=jdbc.opretkøreskole(køreskole);
			System.out.println(sdf.format(new Date())+" bruger:"+brugernavn+" opretter køreskole: row(s) affected:" + i);
			if (i>=1){
				return true;
			}
		}
		return false;
	}

	//----------------------------normal bruger slut--------------------------//



	//----------------------------anonym bruger--------------------------//

	@Override
	public String getTilbudFraPostnummer(int postnummer) throws RemoteException { //anonyme alle skal kunne kigge på de tilbud der er givet fra alle køreskolerne
		ArrayList<TilbudTilBrugere> tilbud;
		tilbud=jdbc.getTilbudTilBrugereFraPostnummer(postnummer);
		System.out.println(sdf.format(new Date())+" Tilbud hentet fra postnummer:"+postnummer+" antal tilbud:"+tilbud.size());
		String str = gson.toJson(tilbud);
		printJson(str);
		return str;
	}

	@Override
	public String getAlleTilbud() throws RemoteException { //anonyme brugere skal også kunne hente alle tilbud og kigge på dem.
		ArrayList<TilbudTilBrugere> tilbud = jdbc.getAlleTilbud();
		String str = gson.toJson(tilbud);
		System.out.println(sdf.format(new Date())+" alle tilbud hentet. antal tilbud:"+tilbud.size());
		printJson(str);
		return str;
	}

	@Override
	public String getTilbudMellemPrisFraPostnummer(int postnummer, int minimumPris, int maximumPris) throws RemoteException {

		ArrayList<TilbudTilBrugere> tilbud = jdbc.getTilbudMellemPrisFraPostnummer(postnummer, minimumPris, maximumPris);
		String str = gson.toJson(tilbud);
		System.out.println(sdf.format(new Date())+" tilbud hentet fra postnummer:"+postnummer+". under kr:"+maximumPris+". antal tilbud:"+tilbud.size());
		printJson(str);
		return str;
	}

	@Override
	public boolean opretKommentarer(String kommentar, int rating, String køreskoleId) throws RemoteException {
		return false;
	}

	//----------------------------anonym bruger slut--------------------------//

	//---------------------------ekstra metoder-------------------------------//

	private void printJson(String str) {
		String[] s = str.split("}");
		for (int j = 0; j < s.length; j++) {
			System.out.println(s[j]);
		}

	}



}