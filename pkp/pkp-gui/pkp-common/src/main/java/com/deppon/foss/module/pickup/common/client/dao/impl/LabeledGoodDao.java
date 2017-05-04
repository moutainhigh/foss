/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/impl/LabeledGoodDao.java
 * 
 * FILE NAME        	: LabeledGoodDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
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
package com.deppon.foss.module.pickup.common.client.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.deppon.foss.module.pickup.common.client.dao.ILabeledGoodDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.util.UUIDUtils;
import com.google.inject.Inject;

/**
 * 
 * 货签数据持久层
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-sunrui,date:2012-10-30 下午3:18:06,
 * </p>
 * 
 * @author foss-sunrui
 * @date 2012-10-30 下午3:18:06
 * @since
 * @version
 */
public class LabeledGoodDao implements ILabeledGoodDao {
	/**
	 * 名称空间
	 */
    private static final String NAMESPACE = "pkp.LabeledGoodMapper.";
    /**
	 * 数据库连接
	 */
    private SqlSession sqlSession;
	/**
	 * 数据库连接
	 * @return void
	 * @since:1.6
	 */
    @Inject
    public void setSqlSession(SqlSession sqlSession) {
    	this.sqlSession = sqlSession;
    }

    /**
     * 
     * <p>
     * 按需插入
     * </p>
     * 
     * @author foss-sunrui
     * @date 2012-10-30 下午3:31:11
     * @param record
     * @return
     * @see com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodDao#insertSelective(com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity)
     */
    public int insertSelective(LabeledGoodEntity record) {
	record.setId(UUIDUtils.getUUID());
	return sqlSession.insert(NAMESPACE + "insertLabeledGoodSelective",
		record);
    }

    /**
     * 
     * <p>
     * 批量插入
     * </p>
     * 
     * @author foss-sunrui
     * @date 2012-11-6 下午4:54:09
     * @param list
     * @see com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodDao#insertBatch(java.util.List)
     */
    public int insertBatch(List<LabeledGoodEntity> list) {
		if (list.size() > 0) {
		    for (int i = 0; i < list.size(); i++) {
			list.get(i).setId(UUIDUtils.getUUID());
		    }
		    return sqlSession
			    .insert(NAMESPACE + "insertLabeledGoodBatch", list);
		} else {
		    return 0;
		}
    }

    /**
     * 
     * <p>
     * 通过运单号查询最大的序列号
     * </p>
     * 
     * @author foss-sunrui
     * @date 2012-10-30 下午4:56:29
     * @param waybillNo
     * @return
     * @see com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodDao#queryLastSerialByWaybillNo(java.lang.String)
     */
    public LabeledGoodEntity queryLastSerialByWaybillNo(String waybillNo) {
		List<LabeledGoodEntity> labeledGoodList = sqlSession.selectList(
			NAMESPACE + "selectlastSerialNoBywaybillNo", waybillNo);
		if (labeledGoodList.size() > 0) {
		    return labeledGoodList.get(0);
		} else {
		    return null;
		}
    }

    /**
     * 
     * <p>
     * 通过运单号查询所有流水号
     * </p>
     * 
     * @author foss-jiangfei
     * @date 2012-10-30 下午4:55:34
     * @param waybillNo
     * @return
     * @see
     */
    public List<LabeledGoodEntity> queryAllSerialByWaybillNo(String waybillNo) {
		List<LabeledGoodEntity> labeledGoodList = sqlSession.selectList(
			NAMESPACE + "selectlastSerialNoBywaybillNo", waybillNo);
		return labeledGoodList;
    }

}