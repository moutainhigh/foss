package com.deppon.foss.module.settlement.closing.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.settlement.closing.api.server.dao.ICollectingPaymentDao;
import com.deppon.foss.module.settlement.closing.api.shared.domain.QueryCollectingPaymentEntity;
import com.deppon.foss.module.settlement.closing.api.shared.domain.ReturnCollectingPaymentEntity;
import com.deppon.foss.module.settlement.closing.api.shared.exception.CollectingPaymentException;
import com.deppon.foss.module.settlement.common.api.shared.dto.CodDO;
/**
 * @author 218392 zhangyongxue
 * @date  2015-09-08 10:08:02
 */
@SuppressWarnings("unchecked")
public class CollectingPaymentDao extends SqlSessionDaoSupport implements ICollectingPaymentDao{
	private static final String NAMESPACE = "com.deppon.foss.module.settlement.closing.api.server.dao.ICollectingPaymentDao.";

	@Override
	public List<ReturnCollectingPaymentEntity> queryCollectingPaymentByConditon(
			QueryCollectingPaymentEntity queryCollectingPaymentEntity) {
		if(queryCollectingPaymentEntity != null){
			//起始页
			int currentPage = queryCollectingPaymentEntity.getCurrentPage();
			//单页显示数量
			int singlePages = queryCollectingPaymentEntity.getSinglePages();
			if(currentPage == -1 || singlePages == -1){
				return this.getSqlSession().selectList(NAMESPACE+"queryCollectingPayment", queryCollectingPaymentEntity);
			}else{
				RowBounds rb = new RowBounds(currentPage, singlePages);
				return this.getSqlSession().selectList(NAMESPACE+"queryCollectingPayment", queryCollectingPaymentEntity, rb);
			}

		}else{
			throw new CollectingPaymentException("传入的查询参数为空......");
		}
	}
	
	@Override
	public List<ReturnCollectingPaymentEntity> queryCollectingPaymentByConditonTotal(
			QueryCollectingPaymentEntity queryCollectingPaymentEntity) {
		
		if(queryCollectingPaymentEntity != null){
				return this.getSqlSession().selectList(NAMESPACE+"queryCollectingPayment", queryCollectingPaymentEntity);
		}else{
			throw new CollectingPaymentException("传入的查询参数为空......");
		}
	}

	@Override
	public Long queryCollectingPaymentTotalItems(
			QueryCollectingPaymentEntity queryCollectingPaymentEntity) {
		if(queryCollectingPaymentEntity != null){
			
			return (Long)this.getSqlSession().selectOne(NAMESPACE + "queryCollectingPaymentTotalItems", queryCollectingPaymentEntity);
			
		}else{
			throw new CollectingPaymentException("传入的查询参数为空......");
		}
	}

	/* 
	 * 根据运单号获取List<ReturnCollectingPaymentEntity>集合
	 * @see com.deppon.foss.module.settlement.closing.api.server.dao.ICollectingPaymentDao#queryWaybillInfoByNos(java.util.List)
	 */
	@Override
	public List<ReturnCollectingPaymentEntity> queryWaybillInfoByNos(
			List<CodDO> codAuditList) {
		return this.getSqlSession().selectList(NAMESPACE + "queryWaybillInfoByNos", codAuditList);
	}

}
