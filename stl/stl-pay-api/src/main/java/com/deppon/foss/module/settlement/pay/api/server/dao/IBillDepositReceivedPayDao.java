package com.deppon.foss.module.settlement.pay.api.server.dao;

import java.util.List;

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
public interface IBillDepositReceivedPayDao {
	
	/**
	 * 根据日期等参数查询对应的预收单信息
	 * @author foss-pengzhen
	 * @date 2012-11-19 下午3:47:47
	 * @return
	 * @see
	 */
	List<BillDepositReceivedEntity> queryDepositReceivedParams(
			BillDepositReceivedPayDto billDepositReceivedPayDto,
			int start,int limit);
	/**
	 * 根据日期等参数查询对应的预收单信息(不分页)
	 * @author foss-pengzhen
	 * @date 2012-11-19 下午3:47:47
	 * @return
	 * @see
	 */
	List<BillDepositReceivedEntity> queryDepositReceivedParam(
			BillDepositReceivedPayDto billDepositReceivedPayDto) ;
	/**
	 * 根据日期等参数查询对应的预收单总条数
	 * @author foss-pengzhen
	 * @date 2012-11-19 下午3:47:47
	 * @return
	 * @see
	 */
	Long queryDepositReceivedParamsCount(BillDepositReceivedPayDto billDepositReceivedPayDto);
	
	/**
	 * 根据预收单参数查询对应的预收单信息
	 * @author foss-pengzhen
	 * @date 2012-11-19 下午3:47:47
	 * @return
	 * @see
	 */
	List<BillDepositReceivedEntity> queryDepositReceivedNos(
			List<String> depositReceivedNos,CurrentInfo currentInfo,
			int start,int limit) ;
	
	/**
	 * 根据预收单参数查询对应的预收单总条数
	 * @author foss-pengzhen
	 * @date 2012-11-19 下午3:47:47
	 * @return
	 * @see
	 */
	BillDepositReceivedPayDto queryDepositReceivedNosCount(BillDepositReceivedPayDto billDepositReceivedPayDto,CurrentInfo currentInfo);
	
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
	 * 根据预收单参数查询对应的预收单总条数
	 * @author foss-maojianqiang
	 * @date 2014-05-19
	 * @return
	 * @see
	 */
	public BillDepositReceivedPayDto queryDepositReceivedParamsCountAndAmount(BillDepositReceivedPayDto billDepositReceivedPayDto);
	
	/**
	 * ptp监控查询合伙人预收单总记录数和总金额数
	 * 
	 * @author gpz
	 * @date 2016年8月5日
	 */
	StlBillDetailDto countDepositReceivedBills(BillingQueryRequestDto requestDto);
	
}
