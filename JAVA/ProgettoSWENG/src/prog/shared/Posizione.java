package prog.shared;

import java.io.Serializable;

public class Posizione implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6166836563666166068L;
	private String latitudine;			
	private String longitudine;
	
	//Costruttori
	
	public Posizione(String lat, String lon){
		this.setLatitudine(lat);
		this.setLongitudine(lon);
	}
	
	//Getters n Setters

	public String getLatitudine() {
		return latitudine;
	}

	public void setLatitudine(String latitudine) {
		this.latitudine = latitudine;
	}

	public String getLongitudine() {
		return longitudine;
	}

	public void setLongitudine(String longitudine) {
		this.longitudine = longitudine;
	}
}
