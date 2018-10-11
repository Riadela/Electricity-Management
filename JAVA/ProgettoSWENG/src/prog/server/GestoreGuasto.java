package prog.server;

import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import prog.shared.Guasto;
import prog.shared.StatoGuasto;
import prog.shared.exception.CercaException;
import prog.shared.exception.InesistenteException;
import prog.shared.exception.ListaException;
import prog.shared.exception.PrivilegiInsufficientiException;

public class GestoreGuasto {

	private ArrayList<Guasto> elencoGuasti = new ArrayList<Guasto>();
	
	private static GestoreGuasto instance = null;
	
	private GestoreGuasto() {
		
	}
	
	public static GestoreGuasto getInstance() {
		if(instance == null) {
			instance = new GestoreGuasto();
		}
		return instance;
	}
	
	//aggiungi guasto
	public void addGuasto(String dett, int idComp, String idcliente, Date data) throws RemoteException, InesistenteException, CercaException{
		try {
			DBManager.getInstance().aggiungiGuasto(dett, idComp, idcliente, data);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//aggiungi guasto
		public void segnalaGuasto(String dett, int idComp, String idcliente, Date data) throws RemoteException, InesistenteException, CercaException{
			try {
				DBManager.getInstance().segnalaGuasto(dett, idComp, idcliente, data);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	//prendi in carico
	public void workOnGuasto(int idG, String idMan, String dett, Date data) throws RemoteException, InesistenteException, PrivilegiInsufficientiException{
		try {
			DBManager.getInstance().prendiInCaricoGuasto(idG, idMan, dett, data);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//ripara guasto
	public void ripGuasto(int idG, String dett, Date data) throws RemoteException, InesistenteException{
		try {
			DBManager.getInstance().riparaGuasto(idG, dett, data);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//vis elenco guasti
	public ArrayList<Guasto> getElencoGuasti(StatoGuasto sg) throws RemoteException, ListaException{
		elencoGuasti.clear();
		try {
			elencoGuasti = DBManager.getInstance().getElencoGuasti(sg);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return elencoGuasti;
	}
	
	//vis elenco guasti man
	public ArrayList<Guasto> getElencoGuastiMan(String idman) throws RemoteException, ListaException {
		elencoGuasti.clear();
		try {
			elencoGuasti = DBManager.getInstance().getElencoGuastiMan(idman);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return elencoGuasti;
	}
	
	//visualiz guasti non risolti del cliente
	public ArrayList<Guasto> visualGuasti(String id) throws RemoteException, ListaException{
		elencoGuasti.clear();
		try {
			elencoGuasti= DBManager.getInstance().visualGuasti(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return elencoGuasti;
	}
}
