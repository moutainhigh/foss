package com.deppon.foss.module.pickup.order.api.server.dao;

import com.deppon.foss.module.pickup.order.api.shared.domain.OrderAutoExceptionLogEntity;

/**
 * 
 * @ClassName: IOrderAutoExceptionLogEntityDao 
 * @Description: 自动处理业务异常日志DAO层接口
 * @author YANGBIN
 * @date 2014-5-9 下午4:19:31 
 *
 */
public interface IOrderAutoExceptionLogEntityDao {
	/**
	 * 
	 * @Title: insertAutoOrderExceptionLog 
	 * @Description: 新增自动调度业务日志
	 * @param @param orderAutoExceptionLogEntity
	 * @param @return    设定文件 
	 * @return int    返回类型 
	 * @throws
	 */
	public int insertAutoOrderExceptionLog(OrderAutoExceptionLogEntity orderAutoExceptionLogEntity);
}