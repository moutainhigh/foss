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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/shared/dto/LoaderDto.java
 *  
 *  FILE NAME          :LoaderDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-pda-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.pda.api.shared.dto
 * FILE    NAME: LoaderDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.pda.api.shared.dto;

import java.io.Serializable;

/**
 * DTO理货员
 * @author dp-duyi
 * @date 2013-1-23 下午5:11:46
 */
public class LoaderDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1763661215111778464L;
	/**理货员编号*/
	private String loaderCode;
	/**标识*/
	private String flag;
	
	/**
	 * Gets the 理货员编号.
	 *
	 * @return the 理货员编号
	 */
	public String getLoaderCode() {
		return loaderCode;
	}
	
	/**
	 * Sets the 理货员编号.
	 *
	 * @param loaderCode the new 理货员编号
	 */
	public void setLoaderCode(String loaderCode) {
		this.loaderCode = loaderCode;
	}
	
	/**
	 * Gets the 标识.
	 *
	 * @return the 标识
	 */
	public String getFlag() {
		return flag;
	}
	
	/**
	 * Sets the 标识.
	 *
	 * @param flag the new 标识
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}
}