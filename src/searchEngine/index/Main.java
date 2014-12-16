package searchEngine.index;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.lucene.document.Document;
import org.xml.sax.SAXException;
import org.apache.lucene.queryparser.classic.ParseException;

import searchEngine.clean.Cleaner;
import searhEngine.query.QueryDocument;
import searhEngine.query.QueryWiki;

public class Main {

	public static void main(String[] args) {
		//cleanXml("/Users/amaury/Documents/frwiki-long.xml","/Users/amaury/Documents/frwiki-long-clean2.xml");
		//cleanXml("xml/frwiki-test.xml","/Users/amaury/Documents/frwiki-short-clean.xml");
		//index("/Users/amaury/Documents/frwiki-entier-clean2.xml","/Users/amaury/Documents/index");
		index("/Users/amaury/Documents/frwiki-long-clean2.xml","/Users/amaury/Documents/index");
		/*try {
			search("antoine");
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	
	}
	
	
	/**
	 * Test search into index
	 * @param line query
	 * @throws IOException
	 * @throws ParseException
	 */
	private static void search(String line) throws IOException, ParseException{
		 ArrayList<QueryDocument> resDocuments = new ArrayList<QueryDocument>();
		 QueryWiki query = new QueryWiki("WebContent/index",20);
		 ArrayList<Document> documents = query.search(new String[]{"title","entities"}, line);
		 for (Document document : documents) {
			QueryDocument doc = new QueryDocument(document.get("id"), document.get("title"), document.get("entities"));
			resDocuments.add(doc);
		}
		 System.out.println(resDocuments.get(0).getEntities());
			
	}	
 
	/**
	 * Run clean XML
	 * @param pathXml
	 * @param pathCleanXMl
	 */
	private static void cleanXml(String pathXml, String pathCleanXMl){
		Date start = new Date();
		Cleaner cleaner = new Cleaner(pathXml, pathCleanXMl);
		try {
			cleaner.clean();
		} catch(Exception e){
			System.out.println("erreur");
			System.out.println(e.getCause());
			System.out.println(e.getStackTrace());
			System.out.println(e.getClass().getSimpleName());
			System.out.println(e.getStackTrace()[2].getLineNumber());
			
		}
		Date end = new Date();
		System.out.println("Time: "+(end.getTime()-start.getTime())+"ms");
	}
	
	/**
	 * Run create index
	 * @param pathCleanXml
	 * @param pathOutput
	 */
	private static void index(String pathCleanXml, String pathOutput){
		
		Date start = new Date();
		Index index = new Index(pathOutput);
		try {
			index.createIndex(pathCleanXml);
		} catch (IOException | ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		}
		Date end = new Date();
		System.out.println("Time: "+(end.getTime()-start.getTime())+"ms");
	}

}
