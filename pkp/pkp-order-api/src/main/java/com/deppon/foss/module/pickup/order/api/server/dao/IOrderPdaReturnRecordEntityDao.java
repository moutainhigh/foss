package com.deppon.foss.module.pickup.order.api.server.dao;

import com.deppon.foss.module.pickup.order.api.shared.domain.OrderPdaReturnRecordEntity;

/**
 * 
 * @ClassName: IOrderPdaReturnRecordEntityDao 
 * @Description: 快递订单退回记录DAO层接口
 * @author YANGBIN
 * @date 2014-5-9 下午4:28:56 
 *
 */
public interface IOrderPdaReturnRecordEntityDao {
	/**
	 * 
	 * @Title: insertPdaReturnRecord 
	 * @Description: 新增
	 * @param @param orderPdaReturnRecordEntity
	 * @param @return    设定文件 
	 * @return int    返回类型 
	 * @throws
	 */
	public int insertPdaReturnRecord(OrderPdaReturnRecordEntity orderPdaReturnRecordEntity);
	
	/**
	 * 
	 * @Title: queryOrderPdaReturnByDriverCode 
	 * @Description: 根据快递员工号，当天时间，查看是否存在退回订单记录 
	 * @param @param orderPdaReturnRecordEntity
	 * @param @return    设定文件 
	 * @return Long    返回类型 
	 * @throws
	 */
	public Long queryOrderPdaReturnByDriverCode(OrderPdaReturnRecordEntity orderPdaReturnRecordEntity);
	
	/**
	 * zxy20140703 新增:删除前一天数据 （昨天凌晨3点到今天凌晨3点）
	 * @Title: deleteOrderPdaReturn 
	 * @Description: 删除退单 
	 * @param orderPdaReturnRecordEntity
	 * @return 
	 * @throws
	 */
	public int deleteOrderPdaReturn(OrderPdaReturnRecordEntity orderPdaReturnRecordEntity);
	
	/**
	 * yb20140708  查询出最新一条退回数据
	 * @Title: queryOrderPdaReturnByCommon 
	 * @Description: 查询出最新一条退回数据 
	 * @param orderPdaReturnRecordEntity
	 * @return  OrderPdaReturnRecordEntity
	 * @throws
	 */
	public OrderPdaReturnRecordEntity queryOrderPdaReturnByCommon(OrderPdaReturnRecordEntity orderPdaReturnRecordEntity);
}