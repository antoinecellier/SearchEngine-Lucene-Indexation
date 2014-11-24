package searchEngine.index;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.lucene.document.Document;
import org.xml.sax.SAXException;

import org.apache.lucene.queryparser.classic.ParseException;
import searchEngine.clean.Cleaner;
import searhEngine.query.QueryWiki;

public class Main {

	public static void main(String[] args) {
		Date start = new Date();
		Cleaner cleaner = new Cleaner("xml/frwiki-test.xml", "xml/clean-fr-wiki.xml");
		try {
			cleaner.clean();
		} catch(Exception e){
		System.out.println(e.getMessage());
		}
		
		
		ParserCleanXml parserCleanXml = new ParserCleanXml("xml/clean-fr-wiki.xml");
			try {
				parserCleanXml.parse();
	
				ArrayList<Page>  pages = parserCleanXml.getPages();
				
				Index index = new Index("index");
				index.createIndex(pages);
				Date end = new Date();
				System.out.println("Time: "+(end.getTime()-start.getTime())+"ms");
				//search("doctorat AND Caucase");
				//search("doctorat OR vecteur");
				search("antoine");
			} 
			catch (ParserConfigurationException | SAXException | IOException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	// Pour tester l'index
	private static void search(String line) throws IOException, ParseException{
		
		 QueryWiki query = new QueryWiki("index",20);
		 ArrayList<Document> documents = query.search("title", line);
		 for (Document document : documents) {
			System.out.println(document.get("title"));
			// http://fr.wikipedia.org/wiki?curid=3
			System.out.println(document.get("id"));
		}
	}

}