package com.deppon.foss.module.pickup.oms.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deppon.foss.customerservice.CommonException;
import com.deppon.foss.esb.crm.server.order.BindOrderRequest;
import com.deppon.foss.esb.crm.server.order.BindOrderResponse;
import com.deppon.foss.esb.crm.server.order.ResultDetal;
import com.deppon.foss.esb.crm.server.order.SyncOrderLockInfo;
import com.deppon.foss.esb.crm.server.order.SyncOrderLockInfoRequest;
import com.deppon.foss.esb.crm.server.order.SyncOrderLockInfoResponse;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.OrderLockEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.OrderLockResult;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;

/**
 * FOSS与OMS的接口
 * 
 * @author 325220-FOSS-LiuHui
 * @date 2016年4月9日 上午10:47:21
 */
@Controller
public class OmsServiceImpl {

	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(OmsServiceImpl.class);
	
	/**
	 * ESB_RESULT_CODE
	 */
	public static final String ESB_RESULT_CODE = "ESB-ResultCode";
	
	/**
	 * ESB_RESULT_FIAL_CODE
	 */
	public static final String ESB_RESULT_FIAL_CODE = "00000";
	
	/**
	 * ESB_RESULT_SUCCESS_CODE
	 */
	public static final String ESB_RESULT_SUCCESS_CODE = "10000";

	/**
	 * 运单管理service
	 */
	@Resource
	private IWaybillManagerService waybillManagerService;

	
	
	
	/**
	 * 更新屏信息
	 * @param request
	 * @param response
	 * @return
	 * @throws CommonException
	 */
	@RequestMapping(value = "/syncOrderLockInfo", method = RequestMethod.POST)
	@ResponseBody
	public SyncOrderLockInfoResponse syncOrderLockInfo(@RequestBody SyncOrderLockInfoRequest request,
			HttpServletResponse response) throws CommonException {
		// 响应体
		SyncOrderLockInfoResponse syncOrderLockInfoResponse = new SyncOrderLockInfoResponse();
		try{
			response.setHeader(ESB_RESULT_CODE, String.valueOf(1));
			// 请求内容
			List<SyncOrderLockInfo> syncOrderLockInfos = request.getSyncOrderLockInfo();
			// 处理请求，组装响应体
			List<OrderLockEntity> records = new ArrayList<OrderLockEntity>();
			for (SyncOrderLockInfo syncOrderLockInfo : syncOrderLockInfos) {
				OrderLockEntity orderLockEntity = new OrderLockEntity();
				orderLockEntity.setDeptCode(syncOrderLockInfo.getDeptCode());
				orderLockEntity.setOrderCountOverdue((long) syncOrderLockInfo.getLockCount());
				orderLockEntity.setOrderCountUnoverdue((long) syncOrderLockInfo.getPromptCount());
				records.add(orderLockEntity);
			}
			// 更新锁屏信息
			List<OrderLockResult> orderLockResults = waybillManagerService.crmsyncorderLock(records);
			for (OrderLockResult orderLockResult : orderLockResults) {
				ResultDetal resultDetal = new ResultDetal();
				resultDetal.setDeptCode(orderLockResult.getDeptCode());
				resultDetal.setReason(orderLockResult.getFailCause());
				resultDetal.setResultCode(orderLockResult.getResultState());
				syncOrderLockInfoResponse.getResultInfo().add(resultDetal);
			}
			// 是否需要异常处理???
			return syncOrderLockInfoResponse;
		}catch(Exception e){
			response.setHeader(ESB_RESULT_CODE, String.valueOf(1));
			LOGGER.info("OMS请求锁屏信息异常",e);
		}
		return syncOrderLockInfoResponse;
	}
	/**
	 * 运单绑定
	 * @param request
	 * @param response
	 * @return
	 * @throws CommonException
	 */
	@RequestMapping(value = "/bindOrder", method = RequestMethod.POST)
	@ResponseBody
	public BindOrderResponse bindOrder(@RequestBody BindOrderRequest request,
			HttpServletResponse response) throws CommonException {
		//创建返回实体
		BindOrderResponse biderOrderResponse = new BindOrderResponse();
		try{
			response.setHeader(ESB_RESULT_CODE, String.valueOf(1));
			//默认设置绑定成功
			biderOrderResponse.setResult(true);
			// 空则返回
			if (request == null) {
				//传入参数为空，绑定失败
				biderOrderResponse.setResult(false);
			} else {
				// 绑定解绑service调用
				ResultDto res = waybillManagerService.reBindOrder(
						request.getOldWayBillNumber(),
						request.getNewWayBillNumber(), request.getOrderNo());
				// 判断是否为0
				// 为0则失败
				if ("0".equals(res.getCode())) {
					biderOrderResponse.setResult(false);
				}
			}
			return biderOrderResponse;
		}catch(Exception e){
			response.setHeader(ESB_RESULT_CODE, String.valueOf(1));
			LOGGER.info("运单绑定异常",e);
		}
		return biderOrderResponse;
	}

	/**
	 * @param waybillManagerService
	 *            the waybillManagerService to set
	 */
	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
}
