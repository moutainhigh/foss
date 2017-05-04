package com.deppon.foss.module.settlement.pay.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.pay.api.server.dao.IRepaymentDisableApplicationEntityDao;
import com.deppon.foss.module.settlement.pay.api.shared.domain.RepaymentDisableApplicationEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentDisableDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentDisableResultDto;

/**
 *作废申请DAO
 *
 *
 * @author 092036-foss-bochenlong
 * @date 2013-11-19 下午6:45:38
 */
public class RepaymentDisableApplicationEntityDao extends iBatis3DaoImpl implements
		IRepaymentDisableApplicationEntityDao {
	private static final String NAMESPACE = "foss.stl.RepaymentDisableApplicationEntityDao.";

	/**
	 * 
	 *添加申请作废记录
	 *
	 * @author 092036-foss-bochenlong
	 * @date 2013-11-19 下午4:30:18 
	 * @param dto
	 */
	@Override
	public void addDisableApplication(RepaymentDisableApplicationEntity entity) {
		getSqlSession().insert(NAMESPACE+"addDisableApplication",entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RepaymentDisableApplicationEntity> queryDisableApplicationByDto(
			BillRepaymentDisableDto dto) {
		
		return getSqlSession().selectList(NAMESPACE+"queryDisableApplicationByDto",dto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RepaymentDisableApplicationEntity> queryDisableApplicationByDto(
			BillRepaymentDisableDto dto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(NAMESPACE+"queryDisableApplicationByDto",dto,rowBounds);
	}
	
	@Override
	public void approveApply(BillRepaymentDisableDto dto) {
		getSqlSession().update(NAMESPACE+"approveApply", dto);
	}

	@Override
	public BillRepaymentDisableResultDto queryResultDto(
			BillRepaymentDisableDto dto) {
		
		return (BillRepaymentDisableResultDto) getSqlSession().selectOne(NAMESPACE+"BillRepaymentDisableResultDto",dto);
	}
}
