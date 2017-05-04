/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-common
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/server/dao/impl/SerialNumberRuleDao.java
 *  
 *  FILE NAME          :SerialNumberRuleDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 ******************************************************************************/
package com.deppon.foss.module.transfer.common.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.common.api.server.dao.ISerialNumberRuleDao;
import com.deppon.foss.module.transfer.common.api.shared.domain.CreateErrorLogEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.SerialNumberRuleEntity;
import com.deppon.foss.util.DateUtils;
/**
 * 
 * @author 038300-foss-pengzhen
 *
 */
public class SerialNumberRuleDao extends iBatis3DaoImpl implements ISerialNumberRuleDao {
	/**
	 * 
	 */
	@Override
	public SerialNumberRuleEntity selectSnRule(String ruleCode) {

		return (SerialNumberRuleEntity) this.getSqlSession().selectOne("foss.tfr.SerialNumberRuleDao.selectSnRule", ruleCode);
	}
	/**
	 * 
	 */
	@Override
	public SerialNumberRuleEntity selectSnRuleForUpdate(String ruleCode) {
		
		return (SerialNumberRuleEntity) this.getSqlSession().selectOne("foss.tfr.SerialNumberRuleDao.selectSnRuleForUpdate", ruleCode);
	}
	/**
	 * 
	 */
	@Override
	public void addSnRule(SerialNumberRuleEntity serialNumberRuleEntity) {
		this.getSqlSession().insert("foss.tfr.SerialNumberRuleDao.addSnRule", serialNumberRuleEntity);
	}

	@Override
	public long getNextSequenceValue(String sequenceName) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("sequenceName", sequenceName);
		
		return (Long) this.getSqlSession().selectOne("foss.tfr.SerialNumberRuleDao.getNextSequenceValue", params);
	}

	@Override
	public void resetSequence(String sequenceName) {
		this.getSqlSession().selectOne("foss.tfr.SerialNumberRuleDao.resetSequence", sequenceName);
	}

	@Override
	public void updateSnRule(SerialNumberRuleEntity snEntity) {
		this.getSqlSession().update("foss.tfr.SerialNumberRuleDao.updateSnRule", snEntity);
	}

	@Override
	public String queryVehicleAssembleBillNo(String oriOrgCode,	List<String> destOrgCodeList, String leaveTime) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("oriOrgCode", oriOrgCode);
		params.put("destOrgCodeList", destOrgCodeList);
		Date leaveStartTime = DateUtils.convert(leaveTime, "yyyyMMdd");
		Date leaveEndTime = DateUtils.addDayToDate(leaveStartTime, 1);
		params.put("leaveStartTime", leaveStartTime);
		params.put("leaveEndTime", leaveEndTime);
		
		return (String) this.getSqlSession().selectOne("foss.tfr.SerialNumberRuleDao.queryVehicleAssembleBillNo", params);
	}
	
	/**
	 * 插入错误记录
	 * @author alfred
	 * @date 2016-10-19 16:26:34
	 * @param entity
	 * @return
	 * @see
	 */
	@Override
	public void createErrorLog(CreateErrorLogEntity entity) {
		this.getSqlSession().insert("foss.tfr.SerialNumberRuleDao.addCreateErrorLog", entity);
	}

}