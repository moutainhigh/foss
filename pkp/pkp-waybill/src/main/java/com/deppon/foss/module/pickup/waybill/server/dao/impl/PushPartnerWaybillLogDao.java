package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IPushPartnerWaybillLogDao;
import com.deppon.foss.module.pickup.waybill.server.service.impl.PushPartnerWaybillLogService;
import com.deppon.foss.module.pickup.waybill.shared.domain.PushPartnerWaybillLogEntity;
import com.deppon.foss.util.UUIDUtils;

/**
 * 向ptp推送运单信息 日志
 * @author 272311-sangwenhao
 * @date 2016-2-17
 */
public class PushPartnerWaybillLogDao extends iBatis3DaoImpl implements IPushPartnerWaybillLogDao {

	private Logger logger = LoggerFactory.getLogger(PushPartnerWaybillLogService.class);
	
	private static final String NAMESPACE = "foss.pkp.PushPartnerWaybillLogEntityMapper.";
	
	/**
	 * 插入日志
	 * @param pushPartnerWaybillLogEntity
	 * @author 272311-sangwenhao
	 * @date 2016-2-17
	 */
	@Override
	public void insert(PushPartnerWaybillLogEntity pushPartnerWaybillLogEntity) {
		logger.info("插入合伙人运单日志信息 ");
		// 设置UUID
		pushPartnerWaybillLogEntity.setId(UUIDUtils.getUUID());
		
		this.getSqlSession().insert(NAMESPACE+"insert", pushPartnerWaybillLogEntity);
	}
	
	//根据订单创建时间和订单编号查询发送日志信息 2016年3月29日 16:28:09 葛亮亮
	@Override
	public PushPartnerWaybillLogEntity queryPushPartnerWaybillLogByMap(Map<String, Object> waybillLogMap){
		return (PushPartnerWaybillLogEntity)this.getSqlSession().selectOne(NAMESPACE + "queryPushPartnerWaybillLogByMap", waybillLogMap);
	}
}
