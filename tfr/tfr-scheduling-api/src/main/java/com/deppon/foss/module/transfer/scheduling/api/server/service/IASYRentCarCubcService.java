package com.deppon.foss.module.transfer.scheduling.api.server.service;


import com.deppon.foss.module.transfer.scheduling.api.shared.dto.RentCarCubcRequest;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.RentCarCubcResponse;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.CrmBudgetControlRequestEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.CrmBudgetControlResponseEntity;



public interface IASYRentCarCubcService {

	public RentCarCubcResponse pushaddRentCar(RentCarCubcRequest rentCarCubcList);
	
	/**
	 * author:lurker-lee
	 * date  :2017-04-18
	 * description: 临时租车标记同步给FSSC系统，走ESB接口
	 */
	public CrmBudgetControlResponseEntity pushTemptalMarkFeeInfoToFSSC(CrmBudgetControlRequestEntity crmRequestEnt);
	
	/**
	 * 本地测试直连FSSC接口（不提交此代码）
	 * @param crmRequestEnt
	 * @return
	 */
	//public CrmBudgetControlResponseEntity callFSSCInterface(CrmBudgetControlRequestEntity crmRequestEnt);

}
