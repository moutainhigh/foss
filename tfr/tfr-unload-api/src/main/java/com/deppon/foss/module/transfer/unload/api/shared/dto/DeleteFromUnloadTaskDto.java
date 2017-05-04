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
 *  PROJECT NAME  : tfr-unload-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/shared/dto/DeleteFromUnloadTaskDto.java
 *  
 *  FILE NAME          :DeleteFromUnloadTaskDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 ******************************************************************************/
package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;
import java.util.List;

/** 
 * @className: DeleteFromUnloadTaskDto
 * @author: ShiWei shiwei@outlook.com
 * @description: 将单据list或者卸车员list从卸车任务中删除时使用该dto给mapper传参
 * @date: 2012-12-14 下午2:51:51
 * 
 */
public class DeleteFromUnloadTaskDto implements Serializable {

	private static final long serialVersionUID = -1089157040980049637L;

	//单据编号或者卸车员code
	private List<String> noList;
	//卸车任务ID
	private String unloadTaskId;
	
	public List<String> getNoList() {
		return noList;
	}
	public void setNoList(List<String> noList) {
		this.noList = noList;
	}
	public String getUnloadTaskId() {
		return unloadTaskId;
	}
	public void setUnloadTaskId(String unloadTaskId) {
		this.unloadTaskId = unloadTaskId;
	}

}