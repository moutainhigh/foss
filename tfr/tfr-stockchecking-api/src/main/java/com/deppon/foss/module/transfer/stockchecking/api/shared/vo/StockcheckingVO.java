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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/api/shared/vo/StockcheckingVO.java
 *  
 *  FILE NAME          :StockcheckingVO.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stockchecking.api.shared.vo;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.transfer.common.api.shared.dto.BaseDataDictDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.ScanDetailDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StOperatorEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.GoodsStockDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StTaskDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StTaskWaybillNoListDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StWaybillInfoDto;

/**
 * 清仓任务VO
 * @author foss-wuyingjie
 * @date 2012-12-26 上午9:57:36
 */
public class StockcheckingVO {
	/**清仓任务查询条件、清仓任务新增页面相关元素、清仓任务编辑页面相关元素*/
	private StTaskDto stTaskDto = new StTaskDto();										  		
	/**清仓任务查询结果集*/
	private List<StTaskDto> stTaskDtoList = new ArrayList<StTaskDto>();					  		
	/**库区查询条件*/
	private GoodsStockDto goodsStockDto = new GoodsStockDto();		      				  		
	/**库区查询结果集*/
	private List<GoodsStockDto> goodsStockDtoList = new ArrayList<GoodsStockDto>(); 	  		
	/**清仓人下拉待选列表*/
	private List<StOperatorEntity> stOperatorEntityList = new ArrayList<StOperatorEntity>();
	/**页面中已勾选的库区*/
	private List<String> selectedGoodsAreasList = new ArrayList<String>();				  		
	/**已选择的清仓人列表*/
	private List<StOperatorEntity> selectedOperatorList = new ArrayList<StOperatorEntity>();	
	/**长短途货区下拉框*/
	private List<BaseDataDictDto> goodsAreaUsageList = new ArrayList<BaseDataDictDto>();	
	/**清仓货物运单清单、人工确认清仓任务页面初始载入*/
	private List<StTaskWaybillNoListDto> stTaskWaybillNoList = new ArrayList<StTaskWaybillNoListDto>();			
	/**正常确认货件*/
	private List<StTaskWaybillNoListDto> scanResultOkList = new ArrayList<StTaskWaybillNoListDto>();	
	/**少货货件*/
	private List<StTaskWaybillNoListDto> scanResultHaveNotList = new ArrayList<StTaskWaybillNoListDto>();	
	/**多货货件*/
	private List<StTaskWaybillNoListDto> scanResultSurplusList = new ArrayList<StTaskWaybillNoListDto>();	
	/**清仓任务明细中的运单统计信息*/
	private List<StWaybillInfoDto> stWaybillInfoDtoList = new ArrayList<StWaybillInfoDto>();
	/**扫描明细列表*/
	private List<ScanDetailDto> scanDetailDtoList = new ArrayList<ScanDetailDto>();	
	/**是否是外场*/
	private String isTransferCenter;
	/**库存部门*/
	private String stockOrgCode;
	/**起始件数*/
	private Integer startQty;
	/**结束件数*/
	private Integer endQty;
	/**是否驻地派送货区*/
	private boolean basStation;
	
	/**当前转运场库区个数**/
	private String goodsAreaCount;
	/**已完成清仓任务数**/
	private String taskDoneCount; 
	/**未完成任务，库区数**/
	private String areaUndoCount;
	
	/**是否试点外场*/
	private boolean testTrans;
	/**提货方式*/
	private String receiveMethod;
	/**分区*/
	private String districtCode;
	/**分区名称*/
	private String districtName;
	/**是否是快递员*/
	private boolean queryIfIsExpressClerk;
	/**是否为驻地营业部**/
	public String isBasDept;
	/**是不是创建快递清仓任务**/
	public String stType;
	
	public String getStType() {
		return stType;
	}

	public void setStType(String stType) {
		this.stType = stType;
	}

	public String getIsBasDept() {
		return isBasDept;
	}

	public void setIsBasDept(String isBasDept) {
		this.isBasDept = isBasDept;
	}

	public boolean isQueryIfIsExpressClerk() {
		return queryIfIsExpressClerk;
	}

	public void setQueryIfIsExpressClerk(boolean queryIfIsExpressClerk) {
		this.queryIfIsExpressClerk = queryIfIsExpressClerk;
	}

	/**所属外场code*/
	private String transferCode;
	
	public String getTransferCode() {
		return transferCode;
	}

	public void setTransferCode(String transferCode) {
		this.transferCode = transferCode;
	}

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

	public boolean isTestTrans() {
		return testTrans;
	}

	public void setTestTrans(boolean testTrans) {
		this.testTrans = testTrans;
	}

	
	public boolean isBasStation() {
		return basStation;
	}

	public void setBasStation(boolean basStation) {
		this.basStation = basStation;
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


	public String getGoodsAreaCount() {
		return goodsAreaCount;
	}

	public void setGoodsAreaCount(String goodsAreaCount) {
		this.goodsAreaCount = goodsAreaCount;
	}

	public String getTaskDoneCount() {
		return taskDoneCount;
	}

	public void setTaskDoneCount(String taskDoneCount) {
		this.taskDoneCount = taskDoneCount;
	}

	public String getAreaUndoCount() {
		return areaUndoCount;
	}

	public void setAreaUndoCount(String areaUndoCount) {
		this.areaUndoCount = areaUndoCount;
	}

	/**
	 * 获取 页面中已勾选的库区.
	 *
	 * @return the 页面中已勾选的库区
	 */
	public List<String> getSelectedGoodsAreasList() {
		return selectedGoodsAreasList;
	}

	/**
	 * 设置 页面中已勾选的库区.
	 *
	 * @param selectedGoodsAreasList the new 页面中已勾选的库区
	 */
	public void setSelectedGoodsAreasList(List<String> selectedGoodsAreasList) {
		this.selectedGoodsAreasList = selectedGoodsAreasList;
	}

	/**
	 * 获取 清仓任务查询条件、清仓任务新增页面相关元素、清仓任务编辑页面相关元素.
	 *
	 * @return the 清仓任务查询条件、清仓任务新增页面相关元素、清仓任务编辑页面相关元素
	 */
	public StTaskDto getStTaskDto() {
		return stTaskDto;
	}

	/**
	 * 设置 清仓任务查询条件、清仓任务新增页面相关元素、清仓任务编辑页面相关元素.
	 *
	 * @param stTaskDto the new 清仓任务查询条件、清仓任务新增页面相关元素、清仓任务编辑页面相关元素
	 */
	public void setStTaskDto(StTaskDto stTaskDto) {
		this.stTaskDto = stTaskDto;
	}
	
	/**
	 * 获取 清仓任务查询结果集.
	 *
	 * @return the 清仓任务查询结果集
	 */
	public List<StTaskDto> getStTaskDtoList() {
		return stTaskDtoList;
	}

	/**
	 * 设置 清仓任务查询结果集.
	 *
	 * @param stTaskDtoList the new 清仓任务查询结果集
	 */
	public void setStTaskDtoList(List<StTaskDto> stTaskDtoList) {
		this.stTaskDtoList = stTaskDtoList;
	}

	/**
	 * 获取 库区查询条件.
	 *
	 * @return the 库区查询条件
	 */
	public GoodsStockDto getGoodsStockDto() {
		return goodsStockDto;
	}

	/**
	 * 设置 库区查询条件.
	 *
	 * @param goodsStockDto the new 库区查询条件
	 */
	public void setGoodsStockDto(GoodsStockDto goodsStockDto) {
		this.goodsStockDto = goodsStockDto;
	}

	/**
	 * 获取 库区查询结果集.
	 *
	 * @return the 库区查询结果集
	 */
	public List<GoodsStockDto> getGoodsStockDtoList() {
		return goodsStockDtoList;
	}

	/**
	 * 设置 库区查询结果集.
	 *
	 * @param goodsStockDtoList the new 库区查询结果集
	 */
	public void setGoodsStockDtoList(List<GoodsStockDto> goodsStockDtoList) {
		this.goodsStockDtoList = goodsStockDtoList;
	}

	/**
	 * 获取 清仓人下拉待选列表.
	 *
	 * @return the 清仓人下拉待选列表
	 */
	public List<StOperatorEntity> getStOperatorEntityList() {
		return stOperatorEntityList;
	}

	/**
	 * 设置 清仓人下拉待选列表.
	 *
	 * @param stOperatorEntityList the new 清仓人下拉待选列表
	 */
	public void setStOperatorEntityList(List<StOperatorEntity> stOperatorEntityList) {
		this.stOperatorEntityList = stOperatorEntityList;
	}

	/**
	 * 获取 长短途货区下拉框.
	 *
	 * @return the 长短途货区下拉框
	 */
	public List<BaseDataDictDto> getGoodsAreaUsageList() {
		return goodsAreaUsageList;
	}

	/**
	 * 设置 长短途货区下拉框.
	 *
	 * @param goodsAreaUsageList the new 长短途货区下拉框
	 */
	public void setGoodsAreaUsageList(List<BaseDataDictDto> goodsAreaUsageList) {
		this.goodsAreaUsageList = goodsAreaUsageList;
	}

	/**
	 * 获取 已选择的清仓人列表.
	 *
	 * @return the 已选择的清仓人列表
	 */
	public List<StOperatorEntity> getSelectedOperatorList() {
		return selectedOperatorList;
	}

	/**
	 * 设置 已选择的清仓人列表.
	 *
	 * @param selectedOperatorList the new 已选择的清仓人列表
	 */
	public void setSelectedOperatorList(List<StOperatorEntity> selectedOperatorList) {
		this.selectedOperatorList = selectedOperatorList;
	}

	/**
	 * 获取 清仓货物运单清单、人工确认清仓任务页面初始载入.
	 *
	 * @return the 清仓货物运单清单、人工确认清仓任务页面初始载入
	 */
	public List<StTaskWaybillNoListDto> getStTaskWaybillNoList() {
		return stTaskWaybillNoList;
	}

	/**
	 * 设置 清仓货物运单清单、人工确认清仓任务页面初始载入.
	 *
	 * @param stTaskWaybillNoList the new 清仓货物运单清单、人工确认清仓任务页面初始载入
	 */
	public void setStTaskWaybillNoList(List<StTaskWaybillNoListDto> stTaskWaybillNoList) {
		this.stTaskWaybillNoList = stTaskWaybillNoList;
	}

	/**
	 * 获取 清仓任务明细中的运单统计信息.
	 *
	 * @return the 清仓任务明细中的运单统计信息
	 */
	public List<StWaybillInfoDto> getStWaybillInfoDtoList() {
		return stWaybillInfoDtoList;
	}

	/**
	 * 设置 清仓任务明细中的运单统计信息.
	 *
	 * @param stWaybillInfoDtoList the new 清仓任务明细中的运单统计信息
	 */
	public void setStWaybillInfoDtoList(List<StWaybillInfoDto> stWaybillInfoDtoList) {
		this.stWaybillInfoDtoList = stWaybillInfoDtoList;
	}

	/**
	 * 获取 扫描明细列表.
	 *
	 * @return the 扫描明细列表
	 */
	public List<ScanDetailDto> getScanDetailDtoList() {
		return scanDetailDtoList;
	}

	/**
	 * 设置 扫描明细列表.
	 *
	 * @param scanDetailDtoList the new 扫描明细列表
	 */
	public void setScanDetailDtoList(List<ScanDetailDto> scanDetailDtoList) {
		this.scanDetailDtoList = scanDetailDtoList;
	}

	/**
	 * 获取 正常确认货件.
	 *
	 * @return the 正常确认货件
	 */
	public List<StTaskWaybillNoListDto> getScanResultOkList() {
		return scanResultOkList;
	}

	/**
	 * 设置 正常确认货件.
	 *
	 * @param scanResultOkList the new 正常确认货件
	 */
	public void setScanResultOkList(List<StTaskWaybillNoListDto> scanResultOkList) {
		this.scanResultOkList = scanResultOkList;
	}

	/**
	 * 获取 少货货件.
	 *
	 * @return the 少货货件
	 */
	public List<StTaskWaybillNoListDto> getScanResultHaveNotList() {
		return scanResultHaveNotList;
	}

	/**
	 * 设置 少货货件.
	 *
	 * @param scanResultHaveNotList the new 少货货件
	 */
	public void setScanResultHaveNotList(
			List<StTaskWaybillNoListDto> scanResultHaveNotList) {
		this.scanResultHaveNotList = scanResultHaveNotList;
	}

	/**
	 * 获取 多货货件.
	 *
	 * @return the 多货货件
	 */
	public List<StTaskWaybillNoListDto> getScanResultSurplusList() {
		return scanResultSurplusList;
	}

	/**
	 * 设置 多货货件.
	 *
	 * @param scanResultSurplusList the new 多货货件
	 */
	public void setScanResultSurplusList(
			List<StTaskWaybillNoListDto> scanResultSurplusList) {
		this.scanResultSurplusList = scanResultSurplusList;
	}

	public String getIsTransferCenter() {
		return isTransferCenter;
	}

	public void setIsTransferCenter(String isTransferCenter) {
		this.isTransferCenter = isTransferCenter;
	}

	public String getStockOrgCode() {
		return stockOrgCode;
	}

	public void setStockOrgCode(String stockOrgCode) {
		this.stockOrgCode = stockOrgCode;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	
}