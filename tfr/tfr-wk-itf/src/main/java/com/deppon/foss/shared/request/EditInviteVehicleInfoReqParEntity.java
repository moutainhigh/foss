package com.deppon.foss.shared.request;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
* @description 修改约车信息请求参数(悟空请求参数实体)
* @version 1.0
* @author 332209-foss-ruilibao
* @update 2016-5-6 下午2:09:33
 */
@XmlRootElement(name="EditInviteVehicleInfoReqParEntity")
public class EditInviteVehicleInfoReqParEntity implements Serializable{
	
	// 序列化ID
	private static final long serialVersionUID = 7354429438336724231L;

	// 车牌号
	private String vehicleNo;
	
	// 约车编号
	private String aboutVehicleNo;
	
	// 使用状态
	private String useStatus;

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	
	public String getAboutVehicleNo() {
		return aboutVehicleNo;
	}

	public void setAboutVehicleNo(String aboutVehicleNo) {
		this.aboutVehicleNo = aboutVehicleNo;
	}

	public String getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(String useStatus) {
		this.useStatus = useStatus;
	}
	
}
