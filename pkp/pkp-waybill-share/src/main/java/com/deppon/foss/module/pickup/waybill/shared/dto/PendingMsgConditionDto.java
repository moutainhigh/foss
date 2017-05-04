package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PendingMsgConditionDto implements Serializable {
	private static final long serialVersionUID = 1L;

	//运单
	private String waybillNo;
	
	//起草变更部门
	private String darftOrgName;
	
	//起草变更部门
		private String darftOrgCode;
	
	//变更申请人
	private String darfter;
	
	//更改受理时间
	private Date pendOperateTimeBegin;

	//更改受理时间
	private Date pendOperateTimeEnd;
	
	//更改单状态
	private String rfcStatus;
	
	private List<String> waybillNoList;
	
	private String keyWord;
	public List<String> getWaybillNoList() {
		return waybillNoList;
	}
	public void setWaybillNoList(List<String> waybillNoList) {
		this.waybillNoList = waybillNoList;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getDarftOrgName() {
		return darftOrgName;
	}

	public void setDarftOrgName(String darftOrgName) {
		this.darftOrgName = darftOrgName;
	}

	public String getDarfter() {
		return darfter;
	}

	public String getDarftOrgCode() {
		return darftOrgCode;
	}

	public void setDarftOrgCode(String darftOrgCode) {
		this.darftOrgCode = darftOrgCode;
	}

	public void setDarfter(String darfter) {
		this.darfter = darfter;
	}

	public Date getPendOperateTimeBegin() {
		return pendOperateTimeBegin;
	}

	public void setPendOperateTimeBegin(Date pendOperateTimeBegin) {
		this.pendOperateTimeBegin = pendOperateTimeBegin;
	}

	public Date getPendOperateTimeEnd() {
		return pendOperateTimeEnd;
	}

	public void setPendOperateTimeEnd(Date pendOperateTimeEnd) {
		this.pendOperateTimeEnd = pendOperateTimeEnd;
	}

	public String getRfcStatus() {
		return rfcStatus;
	}

	public void setRfcStatus(String rfcStatus) {
		this.rfcStatus = rfcStatus;
	}	
}
