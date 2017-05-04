package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.util.Date;

/**
 * 合伙人推送运单信息日志
 * 
 * @author 272311-sangwenhao
 * @date 2016-2-17
 */
public class PushPartnerWaybillLogEntity {

	//id
	private String id;
	//运单号
	private String waybillNo;
	//推送内容
	private String operateContent;
	//操作人
	private String operator;
	//操作人编码
	private String operatorCode;
	//操作部门名称
	private String operateOrgName;
	//操作部门编码
	private String operateOrgCode;
	//操作时间
	private Date operateTime;
	//是否快递
	private String isExpress ;
	//是否有异常
	private String isError ;
	//异常信息
	private String errorMsg ;
	//备注
	private String remark;
	//推送这条运单创建时间
	private Date wayBillCreateTime;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getOperateContent() {
		return operateContent;
	}
	public void setOperateContent(String operateContent) {
		this.operateContent = operateContent;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getOperatorCode() {
		return operatorCode;
	}
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
	public String getOperateOrgName() {
		return operateOrgName;
	}
	public void setOperateOrgName(String operateOrgName) {
		this.operateOrgName = operateOrgName;
	}
	public String getOperateOrgCode() {
		return operateOrgCode;
	}
	public void setOperateOrgCode(String operateOrgCode) {
		this.operateOrgCode = operateOrgCode;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "PushPartnerWaybillLogEntity [id=" + id + ", waybillNo="
				+ waybillNo + ", operateContent=" + operateContent
				+ ", operator=" + operator + ", operatorCode=" + operatorCode
				+ ", operateOrgName=" + operateOrgName + ", operateOrgCode="
				+ operateOrgCode + ", operateTime=" + operateTime + ", remark="
				+ remark + "]";
	}
	public String getIsExpress() {
		return isExpress;
	}
	public void setIsExpress(String isExpress) {
		this.isExpress = isExpress;
	}
	public String getIsError() {
		return isError;
	}
	public void setIsError(String isError) {
		this.isError = isError;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public Date getWayBillCreateTime() {
		return wayBillCreateTime;
	}
	public void setWayBillCreateTime(Date wayBillCreateTime) {
		this.wayBillCreateTime = wayBillCreateTime;
	}
	
}
