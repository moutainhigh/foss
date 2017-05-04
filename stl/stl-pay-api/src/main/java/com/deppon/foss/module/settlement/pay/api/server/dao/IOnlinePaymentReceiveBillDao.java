package com.deppon.foss.module.settlement.pay.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableOnlineQueryDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillSOADOnlineResultDto;

/**
 * 
 * 网上支付查询应收单信息DAO接口
 * 
 * @author 088933-foss-zhangjiheng
 * @date 2012-11-19 下午3:59:49
 */
public interface IOnlinePaymentReceiveBillDao {
	/**
	 * 
	 * 网上支付查询未核销的应收单信息，按客户编码查询
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-19 下午3:46:28
	 */
	List<BillReceivableEntity> queryReceiveBillInfoByCustomer(
			BillReceivableOnlineQueryDto queryDto, int pageNo, int pageSize);
	/**
	 * 
	 * 网上支付查询未核销的应收单信息，按客户编码查询求,总条数
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-19 下午3:46:28
	 */
	BillSOADOnlineResultDto queryCountReceiveBillInfoByCustomer(
			BillReceivableOnlineQueryDto queryDto);

	/**
	 * 
	 * 网上支付查询未核销的应收单信息，按运单号+手机号码查询
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-19 下午3:46:28
	 */
	List<BillReceivableEntity> queryReceiveBillInfoByWaybillNo(
			BillReceivableOnlineQueryDto queryDto);

	/**
	 * 
	 * 网上支付查询未核销的应收单信息，按业务日期查询查询
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-19 下午3:46:28
	 */
	List<BillReceivableEntity> queryReceiveBillInfoByDate(
			BillReceivableOnlineQueryDto queryDto, int pageNo, int pageSize);
	
	/**
	 * 
	 * 网上支付查询未核销的应收单信息，按业务日期查询查询,求总条数
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-19 下午3:46:28
	 */
	BillSOADOnlineResultDto queryCountReceiveBillInfoByDate(
			BillReceivableOnlineQueryDto queryDto);
	
	/**
	 * 
	 * 网上支付查询未核销的应收单信息，按运单号+客户编码
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-19 下午3:46:28
	 */
	List<BillReceivableEntity>  queryReceiveBillInfoByWaybillAndCus(
			BillReceivableOnlineQueryDto queryDto);
	
	/**
	 * 锁定应收单信息
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-23 上午9:05:22
	 */
	int updateReceiveBillInfoForLock(BillReceivableOnlineQueryDto queryDto);
}
