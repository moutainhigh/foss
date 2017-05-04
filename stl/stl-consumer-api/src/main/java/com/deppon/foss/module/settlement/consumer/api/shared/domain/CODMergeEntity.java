package com.deppon.foss.module.settlement.consumer.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 代收货款合并实体
 * 
 * @author 163576-foss-guxinhua
 * @date 2014-10-11 上午10:35:52
 */
public class CODMergeEntity{
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1221382207337339628L;

	/**
	 * id
	 */
	private String id;

	/**
	 * 合并编码
	 */
    private String mergeCode;

    /**
     * 应付部门
     */
    private String payableOrgCode;
    
    /**
     * 所属子公司
     */
    private String payableComCode;

    /**
     * 收款人
     */
    private String payeeName;

    /**
     * 金额
     */
    private BigDecimal codAmount;

    /**
     * 帐号
     */
    private String accountNo;

    /**
     * 银行行号
     */
    private String bankHqCode;

    /**
     * 省
     */
    private String provinceCode;

    /**
     * 市
     */
    private String cityCode;

    /**
     * 银行支行
     */
    private String bankBranchCode;

    /**
     * 手机号码
     */
    private String payeePhone;

    /**
     * 代收货款类型
     */
    private String codType;

    /**
     * 对公对私
     */
    private String publicPrivateFlag;
    
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createUserName;

    /**
     * 创建人
     */
    private String createUserCode;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 修改人
     */
    private String modifyUserName;

    /**
     * 修改人
     */
    private String modifyUserCode;

    /**
     * 备注
     */
    private String note;

    /**
     * 有效
     */
    private String active;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the mergeCode
	 */
	public String getMergeCode() {
		return mergeCode;
	}

	/**
	 * @param mergeCode the mergeCode to set
	 */
	public void setMergeCode(String mergeCode) {
		this.mergeCode = mergeCode;
	}

	/**
	 * @return the payableOrgCode
	 */
	public String getPayableOrgCode() {
		return payableOrgCode;
	}

	/**
	 * @param payableOrgCode the payableOrgCode to set
	 */
	public void setPayableOrgCode(String payableOrgCode) {
		this.payableOrgCode = payableOrgCode;
	}

	/**
	 * @return the payeeName
	 */
	public String getPayeeName() {
		return payeeName;
	}

	/**
	 * @param payeeName the payeeName to set
	 */
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	/**
	 * @return the codAmount
	 */
	public BigDecimal getCodAmount() {
		return codAmount;
	}

	/**
	 * @param codAmount the codAmount to set
	 */
	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}

	/**
	 * @return the accountNo
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * @param accountNo the accountNo to set
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/**
	 * @return the bankHqCode
	 */
	public String getBankHqCode() {
		return bankHqCode;
	}

	/**
	 * @param bankHqCode the bankHqCode to set
	 */
	public void setBankHqCode(String bankHqCode) {
		this.bankHqCode = bankHqCode;
	}

	/**
	 * @return the provinceCode
	 */
	public String getProvinceCode() {
		return provinceCode;
	}

	/**
	 * @param provinceCode the provinceCode to set
	 */
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	/**
	 * @return the cityCode
	 */
	public String getCityCode() {
		return cityCode;
	}

	/**
	 * @param cityCode the cityCode to set
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	/**
	 * @return the bankBranchCode
	 */
	public String getBankBranchCode() {
		return bankBranchCode;
	}

	/**
	 * @param bankBranchCode the bankBranchCode to set
	 */
	public void setBankBranchCode(String bankBranchCode) {
		this.bankBranchCode = bankBranchCode;
	}

	/**
	 * @return the payeePhone
	 */
	public String getPayeePhone() {
		return payeePhone;
	}

	/**
	 * @param payeePhone the payeePhone to set
	 */
	public void setPayeePhone(String payeePhone) {
		this.payeePhone = payeePhone;
	}

	/**
	 * @return the codType
	 */
	public String getCodType() {
		return codType;
	}

	/**
	 * @param codType the codType to set
	 */
	public void setCodType(String codType) {
		this.codType = codType;
	}

	/**
	 * @return the publicPrivateFlag
	 */
	public String getPublicPrivateFlag() {
		return publicPrivateFlag;
	}

	/**
	 * @param publicPrivateFlag the publicPrivateFlag to set
	 */
	public void setPublicPrivateFlag(String publicPrivateFlag) {
		this.publicPrivateFlag = publicPrivateFlag;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * @param createUserName the createUserName to set
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * @return the createUserCode
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * @param createUserCode the createUserCode to set
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**
	 * @return the modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime the modifyTime to set
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return the modifyUserName
	 */
	public String getModifyUserName() {
		return modifyUserName;
	}

	/**
	 * @param modifyUserName the modifyUserName to set
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	/**
	 * @return the modifyUserCode
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}

	/**
	 * @param modifyUserCode the modifyUserCode to set
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return the payableComCode
	 */
	public String getPayableComCode() {
		return payableComCode;
	}

	/**
	 * @param payableComCode the payableComCode to set
	 */
	public void setPayableComCode(String payableComCode) {
		this.payableComCode = payableComCode;
	}

}