package com.deppon.foss.module.settlement.consumer.api.server.service;

import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.PaymentSettlementDto;

/**
 * 
 * 签收结算货款服务
 * 1、接送货确认货款结清（到付运费结转临欠/月结）;接送货：通知客户  到付运费结转临欠/月结（反操作）
 * 2、实收货款和反实收货款服务
 * @author 099995-foss-wujiangtao
 * @date 2012-10-13 上午11:17:42
 * @since
 * @version
 */
public interface IPaymentSettlementService {
	/**
	 * 到付运费结转临欠/月结
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-13 上午11:18:58
	 * @param  运单号：waybillNo 付款方式：paymentType
	 * @param  到达部门编码：destOrgCode  到达部门名称：destOrgName
	 * @param  客户编码：customerCode 客户名称：customerName
	 * @param  业务日期：businessDate
	 * @return
	 * @see
	 */
	void confirmToBillReceivable(PaymentSettlementDto dto,CurrentInfo currentInfo);
	
	/**
	 * 反到付运费结转临欠/月结 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-13 上午11:19:38
	 * @param   运单号：waybillNo
	 * @param   到达部门编码：destOrgCode
	 * @param   到达部门名称：destOrgName
	 * @param   业务日期：businessDate
	 * @param   付款方式paymentType------签收之后，付款方式发生改变才能进行 反到付运费结转临欠/月结
	 * @return
	 * @see
	 */
	void reversConfirmToBillReceiveable(PaymentSettlementDto dto,CurrentInfo currentInfo);
	
	/**
	 * 确认付款 (客户付给公司的费用)
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-13 上午11:23:26
	 * @param    运单号：waybillNo 付款方式：paymentType 到达部门编码：destOrgCode   
	 * @param    到达部门名称：destOrgName 客户编码：customerCode  
	 * @param    客户名称：customerName 业务日期：businessDate
	 * @param    到达实收单号: sourceBillNo
	 * @return   
	 * @see
	 */
	String confirmToPayment(PaymentSettlementDto dto,CurrentInfo currentInfo);
	
	/**
	 * 反确认付款 (客户付给公司的费用)
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-13 上午11:24:13
	 * @param  运单号：waybillNo
	 * @param  到达部门编码：destOrgCode
	 * @param  到达部门名称：destOrgName
	 * @param  到达实收单号: sourceBillNo
	 * @return
	 * @see
	 */
	void reversConfirmPayment(PaymentSettlementDto dto,CurrentInfo currentInfo);
	
	/**
	 * 
	 * ptp合伙人冲销
	 * @author 043258-foss-zhaobin
	 * @date 2016-1-11 下午2:30:54
	 */
	void confirmPTPPaymentAndWriteOff(PaymentSettlementDto dto,CurrentInfo currentInfo);
	
	
	/**
	 * 到达是否网上已支付校验
	 * @param waybillNo 运单号
	 * @param billType 应收单单据类型
	 * @param payType 还款单付款方式
	 * @return
	 */
	public boolean arriveOnlinePay(String waybillNo, String billType, String payType);
	/**
	 * NCI---Pos刷卡---释放交易对应付款单的金额
	 * @param dto
	 * @param info
	 */
    void reversPosCard(PaymentSettlementDto dto,CurrentInfo info);
    /**
	 * 判断运费是否已经货款结清 经过和接送货沟通，只需要判断到付客户的应收单是否货款结清
	 * （到达应收付款方式为到付，而代收货款应收单付款方式原本就为：到付）进入，
	 *          
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-4 上午10:10:07
	 * @param list
	 * @return
	 * 修改于2016-12-20(将实现类里面的private改为public)为了走foss原来的逻辑
	 */
	String decideWaybillIsSettle(List<BillReceivableEntity> list);
}
