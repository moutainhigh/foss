package com.deppon.foss.module.settlement.writeoff.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IDopStatementDao;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.HomeStatementDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.HomeStatementEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.HomeStatementDto;

/**
 * 家装对账单DAO
 * 
 * @ClassName: DopStatementDao
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2015-12-10 下午8:11:21
 */
public class DopStatementDao extends iBatis3DaoImpl implements IDopStatementDao {
	private static final String NAMESPACE = "foss.stl.DopStatementDao.";

	/**
	 * 按单号查询对账单
	 * 
	 * @author &269052 |zhouyuan008@deppon.com
	 * @date 2015-12-10 下午8:11:21
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HomeStatementEntity> queryHomeStatementByNumber(
			HomeStatementDto dto) {
		List<HomeStatementEntity> list = this.getSqlSession().selectList(
				NAMESPACE + "queryHomeStatementByNumber", dto);
		return list;
	}
	
	/**
	 * 按对账单号去查询对账单明细
	 * 
	 * @author &269052 |zhouyuan008@deppon.com
	 * @date 2015-12-10 下午8:11:21
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HomeStatementDEntity> queryHomeDByStatementBillNo(
			HomeStatementDto homeStatementDto) {
		List<HomeStatementDEntity> list = getSqlSession().selectList(
				NAMESPACE + "queryHomeDByStatementBillNo", homeStatementDto);
		return list;
	}
	
	/**
	 * 更新对账单
	 * 
	 * @author &269052 |zhouyuan008@deppon.com
	 * @date 2015-12-10 下午8:11:21
	 */
	@Override
	public int homeStatementUpdateByStatementBillNo(
			HomeStatementDto homeStatementDto) {
		int updateRows = getSqlSession().update(
				NAMESPACE + "homeStatementUpdateByStatementBillNo",
				homeStatementDto);
		return updateRows;
	}
	
	/**
	 * 根据对账单号去查询部门
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HomeStatementDEntity> getOrgNameByStatementBillNo(
			HomeStatementDto homeStatementDto) {
		List<HomeStatementDEntity> list = getSqlSession().selectList(
				NAMESPACE + "getOrgNameByStatementBillNo", homeStatementDto);
		return list;
	}

}
