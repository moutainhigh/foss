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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/BankAction.java
 * 
 * FILE NAME        	: BankAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.server.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBankService;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.BankException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.BankVo;

/**
 * 银行信息action
 * 
 * @author 073586-FOSS-LIXUEXING
 * @date 2012-11-28 19:10:10
 * @since
 * @version 0.01
 */
public class BankAction extends AbstractAction {

    /**
     * 下面是声明的属性
     */
    private static final long serialVersionUID = -3372339694836134644L;

    // service接口
    private IBankService bankService;

    // 银行信息实体
    private BankVo bankVo = new BankVo();

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(BankAction.class);

    /**
     * <p>
     * 查询偏银行信息
     * </p>
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-01 09:10:10
     * @return String
     */
    public String queryBanks() {
	try {
	    bankVo.setBankList(bankService.queryBanks(bankVo.getBankEntity(),
		    limit, start));
	    totalCount = bankService.queryRecordCount(bankVo.getBankEntity());
	    return returnSuccess();
	} catch (BankException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * 修改银行信息
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-01 09:10:10
     * @return String
     * @see
     */
    public String updateBank() {
	bankVo.setReturnInt(bankService.updateBank(bankVo.getBankEntity()));
	return returnSuccess();
    }

    /*
     * =================================================================
     * 下面是get,set方法：
     */
    public BankVo getBankVo() {
	return bankVo;
    }

    public void setBankVo(BankVo bankVo) {
	this.bankVo = bankVo;
    }

    public void setBankService(IBankService bankService) {
	this.bankService = bankService;
    }

}
