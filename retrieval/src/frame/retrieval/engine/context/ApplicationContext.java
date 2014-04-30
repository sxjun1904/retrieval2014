package frame.retrieval.engine.context;

/**
 * 基类
 * @author sxjun
 *
 */
public class ApplicationContext {
	private static RetrievalApplicationContext retrievalApplicationContext=new RetrievalApplicationContext();
	
	public ApplicationContext(){
		
	}
	
	public static RetrievalApplicationContext getApplicationContent(){
		return retrievalApplicationContext;
	}
	
	public static void initIndex(String[] indexPathTypes){
		getApplicationContent().getFacade().initIndex(indexPathTypes);
	}
	
	public static void initIndex(String indexPathType){
		initIndex(new String[]{indexPathType});
	}
	
	public static String[] initIndexSet(String[] indexPathTypes){
		return getApplicationContent().getFacade().initset(indexPathTypes);
	}
	
	public static String initIndexSet(String indexPathType){
		return initIndexSet(new String[]{indexPathType})[0];
	}
	
	public static String[] getLocalIndexPathTypes(String[] indexPathTypes){
		return getApplicationContent().getFacade().getLocalIndexPathTypes(indexPathTypes);
	}
	
	public static void main(String[] args) {
		ApplicationContext.initIndex("DB");
	}
	
}
