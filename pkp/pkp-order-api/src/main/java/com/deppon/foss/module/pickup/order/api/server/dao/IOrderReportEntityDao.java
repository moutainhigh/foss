package com.deppon.foss.module.pickup.order.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.order.api.shared.domain.OrderReportEntity;

/**
 * 
 * @ClassName: IOrderReportEntityDao
 * @Description: 快递每日统计报表DAO层接口
 * @author YANGBIN
 * @date 2014-5-9 下午4:15:06
 * 
 */
public interface IOrderReportEntityDao {
	/**
	 * 
	 * @Title: insertOrderReport
	 * @Description: 处理订单新增一条记录
	 * @param @param orderReportEntity
	 * @param @return 设定文件
	 * @return int 返回类型
	 * @throws
	 */
	public int insertOrderReport(OrderReportEntity orderReportEntity);

	/**
	 * 
	 * @Title: deleteOrderReport
	 * @Description: 删除每日统计对应的数据
	 * @param @param orderReportEntity
	 * @param @return 设定文件
	 * @return int 返回类型
	 * @throws
	 */
	public int deleteOrderReport(OrderReportEntity orderReportEntity);

	/**
	 * 
	 * @Title: selectOrderReportByAddress
	 * @Description: 根据地址查询对应的快递员
	 * @param @param orderReportEntity
	 * @param @return 设定文件
	 * @return OrderReportEntity 返回类型
	 * @throws
	 */
	public OrderReportEntity selectOrderReportByAddress(
			OrderReportEntity orderReportEntity);
	/**
	 * 
	 * @Title: selectOrderReportByAddressDriverCode
	 * @Description: 根据地址和快递员工号查询对应的快递员
	 * @param @param orderReportEntity
	 * @param @return 设定文件
	 * @return OrderReportEntity 返回类型
	 * @throws
	 */
	public OrderReportEntity selectOrderReportByAddressDriverCode(
			OrderReportEntity orderReportEntity);
	
	/**
	 * 
	 * @Title: queryMinReceiveOrderDriver 
	 * @Description: 根据机动快递员集合，查询出收货订单量最少快递员
	 * @param @param list
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws
	 */
	public String queryMinReceiveOrderDriver(List<String> list);
	/**
	 * 14.7.30 gcl 查询 快递员当天接收订单数
	 * @param driverCode
	 * @return
	 */
	public Long queryReceiveCountOrderDriver(String driverCode);
}