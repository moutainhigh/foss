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
 *  PROJECT NAME  : tfr-unload
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/server/dao/impl/ChangeLabelGoodsDao.java
 *  
 *  FILE NAME          :ChangeLabelGoodsDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.unload.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.unload.api.server.dao.IChangeLabelGoodsDao;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ChangeLabelGoodsDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 查询重贴标签货物Dao
 * @author ibm-zhangyixin
 * @date 2012-12-25 下午2:18:08
 */
public class ChangeLabelGoodsDao extends iBatis3DaoImpl implements IChangeLabelGoodsDao {
	private static final String NAMESPACE = "foss.unload.changelabelgoods.";

	/**
	 * 查询重贴标签货物-卸车查询tab中的查询
	 * @author ibm-zhangyixin
	 * @date 2012-11-28 上午10:03:20
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ChangeLabelGoodsDto> queryChangeLabelGoodsUnload(
			ChangeLabelGoodsDto changeLabelGoodsDto, int limit, int start) {
		//设置分页
		RowBounds rowBounds = new RowBounds(start, limit);
		//查询重贴标签货物-卸车查询tab中的查询
		return this.getSqlSession().selectList(NAMESPACE + "queryChangeLabelGoodsUnload", changeLabelGoodsDto, rowBounds);
	}

	/**
	 * 查询重贴标签货物-卸车查询tab中的查询总记录数
	 * @author ibm-zhangyixin
	 * @date 2012-11-28 上午10:03:50
	 */
	@Override
	public Long getTotCountUnload(ChangeLabelGoodsDto changeLabelGoodsDto) {
		//查询重贴标签货物-卸车查询tab中的查询总记录数
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "getTotCountUnload", changeLabelGoodsDto);
	}

	/**
	 * 查询重贴标签货物-清仓查询tab中的查询
	 * @author ibm-zhangyixin
	 * @date 2012-11-28 上午10:04:29
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ChangeLabelGoodsDto> queryChangeLabelGoodsStock(
			ChangeLabelGoodsDto changeLabelGoodsDto, int limit, int start) {
		//设置分页
		RowBounds rowBounds = new RowBounds(start, limit);
		//查询重贴标签货物-清仓查询tab中的查询
		return this.getSqlSession().selectList(NAMESPACE + "queryChangeLabelGoodsStock", changeLabelGoodsDto, rowBounds);
	}

	/**
	 * 查询重贴标签货物-清仓查询tab中的查询总记录数 
	 * @author ibm-zhangyixin
	 * @date 2012-11-28 上午10:04:39
	 */
	@Override
	public Long getTotCountStock(ChangeLabelGoodsDto changeLabelGoodsDto) {
		//查询重贴标签货物-清仓查询tab中的查询总记录数 
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "getTotCountStock", changeLabelGoodsDto);
	}

	/** 
	 * @Title: updateChangeLabelGoods 
	 * @Description: 打印完标签后更新数据库中的状态 
	 * @param changeLabelGoodsDto
	 * @return    
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IChangeLabelGoodsDao#updateChangeLabelGoods(com.deppon.foss.module.transfer.unload.api.shared.dto.ChangeLabelGoodsDto)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-3-28上午10:29:44
	 */
	@Override
	public int updateChangeLabelGoods(ChangeLabelGoodsDto changeLabelGoodsDto) {
		this.getSqlSession().update(NAMESPACE + "updateChangeLabelGoods", changeLabelGoodsDto);
		return FossConstants.SUCCESS;
	}
}