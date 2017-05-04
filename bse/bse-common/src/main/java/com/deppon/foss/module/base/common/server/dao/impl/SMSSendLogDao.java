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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/dao/impl/SMSSendLogDao.java
 * 
 * FILE NAME        	: SMSSendLogDao.java
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
import com.deppon.foss.module.base.common.api.server.dao.ISMSSendLogDao;
import com.deppon.foss.module.base.common.api.shared.domain.SMSSendLogEntity;
import com.deppon.foss.util.UUIDUtils;
/**
 * 用来操作交互“短信信息”的数据库对应数据访问DAO接口实现类：无SUC
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-20 上午8:50:01</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-20 上午8:50:01
 * @since
 * @version
 */
public class SMSSendLogDao extends iBatis3DaoImpl implements ISMSSendLogDao {

    private static final String NAMESPACE = "foss.bse.bse-common.smssendlog";
    
    /**
     * <p>新增一个“短信信息”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-19 下午7:15:33
     * @param smsSendLog  “短信信息”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.common.api.server.dao.ISMSSendLogDao#addSMSSendLog(com.deppon.foss.module.base.common.api.shared.domain.SMSSendLogEntity)
     */
    @Override
    public int addSMSSendLog(SMSSendLogEntity smsSendLog) {
	smsSendLog.setId(UUIDUtils.getUUID());
	smsSendLog.setCreateDate(new Date());
	smsSendLog.setModifyUser(smsSendLog.getCreateUser());
	smsSendLog.setModifyDate(smsSendLog.getCreateDate());
	return getSqlSession().insert(NAMESPACE + ".addSMSSendLog", smsSendLog);
    }

    /**
     * <p>新增一个“短信信息”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-19 下午7:15:36
     * @param smsSendLog “短信信息”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.common.api.server.dao.ISMSSendLogDao#addSMSSendLogBySelective(com.deppon.foss.module.base.common.api.shared.domain.SMSSendLogEntity)
     */
    @Override
    public int addSMSSendLogBySelective(SMSSendLogEntity smsSendLog) {
	smsSendLog.setId(UUIDUtils.getUUID());
	smsSendLog.setCreateDate(new Date());
	smsSendLog.setModifyUser(smsSendLog.getCreateUser());
	smsSendLog.setModifyDate(smsSendLog.getCreateDate());
	return getSqlSession().insert(NAMESPACE + ".addSMSSendLogBySelective", smsSendLog);
    }
    
    /**
     * <p>根据“短信信息”记录唯一标识删除（物理删除）一条“短信信息”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-19 下午7:15:26
     * @param id 记录唯一标识
     * @return 影响记录数
     * @see com.deppon.foss.module.base.common.api.server.dao.ISMSSendLogDao#deleteSMSSendLog(java.lang.String)
     */
    @Override
    public int deleteSMSSendLog(String id) {
	return getSqlSession().delete(NAMESPACE + ".deleteSMSSendLog", id);
    }

    /**
     * <p>修改一个“短信信息”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-19 下午7:15:44
     * @param smsSendLog “短信信息”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.common.api.server.dao.ISMSSendLogDao#updateSMSSendLogBySelective(com.deppon.foss.module.base.common.api.shared.domain.SMSSendLogEntity)
     */
    @Override
    public int updateSMSSendLogBySelective(SMSSendLogEntity smsSendLog) {
	smsSendLog.setModifyDate(new Date());
	return getSqlSession().update(NAMESPACE + ".updateSMSSendLogBySelective", smsSendLog);
    }

    /**
     * <p>修改一个“短信信息”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-19 下午7:15:47
     * @param smsSendLog “短信信息”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.common.api.server.dao.ISMSSendLogDao#updateSMSSendLog(com.deppon.foss.module.base.common.api.shared.domain.SMSSendLogEntity)
     */
    @Override
    public int updateSMSSendLog(SMSSendLogEntity smsSendLog) {
	smsSendLog.setModifyDate(new Date());
	return getSqlSession().update(NAMESPACE + ".updateSMSSendLog", smsSendLog);
    }

    /**
     * <p>根据“短信信息”记录唯一标识查询出一条“短信信息”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-19 下午7:15:39
     * @param smsSendLog “短信信息”参数实体
     * @return “短信信息”实体
     * @see com.deppon.foss.module.base.common.api.server.dao.ISMSSendLogDao#querySMSSendLog(com.deppon.foss.module.base.common.api.shared.domain.SMSSendLogEntity)
     */
    @Override
    @SuppressWarnings("unchecked")
    public SMSSendLogEntity querySMSSendLog(SMSSendLogEntity smsSendLog) {
	List<SMSSendLogEntity> smsSendLogEntities = getSqlSession().selectList(NAMESPACE + ".querySMSSendLog", smsSendLog, new RowBounds(0, 1));
	if (CollectionUtils.isEmpty(smsSendLogEntities)) {
	    return null;
	}
	return smsSendLogEntities.get(0);
    }

}
