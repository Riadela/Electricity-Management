package prog.shared;

import java.io.Serializable;
import java.sql.Date;


public class Contratto implements Serializable{


	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5566184093835945467L;
	private int IDContratto;
	private Date dataInizio;
	private String idutente;
	private int idCompPos;
	private int idCompNeg;
	private float bilancio;
	
	//Costruttori
	public Contratto(){
		super();
	}
	
	public Contratto(int id, Date dataI, String idUtente){
		this.IDContratto=id;
		this.dataInizio=dataI;
		this.idutente=idUtente;
	}
	
	public Contratto(int id, Date dataI, String idUtente, int idCompP, int idCompN, float bil){
		this.IDContratto=id;
		this.dataInizio=dataI;
		this.idutente=idUtente;
		this.idCompPos = idCompP;
		this.idCompNeg = idCompN;
		this.bilancio = bil;
		
	}
	
	//Getters n Setters
	
	public int getIDCompPos(){
		return idCompPos;
	}
	
	public int getIDCompNeg(){
		return idCompNeg;
	}
	
	public float getBilancio(){
		return bilancio;
	}
	public int getIDContratto() {
		return IDContratto;
	}
	public void setIDContratto(int iDContratto) {
		IDContratto = iDContratto;
	}
	public Date getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}
	
	public void setIDCompPos(int idcompp){
		this.idCompPos = idcompp;
		
	}
	
	public void setIDCompNeg(int idcompn){
		this.idCompNeg = idcompn;
		
	}
	
	public void setBilancio(float bil){
		this.bilancio = bil;
		
	}

	public String getIdutente() {
		return idutente;
	}

	public void setIdutente(String idutente) {
		this.idutente = idutente;
	}
	
	
	
}
