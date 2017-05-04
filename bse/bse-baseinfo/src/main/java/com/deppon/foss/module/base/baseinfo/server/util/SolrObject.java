package com.deppon.foss.module.base.baseinfo.server.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

/**
 * solrdocumentlist 对象直接转换成一个bean 集合
 * @ClassName: SolrObject 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author 310854-liuzhenhua
 * @date 2017-2-23 上午10:31:40 
 * 
 * @param <V>
 */
public class SolrObject<V> {

	@SuppressWarnings("unchecked")
	public V toBean(SolrDocument record, Class<?> clazz) {

		Object o = null;
		try {
			o = clazz.newInstance();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			Object value = record.get(field.getName());
			try {
				if(null != value){
					BeanUtils.setProperty(o, field.getName(), value);
				}
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return (V) o;
	}

	public List<V> toBeanList(SolrDocumentList records, Class<V> classz) {
		
		List<V> list = new ArrayList<V>();
		for (SolrDocument record : records) {
			list.add(toBean(record, classz));
		}
		return list;
	}

}