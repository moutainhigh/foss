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
 * PROJECT NAME	: stl-ptp-itf
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/ptpitf/server/esb/BillPayableNewPtpProcess.java
 * 
 * FILE NAME        	: BillPayableNewPtpProcess.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2016  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.ptpitf.server.esb;

import com.deppon.esb.inteface.domain.payable.add.PayableBills;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillNewPayablePtpDto;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.payable.add.PtpAddBillPayableRequest;
import com.deppon.esb.inteface.domain.payable.add.PtpAddBillPayableResponse;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.settlement.common.api.server.service.IEsbInterfaceLogService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillPayablePtpService;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 对接合伙人生成应付单接口
 * @author 蒋迅
 * @date 2016-01-23 下午05:41:00
 * @since
 * @version
 */
public class BillPayableNewPtpProcess implements IProcess {
	/**
	 * 日志
	 */
	private static final Logger logger = LogManager
			.getLogger(BillPayableNewPtpProcess.class);
	
	/**
	 * 应付单service
	 */
	private IBillPayablePtpService billPayablePtpService;
	
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

	public IBillPayablePtpService getBillPayablePtpService() {
		return billPayablePtpService;
	}

	public void setBillPayablePtpService(
			IBillPayablePtpService billPayablePtpService) {
		this.billPayablePtpService = billPayablePtpService;
	}

	/**
	 * 对接合伙人生成应付单service接口实现
	 * @author foss-Jiang Xun
	 * @date 2016-01-25 上午10:25:00
	 */
	@SuppressWarnings("finally")
	@Override
	public Object process(Object obj) throws ESBBusinessException {
		//记录日志
		logger.info("FOSS接口生成应付单开始....");
		//获取返回信息
		PtpAddBillPayableRequest request = (PtpAddBillPayableRequest) obj;
		// 返回Foss生成应付单响应
		PtpAddBillPayableResponse response = new PtpAddBillPayableResponse();
		//验证
		if(request == null||CollectionUtils.isEmpty( request.getPayableBills())|| request.getPayableBills().size()<1){
			logger.error("生成合伙人应付单错误，请求参数为空，调用接口失败");
			response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_FAILED);//处理失败标志
			response.setReason("请求参数为空，调用接口失败");//失败原因
		}

		// 获取传递信息
		String waybillNo = request.getPayableBills().get(0).getWaybillNo();//运单号
		String billTypeRequest = request.getPayableBills().get(0).getBillType();//单据子类型
		response.setWaybillNo(waybillNo);//响应运单号
		response.setBillType(billTypeRequest);//单据子类型
		response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_SUCCESS);//处理成功标志
		
		 //存放流水id集合
        List<String> idList = new ArrayList<String>();
        for(PayableBills payBill : request.getPayableBills()){
        	idList.add(payBill.getId());
        }
        //所有流水id（使用逗号分隔）
		response.setId(StringUtils.collectionToCommaDelimitedString(idList));//流水id
		try{
			//foss内部service处理
            BillNewPayablePtpDto billNewPayablePtpDto =billPayablePtpService.buildDto(request,null);
			billPayablePtpService.generatPtpPayableBill(billNewPayablePtpDto);
            //处理代收货款应付
            List<PayableBills> payableBills = request.getPayableBills();
            for(int i=0;i<payableBills.size();i++){
                //ptp应付信息
                PayableBills payableBill = payableBills.get(i);
                //应付单子类型
                String billType = payableBill.getBillType();
                if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD.equals(billType)) {//合伙人代收货款应付
                    //生成新的代收货款应付单
                    billPayablePtpService.handleAccountPayableCod(payableBill);
                }
            }
			//异常处理
		}catch (SettlementException se) {
			logger.error("\n运单号:"+waybillNo+"生成合伙人应付单结算错误，" + se.getErrorCode(), se);
			response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_FAILED);//处理失败标志
			response.setReason("运单号:"+waybillNo+"。生成合伙人应付单结算错误，"+se.getErrorCode());//失败原因
			
			// 记录错误日志到数据库
			esbInterfaceLogService.savePtpInterfaceLog(obj, se.getErrorCode()
					+ "\n" + ExceptionUtils.getFullStackTrace(se));
		}catch (BusinessException ex) {
			logger.error("\n运单号:"+waybillNo+"生成合伙人应付单业务错误，" + ex.getErrorCode(), ex);
			response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_FAILED);//处理失败标志
			response.setReason("运单号:"+waybillNo+"。生成合伙人应付单结算错误，"+ex.getErrorCode());//失败原因
			
			// 记录错误日志到数据库
			esbInterfaceLogService.savePtpInterfaceLog(obj, ex.getErrorCode()
					+ "\n" + ExceptionUtils.getFullStackTrace(ex));
		} catch (Exception e) {
			logger.error("\n运单号:"+waybillNo+"生成合伙人应付单发生未知错误，" + e.getMessage(), e);
			response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_FAILED);//处理失败标志
			response.setReason("运单号:"+waybillNo+"。生成合伙人应付单结算错误，"+e.getMessage());//失败原因
			
			// 记录错误日志到数据库
			esbInterfaceLogService.savePtpInterfaceLog(obj, e.getMessage()
					+ "\n" + ExceptionUtils.getFullStackTrace(e));
		}finally{
			//记录日志
			logger.info("FOSS接口生成应付单结束....响应结果。成功标志是："+response.getResult()+"，失败原因是："+response.getReason());		
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
