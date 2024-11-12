package exception; 

public class SaldoInsuficienteException extends Exception {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Construtor que recebe uma mensagem de erro
    public SaldoInsuficienteException(String message) {
        super(message); // Chama o construtor da classe pai (Exception)
    }
}
