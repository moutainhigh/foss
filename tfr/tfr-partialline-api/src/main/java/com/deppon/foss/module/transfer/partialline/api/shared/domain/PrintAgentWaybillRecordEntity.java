package com.deppon.foss.module.transfer.partialline.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class PrintAgentWaybillRecordEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	//运单号
	private String waybillNo;
	
	//代理单号
	private String agentWaybillNo;
	
	//代理公司编号
    private String agentCompanyCode;

    //代理公司名字
    private String agentCompanyName;
    //操作人编号
    private String operatorCode;
    
    //操作人姓名
    private String operatorName;

    //操作时间
    private Date operatTime;

    //部门编号
    private String orgCode;
    
    //部门名称
    private String orgName;
    
    //操作人部门电话	
    private String depTelephone;

    //新增时间
    private Date createTime;
    //打印次数
    private Integer printCount;
    //是否有效
    private String active;
    //绑定状态
    private String bundleState;
    //外发费用
    private BigDecimal frightFee;
    //打印类型
    private String printType;
    
    //流水号
    private String serialNo;
    //状态
    private String status;
    //订阅状态
    private String ispush;

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	
	public String getAgentWaybillNo() {
		return agentWaybillNo;
	}

	public void setAgentWaybillNo(String agentWaybillNo) {
		this.agentWaybillNo = agentWaybillNo;
	}

	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Date getOperatTime() {
		return operatTime;
	}

	public void setOperatTime(Date operatTime) {
		this.operatTime = operatTime;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getAgentCompanyCode() {
		return agentCompanyCode;
	}

	public void setAgentCompanyCode(String agentCompanyCode) {
		this.agentCompanyCode = agentCompanyCode;
	}

	public String getAgentCompanyName() {
		return agentCompanyName;
	}

	public void setAgentCompanyName(String agentCompanyName) {
		this.agentCompanyName = agentCompanyName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getPrintCount() {
		return printCount;
	}

	public void setPrintCount(Integer printCount) {
		this.printCount = printCount;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getBundleState() {
		return bundleState;
	}

	public void setBundleState(String bundleState) {
		this.bundleState = bundleState;
	}

	public BigDecimal getFrightFee() {
		return frightFee;
	}

	public void setFrightFee(BigDecimal frightFee) {
		this.frightFee = frightFee;
	}

	public String getPrintType() {
		return printType;
	}

	public void setPrintType(String printType) {
		this.printType = printType;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDepTelephone() {
		return depTelephone;
	}

	public void setDepTelephone(String depTelephone) {
		this.depTelephone = depTelephone;
	}

	public String getIspush() {
		return ispush;
	}
	
	public void setIspush(String ispush) {
		this.ispush = ispush;
	}
	
}
