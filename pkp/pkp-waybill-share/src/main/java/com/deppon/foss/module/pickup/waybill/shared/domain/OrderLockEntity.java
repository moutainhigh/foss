package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class OrderLockEntity extends BaseEntity{
    private String id;

    private String deptCode;

    private Long orderCountOverdue;

    private Long orderCountUnoverdue;

    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public Long getOrderCountOverdue() {
        return orderCountOverdue;
    }

    public void setOrderCountOverdue(Long orderCountOverdue) {
        this.orderCountOverdue = orderCountOverdue;
    }

    public Long getOrderCountUnoverdue() {
        return orderCountUnoverdue;
    }

    public void setOrderCountUnoverdue(Long orderCountUnoverdue) {
        this.orderCountUnoverdue = orderCountUnoverdue;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}