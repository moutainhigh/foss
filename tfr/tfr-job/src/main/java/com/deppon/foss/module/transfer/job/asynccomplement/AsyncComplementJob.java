package com.deppon.foss.module.transfer.job.asynccomplement;

import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.job.BaseStateFulJob;
import com.deppon.foss.module.transfer.load.api.server.service.IAsyncComplementService;

public class AsyncComplementJob extends BaseStateFulJob {
	
	private final String BIZ_NAME = "异步补码";
	private final String BIZ_CODE = "ASYNC_COMPLEMENT";

	@Override
	protected void doExecute(JobExecutionContext paramJobExecutionContext) throws JobExecutionException {

		IAsyncComplementService asyncComplementService = super.getBean("asyncComplementService", IAsyncComplementService.class);
		try {
			asyncComplementService.complementAsync4Job();
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
