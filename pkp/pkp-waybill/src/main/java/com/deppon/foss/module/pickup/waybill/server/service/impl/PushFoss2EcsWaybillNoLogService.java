package com.deppon.foss.module.pickup.waybill.server.service.impl;

import javax.annotation.Resource;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IPushFoss2EcsWaybillNoLogDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPushFoss2EcsWaybillNoLogService;
import com.deppon.foss.module.pickup.waybill.shared.domain.PushFoss2EcsWaybillNoLogEntity;
/*
 * FOSS推送运单号给ECS记录日志
 * 2016年7月23日 08:29:30 葛亮亮
 */
public class PushFoss2EcsWaybillNoLogService implements
		IPushFoss2EcsWaybillNoLogService {
	
	@Resource
	private IPushFoss2EcsWaybillNoLogDao PushFoss2EcsWaybillNoLogDao;
	
	public void setPushFoss2EcsWaybillNoLogDao(
			IPushFoss2EcsWaybillNoLogDao pushFoss2EcsWaybillNoLogDao) {
		PushFoss2EcsWaybillNoLogDao = pushFoss2EcsWaybillNoLogDao;
	}
	
	public void insertWaybillNoLog(PushFoss2EcsWaybillNoLogEntity logEntity) {
		
		try {
			if(null != logEntity){
				PushFoss2EcsWaybillNoLogDao.insertWaybillNoLog(logEntity);
			}
		} catch (Exception e) {
			throw new BusinessException("FOSS推送运单号给ECS记录日志异常"+e.getMessage());
		}
	}

}
