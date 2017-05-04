package com.deppon.foss.module.settlement.common.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentDEntity;

/**
 * 付款单明细Dao 接口
 * @author 099995-foss-wujiangtao
 * @date 2013-3-17 下午3:02:25
 * @since
 * @version
 */
public interface IBillPaymentDEntityDao {
	/**
	 * 新增付款单明细
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-3-17 下午3:03:08
	 * @param entity
	 * @return
	 */
	int add(BillPaymentDEntity entity);
	
	/**
	 * 根据付款单号查询付款单明细
	 *
	 * @author foss-qiaolifeng
	 * @date 2013-5-13 上午9:38:09
	 * @param
	 * @return 成功失败标记
	 * @exception 
	 * @see
	 */
	List<BillPaymentDEntity> queryPaymentDEntityListByPaymentNo(String paymentNo);
}
