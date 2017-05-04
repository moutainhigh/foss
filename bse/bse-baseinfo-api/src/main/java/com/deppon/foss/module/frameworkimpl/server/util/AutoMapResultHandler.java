package com.deppon.foss.module.frameworkimpl.server.util;
import java.util.Map;

import org.apache.ibatis.executor.result.DefaultMapResultHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.ResultContext;
import org.jfree.util.Log;


/**
 * 自定义处理以值作为key和value的map集合，返回当前集合  只适用返回map集合
 * 
 * @author Foss-278328-hujinyang
 * @date 2015-12-16
 */
public class AutoMapResultHandler extends DefaultMapResultHandler {

	Map<String, String> retMap;
	String[] keyValName = {};
	
	public AutoMapResultHandler(String[] keyValColName, Map<String,String> map) {
		super(keyValColName[0]);
		this.keyValName = keyValColName;
		this.retMap = map;
	}
	
	@Override
	public void handleResult(ResultContext context) {
	   super.handleResult(context);
	   final Object value = context.getResultObject();
       final MetaObject mo = MetaObject.forObject(value);
	   Object original = mo.getOriginalObject();
	
	   if(original instanceof java.util.Map<?, ?>){
	    	Map<?,?> map =(Map<?,?>) mo.getOriginalObject();
	    	if(keyValName.length ==2){
	    		try {
					String key =keyValName[0].toUpperCase();
					String val =keyValName[1].toUpperCase();
					retMap.put(map.get(key).toString(),map.get(val).toString());
				} catch (Exception e) {
					Log.error(e);
				}
	    	}
	    }
	}
	
}
