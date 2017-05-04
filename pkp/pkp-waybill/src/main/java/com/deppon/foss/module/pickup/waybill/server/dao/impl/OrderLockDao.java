package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IOrderLockDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.OrderLockEntity;
import com.deppon.foss.util.UUIDUtils;

public class OrderLockDao extends iBatis3DaoImpl  implements IOrderLockDao{

	private static final String NAMESPACE = "foss.pkp.OrderLockEntityMapper.";

	
	
	@Override
	public int insert(OrderLockEntity record) {
		record.setId(UUIDUtils.getUUID());
		return 	this.getSqlSession().insert(
				NAMESPACE + "insert", record);
	}

	@Override
	public int insertSelective(OrderLockEntity record) {
		// TODO Auto-generated method stub
		return 	this.getSqlSession().insert(
				NAMESPACE + "insertSelective", record);
	}

	@Override
	public String queryOrderLockCodeNoCode(OrderLockEntity record) {
		return  (String) getSqlSession().selectOne(NAMESPACE + "queryOrderLockCodeNoCode", record);
	}

	@Override
	public void updateOrderLock(OrderLockEntity record) {
		this.getSqlSession().update(
				NAMESPACE + "updateOrderLock", record);
	}

	/**
	 * 根据部门号查询订单锁定信息
	 */
	 

	@Override
	public OrderLockEntity queryOrderLockByDeptCode(String deptCode) {
		Map<String, String> map =new HashMap<String, String>();
		 map.put("deptCode", deptCode);
		return  (OrderLockEntity) getSqlSession().selectOne(NAMESPACE + "queryOrderLockByDeptCode", map);
	}

	@Override
	public int insertOrderLockLog(OrderLockEntity record) {
		record.setId(UUIDUtils.getUUID());
		return 	this.getSqlSession().insert(
				NAMESPACE + "insertOrderLockLog", record);
	}
	
	@Override
	public String queryUnifiedCode(String code) {
		 Map<String, String> map =new HashMap<String, String>();
		 map.put("code", code);
		return  (String) getSqlSession().selectOne(NAMESPACE + "queryUnifiedCode", map);
	}
	
	
	

}
