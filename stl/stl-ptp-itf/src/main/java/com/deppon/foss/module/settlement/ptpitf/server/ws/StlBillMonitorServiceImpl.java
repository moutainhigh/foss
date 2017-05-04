package com.deppon.foss.module.settlement.ptpitf.server.ws;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivablePartnerService;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillingQueryRequestDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.StlBillDetailDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.StlBillQueryResultDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillDepositReceivedPayService;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillPayablePtpService;
import com.deppon.foss.module.settlement.ptpitf.server.inter.StlBillMonitorService;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * ptp监控结算应收、应付、预收等单据接口
 * @author gpz
 * @date 2016年8月5日
 */
public class StlBillMonitorServiceImpl implements StlBillMonitorService {

	/**
	 * 日志
	 */
	private static final Logger logger = LogManager.getLogger(StlBillMonitorServiceImpl.class);
	
	@Context
	private HttpServletRequest request;
	
	@Context
	private HttpServletResponse response;
	
	/**
	 * 预收单service
	 */
	private IBillDepositReceivedPayService billDepositReceivedPayService;
	
	/**
	 * 应收单service
	 */
	private IBillReceivablePartnerService billReceivablePartnerService;
	
	/**
	 * 应付单service
	 */
	private IBillPayablePtpService billPayablePtpService;
	
	/**
	 * 获取结算各单据总记录数和总金额数
	 */
	@Override
	public String monitorSettlementBill(String data) {
		//记录日志
        logger.info("---ptp监控查询结算各单据开始---");
        //返回结果dto
        StlBillQueryResultDto resultDto = new StlBillQueryResultDto();
        try {
        	if(StringUtils.isBlank(data)){
    			throw new SettlementException("传入查询参数为空！");
    		}
        	logger.info("---ptp查询结算单据传入参数：" + data);
        	
			// 解析json参数转化成dto
			BillingQueryRequestDto requestDto = JSON.parseObject(data,
					BillingQueryRequestDto.class);
			// 开始时间
			if (requestDto.getStartTime() == null) {
				throw new SettlementException("开始时间为空");
			}
        	
        	//结束时间
        	if(requestDto.getEndTime() == null){
        		throw new SettlementException("结束时间为空");
        	}
        	
        	logger.info("---查询dto：" + ReflectionToStringBuilder.toString(requestDto));
        	
			// 根据时间范围查询预收单总金额和总笔数
			StlBillDetailDto depositReceivedDto = billDepositReceivedPayService
					.countDepositReceivedBills(requestDto);
			logger.info("---预收单数据："
					+ ReflectionToStringBuilder.toString(depositReceivedDto));
			// 预收单总金额
			resultDto.setAdvanceTotalAmount(depositReceivedDto.getFlowTotalAmount());
			// 预收单总记录数
			resultDto.setAdvanceTotalCount(depositReceivedDto.getFlowTotalNum());
        	
			// 单据明细
			List<StlBillDetailDto> billList = new ArrayList<StlBillDetailDto>();
			// 根据时间范围查询应收单总金额和总笔数
			List<StlBillDetailDto> receivableList = billReceivablePartnerService
					.countReceivableBills(requestDto);
			if (CollectionUtils.isNotEmpty(receivableList)) {
				billList.addAll(receivableList);
			}
            
			// 根据时间范围查询应付单总金额和总笔数
			List<StlBillDetailDto> payableList = billPayablePtpService
					.countPayableBills(requestDto);
			if (CollectionUtils.isNotEmpty(payableList)) {
				billList.addAll(payableList);
			}
            
        	logger.info("---应收应付单据明细：" + ReflectionToStringBuilder.toString(billList));
        	
        	//应收和应付对应子单据list
        	resultDto.setReceivableBillDetail(billList);
        	
        	//成功，没有发生错误
        	resultDto.setIsError(FossConstants.NO);
        	resultDto.setErrorMsg("执行成功");
        	
        } catch (SettlementException se){
        	//异常，产生错误消息
        	resultDto.setIsError(FossConstants.YES);
        	resultDto.setErrorMsg(se.getErrorCode());
        	//记录日志
        	logger.error(se.getErrorCode(), se);
		} catch (Exception e) {
			//异常，产生错误消息
        	resultDto.setIsError(FossConstants.YES);
        	resultDto.setErrorMsg(e.getMessage());
        	//记录日志
			logger.error(e.getMessage(), e);
		}finally{
			response.addHeader("ESB-ResultCode", "1");
			//响应时间
        	resultDto.setResTime(new Date());
		}
        logger.info("---ptp监控查询结算各单据结束---");
        return JSON.toJSONString(resultDto);
	}

	
	
	/**
	 * @return the billDepositReceivedPayService
	 */
	public IBillDepositReceivedPayService getBillDepositReceivedPayService() {
		return billDepositReceivedPayService;
	}

	/**
	 * @param billDepositReceivedPayService the billDepositReceivedPayService to set
	 */
	public void setBillDepositReceivedPayService(
			IBillDepositReceivedPayService billDepositReceivedPayService) {
		this.billDepositReceivedPayService = billDepositReceivedPayService;
	}

	/**
	 * @return the billReceivablePartnerService
	 */
	public IBillReceivablePartnerService getBillReceivablePartnerService() {
		return billReceivablePartnerService;
	}

	/**
	 * @param billReceivablePartnerService the billReceivablePartnerService to set
	 */
	public void setBillReceivablePartnerService(
			IBillReceivablePartnerService billReceivablePartnerService) {
		this.billReceivablePartnerService = billReceivablePartnerService;
	}

	/**
	 * @return the billPayablePtpService
	 */
	public IBillPayablePtpService getBillPayablePtpService() {
		return billPayablePtpService;
	}

	/**
	 * @param billPayablePtpService the billPayablePtpService to set
	 */
	public void setBillPayablePtpService(
			IBillPayablePtpService billPayablePtpService) {
		this.billPayablePtpService = billPayablePtpService;
	}

}
