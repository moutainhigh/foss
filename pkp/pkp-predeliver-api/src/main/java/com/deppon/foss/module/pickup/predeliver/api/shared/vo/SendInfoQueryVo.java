package com.deppon.foss.module.pickup.predeliver.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SendHandoverInfoDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SendInfoSearchDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SendingWayBillInfoResponse;

public class SendInfoQueryVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private SendInfoSearchDto sendInfoSearchDto;
	
	private List<SendHandoverInfoDto> sendHandoverInfoDtoList;

	private List<SendingWayBillInfoResponse> billInfoResponses;
	
	public SendInfoSearchDto getSendInfoSearchDto() {
		return sendInfoSearchDto;
	}

	public void setSendInfoSearchDto(SendInfoSearchDto sendInfoSearchDto) {
		this.sendInfoSearchDto = sendInfoSearchDto;
	}

	public List<SendHandoverInfoDto> getSendHandoverInfoDtoList() {
		return sendHandoverInfoDtoList;
	}

	public void setSendHandoverInfoDtoList(
			List<SendHandoverInfoDto> sendHandoverInfoDtoList) {
		this.sendHandoverInfoDtoList = sendHandoverInfoDtoList;
	}

	public List<SendingWayBillInfoResponse> getBillInfoResponses() {
		return billInfoResponses;
	}

	public void setBillInfoResponses(
			List<SendingWayBillInfoResponse> billInfoResponses) {
		this.billInfoResponses = billInfoResponses;
	}
	
}
