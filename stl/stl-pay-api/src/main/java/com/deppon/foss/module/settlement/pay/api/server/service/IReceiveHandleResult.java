package com.deppon.foss.module.settlement.pay.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.CostcontrolToFossDto;


/**
 * 接受费控结果
 * @author 045738-foss-maojianqiang
 * @date 2012-12-5 下午5:52:23
 */
public interface IReceiveHandleResult extends IService {
	
	/**
	 * 处理付款工作流
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-5 下午5:56:49
	 */
	public void dealPaymentWorkFlow(CostcontrolToFossDto dto);
	
	/**
	 * 处理备用金工作流
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-5 下午5:57:38
	 */
	public void dealPettyCashWorkFlow(CostcontrolToFossDto dto);
	
	/**
	 * 将来源单号和车价传送给TPS
	 * @author 269044
	 * @date 2015-10-27
	 */
	public void sendSourceBillsToTPS(List<BillPayableEntity> payableList);
	
	
	/**
	 * 合伙人提现报账返回异常状态通知ptp红冲提现流水
	 * @author gpz
	 * @date 2016年12月21日
	 */
	public void notifyPtp(CostcontrolToFossDto dto,List<BillPaymentEntity> paymentList);
}
