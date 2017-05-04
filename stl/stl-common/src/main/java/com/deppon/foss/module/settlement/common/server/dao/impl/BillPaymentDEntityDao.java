package com.deppon.foss.module.settlement.common.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.server.dao.IBillPaymentDEntityDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentDEntity;

/**
 * 付款单明细 dao
 * @author 099995-foss-wujiangtao
 * @date 2013-3-17 下午3:21:26
 * @since
 * @version
 */
public class BillPaymentDEntityDao extends iBatis3DaoImpl implements IBillPaymentDEntityDao {
	
	/**
	 * 命名空间
	 */
	private static final String NAMESPACES="foss.stl.BillPaymentDEntityDao.";

	/**
	 * 新增付款单明细
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-3-17 下午3:08:02
	 * @param entity
	 * @return
	 */
	@Override
	public int add(BillPaymentDEntity entity) {
		return this.getSqlSession().insert(NAMESPACES+"insert",entity);
	}

	/** 
	 * 根据付款单号查询付款单明细
	 * @author foss-qiaolifeng
	 * @date 2013-5-13 上午9:42:08
	 * @param paymentNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillPaymentDEntity> queryPaymentDEntityListByPaymentNo(
			String paymentNo) {
		// 根据付款单号查询付款单明细
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("paymentNo", paymentNo);
		return this.getSqlSession().selectList(NAMESPACES+"selectPaymentDEntityListByPaymentNo",map);
	}

}
