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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/PickupAndDeliverySmallZoneDao.java
 * 
 * FILE NAME        	: PickupAndDeliverySmallZoneDao.java
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
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.INewbirdinfoDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NewbirdinfoEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 定人定区集中集中接送货小区Dao实现类：对定人定区小区提供增删改查的操作
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-10-12 下午1:47:49
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-10-12 下午1:47:49
 * @since
 * @version
 */
public class NewbirdinfoDao extends SqlSessionDaoSupport implements
		INewbirdinfoDao {

	private static final String NAMESPACE = "foss.bse.bse-baseinfo.cainiaoinfo.";

	/**
	 * 根据传入对象查询符合条件所有菜鸟异常单信息
	 * 
	 * @author 261997-foss-css
	 * @date 2015-6-3 下午17:05:49
	 * @param entity
	 *            菜鸟异常单实体
	 * @param limit
	 *            每页最大显示记录数
	 * @param start
	 *            开始记录数
	 * @return 符合条件的实体列表
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<NewbirdinfoEntity> queryNewbirdinfo(NewbirdinfoEntity entity,
			int limit, int start) {

		RowBounds rowBounds = new RowBounds(start, limit);

		return this.getSqlSession().selectList(NAMESPACE + "getAllInfos",
				entity, rowBounds);
	}

	/**
	 * 根据传入对象查询符合条件所有菜鸟异常单信息
	 * 
	 * @author 261997-foss-css
	 * @date 2015-6-3 下午17:05:49
	 * @param entity
	 *            菜鸟异常单实体
	 * @return 符合条件的实体列表
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<NewbirdinfoEntity> queryNewbirdinfoByNoPage(
			NewbirdinfoEntity entity) {

		return this.getSqlSession().selectList(NAMESPACE + "getAllInfos",
				entity);
	}

	/**
	 * 统计总记录数
	 * 
	 * @author 261997-foss-css
	 * @date 2015-5-30 下午1:47:49
	 * @param entity
	 *            集中接送货小区实体
	 * @return
	 * @see
	 */
	@Override
	public Long queryRecordCount(NewbirdinfoEntity entity) {

		return (Long) this.getSqlSession().selectOne(NAMESPACE + "getCount",
				entity);
	}

	/**
	 * <p>
	 * 新增一个“用户信息”实体入库（忽略实体中是否存在空值）
	 * </p>
	 * 
	 * @author 261997-foss-css
	 * @date 2015-06-03 下午16:31:53
	 * @param user
	 *            “菜鸟破损单”实体
	 * @return 影响记录数
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.INewbirdinfoDao#addUser(com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity)
	 */
	@Override
	public int addNewbirdinfo(NewbirdinfoEntity entity) {
		entity.setId(UUIDUtils.getUUID());
		entity.setOperateTime(new Date());
		entity.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().insert(NAMESPACE + "insert", entity);
	}

	/**
	 * 判断是否为淘宝订单
	 * 
	 * @author 261997-foss-css
	 * @date 2015-6-4 上午8:56:49
	 * @param string
	 *            运单号
	 * @return
	 * @see
	 */
	@Override
	public boolean isBoolTaoBao(String str) {
		return (Long) this.getSqlSession().selectOne(
				NAMESPACE + "getTaoBaoCount", str) > 0 ? true : false;
	}

	@Override
	public int updateWaybillPhone(Map<String, Object> map) {
		//判断非空等操作，在service具体业务层做
		return this.getSqlSession().update(NAMESPACE+"updateWaybillPhone", map);
	}

	@Override
	public int updateWaybillMobilephone(Map<String, Object> map) {
		//判断非空等操作，在service具体业务层做
		return this.getSqlSession().update(NAMESPACE+"updateWaybillMobilephone", map); 		
	}

}
