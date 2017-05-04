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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/shared/dto/SMSDto.java
 * 
 * FILE NAME        	: SMSDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.api.shared.dto;

import java.io.Serializable;

import com.deppon.foss.module.base.common.api.shared.domain.SMSFailLogEntity;
import com.deppon.foss.module.base.common.api.shared.domain.SMSSendLogEntity;
/**
 * 用来存储交互“短信发送失败日志和短信信息”的数据库对应数据DTO：无SUC 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-12-28 上午9:48:26</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-12-28 上午9:48:26
 * @since
 * @version
 */
public class SMSDto implements Serializable {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -4368900507206471926L;

    /**
     * 短信信息.
     */
    private SMSSendLogEntity smsSendLog;
    
    /**
     * 短信发送失败信息.
     */
    private SMSFailLogEntity smsFailLog;
    
    /**
     * 获取 短信信息.
     *
     * @return  the smsSendLog
     */
    public SMSSendLogEntity getSmsSendLog() {
        return smsSendLog;
    }
    
    /**
     * 设置 短信信息.
     *
     * @param smsSendLog the smsSendLog to set
     */
    public void setSmsSendLog(SMSSendLogEntity smsSendLog) {
        this.smsSendLog = smsSendLog;
    }
    
    /**
     * 获取 短信发送失败信息.
     *
     * @return  the smsFailLog
     */
    public SMSFailLogEntity getSmsFailLog() {
        return smsFailLog;
    }
    
    /**
     * 设置 短信发送失败信息.
     *
     * @param smsFailLog the smsFailLog to set
     */
    public void setSmsFailLog(SMSFailLogEntity smsFailLog) {
        this.smsFailLog = smsFailLog;
    }
}
