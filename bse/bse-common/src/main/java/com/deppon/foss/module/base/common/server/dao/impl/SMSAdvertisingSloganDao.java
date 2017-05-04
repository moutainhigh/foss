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
 * PROJECT NAME	: bse-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/dao/impl/SMSAdvertisingSloganDao.java
 * 
 * FILE NAME        	: SMSAdvertisingSloganDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.base.common.api.server.dao.ISMSAdvertisingSloganDao;
import com.deppon.foss.module.base.common.api.shared.domain.SMSSloganEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 短信广告语Dao接口实现类：提供对短信广告语的增删改查的基本操作
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-12 下午5:23:12 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-12 下午5:23:12
 * @since
 * @version
 */
public class SMSAdvertisingSloganDao extends iBatis3DaoImpl implements
	ISMSAdvertisingSloganDao {
    
    private static final String NAMESPACE = "foss.bse.bse-common.smsSlogan.";
    
    /**
     * 新增短信广告语 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午5:23:12
     * @param entity
     * @return
     * @see
     */
    @Override
    public int addSMSAdvertisingSlogan(SMSSloganEntity entity) {
	
	entity.setId(UUIDUtils.getUUID());
	entity.setCreateDate(new Date());
	entity.setVirtualCode(entity.getId());
	entity.setActive(FossConstants.ACTIVE);
	
	return this.getSqlSession().insert(NAMESPACE + "insert", entity);
    }
    
    /**
     * 根据code作废短信广告语信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午5:23:12
     * @param codes
     * @return
     * @see
     */
    @Override
    public int deleteSMSAdvertisingSloganByCode(String[] codes,String modifyUser) {
	
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", codes);
	map.put("modifyUser", modifyUser);
	map.put("modifyDate", new Date());
	map.put("active", FossConstants.INACTIVE);
	
	return this.getSqlSession().update(NAMESPACE + "deleteByCode", map);
    }
    
    /**
     * 修改短信广告语信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午5:23:12
     * @param entity
     * @return
     * @see
     */
    @Override
    public int updateSMSAdvertisingSlogan(SMSSloganEntity entity) {
	
	entity.setModifyDate(new Date());
	return this.getSqlSession().update(NAMESPACE + "update", entity);
    }
    
    /**
     * 根据传入对象查询符合条件所有短信广告语信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午5:23:12
     * @param entity
     * @param limit
     * @param start
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SMSSloganEntity> querySMSAdvertisingSlogans(
	    SMSSloganEntity entity, int limit, int start) {
	
	RowBounds rowBounds = new RowBounds(start, limit);
	
	return this.getSqlSession().selectList(NAMESPACE + "getAllInfos", entity,rowBounds);
    }
    
    /**
     * 统计总记录数 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午5:23:12
     * @param entity
     * @return
     * @see
     */
    @Override
    public Long queryRecordCount(SMSSloganEntity entity) {
	
	return  (Long)this.getSqlSession().selectOne(NAMESPACE + "getCount", entity);
    }
    
    /**
     * <p>根据短信广告语代码查询短信广告语</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-1-10 下午2:55:12
     * @param smsSloganCode 短信广告语代码
     * @return 
     * @see com.deppon.foss.module.base.common.api.server.dao.ISMSAdvertisingSloganDao#querySmsSloganBySmsSloganCode(java.lang.String)
     */
    @Override
    public SMSSloganEntity querySmsSloganBySmsSloganCode(String smsSloganCode,String smsId) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("smsSloganCode", smsSloganCode);
	map.put("smsId", smsId);
	map.put("active", FossConstants.ACTIVE);
	
	return (SMSSloganEntity)this.getSqlSession().selectOne(NAMESPACE + "querySmsSloganBySmsSloganCode", map);
    }
    
    /**
     * <p>根据短信广告语名称查询短信广告语</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-1-10 下午2:55:12
     * @param sloganName 短信广告语名称
     * @return 
     * @see com.deppon.foss.module.base.common.api.server.dao.ISMSAdvertisingSloganDao#querySmsSloganBySmsSloganName(java.lang.String)
     */
    @Override
    public SMSSloganEntity querySmsSloganBySmsSloganName(String sloganName,String smsId) {
	
	Map<String, String> map = new HashMap<String, String>();
	map.put("sloganName", sloganName);
	map.put("smsId", smsId);
	map.put("active", FossConstants.ACTIVE);
	
	return (SMSSloganEntity)this.getSqlSession().selectOne(NAMESPACE + "querySmsSloganBySmsSloganName", map);
    }
    

}
