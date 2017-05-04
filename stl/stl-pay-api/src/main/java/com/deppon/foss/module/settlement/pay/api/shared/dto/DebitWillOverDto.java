package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 073615 on 2014/12/23.
 */
public class DebitWillOverDto implements Serializable{
    /**
     * 大区
     */
    private String bigRegion;
    /**
     * 小区
     */
    private String smallRegion;
    /**
     * 部门
     */
    private String deptCode;
    /**
     * 客户
     */
    private String customerCode;
    /**
     * 开始时间
     */
    private Date startTime;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getBigRegion() {
        return bigRegion;
    }

    public void setBigRegion(String bigRegion) {
        this.bigRegion = bigRegion;
    }

    public String getSmallRegion() {
        return smallRegion;
    }

    public void setSmallRegion(String smallRegion) {
        this.smallRegion = smallRegion;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }
}
