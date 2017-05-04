/**
 * 
 */
package com.deppon.foss.module.transfer.platform.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.platform.api.server.dao.IFieldStockMovtionDao;
import com.deppon.foss.module.transfer.platform.api.shared.domain.FieldStockMovtionDetailEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.FieldStockMovtionEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QueryConditionStockMovtionDto;

/**
 * @author 105795
 * @desc 库区库存流动
 * @date 2015-03-03
 */
public class FieldStockMovtionDao extends iBatis3DaoImpl implements IFieldStockMovtionDao {

 private static final String NAMESPACE = "foss.platform.fieldStockMovtion.";

	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 根据货区类型查询货区编码
	 *@param origOrgCode,goodsTypeList
	 *@return List<String>
	 *@author 105795
	 *@date 2015年3月3日下午2:21:05 
	 */
	@SuppressWarnings("unchecked")
	public List<String> queryGoodsCodeByGoodsType(String origOrgCode,List<String> goodsTypeList)
	{
		Map<Object,Object> dataMap=new HashMap<Object,Object>();
		dataMap.put("origOrgCode", origOrgCode);
		dataMap.put("goodsTypeList", goodsTypeList);
		return getSqlSession().selectList(NAMESPACE+"queryGoodsCodeByGoodsType", dataMap);
	}
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 到达未卸车(包括长途短途) 
	 *@param queryCondition
	 *@return List<FieldStockMovtionDetailEntity>
	 *@author 105795
	 *@date 2015年3月3日上午11:13:21 
	 */
	@SuppressWarnings("unchecked")
	public List<FieldStockMovtionDetailEntity> queryArrivedUnload(QueryConditionStockMovtionDto queryCondition,int start, int limit)
	{
		if(start==0&&limit==0)
		{
			return getSqlSession().selectList(NAMESPACE+"queryArrivedUnload", queryCondition);
		}else{
			RowBounds rowBounds=new RowBounds(start,limit);
			return getSqlSession().selectList(NAMESPACE+"queryArrivedUnload", queryCondition,rowBounds);
		}
	}
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 包含长途、短途、集中接货卸车
	 *@param queryCondition
	 *@return List<FieldStockMovtionDetailEntity>
	 *@author 105795
	 *@date 2015年3月3日下午2:16:47 
	 */
	@SuppressWarnings("unchecked")
	public List<FieldStockMovtionDetailEntity> queryUnLoading(QueryConditionStockMovtionDto queryCondition,int start, int limit)
	{
		if(start==0&&limit==0)
		{
			return getSqlSession().selectList(NAMESPACE+"queryUnLoading", queryCondition);
		}else{
			RowBounds rowBounds=new RowBounds(start,limit);
			return getSqlSession().selectList(NAMESPACE+"queryUnLoading", queryCondition,rowBounds);
		}
	}
	

	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 待叉区：托盘绑定未叉车扫描，且在库存中、非正在装车 
	 *@param queryCondition
	 *@return List<FieldStockMovtionDetailEntity>
	 *@author 105795
	 *@date 2015年3月3日下午2:18:57 
	 */
	@SuppressWarnings("unchecked")
	public List<FieldStockMovtionDetailEntity> queryTray(QueryConditionStockMovtionDto queryCondition,int start, int limit)
	{
		if(start==0&&limit==0)
		{
			return getSqlSession().selectList(NAMESPACE+"queryTray", queryCondition);
		}else{
			RowBounds rowBounds=new RowBounds(start,limit);
			return getSqlSession().selectList(NAMESPACE+"queryTray", queryCondition,rowBounds);
		}
	}
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 包装库区：包装库区、非正在装车  
	 *@param queryCondition
	 *@return List<FieldStockMovtionDetailEntity>
	 *@author 105795
	 *@date 2015年3月3日下午2:18:57 
	 */
	@SuppressWarnings("unchecked")
	public List<FieldStockMovtionDetailEntity> queryPackaging(QueryConditionStockMovtionDto queryCondition,int start, int limit)
	{
		if(start==0&&limit==0)
		{
			return getSqlSession().selectList(NAMESPACE+"queryPackaging", queryCondition);
		}else{
			RowBounds rowBounds=new RowBounds(start,limit);
			return getSqlSession().selectList(NAMESPACE+"queryPackaging", queryCondition,rowBounds);
		}
	}
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 零担中转库区：库存中,非快递货、非包装库区，非派送库区、非偏线库区、非空运库库区、非待叉区,非正在装车
	 *@param queryCondition
	 *@return List<FieldStockMovtionDetailEntity>
	 *@author 105795
	 *@date 2015年3月3日下午2:18:57 
	 */
	@SuppressWarnings("unchecked")
	public List<FieldStockMovtionDetailEntity> queryTfrStock(QueryConditionStockMovtionDto queryCondition,int start, int limit)
	{
		if(start==0&&limit==0)
		{
			return getSqlSession().selectList(NAMESPACE+"queryTfrStock", queryCondition);
		}else{
			RowBounds rowBounds=new RowBounds(start,limit);
			return getSqlSession().selectList(NAMESPACE+"queryTfrStock", queryCondition,rowBounds);
		}
	}
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 零担派送库区：派送库区库存中、非待叉区,非正在装车
	 *@param queryCondition
	 *@return List<FieldStockMovtionDetailEntity>
	 *@author 105795
	 *@date 2015年3月3日下午2:18:57 
	 */
	@SuppressWarnings("unchecked")
	public List<FieldStockMovtionDetailEntity> queryTfrPickupStock(QueryConditionStockMovtionDto queryCondition,int start, int limit)
	{
		if(start==0&&limit==0)
		{
			return getSqlSession().selectList(NAMESPACE+"queryTfrPickupStock", queryCondition);
		}else{
			RowBounds rowBounds=new RowBounds(start,limit);
			return getSqlSession().selectList(NAMESPACE+"queryTfrPickupStock", queryCondition,rowBounds);
		}
	}
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 快递中转库区库存
	 *@param queryCondition
	 *@return List<FieldStockMovtionDetailEntity>
	 *@author 105795
	 *@date 2015年3月3日下午2:18:57 
	 */
	@SuppressWarnings("unchecked")
	public List<FieldStockMovtionDetailEntity> queryRcpStock(QueryConditionStockMovtionDto queryCondition,int start, int limit)
	{
		if(start==0&&limit==0)
		{
			return getSqlSession().selectList(NAMESPACE+"queryRcpStock", queryCondition);
		}else{
			RowBounds rowBounds=new RowBounds(start,limit);
			return getSqlSession().selectList(NAMESPACE+"queryRcpStock", queryCondition,rowBounds);
		}
	}
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 快递派送库区库存 
	 *@param queryCondition
	 *@return List<FieldStockMovtionDetailEntity>
	 *@author 105795
	 *@date 2015年3月3日下午2:18:57 
	 */
	@SuppressWarnings("unchecked")
	public List<FieldStockMovtionDetailEntity> queryRcpPickupStock(QueryConditionStockMovtionDto queryCondition,int start, int limit)
	{
		if(start==0&&limit==0)
		{
			return getSqlSession().selectList(NAMESPACE+"queryRcpPickupStock", queryCondition);
		}else{
			RowBounds rowBounds=new RowBounds(start,limit);
			return getSqlSession().selectList(NAMESPACE+"queryRcpPickupStock", queryCondition,rowBounds);
		}
	}
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao  
	 *@desc 正在装车 
	 *@param queryCondition
	 *@return List<FieldStockMovtionDetailEntity>
	 *@author 105795
	 *@date 2015年3月3日下午2:18:57 
	 */
	@SuppressWarnings("unchecked")
	public List<FieldStockMovtionDetailEntity> queryLoading(QueryConditionStockMovtionDto queryCondition,int start, int limit){
		if(start==0&&limit==0)
		{
			return getSqlSession().selectList(NAMESPACE+"queryLoading", queryCondition);
		}else{
			RowBounds rowBounds=new RowBounds(start,limit);
			return getSqlSession().selectList(NAMESPACE+"queryLoading", queryCondition,rowBounds);
		}
	}
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao
	 *@desc  计算外场到达未卸总票数、重量、体积
	 *@param queryCondition
	 *@return FieldStockMovtionEntity
	 *@author 105795
	 *@date 2015年6月27日 
	 */
	public FieldStockMovtionEntity queryArrivedUnloadTotalVote(QueryConditionStockMovtionDto queryCondition) {
		
		Map<String,String> map=new HashMap<String,String>(); 
		map.put("orgCode", queryCondition.getOrigOrgCode());
		return (FieldStockMovtionEntity) getSqlSession().selectOne(NAMESPACE+"queryArrivedUnloadTotalVote", map);
	}
	

	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao
	 *@desc 计算卸车中总票数
	 *@param QueryConditionStockMovtionDto
	 *@return FieldStockMovtionEntity
	 *@author 105795
	 *@date 2015年6月27日 
	 */
	public FieldStockMovtionEntity queryUnLoadingTotalVote(QueryConditionStockMovtionDto queryCondition) {

		Map<String,String> map=new HashMap<String,String>(); 
		map.put("orgCode", queryCondition.getOrigOrgCode());
		
		return (FieldStockMovtionEntity) getSqlSession().selectOne(NAMESPACE+"queryUnLoadingTotalVote", map);
	}

	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao
	 *@desc 计算待叉区库存总票数
	 *@param QueryConditionStockMovtionDto
	 *@return FieldStockMovtionEntity
	 *@author 105795
	 *@date 2015年6月27日 
	 */
	public FieldStockMovtionEntity queryTrayTotalVote(QueryConditionStockMovtionDto queryCondition) {
		
		Map<String,String> map=new HashMap<String,String>(); 
		map.put("orgCode", queryCondition.getOrigOrgCode());
		
		return (FieldStockMovtionEntity) getSqlSession().selectOne(NAMESPACE+"queryTrayTotalVote", map);
	}

	
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao
	 *@desc 计算包装库区库存总票数 
	 *@param queryCondition
	 *@return FieldStockMovtionEntity
	 *@author 105795
	 *@date 2015年6月27日 上午10:22:45 
	 */
	public FieldStockMovtionEntity queryPackagingTotalVote(QueryConditionStockMovtionDto queryCondition) {
		Map<String,String> map=new HashMap<String,String>(); 
		map.put("orgCode", queryCondition.getOrigOrgCode());
		map.put("goodsAreaCode", queryCondition.getGoodsAreaCode());
		return (FieldStockMovtionEntity) getSqlSession().selectOne(NAMESPACE+"queryPackagingTotalVote", map);
		
	}

	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao
	 *@desc 计算零担中转库区库存总票数
	 *@param QueryConditionStockMovtionDto
	 *@return FieldStockMovtionEntity
	 *@author 105795
	 *@date 2015年6月27日 上午10:26:37 
	 */
	public FieldStockMovtionEntity queryTfrStockTotalVote(QueryConditionStockMovtionDto queryCondition) {
		Map<String,Object> map=new HashMap<String,Object>(); 
		map.put("orgCode", queryCondition.getOrigOrgCode());
		map.put("goodsAreaCodeList", queryCondition.getGoodsAreaCodeList());
		return (FieldStockMovtionEntity) getSqlSession().selectOne(NAMESPACE+"queryTfrStockTotalVote", map);
	}

	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao
	 *@desc 计算零担派送库区库存总票数
	 *@param QueryConditionStockMovtionDto
	 *@return FieldStockMovtionEntity
	 *@author 105795
	 *@date 2015年6月27日 上午10:28:00 
	 */
	public FieldStockMovtionEntity queryTfrPickupStockTotalVote(QueryConditionStockMovtionDto queryCondition) {
		Map<String,String> map=new HashMap<String,String>(); 
		map.put("orgCode", queryCondition.getOrigOrgCode());
		map.put("goodsAreaCode", queryCondition.getGoodsAreaCode());
		return (FieldStockMovtionEntity) getSqlSession().selectOne(NAMESPACE+"queryTfrPickupStockTotalVote", map);
	}
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao
	 *@desc 计算快递中转库区库存总票数 
	 *@param QueryConditionStockMovtionDto
	 *@return FieldStockMovtionEntity
	 *@author 105795
	 *@date 2015年6月27日 上午10:29:01 
	 */
	public FieldStockMovtionEntity queryRcpStockTotalVote(QueryConditionStockMovtionDto queryCondition) {
		Map<String,String> map=new HashMap<String,String>(); 
		map.put("orgCode", queryCondition.getOrigOrgCode());
		map.put("goodsAreaCode", queryCondition.getGoodsAreaCode());
		return (FieldStockMovtionEntity) getSqlSession().selectOne(NAMESPACE+"queryRcpStockTotalVote", map);
	}

	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao
	 *@desc 计算快递派送库区库存总票数
	 *@param QueryConditionStockMovtionDto
	 *@return FieldStockMovtionEntity
	 *@author 105795
	 *@date 2015年6月27日 上午10:30:50 
	 */
	public FieldStockMovtionEntity queryRcpPickupStockTotalVote(QueryConditionStockMovtionDto queryCondition) {
		Map<String,String> map=new HashMap<String,String>(); 
		map.put("orgCode", queryCondition.getOrigOrgCode());
		map.put("goodsAreaCode", queryCondition.getGoodsAreaCode());
		return (FieldStockMovtionEntity) getSqlSession().selectOne(NAMESPACE+"queryRcpPickupStockTotalVote", map);
	}

	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.dao
	 *@desc 计算已装车总票数 
	 *@param QueryConditionStockMovtionDto
	 *@return FieldStockMovtionEntity
	 *@author 105795
	 *@date 2015年6月27日 上午10:31:35 
	 */
	public FieldStockMovtionEntity queryLoadingTotalVote(QueryConditionStockMovtionDto queryCondition) {
		Map<String,String> map=new HashMap<String,String>(); 
		map.put("orgCode", queryCondition.getOrigOrgCode());
		return (FieldStockMovtionEntity) getSqlSession().selectOne(NAMESPACE+"queryLoadingTotalVote", map);	}
}
