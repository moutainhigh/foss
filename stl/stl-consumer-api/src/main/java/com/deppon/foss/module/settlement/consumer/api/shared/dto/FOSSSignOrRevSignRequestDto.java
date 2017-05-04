package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.dto.LineSignDto;

/**
 * FOSS新调用CUBC签收反签收服务的适配实体
 * @author 353654
 *
 */
public class FOSSSignOrRevSignRequestDto implements Serializable{
	//序列化版本号
	private static final long serialVersionUID = 6281135135234429519L;
			
	//签收确认收入服务使用DTO
	private LineSignDto lineSignDto;
			
	//系统当前信息
	private CurrentInfo currentInfo;
	
	//异常签收
	private String expSignType;
	
	//签收情况
	private String signSituation;
			
	public String getSignSituation() {
		return signSituation;
	}

	public void setSignSituation(String signSituation) {
		this.signSituation = signSituation;
	}

	public LineSignDto getLineSignDto() {
		return lineSignDto;
	}

	public void setLineSignDto(LineSignDto lineSignDto) {
		this.lineSignDto = lineSignDto;
	}

	public CurrentInfo getCurrentInfo() {
		return currentInfo;
	}

	public void setCurrentInfo(CurrentInfo currentInfo) {
		this.currentInfo = currentInfo;
	}

	public String getExpSignType() {
		return expSignType;
	}

	public void setExpSignType(String expSignType) {
		this.expSignType = expSignType;
	}
	
	
}
