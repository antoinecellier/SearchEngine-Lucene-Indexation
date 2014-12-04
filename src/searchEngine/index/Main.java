package searchEngine.index;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.lucene.document.Document;
import org.xml.sax.SAXException;
import org.apache.lucene.queryparser.classic.ParseException;

import com.sun.corba.se.impl.orb.ParserTable.TestAcceptor1;

import searchEngine.clean.Cleaner;
import searhEngine.query.QueryWiki;

public class Main {

	public static void main(String[] args) {
		//cleanXml();
		index();
	
	}
	
	// Pour tester l'index
	private static void search(String line) throws IOException, ParseException{
		
		 QueryWiki query = new QueryWiki("WebContent/index",20);
		 ArrayList<Document> documents = query.search(new String[]{"title","entities"}, line);
		 for (Document document : documents) {
			System.out.println(document.get("title"));
			// http://fr.wikipedia.org/wiki?curid=3
			System.out.println(document.get("id"));
		}
	}
	
	private static void cleanXml(){
		Date start = new Date();
		Cleaner cleaner = new Cleaner("/Users/amaury/Documents/frwiki-long.xml", "/Users/amaury/Documents/frwiki-long-clean2.xml");
		try {
			cleaner.clean();
		} catch(Exception e){
		System.out.println(e.getMessage());
		}
		Date end = new Date();
		System.out.println("Time: "+(end.getTime()-start.getTime())+"ms");
	}
	
	private static void index(){
		
		Date start = new Date();
		ParserCleanXml parserCleanXml = new ParserCleanXml("/Users/amaury/Documents/frwiki-long-clean2.xml");
			try {
				parserCleanXml.parse();
	
				ArrayList<Page>  pages = parserCleanXml.getPages();
				
				Index index = new Index("/Users/amaury/Documents/index");
				index.createIndex(pages);
				Date end = new Date();
				System.out.println("Time: "+(end.getTime()-start.getTime())+"ms");
				
				//search("doctorat AND Caucase");
				//search("doctorat OR vecteur");
				//search("vecteur OR doctorat");
			} 
			catch (ParserConfigurationException | SAXException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
