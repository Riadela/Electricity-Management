package prog.server;



import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import prog.shared.Contratto;
import prog.shared.exception.CercaException;
import prog.shared.exception.ComponenteNonTuoException;
import prog.shared.exception.InesistenteException;
import prog.shared.exception.ListaException;

public class GestoreContratto {

	private Contratto contratto = new Contratto();
	private ArrayList<Contratto> elencoContratti = new ArrayList<Contratto>();
	
	private static GestoreContratto instance = null;
	private GestoreContratto() {
		
	}
	
	public static GestoreContratto getInstance() {
		if(instance == null) {
			instance = new GestoreContratto();
		}
		return instance;
	}
	
	//chiama metodo del dbMan contente statement e query per aggiungere un contratto 
	public void addContratto(String idutente, Date data, int idcomppos, int idcompneg) throws RemoteException, InesistenteException, CercaException, ComponenteNonTuoException{
		try {
			DBManager.getInstance().aggiungiContratto(idutente, data, idcomppos, idcompneg);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//metodo dbman cancella contratto tramite id contratto
	public void delContratto(int id) throws RemoteException, InesistenteException{
		try {
			DBManager.getInstance().eliminaContratto(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//metodo dbman cerca un contratto tramite id contratto
	public Contratto cercaContratto(int id) throws RemoteException, CercaException{
		GestoreContratto.getInstance().resetContratto();
		try {
			contratto = DBManager.getInstance().cercaContratto(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contratto; 
	}
	
	//cerca elenco contratti per contratto tramite idcliente
	public ArrayList<Contratto> cercaContrattiCliente(String id) throws RemoteException, ListaException{
		elencoContratti.clear();
		try {
			elencoContratti = DBManager.getInstance().cercaContrattiCliente(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return elencoContratti;
	}
	public void resetContratto(){
		contratto.setDataInizio(null);
		contratto.setIDContratto(0);
		contratto.setIdutente(null);
	}
}
