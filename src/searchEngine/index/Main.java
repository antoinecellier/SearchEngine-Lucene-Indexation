package searchEngine.index;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

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
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
