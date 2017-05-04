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
 *  PROJECT NAME  : tfr-stockchecking-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/api/shared/dto/GoodsStockDto.java
 *  
 *  FILE NAME          :GoodsStockDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stockchecking.api.shared.dto;

import java.util.List;

/**
 * 用于库区查询
 * @author foss-wuyingjie
 * @date 2012-11-7 下午2:01:01
 */
public class GoodsStockDto {
	
	/** 获取库区信息查询条件 **/
	
	/**货区，输入获取名称或编号*/
	private String goodsArea; 		
	/**长短途货区 ，取自综合 货区-库区类别字段*/
	private String goodsAreaUsage; 	
	/**清仓任务状态*/
	private String taskStatus;
	/**部门编号*/
	private String deptNo;		
	/**清仓任务ID*/
	private String stTaskId;		
	/**当前登录人登陆时部门编号*/
	public String currentDeptCode;
	
	/** 获取库区信息查询结果集 **/
	
	/**货区编号*/
	private String goodsAreaCode;
	/**货区名称*/
	private String goodsAreaName;	
	/**货区类型*/
	private String goodsAreaType;
	/**运单数*/
	private Integer waybillNum;		
	/**库存件数*/
	private Integer stockGoodsQty;	
	/**总重量*/
	private Double goodWeightTotal;	
	/**总体积*/
	private Double goodVolumeTotal;  
	
	/**起始件数*/
	private Integer startQty;
	/**结束件数*/
	private Integer endQty;
	
	/**提货方式*/
	private String receiveMethod;
	/**分区名称 */
	private String districtCode;
	/**实际提货方式*/
	private List<String> receiveMethodList;
	/**分区对应的行政区域*/
	private List<String> districtCodeList;
	
	public String getReceiveMethod() {
		return receiveMethod;
	}

	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public List<String> getReceiveMethodList() {
		return receiveMethodList;
	}

	public void setReceiveMethodList(List<String> receiveMethodList) {
		this.receiveMethodList = receiveMethodList;
	}

	public List<String> getDistrictCodeList() {
		return districtCodeList;
	}

	public void setDistrictCodeList(List<String> districtCodeList) {
		this.districtCodeList = districtCodeList;
	}

	public Integer getStartQty() {
		return startQty;
	}

	public void setStartQty(Integer startQty) {
		this.startQty = startQty;
	}

	public Integer getEndQty() {
		return endQty;
	}

	public void setEndQty(Integer endQty) {
		this.endQty = endQty;
	}

	/**
	 * 获取 获取库区信息查询结果集 *.
	 *
	 * @return the 获取库区信息查询结果集 *
	 */
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}
	
	/**
	 * 设置 获取库区信息查询结果集 *.
	 *
	 * @param goodsAreaCode the new 获取库区信息查询结果集 *
	 */
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}
	
	/**
	 * 获取 货区名称.
	 *
	 * @return the 货区名称
	 */
	public String getGoodsAreaName() {
		return goodsAreaName;
	}
	
	/**
	 * 设置 货区名称.
	 *
	 * @param goodsAreaName the new 货区名称
	 */
	public void setGoodsAreaName(String goodsAreaName) {
		this.goodsAreaName = goodsAreaName;
	}
	
	/**
	 * 获取 运单数.
	 *
	 * @return the 运单数
	 */
	public Integer getWaybillNum() {
		return waybillNum;
	}
	
	/**
	 * 设置 运单数.
	 *
	 * @param waybillNum the new 运单数
	 */
	public void setWaybillNum(Integer waybillNum) {
		this.waybillNum = waybillNum;
	}
	
	/**
	 * 获取 库存件数.
	 *
	 * @return the 库存件数
	 */
	public Integer getStockGoodsQty() {
		return stockGoodsQty;
	}
	
	/**
	 * 设置 库存件数.
	 *
	 * @param stockGoodsQty the new 库存件数
	 */
	public void setStockGoodsQty(Integer stockGoodsQty) {
		this.stockGoodsQty = stockGoodsQty;
	}
	
	/**
	 * 获取 总重量.
	 *
	 * @return the 总重量
	 */
	public Double getGoodWeightTotal() {
		return goodWeightTotal;
	}
	
	/**
	 * 设置 总重量.
	 *
	 * @param goodWeightTotal the new 总重量
	 */
	public void setGoodWeightTotal(Double goodWeightTotal) {
		this.goodWeightTotal = goodWeightTotal;
	}
	
	/**
	 * 获取 总体积.
	 *
	 * @return the 总体积
	 */
	public Double getGoodVolumeTotal() {
		return goodVolumeTotal;
	}
	
	/**
	 * 设置 总体积.
	 *
	 * @param goodVolumeTotal the new 总体积
	 */
	public void setGoodVolumeTotal(Double goodVolumeTotal) {
		this.goodVolumeTotal = goodVolumeTotal;
	}
	
	/**
	 * 获取 获取库区信息查询条件 *.
	 *
	 * @return the 获取库区信息查询条件 *
	 */
	public String getGoodsArea() {
		return goodsArea;
	}
	
	/**
	 * 设置 获取库区信息查询条件 *.
	 *
	 * @param goodsArea the new 获取库区信息查询条件 *
	 */
	public void setGoodsArea(String goodsArea) {
		this.goodsArea = goodsArea;
	}
	
	/**
	 * 获取 长短途货区 ，取自综合 货区-库区类别字段.
	 *
	 * @return the 长短途货区 ，取自综合 货区-库区类别字段
	 */
	public String getGoodsAreaUsage() {
		return goodsAreaUsage;
	}
	
	/**
	 * 设置 长短途货区 ，取自综合 货区-库区类别字段.
	 *
	 * @param goodsAreaUsage the new 长短途货区 ，取自综合 货区-库区类别字段
	 */
	public void setGoodsAreaUsage(String goodsAreaUsage) {
		this.goodsAreaUsage = goodsAreaUsage;
	}
	
	/**
	 * 获取 清仓任务状态.
	 *
	 * @return the 清仓任务状态
	 */
	public String getTaskStatus() {
		return taskStatus;
	}
	
	/**
	 * 设置 清仓任务状态.
	 *
	 * @param taskStatus the new 清仓任务状态
	 */
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	
	/**
	 * 获取 部门编号.
	 *
	 * @return the 部门编号
	 */
	public String getDeptNo() {
		return deptNo;
	}
	
	/**
	 * 设置 部门编号.
	 *
	 * @param deptNo the new 部门编号
	 */
	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}
	
	/**
	 * 获取 清仓任务ID.
	 *
	 * @return the 清仓任务ID
	 */
	public String getStTaskId() {
		return stTaskId;
	}
	
	/**
	 * 设置 清仓任务ID.
	 *
	 * @param stTaskId the new 清仓任务ID
	 */
	public void setStTaskId(String stTaskId) {
		this.stTaskId = stTaskId;
	}

	public String getCurrentDeptCode() {
		return currentDeptCode;
	}

	public void setCurrentDeptCode(String currentDeptCode) {
		this.currentDeptCode = currentDeptCode;
	}

	public String getGoodsAreaType() {
		return goodsAreaType;
	}

	public void setGoodsAreaType(String goodsAreaType) {
		this.goodsAreaType = goodsAreaType;
	}
}