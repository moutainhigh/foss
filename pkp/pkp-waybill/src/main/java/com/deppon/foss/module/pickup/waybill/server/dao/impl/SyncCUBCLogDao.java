package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ISyncCUBCLogDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.CUBCLogEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 推送CUBC日志
 * SyncCUBCLogDao
 * @author 198771-zhangwei
 * 2016-10-13 上午9:11:11
 */
public class SyncCUBCLogDao extends iBatis3DaoImpl implements ISyncCUBCLogDao {

	
	private static final String NAMESPACE = "foss.pkp.CUBCLogEntityMapper.";
	private Logger logger = LoggerFactory.getLogger(SyncCUBCLogDao.class);

	/**
	 * 插入日志
	 * @author 198771-zhangwei
	 * 2016-10-13 上午8:31:55
	 */
	@Override
	public void insertCUBCLog(CUBCLogEntity cubcLogEntity) {
		this.getSqlSession().insert(NAMESPACE+"insert", cubcLogEntity);
	}

	/**
	 * 
	 * @author 198771-zhangwei
	 * 2016-11-10 下午6:09:20
	 */
	@Override
	public void updateByWaybillNoAndCode(CUBCLogEntity cubcLogEntity) {
		this.getSqlSession().update(NAMESPACE+"updateByWaybillNoAndCode", cubcLogEntity);
	}

	/**
	 * 
	 * @author 198771-zhangwei
	 * 2016-11-11 上午9:37:22
	 */
	@Override
	public void updateById(CUBCLogEntity cubcLogEntity) {
		this.getSqlSession().update(NAMESPACE+"updateById", cubcLogEntity);
	}
	
	
}
