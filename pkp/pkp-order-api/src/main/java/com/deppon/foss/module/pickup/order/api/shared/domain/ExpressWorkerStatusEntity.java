package com.deppon.foss.module.pickup.order.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class ExpressWorkerStatusEntity extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


    private String id;
    
    private Object empCode;

    private Object empName;

    private Object padNo;

    private Object workStatus;

    private Object createUsercode;

    private Date createTime;

    private Object modifyCode;

    private Date modifyTime;

    private String active;
    /**
     * 业务范围 零担/快递
     */
    private String businessArea;
    
    private String pdaSignId;//14.7.16 gcl 新加 AUTO-163
    
    //车牌号
    private String vehicleNo;
    

    public String getPdaSignId() {
		return pdaSignId;
	}

	public void setPdaSignId(String pdaSignId) {
		this.pdaSignId = pdaSignId;
	}

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

    public Object getEmpName() {
        return empName;
    }

    public void setEmpName(Object empName) {
        this.empName = empName;
    }

    public Object getPadNo() {
        return padNo;
    }

    public void setPadNo(Object padNo) {
        this.padNo = padNo;
    }

    public Object getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(Object workStatus) {
        this.workStatus = workStatus;
    }

    public Object getCreateUsercode() {
        return createUsercode;
    }

    public void setCreateUsercode(Object createUsercode) {
        this.createUsercode = createUsercode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Object getModifyCode() {
        return modifyCode;
    }

    public void setModifyCode(Object modifyCode) {
        this.modifyCode = modifyCode;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
}