package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IPushPartnerWaybillLogDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPushPartnerWaybillLogService;
import com.deppon.foss.module.pickup.waybill.shared.domain.PushPartnerWaybillLogEntity;
/**
 * 向ptp推送运单信息 日志
 * @author 272311-sangwenhao
 * @date 2016-2-17
 */
public class PushPartnerWaybillLogService implements IPushPartnerWaybillLogService {

	private Logger logger = LoggerFactory.getLogger(PushPartnerWaybillLogService.class);
	
	private IPushPartnerWaybillLogDao pushPartnerWaybillLogDao ;
	
	public IPushPartnerWaybillLogDao getPushPartnerWaybillLogDao() {
		return pushPartnerWaybillLogDao;
	}

	public void setPushPartnerWaybillLogDao(
			IPushPartnerWaybillLogDao pushPartnerWaybillLogDao) {
		this.pushPartnerWaybillLogDao = pushPartnerWaybillLogDao;
	}

	/**
	 * 插入日志
	 * @param pushPartnerWaybillLogEntity
	 * @author 272311-sangwenhao
	 * @date 2016-2-17
	 */
	@Override
	public void insert(PushPartnerWaybillLogEntity pushPartnerWaybillLogEntity) {
		try {
			logger.debug("进入 insert 参数信息："+ (pushPartnerWaybillLogEntity != null ? pushPartnerWaybillLogEntity.toString() : ""));
			if(pushPartnerWaybillLogEntity!=null){
				pushPartnerWaybillLogDao.insert(pushPartnerWaybillLogEntity);
			}
		} catch (Exception e) {
			logger.error("添加合伙人运单日志信息异常"+e.getMessage());
			throw new BusinessException("添加合伙人运单日志信息异常"+e.getMessage());
		}
	}
	
	//根据订单创建时间和订单编号查询发送日志信息 2016年3月29日 16:28:09 葛亮亮
	public PushPartnerWaybillLogEntity queryPushPartnerWaybillLogByMap(Map<String, Object> waybillLogMap){
		try {
			logger.debug("进入 queryPushPartnerWaybillLogByMap 参数信息："+ waybillLogMap);
			if(waybillLogMap!=null){
				return pushPartnerWaybillLogDao.queryPushPartnerWaybillLogByMap(waybillLogMap);
			}
		} catch (Exception e) {
			logger.error("合伙人运单日志信息查询异常"+e.getMessage());
			throw new BusinessException("合伙人运单日志信息查询异常"+e.getMessage());
		}
		return null;
	}

}
