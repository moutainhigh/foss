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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/PayeeInfoDao.java
 * 
 * FILE NAME        	: PayeeInfoDao.java
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
package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPayeeInfoDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PayeeInfoEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 付款方信息DAO接口实现
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-12-14 下午2:55:52 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-12-14 下午2:55:52
 * @since
 * @version
 */
public class PayeeInfoDao extends SqlSessionDaoSupport implements IPayeeInfoDao {
    
    private static final String NAMESPACE = "foss.bse.bse-baseinfo.payeeInfo.";

    /** 
     * 新增付款方信息
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-12-14 下午2:44:17
     * @param entity
     *            付款方信息实体
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPayeeInfoDao#addPayeeInfo(com.deppon.foss.module.base.baseinfo.api.shared.domain.PayeeInfoEntity)
     */
    @Override
    public int addPayeeInfo(PayeeInfoEntity entity) {
	
	return this.getSqlSession().insert(NAMESPACE + "insert", entity);
    }

    /** 
     * 根据收款方编码作废付款方信息
     * @author dp-xieyantao
     * @date 2012-12-14 下午2:44:17
     * @param payeeNo
     * @param modifyUser
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPayeeInfoDao#deletePayeeInfoByPayeeNo(java.lang.String, java.lang.String)
     */
    @Override
    public int deletePayeeInfoByPayeeNo(String payeeNo, String modifyUser) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("payeeNo", payeeNo);
	map.put("modifyUser", modifyUser);
	map.put("modifyDate", new Date());
	map.put("active", FossConstants.INACTIVE);
	map.put("active0", FossConstants.ACTIVE);
	
	return this.getSqlSession().update(NAMESPACE + "deletePayeeInfoByPayeeNo", map);
    }
    
    /**
     * <p>根据收款方编码查询收款方信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-5-16 下午6:03:59
     * @param payeeNo 收款方编码
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPayeeInfoDao#queryPayeeInfoByPayeeNo(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public PayeeInfoEntity queryPayeeInfoByPayeeNo(String payeeNo) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("payeeNo", payeeNo);
	map.put("active", FossConstants.ACTIVE);
	
	List<PayeeInfoEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryPayeeInfoByPayeeNo", map);
	
	if(CollectionUtils.isNotEmpty(list)){
	    return list.get(0);
	}else {
	    return null;
	}
    }

}
