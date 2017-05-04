package com.deppon.foss.module.settlement.consumer.api.server.service;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillPickupInfoDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillPickupWriteBackDto;

/**
 * @author 218392 张永雪  FOSS结算开发组
 * @date 2016-05-20 08:03:20
 * FOSS结算配合VTS项目：开单生成财务单据（开单其他付款方式，不包含银行卡）
 *
 */

public interface IVtsWaybillFinanceBillService {

	//运单新增方法
	public void addWaybillFinanceBill(WaybillPickupInfoDto waybillPickupInfoDto,CurrentInfo currentInfo);
	
	//运单更改方法：更改、作废、中止
	void modifyWaybillFinanceBill(WaybillPickupInfoDto waybillPickupInfoDto,CurrentInfo currentInfo);
	
	//处理作废运单
	WaybillPickupWriteBackDto handleRedCancleFinanceBill(WaybillPickupInfoDto waybillPickupInfoDto,String waybillNo,CurrentInfo currentInfo);
	
}
