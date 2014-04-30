package frame.retrieval.engine.context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;

import frame.base.core.util.StringClass;
import frame.base.core.util.properties.ReadProperties;
import frame.retrieval.engine.analyzer.impl.CJKAnalyzerBuilder;
import frame.retrieval.engine.common.RetrievalUtil;
import frame.retrieval.engine.index.all.database.impl.rdAbstract.DefaultRDatabaseIndexAllImpl;
import frame.retrieval.engine.index.create.impl.file.FileContentParserManager;
import frame.retrieval.engine.query.formatter.impl.HighlighterMaker;

/**
 * 全文检索属性容器对象
 * 				默认的属性配置文件为classpath下的retrieval.properties文件
 * 
 * @author 
 *
 */
public class RetrievalProperties {
	private Log log=RetrievalUtil.getLog(this.getClass());
	private ReadProperties readProperties=new ReadProperties();
	private LuceneProperties luceneProperties=new LuceneProperties();
	
	/**
	 * 属性配置文件名
	 */
	private static final String PROPERTIES_NAME_FILE_NAME="retrieval.properties";
	

	/**
	 * 
		<br>====================================================================================
		<br>设置索引创建执行参数，如果不设置，则使用默认值  20
		<br>
		<br>批量创建文件索引时每页最大的文件索引文档数量，
		<br>即使在创建索引时，通过API设置超过这个值的数量，也不会生效
		<br>
		<br>====================================================================================
	 */
	private int propertyValueIndexMaxFileDocumentPageSize=RetrievalLoaderConstant.DEFAULT_INDEX_MAX_FILE_DOCUMENT_PAGE_SIZE;
	private static final String PROPERTIES_NAME_INDEX_MAX_FILE_DOCUMENT_PAGE_SIZE="INDEX_MAX_FILE_DOCUMENT_PAGE_SIZE";
	
	
	

	/**
	 * 
		<br>====================================================================================
		<br>设置索引创建执行参数，如果不设置，则使用默认值  3145728（单位：字节）
		<br>
		<br>创建文件索引时，如果文件大小超过这里设置的限制的大小，则忽略该文件的内容，不对文件内容解析创建索引
		<br>
		<br>====================================================================================
	 */
	private long propertyValueIndexMaxIndexFileSize=RetrievalLoaderConstant.DEFAULT_INDEX_MAX_INDEX_FILE_SIZE;
	private static final String PROPERTIES_NAME_INDEX_MAX_INDEX_FILE_SIZE="INDEX_MAX_INDEX_FILE_SIZE";
	
	
	

	/**
	 * 
		<br>====================================================================================
		<br>设置索引创建执行参数，如果不设置，则使用默认值  2000
		<br>
		<br>批量创建数据库索引时，从数据库中读取的每页数据最大记录数
		<br>即使在创建索引时，通过API设置超过这个值的数量，也不会生效
		<br>
		<br>====================================================================================
	 */
	private int propertyValueIndexMaxDBDocumentPageSize=RetrievalLoaderConstant.DEFAULT_INDEX_MAX_DB_DOCUMENT_PAGE_SIZE;
	private static final String PROPERTIES_NAME_INDEX_MAX_DB_DOCUMENT_PAGE_SIZE="INDEX_MAX_DB_DOCUMENT_PAGE_SIZE";
	
	
	

	/**
	 * 
		<br>====================================================================================
		<br>设置索引创建执行参数，如果不设置，则使用默认值  utf-8
		<br>
		<br>解析文本文件内容时使用的默认字符集
		<br>
		<br>====================================================================================
	 */
	private String propertyValueIndexDefaultCharset=RetrievalLoaderConstant.DEFAULT_INDEX_DEFAULT_CHARSET;
	private static final String PROPERTIES_NAME_INDEX_DEFAULT_CHARSET="INDEX_DEFAULT_CHARSET";
	
	
	

	/**
		<br>====================================================================================
		<br>设置索引检索执行参数，如果不设置，则使用默认值  3000
		<br>
		<br>查询结果返回的最大结果集
		<br>
		<br>====================================================================================
	 */
	private int propertyValueQueryResultTopDocsNum=RetrievalLoaderConstant.DEFAULT_QUERY_RESULT_TOP_DOCS_NUM;
	private static final String PROPERTIES_NAME_QUERY_RESULT_TOP_DOCS_NUM="QUERY_RESULT_TOP_DOCS_NUM";
	
	
	
	
	/**
	 * 
		<br>====================================================================================
		<br>Retrieval扩展，如果不设置，则使用默认值  framework.base.retrieval.engine.index.create.impl.file.FileContentParserManager
		<br>
		<br>文件内容解析管理器,对文件创建索引时，通过该管理器对不同的文件类型创建各自对应的解析器对文件内容进行解析
		<br>需要实现接口：framework.base.retrieval.engine.index.create.impl.file.IFileContentParserManager
		<br>====================================================================================
	 */
	@SuppressWarnings("unchecked")
	private Class propertyValueRestrievalExtendsClassFileContentParserManager=FileContentParserManager.class;
	private static final String PROPERTIES_NAME_RETRIEVAL_EXTENDS_CLASS_FILE_CONTENT_PARSER_MANAGER="RETRIEVAL_EXTENDS_CLASS_FILE_CONTENT_PARSER_MANAGER";
	
	
	
	
	/**
	 * 
		<br>====================================================================================
		<br>Retrieval扩展，如果不设置，则使用默认值  framework.base.retrieval.engine.analyzer.CJKAnalyzerBuilder
		<br>
		<br>索引分词器,内置索引分词器包括:
		<br>	framework.base.retrieval.engine.analyzer.CJKAnalyzerBuilder(默认)
		<br>	framework.base.retrieval.engine.analyzer.IKCAnalyzerBuilder(中文分词强烈推荐)
		<br>	framework.base.retrieval.engine.analyzer.StandardAnalyzerBuilder
		<br>	framework.base.retrieval.engine.analyzer.ChineseAnalyzerBuilder
		<br>
		<br>需要实现接口：framework.base.retrieval.engine.analyzer.IRAnalyzerBuilder
		<br>====================================================================================
	 */
	@SuppressWarnings("unchecked")
	private Class propertyValueRestrievalExtendsClassAnalyzerBuilder=CJKAnalyzerBuilder.class;
	private static final String PROPERTIES_NAME_RETRIEVAL_EXTENDS_CLASS_ANALYZER_BUILDER="RETRIEVAL_EXTENDS_CLASS_ANALYZER_BUILDER";
	
	
	
	
	/**
	 * 
		<br>====================================================================================
		<br>Retrieval扩展，如果不设置，则使用默认值  framework.base.retrieval.engine.query.formatter.HighlighterMaker
		<br>
		<br>对查询结果内容进行高亮处理
		<br>
		<br>需要实现接口：framework.base.retrieval.engine.query.formatter.IHighlighterMaker
		<br>====================================================================================
	 */
	@SuppressWarnings("unchecked")
	private Class propertyValueRestrievalExtendsClassHeighlighterMaker=HighlighterMaker.class;
	private static final String PROPERTIES_NAME_RETRIEVAL_EXTENDS_CLASS_HEIGHLIGHTER_MAKER="RETRIEVAL_EXTENDS_CLASS_HEIGHLIGHTER_MAKER";

	
	/**
	 * 
		<br>====================================================================================
		<br>Retrieval扩展，如果不设置，则使用默认值  framework.base.retrieval.engine.index.all.impl.DefaultRDatabaseIndexAllImpl
		<br>
		<br>对查询结果内容进行高亮处理
		<br>
		<br>需要继承抽象类：framework.base.retrieval.engine.index.all.impl.AbstractDefaultRDatabaseIndexAll
		<br>或直接实现接口：framework.base.retrieval.engine.index.all.IRDatabaseIndexAll
		<br>====================================================================================
	 */
	@SuppressWarnings("unchecked")
	private Class propertyValueRestrievalExtendsClassDatabaseIndexAll=DefaultRDatabaseIndexAllImpl.class;
	private static final String PROPERTIES_NAME_RETRIEVAL_EXTENDS_CLASS_DATABASE_INDEX_ALL="RETRIEVAL_EXTENDS_CLASS_DATABASE_INDEX_ALL";
	
	public RetrievalProperties(){
		InputStream inputStream=this.getClass().getResourceAsStream("/"+PROPERTIES_NAME_FILE_NAME);
		if(inputStream==null){
			RetrievalUtil.debugLog(log,"未发现全文检索属性配置文件:"+PROPERTIES_NAME_FILE_NAME+"，使用默认配置");
			return;
		}
		
		RetrievalUtil.debugLog(log,"发现全文检索属性配置文件:"+PROPERTIES_NAME_FILE_NAME+"，载入配置");
		Properties properties=new Properties();
		
		try {
			properties.load(inputStream);
			loadProperties(properties);
		} catch (IOException e) {
			throw new RetrievalLoadException(e);
		}
	}
	
	public RetrievalProperties(Properties properties){
		RetrievalUtil.debugLog(log,"传入全文检索属性配置对象，载入配置");
		loadProperties(properties);
	}
	
	private void loadProperties(Properties properties){
		readProperties.setProperties(properties);
		readProperties.parse();
		
		String paramValueString=null;
		
		paramValueString=StringClass.getString(readProperties.readValue(LuceneProperties.PROPERTIES_NAME_LUCENE_PARAM_VERSION));
		if(!paramValueString.equals("")){
			luceneProperties.propertyValueLuceneVersion=RetrievalUtil.getVersion(paramValueString);
		}
		paramValueString=null;
		

		paramValueString=StringClass.getString(readProperties.readValue(LuceneProperties.PROPERTIES_NAME_LUCENE_PARAM_MAX_FIELD_LENGTH));
		if(!paramValueString.equals("")){
			try{
				luceneProperties.propertyValueLuceneMaxFieldLength=Integer.parseInt(paramValueString);
				if(luceneProperties.propertyValueLuceneMaxFieldLength<=0){
					luceneProperties.propertyValueLuceneMaxFieldLength=RetrievalLoaderConstant.DEFAULT_LUCENE_PARAM_MAX_FIELD_LENGTH;
				}
			}catch(Exception e){
				throw new RetrievalLoadException(e);
			}
		}
		paramValueString=null;

		paramValueString=StringClass.getString(readProperties.readValue(LuceneProperties.PROPERTIES_NAME_LUCENE_PARAM_RAM_BUFFER_SIZE_MB));
		if(!paramValueString.equals("")){
			try{
				luceneProperties.propertyValueLuceneRamBufferSizeMB=Double.parseDouble(paramValueString);
				if(luceneProperties.propertyValueLuceneRamBufferSizeMB<=0){
					luceneProperties.propertyValueLuceneRamBufferSizeMB=RetrievalLoaderConstant.DEFAULT_LUCENE_PARAM_RAM_BUFFER_SIZE_MB;
				}
			}catch(Exception e){
				throw new RetrievalLoadException(e);
			}
		}
		paramValueString=null;

		paramValueString=StringClass.getString(readProperties.readValue(LuceneProperties.PROPERTIES_NAME_LUCENE_PARAM_MAX_BUFFERED_DOCS));
		if(!paramValueString.equals("")){
			try{
				luceneProperties.propertyValueLuceneMaxBufferedDocs=Integer.parseInt(paramValueString);
				if(luceneProperties.propertyValueLuceneMaxBufferedDocs<=0){
					luceneProperties.propertyValueLuceneMaxBufferedDocs=RetrievalLoaderConstant.DEFAULT_LUCENE_PARAM_MAX_BUFFERED_DOCS;
				}
			}catch(Exception e){
				throw new RetrievalLoadException(e);
			}
		}
		paramValueString=null;

		paramValueString=StringClass.getString(readProperties.readValue(LuceneProperties.PROPERTIES_NAME_LUCENE_PARAM_MERGE_FACTOR));
		if(!paramValueString.equals("")){
			try{
				luceneProperties.propertyValueLuceneMergerFactor=Integer.parseInt(paramValueString);
				if(luceneProperties.propertyValueLuceneMergerFactor<=0){
					luceneProperties.propertyValueLuceneMergerFactor=RetrievalLoaderConstant.DEFAULT_LUCENE_PARAM_MERGE_FACTOR;
				}
			}catch(Exception e){
				throw new RetrievalLoadException(e);
			}
		}
		paramValueString=null;

		paramValueString=StringClass.getString(readProperties.readValue(LuceneProperties.PROPERTIES_NAME_LUCENE_PARAM_MAX_MERGE_DOCS));
		if(!paramValueString.equals("")){
			try{
				luceneProperties.propertyValueLuceneMaxMergeDocs=Integer.parseInt(paramValueString);
				if(luceneProperties.propertyValueLuceneMaxMergeDocs<=0){
					luceneProperties.propertyValueLuceneMaxMergeDocs=RetrievalLoaderConstant.DEFAULT_LUCENE_PARAM_MAX_MERGE_DOCS;
				}
			}catch(Exception e){
				throw new RetrievalLoadException(e);
			}
		}
		paramValueString=null;

		paramValueString=StringClass.getString(readProperties.readValue(PROPERTIES_NAME_INDEX_MAX_FILE_DOCUMENT_PAGE_SIZE));
		if(!paramValueString.equals("")){
			try{
				propertyValueIndexMaxFileDocumentPageSize=Integer.parseInt(paramValueString);
				if(propertyValueIndexMaxFileDocumentPageSize<=0){
					propertyValueIndexMaxFileDocumentPageSize=RetrievalLoaderConstant.DEFAULT_INDEX_MAX_FILE_DOCUMENT_PAGE_SIZE;
				}
			}catch(Exception e){
				throw new RetrievalLoadException(e);
			}
		}
		paramValueString=null;

		paramValueString=StringClass.getString(readProperties.readValue(PROPERTIES_NAME_INDEX_MAX_INDEX_FILE_SIZE));
		if(!paramValueString.equals("")){
			try{
				propertyValueIndexMaxIndexFileSize=Long.parseLong(paramValueString);
				if(propertyValueIndexMaxIndexFileSize<=0){
					propertyValueIndexMaxIndexFileSize=RetrievalLoaderConstant.DEFAULT_INDEX_MAX_INDEX_FILE_SIZE;
				}
			}catch(Exception e){
				throw new RetrievalLoadException(e);
			}
		}
		paramValueString=null;

		paramValueString=StringClass.getString(readProperties.readValue(PROPERTIES_NAME_INDEX_MAX_DB_DOCUMENT_PAGE_SIZE));
		if(!paramValueString.equals("")){
			try{
				propertyValueIndexMaxDBDocumentPageSize=Integer.parseInt(paramValueString);
				if(propertyValueIndexMaxDBDocumentPageSize<=0){
					propertyValueIndexMaxDBDocumentPageSize=RetrievalLoaderConstant.DEFAULT_INDEX_MAX_DB_DOCUMENT_PAGE_SIZE;
				}
			}catch(Exception e){
				throw new RetrievalLoadException(e);
			}
		}
		paramValueString=null;

		paramValueString=StringClass.getString(readProperties.readValue(PROPERTIES_NAME_INDEX_DEFAULT_CHARSET));
		if(!paramValueString.equals("")){
			propertyValueIndexDefaultCharset=paramValueString;
		}
		paramValueString=null;


		paramValueString=StringClass.getString(readProperties.readValue(PROPERTIES_NAME_QUERY_RESULT_TOP_DOCS_NUM));
		if(!paramValueString.equals("")){
			try{
				propertyValueQueryResultTopDocsNum=Integer.parseInt(paramValueString);
				if(propertyValueQueryResultTopDocsNum<=0){
					propertyValueQueryResultTopDocsNum=RetrievalLoaderConstant.DEFAULT_QUERY_RESULT_TOP_DOCS_NUM;
				}
			}catch(Exception e){
				throw new RetrievalLoadException(e);
			}
		}
		paramValueString=null;


		paramValueString=StringClass.getString(readProperties.readValue(PROPERTIES_NAME_RETRIEVAL_EXTENDS_CLASS_FILE_CONTENT_PARSER_MANAGER));
		if(!paramValueString.equals("")){
			try{
				propertyValueRestrievalExtendsClassFileContentParserManager=Class.forName(paramValueString);
			}catch(Exception e){
				throw new RetrievalLoadException(e);
			}
		}
		paramValueString=null;


		paramValueString=StringClass.getString(readProperties.readValue(PROPERTIES_NAME_RETRIEVAL_EXTENDS_CLASS_ANALYZER_BUILDER));
		if(!paramValueString.equals("")){
			try{
				propertyValueRestrievalExtendsClassAnalyzerBuilder=Class.forName(paramValueString);
			}catch(Exception e){
				throw new RetrievalLoadException(e);
			}
		}
		paramValueString=null;


		paramValueString=StringClass.getString(readProperties.readValue(PROPERTIES_NAME_RETRIEVAL_EXTENDS_CLASS_HEIGHLIGHTER_MAKER));
		if(!paramValueString.equals("")){
			try{
				propertyValueRestrievalExtendsClassHeighlighterMaker=Class.forName(paramValueString);
			}catch(Exception e){
				throw new RetrievalLoadException(e);
			}
		}
		paramValueString=null;


		paramValueString=StringClass.getString(readProperties.readValue(PROPERTIES_NAME_RETRIEVAL_EXTENDS_CLASS_DATABASE_INDEX_ALL));
		if(!paramValueString.equals("")){
			try{
				propertyValueRestrievalExtendsClassDatabaseIndexAll=Class.forName(paramValueString);
			}catch(Exception e){
				throw new RetrievalLoadException(e);
			}
		}
		paramValueString=null;
		
		readProperties.close();
		
	}

	public LuceneProperties getLuceneProperties() {
		return luceneProperties;
	}

	public void setLuceneProperties(LuceneProperties luceneProperties) {
		this.luceneProperties = luceneProperties;
	}

	/**
	 * 
		<br>====================================================================================
		<br>设置索引创建执行参数，如果不设置，则使用默认值  20
		<br>
		<br>批量创建文件索引时每页最大的文件索引文档数量，
		<br>即使在创建索引时，通过API设置超过这个值的数量，也不会生效
		<br>
		<br>====================================================================================
	 */
	public int getPropertyValueIndexMaxFileDocumentPageSize() {
		return propertyValueIndexMaxFileDocumentPageSize;
	}

	/**
	 * 
		<br>====================================================================================
		<br>设置索引创建执行参数，如果不设置，则使用默认值  20
		<br>
		<br>批量创建文件索引时每页最大的文件索引文档数量，
		<br>即使在创建索引时，通过API设置超过这个值的数量，也不会生效
		<br>
		<br>====================================================================================
	 */
	public void setPropertyValueIndexMaxFileDocumentPageSize(
			int propertyValueIndexMaxFileDocumentPageSize) {
		this.propertyValueIndexMaxFileDocumentPageSize = propertyValueIndexMaxFileDocumentPageSize;
	}

	/**
	 * 
		<br>====================================================================================
		<br>设置索引创建执行参数，如果不设置，则使用默认值  3145728（单位：字节）
		<br>
		<br>创建文件索引时，如果文件大小超过这里设置的限制的大小，则忽略该文件的内容，不对文件内容解析创建索引
		<br>
		<br>====================================================================================
	 */
	public long getPropertyValueIndexMaxIndexFileSize() {
		return propertyValueIndexMaxIndexFileSize;
	}

	/**
	 * 
		<br>====================================================================================
		<br>设置索引创建执行参数，如果不设置，则使用默认值  3145728（单位：字节）
		<br>
		<br>创建文件索引时，如果文件大小超过这里设置的限制的大小，则忽略该文件的内容，不对文件内容解析创建索引
		<br>
		<br>====================================================================================
	 */
	public void setPropertyValueIndexMaxIndexFileSize(
			long propertyValueIndexMaxIndexFileSize) {
		this.propertyValueIndexMaxIndexFileSize = propertyValueIndexMaxIndexFileSize;
	}

	/**
	 * 
		<br>====================================================================================
		<br>设置索引创建执行参数，如果不设置，则使用默认值  500
		<br>
		<br>批量创建数据库索引时，从数据库中读取的每页数据最大记录数
		<br>即使在创建索引时，通过API设置超过这个值的数量，也不会生效
		<br>
		<br>====================================================================================
	 */
	public int getPropertyValueIndexMaxDBDocumentPageSize() {
		return propertyValueIndexMaxDBDocumentPageSize;
	}

	/**
	 * 
		<br>====================================================================================
		<br>设置索引创建执行参数，如果不设置，则使用默认值  500
		<br>
		<br>批量创建数据库索引时，从数据库中读取的每页数据最大记录数
		<br>即使在创建索引时，通过API设置超过这个值的数量，也不会生效
		<br>
		<br>====================================================================================
	 */
	public void setPropertyValueIndexMaxDBDocumentPageSize(
			int propertyValueIndexMaxDBDocumentPageSize) {
		this.propertyValueIndexMaxDBDocumentPageSize = propertyValueIndexMaxDBDocumentPageSize;
	}

	/**
	 * 
		<br>====================================================================================
		<br>设置索引创建执行参数，如果不设置，则使用默认值  utf-8
		<br>
		<br>解析文本文件内容时使用的默认字符集
		<br>
		<br>====================================================================================
	 */
	public String getPropertyValueIndexDefaultCharset() {
		return propertyValueIndexDefaultCharset;
	}

	/**
	 * 
		<br>====================================================================================
		<br>设置索引创建执行参数，如果不设置，则使用默认值  utf-8
		<br>
		<br>解析文本文件内容时使用的默认字符集
		<br>
		<br>====================================================================================
	 */
	public void setPropertyValueIndexDefaultCharset(
			String propertyValueIndexDefaultCharset) {
		this.propertyValueIndexDefaultCharset = propertyValueIndexDefaultCharset;
	}

	/**
	 * 
		<br>====================================================================================
		<br>设置索引检索执行参数，如果不设置，则使用默认值  3000
		<br>
		<br>查询结果返回的最大结果集
		<br>
		<br>====================================================================================
	 */
	public int getPropertyValueQueryResultTopDocsNum() {
		return propertyValueQueryResultTopDocsNum;
	}

	/**
	 * 
		<br>====================================================================================
		<br>设置索引检索执行参数，如果不设置，则使用默认值  3000
		<br>
		<br>查询结果返回的最大结果集
		<br>
		<br>====================================================================================
	 */
	public void setPropertyValueQueryResultTopDocsNum(
			int propertyValueQueryResultTopDocsNum) {
		this.propertyValueQueryResultTopDocsNum = propertyValueQueryResultTopDocsNum;
	}

	/**
	 * 
		<br>====================================================================================
		<br>Retrieval扩展，如果不设置，则使用默认值  framework.base.retrieval.engine.index.create.impl.file.FileContentParserManager
		<br>
		<br>文件内容解析管理器,对文件创建索引时，通过该管理器对不同的文件类型创建各自对应的解析器对文件内容进行解析
		<br>需要实现接口：framework.base.retrieval.engine.index.create.impl.file.IFileContentParserManager
		<br>====================================================================================
	 */
	@SuppressWarnings("unchecked")
	public Class getPropertyValueRestrievalExtendsClassFileContentParserManager() {
		return propertyValueRestrievalExtendsClassFileContentParserManager;
	}

	/**
	 * 
		<br>====================================================================================
		<br>Retrieval扩展，如果不设置，则使用默认值  framework.base.retrieval.engine.index.create.impl.file.FileContentParserManager
		<br>
		<br>文件内容解析管理器,对文件创建索引时，通过该管理器对不同的文件类型创建各自对应的解析器对文件内容进行解析
		<br>需要实现接口：framework.base.retrieval.engine.index.create.impl.file.IFileContentParserManager
		<br>====================================================================================
	 */
	@SuppressWarnings("unchecked")
	public void setPropertyValueRestrievalExtendsClassFileContentParserManager(
			Class propertyValueRestrievalExtendsClassFileContentParserManager) {
		this.propertyValueRestrievalExtendsClassFileContentParserManager = propertyValueRestrievalExtendsClassFileContentParserManager;
	}

	/**
	 * 
		<br>====================================================================================
		<br>Retrieval扩展，如果不设置，则使用默认值  framework.base.retrieval.engine.analyzer.CJKAnalyzerBuilder
		<br>
		<br>索引分词器,内置索引分词器包括:
		<br>	framework.base.retrieval.engine.analyzer.CJKAnalyzerBuilder(默认)
		<br>	framework.base.retrieval.engine.analyzer.IKCAnalyzerBuilder(中文分词强烈推荐)
		<br>	framework.base.retrieval.engine.analyzer.StandardAnalyzerBuilder
		<br>	framework.base.retrieval.engine.analyzer.ChineseAnalyzerBuilder
		<br>
		<br>需要实现接口：framework.base.retrieval.engine.analyzer.IRAnalyzerBuilder
		<br>====================================================================================
	 */
	@SuppressWarnings("unchecked")
	public Class getPropertyValueRestrievalExtendsClassAnalyzerBuilder() {
		return propertyValueRestrievalExtendsClassAnalyzerBuilder;
	}

	/**
	 * 
		<br>====================================================================================
		<br>Retrieval扩展，如果不设置，则使用默认值  framework.base.retrieval.engine.analyzer.CJKAnalyzerBuilder
		<br>
		<br>索引分词器,内置索引分词器包括:
		<br>	framework.base.retrieval.engine.analyzer.CJKAnalyzerBuilder(默认)
		<br>	framework.base.retrieval.engine.analyzer.IKCAnalyzerBuilder(中文分词强烈推荐)
		<br>	framework.base.retrieval.engine.analyzer.StandardAnalyzerBuilder
		<br>	framework.base.retrieval.engine.analyzer.ChineseAnalyzerBuilder
		<br>
		<br>需要实现接口：framework.base.retrieval.engine.analyzer.IRAnalyzerBuilder
		<br>====================================================================================
	 */
	@SuppressWarnings("unchecked")
	public void setPropertyValueRestrievalExtendsClassAnalyzerBuilder(
			Class propertyValueRestrievalExtendsClassAnalyzerBuilder) {
		this.propertyValueRestrievalExtendsClassAnalyzerBuilder = propertyValueRestrievalExtendsClassAnalyzerBuilder;
	}

	/**
	 * 
		<br>====================================================================================
		<br>Retrieval扩展，如果不设置，则使用默认值  framework.base.retrieval.engine.query.formatter.HighlighterMaker
		<br>
		<br>对查询结果内容进行高亮处理
		<br>
		<br>需要实现接口：framework.base.retrieval.engine.query.formatter.IHighlighterMaker
		<br>====================================================================================
	 */
	@SuppressWarnings("unchecked")
	public Class getPropertyValueRestrievalExtendsClassHeighlighterMaker() {
		return propertyValueRestrievalExtendsClassHeighlighterMaker;
	}

	/**
	 * 
		<br>====================================================================================
		<br>Retrieval扩展，如果不设置，则使用默认值  framework.base.retrieval.engine.query.formatter.HighlighterMaker
		<br>
		<br>对查询结果内容进行高亮处理
		<br>
		<br>需要实现接口：framework.base.retrieval.engine.query.formatter.IHighlighterMaker
		<br>====================================================================================
	 */
	@SuppressWarnings("unchecked")
	public void setPropertyValueRestrievalExtendsClassHeighlighterMaker(
			Class propertyValueRestrievalExtendsClassHeighlighterMaker) {
		this.propertyValueRestrievalExtendsClassHeighlighterMaker = propertyValueRestrievalExtendsClassHeighlighterMaker;
	}
	
	/**
	 * 
		<br>====================================================================================
		<br>Retrieval扩展，如果不设置，则使用默认值  framework.base.retrieval.engine.index.all.impl.DefaultRDatabaseIndexAllImpl
		<br>
		<br>对查询结果内容进行高亮处理
		<br>
		<br>需要继承抽象类：framework.base.retrieval.engine.index.all.impl.AbstractDefaultRDatabaseIndexAll
		<br>或直接实现接口：framework.base.retrieval.engine.index.all.IRDatabaseIndexAll
		<br>====================================================================================
	 */
	@SuppressWarnings("unchecked")
	public Class getPropertyValueRestrievalExtendsClassDatabaseIndexAll() {
		return propertyValueRestrievalExtendsClassDatabaseIndexAll;
	}

	/**
	 * 
		<br>====================================================================================
		<br>Retrieval扩展，如果不设置，则使用默认值  framework.base.retrieval.engine.index.all.impl.DefaultRDatabaseIndexAllImpl
		<br>
		<br>对查询结果内容进行高亮处理
		<br>
		<br>需要继承抽象类：framework.base.retrieval.engine.index.all.impl.AbstractDefaultRDatabaseIndexAll
		<br>或直接实现接口：framework.base.retrieval.engine.index.all.IRDatabaseIndexAll
		<br>====================================================================================
	 */
	@SuppressWarnings("unchecked")
	public void setPropertyValueRestrievalExtendsClassDatabaseIndexAll(
			Class propertyValueRestrievalExtendsClassDatabaseIndexAll) {
		this.propertyValueRestrievalExtendsClassDatabaseIndexAll = propertyValueRestrievalExtendsClassDatabaseIndexAll;
	}

	public String toString(){
		StringBuilder propertiesString=new StringBuilder();
		propertiesString.append("RetrievalProperties:{\n");
		
		propertiesString.append("	");
		propertiesString.append("LUCENE_INDEX_BASE_PATH"+":"+luceneProperties.getIndexBasePath()+"\n");
		
		propertiesString.append("	");
		propertiesString.append(LuceneProperties.PROPERTIES_NAME_LUCENE_PARAM_VERSION+":"+luceneProperties.propertyValueLuceneVersion+"\n");

		propertiesString.append("	");
		propertiesString.append(LuceneProperties.PROPERTIES_NAME_LUCENE_PARAM_MAX_FIELD_LENGTH+":"+luceneProperties.propertyValueLuceneMaxFieldLength+"\n");

		propertiesString.append("	");
		propertiesString.append(LuceneProperties.PROPERTIES_NAME_LUCENE_PARAM_RAM_BUFFER_SIZE_MB+":"+luceneProperties.propertyValueLuceneRamBufferSizeMB+"\n");

		propertiesString.append("	");
		propertiesString.append(LuceneProperties.PROPERTIES_NAME_LUCENE_PARAM_MAX_BUFFERED_DOCS+":"+luceneProperties.propertyValueLuceneMaxBufferedDocs+"\n");

		propertiesString.append("	");
		propertiesString.append(LuceneProperties.PROPERTIES_NAME_LUCENE_PARAM_MERGE_FACTOR+":"+luceneProperties.propertyValueLuceneMergerFactor+"\n");

		propertiesString.append("	");
		propertiesString.append(LuceneProperties.PROPERTIES_NAME_LUCENE_PARAM_MAX_MERGE_DOCS+":"+luceneProperties.propertyValueLuceneMaxMergeDocs+"\n");

		propertiesString.append("	");
		propertiesString.append(PROPERTIES_NAME_INDEX_MAX_FILE_DOCUMENT_PAGE_SIZE+":"+propertyValueIndexMaxFileDocumentPageSize+"\n");

		propertiesString.append("	");
		propertiesString.append(PROPERTIES_NAME_INDEX_MAX_INDEX_FILE_SIZE+":"+propertyValueIndexMaxIndexFileSize+"\n");

		propertiesString.append("	");
		propertiesString.append(PROPERTIES_NAME_INDEX_MAX_DB_DOCUMENT_PAGE_SIZE+":"+propertyValueIndexMaxDBDocumentPageSize+"\n");

		propertiesString.append("	");
		propertiesString.append(PROPERTIES_NAME_INDEX_DEFAULT_CHARSET+":"+propertyValueIndexDefaultCharset+"\n");

		propertiesString.append("	");
		propertiesString.append(PROPERTIES_NAME_QUERY_RESULT_TOP_DOCS_NUM+":"+propertyValueQueryResultTopDocsNum+"\n");

		propertiesString.append("	");
		propertiesString.append(PROPERTIES_NAME_RETRIEVAL_EXTENDS_CLASS_FILE_CONTENT_PARSER_MANAGER+":"+propertyValueRestrievalExtendsClassFileContentParserManager+"\n");

		propertiesString.append("	");
		propertiesString.append(PROPERTIES_NAME_RETRIEVAL_EXTENDS_CLASS_ANALYZER_BUILDER+":"+propertyValueRestrievalExtendsClassAnalyzerBuilder+"\n");

		propertiesString.append("	");
		propertiesString.append(PROPERTIES_NAME_RETRIEVAL_EXTENDS_CLASS_HEIGHLIGHTER_MAKER+":"+propertyValueRestrievalExtendsClassHeighlighterMaker+"\n");

		propertiesString.append("	");
		propertiesString.append(PROPERTIES_NAME_RETRIEVAL_EXTENDS_CLASS_DATABASE_INDEX_ALL+":"+propertyValueRestrievalExtendsClassDatabaseIndexAll+"\n");
		
		propertiesString.append("}");
		
		
		return propertiesString.toString();
	}
}
