package com.deppon.foss.module.transfer.platform.api.server.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.platform.api.shared.domain.PersonForTransferEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.StockSaturationEntity;


/**
* @description 仓库饱和度操作类
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年3月21日 下午3:32:20
*/
public interface IStockSaturationDao {
	
	/**
	* @description 查询仓库饱和度
	* @param map
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月21日 下午3:31:53
	*/
	List<StockSaturationEntity> queryStockSaturationList(Map<String,String> map,int start,int limit);
	
	
	/**
	* @description 查询仓库饱和度的总记录数
	* @param map
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月21日 下午3:33:12
	*/
	int queryStockSaturationListCount(Map<String,String> map);
	
	
	
	/**
	* @description 日饱和度
	* @param map
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月22日 下午2:33:08
	*/
	List<StockSaturationEntity> queryStockSaturationDayList(Map<String,String> map);
	
	
	/**
	* @description 月仓库饱和度
	* @param map
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月22日 下午2:33:12
	*/
	List<StockSaturationEntity> queryStockSaturationMonthList(Map<String,String> map);
	
	
	/**
	* @description 根据部门code和理论统计时间查询
	* @param map
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月27日 下午3:48:49
	*/
	StockSaturationEntity queryStockSaturationByOrgCodeAndTime(Map<String,String> map);
	
	/**
	* @description 根据主键更新
	* @param stockSaturationEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月27日 下午3:36:25
	*/
	int updateByPrimaryKey(StockSaturationEntity stockSaturationEntity);
	
	
	/**
	* @description 单条插入
	* @param stockSaturationEntity
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月6日 下午4:27:10
	*/
	void insertStockSaturation(StockSaturationEntity stockSaturationEntity);
	
	/**
	* @description 将统计数据批量插入
	* @param list
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月22日 下午2:12:33
	*/
	void batchInsertStockSaturation(List<StockSaturationEntity> list);
	
	
	/**
	* @description 定时任务的查询语句
	* @param map
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月22日 下午2:20:57
	*/
	List<StockSaturationEntity> stockSaturationJobQuery(Map<String,String> map);
	
	
	/**
	* @description 外场对应的负责人
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月12日 上午9:37:45
	*/
	List<PersonForTransferEntity> queryPersonForTranferCenter();
	
	
	/**
	* @description 获取对应外场的月操作货量
	* @param map queryDateA queryDateB orgCode
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年7月3日 下午4:01:10
	*/
	BigDecimal queryOperateMeasureMonth(Map<String,String> map);
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 统计当月预警天数与当月危险预警天数
	 *@param orgCode、startTime、endTime
	 *@return List<StockSaturationEntity>
	 *@author 105795
	 *@date 2015年3月12日下午3:36:36 
	 */
	List<StockSaturationEntity> calculateWarningAndDangerMothDays(List<String> orgCodeList,String startTime, String endTime);
}
