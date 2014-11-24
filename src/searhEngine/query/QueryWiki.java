package searhEngine.query;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

public class QueryWiki {
	
	private String folderIndex;
	
	private int docBypage;
	
	private int numTotalDoc;
	
	public QueryWiki(String folderIndex){
		this.folderIndex = folderIndex;
		this.docBypage = 0;
	}
	
	public QueryWiki(String folderIndex, int docByPage){
		this.folderIndex = folderIndex;
		this.docBypage = docByPage;
	}
	
	
	public ArrayList<Document> search(String field, String queryString) throws IOException, ParseException{
		
		 ArrayList<Document> documents = new ArrayList<Document>();
		 IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(this.folderIndex)));
		 IndexSearcher searcher = new IndexSearcher(reader);
		 Analyzer analyzer = new StandardAnalyzer();
		 QueryParser parser = new QueryParser(field, analyzer);
		 Query query = parser.parse(queryString);
		 TopDocs results = searcher.search(query,100);
		 this.numTotalDoc = results.totalHits;
		
		 ScoreDoc[] hits = results.scoreDocs;
		
		 int start = 0;
		 int end = Math.min(this.numTotalDoc, this.docBypage);
		
		 for (int i = start; i < end; i++) {
			 documents.add(searcher.doc(hits[i].doc));
		 }
		 
		 reader.close();
		 return documents;
	}
	
	public ArrayList<Document> search(String field, String queryString, int start) throws IOException, ParseException{
		
		 ArrayList<Document> documents = new ArrayList<Document>();
		 IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(this.folderIndex)));
		 IndexSearcher searcher = new IndexSearcher(reader);
		 Analyzer analyzer = new StandardAnalyzer();
		 QueryParser parser = new QueryParser(field, analyzer);
		 Query query =  parser.parse(queryString);
		 TopDocs results = searcher.search(query,100);
		 this.numTotalDoc = results.totalHits;
		
		 ScoreDoc[] hits = results.scoreDocs;
		
		 int end = Math.min(this.numTotalDoc, this.docBypage);
		
		 for (int i = start; i < end; i++) {
			 documents.add(searcher.doc(hits[i].doc));
		 }
		 
		 reader.close();
		 return documents;
	}
	
	public int getNumTotalDoc(){
		return this.numTotalDoc;
	}
	
}
