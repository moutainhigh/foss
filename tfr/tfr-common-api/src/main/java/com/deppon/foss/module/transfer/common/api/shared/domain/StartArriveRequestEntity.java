package com.deppon.foss.module.transfer.common.api.shared.domain;


import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * 类描述：	同步FOSS车辆发车到达指令入参实体
 * 创建人：	221464-ZOUYONG
 * 创建时间：	2014-12-23 下午1:32:55
 *
 */
@XmlRootElement(name="StartArriveInstructionRequestEntity")
public class StartArriveRequestEntity {
	
	// 操作类型
	private String operateType;
	
	// 约车编码
	private String fossAboutCode;
	
	// 车辆配载信息
	private List<StowageBillEntity> stowageBillList;

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getFossAboutCode() {
		return fossAboutCode;
	}

	public void setFossAboutCode(String fossAboutCode) {
		this.fossAboutCode = fossAboutCode;
	}

	public List<StowageBillEntity> getStowageBillList() {
		return stowageBillList;
	}

	public void setStowageBillList(List<StowageBillEntity> stowageBillList) {
		this.stowageBillList = stowageBillList;
	}
	
}
