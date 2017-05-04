package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadBillDto;

public class PdaUnloadTaskDto implements Serializable {

	private static final long serialVersionUID = -554194748614538461L;

	private String unloadTaskId;

	private String unloadTaskNo;

	private String unloadType;

	private String unloadOrgCode;

	private List<UnloadBillDto> unloadBillList;

	public String getUnloadTaskId() {
		return unloadTaskId;
	}

	public void setUnloadTaskId(String unloadTaskId) {
		this.unloadTaskId = unloadTaskId;
	}

	public String getUnloadTaskNo() {
		return unloadTaskNo;
	}

	public void setUnloadTaskNo(String unloadTaskNo) {
		this.unloadTaskNo = unloadTaskNo;
	}

	public String getUnloadType() {
		return unloadType;
	}

	public void setUnloadType(String unloadType) {
		this.unloadType = unloadType;
	}

	public String getUnloadOrgCode() {
		return unloadOrgCode;
	}

	public void setUnloadOrgCode(String unloadOrgCode) {
		this.unloadOrgCode = unloadOrgCode;
	}

	public List<UnloadBillDto> getUnloadBillList() {
		return unloadBillList;
	}

	public void setUnloadBillList(List<UnloadBillDto> unloadBillList) {
		this.unloadBillList = unloadBillList;
	}

	@Override
	public String toString() {
		return "PdaUnloadTaskDto [unloadTaskId=" + unloadTaskId + ", unloadTaskNo=" + unloadTaskNo + ", unloadType="
				+ unloadType + ", unloadOrgCode=" + unloadOrgCode + ", unloadBillList=" + unloadBillList + "]";
	}

}
