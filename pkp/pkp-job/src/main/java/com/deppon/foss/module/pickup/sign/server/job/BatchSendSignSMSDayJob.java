//package com.deppon.foss.module.pickup.sign.server.job;
//
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import com.deppon.foss.framework.server.components.jobgrid.GridJob;
//import com.deppon.foss.module.pickup.sign.api.server.service.IBatchSendSMSJobService;
//
///***
// * <1>
// * @clasaName:com.deppon.foss.module.pickup.sign.server.job
// * @author: yuting@163.com
// * @description: (快递订单调度受理短信)、（快递签收发件人短信）、（签收单返单短信），次日向客户发送批量打包短信<br>
// * 				 是否批量发送  取决于crm端 发送的状态数据   [0,1]	<br>
// * 				次日中午12点发送打包短信
// * @date:2014年7月14日 下午2:27:21
// */
//public class BatchSendSignSMSDayJob extends GridJob {
//		private static final Logger LOGGER = LoggerFactory.getLogger(BatchSendSignSMSDayJob.class);
//		@Override
//		protected void doExecute(JobExecutionContext context) throws JobExecutionException
//		{
//			try {
//				LOGGER.info("1>次日批量发送短信(发货和签单) start");
//				IBatchSendSMSJobService batchSendExpressJobService = getBean("batchSendSingBillSMSDayJobService", IBatchSendSMSJobService.class);
//				batchSendExpressJobService.processBatchSendExpress();
//				LOGGER.info("1>次日批量发送短信(发货和签单)  end");
//			} catch (Exception e) {
//				LOGGER.error("error", e);
//				throw new JobExecutionException("次日批量发送短信异常(发货和签单)",e);
//			}
//		}
//		
//	}