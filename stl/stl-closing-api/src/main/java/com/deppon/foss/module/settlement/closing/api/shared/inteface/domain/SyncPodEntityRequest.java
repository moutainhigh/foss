package com.deppon.foss.module.settlement.closing.api.shared.inteface.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 同步cubc的财务单据完结信息-请求
 * 
 * @author 269044
 * @date 2017-04-21
 */
public class SyncPodEntityRequest implements Serializable{

	/**
	 * 序列号
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public Date getPodDate() {
		return podDate;
	}

	public void setPodDate(Date podDate) {
		this.podDate = podDate;
	}

	public String getPodType() {
		return podType;
	}

	public void setPodType(String podType) {
		this.podType = podType;
	}

	public String getPodUserCode() {
		return podUserCode;
	}

	public void setPodUserCode(String podUserCode) {
		this.podUserCode = podUserCode;
	}

	public String getPodUserName() {
		return podUserName;
	}

	public void setPodUserName(String podUserName) {
		this.podUserName = podUserName;
	}

	public String getPodOrgCode() {
		return podOrgCode;
	}

	public void setPodOrgCode(String podOrgCode) {
		this.podOrgCode = podOrgCode;
	}

	public String getPodOrgName() {
		return podOrgName;
	}

	public void setPodOrgName(String podOrgName) {
		this.podOrgName = podOrgName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getIsInit() {
		return isInit;
	}

	public void setIsInit(String isInit) {
		this.isInit = isInit;
	}   
    
}
