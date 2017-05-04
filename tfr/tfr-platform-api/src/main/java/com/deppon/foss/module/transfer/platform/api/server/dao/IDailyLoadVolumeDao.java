/**   
* @Title: IDailyLoadVolumeDao.java 
* @Package com.deppon.foss.module.transfer.platform.api.server.dao 
* @Description: 日承载货量dao接口
* @author shiwei-045923-shiwei@outlook.com
* @date 2014年6月26日 上午10:43:14 
* @version V1.0   
*/
package com.deppon.foss.module.transfer.platform.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.domain.DailyLoadVolumeEntity;

/** 
 * @ClassName: IDailyLoadVolumeDao 
 * @Description: 日承载货量dao接口
 * @author shiwei-045923-shiwei@outlook.com
 * @date 2014年6月26日 上午10:43:14 
 *  
 */
public interface IDailyLoadVolumeDao {
	
	/**
	* @Title: addDailyLoadVolume 
	* @Description: 新增日承载货量信息
	* @author shiwei-045923-shiwei@outlook.com
	* @date 2014年6月26日 上午10:45:12 
	* @param @param dailyLoadVolumeEntity
	* @param @return    boolean
	* @return boolean    返回类型 
	* @throws
	 */
	boolean addDailyLoadVolumeEntity(DailyLoadVolumeEntity dailyLoadVolumeEntity);
	
	/**
	* @Title: queryDailyLoadVolumeList 
	* @Description: 查询日承载货量信息
	* @author shiwei-045923-shiwei@outlook.com
	* @date 2014年6月26日 上午10:46:54 
	* @param @param dailyLoadVolumeEntity
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	List<DailyLoadVolumeEntity> queryDailyLoadVolumeList(DailyLoadVolumeEntity dailyLoadVolumeEntity,int limit,int start);
	
	/**
	* @Title: queryDailyLoadVolumeHistoryList 
	* @Description: 查询某外场承载货量的历史记录
	* @author shiwei-045923-shiwei@outlook.com
	* @date 2014年6月26日 上午11:30:48 
	* @param @param dailyLoadVolumeEntity
	* @param @return    设定文件 
	* @return List<DailyLoadVolumeEntity>    返回类型 
	* @throws
	 */
	List<DailyLoadVolumeEntity> queryDailyLoadVolumeNoPaging(DailyLoadVolumeEntity dailyLoadVolumeEntity);
	
	/**
	* @Title: queryDailyLoadVolumeCount 
	* @Description: 查询日承载货量条数
	* @author shiwei-045923-shiwei@outlook.com
	* @date 2014年6月26日 上午10:51:47 
	* @param @param dailyLoadVolumeEntity
	* @param @return    设定文件 
	* @return Long    返回类型 
	* @throws
	 */
	Long queryDailyLoadVolumeCount(DailyLoadVolumeEntity dailyLoadVolumeEntity);
	
	/**
	* @Title: updateDailyLoadVolumeEntity 
	* @Description: 更新日承载货量
	* @author shiwei-045923-shiwei@outlook.com
	* @date 2014年6月26日 上午10:48:15 
	* @param @param dailyLoadVolumeEntity
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	boolean updateDailyLoadVolumeEntity(DailyLoadVolumeEntity dailyLoadVolumeEntity);
	
	/**
	* @Title: queryDailyLoadVolumeByOrgCodeAndDate 
	* @Description: 根据日期、部门code获取日承载量信息
	* @author shiwei-045923-shiwei@outlook.com
	* @date 2014年6月29日 下午4:47:19 
	* @param @param orgCode
	* @param @param queryDate
	* @param @return    设定文件 
	* @return List<DailyLoadVolumeEntity>    返回类型 
	* @throws
	 */
	List<DailyLoadVolumeEntity> queryDailyLoadVolumeByOrgCodeAndDate(String orgCode,Date queryDate);
	
	/**
	* @Title: queryMonthLoadVolumeByOrgCodeAndDate 
	* @Description: 查询日期段内日承载货量
	* @author shiwei-045923-shiwei@outlook.com
	* @date 2014年6月29日 下午5:40:42 
	* @param @param orgCode
	* @param @param firstDayOfMonth
	* @param @param endQueryDate
	* @param @return    设定文件 
	* @return List<DailyLoadVolumeEntity>    返回类型 
	* @throws
	 */
	List<DailyLoadVolumeEntity> queryMonthLoadVolumeByOrgCodeAndDate(String orgCode,Date firstDayOfMonth,Date endQueryDate);

}
