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
package framework.retrieval.engine.index.directory;

import framework.retrieval.engine.RetrievalException;

/**
 * 
 * @author 
 *
 */
public class RetrievalDirectoryException extends RetrievalException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RetrievalDirectoryException() {
		super();
	}

	public RetrievalDirectoryException(String message) {
		super(message);
	}

	public RetrievalDirectoryException(String message, Throwable cause) {
		super(message, cause);
	}

	public RetrievalDirectoryException(Throwable cause) {
		super(cause);
	}
	
}
