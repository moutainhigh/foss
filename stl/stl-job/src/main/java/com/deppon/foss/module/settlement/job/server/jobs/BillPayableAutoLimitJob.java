package com.deppon.foss.module.settlement.job.server.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;

/**
 * 长期未支付有效应付单自动限制付款
 * @author 340778-foss-zf
 * @date 2016-7-20 下午2:31:48
 * @description　1	N（运单开单时间）>750天，应付单生效状态为“已生效”，支付状态为“未支付”，对符合以上条件的应付单进行限制付款，涉及应付单子类型包括“偏线外发成本、偏线其他应付、快递代理外发成本、快递代理其他应付、装卸费、应付折扣、零担应付折扣、外请车首款、外请车尾款、整车首款、整车尾款、空运出发代理、空运到达代理、航空公司运费、空运其他应付、理赔、退运费、临时租车应付、包装应付、其他包装应付、服务补救”等21个单据类型
 * 2	系统每天00:00检索符合条件的数据，并进行限制付款，被限制付款的明细仍在“应付单管理”界面展示 
 */
public class BillPayableAutoLimitJob extends GridJob{

	private static final Logger LOGGER = LoggerFactory.getLogger(BillPayableAutoLimitJob.class);
	
	IBillPayableService billPayableService;
	
	@Override
	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {
		try{
			LOGGER.info("---------------------长期未支付有效应付单自动限制付款JOB开始：-----------------------------");
			//TODO  长期未支付有效应付单自动限制付款业务逻辑实现类
			billPayableService = getBean("billPayableService",IBillPayableService.class);
			//启动业务实现类入口
			billPayableService.updateBillPayableAutoLimit();
			LOGGER.info("---------------------长期未支付有效应付单自动限制付款JOB结束：-----------------------------");
		}catch(Exception e){
			LOGGER.error("error", e);
			throw new JobExecutionException("长期未支付有效应付单自动限制付款异常",e);
		}
	}
}
