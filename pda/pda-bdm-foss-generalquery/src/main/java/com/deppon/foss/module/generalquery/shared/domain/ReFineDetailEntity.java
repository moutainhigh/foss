package com.deppon.foss.module.generalquery.shared.domain;

import java.io.Serializable;

public class ReFineDetailEntity implements Serializable {
	
	  /**  
	 * @Fields serialVersionUID : TODO 
	 */
	private static final long serialVersionUID = 1L;
	  //差错类型
	  private String typeName;
	  //运单号
	  private String waybillNo;
	  //责任部门
	  private String orgName;
	  //调查内容
	  private String surveyContent;
	  //处理结果（负激励人）
	  private String empName;
	  //处理结果（金额）
	  private String money;
	  //差错id
	  private String errorId;	  
	  //差错处理人
	  private String operator;
	  //日期
	  private String operatorTime;

	  
	
	  
	  
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getSurveyContent() {
		return surveyContent;
	}
	public void setSurveyContent(String surveyContent) {
		this.surveyContent = surveyContent;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getErrorId() {
		return errorId;
	}
	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getOperatorTime() {
		return operatorTime;
	}
	public void setOperatorTime(String operatorTime) {
		this.operatorTime = operatorTime;
	}
	

	  
	  
	  
	  
	

}
