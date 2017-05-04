package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ErrorVolumeDeductionDto {
	private String id;
	private String errorId;
	private String waybillNo;
	//责任人code
	private String respEmpCode;
	//责任人
	private String respEmpName;
	//责任部门code
	private String respDeptCode;
	
	private String respDeptName;
	//重量
	private BigDecimal weight;
	//差错生成时间
	private Date delTime;
	
	private Date beginDate;
	
	private Date endDate;
	//标杆编码
	private List<String> unifiedCodes;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getErrorId() {
		return errorId;
	}
	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}
	public String getRespEmpCode() {
		return respEmpCode;
	}
	public void setRespEmpCode(String respEmpCode) {
		this.respEmpCode = respEmpCode;
	}
	public String getRespEmpName() {
		return respEmpName;
	}
	public void setRespEmpName(String respEmpName) {
		this.respEmpName = respEmpName;
	}
	public String getRespDeptCode() {
		return respDeptCode;
	}
	public void setRespDeptCode(String respDeptCode) {
		this.respDeptCode = respDeptCode;
	}

	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public Date getDelTime() {
		return delTime;
	}
	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getRespDeptName() {
		return respDeptName;
	}
	public void setRespDeptName(String respDeptName) {
		this.respDeptName = respDeptName;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public List<String> getUnifiedCodes() {
		return unifiedCodes;
	}
	public void setUnifiedCodes(List<String> unifiedCodes) {
		this.unifiedCodes = unifiedCodes;
	}
}
