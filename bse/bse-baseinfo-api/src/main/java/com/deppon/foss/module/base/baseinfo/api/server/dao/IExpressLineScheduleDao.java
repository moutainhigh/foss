package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressLineScheduleEntity;
/**
 *<p>Title: IExpressLineScheduleDao</p>
 * <p>Description:快递班车线路时刻表Dao </p>
 * <p>Company: Deppon</p>
 * @author    130566-ZengJunfan
 * @date       2014-5-13
 */
public interface IExpressLineScheduleDao {
	/**
	 *<p>Title: addExpressLineSchedule</p>
	 *<p>新增快递班车线路时刻表</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-13下午3:58:17
	 * @param entity
	 * @return
	 */
	ExpressLineScheduleEntity addExpressLineSchedule(ExpressLineScheduleEntity entity);
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
	 * 
	 *<p>Title: queryDeptGisIdsByLineName</p>
	 *<p>根据线路名称，查询线路集合</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-17上午10:00:49
	 * @param lineName
	 * @return
	 */
	List<ExpressLineScheduleEntity> queryExpressLineScheduleListByLineName(String lineName,String programName);
}
