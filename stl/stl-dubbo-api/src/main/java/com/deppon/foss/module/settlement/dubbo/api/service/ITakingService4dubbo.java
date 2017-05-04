package com.deppon.foss.module.settlement.dubbo.api.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.dubbo.api.define.BillReceivableEntity;
import com.deppon.foss.module.settlement.dubbo.api.define.WaybillEntity;

public interface ITakingService4dubbo extends IService{
	/**
	 * @author 327090
	 * @param waybill_Nos
	 * @return
	 */
	public List<String> unpaidOnlinePay(List<String> waybill_Nos);
	
	/**
	 * 通过运单号获取运单信息
	 * @author 327090
	 * @param waybillNo
	 * @return
	 */
	public WaybillEntity queryWaybillBasicByNo(String waybillNo);
	
	/**
	 * 根据传入的一到多个来源单号，获取一到多条应收单信息
	 * @author 327090
	 * @param sourceBillNos
	 * @param sourceBillType
	 * @param active
	 * @return
	 */
	public List<BillReceivableEntity> queryBySourceBillNOs(
			List<String> sourceBillNos, String sourceBillType, String active);
	
	

}
