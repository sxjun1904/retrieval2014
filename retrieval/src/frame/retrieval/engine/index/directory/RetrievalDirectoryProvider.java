package frame.retrieval.engine.index.directory;

import java.io.File;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import frame.retrieval.engine.common.RetrievalUtil;

/**
 * Directory对象生成
 * @author 
 *
 */
public class RetrievalDirectoryProvider {
	
	private RetrievalDirectoryProvider(){
		
	}

	/**
	 * 生成Directory对象
	 * @param baseIndexPath
	 * @param indexPathType
	 * @return
	 */
	public static Directory getDirectory(String baseIndexPath,String indexPathType){
		try {
			Directory dir=FSDirectory.open(new File(RetrievalUtil.getIndexPath(baseIndexPath,indexPathType)));
			return dir;
		} catch (Exception e) {
			throw new RetrievalDirectoryException(e);
		}
	}
}
