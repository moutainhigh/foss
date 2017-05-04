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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/shared/vo/SMSTemplateVo.java
 * 
 * FILE NAME        	: SMSTemplateVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.common.api.shared.domain.SMSTemplateEntity;
import com.deppon.foss.module.base.common.api.shared.domain.TemplateAppOrgEntity;

/**
 * 短信模板VO
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-10-11 下午6:15:07
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-10-12 上午9:25:20
 * @since
 * @version
 */
public class SMSTemplateVo implements Serializable {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 7385259253943680333L;

    /**
     * 短信模板实体类.
     */
    private SMSTemplateEntity smsTemplateEntity;

    /**
     * 短信模板信息集合.
     */
    private List<SMSTemplateEntity> smsTemplateEntityList;

    /**
     * 部门短信模板实体类.
     */
    private TemplateAppOrgEntity templateAppOrgEntity;

    /**
     * 部门短信模板集合.
     */
    private List<TemplateAppOrgEntity> templateAppOrgEntityList;

    /**
     * 实体中对应virtualCode字段，字段间以逗号分隔[支持批量删除].
     */
    private String codeStr;

    /**
     * 返回前台的INT类型属性.
     */
    private int returnInt;

    
    /**
     * 获取 短信模板实体类.
     *
     * @return  the smsTemplateEntity
     */
    public SMSTemplateEntity getSmsTemplateEntity() {
        return smsTemplateEntity;
    }

    
    /**
     * 设置 短信模板实体类.
     *
     * @param smsTemplateEntity the smsTemplateEntity to set
     */
    public void setSmsTemplateEntity(SMSTemplateEntity smsTemplateEntity) {
        this.smsTemplateEntity = smsTemplateEntity;
    }

    
    /**
     * 获取 短信模板信息集合.
     *
     * @return  the smsTemplateEntityList
     */
    public List<SMSTemplateEntity> getSmsTemplateEntityList() {
        return smsTemplateEntityList;
    }

    
    /**
     * 设置 短信模板信息集合.
     *
     * @param smsTemplateEntityList the smsTemplateEntityList to set
     */
    public void setSmsTemplateEntityList(
    	List<SMSTemplateEntity> smsTemplateEntityList) {
        this.smsTemplateEntityList = smsTemplateEntityList;
    }

    
    /**
     * 获取 部门短信模板实体类.
     *
     * @return  the templateAppOrgEntity
     */
    public TemplateAppOrgEntity getTemplateAppOrgEntity() {
        return templateAppOrgEntity;
    }

    
    /**
     * 设置 部门短信模板实体类.
     *
     * @param templateAppOrgEntity the templateAppOrgEntity to set
     */
    public void setTemplateAppOrgEntity(TemplateAppOrgEntity templateAppOrgEntity) {
        this.templateAppOrgEntity = templateAppOrgEntity;
    }

    
    /**
     * 获取 部门短信模板集合.
     *
     * @return  the templateAppOrgEntityList
     */
    public List<TemplateAppOrgEntity> getTemplateAppOrgEntityList() {
        return templateAppOrgEntityList;
    }

    
    /**
     * 设置 部门短信模板集合.
     *
     * @param templateAppOrgEntityList the templateAppOrgEntityList to set
     */
    public void setTemplateAppOrgEntityList(
    	List<TemplateAppOrgEntity> templateAppOrgEntityList) {
        this.templateAppOrgEntityList = templateAppOrgEntityList;
    }

    
    /**
     * 获取 实体中对应virtualCode字段，字段间以逗号分隔[支持批量删除].
     *
     * @return  the codeStr
     */
    public String getCodeStr() {
        return codeStr;
    }

    
    /**
     * 设置 实体中对应virtualCode字段，字段间以逗号分隔[支持批量删除].
     *
     * @param codeStr the codeStr to set
     */
    public void setCodeStr(String codeStr) {
        this.codeStr = codeStr;
    }

    
    /**
     * 获取 返回前台的INT类型属性.
     *
     * @return  the returnInt
     */
    public int getReturnInt() {
        return returnInt;
    }

    
    /**
     * 设置 返回前台的INT类型属性.
     *
     * @param returnInt the returnInt to set
     */
    public void setReturnInt(int returnInt) {
        this.returnInt = returnInt;
    }

    
}
