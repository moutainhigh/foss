package com.deppon.foss.dubbo.crm.api.service;

import java.util.List;

import com.deppon.foss.dubbo.crm.api.define.WaybillDetailForSOCEntity;


/**
 * CRM理赔申请
 * @author 335284
 * @since 2016.11.26
 */
public interface ICrmClaimApplicationService4dubbo {
	
	/**
	 * 
	 * @param waybillNoList 运单号集合
	 * @return
	 */
	public WaybillDetailForSOCEntity queryWaybillDetailForSOC(List<String> waybillNoList);

}
