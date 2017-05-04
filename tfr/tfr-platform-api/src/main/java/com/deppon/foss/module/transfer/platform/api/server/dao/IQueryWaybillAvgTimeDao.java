package com.deppon.foss.module.transfer.platform.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.domain.WaybillAvgTimeEntity;
import com.deppon.foss.module.transfer.platform.api.shared.vo.DeliverGoodsAreaQueryVo;

public interface IQueryWaybillAvgTimeDao {
     
	
	public List<WaybillAvgTimeEntity> queryWaybillAvgTimeEntityList(DeliverGoodsAreaQueryVo deliverGoodsAreaQueryVo, int start, int limit);	
	
	public long queryWaybillAvgTimeEntityListCount(DeliverGoodsAreaQueryVo deliverGoodsAreaQueryVo);
	
}
