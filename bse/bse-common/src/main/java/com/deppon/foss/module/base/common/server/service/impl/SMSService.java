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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/service/impl/SMSService.java
 * 
 * FILE NAME        	: SMSService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.common.api.server.service.ISMSFailLogService;
import com.deppon.foss.module.base.common.api.server.service.ISMSSendLogService;
import com.deppon.foss.module.base.common.api.server.service.complex.ISMSService;
import com.deppon.foss.module.base.common.api.shared.domain.SMSFailLogEntity;
import com.deppon.foss.module.base.common.api.shared.domain.SMSSendLogEntity;
import com.deppon.foss.module.base.common.api.shared.dto.SMSDto;
import com.deppon.foss.module.base.common.api.shared.exception.SMSException;
/**
 * 用来交互“短信发送失败日志和短信信息”的数据库对应数据访问Service接口实现类：无SUC 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-12-28 上午9:54:36</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-12-28 上午9:54:36
 * @since
 * @version
 */
public class SMSService implements ISMSService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SMSService.class);
    
    //"短信信息"Service
    private ISMSSendLogService smsSendLogService;

    //"短信发送失败日志"Service
    private ISMSFailLogService smsFailLogService;
    
    /**
     * <p>根据发送的短信息唯一标示获取是否通过验证、错误信息、是否发送成功、失败原因</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-12-28 上午9:54:04
     * @param unionIdentitys 短信息唯一标示集合
     * @return DTO封装数据集合
     * @throws SMSException 
     * @see com.deppon.foss.module.base.common.api.server.service.complex.ISMSService#querySMSDtoByUnionIdentity(java.util.List)
     */
    @Override
    @Transactional(readOnly = true)
    public List<SMSDto> querySMSDtoByUnionIdentity(List<String> unionIdentitys)
	    throws SMSException {
	List<SMSDto> smsDtos = new ArrayList<SMSDto>();
	if(CollectionUtils.isNotEmpty(unionIdentitys)){
	    //准备临时参数
	    SMSSendLogEntity sendLog, smsSendLog = new SMSSendLogEntity();
	    SMSFailLogEntity failLog, smsFailLog = new SMSFailLogEntity();
	    //操作获取短信操作的结果集合
	    for (String unionIdentity : unionIdentitys) {
		SMSDto smsDto = new SMSDto();
		//“短信信息”
		if(StringUtils.isNotBlank(unionIdentity)){
		    smsSendLog.setUnionId(unionIdentity);
		    sendLog = smsSendLogService.querySMSSendLog(smsSendLog);
		    if(null != sendLog){
			smsDto.setSmsSendLog(sendLog);
		    }
		    //“短信发送失败日志”
		    smsFailLog.setFailIdentity(unionIdentity);
		    failLog = smsFailLogService.querySMSFailLog(smsFailLog);
		    if(null != failLog){
			smsDto.setSmsFailLog(failLog);
		    }
		    //放入结果集
		    if(null != smsDto){
			smsDtos.add(smsDto);
		    }
		}else{
		    LOGGER.warn("调用者试图用一个空或者空白的短信唯一标识查询短信发送的所有记录信息");
		}
	    }
	}
	return smsDtos;
    }

    /**
     * @param smsSendLogService the smsSendLogService to set
     */
    public void setSmsSendLogService(ISMSSendLogService smsSendLogService) {
        this.smsSendLogService = smsSendLogService;
    }

    /**
     * @param smsFailLogService the smsFailLogService to set
     */
    public void setSmsFailLogService(ISMSFailLogService smsFailLogService) {
        this.smsFailLogService = smsFailLogService;
    }
}
