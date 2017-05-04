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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/server/service/ISMSSendLogService.java
 * 
 * FILE NAME        	: ISMSSendLogService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.common.api.shared.domain.SMSSendLogEntity;
import com.deppon.foss.module.base.common.api.shared.exception.SMSSendLogException;
/**
 * 用来操作交互“短信信息”的数据库对应数据访问Service接口：无SUC
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-19 下午3:33:15</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-19 下午3:33:15
 * @since
 * @version
 */
public interface ISMSSendLogService extends IService{

    /**
     * <p>新增一个“短信信息”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-19 下午3:43:06
     * @param smsFailLog “短信信息”实体
     * @return 影响记录数
     * @see
     */
    int addSMSSendLog(SMSSendLogEntity smsSendLog) throws SMSSendLogException;

    /**
     * <p>新增一个“短信信息”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-19 下午3:43:30
     * @param smsFailLog “短信信息”实体
     * @return 影响记录数
     * @see
     */
    int addSMSSendLogBySelective(SMSSendLogEntity smsSendLog) throws SMSSendLogException;
    
    /**
     * <p>根据“短信信息”记录唯一标识删除（物理删除）一条“短信信息”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-19 下午3:43:34
     * @param id 记录唯一标识
     * @return 影响记录数
     * @see
     */
    int deleteSMSSendLog(String id) throws SMSSendLogException;

    /**
     * <p>修改一个“短信信息”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-19 下午3:43:41
     * @param smsFailLog “短信信息”实体
     * @return 影响记录数
     * @see
     */
    int updateSMSSendLogBySelective(SMSSendLogEntity smsSendLog) throws SMSSendLogException;

    /**
     * <p>修改一个“短信信息”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-19 下午3:43:44
     * @param smsFailLog “短信信息”实体
     * @return 影响记录数
     * @see
     */
    int updateSMSSendLog(SMSSendLogEntity smsSendLog) throws SMSSendLogException;
    
    /**
     * <p>根据“短信信息”记录唯一标识查询出一条“短信信息”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-19 下午3:43:38
     * @param smsFailLog “短信信息”参数实体
     * @return “短信信息”实体
     * @see
     */
    SMSSendLogEntity querySMSSendLog(SMSSendLogEntity smsSendLog) throws SMSSendLogException;
    
    /**
     * <p>发送一条“短信信息”到短信平台</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-19 下午8:46:51
     * @param smsSendLog “短信信息”实体
     * @throws SMSSendLogException
     * @see
     */
    void sendSMS(SMSSendLogEntity smsSendLog) throws SMSSendLogException; 
}
