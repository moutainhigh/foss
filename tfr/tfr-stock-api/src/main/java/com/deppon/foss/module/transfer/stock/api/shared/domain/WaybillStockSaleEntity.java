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
 *  PROJECT NAME  : tfr-stock-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stock/api/shared/domain/WaybillStockEntity.java
 *  
 *  FILE NAME          :WaybillStockEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stock.api.shared.domain;

import java.util.Date;
import com.deppon.foss.framework.entity.BaseEntity;
/**
* @description 营业部交接库存实体类
* @version 1.0
* @author 360903  linhua.yan-tfr
* @update 2016年9月18日
*/
public class WaybillStockSaleEntity extends BaseEntity {
	
	private static final long serialVersionUID = 7662289535773736821L;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 库存件数
	 */
	private Integer stockGoodsCount;
	/**
	 * 入库时间
	 */
	private Date inStockTime;
	/**
	 * 货区编号
	 */
	private String goodsAreaCode;
	/**
	 * 部门编号
	 */
	private String orgCode;
	/**
	 * 该票最后一件货物入库时间
	 */
	private Date lastInStockTime;
	/**
	 * 计划出发时间
	 */
	private Date planStartTime;
	/**
	 * 下一部门编号
	 */
	private String nextOrgCode;
	/**
	* 库位件数
	*/
	private Integer positionCount;
	/**
	 * 是否出库
	 */
	private String isOut;
	
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public Integer getStockGoodsCount() {
		return stockGoodsCount;
	}
	public void setStockGoodsCount(Integer stockGoodsCount) {
		this.stockGoodsCount = stockGoodsCount;
	}
	public Date getInStockTime() {
		return inStockTime;
	}
	public void setInStockTime(Date inStockTime) {
		this.inStockTime = inStockTime;
	}
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public Date getLastInStockTime() {
		return lastInStockTime;
	}
	public void setLastInStockTime(Date lastInStockTime) {
		this.lastInStockTime = lastInStockTime;
	}
	public Date getPlanStartTime() {
		return planStartTime;
	}
	public void setPlanStartTime(Date planStartTime) {
		this.planStartTime = planStartTime;
	}
	public String getNextOrgCode() {
		return nextOrgCode;
	}
	public void setNextOrgCode(String nextOrgCode) {
		this.nextOrgCode = nextOrgCode;
	}
	public Integer getPositionCount() {
		return positionCount;
	}
	public void setPositionCount(Integer positionCount) {
		this.positionCount = positionCount;
	}
	public String getIsOut() {
		return isOut;
	}
	public void setIsOut(String isOut) {
		this.isOut = isOut;
	}
}