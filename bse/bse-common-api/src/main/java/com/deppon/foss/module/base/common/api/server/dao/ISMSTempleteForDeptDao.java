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
 * PROJECT NAME	: bse-common-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/server/dao/ISMSTempleteForDeptDao.java
 * 
 * FILE NAME        	: ISMSTempleteForDeptDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.common.api.shared.domain.TemplateAppOrgEntity;

/**
 * 部门短信模版Dao接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-11 下午2:34:35 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-11 下午4:04:11
 * @since
 * @version
 */
public interface ISMSTempleteForDeptDao {
    
    /**
     * 新增部门短信模版
     * @author 094463-foss-xieyantao
     * @date 2012-10-11 下午4:04:11
     * @param entity
     * @return
     * @see
     */
    int addSMSTempleteForDept(TemplateAppOrgEntity entity) ;
    
    /**
     * 根据code作废部门短信模版信息
     * @author 094463-foss-xieyantao
     * @date 2012-10-11 下午4:04:11
     * @param codes id拼接的字符串
     * @return
     * @see
     */
    int deleteSMSTempleteForDeptByCode(String[] codes,String modifyUser);
    
    /**
     * 修改部门短信模版信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-11 下午4:04:11
     * @param entity
     * @return
     * @see
     */
    int updateSMSTempleteForDept(TemplateAppOrgEntity entity);
    
    /**
     * 根据传入对象查询符合条件所有部门短信模版信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-11 下午4:04:11
     * @param entity
     * @return
     * @see
     */
    List<TemplateAppOrgEntity> querySMSTempleteForDepts(TemplateAppOrgEntity entity);
    
    /**
     * 统计总记录数 
     * @author 094463-foss-xieyantao
     * @date 2012-10-11 下午4:04:11
     * @param entity
     * @return
     * @see
     */
    Long queryRecordCount(TemplateAppOrgEntity entity);
    
    /**
     * <p>通过id查询部门短信模板信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-4-13 上午9:40:15
     * @param id id
     * @return
     * @see
     */
    TemplateAppOrgEntity queryAppOrgEntityById(String id);
    
    /**
     * <p>根据短信的虚拟编码、部门编码查询部门短信信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-17 上午10:40:25
     * @param orgCode 部门编码
     * @param smsVirtualCode 短信虚拟编码
     * @param appOrgId 部门短信模板ID
     * @return
     * @see
     */
    TemplateAppOrgEntity queryAppOrgSmsByParams(String orgCode,String smsVirtualCode,String appOrgId);
    
    /**
     * <p>根据短信模板虚拟编码、部门编码查询部门短信模板</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-4-12 下午5:54:12
     * @param orgCode 部门编码
     * @param smsVirtualCode 短信模板虚拟编码
     * @return
     * @see
     */
    TemplateAppOrgEntity queryAppOrgByVirtualCodeAndOrgCode(String orgCode,String smsVirtualCode);
}
