package com.deppon.foss.module.pickup.order.server.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.order.api.server.dao.IPreHandEWaybillOrderDao;
import com.deppon.foss.module.pickup.order.api.shared.domain.PreHandEWaybillOrderEntity;
import com.deppon.foss.module.pickup.waybill.shared.vo.GenerateUnActiveEWaybillVo;
/**
 * 待处理电子运单数据持久层
 * @author Foss-105888-Zhangxingwang
 * @date 2015-5-5 21:13:27
 */
public class PreHandEWaybillOrderDao extends iBatis3DaoImpl implements IPreHandEWaybillOrderDao {
	public static final String NAMESPACE = "pkp.order.ewaybill.PreHandleEWaybillMapper.";
	@Override
	public int deletePreEWaybillOrderByPrimaryKey(String id) {
		return this.getSqlSession().delete(NAMESPACE+"deletePreEWaybillOrderByPrimaryKey", id);
	}

	@Override
	public int insertPreEWaybillOrder(PreHandEWaybillOrderEntity record) {
		return this.getSqlSession().insert(NAMESPACE+"insertPreEWaybillOrder", record);
	}

	@Override
	public int insertPreEWaybillOrderSelective(PreHandEWaybillOrderEntity record) {
		return this.getSqlSession().insert(NAMESPACE+"insertPreEWaybillOrderSelective", record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PreHandEWaybillOrderEntity selectPreEWaybillOrderByPrimaryKey(String id) {
		List<PreHandEWaybillOrderEntity> list = this.getSqlSession().selectList(NAMESPACE+"selectPreEWaybillOrderByPrimaryKey", id);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PreHandEWaybillOrderEntity> selectPreEWaybillOrderByOrderNo(String orderNo) {
		return this.getSqlSession().selectList(NAMESPACE+"selectPreEWaybillOrderByOrderNo", orderNo);
	}

	@Override
	public int updatePreEWaybillOrderByIdSelective(PreHandEWaybillOrderEntity record) {
		return this.getSqlSession().update(NAMESPACE+"updatePreEWaybillOrderByIdSelective", record);
	}

	@Override
	public int updatePreEWaybillOrderById(PreHandEWaybillOrderEntity record) {
		return this.getSqlSession().update(NAMESPACE+"updatePreEWaybillOrderById", record);
	}
	
	/**
	 * 修改需要处理的订单数据的JOBID
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-4-17 17:14:41
	 */
	@Override
	public int updateJobIDTopByRowNum(GenerateUnActiveEWaybillVo vo){
		return this.getSqlSession().update(NAMESPACE+"updateJobIDTopByRowNum", vo);
	}
	
	/**
	 * 通过JOBID查询需要处理的订单数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-5-5 20:07:04
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PreHandEWaybillOrderEntity> queryGeneratePreEWaybillOrderByJobID(String jobId){
		return this.getSqlSession().selectList(NAMESPACE+"queryGeneratePreEWaybillOrderByJobID",jobId);
	}
	
	/**
	 * 通过运单号或者订单号进行查询需要预处理的订单数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-5-5 20:07:04
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PreHandEWaybillOrderEntity> selectPreEWaybillOrderByOrderNoOrWaybillNo(Map<String, Object> maps){
		return this.getSqlSession().selectList(NAMESPACE+"selectPreEWaybillOrderByOrderNoOrWaybillNo", maps);
	}

	@Override
	public int deletePreEWaybillOrderByParams(Map<String, Object> params) {
		return this.getSqlSession().delete(NAMESPACE+"deletePreEWaybillOrderByParams", params);
	}
	/**
	 * <p>预处理线程处理，线程池满数据回滚操作，将jobId 置为N/A,状态置为N待于再执行一次预处理后续流程</p> 
	 * @author Foss-151211-yangtaohong 
	 * @date 2016-8-4 下午5:15:25
	 * @param id
	 * @return
	 * @see
	 */
	public int updatePreEWaybillOrderByPrimaryKey(String id){
		return this.getSqlSession().delete(NAMESPACE+"updatePreEWaybillOrderByPrimaryKey", id);
	}
}