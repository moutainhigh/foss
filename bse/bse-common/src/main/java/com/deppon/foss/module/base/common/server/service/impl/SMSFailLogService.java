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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/service/impl/SMSFailLogService.java
 * 
 * FILE NAME        	: SMSFailLogService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.server.service.impl;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.base.common.api.server.dao.ISMSFailLogDao;
import com.deppon.foss.module.base.common.api.server.service.ISMSFailLogService;
import com.deppon.foss.module.base.common.api.shared.domain.SMSFailLogEntity;
import com.deppon.foss.module.base.common.api.shared.exception.SMSFailLogException;
/**
 * 用来操作交互“短信发送失败日志”的数据库对应数据访问Service接口实现类：无SUC 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-20 上午9:20:22</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-20 上午9:20:22
 * @since
 * @version
 */
public class SMSFailLogService implements ISMSFailLogService {

    //"短信发送失败日志"DAO
    private ISMSFailLogDao smsFailLogDao;
    
    /**
     * <p>新增一个“短信发送失败日志”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-19 下午3:20:52
     * @param smsFailLog “短信发送失败日志”实体
     * @return 1：成功；0：失败
     * @throws SMSFailLogException 
     * @see com.deppon.foss.module.base.common.api.server.service.ISMSFailLogService#addSMSFailLog(com.deppon.foss.module.base.common.api.shared.domain.SMSFailLogEntity)
     */
    @Override
    public int addSMSFailLog(SMSFailLogEntity smsFailLog) throws SMSFailLogException {
	if (null == smsFailLog) {
	    throw new SMSFailLogException("", "短信发送失败日志息为空");
	}
	return smsFailLogDao.addSMSFailLog(smsFailLog);
    }

    /**
     * <p>新增一个“短信发送失败日志”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-19 下午3:43:30
     * @param smsFailLog “短信发送失败日志”实体
     * @return 1：成功；0：失败
     * @throws SMSFailLogException 
     * @see com.deppon.foss.module.base.common.api.server.service.ISMSFailLogService#addSMSFailLogBySelective(com.deppon.foss.module.base.common.api.shared.domain.SMSFailLogEntity)
     */
    @Override
    public int addSMSFailLogBySelective(SMSFailLogEntity smsFailLog) throws SMSFailLogException{
	if (null == smsFailLog) {
	    throw new SMSFailLogException("", "短信发送失败日志息为空");
	}
	return smsFailLogDao.addSMSFailLogBySelective(smsFailLog);
    }

    /**
     * <p>根据“短信发送失败日志”记录唯一标识删除（物理删除）一条“短信发送失败日志”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-19 下午3:43:34
     * @param id 记录唯一标识
     * @return 1：成功；0：失败
     * @return 
     * @see com.deppon.foss.module.base.common.api.server.service.ISMSFailLogService#deleteSMSFailLog(java.lang.String)
     */
    @Override
    public int deleteSMSFailLog(String id) {
	if (StringUtils.isBlank(id)) {
	    throw new SMSFailLogException("", "ID为空");
	}
	return smsFailLogDao.deleteSMSFailLog(id);
    }

    /**
     * <p>修改一个“短信发送失败日志”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-19 下午3:43:41
     * @param smsFailLog “短信发送失败日志”实体
     * @return 1：成功；0：失败
     * @throws SMSFailLogException 
     * @see com.deppon.foss.module.base.common.api.server.service.ISMSFailLogService#updateSMSFailLogBySelective(com.deppon.foss.module.base.common.api.shared.domain.SMSFailLogEntity)
     */
    @Override
    public int updateSMSFailLogBySelective(SMSFailLogEntity smsFailLog) throws SMSFailLogException{
	if (null == smsFailLog) {
	    throw new SMSFailLogException("", "短信发送失败日志息为空");
	}
	if (StringUtils.isBlank(smsFailLog.getId())) {
	    throw new SMSFailLogException("", "ID为空");
	}
	return smsFailLogDao.updateSMSFailLogBySelective(smsFailLog);
    }

    /**
     * <p>修改一个“短信发送失败日志”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-19 下午3:43:44
     * @param smsFailLog “短信发送失败日志”实体
     * @return 1：成功；0：失败
     * @throws SMSFailLogException 
     * @see com.deppon.foss.module.base.common.api.server.service.ISMSFailLogService#updateSMSFailLog(com.deppon.foss.module.base.common.api.shared.domain.SMSFailLogEntity)
     */
    @Override
    public int updateSMSFailLog(SMSFailLogEntity smsFailLog) throws SMSFailLogException{
	if (null == smsFailLog) {
	    throw new SMSFailLogException("", "短信发送失败日志息为空");
	}
	if (StringUtils.isBlank(smsFailLog.getId())) {
	    throw new SMSFailLogException("", "ID为空");
	}
	return smsFailLogDao.updateSMSFailLog(smsFailLog);
    }

    /**
     * <p>根据“短信发送失败日志”记录唯一标识查询出一条“短信发送失败日志”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-19 下午3:43:38
     * @param smsFailLog “短信发送失败日志”参数实体
     * @return “短信发送失败日志”实体
     * @throws SMSFailLogException 
     * @see com.deppon.foss.module.base.common.api.server.service.ISMSFailLogService#querySMSFailLog(com.deppon.foss.module.base.common.api.shared.domain.SMSFailLogEntity)
     */
    @Override
    public SMSFailLogEntity querySMSFailLog(SMSFailLogEntity smsFailLog) throws SMSFailLogException{
	if (null == smsFailLog) {
	    return null;
	}
	return smsFailLogDao.querySMSFailLog(smsFailLog);
    }    
    
    /**
     * @param smsFailLogDao the smsFailLogDao to set
     */
    public void setSmsFailLogDao(ISMSFailLogDao smsFailLogDao) {
        this.smsFailLogDao = smsFailLogDao;
    }
}
