/*
 * 党章，是一个政党为保证全党在政治上，思想上的一致和组织上，行动上的统一所制定的章程。
 * 一个党的党章的主要内容应该包括该党的性质、指导思想、纲领任务、组织结构、组织制度，党员的条件、权利、义务和纪律等项。
 * 通常衡量一个政党是否成熟党章也是关键因素之一。党章是政党的宗旨和行为规范。
 * 中国共产党现行党章于中国共产党第十八次全国代表大会部分修改，于2012年11月14日通过。
 * 除总纲外共十一章五十三条。规定了党的纲领、组织机构、组织制度、党员的条件、党员的义务和权利、党的纪律等项.
 */
package com.deppon.foss.module.settlement.writeoff.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * @ClassName: HomeStatementEntity
 * @Description: (家装对账单实体)
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2015-11-24 下午2:32:01
 * 
 */
public class HomeStatementEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 家装对账单号
	 */
    private String statementBillNo;
    /**
	 * 部门编码
	 */
    private String orgCode;
    /**
	 * 部门名称
	 */
    private String orgName;
    /**
	 * 所属子公司编码
	 */
    private String subCompanyCode;
    /**
	 * 所属子公司名称
	 */
    private String subCompanyName;
    /**
	 * 家装代理名称
	 */
    private String homeSupplyName;
    /**
	 * 家装代理编码
	 */
    private String homeSupplyCode;
    /**
	 * 单据类型
	 */
    private String billType;
    /**
	 * 金额
	 */
    //private BigDecimal amount;
    /**
	 * 已核销金额
	 */
    private BigDecimal verifyAmount;
    /**
	 * 未核销金额
	 */
    private BigDecimal unverifyAmount;
    /**
	 * 创建人名字
	 */
    private String createUserName;
    /**
	 * 创建人工号
	 */
    private String createUserCode;
    /**
	 * 业务日期
	 */
    private Date businessDate;
    /**
	 * 创建日期
	 */
    private Date createTime;
    /**
	 * 修改人工号
	 */
    private String modifyUserCode;
    /**
	 * 修改人名字
	 */
    private String modifyUserName;
    /**
	 * 确认人名字
	 */
    private String confirmUserName;
    /**
	 * 确认人工号
	 */
    private String confirmUserCode;
    /**
	 * 确认时间
	 */
    private Date confirmTime;
    /**
	 * 修改时间
	 */
    private Date modifyTime;
    /**
	 * 确认状态
	 */
    private String confirmStatus;
    /**
	 * 本期发生金额
	 */
    private BigDecimal periodAmount;
    /**
	 * 对账单应收金额
	 */
    private BigDecimal periodRecAmount;
    /**
	 * 对账单应付金额
	 */
    private BigDecimal periodPayAmount;
    /**
	 * 版本号
	 */
    private String versionNo;
    
	public String getStatementBillNo() {
		return statementBillNo;
	}
	public void setStatementBillNo(String statementBillNo) {
		this.statementBillNo = statementBillNo;
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
	public String getSubCompanyCode() {
		return subCompanyCode;
	}
	public void setSubCompanyCode(String subCompanyCode) {
		this.subCompanyCode = subCompanyCode;
	}
	public String getSubCompanyName() {
		return subCompanyName;
	}
	public void setSubCompanyName(String subCompanyName) {
		this.subCompanyName = subCompanyName;
	}
	public String getHomeSupplyName() {
		return homeSupplyName;
	}
	public void setHomeSupplyName(String homeSupplyName) {
		this.homeSupplyName = homeSupplyName;
	}
	public String getHomeSupplyCode() {
		return homeSupplyCode;
	}
	public void setHomeSupplyCode(String homeSupplyCode) {
		this.homeSupplyCode = homeSupplyCode;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	/*public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}*/
	public BigDecimal getVerifyAmount() {
		return verifyAmount;
	}
	public void setVerifyAmount(BigDecimal verifyAmount) {
		this.verifyAmount = verifyAmount;
	}
	public BigDecimal getUnverifyAmount() {
		return unverifyAmount;
	}
	public void setUnverifyAmount(BigDecimal unverifyAmount) {
		this.unverifyAmount = unverifyAmount;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getCreateUserCode() {
		return createUserCode;
	}
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	public Date getBusinessDate() {
		return businessDate;
	}
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getModifyUserCode() {
		return modifyUserCode;
	}
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}
	public String getModifyUserName() {
		return modifyUserName;
	}
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}
	public String getConfirmUserName() {
		return confirmUserName;
	}
	public void setConfirmUserName(String confirmUserName) {
		this.confirmUserName = confirmUserName;
	}
	public String getConfirmUserCode() {
		return confirmUserCode;
	}
	public void setConfirmUserCode(String confirmUserCode) {
		this.confirmUserCode = confirmUserCode;
	}
	public Date getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getConfirmStatus() {
		return confirmStatus;
	}
	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public BigDecimal getPeriodAmount() {
		return periodAmount;
	}
	public void setPeriodAmount(BigDecimal periodAmount) {
		this.periodAmount = periodAmount;
	}
	public BigDecimal getPeriodRecAmount() {
		return periodRecAmount;
	}
	public void setPeriodRecAmount(BigDecimal periodRecAmount) {
		this.periodRecAmount = periodRecAmount;
	}
	public BigDecimal getPeriodPayAmount() {
		return periodPayAmount;
	}
	public void setPeriodPayAmount(BigDecimal periodPayAmount) {
		this.periodPayAmount = periodPayAmount;
	}
}
