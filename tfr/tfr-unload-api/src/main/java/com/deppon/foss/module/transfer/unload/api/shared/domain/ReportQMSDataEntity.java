package com.deppon.foss.module.transfer.unload.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

public class ReportQMSDataEntity extends BaseEntity {
	private String oaReportClearlessmsg;
	private String loadOrgmsg;
	private String unloadEntitymsg;
	private String lostWarningId;
					
	public String getLostWarningId() {
		return lostWarningId;
	}
	public void setLostWarningId(String lostWarningId) {
		this.lostWarningId = lostWarningId;
	}
	public String getOaReportClearlessmsg() {
		return oaReportClearlessmsg;
	}
	public void setOaReportClearlessmsg(String oaReportClearlessmsg) {
		this.oaReportClearlessmsg = oaReportClearlessmsg;
	}
	public String getLoadOrgmsg() {
		return loadOrgmsg;
	}
	public void setLoadOrgmsg(String loadOrgmsg) {
		this.loadOrgmsg = loadOrgmsg;
	}
	public String getUnloadEntitymsg() {
		return unloadEntitymsg;
	}
	public void setUnloadEntitymsg(String unloadEntitymsg) {
		this.unloadEntitymsg = unloadEntitymsg;
	}
	
	
	
	
	

}
