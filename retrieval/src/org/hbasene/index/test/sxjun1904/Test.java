package org.hbasene.index.test.sxjun1904;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;
import org.hbasene.index.HBaseIndexReader;
import org.hbasene.index.HBaseIndexStore;
import org.hbasene.index.HBaseIndexWriter;


public class Test{
      static final String indexName = "myindex";
      static final String dataName = "t1";
      public static void main(String[] args) throws IOException {
        
        try{
       Configuration conf = HBaseConfiguration.create(); //hbase-site.xml in the classpath
        conf.set("hbase.rootdir", "hdfs://hadoop0:9000/hbase");
        conf.set("hbase.zookeeper.quorum", "hadoop0");
         HTablePool tablePool = new HTablePool(conf, 10);
         HBaseIndexStore.createLuceneIndexTable(indexName, conf, true);
        //Write
        HBaseIndexStore hbaseIndex = new HBaseIndexStore(tablePool, conf, indexName);
        HBaseIndexWriter writer = new HBaseIndexWriter(hbaseIndex, "content"); //Name of the primary key field.

        getDocument(writer);
        writer.close();
        
        //Read/Search
        IndexReader reader = new HBaseIndexReader(tablePool, indexName, "content");
        IndexSearcher searcher = new IndexSearcher(reader);
        Term term = new Term("content", "dog");
        TermQuery termQuery = new TermQuery(term);
        TopDocs docs = searcher.search(termQuery, 3);
        for (ScoreDoc scoreDoc : docs.scoreDocs) {
			int docSn = scoreDoc.doc;
			Document doc = searcher.doc(docSn); 
			System.out.println(doc.get("content"));
		}
        searcher.close();
        }catch(IOException e){
            e.printStackTrace();
            throw e;
        }
      }


      private static void getDocument(HBaseIndexWriter writer) throws IOException{
        Document doc = new Document();
        doc.add(new Field("content", "some content some dog", Field.Store.YES,
            Field.Index.ANALYZED));
        writer.addDocument(doc, new StandardAnalyzer(Version.LUCENE_30));
        doc = new Document();
        doc.add(new Field("content", "some id", Field.Store.NO, Field.Index.ANALYZED));
        writer.addDocument(doc, new StandardAnalyzer(Version.LUCENE_30));
        doc = new Document();
        doc.add(new Field("content", "hot dog", Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.WITH_POSITIONS));
        writer.addDocument(doc, new StandardAnalyzer(Version.LUCENE_30));
      }
      
      private static void getDocumentFromHTable(HTablePool tablePool, HBaseIndexWriter writer) throws IOException  {
          Document doc = new Document();
          Scan scan = new Scan();
          HTableInterface htable = tablePool.getTable(dataName);
          ResultScanner results = htable.getScanner(scan);
          Result row;
          while((row = results.next()) != null){
              doc = new Document();
              String value = new String(row.getValue("test".getBytes(), null));
              String url = value.split("\"")[2];
              doc.add(new Field("content", url, Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.WITH_OFFSETS));
              writer.addDocument(doc, new StandardAnalyzer(Version.LUCENE_30));
          }
        }
      
}