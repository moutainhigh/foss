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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/shared/vo/TransferSignBillVo.java
 *  
 *  FILE NAME          :TransferSignBillVo.java
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

import com.deppon.foss.module.transfer.management.api.shared.domain.TransferSignBillEntity;
import com.deppon.foss.module.transfer.management.api.shared.dto.TransferSignBillDto;
/**
 * 转货车签单Vo
 * @author 099197-foss-liming
 * @date 2012-11-29 上午11:49:47
 */
public class TransferSignBillVo  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -716660955360756805L;	
	
	/**
	 * 转货车签单list
	 */
	private List<TransferSignBillEntity> transferSignBillList;//
	
	/**
	 * 转货车签单信息
	 */
	private TransferSignBillEntity transferSignBillEntity;//
	
	/**
	 * 转货车签单信息
	 */
	private TransferSignBillDto transferSignBillDto;//
	
	/**
	 * Id
	 */
	private String id;

	/**
	 * 获取 转货车签单list.
	 *
	 * @return the 转货车签单list
	 */
	public List<TransferSignBillEntity> getTransferSignBillList() {
		return transferSignBillList;
	}

	/**
	 * 设置 转货车签单list.
	 *
	 * @param transferSignBillList the new 转货车签单list
	 */
	public void setTransferSignBillList(
			List<TransferSignBillEntity> transferSignBillList) {
		this.transferSignBillList = transferSignBillList;
	}

	/**
	 * 获取 转货车签单信息.
	 *
	 * @return the 转货车签单信息
	 */
	public TransferSignBillEntity getTransferSignBillEntity() {
		return transferSignBillEntity;
	}

	/**
	 * 设置 转货车签单信息.
	 *
	 * @param transferSignBillEntity the new 转货车签单信息
	 */
	public void setTransferSignBillEntity(
			TransferSignBillEntity transferSignBillEntity) {
		this.transferSignBillEntity = transferSignBillEntity;
	}

	/**
	 * 获取 转货车签单信息.
	 *
	 * @return the 转货车签单信息
	 */
	public TransferSignBillDto getTransferSignBillDto() {
		return transferSignBillDto;
	}

	/**
	 * 设置 转货车签单信息.
	 *
	 * @param transferSignBillDto the new 转货车签单信息
	 */
	public void setTransferSignBillDto(TransferSignBillDto transferSignBillDto) {
		this.transferSignBillDto = transferSignBillDto;
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