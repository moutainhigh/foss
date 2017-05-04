package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IBillPayCODOnlineDao;

public class BillPayCODOnlineDao extends iBatis3DaoImpl implements IBillPayCODOnlineDao {

	public static final String NAMESPACE = "foss.stl.BillPayCODOnlineDao.";
	
	/**
	 * 批量插入代收货款付款单明细
	 * @author ddw
	 * @date 2015-10-15
	 */
	@Override
	public int addCodPaymentD(Map<String,Object> codMap) {
		int addPaymentDRow = getSqlSession().insert(NAMESPACE + "addCodPaymentD", codMap);
		return addPaymentDRow;
	}

	/**
	 * 批量插入代收货款付款单
	 * @author ddw
	 * @date 2015-10-15
	 */
	@Override
	public int addCodPayment(Map<String,Object> codMap) {
		int addPaymentRow = getSqlSession().insert(NAMESPACE + "addCodPayment", codMap);
		return addPaymentRow;
	}

	/**
	 * 批量更新代收货款应付单
	 * @author ddw
	 * @date 2015-10-15
	 */
	@Override
	public int updateCodPayable(Map<String,Object> codMap) {
		int updatePayableRow = getSqlSession().update(NAMESPACE + "updateCodPayable", codMap);
		return updatePayableRow;
	}

}
