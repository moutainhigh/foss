package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ReturnGoodsRequestEntity;

/**
 * 返货工单上报Dao
 * @author 198771
 *
 */

public interface IReturnGoodsRequestEntityDao {
	//添加
	void addReturnGoodsRequestEntity(ReturnGoodsRequestEntity entity);
	//根据条件进行查询
	List<ReturnGoodsRequestEntity> queryReturnGoodsRequestEntityByCondition(ReturnGoodsRequestEntity condition);
	//作废
	void updateReturnGoodsRequestEntity(ReturnGoodsRequestEntity condition);
	
	void updateHandleResult(ReturnGoodsRequestEntity entity);
	
	Long queryIsHandleByWayBillNo(ReturnGoodsRequestEntity condition);
	
	/**
	 * 验证处理编号是否存在
	 * @param dealNumber
	 * @return
	 */
	boolean queryExistsReturnGoodsRequestEntityByDealnumber(String dealNumber);
	
}
