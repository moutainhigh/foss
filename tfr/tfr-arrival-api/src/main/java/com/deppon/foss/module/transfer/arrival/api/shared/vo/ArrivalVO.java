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
 *  PROJECT NAME  : tfr-arrival-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/arrival/api/shared/vo/ArrivalVO.java
 *  
 *  FILE NAME          :ArrivalVO.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * 
 */
package com.deppon.foss.module.transfer.arrival.api.shared.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.transfer.arrival.api.shared.domain.ArrivalEntity;
import com.deppon.foss.module.transfer.arrival.api.shared.domain.QueryArrivalEntity;
import com.deppon.foss.module.transfer.arrival.api.shared.dto.LeasedTruckDTO;
import com.deppon.foss.module.transfer.arrival.api.shared.dto.OperationDTO;
import com.deppon.foss.module.transfer.arrival.api.shared.dto.PlatformDTO;
import com.deppon.foss.module.transfer.arrival.api.shared.dto.QueryPlatformDTO;
import com.deppon.foss.module.transfer.arrival.api.shared.dto.TruckActionDetailDto;
import com.deppon.foss.module.transfer.arrival.api.shared.dto.VehicleInfoDTO;
/**
 * 到达展示类
 */
public class ArrivalVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -293285666591619830L;

	/********* 本部门到达到达列表数据 **********/
	List<ArrivalEntity> arrivalList = new ArrayList<ArrivalEntity>();
	
	/********* 本部门出发列表数据 **********/
	List<ArrivalEntity> departureList = new ArrayList<ArrivalEntity>();

	/********* 查询条件 *************/
	QueryArrivalEntity queryArriveEntity = new QueryArrivalEntity();
	
	/********* 查询条件 *************/
	QueryArrivalEntity queryDepartureEntity = new QueryArrivalEntity();

	/********* 预分配月台时（显示车辆相关信息） *************/
	VehicleInfoDTO vehicleInfoDTO = new VehicleInfoDTO();

	/********* 查询的月台信息 ************************/
	List<PlatformDTO> platformList = new ArrayList<PlatformDTO>();

	/********* 操作到达数据时传到后台用来验证，或取值的dto *****/
	OperationDTO operationDTO = new OperationDTO();

	/********** 操作时返回错误提示 ************/
	private String errorCode;

	/********** 外请车（当前部门为出发）信息 ************/
	LeasedTruckDTO leasedDepartureTruckDTO = new LeasedTruckDTO();
	
	/********** 外请车信息 （当前部门为到达）************/
	LeasedTruckDTO leasedArriveTruckDTO = new LeasedTruckDTO();

	/********** 查询月台的条件 ************/
	QueryPlatformDTO queryPlatformDTO = new QueryPlatformDTO();
	
	/********** 根据所属部门取得上级部门 ************/
	ArrivalEntity arrivalEntity = new ArrivalEntity();
	
	/********** 导出的时候传进的ID（复数） ************/
	String ids;
	/********** 判断是否有代办************/
	String hasAgency;
	/**********到达时需要更新的所有的任务车辆明细ID的集合************/
	List<String> detailIds;
	
	/**返回前台的字符串信息**/
	private String msg;
	
	/**用户传到处理待办页面的交接单号**/
	private String handOverBillNo;
	
	/**
	 * 车辆任务操作记录
	 */
	private List<TruckActionDetailDto> truckActionDetailDtos;
	
	/********* 本部门到达确认到达校验列表数据 **********/
	List<ArrivalEntity> arrivalConfirmList = new ArrayList<ArrivalEntity>();
	
	/********* 本部门出发出发到达校验列表数据 **********/
	List<ArrivalEntity> departureConfirmList = new ArrayList<ArrivalEntity>();
	
	public List<ArrivalEntity> getArrivalConfirmList() {
		return arrivalConfirmList;
	}

	public void setArrivalConfirmList(List<ArrivalEntity> arrivalConfirmList) {
		this.arrivalConfirmList = arrivalConfirmList;
	}

	public List<ArrivalEntity> getDepartureConfirmList() {
		return departureConfirmList;
	}

	public void setDepartureConfirmList(List<ArrivalEntity> departureConfirmList) {
		this.departureConfirmList = departureConfirmList;
	}

	/************************************************/

	public List<ArrivalEntity> getArrivalList(){
		return arrivalList;
	}

	/**
	 * 设置 ******* 到达列表数据 *********.
	 *
	 * @param arrivalList the new ******* 到达列表数据 *********
	 */
	public void setArrivalList(List<ArrivalEntity> arrivalList){
		this.arrivalList = arrivalList;
	}

	

	public List<ArrivalEntity> getDepartureList() {
		return departureList;
	}

	public void setDepartureList(List<ArrivalEntity> departureList) {
		this.departureList = departureList;
	}

	public QueryArrivalEntity getQueryArriveEntity() {
		return queryArriveEntity;
	}

	public void setQueryArriveEntity(QueryArrivalEntity queryArriveEntity) {
		this.queryArriveEntity = queryArriveEntity;
	}

	public QueryArrivalEntity getQueryDepartureEntity() {
		return queryDepartureEntity;
	}

	public void setQueryDepartureEntity(QueryArrivalEntity queryDepartureEntity) {
		this.queryDepartureEntity = queryDepartureEntity;
	}

	/**
	 * 获取 ******* 预分配月台时（显示车辆相关信息） ************.
	 *
	 * @return the ******* 预分配月台时（显示车辆相关信息） ************
	 */
	public VehicleInfoDTO getVehicleInfoDTO(){
		return vehicleInfoDTO;
	}

	/**
	 * 设置 ******* 预分配月台时（显示车辆相关信息） ************.
	 *
	 * @param vehicleInfoDTO the new ******* 预分配月台时（显示车辆相关信息） ************
	 */
	public void setVehicleInfoDTO(VehicleInfoDTO vehicleInfoDTO){
		this.vehicleInfoDTO = vehicleInfoDTO;
	}

	/**
	 * 获取 ******* 查询的月台信息 ***********************.
	 *
	 * @return the ******* 查询的月台信息 ***********************
	 */
	public List<PlatformDTO> getPlatformList(){
		return platformList;
	}

	/**
	 * 设置 ******* 查询的月台信息 ***********************.
	 *
	 * @param platformList the new ******* 查询的月台信息 ***********************
	 */
	public void setPlatformList(List<PlatformDTO> platformList){
		this.platformList = platformList;
	}

	/**
	 * 获取 ******* 操作到达数据时传到后台用来验证，或取值的dto ****.
	 *
	 * @return the ******* 操作到达数据时传到后台用来验证，或取值的dto ****
	 */
	public OperationDTO getOperationDTO(){
		return operationDTO;
	}

	/**
	 * 设置 ******* 操作到达数据时传到后台用来验证，或取值的dto ****.
	 *
	 * @param operationDTO the new ******* 操作到达数据时传到后台用来验证，或取值的dto ****
	 */
	public void setOperationDTO(OperationDTO operationDTO){
		this.operationDTO = operationDTO;
	}

	/**
	 * 获取 ******** 操作时返回错误提示 ***********.
	 *
	 * @return the ******** 操作时返回错误提示 ***********
	 */
	public String getErrorCode(){
		return errorCode;
	}

	/**
	 * 设置 ******** 操作时返回错误提示 ***********.
	 *
	 * @param errorCode the new ******** 操作时返回错误提示 ***********
	 */
	public void setErrorCode(String errorCode){
		this.errorCode = errorCode;
	}

	

	public LeasedTruckDTO getLeasedDepartureTruckDTO() {
		return leasedDepartureTruckDTO;
	}

	public void setLeasedDepartureTruckDTO(LeasedTruckDTO leasedDepartureTruckDTO) {
		this.leasedDepartureTruckDTO = leasedDepartureTruckDTO;
	}

	public LeasedTruckDTO getLeasedArriveTruckDTO() {
		return leasedArriveTruckDTO;
	}

	public void setLeasedArriveTruckDTO(LeasedTruckDTO leasedArriveTruckDTO) {
		this.leasedArriveTruckDTO = leasedArriveTruckDTO;
	}

	/**
	 * 获取 ******** 查询月台的条件 ***********.
	 *
	 * @return the ******** 查询月台的条件 ***********
	 */
	public QueryPlatformDTO getQueryPlatformDTO(){
		return queryPlatformDTO;
	}

	/**
	 * 设置 ******** 查询月台的条件 ***********.
	 *
	 * @param queryPlatformDTO the new ******** 查询月台的条件 ***********
	 */
	public void setQueryPlatformDTO(QueryPlatformDTO queryPlatformDTO){
		this.queryPlatformDTO = queryPlatformDTO;
	}

	public ArrivalEntity getArrivalEntity() {
		return arrivalEntity;
	}

	public void setArrivalEntity(ArrivalEntity arrivalEntity) {
		this.arrivalEntity = arrivalEntity;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getHasAgency() {
		return hasAgency;
	}

	public void setHasAgency(String hasAgency) {
		this.hasAgency = hasAgency;
	}

	public List<String> getDetailIds() {
		return detailIds;
	}

	public void setDetailIds(List<String> detailIds) {
		this.detailIds = detailIds;
	}

	/**
	 * @return set the truckActionDetailDtos
	 */
	public List<TruckActionDetailDto> getTruckActionDetailDtos() {
		return truckActionDetailDtos;
	}

	/**
	 * @param truckActionDetailDtos the truckActionDetailDtos to set
	 */
	public void setTruckActionDetailDtos(
			List<TruckActionDetailDto> truckActionDetailDtos) {
		this.truckActionDetailDtos = truckActionDetailDtos;
	}

	/**
	 * @return set the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getHandOverBillNo() {
		return handOverBillNo;
	}

	public void setHandOverBillNo(String handOverBillNo) {
		this.handOverBillNo = handOverBillNo;
	}
}