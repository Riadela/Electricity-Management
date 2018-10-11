package prog.shared;

import java.io.Serializable;

public class Componente implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8062132211705795499L;
	private int IDComponente;
	private Tipo tipo;
	private float valoreEnerg;		
	private Posizione posizione;
	
	//Costruttori
	
	public Componente() {
		super();
	}
	
	public Componente(int id){
		this.IDComponente=id;
		this.tipo=null;
		this.valoreEnerg=0;
	}
	
	public Componente(int id, Tipo t, float val) {
		this.IDComponente=id;
		this.tipo=t;
		this.valoreEnerg=val;
	}
	
	public Componente(int id, Tipo t, float val, Posizione pos){
		this.IDComponente=id;
		this.tipo=t;
		this.valoreEnerg=val;
		this.posizione=pos;
	}

	
	//Getters n Setters
		
	public int getIDComponente() {
		return IDComponente;
	}
	public void setIDComponente(int iDComponente) {
		IDComponente = iDComponente;
	}
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	public float getValoreEnerg() {
		return valoreEnerg;
	}
	public void setValoreEnerg(float valoreEnerg) {
		this.valoreEnerg = valoreEnerg;
	}
	public Posizione getPosizione() {
		return posizione;
	}
	public void setPosizione(Posizione posizione) {
		this.posizione = posizione;
	}
	
}
