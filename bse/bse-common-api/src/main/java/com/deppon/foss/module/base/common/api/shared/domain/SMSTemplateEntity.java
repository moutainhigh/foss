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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/shared/domain/SMSTemplateEntity.java
 * 
 * FILE NAME        	: SMSTemplateEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 短信模板实体类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-11 上午10:45:41 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-11 上午10:45:41
 * @since
 * @version
 */
public class SMSTemplateEntity extends BaseEntity{

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 3386764180124610267L;

    /**
     * 模板代码.
     */
    private String smsCode; 

    /**
     * 模板名称.
     */
    private String smsName; 

    /**
     * 所属子系统.
     */
    private String subSystem;

    /**
     * 子系统功能模块.
     */
    private String subSystemModule;

    /**
     * 模板内容.
     */
    private String content;

    /**
     * 是否启用.
     */
    private String active;
    
    /**
     * 虚拟编码.
     */
    private String virtualCode;
    
    /**
     * 创建人.
     */
    private String createUser;
    
    /**
     * 创建时间.
     */
    private Date createDate;
    
    /**
     * 修改人.
     */
    private String modifyUser;
    
    /**
     * 修改时间.
     */
    private Date modifyDate;
    
    /**
     * 获取创建人.
     */
    public String getCreateUser() {
		return createUser;
	}
    
    /**
     * 设置创建人.
     */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
    
	/**
     * 获取创建时间.
     */
	public Date getCreateDate() {
		return createDate;
	}
    
	/**
     * 设置创建时间.
     */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
    
	/**
     * 获取修改人.
     */
	public String getModifyUser() {
		return modifyUser;
	}
    
	/**
     * 设置修改人.
     */
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
    
	/**
     * 获取修改时间.
     */
	public Date getModifyDate() {
		return modifyDate;
	}
    
	/**
     * 设置修改时间.
     */
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

    /**
     * 获取 模板代码.
     *
     * @return  the smsCode
     */
    public String getSmsCode() {
        return smsCode;
    }

    
    /**
     * 设置 模板代码.
     *
     * @param smsCode the smsCode to set
     */
    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    
    /**
     * 获取 模板名称.
     *
     * @return  the smsName
     */
    public String getSmsName() {
        return smsName;
    }

    
    /**
     * 设置 模板名称.
     *
     * @param smsName the smsName to set
     */
    public void setSmsName(String smsName) {
        this.smsName = smsName;
    }

    
    /**
     * 获取 所属子系统.
     *
     * @return  the subSystem
     */
    public String getSubSystem() {
        return subSystem;
    }

    
    /**
     * 设置 所属子系统.
     *
     * @param subSystem the subSystem to set
     */
    public void setSubSystem(String subSystem) {
        this.subSystem = subSystem;
    }
    
    /**
     * 获取 子系统功能模块.
     *
     * @return  the subSystemModule
     */
    public String getSubSystemModule() {
        return subSystemModule;
    }

    
    /**
     * 设置 子系统功能模块.
     *
     * @param subSystemModule the subSystemModule to set
     */
    public void setSubSystemModule(String subSystemModule) {
        this.subSystemModule = subSystemModule;
    }

    
    /**
     * 获取 模板内容.
     *
     * @return  the content
     */
    public String getContent() {
        return content;
    }

    
    /**
     * 设置 模板内容.
     *
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    
    /**
     * 获取 是否启用.
     *
     * @return  the active
     */
    public String getActive() {
        return active;
    }

    
    /**
     * 设置 是否启用.
     *
     * @param active the active to set
     */
    public void setActive(String active) {
        this.active = active;
    }

    
    /**
     * 获取 虚拟编码.
     *
     * @return  the virtualCode
     */
    public String getVirtualCode() {
        return virtualCode;
    }

    
    /**
     * 设置 虚拟编码.
     *
     * @param virtualCode the virtualCode to set
     */
    public void setVirtualCode(String virtualCode) {
        this.virtualCode = virtualCode;
    }
    

}
