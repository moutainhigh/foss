package com.deppon.foss.module.transfer.departure.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class AgingNodesLogEntity extends BaseEntity{

	private static final long serialVersionUID = -882376863035521695L;
	//修改人
	private String modifyName;
	//修改人code
	private String modifyCode;
	//修改类型
	private String modifyType;
	//修改时间
	private Date modifyTime;
	//修改前时间
	private Date beforeModifyTime;
	//修改后时间
	private Date afterModifyTime;
	//车次号
	private String  billNo;
	//关联单号
	private String relationbillNos;
	
	
	public String getRelationbillNos() {
		return relationbillNos;
	}
	public void setRelationbillNos(String relationbillNos) {
		this.relationbillNos = relationbillNos;
	}
	public String getModifyName() {
		return modifyName;
	}
	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}
	public String getModifyCode() {
		return modifyCode;
	}
	public void setModifyCode(String modifyCode) {
		this.modifyCode = modifyCode;
	}
	public String getModifyType() {
		return modifyType;
	}
	public void setModifyType(String modifyType) {
		this.modifyType = modifyType;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Date getBeforeModifyTime() {
		return beforeModifyTime;
	}
	public void setBeforeModifyTime(Date beforeModifyTime) {
		this.beforeModifyTime = beforeModifyTime;
	}
	public Date getAfterModifyTime() {
		return afterModifyTime;
	}
	public void setAfterModifyTime(Date afterModifyTime) {
		this.afterModifyTime = afterModifyTime;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	
	

}
