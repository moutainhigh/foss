package com.deppon.foss.module.base.baseinfo.api.shared.domain;


import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 对公银行账号
 * 
 * @author 130346-foss-lifanghong
 * @date 2013-08-22 下午2:35:52
 */
public class CommonBankAccountEntity extends BaseEntity {

 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 /**
     * 收款方编码.
     */
    private String payeeNo;
    
    /**
     * 录入人工号.
     */
    private String operatorId;
    
    /**
     * 组织编码
     */
    private String deptCode;
	/**
    *银行账号
    */	
    private String bankAcc;

    /**
    *银行开户名
    */	
    private String bankAccName;

    /**
    *部门标杆编码
    */	
    private String deptCd;

    /**
    *银行编码
    */	
    private String bankCd;

    /**
    *银行名称
    */	
    private String bankName;

    /**
    *支行编码
    */	
    private String subbranchCd;

    /**
    *支行名称
    */	
    private String subbranchName;

    /**
    *省份编码
    */	
    private String provCd;

    /**
    *省份名称
    */	
    private String provName;

    /**
    *城市编码
    */	
    private String cityCd;

    /**
    *城市名称
    */	
    private String cityName;

    /**
    * 账号状态
    */	
    private String accountStatus;

    /**
    * 财务自助ID
    */	
    private String fid;
    
    /**
    *是否启用
    */	
    private String active;
    
    /**
     *账户性质
     */	
     private String accountType;
     /**
      * 账户性质, 1-德邦子公司账户; 2-收银员卡; 3-内部员工账户; 4-公司外部账户.//
      */
     private List<String> accountTypes;
    
   
     
	public List<String> getAccountTypes() {
		return accountTypes;
	}

	public void setAccountTypes(List<String> accountTypes) {
		this.accountTypes = accountTypes;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getPayeeNo() {
		return payeeNo;
	}

	public void setPayeeNo(String payeeNo) {
		this.payeeNo = payeeNo;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}


	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	/**
	 * @return bankAcc
	 */
	public String getBankAcc() {
		return bankAcc;
	}

	/**
	 * @param  bankAcc  
	 */
	public void setBankAcc(String bankAcc) {
		this.bankAcc = bankAcc;
	}

	/**
	 * @return bankAccName
	 */
	public String getBankAccName() {
		return bankAccName;
	}

	/**
	 * @param  bankAccName  
	 */
	public void setBankAccName(String bankAccName) {
		this.bankAccName = bankAccName;
	}

	/**
	 * @return deptCd
	 */
	public String getDeptCd() {
		return deptCd;
	}

	/**
	 * @param  deptCd  
	 */
	public void setDeptCd(String deptCd) {
		this.deptCd = deptCd;
	}

	/**
	 * @return bankCd
	 */
	public String getBankCd() {
		return bankCd;
	}

	/**
	 * @param  bankCd  
	 */
	public void setBankCd(String bankCd) {
		this.bankCd = bankCd;
	}

	/**
	 * @return bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param  bankName  
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return subbranchCd
	 */
	public String getSubbranchCd() {
		return subbranchCd;
	}

	/**
	 * @param  subbranchCd  
	 */
	public void setSubbranchCd(String subbranchCd) {
		this.subbranchCd = subbranchCd;
	}

	/**
	 * @return subbranchName
	 */
	public String getSubbranchName() {
		return subbranchName;
	}

	/**
	 * @param  subbranchName  
	 */
	public void setSubbranchName(String subbranchName) {
		this.subbranchName = subbranchName;
	}

	/**
	 * @return provCd
	 */
	public String getProvCd() {
		return provCd;
	}

	/**
	 * @param  provCd  
	 */
	public void setProvCd(String provCd) {
		this.provCd = provCd;
	}

	/**
	 * @return provName
	 */
	public String getProvName() {
		return provName;
	}

	/**
	 * @param  provName  
	 */
	public void setProvName(String provName) {
		this.provName = provName;
	}

	/**
	 * @return cityCd
	 */
	public String getCityCd() {
		return cityCd;
	}

	/**
	 * @param  cityCd  
	 */
	public void setCityCd(String cityCd) {
		this.cityCd = cityCd;
	}

	/**
	 * @return cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param  cityName  
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return accountStatus
	 */
	public String getAccountStatus() {
		return accountStatus;
	}

	/**
	 * @param  accountStatus  
	 */
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	/**
	 * @return fid
	 */
	public String getFid() {
		return fid;
	}

	/**
	 * @param  fid  
	 */
	public void setFid(String fid) {
		this.fid = fid;
	}

	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param  active  
	 */
	public void setActive(String active) {
		this.active = active;
	}

}
