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
 *  PROJECT NAME  : tfr-stockchecking-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/api/server/dao/IStTaskDao.java
 *  
 *  FILE NAME          :IStTaskDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.lostwarning.server.service.impl;

import java.util.Calendar;
import java.util.List;


import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.lostwarning.api.server.dao.IUnloadLackDiffReportDao;
import com.deppon.foss.module.transfer.lostwarning.api.server.service.IUnloadLackDiffReportService;
import com.deppon.foss.module.transfer.lostwarning.api.server.service.IUnloadLackService;

/** 
 * @className: UnloadLackDiffReportService
 * @author: WangWenbo 
 * @description: 卸车少货差异报告service实现类
 * @date: 2016-06-26 上午10:06:17
 * 
 */
public class UnloadLackDiffReportService implements IUnloadLackDiffReportService {
	
	/**
	 * 记录日志
	 */
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

	/**
	 * 本模块dao
	 */
	private IUnloadLackDiffReportDao unloadLackDiffReportDao;
	
	
	
	private IUnloadLackService unloadLackService;
	
	private ITfrCommonService tfrCommonService;
	
	
	

	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}

	public void setUnloadLackService(IUnloadLackService unloadLackService) {
		this.unloadLackService = unloadLackService;
	}

	public void setUnloadLackDiffReportDao(IUnloadLackDiffReportDao unloadLackDiffReportDao) {
		this.unloadLackDiffReportDao = unloadLackDiffReportDao;
	}
	

	/**
	 * 处理卸车差异报告
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-25 下午2:54:17
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadDiffReportService#executeUnloadDiffReportTask()
	 */
	@Override
	public void executeUnloadLackDiffReportTask(int threadNo, int threadCount) {
		//查询查理状态为 未处理 UnloadConstants.UNLOAD_DIFF_HANDLE_STATE_UNRESOLVED 的差异报告
		//遍历差异报告下明细 
		List<String> reportIdList = unloadLackDiffReportDao.queryUnResolvedDiffReport(threadNo, threadCount, null, null/*bizDate,deliverBizDate*/);
		LOGGER.info("reportIdList " + reportIdList.size());
		if(CollectionUtils.isNotEmpty(reportIdList)){
			for(String reportId : reportIdList){
				try{
					unloadLackService.executeUnloadDiffReportTask(reportId);
				}catch(Exception e){
					LOGGER.error("上报卸车差错出现异常，差异报告ID：" + reportId + "，异常信息：" + e.getMessage());
					TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
					jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.UNLOAD_DIFF_REPORT_QMS.getBizName());
					jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.UNLOAD_DIFF_REPORT_QMS.getBizCode());
					jobProcessLogEntity.setExecBizId(reportId);
					jobProcessLogEntity.setExecTableName("卸车差异报告");
					jobProcessLogEntity.setRemark("上报卸车差错时出现异常");
					jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
					jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
					tfrCommonService.addJobProcessLog(jobProcessLogEntity);
					continue;
				}
			}
		}else{
			//没有未处理完毕的差异报告
		}
		
	}
	
	
		


}