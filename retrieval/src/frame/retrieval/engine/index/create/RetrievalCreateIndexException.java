package frame.retrieval.engine.index.create;

import frame.retrieval.engine.RetrievalException;

/**
 * 
 * @author 
 *
 */
public class RetrievalCreateIndexException extends RetrievalException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6105633071417955376L;

	public RetrievalCreateIndexException() {
		super();
	}

	public RetrievalCreateIndexException(String message) {
		super(message);
	}

	public RetrievalCreateIndexException(String message, Throwable cause) {
		super(message, cause);
	}

	public RetrievalCreateIndexException(Throwable cause) {
		super(cause);
	}

}
