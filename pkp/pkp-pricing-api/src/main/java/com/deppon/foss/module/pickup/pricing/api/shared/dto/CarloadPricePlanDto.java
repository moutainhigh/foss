package com.deppon.foss.module.pickup.pricing.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CarloadPricePlanDto implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	 String id;   //方案ID
	 private String scenarioName;   //方案名称
	 private String active;           //是否激活（Y,N）
	 private String currentUsedVersion;//当前版本（是，否）
	 private Long version;          //版本号
	 private Date createTime;       //创建时间
	 private String createUser;       //创建人
	 private Date beginTime ;       //生效日期
	 private Date endTime ;         //截止日期
	 private Date modifyTime;       	//修改日期
	 private String modifyUser;       	//修改人
	 private String remark ;           	//备注
	 private String organizationID; //组织id
	 private String organizationCode; //组织编码
	 private String organizationName; //组织名称
	 private String invoiceType;      	//发票标记
	 private BigDecimal floatUp;       		//向上浮动值
	 private BigDecimal floatDown;        	//向下浮动值
	 
	 private BigDecimal otherCostParameter;// 其他成本参数
 
	 private BigDecimal packageFeeParameter;// 包装费参数

	 private BigDecimal weightParameter;// 重量参数

	 private BigDecimal carCostParameter;// 车价参数

	 private BigDecimal humanCostParameter;// 人力成本参数
	 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getScenarioName() {
		return scenarioName;
	}
	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	
	public String getCurrentUsedVersion() {
		return currentUsedVersion;
	}
	public void setCurrentUsedVersion(String currentUsedVersion) {
		this.currentUsedVersion = currentUsedVersion;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOrganizationID() {
		return organizationID;
	}
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}
	public String getOrganizationCode() {
		return organizationCode;
	}
	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	public BigDecimal getFloatUp() {
		return floatUp;
	}
	public void setFloatUp(BigDecimal floatUp) {
		this.floatUp = floatUp;
	}
	public BigDecimal getFloatDown() {
		return floatDown;
	}
	public void setFloatDown(BigDecimal floatDown) {
		this.floatDown = floatDown;
	}
	public BigDecimal getOtherCostParameter() {
		return otherCostParameter;
	}
	public void setOtherCostParameter(BigDecimal otherCostParameter) {
		this.otherCostParameter = otherCostParameter;
	}
	public BigDecimal getPackageFeeParameter() {
		return packageFeeParameter;
	}
	public void setPackageFeeParameter(BigDecimal packageFeeParameter) {
		this.packageFeeParameter = packageFeeParameter;
	}
	public BigDecimal getWeightParameter() {
		return weightParameter;
	}
	public void setWeightParameter(BigDecimal weightParameter) {
		this.weightParameter = weightParameter;
	}
	public BigDecimal getCarCostParameter() {
		return carCostParameter;
	}
	public void setCarCostParameter(BigDecimal carCostParameter) {
		this.carCostParameter = carCostParameter;
	}
	public BigDecimal getHumanCostParameter() {
		return humanCostParameter;
	}
	public void setHumanCostParameter(BigDecimal humanCostParameter) {
		this.humanCostParameter = humanCostParameter;
	}

	
	 
}
