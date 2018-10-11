package prog.server;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

import prog.shared.Componente;
import prog.shared.Contratto;
import prog.shared.DBManInterface;
import prog.shared.Fattura;
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

public class DBManager implements DBManInterface {

	private Connection connection=null;
	private static DBManager instance = null;
	private static final String username="root";
	private static final String password="2208";
	PreparedStatement statement;
	ResultSet rs;


	private DBManager() throws RemoteException{

	}

	public static DBManager getInstance() throws RemoteException{
		if(instance == null) {
			instance = new DBManager();
		}
		return instance;
	}



	/// da qui in giu 

	public void connect(){
		this.connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_progetto?autoreconnect=true&useSSL=false",
					username, password);
			System.out.println("DB conneso!");
		}  catch (SQLException e) {
			e.printStackTrace();
		}
	}


	// ************* TABELLA UTENTE *****************

	// Login
	public Privilegio login(String IDUtente, String password) throws SQLException, LoginErratoException, GiaConnessoException{

		statement = null;
		rs = null;
		Privilegio priv=null;


		String query = "SELECT IDUtente, Password, Privilegio, Loggato FROM Utente WHERE IDUtente = ? AND Password = ?";
		statement=(PreparedStatement) connection.prepareStatement(query);
		statement.setString(1, IDUtente);
		statement.setString(2, password);

		rs = statement.executeQuery();


		if(rs.next()){

			if(!rs.getBoolean(4)){

				priv=Privilegio.valueOf(rs.getString(3));
				// Setto loggato a true del relativo utente

				statement = null; 

				query = "UPDATE Utente SET Loggato =  ? WHERE IDUtente = ?";
				statement=(PreparedStatement) connection.prepareStatement(query);
				statement.setBoolean(1, true);
				statement.setString(2, IDUtente);
				statement.executeUpdate();

				rs.close();
				statement.close();
				return priv;

			}else{
				rs.close();
				statement.close();
				throw new GiaConnessoException();
			}


		}
		else{
			rs.close();
			statement.close();
			throw new LoginErratoException();

		}

	}

	// Logout settando Loggato a false
	public void logout(String idUtente) throws SQLException, InesistenteException{

		statement = null; 							
		rs = null;

		String query = "SELECT IDUtente FROM Utente WHERE IDUtente = ?";
		statement=(PreparedStatement) connection.prepareStatement(query);
		statement.setString(1, idUtente);

		rs = statement.executeQuery();

		if(rs.next()){

			statement = null;

			query = "UPDATE Utente SET Loggato =  ? WHERE IDUtente = ?";
			statement=(PreparedStatement) connection.prepareStatement(query);
			statement.setBoolean(1, false);
			statement.setString(2, idUtente);
			statement.executeUpdate();

			rs.close();
			statement.close();}
		else{
			rs.close();
			statement.close();
			throw new InesistenteException("Utente ");
		}
	}	

	// Aggiungi un utente
	public void aggiungiUtente(String idUt, String pw, String email, String numTel, String cogn, String nome,
			Date dataN, String indir, Privilegio priv) throws SQLException, UtenteEsistenteException{

		statement = null;
		rs = null;

		String query = "SELECT IDUtente FROM Utente WHERE IDUtente = ?";
		statement=(PreparedStatement) connection.prepareStatement(query);
		statement.setString(1, idUt);

		rs = statement.executeQuery();

		if(!rs.next()){

			statement = null;

			query = "INSERT INTO Utente (IDUtente, Password, Email, NumeroTelefono, Cognome, Nome, DataNascita, Indirizzo, "
					+ "Privilegio, Loggato) VALUES (?,?,?,?,?,?,?,?,?,?)";
			statement=(PreparedStatement) connection.prepareStatement(query);

			statement.setString(1, idUt);
			statement.setString(2, pw);
			statement.setString(3, email);
			statement.setString(4, numTel);
			statement.setString(5, cogn);
			statement.setString(6, nome);
			statement.setDate(7, dataN);
			statement.setString(8, indir);
			statement.setString(9, priv.toString());
			statement.setBoolean(10, false);

			statement.executeUpdate();

			statement.close();
			rs.close();
		}else{
			statement.close();
			rs.close();
			throw new UtenteEsistenteException();
		}

	}

	// Elimina un determinato utente
	public void EliminaUtente(String idUtente) throws SQLException, InesistenteException{

		statement = null; 
		rs = null;

		String query = "SELECT IDUtente FROM Utente WHERE IDUtente = ?";
		statement=(PreparedStatement) connection.prepareStatement(query);
		statement.setString(1, idUtente);

		rs = statement.executeQuery();

		if(rs.next()){

			statement = null;

			query = "DELETE FROM Utente WHERE IDUtente = ?";
			statement=(PreparedStatement) connection.prepareStatement(query);
			statement.setString(1, idUtente);
			statement.executeUpdate();

			statement.close();
			rs.close();
		}else{
			statement.close();
			rs.close();
			throw new InesistenteException("Utente ");
		}

	}

	// Ottiene la lista di tutti i clienti
	public ArrayList<Utente> listaClienti() throws SQLException, ListaException{

		statement = null;
		rs = null;

		ArrayList<Utente> utList = new ArrayList<Utente>();

		String query = "SELECT * FROM Utente WHERE Privilegio = ?";
		statement = (PreparedStatement) connection.prepareStatement(query);
		statement.setString(1, Privilegio.CL.toString());

		rs = statement.executeQuery();

		// Aggiunge gli oggetti utente all'arraylist

		while (rs.next()) {

			utList.add(new Utente(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getString(6), rs.getDate(7), rs.getString(8), Privilegio.valueOf(rs.getString(9))));
		}

		rs.close();
		statement.close();

		if(utList.size() > 0)
			return utList;
		else
			throw new ListaException("Clienti ");

	}

	// Cerca un determinato utente tramite l'IDUtente
	public Utente cercaUtente(String id) throws SQLException, CercaException{

		Utente utente= new Utente();

		statement = null;
		rs = null;


		String query = "SELECT * FROM Utente WHERE IDUtente = ?";
		statement=(PreparedStatement) connection.prepareStatement(query);
		statement.setString(1, id);

		rs = statement.executeQuery();

		if(rs.next()){

			utente = new Utente(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getString(6), rs.getDate(7), rs.getString(8), Privilegio.valueOf(rs.getString(9)));

			rs.close();
			statement.close();
			return utente;

		}else {

			rs.close();
			statement.close();
			throw new CercaException("Utente ");

		}


	}

	// Modifica tutti i dati dell'utente e li aggiorna tramite un IDUtente
	public void modificaUtente(Utente ut) throws SQLException, InesistenteException{

		statement = null; 

		rs = null;

		String query = "SELECT IDUtente FROM Utente WHERE IDUtente = ?";
		statement=(PreparedStatement) connection.prepareStatement(query);
		statement.setString(1, ut.getIDUtente());

		rs = statement.executeQuery();

		if(rs.next()){

			statement = null;

			query = "UPDATE Utente SET Password =  ?, Email = ?, NumeroTelefono = ?, Cognome = ?, Nome = ?, DataNascita = ?,"
					+ " Indirizzo = ? WHERE IDUtente = ?";
			statement=(PreparedStatement) connection.prepareStatement(query);

			statement.setString(1, ut.getPassword());
			statement.setString(2, ut.getEmail());
			statement.setString(3, ut.getNumTelefono());
			statement.setString(4, ut.getCognome());
			statement.setString(5, ut.getNome());
			statement.setDate(6, ut.getDataDiNascita());
			statement.setString(7, ut.getIndirizzo());
			statement.setString(8, ut.getIDUtente());

			statement.executeUpdate();

			statement.close();
			rs.close();

		}else{
			rs.close();
			statement.close();
			throw new InesistenteException("Utente ");
		}



	}

	public ArrayList<Utente> cercaUtenteEmail(String email) throws SQLException, CercaException{

		statement = null;
		rs = null;

		ArrayList<Utente> utList = new ArrayList<Utente>();


		String query = "SELECT * FROM Utente WHERE Email = ?";
		statement = (PreparedStatement) connection.prepareStatement(query);
		statement.setString(1, email);

		rs = statement.executeQuery();

		// Aggiunge gli oggetti utente all'arraylist

		while (rs.next()) {

			utList.add(new Utente(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getString(6), rs.getDate(7), rs.getString(8), Privilegio.valueOf(rs.getString(9))));
		}

		rs.close();
		statement.close();

		if(utList.size()>0)
			return utList;
		else
			throw new CercaException("Utente ");



	}


	// ****** con numero cell

	public ArrayList<Utente> cercaUtenteNumeroTelefono(String numeroTelefono) throws SQLException, ListaException{

		statement = null;
		rs = null;

		ArrayList<Utente> utList = new ArrayList<Utente>();


		String query = "SELECT * FROM Utente WHERE NumeroTelefono = ?";
		statement = (PreparedStatement) connection.prepareStatement(query);
		statement.setString(1, numeroTelefono);

		rs = statement.executeQuery();

		// Aggiunge gli oggetti utente all'arraylist

		while (rs.next()) {

			utList.add(new Utente(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getString(6), rs.getDate(7), rs.getString(8), Privilegio.valueOf(rs.getString(9))));
		}

		rs.close();
		statement.close();

		if(utList.size()>0)
			return utList;
		else
			throw new ListaException("Utenti ");
	}

	// **************** TABELLA GUASTO *********************


	// Aggiungi guasto al db
	public void segnalaGuasto(String dett, int idComp, String idCliente, Date data) throws SQLException, InesistenteException{
		statement = null;

		rs = null;

		String query = "SELECT IDUtente FROM Contratto WHERE IDUtente = ? AND (IDCompPos = ? or IDCompNeg = ?)";
		statement=(PreparedStatement) connection.prepareStatement(query);
		statement.setString(1, idCliente);
		statement.setInt(2, idComp);
		statement.setInt(3, idComp);


		rs = statement.executeQuery();

		// Controllo se esiste il componente che appartiene al cliente
		if(rs.next()){

			statement = null;

			query = "INSERT INTO Guasto (IDComponente, StatoGuasto, IDCliente, DataSegnalazione, Dettagli) VALUES (?,?,?,?, ?)";
			statement=(PreparedStatement) connection.prepareStatement(query);

			statement.setInt(1, idComp);
			statement.setString(2, StatoGuasto.SEGNALATO.toString());
			statement.setString(3, idCliente);
			statement.setDate(4, data);
			statement.setString(5, dett);

			statement.executeUpdate();

			statement.close();
			rs.close();
		}else{
			statement.close();
			rs.close();
			throw new InesistenteException("Componente/Utente ");
		}


	}

	public void aggiungiGuasto(String dett, int idComp, String idCliente, Date data) throws SQLException, InesistenteException, CercaException{
		statement = null;

		rs = null;

		cercaUtente(idCliente);
		cercaComponente(idComp);


		String query = "INSERT INTO Guasto (IDComponente, StatoGuasto, IDCliente, DataSegnalazione, Dettagli) VALUES (?,?,?,?, ?)";
		statement=(PreparedStatement) connection.prepareStatement(query);

		statement.setInt(1, idComp);
		statement.setString(2, StatoGuasto.SEGNALATO.toString());
		statement.setString(3, idCliente);
		statement.setDate(4, data);
		statement.setString(5, dett);

		statement.executeUpdate();

		statement.close();
		rs.close();


	}

	// Prende in carico un guasto un determinato manutentore cambiando anche lo StatoGuasto
	public void prendiInCaricoGuasto(int idG, String idMan, String dett, Date data) throws SQLException, InesistenteException, PrivilegiInsufficientiException{

		statement = null; 
		rs = null;

		String query = "SELECT IDGuasto FROM Guasto WHERE IDGuasto = ? AND StatoGuasto = ?";
		statement=(PreparedStatement) connection.prepareStatement(query);
		statement.setInt(1, idG);
		statement.setString(2, StatoGuasto.SEGNALATO.toString());

		rs = statement.executeQuery();

		// Verifica esistenza del guasto
		if(rs.next()){

			statement = null;
			rs = null;

			query = "SELECT IDUtente FROM Utente WHERE IDUtente = ? AND Privilegio = ?";
			statement=(PreparedStatement) connection.prepareStatement(query);
			statement.setString(1, idMan);
			statement.setString(2, Privilegio.MAN.toString());

			rs = statement.executeQuery();


			// Se l'utente ha abbastanza privilegi per eseguire il lavoro 
			if(rs.next()){

				statement = null;

				query = "UPDATE Guasto SET IDManutentore =  ?, Dettagli = ?, StatoGuasto = ?, DataIncarico = ? WHERE IDGuasto = ?";
				statement=(PreparedStatement) connection.prepareStatement(query);

				statement.setString(1, idMan);
				statement.setString(2, dett);
				statement.setString(3, StatoGuasto.PRESOINCARICO.toString());
				statement.setDate(4, data);
				statement.setInt(5, idG);

				statement.executeUpdate();

				rs.close();
				statement.close();
			}else{
				statement.close();
				rs.close();
				throw new PrivilegiInsufficientiException();
			}
		}else{

			statement.close();
			rs.close();
			throw new InesistenteException("Guasto in segnalazione ");

		}



	}

	// Manutentore ripara un determinato guasto e viene cambiato lo StatoGuasto a Risolto
	public void riparaGuasto(int idG, String dett, Date data) throws SQLException, InesistenteException{

		statement = null; 
		rs = null;

		String query = "SELECT IDGuasto FROM Guasto WHERE IDGuasto = ? AND StatoGuasto = ?";
		statement=(PreparedStatement) connection.prepareStatement(query);
		statement.setInt(1, idG);
		statement.setString(2, StatoGuasto.PRESOINCARICO.toString());

		rs = statement.executeQuery();

		// Verifica esistenza del guasto in stato perso in carico
		if(rs.next()){

			statement = null;

			query = "UPDATE Guasto SET Dettagli = ?, StatoGuasto = ?, DataRiparazione = ? WHERE IDGuasto = ?";
			statement=(PreparedStatement) connection.prepareStatement(query);

			statement.setString(1, dett);
			statement.setString(2, StatoGuasto.RISOLTO.toString());
			statement.setDate(3, data);
			statement.setInt(4, idG);

			statement.executeUpdate();

			statement.close();
			rs.close();

		}else{

			statement.close();
			rs.close();
			throw new InesistenteException("Guasto in PresoInCarico ");
		}


	}

	// Ricerca di tutti i guasti in un determinato StatoGuasto
	public ArrayList<Guasto> getElencoGuasti(StatoGuasto sg) throws SQLException, ListaException{

		statement = null;
		rs = null;

		ArrayList<Guasto> gList = new ArrayList<Guasto>();


		String query = "SELECT * FROM Guasto WHERE StatoGuasto = ?";
		statement = (PreparedStatement) connection.prepareStatement(query);
		statement.setString(1, sg.toString());

		rs = statement.executeQuery();

		// Aggiunge gli oggetti fattura all'arraylist

		while (rs.next()) {
			gList.add(new Guasto(rs.getInt(1), sg, rs.getDate(3), rs.getDate(4),
					rs.getDate(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9)));
		}

		rs.close();
		statement.close();

		if(gList.size()>0)
			return gList;
		else
			throw new ListaException("Guasti ");

	}

	public ArrayList<Guasto> getElencoGuastiMan(String idman) throws SQLException, ListaException{

		statement = null;
		rs = null;

		ArrayList<Guasto> gList = new ArrayList<Guasto>();


		String query = "SELECT * FROM Guasto WHERE IDManutentore = ?";
		statement = (PreparedStatement) connection.prepareStatement(query);
		statement.setString(1, idman);

		rs = statement.executeQuery();

		while (rs.next()) {
			gList.add(new Guasto(rs.getInt(1), StatoGuasto.valueOf(rs.getString(2)), rs.getDate(3), rs.getDate(4),
					rs.getDate(5), rs.getString(6), rs.getString(7), idman, rs.getInt(9)));
		}

		rs.close();
		statement.close();

		if(gList.size()>0)
			return gList;
		else
			throw new ListaException("Guasti ");


	}

	// Visualizza i guasti ancora non risolti di un determinato IDCliente
	public ArrayList<Guasto> visualGuasti(String idUtente) throws SQLException, ListaException{
		statement = null;
		rs = null;

		ArrayList<Guasto> gList = new ArrayList<Guasto>();


		String query = "SELECT * FROM Guasto WHERE StatoGuasto <> ? AND IDCliente = ?";
		statement = (PreparedStatement) connection.prepareStatement(query);

		statement.setString(1, StatoGuasto.RISOLTO.toString());
		statement.setString(2, idUtente);

		rs = statement.executeQuery();

		// Aggiunge gli oggetti fattura all'arraylist

		while (rs.next()) {
			gList.add(new Guasto(rs.getInt(1), StatoGuasto.valueOf(rs.getString(2)), rs.getDate(3), rs.getDate(4), rs.getDate(5),
					rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9)));
		}

		rs.close();
		statement.close();

		if(gList.size()>0)
			return gList;
		else
			throw new ListaException("Guasti ");

	}


	// ************** TABELLA CONTRATTO ******************


	// Elimina il contratto
	public void eliminaContratto(int idContratto) throws SQLException, InesistenteException{

		statement = null; 
		rs = null;

		String query = "SELECT IDContratto FROM Contratto WHERE IDContratto = ?";
		statement=(PreparedStatement) connection.prepareStatement(query);
		statement.setInt(1, idContratto);

		rs = statement.executeQuery();

		// Verifica esistenza del contratto nel DB
		if(rs.next()){

			statement = null;

			query = "DELETE FROM Contratto WHERE IDContratto = ?";
			statement=(PreparedStatement) connection.prepareStatement(query);

			statement.setInt(1, idContratto);

			statement.executeUpdate();

			statement.close();
			rs.close();

		}else{

			statement.close();
			rs.close();
			throw new InesistenteException("Contratto ");

		}

	}


	// Cerca un contratto tramite id del contratto
	public Contratto cercaContratto(int id) throws SQLException, CercaException{

		Contratto contratto = new Contratto();
		statement = null;
		rs = null;

		String query = "SELECT * FROM Contratto WHERE IDContratto = ?";
		statement = (PreparedStatement) connection.prepareStatement(query);
		statement.setInt(1, id);

		rs = statement.executeQuery();

		if(rs.next()){



			// Crea il nuovo oggetto
			contratto = new Contratto(id, rs.getDate(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getFloat(6));
			rs.close();
			statement.close();
			return contratto;
		}else{
			rs.close();
			statement.close();

			throw new CercaException("Contratto ");
		}


	}

	public ArrayList<Contratto> cercaContrattiCliente(String id) throws SQLException, ListaException{

		statement = null;
		rs = null;

		ArrayList<Contratto> cttList = new ArrayList<Contratto>();


		String query = "SELECT * FROM Contratto WHERE IDUtente = ?";
		statement = (PreparedStatement) connection.prepareStatement(query);
		statement.setString(1, id);

		rs = statement.executeQuery();

		// Aggiunge gli oggetti componente all'arraylist

		while (rs.next()) {
			cttList.add(new Contratto(rs.getInt(1) , rs.getDate(2), id, rs.getInt(4), rs.getInt(5), rs.getFloat(6)));
		}

		rs.close();
		statement.close();

		if(cttList.size()>0)
			return cttList;
		else
			throw new ListaException("Contratti ");
	}

	public void aggiungiContratto(String idUtente, Date data, int idComp1, int idComp2) throws SQLException, InesistenteException, CercaException, ComponenteNonTuoException{

		statement = null; 
		rs = null;

		String query = "SELECT IDUtente FROM Utente WHERE IDUtente = ?";
		statement=(PreparedStatement) connection.prepareStatement(query);
		statement.setString(1, idUtente);

		rs = statement.executeQuery();

		// Verifica esistenza dell'utente
		if(rs.next()){

			// verifica esistenza componenti inseriti
			cercaComponente(idComp1);
			if(idComp2 != 0)
				cercaComponente(idComp2);

			statement = null; 
			rs = null;

			query = "SELECT IDUtente FROM Contratto WHERE IDCompPos = ?";
			statement=(PreparedStatement) connection.prepareStatement(query);
			statement.setInt(1, idComp1);

			rs = statement.executeQuery();

			// Verifica la non esistenza del componente positivo gia in un contratto
			if(!rs.next()){

				statement = null;
				rs = null;

				if(idComp2 == 0){

					query = "INSERT INTO Contratto (DataInizio, IDUtente, IDCompPos, IDCompNeg, Bilancio) VALUES (?,?, ?,?, ?)";
					statement=(PreparedStatement) connection.prepareStatement(query);

					statement.setDate(1, data);
					statement.setString(2, idUtente); 
					statement.setInt(3, idComp1); 
					statement.setInt(4, idComp2);
					statement.setFloat(5, 0);

					statement.executeUpdate();

					statement.close();
					//rs.close();
				}else{

					statement = null;
					rs = null;
					query = "SELECT IDUtente FROM Contratto WHERE IDCompNeg = ?";
					statement=(PreparedStatement) connection.prepareStatement(query);
					statement.setInt(1, idComp2);

					rs = statement.executeQuery();

					// Verifica la non esistenza del componente negativo gia in un contratto
					if(!rs.next()){
						statement = null;
						rs = null;
						query = "INSERT INTO Contratto (DataInizio, IDUtente, IDCompPos, IDCompNeg, Bilancio) VALUES (?,?, ?, ?, ?)";
						statement=(PreparedStatement) connection.prepareStatement(query);

						statement.setDate(1, data);
						statement.setString(2, idUtente); 
						statement.setInt(3, idComp1); 
						statement.setInt(4, idComp2); 
						statement.setFloat(5, 0);

						statement.executeUpdate();

						statement.close();
						//rs.close();
					}
					else{

						rs.close();
						statement.close();
						throw new ComponenteNonTuoException();

					}
				}
			}else{

				rs.close();
				statement.close();
				throw new ComponenteNonTuoException();
			}

		}else{

			statement.close();
			rs.close();
			throw new InesistenteException("Cliente ");

		}


	}


	// ************** TABELLA CONSUMO **************






	public void aggiungiConsumo(int idContratto) throws SQLException, InesistenteException, CercaException{

		statement = null; 

		String query;
		Contratto contr;

		// Verifica esistenza del contratto
		contr = cercaContratto(idContratto);

		Componente comp1 = cercaComponente(contr.getIDCompPos()); 
		Componente comp2 = new Componente();

		if(contr.getIDCompNeg() != 0) 
			comp2 = cercaComponente(contr.getIDCompNeg());	


		query = "UPDATE Contratto SET Bilancio =  ? WHERE IDContratto = ?";
		statement=(PreparedStatement) connection.prepareStatement(query);
		if(comp2.getIDComponente() == 0)
			statement.setFloat(1, comp1.getValoreEnerg());
		else
			statement.setFloat(1, comp1.getValoreEnerg() - comp2.getValoreEnerg());

		statement.setInt(2, idContratto);

		statement.executeUpdate();

		statement.close();


	}




	// **************** TABELLA COMPONENTNE ***************

	// Aggiungi componente alla relativa tabella, e la posizione nella tabella appropriata
	public void aggiungiComponente(Tipo t, Posizione p) throws SQLException{

		statement = null;


		String query = "INSERT INTO Componente (Tipo, ValoreEnergetico) VALUES (?,?)";
		statement=(PreparedStatement) connection.prepareStatement(query);

		statement.setString(1, t.toString());
		statement.setFloat(2, 0); // Valore energetico a 0 inizialmente

		statement.executeUpdate();

		// Cerca l'id dell'ultimo componente appena aggiunto
		statement = null;
		query = "SELECT MAX(IDComponente) FROM Componente";
		statement=(PreparedStatement) connection.prepareStatement(query);
		rs = statement.executeQuery();
		rs.next();

		int idComp = rs.getInt(1); // Salva l'id del componente appena aggiunto

		// Inserisce la posizione anche nella tabella posizione
		query = "INSERT INTO Posizione (Longitudine, Latitudine, IDComponente) VALUES (?,?,?)";
		statement=(PreparedStatement) connection.prepareStatement(query);

		statement.setString(1, p.getLongitudine());
		statement.setString(2, p.getLatitudine());
		statement.setInt(3, idComp);

		statement.executeUpdate();

		rs.close();
		statement.close();

	}		

	// Modifica Valore Energetico di un componente
	public void modificaComponente(int idcomp, float val) throws SQLException, InesistenteException{	

		statement = null; 
		rs = null;

		String query = "SELECT IDComponente FROM Componente WHERE IDComponente = ?";
		statement=(PreparedStatement) connection.prepareStatement(query);
		statement.setInt(1, idcomp);

		rs = statement.executeQuery();

		// Verifica esistenza del componente
		if(rs.next()){

			statement = null;

			query = "UPDATE Componente SET ValoreEnergetico =  ? WHERE IDComponente = ?;";
			statement=(PreparedStatement) connection.prepareStatement(query);

			statement.setFloat(1, val);
			statement.setInt(2, idcomp);

			statement.executeUpdate();

			rs.close();
			statement.close();
		}else{

			statement.close();
			rs.close();
			throw new InesistenteException("Componente ");

		}
	}

	public void eliminaComponente(int idcomp) throws SQLException, InesistenteException{

		statement = null; 
		rs = null;

		String query = "SELECT IDComponente FROM Componente WHERE IDComponente = ?";
		statement=(PreparedStatement) connection.prepareStatement(query);
		statement.setInt(1, idcomp);

		rs = statement.executeQuery();

		// Verifica esistenza del componente
		if(rs.next()){

			statement = null;

			query = "DELETE FROM Posizione WHERE IDComponente = ?";
			statement=(PreparedStatement) connection.prepareStatement(query);

			statement.setInt(1, idcomp);

			statement.executeUpdate();

			query = "DELETE FROM Componente WHERE IDComponente = ?";
			statement=(PreparedStatement) connection.prepareStatement(query);

			statement.setInt(1, idcomp);

			statement.executeUpdate();

			statement.close();
			rs.close();
		}else{
			statement.close();
			rs.close();
			throw new InesistenteException("Componente ");
		}

	}

	public Componente cercaComponente(int id) throws SQLException, CercaException{

		Componente componente = new Componente(0, null, 0, new Posizione("",""));
		statement = null;
		rs = null;


		String query = "SELECT * FROM Componente JOIN Posizione ON Componente.IDComponente=Posizione.IDComponente WHERE Componente.IDComponente = ?";
		statement = (PreparedStatement) connection.prepareStatement(query);
		statement.setInt(1, id);

		rs = statement.executeQuery();

		if(rs.next()){

			//CERCA COMPONENTE
			Posizione p = new Posizione(rs.getString(5), rs.getString(4));
			componente = new Componente(rs.getInt(1), Tipo.valueOf(rs.getString(2)), rs.getFloat(3), p);

			rs.close();
			statement.close();
			return componente;

		}else{
			rs.close();
			statement.close();
			throw new CercaException("Componente ");
		}

	}

	// Cerca tipo di componente a seconda del tipo
	public ArrayList<Componente> cercaElencoComp(Tipo t) throws SQLException, ListaException{

		statement = null;
		rs = null;

		ArrayList<Componente> cmpList = new ArrayList<Componente>();


		String query = "SELECT * FROM Componente JOIN Posizione ON Componente.IDComponente=Posizione.IDComponente WHERE Componente.Tipo = ?";
		statement = (PreparedStatement) connection.prepareStatement(query);
		statement.setString(1, t.toString());

		rs = statement.executeQuery();

		// Aggiunge gli oggetti componente all'arraylist

		while (rs.next()) {

			Posizione p = new Posizione(rs.getString(5), rs.getString(4));

			cmpList.add(new Componente(rs.getInt(1), Tipo.valueOf(rs.getString(2)), rs.getFloat(3), p));
		}

		rs.close();
		statement.close();

		if(cmpList.size()>0)
			return cmpList;
		else
			throw new ListaException("Componenti ");
	}

	public Componente cercaComponentePositivo(int idContratto) throws SQLException, CercaException{

		Componente componente = new Componente();
		statement = null;
		rs = null;


		String query = "SELECT Consumo.IDCompPos, Componente.Tipo, Componente.ValoreEnergetico FROM Componente JOIN Consumo ON Componente.IDComponente = Consumo.IDCompPos WHERE IDContratto = ?";
		statement = (PreparedStatement) connection.prepareStatement(query);
		statement.setInt(1, idContratto);

		rs = statement.executeQuery();

		if(rs.next()){

			// Crea il nuovo oggetto
			componente = new Componente(rs.getInt(1), Tipo.valueOf(rs.getString(2)), rs.getFloat(3));

			rs.close();
			statement.close();
			return componente;
		}else{

			rs.close();
			statement.close();

			throw new CercaException("Componente positivo ");
		}

	}

	public Componente cercaComponenteNegativo(int idContratto) throws SQLException, CercaException{

		Componente componente = new Componente();
		statement = null;
		rs = null;


		String query = "SELECT Consumo.IDCompNeg, Componente.Tipo, Componente.ValoreEnergetico FROM Componente JOIN Consumo ON Componente.IDComponente = Consumo.IDCompNeg WHERE IDContratto = ?;";
		statement = (PreparedStatement) connection.prepareStatement(query);
		statement.setInt(1, idContratto);

		rs = statement.executeQuery();

		if(rs.next()){

			// Crea il nuovo oggetto
			componente = new Componente(rs.getInt(1), Tipo.valueOf(rs.getString(2)), rs.getFloat(3));
			rs.close();
			statement.close();
			return componente;

		}else{
			rs.close();
			statement.close();
			throw new CercaException("Componente negativo ");
		}


	}




	// ***************** TABELLA FATTURA *********************

	// cambiato a string


	// Aggiungi fattura passando l'oggetto fattura contenente tutte le info riguardanti la fattura
	// e passando l'IDContratto della fattura a cui fa riferimento
	public void aggiungiFattura(Fattura ftt) throws SQLException, InesistenteException{

		statement = null; 
		rs = null;

		String query = "SELECT IDContratto FROM Contratto WHERE IDContratto = ?";
		statement=(PreparedStatement) connection.prepareStatement(query);
		statement.setInt(1, ftt.getIDContratto());

		rs = statement.executeQuery();

		// Verifica esistenza del contratto nel DB
		if(rs.next()){

			statement = null;

			query = "INSERT INTO Fattura (DataEmissione, Importo, Dettagli, StatoFattura, IDContratto) VALUES (?,?,?,?,?)";
			statement=(PreparedStatement) connection.prepareStatement(query);
			statement.setDate(1, ftt.getDataEmissione());
			statement.setFloat(2, ftt.getImporto());
			statement.setString(3, ftt.getDett());
			statement.setString(4, ftt.getStatoFattura().toString());
			statement.setInt(5, ftt.getIDContratto());
			statement.executeUpdate();

			statement.close();
			rs.close();
		}else{
			statement.close();
			rs.close();
			throw new InesistenteException("Contratto ");
		}

	}

	// Cerca fattura per id e ritorna un'oggetto di tipo Fattura contenente tutte le info della fattura
	public Fattura cercaFattura(int IDFattura) throws SQLException, CercaException{
		Fattura fattura = new Fattura();
		statement = null;
		rs = null;

		String query = "SELECT * FROM Fattura WHERE IDFattura = ?";
		statement=(PreparedStatement) connection.prepareStatement(query);
		statement.setInt(1, IDFattura);

		rs = statement.executeQuery();

		if(rs.next()){


			fattura = new Fattura(rs.getInt(1), rs.getDate(2), rs.getFloat(3),
					rs.getString(4), StatoFattura.valueOf(rs.getString(5)), rs.getInt(6));

			rs.close();
			statement.close();
			return fattura;
		}else{

			rs.close();
			statement.close();
			throw new CercaException("Fattura ");
		}


	}

	// Cambia lo stato della fattura da EMESSA a PAGATA
	public void pagaFattura(int IDFattura) throws SQLException, CercaException, GiaPagataException{

		statement = null; 
		String query;

		query = "SELECT StatoFattura FROM Fattura WHERE IDFattura = ?";
		statement = (PreparedStatement) connection.prepareStatement(query);
		statement.setInt(1, IDFattura);
		rs = statement.executeQuery();

		if(rs.next()){

			if(StatoFattura.valueOf(rs.getString(1)).equals(StatoFattura.EMESSA)){

				statement = null;

				query = "UPDATE Fattura SET StatoFattura =  ? WHERE IDFattura = ?";
				statement=(PreparedStatement) connection.prepareStatement(query);
				statement.setString(1, StatoFattura.PAGATA.toString());
				statement.setInt(2, IDFattura);

				statement.executeUpdate();

				rs.close();
				statement.close();

			}else{

				rs.close();
				statement.close();
				throw new GiaPagataException();
			}

		}else{
			rs.close();
			statement.close();
			throw new CercaException("Fattura ");
		}

	}

	// Cerca tutte le fatture pagate/non pagate in base al parametro passato
	public ArrayList<Fattura> cercaFatture(StatoFattura sf) throws SQLException, CercaException{

		statement = null;
		rs = null;

		ArrayList<Fattura> fttList = new ArrayList<Fattura>();

		String query = "SELECT * FROM Fattura WHERE StatoFattura = ?";
		statement = (PreparedStatement) connection.prepareStatement(query);
		statement.setString(1, sf.toString());

		rs = statement.executeQuery();

		// Aggiunge gli oggetti fattura all'arraylist

		while (rs.next()) {

			fttList.add(new Fattura(rs.getInt(1), rs.getDate(2), rs.getFloat(3), rs.getString(4), StatoFattura.valueOf(rs.getString(5)), rs.getInt(6)));
		}

		rs.close();
		statement.close();

		if(fttList.size()>0)
			return fttList;
		else
			throw new CercaException("Fatture ");


	}

	public ArrayList<Fattura> cercaFatture(StatoFattura sf, String IDUtente) throws SQLException, CercaException, InesistenteException{


		statement = null; 
		String query;

		query = "SELECT IDUtente FROM Utente WHERE IDUtente = ?";
		statement = (PreparedStatement) connection.prepareStatement(query);
		statement.setString(1, IDUtente);
		rs = statement.executeQuery();

		if(rs.next()){

			statement = null;
			rs = null;

			ArrayList<Fattura> fttList = new ArrayList<Fattura>();

			query = "SELECT * FROM Fattura LEFT JOIN Contratto ON Fattura.IDContratto = Contratto.IDContratto WHERE IDUtente = ? AND StatoFattura = ?";
			statement = (PreparedStatement) connection.prepareStatement(query);
			statement.setString(1, IDUtente);
			statement.setString(2,sf.toString());

			rs = statement.executeQuery();

			// Aggiunge gli oggetti fattura all'arraylist

			while (rs.next()) {

				fttList.add(new Fattura(rs.getInt(1), rs.getDate(2), rs.getFloat(3), rs.getString(4), StatoFattura.valueOf(rs.getString(5)), rs.getInt(6)));
			}

			rs.close();
			statement.close();

			if(fttList.size()>0)
				return fttList;
			else
				throw new CercaException("Fatture ");
		}else{
			rs.close();
			statement.close();
			throw new InesistenteException("Utente ");
		}


	}



	// Cerca fatture per IDContratto
	public ArrayList<Fattura> cercaFttCliente(int idContratto) throws SQLException, ListaException, InesistenteException{


		statement = null; 
		String query;

		query = "SELECT IDContratto FROM Contratto WHERE IDContratto = ?";
		statement = (PreparedStatement) connection.prepareStatement(query);
		statement.setInt(1, idContratto);
		rs = statement.executeQuery();

		if(rs.next()){

			statement = null;
			rs = null;

			ArrayList<Fattura> fttList = new ArrayList<Fattura>();


			query = "SELECT * FROM Fattura WHERE IDContratto = ?";
			statement = (PreparedStatement) connection.prepareStatement(query);
			statement.setInt(1, idContratto);

			rs = statement.executeQuery();

			// Aggiunge gli oggetti fattura all'arraylist

			while (rs.next()) {

				fttList.add(new Fattura(rs.getInt(1), rs.getDate(2), rs.getFloat(3), rs.getString(4), 
						StatoFattura.valueOf(rs.getString(5)), rs.getInt(6)));
			}

			rs.close();
			statement.close();

			if(fttList.size()>0)
				return fttList;
			else
				throw new ListaException("Fatture ");
		}else{
			rs.close();
			statement.close();
			throw new InesistenteException("Contratto ");
		}


	}

	public void eliminaFattura(int idFattura) throws SQLException, InesistenteException{

		statement = null; 
		rs = null;

		String query = "SELECT IDFattura FROM Fattura WHERE IDFattura = ?";
		statement=(PreparedStatement) connection.prepareStatement(query);
		statement.setInt(1, idFattura);

		rs = statement.executeQuery();

		// Verifica esistenza del fattura
		if(rs.next()){

			statement = null;

			query = "DELETE FROM Fattura WHERE IDFattura = ?";
			statement=(PreparedStatement) connection.prepareStatement(query);

			statement.setInt(1, idFattura);

			statement.executeUpdate();			

			statement.close();
			rs.close();
		}else{
			statement.close();
			rs.close();
			throw new InesistenteException("Fattura ");

		}
	}

	public void eliminaGuasto(int idGuasto) throws SQLException, InesistenteException{

		statement = null; 
		rs = null;

		String query = "SELECT IDGuasto FROM Guasto WHERE IDGuasto = ?";
		statement=(PreparedStatement) connection.prepareStatement(query);
		statement.setInt(1, idGuasto);

		rs = statement.executeQuery();

		// Verifica esistenza del fattura
		if(rs.next()){

			statement = null;

			query = "DELETE FROM Guasto WHERE IDGuasto = ?";
			statement=(PreparedStatement) connection.prepareStatement(query);

			statement.setInt(1, idGuasto);

			statement.executeUpdate();			

			statement.close();
			rs.close();
		}else{
			statement.close();
			rs.close();
			throw new InesistenteException("Guasto ");

		}
	}

	public int lastGuastoID() throws SQLException{
		statement = null; 
		rs = null;

		String query = "SELECT MAX(IDGuasto) FROM Guasto";
		statement=(PreparedStatement) connection.prepareStatement(query);

		rs = statement.executeQuery();
		if(rs.next()){
			return rs.getInt(1);
		} else 
			return -1;

	}

	public int lastFttID() throws SQLException{
		statement = null; 
		rs = null;

		String query = "SELECT MAX(IDFattura) FROM Fattura";
		statement=(PreparedStatement) connection.prepareStatement(query);

		rs = statement.executeQuery();
		if(rs.next()){
			return rs.getInt(1);
		} else 
			return -1;
	}

	public int lastContrattoID() throws SQLException{
		statement = null; 
		rs = null;

		String query = "SELECT MAX(IDContratto) FROM Contratto";
		statement=(PreparedStatement) connection.prepareStatement(query);
		rs = statement.executeQuery();
		if(rs.next()){
			return rs.getInt(1);
		} else 
			return -1;
	}

	public int lastComponenteID() throws SQLException{
		statement = null; 
		rs = null;

		String query = "SELECT MAX(IDComponente) FROM Componente";
		statement=(PreparedStatement) connection.prepareStatement(query);
		rs = statement.executeQuery();
		if(rs.next()){
			return rs.getInt(1);
		} else 
			return -1;
	}

	public StatoGuasto getStato(int idGuasto) throws RemoteException, SQLException{
		statement = null; 
		rs = null;
		idGuasto = DBManager.getInstance().lastGuastoID();

		String query = "SELECT StatoGuasto FROM Guasto WHERE IDGuasto = ?";
		statement=(PreparedStatement) connection.prepareStatement(query);
		statement.setInt(1, idGuasto);
		rs = statement.executeQuery();

		if(rs.next()){
			return StatoGuasto.valueOf(rs.getString(1));
		} else 
			return null;
	}

}

