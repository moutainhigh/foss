/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.api.shared.domain;



/**
 * 营业部和集中开单组实体类
 * @author foss-zhujunyong
 * @date Apr 26, 2013 2:02:29 PM
 * @version 1.0
 */
public class SalesBillingGroupEntity extends DownloadableEntity {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -849028294556494545L;

    /**
     * 虚拟编码
     */
    private String virtualCode;
    
    /**
     * 营业部部门编码
     */
    private String salesDeptCode;
    
    /**
     * 营业部部门名称
     */
    private String salesDeptName;
    
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
     * @return  the salesDeptCode
     */
    public String getSalesDeptCode() {
        return salesDeptCode;
    }

    
    /**
     * @param salesDeptCode the salesDeptCode to set
     */
    public void setSalesDeptCode(String salesDeptCode) {
        this.salesDeptCode = salesDeptCode;
    }

    
    /**
     * @return  the salesDeptName
     */
    public String getSalesDeptName() {
        return salesDeptName;
    }

    
    /**
     * @param salesDeptName the salesDeptName to set
     */
    public void setSalesDeptName(String salesDeptName) {
        this.salesDeptName = salesDeptName;
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
