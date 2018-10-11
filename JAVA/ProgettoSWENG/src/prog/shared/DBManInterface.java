package prog.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

import prog.shared.exception.GiaConnessoException;
import prog.shared.exception.InesistenteException;
import prog.shared.exception.LoginErratoException;

public interface DBManInterface extends Remote {

	public Privilegio login(String id, String pw) throws RemoteException, SQLException, LoginErratoException, GiaConnessoException;
	
	public void logout(String idUtente) throws RemoteException, SQLException, InesistenteException;
}
