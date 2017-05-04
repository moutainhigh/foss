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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/airfreight/api/shared/vo/AirWaybillDetailVO.java
 *  
 *  FILE NAME          :AirWaybillDetailVO.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.shared.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAgentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirportEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FlightEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.AirlinesValueAddEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirCargoVolumeEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirCargovolumeSerialnoEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirLockWaybillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirSerialNoDetail;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.TfrWaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirWaybillDetailDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.WaybillDetailDto;

/**
 * 分单和票VO
 * @author 099197-foss-zhoudejun
 * @date 2012-10-19 下午5:13:05
 */
public class AirWaybillDetailVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5245269854490384458L;
	/**
	 * 空运货量DTO
	 */
	private AirWaybillDetailDto ticketDto =  new AirWaybillDetailDto();
	/**
	 * 航空正单entity
	 */
	private AirWaybillEntity billEntity;
	/**
	 * 空运货量明细
	 */
	private List<AirCargoVolumeEntity> volumeResult;
	/**
	 * 航空正单明细list
	 */
	private List<AirWaybillDetailEntity> billDetailList;
	/**
	 * 航空流水明细entity
	 */
	private AirCargoVolumeEntity volumeEntity;
	/**
	 * 航空流水明细list
	 */
	private List<AirCargoVolumeEntity> airCargoVolumeList;
	/**
	 * 空运货量明细list
	 */
	private List<AirCargovolumeSerialnoEntity> airCargovolumeSerialnoList;
	/**
	 * 运单流水明细
	 */
	private List<AirSerialNoDetail> airSerialNoDetailList;
	/**
	 * 运单明细包装类
	 */
	private List<TfrWaybillEntity> waybillList;
	/**
	 * 流水明细map		
	 */
	private Map<String,List> parameter;
	/**
	 * 航空公司二字码list
	 */
	private List<AirlinesEntity> queryAllAirlines;
	/**
	 * 注入航空费率entity
	 */
	private AirlinesValueAddEntity airlinesValueAddEntity;
	/**
	 * 系统参数
	 */
	private List<ConfigurationParamsEntity>  configurationParamsList;
	
	/**
	 * 始发站三字码list
	 */
	private List<AirportEntity> deptRegionAirportList ;
	
	/**
	 * 目的站三字码list
	 */
	private List<AirportEntity> arrvRegionAirportList ;
	
	/**
	 * 航空公司代理人实体 
	 */
	private AirlinesAgentEntity airlinesAgent; 
	
	/**
	 * 需要删除添加的流水号map
	 */
	private Map<String,List> addParameterMap;
	/**
	 * 需要删除的流水号map
	 */
	private Map<String,List> delParameterMap;
	
	/**
	 * 航班信息
	 */
	private List<FlightEntity> flightList;
	
	/**
	 * 航空公司二字码
	 */
	private String airLineTwoletter;
	
	/**
	 * 出发基础三字码
	 */
	private String airportCode;
	
	/**
	 * 航班信息
	 */
	private FlightEntity flightEntity;
	
	/**
	 * 毛重
	 */
	private BigDecimal weight;
	
	/**
	 * 货物类型 
	 */
	private String goodsType;
	
	/**
	 * 运价
	 */
	private BigDecimal priceRate;
	
	/**
	 * 正单号
	 */
	private String airWaybillNo;
	
	/**
	 * 航空公司代理人code
	 */
	private String agentCode;
	
	/**
	 * 始发站code
	 */
	private String deptRegionCode;
	/**
	 * 目的站code
	 */
	private String arrvRegionCode;
	
	/**
	 * 航班号
	 */
	private String flightNo;
	/**
	 * 空运锁票
	 * */
	private AirLockWaybillDetailEntity airLockWaybillDetailEntity=new AirLockWaybillDetailEntity();
	/**
	 * 最低运价
	 */
	private BigDecimal minPriceRate;
	
	private WaybillDetailDto waybillDetailDto;

	public WaybillDetailDto getWaybillDetailDto() {
		return waybillDetailDto;
	}

	public void setWaybillDetailDto(WaybillDetailDto waybillDetailDto) {
		this.waybillDetailDto = waybillDetailDto;
	}
	
	/**
	 * 配载类型
	 * */
	private String airAssembleType;

    /**
     *运输性质
     */
    private String transportType;

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

	
	/**
	 * 获取 空运配载类型DTO
	 *
	 * @return the 空运配载类型DTO
	 */
	public String getAirAssembleType() {
		return airAssembleType;
	}

	/**
	 * 设置  空运配载类型DTO
	 *
	 * @return airAssembleType the new 空运配载类型DTO
	 */
	public void setAirAssembleType(String airAssembleType) {
		this.airAssembleType = airAssembleType;
	}


	/**
	 * 获取 空运货量DTO.
	 *
	 * @return the 空运货量DTO
	 */
	public AirWaybillDetailDto getTicketDto() {
		return ticketDto;
	}

	/**
	 * 设置 空运货量DTO.
	 *
	 * @param ticketDto the new 空运货量DTO
	 */
	public void setTicketDto(AirWaybillDetailDto ticketDto) {
		this.ticketDto = ticketDto;
	}

	/**
	 * 获取 空运货量明细.
	 *
	 * @return the 空运货量明细
	 */
	public List<AirCargoVolumeEntity> getVolumeResult() {
		return volumeResult;
	}

	/**
	 * 设置 空运货量明细.
	 *
	 * @param volumeResult the new 空运货量明细
	 */
	public void setVolumeResult(List<AirCargoVolumeEntity> volumeResult) {
		this.volumeResult = volumeResult;
	}

	/**
	 * 获取 航空正单明细list.
	 *
	 * @return the 航空正单明细list
	 */
	public List<AirWaybillDetailEntity> getBillDetailList() {
		return billDetailList;
	}

	/**
	 * 设置 航空正单明细list.
	 *
	 * @param billDetailList the new 航空正单明细list
	 */
	public void setBillDetailList(List<AirWaybillDetailEntity> billDetailList) {
		this.billDetailList = billDetailList;
	}
	
	/**
	 * 获取 航空正单entity.
	 *
	 * @return the 航空正单entity
	 */
	public AirWaybillEntity getBillEntity() {
		return billEntity;
	}

	/**
	 * 设置 航空正单entity.
	 *
	 * @param billEntity the new 航空正单entity
	 */
	public void setBillEntity(AirWaybillEntity billEntity) {
		this.billEntity = billEntity;
	}

	/**
	 * 获取 航空流水明细entity.
	 *
	 * @return the 航空流水明细entity
	 */
	public AirCargoVolumeEntity getVolumeEntity() {
		return volumeEntity;
	}

	/**
	 * 设置 航空流水明细entity.
	 *
	 * @param volumeEntity the new 航空流水明细entity
	 */
	public void setVolumeEntity(AirCargoVolumeEntity volumeEntity) {
		this.volumeEntity = volumeEntity;
	}

	/**
	 * 获取 航空流水明细list.
	 *
	 * @return the 航空流水明细list
	 */
	public List<AirCargoVolumeEntity> getAirCargoVolumeList() {
		return airCargoVolumeList;
	}

	/**
	 * 设置 航空流水明细list.
	 *
	 * @param airCargoVolumeList the new 航空流水明细list
	 */
	public void setAirCargoVolumeList(List<AirCargoVolumeEntity> airCargoVolumeList) {
		this.airCargoVolumeList = airCargoVolumeList;
	}

	/**
	 * 获取 空运货量明细list.
	 *
	 * @return the 空运货量明细list
	 */
	public List<AirCargovolumeSerialnoEntity> getAirCargovolumeSerialnoList() {
		return airCargovolumeSerialnoList;
	}

	/**
	 * 设置 空运货量明细list.
	 *
	 * @param airCargovolumeSerialnoList the new 空运货量明细list
	 */
	public void setAirCargovolumeSerialnoList(
			List<AirCargovolumeSerialnoEntity> airCargovolumeSerialnoList) {
		this.airCargovolumeSerialnoList = airCargovolumeSerialnoList;
	}

	/**
	 * 获取 运单明细包装类.
	 *
	 * @return the 运单明细包装类
	 */
	public List<TfrWaybillEntity> getWaybillList() {
		return waybillList;
	}

	/**
	 * 设置 运单明细包装类.
	 *
	 * @param waybillList the new 运单明细包装类
	 */
	public void setWaybillList(List<TfrWaybillEntity> waybillList) {
		this.waybillList = waybillList;
	}

	/**
	 * 获取 运单流水明细.
	 *
	 * @return the 运单流水明细
	 */
	public List<AirSerialNoDetail> getAirSerialNoDetailList() {
		return airSerialNoDetailList;
	}

	/**
	 * 设置 运单流水明细.
	 *
	 * @param airSerialNoDetailList the new 运单流水明细
	 */
	public void setAirSerialNoDetailList(
			List<AirSerialNoDetail> airSerialNoDetailList) {
		this.airSerialNoDetailList = airSerialNoDetailList;
	}

	/**
	 * 获取 流水明细map.
	 *
	 * @return the 流水明细map
	 */
	public Map<String, List> getParameter() {
		return parameter;
	}

	/**
	 * 设置 流水明细map.
	 *
	 * @param parameter the new 流水明细map
	 */
	public void setParameter(Map<String, List> parameter) {
		this.parameter = parameter;
	}

	/**
	 * 获取 航空公司二字码list.
	 *
	 * @return the 航空公司二字码list
	 */
	public List<AirlinesEntity> getQueryAllAirlines() {
		return queryAllAirlines;
	}

	/**
	 * 设置 航空公司二字码list.
	 *
	 * @param queryAllAirlines the new 航空公司二字码list
	 */
	public void setQueryAllAirlines(List<AirlinesEntity> queryAllAirlines) {
		this.queryAllAirlines = queryAllAirlines;
	}

	/**
	 * 获取 注入航空费率entity.
	 *
	 * @return the 注入航空费率entity
	 */
	public AirlinesValueAddEntity getAirlinesValueAddEntity() {
		return airlinesValueAddEntity;
	}

	/**
	 * 设置 注入航空费率entity.
	 *
	 * @param airlinesValueAddEntity the new 注入航空费率entity
	 */
	public void setAirlinesValueAddEntity(
			AirlinesValueAddEntity airlinesValueAddEntity) {
		this.airlinesValueAddEntity = airlinesValueAddEntity;
	}

	/**
	 * 获取 系统参数.
	 *
	 * @return the 系统参数
	 */
	public List<ConfigurationParamsEntity> getConfigurationParamsList() {
		return configurationParamsList;
	}

	/**
	 * 设置 系统参数.
	 *
	 * @param configurationParamsList the new 系统参数
	 */
	public void setConfigurationParamsList(
			List<ConfigurationParamsEntity> configurationParamsList) {
		this.configurationParamsList = configurationParamsList;
	}
	
	/**
	 * 获取 需要删除添加的流水号map.
	 *
	 * @return the 需要删除添加的流水号map
	 */
	public Map<String, List> getAddParameterMap() {
		return addParameterMap;
	}

	/**
	 * 设置 需要删除添加的流水号map.
	 *
	 * @param addParameterMap the new 需要删除添加的流水号map
	 */
	public void setAddParameterMap(Map<String, List> addParameterMap) {
		this.addParameterMap = addParameterMap;
	}

	/**
	 * 获取 需要删除的流水号map.
	 *
	 * @return the 需要删除的流水号map
	 */
	public Map<String, List> getDelParameterMap() {
		return delParameterMap;
	}

	/**
	 * 设置 需要删除的流水号map.
	 *
	 * @param delParameterMap the new 需要删除的流水号map
	 */
	public void setDelParameterMap(Map<String, List> delParameterMap) {
		this.delParameterMap = delParameterMap;
	}

	/**
	 * 获取 航班信息.
	 *
	 * @return the 航班信息
	 */
	public List<FlightEntity> getFlightList() {
		return flightList;
	}

	/**
	 * 设置 航班信息.
	 *
	 * @param flightList the new 航班信息
	 */
	public void setFlightList(List<FlightEntity> flightList) {
		this.flightList = flightList;
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
	 * 获取 出发基础三字码.
	 *
	 * @return the 出发基础三字码
	 */
	public String getAirportCode() {
		return airportCode;
	}

	/**
	 * 设置 出发基础三字码.
	 *
	 * @param airportCode the new 出发基础三字码
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	/**
	 * 获取 航班信息.
	 *
	 * @return the 航班信息
	 */
	public FlightEntity getFlightEntity() {
		return flightEntity;
	}

	/**
	 * 设置 航班信息.
	 *
	 * @param flightEntity the new 航班信息
	 */
	public void setFlightEntity(FlightEntity flightEntity) {
		this.flightEntity = flightEntity;
	}

	/**
	 * 获取 毛重.
	 *
	 * @return the 毛重
	 */
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * 设置 毛重.
	 *
	 * @param weight the new 毛重
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * 获取 货物类型.
	 *
	 * @return the 货物类型
	 */
	public String getGoodsType() {
		return goodsType;
	}

	/**
	 * 设置 货物类型.
	 *
	 * @param goodsType the new 货物类型
	 */
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public BigDecimal getPriceRate() {
		return priceRate;
	}

	public void setPriceRate(BigDecimal priceRate) {
		this.priceRate = priceRate;
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
	 * 获取 航空公司代理人code.
	 *
	 * @return the 航空公司代理人code
	 */
	public String getAgentCode() {
		return agentCode;
	}

	/**
	 * 设置 航空公司代理人code.
	 *
	 * @param agentCode the new 航空公司代理人code
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	/**
	 * 获取 航空公司代理人实体.
	 *
	 * @return the 航空公司代理人实体
	 */
	public AirlinesAgentEntity getAirlinesAgent() {
		return airlinesAgent;
	}

	/**
	 * 设置 航空公司代理人实体.
	 *
	 * @param airlinesAgent the new 航空公司代理人实体
	 */
	public void setAirlinesAgent(AirlinesAgentEntity airlinesAgent) {
		this.airlinesAgent = airlinesAgent;
	}

	/**
	 * 获取 始发站三字码list.
	 *
	 * @return the 始发站三字码list
	 */
	public List<AirportEntity> getDeptRegionAirportList() {
		return deptRegionAirportList;
	}

	/**
	 * 设置 始发站三字码list.
	 *
	 * @param deptRegionAirportList the new 始发站三字码list
	 */
	public void setDeptRegionAirportList(List<AirportEntity> deptRegionAirportList) {
		this.deptRegionAirportList = deptRegionAirportList;
	}

	/**
	 * 获取 目的站三字码list.
	 *
	 * @return the 目的站三字码list
	 */
	public List<AirportEntity> getArrvRegionAirportList() {
		return arrvRegionAirportList;
	}

	/**
	 * 设置 目的站三字码list.
	 *
	 * @param arrvRegionAirportList the new 目的站三字码list
	 */
	public void setArrvRegionAirportList(List<AirportEntity> arrvRegionAirportList) {
		this.arrvRegionAirportList = arrvRegionAirportList;
	}

	/**
	 * 获取 始发站code.
	 *
	 * @return the 始发站code
	 */
	public String getDeptRegionCode() {
		return deptRegionCode;
	}

	/**
	 * 设置 始发站code.
	 *
	 * @param deptRegionCode the new 始发站code
	 */
	public void setDeptRegionCode(String deptRegionCode) {
		this.deptRegionCode = deptRegionCode;
	}

	/**
	 * 获取 目的站code.
	 *
	 * @return the 目的站code
	 */
	public String getArrvRegionCode() {
		return arrvRegionCode;
	}

	/**
	 * 设置 目的站code.
	 *
	 * @param arrvRegionCode the new 目的站code
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

	public BigDecimal getMinPriceRate() {
		return minPriceRate;
	}

	public void setMinPriceRate(BigDecimal minPriceRate) {
		this.minPriceRate = minPriceRate;
	}

	public AirLockWaybillDetailEntity getAirLockWaybillDetailEntity() {
		return airLockWaybillDetailEntity;
	}

	public void setAirLockWaybillDetailEntity(
			AirLockWaybillDetailEntity airLockWaybillDetailEntity) {
		this.airLockWaybillDetailEntity = airLockWaybillDetailEntity;
	}
	

}