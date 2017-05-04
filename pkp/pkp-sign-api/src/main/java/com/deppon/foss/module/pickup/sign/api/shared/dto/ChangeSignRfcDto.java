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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/dto/ChangeSignRfcDto.java
 * 
 * FILE NAME        	: ChangeSignRfcDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.sign.api.shared.domain.SignRfcChangeDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignRfcEntity;
/**
 * 
 * <p>到达更改单信息<br />
 * </p>
 * @title ChangeSignRfcDto.java
 * @package com.deppon.foss.module.pickup.sign.api.shared.dto 
 * @author ibm-lizhiguo
 * @version 0.1 2012-12-22
 */
public class ChangeSignRfcDto implements Serializable{
	private static final long serialVersionUID = 3817064841822783071L;
	/**
	 * 变更主信息
	 */
	private SignRfcEntity signRfcEntity;
	/**
	 * 变更详细信息
	 */
	private List<SignRfcChangeDetailEntity> signRfcChangeDetailList;
	/**
	 * @return signRfcEntity : return the property signRfcEntity.
	 */
	public SignRfcEntity getSignRfcEntity() {
		return signRfcEntity;
	}
	/**
	 * @param signRfcEntity : set the property signRfcEntity.
	 */
	public void setSignRfcEntity(SignRfcEntity signRfcEntity) {
		this.signRfcEntity = signRfcEntity;
	}
	/**
	 * @return signRfcChangeDetailList : return the property signRfcChangeDetailList.
	 */
	public List<SignRfcChangeDetailEntity> getSignRfcChangeDetailList() {
		return signRfcChangeDetailList;
	}
	/**
	 * @param signRfcChangeDetailList : set the property signRfcChangeDetailList.
	 */
	public void setSignRfcChangeDetailList(
			List<SignRfcChangeDetailEntity> signRfcChangeDetailList) {
		this.signRfcChangeDetailList = signRfcChangeDetailList;
	}
}