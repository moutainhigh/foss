package com.deppon.foss.module.settlement.common.api.server.service;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.GreenHandWrapWriteoffEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.RequestGreenHandWrapEntity;

/**
 * @author 310970
 */
//核销应收单
public interface IGreenHandWrapWriteoffService {
	//根据dop传过来的单号进行查询  
	public List<BillReceivableEntity> queryReceivableBill(RequestGreenHandWrapEntity request);
	
	//始发应收单核销
	public void writeoffByDoprequest(BillReceivableEntity entity,RequestGreenHandWrapEntity request);
	
	//将数据插入到暂存表里
	public void addDopInfo(RequestGreenHandWrapEntity entity);

	/**
     * @author 231434-bieyexiong
     * @date 2016-06-25 8:22
     * 根据单号查询未核销暂存表的信息
     */
    List<GreenHandWrapWriteoffEntity> queryGreenHandWrapByWaybillNo(String waybillNo);
}
