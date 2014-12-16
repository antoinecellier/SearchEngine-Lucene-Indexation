package searchEngine.index;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.lucene.index.IndexWriter;
import org.xml.sax.SAXException;

/**
 * This class is used for parse clean XML file generate with class Cleaner
 * Extract pages with their titles and entities
 * Use API SAX
 * @author amaury Lavieille - antoine Cellier
 */
public class ParserCleanXml {
	
	
	/**
	 * Path of clean xml file
	 */
	private String pathXml;
	
	/**
	 * Instance of indexWriter
	 */
	private IndexWriter indexWriter;
	
	/**
	 * Builder parser
	 * @param pathXml
	 */
	public ParserCleanXml(String pathXml, IndexWriter indexWriter) {
		this.pathXml = pathXml;
		this.indexWriter = indexWriter;
		//this.listPages = new ArrayList<Page>();
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
		ParserHandler handler = new ParserHandler(this.indexWriter);
		saxParser.parse(this.pathXml, handler);
	}
	
	
}
