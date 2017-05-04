package com.deppon.foss.module.settlement.qms.server.rs.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.jws.WebService;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.settlement.common.api.shared.dto.AbnormalBillAmountCalculatedDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.IOtherRevenueService;
import com.deppon.foss.module.settlement.qms.server.rs.IAbnormalBillAmountCalculated;

/**
 * 异常账单金额计算
 *
 * @author 347069-foss-zhangzhiqiang
 * @date 2016-8-18 上午10:19:22
 */
@WebService
public class AbnormalBillAmountCalculatedImpl implements
		IAbnormalBillAmountCalculated {
	/**
	 * 日志
	 */
	private final Logger LOGGER = LogManager.getLogger(getClass());

	private IOtherRevenueService otherRevenueService;

	@Context
	HttpServletResponse res;

	@Override
	public String queryReceiptsSumByBill(String jsonStr) {
		res.setHeader("ESB-ResultCode", "1");
		AbnormalBillAmountCalculatedDto dto = null;
		if (StringUtils.isEmpty(jsonStr)) {
			LOGGER.debug("AbnormalBillAmountCalculated:传入的小票号为空");
			dto = new AbnormalBillAmountCalculatedDto("传入的小票号为空");
			return JSONObject.toJSONString(dto);
		}
		try {
			dto = JSONObject.parseObject(jsonStr,
					AbnormalBillAmountCalculatedDto.class);
			dto = otherRevenueService.queryOtherRevenueByRevenueNo(dto);
			if (dto != null && dto.getClaimAmount() != null) {
				//数据库获取的单位为分,除以100页面显示单位为元
				BigDecimal a = new BigDecimal("100");
				dto.setClaimAmount(dto.getClaimAmount().divide(a,2,RoundingMode.UP));            
				dto.setFailReason("查询成功!");
				dto.setIsSuccess("1");
			} else {
				dto = JSONObject.parseObject(jsonStr,
						AbnormalBillAmountCalculatedDto.class);
				dto.setFailReason("没有查到信息");
				dto.setIsSuccess("0");
			}
			LOGGER.debug("编号为:" + jsonStr + " 查询结果-" + dto.toString());
			return JSONObject.toJSONString(dto);
		} catch (SettlementException e) {
			LOGGER.debug("编号为:" + jsonStr + " 查询结果异常-" + e.getMessage());
			return JSONObject.toJSONString(new AbnormalBillAmountCalculatedDto(
					e.getMessage()));
		}

	}

	public void setOtherRevenueService(IOtherRevenueService otherRevenueService) {
		this.otherRevenueService = otherRevenueService;
	}
}