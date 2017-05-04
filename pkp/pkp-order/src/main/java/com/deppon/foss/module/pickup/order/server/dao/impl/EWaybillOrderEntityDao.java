package com.deppon.foss.module.pickup.order.server.dao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.order.api.server.dao.IEWaybillOrderEntityDao;
import com.deppon.foss.module.pickup.order.api.shared.domain.EWaybillOrderEntity;
import com.deppon.foss.module.pickup.waybill.shared.vo.GenerateUnActiveEWaybillVo;
import com.deppon.foss.util.UUIDUtils;

/**
 * 调度订单实体DAO
 * @author 136334-foss-bailei
 * @date 2014-10-1 上午9:28:19
 */

public class EWaybillOrderEntityDao extends iBatis3DaoImpl implements IEWaybillOrderEntityDao {

	public static final String NAMESPACE = "com.deppon.foss.module.pickup.order.api.shared.domain.EWaybillOrderEntity.";

	/**
	 * 单条插入电子运单订单
	 * 
	 */
	public int insertEWaybillOrder(EWaybillOrderEntity record) {
		record.setId(UUIDUtils.getUUID());
		//JobId默认为'N/A'
		//record.setJobID(WaybillConstants.UNKNOWN);
		return this.getSqlSession().insert(NAMESPACE + "insertEWaybillOrder", record);
	}
	
	/**
	 * 按照订单号删除电子运单订单
	 * 
	 */
	public int deleteEWaybillOrderbyOrderNO(String orderNO) {
		return this.getSqlSession().update(NAMESPACE + "deleteEWaybillOrderByOrderNO", orderNO);
	}
	
	//修改需要处理的订单数据的JOBID
	public int updateJobIDTopByRowNum(GenerateUnActiveEWaybillVo vo){
		return this.getSqlSession().update(NAMESPACE+"updateJobIDTopByRowNum", vo);
	}
	
	//通过JOBID查询需要处理的订单数据
	@SuppressWarnings("unchecked")
	public List<EWaybillOrderEntity> queryGenerateUnActiveEWaybillByJobID(String jobId){
		return this.getSqlSession().selectList(NAMESPACE+"selectGenerateUnActiveEWaybillByJobID",jobId);
	}
	
	//通过ID更新操作类型
	public int updateOperateResultByID(EWaybillOrderEntity eWaybill){
		return this.getSqlSession().update(NAMESPACE+"updateOperateResultByID", eWaybill);
	}

	@SuppressWarnings("unchecked")
	@Override
	public EWaybillOrderEntity queryEWaybillByOrderNo(String orderNo) {
		List<EWaybillOrderEntity> list = this.getSqlSession().selectList(NAMESPACE+"selectEWaybillByOrderNo", orderNo);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}
	
	/**
	 * 进行数据的更新
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-10-1 18:03:40
	 */
	@Override
	public int updateEWaybillOrderEntitySelective(EWaybillOrderEntity eWaybill){
		return this.getSqlSession().update(NAMESPACE+"updateEWaybillOrderEntitySelective", eWaybill);
	}
	
	//通过ID更新操作类型
	public int activeJobIdByOrderNo(String orderNo){
		return this.getSqlSession().update(NAMESPACE+"activeJobIdByOrderNo", orderNo);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public EWaybillOrderEntity queryEWaybillByWaybillNo(String waybillNo) {
		List<EWaybillOrderEntity> list = this.getSqlSession().selectList(NAMESPACE+"queryEWaybillByWaybillNo", waybillNo);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	/**
	 * <p>待激活线程异常处理根据ID将jobID置为N/A</p> 
	 * @author Foss-151211-yangtaohong 
	 * @date 2016-8-4 下午4:13:37
	 * @param id
	 * @return
	 * @see
	 */
		public int activeJobIdByJobId(String jobId){
			return this.getSqlSession().update(NAMESPACE+"activeJobIdByJobId", jobId);
		}
}