package frame.base.system.exception;

import frame.base.core.exception.SnoicsRuntimeException;
/**
 * SnoicsSystemException
 * @author 
 *
 */
public class SnoicsSystemException extends SnoicsRuntimeException{

	private static final long serialVersionUID = -828569972811611241L;

	public SnoicsSystemException(){
		super();
	}
	
	public SnoicsSystemException(String exceptionmanager){
		super(exceptionmanager);
	}
	
    public SnoicsSystemException(Throwable cause) {
        super(cause);
    }
    
    public SnoicsSystemException(String exceptionmanager, Throwable cause) {
        super(exceptionmanager, cause);
    }
}
