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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/PayeeInfoService.java
 * 
 * FILE NAME        	: PayeeInfoService.java
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
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPayeeInfoDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPayeeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PayeeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.PayeeInfoException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 付款方信息Service接口实现
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-12-14
 * 下午4:44:27
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-12-14 下午4:44:27
 * @since
 * @version
 */
public class PayeeInfoService implements IPayeeInfoService {
    
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PayeeInfoService.class);

    /**
     * 付款方信息DAO接口.
     */
    private IPayeeInfoDao payeeInfoDao;

    /**
     * 设置 付款方信息DAO接口.
     *
     * @param payeeInfoDao the new 付款方信息DAO接口
     */
    public void setPayeeInfoDao(IPayeeInfoDao payeeInfoDao) {
	this.payeeInfoDao = payeeInfoDao;
    }

    /**
     * <p>
     * 新增付款方信息
     * </p>.
     *
     * @param entity 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-12-14 下午4:44:27
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPayeeInfoService#addPayeeInfo(com.deppon.foss.module.base.baseinfo.api.shared.domain.PayeeInfoEntity)
     */
    @Override
    public int addPayeeInfo(PayeeInfoEntity entity) {
	if (null == entity) {

	    return FossConstants.FAILURE;
	}
	if(StringUtils.isBlank(entity.getPayeeNo())){
	    return FossConstants.FAILURE;
	}
	PayeeInfoEntity entity2 = payeeInfoDao.queryPayeeInfoByPayeeNo(entity.getPayeeNo());
	if(null != entity2){
	    return FossConstants.FAILURE;
	}
	entity.setId(UUIDUtils.getUUID());
	entity.setCreateDate(new Date());
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	entity.setActive(FossConstants.ACTIVE);

	return payeeInfoDao.addPayeeInfo(entity);
    }

    /**
     * 修改付款方信息 需要版本控制：作废历史记录，新增一条新记录.
     *
     * @param entity 付款方信息实体
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-12-14 下午2:44:17
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPayeeInfoService#updatePayeeInfo(com.deppon.foss.module.base.baseinfo.api.shared.domain.PayeeInfoEntity)
     */
    @Override
    public int updatePayeeInfo(PayeeInfoEntity entity) {

	if (null != entity) {
	    if (StringUtil.isBlank(entity.getPayeeNo())) {
		throw new PayeeInfoException("收款方编码不允许为空！");
	    } else {
		// 先作废历史记录
		int flag = payeeInfoDao.deletePayeeInfoByPayeeNo(
			entity.getPayeeNo(), entity.getModifyUser());
                LOGGER.debug("flag: "+flag);
		if (flag > 0) {// 作废成功
		    entity.setId(UUIDUtils.getUUID());
		    entity.setCreateDate(new Date());
		    entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		    entity.setActive(FossConstants.ACTIVE);
		    //添加一条新记录
		    return payeeInfoDao.addPayeeInfo(entity);
		}
	    }
	}
	
	return FossConstants.FAILURE;
    }

    /**
     * <p>
     * 根据收款方编码作废付款方信息
     * </p>.
     *
     * @param payeeNo 
     * @param modifyUser 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-12-14 下午4:44:29
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPayeeInfoService#deletePayeeInfoByPayeeNo(java.lang.String,
     * java.lang.String)
     */
    @Override
    public int deletePayeeInfoByPayeeNo(String payeeNo, String modifyUser) {
	
	if(StringUtil.isBlank(payeeNo)){
	    throw new PayeeInfoException("收款方编码不允许为空！");
	}else {
	    LOGGER.debug("payeeNo: "+ payeeNo);
	    return payeeInfoDao.deletePayeeInfoByPayeeNo(payeeNo, modifyUser);
	}
    }

    /**
	 * 根据收款方编码查询收款方信息
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-23
	 *
	 */
	@Override
	public PayeeInfoEntity queryPayeeInfoBypayeeNo(String payNo) {
		if(StringUtils.isEmpty(payNo)){
			 throw new PayeeInfoException("收款方编码不允许为空！");
		}
		PayeeInfoEntity entity = payeeInfoDao.queryPayeeInfoByPayeeNo(payNo);
		return entity;
	}

}
