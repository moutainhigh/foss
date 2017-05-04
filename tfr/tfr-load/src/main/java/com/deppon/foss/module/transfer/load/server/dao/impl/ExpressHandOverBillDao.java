package com.deppon.foss.module.transfer.load.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.server.dao.IExpressHandOverBillDao;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SerialNoStockEntity;

/** 
 * @className: ExpressHandOverBillDao
 * @author: ShiWei shiwei@outlook.com
 * @description: 包交接单dao
 * @date: 2013-7-26 下午2:17:46
 * 
 */
public class ExpressHandOverBillDao extends iBatis3DaoImpl implements IExpressHandOverBillDao {
	
	private static final String NAMESPACE = "foss.load.express.handoverbill.";

	/**
	 * 根据包号获取运单明细
	 * @author 045923-foss-shiwei
	 * @date 2013-7-26 下午5:10:30
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IExpressHandOverBillDao#queryWaybillListByPackageNo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HandOverBillDetailEntity> queryWaybillListByPackageNo(String packageNo) {
		return this.getSqlSession().selectList(NAMESPACE + "queryWaybillListByPackageNo",packageNo);
	}

	/**
	 * 根据包号、运单号获取流水号明细
	 * @author 045923-foss-shiwei
	 * @date 2013-7-26 下午5:10:50
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IExpressHandOverBillDao#querySerialNoListByPackageNoAndWaybillNo(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SerialNoStockEntity> querySerialNoListByPackageNoAndWaybillNo(String packageNo, String waybillNo) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("packageNo", packageNo);
		map.put("waybillNo", waybillNo);
		return this.getSqlSession().selectList(NAMESPACE + "querySerialNoListByPackageNoAndWaybillNo",map);
	}
	
}
