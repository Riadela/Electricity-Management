package prog.test;

import static org.junit.Assert.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import prog.server.ClienteImpl;
import prog.server.DBManager;
import prog.server.ImpiegatoImpl;
import prog.server.ManutentoreImpl;
import prog.server.Server;
import prog.shared.Fattura;
import prog.shared.Posizione;
import prog.shared.Privilegio;
import prog.shared.StatoFattura;
import prog.shared.StatoGuasto;
import prog.shared.Tipo;
import prog.shared.exception.CercaException;
import prog.shared.exception.ComponenteNonTuoException;
import prog.shared.exception.GiaConnessoException;
import prog.shared.exception.GiaPagataException;
import prog.shared.exception.InesistenteException;
import prog.shared.exception.LoginErratoException;
import prog.shared.exception.PrivilegiInsufficientiException;
import prog.shared.exception.UtenteEsistenteException;

public class DBTest {


	static Server server;
	static DBManager dbmanager;
	static ImpiegatoImpl impiegato;
	static ClienteImpl cliente;
	static ManutentoreImpl manutentore;


	@BeforeClass
	public static void setup() throws NotBoundException, SQLException, LoginErratoException, GiaConnessoException, UtenteEsistenteException, RemoteException {
		String year = "2017";
		String month = "1";
		String day = "25";
		String data = new String(year+"-"+month+"-"+day);
		Date datan = java.sql.Date.valueOf(data);
		dbmanager = DBManager.getInstance();
		dbmanager.connect();
		server = Server.getInstance();
		impiegato = ImpiegatoImpl.getInstance();
		cliente = ClienteImpl.getInstance();
		manutentore = ManutentoreImpl.getInstance();
		dbmanager.aggiungiUtente("uno", "unopw", "unomail", "0111", "unocognome", "unonome", datan, "unoindirizzo", Privilegio.IMP);
		dbmanager.login("uno", "unopw");
		impiegato.addUtente("due", "duepw", "duemail", "0222", "duecognome", "duenome", datan, "dueindirizzo", Privilegio.MAN);
		impiegato.addUtente("tre", "trepw", "tremail", "0333", "trecognome", "trenome", datan, "treindirizzo", Privilegio.CL);
		dbmanager.login("due", "duepw");
		dbmanager.login("tre", "trepw");

	}

	@AfterClass
	public static void confirm() throws RemoteException, SQLException, InesistenteException {

		impiegato.delUtente("tre");
		impiegato.delUtente("due");;
		dbmanager.logout("uno");
		dbmanager.EliminaUtente("uno");
		System.out.println("Test completati!");
	}

	@Before
	public void preSet() throws RemoteException, SQLException, InesistenteException, CercaException, ComponenteNonTuoException {
		String year = "2017";
		String month = "1";
		String day = "25";
		String data = new String(year+"-"+month+"-"+day);
		Date datan = java.sql.Date.valueOf(data);

		Posizione p = new Posizione("lat", "lon");
		manutentore.addComponente(Tipo.CABTRASFHM, p);
		int idComp = dbmanager.lastComponenteID();
		manutentore.addGuasto("primo guasto test", idComp, "due", datan);
		manutentore.addComponente(Tipo.CONTATORE, p);
		manutentore.addComponente(Tipo.PANNELLOSOLARE, p);

		impiegato.addContratto("tre", datan, idComp+1, idComp+2);
		int idContr = dbmanager.lastContrattoID();
		Fattura fttTest1 = new Fattura(0, datan, 100, "prima ftt test", StatoFattura.EMESSA, idContr);
		Fattura fttTest2 = new Fattura(0, datan, 200, "seconda ftt test", StatoFattura.PAGATA, idContr);
		impiegato.addFattura(fttTest1);
		impiegato.addFattura(fttTest2);

	}

	@After
	public void deleteSet() throws SQLException, InesistenteException, RemoteException {

		int idFtt = dbmanager.lastFttID();
		dbmanager.eliminaFattura(idFtt);
		dbmanager.eliminaFattura(idFtt-1);

		int idContr = dbmanager.lastContrattoID();
		dbmanager.eliminaContratto(idContr);

		int idComp = dbmanager.lastComponenteID();
		int idGuasto = dbmanager.lastGuastoID();
		dbmanager.eliminaComponente(idComp);
		dbmanager.eliminaComponente(idComp-1);
		dbmanager.eliminaGuasto(idGuasto);
		dbmanager.eliminaComponente(idComp-2);


	}

	@Test
	public void testListaFtt() throws RemoteException, CercaException, SQLException{

		int idFtt = dbmanager.lastFttID();
		ArrayList<Fattura> listaFttEmesse = impiegato.cercaFtt(StatoFattura.EMESSA);

		assertEquals(listaFttEmesse.get(listaFttEmesse.size()-1).getIDFattura(), idFtt-1);
		assertEquals(listaFttEmesse.get(listaFttEmesse.size()-1).getStatoFattura().toString(), StatoFattura.EMESSA.toString());


		ArrayList<Fattura> listaFttPagate = impiegato.cercaFtt(StatoFattura.PAGATA);
		assertEquals(listaFttPagate.get(listaFttPagate.size()-1).getIDFattura(), idFtt);
		assertEquals(listaFttPagate.get(listaFttPagate.size()-1).getStatoFattura().toString(), StatoFattura.PAGATA.toString());

	}

	@Test
	public void testPagaFtt() throws SQLException, CercaException, RemoteException, GiaPagataException{

		int idFtt = dbmanager.lastFttID();
		StatoFattura statoInizialeFtt = dbmanager.cercaFattura(idFtt-1).getStatoFattura();
		assertEquals(statoInizialeFtt.toString(), StatoFattura.EMESSA.toString());

		cliente.pagaFtt(idFtt-1);

		StatoFattura statoFinaleFtt = dbmanager.cercaFattura(idFtt-1).getStatoFattura();
		assertEquals(statoFinaleFtt.toString(), StatoFattura.PAGATA.toString());

	}

	@Test
	public void testPresaInCarico() throws RemoteException, SQLException, InesistenteException, PrivilegiInsufficientiException{
		String year = "2017";
		String month = "1";
		String day = "25";
		String data = new String(year+"-"+month+"-"+day);
		Date datan = java.sql.Date.valueOf(data);

		int idGuasto=dbmanager.lastGuastoID();
		StatoGuasto statoInizialePiC = dbmanager.getStato(idGuasto);
		assertEquals(statoInizialePiC.toString(), StatoGuasto.SEGNALATO.toString());
		manutentore.workOnGuasto(idGuasto, "due", "guasto preso in carico test", datan);

		StatoGuasto statoFinalePic = dbmanager.getStato(idGuasto);
		assertEquals(statoFinalePic.toString(), StatoGuasto.PRESOINCARICO.toString());

		manutentore.ripGuasto(idGuasto, "guasto risolto test", datan);
		StatoGuasto statoFinaleRisolto = dbmanager.getStato(idGuasto);
		assertEquals(statoFinaleRisolto.toString(), StatoGuasto.RISOLTO.toString());
	}





}