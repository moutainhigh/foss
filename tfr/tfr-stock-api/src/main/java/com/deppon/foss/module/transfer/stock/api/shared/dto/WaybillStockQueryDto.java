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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stock/api/shared/dto/WaybillStockQueryDto.java
 *  
 *  FILE NAME          :WaybillStockQueryDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stock.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;
/**
 * 
 * 组装运单库存表与运单表关联查询结果数据
 * @author 097457-dp-wangqiang
 * @date 2012-10-13 下午2:38:27
 */
public class WaybillStockQueryDto implements java.io.Serializable{

	private static final long serialVersionUID = -6430809207619268L;
	/**
	 * 到达时间
	 */
	private Date arrivalTime;
	
	/**
	 * 行政区域
	 */
	private String administrativeArea;



	/**
	 * 运单号
	 */
	private String waybillNO;
	/**
	 * 库存件数
	 */
	private Integer stockGoodsCount;
	/**
	 * 开单件数
	 */
	private Integer waybillGoodsCount;
	/**
	 * 开单时间
	 */
	private Date createWaybillTime;
	/**
	 * 运输性质
	 */
	private String productCode;
	/**
	 * 运输性质code
	 */
	private String productTypeCode;
	/**
	* 提货方式
	*/
	private String receiveMethod;
	/**
	 * 总重量
	 */
	private BigDecimal weightTotal;
	/**
	 * 总体积
	 */
	private BigDecimal volumeTotal;
	/**
	 * 货物名称
	 */
	private String goodsName;
	/**
	 * 出发部门
	 */
	private String departureCode;  
	/**
	 * 下一部门
	 */
	private String nextOrgCode;
	
	/**
	 * 到达部门
	 */
	private String receiveOrgCode;
	/**
	 * 库存部门
	 */
	private String orgCode;
	/**
	 * 货区编号
	 */
	private String goodsAreaCode;
	/**
	 * 货区名称
	 */
	private String goodsAreaName;
	
	/**
	 * 操作人姓名
	 */
	private String operatorName;
	
	/**
	 * 流水号
	 */
	private String serialNO;
	/**
	 * 包装
	 */
	private String packageType;
	/**
	 * 入库时间
	 */
	private Date inStockTime;
	/**
	 * 在库时长
	 */
	private BigDecimal inStockDuration;
	
	/**
	 * 
	 */
	private Date beginInStockTime;
	
	/**
	 * 
	 */
	private Date endInStockTime;
	
	/**
	 * 收货客户联系人
	 */
	private String receiveCustomerContact;
	
	
	/**
	 * 获取 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWaybillNO() {
		return waybillNO;
	}
	
	/**
	 * 设置 运单号.
	 *
	 * @param waybillNO the new 运单号
	 */
	public void setWaybillNO(String waybillNO) {
		this.waybillNO = waybillNO;
	}
	
	/**
	 * 获取 库存件数.
	 *
	 * @return the 库存件数
	 */
	public Integer getStockGoodsCount() {
		return stockGoodsCount;
	}
	
	/**
	 * 设置 库存件数.
	 *
	 * @param stockGoodsCount the new 库存件数
	 */
	public void setStockGoodsCount(Integer stockGoodsCount) {
		this.stockGoodsCount = stockGoodsCount;
	}
	
	/**
	 * 获取 开单件数.
	 *
	 * @return the 开单件数
	 */
	public Integer getWaybillGoodsCount() {
		return waybillGoodsCount;
	}
	
	/**
	 * 设置 开单件数.
	 *
	 * @param waybillGoodsCount the new 开单件数
	 */
	public void setWaybillGoodsCount(Integer waybillGoodsCount) {
		this.waybillGoodsCount = waybillGoodsCount;
	}
	
	/**
	 * 获取 开单时间.
	 *
	 * @return the 开单时间
	 */
	public Date getCreateWaybillTime() {
		return createWaybillTime;
	}
	
	/**
	 * 设置 开单时间.
	 *
	 * @param createWaybillTime the new 开单时间
	 */
	public void setCreateWaybillTime(Date createWaybillTime) {
		this.createWaybillTime = createWaybillTime;
	}
	
	/**
	 * 获取 运输性质.
	 *
	 * @return the 运输性质
	 */
	public String getProductCode() {
		return productCode;
	}
	
	/**
	 * 设置 运输性质.
	 *
	 * @param productCode the new 运输性质
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	/**
	 * 获取 总重量.
	 *
	 * @return the 总重量
	 */
	public BigDecimal getWeightTotal() {
		return weightTotal;
	}
	
	/**
	 * 设置 总重量.
	 *
	 * @param weightTotal the new 总重量
	 */
	public void setWeightTotal(BigDecimal weightTotal) {
		this.weightTotal = weightTotal;
	}
	
	/**
	 * 获取 总体积.
	 *
	 * @return the 总体积
	 */
	public BigDecimal getVolumeTotal() {
		return volumeTotal;
	}
	
	/**
	 * 设置 总体积.
	 *
	 * @param volumeTotal the new 总体积
	 */
	public void setVolumeTotal(BigDecimal volumeTotal) {
		this.volumeTotal = volumeTotal;
	}
	
	/**
	 * 获取 货物名称.
	 *
	 * @return the 货物名称
	 */
	public String getGoodsName() {
		return goodsName;
	}
	
	/**
	 * 设置 货物名称.
	 *
	 * @param goodsName the new 货物名称
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	/**
	 * 获取 出发部门.
	 *
	 * @return the 出发部门
	 */
	public String getDepartureCode() {
		return departureCode;
	}
	
	/**
	 * 设置 出发部门.
	 *
	 * @param departureCode the new 出发部门
	 */
	public void setDepartureCode(String departureCode) {
		this.departureCode = departureCode;
	}
	/**
	 * 获取下一部门.
	 * @return the 下一部门
	 */
	public String getNextOrgCode() {
		return nextOrgCode;
	}
	/**
	 * 设置下一部门.
	 * @param nextOrgCode the new 下一部门
	 */
	public void setNextOrgCode(String nextOrgCode) {
		this.nextOrgCode = nextOrgCode;
	}

	
	/**
	 * 获取 到达部门.
	 *
	 * @return the 到达部门
	 */
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}
	
	/**
	 * 设置 到达部门.
	 *
	 * @param receiveOrgCode the new 到达部门
	 */
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}
	
	/**
	 * 获取 库存部门.
	 *
	 * @return the 库存部门
	 */
	public String getOrgCode() {
		return orgCode;
	}
	
	/**
	 * 设置 库存部门.
	 *
	 * @param orgCode the new 库存部门
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	/**
	 * 获取 货区编号.
	 *
	 * @return the 货区编号
	 */
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}
	
	/**
	 * 设置 货区编号.
	 *
	 * @param goodsAreaCode the new 货区编号
	 */
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}
	
	/**
	 * 获取 操作人姓名.
	 *
	 * @return the 操作人姓名
	 */
	public String getOperatorName() {
		return operatorName;
	}
	
	/**
	 * 设置 操作人姓名.
	 *
	 * @param operatorName the new 操作人姓名
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	/**
	 * 获取 流水号.
	 *
	 * @return the 流水号
	 */
	public String getSerialNO() {
		return serialNO;
	}
	
	/**
	 * 设置 流水号.
	 *
	 * @param serialNO the new 流水号
	 */
	public void setSerialNO(String serialNO) {
		this.serialNO = serialNO;
	}
	
	/**
	 * 获取 包装.
	 *
	 * @return the 包装
	 */
	public String getPackageType() {
		return packageType;
	}
	
	/**
	 * 设置 包装.
	 *
	 * @param packageType the new 包装
	 */
	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}
	
	/**
	 * 获取 在库时长.
	 *
	 * @return the 在库时长
	 */
	public BigDecimal getInStockDuration() {
		return inStockDuration;
	}
	
	/**
	 * 设置 在库时长.
	 *
	 * @param inStockDuration the new 在库时长
	 */
	public void setInStockDuration(BigDecimal inStockDuration) {
		this.inStockDuration = inStockDuration;
	}
	
	/**
	 * 获取 入库时间.
	 *
	 * @return the 入库时间
	 */
	public Date getInStockTime() {
		return inStockTime;
	}
	
	/**
	 * 设置 入库时间.
	 *
	 * @param inStockTime the new 入库时间
	 */
	public void setInStockTime(Date inStockTime) {
		this.inStockTime = inStockTime;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	@DateFormat
	public Date getBeginInStockTime() {
		return beginInStockTime;
	}
	
	/**
	 * 
	 *
	 * @param beginInStockTime 
	 */
	@DateFormat
	public void setBeginInStockTime(Date beginInStockTime) {
		this.beginInStockTime = beginInStockTime;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	@DateFormat
	public Date getEndInStockTime() {
		return endInStockTime;
	}
	
	/**
	 * 
	 *
	 * @param endInStockTime 
	 */
	@DateFormat
	public void setEndInStockTime(Date endInStockTime) {
		this.endInStockTime = endInStockTime;
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
	 * 获取收货客户联系人
	 * @return
	 */
	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}

	/**
	 * 设置收货客户联系人
	 * @param receiveCustomerContact
	 */
	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}

	/**
	* @return
	* @description 获取提货方式
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-23 上午9:06:00
	*/
	public String getReceiveMethod() {
		return receiveMethod;
	}

	/**
	* @param receiveMethod 设置提货方式
	* @description 
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-23 上午9:06:56
	*/
	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}
	
	/**
	 * 获取运输性质code
	 * @author 200978-foss-xiaobingcheng
	 * 2014-8-12
	 * @return
	 */
	public String getProductTypeCode() {
		return productTypeCode;
	}

	/**
	 * 设置运输性质code
	 * @author 200978-foss-xiaobingcheng
	 * 2014-8-12
	 * @param productTypeCode
	 */
	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
	}
	
	/**
	* @description 获取到达时间
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年6月13日 下午5:17:17
	*/
	public Date getArrivalTime() {
		return arrivalTime;
	}

	
	/**
	* @description 设置到达时间
	* @param createTime
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年6月13日 下午5:17:20
	*/
	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	

	/**
	* @description 获取行政区域
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月22日 下午5:39:19
	*/
	public String getAdministrativeArea() {
		return administrativeArea;
	}

	
	/**
	* @description 设置行政区域
	* @param administrativeArea
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月22日 下午5:39:28
	*/
	public void setAdministrativeArea(String administrativeArea) {
		this.administrativeArea = administrativeArea;
	}
	
}