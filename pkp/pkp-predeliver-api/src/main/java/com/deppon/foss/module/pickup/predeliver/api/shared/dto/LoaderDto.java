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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/LoaderDto.java
 * 
 * FILE NAME        	: LoaderDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 装车人Dto，属性从com.deppon.foss.module.transfer.load.api.shared.domain.
 * LoaderParticipationEntity拷贝
 * @author ibm-wangxiexu
 * @date 2012-12-5 下午9:17:14
 */
public class LoaderDto implements Serializable {
	private static final long serialVersionUID = 8253038787955294763L;
	private String loaderName;
	private String loaderCode;
	private Date joinTime;
	private Date leaveTime;

	/**
	 * @return the loaderName
	 */
	public String getLoaderName() {
		return loaderName;
	}

	/**
	 * @param loaderName the loaderName to see
	 */
	public void setLoaderName(String loaderName) {
		this.loaderName = loaderName;
	}

	/**
	 * @return the loaderCode
	 */
	public String getLoaderCode() {
		return loaderCode;
	}

	/**
	 * @param loaderCode the loaderCode to see
	 */
	public void setLoaderCode(String loaderCode) {
		this.loaderCode = loaderCode;
	}

	/**
	 * @return the joinTime
	 */
	public Date getJoinTime() {
		return joinTime;
	}

	/**
	 * @param joinTime the joinTime to see
	 */
	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}

	/**
	 * @return the leaveTime
	 */
	public Date getLeaveTime() {
		return leaveTime;
	}

	/**
	 * @param leaveTime the leaveTime to see
	 */
	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}

}