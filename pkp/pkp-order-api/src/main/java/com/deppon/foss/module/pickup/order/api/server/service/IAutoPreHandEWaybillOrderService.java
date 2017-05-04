package com.deppon.foss.module.pickup.order.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.order.api.shared.domain.PreHandEWaybillOrderEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderHandleDto;
import com.deppon.foss.module.pickup.waybill.shared.vo.GenerateUnActiveEWaybillVo;
/**
 * 电子运单预处理线程池
 * @author Foss-105888-Zhangxingwang
 * @date 2015-4-17 19:23:14
 */
public interface IAutoPreHandEWaybillOrderService extends IService {

    int deletePreEWaybillOrderByPrimaryKey(String id);

    int insertPreEWaybillOrder(PreHandEWaybillOrderEntity record);

    int insertPreEWaybillOrderSelective(PreHandEWaybillOrderEntity record);

    PreHandEWaybillOrderEntity selectPreEWaybillOrderByPrimaryKey(String id);

    int updatePreEWaybillOrderByIdSelective(PreHandEWaybillOrderEntity record);

    int updatePreEWaybillOrderById(PreHandEWaybillOrderEntity record);

	int updateJobIDTopByRowNum(GenerateUnActiveEWaybillVo vo);

	List<PreHandEWaybillOrderEntity> queryGeneratePreEWaybillOrderByJobID(String jobId);
	
	void singleHandlePreHandEWaybillOrder(PreHandEWaybillOrderEntity preHandEWaybillOrderEntity);

	void process(String jobId);
	
	boolean acceptOrder(OrderHandleDto orderHandleDto);
}