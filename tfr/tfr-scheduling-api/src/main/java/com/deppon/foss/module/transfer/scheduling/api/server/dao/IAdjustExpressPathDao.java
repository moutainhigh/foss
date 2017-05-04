package com.deppon.foss.module.transfer.scheduling.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.AdjustEntity;


/**
 * 
 * @desc 快递走货路径调整dao
 * @author wqh
 * @date 2014-12-08
 */
public interface IAdjustExpressPathDao {

	//查询快递修改的走货路径集合
	List<AdjustEntity> queryExpressByParamMap(Map<String, Object> paramMap,int limit, int start);

	//查询快递运单对应的走货路径集合
	List<AdjustEntity> queryExpressWaybillListByParamMap(Map<String, Object> paramMap);

	//查询总条数
	long getCount(Map<String, Object> paramMap);
	
	//查询快递运单
	List<AdjustEntity> queryExpressWaybillList(Map<String, Object> paramMap);
	
	
}
