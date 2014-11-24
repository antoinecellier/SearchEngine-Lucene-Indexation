package searchEngine.index;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

/**
 * This class is used for parse clean XML file generate with class Cleaner
 * Extract pages with their titles and entities
 * Use API SAX
 * @author amaury Lavieille - antoine Cellier
 */
public class ParserCleanXml {
	
	/**
	 * List of pages who are extracted
	 */
	private ArrayList<Page> listPages;
	
	/**
	 * Path of clean xml file
	 */
	private String pathXml;
	
	/**
	 * Builder parser
	 * @param pathXml
	 */
	public ParserCleanXml(String pathXml) {
		this.pathXml = pathXml;
		this.listPages = new ArrayList<Page>();
	}
	
	/**
	 * Start parse of xml file
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 */
	public void parse() throws ParserConfigurationException, SAXException, IOException{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();
		ParserHandler handler = new ParserHandler();
		saxParser.parse(this.pathXml, handler);
		this.listPages = handler.getListPages();
	}
	
	/**
	 * Get list of pages
	 * @return ArrayList<Page>
	 */
	public ArrayList<Page> getPages(){
		return this.listPages;
	}
}
