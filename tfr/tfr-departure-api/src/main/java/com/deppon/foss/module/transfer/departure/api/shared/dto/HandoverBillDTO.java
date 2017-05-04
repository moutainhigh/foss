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
 *  PROJECT NAME  : tfr-departure-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/shared/dto/HandoverBillDTO.java
 *  
 *  FILE NAME          :HandoverBillDTO.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * 
 * 通过运单号取得交接单的信息
 * @author foss-liubinbin(for IBM)
 * @date 2013-1-8 下午12:50:14
 */
public class HandoverBillDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/************交接单号*************/
	private String handoverNo;
	/************运单号*************/
	private String waybillNo;
	/************出发部门编码*************/
	private String origOrgCode;
	/************出发部门名称*************/
	private String origOrgname;
	/************到达部门编码*************/
	private String destOrgCode;
	/************到达部门名称*************/
	private String destOrgName;
	/************交接单状态*************/
	private int handoverbillStatus;
	/************实际出发时间*************/
	private Date actualDepartTime;
	/************实际到达时间*************/
	private Date actualArriveTime;
	/************车牌号*************/
	private String vehicleNo;
	/************预计到达时间*************/
	private Date planArriveTime;
	/************司机姓名*************/
	private String driverName;
	/*************开单件数*****/
	private int goodsQty;
	/***********扫描件数*********/
	private int scanGoodsQty;
	/***********开单时间(用于查询条件)****************/
	private Date billtime;
	/***********流水号(用于查询条件)****************/
	private String serialNo;
	/************卸车部门名称*************/
	private String unloadOrgName;
	/************卸车部门编码*************/
	private String unloadOrgCode;
	/************卸车人编码*************/
	private String loaderCode;
	/************卸车人名称*************/
	private String loaderName;
	/***********卸车时间****************/
	private Date taskBeginTime;
	/************
	 * tfr.t_opt_truck_depart 手工放行人
	 * *************/
	private String manualDepartUserCode;
	
	/**
	 * t_opt_truck_task_detail 中的手动放行人
	 * @author alfred
	 */
	private String manualDepartUserName;
	/************pda放行人*************/
	private String pdaDepartUserCode;
	/************gps放行时间*************/
	private String gpsDepartTime;
	/************手工到达人*************/
	private String manualArriveUserCode;
	/************pda到达人*************/
	private String pdaArriveUserCode;
	/************pda到达部门*************/
	private String pdaArriveOrgCode;
	/************gps到达时间*************/
	private String gpsArriveTime;
	/************制单时间*************/
	private Date createTime;
	/************制单人************/
	private String createUserName;
	/************交接件数*************/
	private Integer handoverGoodsQty;
	/************包装部门名称*************/
	private String orgName;
	/************包装部门编码*************/
	private String orgCode;
	/************包装件数*************/
	private Integer packedNum;
	/************入库类型*************/
	private String inStockType;
	/************入库类型（复数）*************/
	private List<String> inStockTypes;
	/************出库类型*************/
	private String outStockType;
	/************出库类型（复数）*************/
	private List<String> outStockTypes;
	/************交接单类型*************/
	private String handoverType;
	
	/************卸车类型*************/
	private String unloadType;
	
	private String billNo;
	
	/**卸车任务 zwd 200968  卸车任务编号 20150105*/
	private String unloadTaskNo ;
	/**卸车任务 zwd 200968  卸车任务编号 20150105*/
	public String getUnloadTaskNo() {
		return unloadTaskNo;
	}
	/************点单任务状态272681*************/
	private String orderTaskState;
	/************点单任务开始时间*************/
	private Date orderStartTime;
	/************点单任务结束时间*************/
	private Date orderEndTime;
	/************点单任务创建部门*************/
	private String createOrgCode;
	/************点单任务创建部门名称*************/
	private String createOrgName;
	/************点单人*************/
	private String orderManCode;
	private String orderManName;
	/************点单件数*************/
	private int orderGoodsQty;
	/************点单任务编号*************/
	private String orderTaskNo;
	/**卸车任务 zwd 200968  卸车任务编号 20150105*/
	public void setUnloadTaskNo(String unloadTaskNo) {
		this.unloadTaskNo = unloadTaskNo;
	}
	
	// 上分拣 zwd  200968  
	/**操作部门名称 */
	private String  operationOrgName;
	/**操作部门编码 */
	private String operationOrgCode;
	/** 操作人姓名*/
	private String operationName;
	/** 操作人编码 */
	private String operationCode;
	
	// 包信息管理 zwd  200968  
	/**包号 */
	private String packageNo;
	/**包状态*/
	private String status;
	/**出发部门 */
	private String departOrgName;
	/**操作人 */
	private String createUserCode;
	/**建包时间 zwd 200968*/
	private Date endTime;
	
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * 定位编号 zwd 200968
	 */
	private String stockPositionNumber;
	/**
	 * 定位编号 zwd 200968
	 */
	public String getStockPositionNumber() {
		return stockPositionNumber;
	}
	/**
	 * 定位编号 zwd 200968
	 */
	public void setStockPositionNumber(String stockPositionNumber) {
		this.stockPositionNumber = stockPositionNumber;
	}
	public String getPackageNo() {
		return packageNo;
	}

	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getDepartOrgName() {
		return departOrgName;
	}

	public void setDepartOrgName(String departOrgName) {
		this.departOrgName = departOrgName;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getOperationOrgName() {
		return operationOrgName;
	}

	public void setOperationOrgName(String operationOrgName) {
		this.operationOrgName = operationOrgName;
	}

	public String getOperationOrgCode() {
		return operationOrgCode;
	}

	public void setOperationOrgCode(String operationOrgCode) {
		this.operationOrgCode = operationOrgCode;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getOperationCode() {
		return operationCode;
	}

	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}

	/**叉车司机扫描 tfr.t_opt_trayscan_task**/
	/**叉车司机**/
	private String forkliftDriverName;
	/**叉车司所属部门**/
	private String forkliftOrgName;
	/**任务号**/
	private String taskNo;
	/**扫描时间*/
	private Date trayScanaTime;

	/**
	 * 获取 *********流水号(用于查询条件)***************.
	 *
	 * @return the *********流水号(用于查询条件)***************
	 */
	public String getSerialNo(){
		return serialNo;
	}

	/**
	 * 设置 *********流水号(用于查询条件)***************.
	 *
	 * @param serialNo the new *********流水号(用于查询条件)***************
	 */
	public void setSerialNo(String serialNo){
		this.serialNo = serialNo;
	}

	/**
	 * 获取 **********司机姓名************.
	 *
	 * @return the **********司机姓名************
	 */
	public String getDriverName(){
		return driverName;
	}

	/**
	 * 设置 **********司机姓名************.
	 *
	 * @param driverName the new **********司机姓名************
	 */
	public void setDriverName(String driverName){
		this.driverName = driverName;
	}

	/**
	 * 获取 **********交接单号************.
	 *
	 * @return the **********交接单号************
	 */
	public String getHandoverNo(){
		return handoverNo;
	}
	
	/**
	 * 设置 **********交接单号************.
	 *
	 * @param handoverNo the new **********交接单号************
	 */
	public void setHandoverNo(String handoverNo){
		this.handoverNo = handoverNo;
	}
	
	/**
	 * 获取 **********运单号************.
	 *
	 * @return the **********运单号************
	 */
	public String getWaybillNo(){
		return waybillNo;
	}
	
	/**
	 * 设置 **********运单号************.
	 *
	 * @param waybillNo the new **********运单号************
	 */
	public void setWaybillNo(String waybillNo){
		this.waybillNo = waybillNo;
	}
	
	/**
	 * 获取 **********出发部门编码************.
	 *
	 * @return the **********出发部门编码************
	 */
	public String getOrigOrgCode(){
		return origOrgCode;
	}
	
	/**
	 * 设置 **********出发部门编码************.
	 *
	 * @param origOrgCode the new **********出发部门编码************
	 */
	public void setOrigOrgCode(String origOrgCode){
		this.origOrgCode = origOrgCode;
	}
	
	/**
	 * 获取 **********出发部门名称************.
	 *
	 * @return the **********出发部门名称************
	 */
	public String getOrigOrgname(){
		return origOrgname;
	}
	
	/**
	 * 设置 **********出发部门名称************.
	 *
	 * @param origOrgname the new **********出发部门名称************
	 */
	public void setOrigOrgname(String origOrgname){
		this.origOrgname = origOrgname;
	}
	
	/**
	 * 获取 **********到达部门编码************.
	 *
	 * @return the **********到达部门编码************
	 */
	public String getDestOrgCode(){
		return destOrgCode;
	}
	
	/**
	 * 设置 **********到达部门编码************.
	 *
	 * @param destOrgCode the new **********到达部门编码************
	 */
	public void setDestOrgCode(String destOrgCode){
		this.destOrgCode = destOrgCode;
	}
	
	/**
	 * 获取 **********到达部门名称************.
	 *
	 * @return the **********到达部门名称************
	 */
	public String getDestOrgName(){
		return destOrgName;
	}
	
	/**
	 * 设置 **********到达部门名称************.
	 *
	 * @param destOrgName the new **********到达部门名称************
	 */
	public void setDestOrgName(String destOrgName){
		this.destOrgName = destOrgName;
	}
	
	/**
	 * 获取 **********交接单状态************.
	 *
	 * @return the **********交接单状态************
	 */
	public int getHandoverbillStatus(){
		return handoverbillStatus;
	}
	
	/**
	 * 设置 **********交接单状态************.
	 *
	 * @param handoverbillStatus the new **********交接单状态************
	 */
	public void setHandoverbillStatus(int handoverbillStatus){
		this.handoverbillStatus = handoverbillStatus;
	}
	
	/**
	 * 获取 **********实际出发时间************.
	 *
	 * @return the **********实际出发时间************
	 */
	public Date getActualDepartTime(){
		return actualDepartTime;
	}
	
	/**
	 * 设置 **********实际出发时间************.
	 *
	 * @param actualDepartTime the new **********实际出发时间************
	 */
	public void setActualDepartTime(Date actualDepartTime){
		this.actualDepartTime = actualDepartTime;
	}
	
	/**
	 * 获取 **********实际到达时间************.
	 *
	 * @return the **********实际到达时间************
	 */
	public Date getActualArriveTime(){
		return actualArriveTime;
	}
	
	/**
	 * 设置 **********实际到达时间************.
	 *
	 * @param actualArriveTime the new **********实际到达时间************
	 */
	public void setActualArriveTime(Date actualArriveTime){
		this.actualArriveTime = actualArriveTime;
	}
	
	/**
	 * 获取 **********车牌号************.
	 *
	 * @return the **********车牌号************
	 */
	public String getVehicleNo(){
		return vehicleNo;
	}
	
	/**
	 * 设置 **********车牌号************.
	 *
	 * @param vehicleNo the new **********车牌号************
	 */
	public void setVehicleNo(String vehicleNo){
		this.vehicleNo = vehicleNo;
	}
	
	/**
	 * 获取 **********预计到达时间************.
	 *
	 * @return the **********预计到达时间************
	 */
	public Date getPlanArriveTime(){
		return planArriveTime;
	}
	
	/**
	 * 设置 **********预计到达时间************.
	 *
	 * @param planArriveTime the new **********预计到达时间************
	 */
	public void setPlanArriveTime(Date planArriveTime){
		this.planArriveTime = planArriveTime;
	}

	/**
	 * 获取 ***********开单件数****.
	 *
	 * @return the ***********开单件数****
	 */
	public int getGoodsQty(){
		return goodsQty;
	}

	/**
	 * 设置 ***********开单件数****.
	 *
	 * @param goodsQty the new ***********开单件数****
	 */
	public void setGoodsQty(int goodsQty){
		this.goodsQty = goodsQty;
	}

	/**
	 * 获取 *********扫描件数********.
	 *
	 * @return the *********扫描件数********
	 */
	public int getScanGoodsQty(){
		return scanGoodsQty;
	}

	/**
	 * 设置 *********扫描件数********.
	 *
	 * @param scanGoodsQty the new *********扫描件数********
	 */
	public void setScanGoodsQty(int scanGoodsQty){
		this.scanGoodsQty = scanGoodsQty;
	}

	/**
	 * 获取 *********开单时间(用于查询条件)***************.
	 *
	 * @return the *********开单时间(用于查询条件)***************
	 */
	public Date getBilltime(){
		return billtime;
	}

	/**
	 * 设置 *********开单时间(用于查询条件)***************.
	 *
	 * @param billtime the new *********开单时间(用于查询条件)***************
	 */
	public void setBilltime(Date billtime){
		this.billtime = billtime;
	}

	

	public String getUnloadOrgName() {
		return unloadOrgName;
	}

	public void setUnloadOrgName(String unloadOrgName) {
		this.unloadOrgName = unloadOrgName;
	}

	public String getUnloadOrgCode() {
		return unloadOrgCode;
	}

	public void setUnloadOrgCode(String unloadOrgCode) {
		this.unloadOrgCode = unloadOrgCode;
	}

	public String getLoaderCode() {
		return loaderCode;
	}

	public void setLoaderCode(String loaderCode) {
		this.loaderCode = loaderCode;
	}

	public String getLoaderName() {
		return loaderName;
	}

	public void setLoaderName(String loaderName) {
		this.loaderName = loaderName;
	}

	public Date getTaskBeginTime() {
		return taskBeginTime;
	}

	public void setTaskBeginTime(Date taskBeginTime) {
		this.taskBeginTime = taskBeginTime;
	}

	public String getManualDepartUserCode() {
		return manualDepartUserCode;
	}

	public void setManualDepartUserCode(String manualDepartUserCode) {
		this.manualDepartUserCode = manualDepartUserCode;
	}

	/**
	 * 
	 * 获取 手工放行人
	 * @author alfred
	 * @date 2013-8-13 下午2:32:55
	 * @return
	 * @see
	 */
	public String getManualDepartUserName() {
		return manualDepartUserName;
	}

	/**
	 * 
	 * 设置 手工放行人
	 * @author alfred
	 * @date 2013-8-13 下午2:33:02
	 * @param manualDepartUserName
	 * @see
	 */
	public void setManualDepartUserName(String manualDepartUserName) {
		this.manualDepartUserName = manualDepartUserName;
	}

	public String getPdaDepartUserCode() {
		return pdaDepartUserCode;
	}

	public void setPdaDepartUserCode(String pdaDepartUserCode) {
		this.pdaDepartUserCode = pdaDepartUserCode;
	}

	public String getGpsDepartTime() {
		return gpsDepartTime;
	}

	public void setGpsDepartTime(String gpsDepartTime) {
		this.gpsDepartTime = gpsDepartTime;
	}

	public String getManualArriveUserCode() {
		return manualArriveUserCode;
	}

	public void setManualArriveUserCode(String manualArriveUserCode) {
		this.manualArriveUserCode = manualArriveUserCode;
	}

	public String getPdaArriveUserCode() {
		return pdaArriveUserCode;
	}

	public void setPdaArriveUserCode(String pdaArriveUserCode) {
		this.pdaArriveUserCode = pdaArriveUserCode;
	}

	public String getGpsArriveTime() {
		return gpsArriveTime;
	}

	public void setGpsArriveTime(String gpsArriveTime) {
		this.gpsArriveTime = gpsArriveTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Integer getHandoverGoodsQty() {
		return handoverGoodsQty;
	}

	public void setHandoverGoodsQty(Integer handoverGoodsQty) {
		this.handoverGoodsQty = handoverGoodsQty;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getPackedNum() {
		return packedNum;
	}

	public void setPackedNum(Integer packedNum) {
		this.packedNum = packedNum;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getInStockType() {
		return inStockType;
	}

	public void setInStockType(String inStockType) {
		this.inStockType = inStockType;
	}

	public String getOutStockType() {
		return outStockType;
	}

	public void setOutStockType(String outStockType) {
		this.outStockType = outStockType;
	}

	public String getHandoverType() {
		return handoverType;
	}

	public void setHandoverType(String handoverType) {
		this.handoverType = handoverType;
	}

	public String getUnloadType() {
		return unloadType;
	}

	public void setUnloadType(String unloadType) {
		this.unloadType = unloadType;
	}

	public List<String> getInStockTypes() {
		return inStockTypes;
	}

	public void setInStockTypes(List<String> inStockTypes) {
		this.inStockTypes = inStockTypes;
	}

	public List<String> getOutStockTypes() {
		return outStockTypes;
	}

	public void setOutStockTypes(List<String> outStockTypes) {
		this.outStockTypes = outStockTypes;
	}

	public String getPdaArriveOrgCode() {
		return pdaArriveOrgCode;
	}

	public void setPdaArriveOrgCode(String pdaArriveOrgCode) {
		this.pdaArriveOrgCode = pdaArriveOrgCode;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getForkliftDriverName() {
		return forkliftDriverName;
	}

	public void setForkliftDriverName(String forkliftDriverName) {
		this.forkliftDriverName = forkliftDriverName;
	}

	public String getForkliftOrgName() {
		return forkliftOrgName;
	}

	public void setForkliftOrgName(String forkliftOrgName) {
		this.forkliftOrgName = forkliftOrgName;
	}

	public String getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	public Date getTrayScanaTime() {
		return trayScanaTime;
	}

	public void setTrayScanaTime(Date trayScanaTime) {
		this.trayScanaTime = trayScanaTime;
	}
	public String getOrderTaskState() {
		return orderTaskState;
	}
	public void setOrderTaskState(String orderTaskState) {
		this.orderTaskState = orderTaskState;
	}
	public Date getOrderStartTime() {
		return orderStartTime;
	}
	public void setOrderStartTime(Date orderStartTime) {
		this.orderStartTime = orderStartTime;
	}
	public Date getOrderEndTime() {
		return orderEndTime;
	}
	public void setOrderEndTime(Date orderEndTime) {
		this.orderEndTime = orderEndTime;
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
	public String getOrderManCode() {
		return orderManCode;
	}
	public void setOrderManCode(String orderManCode) {
		this.orderManCode = orderManCode;
	}
	public String getOrderManName() {
		return orderManName;
	}
	public void setOrderManName(String orderManName) {
		this.orderManName = orderManName;
	}
	public int getOrderGoodsQty() {
		return orderGoodsQty;
	}
	public void setOrderGoodsQty(int orderGoodsQty) {
		this.orderGoodsQty = orderGoodsQty;
	}
	public String getOrderTaskNo() {
		return orderTaskNo;
	}
	public void setOrderTaskNo(String orderTaskNo) {
		this.orderTaskNo = orderTaskNo;
	}

}