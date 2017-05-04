package com.deppon.foss.module.pickup.waybill.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.OmsOrderEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LTLEWaybillQueryResultDto;

/**
 * @description OMS订单处理DAO
 * @author 297064
 *
 */
public interface IOmsOrderDao {
	
	public int save(OmsOrderEntity omsOrderEntity);
	
	public int updateOmsOrderByOrderNoSelective(OmsOrderEntity omsOrderEntity);
	
	public OmsOrderEntity queryOmsOrderByOrderNo(String orderNo);
	
	public OmsOrderEntity queryOmsOrderByWaybillNo(String waybillNo);

	int updateOmsOrderByPrimaryKey(OmsOrderEntity omsOrderEntity);
	
	public List<LTLEWaybillQueryResultDto> queryOmsOrderOrLabelStatusByWaybillNo(LTLEWaybillQueryResultDto ltleWaybillQueryResultDto);
	
}
