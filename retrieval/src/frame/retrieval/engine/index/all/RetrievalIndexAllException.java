package frame.retrieval.engine.index.all;

import frame.retrieval.engine.RetrievalException;

/**
 * 批量创建索引异常
 * @author 
 *
 */
public class RetrievalIndexAllException extends RetrievalException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6105633071417955376L;

	public RetrievalIndexAllException() {
		super();
	}

	public RetrievalIndexAllException(String message) {
		super(message);
	}

	public RetrievalIndexAllException(String message, Throwable cause) {
		super(message, cause);
	}

	public RetrievalIndexAllException(Throwable cause) {
		super(cause);
	}

}
