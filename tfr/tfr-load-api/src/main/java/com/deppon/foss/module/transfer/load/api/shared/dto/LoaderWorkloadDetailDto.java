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
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/LoaderWorkloadDetailDto.java
 *  
 *  FILE NAME          :LoaderWorkloadDetailDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

/**
 * 装卸车工作量
 * @author ibm-zhangyixin
 * @date 2012-12-21 上午11:38:07
 */
public class LoaderWorkloadDetailDto implements Serializable {
	
	private static final long serialVersionUID = 1485754062732094038L;
	
	/**ID**/
	private String id;

	/**车牌号**/
	private String vehicleNo;

	/**任务号**/
	private String taskNo;

	/**操作类型**/
	private String handleType;

	/**任务类型**/
	private String taskType;

	/**任务开始日期**/
	private String taskBeginDate;//yyyy-mm-dd

	/**开始时间  查询时用到, 非数据库中字段**/
	private String beginDate;	//beginDate = taskBeginDate + ' 00:00:00'

	/**结束时间  查询时用到, 非数据库中字段**/
	private String endDate;		//endDate = taskEndDate + ' 23:59:59';
	
	/**体积**/
	private BigDecimal totVolume;

	/**重量**/
	private BigDecimal totWeight;

	/**票数**/
	private Integer totWaybillQty;

	/**件数**/
	private Integer totGoodsQty;

	/**任务结束时间**/
	private String taskEndTime;
	
	/**建立任务部门**/
	private String orgCode;
	
	/**货物类型(A,B货)**/
    private String goodsType;
	/**
	 * 获取 iD*.
	 *
	 * @return the iD*
	 */
	public String getId() {
		return id;
	}
	
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * 设置 iD*.
	 *
	 * @param id the new iD*
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 获取 车牌号*.
	 *
	 * @return the 车牌号*
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	
	/**
	 * 设置 车牌号*.
	 *
	 * @param vehicleNo the new 车牌号*
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	
	/**
	 * 获取 任务号*.
	 *
	 * @return the 任务号*
	 */
	public String getTaskNo() {
		return taskNo;
	}
	
	/**
	 * 设置 任务号*.
	 *
	 * @param taskNo the new 任务号*
	 */
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	
	/**
	 * 获取 操作类型*.
	 *
	 * @return the 操作类型*
	 */
	public String getHandleType() {
		return handleType;
	}
	
	/**
	 * 设置 操作类型*.
	 *
	 * @param handleType the new 操作类型*
	 */
	public void setHandleType(String handleType) {
		this.handleType = handleType;
	}
	
	/**
	 * 获取 任务类型*.
	 *
	 * @return the 任务类型*
	 */
	public String getTaskType() {
		return taskType;
	}
	
	/**
	 * 设置 任务类型*.
	 *
	 * @param taskType the new 任务类型*
	 */
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	
	/**
	 * 获取 任务开始日期*.
	 *
	 * @return the 任务开始日期*
	 */
	public String getTaskBeginDate() {
		return taskBeginDate;
	}
	
	/**
	 * 设置 任务开始日期*.
	 *
	 * @param taskBeginDate the new 任务开始日期*
	 */
	public void setTaskBeginDate(String taskBeginDate) {
		this.taskBeginDate = taskBeginDate;
	}
	
	/**
	 * 获取 重量*.
	 *
	 * @return the 重量*
	 */
	public BigDecimal getTotWeight() {
		return totWeight;
	}
	
	/**
	 * 设置 重量*.
	 *
	 * @param totWeight the new 重量*
	 */
	public void setTotWeight(BigDecimal totWeight) {
		this.totWeight = totWeight;
	}
	
	/**
	 * 获取 票数*.
	 *
	 * @return the 票数*
	 */
	public Integer getTotWaybillQty() {
		return totWaybillQty;
	}
	
	/**
	 * 设置 票数*.
	 *
	 * @param totWaybillQty the new 票数*
	 */
	public void setTotWaybillQty(Integer totWaybillQty) {
		this.totWaybillQty = totWaybillQty;
	}
	
	/**
	 * 获取 件数*.
	 *
	 * @return the 件数*
	 */
	public Integer getTotGoodsQty() {
		return totGoodsQty;
	}
	
	/**
	 * 设置 件数*.
	 *
	 * @param totGoodsQty the new 件数*
	 */
	public void setTotGoodsQty(Integer totGoodsQty) {
		this.totGoodsQty = totGoodsQty;
	}
	
	/**
	 * 获取 开始时间  查询时用到, 非数据库中字段*.
	 *
	 * @return the 开始时间  查询时用到, 非数据库中字段*
	 */
	public String getBeginDate() {
		if(StringUtils.isNotEmpty(taskBeginDate)) {
			return this.taskBeginDate + " 00:00:00";
		}
		return null;
	}
	
	/**
	 * 设置 开始时间  查询时用到, 非数据库中字段*.
	 *
	 * @param beginDate the new 开始时间  查询时用到, 非数据库中字段*
	 */
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	
	/**
	 * 获取 结束时间  查询时用到, 非数据库中字段*.
	 *
	 * @return the 结束时间  查询时用到, 非数据库中字段*
	 */
	public String getEndDate() {
		if(StringUtils.isNotEmpty(taskBeginDate)) {
			return this.taskBeginDate + " 23:59:59";
		}
		return null;
	}
	
	/**
	 * 设置 结束时间  查询时用到, 非数据库中字段*.
	 *
	 * @param endDate the new 结束时间  查询时用到, 非数据库中字段*
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * 获取 体积*.
	 *
	 * @return the 体积*
	 */
	public BigDecimal getTotVolume() {
		return totVolume;
	}
	
	/**
	 * 设置 体积*.
	 *
	 * @param totVolume the new 体积*
	 */
	public void setTotVolume(BigDecimal totVolume) {
		this.totVolume = totVolume;
	}
	
	/**
	 * 获取 任务结束时间*.
	 *
	 * @return the 任务结束时间*
	 */
	public String getTaskEndTime() {
		return taskEndTime;
	}
	
	/**
	 * 设置 任务结束时间*.
	 *
	 * @param taskEndTime the new 任务结束时间*
	 */
	public void setTaskEndTime(String taskEndTime) {
		this.taskEndTime = taskEndTime;
	}

	/**   
	 * goodsType   
	 *   
	 * @return  the goodsType   
	 */
	
	public String getGoodsType() {
		return goodsType;
	}

	/**   
	 * @param goodsType the goodsType to set
	 * Date:2013-7-10下午2:20:38
	 */
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
}