package com.deppon.foss.module.pickup.waybill.shared.vo;

/**
 * 调用CRM返券之后返回的参数VO
 * @author 
 * @date 
 */
public class ReturnSendCouponParamVo {

	/**
	 * 优惠券编码
	 */
    private String CouponNo;
    /**
	 * 是否接收成功
	 */
    private String succFlag;
    /**
	 * 异常信息
	 */
    private String exceptionInfo;
    
    /**
     * 拼接后的返券短信内容
     * */
    private String sendCouponContent;
	
	
	public String getCouponNo() {
		return CouponNo;
	}
	public void setCouponNo(String couponNo) {
		CouponNo = couponNo;
	}
	public String getSuccFlag() {
		return succFlag;
	}
	public void setSuccFlag(String succFlag) {
		this.succFlag = succFlag;
	}
	public String getExceptionInfo() {
		return exceptionInfo;
	}
	public void setExceptionInfo(String exceptionInfo) {
		this.exceptionInfo = exceptionInfo;
	}
	public String getSendCouponContent() {
		return sendCouponContent;
	}
	public void setSendCouponContent(String sendCouponContent) {
		this.sendCouponContent = sendCouponContent;
	}
  
   
    
}
