package com.deppon.foss.module.settlement.common.api.server.service;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentDEntity;

/**
 * 付款单明细Service 接口
 * @author 099995-foss-wujiangtao
 * @date 2013-3-17 下午3:05:28
 * @since
 * @version
 */
public interface IBillPaymentDService {
	/**
	 *  新增付款单明细
	 *  
	 * @author 099995-foss-wujiangtao
	 * @date 2013-3-17 下午3:06:10
	 * @param entity
	 */
	void  addBillPaymentD(BillPaymentDEntity entity);
	
	/**
	 * 根据付款单号查询付款单明细
	 *
	 * @author foss-qiaolifeng
	 * @date 2013-5-13 上午9:38:09
	 * @param
	 * @return 成功失败标记
	 * @exception SettlementException
	 * @see
	 */
	List<BillPaymentDEntity> queryPaymentDEntityListByPaymentNo(String paymentNo);
	
	/**
	 * 根据运单号、付款单号查运单明细
	 * @author foss-231434-bieyexiong
	 * @date 2016-3-21 下午15:35:26
	 */
	BillPaymentDEntity queryPaymentDEntityByWaybillNo(String waybillNo,String paymentNo);
	
	
}
