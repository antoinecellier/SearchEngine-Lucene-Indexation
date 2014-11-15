package searchEngine.clean;


import java.io.File;
import java.io.IOException;
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


public class Cleaner {

   private String pathInput;
   private String pathOutput;
   
   
   public Cleaner(String pathInput, String pathOutput){
	   this.pathInput = pathInput;
	   this.pathOutput = pathOutput;
   }
   
   public void clean() throws ParserConfigurationException, SAXException, IOException, TransformerException{
	   ArrayList<Page> pages = parsePage();
	   pagesToXml(pages,this.pathOutput);
   }
   
   
   private ArrayList<Page> parsePage() throws ParserConfigurationException, SAXException, IOException{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();
		CleanerPageHandler handler = new CleanerPageHandler();
		saxParser.parse(this.pathInput, handler);
		System.out.println(handler.getListPages().size());
		return handler.getListPages();
   }
   
   private void pagesToXml(ArrayList<Page> pages, String pathOutput) throws ParserConfigurationException, TransformerException{
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
   }
}
