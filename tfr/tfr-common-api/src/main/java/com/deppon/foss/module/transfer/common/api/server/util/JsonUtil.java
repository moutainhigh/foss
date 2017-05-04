package com.deppon.foss.module.transfer.common.api.server.util;



/**
* @Copyright ? 2014-12-20 shine.cn.Co.,Ltd
* @brms-server-core 上午11:58:56
* @Version 
* All right reserved.
*
*/

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
* @title: JsonUtil 
* @author： 206365
* @date： 2014-12-20 下午02:42:24
*/
public class JsonUtil{
	
	/**
	* @MethodName: pojoToJson 
	* @description : 序列化时写入类型信息，将pojo转化为json
	* @author：206365 
	* @date： 2014-12-20 下午02:49:04
	* @param <T>
	* @param pojo
	* @return String
	*/
	public static <T> String pojoToJson(T pojo){
		return JSON.toJSONString(pojo,SerializerFeature.WriteClassName);
	}
	
	/**
	* @MethodName: jsonToPojo 
	* @description : 反序列化，将json转化为pojo；更加适用于序列化的对象中存在循环引用的场景
	* @author：206365 
	* @date： 2014-12-20 下午02:49:13
	* @param <T>
	* @param json
	* @param T
	* @return T
	*/
	public static <T> T jsonToPojo(String json,Class<T> t){
		return (T) JSON.parseObject(json, t);
	}
	

	/**
	* @MethodName: jsonToPojo 
	* @description : 反序列化，将json转化为pojo；更加适用于序列化的对象中不存在循环引用的场景
	* @author：206365 
	* @date： 2014-12-20 下午03:04:58
	* @param <T>
	* @param json
	* @return T
	*/
	@SuppressWarnings("unchecked")
	public static <T> T jsonToPojo(String json){
		return (T) JSON.parse(json);
	}
}

