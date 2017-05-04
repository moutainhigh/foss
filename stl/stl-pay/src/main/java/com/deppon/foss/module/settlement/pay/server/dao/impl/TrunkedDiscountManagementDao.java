package com.deppon.foss.module.settlement.pay.server.dao.impl;

import java.util.List;

import com.deppon.foss.module.settlement.pay.api.shared.dto.TrunkedDiscountManagementDto;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.pay.api.server.dao.ITrunkedDiscountManagementDao;
import com.deppon.foss.module.settlement.pay.api.shared.domain.TrunkDiscountManEntity;

/**
 * 
 * @ClassName: TrunkedDiscountManagementDao
 * @Description: TODO(零担事后折折扣管理Dao)
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2015-9-19 下午1:57:53
 * 
 */
public class TrunkedDiscountManagementDao extends iBatis3DaoImpl implements
		ITrunkedDiscountManagementDao {
	private static final String NAMESPACE = "foss.pay.trunkedDiscountManagementDao.";

	/**
	 * 按客户去查询折扣单
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TrunkDiscountManEntity> queryTrunkedDiscountByCust(
			TrunkedDiscountManagementDto dto, int start, int limit) {
		RowBounds rb = new RowBounds(start, limit);
		List<TrunkDiscountManEntity> lists = this.getSqlSession().selectList(
				NAMESPACE + "getTrunkDiscountManagementByCust", dto, rb);
		return lists;
	}

	/**
	 * 按客户去查询总记录
	 */
	@Override
	public int queryCountDiscountByCust(TrunkedDiscountManagementDto dto) {
		int count = (Integer) this.getSqlSession().selectOne(
				NAMESPACE + "getCountByCust", dto);
		return count;
	}

	/**
	 * 按单号去查询折扣单
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TrunkDiscountManEntity> queryTrunkedDiscountByNumber(
			TrunkedDiscountManagementDto dto, int start, int limit) {
		RowBounds rb = new RowBounds(start, limit);
		List<TrunkDiscountManEntity> lists = this.getSqlSession().selectList(
				NAMESPACE + "getTrunkDiscountManagementByNumber", dto, rb);
		return lists;
	}

	/**
	 * 按单号查询总记录
	 */
	@Override
	public int queryCountDiscountByNumber(TrunkedDiscountManagementDto dto) {
		int count = (Integer) this.getSqlSession().selectOne(
				NAMESPACE + "getCountByNumber", dto);
		return count;
	}
}
