package prog.server;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

import prog.shared.Fattura;
import prog.shared.StatoFattura;
import prog.shared.exception.CercaException;
import prog.shared.exception.GiaPagataException;
import prog.shared.exception.InesistenteException;
import prog.shared.exception.ListaException;

public class GestoreFattura {

	private Fattura fattura = new Fattura(0, null, 0, null, null, 0);
	private ArrayList<Fattura> elencoFtt= new ArrayList<Fattura>();
	
	
	private static GestoreFattura instance = null;
	private GestoreFattura() {
	
	}
	
	public static GestoreFattura getInstance() {
		if(instance == null) {
			instance = new GestoreFattura();
		}
		return instance;
	}
	
	
	//aggiungi fattura
	public void addFattura(Fattura ftt) throws RemoteException, InesistenteException{
		try {
			DBManager.getInstance().aggiungiFattura(ftt);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//cerca elenco fatture per idcontratto
	public ArrayList<Fattura> cercaFttCliente(int id) throws RemoteException, ListaException, InesistenteException{
		elencoFtt.clear();
		try {
			elencoFtt = DBManager.getInstance().cercaFttCliente(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return elencoFtt;
	}
	
	//cerca fattura singola
	public Fattura cercaFtt(int id) throws RemoteException, CercaException{
		GestoreFattura.getInstance().resetFattura();
		try {
			fattura = DBManager.getInstance().cercaFattura(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fattura;
	}
	
	//pagamento ftt
	public void pagaFtt(int id) throws RemoteException, CercaException, GiaPagataException{
		try {
			DBManager.getInstance().pagaFattura(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//cerca ftt per stato
	public ArrayList<Fattura> cercaFtt(StatoFattura sf) throws RemoteException, CercaException {
		elencoFtt.clear();
		try {
			elencoFtt=DBManager.getInstance().cercaFatture(sf);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return elencoFtt;
	}
	
	//cerca ftt per stato
		public ArrayList<Fattura> cercaFtt(StatoFattura sf, String idUtente) throws RemoteException, CercaException, InesistenteException {
			elencoFtt.clear();
			try {
				elencoFtt=DBManager.getInstance().cercaFatture(sf, idUtente);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return elencoFtt;
		}
	public void resetFattura(){
		fattura.setDataEmissione(null);
		fattura.setDett(null);
		fattura.setIDContratto(0);
		fattura.setIDFattura(0);
		fattura.setImporto(0);
		fattura.setStatoFattura(null);
	}
	
}
