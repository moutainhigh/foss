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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/server/service/complex/ISMSService.java
 * 
 * FILE NAME        	: ISMSService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.api.server.service.complex;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.common.api.shared.dto.SMSDto;
import com.deppon.foss.module.base.common.api.shared.exception.SMSException;
/**
 * 用来交互“短信发送失败日志和短信信息”的数据库对应数据访问Service接口：无SUC 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-12-28 上午9:46:25</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-12-28 上午9:46:25
 * @since
 * @version
 */
public interface ISMSService extends IService {

    /**
     * <p>根据发送的短信息唯一标示获取是否通过验证、错误信息、是否发送成功、失败原因</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-12-28 上午9:54:04
     * @param unionIdentitys 短信息唯一标示集合
     * @return DTO封装数据集合
     * @throws SMSException
     * @see
     */
    List<SMSDto> querySMSDtoByUnionIdentity(List<String> unionIdentitys) throws SMSException;
    
}
