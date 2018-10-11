package prog.shared.exception;

public class UtenteEsistenteException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7516522926611360768L;

	public UtenteEsistenteException(){
		super("Utente già esistente! Cambiare ID");
	}

}
