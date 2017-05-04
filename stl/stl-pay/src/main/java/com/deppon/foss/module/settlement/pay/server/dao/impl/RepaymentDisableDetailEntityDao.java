package com.deppon.foss.module.settlement.pay.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.pay.api.server.dao.IRepaymentDisableDetailEntityDao;
import com.deppon.foss.module.settlement.pay.api.shared.domain.RepaymentDisableDetailEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentDisableDto;

public class RepaymentDisableDetailEntityDao extends iBatis3DaoImpl implements
		IRepaymentDisableDetailEntityDao {
	private static final String NAMESPACE = "foss.stl.RepaymentDisableDetailEntityDao.";
	/**
	 * 
	 *插入作废明细
	 *
	 * @author 092036-foss-bochenlong
	 * @date 2013-11-19 下午8:14:56 
	 * @param entity
	 */
	@Override
	public void addDisableDetail(RepaymentDisableDetailEntity entity) {
		getSqlSession().insert(NAMESPACE+"addDisableDetail",entity);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<RepaymentDisableDetailEntity> queryDisableDetailByDto(
			BillRepaymentDisableDto dto) {
		return getSqlSession().selectList(NAMESPACE+"queryDisableDetailByDto",dto);
	}


	@Override
	public void updateDisableDetailByDto(BillRepaymentDisableDto dto) {
		getSqlSession().selectList(NAMESPACE+"updateDisableDetailByDto",dto);
	}


}
