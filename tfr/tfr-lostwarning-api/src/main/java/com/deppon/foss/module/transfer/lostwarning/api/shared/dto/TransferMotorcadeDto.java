package com.deppon.foss.module.transfer.lostwarning.api.shared.dto;

/**
 * 外场车队信息
 * 
 * 项目名称：tfr-lostwarning-api
 * 
 * 类名称：TransferMotorcadeDto
 * 
 * 创建人：lyz 263072
 * 
 * 创建时间：2015-7-14 下午5:23:49
 * 
 * 版权所有：上海德邦物流有限公司
 */
public class TransferMotorcadeDto {
	
	//部门编码
	private String orgCode;
	//部门名称
	private String orgName;
	//服务车队编码
	private String motorcadeCode;
	//服务车队名称
	private String motorcadeName;
	//外场业务渠道
	private String transferServiceChannel;
	
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getMotorcadeCode() {
		return motorcadeCode;
	}
	public void setMotorcadeCode(String motorcadeCode) {
		this.motorcadeCode = motorcadeCode;
	}
	public String getMotorcadeName() {
		return motorcadeName;
	}
	public void setMotorcadeName(String motorcadeName) {
		this.motorcadeName = motorcadeName;
	}
	public String getTransferServiceChannel() {
		return transferServiceChannel;
	}
	public void setTransferServiceChannel(String transferServiceChannel) {
		this.transferServiceChannel = transferServiceChannel;
	}
}
