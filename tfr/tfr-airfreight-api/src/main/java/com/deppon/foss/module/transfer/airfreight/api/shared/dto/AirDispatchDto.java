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
 *  PROJECT NAME  : tfr-airfreight-api
 *  
 *  package_name  : 
 *  
 *  FILE PATH          :/AirDispatchDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.shared.dto;

import java.io.Serializable;

/**
 * 空运总调dto
 * @author foss-liuxue(for IBM)
 * @date 2013-1-30 上午9:41:16
 */
public class AirDispatchDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1298508038832538763L;

	/**
	 * 空运总调code
	 */
	private String orgCode;
	
	/**
	 * 空运总调name
	 */
	private String orgName;

	/**
	 * 获取 空运总调code.
	 *
	 * @return the 空运总调code
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * 设置 空运总调code.
	 *
	 * @param orgCode the new 空运总调code
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * 获取 空运总调name.
	 *
	 * @return the 空运总调name
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * 设置 空运总调name.
	 *
	 * @param orgName the new 空运总调name
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	

}