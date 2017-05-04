package com.deppon.foss.module.settlement.pay.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.pay.api.shared.dto.BillCashCashierConfirmDto;

/**
 * 确认收银service
 * @author foss-pengzhen
 * @date 2012-12-13 下午2:50:05
 * @since
 * @version
 */
public interface IBillCashCashierConfirmPayDao {
	
	/**
	 * 按日期参数查询单据信息
	 * @author foss-pengzhen
	 * @date 2012-12-13 下午3:14:43
	 * @return
	 * @see
	 */
	List<BillCashCashierConfirmDto> querybillCashCashierConfirmParams(BillCashCashierConfirmDto billCashCashierConfirmDto);

	/**
	 * 按单号查询单据信息
	 * @author foss-pengzhen
	 * @date 2012-12-13 下午3:14:43
	 * @return
	 * @see
	 */
	List<BillCashCashierConfirmDto> querybillCashCashierConfirmNos(BillCashCashierConfirmDto billCashCashierConfirmDto);
	
	/**
	 * 按运单号查询单据信息
	 * @author foss-pengzhen
	 * @date 2012-12-13 下午3:14:43
	 * @return
	 * @see
	 */
	List<BillCashCashierConfirmDto> querybillCashCashierConfirmWayBillNos(
			BillCashCashierConfirmDto billCashCashierConfirmDto);
	
	/**
	 * 按id号查询单据信息
	 * @author foss-pengzhen
	 * @date 2012-12-13 下午3:14:43
	 * @return
	 * @see
	 */
	List<BillCashCashierConfirmDto> querybillCashCashierConfirmIds(List<String> billCashCashierConfirmIds);
	
	/**
	 * 按日期参数查询预收单信息
	 * @author foss-pengzhen
	 * @date 2012-12-13 下午3:14:43
	 * @return
	 * @see
	 */
	List<BillCashCashierConfirmDto> queryDepositReceivedParams(
			BillCashCashierConfirmDto billCashCashierConfirmDto);
	
	/**
	 * 按日期参数查询还款单信息
	 * @author foss-pengzhen
	 * @date 2012-12-13 下午3:14:43
	 * @return
	 * @see
	 */
	List<BillCashCashierConfirmDto> queryRepaymentPayParams(
			BillCashCashierConfirmDto billCashCashierConfirmDto);
	
	/**
	 * 按日期参数查询现金收款单信息
	 * @author foss-pengzhen
	 * @date 2012-12-13 下午3:14:43
	 * @return
	 * @see
	 */
	List<BillCashCashierConfirmDto> queryCashCollectionPayParams(
			BillCashCashierConfirmDto billCashCashierConfirmDto);

	/**
	 * 查询运单号
	 * @author foss-pengzhen
	 * @date 2013-6-28 上午10:54:33
	 * @return
	 * @see
	 */
	List<BillCashCashierConfirmDto> cashCashierConfirmDetailWaybillNo(String waybillNo,String writeoffType);

	/**
	 * 批量查询运单号
	 * @author foss-pengzhen
	 * @date 2013-6-28 上午10:54:33
	 * @return
	 * @see
	 */
	List<BillCashCashierConfirmDto> cashCashierConfirmDetailWaybillNos(
			BillCashCashierConfirmDto billCashCashierConfirmDto);

	/**
	 * 
	 * 根据银联交易流水号查询
	 * @author 045738-foss-maojianqiang
	 * @date 2013-7-24 上午9:08:51
	 * @param billCashCashierConfirmDto
	 * @return
	 */
	List<BillCashCashierConfirmDto> querybillCashCashierConfirmBatchNos(
			BillCashCashierConfirmDto billCashCashierConfirmDto);
    /**
     * 查询未收银确认的代收货款相关单据
     * @param billCashCashierConfirmDto
     * @return 单号list
     */
    List<String> queryUnconfirmedCodRelatedBill(BillCashCashierConfirmDto billCashCashierConfirmDto);
}
