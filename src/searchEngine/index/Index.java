package searchEngine.index;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.fr.FrenchAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * This class build index with library Lucene
 * @author amaury Lavieille - antoinee Cellier
 *
 */
public class Index {
	
	/**
	 * Path of folder where index will be written
	 */
	private String pathFolderIndex;
	
	/**
	 * Builder 
	 * @param String pathFolderIndex
	 */
	public Index(String pathFolderIndex){
		this.pathFolderIndex = pathFolderIndex;
	}
	
	
	/**
	 * Create index of pages
	 * @param pages
	 * @throws IOException 
	 */
	public void createIndex(ArrayList<Page> pages) throws IOException{
		Directory dir = FSDirectory.open(new File(this.pathFolderIndex));
		Analyzer analyzer = new FrenchAnalyzer();
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_4_10_2, analyzer);
		iwc.setOpenMode(OpenMode.CREATE);
		IndexWriter writer = new IndexWriter(dir, iwc);
		for (Page page : pages) {
			Document document= createDocument(page);
			writer.addDocument(document);
		}
		 writer.close();
	}
	
	/**
	 * Create a document from a page
	 * Document is of Class of Lucene who can be add to an index
	 * @param page
	 * @return Document
	 */
	private Document createDocument(Page page){
		Document doc = new Document();
		doc.add(new StringField("id", page.getId(), Field.Store.YES));
		Field title = new TextField("title", page.getTitle(),Field.Store.YES);
		title.setBoost(2);
		doc.add(title);
		doc.add(new TextField("entities", page.joinEntities(),  Field.Store.YES));
		return doc;
	}
}
