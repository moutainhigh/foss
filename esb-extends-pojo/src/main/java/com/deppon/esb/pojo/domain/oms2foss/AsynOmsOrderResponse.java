package com.deppon.esb.pojo.domain.oms2foss;

import java.io.Serializable;

/**
 * @description OMS同步订单返回消息
 * @author 297064
 *
 */
public class AsynOmsOrderResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1479441608915172889L;

	/**
	 * 同步订单是否成功
	 */
    private Boolean isSuccess;
    /**
     * 异常信息
     */
    private String errorMeg;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 运单号
     */
    private String waybillNo;
    /**
     * 服务编码
     */
    private String serviceCode;
    
    public Boolean getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getErrorMeg() {
		return errorMeg;
	}
	public void setErrorMeg(String errorMeg) {
		this.errorMeg = errorMeg;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
    
    
}
