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
    private Brugeradmin ba ;


    protected KøreskolePriserImplementering() throws RemoteException, MalformedURLException, NotBoundException {

        super();
    }

    //-------------------------admin bruger---------------------------------//

    @Override
    public boolean logIndAdmin(String adminBrugernavn, String adminKodeord) throws RemoteException { //tjekker om kombinationen er adminstrator.
        if (adminBrugernavn.equals("s165477") && adminKodeord.equals("kodekode")||
                adminBrugernavn.equals("s175132") && adminKodeord.equals("DS2019")) {
            System.out.println(sdf.format(new Date())+" ---------admin login------------- "+adminBrugernavn);
            return true;
        } else {
            System.out.println(sdf.format(new Date())+" ---------admin login -FORKERT---- bruger:"+adminBrugernavn+"-kode:"+adminKodeord);

            return false;
        }
    }

    @Override
    public boolean sletKøreskole(String adminBrugernavn, String adminKodeord, String køreskole_id) throws RemoteException { //admin kan slette koreskole
        int i = 0;
        if (logIndAdmin(adminBrugernavn, adminKodeord)){
            i=jdbc.sletKøreskole(køreskole_id);
            System.out.println(sdf.format(new Date())+" adminBruger:"+adminBrugernavn+" slet koreskole:"+ køreskole_id + " row(s) affected:" + i);
            if (i>=1){
                return true;
            }
        }
        System.out.println(sdf.format(new Date())+" adminBruger:"+adminBrugernavn+" slet koreskole:"+ køreskole_id + " row(s) affected:" + i);
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
        System.out.println(sdf.format(new Date())+" adminBruger:"+adminBrugernavn+" slet tilbud:"+ tilbud_id + " row(s) affected:" + i);
        return false;
    }

    @Override
    public boolean opretKøreskoleAdmin(String adminBrugernavn, String adminKodeord, Koreskole koreskole) throws RemoteException {  //en adminstrator kan oprette en koreskole
        int i = 0;
        if (logIndAdmin(adminBrugernavn, adminKodeord)) {
            i=jdbc.opretkøreskole(koreskole);
            System.out.println(sdf.format(new Date())+" adminBruger:"+adminBrugernavn+" opret koreskole:"+ koreskole.id + " row(s) affected:" + i);
            if (i<=1){
                return true;
            }
        }
        return false;
    }

    @Override
    public String getAlleKøreskoler(String adminBrugernavn, String adminKodeord) throws RemoteException { //en adminstrator kan hente alle køreskoler
        ArrayList<Koreskole> køreskoler = new ArrayList<Koreskole>();
        if (logIndAdmin(adminBrugernavn, adminKodeord)) {
            køreskoler=jdbc.getAlleKøreskoler();
        }
        String str = gson.toJson(køreskoler);

        //		printJson(str);
        System.out.println(sdf.format(new Date())+" adminBruger:"+adminBrugernavn+" get alle koreskole row(s) affected:" + køreskoler.size());

        return str;
    }

    @Override
    public String getTilbudFraKøreskoler(String adminBrugernavn, String adminKodeord, String koreskoleid) throws RemoteException {
        if (logIndAdmin(adminBrugernavn, adminKodeord)){
            ArrayList<Tilbud> tilbud =  jdbc.getTilbudFraKøreskole(koreskoleid);
            System.out.println(sdf.format(new Date())+" adminBruger:"+adminBrugernavn+" get tilbud fra køreskole:" + koreskoleid + " row(s) affected:" + tilbud.size());
            return gson.toJson(tilbud);
        }
        System.out.println(sdf.format(new Date())+" adminBruger:"+adminBrugernavn+" get tilbud fra køreskole:" + koreskoleid + " row(s) affected:" + 0);

        return null;
    }
    //----------------------------Admin slut-----------------------------//



    //----------------------------normal bruger--------------------------//

    @Override
    public boolean logInd(String brugernavn, String kodeord) throws RemoteException { //tjekker om en bruger er en bruger

        try {
            ba = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");
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
        System.out.println(sdf.format(new Date())+" bruger:"+brugernavn+" rediger tilbud:"+ tilbud.id +" row(s) affected:" + i);
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
        System.out.println(sdf.format(new Date())+" bruger:"+brugernavn+" slet tilbud:"+ tilbudID +" row(s) affected:" + slettet);
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
        System.out.println(sdf.format(new Date())+" bruger:"+brugernavn+" henter sine tilbud: row(s) affected:" + tilbud.size());
        return str;
    }


    @Override
    public String getKøreskole(String brugernavn, String kodeord) throws RemoteException {
        int i = 0;
        Koreskole k = null;
        String ejer = "";
        if (logInd(brugernavn, kodeord)){
            k = jdbc.getKøreskole(brugernavn);
            System.out.println(sdf.format(new Date())+" bruger:"+brugernavn+" henter informationer om sin koreskole: row(s) affected:" + k.id);
        }
//		System.out.println(gson.toJson(k));
        return gson.toJson(k);
    }


    @Override
    public boolean opretKøreskole(String brugernavn, String kodeord, Koreskole koreskole) throws RemoteException { //en bruger kan oprette en koreskole for sig selv
        int i = 0;
        if (logInd(brugernavn, kodeord)){
            koreskole.id=brugernavn;
            //koreskole.mail=ba.hentBrugerOffentligt(brugernavn).email;
            i=jdbc.opretkøreskole(koreskole);
            System.out.println(sdf.format(new Date())+" bruger:"+brugernavn+" opretter koreskole: row(s) affected:" + i);
            if (i>=1){
                return true;
            }
        }
        System.out.println(sdf.format(new Date())+" bruger:"+brugernavn+" opretter koreskole: row(s) affected:" + i);
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
//		printJson(str);
        return str;
    }

    @Override
    public String getAlleTilbud() throws RemoteException { //anonyme brugere skal også kunne hente alle tilbud og kigge på dem.
        ArrayList<TilbudTilBrugere> tilbud = jdbc.getAlleTilbud();
        String str = gson.toJson(tilbud);
        System.out.println(sdf.format(new Date())+" alle tilbud hentet. antal tilbud:"+tilbud.size());
//		printJson(str);
        return str;
    }

    @Override
    public String getTilbudMellemPrisFraPostnummer(int postnummer, int minimumPris, int maximumPris) throws RemoteException {

        ArrayList<TilbudTilBrugere> tilbud = jdbc.getTilbudMellemPrisFraPostnummer(postnummer, minimumPris, maximumPris);
        String str = gson.toJson(tilbud);
        System.out.println(sdf.format(new Date())+" tilbud hentet fra postnummer:"+postnummer+". under kr:"+maximumPris+". antal tilbud:"+tilbud.size());
//		printJson(str);
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