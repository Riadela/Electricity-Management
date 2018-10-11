package prog.server;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

import prog.shared.Componente;
import prog.shared.Posizione;
import prog.shared.Tipo;
import prog.shared.exception.CercaException;
import prog.shared.exception.InesistenteException;
import prog.shared.exception.ListaException;

public class GestoreComponente {
	
	private static GestoreComponente instance = null;
	private Componente componente = new Componente();
	private ArrayList<Componente> elencoComp = new ArrayList<Componente>();
	
		   
	private GestoreComponente(){
		
	}
	
	public static GestoreComponente getInstance() {
    if(instance == null) {
       instance = new GestoreComponente();
    }
    return instance;
	}
	
	// Aggiungi componente alla relativa tabella, e la posizione nella tabella appropriata
	public void addComponente(Tipo t, Posizione p) throws RemoteException{		
		try {
			DBManager.getInstance().aggiungiComponente(t, p);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Modifica Valore Energetico di un componente
	public void modComponente(int idcomp, float val) throws RemoteException, InesistenteException{	
		try {
			DBManager.getInstance().modificaComponente(idcomp, val);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//elimina comp
	public void delComponente(int id) throws RemoteException, InesistenteException{
		try {
			DBManager.getInstance().eliminaComponente(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//cerca comp tramite id
	public Componente cercaComp(int id) throws RemoteException, CercaException{
		GestoreComponente.getInstance().resetComponente();

		try {
			componente=DBManager.getInstance().cercaComponente(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return componente;
	}
		
	// Cerca tipo di componente a seconda del tipo
	public ArrayList<Componente> cercaElencoComp(Tipo t) throws RemoteException, ListaException{
		elencoComp.clear();
		try {
			elencoComp=DBManager.getInstance().cercaElencoComp(t);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return elencoComp;
	}
	
	//resetta attributo componente del gestore
	public void resetComponente(){
		componente.setIDComponente(0);
		componente.setPosizione(null);
		componente.setTipo(null);
		componente.setValoreEnerg(0);
	}
}
