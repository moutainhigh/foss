package com.deppon.foss.module.pickup.order.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.order.api.shared.domain.EWaybillOrderLogEntity;

/**
 * 电子运单订单日志实体DAO
 * @author 136334-foss-bailei
 * 
 */
public interface IEWaybillOrderLogEntityDao {

	/**
	 * 电子运单订单日志实体增加记录
	 * @author 136334-foss-bailei
	 * @date 2014-9-28 14:32:50
	 * @param logEntity
	 * @return
	 */
	int addEWaybillOrderLogEntity(EWaybillOrderLogEntity logEntity);
	
	/**
	 * 根据电子运单失败记录更新相关记录
	 * @author 136334-foss-bailei
	 * @date 2014-9-28 14:32:29
	 * @param orderNo
	 * @return
	 */
	int updateEWaybillOrderLogEntitySelective(EWaybillOrderLogEntity logEntity);
	
	/**
	 * 根据订单号进行查询电子运单失败记录表
	 * @author 136334-foss-bailei
	 * @date 2014-9-28 14:32:29
	 * @param orderNo
	 * @return
	 */
	List<EWaybillOrderLogEntity> selectEWaybillOrderByBasicParams(EWaybillOrderLogEntity logEntity);
}