package prog.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class MainServer {

	public static void main(String[] args) throws Exception {

		//Bind del DBManager nel registro
		//Connette DB
		
		try {
			Registry reg = LocateRegistry.createRegistry(1099);
			Server server = Server.getInstance();			
			reg.rebind("interfaceServer", server);
			System.out.println("Server DB pronto.");
			server.connect();

			
		} catch(RemoteException e ){
			e.printStackTrace();
		}
		
	}

}
