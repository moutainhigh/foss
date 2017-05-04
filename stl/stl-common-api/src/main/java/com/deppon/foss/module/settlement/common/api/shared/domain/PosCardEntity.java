package com.deppon.foss.module.settlement.common.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * POS刷卡数据实体
 * @author 269052
 */
public class PosCardEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 交易流水号
	 */
	private String tradeSerialNo;

	/**
	 * 流水号金额
	 */
	private BigDecimal serialAmount;

	/**
	 * 刷卡部门名称
	 */
	private String cardDeptName;

	/**
	 * 刷卡部门编码
	 */
	private String cardDeptCode;

	/**
	 * 刷卡部门标杆编码
	 */
	private String cardDeptBMCode;

	/**
	 * 已使用金额
	 */
	private BigDecimal usedAmount;

	/**
	 * 未使用金额
	 */
	private BigDecimal unUsedAmount;

	/**
	 * 刷卡时间
	 */
	private Date cardTime;

	/**
	 * 所属模块
	 */
	private String belongModule;

	/**
	 * 所属营业区
	 */
	private String businessAreaName;

	/**
	 * 所属营业区编码
	 */
	private String businessAreaCode;

	/**
	 * 所属营业区标杆编码
	 */
	private String businessAreaBMCode;

	/**
	 * 所属大区
	 */
	private String belongRegionName;

	/**
	 * 所属大区编码
	 */
	private String belongRegionCode;

	/**
	 * 所属大区标杆编码 
	 */
	private String belongRegionBMCode;

	/**
	 * 所属营业部
	 */
	private String businessDeptName;

	/**
	 * 所属营业部
	 */
	private String businessDeptCode;

	/**
	 * 所属营业部标杆编码
	 */
	private String businessDeptBMCode;

	/**
	 * 所属财务部
	 */
	private String financeDeptName;

	/**
	 * 所属财务部编码
	 */
	private String financeDeptCode;

	/**
	 * 所属财务部标杆编码
	 */
	private String financeDeptBMCode;

	/**
	 * 创建人编码
	 */
	private String createUserCode;
	
	private String createUserName;
	
	private Date createTime;
	/**
	 * 修改人编码
	 */
	private String modifyUserCode;
	
	/**
	 * 备注
	 */
	private String notes;

	/**
	 * 版本
	 */
	private int version;

	/**
	 * 是否为司机:
	 * true:是
	 * false:否
	 */
	private String isDriver;

	/**
	 * 是否是快递
	 * true:是
	 * false:否
	 */
	private String isKd;
	
	/**
	 * 假如是快递的话，判断是开单还是签收
	 * true : 开单
	 * false: 签收
	 */
	private String isbilling;
	
	/**
	 * 明细集合
	 */
	private List<PosCardDetailEntity> posCardDetailEntitys;

	/**
	 * 封装的明细字段在这里在这里是为了方便在文件下载的时候使用，
	 * 如要使用明细字段数据请封装明细集合
	 */
	/**
	 * 单据类型
	 */
	private String invoiceType;

	/**
	 * 单据号
	 */
	private String invoiceNo;

	/**
	 * 总金额
	 */
	private BigDecimal amount;

	/**
	 * 占用金额
	 */
	private BigDecimal occupateAmount;

	/**
	 * 未核销金额
	 */
	private BigDecimal unVerifyAmount;
	/**
	 * 	是否有效
	 */
	private String active;
	
	/**
	 * 查询字段
	 */
	private String fields;


	/*------------add by panshiqi------------*/
	/**
	 * 记账日期
	 */
	private Date accountTime;
	
	/**
	 * 冻结状态
	 * 0或空表示未冻结 ，1：全部冻结 ， 2：部分冻结  
	 */
	private short frozenStatus;
	
	/**
	 * 冻结时间
	 */
	private Date frozenTime;
	
	/**
	 * 冻结金额
	 */
	private BigDecimal frozenAmount;
	/********getter/settet******/
	public String getTradeSerialNo() {
		return tradeSerialNo;
	}

	public void setTradeSerialNo(String tradeSerialNo) {
		this.tradeSerialNo = tradeSerialNo;
	}

	public BigDecimal getSerialAmount() {
		return serialAmount;
	}

	public void setSerialAmount(BigDecimal serialAmount) {
		this.serialAmount = serialAmount;
	}

	public String getCardDeptName() {
		return cardDeptName;
	}

	public void setCardDeptName(String cardDeptName) {
		this.cardDeptName = cardDeptName;
	}

	public String getCardDeptCode() {
		return cardDeptCode;
	}

	public void setCardDeptCode(String cardDeptCode) {
		this.cardDeptCode = cardDeptCode;
	}

	public BigDecimal getUsedAmount() {
		return usedAmount;
	}

	public void setUsedAmount(BigDecimal usedAmount) {
		this.usedAmount = usedAmount;
	}

	public BigDecimal getUnUsedAmount() {
		return unUsedAmount;
	}

	public void setUnUsedAmount(BigDecimal unUsedAmount) {
		this.unUsedAmount = unUsedAmount;
	}

	public Date getCardTime() {
		return cardTime;
	}

	public void setCardTime(Date cardTime) {
		this.cardTime = cardTime;
	}

	public String getBelongModule() {
		return belongModule;
	}

	public void setBelongModule(String belongModule) {
		this.belongModule = belongModule;
	}

	public String getBusinessAreaName() {
		return businessAreaName;
	}

	public void setBusinessAreaName(String businessAreaName) {
		this.businessAreaName = businessAreaName;
	}

	public String getBusinessAreaCode() {
		return businessAreaCode;
	}

	public void setBusinessAreaCode(String businessAreaCode) {
		this.businessAreaCode = businessAreaCode;
	}

	public String getBelongRegionName() {
		return belongRegionName;
	}

	public void setBelongRegionName(String belongRegionName) {
		this.belongRegionName = belongRegionName;
	}

	public String getBelongRegionCode() {
		return belongRegionCode;
	}

	public void setBelongRegionCode(String belongRegionCode) {
		this.belongRegionCode = belongRegionCode;
	}

	public String getFinanceDeptName() {
		return financeDeptName;
	}

	public void setFinanceDeptName(String financeDeptName) {
		this.financeDeptName = financeDeptName;
	}

	public String getFinanceDeptCode() {
		return financeDeptCode;
	}

	public void setFinanceDeptCode(String financeDeptCode) {
		this.financeDeptCode = financeDeptCode;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getModifyUserCode() {
		return modifyUserCode;
	}

	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	/*public Date getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}*/

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getOccupateAmount() {
		return occupateAmount;
	}

	public void setOccupateAmount(BigDecimal occupateAmount) {
		this.occupateAmount = occupateAmount;
	}

	public BigDecimal getUnVerifyAmount() {
		return unVerifyAmount;
	}

	public void setUnVerifyAmount(BigDecimal unVerifyAmount) {
		this.unVerifyAmount = unVerifyAmount;
	}

	public String getBusinessDeptName() {
		return businessDeptName;
	}

	public void setBusinessDeptName(String businessDeptName) {
		this.businessDeptName = businessDeptName;
	}

	public String getBusinessDeptCode() {
		return businessDeptCode;
	}

	public void setBusinessDeptCode(String businessDeptCode) {
		this.businessDeptCode = businessDeptCode;
	}

	public List<PosCardDetailEntity> getPosCardDetailEntitys() {
		return posCardDetailEntitys;
	}

	public void setPosCardDetailEntitys(List<PosCardDetailEntity> posCardDetailEntitys) {
		this.posCardDetailEntitys = posCardDetailEntitys;
	}

	public String getCardDeptBMCode() {
		return cardDeptBMCode;
	}

	public void setCardDeptBMCode(String cardDeptBMCode) {
		this.cardDeptBMCode = cardDeptBMCode;
	}

	public String getBusinessAreaBMCode() {
		return businessAreaBMCode;
	}

	public void setBusinessAreaBMCode(String businessAreaBMCode) {
		this.businessAreaBMCode = businessAreaBMCode;
	}

	public String getBelongRegionBMCode() {
		return belongRegionBMCode;
	}

	public void setBelongRegionBMCode(String belongRegionBMCode) {
		this.belongRegionBMCode = belongRegionBMCode;
	}

	public String getFinanceDeptBMCode() {
		return financeDeptBMCode;
	}

	public void setFinanceDeptBMCode(String financeDeptBMCode) {
		this.financeDeptBMCode = financeDeptBMCode;
	}

	public String getBusinessDeptBMCode() {
		return businessDeptBMCode;
	}

	public void setBusinessDeptBMCode(String businessDeptBMCode) {
		this.businessDeptBMCode = businessDeptBMCode;
	}

	public String getIsDriver() {
		return isDriver;
	}

	public void setIsDriver(String isDriver) {
		this.isDriver = isDriver;
	}

	public String getIsKd() {
		return isKd;
	}

	public void setIsKd(String isKd) {
		this.isKd = isKd;
	}

	/**  
	 * 获取 记账日期  
	 * @return accountTime 记账日期  
	 */
	public Date getAccountTime() {
		return accountTime;
	}

	/**  
	 * 设置 记账日期  
	 * @param accountTime 记账日期  
	 */
	public void setAccountTime(Date accountTime) {
		this.accountTime = accountTime;
	}
	
	
	public String getIsbilling() {
		return isbilling;
	}

	public void setIsbilling(String isbilling) {
		this.isbilling = isbilling;
	}
	
	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	
	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}
	
	public short getFrozenStatus() {
		return frozenStatus;
	}

	public void setFrozenStatus(short frozenStatus) {
		this.frozenStatus = frozenStatus;
	}

	public Date getFrozenTime() {
		return frozenTime;
	}

	public void setFrozenTime(Date frozenTime) {
		this.frozenTime = frozenTime;
	}

	public BigDecimal getFrozenAmount() {
		return frozenAmount;
	}

	public void setFrozenAmount(BigDecimal frozenAmount) {
		this.frozenAmount = frozenAmount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	@Override
	public String toString() {
		return "PosCardEntity [tradeSerialNo=" + tradeSerialNo
				+ ", serialAmount=" + serialAmount + ", cardDeptName="
				+ cardDeptName + ", cardDeptCode=" + cardDeptCode
				+ ", cardDeptBMCode=" + cardDeptBMCode + ", usedAmount="
				+ usedAmount + ", unUsedAmount=" + unUsedAmount + ", cardTime="
				+ cardTime + ", belongModule=" + belongModule
				+ ", businessAreaName=" + businessAreaName
				+ ", businessAreaCode=" + businessAreaCode
				+ ", businessAreaBMCode=" + businessAreaBMCode
				+ ", belongRegionName=" + belongRegionName
				+ ", belongRegionCode=" + belongRegionCode
				+ ", belongRegionBMCode=" + belongRegionBMCode
				+ ", businessDeptName=" + businessDeptName
				+ ", businessDeptCode=" + businessDeptCode
				+ ", businessDeptBMCode=" + businessDeptBMCode
				+ ", financeDeptName=" + financeDeptName + ", financeDeptCode="
				+ financeDeptCode + ", financeDeptBMCode=" + financeDeptBMCode
				+ ", createUserCode=" + createUserCode + ", modifyUserCode="
				+ modifyUserCode + ", notes=" + notes + ", version=" + version
				+ ", isDriver=" + isDriver + ", isKd=" + isKd + ", isbilling="
				+ isbilling + ", posCardDetailEntitys=" + posCardDetailEntitys
				+ ", invoiceType=" + invoiceType + ", invoiceNo=" + invoiceNo
				+ ", amount=" + amount + ", occupateAmount=" + occupateAmount
				+ ", unVerifyAmount=" + unVerifyAmount + ", active=" + active
				+ ", fields=" + fields + ", accountTime=" + accountTime
				+ ", frozenStatus=" + frozenStatus + ", frozenTime="
				+ frozenTime + ", frozenAmount=" + frozenAmount + "]";
	}
	
}
