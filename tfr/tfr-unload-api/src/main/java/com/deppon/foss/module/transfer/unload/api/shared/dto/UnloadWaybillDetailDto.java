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
 *  PROJECT NAME  : tfr-unload-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/shared/dto/UnloadWaybillDetailDto.java
 *  
 *  FILE NAME          :UnloadWaybillDetailDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadWaybillDetailEntity;

/**
 * 卸车运单明细Dto
 * @author ibm-zhangyixin
 * @date 2012-12-25 下午7:01:49
 */
public class UnloadWaybillDetailDto extends UnloadWaybillDetailEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -826589178301265347L;
	
	/**交接件数**/
	private Integer handoverTotQty;		

	/**件数扫描率**/
	private BigDecimal scanGoodsQtyRate;

	/**交接重量**/
	private BigDecimal weightTotal;		

	/**交接体积**/
	private BigDecimal volumeTotal;
	
	/**流水号**/
	private String serialNo;
	
	/**操作时间**/
	private Date optTime;
	
	/**
	 * 运单所属单据明细的类型
	 */
	private String billType;
	
	/**
	 * 新增自段用来区分快递或零担,express为快递lingdan为零担
	 */
	private String expressOrLingdan;
	
	/**
	 * 卸车编号
	 */
	private String unloadTaskNo;
	
	/**
	 * 件号
	 */
	private String cargoNo;
	
	/**
	 * 件类型
	 */
	private String cargoType;
	
	/**
	 * 是否有差异
	 */
	private boolean hasDifferences;

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	/**
	 * 获取 交接件数*.
	 *
	 * @return the 交接件数*
	 */
	public Integer getHandoverTotQty() {
		return handoverTotQty;
	}
	
	/**
	 * 设置 交接件数*.
	 *
	 * @param handoverTotQty the new 交接件数*
	 */
	public void setHandoverTotQty(Integer handoverTotQty) {
		this.handoverTotQty = handoverTotQty;
	}
	
	/**
	 * 获取 件数扫描率*.
	 *
	 * @return the 件数扫描率*
	 */
	public BigDecimal getScanGoodsQtyRate() {
		return scanGoodsQtyRate;
	}
	
	/**
	 * 设置 件数扫描率*.
	 *
	 * @param scanGoodsQtyRate the new 件数扫描率*
	 */
	public void setScanGoodsQtyRate(BigDecimal scanGoodsQtyRate) {
		this.scanGoodsQtyRate = scanGoodsQtyRate;
	}
	
	/**
	 * 获取 交接重量*.
	 *
	 * @return the 交接重量*
	 */
	public BigDecimal getWeightTotal() {
		return weightTotal;
	}
	
	/**
	 * 设置 交接重量*.
	 *
	 * @param weightTotal the new 交接重量*
	 */
	public void setWeightTotal(BigDecimal weightTotal) {
		this.weightTotal = weightTotal;
	}
	
	/**
	 * 获取 交接体积*.
	 *
	 * @return the 交接体积*
	 */
	public BigDecimal getVolumeTotal() {
		return volumeTotal;
	}
	
	/**
	 * 设置 交接体积*.
	 *
	 * @param volumeTotal the new 交接体积*
	 */
	public void setVolumeTotal(BigDecimal volumeTotal) {
		this.volumeTotal = volumeTotal;
	}

	/**   
	 * serialNo   
	 *   
	 * @return  the serialNo   
	 */
	
	public String getSerialNo() {
		return serialNo;
	}

	/**   
	 * @param serialNo the serialNo to set
	 * Date:2013-6-24下午7:36:17
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	/**   
	 * optTime   
	 *   
	 * @return  the optTime   
	 */
	
	public Date getOptTime() {
		return optTime;
	}

	/**   
	 * @param optTime the optTime to set
	 * Date:2013-6-24下午7:36:17
	 */
	public void setOptTime(Date optTime) {
		this.optTime = optTime;
	}

	/**
	* @description 获取  快递或零担标识
	* @return
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年5月4日 下午12:46:36
	 */
	public String getExpressOrLingdan() {
		return expressOrLingdan;
	}
    /**
    * @description 设置  快递或零担标识
    * @param expressOrLingdan
    * @version 1.0
    * @author 328768-foss-gaojianfu
    * @update 2016年5月4日 下午12:47:36
     */
	public void setExpressOrLingdan(String expressOrLingdan) {
		this.expressOrLingdan = expressOrLingdan;
	}

	public String getUnloadTaskNo() {
		return unloadTaskNo;
	}

	public void setUnloadTaskNo(String unloadTaskNo) {
		this.unloadTaskNo = unloadTaskNo;
	}

	public String getCargoNo() {
		return cargoNo;
	}

	public void setCargoNo(String cargoNo) {
		this.cargoNo = cargoNo;
	}

	public String getCargoType() {
		return cargoType;
	}

	public void setCargoType(String cargoType) {
		this.cargoType = cargoType;
	}

	public boolean isHasDifferences() {
		return hasDifferences;
	}

	public void setHasDifferences(boolean hasDifferences) {
		this.hasDifferences = hasDifferences;
	}
	
	
}