package frame.base.core.exception;
/**
 * SnoicsRuntimeException
 * @author 
 *
 */
public class SnoicsRuntimeException extends RuntimeException{

	private static final long serialVersionUID = 5903550367803942066L;

	public SnoicsRuntimeException(){
		super();
	}
	
	public SnoicsRuntimeException(String exceptionmanager){
		super(exceptionmanager);
	}
	
    public SnoicsRuntimeException(Throwable cause) {
        super(cause);
    }
    
    public SnoicsRuntimeException(String exceptionmanager, Throwable cause) {
        super(exceptionmanager, cause);
    }
}
