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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/BankDao.java
 * 
 * FILE NAME        	: BankDao.java
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

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IBankDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 银行Dao接口实现
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-10-30 下午4:13:48
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-10-30 下午4:13:48
 * @since
 * @version
 */
public class BankDao extends SqlSessionDaoSupport implements IBankDao {

    private static final String NAMESPACE = "foss.bse.bse-baseinfo.bank.";

    /**
     * 新增银行
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-10-30 下午4:13:48
     * @param entity
     *            银行实体
     * @return 1：成功；-1：失败
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IBankDao#addBank(com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity)
     */
    @Override
    public int addBank(BankEntity entity) {

	return this.getSqlSession().insert(NAMESPACE + "insert", entity);
    }

    /**
     * 根据code作废银行
     * 
     * @author dp-xieyantao
     * @date 2012-10-30 下午4:13:48
     * @param codes
     *            ID字符串数组
     * @param modifyUser
     * @return 1：成功；-1：失败
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IBankDao#deleteBank(java.lang.String[],
     *      java.lang.String)
     */
    @Override
    public int deleteBank(String[] codes, String modifyUser) {

	Map<String, Object> map = new HashMap<String, Object>();
	Date date = new Date();
	map.put("codes", codes);
	map.put("modifyUser", modifyUser);
	map.put("modifyDate", date);
	// 设置版本号
	map.put("versionNo", date.getTime());
	map.put("active", FossConstants.INACTIVE);
	map.put("active0", FossConstants.ACTIVE);

	return this.getSqlSession().update(NAMESPACE + "deleteByCode", map);
    }

    /**
     * 修改银行
     * 
     * @author dp-xieyantao
     * @date 2012-10-30 下午4:13:48
     * @param entity
     *            银行实体
     * @return 1：成功；-1：失败
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IBankDao#updateBank(com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity)
     */
    @Override
    public int updateBank(BankEntity entity) {

	return this.getSqlSession().update(NAMESPACE + "update", entity);
    }

    /**
     * 根据传入对象查询符合条件所有银行信息
     * 
     * @author dp-xieyantao
     * @date 2012-10-30 下午4:13:48
     * @param limit
     *            每页最大显示记录数
     * @param start
     *            开始记录数
     * @return 符合条件的实体列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IBankDao#queryBanks(com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity,
     *      int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<BankEntity> queryBanks(BankEntity entity, int limit, int start) {

	RowBounds rowBounds = new RowBounds(start, limit);

	return this.getSqlSession().selectList(NAMESPACE + "queryAllInfos",
		entity, rowBounds);
    }

    /**
     * 统计总记录数
     * 
     * @author dp-xieyantao
     * @date 2012-10-30 下午4:13:48
     * @param entity
     *            银行实体
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IBankDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity)
     */
    @Override
    public Long queryRecordCount(BankEntity entity) {

	return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryCount",
		entity);
    }

    /**
     * <p>
     * 下载银行数据
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-11-6 下午2:19:28
     * @param entity
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IBankDao#queryBanksForDownLoad(com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<BankEntity> queryBanksForDownLoad(BankEntity entity) {

	return this.getSqlSession().selectList(
		NAMESPACE + "queryBanksForDownLoad", entity);
    }

    /**
     * 获取最后跟新时间
     * 
     * @author dp-yangtong
     * @date 2012-10-30 下午4:13:48
     * @param entity
     *            银行实体
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IBankDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity)
     */
    @Override
    public Date getLastModifyTime() {
	Date date = null;
	Object object = this.getSqlSession().selectOne(
		NAMESPACE + "getLastUpdateTime");
	if (object != null) {
	    date = (Date) object;
	}
	return date;
    }

    /**
     * 数据更新
     * 
     * @author dp-yangtong
     * @date 2012-10-30 下午4:13:48
     * @param entity
     *            银行实体
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IBankDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<BankEntity> getSyncData(Date fromDate, String userID) {
	Map<String, Object> parms = new HashMap<String, Object>();
	parms.put("fromDate", fromDate);
	return this.getSqlSession().selectList(NAMESPACE + "getBanks", parms);

    }
    
    /**
     * <p>根据银行编码查询银行信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-2-2 上午10:34:13
     * @param bankCode 银行编码
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IBankDao#queryBankInfoByCode(java.lang.String)
     */
    @Override
    public BankEntity queryBankInfoByCode(String bankCode) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("bankCode", bankCode);
	map.put("active", FossConstants.ACTIVE);
	
	return (BankEntity)this.getSqlSession().selectOne(NAMESPACE + "queryBankInfoByCode", map);
    }
     /**
      * 根据银行名称查询
      * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
      * @author 268984 
      * @date 2016-4-14 下午3:54:47
      * @param bankName
      * @return 
      * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IBankDao#queryBankInfoByName(java.lang.String)
      */
	@Override
	public BankEntity queryBankInfoByName(String bankName) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("bankName", bankName);
		map.put("active", FossConstants.ACTIVE);
		
		return (BankEntity)this.getSqlSession().selectOne(NAMESPACE + "queryBankInfoByName", map);
	}

}
