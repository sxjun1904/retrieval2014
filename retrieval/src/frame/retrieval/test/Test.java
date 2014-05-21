package frame.retrieval.test;

import java.io.File;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Test {
	public static void testQueryBoost() throws Exception { 
		  File indexDir = new File("E:\\index_sxjun"); 
		  IndexWriter writer = new IndexWriter(FSDirectory.open(indexDir), new StandardAnalyzer(Version.LUCENE_CURRENT), true, IndexWriter.MaxFieldLength.LIMITED); 
		  /*Document doc1 = new Document(); 
		  Field f1 = new Field("title", "common2 common2 hello", Field.Store.NO, Field.Index.ANALYZED); 
		  Field f11 = new Field("contents", "common1 hello hello", Field.Store.NO, Field.Index.ANALYZED); 
		  Field f111 = new Field("userid", "1,2,3", Field.Store.YES, Field.Index.NOT_ANALYZED); 
		  doc1.add(f1); 
		  doc1.add(f11); 
		  doc1.add(f111); 
		  
		  writer.addDocument(doc1);*/
		 /* Document doc2 = new Document(); 
		  Field f2 = new Field("title", "common2 common2 hello", Field.Store.NO, Field.Index.ANALYZED); 
		  Field f22 = new Field("contents", "common2 common2 hello", Field.Store.NO, Field.Index.ANALYZED); 
		  doc2.add(f2); 
		  writer.addDocument(doc2); */
		 /*writer.commit();
		  writer.close();*/
		  IndexReader reader = IndexReader.open(FSDirectory.open(indexDir)); 
		  IndexSearcher searcher = new IndexSearcher(reader); 
//		  QueryParser parser = new QueryParser(Version.LUCENE_CURRENT, "contents", new StandardAnalyzer(Version.LUCENE_CURRENT)); 
//		  Query query = parser.parse("common1 common2");
//		  TopDocs docs = searcher.search(query, 10); 
		  
		  QueryParser parser = new QueryParser(Version.LUCENE_CURRENT, "userid", new StandardAnalyzer(Version.LUCENE_CURRENT));
		  parser.setAllowLeadingWildcard(true);
		  //Query myquery = new FuzzyQuery(new Term("userid","2,"));  
		  Query query = parser.parse("*2,*");
		/*  QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_33,new String[]{"sid","content"},new StandardAnalyzer(Version.LUCENE_CURRENT));
		  Query query = queryParser.parse("1,");*/
		  
		  TopDocs docs = searcher.search(query, 10); 
		  
//		  Query query = new FuzzyQuery(new Term("userid","1"));
//		  TopDocs docs=searcher.search(query,10);
		  
		  for (ScoreDoc doc : docs.scoreDocs) { 
		    System.out.println("docid : " + doc.doc + " score : " + doc.score); 
		  } 
		}
	public static void main(String[] args) throws Exception {
		testQueryBoost();
	}

}
