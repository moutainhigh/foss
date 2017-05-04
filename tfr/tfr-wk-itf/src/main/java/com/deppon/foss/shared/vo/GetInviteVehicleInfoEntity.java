package com.deppon.foss.shared.vo;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlRootElement;

/**
* @description 获取约车信息返回实体（返回给悟空系统用）
* @version 1.0
* @author 332209-foss-ruilibao
* @update 2016-5-6 下午2:02:42
 */
@XmlRootElement(name="GetInviteVehicleInfoEntity")
public class GetInviteVehicleInfoEntity {

	// 序列化ID
	private static final long serialVersionUID = 1L;

	// 请车价格
    private BigDecimal totalFee;
    
    // 约车编号
    private String aboutVehicleNo;

	// 车牌号
    private String vehicleNo;
    
    // 约车部门编码
    private String aboutDeptCode;
    
    // 约车部门名称
    private String aboutDeptName;

	public BigDecimal getTotalFee() {
		return totalFee;
	}
	
	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public String getAboutVehicleNo() {
		return aboutVehicleNo;
	}

	public void setAboutVehicleNo(String aboutVehicleNo) {
		this.aboutVehicleNo = aboutVehicleNo;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getAboutDeptCode() {
		return aboutDeptCode;
	}

	public void setAboutDeptCode(String aboutDeptCode) {
		this.aboutDeptCode = aboutDeptCode;
	}

	public String getAboutDeptName() {
		return aboutDeptName;
	}

	public void setAboutDeptName(String aboutDeptName) {
		this.aboutDeptName = aboutDeptName;
	}
}