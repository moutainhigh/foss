package com.deppon.foss.module.transfer.scheduling.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.AdjustEntity;


/**
 * 
 * @desc 快递走货路径调整service
 * @author wqh
 * @date 2014-12-08
 */
public interface IAdjustExpressPathService extends IService {

	//查询快递修改的走货路径集合
	List<AdjustEntity> queryExpressWaybills(AdjustEntity adjustEntity,int limit, int start);


	//查询总条数
	long getCount(AdjustEntity adjustEntity);

	//查询快递运单
	List<AdjustEntity> queryExpressWaybillList(AdjustEntity adjustEntity);
	
}
