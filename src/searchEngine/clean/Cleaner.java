package searchEngine.clean;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import searchEngine.index.Page;

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
    * Start cleaning and write the new xml file
    * @throws ParserConfigurationException
    * @throws SAXException
    * @throws IOException
    * @throws TransformerException
    */
   /*public void clean() throws ParserConfigurationException, SAXException, IOException, TransformerException{
	   ArrayList<Page> pages = parsePage();
	   pagesToXml(pages,this.pathOutput);
   }*/
   
   /**
    * Extract all pages who are defined in the xml file
    * Create an instance of class Page for each 
    * @return ArrayList<Page> list of all pages
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
		//return handler.getListPages();
   }
   
   /**
    * Write new xml file with useful informations of pages
    * @param ArrayList<Page> pages
    * @param String pathOutput
    * @throws ParserConfigurationException
    * @throws TransformerException
    */
   /*private void pagesToXml(ArrayList<Page> pages, String pathOutput) throws ParserConfigurationException, TransformerException{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("mediawiki");
		for (Page page : pages) {
			Element pageElement = page.toXml(doc);
			rootElement.appendChild(pageElement);
		}
		doc.appendChild(rootElement);
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(pathOutput));
		transformer.transform(source, result);
   }*/
}
