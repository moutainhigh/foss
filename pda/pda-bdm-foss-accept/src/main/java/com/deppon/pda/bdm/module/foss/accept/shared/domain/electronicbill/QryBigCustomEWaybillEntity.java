package com.deppon.pda.bdm.module.foss.accept.shared.domain.electronicbill;

import java.io.Serializable;

/**   
 * @ClassName QryBigCustomEntity  
 * @Description 查询大客户实体   
 * @author  092038 张贞献  
 * @date 2014-7-10    
 */ 
public class QryBigCustomEWaybillEntity implements Serializable{
   
	/**  
	 * @Fields serialVersionUID : TODO 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 客户编码
	 */
	private String customCode;
	/**
	 * 车牌号
	 */
	private String truckCode;
	
	/**
	 * 联系人地址
	 */
    private String address;

	/**
	 * 联系人电话
	 */
    private String mobilePhone;
  
	
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getTruckCode() {
		return truckCode;
	}

	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}

	public String getCustomCode() {
		return customCode;
	}

	public void setCustomCode(String customCode) {
		this.customCode = customCode;
	}
	

	
}
