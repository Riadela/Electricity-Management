package prog.shared.exception;

public class InesistenteException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1830477897661533219L;

	public InesistenteException(String nome){
		super(nome + " inesistente!");
	}

}
