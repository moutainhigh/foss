package com.deppon.foss.module.settlement.vtsitf.server.ws;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableForVTSDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.vtsitf.server.inter.IBillPayableQueryForVTS;

/**
 * 根据运单号查询付款状态
 * 
 * @author 331556 fanjingwei
 * @date 2016-05-23
 */
public class BillPayableQueryForVTS implements IBillPayableQueryForVTS {
	/**
	 * 注入日志
	 */
	private static final Logger logger = LogManager
			.getLogger(BillPayableQueryForVTS.class);

	@Context
	HttpServletResponse response;
	@Context
	HttpServletRequest request;
	/**
	 * 注入查询应付单service
	 */
	private IBillPayableService billPayableService;

	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	/**
	 * VTS根据运单号查询付款状态
	 */
	@SuppressWarnings("finally")
	@Override
	public String billPayableQueryForVTS(String wayBillNo) {
		logger.info("开始查询应付单信息...");
		BillPayableForVTSDto dto = new BillPayableForVTSDto();
		try {
			// 参数判空
			if (StringUtils.isEmpty(wayBillNo)) {
				logger.error("VTS传入运单号为空");
				throw new SettlementException("VTS传入参数为空");
			}
			// 解析传入json,封装实体类
			JSONObject object = JSONObject.parseObject(wayBillNo);
			dto = object.getObject("requestEntity", BillPayableForVTSDto.class);

			// 运单号判空
			if (StringUtils.isEmpty(dto.getWayBillNo())) {
				throw new SettlementException("运单号为空");
			}
			logger.info("运单号:" + dto.getWayBillNo());
			// 根据运单号查询开始
			logger.info("查询详细开始，运单号为" + dto.getWayBillNo());
			List<BillPayableEntity> list = this.billPayableService
					.selectByWaybillNos(dto.getWayBillNo());
			BillPayableEntity entity = null;
			if (list != null && list.size() > 0) {
				// 存在多条应付单，不能进行调整费用操作
				if (list.size() > 1) {
					throw new SettlementException("存在" + list.size()
							+ "条应付单，不能进行调整费用操作");
				}
				// 获取集合中的第一条数据
				 entity = list.get(0);
				// 已被冻结不能调整
				if (SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__FROZEN
						.equals(entity.getFrozenStatus())) {
					throw new SettlementException(entity.getWaybillNo()
							+ "已被冻结，不能进行调整费用操作");
				}
				//已支付不能发费用调整
				if(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__YES
						.equals(entity.getPayStatus())){
					throw new SettlementException(entity.getWaybillNo()
							+ "已付款，不能进行调整费用操作");
				}
				// 已被核销不能调整
				if (entity.getVerifyAmount() != null
						&& entity.getVerifyAmount().compareTo(BigDecimal.ZERO) > 0) {
					throw new SettlementException("应付单已核销，不能进行调整费用操作");
				}
			}
			// 封装查询信息
			if(entity != null){
				dto.setWayBillNo(entity.getWaybillNo());
				dto.setPayStatus(entity.getPayStatus());
			}
			dto.setIsSuccess(true);
			dto.setMsg("vts调用foss查询成功");
		} catch (BusinessException e) {
			dto.setIsSuccess(false);
			dto.setMsg(e.getErrorCode());
			logger.info("vts调用foss查询异常,运单号：" + dto.getWayBillNo());
		} catch (Exception e) {
			dto.setIsSuccess(false);
			dto.setMsg(e.getMessage());
			logger.info("VTS调用FOSS查询异常，运单号：" + dto.getWayBillNo());
		} finally {
			response.addHeader("ESB-ResultCode", "1");
			logger.info("VTS调用foss根据运单号查询财务单据信息结束...");
			return JSONObject.toJSONString(dto);
		}

	}

}
