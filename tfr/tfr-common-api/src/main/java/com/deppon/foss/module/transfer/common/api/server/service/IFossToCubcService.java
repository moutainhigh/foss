package com.deppon.foss.module.transfer.common.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcBillReceivableResponse;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcCommonResponse;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcModifyAirChangeResponse;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcTruckConfirmArrivalResponse;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcValidationAirJoinTicketResponse;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcValidationSourceBillNoResponse;
import com.deppon.foss.module.transfer.common.api.shared.dto.ResponseExternalBillDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.ResponseToCubcCallBack;

/**
 * 同步信息至CUBC
 * 
 * @author 316759-RuipengWang-foss
 * @date 2016-11-03 14:45:32
 */
public interface IFossToCubcService extends IService {
	
	/**
	 * 修改航空正单信息
	 * 
	 * @param requestStr
	 * @return ResponseToCubcCallBack
	 * 			result（0失败，1成功）
	 */
	public ResponseToCubcCallBack pushUpdateAirWaybill(String requestStr);

	/**
	 * 修改打木架信息
	 * 
	 * @param requestStr
	 * @return ResponseToCubcCallBack
	 * 			result（0失败，1成功）
	 */
	public ResponseToCubcCallBack pushUpdatePacking(String requestStr);
	
	/**
	 * 审核/反审核/作废
	 * 审核：0，反审核：1，作废：2
	 * 
	 * @param requestStr
	 * @return ResponseToCubcCallBack
	 * 			result（0失败，1成功）
	 */
	public ResponseToCubcCallBack pushAuditReverseAuditInvalid(String requestStr);
	
	/**
	 * @author 365455
	 * 修改合大票清单
	 * @param requestStr
	 * @return ResponseToCubcCallBack
	 * 			result（0失败，1成功）
	 * 调用CUBC接口
	 */
	public ResponseExternalBillDto pushupdateSaveAirPickupAnd(String requestStr);
	
	/**
	 * @author 365455
	 * 删除合大票清单
	 * @param requestStr
	 * @return ResponseToCubcCallBack
	 * 			result（0失败，1成功）
	 * 调用CUBC接口
	 */
	public ResponseExternalBillDto pushdeleteAirPickupAnd(String requestStr);
	
	/**
	 * @author 365455
	 * 修改中转提货清单
	 * @param requestStr
	 * @return ResponseToCubcCallBack
	 * 			result（0失败，1成功）
	 * 调用CUBC接口
	 */
	public ResponseExternalBillDto pushupdateairTransfer(String requestStr);
	
	/**
	 * @author 365455
	 * 删除中转提货清单
	 * @param requestStr
	 * @return ResponseToCubcCallBack
	 * 			result（0失败，1成功）
	 * 调用CUBC接口
	 */
	public ResponseExternalBillDto pushdeleteairTransfer(String requestStr);

	/**
	 * 检查小票单号的合法性
	 * @author 335284 魏晓东
	 * @param jsonString com.deppon.foss.module.transfer.scheduling.server.service.impl.TemprentalMarkService.CUBCSmallTicket
	 * @return
	 */
	public String querysmallTicketNumFromCUBC(String jsonString);
	
	
	/**
	 * FOSS与cubc交互通用方法
	 * @author 328768
	 * @param request
	 * @return
	 */
    public CubcCommonResponse fossToCubc(String esbCodeUrl,String msg,Object param);

    /**
	 * 查询运单所需的正确小票号
	 * @author 335284 魏晓东
     * @param jsonString com.deppon.foss.module.transfer.scheduling.server.service.impl.TemprentalMarkService.CUBCSmallTicket
     * @return
     */
	public String querySmallTicketForWayBillFromCUBC(String jsonString);

	/**
	 * 获取车辆到达信息的未结清货款
	 * @author 335284
	 * @param jsonString
	 * @return
	 */
	public String queryArrivalUnverifyFeeFromCUBC(String jsonString);

	/**
	 * 查询外请车总费用
	 * @param jsonString
	 * @return
	 */
	public String queryLeasedTruckTotalFee(String jsonString);
    
    /**
     * 
     *根据来源单号调用cubc查询应付单是否已经核销
     * @author 328768
     * @param param
     * @return
     */
    public CubcValidationSourceBillNoResponse queryBillPayableIsWriteOff(Object param);
    
    /**
	 * @description 验证空运合大票明细
	 * (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.common.api.server.service.IFossToCubcService#queryBillPayableIsWriteOff(java.lang.Object)
	 * @author 328768-foss-gaojianfu
	 * @update 2017年1月5日 下午5:00:50
	 * @version V1.0
	 */
	public CubcValidationAirJoinTicketResponse validateAirJointTicketDetail(Object param);
	
	/**
	 * @description 推送车辆到达确认信息到cubc
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2017年1月6日 下午3:15:40
	 */
	public CubcTruckConfirmArrivalResponse truckConfirm(Object param);
	
	/**
	* @description 调用cubc接口变更清单
	* @param param
	* @return
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2017年1月7日 上午10:45:16
	 */
	public CubcModifyAirChangeResponse modifyAirChangeDetail(Object param);
	
	/**
	* @description FOSS调用CUBC查询财务单据服务
	* @param param
	* @return
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2017年1月7日 上午10:45:16
	 */
	public CubcBillReceivableResponse queryReceivableAmount(Object param);
}
