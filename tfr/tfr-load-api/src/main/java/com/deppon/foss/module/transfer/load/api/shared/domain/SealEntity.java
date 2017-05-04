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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/domain/SealEntity.java
 *  
 *  FILE NAME          :SealEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 封签
 */
public class SealEntity extends BaseEntity  implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**ID**/
	private String id;

	/**任务车辆明细_ID**///这里其实应该为truckTaskId
	private String truckTaskDetailId;	

	/**车牌号**/
	private String vehicleNo;			

	/**封车类型/操作类型, BIND绑定装车, CHECK校验卸车, INVALID删除**/
	private String sealType;			
	
	/**是否为暂存状态Y则为暂存, 默认为空**/
	//只有PDA传过来校验的封签才会有暂存的状态
	//当车辆未到达状态时PDA已经开始校验操作
	private String isTransientState;

	/**封签状态/差异, 0未检查，1正常，2异常, 3破损**/
	private String sealState;			

	/**封车人编号**/
	private String sealerCode;			

	/**封车人名称**/
	private String sealerName;			

	/**封车部门编号**/
	private String sealdOrgCode;		

	/**封车部门名称**/
	private String sealdOrgName;		

	/**封车时间**/
	private Date sealTime;				

	/**检查人编号**/
	private String checkerUser;			

	/**检查人名称**/
	private String checkerCode;			

	/**检查部门编号**/
	private String checkOrgCode;		

	/**检查部门名称**/
	private String checkOrgName;		

	/**检查时间**/
	private Date checkTime;				

	/**操作时间**/
	private Date operateTime;			

	/**装车封签备注**/
	private String sealOrgMemo;			

	/**卸车封签备注**/
	private String checkOrgMemo;

	/**OA差错编号**/
	private String oaErrorNo;			

	/**PDA设备号 绑定时的**/
	private String pdaDeviceNo;
	
	/**PDA设备号 校验时的**/
	private String checkedPdaDeviceNo;

	/**绑定方式(FOSS, PDA_HAND, PDA_SCAN)**/
	private String bindType;
	
	/**校验方式(FOSS, PDA_HAND, PDA_SCAN)**/
	private String checkType;
	
	/**车辆放行ID**/
	private String truckDepartId;		
	
	/**司机名称**/
	private String driverName;

	/**司机工号**/
	private String driverCode;

	/**司机电话**/
	private String driverPhone;
	
	/**挂牌号***/
	private String trailerNo;
	/** 
	 * ID
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 下午6:28:57
	 * @see com.deppon.foss.framework.entity.BaseEntity#getId()
	 */
	public String getId() {
		return id;
	}
	
	/** 
	 * ID
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 下午6:28:57
	 * @see com.deppon.foss.framework.entity.BaseEntity#setId(java.lang.String)
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 获取 车牌号*.
	 *
	 * @return the 车牌号*
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	
	/**
	 * 设置 车牌号*.
	 *
	 * @param vehicleNo the new 车牌号*
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	
	/**
	 * 获取 封车类型/操作类型, 1绑定装车, 2校验卸车*.
	 *
	 * @return the 封车类型/操作类型, 1绑定装车, 2校验卸车*
	 */
	public String getSealType() {
		return sealType;
	}
	
	/**
	 * 设置 封车类型/操作类型, 1绑定装车, 2校验卸车*.
	 *
	 * @param sealType the new 封车类型/操作类型, 1绑定装车, 2校验卸车*
	 */
	public void setSealType(String sealType) {
		this.sealType = sealType;
	}
	
	/**
	 * 获取 封签状态/差异, 0未检查，1正常，2异常, 3破损*.
	 *
	 * @return the 封签状态/差异, 0未检查，1正常，2异常, 3破损*
	 */
	public String getSealState() {
		return sealState;
	}
	
	/**
	 * 设置 封签状态/差异, 0未检查，1正常，2异常, 3破损*.
	 *
	 * @param sealState the new 封签状态/差异, 0未检查，1正常，2异常, 3破损*
	 */
	public void setSealState(String sealState) {
		this.sealState = sealState;
	}
	
	/**
	 * 获取 封车人编号*.
	 *
	 * @return the 封车人编号*
	 */
	public String getSealerCode() {
		return sealerCode;
	}
	
	/**
	 * 设置 封车人编号*.
	 *
	 * @param sealerCode the new 封车人编号*
	 */
	public void setSealerCode(String sealerCode) {
		this.sealerCode = sealerCode;
	}
	
	/**
	 * 获取 封车人名称*.
	 *
	 * @return the 封车人名称*
	 */
	public String getSealerName() {
		return sealerName;
	}
	
	/**
	 * 设置 封车人名称*.
	 *
	 * @param sealerName the new 封车人名称*
	 */
	public void setSealerName(String sealerName) {
		this.sealerName = sealerName;
	}
	
	/**
	 * 获取 封车部门编号*.
	 *
	 * @return the 封车部门编号*
	 */
	public String getSealdOrgCode() {
		return sealdOrgCode;
	}
	
	/**
	 * 设置 封车部门编号*.
	 *
	 * @param sealdOrgCode the new 封车部门编号*
	 */
	public void setSealdOrgCode(String sealdOrgCode) {
		this.sealdOrgCode = sealdOrgCode;
	}
	
	/**
	 * 获取 封车部门名称*.
	 *
	 * @return the 封车部门名称*
	 */
	public String getSealdOrgName() {
		return sealdOrgName;
	}
	
	/**
	 * 设置 封车部门名称*.
	 *
	 * @param sealdOrgName the new 封车部门名称*
	 */
	public void setSealdOrgName(String sealdOrgName) {
		this.sealdOrgName = sealdOrgName;
	}
	
	/**
	 * 获取 封车时间*.
	 *
	 * @return the 封车时间*
	 */
	public Date getSealTime() {
		return sealTime;
	}
	
	/**
	 * 设置 封车时间*.
	 *
	 * @param sealTime the new 封车时间*
	 */
	public void setSealTime(Date sealTime) {
		this.sealTime = sealTime;
	}
	
	/**
	 * 获取 检查人编号*.
	 *
	 * @return the 检查人编号*
	 */
	public String getCheckerUser() {
		return checkerUser;
	}
	
	/**
	 * 设置 检查人编号*.
	 *
	 * @param checkerUser the new 检查人编号*
	 */
	public void setCheckerUser(String checkerUser) {
		this.checkerUser = checkerUser;
	}
	
	/**
	 * 获取 检查人名称*.
	 *
	 * @return the 检查人名称*
	 */
	public String getCheckerCode() {
		return checkerCode;
	}
	
	/**
	 * 设置 检查人名称*.
	 *
	 * @param checkerCode the new 检查人名称*
	 */
	public void setCheckerCode(String checkerCode) {
		this.checkerCode = checkerCode;
	}
	
	/**
	 * 获取 检查部门编号*.
	 *
	 * @return the 检查部门编号*
	 */
	public String getCheckOrgCode() {
		return checkOrgCode;
	}
	
	/**
	 * 设置 检查部门编号*.
	 *
	 * @param checkOrgCode the new 检查部门编号*
	 */
	public void setCheckOrgCode(String checkOrgCode) {
		this.checkOrgCode = checkOrgCode;
	}
	
	/**
	 * 获取 检查部门名称*.
	 *
	 * @return the 检查部门名称*
	 */
	public String getCheckOrgName() {
		return checkOrgName;
	}
	
	/**
	 * 设置 检查部门名称*.
	 *
	 * @param checkOrgName the new 检查部门名称*
	 */
	public void setCheckOrgName(String checkOrgName) {
		this.checkOrgName = checkOrgName;
	}
	
	/**
	 * 获取 检查时间*.
	 *
	 * @return the 检查时间*
	 */
	public Date getCheckTime() {
		return checkTime;
	}
	
	/**
	 * 设置 检查时间*.
	 *
	 * @param checkTime the new 检查时间*
	 */
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	
	/**
	 * 获取 操作时间*.
	 *
	 * @return the 操作时间*
	 */
	public Date getOperateTime() {
		return operateTime;
	}
	
	/**
	 * 设置 操作时间*.
	 *
	 * @param operateTime the new 操作时间*
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	
	/**
	 * 获取 装车封签备注*.
	 *
	 * @return the 装车封签备注*
	 */
	public String getSealOrgMemo() {
		return sealOrgMemo;
	}
	
	/**
	 * 设置 装车封签备注*.
	 *
	 * @param sealOrgMemo the new 装车封签备注*
	 */
	public void setSealOrgMemo(String sealOrgMemo) {
		this.sealOrgMemo = sealOrgMemo;
	}
	
	/**
	 * 获取 卸车封签备注*.
	 *
	 * @return the 卸车封签备注*
	 */
	public String getCheckOrgMemo() {
		return checkOrgMemo;
	}
	
	/**
	 * 设置 卸车封签备注*.
	 *
	 * @param checkOrgMemo the new 卸车封签备注*
	 */
	public void setCheckOrgMemo(String checkOrgMemo) {
		this.checkOrgMemo = checkOrgMemo;
	}
	
	/**
	 * 获取 oA差错编号*.
	 *
	 * @return the oA差错编号*
	 */
	public String getOaErrorNo() {
		return oaErrorNo;
	}
	
	/**
	 * 设置 oA差错编号*.
	 *
	 * @param oaErrorNo the new oA差错编号*
	 */
	public void setOaErrorNo(String oaErrorNo) {
		this.oaErrorNo = oaErrorNo;
	}
	
	/**
	 * 获取 pDA设备号*.
	 *
	 * @return the pDA设备号*
	 */
	public String getPdaDeviceNo() {
		return pdaDeviceNo;
	}
	
	/**
	 * 设置 pDA设备号*.
	 *
	 * @param pdaDeviceNo the new pDA设备号*
	 */
	public void setPdaDeviceNo(String pdaDeviceNo) {
		this.pdaDeviceNo = pdaDeviceNo;
	}
	
	/**
	 * 获取 任务车辆明细_ID*.
	 *
	 * @return the 任务车辆明细_ID*
	 */
	public String getTruckTaskDetailId() {
		return truckTaskDetailId;
	}
	
	/**
	 * 设置 任务车辆明细_ID*.
	 *
	 * @param truckTaskDetailId the new 任务车辆明细_ID*
	 */
	public void setTruckTaskDetailId(String truckTaskDetailId) {
		this.truckTaskDetailId = truckTaskDetailId;
	}
	
	/**
	 * 获取 车辆放行ID*.
	 *
	 * @return the 车辆放行ID*
	 */
	public String getTruckDepartId() {
		return truckDepartId;
	}
	
	/**
	 * 设置 车辆放行ID*.
	 *
	 * @param truckDepartId the new 车辆放行ID*
	 */
	public void setTruckDepartId(String truckDepartId) {
		this.truckDepartId = truckDepartId;
	}
	
	/**
	 * 获取 司机名称*.
	 *
	 * @return the 司机名称*
	 */
	public String getDriverName() {
		return driverName;
	}
	
	/**
	 * 设置 司机名称*.
	 *
	 * @param driverName the new 司机名称*
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	
	/**
	 * 获取 司机工号*.
	 *
	 * @return the 司机工号*
	 */
	public String getDriverCode() {
		return driverCode;
	}
	
	/**
	 * 设置 司机工号*.
	 *
	 * @param driverCode the new 司机工号*
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}
	
	/**
	 * 获取 司机电话*.
	 *
	 * @return the 司机电话*
	 */
	public String getDriverPhone() {
		return driverPhone;
	}
	
	/**
	 * 设置 司机电话*.
	 *
	 * @param driverPhone the new 司机电话*
	 */
	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	public String getCheckedPdaDeviceNo() {
		return checkedPdaDeviceNo;
	}

	public void setCheckedPdaDeviceNo(String checkedPdaDeviceNo) {
		this.checkedPdaDeviceNo = checkedPdaDeviceNo;
	}

	public String getBindType() {
		return bindType;
	}

	public void setBindType(String bindType) {
		this.bindType = bindType;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	/**
	 * @return set the isTransientState
	 */
	public String getIsTransientState() {
		return isTransientState;
	}

	/**
	 * @param isTransientState the isTransientState to set
	 */
	public void setIsTransientState(String isTransientState) {
		this.isTransientState = isTransientState;
	}

	/**
	 * @return listedNo
	 * @see
	 */
	public final String getTrailerNo() {
		return trailerNo;
	}

	/**
	 * @param listedNo
	 * @see
	 */
	public final void setTrailerNo(String trailerNo) {
		this.trailerNo = trailerNo;
	}

}