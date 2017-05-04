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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/commonselector/CommonBankAction.java
 * 
 * FILE NAME        	: CommonBankAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.server.action.commonselector
 * FILE    NAME: CommonBankAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBankService;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.BankVo;

/**
 * 公共组件银行查询ACTION
 * 
 * @author 078823-foss-panGuangJun
 * @date 2012-12-29 下午3:31:12
 */
public class CommonBankAction extends AbstractAction implements IQueryAction {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8746518529320644668L;

	// service
	private IBankService bankService;
	// vo
	private BankVo bankVo;

	/**
	 * 查询银行
	 * 
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-29 下午3:31:43
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@Override
	public String query() {
		bankVo.setBankList(bankService.queryBanks(bankVo.getBankEntity(),
				limit, start));
		setTotalCount(bankService.queryRecordCount(bankVo.getBankEntity()));
		return returnSuccess();
	}

	/**
	 * getter
	 */
	public BankVo getBankVo() {
		return bankVo;
	}

	/**
	 * setter
	 */
	public void setBankVo(BankVo bankVo) {
		this.bankVo = bankVo;
	}

	/**
	 * setter
	 */
	public void setBankService(IBankService bankService) {
		this.bankService = bankService;
	}

}
