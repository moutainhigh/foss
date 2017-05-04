/**
 *  initial comments.
 */
package com.deppon.foss.module.transfer.common.api.shared.dto;

public class AwbBeanUtils {
	
	/**运单基本信息*/
	private AwbBasicDto awbBasicDto;
	
	/**制单费用信息*/
	private AwbChargeDto awbChargeDto;

	public AwbBasicDto getAwbBasicDto() {
		return awbBasicDto;
	}

	public void setAwbBasicDto(AwbBasicDto awbBasicDto) {
		this.awbBasicDto = awbBasicDto;
	}

	public AwbChargeDto getAwbChargeDto() {
		return awbChargeDto;
	}

	public void setAwbChargeDto(AwbChargeDto awbChargeDto) {
		this.awbChargeDto = awbChargeDto;
	}
	

}