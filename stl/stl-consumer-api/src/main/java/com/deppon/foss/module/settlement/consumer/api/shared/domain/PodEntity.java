package com.deppon.foss.module.settlement.consumer.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 签收记录实体 
 * @author 198704 weitao 
 * @date 2014-08-21
 */
public class PodEntity extends BaseEntity {

	/**
	 * 自动生成序列号
	 */
	private static final long serialVersionUID = 1L;
	
    /**
     * 签收id
     */
    private String id;
    
    /**
     * 运单号
     */
    private String waybillNo;
    
    /**
     * 签收时间
     */
    private Date podDate;
    
    /**
     * 签收类型
     */
    private String podType;
    
    /**
     * 签收客户编码
     */
    private String podUserCode;
    
    /**
     * 签收客户名称
     */
    private String podUserName;
    
    /**
     * 签收部门编码
     */
    private String podOrgCode;
    
    /**
     * 签收部门名称
     */
    private String podOrgName;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 是否初始化
     */
    private String isInit;

    /**
     * @return id
     */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return podDate
	 */
	public Date getPodDate() {
		return podDate;
	}

	/**
	 * @param podDate
	 */
	public void setPodDate(Date podDate) {
		this.podDate = podDate;
	}

	/**
	 * @return podType
	 */
	public String getPodType() {
		return podType;
	}

	/**
	 * @param podType
	 */
	public void setPodType(String podType) {
		this.podType = podType;
	}

	/**
	 * @return podUserCode
	 */
	public String getPodUserCode() {
		return podUserCode;
	}

	/**
	 * @param podUserCode
	 */
	public void setPodUserCode(String podUserCode) {
		this.podUserCode = podUserCode;
	}

	/**
	 * @return podUserName
	 */
	public String getPodUserName() {
		return podUserName;
	}

	/**
	 * @param podUserName
	 */
	public void setPodUserName(String podUserName) {
		this.podUserName = podUserName;
	}

	/**
	 * @return podOrgCode
	 */
	public String getPodOrgCode() {
		return podOrgCode;
	}

	/**
	 * @param podOrgCode
	 */
	public void setPodOrgCode(String podOrgCode) {
		this.podOrgCode = podOrgCode;
	}

	/**
	 * @return podOrgName
	 */
	public String getPodOrgName() {
		return podOrgName;
	}

	/**
	 * @param podOrgName
	 */
	public void setPodOrgName(String podOrgName) {
		this.podOrgName = podOrgName;
	}

	/**
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return idInit
	 */
	public String getIsInit() {
		return isInit;
	}

	/**
	 * @param isInit
	 */
	public void setIsInit(String isInit) {
		this.isInit = isInit;
	}

	/**
	 * @return serialVersionUID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
