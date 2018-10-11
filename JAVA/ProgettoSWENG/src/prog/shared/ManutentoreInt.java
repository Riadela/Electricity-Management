package prog.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;

import prog.shared.exception.CercaException;
import prog.shared.exception.InesistenteException;
import prog.shared.exception.ListaException;
import prog.shared.exception.PrivilegiInsufficientiException;

public interface ManutentoreInt extends Remote {

	//aggiungi guasto OK
	public void addGuasto(String dett, int idComp, String idcliente, Date data) throws RemoteException, InesistenteException, CercaException;
	//prendi in carico OK
	public void workOnGuasto(int idG, String idMan, String dett, Date data) throws RemoteException, InesistenteException, PrivilegiInsufficientiException;
	//ripara guasto OK
	public void ripGuasto(int idG, String dett, Date data) throws RemoteException, InesistenteException;
	//vis elenco guasti  OK
	public ArrayList<Guasto> getElencoGuasti(StatoGuasto sg) throws RemoteException, ListaException;
	//vis elenco guasti del manutentore OK
	public ArrayList<Guasto> getElencoGuastiMan(String idman) throws RemoteException, ListaException;
	
	//aggiungi componente OK
	public void addComponente(Tipo t, Posizione p) throws RemoteException;
	//mod val energetico componente		OK
	public void modComponente(int idcomp, float val) throws RemoteException, InesistenteException;
	//elimina componente OK
	public void delComponente(int id) throws RemoteException, InesistenteException;
	
	//cerca componente specifico  OK
	public Componente cercaComp(int id) throws RemoteException, CercaException; 
	//cerca elenco componenti di un tipo OK
	public ArrayList<Componente> cercaElencoComp(Tipo t) throws RemoteException, ListaException;
	//aggiuge consumo
	public void addConsumo(int idContr) throws RemoteException, CercaException, InesistenteException;
	
	
	
}
