package com.deppon.pda.bdm.module.core.shared.domain;


/**
 * 
  * @ClassName VehicleEntity 快递车牌信息
  * @Description TODO 
  * @author mt hyssmt@vip.qq.com
  * @date 2013-9-7 下午2:56:16
 */
public class VehicleEntity extends DomainEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 快递车牌号
	 */
	String vehicleNo;
	
	/**
	 * 快递员工号
	 */
	String empCode;
	
	/**
	 * 快递员手机号
	 */
	String mobilePhone;
	
	/**
	 * 部门编号
	 */
	String orgCode;

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
}
