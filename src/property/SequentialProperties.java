package property;

import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class SequentialProperties extends Properties {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<Object, Object> linkMap = new LinkedHashMap<Object,Object>();

    public void clear(){
        linkMap.clear();
    }
    public boolean contains(Object value){
        return linkMap.containsValue(value);
    }
    public boolean containsKey(Object key){
        return linkMap.containsKey(key);
    }
    public boolean containsValue(Object value){
        return linkMap.containsValue(value);
    }
    public Enumeration elements(){
        throw new RuntimeException("Method elements is not supported in LinkedProperties class");
    }
    public Set entrySet(){
        return linkMap.entrySet();
    }
    public boolean equals(Object o){
        return linkMap.equals(o);
    }
    public Object get(Object key){
        return linkMap.get(key);
    }
    public String getProperty(String key) {
        Object oval = get(key); 
        if(oval==null)return null;
        return (oval instanceof String) ? (String)oval : null; 
    }
    public boolean isEmpty(){
        return linkMap.isEmpty();
    }
    public  Enumeration keys(){
        Set keys=linkMap.keySet();
        return Collections.enumeration(keys);
    }
    public Set keySet(){
        return linkMap.keySet();
    }

    public Object put(Object key, Object value){
        return linkMap.put(key, value);
    }
    public int size(){
        return linkMap.size();
    }
    public Collection values(){
        return linkMap.values();
    }
}
