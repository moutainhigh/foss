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
 *  PROJECT NAME  : tfr-stockchecking-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/api/shared/dto/StDifferDetailDto.java
 *  
 *  FILE NAME          :StDifferDetailDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stockchecking.api.shared.dto;

import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StDifferDetailEntity;

/**
 * 清仓差异明细业务实体
 * @author foss-wuyingjie
 * @date 2012-12-26 上午9:49:12
 */
public class StDifferDetailDto extends StDifferDetailEntity{

	private static final long serialVersionUID = 1954119633891504493L;

	/**部门编号*/
    private String deptcode;

	/**
	 * 获取 部门编号.
	 *
	 * @return the 部门编号
	 */
	public String getDeptcode() {
		return deptcode;
	}

	/**
	 * 设置 部门编号.
	 *
	 * @param deptcode the new 部门编号
	 */
	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}
}