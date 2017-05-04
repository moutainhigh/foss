package com.deppon.foss.module.settlement.consumer.api.server.service;

import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillInvoiceDto;
import com.deppon.foss.module.settlement.consumer.api.shared.vo.InvoiceRegisterVo;

/**
 * 定额发票登记服务
 * @author 163576
 * @date 2014-06-6 下午1:10:23
 */
public interface IInvoiceRegisterService {

	/**
	 * 验证运单及小票，并获得运单及小票总金额，可开票金额
	 * 查询运单小票总金额，已开票金额信息
	 * 根据输入的运单号 小票号，查询运单及小票的总金额，及调用FIMS发票系统查询运单及小票已开票金额
	 * 可开票金额 = 总金额 - 已开票金额
	 * 返回 InvoiceRegisterVo(totleAmount,openAmount,isSuccess),isSuccess false标示验证不通过
	 * @author 163576
	 * @date 2014-06-6 下午1:10:23
	 * @param vo
	 * @return
	 */
	InvoiceRegisterVo validateAndQueryInvoiceAmounts(InvoiceRegisterVo vo,CurrentInfo cInfo);
	
	
	/**
	 * 保存定额发票信息-发送至发票系统.
	 * 将本次开票金额，开票员，小票列表，运单列表(运单/小票包括出发营业部，
	 * 到达营业部，运单号，运单金额) 发送至FIMS发票系统
	 * 返回InvoiceRegisterVo的isSuccess true 申请成功
	 * @author 163576
	 * @date 2014-06-6 下午1:10:23
	 * @param vo
	 * @param cInfo
	 * @return
	 */
	InvoiceRegisterVo registerInvoice(InvoiceRegisterVo vo,CurrentInfo cInfo);
	
	
	/**
	 * 验证定额发票信息
	 */
	
	/**
	 * 根据运单号或者小票号集合返回运单及小票集合
	 * 
	 * 在发票管理系统中输入运单号（零担、快递代理）或小票单号，返回运单或小票的列表信息，用于发票开具。
	 * 如果运单中包括小票，则该小票也要返回。
	 * @author 163576
	 * @date 2014-07-10 10:10:23
	 * @param waybillNos
	 * @param otherRevenues
	 * @return
	 * @throws com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException.SettlementException
	 */
	WaybillInvoiceDto queryWaybillReceiptInfoForFPList(List<String> waybillNos, List<String> mergeWaybillNos,List<String> otherRevenues,List<String> hhStatementNoList);

	/**
	 * 运单关联小票
	 *
	 * @return the string
	 * @author ddw
	 * @date 2015-04-09
	 */
	InvoiceRegisterVo queryOtherRevenueNosByWaybillNos(InvoiceRegisterVo vo);
}
