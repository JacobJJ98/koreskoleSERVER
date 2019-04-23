package server;

import org.apache.log4j.BasicConfigurator;

import java.rmi.Naming;
import java.sql.Connection;
import java.sql.ResultSet;

public class Main {

	public static void main(String[] args) {

		BasicConfigurator.configure();

		try {
			java.rmi.registry.LocateRegistry.createRegistry(5478); // start i server-JVM
			KøreskolePriserImplementering ki = new KøreskolePriserImplementering();
			Naming.rebind("rmi://localhost:5478/koereskolepriser", ki);
//			Naming.rebind("rmi://130.225.170.204:5478/koereskolepriser", ki);

			System.out.println("serveren er startet");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
