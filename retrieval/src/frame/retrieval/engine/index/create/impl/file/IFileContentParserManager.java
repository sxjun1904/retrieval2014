package frame.retrieval.engine.index.create.impl.file;

/**
 * 文件内容解析管理器
 * @author 
 *
 */
public interface IFileContentParserManager {

	/**
	 * 注册文件内容解析器
	 * @param fileType
	 * @param fileContentParser
	 */
	public void regFileContentParser(String fileType,IFileContentParser fileContentParser);
	

	/**
	 * 移除文件内容解析器
	 * @param fileType
	 */
	public void removeFileContentParser(String fileType);
	

	/**
	 * 移除所有文件内容解析器
	 */
	public void removeAllFileContentParser();
	

	/**
	 * 获取文件内容解析器
	 * @param fileType
	 * @return
	 */
	public IFileContentParser getFileContentParser(String fileType);
	

	/**
	 * 获取默认文件内容解析器,当文件类型没有对应的解析器时，默认使用该解析器进行解析
	 * @return
	 */
	public IFileContentParser getDefaultFileContentParser();
	

	/**
	 * 设置默认文件内容解析器,当文件类型没有对应的解析器时，默认使用该解析器进行解析
	 * @param defaultFileContentParser
	 */
	public void setDefaultFileContentParser(
			IFileContentParser defaultFileContentParser);
	
	
}
