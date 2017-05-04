package com.deppon.foss.module.transfer.platform.server.dao.impl;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.platform.api.server.dao.IStockPairDao;
import com.deppon.foss.module.transfer.platform.api.shared.define.DeliverConstants;
import com.deppon.foss.module.transfer.platform.api.shared.domain.StockPairEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TransCenterOrgEntity;
import com.deppon.foss.util.UUIDUtils;


/**
* @description  库存副表的操作
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年2月28日 上午9:54:25
*/
public class StockPairDaoImpl extends iBatis3DaoImpl implements IStockPairDao {
	/** mapper 文件命名空间*/
	public static final String NAME_SPACE = "foss.platform.stockPair.";

	/**
	* @description 添加库存副表
	* @param stockPairEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年2月28日 上午9:35:42
	*/
	@Override
	public int addStockPair(StockPairEntity stockPairEntity) {
		if(stockPairEntity!=null){
			stockPairEntity.setId(UUIDUtils.getUUID());
		}
		int backNum = this.getSqlSession().insert(NAME_SPACE + "addStockPair", stockPairEntity);
		if(backNum>0){
			return DeliverConstants.SUCCESS;
		}else{
			return DeliverConstants.FAILURE;
		}
	}
	
	
	/**
	* @description  批量添加库存副表
	* @param stockPairEntityList
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月26日 下午6:06:39
	*/
	@Override
	public void batchAddStockPair(List<StockPairEntity> stockPairEntityList) {
		this.getSqlSession().insert(NAME_SPACE + "batchAddStockPair", stockPairEntityList);
	}



	/**
	* @description 删除库存副表 所有已表示统计过的数据
	* @param stockPairEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年2月28日 上午9:36:13
	*/
	@Override
	public int deleteStockPair(Map<String,String> map) {
		int backNum = this.getSqlSession().delete(NAME_SPACE + "deleteStockPair",map);
		
		return backNum;
	}
	
	/**
	* @description 查询库存副表
	* @param stockPairEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年2月28日 上午9:39:14
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<StockPairEntity> queryStockPairEntity(
			StockPairEntity stockPairEntity) {
		return this.getSqlSession().selectList(NAME_SPACE + "queryStockPairEntity", stockPairEntity);
	}

	/**
	* @description 更新库存副表
	* @param stockPairEntity
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年2月28日 上午9:40:00
	*/
	@Override
	public int updateStockPair(Map<String,String> map) {
		int backNum = this.getSqlSession().update(NAME_SPACE + "updateStockPair", map);
		if(backNum>0){
			return DeliverConstants.SUCCESS;
		}else{
			return DeliverConstants.FAILURE;
		}
	}

	
	/**
	* @description 获取所有的外场以及对应的派送部货区
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.platform.api.server.dao.IStockPairDao#transferCenterAndGoodsAreaQuery()
	* @author 14022-foss-songjie
	* @update 2014年3月4日 上午10:55:47
	* @version V1.0
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<StockPairEntity> transferCenterAndGoodsAreaQuery() {
		return this.getSqlSession().selectList(NAME_SPACE + "transferCenterAndGoodsAreaQuery");
	}
	
	/**
	* @description 库存副表定时执行的查询
	* @param threadNo
	* @param threadCount
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月8日 下午2:23:24
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<StockPairEntity> queryStockPairJob(Map<String,String> map) {
		return this.getSqlSession().selectList(NAME_SPACE + "queryStockPairJob",map);
		
	}


	/**
	* @description 所有外场(包含有派送部的外场)对应的 事业部 本部
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月13日 上午11:39:50
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<TransCenterOrgEntity> queryAllTransOrg() {
		return this.getSqlSession().selectList(NAME_SPACE + "queryAllTransOrg");
	}

	/**
	* @description 所有外场对应的 事业部 本部
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月2日 上午11:00:44
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<TransCenterOrgEntity> queryAllOutTransOrg() {
		return this.getSqlSession().selectList(NAME_SPACE + "queryAllOutTransOrg");
	}
	
	
	
}
