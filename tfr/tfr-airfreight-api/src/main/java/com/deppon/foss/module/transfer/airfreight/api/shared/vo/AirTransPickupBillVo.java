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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/airfreight/api/shared/vo/AirTransPickupBillVo.java
 *  
 *  FILE NAME          :AirTransPickupBillVo.java
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
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirChangeInventoryDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirChangeInventoryEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirRevisebillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTranDataCollectionEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTransPickupDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTransPickupbillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.SerialEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTransPickupBillDto;
/**
 * 中转提货清单VO
 * @author 038300-foss-pengzhen
 * @date 2012-12-25 下午7:05:26
 */
public class AirTransPickupBillVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4830576340604699028L;
	/**
	 * 制作中转提货清单dto
	 */
	private AirTransPickupBillDto airTransPickupBillDto = new AirTransPickupBillDto();
	/**
	 * 航空正单明细list
	 */
	private List<AirWaybillDetailEntity>  resultAirWaybillDetailList;
	/**
	 * 航空正单明细实体
	 */
	private AirWaybillDetailEntity  resultAirWaybillDetailInfo;
	/**
	 * 航空正单实体
	 */
	private AirWaybillEntity airWaybillEntity;
	/**
	 * 制作中转提货清单保存数据收集类
	 */
	private AirTranDataCollectionEntity airTranDataCollectionEntity;
	/**
	 * 合票清单明细实体
	 */
	private List<AirPickupbillDetailEntity> airPickupbillDetailList;
	/**
	 * 合大票清单明细
	 */
	private AirPickupbillDetailEntity airPickupbillDetailEntity;
	/**
	 * 合大票清单
	 */
	private AirPickupbillEntity airPickupbillEntity;
	/**
	 * 合大票清单list
	 */
	private List<AirPickupbillEntity> airPickupbillList;
	/**
	 * 组装合大票明细List
	 */
	private List<AirChangeInventoryEntity> airChangeInventoryList;
	/**
	 * 合大票明细日志
	 */
	private List<AirRevisebillDetailEntity> airRevisebillDetailList;
	/**
	 * 变更清单List
	 */
	private List<AirChangeInventoryDetailEntity> airChangeInventoryDetailList;
	/**
	 * 流水明细
	 */
	private List<SerialEntity> serialList;
	private SerialEntity serialEntity;
	private List<List> allList;
	
	public List<List> getAllList() {
		return allList;
	}

	public void setAllList(List<List> allList) {
		this.allList = allList;
	}
	private String updateLogger;
	public String getUpdateLogger() {
		return updateLogger;
	}

	public void setUpdateLogger(String updateLogger) {
		this.updateLogger = updateLogger;
	}

	/**
	 * 参数
	 */
	private Map<String,String> parameter;
	/**
	 * 
	 */
	private Map<String,String> stlWayBillNoMap;
	/**
	 * 
	 */
	private Map<String,String> delWayBillNoMap;
	/**
	 * 
	 */
	private List<AirPickupbillDetailEntity> deletePickupDetailList;
	/**
	 * 
	 */
	private List<AirPickupbillDetailEntity> addPickupDetailList;
	/**
	 * 
	 */
	private List<AirPickupbillDetailEntity> stlPickupDetailList;
	/**
	 * 
	 */
	private List<AirTransPickupbillEntity> airTransPickupbillList;
	/**
	 * 
	 */
	private List<AirTransPickupDetailEntity> airTransPickupDetailList;
	/**
	 * 
	 */
	private List<AirTransPickupDetailEntity> delTransPickupDetailList;
	/**
	 * 
	 */
	private List<AirTransPickupDetailEntity> callStlTransPickupDetailList;
	/**
	 * 
	 */
	private List<AirTransPickupDetailEntity> modifyTransPickupDetailList;
	/**
	 * 
	 */
	private Map<String,String> modifyNotesMaps;
	
	/**
	 * 
	 */
	private String airPickupbillId;
	
	/**
	 * 
	 */
	private String airTransferPickUpBillId;
	
	/**
	 * 合大票明细ids
	 */
	private List<String> ids;
	
	/**
	 * 正单号
	 */
	private String airWaybillNo;
	
	/**
	 * 是否调用edi
	 */
	private String callIsNotEdiFlag;
	
	/**
	 * 航班号
	 */
	private String fightNo;
	
	/**
	 * 获取 参数.
	 *
	 * @return the 参数
	 */
	public Map<String, String> getParameter() {
		return parameter;
	}

	/**
	 * 设置 参数.
	 *
	 * @param parameter the new 参数
	 */
	public void setParameter(Map<String, String> parameter) {
		this.parameter = parameter;
	}

	/**
	 * 获取 制作中转提货清单dto.
	 *
	 * @return the 制作中转提货清单dto
	 */
	public AirTransPickupBillDto getAirTransPickupBillDto() {
		return airTransPickupBillDto;
	}

	/**
	 * 设置 制作中转提货清单dto.
	 *
	 * @param airTransPickupBillDto the new 制作中转提货清单dto
	 */
	public void setAirTransPickupBillDto(AirTransPickupBillDto airTransPickupBillDto) {
		this.airTransPickupBillDto = airTransPickupBillDto;
	}

	/**
	 * 获取 航空正单明细list.
	 *
	 * @return the 航空正单明细list
	 */
	public List<AirWaybillDetailEntity> getResultAirWaybillDetailList() {
		return resultAirWaybillDetailList;
	}

	/**
	 * 设置 航空正单明细list.
	 *
	 * @param resultAirWaybillDetailList the new 航空正单明细list
	 */
	public void setResultAirWaybillDetailList(
			List<AirWaybillDetailEntity> resultAirWaybillDetailList) {
		this.resultAirWaybillDetailList = resultAirWaybillDetailList;
	}

	/**
	 * 获取 航空正单明细实体.
	 *
	 * @return the 航空正单明细实体
	 */
	public AirWaybillDetailEntity getResultAirWaybillDetailInfo() {
		return resultAirWaybillDetailInfo;
	}

	/**
	 * 设置 航空正单明细实体.
	 *
	 * @param resultAirWaybillDetailInfo the new 航空正单明细实体
	 */
	public void setResultAirWaybillDetailInfo(
			AirWaybillDetailEntity resultAirWaybillDetailInfo) {
		this.resultAirWaybillDetailInfo = resultAirWaybillDetailInfo;
	}
	
	/**
	 * 获取 组装合大票明细List.
	 *
	 * @return the 组装合大票明细List
	 */
	public List<AirChangeInventoryEntity> getAirChangeInventoryList() {
		return airChangeInventoryList;
	}

	/**
	 * 设置 组装合大票明细List.
	 *
	 * @param airChangeInventoryList the new 组装合大票明细List
	 */
	public void setAirChangeInventoryList(
			List<AirChangeInventoryEntity> airChangeInventoryList) {
		this.airChangeInventoryList = airChangeInventoryList;
	}

	/**
	 * 获取 航空正单实体.
	 *
	 * @return the 航空正单实体
	 */
	public AirWaybillEntity getAirWaybillEntity() {
		return airWaybillEntity;
	}

	/**
	 * 设置 航空正单实体.
	 *
	 * @param airWaybillEntity the new 航空正单实体
	 */
	public void setAirWaybillEntity(AirWaybillEntity airWaybillEntity) {
		this.airWaybillEntity = airWaybillEntity;
	}

	/**
	 * 获取 制作中转提货清单保存数据收集类.
	 *
	 * @return the 制作中转提货清单保存数据收集类
	 */
	public AirTranDataCollectionEntity getAirTranDataCollectionEntity() {
		return airTranDataCollectionEntity;
	}

	/**
	 * 设置 制作中转提货清单保存数据收集类.
	 *
	 * @param airTranDataCollectionEntity the new 制作中转提货清单保存数据收集类
	 */
	public void setAirTranDataCollectionEntity(
			AirTranDataCollectionEntity airTranDataCollectionEntity) {
		this.airTranDataCollectionEntity = airTranDataCollectionEntity;
	}

	/**
	 * 获取 合票清单明细实体.
	 *
	 * @return the 合票清单明细实体
	 */
	public List<AirPickupbillDetailEntity> getAirPickupbillDetailList() {
		return airPickupbillDetailList;
	}

	/**
	 * 设置 合票清单明细实体.
	 *
	 * @param airPickupbillDetailList the new 合票清单明细实体
	 */
	public void setAirPickupbillDetailList(
			List<AirPickupbillDetailEntity> airPickupbillDetailList) {
		this.airPickupbillDetailList = airPickupbillDetailList;
	}
	
	/**
	 * 获取 合大票清单明细.
	 *
	 * @return the 合大票清单明细
	 */
	public AirPickupbillDetailEntity getAirPickupbillDetailEntity() {
		return airPickupbillDetailEntity;
	}

	/**
	 * 设置 合大票清单明细.
	 *
	 * @param airPickupbillDetailEntity the new 合大票清单明细
	 */
	public void setAirPickupbillDetailEntity(
			AirPickupbillDetailEntity airPickupbillDetailEntity) {
		this.airPickupbillDetailEntity = airPickupbillDetailEntity;
	}

	/**
	 * 获取 合大票清单.
	 *
	 * @return the 合大票清单
	 */
	public AirPickupbillEntity getAirPickupbillEntity() {
		return airPickupbillEntity;
	}

	/**
	 * 设置 合大票清单.
	 *
	 * @param airPickupbillEntity the new 合大票清单
	 */
	public void setAirPickupbillEntity(AirPickupbillEntity airPickupbillEntity) {
		this.airPickupbillEntity = airPickupbillEntity;
	}

	/**
	 * 获取 合大票清单list.
	 *
	 * @return the 合大票清单list
	 */
	public List<AirPickupbillEntity> getAirPickupbillList() {
		return airPickupbillList;
	}

	/**
	 * 设置 合大票清单list.
	 *
	 * @param airPickupbillList the new 合大票清单list
	 */
	public void setAirPickupbillList(List<AirPickupbillEntity> airPickupbillList) {
		this.airPickupbillList = airPickupbillList;
	}

	/**
	 * 获取 合大票明细日志.
	 *
	 * @return the 合大票明细日志
	 */
	public List<AirRevisebillDetailEntity> getAirRevisebillDetailList() {
		return airRevisebillDetailList;
	}

	/**
	 * 设置 合大票明细日志.
	 * @param airRevisebillDetailList the new 合大票明细日志
	 */
	public void setAirRevisebillDetailList(
			List<AirRevisebillDetailEntity> airRevisebillDetailList) {
		this.airRevisebillDetailList = airRevisebillDetailList;
	}

	/**
	 * 获取 变更清单List.
	 * @return the 变更清单List
	 */
	public List<AirChangeInventoryDetailEntity> getAirChangeInventoryDetailList() {
		return airChangeInventoryDetailList;
	}

	/**
	 * 设置 变更清单List.
	 * @param airChangeInventoryDetailList the new 变更清单List
	 */
	public void setAirChangeInventoryDetailList(
			List<AirChangeInventoryDetailEntity> airChangeInventoryDetailList) {
		this.airChangeInventoryDetailList = airChangeInventoryDetailList;
	}

	/**
	 * @return 
	 */
	public Map<String, String> getStlWayBillNoMap() {
		return stlWayBillNoMap;
	}

	/**
	 * @param stlWayBillNoMap 
	 */
	public void setStlWayBillNoMap(Map<String, String> stlWayBillNoMap) {
		this.stlWayBillNoMap = stlWayBillNoMap;
	}

	/**
	 * @return 
	 */
	public Map<String, String> getDelWayBillNoMap() {
		return delWayBillNoMap;
	}

	/**
	 * @param delWayBillNoMap 
	 */
	public void setDelWayBillNoMap(Map<String, String> delWayBillNoMap) {
		this.delWayBillNoMap = delWayBillNoMap;
	}

	/**
	 * @return 
	 */
	public List<AirPickupbillDetailEntity> getDeletePickupDetailList() {
		return deletePickupDetailList;
	}

	/**
	 * @param deletePickupDetailList 
	 */
	public void setDeletePickupDetailList(List<AirPickupbillDetailEntity> deletePickupDetailList) {
		this.deletePickupDetailList = deletePickupDetailList;
	}
	
	/**
	 * @return 
	 */
	public List<AirPickupbillDetailEntity> getAddPickupDetailList() {
		return addPickupDetailList;
	}

	/**
	 * @param addPickupDetailList 
	 */
	public void setAddPickupDetailList(
			List<AirPickupbillDetailEntity> addPickupDetailList) {
		this.addPickupDetailList = addPickupDetailList;
	}

	/**
	 * @return 
	 */
	public List<AirPickupbillDetailEntity> getStlPickupDetailList() {
		return stlPickupDetailList;
	}

	/**
	 * @param stlPickupDetailList 
	 */
	public void setStlPickupDetailList(
			List<AirPickupbillDetailEntity> stlPickupDetailList) {
		this.stlPickupDetailList = stlPickupDetailList;
	}

	/**
	 * @return 
	 */
	public Map<String, String> getModifyNotesMaps() {
		return modifyNotesMaps;
	}

	/**
	 * @param modifyNotesMaps 
	 */
	public void setModifyNotesMaps(Map<String, String> modifyNotesMaps) {
		this.modifyNotesMaps = modifyNotesMaps;
	}

	/**
	 * @return 
	 */
	public String getAirPickupbillId() {
		return airPickupbillId;
	}

	/**
	 * @param airPickupbillId 
	 */
	public void setAirPickupbillId(String airPickupbillId) {
		this.airPickupbillId = airPickupbillId;
	}

	/**
	 * @return 
	 */
	public List<AirTransPickupbillEntity> getAirTransPickupbillList() {
		return airTransPickupbillList;
	}

	/**
	 * @param airTransPickupbillList 
	 */
	public void setAirTransPickupbillList(
			List<AirTransPickupbillEntity> airTransPickupbillList) {
		this.airTransPickupbillList = airTransPickupbillList;
	}

	/**
	 * @return 
	 */
	public List<AirTransPickupDetailEntity> getAirTransPickupDetailList() {
		return airTransPickupDetailList;
	}

	/**
	 * @param airTransPickupDetailList 
	 */
	public void setAirTransPickupDetailList(
			List<AirTransPickupDetailEntity> airTransPickupDetailList) {
		this.airTransPickupDetailList = airTransPickupDetailList;
	}

	/**
	 * @return 
	 */
	public List<AirTransPickupDetailEntity> getDelTransPickupDetailList() {
		return delTransPickupDetailList;
	}

	/**
	 * @param delTransPickupDetailList 
	 */
	public void setDelTransPickupDetailList(
			List<AirTransPickupDetailEntity> delTransPickupDetailList) {
		this.delTransPickupDetailList = delTransPickupDetailList;
	}

	/**
	 * @return 
	 */
	public List<AirTransPickupDetailEntity> getCallStlTransPickupDetailList() {
		return callStlTransPickupDetailList;
	}

	/**
	 * @param callStlTransPickupDetailList 
	 */
	public void setCallStlTransPickupDetailList(
			List<AirTransPickupDetailEntity> callStlTransPickupDetailList) {
		this.callStlTransPickupDetailList = callStlTransPickupDetailList;
	}

	/**
	 * @return 
	 */
	public String getAirTransferPickUpBillId() {
		return airTransferPickUpBillId;
	}

	/**
	 * @param airTransferPickUpBillId 
	 */
	public void setAirTransferPickUpBillId(String airTransferPickUpBillId) {
		this.airTransferPickUpBillId = airTransferPickUpBillId;
	}

	/**
	 * @return 
	 */
	public List<AirTransPickupDetailEntity> getModifyTransPickupDetailList() {
		return modifyTransPickupDetailList;
	}

	/**
	 * @param modifyTransPickupDetailList 
	 */
	public void setModifyTransPickupDetailList(
			List<AirTransPickupDetailEntity> modifyTransPickupDetailList) {
		this.modifyTransPickupDetailList = modifyTransPickupDetailList;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public String getAirWaybillNo() {
		return airWaybillNo;
	}

	public void setAirWaybillNo(String airWaybillNo) {
		this.airWaybillNo = airWaybillNo;
	}

	public String getCallIsNotEdiFlag() {
		return callIsNotEdiFlag;
	}

	public void setCallIsNotEdiFlag(String callIsNotEdiFlag) {
		this.callIsNotEdiFlag = callIsNotEdiFlag;
	}

	public String getFightNo() {
		return fightNo;
	}

	public void setFightNo(String fightNo) {
		this.fightNo = fightNo;
	}

	public List<SerialEntity> getSerialList() {
		return serialList;
	}

	public void setSerialList(List<SerialEntity> serialList) {
		this.serialList = serialList;
	}

	public SerialEntity getSerialEntity() {
		return serialEntity;
	}

	public void setSerialEntity(SerialEntity serialEntity) {
		this.serialEntity = serialEntity;
	}

}