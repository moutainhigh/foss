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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/shared/domain/SMSFailLogEntity.java
 * 
 * FILE NAME        	: SMSFailLogEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 用来存储交互“短信发送失败信息”的数据库对应实体：无SUC
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-19 下午3:37:01</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-19 下午3:37:01
 * @since
 * @version
 */
public class SMSFailLogEntity extends BaseEntity {
    
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -6012178647959580419L;

    //错误短信语音唯一标识
    private String failIdentity;

    //错误原因
    private String failReason;

    //是否发送成功
    private String isSuccessed;

    public String getFailIdentity() {
        return failIdentity;
    }

    public void setFailIdentity(String failIdentity) {
        this.failIdentity = failIdentity;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }
    
    public String getIsSuccessed() {
        return isSuccessed;
    }
    
    public void setIsSuccessed(String isSuccessed) {
        this.isSuccessed = isSuccessed;
    }
}
