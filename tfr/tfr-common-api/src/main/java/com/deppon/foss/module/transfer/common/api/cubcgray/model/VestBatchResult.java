/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.transfer.common.api.cubcgray.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *<pre>
 *功能:批量判断归属服务返回对象
 *作者：132028
 *日期：2016年12月19日上午11:33:21
 *</pre>
 */
public class VestBatchResult implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1152363784844602090L;

	/**
	 * 归属系统编码
	 */
	private String vestSystemCode;
	
	/**
	 * 归属服务的对象
	 */
	private List<String> vestObject;

	
	/**  
	 * 获取归属系统编码  
	 * @return vestSystemCode 归属系统编码  
	 */
	public String getVestSystemCode() {
		return vestSystemCode;
	}

	
	/**  
	 * 设置归属系统编码  
	 * @param vestSystemCode 归属系统编码  
	 */
	public void setVestSystemCode(String vestSystemCode) {
		this.vestSystemCode = vestSystemCode;
	}

	
	/**  
	 * 获取归属服务的对象  
	 * @return vestObject 归属服务的对象  
	 */
	public List<String> getVestObject() {
		if(vestObject == null){
			vestObject = new ArrayList<String>();
		}
		return this.vestObject;
	}

	
	/**  
	 * 设置归属服务的对象  
	 * @param vestObject 归属服务的对象  
	 */
	public void setVestObject(List<String> vestObject) {
		this.vestObject = vestObject;
	}
}
