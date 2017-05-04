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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/dao/impl/SMSAdvertisingSloganForDeptDao.java
 * 
 * FILE NAME        	: SMSAdvertisingSloganForDeptDao.java
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
import com.deppon.foss.module.base.common.api.server.dao.ISMSAdvertisingSloganForDeptDao;
import com.deppon.foss.module.base.common.api.shared.domain.SloganAppOrgEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 部门短信广告语Dao接口实现类：提供对部门短信广告语的增删改查的基本操作
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-12 下午5:31:49 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-12 下午5:31:49
 * @since
 * @version
 */
public class SMSAdvertisingSloganForDeptDao extends iBatis3DaoImpl implements
	ISMSAdvertisingSloganForDeptDao {
    
    private static final String NAMESPACE = "foss.bse.bse-common.sloganAppOrg.";
    
    /**
     * 新增部门短信广告语 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午5:31:49
     * @param entity
     * @return
     * @see
     */
    @Override
    public int addSMSAdvertisingSloganForDept(SloganAppOrgEntity entity) {
	
	entity.setId(UUIDUtils.getUUID());
	entity.setCreateDate(new Date());
	
	return this.getSqlSession().insert(NAMESPACE + "insert", entity);
    }
    
    /**
     * 根据code作废部门短信广告语信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午5:31:49
     * @param codes 这里是ID拼接的字符串
     * @return
     * @see
     */
    @Override
    public int deleteSMSAdvertisingSloganForDeptByCode(String[] codes,String modifyUser) {
	
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
     * @date 2012-10-12 下午5:31:49
     * @param entity
     * @return
     * @see
     */
    @Override
    public int updateSMSAdvertisingSloganForDept(SloganAppOrgEntity entity) {
	
	entity.setModifyDate(new Date());
	return this.getSqlSession().update(NAMESPACE + "update", entity);
    }
    
    /**
     * 根据传入对象查询符合条件所有部门短信广告语信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午5:31:49
     * @param entity
     * @param limit
     * @param start
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SloganAppOrgEntity> querySMSAdvertisingSloganForDepts(
	    SloganAppOrgEntity entity, int limit, int start) {
	
	RowBounds rowBounds = new RowBounds(start, limit);
	
	return this.getSqlSession().selectList(NAMESPACE + "getAllInfos", entity,rowBounds);
    }
    
    /**
     * 统计总记录数 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午5:31:49
     * @param entity
     * @return
     * @see
     */
    @Override
    public Long queryRecordCount(SloganAppOrgEntity entity) {
	
	return  (Long)this.getSqlSession().selectOne(NAMESPACE + "getCount", entity);
    }
    
    /**
     * <p>根据短信广告语虚拟编码、部门编码查询部门短信广告语</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-1-10 下午2:55:12
     * @param orgCode 部门编码
     * @param smsSloganVirtualCode 短信广告语虚拟编码
     * @return 
     * @see com.deppon.foss.module.base.common.api.server.dao.ISMSAdvertisingSloganForDeptDao#querySloganAppOrgByCode(java.lang.String, java.lang.String)
     */
    @Override
    public SloganAppOrgEntity querySloganAppOrgByCode(String orgCode,
	    String smsSloganVirtualCode,String sloganAppOrgId) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("orgCode", orgCode);
	map.put("smsSloganVirtualCode", smsSloganVirtualCode);
	map.put("active", FossConstants.ACTIVE);
	map.put("sloganAppOrgId", sloganAppOrgId);
	
	return (SloganAppOrgEntity)this.getSqlSession().selectOne(NAMESPACE+"querySloganAppOrgByCode", map);
    }

}
