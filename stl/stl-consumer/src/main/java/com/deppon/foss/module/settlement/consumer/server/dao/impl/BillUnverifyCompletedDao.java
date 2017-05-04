package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IBillUnverifyCompletedDao;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillUnverifyComletedResultDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillUnverifyCompletedQueryDto;


/**
 * 未完全核销单据查询Dao实现
 * @author foss-qiaolifeng
 * @date 2013-5-14 下午3:24:32
 */
public class BillUnverifyCompletedDao extends iBatis3DaoImpl implements
		IBillUnverifyCompletedDao {

	private static final String NAMESPACE = "foss.stl.BillUnverifyCompletedQueryDao.";
	
	/** 
	 * 根据客户编码查询该客户的未完全核销的单据，包含预收、预付、应收、应付
	 * @author foss-qiaolifeng
	 * @date 2013-5-14 下午3:24:34
	 * @param dto
	 * @return
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.IBillUnverifyCompletedDao#queryBillUnverifyCompletedList(com.deppon.foss.module.settlement.consumer.api.shared.dto.BillUnverifyCompletedQueryDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillUnverifyComletedResultDto> queryBillUnverifyCompletedList(
			BillUnverifyCompletedQueryDto dto) {
		// 执行查询
		return this.getSqlSession().selectList(
				NAMESPACE + "queryBillUnverifyCompletedList", dto);
	}

	/** 
	 * 根据客户编码查询该客户的所有未完全核销单据总条数
	 * @author foss-qiaolifeng
	 * @date 2013-5-14 下午4:13:05
	 * @param dto
	 * @return
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.IBillUnverifyCompletedDao#queryBillUnverifyCompletedTotals(com.deppon.foss.module.settlement.consumer.api.shared.dto.BillUnverifyCompletedQueryDto)
	 */
	@Override
	public Long queryBillUnverifyCompletedTotals(
			BillUnverifyCompletedQueryDto dto) {
		// 执行查询
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryBillUnverifyCompletedTotals", dto);
	}

}
