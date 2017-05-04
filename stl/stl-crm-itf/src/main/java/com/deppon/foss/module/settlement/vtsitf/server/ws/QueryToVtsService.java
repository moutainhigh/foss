package com.deppon.foss.module.settlement.vtsitf.server.ws;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RepaymentDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.VtsQueryDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.vtsitf.server.inter.IQueryToVtsService;

/** 
 * VTS-foss根据运单号查询财务单据信息
 * @author foss结算-306579-guoxinru 
 * @date 2016-5-10  
 */
public class QueryToVtsService implements IQueryToVtsService{

	/**
	 * 日志
	 */
	private static final Logger logger = LogManager.getLogger(QueryToVtsService.class);

	@Context
	HttpServletResponse response;
	@Context
	HttpServletRequest request;

	/**
	 * 注入根据运单号查询财务单据信息service
	 */
	
	private IRepaymentService repaymentService;
	
	/**
	 * VTS根据运单号查询财务单据信息
	 */
	@SuppressWarnings("finally")
	@Override
	public String queryPaymentByWaybillNo(String waybillNo) {

		logger.info("VTS调用foss根据运单号查询财务单据信息开始...");
		
		VtsQueryDto dto = new VtsQueryDto();
		
		try {
			//参数判空
			if(StringUtils.isEmpty(waybillNo)){
				logger.error("vts传入运单号为空！");
				throw new SettlementException("vts传入参数为空！");
			}
			
			//解析传入json，封装实体类
			JSONObject object = JSONObject.parseObject(waybillNo);
			dto = object.getObject("requestEntity", VtsQueryDto.class);
			
			//运单号判空
			if(StringUtils.isEmpty(dto.getWaybillNo())){
				throw new SettlementException("vts传入运单号为空！");
			}
			logger.info("vts传入运单号："+dto.getWaybillNo());
			
			//根据运单号查询财务单据详细信息
			logger.info("根据运单号查询财务单据详细信息开始，运单号："+dto.getWaybillNo());
			RepaymentDto repaymentDto = this.repaymentService.vtsQueryPaymentByWaybillNo(dto.getWaybillNo());
			logger.info("根据运单号查询财务单据详细信息结束，运单号："+dto.getWaybillNo());
			//保管费取整数
		    if(repaymentDto.getWaybillDto().getStorageCharge()!=null){
			 repaymentDto.getWaybillDto().setStorageCharge(repaymentDto.getWaybillDto().getStorageCharge().setScale(0,BigDecimal.ROUND_HALF_UP));	
		 	}
		    //查询成功，封装返回vts的Dto
		    dto.setRepaymentDto(repaymentDto);
		    dto.setIsSuccess(true);
		    dto.setMsg("vts调用foss查询成功！");
		}catch (BusinessException e) {
			dto.setIsSuccess(false);
			dto.setMsg(e.getErrorCode());
			logger.info("vts调用foss查询异常,运单号："+dto.getWaybillNo());
		} catch (Exception e1) {
			dto.setIsSuccess(false);
			dto.setMsg(e1.getMessage());
			logger.info("vts调用foss查询异常,运单号："+dto.getWaybillNo());
		} finally {
			response.addHeader("ESB-ResultCode", "1");
			logger.info("VTS调用foss根据运单号查询财务单据信息结束...");
			return JSONObject.toJSONString(dto);
		}
	}

	public void setRepaymentService(IRepaymentService repaymentService) {
		this.repaymentService = repaymentService;
	}

}
