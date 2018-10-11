package prog.shared;

import java.io.Serializable;
import java.sql.Date;

public class Utente implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 115163937717503683L;
	private String IDUtente;		
	private String password;
	private String email;
	private String numTelefono;
	private String cognome;
	private String nome;
	private Date dataDiNascita;		
	private String indirizzo;
	private Privilegio privilegio;
	private int loggato;
	
	//Costruttori
	
	public Utente(){
		super();
	}
	
	public Utente(String id, String pw, Privilegio priv){
		this.IDUtente = id;		
		this.password =pw;
		this.email="";
		this.numTelefono=null;
		this.cognome="";
		this.nome="";
		this.dataDiNascita=null;		
		this.indirizzo="";
		this.privilegio=priv;
		this.loggato=0;
	}
	
	public Utente(String id, String pw, String email, String numTel, String cogn, String nome,
					Date dataN, String indir, Privilegio priv){
		this.IDUtente = id;		
		this.password =pw;
		this.email=email;
		this.numTelefono=numTel;
		this.cognome=cogn;
		this.nome=nome;
		this.dataDiNascita=dataN;		
		this.indirizzo=indir;
		this.privilegio=priv;
		this.loggato=0;
	}
	
	//Getters n Setters
	
	public String getIDUtente() {
		return IDUtente;
	}
	public void setIDUtente(String iDUtente) {
		IDUtente = iDUtente;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNumTelefono() {
		return numTelefono;
	}
	public void setNumTelefono(String numTelefono) {
		this.numTelefono = numTelefono;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getDataDiNascita() {
		return dataDiNascita;
	}
	public void setDataDiNascita(Date dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public Privilegio getPrivilegio() {
		return privilegio;
	}
	public void setPrivilegio(Privilegio privilegio) {
		this.privilegio = privilegio;
	}
	public int getLoggato() {
		return loggato;
	}
	public void setLoggato(int loggato) {
		this.loggato = loggato;
	} 
	
}
