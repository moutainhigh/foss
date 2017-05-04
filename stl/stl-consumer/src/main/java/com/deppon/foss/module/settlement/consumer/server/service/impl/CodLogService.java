/**
 * Copyright 2013 STL TEAM
 */
/*******************************************************************************
 * Copyright 2013 STL TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: stl-consumer
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/consumer/server/service/impl/CodLogService.java
 * 
 * FILE NAME        	: CodLogService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.dao.ICODLogEntityDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodLogService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODLogEntity;

/**
 * 代收货款日志服务
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-11-8 上午10:22:58
 */
public class CodLogService extends iBatis3DaoImpl implements ICodLogService {

	private static final Logger logger = LogManager
			.getLogger(CodLogService.class);

	/**
	 * 代收货款日志记录
	 */
	private ICODLogEntityDao codLogEntityDao;

	/**
	 * 添加代收货款日志
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-8 上午10:25:33
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ICodLogService#addCODLog(com.deppon.foss.module.settlement.consumer.api.shared.domain.CODLogEntity)
	 */
	@Override
	public int addCodLog(CODLogEntity entity) {
		if (entity != null) {
			// 检查参数
			if (!StringUtils.isEmpty(entity.getWaybillNo())
					&& !StringUtils.isEmpty(entity.getCodId())
					&& !StringUtils.isEmpty(entity.getOperateActiontype())) {

				logger.info("记录代收货款操作日志");

				// 执行日志新加
				return this.codLogEntityDao.addCODLog(entity);

			} else {
				throw new SettlementException("内部错误,关键信息为空！");
			}
		} else {
			throw new SettlementException("内部错误参数为空！");
		}
	}

	/**
	 * 查询代收货款日志
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-8 上午10:25:47
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ICodLogService#queryByWaybill(java.lang.String)
	 */
	@Override
	public List<CODLogEntity> queryByWaybill(String waybillNumber) {
		if (!StringUtils.isEmpty(waybillNumber)) {
			return this.codLogEntityDao.queryByWaybill(waybillNumber);
		} else {
			throw new SettlementException("内部错误参数为空！");
		}
	}
	@Override
	public List<CODLogEntity> queryAuditLogByWaybill(String waybillNo) {
		if (!StringUtils.isEmpty(waybillNo)) {
			return this.codLogEntityDao.queryAuditLogByWaybill(waybillNo);
		} else {
			throw new SettlementException("内部错误参数为空！");
		}
	}


	
	/**
	 * @param codLogEntityDao
	 */
	public void setCodLogEntityDao(ICODLogEntityDao codLogEntityDao) {
		this.codLogEntityDao = codLogEntityDao;
	}

	

}
