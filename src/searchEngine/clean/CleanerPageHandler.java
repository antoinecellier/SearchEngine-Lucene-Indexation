package searchEngine.clean;

import searchEngine.index.Page;

import java.util.ArrayList;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;

public class CleanerPageHandler extends DefaultHandler{
	
	private ArrayList<Page> listPages;
	
	private ArrayList<String> path;
	
	private Page tempPage;
	
	public CleanerPageHandler(){
		this.path = new ArrayList<String>();
		this.listPages = new ArrayList<Page>();
	}
	
	public ArrayList<Page> getListPages(){
		return this.listPages;
	}
	
	
	private String pathToString(){
		String res = "";
		for (String value : this.path) {
			res += value+"/";
		}
		return res;
	}
	
	public void startElement(String uri, String localName, String qName, Attributes attributes){
		this.path.add(qName);
		if(pathToString().equals("mediawiki/page/")) {
			this.tempPage = new Page();
		}
	}
	
	public void characters(char[] ch,int start, int length){
		
		if(pathToString().equals("mediawiki/page/title/")) {
			String content = new String(ch,start,length);
			this.tempPage.setTitle(content);
		}
		if(pathToString().equals("mediawiki/page/revision/text/")) {
			String content = new String(ch,start,length);
			this.tempPage.concatContent(content);
		}
	} 
	
	public void endElement(String uri, String localName, String qName){
		if(pathToString().equals("mediawiki/page/")) {
			this.listPages.add((Page) tempPage.clone());
			this.tempPage = null;
		}
		this.path.remove(this.path.size() - 1);
	}
	
	public void endDocument(){
		for (Page page : this.listPages) {
			page.extractEntite();
		}
	}
	

}
