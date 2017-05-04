package com.deppon.foss.module.transfer.platform.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.domain.CarEfficiencyEntity;


/**
* @description 车辆装卸车效率管理Dao
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年5月5日 上午11:09:55
*/
public interface ICarEfficiencyManageDao {
	
	
	/**
	* @description 长途装卸车效率查询
	* @param map
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月5日 上午11:11:20
	*/
	List<CarEfficiencyEntity> queryCarEfficiencyWayLong(CarEfficiencyEntity carEfficiency,int start, int limit);
	
	
	/**
	* @description 长途装卸车效率查询 总记录数
	* @param carEfficiency
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月5日 上午11:48:29
	*/
	int queryCarEfficiencyWayLongCount(CarEfficiencyEntity carEfficiency);
	
	
	/**
	* @description 短装卸车效率查询
	* @param map
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月5日 上午11:11:14
	*/
	List<CarEfficiencyEntity> queryCarEfficiencyWayShort(CarEfficiencyEntity carEfficiency,int start, int limit);
	
	/**
	* @description 短装卸车效率查询 总记录数
	* @param carEfficiency
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月5日 上午11:48:43
	*/
	int queryCarEfficiencyWayShortCount(CarEfficiencyEntity carEfficiency);
	
	
	/**
	* @description 相关的理货员
	* @param list
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月5日 下午2:44:24
	*/
	List<CarEfficiencyEntity>  queryLoaderListByTaskId(List<String> taskIdList);
}
