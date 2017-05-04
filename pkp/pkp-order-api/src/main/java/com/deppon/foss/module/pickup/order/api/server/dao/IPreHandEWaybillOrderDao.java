package com.deppon.foss.module.pickup.order.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.pickup.order.api.shared.domain.PreHandEWaybillOrderEntity;
import com.deppon.foss.module.pickup.waybill.shared.vo.GenerateUnActiveEWaybillVo;
/**
 * 待处理电子运单数据持久层
 * @author Foss-105888-Zhangxingwang
 * @date 2015-5-5 21:13:27
 */
public interface IPreHandEWaybillOrderDao {

    int deletePreEWaybillOrderByPrimaryKey(String id);

    int insertPreEWaybillOrder(PreHandEWaybillOrderEntity record);

    int insertPreEWaybillOrderSelective(PreHandEWaybillOrderEntity record);

    PreHandEWaybillOrderEntity selectPreEWaybillOrderByPrimaryKey(String id);

    int updatePreEWaybillOrderByIdSelective(PreHandEWaybillOrderEntity record);

    int updatePreEWaybillOrderById(PreHandEWaybillOrderEntity record);

	int updateJobIDTopByRowNum(GenerateUnActiveEWaybillVo vo);

	List<PreHandEWaybillOrderEntity> queryGeneratePreEWaybillOrderByJobID(String jobId);

	List<PreHandEWaybillOrderEntity> selectPreEWaybillOrderByOrderNo(String orderNo);

	List<PreHandEWaybillOrderEntity> selectPreEWaybillOrderByOrderNoOrWaybillNo(Map<String, Object> maps);
	
	int deletePreEWaybillOrderByParams(Map<String, Object> params);

	/**
	 * <p>预处理线程处理，线程池满数据回滚操作，将jobId 置为N/A,状态置为N待于再执行一次预处理后续流程</p> 
	 * @author Foss-151211-yangtaohong 
	 * @date 2016-8-4 下午5:15:25
	 * @param id
	 * @return
	 * @see
	 */
	int updatePreEWaybillOrderByPrimaryKey(String id);
}