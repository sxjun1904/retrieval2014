package frame.base.core.exception;

/**
 * 异常
 *
 * @author  [snoics@gmail.com]
 *
 */

public class SnoicsException extends Exception{

	private static final long serialVersionUID = -4874802076374039123L;

    public SnoicsException() {
    	super();
    }
    
    public SnoicsException(String message) {
    	super(message);
    }
    
    public SnoicsException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public SnoicsException(Throwable cause) {
        super(cause);
    }
}
