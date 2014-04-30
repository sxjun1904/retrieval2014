package frame.retrieval.engine.context;

import frame.retrieval.engine.RetrievalException;

/**
 * 加载异常
 * 
 * @author 
 *
 */
public class RetrievalLoadException extends RetrievalException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RetrievalLoadException() {
		super();
	}

	public RetrievalLoadException(String message) {
		super(message);
	}

	public RetrievalLoadException(String message, Throwable cause) {
		super(message, cause);
	}

	public RetrievalLoadException(Throwable cause) {
		super(cause);
	}

}
