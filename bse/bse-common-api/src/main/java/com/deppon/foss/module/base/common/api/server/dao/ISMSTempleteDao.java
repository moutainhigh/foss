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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/server/dao/ISMSTempleteDao.java
 * 
 * FILE NAME        	: ISMSTempleteDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.common.api.shared.domain.SMSTemplateEntity;
/**
 * 短信模版Dao接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-11 下午2:34:35 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-11 下午3:59:49
 * @since
 * @version
 */
public interface ISMSTempleteDao {
    
    /**
     * 新增短信模版 
     * @author 094463-foss-xieyantao
     * @date 2012-10-11 下午3:59:49
     * @param entity
     * @return
     * @see
     */
    int addSMSTemplete(SMSTemplateEntity entity) ;
    
    /**
     * 根据code作废短信模版信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-11 下午3:59:49
     * @param codes 虚拟编码
     * @return
     * @see
     */
    int deleteSMSTempleteByCode(String[] codes,String modifyUser);
    
    /**
     * 修改短信模版信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-11 下午3:59:49
     * @param entity
     * @return
     * @see
     */
    int updateSMSTemplete(SMSTemplateEntity entity);
    
    /**
     * 根据传入对象查询符合条件所有短信模版信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-11 下午3:59:49
     * @param entity
     * @param limit
     * @param start
     * @return
     * @see
     */
    List<SMSTemplateEntity> querySMSTempletes(SMSTemplateEntity entity,int limit,int start);
    
    /**
     * 统计总记录数 
     * @author 094463-foss-xieyantao
     * @date 2012-10-11 下午3:59:49
     * @param entity
     * @return
     * @see
     */
    Long queryRecordCount(SMSTemplateEntity entity);
    
    /**
     * <p>根据传入的参数查询符合条件的短信</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-16 下午4:41:37
     * @param smsCode
     * @param smsId 模板ID
     * @return
     * @see
     */
    SMSTemplateEntity querySmsBySmsCode(String smsCode,String smsId);
    
    /**
     * <p>通过短信模板编码查询短信模板</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-4-12 上午11:11:19
     * @param smsCode 短信模板编码
     * @return
     * @see
     */
    SMSTemplateEntity querySmsByCode(String smsCode);
    
    /**
     * <p>根据虚拟编码查询短信模板</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-4-12 下午3:46:20
     * @param virtualCode 虚拟编码
     * @return
     * @see
     */
    SMSTemplateEntity querySmsByVirtualCode(String virtualCode);
    
    /**
     * <p>验证短信模板名称是否唯一</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-1-12 下午3:49:02
     * @param smsName 模板名称
     * @param smsId 模板ID
     * @return
     * @see
     */
    SMSTemplateEntity querySmsTemplateByName(String smsName,String smsId);
    
    
    
    
}
