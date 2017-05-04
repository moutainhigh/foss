package com.deppon.foss.module.pickup.order.api.server.dao;

import com.deppon.foss.module.pickup.order.api.shared.domain.OrderForwardRecordEntity;

/**
 * 
 * @ClassName: IOrderForwardRecordEntityDao 
 * @Description: 快递订单转发记录DAO层接口
 * @author YANGBIN
 * @date 2014-5-9 下午4:24:17 
 *
 */
public interface IOrderForwardRecordEntityDao {
	/**
	 * 
	 * @Title: insertOrderForward 
	 * @Description: 新增转发记录
	 * @param @param orderForwardRecordEntity
	 * @param @return    设定文件 
	 * @return int    返回类型 
	 * @throws
	 */
	public int insertOrderForward(OrderForwardRecordEntity orderForwardRecordEntity);
	/**
	 * 
	 * @Title: queryOrderForwordByDriverCode 
	 * @Description: 根据快递员工号，以及当天操作时间，查看是否有转发记录 
	 * @param @param orderForwardRecordEntity
	 * @param @return    设定文件 
	 * @return Long    返回类型 
	 * @throws
	 */
	public Long queryOrderForwordByDriverCode(OrderForwardRecordEntity orderForwardRecordEntity);
	/**
	 * 14.8.7 gcl AUTO-221
	 * @Description: 根据快递员工号，以及订单号，查看是否有转发记录 
	 * @throws
	 */
	public Long queryOrderForwordByDriverOrderCode(OrderForwardRecordEntity orderForwardRecordEntity);
}