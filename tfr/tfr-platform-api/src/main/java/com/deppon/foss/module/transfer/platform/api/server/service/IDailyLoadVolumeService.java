/**   
* @Title: IDailyLoadVolumeService.java 
* @Package com.deppon.foss.module.transfer.platform.api.server.service 
* @Description: 日承载货量 service接口
* @author shiwei-045923-shiwei@outlook.com
* @date 2014年6月26日 上午10:49:28 
* @version V1.0   
*/
package com.deppon.foss.module.transfer.platform.api.server.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.platform.api.shared.domain.DailyLoadVolumeEntity;

/** 
 * @ClassName: IDailyLoadVolumeService 
 * @Description: 日承载货量 service接口
 * @author shiwei-045923-shiwei@outlook.com
 * @date 2014年6月26日 上午10:49:28 
 *  
 */
public interface IDailyLoadVolumeService extends IService {
	
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
	* @Title: queryDailyLoadVolumeHistoryList 
	* @Description: 查询某转运场日承载货量的修改记录
	* @author shiwei-045923-shiwei@outlook.com
	* @date 2014年6月26日 上午11:33:21 
	* @param @param dailyLoadVolumeEntity
	* @param @return    设定文件 
	* @throws 
	*/
	List<DailyLoadVolumeEntity> queryDailyLoadVolumeNoPaging(
			DailyLoadVolumeEntity dailyLoadVolumeEntity);
	
	/**
	* @Title: queryFullValueByOrgCode 
	* @Description: 根据转运场code获取日承载货量信息
	* @author shiwei-045923-shiwei@outlook.com
	* @date 2014年6月29日 下午3:50:04 
	* @param @param orgCode
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	DailyLoadVolumeEntity queryDailyLoadVolumeByOrgCode(String orgCode);
	
	/**
	* @Title: queryDailyLoadVolumeByOrgCodeAndDate 
	* @Description: 根据转运场code、查询日期获取日承载货量
	* @author shiwei-045923-shiwei@outlook.com
	* @date 2014年6月29日 下午4:36:26 
	* @param @param orgCode
	* @param @param queryDate
	* @param @return    设定文件 
	* @return BigDecimal    返回类型 
	* @throws
	 */
	DailyLoadVolumeEntity queryDailyLoadVolumeByOrgCodeAndDate(String orgCode,Date queryDate);
	
	/**
	* @Title: queryMonthLoadVolumeByOrgCodeAndDate 
	* @Description: 传入指定的部门code，日期，计算月承载货量，需要查询历史记录
	* @author shiwei-045923-shiwei@outlook.com
	* @date 2014年6月29日 下午5:19:24 
	* @param @param orgCode
	* @param @param queryDate
	* @param @return    设定文件 
	* @return BigDecimal    返回类型 
	* @throws
	 */
	BigDecimal queryMonthLoadVolumeByOrgCodeAndDate(String orgCode,Date queryDate);

}
