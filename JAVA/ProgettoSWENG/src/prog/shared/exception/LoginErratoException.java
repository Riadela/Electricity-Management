package prog.shared.exception;

public class LoginErratoException extends Exception {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3442808459747757753L;
	
	public LoginErratoException(){
		super("Utente e/o password errati");
	}

}