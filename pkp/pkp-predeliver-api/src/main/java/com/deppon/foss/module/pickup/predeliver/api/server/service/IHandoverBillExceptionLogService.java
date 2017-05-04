package com.deppon.foss.module.pickup.predeliver.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.HandoverBillExceptionLogEntity;

/** 
 * @ClassName: IHandoverBillExceptionLogService 
 * @Description: 交单自动匹配小区和车辆异常日志Service
 * @author 237982-foss-fangwenjun 
 * @date 2015-5-9 下午1:56:58 
 *  
 */
public interface IHandoverBillExceptionLogService extends IService {
	
	/**
	 * @Title: insertHandoverBillExceptionLog
	 * @Description: 添加异常日志
	 * @param handoverBillExceptionLogEntity 交单自动匹配小区和车辆异常日志 对象
	 * @return 受影响的行数
	 */
	int insertHandoverBillExceptionLog(HandoverBillExceptionLogEntity handoverBillExceptionLogEntity);

}
