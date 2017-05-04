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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/MacWhiteDao.java
 * 
 * FILE NAME        	: MacWhiteDao.java
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IMacWhiteDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MacWhiteEntity;
import com.deppon.foss.util.define.FossConstants;


/**
 * MAC地址白名单DAO接口实现
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-3-12 上午10:33:34 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-3-12 上午10:33:34
 * @since
 * @version
 * 新增：增，删，改，查 。 author:132599-foss-shenweihua,date:2013-4-25 上午11:01:13
 */
public class MacWhiteDao extends SqlSessionDaoSupport implements IMacWhiteDao{
    
    private static final String NAMESPACE = "foss.bse.bse-baseinfo.macWhite.";
    	
    /**
     * <p>验证MAC地址是否存在</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-3-12 上午9:36:03
     * @param mac mac地址
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMacWhiteDao#queryMacAddressByMac(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean queryMacAddressByMac(String mac) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("macAddress", mac);
	map.put("active", FossConstants.ACTIVE);
	
	List<MacWhiteEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryMacAddressByMac", map);
	
	return CollectionUtils.isNotEmpty(list);
    }
    
    /**
     * <p>新增MAC地址白名单信息</p> 
     * @author 132599-foss-shenweihua
     * @date 2013-4-25 上午10:40:21
     * @param entity
     * @return
     * @see
     */
	@Override
	public int addMacWhite(MacWhiteEntity entity) {
		
		return this.getSqlSession().insert(NAMESPACE + "insert", entity);
	}
	
	/**
     * <p>修改MAC地址白名单信息</p> 
     * @author 132599-foss-shenweihua
     * @date 2013-4-25 上午10:40:21
     * @param entity
     * @return
     * @see
     */
	@Override
	public int updateMacWhite(MacWhiteEntity entity) {
		
		return this.getSqlSession().update(NAMESPACE + "update", entity);
	}
	
	/**
     * <p>作废MAC地址白名单信息</p> 
     * @author 132599-foss-shenweihua
     * @date 2013-4-25 上午10:40:21
     * @param idList MAC地址白名单信息ID集合
     * @return
     * @see
     */
	@Override
	public int deleteMacWhiteById(List<String> idList) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idList", idList);
		map.put("inactive", FossConstants.INACTIVE);
		map.put("active", FossConstants.ACTIVE);
		
		return this.getSqlSession().update(NAMESPACE + "deleteById", map);
	}
	
	/**
     * 根据传入对象查询符合条件所有MAC地址白名单信息
     * 
     * @author 132599-foss-shenweihua
     * @date 2013-4-25 上午10:50:21
     * @param entity
     *            MAC地址白名单信息实体
     * @param limit
     *            每页最大显示记录数
     * @param start
     *            开始记录数
     * @return 符合条件的实体列表
     * @see
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<MacWhiteEntity> queryAllMacWhite(MacWhiteEntity entity,
			int limit, int start) {
		
		RowBounds rowBounds = new RowBounds(start, limit);

		return this.getSqlSession().selectList(NAMESPACE + "queryAllMacWhite",
			entity, rowBounds);
	}
	
	/**
     * 统计总记录数
     * 
     * @author dp-xieyantao
     * @date 2013-4-15 下午3:10:32
     * @param entity
     *             MAC地址白名单信息实体
     * @return
     * @see
     */
	@Override
	public Long queryRecordCount(MacWhiteEntity entity) {
		
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryCount",
				entity);
	}

}
