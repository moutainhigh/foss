package com.deppon.foss.module.transfer.common.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcBillPayableConditionRequest;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcBillPayableConditionResponse;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcExternalBillRequest;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcVehicleAssembleBillRequest;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcVehicleAssembleBillResponse;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcExternalBillResponse;
/**
 * FOSS - TFR 传递给CUBC同步service
 * @author 310248
 */
public interface IFossToCubc extends IService {

	/**
	 * 更新偏线外发单推送CUBC数据
	 * @author 310248
	 * @param requestStr
	 * @return
	 */
	public CubcExternalBillResponse pushUpdataExternalBill(CubcExternalBillRequest request);

	/**
	 * 推送给cubc订单状态，
	 * 审核、反审核，作废外发单
	 * @author 310248
	 * @param requestStr
	 * @return
	 */
	public CubcExternalBillResponse pushExternalBillStatus(CubcExternalBillRequest request);
	
	/**
	 * 零担修改外请车配载单调用接CUBC接口
	 * @author 310248
	 * @param requestStr
	 * @return
	 */
	public CubcVehicleAssembleBillResponse pushmodifyTruckStowage(CubcVehicleAssembleBillRequest request);
	
	/**
	 * 零担作废外请车配载单调用接CUBC接口
	 * @author 310248
	 * @param requestStr
	 * @return
	 */
	public CubcVehicleAssembleBillResponse pushdisableTruckStowage(CubcVehicleAssembleBillRequest request);
	
	/**
	 * 费用调整审批通过调用CUBC接口
	 * @author 310248
	 * @param requestStr
	 * @return
	 */
	public CubcVehicleAssembleBillResponse pushadjustOutVehicleFee(CubcVehicleAssembleBillRequest request);
    
	/**
	 * 查询货物轨迹返回CUBC
	 * @author 310248
	 * @param request
	 * @return
	 */
	public CubcBillPayableConditionResponse queryBillPayableByCondition(CubcBillPayableConditionRequest request);


}
