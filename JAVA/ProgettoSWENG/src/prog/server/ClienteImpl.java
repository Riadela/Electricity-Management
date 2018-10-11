package prog.server;

import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;

import prog.shared.ClienteInt;
import prog.shared.Contratto;
import prog.shared.Fattura;
import prog.shared.Guasto;
import prog.shared.StatoFattura;
import prog.shared.exception.CercaException;
import prog.shared.exception.GiaPagataException;
import prog.shared.exception.InesistenteException;
import prog.shared.exception.ListaException;

public class ClienteImpl implements ClienteInt {

	

	protected ClienteImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	private GestoreFattura gftt = GestoreFattura.getInstance();
	private GestoreContratto gcontr= GestoreContratto.getInstance();
	private GestoreConsumo gcons= GestoreConsumo.getInstance();
	private GestoreGuasto ggua=GestoreGuasto.getInstance();
	private static ClienteImpl instance = null;
	
	public static ClienteImpl getInstance() throws RemoteException{
		if(instance == null) {
			instance = new ClienteImpl();
		}
		return instance;
	}
	
	
	@Override
	public void pagaFtt(int id) throws RemoteException, CercaException, GiaPagataException {
		gftt.pagaFtt(id);
	}

	@Override
	public ArrayList<Fattura> cercaFtt(StatoFattura sf, String idUtente) throws RemoteException, CercaException, InesistenteException {
		return (gftt.cercaFtt(sf, idUtente));
	}

	@Override
	public ArrayList<Contratto> infoContratto(String id) throws RemoteException, ListaException {
		return (gcontr.cercaContrattiCliente(id));
	}

	@Override
	public ArrayList<Contratto> cercaConsumo(String idut) throws RemoteException, ListaException {
		return (gcons.cercaConsumo(idut));
	}

	@Override
	public ArrayList<Guasto> visualGuasti(String id) throws RemoteException, ListaException {
		return (ggua.visualGuasti(id));
	}

	@Override
	public void addGuasto(String dett, int idComp, String idcliente, Date data) throws RemoteException, InesistenteException, CercaException{
		ggua.segnalaGuasto(dett, idComp, idcliente, data);
	}
}
