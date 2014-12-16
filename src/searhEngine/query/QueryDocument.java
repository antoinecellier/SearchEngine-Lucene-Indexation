package searhEngine.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * Class who represent a result of query on lucene index
 * @author amaury lavieille - antoine celier
 *
 */
public class QueryDocument {
	
	/**
	 * Document's id
	 */
	private String id;
	
	/**
	 * Document's title
	 */
	private String title;
	
	
	/**
	 * List of document's entities
	 */
	private List<Entry<String, Integer>> entities;
	
	
	/**
	 * Builder
	 * @param id Document's id
	 * @param title Document's title
	 * @param entitiesConcat Document's entities
	 */
	public QueryDocument(String id, String title, String entitiesConcat){
		this.id = id;
		this.title = title;
		this.entities = sortByValue(mapEntitiesValue(new ArrayList<String>(Arrays.asList(entitiesConcat.split(";")))));
	}
	
	/**
	 * Count occurence number of entity
	 * @param list 
	 * @return
	 */
	private Map<String, Integer> mapEntitiesValue(ArrayList<String> list){
		 Map<String, Integer> map = new HashMap<String, Integer>();
		 
			for (String temp : list) {
				Integer count = map.get(temp);
				map.put(temp, (count == null) ? 1 : count + 1);
			}
		return map;
	}
	
	/**
	 * Sort entities by number of occurence
	 * @param map
	 * @return
	 */
	private List<Entry<String, Integer>> sortByValue( Map<String, Integer> map)
	{
		Set<Entry<String, Integer>> set = map.entrySet();
        List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
        Collections.sort( list, new Comparator<Map.Entry<String, Integer>>()
        {
            public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
            {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        } );
        return list;
	}
	
	
	/**
	 * Getter id
	 * @return document's id
	 */
	public String getId(){
		return this.id;
	}
	
	/**
	 * Getter title
	 * @return document's title
	 */
	public String getTitle(){
		return this.title;
	}
	
	/**
	 * Getter entities
	 * @return document's entities
	 */
	public List<Entry<String, Integer>> getEntities(){
		return this.entities;
	}
}
