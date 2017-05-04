package com.deppon.foss.module.pickup.order.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.order.api.shared.domain.CourierReportEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.CourierQueryDto;

/**
 * 
 * @ClassName: ICourierReportEntityDao
 * @Description: 快递每日统计报表DAO层接口
 * @author YANGBIN
 * @date 2014-5-9 下午4:15:06
 * 
 */
public interface ICourierReportEntityDao {
	/**
	 * 
	 * @Title: insertOrderReport
	 * @Description: 新增一条每日报表记录
	 * @param @param courierReportEntity
	 * @param @return 设定文件
	 * @return int 返回类型
	 * @throws
	 */
	public int insertCourierReport(CourierReportEntity courierReportEntity);

	/**
	 * 
	 * @Title: queryCourierByCommon
	 * @Description: 根据查询条件返回查询集合
	 * @param @param courierQueryDto
	 * @param @return 设定文件
	 * @return List<CourierReportEntity> 返回类型
	 * @throws
	 */
	public List<CourierReportEntity> queryCourierByCommon(
			CourierQueryDto courierQueryDto,int start, int limit);
	
	/**
	 * 
	 * @Title: queryCourierByCommon
	 * @Description: 根据查询条件返回查询集合
	 * @param @param courierQueryDto
	 * @param @return 设定文件
	 * @return List<CourierReportEntity> 返回类型
	 * @throws
	 */
	public List<CourierReportEntity> queryCourierByCommon(
			CourierQueryDto courierQueryDto);
	
	/**
	 * 
	 * @Title: queryCourierByCommon
	 * @Description: 根据查询条件返回查询总数
	 * @param @param courierQueryDto
	 * @param @return 设定文件
	 * @return Long
	 * @throws
	 */
	public Long queryCourierByCommonCount(CourierQueryDto courierQueryDto);
	
	/**
	 * 
	 * @Title: queryCourierByCommon
	 * @Description: 查询统计总数
	 * @param @param courierQueryDto
	 * @param @return 设定文件
	 * @return List<CourierReportEntity>
	 * @throws
	 */
	public List<CourierReportEntity> queryCourierReportByDay(Date curDate, Date preDate);
	
	/**
	 * 按日志刪除报表统计数据
	 * deleteCourierReportByDay: <br/>
	 * 
	 * Date:2014-7-14下午5:03:48
	 * @author 157229-zxy
	 * @param date
	 * @return
	 * @since JDK 1.6
	 */
	public int deleteCourierReportByDay(Date date);
}