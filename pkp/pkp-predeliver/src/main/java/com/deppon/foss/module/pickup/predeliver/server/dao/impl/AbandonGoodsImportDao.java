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
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/dao/impl/AbandonGoodsImportDao.java
 * 
 * FILE NAME        	: AbandonGoodsImportDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IAbandonGoodsImportDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.AbandonGoodsImportEntity;

/**
 * 内部带货中间导入表 dao
 * 
 * @date 2012-11-27 上午11:02:07
 */
public class AbandonGoodsImportDao extends iBatis3DaoImpl implements IAbandonGoodsImportDao {

	//内部带货中间导入表 命名空间
	private static final String ABANDONGOODSIMPORTNAMESPACE = "com.deppon.foss.module.pickup.predeliver.api.shared.domain.AbandonGoodsImportEntity.";

	//插入内部带货中间导入表 
	private static final String INSERT = "insert";

	//插入内部带货中间导入表 
	private static final String INSERTSELECT = "insertSelective";

	//根据ID查询
	private static final String SELECTBYPRIMARYKEY = "selectByPrimaryKey";

	//更新内部带货中间导入表 
	private static final String UPDATEBYPRIMARYKEYSELECT = "updateByPrimaryKeySelective";

	//更新内部带货中间导入表 
	private static final String UPDATEBYPRIMARYKEY = "updateByPrimaryKey";

	//根据WAYBILLNO运单号更新内部带货中间导入表 
	private static final String UPDATEBYWAYBILLNO = "updateByWaybillNoSelective";

	/**
	 * 删除内部带货中间导入表
	 * 
	 * @date 2012-11-27 上午11:02:15
	 */
	public int deleteByPrimaryKey(String id) {
		return 0;
	}

	/**
	 * 插入删除内部带货中间导入表
	 * 
	 * @date 2012-11-27 上午11:02:11
	 */
	public int insert(AbandonGoodsImportEntity record) {
		return this.getSqlSession().insert(ABANDONGOODSIMPORTNAMESPACE + INSERT, record);
	}

	/**
	 * 插入内部带货中间导入表
	 * 
	 * @date 2012-11-27 上午11:02:10
	 */
	public int insertSelective(AbandonGoodsImportEntity record) {
		return this.getSqlSession().insert(ABANDONGOODSIMPORTNAMESPACE + INSERTSELECT, record);
	}

	/**
	 * 选择内部带货中间导入表 纪录
	 * 
	 * @date 2012-11-27 上午11:02:09
	 */
	public AbandonGoodsImportEntity selectByPrimaryKey(String id) {
		return (AbandonGoodsImportEntity) this.getSqlSession().selectOne(SELECTBYPRIMARYKEY, id);

	}

	/**
	 * 更新内部带货中间导入表
	 * 
	 * @date 2012-11-27 上午11:02:08
	 */
	public int updateByPrimaryKeySelective(AbandonGoodsImportEntity record) {
		return this.getSqlSession().update(ABANDONGOODSIMPORTNAMESPACE + UPDATEBYPRIMARYKEYSELECT, record);
	}

	/**
	 * 更新内部带货中间导入表
	 * 
	 * @date 2012-11-27 上午11:02:08
	 */
	public int updateByPrimaryKey(AbandonGoodsImportEntity record) {
		return this.getSqlSession().update(ABANDONGOODSIMPORTNAMESPACE + UPDATEBYPRIMARYKEY, record);
	}

	/**
	 * 根据WAYBILLNO运单号更新内部带货中间导入表
	 * 
	 * @date 2012-11-27 上午11:02:08
	 */
	public int updateByWaybillNoSelective(AbandonGoodsImportEntity record) {
		return this.getSqlSession().update(ABANDONGOODSIMPORTNAMESPACE + UPDATEBYWAYBILLNO, record);
	}
}