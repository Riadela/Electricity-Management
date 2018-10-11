package prog.shared;

import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;

import prog.shared.exception.CercaException;
import prog.shared.exception.GiaPagataException;
import prog.shared.exception.InesistenteException;
import prog.shared.exception.ListaException;

public interface ClienteInt {


	//pagamento ftt  OK
	public void pagaFtt(int id) throws RemoteException, CercaException, GiaPagataException;
	//cerca ftt  OK
	public ArrayList<Fattura> cercaFtt(StatoFattura sf, String idUtente) throws RemoteException, CercaException, InesistenteException;
	
	
	//mostra info contratti intestati al cliente tramite il suo id  OK
	public ArrayList<Contratto> infoContratto(String id) throws RemoteException, ListaException;
	//mostra info consumi di un detutente   OK
	public ArrayList<Contratto> cercaConsumo(String id) throws RemoteException, ListaException;
	
	
	//segnala guasto con id del contratto OK
	public void addGuasto(String dett, int idComp, String idcliente, Date data) throws RemoteException, InesistenteException, CercaException;
	//visualiz guasti non risolti del cliente OK
	public ArrayList<Guasto> visualGuasti(String id) throws RemoteException, ListaException;
}




/// NON ESTENDE REMOTE