/**   
* @Title: CancelPackageResultDto.java 
* @Package com.deppon.foss.module.transfer.load.api.shared.dto 
* @Description: 
* @author shiwei shiwei@outlook.com
* @date 2013-9-3 上午10:44:27 
* @version V1.0   
*/
package com.deppon.foss.module.transfer.load.api.shared.dto;

/** 
 * @ClassName: CancelPackageResultDto 
 * @Description: 用于返回批量撤销包时的处理结果
 * @author shiwei shiwei@outlook.com
 * @date 2013-9-3 上午10:44:27 
 *  
 */
public class CancelPackageResultDto {
	
	/**
	 * 包号
	 */
	private String packageNo;
	
	/**
	 * 是否成功
	 */
	private String beSuccess;
	
	/**
	 * 异常信息
	 */
	private String message;

	public String getPackageNo() {
		return packageNo;
	}

	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}

	public String getBeSuccess() {
		return beSuccess;
	}

	public void setBeSuccess(String beSuccess) {
		this.beSuccess = beSuccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
