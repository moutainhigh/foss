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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/dao/impl/ReceiveAddressRfcDao.java
 * 
 * FILE NAME        	: ReceiveAddressRfcDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IReceiveAddressRfcDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ReceiveAddressRfcEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 
 * 运单地址临时表DAO
 * @author ibm-wangfei
 * @date Oct 19, 2012 3:21:18 PM
 */
public class ReceiveAddressRfcDao  extends iBatis3DaoImpl implements IReceiveAddressRfcDao {
	
	//运单地址临时表 name space
	private static final String NAMESPACE = "com.deppon.foss.module.pickup.predeliver.api.shared.domain.ReceiveAddressRfcEntity.";
	
	/**
	 * 
	 * 查询count
	 * @author ibm-wangfei
	 * @date Dec 3, 2012 6:29:17 PM
	 */
	@Override
	public Long queryReceiveAddressRfcCount(ReceiveAddressRfcEntity receiveAddressRfcEntity) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "selectCountByParam", receiveAddressRfcEntity);
	}
	
	/**
	 * 
	 * 查询
	 * @author ibm-wangfei
	 * @date Nov 30, 2012 4:08:47 PM
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<ReceiveAddressRfcEntity> queryReceiveAddressRfc(ReceiveAddressRfcEntity receiveAddressRfcEntity) {
		return this.getSqlSession().selectList(NAMESPACE + "selectByParam", receiveAddressRfcEntity);
	}
	
	/**
	 * 更新
	 * @author ibm-wangfei
	 * @date Nov 30, 2012 3:37:04 PM
	 */
	@Override
	public int updateReceiveAddressRfcEntity(ReceiveAddressRfcEntity receiveAddressRfcEntity) {
		receiveAddressRfcEntity.setJobId(UUIDUtils.getUUID());
		return this.getSqlSession().update(NAMESPACE + "updateByParams", receiveAddressRfcEntity);
	}

	/**
	 * 
	 * 删除
	 * @author ibm-wangfei
	 * @date Nov 30, 2012 3:37:19 PM
	 */
	@Override
	public int deleteReceiveAddressRfcEntity(ReceiveAddressRfcEntity receiveAddressRfcEntity) {
		return this.getSqlSession().update(NAMESPACE + "deleteByPrimaryKey", receiveAddressRfcEntity);
	}
	
	/**
	 * 
	 * 新增
	 * @author ibm-wangfei
	 * @date Nov 30, 2012 3:37:19 PM
	 */
	@Override
	public int addReceiveAddressRfcEntity(ReceiveAddressRfcEntity receiveAddressRfcEntity) {
		// 设置UUID
		receiveAddressRfcEntity.setId(UUIDUtils.getUUID());
		return this.getSqlSession().update(NAMESPACE + "insertSelective", receiveAddressRfcEntity);
	}

	/**
	 * 
	 * 查询集中送货小区
	 * @param scopeCoordinatesId
	 * @return
	 * @author ibm-wangfei
	 * @date May 29, 2013 6:45:29 PM
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryRegionCodeForGisId(String gisid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("regionType", DictionaryValueConstants.REGION_TYPE_DE);
		map.put("gisid", gisid);
		map.put("active", FossConstants.ACTIVE);
		return (String)this.getSqlSession().selectOne(NAMESPACE + "queryRegionCodeForGisId", map);
	}
}