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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/dao/impl/SMSFailLogDao.java
 * 
 * FILE NAME        	: SMSFailLogDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.server.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.base.common.api.server.dao.ISMSFailLogDao;
import com.deppon.foss.module.base.common.api.shared.domain.SMSFailLogEntity;
import com.deppon.foss.util.UUIDUtils;


public class SMSFailLogDao extends iBatis3DaoImpl implements ISMSFailLogDao {
    
    private static final String NAMESPACE = "foss.bse.bse-common.smsfaillog";

    /**
     * <p>新增一个“短信发送失败日志”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-19 下午3:43:06
     * @param smsFailLog “短信发送失败日志”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.common.api.server.dao.ISMSFailLogDao#addSMSFailLog(com.deppon.foss.module.base.common.api.shared.domain.SMSFailLogEntity)
     */
    @Override
    public int addSMSFailLog(SMSFailLogEntity smsFailLog) {
	smsFailLog.setId(UUIDUtils.getUUID());
	smsFailLog.setCreateDate(new Date());
	smsFailLog.setModifyUser(smsFailLog.getCreateUser());
	smsFailLog.setModifyDate(smsFailLog.getCreateDate());
	return getSqlSession().insert(NAMESPACE + ".addSMSFailLog", smsFailLog);
    }

    /**
     * <p>新增一个“短信发送失败日志”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-19 下午3:43:30
     * @param smsFailLog “短信发送失败日志”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.common.api.server.dao.ISMSFailLogDao#addSMSFailLogBySelective(com.deppon.foss.module.base.common.api.shared.domain.SMSFailLogEntity)
     */
    @Override
    public int addSMSFailLogBySelective(SMSFailLogEntity smsFailLog) {
	smsFailLog.setId(UUIDUtils.getUUID());
	smsFailLog.setCreateDate(new Date());
	smsFailLog.setModifyUser(smsFailLog.getCreateUser());
	smsFailLog.setModifyDate(smsFailLog.getCreateDate());
	return getSqlSession().insert(NAMESPACE + ".addSMSFailLogBySelective", smsFailLog);
    }

    /**
     * <p>根据“短信发送失败日志”记录唯一标识删除（物理删除）一条“短信发送失败日志”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-19 下午3:43:34
     * @param id 记录唯一标识
     * @return 影响记录数
     * @see com.deppon.foss.module.base.common.api.server.dao.ISMSFailLogDao#deleteSMSFailLog(java.lang.String)
     */
    @Override
    public int deleteSMSFailLog(String id) {
	return getSqlSession().delete(NAMESPACE + ".deleteSMSFailLog", id);
    }

    /**
     * <p>修改一个“短信发送失败日志”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-19 下午3:43:41
     * @param smsFailLog “短信发送失败日志”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.common.api.server.dao.ISMSFailLogDao#updateSMSFailLogBySelective(com.deppon.foss.module.base.common.api.shared.domain.SMSFailLogEntity)
     */
    @Override
    public int updateSMSFailLogBySelective(SMSFailLogEntity smsFailLog) {
	smsFailLog.setModifyDate(new Date());
	return getSqlSession().update(NAMESPACE + ".updateSMSFailLogBySelective", smsFailLog);
    }

    /**
     * <p>修改一个“短信发送失败日志”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-19 下午3:43:44
     * @param smsFailLog “短信发送失败日志”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.common.api.server.dao.ISMSFailLogDao#updateSMSFailLog(com.deppon.foss.module.base.common.api.shared.domain.SMSFailLogEntity)
     */
    @Override
    public int updateSMSFailLog(SMSFailLogEntity smsFailLog) {
	smsFailLog.setModifyDate(new Date());
	return getSqlSession().update(NAMESPACE + ".updateSMSFailLog", smsFailLog);
    }

    /**
     * <p>根据“短信发送失败日志”记录唯一标识查询出一条“短信发送失败日志”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-19 下午3:43:38
     * @param smsFailLog “短信发送失败日志”参数实体
     * @return “短信发送失败日志”实体
     * @see com.deppon.foss.module.base.common.api.server.dao.ISMSFailLogDao#querySMSFailLog(com.deppon.foss.module.base.common.api.shared.domain.SMSFailLogEntity)
     */
    @Override
    @SuppressWarnings("unchecked")
    public SMSFailLogEntity querySMSFailLog(SMSFailLogEntity smsFailLog) {
	List<SMSFailLogEntity> smsFailLogEntities = getSqlSession().selectList(NAMESPACE + ".querySMSFailLog", smsFailLog, new RowBounds(0, 1));
	if (CollectionUtils.isEmpty(smsFailLogEntities)) {
	    return null;
	}
	return smsFailLogEntities.get(0);
    }

}
