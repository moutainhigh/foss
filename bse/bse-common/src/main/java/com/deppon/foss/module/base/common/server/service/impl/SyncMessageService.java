package com.deppon.foss.module.base.common.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.deppon.esb.inteface.domain.msg.SyncMsgResponseBody;
import com.deppon.foss.module.base.common.api.server.service.IMessageService;
import com.deppon.foss.module.base.common.api.server.service.IOnLineMsgService;
import com.deppon.foss.module.base.common.api.server.service.ISyncMessageService;
import com.deppon.foss.module.base.common.api.server.service.IToDoMsgService;
import com.deppon.foss.module.base.common.api.shared.domain.InstationMsgEntity;
import com.deppon.foss.module.base.common.api.shared.domain.MsgOnlineEntity;
import com.deppon.foss.module.base.common.api.shared.domain.ToDoMsgEntity;
import com.deppon.foss.module.base.common.api.shared.domain.ToDoMsgResult;
import com.deppon.foss.module.base.common.api.shared.exception.MessageException;
import com.deppon.foss.module.base.common.api.shared.exception.OnlineMessageException;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.CollectionUtils;

public class SyncMessageService implements ISyncMessageService {

	/**
	 * 在线通知
	 */
	private IOnLineMsgService onLineMsgService;
	
	/**
	 * 代办
	 */
	private IToDoMsgService toDoMsgService;
	
	/**
	 * 站内消息
	 */
	private IMessageService messageService;
	
	public void setOnLineMsgService(IOnLineMsgService onLineMsgService) {
		this.onLineMsgService = onLineMsgService;
	}

	public void setToDoMsgService(IToDoMsgService toDoMsgService) {
		this.toDoMsgService = toDoMsgService;
	}

	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	@Override
	public SyncMsgResponseBody syncMessage(List<?> entitys) {
		// TODO Auto-generated method stub
		SyncMsgResponseBody body = new SyncMsgResponseBody();
		List<ToDoMsgEntity> entityList = new ArrayList<ToDoMsgEntity>();
		for(int i = 0;i < entitys.size(); i++){
			if(entitys.get(i) instanceof MsgOnlineEntity){
				try {
					onLineMsgService.addOnlineMsgByECS((MsgOnlineEntity)entitys.get(i));
				} catch (OnlineMessageException e) {
					body.setStatus("1");
					body.setMsg("在线通知插入异常："+e.getMessage());
				}
			} else if(entitys.get(i) instanceof ToDoMsgEntity){
				entityList.add((ToDoMsgEntity)entitys.get(i));
			} else if(entitys.get(i) instanceof InstationMsgEntity){
				try {
					InstationMsgEntity entity = (InstationMsgEntity)entitys.get(i);
					entity.setCreateType(DictionaryValueConstants.MSG_CREAT_TYPE_ECS);
					messageService.saveInstationMsgByECS(entity);
				} catch (MessageException e) {
					// TODO: handle exception
					body.setStatus("1");
					body.setMsg("站内消息插入异常："+e.getMessage());
				}
			}
		}
		if(!CollectionUtils.isEmpty(entityList)){
			List<ToDoMsgResult> toDoMsgResults = toDoMsgService.createIncrementToDoMsg(entityList);
			for(ToDoMsgResult result : toDoMsgResults){
				if(!result.getIsSuccess()){
					body.setStatus("1");
					body.setMsg("代办事项插入异常："+result.getErrorMsg());
					break;
				}
			}
		}
		return body;
	}

}
