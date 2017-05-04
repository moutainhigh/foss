package com.deppon.foss.module.transfer.platform.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.platform.api.shared.domain.StockPairEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TransCenterOrgEntity;


/**
* @description 库存副表的操作
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年2月28日 上午9:24:05
*/
public interface IStockPairDao {
	
	/**
	* @description 添加库存副表
	* @param stockPairEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年2月28日 上午9:35:42
	*/
	int addStockPair(StockPairEntity stockPairEntity);
	
	
	/**
	* @description  批量添加库存副表
	* @param stockPairEntityList
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月26日 下午6:06:39
	*/
	void batchAddStockPair(List<StockPairEntity> stockPairEntityList);
	
	/**
	* @description 删除库存副表 所有已表示统计过的数据
	* @param stockPairEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年2月28日 上午9:36:13
	*/
	int deleteStockPair(Map<String,String> map);
	
	
	/**
	* @description 查询库存副表
	* @param stockPairEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年2月28日 上午9:39:14
	*/
	List<StockPairEntity> queryStockPairEntity(StockPairEntity stockPairEntity);
	
	
	
	/**
	* @description 更新库存副表
	* @param stockPairEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年2月28日 上午9:40:00
	*/
	int updateStockPair(Map<String,String> map);
	
	
	/**
	* @description 获取所有的外场以及对应的派送部货区
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月4日 上午10:54:58
	*/
	List<StockPairEntity> transferCenterAndGoodsAreaQuery();
	
	
	/**
	* @description 库存副表定时执行
	* @param threadNo
	* @param threadCount
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月8日 下午2:23:24
	*/
	List<StockPairEntity> queryStockPairJob(Map<String,String> map);
	
	
	/**
	* @description 所有外场(包含有派送部的外场)对应的 事业部 本部
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月13日 上午11:39:50
	*/
	List<TransCenterOrgEntity> queryAllTransOrg();
	
	/**
	* @description 所有外场对应的 事业部 本部
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月2日 上午11:00:44
	*/
	List<TransCenterOrgEntity> queryAllOutTransOrg();
	
}
