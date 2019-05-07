package server;

import java.rmi.Naming;
import java.sql.Connection;
import java.sql.ResultSet;
/**
 * main metode
 * @author Alexander Kjeldsen */

public class Main {

	public static void main(String[] args) {

		try {
			java.rmi.registry.LocateRegistry.createRegistry(1099); // start i server-JVM
			KøreskolePriserImplementering ki = new KøreskolePriserImplementering();
//			Naming.rebind("rmi://localhost:1235/koereskolepriser", ki);
			Naming.rebind("rmi://localhost:1099/koereskolepriser", ki);
//			Naming.rebind("rmi://dist.saluton.dk:5500/koereskolepriser", ki);
			System.out.println("serveren er startet");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
