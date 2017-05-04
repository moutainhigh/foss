package com.deppon.foss.module.pickup.order.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class OrderThreadPoollogEntity extends BaseEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


    private Object serviceName;

    private Object exceptionData;

    private Object exceptionPoolname;

    private BigDecimal failTimes;

    private Date createTime;

    private String exceptionMsg;

   
    public Object getServiceName() {
        return serviceName;
    }

    public void setServiceName(Object serviceName) {
        this.serviceName = serviceName;
    }

    public Object getExceptionData() {
        return exceptionData;
    }

    public void setExceptionData(Object exceptionData) {
        this.exceptionData = exceptionData;
    }

    public Object getExceptionPoolname() {
        return exceptionPoolname;
    }

    public void setExceptionPoolname(Object exceptionPoolname) {
        this.exceptionPoolname = exceptionPoolname;
    }

    public BigDecimal getFailTimes() {
        return failTimes;
    }

    public void setFailTimes(BigDecimal failTimes) {
        this.failTimes = failTimes;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }
}