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
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/dao/impl/LabeledGoodPDADao.java
 * 
 * FILE NAME        	: LabeledGoodPDADao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodPDADao;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodPDAEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * PDA货签信息数据持久层
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-12-11 下午2:40:47</p>
 * @author foss-sunrui
 * @date 2012-12-11 下午2:40:47
 * @since
 * @version
 */
public class LabeledGoodPDADao extends iBatis3DaoImpl implements ILabeledGoodPDADao {
	
	private static final String NAMESPACE = "foss.pkp.LabeledGoodPDAEntityMapper.";

	/**
	 * 
	 * <p>插入一条记录</p> 
	 * @author foss-sunrui
	 * @date 2012-12-11 下午2:38:50
	 * @param record
	 * @return
	 * @see
	 */
	@Override
	public int insertSelective(LabeledGoodPDAEntity record) {
		record.setId(UUIDUtils.getUUID());
		return this.getSqlSession().insert(NAMESPACE + "insertSelective", record);
	}
	
	/**
	 * 批量插入PDA货签信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-17 下午4:30:50
	 */
	@Override
	public int insertBatch(List<LabeledGoodPDAEntity> list){
		for (LabeledGoodPDAEntity entity : list) {
    	    // 设置UUID
    	    entity.setId(UUIDUtils.getUUID());
    	}
		
		return this.getSqlSession().insert(NAMESPACE + "insertBatch", list);
	}
	
	/**
	 * 批量更新PDA货签信息
	 * @author 026123-foss-lifengteng
	 * @return 
	 * @date 2013-3-17 下午4:30:50
	 */
	@Override
	public int updateActiveBatch(List<LabeledGoodPDAEntity> list){
		return this.getSqlSession().update(NAMESPACE + "updateActiveBatch", list);
	}

	/**
	 * 
	 * <p>按主键查询</p> 
	 * @author foss-sunrui
	 * @date 2012-12-11 下午2:39:01
	 * @param id
	 * @return
	 * @see
	 */
	@Override
	public LabeledGoodPDAEntity queryByPrimaryKey(String id) {
		return (LabeledGoodPDAEntity) this.getSqlSession().selectOne(NAMESPACE + "selectByPrimaryKey", id);
	}
	
	/**
	 * 
	 * <p>按运单编号查询</p> 
	 * @author foss-sunrui
	 * @date 2012-12-11 下午2:39:01
	 * @param id
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LabeledGoodPDAEntity> queryByWaybillNo(String waybillNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("waybillNo", waybillNo);
		map.put("active", FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE + "selectByWaybillNo", map);
	}

	/**
	 * 
	 * <p>按主键更新</p> 
	 * @author foss-sunrui
	 * @date 2012-12-11 下午2:39:08
	 * @param record
	 * @return
	 * @see
	 */
	@Override
	public int updateByPrimaryKeySelective(LabeledGoodPDAEntity record) {
		return this.getSqlSession().update(NAMESPACE + "updateByPrimaryKeySelective", record);
	}

	/**
	 * 通过运单号更新记录状态
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-18 下午4:20:44
	 */
	@Override
	public int updateActiveByWaybillNo(String waybillNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("waybillNo", waybillNo);
		map.put("active", FossConstants.INACTIVE);
		return this.getSqlSession().update(NAMESPACE + "updateActiveByWaybillNo", map);
	}

}