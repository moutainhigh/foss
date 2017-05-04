package com.deppon.foss.module.pickup.order.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.order.api.shared.domain.EWaybillOrderEntity;
import com.deppon.foss.module.pickup.waybill.shared.vo.GenerateUnActiveEWaybillVo;


/**
 * 电子运单订单实体DAO
 * @author 136334-foss-bailei
 * 
 */
public interface IEWaybillOrderEntityDao {
	
	//单条插入电子运单订单数据
	public int insertEWaybillOrder(EWaybillOrderEntity record);
	
	//单条删除电子运单订单数据
	public int deleteEWaybillOrderbyOrderNO(String orderNO);

	//通过JOBID查询需要处理的订单数据
	public List<EWaybillOrderEntity> queryGenerateUnActiveEWaybillByJobID(
			String jobId);
	
	//修改需要处理的订单数据的JOBID
	int updateJobIDTopByRowNum(GenerateUnActiveEWaybillVo vo);
	

	//通过ID更新操作类型
	public int updateOperateResultByID(EWaybillOrderEntity eWaybill);
	
	/**
	 * 通过订单号查询订单信息
	 * @param orderNo
	 * @return
	 */
	public EWaybillOrderEntity queryEWaybillByOrderNo(String orderNo);

	/**
	 * 进行数据的更新
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-10-1 18:03:40
	 */
	int updateEWaybillOrderEntitySelective(EWaybillOrderEntity eWaybill);
	
	/**
	 * 根据订单号设置订单为JOB可执行的数据
	 * @param orderNo
	 * @return
	 */
	int activeJobIdByOrderNo(String orderNo);

	/**
	 * 通过运单号查询订单信息
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-3-19 10:14:33
	 * @param orderNo
	 * @return
	 */
	EWaybillOrderEntity queryEWaybillByWaybillNo(String waybillNo);

	/**
	 * <p>待激活线程异常处理根据ID将jobID置为N/A</p> 
	 * @author Foss-151211-yangtaohong 
	 * @date 2016-8-4 下午4:13:37
	 * @param JOBID
	 * @return
	 * @see
	 */
	public int activeJobIdByJobId(String jobId);
}