package com.deppon.foss.module.transfer.unload.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.unload.api.server.dao.IPDAOrderTaskDao;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAOrderSerialNoDetailEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAOrderTaskBillEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAOrderTaskEntity;


public class PDAOrderTaskDao extends iBatis3DaoImpl implements IPDAOrderTaskDao{

	private static final String NAMESPACE = "foss.unload.pdaOrderTask.";
	/**
	 * pda刷新点单任务，获得点单中的点单任务
	 * @author 272681-foss-chenlei
	 * @date 2016-01-25
	 */
	@SuppressWarnings("unchecked")
	public List<PDAOrderTaskEntity> refreshOrderTaskByPDA(String orgCode,String operator) {
		Map<String, String> params = new HashMap<String, String>(2);
		params.put("orgCode", orgCode);
		params.put("operator", operator);
		return this.getSqlSession().selectList(NAMESPACE + "refreshOrderTaskByPDA", params);
	}
	
	/**
	 * pda任务更新,根据点单任务编号 获得点单任务明细以及总重量，总体积，总票数，总件数
	 * @author 272681-foss-chenlei
	 * @date 2016-01-25
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PDAOrderTaskBillEntity> queryOrderTaskDetailByOrderTaskNo(
			String orderTaskNo) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(NAMESPACE + "queryOrderTaskDetailByOrderTaskNo", orderTaskNo);
	}

	/**
	 * pda获取流水明细,根据点单任务明细id
	 * @author 272681-foss-chenlei
	 * @date 2016-01-25
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PDAOrderSerialNoDetailEntity> queryPDAOrderTaskSerialNoListByBillNo(
			String id) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(NAMESPACE+"queryPDAOrderTaskSerialNoListByBillNo", id);
	}
	
}
