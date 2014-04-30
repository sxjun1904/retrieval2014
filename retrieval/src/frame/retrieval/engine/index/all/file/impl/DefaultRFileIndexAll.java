package frame.retrieval.engine.index.all.file.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.lucene.index.IndexWriter;

import frame.base.core.util.StringClass;
import frame.base.core.util.UtilTool;
import frame.base.core.util.file.FileHelper;
import frame.retrieval.engine.RetrievalType;
import frame.retrieval.engine.common.RetrievalUtil;
import frame.retrieval.engine.facade.IRDocOperatorFacade;
import frame.retrieval.engine.facade.IRQueryFacade;
import frame.retrieval.engine.index.all.RetrievalIndexAllException;
import frame.retrieval.engine.index.all.file.IIndexAllFileInterceptor;
import frame.retrieval.engine.index.all.file.IRFileIndexAll;
import frame.retrieval.engine.index.doc.file.FileIndexDocument;
import frame.retrieval.engine.index.doc.file.RFileIndexAllItem;
import frame.retrieval.engine.index.doc.internal.RDocItem;

/**
 * 内置对文件进行批量创建索引默认实现
 * @author 
 *
 */
public class DefaultRFileIndexAll implements IRFileIndexAll{
	private Log log=RetrievalUtil.getLog(this.getClass());
	
	private static FileHelper FileHelper = new FileHelper();
	private IRQueryFacade queryFacade=null;
	private IRDocOperatorFacade docOperatorFacade=null;
	private RFileIndexAllItem fileIndexAllItem=null;
	private long indexCount=0;
	
	/**
	 * 获取文件批量索引对象
	 * @return
	 */
	public RFileIndexAllItem getFileIndexAllItem() {
		return fileIndexAllItem;
	}

	/**
	 * 设置文件批量索引对象
	 * @param fileIndexAllItem
	 */
	public void setFileIndexAllItem(RFileIndexAllItem fileIndexAllItem) {
		if(fileIndexAllItem.getIndexPathType()==null){
			throw new RetrievalIndexAllException("RFileIndexAllItem 的 indexPathType 属性 不允许为空！！！");
		}
		if(fileIndexAllItem.getIndexOperatorType()==null){
			throw new RetrievalIndexAllException("RFileIndexAllItem 的 RetrievalType.RIndexOperatorType 属性 不允许为空！！！");
		}
		this.fileIndexAllItem = fileIndexAllItem;
		this.queryFacade=fileIndexAllItem.getQueryFacade();
		this.docOperatorFacade=fileIndexAllItem.getDocOperatorFacade();
	}

	/**
	 * 给所有文件内容建索引
	 */
	public long indexAll() {

		List<FileIndexDocument> allFileIndexDocumentList=new ArrayList<FileIndexDocument>();
		
		indexAll(fileIndexAllItem.getFileBasePath(),allFileIndexDocumentList);
			
		createFileIndex(allFileIndexDocumentList);
		
		return indexCount;
		
	}
	
	/**
	 * 递归生成所有文件的索引
	 * @param filePath
	 * @param allFileIndexDocumentList
	 */
	private void indexAll(String filePath,List<FileIndexDocument> allFileIndexDocumentList){
		
		if(!FileHelper.isDir(filePath)){
			
			if(allFileIndexDocumentList!=null && allFileIndexDocumentList.size()>0){
				createFileIndex(allFileIndexDocumentList);
			}
			
			return;
		}
		
		String[] fileList=new File(filePath).list();
		
		if(fileList!=null && fileList.length>0){
			int length=fileList.length;
			
			for(int i=0;i<length;i++){
				String fileName=StringClass.getFormatPath(filePath+"/"+fileList[i]);
				
				if(FileHelper.isDir(fileName)){
					if(fileIndexAllItem.isIncludeSubDir()){
						indexAll(fileName,allFileIndexDocumentList);
					}
				}else if(FileHelper.isFile(fileName)){

					String fileType=RetrievalUtil.getFileType(fileName);
					
					if(fileType.equals("")){
						continue;
					}
					
					if(fileIndexAllItem.getFileTypes()!=null 
							&& fileIndexAllItem.getFileTypes().size()>0
							&& !fileIndexAllItem.getFileTypes().contains(fileType)){
						continue;
					}
					
					File file=new File(fileName);
					
					FileIndexDocument fileIndexDocument=new FileIndexDocument(fileIndexAllItem.isFullContentFlag(),fileIndexAllItem.getCharsetName());
					

					String absolutePath=StringClass.getFormatPath(file.getAbsolutePath());
					String basePath=StringClass.getFormatPath(fileIndexAllItem.getFileBasePath());
					String relativizePath=StringClass.getReplaceString(absolutePath, basePath, "");
					relativizePath=StringClass.getFormatPath("/"+relativizePath);
					
					fileIndexDocument.setFile(file);
					fileIndexDocument.setFileBasePath(basePath);
					fileIndexDocument.setFileId(relativizePath);
					
					allFileIndexDocumentList.add(fileIndexDocument);
					
				}
				
				if(allFileIndexDocumentList.size()>=fileIndexAllItem.getPageSize()){
					createFileIndex(allFileIndexDocumentList);
				}
				
			}
		}

		if(allFileIndexDocumentList.size()>=fileIndexAllItem.getPageSize()){
			createFileIndex(allFileIndexDocumentList);
		}
		
	}
	
	/**
	 * 将一个文件列表生成索引
	 * @param list
	 */
	private void createFileIndex(List<FileIndexDocument> list){
		int length=list.size();
		
		indexCount+=length;

		boolean debugLogFlag=false;
		if(log.isDebugEnabled()){
			debugLogFlag=true;
		}
		
		List<String> deleteFileDocumentIdList=new ArrayList<String>();

		StringBuilder stringBuilder=new StringBuilder();
		for(int i=0;i<length;i++){
			FileIndexDocument fileIndexDocument=list.get(i);
			
			fileIndexDocument.setIndexInfoType(fileIndexAllItem.getIndexInfoType());
			fileIndexDocument.setIndexPathType(fileIndexAllItem.getIndexPathType());
			
			//拦截生成文件附加索引内容
			IIndexAllFileInterceptor indexAllFileInterceptor=fileIndexAllItem.getIndexAllFileInterceptor();
			
			if(indexAllFileInterceptor!=null){
				Map<String,Object> addonFileDocuments=indexAllFileInterceptor.interceptor(fileIndexDocument);
				Map<String,Object> addonFileDocumentsType=indexAllFileInterceptor.getFieldsType();
				if(addonFileDocuments!=null && addonFileDocuments.size()>0){
					Object[][] objects=UtilTool.getMapKeyValue(addonFileDocuments);
					int objectlength=objects.length;
					for(int j=0;j<objectlength;j++){
						String fieldName=String.valueOf(objects[j][0]);
						String fieldValue=String.valueOf(objects[j][1]);
						String type="";
						
						if(addonFileDocumentsType!=null){
							type=String.valueOf(addonFileDocumentsType.get(fieldName));
						}
						
						RDocItem docItem=new RDocItem();
						docItem.setName(fieldName);
						docItem.setContent(fieldValue);
						
						if(!type.equals("")){
							if(type.equalsIgnoreCase(String.valueOf(RetrievalType.RDocItemType.KEYWORD))){
								fileIndexDocument.addKeyWord(docItem);
							}else if(type.equalsIgnoreCase(String.valueOf(RetrievalType.RDocItemType.DATE))){
								fileIndexDocument.addDateProperty(docItem);
							}else if(type.equalsIgnoreCase(String.valueOf(RetrievalType.RDocItemType.NUMBER))){
								fileIndexDocument.addNumberProperty(docItem);
							}else if(type.equalsIgnoreCase(String.valueOf(RetrievalType.RDocItemType.PROPERTY))){
								fileIndexDocument.addContent(docItem);
							}else{
								fileIndexDocument.addContent(docItem);
							}
						}else{
							fileIndexDocument.addContent(docItem);	
						}
					}
				}
			}
			
			//判断当前操作时属于更新索引，还是重建索引
			if(fileIndexAllItem.getIndexOperatorType()==RetrievalType.RIndexOperatorType.UPDATE){
				
				if(debugLogFlag){
					stringBuilder.append("批量文件索引["+fileIndexDocument.getIndexPathType()+"]更新:"+fileIndexDocument.getFile().getAbsolutePath()+"\n");
				}
				
				String existsFileDocumentId=StringClass.getString(queryFacade.queryFileDocumentIndexIdByFileId(fileIndexAllItem.getIndexPathType(), fileIndexDocument.getFileId()));
				if(existsFileDocumentId.equals("")){
					list.add(fileIndexDocument);
				}else{
					fileIndexDocument.setId(existsFileDocumentId);
					list.add(fileIndexDocument);
					
					deleteFileDocumentIdList.add(existsFileDocumentId);
				}
			}else{
				if(debugLogFlag){
					stringBuilder.append("批量文件索引["+fileIndexDocument.getIndexPathType()+"]新增:"+fileIndexDocument.getFile().getAbsolutePath()+"\n");
				}
			}
		}
		
		log.debug(stringBuilder);
		
		if(deleteFileDocumentIdList!=null && deleteFileDocumentIdList.size()>0){
			docOperatorFacade.delete(fileIndexAllItem.getIndexPathType(), deleteFileDocumentIdList);
		}

		Iterator<FileIndexDocument> it=list.iterator();
		List<FileIndexDocument> theList=new ArrayList<FileIndexDocument>();
		while(it.hasNext()){
			theList.add(it.next());
			it.remove();
			if(theList.size()>=fileIndexAllItem.getPageSize()){
				docOperatorFacade.createFileIndexs(theList,fileIndexAllItem.getMaxIndexFileSize());
				theList.clear();
			}
		}
		if(theList.size()>0){
			docOperatorFacade.createFileIndexs(theList,fileIndexAllItem.getMaxIndexFileSize());
			theList.clear();
		}
		theList=null;
		list.clear();
	}

	@Override
	public long indexAll(IndexWriter indexWriter) {
		return 0;
	}
}
