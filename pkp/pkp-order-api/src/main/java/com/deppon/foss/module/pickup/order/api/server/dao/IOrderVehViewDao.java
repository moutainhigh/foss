package com.deppon.foss.module.pickup.order.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.order.api.shared.dto.DriverBillCountDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.DriverQueryDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderVehViewSignDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckSignDto;

/**
 * 
 * @ClassName: IOrderVehViewDao
 * @Description: 零担可视化-操作
 * @author YANGBIN
 * @date 2014-5-9 下午4:15:06
 * 
 */
public interface IOrderVehViewDao {
	
	 /**
     * 
     * @Title: queryDriverByCommon 
     * @Description: 查询出对应签到司机统计信息
     * @param @param driverQueryDto
     * @param @return    设定文件 
     * @return List<OwnTruckSignDto>    返回类型 
     * @throws
     */
    public List<OwnTruckSignDto> queryDriverByCommon(DriverQueryDto driverQueryDto ,int start, int limit);
    
    /**
     * 
     * @Title: queryDriverByCommonCount 
     * @Description: 查询出对应签到司机统计信息
     * @param @param driverQueryDto
     * @param @return    设定文件 
     * @return Long    返回类型 
     * @throws
     */
    public Long queryDriverByCommonCount(DriverQueryDto driverQueryDto); 
    
    /**
     * 
     * @Title: queryOrderVehViewByCommon 
     * @Description: 查询司机、车牌对应的订单信息
     * @param @param driverQueryDto
     * @param @return    设定文件 
     * @return List<OrderVehViewSignDto>    返回类型 
     * @throws
     */
	public List<OrderVehViewSignDto> queryOrderVehViewByCommon(DriverQueryDto driverQueryDto);
    
	/**
     * 
     * @Title: queryOrderBillCount 
     * @Description: 查询司机、车牌订单总数
     * @param @param driverQueryDto
     * @param @return    设定文件 
     * @return List<DriverBillCountDto>    返回类型 
     * @throws
     */
	public List<DriverBillCountDto> queryOrderBillCount(DriverQueryDto driverQueryDto);
	
	/**
     * 
     * @Title: queryDeliverBillCount 
     * @Description: 查询司机、车牌对应派送总数
     * @param @param driverQueryDto
     * @param @return    设定文件 
     * @return List<DriverBillCountDto>    返回类型 
     * @throws
     */
	public List<DriverBillCountDto> queryDeliverBillCount(DriverQueryDto driverQueryDto);
	
	 /**
     * 根据大区编码查询小区 
     */
    public List<DriverQueryDto> querySmallZoneCodesByBigZoneCodes(DriverQueryDto driverQueryDto);
}