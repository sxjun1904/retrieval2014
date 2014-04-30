package frame.retrieval.engine.index.reader;

import frame.retrieval.engine.RetrievalException;

/**
 * 
 * @author 
 *
 */
public class RetrievalDeleteException extends RetrievalException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RetrievalDeleteException() {
		super();
	}

	public RetrievalDeleteException(String message) {
		super(message);
	}

	public RetrievalDeleteException(String message, Throwable cause) {
		super(message, cause);
	}

	public RetrievalDeleteException(Throwable cause) {
		super(cause);
	}
	
}
