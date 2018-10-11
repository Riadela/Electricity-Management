package prog.server;

import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;

import prog.shared.Componente;
import prog.shared.Guasto;
import prog.shared.ManutentoreInt;
import prog.shared.Posizione;
import prog.shared.StatoGuasto;
import prog.shared.Tipo;
import prog.shared.exception.CercaException;
import prog.shared.exception.InesistenteException;
import prog.shared.exception.ListaException;
import prog.shared.exception.PrivilegiInsufficientiException;

public class ManutentoreImpl implements ManutentoreInt {



	protected ManutentoreImpl() throws RemoteException {
		super();
	}

	private GestoreGuasto ggua=GestoreGuasto.getInstance();
	private GestoreComponente gcomp=GestoreComponente.getInstance();
	private GestoreConsumo gcons = GestoreConsumo.getInstance();
	private static ManutentoreImpl instance = null;

	public static ManutentoreImpl getInstance() throws RemoteException{
		if(instance == null) {
			instance = new ManutentoreImpl();
		}
		return instance;
	}

	@Override
	public void addGuasto(String dett, int idComp, String idcliente, Date data) throws RemoteException, InesistenteException, CercaException {
		ggua.addGuasto(dett, idComp, idcliente, data);
	}

	@Override
	public void workOnGuasto(int idG, String idMan, String dett, Date data) throws RemoteException, InesistenteException, PrivilegiInsufficientiException {
		ggua.workOnGuasto(idG, idMan, dett, data);
	}

	@Override
	public void ripGuasto(int idG, String dett, Date data) throws RemoteException, InesistenteException {
		ggua.ripGuasto(idG, dett, data);
	}

	@Override
	public ArrayList<Guasto> getElencoGuasti(StatoGuasto sg) throws RemoteException, ListaException {
		return (ggua.getElencoGuasti(sg));
	}

	@Override
	public ArrayList<Guasto> getElencoGuastiMan(String idman) throws RemoteException, ListaException {
		return ggua.getElencoGuastiMan(idman);
	}

	@Override
	public void addComponente(Tipo t, Posizione p) throws RemoteException {
		gcomp.addComponente(t, p);
	}

	@Override
	public void modComponente(int idcomp, float val) throws RemoteException, InesistenteException {
		gcomp.modComponente(idcomp, val);
	}

	@Override
	public void delComponente(int id) throws RemoteException, InesistenteException {
		gcomp.delComponente(id);
	}

	@Override
	public Componente cercaComp(int id) throws RemoteException, CercaException {
		return gcomp.cercaComp(id);
	}

	@Override
	public ArrayList<Componente> cercaElencoComp(Tipo t) throws RemoteException, ListaException {
		return gcomp.cercaElencoComp(t);
	}
	
	@Override
	public void addConsumo(int idContr) throws RemoteException, CercaException, InesistenteException {
		gcons.addConsumo(idContr);
	}


}
