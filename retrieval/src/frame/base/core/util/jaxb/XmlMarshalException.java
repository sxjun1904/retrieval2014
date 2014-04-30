package frame.base.core.util.jaxb;

import frame.base.core.exception.SnoicsRuntimeException;


/**
 * Xml编组异常
 * @author 
 *
 *
 */

public class XmlMarshalException extends SnoicsRuntimeException{
	private static final long serialVersionUID = 1L;

    public XmlMarshalException() {
    	super();
    }
    
    public XmlMarshalException(String message) {
    	super(message);
    }
    
    public XmlMarshalException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public XmlMarshalException(Throwable cause) {
        super(cause);
    }
}
