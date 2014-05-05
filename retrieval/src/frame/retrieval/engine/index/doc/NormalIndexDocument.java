package frame.retrieval.engine.index.doc;

import java.io.Serializable;

import frame.retrieval.engine.index.doc.internal.RDefaultDocument;

/**
 * 当个普通类型Document
 * @author 
 *
 */
public class NormalIndexDocument extends AbstractIndexDocument implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4737929916612807540L;
	
	private RDefaultDocument rDocument=null;
	
	private Object transObject;
	
	public NormalIndexDocument(boolean fullContentFlag){
		rDocument=new RDefaultDocument(fullContentFlag);
	}

	public RDefaultDocument getRDocument() {
		return rDocument;
	}

	public Object getTransObject() {
		return transObject;
	}

	public void setTransObject(Object transObject) {
		this.transObject = transObject;
	}
	
	
	
}
