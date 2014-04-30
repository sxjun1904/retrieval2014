package frame.retrieval.engine;

/**
 * 基础异常
 * @author 
 *
 */
public class RetrievalException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5738079315874422733L;

	public RetrievalException() {
		super();
	}

	public RetrievalException(String message) {
		super(message);
	}

	public RetrievalException(String message, Throwable cause) {
		super(message, cause);
	}

	public RetrievalException(Throwable cause) {
		super(cause);
	}

}
