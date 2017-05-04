package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;
/**
 * 
 * 返回是否在圈内，主客户编码
 * @author 308861 
 * @date 2017-1-10 上午9:26:12
 * @since
 * @version
 */
public class IsCustomerCircleDto implements Serializable{
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 是否在客户圈内
	 */
	private String isCustCircle;
	/**
	 *主客户编码 
	 */
	private String mainCode;
	
	public String getIsCustCircle() {
		return isCustCircle;
	}
	public void setIsCustCircle(String isCustCircle) {
		this.isCustCircle = isCustCircle;
	}
	public String getMainCode() {
		return mainCode;
	}
	public void setMainCode(String mainCode) {
		this.mainCode = mainCode;
	}
}
