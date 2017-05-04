package com.deppon.pda.bdm.module.foss.unload.shared.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.pda.bdm.module.core.shared.domain.DomainEntity;

/**
 * 托盘扫描明细类实体
 * @author wenwuneng
 * @date 2013-08-09
 * @version 1.0
 * @since
 */
public class BindingScanEntity extends DomainEntity implements Serializable{
	/** 
	 * @fields serialVersionUID 
	 */ 
	
	private static final long serialVersionUID = 1L;
	/**
	 * 运单号
	 */
	private String wl;
	/**
	 * 流水号集合
	 */
	private List<String> sn;
	
//	/**
//	 * 运单号
//	 */
//	private String wblCode;
//	/**
//	 * 流水号
//	 */
//	private String serialNo;
//	/**
//	 * 运单到达部门编码（预留）
//	 */
//	private String deptDestCode;
//	/**
//	 * 运单到达部门名称（预留）
//	 */
//	private String deptDestName;

	/**
	 *绑定唯一编码
	 */
	private String bindingNo;
	/**
	 *绑定任务时间
	 */
	private Date bindingDate;
	/**
	 *流水号
	 */
	private String serialNo;

//	public String getWblCode() {
//		return wblCode;
//	}
//	public void setWblCode(String wblCode) {
//		this.wblCode = wblCode;
//	}
//	public String getSerialNo() {
//		return serialNo;
//	}
//	public void setSerialNo(String serialNo) {
//		this.serialNo = serialNo;
//	}
//	public String getDeptDestCode() {
//		return deptDestCode;
//	}
//	public void setDeptDestCode(String deptDestCode) {
//		this.deptDestCode = deptDestCode;
//	}
//	public String getDeptDestName() {
//		return deptDestName;
//	}
//	public void setDeptDestName(String deptDestName) {
//		this.deptDestName = deptDestName;
//	}
	public String getBindingNo() {
		return bindingNo;
	}
	public void setBindingNo(String bindingNo) {
		this.bindingNo = bindingNo;
	}
	public Date getBindingDate() {
		return bindingDate;
	}
	public void setBindingDate(Date bindingDate) {
		this.bindingDate = bindingDate;
	}
	public String getWl() {
		return wl;
	}
	public void setWl(String wl) {
		this.wl = wl;
	}
	public List<String> getSn() {
		return sn;
	}
	public void setSn(List<String> sn) {
		this.sn = sn;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
	
}