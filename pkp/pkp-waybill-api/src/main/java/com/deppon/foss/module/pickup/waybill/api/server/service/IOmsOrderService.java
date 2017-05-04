package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.domain.OmsOrderEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LTLEWaybillQueryResultDto;

public interface IOmsOrderService extends IService {
	
	/**
	 * 同步订单
	 * @param entity
	 * @param asynOmsOrder 同步时间
	 * @throws Exception
	 */
	public void  asynOmsOrder(OmsOrderEntity entity);
	
	/**
	 * 生成OmsOrder
	 */
	public void generateOmsOrder(OmsOrderEntity entity);
	
	/**
	 * 根据订单号查询
	 * @param orderNo
	 * @return
	 */
	public OmsOrderEntity queryOmsOrderByOrderNo(String orderNo);
	
	/**
	 * 根据运单号查询
	 * @param waybillNo
	 * @return
	 */
	public OmsOrderEntity queryOmsOrderByWaybillNo(String waybillNo);
	
	/**
	 * 
	 * @param ltleWaybillQueryResultDto
	 * @return
	 */
	public List<LTLEWaybillQueryResultDto> queryOmsOrderOrLabelStatusByWaybillNo(LTLEWaybillQueryResultDto ltleWaybillQueryResultDto);
	
	/**
	 * 根据订单号更新有值项
	 * @param entity
	 * @return
	 */
	public int updateOmsOrderByOrderNoSelective(OmsOrderEntity entity);

	/**
	 * 根据主键更新
	 * @param entity
	 * @return
	 */
	int updateOmsOrderByPrimaryKey(OmsOrderEntity entity);
	
	/**
	 * 判断异常是不是大件上楼异常
	 */
	public boolean isBigUpException(String errorCode);
	
	/**
	 * 取消订单
	 */
	public void cancelOrder(OmsOrderEntity entity);
}
