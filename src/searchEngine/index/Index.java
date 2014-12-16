package searchEngine.index;


import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.fr.FrenchAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.xml.sax.SAXException;

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
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public void createIndex(String pathCleanXml) throws IOException, ParserConfigurationException, SAXException{
		Directory dir = FSDirectory.open(new File(this.pathFolderIndex));
		Analyzer analyzer = new FrenchAnalyzer();
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_4_10_2, analyzer);
		iwc.setOpenMode(OpenMode.CREATE);
		
		IndexWriter writer = new IndexWriter(dir, iwc);
		ParserCleanXml parserCleanXml = new ParserCleanXml(pathCleanXml, writer);
		parserCleanXml.parse();
		writer.close();
	}
}