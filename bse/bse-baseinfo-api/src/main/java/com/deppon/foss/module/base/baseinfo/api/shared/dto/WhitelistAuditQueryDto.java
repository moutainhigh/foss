package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;
import java.util.Date;


/**
 * 车辆白名单查询Dto
 * @author foss-qiaolifeng
 * @date 2013-7-4 下午1:45:02
 */
public class WhitelistAuditQueryDto implements Serializable {

	/**
	 * 序列号ID
	 */
	private static final long serialVersionUID = -7447000126980808589L;

	/**
     * 车牌号.
     */
    private String vehicleNo;
	
    /**
     * 当前申请.
     */
    private String currentApplication;
	
    /**
     * 白名单状态.
     */
    private String status;
    
    /**
     * 申请开始时间.
     */
    private Date beginDate;
    
    /**
     * 申请结束时间.
     */
    private Date endDate;
    
    /**
     * 申请人编码
     */
    private String applyUser;
    
    /**
     * 申请部门
     */
    private String applyOrg;

    /**
     * 白名单类型.
     */
    private String whitelistType;
	
    /**
     * 是否启用.
     */
    private String active;
	
    /**
     * 司机姓名.
     */
    private String driverName;

    /**
     * 司机身份证.
     */
    private String driverIdCard;
	
	
	/**
	 * @get
	 * @return driverName
	 */
	public String getDriverName() {
		/*
		 * @get
		 * @return driverName
		 */
		return driverName;
	}




	
	/**
	 * @set
	 * @param driverName
	 */
	public void setDriverName(String driverName) {
		/*
		 *@set
		 *@this.driverName = driverName
		 */
		this.driverName = driverName;
	}




	
	/**
	 * @get
	 * @return driverIdCard
	 */
	public String getDriverIdCard() {
		/*
		 * @get
		 * @return driverIdCard
		 */
		return driverIdCard;
	}




	
	/**
	 * @set
	 * @param driverIdCard
	 */
	public void setDriverIdCard(String driverIdCard) {
		/*
		 *@set
		 *@this.driverIdCard = driverIdCard
		 */
		this.driverIdCard = driverIdCard;
	}




	/**
	 * @get
	 * @return active
	 */
	public String getActive() {
		/*
		 * @get
		 * @return active
		 */
		return active;
	}



	
	/**
	 * @set
	 * @param active
	 */
	public void setActive(String active) {
		/*
		 *@set
		 *@this.active = active
		 */
		this.active = active;
	}



	/**
	 * @get
	 * @return whitelistType
	 */
	public String getWhitelistType() {
		/*
		 * @get
		 * @return whitelistType
		 */
		return whitelistType;
	}


	
	/**
	 * @set
	 * @param whitelistType
	 */
	public void setWhitelistType(String whitelistType) {
		/*
		 *@set
		 *@this.whitelistType = whitelistType
		 */
		this.whitelistType = whitelistType;
	}


	/**
	 * @get
	 * @return vehicleNo
	 */
	public String getVehicleNo() {
		/*
		 * @get
		 * @return vehicleNo
		 */
		return vehicleNo;
	}

	
	/**
	 * @set
	 * @param vehicleNo
	 */
	public void setVehicleNo(String vehicleNo) {
		/*
		 *@set
		 *@this.vehicleNo = vehicleNo
		 */
		this.vehicleNo = vehicleNo;
	}

	
	/**
	 * @get
	 * @return currentApplication
	 */
	public String getCurrentApplication() {
		/*
		 * @get
		 * @return currentApplication
		 */
		return currentApplication;
	}

	
	/**
	 * @set
	 * @param currentApplication
	 */
	public void setCurrentApplication(String currentApplication) {
		/*
		 *@set
		 *@this.currentApplication = currentApplication
		 */
		this.currentApplication = currentApplication;
	}

	
	/**
	 * @get
	 * @return status
	 */
	public String getStatus() {
		/*
		 * @get
		 * @return status
		 */
		return status;
	}

	
	/**
	 * @set
	 * @param status
	 */
	public void setStatus(String status) {
		/*
		 *@set
		 *@this.status = status
		 */
		this.status = status;
	}

	
	/**
	 * @get
	 * @return beginDate
	 */
	public Date getBeginDate() {
		/*
		 * @get
		 * @return beginDate
		 */
		return beginDate;
	}

	
	/**
	 * @set
	 * @param beginDate
	 */
	public void setBeginDate(Date beginDate) {
		/*
		 *@set
		 *@this.beginDate = beginDate
		 */
		this.beginDate = beginDate;
	}

	
	/**
	 * @get
	 * @return endDate
	 */
	public Date getEndDate() {
		/*
		 * @get
		 * @return endDate
		 */
		return endDate;
	}

	
	/**
	 * @set
	 * @param endDate
	 */
	public void setEndDate(Date endDate) {
		/*
		 *@set
		 *@this.endDate = endDate
		 */
		this.endDate = endDate;
	}

	
	

	
	
	/**
	 * @get
	 * @return applyUser
	 */
	public String getApplyUser() {
		/*
		 * @get
		 * @return applyUser
		 */
		return applyUser;
	}




	
	/**
	 * @set
	 * @param applyUser
	 */
	public void setApplyUser(String applyUser) {
		/*
		 *@set
		 *@this.applyUser = applyUser
		 */
		this.applyUser = applyUser;
	}




	/**
	 * @get
	 * @return applyOrg
	 */
	public String getApplyOrg() {
		/*
		 * @get
		 * @return applyOrg
		 */
		return applyOrg;
	}

	
	/**
	 * @set
	 * @param applyOrg
	 */
	public void setApplyOrg(String applyOrg) {
		/*
		 *@set
		 *@this.applyOrg = applyOrg
		 */
		this.applyOrg = applyOrg;
	}
    
    
}
