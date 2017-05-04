package com.deppon.foss.module.pickup.common.client.service;

import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcTranferEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;

/**
 * 加收方案计算接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Foss-351326-xingjun,date:2016-8-30 下午2:57:16,content:TODO </p>
 * @author Foss-351326-xingjun 
 * @date 2016-8-30 下午2:57:16
 * @since
 * @version
 */
public interface IAddedFeeCalculateService {
	/**
	 * gui开单加收方案计算
	 * @author Foss-351326-xingjun 
	 * @date 2016-8-30 下午3:13:38
	 * @param bean
	 * @see
	 */
	public void guiWaybillCreate(WaybillPanelVo bean);
	
	/**
	 * gui改单处理加收费计算
	 * @author Foss-351326-xingjun 
	 * @date 2016-9-2 下午2:19:06
	 * @param bean
	 * @param finalCustomerPickupOrgCode
	 * @param dto
	 * @see
	 */
	public void guiWabillChange(WaybillPanelVo bean,String finalCustomerPickupOrgCode,WaybillDto dto);
	
	
	/**
	 * 运单更改中转信息计算加收费
	 * @author Foss-351326-xingjun 
	 * @date 2016-9-5 上午11:24:24
	 * @param bean
	 * @param waybillRfcTranferEntity
	 * @see
	 */
	public void guiWaybillRfcTranfer(WaybillPanelVo bean,WaybillRfcTranferEntity waybillRfcTranferEntity);
}
