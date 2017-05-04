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
 * PROJECT NAME	: bse-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/action/MsgAction.java
 * 
 * FILE NAME        	: MsgAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.server.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.common.api.server.service.IMessageService;
import com.deppon.foss.module.base.common.api.server.service.IToDoMsgService;
import com.deppon.foss.module.base.common.api.shared.domain.InstationJobMsgEntity;
import com.deppon.foss.module.base.common.api.shared.dto.MessagesDto;
import com.deppon.foss.module.base.common.api.shared.dto.ToDoMsgDto;
import com.deppon.foss.module.base.common.api.shared.vo.MsgVo;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 站内消息和待办事项Action
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2012-12-18 上午10:29:30
 */
public class MsgAction extends AbstractAction {

	private static final long serialVersionUID = -632086951867058284L;

	private static final Logger LOGGER = LoggerFactory.getLogger(MsgAction.class);

	/**
	 * 站内消息Service
	 */
	private IMessageService messageService;

	/**
	 * 待办消息Service
	 */
	private IToDoMsgService toDoMsgService;
	
	

	/**
	 * 站内消息Vo
	 */
	private  MsgVo msgVo;

	/**
	 * 站内消息初始化
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @param
	 * @date 2012-12-20 下午5:14:19
	 * @return
	 */
	public String instationMsgInit() {
		return returnSuccess();
	}

	/**
	 * 站内消息查询
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @param
	 * @date 2012-12-20 下午6:33:11
	 * @return
	 */
	@JSON
	public String queryInstationMsg() {
		try {
			//当前用户登录信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			if (null == currentInfo) {
				return returnError("请刷新页面再试");
			}

			if (null == msgVo) {
				return returnError("查询参数无效");
			}
			//前端用户输入参数信息
			MessagesDto msgDto = msgVo.getMsgDto();
			//站内消息开始时间
			Date startTime = msgDto.getCreateStartTime();
			//站内消息结束时间
			Date endTime = msgDto.getCreateEndTime();

			if (null == startTime) {
				return returnError("请选择消息发送开始日期!");
			}
			if (null == endTime) {
				return returnError("请选择消息发送结束日期!");
			}
			//对比开始时间与结束时间相差天数
			long diffDays = DateUtils.getTimeDiff(startTime, endTime);
			//查询时间间隔不能大于30天
			
			if (diffDays > MessageConstants.DATE_LIMIT_DAYS_MONTH) {
				return returnError("查询日期不能大于"
						+ MessageConstants.DATE_LIMIT_DAYS_MONTH + "天!");
			}
			if (diffDays < 0) {
				return returnError("消息发送结束必须大于消息发送开始!");
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("startTime:" + startTime);
				LOGGER.debug("endTime:" + endTime);
			}
			//结束时间加1天
			msgDto.setCreateEndTime(DateUtils.addDayToDate(endTime, 1));
			//返回记录数
			Long totalCount = messageService.countQueryInstationMsgByEntity(msgDto,currentInfo);
			if (null != totalCount && totalCount > 0) {
				List<MessagesDto> instationMsgList = messageService.queryInstationMsgByEntity(msgDto,currentInfo, this.getStart(),this.getLimit());
				msgVo.setMessageList(instationMsgList);
				this.setTotalCount(totalCount);
			}
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return returnError(e);
		}

	}
	/**
	 * 查询同类型消息数据
	 * @author 101911-foss-zhouChunlai
	 * @param
	 * @date 2012-12-20 下午6:33:11
	 * @return
	 */
	@JSON
	public String queryMsgByMsgType() {
		try {
			//当前用户登录信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			if (null == currentInfo) {
				return returnError("请刷新页面再试");
			}
			if (null == msgVo) {
				return returnError("查询参数无效");
			}
			//返回记录数
			Long totalCount = messageService.countQueryMsgByMsgType(msgVo.getMsgType(),currentInfo);
			if (null != totalCount && totalCount > 0) {
				//根据消息类型和当前登录人编码获取站内消息信息
				List<MessagesDto> instationMsgList = messageService.queryMsgByMsgType(msgVo.getMsgType(),currentInfo, this.getStart(),this.getLimit());
				//把获取信息展示到前端
				msgVo.setMessageList(instationMsgList);
				this.setTotalCount(totalCount);
			}
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return returnError(e);
		}
	} 
	/**
	 * 待办事项初始化
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @param
	 * @date 2012-12-20 下午5:14:35
	 * @return
	 */
	public String toDoMsgInit() {
		return returnSuccess();
	}

	/**
	 * 待办事项查询
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @param
	 * @date 2012-12-20 下午6:33:11
	 * @return
	 */
	@JSON
	public String queryToDoMsg() {
		try {
			//当前用户登录信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			if (null == currentInfo) {
				return returnError("请刷新页面再试");
			}

			if (null == msgVo) {
				return returnError("查询参数无效");
			}
			//前端用户输入参数信息
			ToDoMsgDto  toDoDto = msgVo.getToDoDto();
			//站内消息开始时间
			Date startTime = toDoDto.getCreateStartTime();
			//站内消息结束时间
			Date endTime = toDoDto.getCreateEndTime();

			if (null == startTime) {
				return returnError("请选择待办开始日期!");
			}
			if (null == endTime) {
				return returnError("请选择待办结束日期!");
			}
			long diffDays = DateUtils.getTimeDiff(startTime, endTime);
			if (diffDays > MessageConstants.DATE_LIMIT_DAYS_MONTH) {
				return returnError("查询日期不能大于"
						+ MessageConstants.DATE_LIMIT_DAYS_MONTH + "天!");
			}
			if (diffDays < 0) {
				return returnError("待办结束时间必须大于待办开始时间!");
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("startTime:" + startTime);
				LOGGER.debug("endTime:" + endTime);
			}
			//设置结束日期加1天
			toDoDto.setCreateEndTime(DateUtils.addDayToDate(endTime, 1));
			//根据待办条件和当前用户信息返回
			Long totalCount = toDoMsgService.countSelectToDoMsgDetailByEntity(toDoDto,currentInfo);
			if (null != totalCount && totalCount > 0) {
				List<ToDoMsgDto> toDoMsgList = toDoMsgService.selectToDoMsgDetailByEntity(toDoDto,currentInfo,this.getStart(),this.getLimit());
				msgVo.setToDoMsgList(toDoMsgList);
				this.setTotalCount(totalCount);
			}
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return returnError(e);
		}
	}
	/**
	 * 查询同类型待办数据
	 * @author 101911-foss-zhouChunlai
	 * @param
	 * @date 2012-12-20 下午6:33:11
	 * @return
	 */
	@JSON
	public String queryToDoMsgByBisType() {
		try {
			//当前用户登录信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			if (null == currentInfo) {
				return returnError("请刷新页面再试");
			}
			if (null == msgVo) {
				return returnError("查询参数无效");
			}
			//返回记录数
			Long totalCount = toDoMsgService.countQueryToDoMsgByBisType(msgVo.getBisType(),currentInfo,MessageConstants.MSG__URL_TYPE__WEB);
			if (null != totalCount && totalCount > 0) {
				List<ToDoMsgDto> toDoMsgList = toDoMsgService.queryToDoMsgByBisType(msgVo.getBisType(),currentInfo,MessageConstants.MSG__URL_TYPE__WEB, this.getStart(),this.getLimit());
				msgVo.setToDoMsgList(toDoMsgList);
				this.setTotalCount(totalCount);
			}
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return returnError(e);
		}
	}
	
	/**
	 * 统计待办事项,站内消息
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @param
	 * @date 2012-12-20 上午9:28:41
	 * @return
	 */
	@JSON
	public String queryMsgTotal() {
		try {
			//当前用户登录信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			if (null == currentInfo) {
				return returnError("用户信息不正确,请刷新页面再进行操作!");
			}
			// 获取未读普通消息
			List<MessagesDto> normalMsgList = messageService.queryInstationMsgTotal(currentInfo,DictionaryValueConstants.MSG_TYPE__NORMAL);
			//获取开单到付客户网上支付消息
			List<MessagesDto> onLinePayMentMsgList = messageService.queryInstationMsgTotal(currentInfo,MessageConstants.MSG_TYPE__ONLINEPAYMENT);
			//获取供应商补录失败消息-187862-dujunhui
			List<MessagesDto> supplierFailPatchMsgList = messageService.queryInstationMsgTotal(currentInfo,MessageConstants.MSG_TYPE__SUPPLIER_FAIL_PATCH);
			
			List<MessagesDto> allMsgList = new ArrayList<MessagesDto>();
			allMsgList.addAll(supplierFailPatchMsgList);
			allMsgList.addAll(normalMsgList);
			allMsgList.addAll(onLinePayMentMsgList);
			
			// 获取未读在线通知
			List<MessagesDto> netMsgList = messageService.queryQWInstationMsgTotal(currentInfo);
			//未处理信息统计
			int noDealTotal=0;
			//313353 sonar
			noDealTotal = this.sonarSplitOne(netMsgList, normalMsgList, supplierFailPatchMsgList, onLinePayMentMsgList);
			
			// 获取未读代收货款在线提醒
			List<MessagesDto> collectionMsgList = messageService.queryInstationMsgTotal(currentInfo,MessageConstants.MSG_TYPE__COLLECTION);
			if(CollectionUtils.isNotEmpty(collectionMsgList)){
				noDealTotal=noDealTotal+collectionMsgList.get(0).getNoDealNum();
			}
			//获取未读代收货款退款失败提醒
			List<MessagesDto> codPayFaild = messageService.queryInstationMsgTotal(currentInfo,MessageConstants.MSG_TYPE__CODPAYFAILD);
			if(CollectionUtils.isNotEmpty(codPayFaild)){
				noDealTotal=noDealTotal+codPayFaild.get(0).getNoDealNum();
			}
			// 获取未解决的cc催单信息
			List<ToDoMsgDto> callCenterWaybillMsgList = toDoMsgService.queryCallCenterWaybillMsgTotal(currentInfo);
				if(CollectionUtils.isNotEmpty(callCenterWaybillMsgList)){
					noDealTotal=noDealTotal+callCenterWaybillMsgList.get(0).getNoDealNum();
			}
			//ddw
			List<MessagesDto> failingInvoiceMsgList = messageService.queryFailingInvoiceMsgTotal(currentInfo,MessageConstants.MSG_TYPE__FAILINGINVOICE);
			if(CollectionUtils.isNotEmpty(failingInvoiceMsgList)){
				noDealTotal=noDealTotal+failingInvoiceMsgList.get(0).getNoDealNum();
			}
			
			//未对账月结客户消息187862-dujunhui
			List<MessagesDto> unReconciliationMsgList = messageService.queryUnReconciliationMsgTotal(currentInfo,MessageConstants.MSG_TYPE__UNRECONCILIATION);
			if(CollectionUtils.isNotEmpty(unReconciliationMsgList)){
				noDealTotal=noDealTotal+unReconciliationMsgList.get(0).getNoDealNum();
			}
			
			//距结款时间不足5日还未还款月结客户消息187862-dujunhui
			List<MessagesDto> noRepaymentMsgList = messageService.queryNoRepaymentMsgTotal(currentInfo,MessageConstants.MSG_TYPE__NOREPAYMENT);
			if(CollectionUtils.isNotEmpty(noRepaymentMsgList)){
				noDealTotal=noDealTotal+noRepaymentMsgList.get(0).getNoDealNum();
			}
			// 获取未处理待办事项
			List<ToDoMsgDto> toDoMsgList = toDoMsgService.queryToDoMsgTotal(currentInfo,MessageConstants.MSG__URL_TYPE__WEB);
			if(CollectionUtils.isNotEmpty(toDoMsgList)){
				for(ToDoMsgDto toDoDto :  toDoMsgList){
					noDealTotal = noDealTotal+toDoDto.getNoDealNum();
				}
			}
			// 获取未处理弃货信息
			List<ToDoMsgDto> autoAbandGoodsMsgList = toDoMsgService.queryAbandGoodsTypeAutoTotal(currentInfo,MessageConstants.MSG__URL_TYPE__WEB);
			if(CollectionUtils.isNotEmpty(autoAbandGoodsMsgList)){
				for(ToDoMsgDto autoAbandGoods :  autoAbandGoodsMsgList){
					noDealTotal = noDealTotal+autoAbandGoods.getNoDealNum();
				}
			}

			//LHS
			List<MessagesDto>  debitWillOverTips = new ArrayList<MessagesDto>();
			//临时欠款消息
			List<MessagesDto> dtMsg = messageService.queryUnReconciliationMsgTotal(currentInfo, MessageConstants.MSG_DT__DEBITWILLOVER);

			//月结消息
			List<MessagesDto> ctMsg = messageService.queryUnReconciliationMsgTotal(currentInfo, MessageConstants.MSG_CT__DEBITWILLOVER);
			//拼装消息
			debitWillOverTips.addAll(dtMsg);
			debitWillOverTips.addAll(ctMsg);
			if(CollectionUtils.isNotEmpty(debitWillOverTips)){
				for(MessagesDto messagesDto:debitWillOverTips){
					noDealTotal=noDealTotal+ messagesDto.getNoDealNum();
				}
			}

			/*//派送退回提醒
			List<MessagesDto>  deliveryReturnList = new ArrayList<MessagesDto>();
			//派送拉回消息
			List<MessagesDto> pslhMsg = messageService.queryDeliveryReturnMsgTotal(MessageConstants.MSG_SEND__DELIVERYRETURN,MessageConstants.MSG_STATUS,currentInfo);

			//调度退回消息
			List<MessagesDto> duthMsg = messageService.queryDeliveryReturnMsgTotal(MessageConstants.MSG_DISPATCH__DELIVERYRETURN,MessageConstants.MSG_STATUS,currentInfo);
			//拼装消息
			deliveryReturnList.addAll(pslhMsg);
			deliveryReturnList.addAll(duthMsg);
			if(CollectionUtils.isNotEmpty(deliveryReturnList)){
				for(MessagesDto messagesDto:deliveryReturnList){
					noDealTotal=noDealTotal+ messagesDto.getNoDealNum();
				}
			}*/

			// 实例化消息Vo
			msgVo = new MsgVo();

			// 设置普通消息
			msgVo.setNormalMsgList(allMsgList);
			// 设置全网消息
			msgVo.setNetMsgList(netMsgList);
			// 设置待办事项
			msgVo.setToDoMsgList(toDoMsgList);
			//ddw
			msgVo.setFailingInvoiceMsgList(failingInvoiceMsgList);
			//设置未对账月结客户消息187862-dujunhui
			msgVo.setUnReconciliationMsgList(unReconciliationMsgList);
			//设置距结款时间不足5日还未还款月结客户消息187862-dujunhui
			msgVo.setNoRepaymentMsgList(noRepaymentMsgList);
			//设置代收货款在线提醒(代收货款退款失败和代收货款提醒)
			collectionMsgList.addAll(codPayFaild);
			msgVo.setCollectionMsgList(collectionMsgList);
			//设置未处理信息总记录数
			msgVo.setNoDealtotal(noDealTotal);
			//设置弃货在线提醒
			msgVo.setAutoAbandMsgList(autoAbandGoodsMsgList);
			//设置催单在线提醒
			msgVo.setCallCenterWaybillMsgList(callCenterWaybillMsgList);
			msgVo.setDebitWillOverList(debitWillOverTips);
			//派送退回提醒
			//msgVo.setDeliveryReturnList(deliveryReturnList);
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return returnError(e);
		}

	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private int sonarSplitOne(List<MessagesDto> netMsgList, List<MessagesDto> normalMsgList,
			List<MessagesDto> supplierFailPatchMsgList, List<MessagesDto> onLinePayMentMsgList) {
		int noDealTotal = 0;
		if(CollectionUtils.isNotEmpty(normalMsgList)){
			noDealTotal=normalMsgList.get(0).getNoDealNum();
		}
		if(CollectionUtils.isNotEmpty(onLinePayMentMsgList)){
			noDealTotal=noDealTotal+onLinePayMentMsgList.get(0).getNoDealNum();
		}
		if(CollectionUtils.isNotEmpty(supplierFailPatchMsgList)){//获取供应商补录失败消息条数-187862-dujunhui
			noDealTotal=noDealTotal+supplierFailPatchMsgList.get(0).getNoDealNum();
		}
		if(CollectionUtils.isNotEmpty(netMsgList)){
			noDealTotal=noDealTotal+netMsgList.get(0).getNoDealNum();
		}
		return noDealTotal;
	}

	/**
	 * 根据Id更新站内消息的读取状态
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2012-12-25 下午3:28:35
	 * @return 
	 */
	public String updateMsgIsReadByIds(){
		try { 
			if(null == msgVo){
				return returnError("更新参数不正确");
			}
			//当前用户登录信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			if (null == currentInfo) {
				return returnError("用户信息不正确,请刷新页面再进行操作!");
			}
			messageService.updateInstationMsgByIds(msgVo.getIds(),currentInfo);
			return returnSuccess("标记成功");
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return returnError(e);
		}
	}
	
	/**
	 * 初始化全网发送界面
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2012-12-26 下午4:32:18
	 * @return sendAllNetMsg
	 */
	public String allNetMsgSendInit(){
		return returnSuccess();
	}
	
	/**
	 * 消息发送
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2012-12-25 下午3:28:35
	 * @return 
	 */
	public String msgSend(){
		try { 
			//当前用户登录信息
			CurrentInfo currentInfo=FossUserContext.getCurrentInfo();
			if(null == currentInfo){
				return returnError("session失效，请刷新页面!");
			}
			if(null == msgVo){
				return returnError("参数不正确");
			}
			InstationJobMsgEntity entity= msgVo.getMsgJobEntity();
			if(StringUtils.isBlank(entity.getReceiveOrgCode())){
				return returnError("接收部门不能为空");
			}
			if(StringUtils.isBlank(entity.getContext())){
				return returnError("发送内容不能为空");
			}
			//如果发送部门为总公司则认为该消息为全网消息,否则为普通消息
			if(StringUtils.equals(FossConstants.ROOT_ORG_CODE, entity.getReceiveOrgCode())){
				messageService.manualCreateInstationMsg(entity,currentInfo);
			}else{
				messageService.manualCreateBatchInstationMsg(entity,currentInfo);
			}
			
			return returnSuccess("发送成功");
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return returnError(e);
		}
	}
	
	public MsgVo getMsgVo() {
		return msgVo;
	}

	public void setMsgVo(MsgVo msgVo) {
		this.msgVo = msgVo;
	}

	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	public void setToDoMsgService(IToDoMsgService toDoMsgService) {
		this.toDoMsgService = toDoMsgService;
	}
	
}
