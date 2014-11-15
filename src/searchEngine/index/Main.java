package searchEngine.index;

import searchEngine.clean.Cleaner;


public class Main {

	public static void main(String[] args) {
		Cleaner cleaner = new Cleaner("xml/frwiki-test.xml", "xml/output.xml");
		try {
			cleaner.clean();
		} catch(Exception e){
		System.out.println(e.getMessage());
		}
	}

}
