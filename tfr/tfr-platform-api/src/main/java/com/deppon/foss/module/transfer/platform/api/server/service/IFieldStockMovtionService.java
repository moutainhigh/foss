/**
 * 
 */
package com.deppon.foss.module.transfer.platform.api.server.service;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.platform.api.shared.domain.FieldStockMovtionDetailEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.FieldStockMovtionEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QueryConditionStockMovtionDto;

/**
 * @author 105795
 * @desc 库区库存流动
 * @date 2015-03-03
 */
public interface IFieldStockMovtionService extends IService {

	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.service 
	 *@desc 根据货区类型查询货区编码
	 *@param origOrgCode,goodsTypeList
	 *@return List<String>
	 *@author 105795
	 *@date 2015年3月3日下午2:21:05 
	 */
	List<String> queryGoodsCodeByGoodsType(String origOrgCode,List<String> goodsTypeList);
	
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.service  
	 *@desc 查询场内库存流动所有情况
	 *@param 
	 *@return List<FieldStockMovtionEntity>
	 *@author 105795
	 *@date 2015年3月3日下午3:39:49 
	 */
	FieldStockMovtionEntity queryFieldStockMovtion(String origOrgCode);
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.service  
	 *@desc 到达未卸车(包括长途短途) 
	 *@param queryCondition
	 *@return List<FieldStockMovtionDetailEntity>
	 *@author 105795
	 *@date 2015年3月3日上午11:13:21 
	 */
	List<FieldStockMovtionDetailEntity> queryArrivedUnload(QueryConditionStockMovtionDto queryCondition,int start, int limit);
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.service  
	 *@desc 包含长途、短途、集中接货卸车
	 *@param queryCondition
	 *@return List<FieldStockMovtionDetailEntity>
	 *@author 105795
	 *@date 2015年3月3日下午2:16:47 
	 */
	List<FieldStockMovtionDetailEntity> queryUnLoading(QueryConditionStockMovtionDto queryCondition,int start, int limit);
	

	/**
	 *com.deppon.foss.module.transfer.platform.api.server.service  
	 *@desc 待叉区：托盘绑定未叉车扫描，且在库存中、非正在装车 
	 *@param queryCondition
	 *@return List<FieldStockMovtionDetailEntity>
	 *@author 105795
	 *@date 2015年3月3日下午2:18:57 
	 */
	List<FieldStockMovtionDetailEntity> queryTray(QueryConditionStockMovtionDto queryCondition,int start, int limit);
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.service  
	 *@desc 包装库区：包装库区、非正在装车  
	 *@param queryCondition
	 *@return List<FieldStockMovtionDetailEntity>
	 *@author 105795
	 *@date 2015年3月3日下午2:18:57 
	 */
	List<FieldStockMovtionDetailEntity> queryPackaging(QueryConditionStockMovtionDto queryCondition,int start, int limit);
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.service  
	 *@desc 零担中转库区：库存中,非快递货、非包装库区，非派送库区、非偏线库区、非空运库库区、非待叉区,非正在装车
	 *@param queryCondition
	 *@return List<FieldStockMovtionDetailEntity>
	 *@author 105795
	 *@date 2015年3月3日下午2:18:57 
	 */
	List<FieldStockMovtionDetailEntity> queryTfrStock(QueryConditionStockMovtionDto queryCondition,int start, int limit);
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.service  
	 *@desc 零担派送库区：派送库区库存中、非待叉区,非正在装车
	 *@param queryCondition
	 *@return List<FieldStockMovtionDetailEntity>
	 *@author 105795
	 *@date 2015年3月3日下午2:18:57 
	 */
	List<FieldStockMovtionDetailEntity> queryTfrPickupStock(QueryConditionStockMovtionDto queryCondition,int start, int limit);
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.service  
	 *@desc 快递中转库区库存
	 *@param queryCondition
	 *@return List<FieldStockMovtionDetailEntity>
	 *@author 105795
	 *@date 2015年3月3日下午2:18:57 
	 */
	List<FieldStockMovtionDetailEntity> queryRcpStock(QueryConditionStockMovtionDto queryCondition,int start, int limit);
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.service  
	 *@desc 快递派送库区库存 
	 *@param queryCondition
	 *@return List<FieldStockMovtionDetailEntity>
	 *@author 105795
	 *@date 2015年3月3日下午2:18:57 
	 */
	List<FieldStockMovtionDetailEntity> queryRcpPickupStock(QueryConditionStockMovtionDto queryCondition,int start, int limit);
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.service  
	 *@desc 正在装车 
	 *@param queryCondition
	 *@return List<FieldStockMovtionDetailEntity>
	 *@author 105795
	 *@date 2015年3月3日下午2:18:57 
	 */
	List<FieldStockMovtionDetailEntity> queryLoading(QueryConditionStockMovtionDto queryCondition,int start, int limit);

	/**
	 *com.deppon.foss.module.transfer.platform.api.server.service  
	 *@desc 根据部门编码查询顶级外场
	 *@param 
	 *@return Map<String,String>
	 *@author 105795
	 *@date 2015年3月4日下午2:55:03 
	 */
	Map<String,String> queryParentTfrCtrCode(String origOrgCode);
}
