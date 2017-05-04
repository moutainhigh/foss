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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/api/shared/dto/StWaybillDetailDto.java
 *  
 *  FILE NAME          :StWaybillDetailDto.java
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

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StDifferDetailEntity;

/**
 * 运单明细业务实体
 * @author foss-wuyingjie
 * @date 2012-12-26 上午9:55:46
 */
public class StWaybillDetailDto {
	/**部门编号*/
    private String deptcode;
    /**少货列表*/
	private List<StDifferDetailEntity> lackList = new ArrayList<StDifferDetailEntity>();
	/**多货列表*/
	private List<StDifferDetailEntity> surplusList = new ArrayList<StDifferDetailEntity>();

	/**
	 * 获取 少货列表.
	 *
	 * @return the 少货列表
	 */
	public List<StDifferDetailEntity> getLackList() {
		return lackList;
	}

	/**
	 * 设置 少货列表.
	 *
	 * @param lackList the new 少货列表
	 */
	public void setLackList(List<StDifferDetailEntity> lackList) {
		this.lackList = lackList;
	}

	/**
	 * 获取 多货列表.
	 *
	 * @return the 多货列表
	 */
	public List<StDifferDetailEntity> getSurplusList() {
		return surplusList;
	}

	/**
	 * 设置 多货列表.
	 *
	 * @param surplusList the new 多货列表
	 */
	public void setSurplusList(List<StDifferDetailEntity> surplusList) {
		this.surplusList = surplusList;
	}

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
	
	/**
	 * 
	 *
	 * @return 
	 */
	public String getLackListString(){
		StringBuffer s = new StringBuffer(100);
		for(StDifferDetailEntity entity: lackList){
			s.append(entity.getSerialNo()).append(",");
		}
		
		return s.toString();
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	public String getSurplusListString(){
		StringBuffer s = new StringBuffer(100);
		for(StDifferDetailEntity entity: surplusList){
			s.append(entity.getSerialNo()).append(",");
		}
		
		return s.toString();
	}
}