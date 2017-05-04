package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.settlement.closing.api.server.service.ICollectingPaymentService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.QueryCollectingPaymentEntity;
import com.deppon.foss.module.settlement.closing.api.shared.domain.ReturnCollectingPaymentEntity;
import com.deppon.foss.module.settlement.closing.api.shared.exception.CollectingPaymentException;
import com.deppon.foss.module.settlement.closing.server.dao.impl.CollectingPaymentDao;
import com.deppon.foss.module.settlement.common.api.shared.dto.CodDO;
/**
 * @author 218392 zhangyongxue
 * @date   2015-09-07 17:01:30
 * 代收货款清单查询service
 */
public class CollectingPaymentService implements ICollectingPaymentService{
	
	/**
	 * 注入collectingPaymentDao
	 */
	private CollectingPaymentDao collectingPaymentDao;
	
	public void setCollectingPaymentDao(CollectingPaymentDao collectingPaymentDao) {
		this.collectingPaymentDao = collectingPaymentDao;
	}

	/**
	 * 代收货款清单查询
	 */
	@Override
	public List<ReturnCollectingPaymentEntity> queryCollectingPaymentByConditonTotal(
			QueryCollectingPaymentEntity queryCollectingPaymentEntity) {
		if(queryCollectingPaymentEntity.getCustomerCode() != null && 
				!StringUtils.equals("", queryCollectingPaymentEntity.getCustomerCode())){
				return collectingPaymentDao.queryCollectingPaymentByConditonTotal(queryCollectingPaymentEntity);
			
		}else{
			throw new CollectingPaymentException("传入的查询参数为空...");
		}
		
	}
	
	/**
	 * 代收货款清单查询
	 */
	@Override
	public List<ReturnCollectingPaymentEntity> queryCollectingPaymentByConditon(
			QueryCollectingPaymentEntity queryCollectingPaymentEntity) {
		if(queryCollectingPaymentEntity.getCustomerCode() != null && 
				!StringUtils.equals("", queryCollectingPaymentEntity.getCustomerCode())){
				
				return collectingPaymentDao.queryCollectingPaymentByConditon(queryCollectingPaymentEntity);
			
		}else{
			throw new CollectingPaymentException("传入的查询参数为空...");
		}
		
	}

	@Override
	public Long queryCollectingPaymentTotalItems(
			QueryCollectingPaymentEntity queryCollectingPaymentEntity) {
		if(queryCollectingPaymentEntity != null){
			
			return collectingPaymentDao.queryCollectingPaymentTotalItems(queryCollectingPaymentEntity);
			
		}else{
			throw new CollectingPaymentException("传入的查询参数为空...");
		}
	}

	/* 
	 * 根据运单号获取List<ReturnCollectingPaymentEntity>集合
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.ICollectingPaymentService#queryWaybillInfoByNos(java.util.List)
	 */
	@Override
	public List<ReturnCollectingPaymentEntity> queryWaybillInfoByNos(
			List<CodDO> codAuditList){
		if(CollectionUtils.isNotEmpty(codAuditList)){
			return collectingPaymentDao.queryWaybillInfoByNos(codAuditList);
		}
		return null;
	}
}
