package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

/**
 * 
  * @ClassName PcVote 
  * @Description 票件关系 
  * @author xujun cometzb@126.com 
  * @date 2012-12-26
 */
public class PcVoteEntity {
	//运单号
	private String wblCode;
	//流水号
	private String labelCode;
	//备注
	private String remark;
	public String getWblCode() {
		return wblCode;
	}
	public void setWblCode(String wblCode) {
		this.wblCode = wblCode;
	}
	public String getLabelCode() {
		return labelCode;
	}
	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	
}
