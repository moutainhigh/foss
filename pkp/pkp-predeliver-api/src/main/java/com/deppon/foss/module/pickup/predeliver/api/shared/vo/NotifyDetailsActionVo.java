package com.deppon.foss.module.pickup.predeliver.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyDetailsConditonDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyDetailsDto;

/**
 * @功能 通知客户明细Vo
 * @author Foss-105888-Zhangxingwang
 * @date 2013-12-27 9:22:18
 *
 */
public class NotifyDetailsActionVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//通知客户明细查询条件Dto
	private NotifyDetailsConditonDto notifyDetailsConditonDto;
	//通知客户明细查询结果Dto
	private List<NotifyDetailsDto> notifyDetailsDtoList;

	public NotifyDetailsConditonDto getNotifyDetailsConditonDto() {
		return notifyDetailsConditonDto;
	}

	public void setNotifyDetailsConditonDto(
			NotifyDetailsConditonDto notifyDetailsConditonDto) {
		this.notifyDetailsConditonDto = notifyDetailsConditonDto;
	}

	public List<NotifyDetailsDto> getNotifyDetailsDtoList() {
		return notifyDetailsDtoList;
	}

	public void setNotifyDetailsDtoList(List<NotifyDetailsDto> notifyDetailsDtoList) {
		this.notifyDetailsDtoList = notifyDetailsDtoList;
	}
}
