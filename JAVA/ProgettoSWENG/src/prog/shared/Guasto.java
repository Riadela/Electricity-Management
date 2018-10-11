package prog.shared;

import java.io.Serializable;
import java.sql.Date;

public class Guasto implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -490540759041926714L;
	private int IDGuasto;
	private StatoGuasto statoGuasto;
	private Date dataSegnalazione;
	private Date dataIncarico;
	private Date dataRiparazione;
	private String dettagli;
	private String IDCliente;
	private String IDManutentore;
	private int IDComponente;
	
	//Costruttori
	
	public Guasto(int id, Date dataS, String dett){
		this.IDGuasto=id;
		this.statoGuasto= StatoGuasto.SEGNALATO;
		this.dataSegnalazione=dataS;
		this.dataIncarico=null;
		this.dataRiparazione=null;
		this.dettagli=dett;
	}
	
	public Guasto(int id, StatoGuasto sg, Date dataS, Date dataI, Date dataR, String dett,
					String idCli, String idMan, int idComp){
		this.IDGuasto=id;
		this.statoGuasto= sg;
		this.dataSegnalazione=dataS;
		this.dataIncarico=dataI;
		this.dataRiparazione=dataR;
		this.dettagli=dett;
		this.IDCliente=idCli;
		this.IDManutentore=idMan;
		this.IDComponente=idComp;
	}
	
	
	
	//Getters n Setters
	
	public int getIDGuasto() {
		return IDGuasto;
	}
	public void setIDGuasto(int iDGuasto) {
		IDGuasto = iDGuasto;
	}
	public StatoGuasto getStatoGuasto() {
		return statoGuasto;
	}
	public void setStatoGuasto(StatoGuasto statoGuasto) {
		this.statoGuasto = statoGuasto;
	}
	public Date getDataSegnalazione() {
		return dataSegnalazione;
	}
	public void setDataSegnalazione(Date dataSegnalazione) {
		this.dataSegnalazione = dataSegnalazione;
	}
	public Date getDataIncarico() {
		return dataIncarico;
	}
	public void setDataIncarico(Date dataIncarico) {
		this.dataIncarico = dataIncarico;
	}
	public Date getDataRiparazione() {
		return dataRiparazione;
	}
	public void setDataRiparazione(Date dataRiparazione) {
		this.dataRiparazione = dataRiparazione;
	}
	public String getDettagli() {
		return dettagli;
	}
	public void setDettagli(String dettagli) {
		this.dettagli = dettagli;
	}

	public String getIDCliente() {
		return IDCliente;
	}

	public void setIDCliente(String iDCliente) {
		IDCliente = iDCliente;
	}

	public String getIDManutentore() {
		return IDManutentore;
	}

	public void setIDManutentore(String iDManutentore) {
		IDManutentore = iDManutentore;
	}

	public int getIDComponente() {
		return IDComponente;
	}

	public void setIDComponente(int iDComponente) {
		IDComponente = iDComponente;
	}
	
	
}
