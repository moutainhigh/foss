/**
 * 
 */
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 补码-更新运单提货网点等信息 dto
 * @author ibm-foss-sxw
 *
 */
public class WaybillExpressDto implements Serializable{

	/**
     * 序列化对象ID
     */
	private static final long serialVersionUID = 1L;
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 补码时间
	 */
	private Date addCodeTime;
	
	/**
	 * 新的客户提货网点
	 */
	private String customerPickupOrgCode;

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public Date getAddCodeTime() {
		return addCodeTime;
	}

	public void setAddCodeTime(Date addCodeTime) {
		this.addCodeTime = addCodeTime;
	}

	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}

	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}
	
	
	
}
