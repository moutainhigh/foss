package com.deppon.foss.module.settlement.closing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.QueryCollectingPaymentEntity;
import com.deppon.foss.module.settlement.closing.api.shared.domain.ReturnCollectingPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CodDO;

/**
 * 
 * @author 218392 张永雪
 * @date 2015-09-07  17:37:18
 * 代收货款清单Dao
 */
public interface ICollectingPaymentDao {
	/**
	 * 根据CRM传过来的查询参数，返回CRM想要的结果集合
	 * @param queryCollectingPaymentEntity
	 * @return
	 */
	List<ReturnCollectingPaymentEntity> queryCollectingPaymentByConditon(QueryCollectingPaymentEntity queryCollectingPaymentEntity);
	
	/**
	 * 不按分页查询，供统计总记录数以及合计使用
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
