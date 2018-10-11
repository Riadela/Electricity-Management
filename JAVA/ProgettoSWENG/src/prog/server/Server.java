package prog.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import prog.shared.Componente;
import prog.shared.Contratto;
import prog.shared.Fattura;
import prog.shared.GeneralInt;
import prog.shared.Guasto;
import prog.shared.Posizione;
import prog.shared.Privilegio;
import prog.shared.StatoFattura;
import prog.shared.StatoGuasto;
import prog.shared.Tipo;
import prog.shared.Utente;
import prog.shared.exception.CercaException;
import prog.shared.exception.ComponenteNonTuoException;
import prog.shared.exception.GiaConnessoException;
import prog.shared.exception.GiaPagataException;
import prog.shared.exception.InesistenteException;
import prog.shared.exception.ListaException;
import prog.shared.exception.LoginErratoException;
import prog.shared.exception.PrivilegiInsufficientiException;
import prog.shared.exception.UtenteEsistenteException;

public class Server extends UnicastRemoteObject implements GeneralInt{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3364441192322514558L;

	protected Server() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	private Privilegio privlog;
	private static Server instance = null;

	public static Server getInstance() throws RemoteException{
		if(instance == null) {
			instance = new Server();
		}
		return instance;
	}
	
	public void connect() throws RemoteException {
		DBManager.getInstance().connect();
	}
	
	@Override
	public void pagaFtt(int id) throws RemoteException, CercaException, GiaPagataException {
			ClienteImpl.getInstance().pagaFtt(id);
	}

	@Override
	public ArrayList<Fattura> cercaFtt(StatoFattura sf) throws RemoteException, CercaException {
		
			return ImpiegatoImpl.getInstance().cercaFtt(sf);
	}
	
	@Override
	public ArrayList<Fattura> cercaFtt(StatoFattura sf, String idUtente) throws RemoteException, CercaException, InesistenteException {
		
			return ClienteImpl.getInstance().cercaFtt(sf, idUtente);
		
	}
	
	@Override
	public ArrayList<Contratto> infoContratto(String id) throws RemoteException, ListaException {
			return ClienteImpl.getInstance().infoContratto(id);
	}

	@Override
	public ArrayList<Contratto> cercaConsumo(String id) throws RemoteException, ListaException {
			return ClienteImpl.getInstance().cercaConsumo(id);
	}

	@Override
	public void addGuasto(String dett, int idComp, String idcliente, Date data) throws RemoteException, InesistenteException, CercaException {
		if (privlog==Privilegio.CL) 
			ClienteImpl.getInstance().addGuasto(dett, idComp, idcliente, data);
		else 
			ManutentoreImpl.getInstance().addGuasto(dett, idComp, idcliente, data);
		
	}

	@Override
	public ArrayList<Guasto> visualGuasti(String id) throws RemoteException, ListaException {
			return ClienteImpl.getInstance().visualGuasti(id);
	}

	@Override
	public Privilegio login(String id, String pw) throws RemoteException, LoginErratoException, SQLException, GiaConnessoException {
			
		return DBManager.getInstance().login(id, pw);
	}

	@Override
	public void logout(String idUtente) throws RemoteException, SQLException, InesistenteException {
		DBManager.getInstance().logout(idUtente);
	}

	@Override
	public void addUtente(String idUt, String pw, String email, String numTel, String cogn, String nome, Date dataN,
			String indir, Privilegio priv) throws RemoteException, UtenteEsistenteException {
		ImpiegatoImpl.getInstance().addUtente(idUt, pw, email, numTel, cogn, nome, dataN, indir, priv);
	}

	@Override
	public void delUtente(String id) throws RemoteException, InesistenteException {
		ImpiegatoImpl.getInstance().delUtente(id);
	}

	@Override
	public void modificaUtente(Utente ut) throws RemoteException, InesistenteException {
		ImpiegatoImpl.getInstance().modificaUtente(ut);
	}

	@Override
	public ArrayList<Utente> listaClienti(Privilegio priv) throws RemoteException, ListaException {
		return ImpiegatoImpl.getInstance().listaClienti(priv);
	}

	@Override
	public Utente cercaUtenteId(String id) throws RemoteException, CercaException {
		return ImpiegatoImpl.getInstance().cercaUtenteId(id);
	}

	@Override
	public ArrayList<Utente> cercaUtenteMail(String mail) throws RemoteException, CercaException {
		return ImpiegatoImpl.getInstance().cercaUtenteMail(mail);
	}

	@Override
	public ArrayList<Utente> cercaUtenteTel(String numetel) throws RemoteException, ListaException {
		return ImpiegatoImpl.getInstance().cercaUtenteTel(numetel);
	}

	@Override
	public void addContratto(String idutente, Date data, int idcomppos, int idcompneg) throws RemoteException, InesistenteException, CercaException, ComponenteNonTuoException {
		ImpiegatoImpl.getInstance().addContratto(idutente, data, idcomppos, idcompneg);
	}

	@Override
	public void delContratto(int id) throws RemoteException, InesistenteException {
		ImpiegatoImpl.getInstance().delContratto(id);
	}

	@Override
	public Contratto cercaContratto(int id) throws RemoteException, CercaException {
		return ImpiegatoImpl.getInstance().cercaContratto(id);
	}

	@Override
	public ArrayList<Contratto> cercaContrattiCliente(String id) throws RemoteException, ListaException {
		return ImpiegatoImpl.getInstance().cercaContrattiCliente(id);
	}

	@Override
	public void addFattura(Fattura ftt) throws RemoteException, InesistenteException {
		ImpiegatoImpl.getInstance().addFattura(ftt);
	}

	@Override
	public ArrayList<Fattura> cercaFttCliente(int id) throws RemoteException, ListaException, InesistenteException {
		return ImpiegatoImpl.getInstance().cercaFttCliente(id);
	}

	@Override
	public Fattura cercaFtt(int id) throws RemoteException, CercaException {
		return ImpiegatoImpl.getInstance().cercaFtt(id);
	}

	@Override
	public void addConsumo(int idContr) throws RemoteException, CercaException, InesistenteException {
		ManutentoreImpl.getInstance().addConsumo(idContr);
	}

	@Override
	public void workOnGuasto(int idG, String idMan, String dett, Date data) throws RemoteException, InesistenteException, PrivilegiInsufficientiException {
		ManutentoreImpl.getInstance().workOnGuasto(idG, idMan, dett, data);
	}

	@Override
	public void ripGuasto(int idG, String dett, Date data) throws RemoteException, InesistenteException {
		ManutentoreImpl.getInstance().ripGuasto(idG, dett, data);
	}

	@Override
	public ArrayList<Guasto> getElencoGuasti(StatoGuasto sg) throws RemoteException, ListaException {
		return ManutentoreImpl.getInstance().getElencoGuasti(sg);
	}

	@Override
	public ArrayList<Guasto> getElencoGuastiMan(String idman) throws RemoteException, ListaException {
		return ManutentoreImpl.getInstance().getElencoGuastiMan(idman);
	}

	@Override
	public void addComponente(Tipo t, Posizione p) throws RemoteException {
		ManutentoreImpl.getInstance().addComponente(t, p);
	}

	@Override
	public void modComponente(int idcomp, float val) throws RemoteException, InesistenteException {
		ManutentoreImpl.getInstance().modComponente(idcomp, val);
	}

	@Override
	public void delComponente(int id) throws RemoteException, InesistenteException {
		ManutentoreImpl.getInstance().delComponente(id);
	}

	@Override
	public Componente cercaComp(int id) throws RemoteException, CercaException {
		return ManutentoreImpl.getInstance().cercaComp(id);
	}

	@Override
	public ArrayList<Componente> cercaElencoComp(Tipo t) throws RemoteException, ListaException {
		return ManutentoreImpl.getInstance().cercaElencoComp(t);
	}

}
