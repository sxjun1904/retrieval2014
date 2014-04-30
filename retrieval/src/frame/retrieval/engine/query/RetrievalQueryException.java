package frame.retrieval.engine.query;

import frame.retrieval.engine.RetrievalException;

/**
 * 
 * @author 
 *
 */
public class RetrievalQueryException extends RetrievalException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RetrievalQueryException() {
		super();
	}

	public RetrievalQueryException(String message) {
		super(message);
	}

	public RetrievalQueryException(String message, Throwable cause) {
		super(message, cause);
	}

	public RetrievalQueryException(Throwable cause) {
		super(cause);
	}
	
}
