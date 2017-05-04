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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/domain/AdjustEntity.java
 * 
 *  FILE NAME     :AdjustEntity.java
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
/**
 * Entity开发规范
 * 1.必须继承com.deppon.foss.framework.entity.BaseEntity
 * 2.类名必须以Entity结尾
 * 3.必须生成serialVersionUID
 * 4.建议属性名称与数据库字段命名规则一致
 */
package com.deppon.foss.module.transfer.scheduling.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 修改货物走货路径entity
 */
public class AdjustEntity extends BaseEntity{
	
	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 5175681817586795521L;
	/**
	 * 货区
	 */
	private String goodsAreaCode;
	
	private String goodsAreaName;
	/**
	 * 部门
	 */
	private String orgCode;
	
	private String orgName;
	/**
	 * 总重量
	 */
	private BigDecimal areaWeightTotal;
	/**
	 * 总体积
	 */
	private BigDecimal areaVolumeTotal;
	/**
	 * 货区线路
	 */
	private String areaLine;
	
	private String areaLineName;
    /**
     * 货区票数
     */
    private Integer areaWaybillQty;
	/**
	 * 运单号
	 */
    private String waybillNo;
    /**
     * 货件数
     */
    private Integer goodsQtyTotal;
    /**
     * 库区件数
     */
    private Integer stockGoodsQTY;
    /**
     * 运单重量
     */
    private BigDecimal goodsWeightTotal;
    /**
     * 运单体积
     */
    private BigDecimal goodsVolumeTotal;
    /**
     * 改变前线路
     */
    private String beforeLine;
    /**
     * 改变后线路
     */
    private String afterLine;
    /**
     * 货件号
     */
    private String serialNo;
    /**
     * 是否可以合车调整
     */
    private int ifDisable;
   
    /**
     * 获取 货件号.
     *
     * @return the 货件号
     */
    public String getSerialNo() {
		return serialNo;
	}
    
    
    /**
    * 运单的运输性质
    * @fields productCode
    * @author 14022-foss-songjie 
    * @update 2014年2月14日 下午2:12:06
    * @version V1.0
    */
    public String productCode;
    
    
    /**
    * 快递的下一部门code
    * @fields packageNextOrgCode
    * @author 14022-foss-songjie 
    * @update 2014年2月19日 上午8:53:29
    * @version V1.0
    */
    private String packageNextOrgCode;
    
    
    /**
    * 快递货区的code 
    * @fields packageGoodsAreaCode
    * @author 14022-foss-songjie 
    * @update 2014年2月19日 上午9:05:04
    * @version V1.0
    */
    private String packageGoodsAreaCode;
	
	/**
	 * 设置 货件号.
	 *
	 * @param serialNo the new 货件号
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
	/**
	 * 获取 货区.
	 *
	 * @return the 货区
	 */
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	public String getGoodsAreaName() {
		return goodsAreaName;
	}

	/**
	 * 
	 *
	 * @param goodsAreaName 
	 */
	public void setGoodsAreaName(String goodsAreaName) {
		this.goodsAreaName = goodsAreaName;
	}

	/**
	 * 设置 货区.
	 *
	 * @param goodsAreaCode the new 货区
	 */
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}
	
	/**
	 * 获取 部门.
	 *
	 * @return the 部门
	 */
	public String getOrgCode() {
		return orgCode;
	}
	
	/**
	 * 设置 部门.
	 *
	 * @param orgCode the new 部门
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	/**
	 * 获取 总重量.
	 *
	 * @return the 总重量
	 */
	public BigDecimal getAreaWeightTotal() {
		return areaWeightTotal;
	}
	
	/**
	 * 设置 总重量.
	 *
	 * @param areaWeightTotal the new 总重量
	 */
	public void setAreaWeightTotal(BigDecimal areaWeightTotal) {
		this.areaWeightTotal = areaWeightTotal;
	}
	
	/**
	 * 获取 总体积.
	 *
	 * @return the 总体积
	 */
	public BigDecimal getAreaVolumeTotal() {
		return areaVolumeTotal;
	}
	
	/**
	 * 设置 总体积.
	 *
	 * @param areaVolumeTotal the new 总体积
	 */
	public void setAreaVolumeTotal(BigDecimal areaVolumeTotal) {
		this.areaVolumeTotal = areaVolumeTotal;
	}
	
	/**
	 * 获取 货区线路.
	 *
	 * @return the 货区线路
	 */
	public String getAreaLine() {
		return areaLine;
	}
	
	/**
	 * 设置 货区线路.
	 *
	 * @param areaLine the new 货区线路
	 */
	public void setAreaLine(String areaLine) {
		this.areaLine = areaLine;
	}
	
	/**
	 * 获取 货区票数.
	 *
	 * @return the 货区票数
	 */
	public Integer getAreaWaybillQty() {
		return areaWaybillQty;
	}
	
	/**
	 * 设置 货区票数.
	 *
	 * @param areaWaybillQty the new 货区票数
	 */
	public void setAreaWaybillQty(Integer areaWaybillQty) {
		this.areaWaybillQty = areaWaybillQty;
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
	 * 获取 货件数.
	 *
	 * @return the 货件数
	 */
	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}
	
	/**
	 * 设置 货件数.
	 *
	 * @param goodsQtyTotal the new 货件数
	 */
	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}
	
	/**
	 * 获取 库区件数.
	 *
	 * @return the 库区件数
	 */
	public Integer getStockGoodsQTY() {
		return stockGoodsQTY;
	}
	
	/**
	 * 设置 库区件数.
	 *
	 * @param stockGoodsQTY the new 库区件数
	 */
	public void setStockGoodsQTY(Integer stockGoodsQTY) {
		this.stockGoodsQTY = stockGoodsQTY;
	}
	
	/**
	 * 获取 运单重量.
	 *
	 * @return the 运单重量
	 */
	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}
	
	/**
	 * 设置 运单重量.
	 *
	 * @param goodsWeightTotal the new 运单重量
	 */
	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}
	
	/**
	 * 获取 运单体积.
	 *
	 * @return the 运单体积
	 */
	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}
	
	/**
	 * 设置 运单体积.
	 *
	 * @param goodsVolumeTotal the new 运单体积
	 */
	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}
	
	/**
	 * 获取 改变前线路.
	 *
	 * @return the 改变前线路
	 */
	public String getBeforeLine() {
		return beforeLine;
	}
	
	/**
	 * 设置 改变前线路.
	 *
	 * @param beforeLine the new 改变前线路
	 */
	public void setBeforeLine(String beforeLine) {
		this.beforeLine = beforeLine;
	}
	
	/**
	 * 获取 改变后线路.
	 *
	 * @return the 改变后线路
	 */
	public String getAfterLine() {
		return afterLine;
	}
	
	/**
	 * 设置 改变后线路.
	 *
	 * @param afterLine the new 改变后线路
	 */
	public void setAfterLine(String afterLine) {
		this.afterLine = afterLine;
	}
	
	/**
	 * 获取 是否可以合车调整.
	 *
	 * @return the 是否可以合车调整
	 */
	public int getIfDisable() {
		return ifDisable;
	}
	
	/**
	 * 设置 是否可以合车调整.
	 *
	 * @param ifDisable the new 是否可以合车调整
	 */
	public void setIfDisable(int ifDisable) {
		this.ifDisable = ifDisable;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * 
	 *
	 * @param orgName 
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getAreaLineName() {
		return areaLineName;
	}

	/**
	 * 
	 *
	 * @param areaLineName 
	 */
	public void setAreaLineName(String areaLineName) {
		this.areaLineName = areaLineName;
	}

	/**
	 * 版本号
	 *
	 * @return 
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	/**
	* @description 获取 运单的运输性质
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年2月14日 下午2:12:53
	*/
	public String getProductCode() {
		return productCode;
	}

	
	/**
	* @description 设置运单的运输性质
	* @param productCode
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年2月14日 下午2:12:56
	*/
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	
	/**
	* @description 获取  快递的下一部门code
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年2月19日 上午8:54:11
	*/
	public String getPackageNextOrgCode() {
		return packageNextOrgCode;
	}

	
	/**
	* @description 设置 快递的下一部门code
	* @param packageNextOrgCode
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年2月19日 上午8:54:14
	*/
	public void setPackageNextOrgCode(String packageNextOrgCode) {
		this.packageNextOrgCode = packageNextOrgCode;
	}

	
	/**
	* @description 获取  快递货区的code 
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年2月19日 上午9:05:54
	*/
	public String getPackageGoodsAreaCode() {
		return packageGoodsAreaCode;
	}

	
	/**
	* @description 设置 快递货区的code 
	* @param packageGoodsAreaCode
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年2月19日 上午9:06:11
	*/
	public void setPackageGoodsAreaCode(String packageGoodsAreaCode) {
		this.packageGoodsAreaCode = packageGoodsAreaCode;
	}
	
	
    
}