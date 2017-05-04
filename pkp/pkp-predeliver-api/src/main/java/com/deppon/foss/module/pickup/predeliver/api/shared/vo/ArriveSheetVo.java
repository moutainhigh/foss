/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/vo/ArriveSheetVo.java
 * 
 * FILE NAME        	: ArriveSheetVo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveSheetDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveSheetWaybillAddPropertyDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveSheetWaybillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignArriveSheetDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;

/**
 * 到达联VO
 * @author dp-dengtingting
 */
public class ArriveSheetVo implements Serializable {

	private static final long serialVersionUID = 9188497837257143403L;
	
	private  ArriveSheetEntity arriveSheet;   //到达联实体
	 
	private WaybillDto waybillDto;  //运单详细信息Dto
	
	//到达联  运单信息
	private ArriveSheetWaybillAddPropertyDto arriveSheetWaybillAddPropertyDto; 
	
	private List<ArriveSheetEntity> arriveSheetList;//到达联list

	private List<ArriveSheetDto> arriveDtoList;  //到达联集合
	
	private List<SignArriveSheetDto> signArriveSheetDtoList;  //签收出库   查询运单到达联集合
	private ArriveSheetDto arriveSheetDto = new ArriveSheetDto();  //到达联管理DTO
	
	private List<ArriveSheetWaybillDto> arriveSheetWaybillList;  //根据运单信息查询到达联表格List
	
	private String waybillNos; //运单号集合
	private String source; //到达联打印来源
	
	private String arriveSheetNos; //到达联编号集合
	
	//private String arriveNotoutGoodsQtys; //库存件数集合
	private String arriveSheetGoodsQtys; //到达联件数集合
	private Long totalCount;//自提签收记录签收条数
	
	//根据运单查询到达联表格DTO
	private ArriveSheetWaybillDto arriveSheetWaybillDto = new ArriveSheetWaybillDto();
	
	/**
	 * @return the waybillNos
	 */
	public String getWaybillNos() {
		return waybillNos;
	}

	/**
	 * @param waybillNos the waybillNos to see
	 */
	public void setWaybillNos(String waybillNos) {
		this.waybillNos = waybillNos;
	}

	/**
	 * @return the arriveDtoList
	 */
	public List<ArriveSheetDto> getArriveDtoList() {
		return arriveDtoList;
	}

	/**
	 * @param arriveDtoList the arriveDtoList to see
	 */
	public void setArriveDtoList(List<ArriveSheetDto> arriveDtoList) {
		this.arriveDtoList = arriveDtoList;
	}

	/**
	 * @return the arriveSheet
	 */
	public ArriveSheetEntity getArriveSheet() {
		return arriveSheet;
	}

	/**
	 * @param arriveSheet the arriveSheet to see
	 */
	public void setArriveSheet(ArriveSheetEntity arriveSheet) {
		this.arriveSheet = arriveSheet;
	}

	/**
	 * @return the arriveSheetDto
	 */
	public ArriveSheetDto getArriveSheetDto() {
		return arriveSheetDto;
	}

	/**
	 * @param arriveSheetDto the arriveSheetDto to see
	 */
	public void setArriveSheetDto(ArriveSheetDto arriveSheetDto) {
		this.arriveSheetDto = arriveSheetDto;
	}

	/**
	 * @return the arriveSheetList
	 */
	public List<ArriveSheetEntity> getArriveSheetList() {
		return arriveSheetList;
	}

	/**
	 * @param arriveSheetList the arriveSheetList to see
	 */
	public void setArriveSheetList(List<ArriveSheetEntity> arriveSheetList) {
		this.arriveSheetList = arriveSheetList;
	}

	/**
	 * @return the arriveSheetWaybillDto
	 */
	public ArriveSheetWaybillDto getArriveSheetWaybillDto() {
		return arriveSheetWaybillDto;
	}

	/**
	 * @param arriveSheetWaybillDto the arriveSheetWaybillDto to see
	 */
	public void setArriveSheetWaybillDto(ArriveSheetWaybillDto arriveSheetWaybillDto) {
		this.arriveSheetWaybillDto = arriveSheetWaybillDto;
	}

	/**
	 * @return the arriveSheetWaybillList
	 */
	public List<ArriveSheetWaybillDto> getArriveSheetWaybillList() {
		return arriveSheetWaybillList;
	}

	/**
	 * @param arriveSheetWaybillList the arriveSheetWaybillList to see
	 */
	public void setArriveSheetWaybillList(List<ArriveSheetWaybillDto> arriveSheetWaybillList) {
		this.arriveSheetWaybillList = arriveSheetWaybillList;
	}

	/**
	 * @return the waybillDto
	 */
	public WaybillDto getWaybillDto() {
		return waybillDto;
	}

	/**
	 * @param waybillDto the waybillDto to see
	 */
	public void setWaybillDto(WaybillDto waybillDto) {
		this.waybillDto = waybillDto;
	}

	/**
	 * @return the arriveSheetNos
	 */
	public String getArriveSheetNos() {
		return arriveSheetNos;
	}

	/**
	 * @param arriveSheetNos the arriveSheetNos to see
	 */
	public void setArriveSheetNos(String arriveSheetNos) {
		this.arriveSheetNos = arriveSheetNos;
	}

	/**
	 * @return the signArriveSheetDtoList
	 */
	public List<SignArriveSheetDto> getSignArriveSheetDtoList() {
		return signArriveSheetDtoList;
	}

	/**
	 * @param signArriveSheetDtoList the signArriveSheetDtoList to see
	 */
	public void setSignArriveSheetDtoList(List<SignArriveSheetDto> signArriveSheetDtoList) {
		this.signArriveSheetDtoList = signArriveSheetDtoList;
	}

	/**
	 * @return the arriveSheetGoodsQtys
	 */
	public String getArriveSheetGoodsQtys() {
		return arriveSheetGoodsQtys;
	}

	/**
	 * @param arriveSheetGoodsQtys the arriveSheetGoodsQtys to see
	 */
	public void setArriveSheetGoodsQtys(String arriveSheetGoodsQtys) {
		this.arriveSheetGoodsQtys = arriveSheetGoodsQtys;
	}

	public ArriveSheetWaybillAddPropertyDto getArriveSheetWaybillAddPropertyDto() {
		return arriveSheetWaybillAddPropertyDto;
	}

	public void setArriveSheetWaybillAddPropertyDto(
			ArriveSheetWaybillAddPropertyDto arriveSheetWaybillAddPropertyDto) {
		this.arriveSheetWaybillAddPropertyDto = arriveSheetWaybillAddPropertyDto;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	
	

}