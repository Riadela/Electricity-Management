package prog.shared.exception;

public class ListaException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 267112938813527633L;

	public ListaException(String nome) {
		super(nome + "non trovati!");
	}

}