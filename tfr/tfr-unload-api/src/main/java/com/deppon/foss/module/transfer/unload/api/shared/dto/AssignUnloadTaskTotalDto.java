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
 *  PROJECT NAME  : tfr-unload-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/shared/dto/AssignUnloadTaskDto.java
 *  
 *  FILE NAME          :AssignUnloadTaskDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-unload-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.api.shared.domain
 * FILE    NAME: AssignUnloadTask.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;
import java.util.List;


/**
 * 分配卸车任务
 * @author dp-duyi
 * @date 2012-10-18 下午5:05:12
 */
public class AssignUnloadTaskTotalDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -480385382321794553L;
	/**id*/
	private String id;
	/**beCanceled*/
	private String beCanceled;
	/**createUserName*/
	private String createUserName;
	/**createUserCode*/
	private String createUserCode;
	/**modifyUserName*/
	private String modifyUserName;
	/**modifyUserCode*/
	private String modifyUserCode;
	/**createOrgCode*/
	private String createOrgCode;
	/**createOrgName*/
	private String createOrgName;
	/**createTime*/
	private String createTime;
	/**modifyTime*/
	private String modifyTime;
	private String vehicleNo;       
	private String line;     
	private String unloadType;   
	private String arriveTime;
	private String vehicleType;        
	private String loaderName;         
	private String loaderCode;    
	private String loaderOrgName;
	private String loaderOrgCode;
	private String platformNo;
	private String platformVirtualCode;
	private int billQtyTotal;       
	private double weightTotal;        
	private double volumeTotal;        
	private int fastWayBillQtyTotal;
	private int goodsQtyTotal; 
	private String taskState;
	private int afWayBillQtyTotal;
	private double afVolumeTotal;
	private double afWeightTotal;
	private int flfWayBillQtyTotal;
	private double flfVolumeTotal;
	private double flfWeightTotal;
	private int fsfWayBillQtyTotal;
	private double fsfVolumeTotal;
	private double fsfWeightTotal;
	/**快递运单票数*/
	private int expressWayBillQty;
	//运输性质
	private String productCode;
	//出发部门
	private String departOrg;
	//总票数
	private int wayBillQtyTotal;
	//272681 航班号
	private String flightNo;  
	//已分配票数
	private int assignedBillQty;
	//快递票数
	private int expressBillQty;
	
	public int getAfWayBillQtyTotal() {
		return afWayBillQtyTotal;
	}
	public void setAfWayBillQtyTotal(int afWayBillQtyTotal) {
		this.afWayBillQtyTotal = afWayBillQtyTotal;
	}
	public double getAfVolumeTotal() {
		return afVolumeTotal;
	}
	public void setAfVolumeTotal(double afVolumeTotal) {
		this.afVolumeTotal = afVolumeTotal;
	}
	public double getAfWeightTotal() {
		return afWeightTotal;
	}
	public void setAfWeightTotal(double afWeightTotal) {
		this.afWeightTotal = afWeightTotal;
	}
	public int getFlfWayBillQtyTotal() {
		return flfWayBillQtyTotal;
	}
	public void setFlfWayBillQtyTotal(int flfWayBillQtyTotal) {
		this.flfWayBillQtyTotal = flfWayBillQtyTotal;
	}
	public double getFlfVolumeTotal() {
		return flfVolumeTotal;
	}
	public void setFlfVolumeTotal(double flfVolumeTotal) {
		this.flfVolumeTotal = flfVolumeTotal;
	}
	public double getFlfWeightTotal() {
		return flfWeightTotal;
	}
	public void setFlfWeightTotal(double flfWeightTotal) {
		this.flfWeightTotal = flfWeightTotal;
	}
	public int getFsfWayBillQtyTotal() {
		return fsfWayBillQtyTotal;
	}
	public void setFsfWayBillQtyTotal(int fsfWayBillQtyTotal) {
		this.fsfWayBillQtyTotal = fsfWayBillQtyTotal;
	}
	public double getFsfVolumeTotal() {
		return fsfVolumeTotal;
	}
	public void setFsfVolumeTotal(double fsfVolumeTotal) {
		this.fsfVolumeTotal = fsfVolumeTotal;
	}
	public double getFsfWeightTotal() {
		return fsfWeightTotal;
	}
	public void setFsfWeightTotal(double fsfWeightTotal) {
		this.fsfWeightTotal = fsfWeightTotal;
	}
	//查询条件
	/**billNo*/
	private String billNo;
	/**arriveTimeBegin*/
	private String arriveTimeBegin;
	/**arriveTimeEnd*/
	private String arriveTimeEnd;
	/**destOrgCode*/
	private String destOrgCode;
	private List<String> destOrgCodes;
		
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getArriveTimeBegin() {
		return arriveTimeBegin;
	}
	public void setArriveTimeBegin(String arriveTimeBegin) {
		this.arriveTimeBegin = arriveTimeBegin;
	}
	public String getArriveTimeEnd() {
		return arriveTimeEnd;
	}
	public void setArriveTimeEnd(String arriveTimeEnd) {
		this.arriveTimeEnd = arriveTimeEnd;
	}
	public String getDestOrgCode() {
		return destOrgCode;
	}
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}
	public List<String> getDestOrgCodes() {
		return destOrgCodes;
	}
	public void setDestOrgCodes(List<String> destOrgCodes) {
		this.destOrgCodes = destOrgCodes;
	}
	public String getPlatformVirtualCode() {
		return platformVirtualCode;
	}
	public void setPlatformVirtualCode(String platformVirtualCode) {
		this.platformVirtualCode = platformVirtualCode;
	}
	public String getLoaderOrgName() {
		return loaderOrgName;
	}
	public void setLoaderOrgName(String loaderOrgName) {
		this.loaderOrgName = loaderOrgName;
	}
	public String getLoaderOrgCode() {
		return loaderOrgCode;
	}
	public void setLoaderOrgCode(String loaderOrgCode) {
		this.loaderOrgCode = loaderOrgCode;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBeCanceled() {
		return beCanceled;
	}
	public void setBeCanceled(String beCanceled) {
		this.beCanceled = beCanceled;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getCreateUserCode() {
		return createUserCode;
	}
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	public String getModifyUserName() {
		return modifyUserName;
	}
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}
	public String getModifyUserCode() {
		return modifyUserCode;
	}
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}
	public String getCreateOrgCode() {
		return createOrgCode;
	}
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}
	public String getCreateOrgName() {
		return createOrgName;
	}
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public String getUnloadType() {
		return unloadType;
	}
	public void setUnloadType(String unloadType) {
		this.unloadType = unloadType;
	}
	public String getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public String getLoaderName() {
		return loaderName;
	}
	public void setLoaderName(String loaderName) {
		this.loaderName = loaderName;
	}
	public String getLoaderCode() {
		return loaderCode;
	}
	public void setLoaderCode(String loaderCode) {
		this.loaderCode = loaderCode;
	}
	public String getPlatformNo() {
		return platformNo;
	}
	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}
	public int getBillQtyTotal() {
		return billQtyTotal;
	}
	public void setBillQtyTotal(int billQtyTotal) {
		this.billQtyTotal = billQtyTotal;
	}
	public double getWeightTotal() {
		return weightTotal;
	}
	public void setWeightTotal(double weightTotal) {
		this.weightTotal = weightTotal;
	}
	public double getVolumeTotal() {
		return volumeTotal;
	}
	public void setVolumeTotal(double volumeTotal) {
		this.volumeTotal = volumeTotal;
	}
	public int getFastWayBillQtyTotal() {
		return fastWayBillQtyTotal;
	}
	public void setFastWayBillQtyTotal(int fastWayBillQtyTotal) {
		this.fastWayBillQtyTotal = fastWayBillQtyTotal;
	}
	public int getGoodsQtyTotal() {
		return goodsQtyTotal;
	}
	public void setGoodsQtyTotal(int goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}
	public String getTaskState() {
		return taskState;
	}
	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}
	/**
	 * 
	 * get departOrg
	 * @author alfred
	 * @date 2014-1-14 下午2:42:07
	 * @return
	 * @see
	 */
	public String getProductCode() {
		return productCode;
	}
	/**
	 * 
	 * set setProductCode
	 * @author alfred
	 * @date 2014-1-14 下午2:42:21
	 * @param productCode
	 * @see
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	/**
	 * 
	 * get departOrg 
	 * @author alfred
	 * @date 2014-1-14 下午2:42:17
	 * @return
	 * @see
	 */
	public String getDepartOrg() {
		return departOrg;
	}
	/**
	 * 
	 * set setDepartOrg 
	 * @author alfred
	 * @date 2014-1-14 下午2:42:25
	 * @param departOrg
	 * @see
	 */
	public void setDepartOrg(String departOrg) {
		this.departOrg = departOrg;
	}
	public int getWayBillQtyTotal() {
		return wayBillQtyTotal;
	}
	public void setWayBillQtyTotal(int wayBillQtyTotal) {
		this.wayBillQtyTotal = wayBillQtyTotal;
	}
	public String getFlightNo() {
		return flightNo;
	}
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	public int getExpressWayBillQty() {
		return expressWayBillQty;
	}
	public void setExpressWayBillQty(int expressWayBillQty) {
		this.expressWayBillQty = expressWayBillQty;
	}
	public int getAssignedBillQty() {
		return assignedBillQty;
	}
	public void setAssignedBillQty(int assignedBillQty) {
		this.assignedBillQty = assignedBillQty;
	}
	public int getExpressBillQty() {
		return expressBillQty;
	}
	public void setExpressBillQty(int expressBillQty) {
		this.expressBillQty = expressBillQty;
	}
	
	
}