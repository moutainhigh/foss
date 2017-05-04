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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/api/shared/dto/StTaskDto.java
 *  
 *  FILE NAME          :StTaskDto.java
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

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StResultListEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StTaskEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StTaskListEntity;

/**
 * 用于清仓任务查询以及新增清仓任务、编辑清仓任务、确认清仓任务
 * @author foss-wuyingjie
 * @date 2012-11-7 下午2:01:17
 */
public class StTaskDto extends StTaskEntity{

	private static final long serialVersionUID = 5769807154900062051L;

	/** 清仓任务查询条件 **/
	
	/**货区，输入获取名称或编号*/
	public String goodsArea; 
	/**长短途货区 ，取自综合 货区-库区类别字段*/
	public String goodsAreaUsage; 
	/**传入的查询清仓人列表信息的参数*/
	public String operator;
	
	/** 清仓任务查询结果集 **/
	
	/**差异报告编号*/
	public String reportCode;
	/**差异报告处理状态*/
	public String handleStatus; 
	/**清仓人名称*/
	public String empName;	
	/**清仓人编号*/
	public String empCode; 
	/**任务状态对应的页面显示的文字*/
	public String taskStatusValue;	
	/**运单号，对应扫描明细页面需传入的参数*/
	public String waybillNo;
	/**库区编码列表*/
	public List<String> goodsAreaCodeList = new ArrayList<String>();
	/**清仓快照列表*/
	public List<StTaskListEntity> stSnapshotList = new ArrayList<StTaskListEntity>();
	/**清仓对比结果列表*/
	public List<StResultListEntity> stResultList = new ArrayList<StResultListEntity>();	
	
	/**当前登录人登陆时部门编号*/
	public String currentDeptCode;
	/**是否为外场*/
	public String transferCenter;
	/**是否存在未结束的任务*/
	public boolean existTaskInProcess;
	/**起始件数*/
	private Integer startQty;
	/**结束件数*/
	private Integer endQty;
	
	/**清仓中状态是否超过2个小时*/
	private String passTwoHours;
	
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
	 * 获取 清仓任务查询结果集 *.
	 *
	 * @return the 清仓任务查询结果集 *
	 */
	public String getReportCode() {
		return reportCode;
	}

	/**
	 * 设置 清仓任务查询结果集 *.
	 *
	 * @param reportCode the new 清仓任务查询结果集 *
	 */
	public void setReportCode(String reportCode) {
		this.reportCode = reportCode;
	}

	/**
	 * 获取 差异报告处理状态.
	 *
	 * @return the 差异报告处理状态
	 */
	public String getHandleStatus() {
		return handleStatus;
	}

	/**
	 * 设置 差异报告处理状态.
	 *
	 * @param handleStatus the new 差异报告处理状态
	 */
	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
	}
	
	/**
	 * 获取 清仓任务查询条件 *.
	 *
	 * @return the 清仓任务查询条件 *
	 */
	public String getGoodsArea() {
		return goodsArea;
	}

	/**
	 * 设置 清仓任务查询条件 *.
	 *
	 * @param goodsArea the new 清仓任务查询条件 *
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
	 * 获取 库区编码列表.
	 *
	 * @return the 库区编码列表
	 */
	public List<String> getGoodsAreaCodeList() {
		return goodsAreaCodeList;
	}

	/**
	 * 设置 库区编码列表.
	 *
	 * @param goodsAreaCodeList the new 库区编码列表
	 */
	public void setGoodsAreaCodeList(List<String> goodsAreaCodeList) {
		this.goodsAreaCodeList = goodsAreaCodeList;
	}

	/**
	 * 获取 清仓人名称.
	 *
	 * @return the 清仓人名称
	 */
	public String getEmpName() {
		return empName;
	}

	/**
	 * 设置 清仓人名称.
	 *
	 * @param empName the new 清仓人名称
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}

	/**
	 * 获取 清仓人编号.
	 *
	 * @return the 清仓人编号
	 */
	public String getEmpCode() {
		return empCode;
	}

	/**
	 * 设置 清仓人编号.
	 *
	 * @param empCode the new 清仓人编号
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	/**
	 * 获取 传入的查询清仓人列表信息的参数.
	 *
	 * @return the 传入的查询清仓人列表信息的参数
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * 设置 传入的查询清仓人列表信息的参数.
	 *
	 * @param operator the new 传入的查询清仓人列表信息的参数
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * 获取 任务状态对应的页面显示的文字.
	 *
	 * @return the 任务状态对应的页面显示的文字
	 */
	public String getTaskStatusValue() {
		return taskStatusValue;
	}

	/**
	 * 设置 任务状态对应的页面显示的文字.
	 *
	 * @param taskStatusValue the new 任务状态对应的页面显示的文字
	 */
	public void setTaskStatusValue(String taskStatusValue) {
		this.taskStatusValue = taskStatusValue;
	}

	/**
	 * 获取 运单号，对应扫描明细页面需传入的参数.
	 *
	 * @return the 运单号，对应扫描明细页面需传入的参数
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * 设置 运单号，对应扫描明细页面需传入的参数.
	 *
	 * @param waybillNo the new 运单号，对应扫描明细页面需传入的参数
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * 获取 清仓快照列表.
	 *
	 * @return the 清仓快照列表
	 */
	public List<StTaskListEntity> getStSnapshotList() {
		return stSnapshotList;
	}

	/**
	 * 设置 清仓快照列表.
	 *
	 * @param stSnapshotList the new 清仓快照列表
	 */
	public void setStSnapshotList(List<StTaskListEntity> stSnapshotList) {
		this.stSnapshotList = stSnapshotList;
	}

	/**
	 * 获取 清仓对比结果列表.
	 *
	 * @return the 清仓对比结果列表
	 */
	public List<StResultListEntity> getStResultList() {
		return stResultList;
	}

	/**
	 * 设置 清仓对比结果列表.
	 *
	 * @param stResultList the new 清仓对比结果列表
	 */
	public void setStResultList(List<StResultListEntity> stResultList) {
		this.stResultList = stResultList;
	}

	public String getCurrentDeptCode() {
		return currentDeptCode;
	}

	public void setCurrentDeptCode(String currentDeptCode) {
		this.currentDeptCode = currentDeptCode;
	}

	public String getTransferCenter() {
		return transferCenter;
	}

	public void setTransferCenter(String transferCenter) {
		this.transferCenter = transferCenter;
	}

	public boolean isExistTaskInProcess() {
		return existTaskInProcess;
	}

	public void setExistTaskInProcess(boolean existTaskInProcess) {
		this.existTaskInProcess = existTaskInProcess;
	}

	public String getPassTwoHours() {
		return passTwoHours;
	}

	public void setPassTwoHours(String passTwoHours) {
		this.passTwoHours = passTwoHours;
	}
	
}