package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JDBC {
	Connector c;
	Connection con;

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

	public void opretkøreskole(Køreskole køreskole) {


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
		} finally {
			System.out.println("update(s) "+updated+" køreskole oprettet");
		}

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
		} catch (Exception e) {
			e.printStackTrace();
		}


		try {
			while(rs.next()) {
				k.id = rs.getString(1);
				k.navn = rs.getString(2);
				k.adresse = rs.getString(3);
				k.postnummer = rs.getInt(4);
				k.telefonnummer = rs.getInt(5);
				k.mail = rs.getString(6);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}



		return k;

	}

	public void opretTilbud(Tilbud t) {
		/*
		String s = "INSERT INTO tilbud(koreskole_id, pris,korekort_type, lynkursus,bilmarke, bilstorrelse, kon, beskrivelse, "
				+"tilgangelig_mandag, tilgangelig_tirsdag, tilgangelig_onsdag, tilgangelig_torsdag, tilgangelig_fredag, tilgangelig_lordag, tilgangelig_sondag)"
				+" values('s175132', 11000, 'a', 1, 'peugeot', 'stor', 'andet', 'er vi så smart i en fart eller hvad', "
				+"1, 1, 1, 0, 0, 0, 0);";
		 */
		String s = "INSERT INTO tilbud(koreskole_id, pris,korekort_type, lynkursus,bilmarke, bilstorrelse, kon, beskrivelse, " //8
				+"tilgangelig_mandag, tilgangelig_tirsdag, tilgangelig_onsdag, tilgangelig_torsdag, tilgangelig_fredag, tilgangelig_lordag, tilgangelig_sondag)" //7
				+" values(?, ?, ?, ?, ?, ?, ?, ?, " //8
				+"?, ?, ?, ?, ?, ?, ?);"; //7


		int i = 0;
		//		+ "values('s165477', 9500, 'b', 0, 'andet', 'mellem', 'kvinde', 1110000, 'jeg er en frisk fyr')";
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
		}
		System.out.println("update(s) " + i + " tilbud oprettet");

	}

	public void sletTilbud(String bruger_id, int tilbud_id) {
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

		System.out.println("update(s) "+i+" tilbud slettet (bruger:"+bruger_id+")"+"(tilbudsid:"+tilbud_id+")");

	}


	public void updateTilbud(int gammelt_tilbuds_id, Tilbud nytTilbud) {

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

		System.out.println("update(s) "+i+" tilbud ændret (tilbudsid:"+gammelt_tilbuds_id+")");

	}



	public int sletKøreskole(String køreskole_id) {

		String s = "DELETE FROM koreskoler WHERE koreskole_id = ?;";
		PreparedStatement ps;
		int i = 0;
		try {
			ps = con.prepareStatement(s);
			ps.setString(1, køreskole_id);;
			i = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	public ArrayList<Tilbud> getTilbud(String brugernavn) {

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
				Tilbud t = new Tilbud();
				t.id =rs.getInt(1);
				t.koreskole_id=rs.getString(2);
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

				tilbud.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}



		return tilbud;


	}












}