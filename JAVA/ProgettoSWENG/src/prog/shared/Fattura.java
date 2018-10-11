package prog.shared;

import java.io.Serializable;
import java.sql.Date;

public class Fattura implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -614532250682846018L;
	private int IDFattura;
	private Date dataEmissione;
	private float importo;
	private String dettagli;
	private StatoFattura statoFattura;
	private int IDContratto;
	
	//Costruttori
	
	public Fattura(){
		super();
	}
	
	public Fattura(int id, Date dataE, float imp, String dett, StatoFattura sf, int idcont){
		this.IDFattura=id;
		this.dataEmissione=dataE;
		this.importo=imp;
		this.dettagli=dett;
		this.statoFattura=sf;
		this.IDContratto=idcont;
	}
	
	//Getters n Setters
	
	
	public int getIDFattura() {
		return IDFattura;
	}
	public void setIDFattura(int iDFattura) {
		IDFattura = iDFattura;
	}
	public Date getDataEmissione() {
		return dataEmissione;
	}
	public void setDataEmissione(Date dataEmissione) {
		this.dataEmissione = dataEmissione;
	}
	public float getImporto() {
		return importo;
	}
	public void setImporto(float importo) {
		this.importo = importo;
	}
	public String getDett() {
		return dettagli;
	}
	public void setDett(String dett) {
		this.dettagli = dett;
	}
	public StatoFattura getStatoFattura() {
		return statoFattura;
	}
	public void setStatoFattura(StatoFattura statoFattura) {
		this.statoFattura = statoFattura;
	}

	public int getIDContratto() {
		return IDContratto;
	}

	public void setIDContratto(int iDContratto) {
		IDContratto = iDContratto;
	}
}
