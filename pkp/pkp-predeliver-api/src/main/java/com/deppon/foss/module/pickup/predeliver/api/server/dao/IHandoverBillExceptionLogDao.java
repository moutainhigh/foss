package com.deppon.foss.module.pickup.predeliver.api.server.dao;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.HandoverBillExceptionLogEntity;

/** 
 * @ClassName: IHandoverBillExceptionLogDao 
 * @Description: 交单自动匹配小区和车辆异常日志Dao
 * @author 237982-foss-fangwenjun 
 * @date 2015-5-9 上午10:30:52 
 *  
 */
public interface IHandoverBillExceptionLogDao {
	
	/**
	 * @Title: insertOne
	 * @Description: 添加交单自动匹配小区和车辆异常日志
	 * @param handoverBillExceptionLogEntity 交单自动匹配小区和车辆异常日志 对象
	 * @return 受影响的行数
	 */
	int insertOne(HandoverBillExceptionLogEntity handoverBillExceptionLogEntity);

}
