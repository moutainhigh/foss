
package com.deppon.foss.module.pickup.waybill.shared.dto;



/**
 * OA差错上报response DTO  --MANA-518

  * @ClassName: OAErrorReportResultDto

  * @Description: TODO

  * @author deppon-157229-zxy

  * @date 2014-2-14 下午4:06:29

  *
 */
public class OAErrorReportResultDto {
	//是否成功 false代码正常 true代表发生异常
	private boolean isException;
	//异常编码
	private String code;
	//异常信息
	private String message;
	//运单号
	private String errorsNo;
	//成功和失败
	private int sussces;
	
	public boolean isException() {
		return isException;
	}
	public void setException(boolean isException) {
		this.isException = isException;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getErrorsNo() {
		return errorsNo;
	}
	public void setErrorsNo(String errorsNo) {
		this.errorsNo = errorsNo;
	}
	public int getSussces() {
		return sussces;
	}
	public void setSussces(int sussces) {
		this.sussces = sussces;
	}
}
