package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ReturnGoodsRequestEntity;

/**
 * 返货工单Service
 * @author 198771
 *
 */
public interface IReturnGoodsRequestEntityService{	
	void addReturnGoodsRequestEntity(ReturnGoodsRequestEntity entity);
	
	List<ReturnGoodsRequestEntity> queryReturnGoodsRequestEntityByCondition(ReturnGoodsRequestEntity condition);

	void updateReturnGoodsRequestEntity(ReturnGoodsRequestEntity entity);
	
	void updateHandleResult(ReturnGoodsRequestEntity entity);
	//根据原单号查询返货信息

	ReturnGoodsRequestEntity queryReturnGoodsRequestEntityByWayBillNo(String wayBillNo);	
	//根据原单号查询返货信息(受理状态)
	ReturnGoodsRequestEntity queryReturnGoodsEntityByWayBillNo(String wayBillNo);
	
	boolean queryIsHandleByWayBillNo(String wayBillNo);
	
	/**
	 * 验证处理编号是否存在
	 * @param dealNumber
	 * @return
	 */
	boolean queryExistsReturnGoodsRequestEntityByDealnumber(String dealNumber);

}
