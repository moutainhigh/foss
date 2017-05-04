package com.deppon.foss.module.base.common.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.common.api.shared.domain.MsgOnlineEntity;
import com.deppon.foss.module.base.common.api.shared.dto.MsgOnlineDto;

public class MsgOnlineVo implements Serializable {

	/**
	 * 在线消息vo
	 * @author 130346-foss-lifanghong
	 * @date 2013-07-08 
	 */
	private static final long serialVersionUID = -2210352056324155952L;
	/**
	 * 在线通知DTO
	 */
	private MsgOnlineDto msgOnlineDto;
	private List<MsgOnlineDto> msgOnlineDtos;
	/**
	 * 在线通知实体类
	 */
	private MsgOnlineEntity msgOnlineEntity;
	private List<MsgOnlineEntity> msgOnlineEntitys;
	/**
	 * 修改集合
	 */
	private List<MsgOnlineEntity> msgOnlineEntityList;
	
	
	public List<MsgOnlineEntity> getMsgOnlineEntityList() {
		return msgOnlineEntityList;
	}
	public void setMsgOnlineEntityList(List<MsgOnlineEntity> msgOnlineEntityList) {
		this.msgOnlineEntityList = msgOnlineEntityList;
	}
	public MsgOnlineEntity getMsgOnlineEntity() {
		return msgOnlineEntity;
	}
	public void setMsgOnlineEntity(MsgOnlineEntity msgOnlineEntity) {
		this.msgOnlineEntity = msgOnlineEntity;
	}
	public List<MsgOnlineEntity> getMsgOnlineEntitys() {
		return msgOnlineEntitys;
	}
	public void setMsgOnlineEntitys(List<MsgOnlineEntity> msgOnlineEntitys) {
		this.msgOnlineEntitys = msgOnlineEntitys;
	}
	public MsgOnlineDto getMsgOnlineDto() {
		return msgOnlineDto;
	}
	public void setMsgOnlineDto(MsgOnlineDto msgOnlineDto) {
		this.msgOnlineDto = msgOnlineDto;
	}
	public List<MsgOnlineDto> getMsgOnlineDtos() {
		return msgOnlineDtos;
	}
	public void setMsgOnlineDtos(List<MsgOnlineDto> msgOnlineDtos) {
		this.msgOnlineDtos = msgOnlineDtos;
	}
	
	
}
