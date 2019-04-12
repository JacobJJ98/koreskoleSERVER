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

	}

}
