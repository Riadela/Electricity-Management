package prog.server;

import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import prog.shared.Privilegio;
import prog.shared.Utente;
import prog.shared.exception.CercaException;
import prog.shared.exception.InesistenteException;
import prog.shared.exception.ListaException;
import prog.shared.exception.UtenteEsistenteException;





public class GestoreUtente {

	private Utente utente = new Utente();
	private ArrayList<Utente> listaUtenti = new ArrayList<Utente>();
	
	private static GestoreUtente instance = null;
	private GestoreUtente() {
		
	}
	
	public static GestoreUtente getInstance() {
		if(instance == null) {
			instance = new GestoreUtente();
		}
		return instance;
	}
	
	//aggiungi utente
	public void addUtente(String idUt, String pw, String email, String numTel, String cogn, String nome,
						Date dataN, String indir, Privilegio priv) throws RemoteException, UtenteEsistenteException{
		try {
			DBManager.getInstance().aggiungiUtente(idUt, pw, email, numTel, cogn, nome, dataN, indir, priv);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//elimina utente
	public void delUtente(String id) throws RemoteException, InesistenteException{
		try {
			DBManager.getInstance().EliminaUtente(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//modifica utente
	public void modificaUtente(Utente ut) throws RemoteException, InesistenteException{
		try {
			DBManager.getInstance().modificaUtente(ut);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//ottieni lista clienti
	public ArrayList<Utente> listaClienti(Privilegio priv) throws RemoteException, ListaException{
		listaUtenti.clear(); 
		try {
			listaUtenti=DBManager.getInstance().listaClienti();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaUtenti;
	}
	
	//cerca utente 
	public Utente cercaUtente(String id) throws RemoteException, CercaException{
		GestoreUtente.getInstance().resetUtente();
		try {
			utente= DBManager.getInstance().cercaUtente(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return utente;
	}
	
	public ArrayList<Utente> cercaUtenteMail(String mail) throws RemoteException, CercaException {
		listaUtenti.clear();
		try {
			listaUtenti=DBManager.getInstance().cercaUtenteEmail(mail);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaUtenti;
	}
	public ArrayList<Utente> cercaUtenteTel(String numetel) throws RemoteException, ListaException {
		listaUtenti.clear();
		try {
			listaUtenti=DBManager.getInstance().cercaUtenteNumeroTelefono(numetel);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaUtenti;
	} 
	
	
	
	
	public void resetUtente(){
		utente.setCognome(null);
		utente.setDataDiNascita(null);
		utente.setEmail(null);
		utente.setIDUtente(null);
		utente.setIndirizzo(null);
		utente.setLoggato(0);
		utente.setNome(null);
		utente.setNumTelefono(null);
		utente.setPassword(null);
		utente.setPrivilegio(null);
	}
}














