package com.deppon.foss.module.pickup.waybill.shared.vo;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.dto.ExceptMsgActionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExceptMsgConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabelGoodTodoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PendingMsgActionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PendingMsgConditionDto;

public class ExceptMsgActionVo {
	//查询待办异常信息
	private ExceptMsgConditionDto exceptMsgConditionDto;
	
	//查询待办详细信息
	private List<ExceptMsgActionDto> exceptMsgActionDtoList;
	
	//查询未生成待办条件
	private PendingMsgConditionDto pendingMsgConditionDto;
	
	//查询未生成待办详细信息
	private List<PendingMsgActionDto> pendingMsgActionDtoList;
	
	//查询所有代办异常数据详细信息
	private List<LabelGoodTodoDto> labelGoodTodoList;
	
	//查询未生成代办异常的条件Dto
	private List<String> pendTodoIdList;
	
	//更改单List
	private List<String> waybillRfcIdList;

	public List<String> getWaybillRfcIdList() {
		return waybillRfcIdList;
	}

	public void setWaybillRfcIdList(List<String> waybillRfcIdList) {
		this.waybillRfcIdList = waybillRfcIdList;
	}

	public List<String> getPendTodoIdList() {
		return pendTodoIdList;
	}

	public void setPendTodoIdList(List<String> pendTodoIdList) {
		this.pendTodoIdList = pendTodoIdList;
	}

	public ExceptMsgConditionDto getExceptMsgConditionDto() {
		return exceptMsgConditionDto;
	}

	public void setExceptMsgConditionDto(ExceptMsgConditionDto exceptMsgConditionDto) {
		this.exceptMsgConditionDto = exceptMsgConditionDto;
	}

	public List<ExceptMsgActionDto> getExceptMsgActionDtoList() {
		return exceptMsgActionDtoList;
	}

	public void setExceptMsgActionDtoList(
			List<ExceptMsgActionDto> exceptMsgActionDtoList) {
		this.exceptMsgActionDtoList = exceptMsgActionDtoList;
	}

	public PendingMsgConditionDto getPendingMsgConditionDto() {
		return pendingMsgConditionDto;
	}

	public void setPendingMsgConditionDto(
			PendingMsgConditionDto pendingMsgConditionDto) {
		this.pendingMsgConditionDto = pendingMsgConditionDto;
	}

	public List<PendingMsgActionDto> getPendingMsgActionDtoList() {
		return pendingMsgActionDtoList;
	}

	public void setPendingMsgActionDtoList(
			List<PendingMsgActionDto> pendingMsgActionDtoList) {
		this.pendingMsgActionDtoList = pendingMsgActionDtoList;
	}

	public List<LabelGoodTodoDto> getLabelGoodTodoList() {
		return labelGoodTodoList;
	}

	public void setLabelGoodTodoList(List<LabelGoodTodoDto> labelGoodTodoList) {
		this.labelGoodTodoList = labelGoodTodoList;
	}	
}
