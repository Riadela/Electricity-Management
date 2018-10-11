package prog.shared.exception;

public class GiaConnessoException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3763339669567864347L;

	public GiaConnessoException() {
		
		super("Utente già connesso! Se il problema persiste, contattare l'assistenza.");
		
	}

}
