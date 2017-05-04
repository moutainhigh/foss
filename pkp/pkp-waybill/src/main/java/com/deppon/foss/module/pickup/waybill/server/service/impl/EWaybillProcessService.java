/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/service/impl/OrderService.java
 * 
 * FILE NAME        	: OrderService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-waybill
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.server.service.impl
 * FILE    NAME: OrderService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IEWaybillProcessDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IEWaybillProcessService;
import com.deppon.foss.module.pickup.waybill.shared.domain.EWaybillProcessEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 待激活订单处理Service
 * 
 * @author 045925
 * @date 2015-01-30
 */
public class EWaybillProcessService implements IEWaybillProcessService {
	// 日志信息
	public static final Logger LOGGER = LoggerFactory
			.getLogger(EWaybillProcessService.class);
	private IEWaybillProcessDao eWaybillProcessDao;
	
	/**
	 * 
	 * <p>新增待激活运单数据</p> 
	 * @author FOSS-YANGBIN
     * @date 2015-01-30 
     * @param waybillNo
     * @return integer
     * @see
	 */
	@Override
	public int insert(EWaybillProcessEntity entity) {
		entity.setId(UUIDUtils.getUUID());
		entity.setJobId("N/A");
		entity.setCreateTime(new Date());
		entity.setModifyTime(new Date());
		entity.setFailReason(FossConstants.NO);
		return eWaybillProcessDao.insert(entity);
	}
	
	/**
     * 
     * <p>通过设置的JOBID查询待激活运单数据</p> 
     * @author FOSS-YANGBIN
     * @date 2015-01-30 
     * @param waybillNo
     * @return integer
     * @see
     */
	@Override
	public List<EWaybillProcessEntity> queryAllByCommon(String jobId) {
		if(StringUtils.isEmpty(jobId)){
			return null;
		}
		return eWaybillProcessDao.queryAllByCommon(jobId);
	}
	
    /**
     * 
     * <p>更新一批待激活运单数据</p> 
     * @author FOSS-YANGBIN
     * @date 2015-01-30 
     * @param waybillNo
     * @return integer
     * @see
     */
	@Override
	public int updateForJob(Map<String, Object> map) {
		if(null == map
				|| map.isEmpty()){
			return 0;
		}
		String rownum = (String) map.get("rownum");
		String jobId = (String) map.get("jobId");
		if(StringUtils.isEmpty(jobId)){
			return 0;
		}
		if(StringUtils.isEmpty(rownum)){
			return 0;
		}
		return eWaybillProcessDao.updateForJob(map);
	}

    /**
     * 
     * <p>激活完成后删除待激活运单数据</p> 
     * @author FOSS-YANGBIN
     * @date 2015-01-30 
     * @param waybillNo
     * @return integer
     * @see
     */
	@Override
	public int deleteByWaybillNo(String waybillNo) {
		if(StringUtils.isEmpty(waybillNo)){
			return 0;
		}
		return	eWaybillProcessDao.deleteByWaybillNo(waybillNo);
	}

	public void seteWaybillProcessDao(IEWaybillProcessDao eWaybillProcessDao) {
		this.eWaybillProcessDao = eWaybillProcessDao;
	}

	 /**
     * 根据条件查询对应的条件个数
     * @author Foss-105888-Zhangxingwang
     * @date 2015-3-17 20:37:34
     * @param maps
     * @return
     */
	@Override
	public List<EWaybillProcessEntity> queryEWaybillByCondition(Map<String, Object> maps) {
		return eWaybillProcessDao.queryEWaybillByCondition(maps);
	}
	
	@Override
	public int updateEWaybillProcessByWaybillNo(EWaybillProcessEntity entity) {
		return eWaybillProcessDao.updateEWaybillProcessByWaybillNo(entity);
	}
}