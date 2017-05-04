//package com.deppon.foss.module.pickup.predeliver.server.job;
//
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.deppon.foss.framework.server.components.jobgrid.GridJob;
//import com.deppon.foss.framework.server.components.logger.ILogBuffer;
//import com.deppon.foss.module.pickup.predeliver.api.server.service.IHandoverBillVoService;
//
///** 
// * @ClassName: EditVechileSchedulingJob 
// * @Description: 排班发生变化时修改已交单的车辆信息
// * @author fangwenjun 237982
// * @date 2015-5-9 下午2:31:55  
// *  
// */
//public class EditVechileSchedulingJob extends GridJob{
//	private static final Logger LOGGER = LoggerFactory.getLogger(EditVechileSchedulingJob.class);
//	@Override
//	protected void doExecute(JobExecutionContext context)
//			throws JobExecutionException {
//		try {
//			IHandoverBillVoService handoverBillVoService = getBean("handoverBillVoService", IHandoverBillVoService.class);
//
//			ILogBuffer logBuffer = getBean("performanceLog", ILogBuffer.class);
//			logBuffer.write("车辆排班发生变更更新交单车辆 服务启动");
//			handoverBillVoService.preprocess();
//			logBuffer.write("车辆排班发生变更更新交单车辆  服务结束");
//		} catch (Exception e) {
//			LOGGER.error("车辆排班发生变更更新交单车辆Job error", e);
//			throw new JobExecutionException("车辆排班发生变更更新交单车辆Job error", e);
//		}
//		
//	}
//
//}
