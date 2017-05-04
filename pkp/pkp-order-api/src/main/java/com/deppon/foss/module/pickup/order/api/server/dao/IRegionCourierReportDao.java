package com.deppon.foss.module.pickup.order.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.order.api.shared.domain.RegionCourierReportEntity;

/**
 * 
 * @ClassName: IOrderVehViewDao
 * @Description: 零担可视化-操作
 * @author YANGBIN
 * @date 2014-5-9 下午4:15:06
 * 
 */
public interface IRegionCourierReportDao {
	
	 /**
     * 
     * @Title: queryOneByCommon 
     * @Description: 查询出司机统计信息
     * @param @param courierCode
     * @param @return    设定文件 
     * @return RegionCourierReportEntity    返回类型 
     * @throws
     */
    public RegionCourierReportEntity queryOneByCommon(String courierCode);
    
    /**
     * 
     * @Title: queryMinCourierByCommon 
     * @Description: 查询最少接货快递员工号
     * @param @param courierList
     * @param @return    设定文件 
     * @return String    返回类型 
     * @throws
     */
    public String queryMinCourierByCommon(List<String> courierList); 
    
    /**
     * 
     * @Title: insert 
     * @Description: 增加快递员统计报表数据
     * @param @param regionCourierReportEntity
     * @param @return    设定文件 
     * @return int    返回类型 
     * @throws
     */
	public int insert(RegionCourierReportEntity regionCourierReportEntity);
    
	/**
     * 
     * @Title: update 
     * @Description: 更新快递员统计报表数据
     * @param @param regionCourierReportEntity
     * @param @return    设定文件 
     * @return int    返回类型 
     * @throws
     */
	public int update(RegionCourierReportEntity regionCourierReportEntity);
	public int delete(RegionCourierReportEntity regionCourierReportEntity);
	
}