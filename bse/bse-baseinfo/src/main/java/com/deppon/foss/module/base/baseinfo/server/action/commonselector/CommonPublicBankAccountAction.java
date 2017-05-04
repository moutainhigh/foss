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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/commonselector/CommonPublicBankAccountAction.java
 * 
 * FILE NAME        	: CommonPublicBankAccountAction.java
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
 * FILE    NAME: CommonPublicBankAccountAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonPublicBankAccountService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PublicBankAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CommonPublicBankAccountVo;

/**
 * 公共查询组件--对公银行账户查询ACTION.
 * 
 * @author 078823-foss-panGuangJun
 * @date 2012-12-15 下午3:25:42
 */
public class CommonPublicBankAccountAction extends AbstractAction implements
		IQueryAction {

	private static final long serialVersionUID = 9159077336190440851L;

	/**
	 * 组织对公账户Service
	 */
	private ICommonPublicBankAccountService commonPublicBankAccountService;

	/**
	 * 组织对公账户Vo
	 */
	private CommonPublicBankAccountVo commonPublicBankAccountVo = new CommonPublicBankAccountVo();

	/**
	 * 查询对公银行账户.
	 * 
	 * @return the string
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-15 下午3:25:42
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@Override
	public String query() {
		Long totalCount=commonPublicBankAccountService.countQueryPublicBankAccountByDto(commonPublicBankAccountVo.getPublicBankAccountDto());
		if(totalCount>0){
			List<PublicBankAccountEntity> pBankAccountEntityList = commonPublicBankAccountService.queryPublicBankAccountByDto(commonPublicBankAccountVo.getPublicBankAccountDto(), start, limit);
			commonPublicBankAccountVo.setPublicBankAccountEntityList(pBankAccountEntityList);
		}
		setTotalCount(totalCount);
		
		return returnSuccess();
	}

	public CommonPublicBankAccountVo getCommonPublicBankAccountVo() {
		return commonPublicBankAccountVo;
	}

	public void setCommonPublicBankAccountVo(
			CommonPublicBankAccountVo commonPublicBankAccountVo) {
		this.commonPublicBankAccountVo = commonPublicBankAccountVo;
	}

	public void setCommonPublicBankAccountService(
			ICommonPublicBankAccountService commonPublicBankAccountService) {
		this.commonPublicBankAccountService = commonPublicBankAccountService;
	}
}
