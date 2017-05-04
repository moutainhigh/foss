package com.deppon.foss.module.transfer.stock.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

public class IsLoseGroupEntity extends BaseEntity{
	
	
	/**
	* @fields serialVersionUID
	* @author 218381-foss-lijie
	* @update 2015年6月2日 下午2:12:55
	* @version V1.0
	*/
	
	private static final long serialVersionUID = 1L;
	//运单号
	private String waybillNo;
	//部门code
	private String orgCode;
	
	
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
}
