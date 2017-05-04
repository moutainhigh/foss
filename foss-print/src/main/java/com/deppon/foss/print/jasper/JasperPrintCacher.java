package com.deppon.foss.print.jasper;

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JasperPrint;

/**
 * 
 * @author niujian
 *
 */
public class JasperPrintCacher {
	private static JasperPrintCacher instance = null;
	Map<String,Map<String,Object>> cacher = null;
	
	private JasperPrintCacher(){
		cacher = new HashMap<String,Map<String,Object>>();
	}
	
	public static JasperPrintCacher getInstance(){
		if(instance==null){
			instance = new JasperPrintCacher();
		}
		return instance;
	}
	
	/*
	public void put(String id,String prtkey, Object jp){
		if(cacher.containsKey(id)){
			Map jpmap = (Map)cacher.get(id);
			jpmap.put(prtkey, jp);
			
		}
		else {
			Map jpmap = new HashMap<String, JasperPrint>();
			jpmap.put(prtkey, jp);
			cacher.put(id,jpmap);
		}
	}

	public Object get(String id,String prtkey ){
		if(cacher.containsKey(id)){
			Map jpmap = ((Map)cacher.get(id));
			return jpmap.get(prtkey);
		}
		else {
			return null;
		}
	}
	
	public void remove(String id ){
		cacher.remove(id);
	}
	
	public void clearAllCacher(){
		cacher.clear();
	}*/
}
