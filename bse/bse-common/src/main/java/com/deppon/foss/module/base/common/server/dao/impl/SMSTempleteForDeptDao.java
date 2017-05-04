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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/dao/impl/SMSTempleteForDeptDao.java
 * 
 * FILE NAME        	: SMSTempleteForDeptDao.java
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

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.base.common.api.server.dao.ISMSTempleteForDeptDao;
import com.deppon.foss.module.base.common.api.shared.domain.TemplateAppOrgEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 部门短信模版Dao接口实现类：提供对部门短信模版的增删改查的基本操作
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-12 下午5:15:52 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-12 下午5:15:52
 * @since
 * @version
 */
public class SMSTempleteForDeptDao extends iBatis3DaoImpl implements
	ISMSTempleteForDeptDao {
    
    private static final String NAMESPACE = "foss.bse.bse-common.templateAppOrg.";
    
    /**
     * 新增部门短信模版 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午5:15:52
     * @param entity
     * @return
     * @see
     */
    @Override
    public int addSMSTempleteForDept(TemplateAppOrgEntity entity) {
	
	return this.getSqlSession().insert(NAMESPACE + "insert", entity);
    }
    
    /**
     * 根据code作废部门短信模版信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午5:15:52
     * @param codes 这里是Id拼接的字符串
     * @return
     * @see
     */
    @Override
    public int deleteSMSTempleteForDeptByCode(String[] codes,String modifyUser) {
	
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", codes);
	map.put("modifyUser", modifyUser);
	map.put("modifyDate", new Date());
	map.put("active", FossConstants.INACTIVE);
	
	return this.getSqlSession().update(NAMESPACE + "deleteByCode", map);
    }
    
    /**
     * 修改部门短信模版信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午5:15:52
     * @param entity
     * @return
     * @see
     */
    @Override
    public int updateSMSTempleteForDept(TemplateAppOrgEntity entity) {
	
	entity.setModifyDate(new Date());
	return this.getSqlSession().update(NAMESPACE + "update", entity);
    }
    
    /**
     * 根据传入对象查询符合条件所有部门短信模版信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午5:15:52
     * @param entity
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<TemplateAppOrgEntity> querySMSTempleteForDepts(
	    TemplateAppOrgEntity entity) {
	
	return this.getSqlSession().selectList(NAMESPACE + "getAllInfos", entity);
    }
    
    /**
     * 统计总记录数 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午5:15:52
     * @param entity
     * @return
     * @see
     */
    @Override
    public Long queryRecordCount(TemplateAppOrgEntity entity) {
	
	return  (Long)this.getSqlSession().selectOne(NAMESPACE + "getCount", entity);
    }
    
    /**
     * <p>根据短信的虚拟编码、部门编码查询部门短信信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-17 上午10:40:25
     * @param orgCode 部门编码
     * @param smsVirtualCode 短信虚拟编码
     * @param appOrgId 部门短信模板ID
     * @return 
     * @see com.deppon.foss.module.base.common.api.server.dao.ISMSTempleteForDeptDao#queryAppOrgSmsByParams(java.lang.String, java.lang.String)
     */
    @Override
    public TemplateAppOrgEntity queryAppOrgSmsByParams(String orgCode,
	    String smsVirtualCode,String appOrgId) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("orgCode",orgCode);
	map.put("smsVirtualCode",smsVirtualCode);
	map.put("active", FossConstants.ACTIVE);
	map.put("appOrgId", appOrgId);
	
	return (TemplateAppOrgEntity)this.getSqlSession().selectOne(NAMESPACE + "queryAppOrgSmsByParams",map);
    }
    
    /**
     * <p>根据短信模板虚拟编码、部门编码查询部门短信模板</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-4-12 下午5:54:12
     * @param orgCode 部门编码
     * @param smsVirtualCode 短信模板虚拟编码
     * @return 
     * @see com.deppon.foss.module.base.common.api.server.dao.ISMSTempleteForDeptDao#queryAppOrgByVirtualCodeAndOrgCode(java.lang.String, java.lang.String)
     */
    @Override
    public TemplateAppOrgEntity queryAppOrgByVirtualCodeAndOrgCode(
	    String orgCode, String smsVirtualCode) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("orgCode", orgCode);
	map.put("smsVirtualCode", smsVirtualCode);
	map.put("active", FossConstants.ACTIVE);
	map.put("appOrgId", null);
	
	return (TemplateAppOrgEntity)this.getSqlSession().selectOne(NAMESPACE + "queryAppOrgSmsByParams",map);
    }
    
    /**
     * <p>通过id查询部门短信模板信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-4-13 上午9:40:15
     * @param id id
     * @return 
     * @see com.deppon.foss.module.base.common.api.server.dao.ISMSTempleteForDeptDao#queryAppOrgEntityById(java.lang.String)
     */
    @Override
    public TemplateAppOrgEntity queryAppOrgEntityById(String id) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("id", id);
	map.put("active", FossConstants.ACTIVE);
	
	return (TemplateAppOrgEntity)this.getSqlSession().selectOne(NAMESPACE + "queryAppOrgEntityById", map);
    }


}
