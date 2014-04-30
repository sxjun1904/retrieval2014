package frame.retrieval.engine.index.create;

import frame.retrieval.engine.RetrievalException;

/**
 * 
 * @author 
 *
 */
public class RetrievalDocumentException extends RetrievalException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8809286333528942301L;


	public RetrievalDocumentException() {
		super();
	}

	public RetrievalDocumentException(String message) {
		super(message);
	}

	public RetrievalDocumentException(String message, Throwable cause) {
		super(message, cause);
	}

	public RetrievalDocumentException(Throwable cause) {
		super(cause);
	}
}
