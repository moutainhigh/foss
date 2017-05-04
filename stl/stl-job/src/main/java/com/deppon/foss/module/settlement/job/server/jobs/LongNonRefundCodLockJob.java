package com.deppon.foss.module.settlement.job.server.jobs;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.settlement.common.api.shared.domain.InterfaceLogEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.INonRefundCodLockService;
import com.deppon.foss.util.UUIDUtils;

/**
 * @author 218392 zyx
 * @date 2016-07-07 19:32:00
 * 长期未退代收货款冻结需求,需求描述如下：
 * （1）90天≤N≤730天，系统每天23:00筛选符合条件的数据，并进行冻结,冻结后的数据进入代收货款支付审核界面并进行展示； 
 * （2）N＞730天，系统每天23:00筛选符合条件的数据，并进行永久冻结，永久冻结后数据进入“代收货款支付审核界面”并进行展示；
 * （3）长期冻结的运单，若需要支付代收货款，只能通过起草异常退代收工作流进行退款；
 * （4）审核状态为：冻结，此类运单不能进行运单更改（给予弹窗提示：该单已被冻结，联系资金安全控制组取消冻结）、不能进行代收货款支付、
 *      不能进行账号修改，资金部取消冻结后方可进行运单更改；审核状态为：永久冻结，此类运单不能进行任何付款操作，不能进行任何运单更改，不能进行账号修改。
 */
public class LongNonRefundCodLockJob extends GridJob{
	/**
	 * 日志
	 */
	private static final Logger logger = LogManager.getLogger(LongNonRefundCodLockJob.class);
	
	/**
	 * 执行
	 */
	@Override
	protected void doExecute(JobExecutionContext context) throws JobExecutionException {
		
		/**
		 * 实例化未退款锁定Service
		 */
		INonRefundCodLockService nonRefundCodLockService;
		try{
			logger.info("代收货款长期未退款冻结开始!");
			nonRefundCodLockService = getBean("nonRefundCodLockService", INonRefundCodLockService.class);
			
			//执行
			nonRefundCodLockService.process();
			
			logger.info("代收货款长期未退款冻结结束!");
			
		} catch (Exception e) {
			StringWriter writer = new StringWriter();
			e.printStackTrace(new PrintWriter(writer));
			logger.info("长期未退款job冻结报错Exception：" + writer.toString());
			//记录失败日志
		}
		
	}

}
