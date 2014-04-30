package frame.base.core.util.jaxb;

import frame.base.core.exception.SnoicsRuntimeException;


/**
 * Xml解组异常
 * @author 
 *
 *
 */

public class XmlUnmarshalException extends SnoicsRuntimeException{
	private static final long serialVersionUID = 1L;

    public XmlUnmarshalException() {
    	super();
    }
    
    public XmlUnmarshalException(String message) {
    	super(message);
    }
    
    public XmlUnmarshalException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public XmlUnmarshalException(Throwable cause) {
        super(cause);
    }
}
