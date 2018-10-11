package prog.server;

import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;

import prog.shared.Contratto;
import prog.shared.Fattura;
import prog.shared.ImpiegatoInt;
import prog.shared.Privilegio;
import prog.shared.StatoFattura;
import prog.shared.Utente;
import prog.shared.exception.CercaException;
import prog.shared.exception.ComponenteNonTuoException;
import prog.shared.exception.InesistenteException;
import prog.shared.exception.ListaException;
import prog.shared.exception.UtenteEsistenteException;

public class ImpiegatoImpl implements ImpiegatoInt {




	protected ImpiegatoImpl() throws RemoteException {
		super();
	}

	private GestoreUtente gut=GestoreUtente.getInstance();
	private GestoreContratto gcontr = GestoreContratto.getInstance();
	private GestoreFattura gftt=GestoreFattura.getInstance();
	private static ImpiegatoImpl instance = null;

	public static ImpiegatoImpl getInstance() throws RemoteException{
		if(instance == null) {
			instance = new ImpiegatoImpl();
		}
		return instance;
	}


	@Override
	public void addUtente(String idUt, String pw, String email, String numTel, String cogn, String nome, Date dataN, String indir,
			Privilegio priv) throws RemoteException, UtenteEsistenteException {
		gut.addUtente(idUt, pw, email, numTel, cogn, nome, dataN, indir, priv);
	}

	@Override
	public void delUtente(String id) throws RemoteException, InesistenteException {
		gut.delUtente(id);
	}

	@Override
	public void modificaUtente(Utente ut) throws RemoteException, InesistenteException{
		gut.modificaUtente(ut);
	}

	@Override
	public ArrayList<Utente> listaClienti(Privilegio priv) throws RemoteException, ListaException {
		return gut.listaClienti(priv);
	}

	@Override
	public Utente cercaUtenteId(String id) throws RemoteException, CercaException {
		return gut.cercaUtente(id);
	}

	@Override
	public ArrayList<Utente> cercaUtenteMail(String mail) throws RemoteException, CercaException {
		return gut.cercaUtenteMail(mail);
	}
	@Override
	public ArrayList<Utente> cercaUtenteTel(String numetel) throws RemoteException, ListaException {
		return (gut.cercaUtenteTel(numetel));
	}

	@Override
	public void addContratto(String idutente, Date data, int idcomppos, int idcompneg) throws RemoteException, InesistenteException, CercaException, ComponenteNonTuoException {
		gcontr.addContratto(idutente, data, idcomppos, idcompneg);
	}

	@Override
	public void delContratto(int id) throws RemoteException, InesistenteException {
		gcontr.delContratto(id);
	}

	@Override
	public Contratto cercaContratto(int id) throws RemoteException, CercaException {
		Contratto contr = gcontr.cercaContratto(id);
		return contr;
	}

	@Override
	public void addFattura(Fattura ftt) throws RemoteException, InesistenteException {
		gftt.addFattura(ftt);
	}

	@Override
	public ArrayList<Fattura> cercaFttCliente(int id) throws RemoteException, ListaException, InesistenteException {
		return gftt.cercaFttCliente(id);
	}

	@Override
	public Fattura cercaFtt(int id) throws RemoteException, CercaException {
		return gftt.cercaFtt(id);
	}

	@Override
	public ArrayList<Fattura> cercaFtt(StatoFattura sf) throws RemoteException, CercaException {
		return (gftt.cercaFtt(sf));
	}

	@Override
	public ArrayList<Contratto> cercaContrattiCliente(String id) throws RemoteException, ListaException {
		return (gcontr.cercaContrattiCliente(id));
	}

	





}
