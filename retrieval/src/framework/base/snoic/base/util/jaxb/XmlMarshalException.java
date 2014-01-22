/**
 * Copyright 2010 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package framework.base.snoic.base.util.jaxb;

import framework.base.snoic.base.exception.SnoicsRuntimeException;


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
