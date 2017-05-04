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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/server/service/ISMSTempleteService.java
 * 
 * FILE NAME        	: ISMSTempleteService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.common.api.shared.domain.SMSTemplateEntity;
import com.deppon.foss.module.base.common.api.shared.dto.SmsParamDto;

/**
 * 短信模板service接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-11 下午5:03:08 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-11 下午5:03:08
 * @since
 * @version
 */
public interface ISMSTempleteService extends IService {
    
    /**
     * 新增短信模版 
     * @author 094463-foss-xieyantao
     * @date 2012-10-11 下午5:03:08
     * @param entity
     * @return
     * @see
     */
    int addSMSTemplete(SMSTemplateEntity entity) ;
    
    /**
     * 根据code作废短信模版信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-11 下午5:03:08
     * @param codeStr 虚拟编码拼接的字符串
     * @return
     * @see
     */
    int deleteSMSTempleteByCode(String codeStr,String modifyUser);
    
    /**
     * 修改短信模版信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-11 下午5:03:08
     * @param entity
     * @return
     * @see
     */
    int updateSMSTemplete(SMSTemplateEntity entity);
    
    /**
     * <p>验证短信模板编码是否唯一</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-1-12 下午3:49:02
     * @param smsCode 模板编码
     * @param smsId 模板ID
     * @return
     * @see
     */
    SMSTemplateEntity querySmsTemplateByCode(String smsCode,String smsId);
    
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
    
    /**
     * 根据传入对象查询符合条件所有短信模版信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-11 下午5:03:08
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
     * @date 2012-10-11 下午5:03:08
     * @param entity
     * @return
     * @see
     */
    Long queryRecordCount(SMSTemplateEntity entity);
    
    /**
     * ---------------------------以下对外接口------------------------------------
     */
    /**
     * <p>根据传入的参数查询符合条件的短信</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-16 下午4:41:37
     * @param dto
     * @return
     * @see
     */
    String querySmsByParam(SmsParamDto dto);
    
    

}
