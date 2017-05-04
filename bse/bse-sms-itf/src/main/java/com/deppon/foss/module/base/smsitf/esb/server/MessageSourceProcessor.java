package com.deppon.foss.module.base.smsitf.esb.server;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.dpap.esb.mqc.core.exception.ESBBusinessException;
import com.deppon.dpap.esb.mqc.core.process.IProcess;
import com.deppon.esb.inteface.domain.msg.InstationMsg;
import com.deppon.esb.inteface.domain.msg.OnlineNoticeMsg;
import com.deppon.esb.inteface.domain.msg.SyncMsgRequest;
import com.deppon.esb.inteface.domain.msg.SyncMsgResponse;
import com.deppon.esb.inteface.domain.msg.SyncMsgResponseBody;
import com.deppon.esb.inteface.domain.msg.TodoMsg;
import com.deppon.esb.pojo.transformer.jaxb.SyncMsgRequestTrans;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.common.api.server.service.ISyncMessageService;
import com.deppon.foss.module.base.common.api.shared.domain.InstationMsgEntity;
import com.deppon.foss.module.base.common.api.shared.domain.MsgOnlineEntity;
import com.deppon.foss.module.base.common.api.shared.domain.ToDoMsgEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.CollectionUtils;
/*import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;*/


/**
 *  待办事项接口
 *  ECS快递将在线通知、站内消息、待办事项推给FOSS，FOSS插到数据库里
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:232607,date:2016-5-6 上午9:01:07,content:TODO </p>
 * @author 232607 
 * @date 2016-5-6 上午9:01:07
 * @since
 * @version
 */
public class MessageSourceProcessor implements IProcess {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MessageSourceProcessor.class);

	ISyncMessageService syncMessageService;

	public void setSyncMessageService(ISyncMessageService syncMessageService) {
		this.syncMessageService = syncMessageService;
	}

	@Override
	public Object process(Object req) throws ESBBusinessException {
		// TODO Auto-generated method stub
		LOGGER.info(" ***************************** 同步ECS待办内容开始 ***************************** ");
		// 接收到的请求消息类
		SyncMsgRequest request = (SyncMsgRequest) req;
		// 响应消息类
		SyncMsgResponse response = new SyncMsgResponse();
		SyncMsgResponseBody body = new SyncMsgResponseBody();
		LOGGER.info(new SyncMsgRequestTrans().fromMessage(request));// 请求转换类
		try{
			if (null != request) {
				List<Object> entitys = new ArrayList<Object>();
				if (!CollectionUtils.isEmpty(request.getInstationMsgInfo())) {
					entitys.addAll(transformationInstationMsg(request.getInstationMsgInfo()));
				} else if (!CollectionUtils.isEmpty(request.getOnlineNoticeMsgInfo())) {
					entitys.addAll(transformationOnlineNoticeMsg(request.getOnlineNoticeMsgInfo()));
				} else if (!CollectionUtils.isEmpty(request.getTodoMsgInfo())) {
					entitys.addAll(transformationTodoMsg(request.getTodoMsgInfo()));
				}
				body = syncMessageService.syncMessage(entitys);
				response.setResponseHeader(request);
				response.setResponseBody(body);
			}
			LOGGER.info(" ***************************** 同步ECS待办内容结束************************** ");
			return response;
		}catch(BusinessException e){
			body.setStatus("1");
			body.setMsg("未知异常");
			response.setResponseHeader(request);
			response.setResponseBody(body);
			return response;
		}
	}

	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		// TODO Auto-generated method stub
		LOGGER.error("ESB处理错误");
		return null;
	}

	/**
	 * <p>在线通知的转换方法</p> 
	 * @author 232607 
	 * @date 2016-4-28 下午4:22:42
	 * @param onlineNoticeMsgs
	 * @return
	 * @see
	 */
	private List<MsgOnlineEntity> transformationOnlineNoticeMsg(List<OnlineNoticeMsg> onlineNoticeMsgs) {
		List<MsgOnlineEntity> entityList = new ArrayList<MsgOnlineEntity>();
		MsgOnlineEntity entity;
		for(OnlineNoticeMsg msg : onlineNoticeMsgs){
			entity=new MsgOnlineEntity();
			entity.setExpireTime(msg.getExpireTime());
			entity.setContext(msg.getContext());
			entity.setWaybillNo(msg.getAirWaybillNo());
			entity.setReceiveOrgCode(msg.getReceiveOrgCode());
			entity.setReceiveOrgName(msg.getReceiveOrgName());
			entity.setSendOrgCode(msg.getSendOrgCode());
			entity.setSendOrgName(msg.getSendOrgName());
			entity.setSendUserCode(msg.getSendUserCode());
			entity.setSendUserName(msg.getSendUserName());
			entityList.add(entity);
		}
		return entityList;
	}
	
	/**
	 * <p>站内消息的转换方法</p> 
	 * @author 232607 
	 * @date 2016-4-28 下午4:23:28
	 * @param instationMsgs
	 * @return
	 * @see
	 */
	private List<InstationMsgEntity> transformationInstationMsg(List<InstationMsg> instationMsgs) {
		List<InstationMsgEntity> entityList = new ArrayList<InstationMsgEntity>();
		InstationMsgEntity entity ;
		for(InstationMsg msg : instationMsgs){
			entity = new InstationMsgEntity();
			entity.setContext(msg.getContext());
			entity.setCreateType(msg.getCreateType());
			entity.setExpireTime(msg.getExpireTime());
			entity.setMsgType(msg.getMsgType());
			entity.setReceiveOrgCode(msg.getReceiveOrgCode());
			entity.setReceiveOrgName(msg.getReceiveOrgName());
			entity.setReceiveSubOrgCode(msg.getReceiveSubOrgCode());
			entity.setReceiveSubOrgName(msg.getReceiveSubOrgName());
			entity.setReceiveType(msg.getReceiveType());
			entity.setReceiveUserName(msg.getReceiveOrgName());
			entity.setReceiveUserCode(msg.getReceiveUserCode());
			entity.setSendOrgCode(msg.getSendOrgCode());
			entity.setSendOrgName(msg.getSendOrgName());
			entity.setSendUserCode(msg.getSendUserCode());
			entity.setSendUserName(msg.getSendUserName());
			entity.setSerialNumber(msg.getSerialNumber());
			entityList.add(entity);
		}
		return entityList;
	}

	/**
	 * <p>待办事项的转换方法</p> 
	 * @author 232607 
	 * @date 2016-4-29 下午4:26:43
	 * @param todoMsgs
	 * @return
	 * @see
	 */
	private List<ToDoMsgEntity> transformationTodoMsg(List<TodoMsg> todoMsgs) {
		List<ToDoMsgEntity> toDoMsgEntitys = new ArrayList<ToDoMsgEntity>();
		ToDoMsgEntity toDoMsgEntity;
		for(TodoMsg todoMsg : todoMsgs){
			toDoMsgEntity = new ToDoMsgEntity();
			toDoMsgEntity.setBusinessNo(todoMsg.getBusinessNo());
			toDoMsgEntity.setBusinessType(todoMsg.getBusinessType());
			toDoMsgEntity.setCreateUserCode(todoMsg.getCreateUserCode());
			toDoMsgEntity.setCreateUserName(todoMsg.getCreateUserName());
			toDoMsgEntity.setDealUrl(todoMsg.getDealUrl());
			toDoMsgEntity.setReceiveOrgCode(todoMsg.getReceiveOrgCode());
			toDoMsgEntity.setReceiveRoleCode(todoMsg.getReceiveRoleCode());
			toDoMsgEntity.setReceiveSubOrgCode(todoMsg.getReceiveSubOrgCode());
			toDoMsgEntity.setReceiveSubOrgName(todoMsg.getReceiveSubOrgName());
			toDoMsgEntity.setReceiveType(todoMsg.getReceiveType());
			toDoMsgEntity.setSerialNumber(todoMsg.getSerialNumber());
			toDoMsgEntity.setTitle(todoMsg.getTitle());
			toDoMsgEntity.setUrlType(todoMsg.getUrlType());
			toDoMsgEntity.setStatus(DictionaryValueConstants.TODOMSG__STATUS_TYPE__PROCESSING);
			toDoMsgEntitys.add(toDoMsgEntity);
		}
		return toDoMsgEntitys;
	}



}
