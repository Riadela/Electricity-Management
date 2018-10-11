package prog.shared.exception;

public class CercaException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1786489296499060166L;

	public CercaException(String nome) {
		
		super(nome + " non trovato!");
		
	}

}