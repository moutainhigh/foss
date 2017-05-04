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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/api/shared/dto/StTaskExportDto.java
 *  
 *  FILE NAME          :StTaskExportDto.java
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

import java.util.Date;

/**
 * 清仓任务导出业务实体
 * @author foss-wuyingjie
 * @date 2012-11-15 下午4:26:38
 */
public class StTaskExportDto {
	/**清仓任务编号*/
	private String taskNo;				
	/**库区名称*/
    private String goodsareaname;		
    /**清仓人*/
    private String operators;			
    /**清仓任务创建时间*/
    private Date createtime;			
    /**运单号*/
    private String waybillNo;			
    /**流水号*/
    private String serialNo;			
    /**货名*/
    private String goodsName;			
    /**包装*/
    private String goodsPackage;		
    /**重量*/
    private Double goodsWeight;			
    /**体积*/
    private Double goodsVolume;			
    /**入库时间*/
    private Date stockTime;				
    /**总票数*/
    private Integer totalWaybillNo;		
    /**总件数*/
    private Integer totalGoodsQty;		
    /**总重量*/
    private Double totalGoodsWeight;	
    /**总体积*/
    private Double totalGoodsVolume;
    /**开单件数*/
    private Integer goodsQty;
    /**运输性质*/
    private String transProperty;
    
    /**
     * 获取运输性质
     * @return String
     */
	public String getTransProperty() {
		return transProperty;
	}
	
	/**
	 * 设置运输性质
	 * @param transProperty
	 */
	public void setTransProperty(String transProperty) {
		this.transProperty = transProperty;
	}
	
	/**
	 * 获取开单件数
	 * @return Integer
	 */
	public Integer getGoodsQty() {
		return goodsQty;
	}
	
	/**
	 * 设置开单件数
	 * @param goodsQty
	 */
	public void setGoodsQty(Integer goodsQty) {
		this.goodsQty = goodsQty;
	}

	/**
	 * 获取 清仓任务编号.
	 *
	 * @return the 清仓任务编号
	 */
	public String getTaskNo() {
		return taskNo;
	}
	
	/**
	 * 设置 清仓任务编号.
	 *
	 * @param taskNo the new 清仓任务编号
	 */
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	
	/**
	 * 获取 库区名称.
	 *
	 * @return the 库区名称
	 */
	public String getGoodsareaname() {
		return goodsareaname;
	}
	
	/**
	 * 设置 库区名称.
	 *
	 * @param goodsareaname the new 库区名称
	 */
	public void setGoodsareaname(String goodsareaname) {
		this.goodsareaname = goodsareaname;
	}
	
	/**
	 * 获取 清仓人.
	 *
	 * @return the 清仓人
	 */
	public String getOperators() {
		return operators;
	}
	
	/**
	 * 设置 清仓人.
	 *
	 * @param operators the new 清仓人
	 */
	public void setOperators(String operators) {
		this.operators = operators;
	}
	
	/**
	 * 获取 清仓任务创建时间.
	 *
	 * @return the 清仓任务创建时间
	 */
	public Date getCreatetime() {
		return createtime;
	}
	
	/**
	 * 设置 清仓任务创建时间.
	 *
	 * @param createtime the new 清仓任务创建时间
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
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
	 * 获取 货名.
	 *
	 * @return the 货名
	 */
	public String getGoodsName() {
		return goodsName;
	}
	
	/**
	 * 设置 货名.
	 *
	 * @param goodsName the new 货名
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	/**
	 * 获取 包装.
	 *
	 * @return the 包装
	 */
	public String getGoodsPackage() {
		return goodsPackage;
	}
	
	/**
	 * 设置 包装.
	 *
	 * @param goodsPackage the new 包装
	 */
	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}
	
	/**
	 * 获取 重量.
	 *
	 * @return the 重量
	 */
	public Double getGoodsWeight() {
		return goodsWeight;
	}
	
	/**
	 * 设置 重量.
	 *
	 * @param goodsWeight the new 重量
	 */
	public void setGoodsWeight(Double goodsWeight) {
		this.goodsWeight = goodsWeight;
	}
	
	/**
	 * 获取 体积.
	 *
	 * @return the 体积
	 */
	public Double getGoodsVolume() {
		return goodsVolume;
	}
	
	/**
	 * 设置 体积.
	 *
	 * @param goodsVolume the new 体积
	 */
	public void setGoodsVolume(Double goodsVolume) {
		this.goodsVolume = goodsVolume;
	}
	
	/**
	 * 获取 入库时间.
	 *
	 * @return the 入库时间
	 */
	public Date getStockTime() {
		return stockTime;
	}
	
	/**
	 * 设置 入库时间.
	 *
	 * @param stockTime the new 入库时间
	 */
	public void setStockTime(Date stockTime) {
		this.stockTime = stockTime;
	}
	
	/**
	 * 获取 总票数.
	 *
	 * @return the 总票数
	 */
	public Integer getTotalWaybillNo() {
		return totalWaybillNo;
	}
	
	/**
	 * 设置 总票数.
	 *
	 * @param totalWaybillNo the new 总票数
	 */
	public void setTotalWaybillNo(Integer totalWaybillNo) {
		this.totalWaybillNo = totalWaybillNo;
	}
	
	/**
	 * 获取 总件数.
	 *
	 * @return the 总件数
	 */
	public Integer getTotalGoodsQty() {
		return totalGoodsQty;
	}
	
	/**
	 * 设置 总件数.
	 *
	 * @param totalGoodsQty the new 总件数
	 */
	public void setTotalGoodsQty(Integer totalGoodsQty) {
		this.totalGoodsQty = totalGoodsQty;
	}
	
	/**
	 * 获取 总重量.
	 *
	 * @return the 总重量
	 */
	public Double getTotalGoodsWeight() {
		return totalGoodsWeight;
	}
	
	/**
	 * 设置 总重量.
	 *
	 * @param totalGoodsWeight the new 总重量
	 */
	public void setTotalGoodsWeight(Double totalGoodsWeight) {
		this.totalGoodsWeight = totalGoodsWeight;
	}
	
	/**
	 * 获取 总体积.
	 *
	 * @return the 总体积
	 */
	public Double getTotalGoodsVolume() {
		return totalGoodsVolume;
	}
	
	/**
	 * 设置 总体积.
	 *
	 * @param totalGoodsVolume the new 总体积
	 */
	public void setTotalGoodsVolume(Double totalGoodsVolume) {
		this.totalGoodsVolume = totalGoodsVolume;
	}
}