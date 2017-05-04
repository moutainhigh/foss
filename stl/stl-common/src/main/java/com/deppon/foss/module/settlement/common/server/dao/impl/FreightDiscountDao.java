package com.deppon.foss.module.settlement.common.server.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.server.dao.IFreightDiscountDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;

public class FreightDiscountDao extends iBatis3DaoImpl implements IFreightDiscountDao {

	private static final String NAMESPACE = "foss.stl.FreightDiscountDao.";
	
	/**
	 * 查询客户合同信息
	 * @param customerCode
	 * @return
	 */
	@Override
	public List<Integer> queryCustomerContract(Map<String,Object> contractMap) {
		List<Integer> list = new ArrayList<Integer>();
		Object obj = this.getSqlSession().selectOne(NAMESPACE + "queryCustomerContract", contractMap);
		list.add(Integer.valueOf(obj.toString()));
		return list;
	}

	/**
	 * 查询折扣应付
	 * @param contractMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPayableEntity> queryDiscountPayableBill(Map<String, Object> contractMap) {
		List<BillPayableEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryDiscountPayableBill", contractMap);
		return list;
	}

	/**
	 * 查询客户折扣率
	 * @param contractMap
	 * @return
	 */
	@Override
	public List<Integer> queryCustomerDiscountRate(Map<String, Object> contractMap) {
		List<Integer> list = new ArrayList<Integer>();
		Object obj = this.getSqlSession().selectOne(NAMESPACE + "queryCustomerDiscountRate", contractMap);
		list.add(Integer.valueOf(obj.toString()));
		return list;
	}

	/**
	 * 新增零担事后折应付
	 * @param contractMap
	 */
	@Override
	public void createFreightDiscountPayableBill(Map<String, Object> contractMap) {
		this.getSqlSession().insert(NAMESPACE + "createFreightDiscountPayableBill", contractMap);
	}
	
}
