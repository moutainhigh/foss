package com.deppon.foss.module.settlement.closing.api.server.service;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.QueryCollectingPaymentEntity;
import com.deppon.foss.module.settlement.closing.api.shared.domain.ReturnCollectingPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CodDO;

/**
 * @author 218392 zhangyongxue
 * @date   2015-09-07 16:52:30
 * 代收货款清单接口
 */
public interface ICollectingPaymentService {
	
	/**
	 * 根据CRM传过来的查询参数，返回CRM想要的结果集合
	 * @param queryCollectingPaymentEntity
	 * @return
	 */
	List<ReturnCollectingPaymentEntity> queryCollectingPaymentByConditon(QueryCollectingPaymentEntity queryCollectingPaymentEntity);
	
	/**
	 * 根据CRM传过来的查询参数，返回CRM想要的结果集合
	 * @param queryCollectingPaymentEntity
	 * @return
	 */
	List<ReturnCollectingPaymentEntity> queryCollectingPaymentByConditonTotal(QueryCollectingPaymentEntity queryCollectingPaymentEntity);
	
	/**
	 * 查询总记录数
	 */
	Long queryCollectingPaymentTotalItems(QueryCollectingPaymentEntity queryCollectingPaymentEntity);

	/**
	 * 根据运单号获取List<ReturnCollectingPaymentEntity>集合
	 * @param codAuditList
	 * @return List<ReturnCollectingPaymentEntity>
	 */
	List<ReturnCollectingPaymentEntity> queryWaybillInfoByNos(
			List<CodDO> codAuditList);
}
