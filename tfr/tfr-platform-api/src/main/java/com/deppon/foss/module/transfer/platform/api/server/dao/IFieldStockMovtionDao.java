/**
 * 
 */
package com.deppon.foss.module.transfer.platform.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.domain.FieldStockMovtionDetailEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.FieldStockMovtionEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QueryConditionStockMovtionDto;

/**
 * @author 105795
 * @desc 库区库存流动
 * @date 2015-03-03
 */
/**
 * @author 105795
 *
 */
public interface IFieldStockMovtionDao {
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 根据货区类型查询货区编码
	 *@param origOrgCode,goodsTypeList
	 *@return List<String>
	 *@author 105795
	 *@date 2015年3月3日下午2:21:05 
	 */
	List<String> queryGoodsCodeByGoodsType(String origOrgCode,List<String> goodsTypeList);
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 到达未卸车(包括长途短途) 
	 *@param queryCondition
	 *@return List<FieldStockMovtionDetailEntity>
	 *@author 105795
	 *@date 2015年3月3日上午11:13:21 
	 */
	List<FieldStockMovtionDetailEntity> queryArrivedUnload(QueryConditionStockMovtionDto queryCondition,int start, int limit);
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 包含长途、短途、集中接货卸车
	 *@param queryCondition
	 *@return List<FieldStockMovtionDetailEntity>
	 *@author 105795
	 *@date 2015年3月3日下午2:16:47 
	 */
	List<FieldStockMovtionDetailEntity> queryUnLoading(QueryConditionStockMovtionDto queryCondition,int start, int limit);
	

	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 待叉区：托盘绑定未叉车扫描，且在库存中、非正在装车 
	 *@param queryCondition
	 *@return List<FieldStockMovtionDetailEntity>
	 *@author 105795
	 *@date 2015年3月3日下午2:18:57 
	 */
	List<FieldStockMovtionDetailEntity> queryTray(QueryConditionStockMovtionDto queryCondition,int start, int limit);
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 包装库区：包装库区、非正在装车  
	 *@param queryCondition
	 *@return List<FieldStockMovtionDetailEntity>
	 *@author 105795
	 *@date 2015年3月3日下午2:18:57 
	 */
	List<FieldStockMovtionDetailEntity> queryPackaging(QueryConditionStockMovtionDto queryCondition,int start, int limit);
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 零担中转库区：库存中,非快递货、非包装库区，非派送库区、非偏线库区、非空运库库区、非待叉区,非正在装车
	 *@param queryCondition
	 *@return List<FieldStockMovtionDetailEntity>
	 *@author 105795
	 *@date 2015年3月3日下午2:18:57 
	 */
	List<FieldStockMovtionDetailEntity> queryTfrStock(QueryConditionStockMovtionDto queryCondition,int start, int limit);
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 零担派送库区：派送库区库存中、非待叉区,非正在装车
	 *@param queryCondition
	 *@return List<FieldStockMovtionDetailEntity>
	 *@author 105795
	 *@date 2015年3月3日下午2:18:57 
	 */
	List<FieldStockMovtionDetailEntity> queryTfrPickupStock(QueryConditionStockMovtionDto queryCondition,int start, int limit);
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 快递中转库区库存
	 *@param queryCondition
	 *@return List<FieldStockMovtionDetailEntity>
	 *@author 105795
	 *@date 2015年3月3日下午2:18:57 
	 */
	List<FieldStockMovtionDetailEntity> queryRcpStock(QueryConditionStockMovtionDto queryCondition,int start, int limit);
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 快递派送库区库存 
	 *@param queryCondition
	 *@return List<FieldStockMovtionDetailEntity>
	 *@author 105795
	 *@date 2015年3月3日下午2:18:57 
	 */
	List<FieldStockMovtionDetailEntity> queryRcpPickupStock(QueryConditionStockMovtionDto queryCondition,int start, int limit);
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 正在装车 
	 *@param queryCondition
	 *@return List<FieldStockMovtionDetailEntity>
	 *@author 105795
	 *@date 2015年3月3日下午2:18:57 
	 */
	List<FieldStockMovtionDetailEntity> queryLoading(QueryConditionStockMovtionDto queryCondition,int start, int limit);
	
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao
	 *@desc  计算外场到达未卸总票数、重量、体积
	 *@param queryCondition
	 *@return FieldStockMovtionEntity
	 *@author 105795
	 *@date 2015年6月27日 
	 */
	FieldStockMovtionEntity queryArrivedUnloadTotalVote(QueryConditionStockMovtionDto queryCondition);
	

	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao
	 *@desc 计算卸车中总票数
	 *@param QueryConditionStockMovtionDto
	 *@return FieldStockMovtionEntity
	 *@author 105795
	 *@date 2015年6月27日 
	 */
	FieldStockMovtionEntity queryUnLoadingTotalVote(QueryConditionStockMovtionDto queryCondition);

	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao
	 *@desc 计算待叉区库存总票数
	 *@param QueryConditionStockMovtionDto
	 *@return FieldStockMovtionEntity
	 *@author 105795
	 *@date 2015年6月27日 
	 */
	FieldStockMovtionEntity queryTrayTotalVote(QueryConditionStockMovtionDto queryCondition);

	
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao
	 *@desc 计算包装库区库存总票数 
	 *@param queryCondition
	 *@return FieldStockMovtionEntity
	 *@author 105795
	 *@date 2015年6月27日 上午10:22:45 
	 */
	FieldStockMovtionEntity queryPackagingTotalVote(QueryConditionStockMovtionDto queryCondition);

	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao
	 *@desc 计算零担中转库区库存总票数
	 *@param QueryConditionStockMovtionDto
	 *@return FieldStockMovtionEntity
	 *@author 105795
	 *@date 2015年6月27日 上午10:26:37 
	 */
	FieldStockMovtionEntity queryTfrStockTotalVote(QueryConditionStockMovtionDto queryCondition);

	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao
	 *@desc 计算零担派送库区库存总票数
	 *@param QueryConditionStockMovtionDto
	 *@return FieldStockMovtionEntity
	 *@author 105795
	 *@date 2015年6月27日 上午10:28:00 
	 */
	FieldStockMovtionEntity queryTfrPickupStockTotalVote(QueryConditionStockMovtionDto queryCondition);
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao
	 *@desc 计算快递中转库区库存总票数 
	 *@param QueryConditionStockMovtionDto
	 *@return FieldStockMovtionEntity
	 *@author 105795
	 *@date 2015年6月27日 上午10:29:01 
	 */
	FieldStockMovtionEntity queryRcpStockTotalVote(QueryConditionStockMovtionDto queryCondition);

	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao
	 *@desc 计算快递派送库区库存总票数
	 *@param QueryConditionStockMovtionDto
	 *@return FieldStockMovtionEntity
	 *@author 105795
	 *@date 2015年6月27日 上午10:30:50 
	 */
	FieldStockMovtionEntity queryRcpPickupStockTotalVote(QueryConditionStockMovtionDto queryCondition);

	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao
	 *@desc 计算已装车总票数 
	 *@param QueryConditionStockMovtionDto
	 *@return FieldStockMovtionEntity
	 *@author 105795
	 *@date 2015年6月27日 上午10:31:35 
	 */
	FieldStockMovtionEntity queryLoadingTotalVote(QueryConditionStockMovtionDto queryCondition);

	
}
