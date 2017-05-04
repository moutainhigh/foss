package com.deppon.pda.bdm.module.foss.upgrade.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
  * @ClassName ExpressPartSalesDeptEntity 
  * @Description TODO 快递点部与营业部映射
  * @author mt 
  * @date 2013-8-16 上午9:41:36
 */
public class ExpressPartSalesDeptEntity extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5162976418805809835L;
	/**
	 * 点部CODE
	 */
	private String partCode;
	/**
	 * 营业部CODE
	 */
	private String salesdeptCode;
	/**
	 * 数据版本
	 */
	private String versionNo;
	/**
	 * 操作标识
	 */
	private String operFlag;
	
	public String getPartCode() {
		return partCode;
	}
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	public String getSalesdeptCode() {
		return salesdeptCode;
	}
	public void setSalesdeptCode(String salesdeptCode) {
		this.salesdeptCode = salesdeptCode;
	}
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public String getOperFlag() {
		return operFlag;
	}
	public void setOperFlag(String operFlag) {
		this.operFlag = operFlag;
	}
}
