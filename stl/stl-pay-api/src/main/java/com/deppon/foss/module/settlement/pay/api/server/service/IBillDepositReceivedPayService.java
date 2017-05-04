package com.deppon.foss.module.settlement.pay.api.server.service;


import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillingQueryRequestDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.StlBillDetailDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillDepositReceivedPayDto;


/**
 * 预收单接口
 * @author foss-pengzhen
 * @date 2012-11-19 下午4:25:03
 * @since
 * @version
 */
public interface IBillDepositReceivedPayService {
	
	/**
	 * 查询对应的预收单信息
	 * @author foss-pengzhen
	 * @date 2012-11-19 下午3:47:47
	 * @return
	 * @see
	 */
	BillDepositReceivedPayDto queryDepositReceived(
			BillDepositReceivedPayDto billDepositReceivedPayDto,CurrentInfo currentInfo,int start,int limit) ;
	
	/**
	 * 新增预收单
	 * @author foss-pengzhen
	 * @date 2012-11-26 下午7:17:49
	 * @param billDepositReceivedPayDto
	 * @param currentInfo
	 * @return 
	 * @see
	 */
	BillDepositReceivedPayDto addBillDepositReceivedPay(BillDepositReceivedPayDto billDepositReceivedPayDto,CurrentInfo currentInfo);
	
	/**
	 * 根据汇款单号获取汇款信息
	 * @author foss-pengzhen
	 * @date 2013-1-15 下午2:24:30
	 * @return
	 * @see
	 */
	BillDepositReceivedPayDto queryPayRemittanceDetail(BillDepositReceivedPayDto billDepositReceivedPayDto,CurrentInfo currentInfo);

    /**
     * 根据网上支付编码获取汇款信息
     * @param billDepositReceivedPayDto
     * @param currentInfo
     * @return
     */
    BillDepositReceivedPayDto queryOnlinePayDetail(BillDepositReceivedPayDto billDepositReceivedPayDto,
                                                   CurrentInfo currentInfo);

    /**
	 * 导出预收单
	 * @author foss-pengzhen
	 * @date 2012-12-11 下午4:16:38
	 * @param billDepositReceivedPayDto
	 * @param currentInfo
	 * @return 
	 * @see
	 */
	HSSFWorkbook depositReceivedListExport(BillDepositReceivedPayDto billDepositReceivedPayDto,CurrentInfo currentInfo);
	
	/**
	 * 预付单作废
	 * @author foss-pengzhen
	 * @date 2012-11-26 下午6:49:24
	 * @param billDepositReceivedPayDto
	 * @param currentInfo
	 * @return
	 * @see
	 */
	void writeBackBillDepositReceived(BillDepositReceivedPayDto billDepositReceivedPayDto,CurrentInfo currentInfo);
	
	/**
	 * 退预收
	 * @author foss-pengzhen
	 * @date 2012-12-4 上午11:30:08
	 * @param billDepositReceivedPayDto 预收单信息
	 * @param currentInfo 用户信息
	 * @return
	 * @see
	 */
	BillDepositReceivedPayDto backDepositReceived(BillDepositReceivedPayDto billDepositReceivedPayDto,CurrentInfo currentInfo,String operateType);

	/**
	 * 根据付款单号获取预收单
	 *
	 * @author foss-qiaolifeng
	 * @date 2013-3-20 下午7:07:25
	 * @param
	 * @return 成功失败标记
	 * @exception SettlementException
	 * @see
	 */
	List<BillDepositReceivedEntity> queryListByPaymentNo(BillDepositReceivedPayDto dto);
	
	/**
	 * ptp监控查询合伙人预收单总记录数和总金额数
	 * 
	 * @author gpz
	 * @date 2016年8月5日
	 * @param BillDepositReceivedPartnerDto 查询参数
	 */
	StlBillDetailDto countDepositReceivedBills(BillingQueryRequestDto requestDto);
}
