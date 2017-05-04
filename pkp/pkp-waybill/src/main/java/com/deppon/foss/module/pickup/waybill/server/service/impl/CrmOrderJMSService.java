/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/service/impl/CrmOrderJMSService.java
 * 
 * FILE NAME        	: CrmOrderJMSService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.crm.inteface.foss.domain.UpdateOrderRequest;
import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.foss.framework.server.web.message.IMessageBundle;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IEWaybillMessageDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillProcessLogDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICrmOrderJMSService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.PdaAppInfoEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmModifyInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;

/**
 * 
 * CRM订单数据JMS服务
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-sunrui,date:2012-11-22 下午5:23:55,content:TODO
 * </p>
 * 
 * @author foss-sunrui
 * @date 2012-11-22 下午5:23:55
 * @since
 * @version
 */
public class CrmOrderJMSService implements ICrmOrderJMSService {

	/**
	 * 员工DAO
	 */
	private IEmployeeService employeeService;
	
	/**
	 * 与电子运单合用
	 */
	private IEWaybillMessageDao eWaybillMessageDao;
	
	/**
	 * 日志记录
	 */	
	private IWaybillProcessLogDao waybillProcessLogDao;
	
	/**
	 * 国际化资源接口
	 */
	IMessageBundle messageBundle; 
	
	/**
	 * 日志
	 */
	protected final static Logger logger = LoggerFactory
			.getLogger(CrmOrderJMSService.class.getName());

	/**
	 * 
	 * <p>
	 * 更新订单状态
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-11-22 下午5:25:17
	 * @param request
	 * @return
	 * @see
	 */
	@Override
	public ResultDto sendModifyOrder(CrmModifyInfoDto request) {
		logger.info("OMS订单数据JMS服务 sendModifyOrder start : " + ReflectionToStringBuilder.toString(request));
		// 标识更新状态为成功
		String code = "1";
		// 初始化消息
		String msg = "";
		//判断是否为空
		if (request != null) {
			// 准备消息头信息
			AccessHeader header = new AccessHeader();
			header.setEsbServiceCode("ESB_FOSS2ESB_MODIFYORDER");
			header.setVersion("0.1");
			header.setBusinessId(request.getOrderNumber());

			UpdateOrderRequest req = new UpdateOrderRequest();
			req.setBackInfo(request.getBackInfo());
			req.setDriverMobile(request.getDriverMobile());
			req.setDriverName(request.getDriverName());
			req.setGoodsStatus(request.getGoodsStatus());
            req.setDeliveryCustomerCode(request.getDeliveryCustomerCode());		    
//			增加重量体积字段
			req.setWeight(request.getWeight()==null?0:request.getWeight());
			req.setVolume(request.getVolume()==null?0:request.getVolume());
			req.setOprateDeptCode(StringUtils.isBlank(request.getOprateDeptCode())?"DP0000":request.getOprateDeptCode());
			req.setOprateUserNum(StringUtils.isBlank(request.getOprateUserNum())?"000000":request.getOprateUserNum());
			req.setOrderNumber(request.getOrderNumber());
			req.setWaybillNumber(request.getWaybillNumber());
			req.setEarningDeptStandardCode(request.getEarningDeptStandardCode());
			req.setEarningDeptStandardName(request.getEarningDeptStandardName());
			req.setBillingOrgCode(request.getBillingOrgCode());
			req.setBillingOrgPhone(request.getBillingOrgPhone());
			logger.info("OMS订单数据JMS服务 sendModifyOrder ing : " + ReflectionToStringBuilder.toString(req));
			// 发送请求
			try {
				ESBJMSAccessor.asynReqeust(header, req);
			} catch (Exception e) {
				// 对异常进行处理
				logger.error("订单状态修改失败：", e);
				code = "0";
				msg = "JMS请求发送失败：" + e.getMessage();
			}
		} else {
			//写异常信息到日志中
			logger.error("订单状态修改失败：传入的实体为空");
			code = "0";
			msg = "JMS请求发送失败：传入的实体为空";
		}

		//封装返回的结果信息
		ResultDto res = new ResultDto();
		res.setCode(code);
		res.setMsg(msg);
		logger.info("OMS订单数据JMS服务 sendModifyOrder end : " + ReflectionToStringBuilder.toString(res));
		return res;
	}
	
	/**
	 * 零担电子运单推送消息获取组装信息(参数WaybillStatus支持三中状态，常量在WaybillConstants中
	 * 	WAYBILL_PICTURE_TYPE_RETURN = "RETURN" 退回
	 *  WAYBILL_PICTURE_TYPE_PDA_ACTIVE = "PDA_ACTIVE" 开单
	 *  WAYBILL_PICTURE_TYPE_PDA_PENDING = "PDA_PENDING" 代补录
	 *  推送成功或失败都将会记录日志 PUSH_OMS_ORDER_STATUS_LOG = "pushOmsOrderStatus"    
	 * @param entity
	 * @param waybillStatus
	 * @param driverCode
	 * @param exceptionCode
	 * @return
	 */
	public void pushOmsOrderInfoStatust(String orderNo,String waybillNo,String logType,PdaAppInfoEntity pdaAppInfo,String waybillStatus,String driverCode,String exceptionCode,String exceptionMessage){
		
		/**
		 * 获取推送状态
		 */
		String goodsStatus = "";
		ResultDto sendResult = new ResultDto();
		CrmModifyInfoDto dto = new CrmModifyInfoDto();
		goodsStatus = WaybillConstants.OMS_ORDER_STATUS_RELATEION.get(waybillStatus);
		if(StringUtils.isNotEmpty(goodsStatus)){
			//设置推送状态
			dto.setGoodsStatus(goodsStatus);
			// 订单编号
			dto.setOrderNumber(orderNo);
			// 运单号码
			dto.setWaybillNumber(waybillNo);
			// 操作人员
			dto.setOprateUserNum(driverCode);
			// 操作人员所在部门
			EmployeeEntity expEmp = employeeService.queryEmployeeByEmpCode(driverCode);
			if(expEmp!=null&&expEmp.getDepartment()!=null){
				dto.setOprateDeptCode(expEmp.getDepartment().getUnifiedCode());
			}
			/**
			 * 零担电子运单只有在接货后，由App信息给予DriverCode,App完成提交之前无DriverCode 297064 2016-06-22
			 */
			if(StringUtil.isNotEmpty(driverCode)){
				// 员工信息
				EmployeeEntity emp = employeeService.queryEmployeeByEmpCode(driverCode);
				if (null != emp) {
					// 司机姓名
					dto.setDriverName(emp.getEmpName());
					// 司机手机
					dto.setDriverMobile(emp.getMobilePhone());	
				}
				//TODO 代办设置操作部门问题
			}
			/**
			 * 匹配到了映射状态，推送消息
			 */
			if(WaybillConstants.WAYBILL_PICTURE_TYPE_RETURN.contains(waybillStatus)){
				String failReason = eWaybillMessageDao.getWVMessageByFailCode(exceptionCode);
				// 错误信息
				dto.setBackInfo(failReason);
			}
			sendResult = sendModifyOrder(dto);
		}
		/**
		 * 设置推送内容和推送结果
		 */
		String sendInfoStr = ReflectionToStringBuilder.toString(dto);
		String sendResultStr = ReflectionToStringBuilder.toString(sendResult);
		String content = "ModifyInfo:\n"+sendInfoStr+";\nSendResult:\n"+sendResultStr;
		if(pdaAppInfo!=null){
			content = content+"\nAppInfo:\n"+ReflectionToStringBuilder.toString(pdaAppInfo);
		}
		if(StringUtils.isNotEmpty(waybillStatus)&&WaybillConstants.WAYBILL_PICTURE_TYPE_RETURN.equals(waybillStatus)){
			//失败
			waybillProcessLogDao.saveLog(content, orderNo , waybillNo , new Date(), logType, WaybillConstants.LTLEWAYBILL_PROCESS_LOG_FAILURE,exceptionMessage);
		}else{
			//成功
			waybillProcessLogDao.saveLog(content, orderNo , waybillNo , new Date(), logType, WaybillConstants.LTLEWAYBILL_PROCESS_LOG_SUCCESS,exceptionMessage);
		}
	}


	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}


	public void seteWaybillMessageDao(IEWaybillMessageDao eWaybillMessageDao) {
		this.eWaybillMessageDao = eWaybillMessageDao;
	}


	public void setMessageBundle(IMessageBundle messageBundle) {
		this.messageBundle = messageBundle;
	}

	public void setWaybillProcessLogDao(IWaybillProcessLogDao waybillProcessLogDao) {
		this.waybillProcessLogDao = waybillProcessLogDao;
	}
	
}