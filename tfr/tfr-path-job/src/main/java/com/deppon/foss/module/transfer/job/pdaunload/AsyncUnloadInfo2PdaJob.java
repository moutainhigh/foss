package com.deppon.foss.module.transfer.job.pdaunload;

import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.job.BaseStateFulJob;
import com.deppon.foss.module.transfer.unload.api.server.service.IAsyncPdaUnloadService;

public class AsyncUnloadInfo2PdaJob extends BaseStateFulJob {
	private static final String BIZ_NAME = "同步长短途卸车信息给PDA";
	private static final String BIZ_CODE = "ASYNC_UNLOAD_INFO_2_PDA";

	@Override
	protected void doExecute(JobExecutionContext paramJobExecutionContext) throws JobExecutionException {

		IAsyncPdaUnloadService asyncPdaUnloadService = super.getBean("asyncPdaUnloadService",
				IAsyncPdaUnloadService.class);
		try {
			asyncPdaUnloadService.pushPdaUnloadSerial2Pda();
		} catch (Exception e) {
			ITfrCommonService tfrCommonService = super.getBean("tfrCommonService", ITfrCommonService.class);

			TfrJobProcessLogEntity logEntity = new TfrJobProcessLogEntity();
			logEntity.setBizName(BIZ_NAME);
			logEntity.setBizCode(BIZ_CODE);
			logEntity.setRemark(TransferConstants.JOB_FAILURE);
			logEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
			logEntity.setCreateTime(new Date());

			tfrCommonService.addJobProcessLog(logEntity);
		}
	}
}
