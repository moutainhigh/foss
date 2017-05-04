package com.deppon.foss.module.pickup.sign.api.shared.dto;


/**
 * 快递代理理外发XX天未签收自动上报OA丢货Dto
 * 
 * @ClassName: LdpNotSignReportOaDto
 * @author 200664-yangjinheng
 * @date 2014年9月4日 下午3:13:05
 */
public class LdpNotSignReportOaDto {

	/* TFR.T_OPT_LDP_EXTERNAL_BILL 的ID */
	private String id;
	/* 运单号 */
	private String wayBillId;
	/* 交接单号 */
	private String replayBill;
	/* 外发部门编号 */
	private String externalOrgCode;
	/* 外发部门名称 */
	private String externalOrgName;
	/* 外发员工号 */
	private String externalUserCode;
	/* 录入日期 */
	private String registerTime;
	/* 车牌号 */
	private String vehicleNo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWayBillId() {
		return wayBillId;
	}

	public void setWayBillId(String wayBillId) {
		this.wayBillId = wayBillId;
	}

	public String getReplayBill() {
		return replayBill;
	}

	public void setReplayBill(String replayBill) {
		this.replayBill = replayBill;
	}

	public String getExternalOrgCode() {
		return externalOrgCode;
	}

	public void setExternalOrgCode(String externalOrgCode) {
		this.externalOrgCode = externalOrgCode;
	}

	public String getExternalOrgName() {
		return externalOrgName;
	}

	public void setExternalOrgName(String externalOrgName) {
		this.externalOrgName = externalOrgName;
	}

	public String getExternalUserCode() {
		return externalUserCode;
	}

	public void setExternalUserCode(String externalUserCode) {
		this.externalUserCode = externalUserCode;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

}
