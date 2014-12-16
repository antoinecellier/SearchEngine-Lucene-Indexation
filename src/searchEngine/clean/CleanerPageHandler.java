package searchEngine.clean;

import searchEngine.index.Page;

import java.io.FileNotFoundException;
import java.io.IOException;
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
	 * Builder - initialize path of tag
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 */
	public CleanerPageHandler(Writer writer) {
		this.path = new ArrayList<String>();
		this.writer = writer;
	}
	
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
	 * if this is the end of tag page, extract entities of page and write page into clean file
	 */
	public void endElement(String uri, String localName, String qName){
		if(pathToString().equals("mediawiki/page/")) {
			tempPage.extractEntities();
			try {
				this.writer.write(tempPage.toXml());
			} catch (IOException e) {
				System.out.println("err");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.tempPage = null;
		}
		this.path.remove(this.path.size() - 1);
	}
	
	/**
	 * End of xml file
	 * Close write buffer
	 */
	public void endDocument(){
		try {
			this.writer.write("</mediawiki>");
			this.writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
