/**   
* @Title: DailyLoadVolumeDao.java 
* @Package com.deppon.foss.module.transfer.platform.server.dao.impl 
* @Description: 日承载货量dao
* @author shiwei-045923-shiwei@outlook.com
* @date 2014年6月26日 上午10:53:39 
* @version V1.0   
*/
package com.deppon.foss.module.transfer.platform.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.platform.api.server.dao.IDailyLoadVolumeDao;
import com.deppon.foss.module.transfer.platform.api.shared.domain.DailyLoadVolumeEntity;

/** 
 * @ClassName: DailyLoadVolumeDao 
 * @Description: 日承载货量dao
 * @author shiwei-045923-shiwei@outlook.com
 * @date 2014年6月26日 上午10:53:39 
 *  
 */
public class DailyLoadVolumeDao extends iBatis3DaoImpl implements
		IDailyLoadVolumeDao {
	
	private static final String NAMESPACE = "com.deppon.foss.module.transfer.platform.dailyLoadVolume.";

	/** 
	 * @Title: addDailyLoadVolumeEntity 
	 * @Description: 新增
	 * @author shiwei-045923-shiwei@outlook.com
	 * @date 2014年6月26日 上午10:53:39 
	 * @param @param dailyLoadVolumeEntity
	 * @param @return    设定文件 
	 * @throws 
	 */
	@Override
	public boolean addDailyLoadVolumeEntity(
			DailyLoadVolumeEntity dailyLoadVolumeEntity) {
		getSqlSession().insert(NAMESPACE + "addDailyLoadVolumeEntity",dailyLoadVolumeEntity);
		return true;
	}

	/** 
	 * @Title: queryDailyLoadVolumeList 
	 * @Description: 查询
	 * @author shiwei-045923-shiwei@outlook.com
	 * @date 2014年6月26日 上午10:53:39 
	 * @param @param dailyLoadVolumeEntity
	 * @param @return    设定文件 
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DailyLoadVolumeEntity> queryDailyLoadVolumeList(
			DailyLoadVolumeEntity dailyLoadVolumeEntity,int limit,int start) {
		RowBounds rb = new RowBounds(start,limit);
		return getSqlSession().selectList(NAMESPACE + "queryDailyLoadVolumeList",dailyLoadVolumeEntity,rb);
	}

	/** 
	 * @Title: queryDailyLoadVolumeCount 
	 * @Description: 查询总量
	 * @author shiwei-045923-shiwei@outlook.com
	 * @date 2014年6月26日 上午10:53:39 
	 * @param @param dailyLoadVolumeEntity
	 * @param @return    设定文件 
	 * @throws 
	 */
	@Override
	public Long queryDailyLoadVolumeCount(
			DailyLoadVolumeEntity dailyLoadVolumeEntity) {
		return (Long)getSqlSession().selectOne(NAMESPACE + "queryDailyLoadVolumeCount", dailyLoadVolumeEntity);
	}

	/** 
	 * @Title: updateDailyLoadVolumeEntity 
	 * @Description: 更新
	 * @author shiwei-045923-shiwei@outlook.com
	 * @date 2014年6月26日 上午10:53:39 
	 * @param @param dailyLoadVolumeEntity
	 * @param @return    设定文件 
	 * @throws 
	 */
	@Override
	public boolean updateDailyLoadVolumeEntity(
			DailyLoadVolumeEntity dailyLoadVolumeEntity) {
		getSqlSession().update(NAMESPACE + "updateDailyLoadVolumeEntity",dailyLoadVolumeEntity);
		return true;
	}

	/** 
	* @Title: queryDailyLoadVolumeHistoryList 
	* @Description: 查询某转运场日承载货量的修改记录
	* @author shiwei-045923-shiwei@outlook.com
	* @date 2014年6月26日 上午11:33:21 
	* @param @param dailyLoadVolumeEntity
	* @param @return    设定文件 
	* @throws 
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<DailyLoadVolumeEntity> queryDailyLoadVolumeNoPaging(
			DailyLoadVolumeEntity dailyLoadVolumeEntity) {
		return getSqlSession().selectList(NAMESPACE + "queryDailyLoadVolumeList",dailyLoadVolumeEntity);
	}

	/** 
	* @Title: queryDailyLoadVolumeByOrgCodeAndDate 
	* @Description: 根据日期、部门code获取日承载量信息
	* @author shiwei-045923-shiwei@outlook.com
	* @date 2014年6月29日 下午4:52:26 
	* @param @param orgCode
	* @param @param queryDate
	* @param @return    设定文件 
	* @throws 
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<DailyLoadVolumeEntity> queryDailyLoadVolumeByOrgCodeAndDate(
			String orgCode, Date queryDate) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("orgCode", orgCode);
		map.put("queryDate", queryDate);
		return getSqlSession().selectList(NAMESPACE + "queryDailyLoadVolumeByOrgCodeAndDate",map);
	}

	/** 
	* @Title: queryMonthLoadVolumeByOrgCodeAndDate 
	* @Description: 查询日期段内日承载货量
	* @author shiwei-045923-shiwei@outlook.com
	* @date 2014年6月29日 下午5:41:15 
	* @param @param orgCode
	* @param @param firstDayOfMonth
	* @param @param endQueryDate
	* @param @return    设定文件 
	* @throws 
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<DailyLoadVolumeEntity> queryMonthLoadVolumeByOrgCodeAndDate(
			String orgCode, Date firstDayOfMonth, Date endQueryDate) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("orgCode", orgCode);
		map.put("firstDayOfMonth", firstDayOfMonth);
		map.put("endQueryDate", endQueryDate);
		return getSqlSession().selectList(NAMESPACE + "queryMonthLoadVolumeByOrgCodeAndDate",map);
	}

}
