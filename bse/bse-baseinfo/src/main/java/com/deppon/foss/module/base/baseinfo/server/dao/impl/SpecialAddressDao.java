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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/SpecialAddressDao.java
 * 
 * FILE NAME        	: SpecialAddressDao.java
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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISpecialAddressDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SpecialAddressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.SpecialAddressException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 
 * 特殊地址DAO接口处理类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:何波,date:2013-2-20 下午2:23:07 </p>
 * @author 何波
 * @date 2013-2-20 下午2:23:07
 * @since
 * @version
 */
public class SpecialAddressDao extends SqlSessionDaoSupport implements ISpecialAddressDao {

	 private static final String NAMESPACE = "foss.bse.bse-baseinfo.specialAddress.";
	 
	/**
	 * 
	 * <p>新增特殊地址</p> 
	 * @author 何波
	 * @date 2013-1-31 上午10:50:48
	 * @param specialAddress
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISpecialAddressDao#addSpecialAddress(com.deppon.foss.module.base.baseinfo.api.shared.domain.SpecialAddressEntity)
	 */
	@Override
	public int addSpecialAddress(SpecialAddressEntity specialAddress) {

		if (specialAddress == null) {
			return FossConstants.FAILURE;
		}
		specialAddress.setId(UUIDUtils.getUUID());
		specialAddress.setActive(FossConstants.ACTIVE);
		specialAddress.setCreateDate(new Date());
		specialAddress.setModifyDate(specialAddress.getCreateDate());
		specialAddress.setModifyUser(specialAddress.getCreateUser());
		int result = getSqlSession().insert(NAMESPACE+"addSpecialAddress", specialAddress);

		return  0 < result ? FossConstants.SUCCESS : FossConstants.FAILURE;
	}

	/**
	 * 
	 * <p>作废特殊地址</p> 
	 * @author 何波
	 * @date 2013-1-31 上午10:51:13
	 * @param specialAddress
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISpecialAddressDao#deleteSpecialAddress(com.deppon.foss.module.base.baseinfo.api.shared.domain.SpecialAddressEntity)
	 */
	@Override
	public int deleteSpecialAddress(SpecialAddressEntity specialAddress ) {
		if (specialAddress == null ) {
			return FossConstants.FAILURE;
		}
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("specialAddress", specialAddress);
		map.put("active", FossConstants.ACTIVE);
		int result = getSqlSession().update(NAMESPACE+"deleteSpecialAddress", map);
		
		return  0 < result ? FossConstants.SUCCESS : FossConstants.FAILURE;
	}

	/**
	 * 
	 * <p>修改特殊地址</p> 
	 * @author 何波
	 * @date 2013-1-31 上午10:52:48
	 * @param specialAddress
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISpecialAddressDao#updateSpecialAddress(com.deppon.foss.module.base.baseinfo.api.shared.domain.SpecialAddressEntity)
	 */
	@Override
	public int updateSpecialAddress(SpecialAddressEntity specialAddress) {
		
		if (specialAddress == null) {
			return FossConstants.FAILURE;
		}
		specialAddress.setModifyDate(new Date());
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("specialAddress", specialAddress);
		map.put("active", FossConstants.ACTIVE);
		int result = getSqlSession().update(NAMESPACE+"updateSpecialAddress", map);
		
		return  0 < result ? FossConstants.SUCCESS : FossConstants.FAILURE;
	}

	/**
	 * 
	 * <p>根据条件查询出特殊地址</p> 
	 * @author 何波
	 * @date 2013-2-20 下午2:10:31
	 * @param specialAddress
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISpecialAddressDao#querySpecialAddress(com.deppon.foss.module.base.baseinfo.api.shared.domain.SpecialAddressEntity)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SpecialAddressEntity> querySpecialAddress(
			SpecialAddressEntity specialAddress) {
		if (specialAddress == null) {
			return null;
		}
		
		specialAddress.setActive(FossConstants.ACTIVE);
		List<SpecialAddressEntity> specialAddresss = getSqlSession()
				.selectList(NAMESPACE+"querySpecialAddressList", specialAddress);
		return specialAddresss;
	}

	/**
	 * 
	 * <p>查询一条有效的特殊地址</p> 
	 * @author 何波
	 * @date 2013-2-18 下午5:34:46
	 * @param id
	 * @return
	 * @see
	 */
	@Override
	public SpecialAddressEntity querySpecialAddressBySelective(String id) {
		if (StringUtils.isBlank(id)) {
		    throw new  SpecialAddressException("ID 为空");
		}
		
		SpecialAddressEntity specialAddress = new SpecialAddressEntity();
		specialAddress.setId(id);
		List<SpecialAddressEntity> specialAddressList = querySpecialAddress(specialAddress);
		if (CollectionUtils.isEmpty(specialAddressList)) {
		    return null;
		}
		return specialAddressList.get(NumberConstants.NUMERAL_ZORE);
	}

	/**
	 * 
	 * <p>分页查询</p> 
	 * @author 何波
	 * @date 2013-2-19 下午5:27:48
	 * @param specialAddress
	 * @param offset
	 * @param limit
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISpecialAddressDao#querySpecialAddressListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.SpecialAddressEntity, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SpecialAddressEntity> querySpecialAddressListBySelectiveCondition(
			SpecialAddressEntity specialAddress, int offset, int limit) {
		specialAddress.setActive(FossConstants.ACTIVE);
		List<SpecialAddressEntity> specialAddressList = getSqlSession()
				.selectList(NAMESPACE+"querySpecialAddressList", specialAddress,new RowBounds(offset, limit));
		
        return specialAddressList;
	}

	/**
	 * 
	 * <p>查询满足条件的总条数</p> 
	 * @author 何波
	 * @date 2013-2-20 下午2:11:07
	 * @param specialAddress
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISpecialAddressDao#querySpecialAddressCountBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.SpecialAddressEntity)
	 */
	@Override
	public Long querySpecialAddressCountBySelectiveCondition(
			SpecialAddressEntity specialAddress) {
		long recordRount = 0;
		//设置为只查询“启用”的记录
		specialAddress.setActive(FossConstants.ACTIVE);
		Object result = getSqlSession().selectOne(
				NAMESPACE + "querySpecialAddressCountBySelectiveCondition",
				specialAddress);
		if (null != result) {
			recordRount = Long.parseLong(String.valueOf(result));
		}
		return recordRount;
	}

}
