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
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/SealDto.java
 *  
 *  FILE NAME          :SealDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.SealEntity;

/**
 * 封签Dto
 * @author ibm-zhangyixin
 * @date 2012-12-25 下午6:46:31
 */
public class SealDto extends SealEntity implements Serializable {
	
	private static final long serialVersionUID = 6211814587312422911L;
	
	/**开始日期**/
	private String beginDate;

	/**结束日期**/
	private String endDate;
	
	/**单据编号**/
	private String billNo;
	
	/**车辆任务ID**/
	private String truckTaskId;
	
	/**线路**/
	private String lineName;
	
	/**司机**/
	private String driverName;	

	/**司机Code**/
	private String driverCode;	

	/**出发部门**/
	private String origOrgCode;	

	/**到达部门**/
	private String destOrgCode;	

	/**出发部门名称**/
	private String origOrgName;	
	
	/**到达部门名称**/
	private String destOrgName;	
	
	/**车辆任务创建时间**/
	private Date createTime;

	/**车辆出发状态,未出发-UNDEPART，在途-ONTHEWAY，已到达-ARRIVED，作废-CANCLED**/
	private String status;		
	/**封签校验类型**/
	private String sealCheckType;
	
	/**封签类型list**/
	private List<String> sealCheckTypeList=new ArrayList<String>();
	
	/**
	 * 获取 出发部门*.
	 *
	 * @return the 出发部门*
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}
	
	/**
	 * 设置 出发部门*.
	 *
	 * @param origOrgCode the new 出发部门*
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}
	
	/**
	 * 获取 到达部门*.
	 *
	 * @return the 到达部门*
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}
	
	/**
	 * 设置 到达部门*.
	 *
	 * @param destOrgCode the new 到达部门*
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}
	
	/**
	 * 获取 车辆出发状态,未出发-UNDEPART，在途-ONTHEWAY，已到达-ARRIVED，作废-CANCLED*.
	 *
	 * @return the 车辆出发状态,未出发-UNDEPART，在途-ONTHEWAY，已到达-ARRIVED，作废-CANCLED*
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * 设置 车辆出发状态,未出发-UNDEPART，在途-ONTHEWAY，已到达-ARRIVED，作废-CANCLED*.
	 *
	 * @param status the new 车辆出发状态,未出发-UNDEPART，在途-ONTHEWAY，已到达-ARRIVED，作废-CANCLED*
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * 获取 开始日期*.
	 *
	 * @return the 开始日期*
	 */
	public String getBeginDate() {
		return beginDate;
	}
	
	/**
	 * 设置 开始日期*.
	 *
	 * @param beginDate the new 开始日期*
	 */
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	
	/**
	 * 获取 结束日期*.
	 *
	 * @return the 结束日期*
	 */
	public String getEndDate() {
		return endDate;
	}
	
	/**
	 * 设置 结束日期*.
	 *
	 * @param endDate the new 结束日期*
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	/** 
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 下午6:47:18
	 * @see com.deppon.foss.module.transfer.load.api.shared.domain.SealEntity#getDriverName()
	 */
	public String getDriverName() {
		return driverName;
	}
	
	/** 
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 下午6:47:18
	 * @see com.deppon.foss.module.transfer.load.api.shared.domain.SealEntity#setDriverName(java.lang.String)
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getOrigOrgName() {
		return origOrgName;
	}

	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}

	public String getDestOrgName() {
		return destOrgName;
	}

	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getTruckTaskId() {
		return truckTaskId;
	}

	public void setTruckTaskId(String truckTaskId) {
		this.truckTaskId = truckTaskId;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getDriverCode() {
		return driverCode;
	}

	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the sealCheakType
	 */
	public String getSealCheckType() {
		return sealCheckType;
	}

	/**
	 * @param sealCheakType the sealCheakType to set
	 */
	public void setSealCheckType(String sealCheakType) {
		this.sealCheckType = sealCheakType;
	}

	/**
	 * @return the sealCheckTypeList
	 */
	public List<String> getSealCheckTypeList() {
		return sealCheckTypeList;
	}

	/**
	 * @param sealCheckTypeList the sealCheckTypeList to set
	 */
	public void setSealCheckTypeList(List<String> sealCheckTypeList) {
		this.sealCheckTypeList = sealCheckTypeList;
	}
	
}