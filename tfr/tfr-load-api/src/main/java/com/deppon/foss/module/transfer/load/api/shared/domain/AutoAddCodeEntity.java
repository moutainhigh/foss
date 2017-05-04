package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 自动补码实体;运单补录完毕后保存进入此表.
 * @author 140022-foss-songjie
 * @Date 2015-05-13 09:22:38
 */
public class AutoAddCodeEntity extends BaseEntity{
	
	/** 
	* The Constant serialVersionUID. 
	*/
	private static final long serialVersionUID = 1L;
	
	/** 
	* ID 
	*/
	private String tbid;
	
	/** 
	* 运单号 
	*/
	private String waybillNO;
	
	/** 
	* 运单id 
	*/
	private String waybillID;
	
	/** 
	* 收货省份 
	*/
	private String receiveCustomerProvCode;
	
	/** 
	* 收货市 
	*/
	private String receiveCustomerCityCode;
	
	/** 
	* 收货区 
	*/
	private String receiveCustomerDistCode;
	
	/** 
	* 收货镇 
	*/
	private String receiveCustomerTownCode;
	
	/** 
	* 收货省份名称
	*/
	private String receiveCustomerProvName;
	
	/** 
	* 收货市名称
	*/
	private String receiveCustomerCityName;
	
	/** 
	* 收货区名称
	*/
	private String receiveCustomerDistName;
	
	/** 
	* 收货镇 
	*/
	private String receiveCustomerTownName;
	
	/** 
	* 收货具体地址 
	*/
	private String receiveCustomerAddress;
	
	/** 
	* 目的站 
	*/
	private String targetOrgCode;
	
	/** 
	* 提货网点编码 
	*/
	private String customerPickupOrgCode;
	
	/** 
	* 开单时间 
	*/
	private Date billTime;
	
	/** 
	* 开单组织 
	*/
	private String createOrgCode;
	
	/** 
	* 创建时间 
	*/
	private Date createTime;
	
	/**
	 * job id
	 */
	private String jobId;
	
	/** 
	* 补录的部门code 
	*/
	private String newTargetOrgCode;
	

	/**  
	 * jobId.  
	 *  
	 * @return  the jobId  
	 * @since   JDK 1.6  
	 */
	public String getJobId() {
		return jobId;
	}

	/**  
	 * jobId.  
	 *  
	 * @param   jobId    the jobId to set  
	 * @since   JDK 1.6  
	 */
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	/**  
	 * receiveCustomerProvName.  
	 *  
	 * @return  the receiveCustomerProvName  
	 * @since   JDK 1.6  
	 */
	public String getReceiveCustomerProvName() {
		return receiveCustomerProvName;
	}

	/**  
	 * receiveCustomerProvName.  
	 *  
	 * @param   receiveCustomerProvName    the receiveCustomerProvName to set  
	 * @since   JDK 1.6  
	 */
	public void setReceiveCustomerProvName(String receiveCustomerProvName) {
		this.receiveCustomerProvName = receiveCustomerProvName;
	}

	/**  
	 * receiveCustomerCityName.  
	 *  
	 * @return  the receiveCustomerCityName  
	 * @since   JDK 1.6  
	 */
	public String getReceiveCustomerCityName() {
		return receiveCustomerCityName;
	}

	/**  
	 * receiveCustomerCityName.  
	 *  
	 * @param   receiveCustomerCityName    the receiveCustomerCityName to set  
	 * @since   JDK 1.6  
	 */
	public void setReceiveCustomerCityName(String receiveCustomerCityName) {
		this.receiveCustomerCityName = receiveCustomerCityName;
	}

	/**  
	 * receiveCustomerDistName.  
	 *  
	 * @return  the receiveCustomerDistName  
	 * @since   JDK 1.6  
	 */
	public String getReceiveCustomerDistName() {
		return receiveCustomerDistName;
	}

	/**  
	 * receiveCustomerDistName.  
	 *  
	 * @param   receiveCustomerDistName    the receiveCustomerDistName to set  
	 * @since   JDK 1.6  
	 */
	public void setReceiveCustomerDistName(String receiveCustomerDistName) {
		this.receiveCustomerDistName = receiveCustomerDistName;
	}

	/**  
	 * receiveCustomerTownName.  
	 *  
	 * @return  the receiveCustomerTownName  
	 * @since   JDK 1.6  
	 */
	public String getReceiveCustomerTownName() {
		return receiveCustomerTownName;
	}

	/**  
	 * receiveCustomerTownName.  
	 *  
	 * @param   receiveCustomerTownName    the receiveCustomerTownName to set  
	 * @since   JDK 1.6  
	 */
	public void setReceiveCustomerTownName(String receiveCustomerTownName) {
		this.receiveCustomerTownName = receiveCustomerTownName;
	}
	
	
	public String getTbid() {
		return tbid;
	}

	public void setTbid(String tbid) {
		this.tbid = tbid;
	}

	/**
	* Get() 运单号.
	*
	* @return 运单号.
	*/
	public String getWaybillNO() {
		return waybillNO;
	}

	/**
	* Set() 运单号.
	*
	* @param 运单号.
	*/
	public void setWaybillNO(String waybillNO) {
		this.waybillNO = waybillNO;
	}
	
	/**
	* Get() 运单id.
	*
	* @return 运单id.
	*/
	public String getWaybillID() {
		return waybillID;
	}

	/**
	* Set() 运单id.
	*
	* @param 运单id.
	*/
	public void setWaybillID(String waybillID) {
		this.waybillID = waybillID;
	}
	
	/**
	* Get() 收货省份.
	*
	* @return 收货省份.
	*/
	public String getReceiveCustomerProvCode() {
		return receiveCustomerProvCode;
	}

	/**
	* Set() 收货省份.
	*
	* @param 收货省份.
	*/
	public void setReceiveCustomerProvCode(String receiveCustomerProvCode) {
		this.receiveCustomerProvCode = receiveCustomerProvCode;
	}
	
	/**
	* Get() 收货市.
	*
	* @return 收货市.
	*/
	public String getReceiveCustomerCityCode() {
		return receiveCustomerCityCode;
	}

	/**
	* Set() 收货市.
	*
	* @param 收货市.
	*/
	public void setReceiveCustomerCityCode(String receiveCustomerCityCode) {
		this.receiveCustomerCityCode = receiveCustomerCityCode;
	}
	
	/**
	* Get() 收货区.
	*
	* @return 收货区.
	*/
	public String getReceiveCustomerDistCode() {
		return receiveCustomerDistCode;
	}

	/**
	* Set() 收货区.
	*
	* @param 收货区.
	*/
	public void setReceiveCustomerDistCode(String receiveCustomerDistCode) {
		this.receiveCustomerDistCode = receiveCustomerDistCode;
	}
	
	/**
	* Get() 收货镇.
	*
	* @return 收货镇.
	*/
	public String getReceiveCustomerTownCode() {
		return receiveCustomerTownCode;
	}

	/**
	* Set() 收货镇.
	*
	* @param 收货镇.
	*/
	public void setReceiveCustomerTownCode(String receiveCustomerTownCode) {
		this.receiveCustomerTownCode = receiveCustomerTownCode;
	}
	
	/**
	* Get() 收货具体地址.
	*
	* @return 收货具体地址.
	*/
	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}

	/**
	* Set() 收货具体地址.
	*
	* @param 收货具体地址.
	*/
	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}
	
	/**
	* Get() 目的站.
	*
	* @return 目的站.
	*/
	public String getTargetOrgCode() {
		return targetOrgCode;
	}

	/**
	* Set() 目的站.
	*
	* @param 目的站.
	*/
	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}
	
	/**
	* Get() 提货网点编码.
	*
	* @return 提货网点编码.
	*/
	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}

	/**
	* Set() 提货网点编码.
	*
	* @param 提货网点编码.
	*/
	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}
	
	/**
	* Get() 开单时间.
	*
	* @return 开单时间.
	*/
	public Date getBillTime() {
		return billTime;
	}

	/**
	* Set() 开单时间.
	*
	* @param 开单时间.
	*/
	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}
	
	/**
	* Get() 开单组织.
	*
	* @return 开单组织.
	*/
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	* Set() 开单组织.
	*
	* @param 开单组织.
	*/
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}
	
	/**
	* Get() 创建时间.
	*
	* @return 创建时间.
	*/
	public Date getCreateTime() {
		return createTime;
	}

	/**
	* Set() 创建时间.
	*
	* @param 创建时间.
	*/
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getNewTargetOrgCode() {
		return newTargetOrgCode;
	}

	public void setNewTargetOrgCode(String newTargetOrgCode) {
		this.newTargetOrgCode = newTargetOrgCode;
	}
	
	
}
