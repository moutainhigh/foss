package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

/**
 * 外场和集中开单组实体类
 * @author foss-lifanghong
 * @date 2013-06-02
 * @version 1.0
 */
public class BillingGroupTransFerEntity extends DownloadableEntity {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -849028294556494548L;

    /**
     * 虚拟编码
     */
    private String virtualCode;
    
    /**
     * 外场编码
     */
    private String transFerCode;
    
    /**
     * 外场名称
     */
    private String transFerName;
    
    /**
     * 集中开单组部门编码
     */
    private String billingGroupCode;
    
    /**
     * 集中开单组部门名称
     */
    private String billingGroupName;
    
    /**
     * 是否有效
     */
    private String active;
    /**
     * 搜索开始时间
     */
    private Date createTime;
    
    

    


	

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
     * @return  the virtualCode
     */
    public String getVirtualCode() {
        return virtualCode;
    }
    
    /**
     * @param virtualCode the virtualCode to set
     */
    public void setVirtualCode(String virtualCode) {
        this.virtualCode = virtualCode;
    }

    /**
     * @return  the transFerCode
     */
	public String getTransFerCode() {
		return transFerCode;
	}

	 /**
     * @param transFerCode the virtualCode to set
     */
	public void setTransFerCode(String transFerCode) {
		this.transFerCode = transFerCode;
	}
	
	/**
     * @return  the transFerName
     */
	public String getTransFerName() {
		return transFerName;
	}

	 /**
     * @param virtualCode the virtualCode to set
     */
	public void setTransFerName(String transFerName) {
		this.transFerName = transFerName;
	}


	/**
     * @return  the billingGroupCode
     */
    public String getBillingGroupCode() {
        return billingGroupCode;
    }

    
    /**
     * @param billingGroupCode the billingGroupCode to set
     */
    public void setBillingGroupCode(String billingGroupCode) {
        this.billingGroupCode = billingGroupCode;
    }

    
    /**
     * @return  the billingGroupName
     */
    public String getBillingGroupName() {
        return billingGroupName;
    }

    
    /**
     * @param billingGroupName the billingGroupName to set
     */
    public void setBillingGroupName(String billingGroupName) {
        this.billingGroupName = billingGroupName;
    }

    
    /**
     * @return  the active
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

    
   
    
}
