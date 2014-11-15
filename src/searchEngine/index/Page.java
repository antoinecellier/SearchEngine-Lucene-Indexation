package searchEngine.index;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Page {
	
	
	private String title;
	private String content;
	private ArrayList<String> entities;
	
	public Page()
	{
		this.title = "";
		this.content = "";
		this.entities = new ArrayList<String>();
	}
	
	public Page(String title, String content, ArrayList<String> entites){
		this.title = title;
		this.content = content;
		this.entities = entites;
	}
	
	
	public Page clone()
	{
		return new Page(this.title,this.content,this.entities);
	}
	
	
	public void extractEntite(){
		Pattern p = Pattern.compile("\\[\\[([^\\]]+)\\]\\]");
		Matcher m = p.matcher(this.content);
		
		while(m.find()) {
			String[] entitesGroup = m.group(1).split("\\|");
			entities.add(entitesGroup[0]);	    
		}
	}
	
	public String joinEntities(){
		String res = "";
		for (String entity : this.entities) {
			res += entity+" ";
		}
		return res;
	}
	
	public void concatContent(String content){
		this.content += content;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public ArrayList<String> getEntites(){
		return this.entities;
	}
	
	public Element toXml(Document document){
		Element pageElement = document.createElement("page");
		Element title = document.createElement("title");
		title.appendChild(document.createTextNode(this.title));
		pageElement.appendChild(title);
		if(this.entities.size() > 0) {
			Element entitiesElement = document.createElement("entities");
			for (String entity : entities) {
				Element entityElement = document.createElement("entity");
				entityElement.appendChild(document.createTextNode(entity));
				entitiesElement.appendChild(entityElement);
			}
			pageElement.appendChild(entitiesElement);
		}
		return pageElement;
	}
}
