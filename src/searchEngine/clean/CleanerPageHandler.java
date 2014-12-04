package searchEngine.clean;

import searchEngine.index.Page;

import java.io.BufferedWriter;
import java.io.Console;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;

/**
 * This class is used for parse the XML dump of Wikipedia database 
 * and extract title and entities of all pages
 * @author amaury Lavieille - antoine Cellier
 *
 */
public class CleanerPageHandler extends DefaultHandler{
	
	/**
	 * List of Page
	 */
	//private ArrayList<Page> listPages;
	
	/*
	 * Path of the current tags
	 */
	private ArrayList<String> path;
	
	/**
	 * Current page
	 */
	private Page tempPage;
	
	private Writer writer;
	
	/**
	 * Builder - initialize list of page and path of tag
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 */
	public CleanerPageHandler(Writer writer) {
		this.path = new ArrayList<String>();
		//this.listPages = new ArrayList<Page>();
		this.writer = writer;
	}
	
	/**
	 * Get listPages
	 * @return ArrayList<Page>
	 */
	/*public ArrayList<Page> getListPages(){
		return this.listPages;
	}*/
	
	
	
	/**
	 * Join all tags of ArrayList with character "/"
	 * @return String 
	 */
	private String pathToString(){
		String res = "";
		for (String value : this.path) {
			res += value+"/";
		}
		return res;
	}
	public void startDocument(){
		try {
			this.writer.write("<mediawiki>\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Start tag
	 * if path is mediawiki/page/ create new instance of pages
	 */
	public void startElement(String uri, String localName, String qName, Attributes attributes){
		this.path.add(qName);
		if(pathToString().equals("mediawiki/page/")) {
			this.tempPage = new Page();
		}
	}
	
	/**
	 * Content of tag
	 * Add title and content to current instance of pages if we are inner of tag page
	 */
	public void characters(char[] ch,int start, int length){
		
		if(pathToString().equals("mediawiki/page/title/")) {
			String content = new String(ch,start,length);
			content = content.replace("&", "&amp;");
			this.tempPage.setTitle(content);
		}
		if(pathToString().equals("mediawiki/page/id/")) {
			String content = new String(ch,start,length);
			this.tempPage.setId(content);
		}
		if(pathToString().equals("mediawiki/page/revision/text/")) {
			String content = new String(ch,start,length);
			content = content.replace("&", "&amp;");
			content = content.replace("<", "");
			content = content.replace(">", "");
			this.tempPage.concatContent(content);
		}
	} 
	
	/**
	 * End of tag
	 * if this is the end of tag page, add the current page to the list
	 */
	public void endElement(String uri, String localName, String qName){
		if(pathToString().equals("mediawiki/page/")) {
			tempPage.extractEntities();
			//this.listPages.add((Page) tempPage.clone());
			try {
				this.writer.write(tempPage.toXml());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.tempPage = null;
		}
		this.path.remove(this.path.size() - 1);
	}
	
	/**
	 * End of xml file
	 * Extract all entities of content
	 */
	public void endDocument(){
		/*for (Page page : this.listPages) {
			page.extractEntities();
		}*/
		try {
			this.writer.write("</mediawiki>");
			this.writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
