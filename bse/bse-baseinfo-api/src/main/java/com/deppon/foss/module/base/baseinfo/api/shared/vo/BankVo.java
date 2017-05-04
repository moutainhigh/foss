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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/BankVo.java
 * 
 * FILE NAME        	: BankVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity;

public class BankVo {
	// 银行信息 集合
	private List<BankEntity> bankList;
	//银行信息实体
	private BankEntity bankEntity = new BankEntity();
	// 返回前台的INT类型属性
	private int returnInt;
	/**
	 * 下面是get,set方法
	 * 
	 * @author 073586-FOSS-LIXUEXING
	 * @date 2012-11-27 下午19:40:42
	 */

	public List<BankEntity> getBankList() {
		return bankList;
	}
	public void setBankList(List<BankEntity> bankList) {
		this.bankList = bankList;
	}
	public BankEntity getBankEntity() {
		return bankEntity;
	}
	public void setBankEntity(BankEntity bankEntity) {
		this.bankEntity = bankEntity;
	}
	public int getReturnInt() {
		return returnInt;
	}
	public void setReturnInt(int returnInt) {
		this.returnInt = returnInt;
	}
}
