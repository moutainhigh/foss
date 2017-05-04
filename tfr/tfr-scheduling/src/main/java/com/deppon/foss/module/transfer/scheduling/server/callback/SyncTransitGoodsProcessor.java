/*package com.deppon.foss.module.transfer.scheduling.server.callback;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.dpap.esb.mqc.core.exception.ESBBusinessException;
import com.deppon.dpap.esb.mqc.core.process.IProcess;
import com.deppon.esb.pojo.domain.foss2oms.SyncTransitGoodsResponse;
import com.deppon.esb.pojo.domain.foss2oms.TransformOrderInfoStatusPushRequest;
import com.deppon.esb.pojo.transformer.json.SyncTransitGoodsStatusRequestTrans;
import com.deppon.foss.module.transfer.scheduling.api.define.OrderVehicleConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IOrderVehicleDao;
import com.deppon.foss.module.transfer.scheduling.server.service.impl.OrderVehicleService;
import com.deppon.foss.util.define.FossConstants;
*//**
 *	OMS同步转货订单状态到foss
 * @author 332153-foss-zm
 * @date 2016年11月22日14:00:33
 *//*
public class SyncTransitGoodsProcessor implements IProcess {

	private static final Logger logger = LoggerFactory.getLogger(OrderVehicleService.class);

	@Autowired
	private IOrderVehicleDao orderVehicleDao;

	@Override
	public Object process(Object req) throws ESBBusinessException {
		logger.info("OMS同步转货订单状态到foss start");
		SyncTransitGoodsResponse response=new SyncTransitGoodsResponse();
		try {
			TransformOrderInfoStatusPushRequest request=(TransformOrderInfoStatusPushRequest)req;
			logger.info("传入参数："+new SyncTransitGoodsStatusRequestTrans().fromMessage(request));
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("orderNo", request.getOrderNumber());
			String status=request.getOrderStatus();
			parameterMap.put("status", status);
			if(OrderVehicleConstants.ORDERVEHICLE_STATUS_CONFIRMTO.equals(status)){
				parameterMap.put("arrivalTime", status);
			}else{
				parameterMap.put("arrivalTime", null);
			}
			orderVehicleDao.updateOrderVehicleApplyStatus(parameterMap);
			response.setIsSuccess(FossConstants.YES);
			logger.info("OMS同步转货订单状态到foss success");
		} catch (Exception e) {
			response.setIsSuccess(FossConstants.NO);
			logger.error("OMS同步转货订单状态到foss失败："+e.getMessage());
		}
		logger.info("OMS同步转货订单状态到foss end");
		return response;
	}
	
	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		logger.info("OMS同步转货订单状态到foss失败");
		return null;
	}
	*//**
	 * 设置orderVehicleDao
	 * 
	 * @param orderVehicleDao
	 *            the orderVehicleDao to set
	 *//*
	public void setOrderVehicleDao(IOrderVehicleDao orderVehicleDao) {
		this.orderVehicleDao = orderVehicleDao;
	}
}
*/