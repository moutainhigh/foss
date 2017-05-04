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

import java.util.ArrayList;
import java.util.List;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.payable.update.*;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.settlement.common.api.server.service.IEsbInterfaceLogService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillPayablePtpService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillNewPayablePtpDto;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

/**
 *
 * 对接合伙人接收运单更改与异常签收通知请求信息service接口实现
 * @author foss-hemingyu
 * @date 2016-01-26 17:16:20
 * @since
 * @version
 */
public class BillPayableFinaChangeProcessor implements IProcess {
	/**
	 * 日志
	 */
	private static final Logger logger = LogManager
			.getLogger(BillPayableFinaChangeProcessor.class);
	
	/**
	 * 应付单service
	 */
	private IBillPayablePtpService billPayablePtpService;

    public IBillPayablePtpService getBillPayablePtpService() {
        return billPayablePtpService;
    }

    public void setBillPayablePtpService(IBillPayablePtpService billPayablePtpService) {
        this.billPayablePtpService = billPayablePtpService;
    }
    
    /**
     * 错误消息日志记录service
     */
    private IEsbInterfaceLogService esbInterfaceLogService;

	/**
	 * 对接合伙人接收运单更改与异常签收通知请求信息service接口实现
	 * @author foss-hemingyu
	 * @date 2016-01-26 17:16:20
	 */
	@SuppressWarnings("finally")
	@Override
	public Object process(Object obj) throws ESBBusinessException {
		//记录日志
		logger.info("FOSS接口接收运单更改与异常签收通知请求信息开始....");
		//获取返回信息
        BillPayableChangedRequest request = (BillPayableChangedRequest) obj;
		// 返回Foss接收运单更改与异常签收通知请求信息
        BillPayableChangedResponse response = new BillPayableChangedResponse();
		//验证
        if(request == null || CollectionUtils.isEmpty(request.getChangePayableBills())){
			logger.error("接收运单更改与异常签收通知请求信息，请求参数为空，调用接口失败");
            response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_FAILED);//处理失败标志
            response.setReason("请求参数为空，调用接口失败");//失败原因
            logger.info("FOSS接口接收运单更改与异常签收通知请求信息结束....响应结果。");
            return response;
		}
        // 获取传递信息
        String waybillNo = request.getChangePayableBills().get(0).getWaybillNo();//运单号
        String billType =  request.getChangePayableBills().get(0).getBillType();//运单类型
        response.setWaybillNo(waybillNo);//响应运单号
        response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_SUCCESS);//处理成功标志
        
        //存放流水id集合
        List<String> idList = new ArrayList<String>();
        for(ChangePayableBills paybill : request.getChangePayableBills()){
        	idList.add(paybill.getId());
        }
        //所有流水id（使用逗号分隔）
        response.setId(StringUtils.collectionToCommaDelimitedString(idList));
        try {
            //得到合伙人传入的信息有来源单号和来源单号类型
            BillNewPayablePtpDto billNewPayablePtpDtoChange =  billPayablePtpService.buildDto(null, request);
            //根据合伙人传入的信息是变更规则
            billPayablePtpService.writeBackAndAddPayable(request,billNewPayablePtpDtoChange);
            response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_SUCCESS);
            response.setWaybillNo(waybillNo);
            response.setReason("运单号:"+waybillNo+"。执行成功");
        }catch (SettlementException se) {
            logger.error("接收运单更改与异常签收通知请求信息SettlementException错误，" + se.getErrorCode(), se);
            response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_FAILED);//处理失败标志
            response.setReason("运单号:"+waybillNo+"运单类型"+billType+"。接收请求信息错误，"+se.getErrorCode());//失败原因
            
			// 记录错误日志到数据库
			esbInterfaceLogService.savePtpInterfaceLog(obj, se.getErrorCode()
					+ "\n" + ExceptionUtils.getFullStackTrace(se));
        }catch (BusinessException ex) {
            logger.error("接收运单更改与异常签收通知请求信息BusinessException错误，" + ex.getErrorCode(), ex);
            response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_FAILED);//处理失败标志
            response.setReason("运单号:"+waybillNo+"运单类型"+billType+"。接收请求信息错误，"+ex.getErrorCode());//失败原因
            
			// 记录错误日志到数据库
			esbInterfaceLogService.savePtpInterfaceLog(obj, ex.getErrorCode()
					+ "\n" + ExceptionUtils.getFullStackTrace(ex));
        } catch (Exception e) {
            logger.error("接收运单更改与异常签收通知请求信息发生未知错误，" + e.getMessage(), e);
            response.setResult(SettlementESBDictionaryConstants.INTERFACE_RESPONSE_FAILED);//处理失败标志
            response.setReason("请检查下运单号是否错误。接收请求信息错误，"+e.getMessage());//失败原因
            
			// 记录错误日志到数据库
			esbInterfaceLogService.savePtpInterfaceLog(obj, e.getMessage()
					+ "\n" + ExceptionUtils.getFullStackTrace(e));
        }finally{
        	//记录日志
            logger.info("FOSS接口接收运单更改与异常签收通知请求信息结束....响应结果。");
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
