package com.deppon.foss.module.settlement.consumer.server.service.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.foss.inteface.domain.courier.CourierInfo;
import com.deppon.foss.inteface.domain.courier.SendCourierRequest;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.service.IElectronicInvoiceSendService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.ElectronicInvoiceDto;
import com.deppon.foss.service.platformservice.CommonException;

/**
 * 
 * 将电子发票数据发送给发票系统
 * 
 * @date 2014-10-29 上午10:38:33
 */
public class ElectronicInvoiceSendService implements IElectronicInvoiceSendService{

	/**
	 * 日志
	 */
	private static final Logger LOGGER = LogManager
			.getLogger(BillPayCODOnlineSendService.class);
	
	/**
	 * 
	 * 将电子发票数据发送给发票系统
	 * 
	 * @date 2014-10-29 上午10:38:33
	 */
	@Override
	public void electronicInvoiceSend(ElectronicInvoiceDto dto) throws Exception {
		LOGGER.info("开始电子发票数据发送给发票系统...：" + dto.getWayBillNo());
		try {
			// 构造发送对象和表头
			SendCourierRequest request = this.buildRequest(dto);
			AccessHeader header = this.buildHeader(dto.getWayBillNo());

			ESBJMSAccessor.asynReqeust(header, request);
		} catch (Exception e) {
			LOGGER.error("构造发送对象和表头异常：" + e.getMessage(), e);
			throw new CommonException("构造发送对象和表头异常：" + e.getMessage(), e);
		}
		LOGGER.info("电子发票数据发送给发票系统结束.");
	}
	
	/**
	 * 
	 * 构建Request对象
	 * 
	 */
	private SendCourierRequest buildRequest(ElectronicInvoiceDto dto) throws CommonException {

		SettlementUtil.valideIsNull(dto, "ElectronicInvoiceDto对象为空");
		/*SettlementUtil.valideIsNull(dto.getWayBillNo(), "运单号不能为空");
		SettlementUtil.valideIsNull(dto.getBillingTime(), "开单时间不能为空");
		SettlementUtil.valideIsNull(dto.getCompanyCode(), "开票子公司编码不能为空");
		SettlementUtil.valideIsNull(dto.getCompanyStandCode(), "开票部门标杆编码不能为空");
		SettlementUtil.valideIsNull(dto.getBuyerName(), "购货方名称不能为空");
		SettlementUtil.valideIsNull(dto.getBuyerPhone(), "购货方电话不能为空");
		
		SettlementUtil.valideIsNull(dto.getBusinessType(), "业务类型不能为空");*/
		SettlementUtil.valideIsNull(dto.getAmountTotal(), "开票金额合计不能为空");
		
		CourierInfo info = new CourierInfo();
		BeanUtils.copyProperties(dto, info,new String[]{"amountTotal"});
		info.setAmountTotal(dto.getAmountTotal().doubleValue());
		
		info.setBuyerType("03");//默认认为是“03：个人”
		info.setRepeatFlag("1");//默认1

		SendCourierRequest request = new SendCourierRequest();
		request.getCourierInfo().add(info);
		return request;
	}

	/**
	 * 
	 * 返回ESB请求头消息
	 * 
	 * @date 2012-11-19 下午6:41:17
	 */
	private AccessHeader buildHeader(String wayBillNo) {
		AccessHeader header = new AccessHeader();
		header.setBusinessId(wayBillNo);
		header.setEsbServiceCode(SettlementESBDictionaryConstants.ESB_FOSS2ESB_SYNC_ORDER2FIMS_LTLANDEXPRESS);
		header.setBusinessDesc1(SettlementESBDictionaryConstants.ESB_FOSS2ESB_SYNC_ORDER2FIMS_LTLANDEXPRESS_DESC);
		header.setVersion(SettlementESBDictionaryConstants.ESB_HEADER__VERSION);

		return header;
	}
}
