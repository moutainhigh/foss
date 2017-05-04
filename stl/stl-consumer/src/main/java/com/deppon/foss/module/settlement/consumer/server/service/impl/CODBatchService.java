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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/consumer/server/service/impl/CODBatchService.java
 * 
 * FILE NAME        	: CODBatchService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.dao.ICODBatchEntityDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICODBatchService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODBatchEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODBatchDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;

/**
 * 
 * 代收货款批次号服务
 * 
 * @author 046644-foss-zengbinwen
 * @date 2012-11-29 下午4:30:31
 */
public class CODBatchService implements ICODBatchService {

	private static final String DATE_FORMAT = "yyyyMMdd";
	
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LogManager.getLogger(CODBatchService.class);

	/**
	 * 代收货款批次号服务
	 */
	private ICODBatchEntityDao codBatchEntityDao;

	
	/**
	 * 生成代收货款批次号
	 * 当前时间截取到日期01/02八位流水号（此处01代表即日退，02为三日退或审核退）
	 *  如：201207030100000001
	 */
	@Override
	public String generateCODBatchNo(String codType){
		LOGGER.info("生成代收货款批次号开始 ...");
		// 生成代收货款批次号，由日期和SEQ组成
		long batchNoSeq = codBatchEntityDao.selectBatchNoSeq();
		String today = DateUtils.convert(new Date(), DATE_FORMAT);
		String batchNoSeqString = String.format("%08d", batchNoSeq);
	
		// 根据代收货款类型确定批次号类型
		String codTypeNum = null;
		if(StringUtils.equals(codType, SettlementDictionaryConstants.COD__COD_TYPE__RETURN_1_DAY)){
			codTypeNum = SettlementConstants.COD_TYPE__BATCHNO_R1;
		}else if(StringUtils.equals(codType, SettlementConstants.COD__COD_TYPE__RETURN_3_A_DAY_CODE)){
			codTypeNum = SettlementConstants.COD_TYPE__BATCHNO_R3_RA;
		}else{
			throw new SettlementException("codType不是即日退类型或三日退（审核退）类型，生成代收货款批次号失败");
		}
		
		String batchNo = today + codTypeNum + batchNoSeqString;
		LOGGER.info("生成代收货款批次号结束 ...");
		return batchNo;
	}
	
	/**
	 * 
	 * 创建代收货款批次号实体
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-29 下午4:32:12
	 */
	@Override
	public void createCODBatchEntity(CurrentInfo currentInfo,String codBatchNo,String batchState) {
		
		LOGGER.info("Service start ...");
		// 构建代收货款批次实体
		CODBatchEntity entity = new CODBatchEntity();

		// ID、操作时间、操作人、批次号、状态、失败原因
		entity.setId(UUIDUtils.getUUID());
		entity.setOperateTime(new Date());
		entity.setOperatorCode(currentInfo.getEmpCode());
		entity.setOperatorName(currentInfo.getEmpName());
		entity.setBatchNo(codBatchNo);
		entity.setStatus(batchState);
		entity.setFailNotes(null);

		// 新增批次号实体
		int rows = codBatchEntityDao.insert(entity);
		if (rows != 1) {
			throw new SettlementException("创建代收货款批次号实体异常");
		}
		LOGGER.info("Service end .");
	}

	/**
	 * 
	 * 根据批次号查询代收货款批次实体
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-29 下午4:34:06
	 */
	@Override
	public CODBatchEntity queryByBatchNo(String batchNo) {

		LOGGER.debug("Service start,batchNo:" + batchNo);

		// 如果ID为空，抛出异常
		if (StringUtils.isEmpty(batchNo)) {
			throw new SettlementException("代收货款批次号实体批次号为空.");
		}

		// 查询数据
		CODBatchEntity entity = codBatchEntityDao.queryByBatchNo(batchNo);

		LOGGER.debug("Service end.");

		return entity;
	}

	/**
	 * 
	 * 更新代收货款状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-29 下午8:11:38
	 */
	public void updateCODBatchStatus(CODBatchDto dto) {

		LOGGER.info("Service start,batchNo:"
				+ (dto == null ? null : dto.getBatchNo()));

		// 判断参数不为空
		if (dto == null) {
			throw new SettlementException("代收货款批次号实体为空.");
		}

		// 更新状态
		int rows = codBatchEntityDao.updateCODBatchStatus(dto);

		// 判断更新行数是否唯一
		if (rows != 1) {
			throw new SettlementException("更新行数不唯一");
		}

		LOGGER.info("Service end.");
	}

	
	
	/**
	 * @param codBatchEntityDao
	 */
	public void setCodBatchEntityDao(ICODBatchEntityDao codBatchEntityDao) {
		this.codBatchEntityDao = codBatchEntityDao;
	}
}
