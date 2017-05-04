/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/ArrangeArriveSheetDto.java
 * 
 * FILE NAME        	: ArrangeArriveSheetDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;

/**
 * 排单时打印到达联返回DTO
 * @author 097972-foss-dengtingting
 * @date 2012-12-8 下午6:48:06
 */
public class ArrangeArriveSheetDto implements Serializable {

	private static final long serialVersionUID = 1L;
	// 排单件数
	private Integer arrangeGoodsQty;
	// 到达联编号
	private String arriveSheetNo;
	// 原到达联件数
	private Integer oldArriveSheetGoodsQty;
	// 新生成到达联件数
	private Integer newArriveSheetGoodsQty;
	// 运单编号
	private String waybillNo;

	/**
	 * @return the arrangeGoodsQty
	 */
	public Integer getArrangeGoodsQty() {
		return arrangeGoodsQty;
	}

	/**
	 * @param arrangeGoodsQty the arrangeGoodsQty to see
	 */
	public void setArrangeGoodsQty(Integer arrangeGoodsQty) {
		this.arrangeGoodsQty = arrangeGoodsQty;
	}

	/**
	 * @return the arriveSheetNo
	 */
	public String getArriveSheetNo() {
		return arriveSheetNo;
	}

	/**
	 * @param arriveSheetNo the arriveSheetNo to see
	 */
	public void setArriveSheetNo(String arriveSheetNo) {
		this.arriveSheetNo = arriveSheetNo;
	}

	/**
	 * @return the oldArriveSheetGoodsQty
	 */
	public Integer getOldArriveSheetGoodsQty() {
		return oldArriveSheetGoodsQty;
	}

	/**
	 * @param oldArriveSheetGoodsQty the oldArriveSheetGoodsQty to see
	 */
	public void setOldArriveSheetGoodsQty(Integer oldArriveSheetGoodsQty) {
		this.oldArriveSheetGoodsQty = oldArriveSheetGoodsQty;
	}

	/**
	 * @return the newArriveSheetGoodsQty
	 */
	public Integer getNewArriveSheetGoodsQty() {
		return newArriveSheetGoodsQty;
	}

	/**
	 * @param newArriveSheetGoodsQty the newArriveSheetGoodsQty to see
	 */
	public void setNewArriveSheetGoodsQty(Integer newArriveSheetGoodsQty) {
		this.newArriveSheetGoodsQty = newArriveSheetGoodsQty;
	}

	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo the waybillNo to see
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

}