package prog.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;

import prog.shared.exception.CercaException;
import prog.shared.exception.ComponenteNonTuoException;
import prog.shared.exception.InesistenteException;
import prog.shared.exception.ListaException;
import prog.shared.exception.UtenteEsistenteException;


public interface ImpiegatoInt extends Remote {

	
		
	
	//aggiungi utente OK
	public void addUtente(String idUt, String pw, String email, String numTel, String cogn, String nome,
					Date dataN, String indir, Privilegio priv) throws RemoteException, UtenteEsistenteException;
	//elimina utente OK 
	public void delUtente(String id) throws RemoteException, InesistenteException;
	//modifica utente OK 
	public void modificaUtente(Utente ut) throws RemoteException, InesistenteException;	
	//ottieni lista clienti OK 
	public ArrayList<Utente> listaClienti(Privilegio priv) throws RemoteException, ListaException;
	//cerca utente tramite id OK
	public Utente cercaUtenteId(String id) throws RemoteException, CercaException;
	//cerca utente tramite mail OK
	public ArrayList<Utente> cercaUtenteMail(String mail) throws RemoteException, CercaException;
	//cerca utente tramite numtel OK
	public ArrayList<Utente> cercaUtenteTel(String numetel) throws RemoteException, ListaException;
	
	
	//aggiungi contratto OK
	public void addContratto(String idutente, Date data, int idCompPos, int idCompNeg) throws RemoteException, InesistenteException, CercaException, ComponenteNonTuoException;
	//elimina contratto OK
	public void delContratto(int id) throws RemoteException, InesistenteException;
	//cerca contratto OK
	public Contratto cercaContratto(int id) throws RemoteException, CercaException;
	//cerca elenco Contratti per id OK
	public ArrayList<Contratto> cercaContrattiCliente(String id) throws RemoteException, ListaException;
	
	//aggiungi fattura	OK
	public void addFattura(Fattura ftt) throws RemoteException, InesistenteException;
	//cerca elenco fatture per contratto 	OK
	public ArrayList<Fattura> cercaFttCliente(int id) throws RemoteException, ListaException, InesistenteException;
	//cerca fattura singola OK
	public Fattura cercaFtt(int id) throws RemoteException, CercaException;
	//cerca elenco ftt non pagate OK
	public ArrayList<Fattura> cercaFtt(StatoFattura sf) throws RemoteException, CercaException;
	
	
}
