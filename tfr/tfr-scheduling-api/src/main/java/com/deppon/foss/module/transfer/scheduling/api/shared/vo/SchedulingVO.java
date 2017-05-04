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
 *  
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/vo/SchedulingVO.java
 * 
 *  FILE NAME     :SchedulingVO.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.scheduling.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.transfer.common.api.shared.dto.BaseDataDictDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.AdjustEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.ChangePathEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PathDetailEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TransportPathEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.DepartureDto;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;

/**
 * 走货路径VO
 */
public class SchedulingVO implements java.io.Serializable{

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 2675931990243271502L;
	/**
	 * 待修改运单号list
	 */
	private List<String> waybillList;
	/**
	 * 待修改货区list
	 */
	private List<String> areaCodeList;
	/**
	 * 下一可到外场编码list
	 */
	private List<String> nextOrgCode;
	
	private List<String> nextOrgName;

	/**
	 * 修改货物走货路径entity
	 */
	private AdjustEntity adjustEntity;
	
	/**
	 * 走货路径entity
	 */
	private TransportPathEntity transportPathEntity;
	
	/**
	 * 走货路径明细entity
	 */
	private PathDetailEntity pathDetailEntity;
	
	/**
	 * 修改走货路径entity
	 */
	private ChangePathEntity changePathEntity;

	/**
	 * 修改货物走货路径entity list
	 */
	private List<AdjustEntity> adjustList;
	
	/**
	 * 走货路径entity list
	 */
	private List<TransportPathEntity> transportPathList;
	
	/**
	 * 走货路径明细entity list
	 */
	private List<PathDetailEntity> pathDetailList;
	
	/**
	 * 修改走货路径entity list
	 */
	private List<ChangePathEntity> changePathList;
	
	/**
	 * 班次DTO
	 */
	private List<DepartureDto> departureDto;
	
	/**
	 * 库存list
	 */
	private List<StockEntity>  stockEntityList;
	
	
	// modify by liangfuxiang 2013-6-1下午2:14:14 begin BUG-12926
	/**
	 * 外场编码
	 */
	private String outOrgCode;
	/**
	 * 快递库区专用
	 * */
	private String goodsAreaCode;
	
	
	/**
	* 快递 目的站的部门名称和code 集合
	* @fields packageOrgList
	* @author 14022-foss-songjie 
	* @update 2014年2月18日 上午11:26:38
	* @version V1.0
	*/
	private List<BaseDataDictDto> packageOrgList;

	/**
	 * outOrgCode
	 * 
	 * @return the outOrgCode
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public String getOutOrgCode() {
		return outOrgCode;
	}

	/**
	 * @param outOrgCode the outOrgCode to set Date:2013-6-1下午2:14:43
	 */

	public void setOutOrgCode(String outOrgCode) {
		this.outOrgCode = outOrgCode;
	}

	// modify by liangfuxiang 2013-6-1下午2:14:29 end;BUG-12926
	
	/**
	 * 获取 待修改运单号list.
	 *
	 * @return the 待修改运单号list
	 */
	public List<String> getWaybillList() {
		return waybillList;
	}

	/**
	 * 设置 待修改运单号list.
	 *
	 * @param waybillList the new 待修改运单号list
	 */
	public void setWaybillList(List<String> waybillList) {
		this.waybillList = waybillList;
	}

	/**
	 * 获取 待修改货区list.
	 *
	 * @return the 待修改货区list
	 */
	public List<String> getAreaCodeList() {
		return areaCodeList;
	}

	/**
	 * 设置 待修改货区list.
	 *
	 * @param areaCodeList the new 待修改货区list
	 */
	public void setAreaCodeList(List<String> areaCodeList) {
		this.areaCodeList = areaCodeList;
	}

	/**
	 * 获取 修改货物走货路径entity.
	 *
	 * @return the 修改货物走货路径entity
	 */
	public AdjustEntity getAdjustEntity() {
		return adjustEntity;
	}

	/**
	 * 设置 修改货物走货路径entity.
	 *
	 * @param adjustEntity the new 修改货物走货路径entity
	 */
	public void setAdjustEntity(AdjustEntity adjustEntity) {
		this.adjustEntity = adjustEntity;
	}

	/**
	 * 获取 修改货物走货路径entity list.
	 *
	 * @return the 修改货物走货路径entity list
	 */
	public List<AdjustEntity> getAdjustList() {
		return adjustList;
	}

	/**
	 * 设置 修改货物走货路径entity list.
	 *
	 * @param adjustList the new 修改货物走货路径entity list
	 */
	public void setAdjustList(List<AdjustEntity> adjustList) {
		this.adjustList = adjustList;
	}
	
	/**
	 * 获取 走货路径entity.
	 *
	 * @return the 走货路径entity
	 */
	public TransportPathEntity getTransportPathEntity() {
		return transportPathEntity;
	}

	/**
	 * 设置 走货路径entity.
	 *
	 * @param transportPathEntity the new 走货路径entity
	 */
	public void setTransportPathEntity(TransportPathEntity transportPathEntity) {
		this.transportPathEntity = transportPathEntity;
	}

	/**
	 * 获取 走货路径明细entity.
	 *
	 * @return the 走货路径明细entity
	 */
	public PathDetailEntity getPathDetailEntity() {
		return pathDetailEntity;
	}

	/**
	 * 设置 走货路径明细entity.
	 *
	 * @param pathDetailEntity the new 走货路径明细entity
	 */
	public void setPathDetailEntity(PathDetailEntity pathDetailEntity) {
		this.pathDetailEntity = pathDetailEntity;
	}

	/**
	 * 获取 修改走货路径entity.
	 *
	 * @return the 修改走货路径entity
	 */
	public ChangePathEntity getChangePathEntity() {
		return changePathEntity;
	}

	/**
	 * 设置 修改走货路径entity.
	 *
	 * @param changePathEntity the new 修改走货路径entity
	 */
	public void setChangePathEntity(ChangePathEntity changePathEntity) {
		this.changePathEntity = changePathEntity;
	}

	/**
	 * 获取 走货路径entity list.
	 *
	 * @return the 走货路径entity list
	 */
	public List<TransportPathEntity> getTransportPathList() {
		return transportPathList;
	}

	/**
	 * 设置 走货路径entity list.
	 *
	 * @param transportPathList the new 走货路径entity list
	 */
	public void setTransportPathList(List<TransportPathEntity> transportPathList) {
		this.transportPathList = transportPathList;
	}

	/**
	 * 获取 走货路径明细entity list.
	 *
	 * @return the 走货路径明细entity list
	 */
	public List<PathDetailEntity> getPathDetailList() {
		return pathDetailList;
	}

	/**
	 * 设置 走货路径明细entity list.
	 *
	 * @param pathDetailList the new 走货路径明细entity list
	 */
	public void setPathDetailList(List<PathDetailEntity> pathDetailList) {
		this.pathDetailList = pathDetailList;
	}

	/**
	 * 获取 修改走货路径entity list.
	 *
	 * @return the 修改走货路径entity list
	 */
	public List<ChangePathEntity> getChangePathList() {
		return changePathList;
	}

	/**
	 * 设置 修改走货路径entity list.
	 *
	 * @param changePathList the new 修改走货路径entity list
	 */
	public void setChangePathList(List<ChangePathEntity> changePathList) {
		this.changePathList = changePathList;
	}

	/**
	 * 获取 下一可到外场编码list.
	 *
	 * @return the 下一可到外场编码list
	 */
	public List<String> getNextOrgCode() {
		return nextOrgCode;
	}

	/**
	 * 设置 下一可到外场编码list.
	 *
	 * @param nextOrgCode the new 下一可到外场编码list
	 */
	public void setNextOrgCode(List<String> nextOrgCode) {
		this.nextOrgCode = nextOrgCode;
	}

	/**
	 * 获取 班次DTO.
	 *
	 * @return the 班次DTO
	 */
	public List<DepartureDto> getDepartureDto() {
		return departureDto;
	}

	/**
	 * 设置 班次DTO.
	 *
	 * @param departureDto the new 班次DTO
	 */
	public void setDepartureDto(List<DepartureDto> departureDto) {
		this.departureDto = departureDto;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public List<String> getNextOrgName() {
		return nextOrgName;
	}

	/**
	 * 
	 *
	 * @param nextOrgName 
	 */
	public void setNextOrgName(List<String> nextOrgName) {
		this.nextOrgName = nextOrgName;
	}
	
	public List<StockEntity> getStockEntityList() {
		return stockEntityList;
	}

	public void setStockEntityList(List<StockEntity> stockEntityList) {
		this.stockEntityList = stockEntityList;
	}

	
	/**
	* @description 获取  快递 目的站的部门名称和code 集合
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年2月18日 上午11:28:30
	*/
	public List<BaseDataDictDto> getPackageOrgList() {
		return packageOrgList;
	}

	
	/**
	* @description 设置 快递 目的站的部门名称和code 集合
	* @param packageOrgList
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年2月18日 上午11:28:45
	*/
	public void setPackageOrgList(List<BaseDataDictDto> packageOrgList) {
		this.packageOrgList = packageOrgList;
	}

	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}

	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}
	
	

}