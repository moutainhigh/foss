package com.deppon.foss.module.pickup.sign.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.inteface.domain.foss2ptp.PartnerUpdateTakeEffectTimeListRequest;
import com.deppon.esb.inteface.domain.foss2ptp.PartnerUpdateTakeEffectTimeRequest;
import com.deppon.foss.module.pickup.sign.api.server.service.IPtpSignPartnerService;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;

/**
 * 合伙人-签收时-更新派送流水状态-异步接口JSON-367638
 * 分批给ptp推送签收时间和运单号
 */
public class PtpSignPartnerService implements IPtpSignPartnerService{
	/**
	 * 记录日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PtpSignPartnerService.class);

	/* 
	 * 向ptp推送签收时间
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IPtpSignPartnerService#validBillSaleFlowByAsynESB(java.util.List)
	 */
	public boolean validBillSaleFlowByAsynESB(List<PartnerUpdateTakeEffectTimeRequest> result) {
		if (CollectionUtils.isNotEmpty(result)) {
			
			List<List<PartnerUpdateTakeEffectTimeRequest>> resultList = com.deppon.foss.util.CollectionUtils
					.splitListBySize(result, SignConstants.ORACLE_MAX_SIZE);
			
			AccessHeader header = new AccessHeader();
			// 版本号
			header.setVersion(SettlementESBDictionaryConstants.ESB_HEADER__VERSION);
			header.setBusinessId(UUIDUtils.getUUID());
			// 服务编码 数据库 bse.t_jms_service_config
			header.setEsbServiceCode(SignConstants.PTP_MODIFY_SALEFLOW__STATUS_ASYN_CODE);
			header.setBusinessDesc1("当天同步"+DateUtils.getChDate(new Date())+"的签收时间给ptp");
			
			for (List<PartnerUpdateTakeEffectTimeRequest> addlist : resultList) {
				PartnerUpdateTakeEffectTimeListRequest list = new PartnerUpdateTakeEffectTimeListRequest();
				list.getPartnerUpdateTakeEffectTimeListRequestList().addAll(
						addlist);
				// 发送请求
				try {
					LOGGER.info("--同步签收时间接口传递参数开始·········");
					ESBJMSAccessor.asynReqeust(header, list);
					LOGGER.info("合伙人签收--生效应付流水-ESB异步完成发送");
				} catch (ESBException e) {
					LOGGER.error("合伙人签收--生效应付流水-ESB异步请求错误!" + e.getExceptionCode(), e);
					return false;
				}
			}

		}
		return true;
	}
}
