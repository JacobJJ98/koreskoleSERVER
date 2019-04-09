package server;

import java.rmi.Naming;
import java.sql.Connection;
import java.sql.ResultSet;

public class Main {

	public static void main(String[] args) {
		
		try {
		java.rmi.registry.LocateRegistry.createRegistry(1235); // start i server-JVM                
		KøreskolePriserImplementering ki = new KøreskolePriserImplementering(); 
		Naming.rebind("rmi://localhost:1235/koereskolepriser", ki);
		System.out.println("serveren er startet");
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		JDBC j = null;
		try {
		j = new JDBC();
		Køreskole k = new Køreskole();
		k.id="s165479";
		k.navn="peter";
		k.adresse="tingstedvej";
		k.mail="mail@mail.dk";
		k.postnummer=4000;
		k.telefonnummer=88898876;

		j.usekoreskoleDatabase();

		j.opretkøreskole(k);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
		Køreskole ks = j.getKøreskole("s165477");
		System.out.println(ks.toString());
		Tilbud t = new Tilbud();
		t.koreskole_id=ks.id;
		t.beskrivelse="jeg er en smart fyr fra holte";
		t.bilmarke="andet";
		t.bilstørrelse="stor";
		t.korekort_type="b";
		t.køn="mand";
		t.lynkursus=0;
		t.pris=15000;
		t.tilgængeligeDage = new TilgængeligeDage();
		t.tilgængeligeDage.tilgængelig_mandag=1;
		t.tilgængeligeDage.tilgængelig_tirsdag=1;
		t.tilgængeligeDage.tilgængelig_onsdag=1;
		t.tilgængeligeDage.tilgængelig_torsdag=1;
		t.tilgængeligeDage.tilgængelig_fredag=1;
		t.tilgængeligeDage.tilgængelig_lørdag=1;
		t.tilgængeligeDage.tilgængelig_søndag=1;
		
		
		j.opretTilbud(t);

		j.con.close();
		} catch (Exception e) {
			e.printStackTrace();		}
		System.exit(0);
		*/
	}

}
