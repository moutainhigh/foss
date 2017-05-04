package com.deppon.foss.shared.request;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
* @description 获取约车信息传入参数实体(悟空系统请求参数）
* @version 1.0
* @author 332209-foss-ruilibao
* @update 2016-5-6 下午2:10:33
 */
@XmlRootElement(name="GetInviteVehicleInfoReqParEntity")
public class GetInviteVehicleInfoReqParEntity implements Serializable{

	// 序列化ID
	private static final long serialVersionUID = 7354429438336724231L;

	// 车牌号
	private String vehicleNo;
	
	// 用车部门
	private String origOrgCode;
	

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getOrigOrgCode() {
		return origOrgCode;
	}

	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}
}
