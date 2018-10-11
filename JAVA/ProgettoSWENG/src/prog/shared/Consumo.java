package prog.shared;

import java.io.Serializable;

public class Consumo implements Serializable{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 4809630882164991076L;
	private int IDConsumo;
	private float bilancio;
	private int IDContratto;
	private int IDCompPos;
	private int IDCompNeg;
	
	//Costruttori
	
	public Consumo(int id, float bil){
		this.IDConsumo=id;
		this.bilancio=bil;
	}
	
	public Consumo(float bil, int idcont){
		this.IDConsumo = 0;
		this.bilancio = bil;
		this.IDContratto= idcont;
	}
	
	public Consumo(int idcons, float bil, int idcont, int idpos, int idneg ){
		this.IDConsumo = idcons;
		this.bilancio = bil;
		this.IDContratto= idcont;
		this.IDCompPos = idpos;
		this.IDCompNeg = idneg;
	}
	
	//Getters n Setters
	
	public Consumo() {
		super();
	}

	public int getIDConsumo() {
		return IDConsumo;
	}
	public void setIDConsumo(int iDConsumo) {
		IDConsumo = iDConsumo;
	}
	public float getBilancio() {
		return bilancio;
	}
	public void setBilancio(float bilancio) {
		this.bilancio = bilancio;
	}

	public int getIDContratto() {
		return IDContratto;
	}

	public void setIDContratto(int iDContratto) {
		IDContratto = iDContratto;
	}

	public int getIDCompPos() {
		return IDCompPos;
	}

	public void setIDCompPos(int iDCompPos) {
		IDCompPos = iDCompPos;
	}

	public int getIDCompNeg() {
		return IDCompNeg;
	}

	public void setIDCompNeg(int iDCompNeg) {
		IDCompNeg = iDCompNeg;
	}
	
	
}
