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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/CusAddressDao.java
 * 
 * FILE NAME        	: CusAddressDao.java
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

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICusAddressDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAddressEntity;
import com.deppon.foss.util.define.FossConstants;


/**
 * 客户接送货地址DAO接口实现
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-21 上午9:38:23 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-21 上午9:38:23
 * @since
 * @version
 */
public class CusAddressDao extends SqlSessionDaoSupport implements ICusAddressDao {
    
    private static final String NAMESPACE = "foss.bse.bse-baseinfo.cusAddress.";

    /** 
     * 新增客户接送货地址
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-11-21 上午9:08:09
     * @param entity
     *            客户接送货地址实体
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICusAddressDao#addCusAddress(com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAddressEntity)
     */
    @Override
    public int addCusAddress(CusAddressEntity entity) {
	
	return this.getSqlSession().insert(NAMESPACE + "insert", entity);
    }

    /** 
     * 根据code作废客户接送货地址
     * 
     * @author dp-xieyantao
     * @date 2012-11-21 上午9:08:09
     * @param crmId
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICusAddressDao#deleteCusAddressByCode(java.lang.String, java.lang.String)
     */
    @Override
    public int deleteCusAddressByCode(BigDecimal crmId, String modifyUser) {
	
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("code", crmId);
	map.put("modifyUser", modifyUser);
	map.put("modifyDate", new Date());
	map.put("active", FossConstants.INACTIVE);
	map.put("active0", FossConstants.ACTIVE);
	
	return this.getSqlSession().update(NAMESPACE + "deleteByCode", map);
    }

    /** 
     * 修改客户接送货地址
     * @author dp-xieyantao
     * @date 2012-11-21 上午9:08:09
     * @param entity
     *            客户接送货地址实体
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICusAddressDao#updateCusAddress(com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAddressEntity)
     */
    @Override
    public int updateCusAddress(CusAddressEntity entity) {
	
	return this.getSqlSession().update(NAMESPACE + "update", entity);
    }
    
    /**
     * <p>根据crmId,最后一次修改时间查询客户接送货地址是否存在</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-26 下午3:02:51
     * @param crmId
     * @param lastupdatetime
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICusAddressDao#queryCusAddressByCrmId(java.math.BigDecimal, java.util.Date)
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean queryCusAddressByCrmId(BigDecimal crmId, Date lastupdatetime) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("modifyDate", lastupdatetime);
	map.put("crmId", crmId);
	map.put("active", null);
	
	List<CusAddressEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryCusAddressByCrmId", map);
	return CollectionUtils.isNotEmpty(list);
    }

    /**
     * <p>根据ownCustId查询客户接送货地址</p> 
     * @author css 
     * @date 2015-07-21 19:15:29
     * @param ownCustId    
     * @return
     * @see
     */
    @Override
	public CusAddressEntity queryCusAddressByOwnCustId(String ownCustId) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("ownCustId", ownCustId);		
		List<CusAddressEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryCusAddressByCustfossid", map);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
    /**
     * 修改客户接送货地址
     * @author css 
     * @date 2015-07-21 19:25:29
     * @param ownCustId    
     * @return
     * @see
     */
	@Override
	public int updateCusAddressByCusfossid(CusAddressEntity entity) {
		return this.getSqlSession().update(NAMESPACE + "updateCusAddressByCusfossid", entity);
	}

}
