//package com.deppon.foss.module.pickup.express.server.job;
//
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.deppon.foss.framework.server.components.jobgrid.GridJob;
//import com.deppon.foss.module.pickup.sign.api.server.service.ILdpNotSignReportOaService;
//
///**
// * 快递代理外发XX天未签收自动上报OA丢货JOB DMANA-3046
// * 
// * @ClassName: LdpNotSignReportOAJob
// * @author 200664-yangjinheng
// * @date 2014年9月2日 上午8:33:40
// */
//public class LdpNotSignReportOaJob extends GridJob {
//
//	/**
//	 * 记录日志
//	 */
//	private static final Logger LOGGER = LoggerFactory.getLogger(LdpNotSignReportOaJob.class);
//
//	/**
//	 * 自动上报JOB
//	 * 
//	 * @Title: doExecute
//	 * @author 200664-yangjinheng
//	 * @date 2014年9月2日 上午8:35:42
//	 * @throws JobExecutionException
//	 */
//	protected void doExecute(JobExecutionContext context) throws JobExecutionException {
//		try {
//
//			// 上报开始
//			LOGGER.info("快递代理外发XX天未签收自动上报OA丢货     LdpNotSignReportOaJob begin");
//			// 快递代理外发XX天未签收自动上报OA丢货接口
//			ILdpNotSignReportOaService ldpNotSignReportOaService = getBean("ldpNotSignReportOaService", ILdpNotSignReportOaService.class);
//			
//			ldpNotSignReportOaService.reportOALessGoods();
//			
//			// 上报结束
//			LOGGER.info("快递代理外发XX天未签收自动上报OA丢货     LdpNotSignReportOaJob End");
//		} catch (Exception e) {
//			LOGGER.error("error", e);
//			throw new JobExecutionException("快递代理外发XX天未签收自动上报OA丢货", e);
//		}
//	}
//
//	
//
//}
