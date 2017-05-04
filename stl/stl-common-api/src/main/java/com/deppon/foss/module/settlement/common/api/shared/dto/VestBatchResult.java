package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @date 2016/12/30
 * @author 353654
 *
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
