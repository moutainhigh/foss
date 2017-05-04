package com.deppon.foss.module.pickup.predeliver.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IHandoverBillExceptionLogDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.HandoverBillExceptionLogEntity;

/** 
 * @ClassName: HandoverBillExceptionLogDao 
 * @Description: 交单自动匹配小区和车辆异常日志Dao实现
 * @author 237982-foss-fangwenjun 
 * @date 2015-5-9 上午10:36:41 
 *  
 */
public class HandoverBillExceptionLogDao extends iBatis3DaoImpl implements
		IHandoverBillExceptionLogDao {

	/**
	 * 交单匹配地址异常日志名称空间
	 */
	private final static String HBEXCEPTIONLOG_NAMESPACE = "com.deppon.foss.module.pickup.predeliver.api.shared.domain.HandoverBillExceptionLogEntityMapper.";
	
	/**
	 * @Title: insertOne
	 * @Description: 添加交单自动匹配小区和车辆异常日志 
	 * @param handoverBillExceptionLogEntity 交单自动匹配小区和车辆异常日志 对象
	 * @return 受影响的行数
	 */
	@Override
	public int insertOne(
			HandoverBillExceptionLogEntity handoverBillExceptionLogEntity) {
		return super.getSqlSession().insert(HBEXCEPTIONLOG_NAMESPACE + "insertOne", handoverBillExceptionLogEntity);
	}

}
