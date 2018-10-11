package prog.shared.exception;

public class ComponenteNonTuoException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8282722916711210647L;

	public ComponenteNonTuoException() {
		super("Componente non appartenente al cliente");
	}

}
