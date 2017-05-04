package com.deppon.foss.module.settlement.consumer.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillInvoiceDto;

/**
 * 配合发票，查询运单及小票接口
 * @author 163576
 * @date 2014-7-9 10:42:20
 * 
 */
public interface IInvoiceRegisterDao {

	/**
	 * 查询运单集合
	 * @author 163576
	 * @date 2014-7-9 10:42:20
	 * @param waybillNos
	 * @return
	 */
	List<WaybillInvoiceDto> queryWaybillInfoForInvoiceList(List<String> waybillNos);
	
	/**
	 * 查询小票集合及运单中包括小票集合
	 * @author 163576
	 * @date 2014-7-9 10:42:20
	 * @param waybillNos 与小票号两者至少必录其一
	 * @param otherRevenues 与运单号两者至少必录其一
	 * @return
	 */
	List<WaybillInvoiceDto> queryOtherRevenueInfoForInvoiceList(List<String> waybillNos,List<String> otherRevenues);

		/**
	 * 运单关联小票
	 *
	 * @return the string
	 * @author ddw
	 * @date 2015-04-09
	 */
	List<String> queryOtherRevenueNosByWaybillNos(List<String> waybillNos);
	
	/**
	 * 查询合伙人对账单结合
	 * @param hhStatementNoList
	 * @return ddw
	 */
	List<WaybillInvoiceDto> queryHhStatementInfoForInvoiceList(
				List<String> hhStatementNoList);
	/**
	 * 根据运单号查询发货客户是否是事后折客户  快递
	 * @param waybillNo
	 * @return 329757
	 */
	List<String> queryIsDisCountByWaybill(String waybillNo);

	/**
	 * 根据运单号查询发货客户是否是事后折客户 -零担
	 * @param waybillNo
	 * @return 329757
	 */
	int queryIsDisCountNoExp(String waybillNo);

	/**
	 * 查询零担是否存在折扣应付单--零担
	 * @param waybillNo
	 * @return 329757
	 */
	int queryDisCountNoexe(String waybillNo);

	/**
	 * add by 329757
	 * 判断运客户编码是否存在折扣--快递
	 * @param waybillNo
	 * @return
	 */
	int queryDisCountExe(String waybillNo);
}
