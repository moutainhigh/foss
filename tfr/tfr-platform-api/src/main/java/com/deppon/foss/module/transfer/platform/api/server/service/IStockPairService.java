package com.deppon.foss.module.transfer.platform.api.server.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.platform.api.shared.domain.StockPairEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TransCenterOrgEntity;


/**
* @description 库存副表的Service
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年2月28日 上午9:24:05
*/
public interface IStockPairService extends IService {
	
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
	* @description 删除库存副表
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
	* @description 定时任务
	* @param threadNo
	* @param threadCount
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月4日 上午9:25:40
	*/
	void doStockPairJob(Date queryDate,int threadNo,int threadCount) throws Exception;
	
	
	/**
	* @description 定时任务执行完毕之后
	* @param Date
	* @throws Exception
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月20日 上午9:19:35
	*/
	void doStockPairJobContinue(Date queryDate) throws Exception;
	
	
	/**
	* @description 所有外场(包含有派送部的外场)对应的 事业部 本部
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月13日 上午11:39:50
	*/
	List<TransCenterOrgEntity> queryAllTransOrg();
	
	
	/**
	* @description 所有外场对应的 事业部 本部 大区
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月2日 上午11:00:44
	*/
	List<TransCenterOrgEntity> queryAllOutTransOrg();
	
	
}
