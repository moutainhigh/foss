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
 * PROJECT NAME	: pkp-job
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/notification/server/job/StorageChargeProcessJob.java
 * 
 * FILE NAME        	: StorageChargeProcessJob.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.job;


import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.pickup.sign.api.server.dao.ISignStockJobDao;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignStockJobService;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignStockEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.SignStockDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.shared.exception.StockException;
import com.deppon.foss.util.UUIDUtils;
/**
 * 
 * 入库JOB 
 * @author 
 *		foss-meiying
 * @date 
 *      2013-3-23 上午11:59:36
 * @since
 * @version
 */
public class InStockProcessJob extends GridJob {
	private static final Logger LOGGER = LoggerFactory.getLogger(InStockProcessJob.class);
	/**
	 * 入库JOB
	 * @author foss-meiying
	 * @date 2013-3-23 下午12:00:09
	 * @param arg0
	 * @throws JobExecutionException 
	 * @see com.deppon.foss.framework.server.components.jobgrid.GridJob#doExecute(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void doExecute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			ISignStockJobService signStockJobService = getBean("signStockJobService", ISignStockJobService.class);
			ISignStockJobDao signStockJobDao = getBean("signStockJobDao", ISignStockJobDao.class);
			// 入库开始
			LOGGER.info("反签收入库     InStockProcessJob begin");
			boolean hasLongRecord =true;
			while (hasLongRecord) {
				SignStockEntity signStockEntity = new SignStockEntity();
				signStockEntity.setStatus(NumberConstants.ZERO.toString());//0-待处理
				signStockEntity.setInoutType(StockConstants.REVERSE_SIGN_IN_STOCK_TYPE);
				Integer count =signStockJobDao.queryCountbyCondition(signStockEntity);
				LOGGER.info("反签收入库      t_srv_sign_stock 中是否存在status = 0（待处理）的数据" +count);//记录日志
				if(count > 0){
					if(count < SignConstants.SIGN_JOB_EACH__COUNT){//如果查询的数据小于指定的记录数
						hasLongRecord = false;
					}
				}else {
					break;//跳出当前循环
				}
				SignStockDto signStockDto = new SignStockDto();
				signStockDto.setOldStatus(NumberConstants.ZERO.toString());//状态 0-待执行 
				signStockDto.setStatus(NumberConstants.ONE.toString());//1-执行中
				//出入库类型 --反签收入库
				signStockDto.setInoutType(StockConstants.REVERSE_SIGN_IN_STOCK_TYPE);//反签收入库
				signStockDto.setTempCount(SignConstants.SIGN_JOB_EACH__COUNT);
				signStockDto.setJobId(UUIDUtils.getUUID());//jobID
				if(signStockJobDao.updateByCondition(signStockDto)>NumberConstants.ZERO){
					List<SignStockEntity> signStockList =signStockJobDao.queryByCondition(signStockDto);
					if(!CollectionUtils.isEmpty(signStockList)){
						// 系统调用中转接口（SUC-238）入库货物
						for (SignStockEntity signStock : signStockList){
							try {
								signStockJobService.inStock(signStock);
								} catch (StockException e) {//捕获异常
									SignStockEntity signStockentity = new SignStockEntity();
									signStockentity.setId(signStock.getId());
									signStockentity.setStatus(NumberConstants.TWO.toString());//2异常
									signStockentity.setExceptionMessage(e.getErrorCode());//异常原因
									signStockJobDao.updateById(signStockentity);//记录失败原因
									LOGGER.error("--调用中转入库数据中的一条数据失败，修改t_sign_stock中的异常原因"+ReflectionToStringBuilder.toString(signStockentity));//记录日志
								}catch (TfrBusinessException e) {//捕获异常
									SignStockEntity signStockentity = new SignStockEntity();
									signStockentity.setId(signStock.getId());
									signStockentity.setStatus(NumberConstants.TWO.toString());//2异常
									signStockentity.setExceptionMessage(e.getErrorCode());//异常原因
									signStockJobDao.updateById(signStockentity);//记录失败原因
									LOGGER.error("--调用中转走货路径数据中的一条数据失败，修改t_sign_stock中的异常原因"+ReflectionToStringBuilder.toString(signStockentity));//记录日志
								}catch (BusinessException e) {//捕获异常
									SignStockEntity signStockentity = new SignStockEntity();
									signStockentity.setId(signStock.getId());//id
									signStockentity.setStatus(NumberConstants.TWO.toString());//2异常
									signStockentity.setExceptionMessage(e.getErrorCode());//异常原因
									signStockJobDao.updateById(signStockentity);//记录失败原因
									LOGGER.error("--操作 失败，修改t_sign_stock中的异常原因"+ReflectionToStringBuilder.toString(signStockentity));//记录日志
							}
						}
					}
				}
			}
			// 入库结束
			LOGGER.info("反签收入库     InStockProcessJob End");
		} catch (Exception e) {
			LOGGER.error("error", e);
			throw new JobExecutionException("反签收入库异常",e);
		}
	}
}