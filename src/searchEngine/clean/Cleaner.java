package searchEngine.clean;


import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;


/**
 * This class is used for convert the xml dump of Wikipedia database
 * Clean all useless information of pages and save the other (title, entities) into xml
 * For parse xml file use api SAX
 * @author amaury Lavieille - Antoine Cellier
 *
 */
public class Cleaner {
 
   /**
    * Path of xml input
    */
   private String pathInput;
   
   /**
    * Path of xml output
    */
   private String pathOutput;
   
   
   /**
    * initialize path of input and output xml
    * @param String pathInput
    * @param String pathOutput
    */
   public Cleaner(String pathInput, String pathOutput){
	   this.pathInput = pathInput;
	   this.pathOutput = pathOutput;
   }
   
 
   /**
    * Extract all pages who are defined in the xml file
    * Create an instance of class Page for each and write it in stream
    * @throws ParserConfigurationException
    * @throws SAXException
    * @throws IOException
    */
   public void clean() throws ParserConfigurationException, SAXException, IOException{
	   Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.pathOutput), "utf-8"));
	   SAXParserFactory factory = SAXParserFactory.newInstance();
	   SAXParser saxParser = factory.newSAXParser();
		
		CleanerPageHandler handler = new CleanerPageHandler(writer);
		saxParser.parse(this.pathInput, handler);
   }
   
 
}
