package searchEngine.index;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.xml.sax.SAXException;

public class Main {

	public static void main(String[] args) {
		/*Cleaner cleaner = new Cleaner("xml/frwiki-test.xml", "xml/clean-fr-wiki.xml");
		try {
			cleaner.clean();
		} catch(Exception e){
		System.out.println(e.getMessage());
		}*/
		
		ParserCleanXml parserCleanXml = new ParserCleanXml("xml/clean-fr-wiki.xml");
		try {
			parserCleanXml.parse();
			ArrayList<Page>  pages = parserCleanXml.getPages();
			Index index = new Index("index");
			index.createIndex(pages);
			//search("doctorat AND Caucase");
			//search("doctorat OR vecteur");
			search("antoine");
		} catch (ParserConfigurationException | SAXException | IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Pour tester l'index
	private static void search(String line) throws IOException, ParseException{
		 String field = "entities";
		 String[] fields = {"title", "entities"};
		 IndexReader reader = DirectoryReader.open(FSDirectory.open(new File("index")));
		 IndexSearcher searcher = new IndexSearcher(reader);
		 Analyzer analyzer = new StandardAnalyzer();
		 //QueryParser parser = new QueryParser(field, analyzer);
		 MultiFieldQueryParser parser = new MultiFieldQueryParser(fields, analyzer);
		 Query query = parser.parse(line);
		 System.out.println("Recherche pour: " + query.toString(field));
		 TopDocs results = searcher.search(query,100);
		 ScoreDoc[] hits = results.scoreDocs;
		 int numTotalHits = results.totalHits;
		 System.out.println(numTotalHits + " total trouvé");
		 int start = 0;
		 int end = Math.min(numTotalHits, 10);
		 for (int i = start; i < end; i++) {
			 Document doc = searcher.doc(hits[i].doc);
			 System.out.println("Titre du document :"+doc.get("title"));
		 }
		 reader.close();
	}

}
