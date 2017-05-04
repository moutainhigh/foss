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
 *  PROJECT NAME  : tfr-common-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/api/shared/dto/ReportOALessGoodsFoundDto.java
 *  
 *  FILE NAME          :ReportOALessGoodsFoundDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.common.api.shared.dto;

import java.io.Serializable;

/** 
 * @className: ReportOALessGoodsFoundDto
 * @author: ShiWei shiwei@outlook.com
 * @description: TODO
 * @date: 2013-1-14 下午8:17:14
 * 
 */
public class ReportOALessGoodsFoundDto implements Serializable {

	private static final long serialVersionUID = 135345353568970780L;
	
	private String oaErrorNo;
	private String userCode;
	private String userName;
	private String preOrg;
	public String getOaErrorNo() {
		return oaErrorNo;
	}
	public void setOaErrorNo(String oaErrorNo) {
		this.oaErrorNo = oaErrorNo;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPreOrg() {
		return preOrg;
	}
	public void setPreOrg(String preOrg) {
		this.preOrg = preOrg;
	}

}