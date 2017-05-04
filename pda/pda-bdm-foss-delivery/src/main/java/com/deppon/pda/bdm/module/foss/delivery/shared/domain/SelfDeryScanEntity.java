package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.util.List;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

/**
 * 
 * @ClassName SelfDeryScanEntity
 * @Description 客户自提 请求实体
 * @author xujun cometzb@126.com
 * @date 2012-12-26
 */
public class SelfDeryScanEntity extends ScanMsgEntity {

	private static final long serialVersionUID = 6435992876503930728L;
	//异常原因
	private String excpReason;
	//到达联编号
	private String arrInfoCode;
	//签收人
	private String signPerson;
	//签收状态
	private String signStatus;
	//流水号
	private List<String> labelCodes;

	public String getExcpReason() {
		return excpReason;
	}

	public void setExcpReason(String excpReason) {
		this.excpReason = excpReason;
	}

	public String getArrInfoCode() {
		return arrInfoCode;
	}

	public void setArrInfoCode(String arrInfoCode) {
		this.arrInfoCode = arrInfoCode;
	}

	public String getSignPerson() {
		return signPerson;
	}

	public void setSignPerson(String signPerson) {
		this.signPerson = signPerson;
	}

	public String getSignStatus() {
		return signStatus;
	}

	public void setSignStatus(String signStatus) {
		this.signStatus = signStatus;
	}

	public List<String> getLabelCodes() {
		return labelCodes;
	}

	public void setLabelCodes(List<String> labelCodes) {
		this.labelCodes = labelCodes;
	}

}
