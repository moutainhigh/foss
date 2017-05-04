package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 重分类收入基础信息
 * @author 307196
 *
 */
public class ClassifiedIncomeEntity extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
    private String id;
    /**
     * 产品类型名称
     */
    private String productTypeName;
    /**
     * 产品类型编码
     */
    private String productTypeCode;
    /**
     * 所属子公司名称
     */
    private String owendSubsidiaryName;
    /**
     * 所属子公司编码
     */
    private String owendSubsidiaryCode;
    /**
     * 6%比例
     */
    private String sixPercent;
    
    /**
	 * 11%比例
	 */
	private String elevenPercent;
   
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 创建人
     */
    private String createUser;
    
    /**
     * 创建人名称
     */
    private String createUserName;
    

   
    /**
     * 修改人
     */
    private String modifyUser;

    /**
     * 修改人名称
     */
    private String modifyUserName;

    /**
     * 是否有效
     */
    private String active;
    

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
	
	


	public String getProductTypeName() {
		return productTypeName;
	}


	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}


	public String getProductTypeCode() {
		return productTypeCode;
	}


	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
	}


	public String getOwendSubsidiaryName() {
		return owendSubsidiaryName;
	}


	public void setOwendSubsidiaryName(String owendSubsidiaryName) {
		this.owendSubsidiaryName = owendSubsidiaryName;
	}


	public String getOwendSubsidiaryCode() {
		return owendSubsidiaryCode;
	}


	public void setOwendSubsidiaryCode(String owendSubsidiaryCode) {
		this.owendSubsidiaryCode = owendSubsidiaryCode;
	}


	public String getSixPercent() {
		return sixPercent;
	}


	public void setSixPercent(String sixPercent) {
		this.sixPercent = sixPercent;
	}


	public String getElevenPercent() {
		return elevenPercent;
	}


	public void setElevenPercent(String elevenPercent) {
		this.elevenPercent = elevenPercent;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Date getModifyTime() {
		return modifyTime;
	}


	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}


	

	public String getCreateUser() {
		return createUser;
	}


	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}


	public String getCreateUserName() {
		return createUserName;
	}


	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}


	public String getModifyUser() {
		return modifyUser;
	}


	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	
	public String getModifyUserName() {
		return modifyUserName;
	}


	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}


	public String getActive() {
		return active;
	}


	public void setActive(String active) {
		this.active = active;
	}
   
    
    
}