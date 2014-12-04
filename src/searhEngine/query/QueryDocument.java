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

public class QueryDocument {
	
	
	private String id;
	private String title;
	
	private List<Entry<String, Integer>> entities;
	
	
	public QueryDocument(String id, String title, String entitiesConcat){
		this.id = id;
		this.title = title;
		this.entities = sortByValue(mapEntitiesValue(new ArrayList<String>(Arrays.asList(entitiesConcat.split(";")))));
	}
	
	
	private Map<String, Integer> mapEntitiesValue(ArrayList<String> list){
		 Map<String, Integer> map = new HashMap<String, Integer>();
		 
			for (String temp : list) {
				Integer count = map.get(temp);
				map.put(temp, (count == null) ? 1 : count + 1);
			}
		return map;
	}
	
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
	
	
	public String getId(){
		return this.id;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public List<Entry<String, Integer>> getEntities(){
		return this.entities;
	}
}
