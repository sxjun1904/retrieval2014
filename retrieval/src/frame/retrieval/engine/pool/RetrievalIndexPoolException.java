package frame.retrieval.engine.pool;

/**
 * 对象池异常
 * @author 
 *
 */
public class RetrievalIndexPoolException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5738079315874422733L;

	public RetrievalIndexPoolException() {
		super();
	}

	public RetrievalIndexPoolException(String message) {
		super(message);
	}

	public RetrievalIndexPoolException(String message, Throwable cause) {
		super(message, cause);
	}

	public RetrievalIndexPoolException(Throwable cause) {
		super(cause);
	}

}
