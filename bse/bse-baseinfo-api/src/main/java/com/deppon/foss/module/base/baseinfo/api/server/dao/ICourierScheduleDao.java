/**
 * 
 */
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CourierScheduleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CourierScheduleReportEntity;

/**
 *<p>Title: ICourierScheduleDao</p>
 * <p>Description:快递员排班Dao接口 </p>
 * <p>Company: Deppon</p>
 * @author    130566-ZengJunfan
 * @date       2014-4-16
 */
public interface ICourierScheduleDao {
	/**
	 *<p>Title: addCourierScheduleEntity</p>
	 *<P>新增快递员排班信息<P>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-16下午4:45:11
	 * @param courierScheduleEntity
	 * @return
	 */
	CourierScheduleEntity addCourierSchedule(CourierScheduleEntity courierScheduleEntity);
	/**
	 *<p>Title: deleteCourierSchedule</p>
	 *<P>作废快递员排班信息<P>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-16下午4:47:19
	 * @param courierScheduleEntity
	 * @return
	 */
	CourierScheduleEntity deleteCourierSchedule(CourierScheduleEntity courierScheduleEntity);
	/**
	 *<p>Title: updateCourierSchedule</p>
	 *P>修改快递员排班信息<P>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-16下午4:49:26
	 * @param courierScheduleEntity
	 * @return
	 */
	CourierScheduleEntity updateCourierSchedule(CourierScheduleEntity courierScheduleEntity);
	/**
	 *<p>Title: queryCourierScheduleList</p>
	 *<p>动态查询排班信息</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-17下午2:20:11
	 * @param entity
	 * @return
	 */
	List<CourierScheduleEntity> queryCourierScheduleList(CourierScheduleEntity  entity,int start,int limit);
	/**
	 *<p>Title: queryCount</p>
	 *<p>查询总数</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-17下午2:24:19
	 * @param entity
	 * @return
	 */
	long queryCount(CourierScheduleEntity entity);
	/**
	 *<p>Title: queryCourierScheduleListByEntity</p>
	 *<p>通过时间和收派小区查询记录</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-18下午1:33:34
	 * @param entity
	 * @return
	 */
	List<CourierScheduleEntity> queryCourierScheduleListByEntity(
			CourierScheduleEntity entity);
	/**
	 *<p>Title: queryCountByIFloor</p>
	 *<p>通过时间和收派小区，快递员属性，楼层区间判断是否有重叠的记录数</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-18下午3:35:30
	 * @param entity
	 * @return
	 */
	long queryCountByIFloor(CourierScheduleEntity entity);
	/**
	 *<p>Title: queryCourierScheduleById</p>
	 *<p>根据id查询</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-18下午6:08:47
	 * @param entity
	 * @return
	 */
	CourierScheduleEntity queryCourierScheduleById(CourierScheduleEntity entity);
	/**
	 *<p>Title: queryCourierScheduleListByOther</p>
	 *<p>根据条件动态查询</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-24下午6:44:27
	 * @param entity
	 * @return
	 */
	List<CourierScheduleEntity> queryCourierScheduleListByOther(
			CourierScheduleEntity entity);
	/**
	 *<p>Title: queryCourierScheduleReportList</p>
	 *<p>根据报表式查询</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-28下午7:14:24
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 */
	List<CourierScheduleReportEntity> queryCourierScheduleReportList(
			CourierScheduleEntity entity, int start, int limit);
	/**
	 *<p>Title: queryReportListCount</p>
	 *<p>报表式查询count</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-29下午1:35:48
	 * @return
	 */
	long queryReportListCount(CourierScheduleEntity entity);
	/**
	 * 
	 *<p>Title: queryCourierScheduleByCondition</p>
	 *<p>根据收派小区编码和楼层查询排班集合</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-14下午6:43:42
	 * @param courierSchedule
	 * @param intFloor
	 * @return
	 */
	List<CourierScheduleEntity> queryCourierScheduleByCondition(
			CourierScheduleEntity courierSchedule, int intFloor);
	/**
	 *<p>Title: queryCountByIFloor</p>
	 *<p>通过时间和收派小区，快递员属性，楼层区间判断是否有重叠的记录数,不包括本身的情况下</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-18下午3:35:30
	 * @param entity
	 * @return
	 */
	long queryCountByFloorNotIncludeEntity(CourierScheduleEntity entity);
	/**
	 * <p>根据小区编码,小区名称作废相关的小区编码的排班信息</p>
	 * @param expressSmallZoneCode
	 * @return
	 */
	int deleteCourierScheduleByExpressSmallZoneCode(String[] expressSmallZoneCodes);
	/**
	 * 根据小区编码,小区名称修改相关的小区编码，小区名称排班信息
	 * @param newCourierSchedule
	 * @param oldRegionsCode
	 * @param oldRegionsName
	 * @return
	 */
	int updateCourierScheduleByCondition(
			CourierScheduleEntity newCourierSchedule, String oldRegionsCode,
			String oldRegionsName);
	
}
