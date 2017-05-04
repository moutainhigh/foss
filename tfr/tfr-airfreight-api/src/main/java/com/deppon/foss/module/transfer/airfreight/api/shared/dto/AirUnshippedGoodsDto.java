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
 *  FILE PATH          :/AirUnshippedGoodsDto.java
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
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirUnshippedGoodsEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirUnshippedSerialNoEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillSerialNoEntity;
/**
 * 拉货DTO
 * @author 038300-foss-pengzhen
 * @date 2012-12-25 下午6:56:50
 */
public class AirUnshippedGoodsDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4999850384177793098L;
	/**
	 * 配载类型
	 */
	private String airAssembleType;  
	/**
	 * 正单号/代单号
	 */
	private String billNo;  
	/**
	 * 配载部门
	 */
	private String airAssembleOrgCode;  
	/**
	 * 目的站
	 */
	private String arrvRegionCode;  
	/**
	 * 航班号
	 */
	private String flightNo;  
	/**
	 * 拉货开始时间
	 */
	private String unshippedTimeBeginDate;  
	/**
	 * 拉货结束时间
	 */
	private String unshippedTimeEndDate; 
	/**
	 * 航空公司
	 */
	private String airLine;	
	/**
	 * 外发代理
	 */
	private String agenctCode; 
	/**
	 * 单号类型，用与区分是代单号还是正单号
	 */
	private String billNoType; 
	/**
	 * 拉货信息ID
	 */
	private String id; 
	/**
	 * 拉货信息
	 */
	private AirUnshippedGoodsEntity airUnshippedGoodsEntity;
	/**
	 * 流水号集合
	 */
	private List<AirUnshippedSerialNoEntity> airUnshippedSerialNoList;
	
	/**
	 * 标记了不可反选流水号集合
	 */
	private List<AirWaybillSerialNoEntity> airWaybillSerialNoList;
	
	/**
	 * 拉货时间
	 */
	private String unshippedTime;
	
	/**
	 * 拉货件数
	 */
	private String unshippedGoodsQty;
	
	/**
	 * 配载类型
	 */
	@SuppressWarnings("unused")
	private String isLoading = AirfreightConstants.AIRFREIGHT_ISYESLOADING;
	
	/**
	 * 签收状态，Y表示已签收
	 */
	@SuppressWarnings("unused")
	private String active = AirfreightConstants.PKP_SIGN_ACTIVE;

	/**
	 * 获取 拉货件数.
	 *
	 * @return the 拉货件数
	 */
	public String getUnshippedGoodsQty() {
		return unshippedGoodsQty;
	}

	/**
	 * 设置 拉货件数.
	 *
	 * @param unshippedGoodsQty the new 拉货件数
	 */
	public void setUnshippedGoodsQty(String unshippedGoodsQty) {
		this.unshippedGoodsQty = unshippedGoodsQty;
	}

	/**
	 * 获取 拉货时间.
	 *
	 * @return the 拉货时间
	 */
	public String getUnshippedTime() {
		return unshippedTime;
	}

	/**
	 * 设置 拉货时间.
	 *
	 * @param unshippedTime the new 拉货时间
	 */
	public void setUnshippedTime(String unshippedTime) {
		this.unshippedTime = unshippedTime;
	}

	/**
	 * 获取 航空公司.
	 *
	 * @return the 航空公司
	 */
	public String getAirLine() {
		return airLine;
	}

	/**
	 * 设置 航空公司.
	 *
	 * @param airLine the new 航空公司
	 */
	public void setAirLine(String airLine) {
		this.airLine = airLine;
	}

	/**
	 * 获取 外发代理.
	 *
	 * @return the 外发代理
	 */
	public String getAgenctCode() {
		return agenctCode;
	}

	/**
	 * 设置 外发代理.
	 *
	 * @param agenctCode the new 外发代理
	 */
	public void setAgenctCode(String agenctCode) {
		this.agenctCode = agenctCode;
	}

	/**
	 * 获取 拉货开始时间.
	 *
	 * @return the 拉货开始时间
	 */
	public String getUnshippedTimeBeginDate() {
		return unshippedTimeBeginDate;
	}

	/**
	 * 设置 拉货开始时间.
	 *
	 * @param unshippedTimeBeginDate the new 拉货开始时间
	 */
	public void setUnshippedTimeBeginDate(String unshippedTimeBeginDate) {
		this.unshippedTimeBeginDate = unshippedTimeBeginDate;
	}

	/**
	 * 获取 拉货结束时间.
	 *
	 * @return the 拉货结束时间
	 */
	public String getUnshippedTimeEndDate() {
		return unshippedTimeEndDate;
	}

	/**
	 * 设置 拉货结束时间.
	 *
	 * @param unshippedTimeEndDate the new 拉货结束时间
	 */
	public void setUnshippedTimeEndDate(String unshippedTimeEndDate) {
		this.unshippedTimeEndDate = unshippedTimeEndDate;
	}

	/**
	 * 获取 配载类型.
	 *
	 * @return the 配载类型
	 */
	public String getAirAssembleType() {
		return airAssembleType;
	}

	/**
	 * 设置 配载类型.
	 *
	 * @param airAssembleType the new 配载类型
	 */
	public void setAirAssembleType(String airAssembleType) {
		this.airAssembleType = airAssembleType;
	}

	/**
	 * 获取 正单号/代单号.
	 *
	 * @return the 正单号/代单号
	 */
	public String getBillNo() {
		return billNo;
	}

	/**
	 * 设置 正单号/代单号.
	 *
	 * @param billNo the new 正单号/代单号
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	/**
	 * 获取 配载部门.
	 *
	 * @return the 配载部门
	 */
	public String getAirAssembleOrgCode() {
		return airAssembleOrgCode;
	}

	/**
	 * 设置 配载部门.
	 *
	 * @param airAssembleOrgCode the new 配载部门
	 */
	public void setAirAssembleOrgCode(String airAssembleOrgCode) {
		this.airAssembleOrgCode = airAssembleOrgCode;
	}

	/**
	 * 获取 目的站.
	 *
	 * @return the 目的站
	 */
	public String getArrvRegionCode() {
		return arrvRegionCode;
	}

	/**
	 * 设置 目的站.
	 *
	 * @param arrvRegionName the new 目的站
	 */
	public void setArrvRegionCode(String arrvRegionCode) {
		this.arrvRegionCode = arrvRegionCode;
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
	 * 获取 单号类型，用与区分是代单号还是正单号.
	 *
	 * @return the 单号类型，用与区分是代单号还是正单号
	 */
	public String getBillNoType() {
		return billNoType;
	}

	/**
	 * 设置 单号类型，用与区分是代单号还是正单号.
	 *
	 * @param billNoType the new 单号类型，用与区分是代单号还是正单号
	 */
	public void setBillNoType(String billNoType) {
		this.billNoType = billNoType;
	}

	/**
	 * 获取 拉货信息ID.
	 *
	 * @return the 拉货信息ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置 拉货信息ID.
	 *
	 * @param id the new 拉货信息ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取 拉货信息.
	 *
	 * @return the 拉货信息
	 */
	public AirUnshippedGoodsEntity getAirUnshippedGoodsEntity() {
		return airUnshippedGoodsEntity;
	}

	/**
	 * 设置 拉货信息.
	 *
	 * @param airUnshippedGoodsEntity the new 拉货信息
	 */
	public void setAirUnshippedGoodsEntity(
			AirUnshippedGoodsEntity airUnshippedGoodsEntity) {
		this.airUnshippedGoodsEntity = airUnshippedGoodsEntity;
	}

	/**
	 * 获取 流水号集合.
	 *
	 * @return the 流水号集合
	 */
	public List<AirUnshippedSerialNoEntity> getAirUnshippedSerialNoList() {
		return airUnshippedSerialNoList;
	}

	/**
	 * 设置 流水号集合.
	 *
	 * @param airUnshippedSerialNoList the new 流水号集合
	 */
	public void setAirUnshippedSerialNoList(
			List<AirUnshippedSerialNoEntity> airUnshippedSerialNoList) {
		this.airUnshippedSerialNoList = airUnshippedSerialNoList;
	}

	/**
	 * 获取 标记了不可反选流水号集合.
	 *
	 * @return the 标记了不可反选流水号集合
	 */
	public List<AirWaybillSerialNoEntity> getAirWaybillSerialNoList() {
		return airWaybillSerialNoList;
	}

	/**
	 * 设置 标记了不可反选流水号集合.
	 *
	 * @param airWaybillSerialNoList the new 标记了不可反选流水号集合
	 */
	public void setAirWaybillSerialNoList(
			List<AirWaybillSerialNoEntity> airWaybillSerialNoList) {
		this.airWaybillSerialNoList = airWaybillSerialNoList;
	}

}