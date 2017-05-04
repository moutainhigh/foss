package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import javax.annotation.Resource;

import com.deppon.foss.module.pickup.predeliver.api.server.dao.IHandoverBillExceptionLogDao;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IHandoverBillExceptionLogService;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.HandoverBillExceptionLogEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.util.ReflectUtils;

/** 
 * @ClassName: HandoverBillExceptionLogService 
 * @Description: 交单自动匹配小区和车辆异常日志Service实现
 * @author 237982-foss-fangwenjun 
 * @date 2015-5-9 下午2:01:39 
 *  
 */
public class HandoverBillExceptionLogService implements
		IHandoverBillExceptionLogService {
	
	/**
	 * 交单自动匹配小区和车辆异常日志Dao
	 */
	private IHandoverBillExceptionLogDao handoverBillExceptionLogDao;

	/**
	 * 设置handoverBillExceptionLogDao
	 * @param handoverBillExceptionLogDao 要设置的handoverBillExceptionLogDao
	 */
	@Resource
	public void setHandoverBillExceptionLogDao(
			IHandoverBillExceptionLogDao handoverBillExceptionLogDao) {
		this.handoverBillExceptionLogDao = handoverBillExceptionLogDao;
	}

	@Override
	public int insertHandoverBillExceptionLog(
			HandoverBillExceptionLogEntity handoverBillExceptionLogEntity) {
		
		// 判断对象及对象字段是否为空
		if (ReflectUtils.fieldValueIsNull(handoverBillExceptionLogEntity, false)){
			return -1;
		}
		
		return handoverBillExceptionLogDao.insertOne(handoverBillExceptionLogEntity);
	}

}
