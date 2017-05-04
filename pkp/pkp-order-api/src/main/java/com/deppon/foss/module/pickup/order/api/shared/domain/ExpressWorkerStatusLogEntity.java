package com.deppon.foss.module.pickup.order.api.shared.domain;


import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class ExpressWorkerStatusLogEntity extends BaseEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private String id;
    private Object empCode;

    private Object operateSystem;

    private Object operateType;

    private Object pdaNo;

    private Object createTime;

    private Object createUsercode;

    private Date modifyTime;

    private Object modifyUsercode;
    
    /**
     * 业务范围：零担/快递
     */
    private String businessArea;    
    
    
    public String getBusinessArea() {
		return businessArea;
	}

	public void setBusinessArea(String businessArea) {
		this.businessArea = businessArea;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Object getEmpCode() {
        return empCode;
    }

    public void setEmpCode(Object empCode) {
        this.empCode = empCode;
    }

    public Object getOperateSystem() {
        return operateSystem;
    }

    public void setOperateSystem(Object operateSystem) {
        this.operateSystem = operateSystem;
    }

    public Object getOperateType() {
        return operateType;
    }

    public void setOperateType(Object operateType) {
        this.operateType = operateType;
    }

    public Object getPdaNo() {
        return pdaNo;
    }

    public void setPdaNo(Object pdaNo) {
        this.pdaNo = pdaNo;
    }

    public Object getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Object createTime) {
        this.createTime = createTime;
    }

    public Object getCreateUsercode() {
        return createUsercode;
    }

    public void setCreateUsercode(Object createUsercode) {
        this.createUsercode = createUsercode;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Object getModifyUsercode() {
        return modifyUsercode;
    }

    public void setModifyUsercode(Object modifyUsercode) {
        this.modifyUsercode = modifyUsercode;
    }
}