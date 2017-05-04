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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/QueryHandOverBillNoForUnloadTaskLackGoodsDto.java
 *  
 *  FILE NAME          :QueryHandOverBillNoForUnloadTaskLackGoodsDto.java
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

import java.util.List;

/** 
 * @className: QueryHandOverBillNoForUnloadTaskLackGoodsDto
 * @author: ShiWei shiwei@outlook.com
 * @description: 用于卸车少货时，根据运单号查找交接单号
 * @date: 2013-2-28 下午4:28:43
 * 
 */
public class QueryHandOverBillNoForUnloadTaskLackGoodsDto {
	
	/**
	 * 出发部门code
	 */
	private String departOrgCode;
	
	/**
	 * 到达部门code
	 */
	private String destOrgCode;
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 交接单号List
	 */
	private List<String> unloadedHandOverBillNoList;

	public String getDepartOrgCode() {
		return departOrgCode;
	}

	public void setDepartOrgCode(String departOrgCode) {
		this.departOrgCode = departOrgCode;
	}

	public String getDestOrgCode() {
		return destOrgCode;
	}

	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public List<String> getUnloadedHandOverBillNoList() {
		return unloadedHandOverBillNoList;
	}

	public void setUnloadedHandOverBillNoList(
			List<String> unloadedHandOverBillNoList) {
		this.unloadedHandOverBillNoList = unloadedHandOverBillNoList;
	}

}