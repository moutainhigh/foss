package com.deppon.foss.module.settlement.dubbo.api.service.impl.expose;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.dubbo.ecs.api.define.EcsResponseDto;
import com.deppon.foss.dubbo.ecs.api.service.EcsDeliverForSettlementDubboService;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.settlement.dubbo.api.service.ITakingService4dubbo;
import com.deppon.foss.module.settlement.dubbo.api.util.SettlementConstants;

public class EcsDeliverForSettlementServiceImpl4dubbo implements
		EcsDeliverForSettlementDubboService {

	// 日志
	private final Logger logger = LogManager.getLogger(EcsDeliverForSettlementServiceImpl4dubbo.class);
	// 结算 收入确认、反确认 服务接口
	@Resource
	private ITakingService4dubbo takingService4dubbo;

	/**
	 * @author 327090
	 * 获取网上支付未完成的运单
	 * @date 2016-12-7
	 */
	@Override
	public EcsResponseDto queryUnpaidOnlinePay(List<String> waybillNOs) {

		// 获取的网上未支付完成的运单
		EcsResponseDto resDto = new EcsResponseDto();
		// 返回的集合，推送给快递
		List<String> list = new ArrayList<String>();

		if (null == waybillNOs) {
			// 记录日志
			logger.info("获取网上支付未完成的运单失败：请求参数为空！");
			resDto.setResult(SettlementConstants.RETURN_FAILURE);
			resDto.setMessage("获取网上支付未完成的运单失败：请求参数为空！");
			resDto.setWaybillNos(list);
			return resDto;
		}
		try {
			logger.info("接收单号"+waybillNOs.get(0));
			// 开单网上支付，但是尚未支付的单据
			List<String> notPayWaybillNo = takingService4dubbo.unpaidOnlinePay(waybillNOs);
			// 记录日志
			logger.info("获取网上支付未完成的运单成功！");
			resDto.setResult(SettlementConstants.RETURN_SUCESS);
			resDto.setMessage("获取网上支付未完成的运单成功！");
			resDto.setWaybillNos(notPayWaybillNo);
			logger.info("开单网上支付，但是尚未支付的运单号："+notPayWaybillNo);
			logger.info(resDto);
			return resDto;
		} catch (BusinessException e) {
			resDto.setResult(SettlementConstants.RETURN_FAILURE);
			resDto.setMessage("结算获取网上支付未完成的运单失败："+ (e.getErrorCode() != null ? e.getErrorCode() : e.getMessage()));
			resDto.setWaybillNos(waybillNOs);
			return resDto;
		} catch (Exception e) {
			resDto.setResult(SettlementConstants.RETURN_FAILURE);
			resDto.setMessage("结算获取网上支付未完成的运单失败：系统异常，请重新操作！" + e);
			resDto.setWaybillNos(waybillNOs);
			return resDto;
		}
	}

}
