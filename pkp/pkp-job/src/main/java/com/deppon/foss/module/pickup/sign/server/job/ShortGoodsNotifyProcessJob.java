//package com.deppon.foss.module.pickup.sign.server.job;
//
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.deppon.foss.framework.server.components.jobgrid.GridJob;
//import com.deppon.foss.module.pickup.sign.api.server.service.IShortGoodsNotifyJMSService;
//
///***
// * @clasaName:com.deppon.foss.module.pickup.sign.server.job.ShortGoodsNotifyProcessJob
// * @author: foss-yuting
// * @description: 新增内物短少差错-FOSS自动上报	每10分钟执行一次   </br>
// * @date:2014年12月3日 下午2:27:21
// */
//public class ShortGoodsNotifyProcessJob extends GridJob {
//	private static final Logger LOGGER = LoggerFactory.getLogger(ShortGoodsNotifyProcessJob.class);
//	@Override
//	protected void doExecute(JobExecutionContext context) throws JobExecutionException
//	{
//		try {
//			LOGGER.info("内物短少差错上报 start");
//			IShortGoodsNotifyJMSService shortGoodsNotifyService = getBean("shortGoodsNotifyService", IShortGoodsNotifyJMSService.class);
//			shortGoodsNotifyService.processNotifyShortGoods();
//			LOGGER.info("内物短少差错上报  end");
//		} catch (Exception e) {
//			LOGGER.error("error", e);
//			throw new JobExecutionException("内物短少差错异常",e);
//		}
//	}
//	
//}