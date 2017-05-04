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
 *  PROJECT NAME  : tfr-management-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/shared/vo/OtherSignBillVo.java
 *  
 *  FILE NAME          :OtherSignBillVo.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.management.api.shared.domain.OtherSignBillEntity;
import com.deppon.foss.module.transfer.management.api.shared.dto.OtherSignBillDto;
/**
 *  其他签单Vo
 * 
 * @author dp-liming
 * @date 2012-11-29 上午10:50:47
 */
public class OtherSignBillVo  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -716660955360756805L;	
	
	/**
	 * 其他签单集合
	 */
	private List<OtherSignBillEntity> otherSignBillList;
	
	/**
	 * 其他签单entity
	 */
	private OtherSignBillEntity otherSignBillEntity;
	
	/**
	 * 其他签单Dto
	 */
	private OtherSignBillDto otherSignBillDto;

	/**
	 * id
	 */
	private String id;

	/**
	 * 获取 其他签单集合.
	 *
	 * @return the 其他签单集合
	 */
	public List<OtherSignBillEntity> getOtherSignBillList() {
		return otherSignBillList;
	}

	/**
	 * 设置 其他签单集合.
	 *
	 * @param otherSignBillList the new 其他签单集合
	 */
	public void setOtherSignBillList(List<OtherSignBillEntity> otherSignBillList) {
		this.otherSignBillList = otherSignBillList;
	}

	/**
	 * 获取 其他签单entity.
	 *
	 * @return the 其他签单entity
	 */
	public OtherSignBillEntity getOtherSignBillEntity() {
		return otherSignBillEntity;
	}

	/**
	 * 设置 其他签单entity.
	 *
	 * @param otherSignBillEntity the new 其他签单entity
	 */
	public void setOtherSignBillEntity(OtherSignBillEntity otherSignBillEntity) {
		this.otherSignBillEntity = otherSignBillEntity;
	}

	/**
	 * 获取 其他签单Dto.
	 *
	 * @return the 其他签单Dto
	 */
	public OtherSignBillDto getOtherSignBillDto() {
		return otherSignBillDto;
	}

	/**
	 * 设置 其他签单Dto.
	 *
	 * @param otherSignBillDto the new 其他签单Dto
	 */
	public void setOtherSignBillDto(OtherSignBillDto otherSignBillDto) {
		this.otherSignBillDto = otherSignBillDto;
	}

	/**
	 * 获取 id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置 id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	

}