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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/OwedLimitRegionAction.java
 * 
 * FILE NAME        	: OwedLimitRegionAction.java
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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExchangeRateService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExchangeRateEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.ExchangeRateVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

/**
 * 汇率信息维护Action类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-4-16 下午5:05:30 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-4-16 下午5:05:30
 * @since
 * @version
 */
public class ExchangeRateAction extends AbstractAction {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1433233896654988296L;

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(ExchangeRateAction.class);

    /**
     * 汇率信息维护Service接口.
     */
    private IExchangeRateService exchangeRateService;

    /**
     * 汇率信息VO.
     */
    private ExchangeRateVo vo = new ExchangeRateVo();
    
    /**
     * <p>添加汇率信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-4-16 下午5:34:10
     * @return
     * @see
     */
    @JSON
    public String addExchangeRate() {
	try {
	    UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
	    // 获取Form信息
	    ExchangeRateEntity entity = vo.getEntity();
	    entity.setCreateUser(userCode);
	    entity.setModifyUser(userCode);

	    // 保存成功返回一个对象
	    exchangeRateService.addExchangeRate(entity);

	    return returnSuccess(MessageType.SAVE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }
    
    /**
     * <p>作废汇率信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-4-16 下午5:34:36
     * @return
     * @see
     */
    @JSON
    public String deleteExchangeRate() {
	try {
	    UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
	    
	    exchangeRateService.deleteExchangeRateByVirtualCode(vo.getCodeList(), userCode);

	    return returnSuccess(MessageType.DELETE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }
    /**
     * 
     *<p>查询最大失效时间的币种汇率</p>
     *@author 130566-zengJunfan
     *@date   2013-9-16下午7:35:51
     * @return
     */
    @JSON
    public String queryExchangeRateMaxEndTime(){
    	String currency =vo.getEntity().getCurrency();
    	try {
    		ExchangeRateEntity entity =exchangeRateService.queryRateEntityByMaxEndTimeCurrency(currency);
    		vo.setEntity(entity);
    		return returnSuccess();
		} catch (BusinessException e) {
			 LOGGER.debug(e.getMessage(), e);
			    return returnError(e);
		}
    }    
    /**
     * <p>修改汇率信息</p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-4-16 下午5:34:36
     * @see
     */
    @JSON
    public String updateExchangeRate() {
	try {
	    UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
	    // 获取Form信息
	    ExchangeRateEntity entity = vo.getEntity();
	    entity.setModifyUser(userCode);

	    // 保存成功返回一个对象
	    exchangeRateService.updateExchangeRate(entity);

	    return returnSuccess(MessageType.UPDATE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }
    
    /**
     * <p>分页查询临欠额度区间信息</p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-4-16 下午5:34:36
     * @see
     */
    @JSON
    public String queryExchangeRates() {
	try {
		//分页查询库中的全部信息
	    List<ExchangeRateEntity> entityList = exchangeRateService.queryAllExchangeRate(vo.getEntity(), this.getLimit(), this.getStart());	    
	    vo.setEntityList(entityList);
	    // 查询总记录数
	    Long totalCount = exchangeRateService.queryRecordCount(vo.getEntity());
	    // 设置totalCount
	    this.setTotalCount(totalCount);

	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }    
   
    public ExchangeRateVo getVo() {
		return vo;
	}

	public void setVo(ExchangeRateVo vo) {
		this.vo = vo;
	}

	/**
     * 设置 汇率信息维护Service接口.
     *
     * @param exchangeRateService the exchangeRateService to set
     */
    public void setExchangeRateService(IExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    
}
