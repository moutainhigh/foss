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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/dao/impl/WoodenRequirementsDao.java
 * 
 * FILE NAME        	: WoodenRequirementsDao.java
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
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWoodenRequirementsDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirementsEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LastWaybillRfcQueryDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 代打木架，木箱数据持久层
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-sunrui,date:2012-11-5 上午11:07:06,content:TODO
 * </p>
 * 
 * @author foss-sunrui
 * @date 2012-11-5 上午11:07:06
 * @since
 * @version
 */
public class WoodenRequirementsDao extends iBatis3DaoImpl implements IWoodenRequirementsDao {

    private static final String NAMESPACE = "foss.pkp.WoodenRequirementsEntityMapper.";

    /**
     * 新增代打木架实体信息
     * 
     * @author 026123-foss-lifengteng
     * @date 2012-11-5 下午5:50:17
     * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWoodenRequirementsDao#addWoodenRequirementsEntity(com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirementsEntity)
     */
    @Override
    public int addWoodenRequirementsEntity(WoodenRequirementsEntity wooden) {
	// 设置UUID
	wooden.setId(UUIDUtils.getUUID());
	return this.getSqlSession().insert(NAMESPACE + "insert", wooden);
    }

    /**
     * 根据运单号查询代打木架实体信息
     * 
     * @author 026123-foss-lifengteng
     * @date 2012-11-5 下午5:53:34
     * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWoodenRequirementsDao#queryWoodenByNo(java.lang.String)
     */
    @Override
    public WoodenRequirementsEntity queryWoodenByNo(String waybillNo) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("waybillNo", waybillNo);
    	map.put("active", FossConstants.ACTIVE);
    	return (WoodenRequirementsEntity) this.getSqlSession().selectOne(NAMESPACE + "selectByWaybillNo", map);
    }

    /**
     * 根据运单号查询代打木架实体信息
     * 
     * @author 280747 zhuxue
     * @date 2012-11-5 下午5:53:34
     * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWoodenRequirementsDao#queryWoodenByNo(java.lang.String)
     */
    @Override
    public WoodenRequirementsEntity queryNewWoodenByNo(String waybillNo) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("waybillNo", waybillNo);
    	map.put("active", FossConstants.ACTIVE);
    	return (WoodenRequirementsEntity) this.getSqlSession().selectOne(NAMESPACE + "selectWoodWaybillNo", map);
    }
    
    /**
	 * 
	 * <p>
	 * 新版运单代打木架信息<br />
	 * </p>
	 * @author suyujun
	 * @version 0.1 2012-12-1
	 * @param waybillNo
	 * @return
	 * WoodenRequirementsEntity
	 */
	public WoodenRequirementsEntity queryNewWoodenRequirements(LastWaybillRfcQueryDto queryDto) {
		List<WoodenRequirementsEntity> list = this.getSqlSession()
			.selectList(NAMESPACE + "queryNewWoodenRequirements",queryDto);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	/**
	 * 更新打木架信息
	 * @author 043260-foss-suyujun
	 * @date 2012-12-24
	 * @param woodRequirements
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWoodenRequirementsDao#updateWoodenRequirements(com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirementsEntity)
	 */
	@Override
	public void updateWoodenRequirements(
			WoodenRequirementsEntity woodRequirements) {
		this.getSqlSession().update(NAMESPACE + "updateWoodenRequirements", woodRequirements);
	}
	
	/**
	 * 更新打木架信息
	 * @author 043260-foss-suyujun
	 * @date 2012-12-24
	 * @param woodRequirements
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWoodenRequirementsDao#updateWoodenRequirements(com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirementsEntity)
	 */
	@Override
	public void updateAllOtherWoodenRequirementsToNo(
			WoodenRequirementsEntity woodRequirements) {
		this.getSqlSession().update(NAMESPACE + "updateAllOtherWoodenRequirementsToNo", woodRequirements);
	}
	
	

	/**
	 * 删除打木架
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWoodenRequirementsDao#deleteWoodRequirements(java.lang.String)
	 */
	@Override
	public int deleteWoodRequirementsById(String id) {
		return this.getSqlSession().delete(NAMESPACE + "deleteWoodRequirementsById", id);
	}

	/**
	 * 根据code查询运输性质
	 */
	@Override
	public String getProductByCacheCode(String code) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
    	map.put("code", code);
    	map.put("active", FossConstants.ACTIVE);
		return (String) this.getSqlSession().selectOne(NAMESPACE + "selectProductByCacheCode", map);
	}

}