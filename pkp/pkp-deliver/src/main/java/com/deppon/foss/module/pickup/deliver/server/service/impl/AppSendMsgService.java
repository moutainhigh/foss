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
 * PROJECT NAME	: pkp-deliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/deliver/server/service/impl/AppSendMsgService.java
 * 
 * FILE NAME        	: AppSendMsgService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 *****************************************************************************
 *修订记录 
 日期 	修订内容 	修订人员 	版本号 
 2016-09-12 	新增	jiangxun	V0.1

 1.	
 DN201606250013 接送货异常联系短信推送
 1.1
 司机送接过程，及时推送异常短信至客户
 1.2	
 司机送货过程，及时推送异常短信至客户


 **/
package com.deppon.foss.module.pickup.deliver.server.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.common.api.server.service.ISMSSendLogService;
import com.deppon.foss.module.base.common.api.server.service.ISMSTempleteService;
import com.deppon.foss.module.base.common.api.shared.domain.SMSSendLogEntity;
import com.deppon.foss.module.base.common.api.shared.dto.SmsParamDto;
import com.deppon.foss.module.base.common.api.shared.exception.SMSSendLogException;
import com.deppon.foss.module.base.common.api.shared.exception.SMSTempleteException;
import com.deppon.foss.module.pickup.pda.api.server.service.IAppSendMsgService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.AppSMSDto;
import com.deppon.foss.module.pickup.pda.api.shared.exception.PdaProcessException;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.DeliverbillConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.NotifyCustomerException;
import com.deppon.foss.util.UUIDUtils;

/**
 * 
 * APP短信发送 service实现
 * 
 * @author
 * @date 2016-09-12 下午5:15:23
 * @since
 * @version
 */
public class AppSendMsgService implements IAppSendMsgService {
    /**
     * 记录 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AppSendMsgService.class);

    /**
     * 发送短信接口
     */
    private ISMSSendLogService smsSendLogService;

    /**
     * 短信模板service接口
     */
    private ISMSTempleteService sMSTempleteService;

    /**
     * 员工接口
     */
    private IEmployeeService employeeService;

    /**
     * 司机接、送货过程，及时推送异常短信至客户，避免客户投诉 MsgType 1：接货短信，发给发货人 MsgType 2：送货短信，发给收货人
     * 
     * @author 302346
     * @date 2016-9-12 下午5:10:42
     */
    public void appSendMsg(AppSMSDto appSMSDto) {
        try {
            // 短信模板代码
            String smsCode = "";
            // 短信业务类型
            String msgType = "";
            // 参数校验
            if (appSMSDto == null) {
                LOGGER.error("APP短信实体为空！不能发送短信");
                throw new PdaProcessException("APP短信实体为空！不能发送短信");
            }
            if (StringUtil.isEmpty(appSMSDto.getDriverCode())) {
                LOGGER.error("APP发送短信，司机编号为空，不能发送短信");
                throw new PdaProcessException("APP发送短信，司机编号为空，不能发送短信");
            }
            if (StringUtil.isEmpty(appSMSDto.getMsgType())) {
                LOGGER.error("APP发送短信，短信类型为空，不能发送短信");
                throw new PdaProcessException("APP发送短信，短信类型为空，不能发送短信");
            }
            if (StringUtil.isEmpty(appSMSDto.getMobile())) {
                LOGGER.error("APP发送短信，客户手机号为空，不能发送短信");
                throw new PdaProcessException("APP发送短信，客户手机号为空，不能发送短信");
            }

            // 短信接受者手机号 PKP_ORDER接货类型
            String toPhone = appSMSDto.getMobile().trim();
            if (appSMSDto.getMsgType().equalsIgnoreCase("1")) {// 接货短信，发给发货人
                smsCode = DeliverbillConstants.SMS_CODE_PKP_PICK_NO_ANSWER;
                msgType = DeliverbillConstants.PKP_NOTIFY_APP_PICKUP;
            } else if (appSMSDto.getMsgType().equalsIgnoreCase("2")) {// 送货短信，发给收货人
                smsCode = DeliverbillConstants.SMS_CODE_PKP_DELIVER_NO_ANSWER;
                msgType = DeliverbillConstants.PKP_NOTIFY_APP_DELIVER;
            } else {
                LOGGER.error("APP发送短信，短信类型不正确，不能发送短信");
                throw new PdaProcessException("APP发送短信，短信类型不正确，不能发送短信");
            }
            // 根据司机编码查询司机姓名和电话
            EmployeeEntity employee = employeeService.queryEmployeeByEmpCode(StringUtil
                    .defaultIfNull(appSMSDto.getDriverCode()));
            appSMSDto.setDriverName(employee.getEmpName());// 司机姓名
            appSMSDto.setDriverTel(employee.getMobilePhone());// 司机电话

            // 模版参数
            SmsParamDto smsParamDto = new SmsParamDto();
            // 短信模板编码
            smsParamDto.setSmsCode(smsCode);
            // 部门编码
            // smsParamDto.setOrgCode(appSMSDto.getOperateOrgCode());
            // 参数Map
            smsParamDto.setMap(this.getSmsParam(appSMSDto));
            // 短信内容
            String msgConten = "";
            msgConten = sMSTempleteService.querySmsByParam(smsParamDto);
            LOGGER.info("APP短信内容：{}", msgConten);

            // 短信实体
            SMSSendLogEntity smsSendLog = new SMSSendLogEntity();
            // 发送部门编码
            smsSendLog.setSenddeptCode(employee.getOrgCode());
            // 发送人员编码
            smsSendLog.setSenderCode(appSMSDto.getDriverCode());
            // 电话
            smsSendLog.setMobile(toPhone);
            // 短信内容
            smsSendLog.setContent(msgConten);
            // 发送部门
            smsSendLog.setSenddept(employee.getOrgName());
            // 发送人
            smsSendLog.setSender(appSMSDto.getDriverCode());
            // 业务类型
            smsSendLog.setMsgtype(msgType);
            // 短信来源
            smsSendLog.setMsgsource(NotifyCustomerConstants.SYS_SOURCE);
            // 唯一标识
            smsSendLog.setUnionId(UUIDUtils.getUUID());
            // 运单号
            smsSendLog.setWaybillNo(appSMSDto.getWaybillNo());
            // 发送时间
            smsSendLog.setSendTime(new Date());
            // 服务类型（1:短信）
            smsSendLog.setServiceType(NumberConstants.NUMERAL_S_ONE);
            LOGGER.info("APP短信内容：" + ReflectionToStringBuilder.toString(smsSendLog));
            // 发送短信内容
            smsSendLogService.sendSMS(smsSendLog);
        } catch (SMSTempleteException e) {
            LOGGER.error(e.getMessage(), e);
            throw new NotifyCustomerException(NotifyCustomerException.MESSAGE_EMPTY, e);
        } catch (SMSSendLogException se) {
            LOGGER.error(NotifyCustomerException.ERROR, se);
            throw new NotifyCustomerException(se.getMessage(), se);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new NotifyCustomerException(ex.getMessage(), ex);
        }
    }

    /**
     * 设置发送客户短信模版内容的参数--302346
     * 
     * @param dto
     * @return
     */
    private Map<String, String> getSmsParam(AppSMSDto dto) {
        Map<String, String> paramMap = new HashMap<String, String>();
        // 运单号
        // if (StringUtil.isNotEmpty(dto.getWaybillNo())) {
        // paramMap.put("waybillNo", dto.getWaybillNo());
        // } else {
        // paramMap.put("waybillNo", "");
        // }
        // 司机姓名
        if (StringUtil.isNotEmpty(dto.getDriverName())) {
            paramMap.put("driverName", dto.getDriverName());
        } else {
            paramMap.put("driverName", "");
        }
        // 司机电话
        if (StringUtil.isNotEmpty(dto.getDriverTel())) {
            paramMap.put("driverPhone", dto.getDriverTel());
        } else {
            paramMap.put("driverPhone", "");
        }
        return paramMap;
    }

    public void setSmsSendLogService(ISMSSendLogService smsSendLogService) {
        this.smsSendLogService = smsSendLogService;
    }

    public void setsMSTempleteService(ISMSTempleteService sMSTempleteService) {
        this.sMSTempleteService = sMSTempleteService;
    }

    public void setEmployeeService(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

}