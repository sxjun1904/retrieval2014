package frame.retrieval.engine.index;

import frame.retrieval.engine.RetrievalException;

/**
 * 
 * @author 
 *
 */
public class RetrievalIndexException extends RetrievalException{

	private static final long serialVersionUID = 1L;

	public RetrievalIndexException() {
		super();
	}

	public RetrievalIndexException(String message) {
		super(message);
	}

	public RetrievalIndexException(String message, Throwable cause) {
		super(message, cause);
	}

	public RetrievalIndexException(Throwable cause) {
		super(cause);
	}
	
}
