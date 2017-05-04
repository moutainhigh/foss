/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/BillSloganAndDeptVo.java
 * 
 * FILE NAME        	: BillSloganAndDeptVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillSloganAppOrgEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillSloganEntity;

/**
 * (单据VO).
 *
 * @author 073586-FOSS-LIXUEXING
 * @date 2012-12-12 09:40:42
 * @since
 * @version
 */
public class BillSloganAndDeptVo implements Serializable {
    
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 952388267058020611L;
	
	/**
	 * 单据实体.
	 */
	private BillSloganEntity billSloganEntity;
	
	/**
	 * 单据实体LIST.
	 */
	private List<BillSloganEntity> billSloganEntityList;
	
	/**
	 * 单据部门实体.
	 */
	private BillSloganAppOrgEntity billSloganAppOrgEntity;
	
	/**
	 * 单据部门实体LIST.
	 */
	private List<BillSloganAppOrgEntity> billSloganAppOrgEntityList;
	
	/**
	 * 实体中对应virtualCode字段，字段间以逗号分隔[支持批量删除].
	 */
	private String codeStr;
	
	/**
	 * 返回前台的INT类型属性.
	 */
	private int returnInt;
	
	/**
	 * 下面是get,set方法.
	 *
	 * @return the 单据实体
	 * @author 073586-FOSS-LIXUEXING
	 * @date 2012-11-27  19:40:42
	 */
	public BillSloganEntity getBillSloganEntity() {
		return billSloganEntity;
	}
	
	/**
	 * 设置 单据实体.
	 *
	 * @param billSloganEntity the new 单据实体
	 */
	public void setBillSloganEntity(BillSloganEntity billSloganEntity) {
		this.billSloganEntity = billSloganEntity;
	}
	
	/**
	 * 获取 单据实体LIST.
	 *
	 * @return the 单据实体LIST
	 */
	public List<BillSloganEntity> getBillSloganEntityList() {
		return billSloganEntityList;
	}
	
	/**
	 * 设置 单据实体LIST.
	 *
	 * @param billSloganEntityList the new 单据实体LIST
	 */
	public void setBillSloganEntityList(List<BillSloganEntity> billSloganEntityList) {
		this.billSloganEntityList = billSloganEntityList;
	}
	
	/**
	 * 获取 单据部门实体.
	 *
	 * @return the 单据部门实体
	 */
	public BillSloganAppOrgEntity getBillSloganAppOrgEntity() {
		return billSloganAppOrgEntity;
	}
	
	/**
	 * 设置 单据部门实体.
	 *
	 * @param billSloganAppOrgEntity the new 单据部门实体
	 */
	public void setBillSloganAppOrgEntity(
			BillSloganAppOrgEntity billSloganAppOrgEntity) {
		this.billSloganAppOrgEntity = billSloganAppOrgEntity;
	}
	
	/**
	 * 获取 单据部门实体LIST.
	 *
	 * @return the 单据部门实体LIST
	 */
	public List<BillSloganAppOrgEntity> getBillSloganAppOrgEntityList() {
		return billSloganAppOrgEntityList;
	}
	
	/**
	 * 设置 单据部门实体LIST.
	 *
	 * @param billSloganAppOrgEntityList the new 单据部门实体LIST
	 */
	public void setBillSloganAppOrgEntityList(
			List<BillSloganAppOrgEntity> billSloganAppOrgEntityList) {
		this.billSloganAppOrgEntityList = billSloganAppOrgEntityList;
	}
	
	/**
	 * 获取 实体中对应virtualCode字段，字段间以逗号分隔[支持批量删除].
	 *
	 * @return the 实体中对应virtualCode字段，字段间以逗号分隔[支持批量删除]
	 */
	public String getCodeStr() {
		return codeStr;
	}
	
	/**
	 * 设置 实体中对应virtualCode字段，字段间以逗号分隔[支持批量删除].
	 *
	 * @param codeStr the new 实体中对应virtualCode字段，字段间以逗号分隔[支持批量删除]
	 */
	public void setCodeStr(String codeStr) {
		this.codeStr = codeStr;
	}
	
	/**
	 * 获取 返回前台的INT类型属性.
	 *
	 * @return the 返回前台的INT类型属性
	 */
	public int getReturnInt() {
		return returnInt;
	}
	
	/**
	 * 设置 返回前台的INT类型属性.
	 *
	 * @param returnInt the new 返回前台的INT类型属性
	 */
	public void setReturnInt(int returnInt) {
		this.returnInt = returnInt;
	}
}
