package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;

/**
 * 
  * @ClassName ExpressPartSalesDeptEntity 
  * @Description TODO 快递点部与营业部映射
  * @author mt 
  * @date 2013-8-16 上午9:41:36
 */
public class KdPartSalesDeptEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5162976418805809835L;
	
	/**
	 * 营业部NAME
	 */
	private String salesdeptName;
	
	/**
	 * 营业部CODE
	 */
	private String salesdeptCode;
	
	public String getSalesdeptName() {
		return salesdeptName;
	}
	public void setSalesdeptName(String salesdeptName) {
		this.salesdeptName = salesdeptName;
	}
	
	public String getSalesdeptCode() {
		return salesdeptCode;
	}
	public void setSalesdeptCode(String salesdeptCode) {
		this.salesdeptCode = salesdeptCode;
	}
}
