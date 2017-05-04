/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-common-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/shared/domain/SMSSendLogEntity.java
 * 
 * FILE NAME        	: SMSSendLogEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 用来存储交互“短信信息”的数据库对应实体：无SUC
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-19 下午6:12:32</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-19 下午6:12:32
 * @since
 * @version
 */
public class SMSSendLogEntity extends BaseEntity {
    
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -1075369796545989863L;

    //手机号码
    private String mobile;

    //短信内容
    private String content;

    //发送部门名称
    private String senddept;
    
    //发送部门编码
    private String senddeptCode;
    
    //发送部门标杆编码
    private String senddeptUnifiedCode;
    
    //发送人名称
    private String sender;
    
    //发送人编码
    private String senderCode;

    //业务类型
    private String msgtype;

    //系统来源
    private String msgsource;

    //唯一标识
    private String unionId;

    //运单号
    private String waybillNo;

    //服务类型（1:短信、2:语音、3:短信语音）
    private String serviceType;

    //发送时间
    private Date sendTime;

    //最晚发送时间
    private Date lastsendTime;
    
    //是否通过验证
    private String isValidated;
    
    //错误原因
    private String reason;
	
    //错误结果码
    private String resultCode;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSenddept() {
        return senddept;
    }

    public void setSenddept(String senddept) {
        this.senddept = senddept;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public String getMsgsource() {
        return msgsource;
    }

    public void setMsgsource(String msgsource) {
        this.msgsource = msgsource;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getLastsendTime() {
        return lastsendTime;
    }

    public void setLastsendTime(Date lastsendTime) {
        this.lastsendTime = lastsendTime;
    }

    public String getIsValidated() {
        return isValidated;
    }

    public void setIsValidated(String isValidated) {
        this.isValidated = isValidated;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

	
	public String getSenddeptCode() {
		return senddeptCode;
	}

	
	public void setSenddeptCode(String senddeptCode) {
		this.senddeptCode = senddeptCode;
	}

	
	public String getSenderCode() {
		return senderCode;
	}

	
	public void setSenderCode(String senderCode) {
		this.senderCode = senderCode;
	}

	
	public String getSenddeptUnifiedCode() {
		return senddeptUnifiedCode;
	}

	
	public void setSenddeptUnifiedCode(String senddeptUnifiedCode) {
		this.senddeptUnifiedCode = senddeptUnifiedCode;
	}
    
}
