package com.deppon.foss.module.transfer.lostwarning.api.shared.dto;

import java.io.Serializable;

/**
 * 运单打包 包装信息
 * 
 * 项目名称：tfr-lostwarning-api
 * 
 * 类名称：WayBillPkgInfoDto
 * 
 * 创建人：lyz 263072
 * 
 * 创建时间：2015-6-16 上午10:28:30
 * 
 * 版权所有：上海德邦物流有限公司
 */
public class WayBillPkgInfoDto implements Serializable{
	
	private static final long serialVersionUID = 669275311225917290L;
	
	private String packageNo;
	private String departOrgCode;//出发部门编码   建包
	private String departOrgName;//出发部门名称
	private String arriveOrgCode;//到达部门编码  解包
	private String arriveOrgName;//到达部门名称
	private String serialNo;//流水号
	
	
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getPackageNo() {
		return packageNo;
	}
	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}
	public String getDepartOrgCode() {
		return departOrgCode;
	}
	public void setDepartOrgCode(String departOrgCode) {
		this.departOrgCode = departOrgCode;
	}
	public String getDepartOrgName() {
		return departOrgName;
	}
	public void setDepartOrgName(String departOrgName) {
		this.departOrgName = departOrgName;
	}
	public String getArriveOrgCode() {
		return arriveOrgCode;
	}
	public void setArriveOrgCode(String arriveOrgCode) {
		this.arriveOrgCode = arriveOrgCode;
	}
	public String getArriveOrgName() {
		return arriveOrgName;
	}
	public void setArriveOrgName(String arriveOrgName) {
		this.arriveOrgName = arriveOrgName;
	}

}
