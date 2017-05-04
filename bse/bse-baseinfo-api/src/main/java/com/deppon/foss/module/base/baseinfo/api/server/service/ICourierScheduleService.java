/**
 * 
 */
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CourierScheduleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CourierScheduleReportEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CourierScheduleExcelDto;

/**
 *<p>Title: ICourierService</p>
 * <p>Description: 快递员排班Service接口</p>
 * <p>Company: Deppon</p>
 * @author    130566-ZengJunfan
 * @date       2014-4-17
 */
public interface ICourierScheduleService {
	/**
	 *<p>Title: addCourierSchedule</p>
	 *<p>新增排班信息</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-17下午4:08:38
	 * @param entity
	 * @return
	 */
	CourierScheduleEntity addCourierSchedule(CourierScheduleEntity entity);
	/**
	 *<p>Title: deleteCourierSchedule</p>
	 *<p>作废排班信息</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-17下午4:09:38
	 *@param entity
	 *@return
	 */
	int deleteCourierSchedule(CourierScheduleEntity entity);
	/**
	 *<p>Title: updateCourierSchedule</p>
	 *<p>修改排班信息</p>
	 * @author 130566-ZengJunfan
	 * @date 2014-4-17下午4:11:45
	 * @param entity
	 * @return
	 */
	CourierScheduleEntity updateCourierSchedule(CourierScheduleEntity entity);
	/**
	 *<p>Title: queryCourierScheduleList</p>
	 *<p>批量查询排班信息</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-17下午4:16:17
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 */
	List<CourierScheduleEntity> queryCourierScheduleList(CourierScheduleEntity entity,int start,int limit);
	/**
	 *<p>Title: queryCount</p>
	 *<p>查询总数</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-17下午4:17:25
	 * @param entity
	 * @return
	 */
	long queryCount(CourierScheduleEntity entity);
	/**
	 *<p>Title: queryCourierScheduleListByOther</p>
	 *<p>根据条件查询</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-24下午6:42:56
	 * @param entity
	 * @return
	 */
	List<CourierScheduleEntity> queryCourierScheduleListByOther(
			CourierScheduleEntity entity);
	/**
	 *<p>Title: addCourierScheduleMore</p>
	 *<p>批量插入数据</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-25下午4:45:39
	 * @param excelDtos
	 * @param empCode
	 */
	int addCourierScheduleMore(List<CourierScheduleExcelDto> excelDtos,
			String empCode);
	/**
	 *<p>Title: addCourierSchedule</p>
	 *<p>插入方法</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-25下午5:00:03
	 * @param entity
	 * @param rowNum
	 * @return
	 */
	int addCourierSchedule(CourierScheduleEntity entity,int rowNum);
	/**
	 *<p>Title: deleteCourierScheduleMore</p>
	 *<p>批量作废 </p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-28上午8:38:10
	 * @param ids
	 * @param empCode
	 */
	int deleteCourierScheduleMore(List<String> ids, String modifyUser);
	/**
	 *<p>Title: queryCourierScheduleReportList</p>
	 *<p>报表式查询实体</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-28下午7:09:12
	 * @param entity
	 * @param limit
	 * @param start
	 * @return
	 */
	List<CourierScheduleReportEntity> queryCourierScheduleReportList(
			CourierScheduleEntity entity, int start, int limit);
	/**
	 *<p>Title: queryReportListCount</p>
	 *<p>报表式查询总数</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-29下午1:32:35
	 * @param entity
	 * @return
	 */
	long queryReportListCount(CourierScheduleEntity entity);
	/**
	 *<p>Title: queryCourierScheduleByCondition</p>
	 *<p>根据小区名称和楼层查询快递排班信息</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-14下午6:21:41
	 * @param expressSmallZoneCode
	 * @param floor
	 * @return
	 */
	List<CourierScheduleEntity> queryCourierScheduleByCondition(String expressSmallZoneName,Integer floor);
	/**
	 * <p>根据小区编码,小区名称作废相关的小区编码的排班信息</p>
	 * @param expressSmallZoneCode
	 * @return
	 */
	int deleteCourierScheduleByExpressSmallZoneCode(String[] expressSmallZoneCodes);
	/**
	 * <p>根据小区编码,小区名称修改相关的小区编码，小区名称排班信息</p>
	 * @param newRegionsCode
	 * @param newRegionsName
	 * @param oldRegionsCode
	 * @param oldRegionsName
	 * @return
	 */
	int updateCourierScheduleByCondition(String newRegionsCode,String newRegionsName,String oldRegionsCode,String oldRegionsName);
	
}
