/**
 * Copyright 2016 STL TEAM
 */
/*******************************************************************************
 * Copyright 2016 STL TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: stl-consumer-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/ptpitf/server/esb/BillReceivableNewPtpProcess.java
 * 
 * FILE NAME        	: BillReceivableNewPtpProcess.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2016  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.ptpitf.server.esb;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.receivable.add.PtpAddBillReceivableRequest;
import com.deppon.esb.inteface.domain.receivable.add.PtpAddBillReceivableResponse;
import com.deppon.esb.inteface.domain.receivable.add.ReceivableBills;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.settlement.common.api.server.service.IEsbInterfaceLogService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillReceivablePtpService;

public class BillReceivableNewPtpProcess implements IProcess {
	/**
	 * 日志
	 */
	private static final Logger logger = LogManager
			.getLogger(BillReceivableNewPtpProcess.class);
	
	/**
	 * 生成应收单service
	 */
	private IBillReceivablePtpService billReceivablePtpService;
	
	/**
	 * 结算通用服务service
	 */
	private ISettlementCommonService settlementCommonService;
	
	/**
     * 错误消息日志记录service
     */
    private IEsbInterfaceLogService esbInterfaceLogService;
	

	public ISettlementCommonService getSettlementCommonService() {
		return settlementCommonService;
	}

	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	public IBillReceivablePtpService getbillReceivablePtpService() {
		return billReceivablePtpService;
	}

	public void setbillReceivablePtpService(
			IBillReceivablePtpService billReceivablePtpService) {
		this.billReceivablePtpService = billReceivablePtpService;
	}

	/**
	 * 对接合伙人生成应收单service接口实现
	 * @author foss-Jiang Xun
	 * @date 2016-01-14 下午8:16:00
	 */
	@SuppressWarnings("finally")
	@Override
	public Object process(Object obj) throws ESBBusinessException {
		//记录日志
		logger.info("FOSS接口生成应收单开始....");
		//获取返回信息
		PtpAddBillReceivableRequest request = (PtpAddBillReceivableRequest) obj;
		// 返回Foss生成应收单响应
		PtpAddBillReceivableResponse response = new PtpAddBillReceivableResponse();
		//验证
		if(request == null||CollectionUtils.isEmpty( request.getReceivableBills())|| request.getReceivableBills().size()<1){
			logger.error("生成合伙人应收单错误，请求参数为空，调用接口失败");
			response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_FAILED);//处理失败标志
			response.setReason("请求参数为空，调用接口失败");//失败原因
		}

		// 获取传递信息
		String waybillNo = request.getReceivableBills().get(0).getWaybillNo();//运单号
		String billType = request.getReceivableBills().get(0).getBillType();//单据子类型
		response.setWaybillNo(waybillNo);//响应运单号
		response.setBillType(billType);//单据子类型
		response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_SUCCESS);//处理成功标志
		
		 //存放流水id集合
        List<String> idList = new ArrayList<String>();
        for(ReceivableBills recBill : request.getReceivableBills()){
        	idList.add(recBill.getId());
        }
        //所有流水id（使用逗号分隔）
		response.setId(StringUtils.collectionToCommaDelimitedString(idList));
		try{
			//foss内部service处理
			billReceivablePtpService.generatReceivableBillController(billReceivablePtpService.buildDto(request));
			//异常处理
		}catch (SettlementException se) {
			logger.error("\n运单号:"+waybillNo+"生成合伙人应收单结算错误，" + se.getErrorCode(), se);
			response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_FAILED);//处理失败标志
			response.setReason("运单号:"+waybillNo+"。生成合伙人应收单结算错误，"+se.getErrorCode());//失败原因
			
			//记录错误日志到数据库
			esbInterfaceLogService.savePtpInterfaceLog(obj, se.getErrorCode()
					+ "\n" + ExceptionUtils.getFullStackTrace(se));
		}catch (BusinessException ex) {
			logger.error("\n运单号:"+waybillNo+"生成合伙人应收单业务错误，" + ex.getErrorCode(), ex);
			response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_FAILED);//处理失败标志
			response.setReason("运单号:"+waybillNo+"。生成合伙人应收单结算错误，"+ex.getErrorCode());//失败原因
			
			// 记录错误日志到数据库
			esbInterfaceLogService.savePtpInterfaceLog(obj, ex.getErrorCode()
					+ "\n" + ExceptionUtils.getFullStackTrace(ex));
		} catch (Exception e) {
			logger.error("\n运单号:"+waybillNo+"生成合伙人应收单发生未知错误，" + e.getMessage(), e);
			response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_FAILED);//处理失败标志
			response.setReason("运单号:"+waybillNo+"。生成合伙人应收单结算错误，"+e.getMessage());//失败原因
			
			// 记录错误日志到数据库
			esbInterfaceLogService.savePtpInterfaceLog(obj, e.getMessage()
					+ "\n" + ExceptionUtils.getFullStackTrace(e));
		}finally{
			//记录日志
			logger.info("FOSS接口生成应收单结束....响应结果。成功标志是："+response.getResult()+"，失败原因是："+response.getReason());
			//记录日志
			logger.info("FOSS接口生成应收单结束....");
			return response;
		}
	}
	
	
	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the esbInterfaceLogService
	 */
	public IEsbInterfaceLogService getEsbInterfaceLogService() {
		return esbInterfaceLogService;
	}

	/**
	 * @param esbInterfaceLogService the esbInterfaceLogService to set
	 */
	public void setEsbInterfaceLogService(
			IEsbInterfaceLogService esbInterfaceLogService) {
		this.esbInterfaceLogService = esbInterfaceLogService;
	}

}
