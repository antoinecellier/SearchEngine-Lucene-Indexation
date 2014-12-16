package searhEngine.query;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.fr.FrenchAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

/**
 * Class for query index who are generated with lucence
 * @author amaury lavieille - antoine cellier
 *
 */
public class QueryWiki {
	
	
	/**
	 * Path of index's folder
	 */
	private String folderIndex;
	
	/**
	 * Number of result
	 */
	private int docBypage;
	

	
	/**
	 * Builder
	 * @param folderIndex path of index
	 * @param docByPage number of result
	 */
	public QueryWiki(String folderIndex, int docByPage){
		this.folderIndex = folderIndex;
		this.docBypage = docByPage;
	}
	
	/**
	 * Run search into index
	 * @param fields fields of search
	 * @param queryString query
	 * @return list of results
	 * @throws IOException
	 * @throws ParseException
	 */
	public ArrayList<Document> search(String[] fields, String queryString) throws IOException, ParseException{
		
		 ArrayList<Document> documents = new ArrayList<Document>();
		 IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(this.folderIndex)));
		 IndexSearcher searcher = new IndexSearcher(reader);
		 Analyzer analyzer = new FrenchAnalyzer();
		 
		 MultiFieldQueryParser parser = new MultiFieldQueryParser(fields, analyzer);
		 //QueryParser parser = new QueryParser(field, analyzer);
		 Query query = parser.parse(queryString);
		 TopDocs results = searcher.search(query,100);
		 //this.numTotalDoc = results.totalHits;
		
		 ScoreDoc[] hits = results.scoreDocs;
		
		 int start = 0;
		 int end = Math.min(results.totalHits, this.docBypage);
		
		 for (int i = start; i < end; i++) {
			 documents.add(searcher.doc(hits[i].doc));
		 }
		 
		 reader.close();
		 return documents;
	}


	
}
