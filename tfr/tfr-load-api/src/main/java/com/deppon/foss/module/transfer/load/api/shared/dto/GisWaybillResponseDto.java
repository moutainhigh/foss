/**  
 * Project Name:tfr-load-api  
 * File Name:GisWaybillResponseDto.java  
 * Package Name:com.deppon.foss.module.transfer.load.api.shared.dto  
 * Date:2015年6月11日下午8:11:19  
 *  
 */
package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.io.Serializable;

/**  
 * ClassName: GisWaybillResponseDto <br/>  
 * Function: GIS返回dto. <br/>  
 * date: 2015年6月11日 下午8:11:19 <br/>  
 *  
 * @author shiwei-045923 shiwei@outlook.com  
 * @version   
 * @since JDK 1.6  
 */
public class GisWaybillResponseDto implements Serializable {
	/**  
	 * serialVersionUID
	 * @since JDK 1.6  
	 */
	private static final long serialVersionUID = 123534645310L;
	
	/**
	 * 运单号
	 */
	private String waybillNum;
	
	/**
	 * 运单ID
	 */
	private String waybillID;
	
	/**
	 * 匹配到的部门code
	 */
	private String deptCode;
	
	/**
	 * 异常信息
	 */
	private String exceptionMSG;
	
	/**
	 * 是否成功
	 */
	private boolean is_success;
	
	/**
	 * 匹配模式
	 * 218381
	 */
	private String matchType;
	public String getMatchType() {
		return matchType;
	}
	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}
	/**  
	 * waybillNum.  
	 *  
	 * @return  the waybillNum  
	 * @since   JDK 1.6  
	 */
	public String getWaybillNum() {
		return waybillNum;
	}
	/**  
	 * waybillNum.  
	 *  
	 * @param   waybillNum    the waybillNum to set  
	 * @since   JDK 1.6  
	 */
	public void setWaybillNum(String waybillNum) {
		this.waybillNum = waybillNum;
	}
	/**  
	 * waybillID.  
	 *  
	 * @return  the waybillID  
	 * @since   JDK 1.6  
	 */
	public String getWaybillID() {
		return waybillID;
	}
	/**  
	 * waybillID.  
	 *  
	 * @param   waybillID    the waybillID to set  
	 * @since   JDK 1.6  
	 */
	public void setWaybillID(String waybillID) {
		this.waybillID = waybillID;
	}
	/**  
	 * deptCode.  
	 *  
	 * @return  the deptCode  
	 * @since   JDK 1.6  
	 */
	public String getDeptCode() {
		return deptCode;
	}
	/**  
	 * deptCode.  
	 *  
	 * @param   deptCode    the deptCode to set  
	 * @since   JDK 1.6  
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	/**  
	 * exceptionMSG.  
	 *  
	 * @return  the exceptionMSG  
	 * @since   JDK 1.6  
	 */
	public String getExceptionMSG() {
		return exceptionMSG;
	}
	/**  
	 * exceptionMSG.  
	 *  
	 * @param   exceptionMSG    the exceptionMSG to set  
	 * @since   JDK 1.6  
	 */
	public void setExceptionMSG(String exceptionMSG) {
		this.exceptionMSG = exceptionMSG;
	}
	/**  
	 * is_success.  
	 *  
	 * @return  the is_success  
	 * @since   JDK 1.6  
	 */
	public boolean getIs_success() {
		return is_success;
	}
	/**  
	 * is_success.  
	 *  
	 * @param   is_success    the is_success to set  
	 * @since   JDK 1.6  
	 */
	public void setIs_success(boolean is_success) {
		this.is_success = is_success;
	}
	
	
	
}
