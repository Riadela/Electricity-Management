package prog.shared.exception;

public class GiaPagataException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3290848602753089088L;

	public GiaPagataException(){
		super("Fattura già pagata!");
	}

}
