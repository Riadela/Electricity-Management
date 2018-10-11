package prog.server;


import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

import prog.shared.Contratto;
import prog.shared.exception.CercaException;
import prog.shared.exception.InesistenteException;
import prog.shared.exception.ListaException;

public class GestoreConsumo {

	private static GestoreConsumo instance = null;
	private ArrayList<Contratto> listcont = new ArrayList<Contratto>();

	private GestoreConsumo() {

	}
	
	public static GestoreConsumo getInstance() {
		if(instance == null) {
			instance = new GestoreConsumo();
		}
		return instance;
	}
	// Cerca per id del consomo e ottiene info sui consumi
	public ArrayList<Contratto> cercaConsumo(String id) throws RemoteException, ListaException{
		listcont.clear();
		try {
			listcont = DBManager.getInstance().cercaContrattiCliente(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listcont;
	}
	
	public void addConsumo(int idContr) throws RemoteException, CercaException, InesistenteException{
		try {
			DBManager.getInstance().aggiungiConsumo(idContr);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
