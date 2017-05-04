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
 *  PROJECT NAME  : tfr-pda-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/shared/domain/QueryPDAUnpackResEntity.java
 *  
 *  FILE NAME          :QueryPDAUnpackResEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/**
 * 
 */
package com.deppon.foss.module.transfer.pda.api.shared.domain;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.module.transfer.pda.api.shared.dto.SerialNoAreaDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.SerialNoStatusDto;

/**
 * pda查询营业部代打包装的返回结果集实体
 * @author 046130-foss-xuduowei
 * @date 2012-10-12 下午6:19:47
 */
public class QueryPDAUnpackResEntity extends BaseEntity {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -896560834759964127L;
	/**
	 * 运单号
	 */
	private String wayBillNumber;
	/**
	 * 开单件数
	 */
	private String waybillNum;
	/**
	 * 代包装货区入库件数
	 */
	private String packStockNum;
	/**
	 * 货物状态
	 */
	private String goodsStatus;
	/**
	 * 运输性质
	 */
	private String transportationType;
	/**
	 * 开单部门
	 */
	private String waybillCreateDept;
	/**
	 * 预计发车时间
	 */
	private Date predictDepartDate;
	/**
	 * 代包装要求
	 */
	private String packRequire;
	/**
	 * 流水号状态
	 */
	private List<SerialNoStatusDto> serialNoStatusDto;
	/**
	 * 流水号状态
	 */
	private List<SerialNoAreaDto> serialNoAreaDto;
	
	
	/**
	 * 获取 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWayBillNumber() {
		return wayBillNumber;
	}
	
	/**
	 * 设置 运单号.
	 *
	 * @param wayBillNumber the new 运单号
	 */
	public void setWayBillNumber(String wayBillNumber) {
		this.wayBillNumber = wayBillNumber;
	}
	
	/**
	 * 获取 开单件数.
	 *
	 * @return the 开单件数
	 */
	public String getWaybillNum() {
		return waybillNum;
	}
	
	/**
	 * 设置 开单件数.
	 *
	 * @param waybillNum the new 开单件数
	 */
	public void setWaybillNum(String waybillNum) {
		this.waybillNum = waybillNum;
	}

	/**
	 * 获取 代包装货区入库件数.
	 *
	 * @return the 代包装货区入库件数
	 */
	public String getPackStockNum() {
		return packStockNum;
	}
	
	/**
	 * 设置 代包装货区入库件数.
	 *
	 * @param packStockNum the new 代包装货区入库件数
	 */
	public void setPackStockNum(String packStockNum) {
		this.packStockNum = packStockNum;
	}

	public List<SerialNoStatusDto> getSerialNoStatusDto() {
		return serialNoStatusDto;
	}

	public void setSerialNoStatusDto(List<SerialNoStatusDto> serialNoStatusDto) {
		this.serialNoStatusDto = serialNoStatusDto;
	}

	/**
	 * 获取 货物状态.
	 *
	 * @return the 货物状态
	 */
	public String getGoodsStatus() {
		return goodsStatus;
	}
	
	/**
	 * 设置 货物状态.
	 *
	 * @param goodsStatus the new 货物状态
	 */
	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}
	
	/**
	 * 获取 运输性质.
	 *
	 * @return the 运输性质
	 */
	public String getTransportationType() {
		return transportationType;
	}
	
	/**
	 * 设置 运输性质.
	 *
	 * @param transportationType the new 运输性质
	 */
	public void setTransportationType(String transportationType) {
		this.transportationType = transportationType;
	}

	/**
	 * 获取 开单部门.
	 *
	 * @return the 开单部门
	 */
	public String getWaybillCreateDept() {
		return waybillCreateDept;
	}
	
	/**
	 * 设置 开单部门.
	 *
	 * @param waybillCreateDept the new 开单部门
	 */
	public void setWaybillCreateDept(String waybillCreateDept) {
		this.waybillCreateDept = waybillCreateDept;
	}


	/**
	 * 获取 预计发车时间.
	 *
	 * @return the 预计发车时间
	 */
	public Date getPredictDepartDate() {
		return predictDepartDate;
	}
	
	/**
	 * 设置 预计发车时间.
	 *
	 * @param predictDepartDate the new 预计发车时间
	 */
	public void setPredictDepartDate(Date predictDepartDate) {
		this.predictDepartDate = predictDepartDate;
	}
	
	/**
	 * 获取 代包装要求.
	 *
	 * @return the 代包装要求
	 */
	public String getPackRequire() {
		return packRequire;
	}
	
	/**
	 * 设置 代包装要求.
	 *
	 * @param packRequire the new 代包装要求
	 */
	public void setPackRequire(String packRequire) {
		this.packRequire = packRequire;
	}

	public List<SerialNoAreaDto> getSerialNoAreaDto() {
		return serialNoAreaDto;
	}

	public void setSerialNoAreaDto(List<SerialNoAreaDto> serialNoAreaDto) {
		this.serialNoAreaDto = serialNoAreaDto;
	}
	
	
}