package com.deppon.foss.module.settlement.pay.api.server.service;

import java.util.List;

import com.deppon.esb.inteface.domain.payment.AutoPayResult;
import com.deppon.foss.framework.service.IService;

/**
 * 合伙人开单到付运费提现自动付款
 * @author 302346 Jiang Xun
 * @date 2016-05-18 下午4:44:00
 */
public interface IBillAutoPayPtpService extends IService{
	
	/**
	 * 自动付款到PTP
	 * @author 302346 jiang xun
	 * @date 2016-05-20 下午5:43:00
	 * @param autoWithdrawList	
	 * 付款信息列表
	 * @return
	 */
	public void autoPaytoPtp();
	
	/**
	 * 自动付款到PTP,处理结果回调
	 * @author 302346 jiang xun
	 * @date 2016-06-01 下午3:24:00
	 * @param autoWithdrawList	
	 * 付款信息列表
	 * @return
	 */
	public void dealAutoPayResult(List<AutoPayResult> resultList);
	
	/**
	 * 合伙人到付运费自动付款重推方法
	 *@author 231438
	 *@date 2016-06-06 上午8:15:05
	 *@return
	 */
	public void autoPaytoPtpPushAgin();
	/**
	 * 合伙人委托派到PTP方法
	 * @author yan junjie
	 * 
	 */
	public void autoPaytoPtpPDDF();
	
	/**
	 * 合伙人奖励自动返
	 * @author 355019
	 */
	public void autoPaytoPtpReward();

}
