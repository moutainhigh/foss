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
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;


/**
 * 营业部向CRM同步DTO
 * @author foss-zhujunyong
 * @date Jun 3, 2013 10:31:55 AM
 * @version 1.0
 */
public class SalesBillingGroupSyncDto implements Serializable{

    /**
     * 序列化
     */
    private static final long serialVersionUID = 4971268919133717348L;

    /**
     * 营业部编码
     */
    private String saleDeptCode;
    /**
     * 集中开单组编码
     */
    private String billingGroupCode;
    /**
     * 操作类型
     */
    private String operateFlag;
    
    /**
     * @return  the saleDeptCode
     */
    public String getSaleDeptCode() {
        return saleDeptCode;
    }
    
    /**
     * @param saleDeptCode the saleDeptCode to set
     */
    public void setSaleDeptCode(String saleDeptCode) {
        this.saleDeptCode = saleDeptCode;
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
     * @return  the operateFlag
     */
    public String getOperateFlag() {
        return operateFlag;
    }
    
    /**
     * @param operateFlag the operateFlag to set
     */
    public void setOperateFlag(String operateFlag) {
        this.operateFlag = operateFlag;
    }
    
    
}
