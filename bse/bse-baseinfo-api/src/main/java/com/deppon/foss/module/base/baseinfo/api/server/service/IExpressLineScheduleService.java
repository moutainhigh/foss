package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressLineScheduleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.OrgGisLongitudeDto;

/**
 *<p>Title: IExpressLineScheduleService</p>
 * <p>Description:快递班车线路时刻表Service </p>
 * <p>Company: Deppon</p>
 * @author    130566-ZengJunfan
 * @date       2014-5-13
 */
public interface IExpressLineScheduleService {
	/**
	 *<p>Title: addExpressLineSchedule</p>
	 *<p>新增快递班车线路时刻表</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-13下午3:58:17
	 * @param entity
	 * @return
	 */
	int addExpressLineSchedule(ExpressLineScheduleEntity entity);
	/**
	 *<p>Title: deleteExpressLineSchedule</p>
	 *<p>根据实体作废</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-13下午4:03:28
	 * @param entity
	 * @return
	 */
	int deleteExpressLineSchedule(ExpressLineScheduleEntity entity);
	/**
	 *<p>Title: queryExpressLineScheduleList</p>
	 *<p>分页查询快递线路时刻表</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-13下午3:59:56
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 */
	List<ExpressLineScheduleEntity> queryExpressLineScheduleList(ExpressLineScheduleEntity entity,int start,int limit);
	/**
	 *<p>Title: queryCount</p>
	 *<p>查询记录数</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-13下午4:02:06
	 * @param entity
	 * @return
	 */
	long queryCount(ExpressLineScheduleEntity entity);
	/**
	 *<p>Title: deleteExpressLineScheduleByProgramName</p>
	 *<p>根据方案名称作废</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-14下午2:28:24
	 * @param entity
	 * @return
	 */
	int deleteExpressLineScheduleByProgramName(ExpressLineScheduleEntity entity);
	/**
	 *<p>Title: addExpressLineScheduleMore</p>
	 *<p>批量新增线路时刻表信息</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-14下午4:45:15
	 * @param lineScheduleList
	 * @return
	 */
	int addExpressLineScheduleMore(
			List<ExpressLineScheduleEntity> lineScheduleList);
	/**
	 *<p>Title: deleteLineScheduleListByLineName</p>
	 *<p>根据线路名称批量作废</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-15下午3:46:30
	 * @param lineNameList
	 * @param empCode
	 * @param string 
	 * @return
	 */
	int deleteLineScheduleListByLineName(List<String> lineNameList,
			String programName, String modifyUser);
	/**
	 *<p>Title: queryDeptGisIdsByLineName</p>
	 *<p>根据线路名称查询该线路的所有部门GisId</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-17上午9:51:05
	 * @param lineName
	 * @return
	 */
	List<OrgGisLongitudeDto> queryDeptGisIdsByLineName(String lineName,String programName);
	/**
	 *<p>Title: updateExpressLineSchedule</p>
	 *<p>更新线路</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-17下午7:28:25
	 * @param entity
	 */
	int updateExpressLineSchedule(ExpressLineScheduleEntity entity);
	/**
	 * 
	 *<p>Title: exportLeaseVehicle</p>
	 *<p>导出线路信息</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-21上午11:37:08
	 * @param entity
	 * @return
	 */
	InputStream exportLeaseVehicle(ExpressLineScheduleEntity entity);
	
}
