package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.dao.ICODLogEntityDao;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODLogEntity;

/**
 * 
 * 代收货款日志具体实现类
 * 
 * @author dp-huangxb
 * @date 2012-10-13 下午4:33:56
 */
public class CODLogEntityDao extends iBatis3DaoImpl implements ICODLogEntityDao {

	private final static String NAMESPACE = "foss.stl.CodLogEntityDao.";

	/**
	 * 
	 * 添加操作日志
	 * 
	 * @author dp-huangxb
	 * @date 2012-10-13 下午4:46:01
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.ICODLogEntityDao#addCODLog(com.deppon.foss.module.settlement.consumer.api.shared.domain.CODLogEntity)
	 */
	@Override
	public int addCODLog(CODLogEntity entity) {
		if (entity != null) {
			// 调用插入方法
			return this.getSqlSession().insert(NAMESPACE + "addCodLog", entity);
		} else {
			throw new SettlementException("内部错误，参数为为空");
		}
	}

	/**
	 * 根据单号进行查询
	 * 
	 * @author dp-huangxb
	 * @date 2012-10-13 下午4:51:04
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.ICODLogEntityDao#queryByWaybill(java.lang.String)
	 * @param waybillNumber
	 *            运单单号
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CODLogEntity> queryByWaybill(String waybillNumber) {
		if (!StringUtils.isEmpty("waybillNumber")) {
			return  this.getSqlSession().selectList(
					NAMESPACE + "queryByWaybill", waybillNumber);
		} else {
			throw new SettlementException("内部错误，参数为为空");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CODLogEntity> queryAuditLogByWaybill(String waybillNo) {
		if (!StringUtils.isEmpty("waybillNumber")) {
			return  this.getSqlSession().selectList(
					NAMESPACE + "queryAuditLogByWaybill", waybillNo);
		} else {
			throw new SettlementException("内部错误，参数为为空");
		}
	}

}
