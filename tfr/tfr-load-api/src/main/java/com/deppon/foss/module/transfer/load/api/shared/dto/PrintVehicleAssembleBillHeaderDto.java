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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/PrintVehicleAssembleBillHeaderDto.java
 *  
 *  FILE NAME          :PrintVehicleAssembleBillHeaderDto.java
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
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 打印配载单HeaderDto
 * @author ibm-zhangyixin
 * @date 2012-12-25 下午6:44:02
 */
public class PrintVehicleAssembleBillHeaderDto extends BaseEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8608100876659545929L;
	
	/**ID**/
	private String id;

	/**车次号**/
	private String vehicleAssembleNo;	

	/**车牌号**/
	private String vehicleNo;			

	/**驾驶员**/
	private String driverName;			

	/**交接单号, eg:123456,123456,123456**/
	private String handOverBillNos;		
	
	/**TO**/
	private String destOrgName;			

	/**FROM**/
	private String origOrgName;			

	/**配载类型**/
	private String assembleType;		

	/**制单人**/
	private String createUserName;		

	/**制单时间**/
	private Date createTime;			

	/**装车人**/
	private String loaderName;			

	/**总件数**/
	private BigDecimal goodsQtyTotal;	

	/**重量**/
	private BigDecimal weightTotal;		

	/**体积**/
	private BigDecimal volumeTotal;		
	
	/**
	 * 获取 车次号*.
	 *
	 * @return the 车次号*
	 */
	public String getVehicleAssembleNo() {
		return vehicleAssembleNo;
	}
	
	/**
	 * 设置 车次号*.
	 *
	 * @param vehicleAssembleNo the new 车次号*
	 */
	public void setVehicleAssembleNo(String vehicleAssembleNo) {
		this.vehicleAssembleNo = vehicleAssembleNo;
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
	 * 获取 驾驶员*.
	 *
	 * @return the 驾驶员*
	 */
	public String getDriverName() {
		return driverName;
	}
	
	/**
	 * 设置 驾驶员*.
	 *
	 * @param driverName the new 驾驶员*
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	
	/**
	 * 获取 交接单号, eg:123456,123456,123456*.
	 *
	 * @return the 交接单号, eg:123456,123456,123456*
	 */
	public String getHandOverBillNos() {
		return handOverBillNos;
	}
	
	/**
	 * 设置 交接单号, eg:123456,123456,123456*.
	 *
	 * @param handOverBillNos the new 交接单号, eg:123456,123456,123456*
	 */
	public void setHandOverBillNos(String handOverBillNos) {
		this.handOverBillNos = handOverBillNos;
	}
	
	/**
	 * 获取 tO*.
	 *
	 * @return the tO*
	 */
	public String getDestOrgName() {
		return destOrgName;
	}
	
	/**
	 * 设置 tO*.
	 *
	 * @param destOrgName the new tO*
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}
	
	/**
	 * 获取 fROM*.
	 *
	 * @return the fROM*
	 */
	public String getOrigOrgName() {
		return origOrgName;
	}
	
	/**
	 * 设置 fROM*.
	 *
	 * @param origOrgName the new fROM*
	 */
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}
	
	/**
	 * 获取 配载类型*.
	 *
	 * @return the 配载类型*
	 */
	public String getAssembleType() {
		return assembleType;
	}
	
	/**
	 * 设置 配载类型*.
	 *
	 * @param assembleType the new 配载类型*
	 */
	public void setAssembleType(String assembleType) {
		this.assembleType = assembleType;
	}
	
	/**
	 * 获取 制单人*.
	 *
	 * @return the 制单人*
	 */
	public String getCreateUserName() {
		return createUserName;
	}
	
	/**
	 * 设置 制单人*.
	 *
	 * @param createUserName the new 制单人*
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
	/**
	 * 获取 制单时间*.
	 *
	 * @return the 制单时间*
	 */
	public Date getCreateTime() {
		return createTime;
	}
	
	/**
	 * 设置 制单时间*.
	 *
	 * @param createTime the new 制单时间*
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * 获取 装车人*.
	 *
	 * @return the 装车人*
	 */
	public String getLoaderName() {
		return loaderName;
	}
	
	/**
	 * 设置 装车人*.
	 *
	 * @param loaderName the new 装车人*
	 */
	public void setLoaderName(String loaderName) {
		this.loaderName = loaderName;
	}
	
	/**
	 * 获取 总件数*.
	 *
	 * @return the 总件数*
	 */
	public BigDecimal getGoodsQtyTotal() {
		return goodsQtyTotal;
	}
	
	/**
	 * 设置 总件数*.
	 *
	 * @param goodsQtyTotal the new 总件数*
	 */
	public void setGoodsQtyTotal(BigDecimal goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}
	
	/**
	 * 获取 重量*.
	 *
	 * @return the 重量*
	 */
	public BigDecimal getWeightTotal() {
		return weightTotal;
	}
	
	/**
	 * 设置 重量*.
	 *
	 * @param weightTotal the new 重量*
	 */
	public void setWeightTotal(BigDecimal weightTotal) {
		this.weightTotal = weightTotal;
	}
	
	/**
	 * 获取 体积*.
	 *
	 * @return the 体积*
	 */
	public BigDecimal getVolumeTotal() {
		return volumeTotal;
	}
	
	/**
	 * 设置 体积*.
	 *
	 * @param volumeTotal the new 体积*
	 */
	public void setVolumeTotal(BigDecimal volumeTotal) {
		this.volumeTotal = volumeTotal;
	}
	
	/** 
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 下午6:46:12
	 * @see com.deppon.foss.framework.entity.BaseEntity#getId()
	 */
	public String getId() {
		return id;
	}
	
	/** 
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 下午6:46:12
	 * @see com.deppon.foss.framework.entity.BaseEntity#setId(java.lang.String)
	 */
	public void setId(String id) {
		this.id = id;
	}
}