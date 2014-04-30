package frame.retrieval.engine.index.directory;

import frame.retrieval.engine.RetrievalException;

/**
 * 
 * @author 
 *
 */
public class RetrievalDirectoryException extends RetrievalException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RetrievalDirectoryException() {
		super();
	}

	public RetrievalDirectoryException(String message) {
		super(message);
	}

	public RetrievalDirectoryException(String message, Throwable cause) {
		super(message, cause);
	}

	public RetrievalDirectoryException(Throwable cause) {
		super(cause);
	}
	
}
