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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/dao/impl/SMSTempleteDao.java
 * 
 * FILE NAME        	: SMSTempleteDao.java
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
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.common.api.server.dao.ISMSTempleteDao;
import com.deppon.foss.module.base.common.api.shared.domain.SMSTemplateEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 短信模版Dao接口实现类：提供对短信模版的增删改查的基本操作
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-12 下午5:07:53 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-12 下午5:07:53
 * @since
 * @version
 */
public class SMSTempleteDao extends iBatis3DaoImpl implements ISMSTempleteDao {
    
    private static final String NAMESPACE = "foss.bse.bse-common.smstemplate.";
    
    /**
     * 新增短信模版 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午5:07:53
     * @param entity
     * @return
     * @see
     */
    @Override
    public int addSMSTemplete(SMSTemplateEntity entity) {
	
    UserEntity user = (UserEntity)UserContext.getCurrentUser();
    String createUser = user.getEmployee().getEmpCode();
    entity.setCreateUser(createUser);
	entity.setId(UUIDUtils.getUUID());
	entity.setCreateDate(new Date());
	entity.setVirtualCode(entity.getId());
	entity.setActive(FossConstants.ACTIVE);
	
	return this.getSqlSession().insert(NAMESPACE + "insert", entity);
    }
    
    /**
     * 根据code作废短信模版信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午5:07:53
     * @param codes 这里是指虚拟Code
     * @return
     * @see
     */
    @Override
    public int deleteSMSTempleteByCode(String[] codes,String modifyUser) {
	
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", codes);
	map.put("modifyUser", modifyUser);
	map.put("modifyDate", new Date());
	map.put("active", FossConstants.INACTIVE);
	
	return this.getSqlSession().update(NAMESPACE + "deleteByCode", map);
    }
    
    /**
     * 修改短信模版信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午5:07:53
     * @param entity
     * @return
     * @see
     */
    @Override
    public int updateSMSTemplete(SMSTemplateEntity entity) {
	
    UserEntity user = (UserEntity)UserContext.getCurrentUser();
    String modifyUser = user.getEmployee().getEmpCode();
    entity.setModifyUser(modifyUser);
	entity.setModifyDate(new Date());
	entity.setActive(FossConstants.ACTIVE);
	return this.getSqlSession().update(NAMESPACE + "update", entity);
    }
    
    /**
     * 根据传入对象查询符合条件所有短信模版信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午5:07:53
     * @param entity
     * @param limit
     * @param start
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SMSTemplateEntity> querySMSTempletes(SMSTemplateEntity entity,
	    int limit, int start) {
	
	RowBounds rowBounds = new RowBounds(start, limit);
	
	return this.getSqlSession().selectList(NAMESPACE + "getAllInfos", entity,rowBounds);
    }
    
    /**
     * 统计总记录数 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午5:07:53
     * @param entity
     * @return
     * @see
     */
    @Override
    public Long queryRecordCount(SMSTemplateEntity entity) {
	
	return  (Long)this.getSqlSession().selectOne(NAMESPACE + "getCount", entity);
    }
    
    /**
     * <p>根据传入的参数查询符合条件的短信</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-16 下午4:41:37
     * @param smsCode 短信模板编码
     * @param smsId 模板ID
     * @return
     * @see com.deppon.foss.module.base.common.api.server.dao.ISMSTempleteDao#querySmsByParam(java.lang.String)
     */
    @Override
    public SMSTemplateEntity querySmsBySmsCode(String smsCode,String smsId) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("smsCode", smsCode);
	map.put("smsId", smsId);
	map.put("active", FossConstants.ACTIVE);
	
	return (SMSTemplateEntity)this.getSqlSession().selectOne(NAMESPACE +"querySmsBySmsCode", map);
    }
    
    /**
     * <p>通过短信模板编码查询短信模板</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-4-12 上午11:11:19
     * @param smsCode 短信模板编码
     * @return 
     * @see com.deppon.foss.module.base.common.api.server.dao.ISMSTempleteDao#querySmsByCode(java.lang.String)
     */
    @Override
    public SMSTemplateEntity querySmsByCode(String smsCode) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("smsCode", smsCode);
	map.put("smsId", null);
	map.put("active", FossConstants.ACTIVE);
	
	@SuppressWarnings("unchecked")
	List<SMSTemplateEntity> sMSTemplateEntityList =
			this.getSqlSession().selectList(NAMESPACE + "querySmsBySmsCode", map);
	if(sMSTemplateEntityList != null && sMSTemplateEntityList.size() > 0){
		return sMSTemplateEntityList.get(0);
	}else{
		return null;
	}
	
	/*return (SMSTemplateEntity)this.getSqlSession().selectList(NAMESPACE +"querySmsBySmsCode", map).get(0);*/
    }
    
    /**
     * <p>验证短信模板名称是否唯一</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-1-12 下午3:49:02
     * @param smsName 模板名称
     * @param smsId 模板ID
     * @return 
     * @see com.deppon.foss.module.base.common.api.server.dao.ISMSTempleteDao#querySmsTemplateByName(java.lang.String)
     */
    @Override
    public SMSTemplateEntity querySmsTemplateByName(String smsName,String smsId) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("smsName", smsName);
	map.put("smsId", smsId);
	map.put("active", FossConstants.ACTIVE);
	
	return (SMSTemplateEntity)this.getSqlSession().selectOne(NAMESPACE +"querySmsTemplateByName", map);
    }
    
    /**
     * <p>根据虚拟编码查询短信模板</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-4-12 下午3:46:20
     * @param virtualCode 虚拟编码
     * @return 
     * @see com.deppon.foss.module.base.common.api.server.dao.ISMSTempleteDao#querySmsByVirtualCode(java.lang.String)
     */
    @Override
    public SMSTemplateEntity querySmsByVirtualCode(String virtualCode) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("virtualCode", virtualCode);
	map.put("active", FossConstants.ACTIVE);
	
	return (SMSTemplateEntity)this.getSqlSession().selectOne(NAMESPACE + "querySmsByVirtualCode", map);
    }
    
}
