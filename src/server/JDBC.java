package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JDBC {
	private Connector c;
	private Connection con;

	public JDBC() {
		this.c = new Connector();
		this.con = c.getConnection();
	}

	public void usekoreskoleDatabase() {
		try {
			String s = "use koreskole";
			PreparedStatement p = con.prepareStatement(s);
			p.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int opretkøreskole(Køreskole køreskole) {
		String s = "INSERT INTO koreskoler(koreskole_id, koreskolenavn, adresse, postnummer, telefonnummer, mail) values (?, ?, ?, ?, ?, ?);";
		int updated = 0;
		try {
			PreparedStatement p = con.prepareStatement(s);
			p.setString(1, køreskole.id);
			p.setString(2, køreskole.navn);
			p.setString(3, køreskole.adresse);
			p.setInt(4, køreskole.postnummer);
			p.setInt(5, køreskole.telefonnummer);
			p.setString(6, køreskole.mail);
			updated = p.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return updated;

	}

	public Køreskole getKøreskole(String id) {
		String s = "SELECT * FROM koreskoler where koreskole_id = ?;";
		ResultSet rs = null;
		Køreskole k=null;
		try {
			k = new Køreskole();
			PreparedStatement p = con.prepareStatement(s);
			p.setString(1, id);
			rs = p.executeQuery();
			while (rs.next()){
				k = køreskoleBuilder(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return k;

	}

	public int opretTilbud(Tilbud t) {

		String s = "INSERT INTO tilbud(koreskole_id, pris,korekort_type, lynkursus,bilmarke, bilstorrelse, kon, beskrivelse, " //8
				+"tilgangelig_mandag, tilgangelig_tirsdag, tilgangelig_onsdag, tilgangelig_torsdag, tilgangelig_fredag, tilgangelig_lordag, tilgangelig_sondag)" //7
				+" values(?, ?, ?, ?, ?, ?, ?, ?, " //8
				+"?, ?, ?, ?, ?, ?, ?);"; //7

		int i = 0;

		try {
			PreparedStatement p = con.prepareStatement(s);
			p.setString(1, t.koreskole_id);
			p.setInt(2, t.pris);
			p.setString(3, t.korekort_type);
			p.setInt(4, t.lynkursus);
			p.setString(5, t.bilmarke);
			p.setString(6, t.bilstørrelse);
			p.setString(7, t.køn);
			p.setString(8, t.beskrivelse);

			//uge dage
			p.setInt(9, t.tilgængeligeDage.tilgængelig_mandag);
			p.setInt(10, t.tilgængeligeDage.tilgængelig_tirsdag);
			p.setInt(11, t.tilgængeligeDage.tilgængelig_onsdag);
			p.setInt(12, t.tilgængeligeDage.tilgængelig_torsdag);
			p.setInt(13, t.tilgængeligeDage.tilgængelig_fredag);
			p.setInt(14, t.tilgængeligeDage.tilgængelig_lørdag);
			p.setInt(15, t.tilgængeligeDage.tilgængelig_søndag);

			i=p.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			return i;
		}

		return i;

	}

	public int sletTilbud(int tilbud_id) {
		String s = "DELETE FROM tilbud WHERE tilbud_id = ?;";
		PreparedStatement ps;
		int i = 0;
		try {
			ps = con.prepareStatement(s);
			ps.setInt(1, tilbud_id);
			i = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return i;


	}


	public int redigerTilbud(int gammelt_tilbuds_id, Tilbud nytTilbud) {

		String s = "UPDATE tilbud t "
				+ "SET t.pris = ?, t.korekort_type = ?, t.lynkursus = ?, t.bilmarke = ?, t.bilstorrelse = ?, t.kon = ?, beskrivelse = ? ,"
				+ "tilgangelig_mandag = ?, tilgangelig_tirsdag = ?, tilgangelig_onsdag = ?, tilgangelig_torsdag = ?, tilgangelig_fredag = ?, tilgangelig_lordag = ?, tilgangelig_sondag = ? "
				+ "WHERE t.tilbud_id=?;";

		PreparedStatement ps;
		int i = 0;
		try {
			ps = con.prepareStatement(s);
			//-----------------om tilbuddet----------
			ps.setInt(1, nytTilbud.pris);
			ps.setString(2, nytTilbud.korekort_type);
			ps.setInt(3, nytTilbud.lynkursus);
			ps.setString(4, nytTilbud.bilmarke);
			ps.setString(5, nytTilbud.bilstørrelse);
			ps.setString(6, nytTilbud.køn);
			ps.setString(7, nytTilbud.beskrivelse);

			//-------------tilgængelige dage-----------
			ps.setInt(8, nytTilbud.tilgængeligeDage.tilgængelig_mandag);
			ps.setInt(9, nytTilbud.tilgængeligeDage.tilgængelig_tirsdag);
			ps.setInt(10, nytTilbud.tilgængeligeDage.tilgængelig_onsdag);
			ps.setInt(11, nytTilbud.tilgængeligeDage.tilgængelig_torsdag);
			ps.setInt(12, nytTilbud.tilgængeligeDage.tilgængelig_fredag);
			ps.setInt(13, nytTilbud.tilgængeligeDage.tilgængelig_lørdag);
			ps.setInt(14, nytTilbud.tilgængeligeDage.tilgængelig_søndag);

			//-------------på id nr----------------
			ps.setInt(15, gammelt_tilbuds_id);

			i = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return i;

	}



	public int sletKøreskole(String køreskole_id) {

		String s = "DELETE FROM koreskoler WHERE koreskole_id = ?;";
		PreparedStatement ps;
		int i = 0;
		try {
			ps = con.prepareStatement(s);
			ps.setString(1, køreskole_id);
			i = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	public ArrayList<Tilbud> getTilbudFraKøreskole(String brugernavn) {

		String s = "SELECT * FROM tilbud where koreskole_id = ?;";
		ResultSet rs = null;
		ArrayList<Tilbud> tilbud = new ArrayList<Tilbud>();
		try {
			PreparedStatement p = con.prepareStatement(s);
			p.setString(1, brugernavn);
			rs = p.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			while(rs.next()) {
				Tilbud t = tilbudBuilder(rs, false);
				tilbud.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tilbud;

	}

	public ArrayList<TilbudTilBrugere> getAlleTilbud() {

		String s = "SELECT * FROM tilbud t natural join koreskoler k;";
		ResultSet rs = null;
		ArrayList<TilbudTilBrugere> tilbud = new ArrayList<TilbudTilBrugere>();
		try {
			PreparedStatement p = con.prepareStatement(s);
			rs = p.executeQuery();
			while(rs.next()) {
				TilbudTilBrugere t = tilbudTilBrugereBuilder(rs);
				tilbud.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tilbud;
	}

	public ArrayList<Køreskole> getAlleKøreskoler(){

		String s = "SELECT * FROM koreskoler;";
		ResultSet rs = null;
		ArrayList<Køreskole> køreskoler = new ArrayList<Køreskole>();
		try {
			PreparedStatement p = con.prepareStatement(s);
			rs = p.executeQuery();

			while(rs.next()) {
				Køreskole k = køreskoleBuilder(rs);
				køreskoler.add(k);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return køreskoler;
	}

	public Tilbud getTilbudFraId(int id){

		String s = "SELECT * FROM tilbud where tilbud_id = ?;";
		ResultSet rs ;
		Tilbud t = null;
		try {
			PreparedStatement p = con.prepareStatement(s);
			p.setInt(1, id);
			rs = p.executeQuery();
			while (rs.next()) {
				t = tilbudBuilder(rs, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return t;


	}

	public ArrayList<TilbudTilBrugere> getTilbudTilBrugereFraPostnummer(int postnummer) {
		ArrayList<TilbudTilBrugere> tilbud = new ArrayList<TilbudTilBrugere>();
		String s = "SELECT * FROM tilbud t natural join koreskoler k where k.postnummer=?;";
		ResultSet rs = null;
		try {
			PreparedStatement p = con.prepareStatement(s);
			p.setInt(1, postnummer);
			rs = p.executeQuery();
			while (rs.next()){
				TilbudTilBrugere tilbudTilBrugere = tilbudTilBrugereBuilder(rs);
				tilbud.add(tilbudTilBrugere);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tilbud;
	}



	public ArrayList<TilbudTilBrugere> getTilbudMellemPrisFraPostnummer(int postnummer, int minimumPris, int maximumPris){
		ArrayList<TilbudTilBrugere> tilbud = new ArrayList<TilbudTilBrugere>();
		String s = "SELECT * FROM tilbud t natural join koreskoler k where k.postnummer=? and t.pris < ?;";
		ResultSet rs = null;
		try {
			PreparedStatement p = con.prepareStatement(s);
			p.setInt(1, postnummer);
			//p.setInt(2, minimumPris);
			p.setInt(2, maximumPris);
			rs = p.executeQuery();
			while (rs.next()){
				TilbudTilBrugere tilbudTilBrugere = tilbudTilBrugereBuilder(rs);
				tilbud.add(tilbudTilBrugere);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tilbud;


	}



	private Tilbud tilbudBuilder(ResultSet rs, boolean tilKunder){
		try {
			Tilbud t = new Tilbud();
			if (!tilKunder){
				t.id =rs.getInt(1);
				t.koreskole_id=rs.getString(2);
			} else {
				t.id=0;
				t.koreskole_id="tom";
			}
			t.pris = rs.getInt(3);
			t.korekort_type = rs.getString(4);
			t.lynkursus = rs.getInt(5);
			t.bilmarke = rs.getString(6);
			t.bilstørrelse = rs.getString(7);
			t.køn = rs.getString(8);

			//-----------------dage------------------//
			t.tilgængeligeDage = new TilgængeligeDage();
			t.tilgængeligeDage.tilgængelig_mandag = rs.getInt(9);
			t.tilgængeligeDage.tilgængelig_tirsdag = rs.getInt(10);
			t.tilgængeligeDage.tilgængelig_onsdag = rs.getInt(11);
			t.tilgængeligeDage.tilgængelig_torsdag = rs.getInt(12);
			t.tilgængeligeDage.tilgængelig_fredag = rs.getInt(13);
			t.tilgængeligeDage.tilgængelig_lørdag = rs.getInt(14);
			t.tilgængeligeDage.tilgængelig_søndag = rs.getInt(15);
			t.beskrivelse=rs.getString(16);
			return t;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}

	}

	private Køreskole køreskoleBuilder(ResultSet rs) {
		Køreskole k;
		try {
			k = new Køreskole();
			k.id = rs.getString(1);
			k.navn = rs.getString(2);
			k.adresse = rs.getString(3);
			k.postnummer = rs.getInt(4);
			k.telefonnummer = rs.getInt(5);
			k.mail = rs.getString(6);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return k;
	}

	private TilbudTilBrugere tilbudTilBrugereBuilder(ResultSet rs) {
		TilbudTilBrugere tilbudTilBrugere = null;
		try {
			tilbudTilBrugere = new TilbudTilBrugere();
		} catch (Exception e) {
			e.printStackTrace();
		}
		tilbudTilBrugere.tilbud=tilbudBuilder(rs, true);
		Køreskole k;
		try {
			k = new Køreskole();
			k.navn = rs.getString(17);
			k.adresse = rs.getString(18);
			k.postnummer = rs.getInt(19);
			k.telefonnummer = rs.getInt(20);
			k.mail = rs.getString(21);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		tilbudTilBrugere.køreskole=k;
		return tilbudTilBrugere;
	}





}