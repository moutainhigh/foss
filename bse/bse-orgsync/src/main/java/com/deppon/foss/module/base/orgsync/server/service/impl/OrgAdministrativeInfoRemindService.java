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
 * PROJECT NAME	: bse-orgsync
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/orgsync/server/service/impl/OrgAdministrativeInfoRemindService.java
 * 
 * FILE NAME        	: OrgAdministrativeInfoRemindService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.orgsync.server.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.common.api.server.service.IMessageService;
import com.deppon.foss.module.base.common.api.server.service.ISMSTempleteService;
import com.deppon.foss.module.base.common.api.shared.domain.InstationJobMsgEntity;
import com.deppon.foss.module.base.common.api.shared.dto.SmsParamDto;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.orgsync.api.server.service.IOrgAdministrativeInfoRemindService;
import com.deppon.foss.module.base.orgsync.api.shared.exception.OrgAdministrativeInfoRemindException;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ISettlementInfoQueryService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OrgSettlementInfoDto;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.util.UUIDUtils;

/**
 * 同步行政组织信息.
 *
 * @author 087584-foss-lijun
 * @date 2012-12-26 上午11:38:13
 */
public class OrgAdministrativeInfoRemindService implements
	IOrgAdministrativeInfoRemindService {
    
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(OrgAdministrativeInfoRemindService.class);
    
    /**
     * 用于发送消息的service.
     */
    private IMessageService messageService;

    /**
     * 来自于接送货的Service.
     */
    private IWaybillSignResultService waybillSignResultService;

    /**
     * 来自于中转的Service.
     */
    private IStockService stockService;

    /**
     * 来自于结算的Service.
     */
    private ISettlementInfoQueryService settlementInfoQueryService;
    
    /**
     * sMSTempleteService
     */
    private ISMSTempleteService sMSTempleteService;
    
    
    /**
     * @param sMSTempleteService the sMSTempleteService to set
     */
    public void setsMSTempleteService(ISMSTempleteService sMSTempleteService) {
        this.sMSTempleteService = sMSTempleteService;
    }


    /**
     * 撤销组织提醒.
     *
     * @param org 
     * @return boolean 是否提醒成功
     * @throws OrgAdministrativeInfoRemindException 
     * @author 087584-foss-lijun
     * @date 2012-12-26 上午11:38:15
     */
    @SuppressWarnings("deprecation")
	@Override
    public boolean cancelOrgAdministrativeInfoRemind(
	    OrgAdministrativeInfoEntity org)
	    throws OrgAdministrativeInfoRemindException {
	if (org == null) {
	    return false;
	}
	InstationJobMsgEntity msg = new InstationJobMsgEntity();

//	StringBuilder context = new StringBuilder();

	// 部门编码，需要多次使用
	String orgCode = org.getCode();

	// 库存
	long stockGoodsQty = 0;
	try {
	    if (orgCode == null) {
		LOGGER.error(" 部门编码为空");
	    }
	    if (stockService == null) {
		LOGGER.error("stockService对象为空");
	    } else {
		LOGGER.info("stockService:" + stockService);
		LOGGER.info("orgCode:" + orgCode);
		stockGoodsQty = stockService.queryStockGoodsQty(orgCode);
	    }
	} catch (Exception e) {
	    if (StringUtils.isEmpty(orgCode)) {
		LOGGER.error("部门 库存 获取异常", e);
	    } else {
		LOGGER.error(orgCode + "部门 库存 获取异常", e);
	    }
	}

	// 未签收票数
	int notSignGoods = 0;
	try {
	    notSignGoods = waybillSignResultService
		    .queryNotSignGoodsQtyByOrgCode(orgCode);
	} catch (Exception e) {
	    LOGGER.error(orgCode + "部门 未签收票数 获取异常", e);
	}

	// 查询 应付单金额，应收单金额，预付单金额，预收单金额
	OrgSettlementInfoDto stlDto = null;
	try {
	    stlDto = settlementInfoQueryService.queryOrgSettlementInfo(orgCode,
		    org.getCreateDate());
	} catch (Exception e) {
	    LOGGER.error(orgCode + "部门查询 应付单金额，应收单金额，预付单金额，预收单金额 异常", e);
	}

	if (stlDto == null) {
	    stlDto = new OrgSettlementInfoDto();
	}

	// 构造短信模板中的变量替换Map
	Map<String, String> map = new HashMap<String, String> ();
	map.put("orgName", org.getName());
	map.put("stlPayableAmount", stlDto.getPayableAmount() == null ? "0" : stlDto.getPayableAmount().toString());
	map.put("stlReceivableAmount", stlDto.getReceivableAmount() == null ? "0" : stlDto.getReceivableAmount().toString());
	map.put("stlAdvancedPaymentAmount", stlDto.getAdvancedPaymentAmount() == null ? "0" : stlDto.getAdvancedPaymentAmount().toString());
	map.put("stlDepositReceiveAmount", stlDto.getDepositReceivedAmount() == null ? "0" : stlDto.getDepositReceivedAmount().toString());
	map.put("stockGoodsQty", String.valueOf(stockGoodsQty));
	map.put("notSignGoods", String.valueOf(notSignGoods));
	// 查询短信模板
	SmsParamDto dto = new SmsParamDto();
	dto.setSmsCode("DISMISS_DEPT_REMIND");
	dto.setMap(map);
	String message = sMSTempleteService.querySmsByParam(dto);
	if (StringUtils.isBlank(message)) {
	    LOGGER.error("查询部门撤销提醒时找不到模板，smsCode 是 "+ dto.getSmsCode());
	    return false;
	}
	
//	context.append("组织 ").append(org.getName()).append(" 将撤销").append(",请留意相关事宜是否已处理完成")
//		.append(SymbolConstants.ASCII_CHANGE_LINE)
//		.append(SymbolConstants.ASCII_CHANGE_LINE).append("待撤销组织相关明细:")
//		.append(SymbolConstants.ASCII_CHANGE_LINE);
//
//	if (stlDto.getPayableAmount() != null
//		&& stlDto.getPayableAmount().floatValue() != 0) {
//	    context.append(SymbolConstants.ASCII_CHANGE_LINE).append("		应付单金额")
//		    .append(stlDto.getPayableAmount()).append("元");
//	}
//
//	if (stlDto.getReceivableAmount() != null
//		&& stlDto.getReceivableAmount().floatValue() != 0) {
//	    context.append(SymbolConstants.ASCII_CHANGE_LINE).append("		应收单金额")
//		    .append(stlDto.getReceivableAmount()).append("元");
//	}
//
//	if (stlDto.getAdvancedPaymentAmount() != null
//		&& stlDto.getAdvancedPaymentAmount().floatValue() != 0) {
//	    context.append(SymbolConstants.ASCII_CHANGE_LINE).append("		预付单金额")
//		    .append(stlDto.getAdvancedPaymentAmount()).append("元");
//	}
//
//	if (stlDto.getDepositReceivedAmount() != null
//		&& stlDto.getDepositReceivedAmount().floatValue() != 0) {
//	    context.append(SymbolConstants.ASCII_CHANGE_LINE).append("		预收单金额")
//		    .append(stlDto.getDepositReceivedAmount()).append("元");
//	}
//
//	context.append(SymbolConstants.ASCII_CHANGE_LINE);
//
//	if (stockGoodsQty > 0) {
//	    context.append(SymbolConstants.ASCII_CHANGE_LINE).append("		库存")
//		    .append(stockGoodsQty).append("件");
//	}
//
//	if (notSignGoods > 0) {
//	    context.append(SymbolConstants.ASCII_CHANGE_LINE).append("		未签收")
//		    .append(notSignGoods).append("票");
//	}

	// 发送方编码
	msg.setSenderCode(MessageConstants.MSG__SYS_USER_CODE);
	// 发送方名称
	msg.setSenderName(MessageConstants.MSG__SYS_USER_CODE);
	// 发送方组织编码
	msg.setSenderOrgCode(MessageConstants.MSG__SYS_ORG_CODE);
	// 发送方组织名称
	msg.setSenderOrgName(MessageConstants.MSG__SYS_ORG_CODE);
	// 接收方组织编码
	msg.setReceiveOrgCode(org.getCode());
	// 接收方角色编码
	msg.setReceiveRoleCode(null);

	/**
	 * 接收方类型； 消息常量类 MessageConstants 常量 MSG__RECEIVE_TYPE__ORG 组织
	 * MSG__RECEIVE_TYPE__ORG_ROLE 组织+角色
	 * 
	 */
	msg.setReceiveType(MessageConstants.MSG__RECEIVE_TYPE__ORG);
	// 消息内容
	msg.setContext(message);
	/**
	 * 站内消息类型 普通消息/全网通知 所需常量 数据字典类 DictionaryValueConstants 常量
	 * MSG_TYPE__NORMAL 普通消息 MSG_TYPE__ALLNET 全网消息
	 */
	msg.setMsgType(DictionaryValueConstants.MSG_TYPE__NORMAL);
	// 发送时间
	msg.setPostDate(new Date());
	/**
	 * 消息状态
	 * 
	 * 所需常量 MessageConstants MSG__STATUS__PROCESSED 已处理
	 * MSG__STATUS__PROCESSING 未处理
	 */
	msg.setStatus(MessageConstants.MSG__STATUS__PROCESSING);

	// 要把ID设置进去
	msg.setId(UUIDUtils.getUUID());

	try {
	    LOGGER.info("开始发送撤销组织消息, orgCode = " + msg.getReceiveOrgCode() + ", msg.id = " + msg.getId());
	    LOGGER.info("撤销组织消息的内容为:" + msg.getContext());
	    messageService.createBatchInstationMsg(msg);
	} catch (Exception e) {
	    LOGGER.error(orgCode + "部门 预撤销提醒 异常", e);
	}

	return true;
    }


    /**
     * 下面是需要注入的service及set方法.
     *
     * @param messageService the new 用于发送消息的service
     */


    public void setMessageService(IMessageService messageService) {
	this.messageService = messageService;
    }

    /**
     * 设置 来自于接送货的Service.
     *
     * @param waybillSignResultService the new 来自于接送货的Service
     */
    public void setWaybillSignResultService(
	    IWaybillSignResultService waybillSignResultService) {
	this.waybillSignResultService = waybillSignResultService;
    }

    /**
     * 设置 来自于中转的Service.
     *
     * @param stockService the new 来自于中转的Service
     */
    public void setStockService(IStockService stockService) {
	this.stockService = stockService;
    }

    /**
     * 设置 来自于结算的Service.
     *
     * @param settlementInfoQueryService the new 来自于结算的Service
     */
    public void setSettlementInfoQueryService(
	    ISettlementInfoQueryService settlementInfoQueryService) {
	this.settlementInfoQueryService = settlementInfoQueryService;
    }

}
