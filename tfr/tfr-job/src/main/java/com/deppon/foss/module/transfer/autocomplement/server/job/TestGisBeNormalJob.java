/**  
 * Project Name:tfr-job  
 * File Name:TestGisBeNormalJob.java  
 * Package Name:com.deppon.foss.module.transfer.autocomplement.server.job  
 * Date:2015年6月14日下午4:59:33  
 *  
 */
package com.deppon.foss.module.transfer.autocomplement.server.job;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.job.BaseStateFulJob;
import com.deppon.foss.module.transfer.load.api.server.service.IAutoAddCodeService;
import com.deppon.foss.module.transfer.load.api.shared.define.AutoAddCodeConstants;
import com.deppon.foss.util.define.FossConstants;

/**  
 * ClassName: TestGisBeNormalJob <br/>  
 * Function: 检测GIS系统地址匹配服务是否正常的job <br/>  
 * date: 2015年6月14日 下午4:59:33 <br/>  
 *  
 * @author shiwei-045923 shiwei@outlook.com  
 * @version   
 * @since JDK 1.6  
 */
public class TestGisBeNormalJob extends BaseStateFulJob {

	/**  
	 * @see com.deppon.foss.module.transfer.job.BaseStateFulJob#doExecute(org.quartz.JobExecutionContext)  
	 */
	@Override
	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {
		LOGGER.info("---检测GIS匹配网点服务JOB开始---");
		IAutoAddCodeService autoAddCodeService = this.getBean("autoAddCodeService", IAutoAddCodeService.class);
		ITfrCommonService tfrCommonService = this.getBean("tfrCommonService", ITfrCommonService.class);
		//读取数据字典的 自动补码总开关
		String readValue = autoAddCodeService.readAutoAddCodePower();
		if(StringUtils.isNotBlank(readValue)){
			if(StringUtils.endsWith(readValue, AutoAddCodeConstants.AUTO_ADD_JOB_OPEN+"")){
				TfrJobProcessEntity jobProcess = new TfrJobProcessEntity();
				Date jobStartTime = null;
				Date jobEndTime = null;
				int threadNo = 0;
				int threadCount = 1;
				try{
					threadNo = Integer.valueOf((String)context.getMergedJobDataMap().get("threadNo")).intValue();
					threadCount = Integer.valueOf((String)context.getMergedJobDataMap().get("threadCount")).intValue();
					Date scheduledFireTime = context.getScheduledFireTime();
					
					jobProcess = tfrCommonService.queryJobInfo(TfrJobBusinessTypeEnum.TEST_GIS_BE_NORMAL_JOB, scheduledFireTime, threadNo, threadCount);
					//job开始执行时间
					jobStartTime = Calendar.getInstance().getTime();
					//执行任务
					int rlt = autoAddCodeService.testGisBeNormal();
					if(FossConstants.FAILURE == rlt){
						Thread.sleep(10000L);//10S
						autoAddCodeService.testGisBeNormal();
					}
					//job结束执行时间
					jobEndTime = Calendar.getInstance().getTime();
					
					jobProcess.setJobStartTime(jobStartTime);
					jobProcess.setJobEndTime(jobEndTime);
					jobProcess.setBizStartTime(jobProcess.getBizEndTime());
					jobProcess.setBizEndTime(scheduledFireTime);
					jobProcess.setStatus(TransferConstants.JOB_SUCCESS);
					
				}catch(Exception e) {
					LOGGER.error("TEST_GIS_BE_NORMAL_JOB error", e);
					jobProcess.setStatus(TransferConstants.JOB_FAILURE);
					//	记录出错日志
					TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
					jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.TEST_GIS_BE_NORMAL_JOB.getBizName());
					jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.TEST_GIS_BE_NORMAL_JOB.getBizCode());
					jobProcessLogEntity.setRemark("任务执行失败！");
					jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
					jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
					
					tfrCommonService.addJobProcessLog(jobProcessLogEntity);
				}finally {
					tfrCommonService.updateExecutedJob(jobProcess);
				}
			}
		}
		
		LOGGER.info("---检测GIS匹配网点服务JOB结束---");
	}

}
