package com.deppon.foss.module.settlement.positf.server.ws;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.pickup.sign.api.shared.define.RepaymentConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.positf.server.inter.IPosSettleForCUBC;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * @author 378375
 * @date 2017年1月20日 17:28:06 把CUBC的pos机刷卡记录保存到foss的付款记录表里面
 */
public class PosSettleForCUBC implements IPosSettleForCUBC {
	/**
	 * 日志
	 */
	private static final Logger logger = LogManager
			.getLogger(PosSettleForCUBC.class);

	/**
	 * 注入结清货款记录表
	 */
	private IRepaymentService repaymentService;
	
	/** 实际货物服务. */
	private IActualFreightService actualFreightService;

	@Context
	protected HttpServletRequest req;
	@Context
	protected HttpServletResponse resp;

	/**
	 * 将CUBC的pos机刷卡记录保存到foss的付款记录表里面
	 */
	@Override
	public Response insertPosSettleRecord(String param) {
		logger.info("CUBC传入的pos机结清参数：param :" + param);
		RepaymentEntity request = JSONObject.parseObject(param,
				RepaymentEntity.class);
		// 响应
		RepaymentEntity response = new RepaymentEntity();
		try {
			if (StringUtils.isEmpty(request.getWaybillNo())) {
				throw new SettlementException("传入的付款记录参数为空！");
			}
			//到付非空转换
			BigDecimal actualFreight = request.getActualFreight() == null ?  BigDecimal.ZERO : request.getActualFreight();
			//代付货款非空转换
			BigDecimal codAmount = request.getCodAmount() == null ? BigDecimal.ZERO : request.getCodAmount();
			
			request.setActualFreight(actualFreight);
			request.setCodAmount(codAmount);
			request.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_GENERATED);
			
			repaymentService.addPaymentInfo(request);
			//判断是否全部结清，是则更新实际承运信息表
			if(StringUtils.equals(request.getIsSettle(), FossConstants.YES)){
				// 更新ActualFreight表中的结清状态为已结清
				ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
				//运单号
				actualFreightEntity.setWaybillNo(request.getWaybillNo());
				//结清状态-已结清
				actualFreightEntity.setSettleStatus(FossConstants.YES);
				//结款日期
				actualFreightEntity.setSettleTime(new Date());
				//收货人
				actualFreightEntity.setDeliverymanName(request.getDeliverymanName());
				//更新actualFreight表
				actualFreightService.updateActualFreightSettleStatusByNo(actualFreightEntity);
			}
			response.setSuccess(Boolean.TRUE);
			response.setErrorMessage("成功");
		} catch (SettlementException e) {
			logger.error("SettlementException层：" + e.getErrorCode());
			response.setSuccess(Boolean.FALSE);
			response.setErrorMessage("保存失败！" + e.getErrorCode());
		} catch (Exception se) {
			logger.error("SettlementException层：" + se.getMessage());
			response.setSuccess(Boolean.FALSE);
			response.setErrorMessage("保存失败！" + se);
		}
		return Response.ok(JSONObject.toJSONString(response))
				.header("ESB-ResultCode", 1).build();
	}

	public HttpServletRequest getReq() {
		return req;
	}

	public void setReq(HttpServletRequest req) {
		this.req = req;
	}

	public HttpServletResponse getResp() {
		return resp;
	}

	public void setResp(HttpServletResponse resp) {
		this.resp = resp;
	}

	public void setActualFreightService(IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}

	public void setRepaymentService(IRepaymentService repaymentService) {
		this.repaymentService = repaymentService;
	}
}
