package frame.retrieval.engine.index.reader;

import frame.retrieval.engine.RetrievalException;

/**
 * 
 * @author 
 *
 */
public class RetrievalReaderException extends RetrievalException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RetrievalReaderException() {
		super();
	}

	public RetrievalReaderException(String message) {
		super(message);
	}

	public RetrievalReaderException(String message, Throwable cause) {
		super(message, cause);
	}

	public RetrievalReaderException(Throwable cause) {
		super(cause);
	}
	
}
