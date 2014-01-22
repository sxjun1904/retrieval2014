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
package framework.retrieval.engine.query.item;

import java.io.Serializable;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;

import framework.base.snoic.base.util.StringClass;
import framework.retrieval.engine.RetrievalType;
import framework.retrieval.engine.analyzer.IRAnalyzerFactory;
import framework.retrieval.engine.query.RetrievalQueryException;

/**
 * QueryItem
 * @author 
 *
 */
public class QueryItem implements Serializable{
	private static final long serialVersionUID = -2389520247054981268L;
	
	public static final BooleanClause.Occur MUST=BooleanClause.Occur.MUST;
	public static final BooleanClause.Occur SHOULD=BooleanClause.Occur.SHOULD;
	public static final BooleanClause.Occur MUST_NOT=BooleanClause.Occur.MUST_NOT;
	
	private RetrievalType.RDocItemType docItemType=RetrievalType.RDocItemType.CONTENT;
	private String name=null;
	private String value=null;
	private boolean regex=false;
	
	private Query query=null;
	private QueryWrap queryWrap=null;
	private IRAnalyzerFactory analyzerFactory=null;
	
	/**
	 * 查询字段
	 * @param analyzerFactory		分词器
	 * @param docItemType		索引类型
	 * @param name				字段名称
	 * @param keyWord				查询内容
	 */
	public QueryItem(IRAnalyzerFactory analyzerFactory,
			RetrievalType.RDocItemType docItemType,
			Object name,
			String keyWord){
		this.analyzerFactory=analyzerFactory;
		this.docItemType=docItemType;
		this.name=StringClass.getString(name).toUpperCase();
		this.value=keyWord;
		query=createQuery(docItemType, this.name, keyWord, false);
	}
	
	public QueryItem(IRAnalyzerFactory analyzerFactory,
			RetrievalType.RDocItemType docItemType,
			Object name,
			String keyWord,Float score){
		this.analyzerFactory=analyzerFactory;
		this.docItemType=docItemType;
		this.name=StringClass.getString(name).toUpperCase();
		this.value=keyWord;
		query=createQuery(docItemType, this.name, keyWord, false, score);
	}
	
	/**
	 * 查询字段
	 * @param analyzerFactory		分词器
	 * @param docItemType		索引类型
	 * @param name				字段名称
	 * @param keyWord				查询内容
	 * @param regex				是否正则表达式
	 */
	public QueryItem(IRAnalyzerFactory analyzerFactory,
			RetrievalType.RDocItemType docItemType,
			Object name,
			String keyWord,
			boolean regex){
		this.analyzerFactory=analyzerFactory;
		this.docItemType=docItemType;
		this.name=StringClass.getString(name).toUpperCase();
		this.value=keyWord;
		this.regex=regex;
		query=createQuery(docItemType, this.name, keyWord, this.regex);
	}
	
	/**
	 * 获取索引类型
	 * @return
	 */
	public RetrievalType.RDocItemType getDocItemType() {
		return docItemType;
	}

	/**
	 * 获取查询字段名称
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 获取查询内容
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 是否是正则表达式
	 * @return
	 */
	public boolean isRegex() {
		return regex;
	}
	
	/**
	 * 获取QueryWrap对象
	 * @return
	 */
	public QueryWrap getQueryWrap(){
		synchronized(this){
			if(queryWrap==null){
				queryWrap=new QueryWrap(this,query);
			}
		}
		return queryWrap;
	}
	
	/**
	 * 构造OR条件
	 * @param queryItem	需要连接的下一个QueryItem对象
	 * @return
	 */
	public QueryItem should(QueryItem queryItem){
		if(!(this.query instanceof BooleanQuery)){
			throw new RetrievalQueryException("第一个QueryItem对象请调用 public QueryItem should(BooleanClause.Occur occur,QueryItem queryItem) 方法");
		}
		Query theQuery=queryItem.getQueryWrap().getQuery();
		BooleanQuery query=new BooleanQuery();
		query.add(this.query,QueryItem.MUST);
		query.add(theQuery, QueryItem.SHOULD);
		this.query=query;
		return this;
	}
	
	/**
	 * 构造OR条件
	 * @param occur		当前QueryItem对象的判断条件
	 * @param queryItem	需要连接的下一个QueryItem对象
	 * @return
	 */
	public QueryItem should(BooleanClause.Occur occur,QueryItem queryItem){
		
		Query theQuery=queryItem.getQueryWrap().getQuery();
		
		BooleanQuery query=new BooleanQuery();
		query.add(this.query,occur);
		query.add(theQuery, QueryItem.SHOULD);
		this.query=query;
		
		return this;
	}
	
	/**
	 * 构造AND条件
	 * @param queryItem	需要连接的下一个QueryItem对象
	 * @return
	 */
	public QueryItem must(QueryItem queryItem){
		if(!(this.query instanceof BooleanQuery)){
			throw new RetrievalQueryException("第一个QueryItem对象请调用 public QueryItem should(BooleanClause.Occur occur,QueryItem queryItem) 方法");
		}
		
		Query theQuery=queryItem.getQueryWrap().getQuery();
		
		BooleanQuery query=new BooleanQuery();
		query.add(this.query,MUST);
		query.add(theQuery, QueryItem.MUST);
		this.query=query;
		
		return this;
	}
	
	/**
	 * 构造AND条件
	 * @param occur		当前QueryItem对象的判断条件
	 * @param queryItem	需要连接的下一个QueryItem对象
	 * @return
	 */
	public QueryItem must(BooleanClause.Occur occur,QueryItem queryItem){
		
		Query theQuery=queryItem.getQueryWrap().getQuery();
		
		BooleanQuery query=new BooleanQuery();
		query.add(this.query,occur);
		query.add(theQuery, QueryItem.MUST);
		this.query=query;
		
		return this;
	}
	
	/**
	 * 构造NOT条件
	 * @param queryItem	需要连接的下一个QueryItem对象
	 * @return
	 */
	public QueryItem mustNot(QueryItem queryItem){
		if(!(this.query instanceof BooleanQuery)){
			throw new RetrievalQueryException("第一个QueryItem对象请调用 public QueryItem should(BooleanClause.Occur occur,QueryItem queryItem) 方法");
		}
		
		Query theQuery=queryItem.getQueryWrap().getQuery();
		
		BooleanQuery query=new BooleanQuery();
		query.add(this.query,QueryItem.MUST);
		query.add(theQuery, QueryItem.MUST_NOT);
		this.query=query;
		
		return this;
	}
	
	/**
	 * 构造NOT条件
	 * @param occur		当前QueryItem对象的判断条件
	 * @param queryItem	需要连接的下一个QueryItem对象
	 * @return
	 */
	public QueryItem mustNot(BooleanClause.Occur occur,QueryItem queryItem){
		
		Query theQuery=queryItem.getQueryWrap().getQuery();
		
		BooleanQuery query=new BooleanQuery();
		query.add(this.query,occur);
		query.add(theQuery, QueryItem.MUST_NOT);
		this.query=query;
		
		return this;
	}
	
	/**
	 * 创建Query对象
	 * @param docItemType
	 * @param name
	 * @param keyWord
	 * @param regex
	 * @return
	 */
	private Query createQuery(RetrievalType.RDocItemType docItemType,
			String name,
			String keyWord,
			boolean regex){
		return createQuery(docItemType, name, keyWord, regex, null);
	}

	private Query createQuery(RetrievalType.RDocItemType docItemType, String name, String keyWord, boolean regex,Float score){
		Query query = null;
		if(regex){
			query = new WildcardQuery(new Term(name,keyWord));
		}else{
			if(docItemType==RetrievalType.RDocItemType.KEYWORD 
					|| docItemType==RetrievalType.RDocItemType.DATE 
					|| docItemType==RetrievalType.RDocItemType.NUMBER){
					query = new TermQuery(new Term(name,keyWord));
					if(score!=null)
						query.setBoost(score);
			}else if(docItemType==RetrievalType.RDocItemType.PROPERTY 
					|| docItemType==RetrievalType.RDocItemType.CONTENT){
				if(score==null)
					query=QueryUtil.createQuery(analyzerFactory,name, keyWord);
				else
					query=QueryUtil.createQuery(analyzerFactory,name, keyWord, score);
			}
		}
		return query;
	}
	
	public String toString(){
		return query.toString();
	}
}
