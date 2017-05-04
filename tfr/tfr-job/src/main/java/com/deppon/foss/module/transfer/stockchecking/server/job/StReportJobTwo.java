package com.deppon.foss.module.transfer.stockchecking.server.job;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.job.BaseStateFulJob;
import com.deppon.foss.module.transfer.stockchecking.api.server.service.IStReportService;
import com.deppon.foss.util.DateUtils;

public class StReportJobTwo extends BaseStateFulJob{

	/** 
	 * JOB定时执行生成对应的清仓差异报告
	 * 1、通过上次JOB执行状态，确定本次业务起止处理时间段
	 * 2、按业务起止处理时间段，处理此时间段内某切片的清仓任务
	 * 3、更新此JOB的执行时间和状态
	 * @author foss-wuyingjie
	 * @date 2012-11-27 下午4:18:25
	 * @see com.deppon.foss.framework.server.components.jobgrid.GridJob#doExecute(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void doExecute(JobExecutionContext context) throws JobExecutionException {
		LOGGER.trace("************************ 开始执行清仓差异报告 JOB ************************");
		
		TfrJobProcessEntity jobProcess = new TfrJobProcessEntity();
		Date jobStartTime = null;
		Date jobEndTime = null;
		int threadNo = 0;
		int threadCount = 1;
		IStReportService stReportService = getBean("stReportService", IStReportService.class);
		ITfrCommonService tfrCommonService = getBean("tfrCommonService", ITfrCommonService.class);
		IDataDictionaryValueService dataDictionaryValueService = getBean("dataDictionaryValueService",IDataDictionaryValueService.class);
		try {
			threadNo = Integer.valueOf((String) context.getMergedJobDataMap().get("threadNo")).intValue();
			threadCount = Integer.valueOf((String) context.getMergedJobDataMap().get("threadCount")).intValue();
			Date scheduledFireTime = context.getScheduledFireTime();
			
			//1、取出上次成功执行JOB后的业务截止时间
			jobProcess = tfrCommonService.queryJobInfo(TfrJobBusinessTypeEnum.ST_REPORT_ONE, scheduledFireTime, threadNo, threadCount);
			
			//2、执行业务任务
			jobStartTime = Calendar.getInstance().getTime();
			 //2.1、创建清仓任务差异报告及差异明细
			 //2.2、执行成功后，若存在明细都已处理完毕的清仓差异报告，需更新这些清仓报告状态为TransferConstants.STOCK_CHECKING_REPORT_DONE
			LOGGER.info("固定数据开始");
			//String startDate="25-10月 -15 12.00.00.000000 上午";
			//String endDate="27-10月 -15 08.00.00.000000 下午";
			//SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//Date startDT=sdf.parse(startDate);
			//Date endDT=sdf.parse(endDate);
			List<DataDictionaryValueEntity> paramList = dataDictionaryValueService.queryDataDictionaryValueByTermsCode(DictionaryConstants.TFR_PARAM_CREATE_STOCKCHECKING_ONE_TIME);
			if(paramList==null||paramList.size()<2){
				throw new Exception("未配置起止时间参数:TFR_PARAM_CREATE_STOCKCHECKING_ONE_TIME");
			}
			Date startTime=new Date();
			Date endTime=new Date();
			for(DataDictionaryValueEntity vEntity : paramList){
				if(vEntity.getValueCode().equals("startTime")){
					startTime=DateUtils.strToDate(vEntity.getValueName());
				}else if(vEntity.getValueCode().equals("endTime")){
					endTime=DateUtils.strToDate(vEntity.getValueName());
				}
			}
			LOGGER.info("固定数据结束");
			stReportService.executeStReportTask(startTime, endTime, threadNo, threadCount);
			//stReportService.executeStReportTask(jobProcess.getBizEndTime(), scheduledFireTime, threadNo, threadCount);
			jobEndTime = Calendar.getInstance().getTime();
			//3、更新任务完成状态及时间
			jobProcess.setJobStartTime(jobStartTime);
			jobProcess.setJobEndTime(jobEndTime);
			jobProcess.setBizStartTime(jobProcess.getBizEndTime());
			jobProcess.setBizEndTime(scheduledFireTime);
			jobProcess.setStatus(TransferConstants.JOB_SUCCESS);
		} catch (Exception e) {
			LOGGER.error("StReportJob error", e);
			jobProcess.setStatus(TransferConstants.JOB_FAILURE);
			//记录出错日志
			TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
			jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.ST_REPORT_ONE.getBizName());
			jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.ST_REPORT_ONE.getBizCode());
			jobProcessLogEntity.setRemark("任务执行失败！");
			jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
			jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
			tfrCommonService.addJobProcessLog(jobProcessLogEntity);
		} finally{
			tfrCommonService.updateExecutedJob(jobProcess);
		}
		
		LOGGER.trace("************************ 结束执行清仓差异报告 JOB ************************");
	}

}
