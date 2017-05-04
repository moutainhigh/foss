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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/dao/impl/WaybillAcHisPdaDao.java
 * 
 * FILE NAME        	: WaybillAcHisPdaDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillAcHisPdaDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillAcHisPdaEntity;
import com.deppon.foss.util.UUIDUtils;

/**
 * PDA操作历史记录dao
 * @author 026123-foss-lifengteng
 * @date 2012-12-10 下午4:55:55
 */
public class WaybillAcHisPdaDao extends iBatis3DaoImpl implements IWaybillAcHisPdaDao{
	private static final String NAME_SPACE = "foss.pkp.WaybillAcHisPdaEntityMapper.";
    /**
     * 插入操作历史记录
     * @author 026123-foss-lifengteng
     * @date 2012-12-10 下午4:56:17
     */
	@Override
    public int insert(WaybillAcHisPdaEntity record){
		record.setId(UUIDUtils.getUUID());
    	return this.getSqlSession().insert(NAME_SPACE + "insert", record);
    }

    /**
     * 有选择的插入操作历史记录
     * @author 026123-foss-lifengteng
     * @date 2012-12-10 下午4:56:20
     */
	@Override
    public int insertSelective(WaybillAcHisPdaEntity record){
    	return this.getSqlSession().insert(NAME_SPACE + "insertSelective", record);
    }
    /**
     * 根据ID插入操作历史记录
     * @author 026123-foss-lifengteng
     * @date 2012-12-10 下午4:56:22
     */
	@Override
    public WaybillAcHisPdaEntity selectByPrimaryKey(String id){
    	return (WaybillAcHisPdaEntity) this.getSqlSession().selectOne(NAME_SPACE + "selectByPrimaryKey", id);
    }
    
	/**
	 * 根据id删除运单PDA操作记录
	 * @author 043260-foss-suyujun
	 * @date 2012-12-11
	 * @param id
	 * @return
	 * @@see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillAcHisPdaDao#deleteByPrimaryKey(java.lang.String)
	 */
	@Override
    public int deleteByPrimaryKey(String id){
    	return this.getSqlSession().delete(NAME_SPACE + "deleteByPrimaryKey", id);
    }

	/**
	 * 更新运单
	 * @author 043260-foss-suyujun
	 * @date 2012-12-11
	 * @param entity
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillAcHisPdaDao#updateWaybillAcHisPdaEntity(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillAcHisPdaEntity)
	 */
	@Override
	public int updateWaybillAcHisPdaEntity(WaybillAcHisPdaEntity entity) {
		return this.getSqlSession().update(NAME_SPACE + "updateByPrimaryKeySelective", entity);
	}
}