package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ISyncWaybillLogDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillLogEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillLogEntityQueryDto;

/**
 * 推送CUBC日志
 * SyncCUBCLogDao
 * @author 198771-zhangwei
 * 2016-10-13 上午9:11:11
 */
public class SyncWaybillLogDao extends iBatis3DaoImpl implements ISyncWaybillLogDao {

	
	private static final String NAMESPACE = "foss.pkp.WaybillLogEntityMapper.";
	private Logger logger = LoggerFactory.getLogger(SyncWaybillLogDao.class);

	/**
	 * 插入日志
	 * @author 198771-zhangwei
	 * 2016-10-13 上午8:31:55
	 */
	@Override
	public void insertWaybillLog(WaybillLogEntity waybillLogEntity) {
		try {
			this.getSqlSession().insert(NAMESPACE+"insert", waybillLogEntity);
		} catch (Throwable e) {
			logger.info(e.getMessage());
		}
	}

	/**
	 * 
	 * @author 198771-zhangwei
	 * 2016-11-10 下午6:09:20
	 */
	@Override
	public void deleteById(WaybillLogEntity waybillLogEntity) {
		try {
			this.getSqlSession().update(NAMESPACE+"deleteById", waybillLogEntity);
		} catch (Throwable e) {
			logger.info(e.getMessage());
		}
	}

	/**
	 * 
	 * @author 198771-zhangwei
	 * 2016-11-11 上午9:37:22
	 */
	@Override
	public void updateById(WaybillLogEntity waybillLogEntity) {
		try{
			this.getSqlSession().update(NAMESPACE+"updateById", waybillLogEntity);
		} catch (Throwable e) {
			logger.info(e.getMessage());
		}
	}
	
	/**
	 * 根据条件查询推送CUBC接口日志
	 * @param queryDto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillLogEntity> queryLogEntityByCondition(WaybillLogEntityQueryDto queryDto){
		if(null==queryDto){
			return null; 
		}
		return this.getSqlSession().selectList(NAMESPACE+"queryCUBCLogEntityStatusByWaybillNo", queryDto);
	}
}
