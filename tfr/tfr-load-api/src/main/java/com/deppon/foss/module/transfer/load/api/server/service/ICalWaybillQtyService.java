package com.deppon.foss.module.transfer.load.api.server.service;

import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.dto.CalWaybillQtyDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpExternalBillDto;

public interface ICalWaybillQtyService {
	/***
	 * 运输中票数:	查询时间范围内，货物状态为运输中票数之和。
	 * 根据接送货传过来的运单号集合,计算出所有状态为运输中的票数 ,分批配载的只要有运单在途,就算在途
	 * 2015-07-22 zwd 200968 
	 * 参数:运单号集合
	 * 返回值:运输中票数之和
	 */
	
	public  CalWaybillQtyDto calWaybillOnQtys(List<String> waybillList);
	
	/**
	 * 按照运单号查找外发单号和外发公司  zwd 200968 2016-2-24
	 * @param waybillList
	 * @return
	 */
	public List<LdpExternalBillDto> queryLdpExternalBillNoListByWayBillNos(List<String> waybillList);
}

