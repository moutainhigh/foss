/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-management-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/shared/domain/FocusSignBillEntity.java
 *  
 *  FILE NAME          :FocusSignBillEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;
/**
 * 集中接货签单
 * @author 038300-foss-pengzhen
 * @date 2012-11-27 下午4:13:17
 */
public class FocusSignBillEntity extends BaseEntity{
	
	private static final long serialVersionUID = 8287559859568723067L;
	/**签单编号*/
	private String signBillNo;
	/**司机编号*/
    private String driverCode;
    /**司机姓名*/
    private String driverName;
    /**接货员编号*/
    private String receiverCode;
    /**接货员姓名*/
    private String receiverName;
    /**车牌号*/
    private String vehicleNo;
    /**车型*/
    private String vehicleTypeLength;
    /**车型名称**/
    private String vehicleLengthName;
    /**空缺公里*/
    private BigDecimal vacancyKm;
    /**行驶公里*/
    private BigDecimal runKm;
    /**签单日期*/
    private Date signBillDate;
    /**新增部门**/
    private String orgCode;
    /**新增部门**/
    private String orgName;
    
    /**
     * 获取 签单编号.
     *
     * @return the 签单编号
     */
    public String getSignBillNo() {
        return signBillNo;
    }

    /**
     * 设置 签单编号.
     *
     * @param signBillNo the new 签单编号
     */
    public void setSignBillNo(String signBillNo) {
        this.signBillNo = signBillNo;
    }

    /**
     * 获取 司机编号.
     *
     * @return the 司机编号
     */
    public String getDriverCode() {
        return driverCode;
    }

    /**
     * 设置 司机编号.
     *
     * @param driverCode the new 司机编号
     */
    public void setDriverCode(String driverCode) {
        this.driverCode = driverCode;
    }

    /**
     * 获取 司机姓名.
     *
     * @return the 司机姓名
     */
    public String getDriverName() {
        return driverName;
    }

    /**
     * 设置 司机姓名.
     *
     * @param driverName the new 司机姓名
     */
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    /**
     * 获取 接货员编号.
     *
     * @return the 接货员编号
     */
    public String getReceiverCode() {
        return receiverCode;
    }

    /**
     * 设置 接货员编号.
     *
     * @param receiverCode the new 接货员编号
     */
    public void setReceiverCode(String receiverCode) {
        this.receiverCode = receiverCode;
    }

    /**
     * 获取 接货员姓名.
     *
     * @return the 接货员姓名
     */
    public String getReceiverName() {
        return receiverName;
    }

    /**
     * 设置 接货员姓名.
     *
     * @param receiverName the new 接货员姓名
     */
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    /**
     * 获取 车牌号.
     *
     * @return the 车牌号
     */
    public String getVehicleNo() {
        return vehicleNo;
    }

    /**
     * 设置 车牌号.
     *
     * @param vehicleNo the new 车牌号
     */
    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    /**
     * 获取 车型.
     *
     * @return the 车型
     */
    public String getVehicleTypeLength() {
        return vehicleTypeLength;
    }

    /**
     * 设置 车型.
     *
     * @param vehicleTypeLength the new 车型
     */
    public void setVehicleTypeLength(String vehicleTypeLength) {
        this.vehicleTypeLength = vehicleTypeLength;
    }

    /**
     * 获取 空缺公里.
     *
     * @return the 空缺公里
     */
    public BigDecimal getVacancyKm() {
        return vacancyKm;
    }

    /**
     * 设置 空缺公里.
     *
     * @param vacancyKm the new 空缺公里
     */
    public void setVacancyKm(BigDecimal vacancyKm) {
        this.vacancyKm = vacancyKm;
    }

    /**
     * 获取 行驶公里.
     *
     * @return the 行驶公里
     */
    public BigDecimal getRunKm() {
        return runKm;
    }

    /**
     * 设置 行驶公里.
     *
     * @param runKm the new 行驶公里
     */
    public void setRunKm(BigDecimal runKm) {
        this.runKm = runKm;
    }
    
    /**
     * 获取 签单日期.
     *
     * @return the 签单日期
     */
    @DateFormat(formate="yyyy-MM-dd")
    public Date getSignBillDate() {
        return signBillDate;
    }
    
    /**
     * 设置 签单日期.
     *
     * @param signBillDate the new 签单日期
     */
    @DateFormat(formate="yyyy-MM-dd")
    public void setSignBillDate(Date signBillDate) {
        this.signBillDate = signBillDate;
    }

	/**   
	 * vehicleLengthName   
	 *   
	 * @return  the vehicleLengthName   
	 */
	
	public String getVehicleLengthName() {
		return vehicleLengthName;
	}

	/**   
	 * @param vehicleLengthName the vehicleLengthName to set
	 * Date:2013-3-25下午7:05:00
	 */
	public void setVehicleLengthName(String vehicleLengthName) {
		this.vehicleLengthName = vehicleLengthName;
	}

	/**   
	 * orgCode   
	 *   
	 * @return  the orgCode   
	 */
	
	public String getOrgCode() {
		return orgCode;
	}

	/**   
	 * @param orgCode the orgCode to set
	 * Date:2013-5-7上午11:28:55
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**   
	 * orgName   
	 *   
	 * @return  the orgName   
	 */
	
	public String getOrgName() {
		return orgName;
	}

	/**   
	 * @param orgName the orgName to set
	 * Date:2013-5-7上午11:28:55
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
}