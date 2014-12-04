package searchEngine.index;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.lucene.document.Document;
import org.xml.sax.SAXException;
import org.apache.lucene.queryparser.classic.ParseException;

import searchEngine.clean.Cleaner;
import searhEngine.query.QueryDocument;
import searhEngine.query.QueryWiki;

public class Main {

	public static void main(String[] args) {
		//cleanXml();
		//index();
		try {
			search("antoine");
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	// Pour tester l'index
	@SuppressWarnings("unchecked")
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
	 public static void printMap(Map<String, Integer> map){
		 
			for (Map.Entry<String, Integer> entry : map.entrySet()) {
				System.out.println("Key : " + entry.getKey() + " Value : "
					+ entry.getValue());
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
