/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-exceptiongoods
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/exceptiongoods/server/dao/impl/ContrabandGoodsDao.java
 *  
 *  FILE NAME          :ContrabandGoodsDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.exceptiongoods.server.dao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.exceptiongoods.api.server.dao.IContrabandGoodsDao;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.ContrabandGoodsEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 实现违禁品的基本操作
 * @author 097457-foss-wangqiang
 * @date 2012-12-25 下午12:17:02
 */
public class ContrabandGoodsDao extends iBatis3DaoImpl implements IContrabandGoodsDao {

	/** mapper 文件命名空间*/
	public static final String NAME_SPACE = "foss.exceptiongoods.contrabandgoods.";
	/**
	 * 增加违禁品
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-13 下午4:42:04
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.dao.IContrabandGoodsDao#addContrabandGoods(com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.ContrabandGoodsEntity)
	 */
	@Override
	public int addContrabandGoods(ContrabandGoodsEntity contrabandGoodsEntity) {
		contrabandGoodsEntity.setId(UUIDUtils.getUUID());
		this.getSqlSession().insert(NAME_SPACE + "insertContrabandGoods", contrabandGoodsEntity);
		return FossConstants.SUCCESS;
	}
	/**
	 * 更新违禁品
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-13 下午4:42:23
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.dao.IContrabandGoodsDao#updateContrabandGoods(com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.ContrabandGoodsEntity)
	 */
	@Override
	public int updateContrabandGoods(ContrabandGoodsEntity contrabandGoodsEntity) {
		this.getSqlSession().update(NAME_SPACE + "updateContrabandGoods", contrabandGoodsEntity);
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 分页查询违禁品
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-5 下午4:10:33
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.dao.IContrabandGoodsDao#queryContrabandGoods(com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.ContrabandGoodsEntity, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ContrabandGoodsEntity> queryContrabandGoods(ContrabandGoodsEntity contrabandGoods, int limit, int start) {
		return this.getSqlSession().selectList(NAME_SPACE + "queryContrabandGoods", contrabandGoods, new RowBounds(start, limit));
	}
	
	/**
	 * 违禁品总数
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-5 下午5:18:10
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.dao.IContrabandGoodsDao#queryContrabandGoodsCount(com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.ContrabandGoodsEntity)
	 */
	@Override
	public Long queryContrabandGoodsCount(ContrabandGoodsEntity contrabandGoods) {
		return (Long)this.getSqlSession().selectOne(NAME_SPACE + "queryContrabandGoodsCount", contrabandGoods);
	}
	
	/**
	 * 查询违禁品
	 * @author 097457-foss-wangqiang
	 * @date 2013-1-30 下午4:50:57
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.dao.IContrabandGoodsDao#queryContrabandGoodsStatus(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ContrabandGoodsEntity queryContrabandGoods(String waybillNo){
		List<ContrabandGoodsEntity> list = this.getSqlSession().selectList(NAME_SPACE + "queryUniqueContrabandGoods", waybillNo);
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 增加从QMS推送的违禁品
	 * @author 316759-foss-ruipengwang
	 * @date 2016-05-19 下午16:30:04
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.dao.IContrabandGoodsDao#addContrabandGoodsFromQms(com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.ContrabandGoodsEntity)
	 */
	@Override
	public int addContrabandGoodsFromQms(ContrabandGoodsEntity contrabandGoodsEntity) {
		contrabandGoodsEntity.setId(UUIDUtils.getUUID());
		this.getSqlSession().insert(NAME_SPACE + "insertContrabandGoodsFromQms", contrabandGoodsEntity);
		return FossConstants.SUCCESS;
	}
	
	/**
	 * QMS更新违禁品
	 * @author 316759-foss-reipengwang
	 * @date 2016-05-20上午10:45:23
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.dao.IContrabandGoodsDao#updateContrabandGoods(com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.ContrabandGoodsEntity)
	 */
	@Override
	public int updateContrabandGoodsFromQms(ContrabandGoodsEntity contrabandGoodsEntity) {
		this.getSqlSession().update(NAME_SPACE + "updateContrabandGoodsFromQms", contrabandGoodsEntity);
		return FossConstants.SUCCESS;
	}

}