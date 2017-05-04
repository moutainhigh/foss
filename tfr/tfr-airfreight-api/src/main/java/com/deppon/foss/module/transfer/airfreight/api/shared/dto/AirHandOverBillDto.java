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
 *  PROJECT NAME  : tfr-airfreight-api
 *  
 *  package_name  : 
 *  
 *  FILE PATH          :/AirHandOverBillDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.airfreight.api.define.AirfreightConstants;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirHandOverBillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirHandOverBillEntity;

/**
 * 航空交接单DTO
 * @author 038300-foss-pengzhen
 * @date 2012-12-25 下午6:59:37
 */
public class AirHandOverBillDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6044611351665547528L;
	/**
	 * 交接单实体
	 */
	private AirHandOverBillEntity airHandOverBillEntity;  
	/**
	 * 正单明细的集合
	 */
	private List<AirHandOverBillDetailEntity> airHandOverBillDetailList;  
	/**
	 * 正单流水号集合
	 */
	private List<AirHandOverBillGetSerialNoDto> airHandOverBillGetSerialNoDtoList;
	/**
	 * 交接单号
	 */
    private String airHandoverNo; 
    /**
     * 航班日期
     */
    private String flightDate;  
    /**
     * 航班号
     */
    private String flightNo;  
    /**
     * 正单号
     */
    private String airWaybillNo; 
    /**
     * 运单号
     */
    private String waybillNo;  
    /**
     * 空运总调
     */
    private String orgCode;  
    /**
     * 库存状态
     */
    private String airWaybillStockState; 
    /**
     * 航空公司二字码
     */
    private String airLineTwoletter; 
    /**
     * 起飞时间
     */
    private String takeOffTime;  
    /**
     * id
     */
    private String id;
    
    /**
     * 修改出库状态
     */
    @SuppressWarnings("unused")
    private static final String stockStatus = "Y";
    
    /**
	 * 配载类型
	 */
	@SuppressWarnings("unused")
	private String isLoading = AirfreightConstants.AIRFREIGHT_ISYESLOADING;
    
    /**
     * 修改正单交接状态
     */
    private String handOverState;
    
    /**
     * 走货路径状态-离开
     */
    private String leave;
    
    /**
     * 流水号
     */
    private String serialNo;
    
    /**
     * 正单号集合
     */
    private List<String> airWayBillNos;
    
    /**
     * 卸车状态
     */
    private String expressUnloadStatus;
    
    /**
     * 交接类型
     * */
    private String airHandOverType;

    /**
     * 下一外场
     * **/
    private String expressArriveCode; 
    

	public String getExpressUnloadStatus() {
		return expressUnloadStatus;
	}

	public void setExpressUnloadStatus(String expressUnloadStatus) {
		this.expressUnloadStatus = expressUnloadStatus;
	}

	/**
	 * 获取 修改正单交接状态.
	 *
	 * @return the 修改正单交接状态
	 */
	public String getHandOverState() {
		return handOverState;
	}

	/**
	 * 设置 修改正单交接状态.
	 *
	 * @param handOverState the new 修改正单交接状态
	 */
	public void setHandOverState(String handOverState) {
		this.handOverState = handOverState;
	}

	/**
	 * 获取 正单号集合.
	 *
	 * @return the 正单号集合
	 */
	public List<String> getAirWayBillNos() {
		return airWayBillNos;
	}

	/**
	 * 设置 正单号集合.
	 *
	 * @param airWayBillNos the new 正单号集合
	 */
	public void setAirWayBillNos(List<String> airWayBillNos) {
		this.airWayBillNos = airWayBillNos;
	}

	/**
	 * 获取 流水号.
	 *
	 * @return the 流水号
	 */
	public String getSerialNo() {
		return serialNo;
	}

	/**
	 * 设置 流水号.
	 *
	 * @param serialNo the new 流水号
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * 获取 走货路径状态-离开.
	 *
	 * @return the 走货路径状态-离开
	 */
	public String getLeave() {
		return leave;
	}

	/**
	 * 设置 走货路径状态-离开.
	 *
	 * @param leave the new 走货路径状态-离开
	 */
	public void setLeave(String leave) {
		this.leave = leave;
	}

	/**
	 * 获取 交接单实体.
	 *
	 * @return the 交接单实体
	 */
	public AirHandOverBillEntity getAirHandOverBillEntity() {
		return airHandOverBillEntity;
	}

	/**
	 * 设置 交接单实体.
	 *
	 * @param airHandOverBillEntity the new 交接单实体
	 */
	public void setAirHandOverBillEntity(AirHandOverBillEntity airHandOverBillEntity) {
		this.airHandOverBillEntity = airHandOverBillEntity;
	}

	/**
	 * 获取 正单明细的集合.
	 *
	 * @return the 正单明细的集合
	 */
	public List<AirHandOverBillDetailEntity> getAirHandOverBillDetailList() {
		return airHandOverBillDetailList;
	}

	/**
	 * 设置 正单明细的集合.
	 *
	 * @param airHandOverBillDetailList the new 正单明细的集合
	 */
	public void setAirHandOverBillDetailList(
			List<AirHandOverBillDetailEntity> airHandOverBillDetailList) {
		this.airHandOverBillDetailList = airHandOverBillDetailList;
	}

	/**
	 * 获取 正单流水号集合.
	 *
	 * @return the 正单流水号集合
	 */
	public List<AirHandOverBillGetSerialNoDto> getAirHandOverBillGetSerialNoDtoList() {
		return airHandOverBillGetSerialNoDtoList;
	}

	/**
	 * 设置 正单流水号集合.
	 *
	 * @param airHandOverBillGetSerialNoDtoList the new 正单流水号集合
	 */
	public void setAirHandOverBillGetSerialNoDtoList(
			List<AirHandOverBillGetSerialNoDto> airHandOverBillGetSerialNoDtoList) {
		this.airHandOverBillGetSerialNoDtoList = airHandOverBillGetSerialNoDtoList;
	}

	/**
	 * 获取 交接单号.
	 *
	 * @return the 交接单号
	 */
	public String getAirHandoverNo() {
		return airHandoverNo;
	}

	/**
	 * 设置 交接单号.
	 *
	 * @param airHandoverNo the new 交接单号
	 */
	public void setAirHandoverNo(String airHandoverNo) {
		this.airHandoverNo = airHandoverNo;
	}

	/**
	 * 获取 航班日期.
	 *
	 * @return the 航班日期
	 */
	public String getFlightDate() {
		return flightDate;
	}

	/**
	 * 设置 航班日期.
	 *
	 * @param flightDate the new 航班日期
	 */
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}

	/**
	 * 获取 航班号.
	 *
	 * @return the 航班号
	 */
	public String getFlightNo() {
		return flightNo;
	}

	/**
	 * 设置 航班号.
	 *
	 * @param flightNo the new 航班号
	 */
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	/**
	 * 获取 正单号.
	 *
	 * @return the 正单号
	 */
	public String getAirWaybillNo() {
		return airWaybillNo;
	}

	/**
	 * 设置 正单号.
	 *
	 * @param airWaybillNo the new 正单号
	 */
	public void setAirWaybillNo(String airWaybillNo) {
		this.airWaybillNo = airWaybillNo;
	}

	/**
	 * 获取 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * 设置 运单号.
	 *
	 * @param waybillNo the new 运单号
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * 获取 空运总调.
	 *
	 * @return the 空运总调
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * 设置 空运总调.
	 *
	 * @param orgName the new 空运总调
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * 获取 库存状态.
	 *
	 * @return the 库存状态
	 */
	public String getAirWaybillStockState() {
		return airWaybillStockState;
	}

	/**
	 * 设置 库存状态.
	 *
	 * @param airWaybillStockState the new 库存状态
	 */
	public void setAirWaybillStockState(String airWaybillStockState) {
		this.airWaybillStockState = airWaybillStockState;
	}

	/**
	 * 获取 id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置 id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取 航空公司二字码.
	 *
	 * @return the 航空公司二字码
	 */
	public String getAirLineTwoletter() {
		return airLineTwoletter;
	}

	/**
	 * 设置 航空公司二字码.
	 *
	 * @param airLineTwoletter the new 航空公司二字码
	 */
	public void setAirLineTwoletter(String airLineTwoletter) {
		this.airLineTwoletter = airLineTwoletter;
	}

	/**
	 * 获取 起飞时间.
	 *
	 * @return the 起飞时间
	 */
	public String getTakeOffTime() {
		return takeOffTime;
	}

	/**
	 * 设置 起飞时间.
	 *
	 * @param takeOffTime the new 起飞时间
	 */
	public void setTakeOffTime(String takeOffTime) {
		this.takeOffTime = takeOffTime;
	}
	
	public String getAirHandOverType() {
		return airHandOverType;
	}

	public void setAirHandOverType(String airHandOverType) {
		this.airHandOverType = airHandOverType;
	}

	public String getExpressArriveCode() {
		return expressArriveCode;
	}

	public void setExpressArriveCode(String expressArriveCode) {
		this.expressArriveCode = expressArriveCode;
	}
    
}