package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 自动补码状态实体类(自动补码处理状态对象)
 * @author 140022-foss-songjie
 * @Date 2015-05-13 10:56:44
 */
public class AutoAddCodeLogEntity implements Serializable {

	
	
	/**
	* @fields serialVersionUID
	* @author 14022-foss-songjie
	* @update 2015年7月2日 上午9:28:52
	* @version V1.0
	*/
	
	private static final long serialVersionUID = 1L;

	/** 
	* id 
	*/
	private String id;
	
	/** 
	* 运单号
	*/
	private String waybillNo;
	
	
	
	/**运单号 作为查询时使用
	* @fields waybillNum
	* @author 14022-foss-songjie
	* @update 2015年7月2日 上午10:11:28
	* @version V1.0
	*/
	private String waybillNum;
	
	/** 
	* 补码结果
	*/
	private String result;
	
	/** 
	* 返回值 
	*/
	private String exceptionInfo;
	private String receiveCustomerProvCode;
	private String receiveCustomerCityCode;
	private String receiveCustomerDistCode;
	private String receiveCustomerTownCode;
	private String receiveCustomerAddress;
	
	
	/** 
	* 补录的部门code 
	*/
	private String newTargetOrgCode;
	
	private Date gisStartTime;
	private Date gisEndTime;
	private Date autoStartTime;
	private Date autoEndTime;
	private String jobid;

	
	
	
	public Date getGisStartTime() {
		return gisStartTime;
	}

	public void setGisStartTime(Date gisStartTime) {
		this.gisStartTime = gisStartTime;
	}

	public Date getGisEndTime() {
		return gisEndTime;
	}

	public void setGisEndTime(Date gisEndTime) {
		this.gisEndTime = gisEndTime;
	}

	public Date getAutoStartTime() {
		return autoStartTime;
	}

	public void setAutoStartTime(Date autoStartTime) {
		this.autoStartTime = autoStartTime;
	}

	public Date getAutoEndTime() {
		return autoEndTime;
	}

	public void setAutoEndTime(Date autoEndTime) {
		this.autoEndTime = autoEndTime;
	}

	public String getJobid() {
		return jobid;
	}

	public void setJobid(String jobid) {
		this.jobid = jobid;
	}

	/**  
	 * waybillNo.  
	 *  
	 * @return  the waybillNo  
	 * @since   JDK 1.6  
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**  
	 * waybillNo.  
	 *  
	 * @param   waybillNo    the waybillNo to set  
	 * @since   JDK 1.6  
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	
	
	public String getWaybillNum() {
		return waybillNum;
	}

	public void setWaybillNum(String waybillNum) {
		this.waybillNum = waybillNum;
	}

	/**
	* Get() id.
	*
	* @return id.
	*/
	public String getId() {
		return id;
	}

	/**
	* Set() id.
	*
	* @param id.
	*/
	public void setId(String id) {
		this.id = id;
	}
	
	/**  
	 * result.  
	 *  
	 * @return  the result  
	 * @since   JDK 1.6  
	 */
	public String getResult() {
		return result;
	}

	/**  
	 * result.  
	 *  
	 * @param   result    the result to set  
	 * @since   JDK 1.6  
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**  
	 * exceptionInfo.  
	 *  
	 * @return  the exceptionInfo  
	 * @since   JDK 1.6  
	 */
	public String getExceptionInfo() {
		return exceptionInfo;
	}

	/**  
	 * exceptionInfo.  
	 *  
	 * @param   exceptionInfo    the exceptionInfo to set  
	 * @since   JDK 1.6  
	 */
	public void setExceptionInfo(String exceptionInfo) {
		this.exceptionInfo = exceptionInfo;
	}

	/**
	* Get() 补录的部门code.
	*
	* @return 补录的部门code.
	*/
	public String getNewTargetOrgCode() {
		return newTargetOrgCode;
	}

	/**
	* Set() 补录的部门code.
	*
	* @param 补录的部门code.
	*/
	public void setNewTargetOrgCode(String newTargetOrgCode) {
		this.newTargetOrgCode = newTargetOrgCode;
	}

	public String getReceiveCustomerProvCode() {
		return receiveCustomerProvCode;
	}

	public void setReceiveCustomerProvCode(String receiveCustomerProvCode) {
		this.receiveCustomerProvCode = receiveCustomerProvCode;
	}

	public String getReceiveCustomerCityCode() {
		return receiveCustomerCityCode;
	}

	public void setReceiveCustomerCityCode(String receiveCustomerCityCode) {
		this.receiveCustomerCityCode = receiveCustomerCityCode;
	}

	public String getReceiveCustomerDistCode() {
		return receiveCustomerDistCode;
	}

	public void setReceiveCustomerDistCode(String receiveCustomerDistCode) {
		this.receiveCustomerDistCode = receiveCustomerDistCode;
	}

	public String getReceiveCustomerTownCode() {
		return receiveCustomerTownCode;
	}

	public void setReceiveCustomerTownCode(String receiveCustomerTownCode) {
		this.receiveCustomerTownCode = receiveCustomerTownCode;
	}

	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}

	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}
	
	

}
