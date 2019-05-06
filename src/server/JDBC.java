package server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * DAO
 * @author Alexander Kjeldsen */

public class JDBC {
	private Connector c;

	public JDBC() {
		this.c = new Connector();
	}

	public void usekoreskoleDatabase() {
		try {
			String s = "use koreskole";
			PreparedStatement p = c.getConnection().prepareStatement(s);
			p.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				c.getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public int opretkøreskole(Koreskole koreskole) {
		String s = "INSERT INTO koreskoler(koreskole_id, koreskolenavn, adresse, postnummer, telefonnummer, mail) values (?, ?, ?, ?, ?, ?);";
		int updated = 0;
		try {
			PreparedStatement p = c.getConnection().prepareStatement(s);
			p.setString(1, koreskole.id);
			p.setString(2, koreskole.navn);
			p.setString(3, koreskole.adresse);
			p.setInt(4, koreskole.postnummer);
			p.setInt(5, koreskole.telefonnummer);
			p.setString(6, koreskole.mail);
			updated = p.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				c.getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return updated;

	}

	public Koreskole getKøreskole(String id) {
		String s = "SELECT * FROM koreskoler where koreskole_id = ?;";
		ResultSet rs = null;
		Koreskole k=null;
		try {
			k = new Koreskole();
			PreparedStatement p = c.getConnection().prepareStatement(s);
			p.setString(1, id);
			rs = p.executeQuery();
			while (rs.next()){
				k = køreskoleBuilder(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				c.getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
			PreparedStatement p = c.getConnection().prepareStatement(s);
			p.setString(1, t.koreskole_id);
			p.setInt(2, t.pris);
			p.setString(3, t.korekort_type);
			p.setInt(4, t.lynkursus);
			p.setString(5, t.bilmarke);
			p.setString(6, t.bilstorrelse);
			p.setString(7, t.kon);
			p.setString(8, t.beskrivelse);

			//uge dage
			p.setInt(9, t.tilgangeligeDage.tilgangelig_mandag);
			p.setInt(10, t.tilgangeligeDage.tilgangelig_tirsdag);
			p.setInt(11, t.tilgangeligeDage.tilgangelig_onsdag);
			p.setInt(12, t.tilgangeligeDage.tilgangelig_torsdag);
			p.setInt(13, t.tilgangeligeDage.tilgangelig_fredag);
			p.setInt(14, t.tilgangeligeDage.tilgangelig_lordag);
			p.setInt(15, t.tilgangeligeDage.tilgangelig_sondag);

			i=p.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			return i;
		} finally {
			try {
				c.getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return i;

	}

	public int sletTilbud(int tilbud_id) {
		String s = "DELETE FROM tilbud WHERE tilbud_id = ?;";
		PreparedStatement ps;
		int i = 0;
		try {
			ps = c.getConnection().prepareStatement(s);
			ps.setInt(1, tilbud_id);
			i = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				c.getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
			ps = c.getConnection().prepareStatement(s);
			//-----------------om tilbuddet----------
			ps.setInt(1, nytTilbud.pris);
			ps.setString(2, nytTilbud.korekort_type);
			ps.setInt(3, nytTilbud.lynkursus);
			ps.setString(4, nytTilbud.bilmarke);
			ps.setString(5, nytTilbud.bilstorrelse);
			ps.setString(6, nytTilbud.kon);
			ps.setString(7, nytTilbud.beskrivelse);

			//-------------tilgængelige dage-----------
			ps.setInt(8, nytTilbud.tilgangeligeDage.tilgangelig_mandag);
			ps.setInt(9, nytTilbud.tilgangeligeDage.tilgangelig_tirsdag);
			ps.setInt(10, nytTilbud.tilgangeligeDage.tilgangelig_onsdag);
			ps.setInt(11, nytTilbud.tilgangeligeDage.tilgangelig_torsdag);
			ps.setInt(12, nytTilbud.tilgangeligeDage.tilgangelig_fredag);
			ps.setInt(13, nytTilbud.tilgangeligeDage.tilgangelig_lordag);
			ps.setInt(14, nytTilbud.tilgangeligeDage.tilgangelig_sondag);

			//-------------på id nr----------------
			ps.setInt(15, gammelt_tilbuds_id);

			i = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				c.getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return i;

	}



	public int sletKøreskole(String køreskole_id) {

		String s = "DELETE FROM koreskoler WHERE koreskole_id = ?;";
		PreparedStatement ps;
		int i = 0;
		try {
			ps = c.getConnection().prepareStatement(s);
			ps.setString(1, køreskole_id);
			i = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				c.getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return i;
	}

	public ArrayList<Tilbud> getTilbudFraKøreskole(String brugernavn) {

		String s = "SELECT * FROM tilbud where koreskole_id = ?;";
		ResultSet rs = null;
		ArrayList<Tilbud> tilbud = new ArrayList<Tilbud>();
		try {
			PreparedStatement p = c.getConnection().prepareStatement(s);
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
		} finally {
			try {
				c.getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return tilbud;

	}

	public ArrayList<TilbudTilBrugere> getAlleTilbud() {

		String s = "SELECT * FROM tilbud t natural join koreskoler k;";
		ResultSet rs = null;
		ArrayList<TilbudTilBrugere> tilbud = new ArrayList<TilbudTilBrugere>();
		try {
			PreparedStatement p = c.getConnection().prepareStatement(s);
			rs = p.executeQuery();
			while(rs.next()) {
				TilbudTilBrugere t = tilbudTilBrugereBuilder(rs);
				tilbud.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				c.getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return tilbud;
	}

	public ArrayList<Koreskole> getAlleKøreskoler(){

		String s = "SELECT * FROM koreskoler;";
		ResultSet rs = null;
		ArrayList<Koreskole> køreskoler = new ArrayList<Koreskole>();
		try {
			PreparedStatement p = c.getConnection().prepareStatement(s);
			rs = p.executeQuery();

			while(rs.next()) {
				Koreskole k = køreskoleBuilder(rs);
				køreskoler.add(k);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				c.getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return køreskoler;
	}

	public Tilbud getTilbudFraId(int id){

		String s = "SELECT * FROM tilbud where tilbud_id = ?;";
		ResultSet rs ;
		Tilbud t = null;
		try {
			PreparedStatement p = c.getConnection().prepareStatement(s);
			p.setInt(1, id);
			rs = p.executeQuery();
			while (rs.next()) {
				t = tilbudBuilder(rs, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				c.getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return t;


	}

	public ArrayList<TilbudTilBrugere> getTilbudTilBrugereFraPostnummer(int postnummer) {
		ArrayList<TilbudTilBrugere> tilbud = new ArrayList<TilbudTilBrugere>();
		String s = "SELECT * FROM tilbud t natural join koreskoler k where k.postnummer=?;";
		ResultSet rs = null;
		try {
			PreparedStatement p = c.getConnection().prepareStatement(s);
			p.setInt(1, postnummer);
			rs = p.executeQuery();
			while (rs.next()){
				TilbudTilBrugere tilbudTilBrugere = tilbudTilBrugereBuilder(rs);
				tilbud.add(tilbudTilBrugere);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				c.getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return tilbud;
	}



	public ArrayList<TilbudTilBrugere> getTilbudMellemPrisFraPostnummer(int postnummer, int minimumPris, int maximumPris){
		ArrayList<TilbudTilBrugere> tilbud = new ArrayList<TilbudTilBrugere>();
		String s = "SELECT * FROM tilbud t natural join koreskoler k where k.postnummer=? and t.pris < ?;";
		ResultSet rs = null;
		try {
			PreparedStatement p = c.getConnection().prepareStatement(s);
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
		} finally {
			try {
				c.getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
			t.bilstorrelse = rs.getString(7);
			t.kon = rs.getString(8);

			//-----------------dage------------------//
			t.tilgangeligeDage = new TilgangeligeDage();
			t.tilgangeligeDage.tilgangelig_mandag = rs.getInt(9);
			t.tilgangeligeDage.tilgangelig_tirsdag = rs.getInt(10);
			t.tilgangeligeDage.tilgangelig_onsdag = rs.getInt(11);
			t.tilgangeligeDage.tilgangelig_torsdag = rs.getInt(12);
			t.tilgangeligeDage.tilgangelig_fredag = rs.getInt(13);
			t.tilgangeligeDage.tilgangelig_lordag = rs.getInt(14);
			t.tilgangeligeDage.tilgangelig_sondag = rs.getInt(15);
			t.beskrivelse=rs.getString(16);
			return t;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}

	}

	private Koreskole køreskoleBuilder(ResultSet rs) {
		Koreskole k;
		try {
			k = new Koreskole();
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
		Koreskole k;
		try {
			k = new Koreskole();
			k.navn = rs.getString(17);
			k.adresse = rs.getString(18);
			k.postnummer = rs.getInt(19);
			k.telefonnummer = rs.getInt(20);
			k.mail = rs.getString(21);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		tilbudTilBrugere.koreskole =k;
		return tilbudTilBrugere;
	}





}