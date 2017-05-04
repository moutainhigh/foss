package com.deppon.foss.module.transfer.platform.api.shared.dto;

/**
 * @author 042770 外场集中接送货重量、体积配置
 */
public class CentralizePickupConifg {

	private String orgCode;

	private String value;

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "CentralizePickupConifg [orgCode=" + orgCode + ", value="
				+ value + "]";
	}

}
