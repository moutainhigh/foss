package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 签收记录dto
 * @author 198704 weitao
 * @date 2014-08-22
 */
public class PodDto implements Serializable{

	/**
	 * 生成序列号
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
     *  查询起始时间
     */
    private Date podStartDate;
    
     /**
      * 查询终止时间
      */
    private Date podEndDate;
    
    /**
     * 查询类别
     */
    private String queryType;
    
    /**
     * 运单号数组
     */
    private String[] waybillNos;
    
    /**
	 * 返回的总条数
	 */
    private int totalCount=0;
    
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
	 * @return isInit
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

	/**
	 * @return podStartDate
	 */
	public Date getPodStartDate() {
		return podStartDate;
	}

	/**
	 * @param podStartDate
	 */
	public void setPodStartDate(Date podStartDate) {
		this.podStartDate = podStartDate;
	}

	/**
	 * @return podEndDate
	 */
	public Date getPodEndDate() {
		return podEndDate;
	}

	/**
	 * @param podEndDate
	 */
	public void setPodEndDate(Date podEndDate) {
		this.podEndDate = podEndDate;
	}

	/**
	 * @return queryType
	 */
	public String getQueryType() {
		return queryType;
	}

	/**
	 * @param queryType
	 */
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	/**
	 * @return waybillNos
	 */
	public String[] getWaybillNos() {
		return waybillNos;
	}

	/**
	 * @param waybillNos
	 */
	public void setWaybillNos(String[] waybillNos) {
		this.waybillNos = waybillNos;
	}

	/**
	 * @return totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
}
