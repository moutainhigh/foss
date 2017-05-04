package com.deppon.esb.pojo.domain.crm2foss;

/** 
 * @ClassName: updateOrderResponse 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author lvchognxin
 * @date 2015年6月5日 下午3:23:58 
 *  
 */
public class UpdateEOrderResponse {
	//是否成功
    private Boolean ifSuccess;
    //异常信息
    private String errorMeg;
    //订单号
    private String orderNumber;
    //运单号
    private String waybillNumber;
	//请求实体
    private UpdateEOrderRequest updateEOrderRequest;
    
	public Boolean getIfSuccess() {
		return ifSuccess;
	}
	public void setIfSuccess(Boolean ifSuccess) {
		this.ifSuccess = ifSuccess;
	}
	public String getErrorMeg() {
		return errorMeg;
	}
	public void setErrorMeg(String errorMeg) {
		this.errorMeg = errorMeg;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getWaybillNumber() {
		return waybillNumber;
	}
	public void setWaybillNumber(String waybillNumber) {
		this.waybillNumber = waybillNumber;
	}
	public UpdateEOrderRequest getUpdateEOrderRequest() {
		return updateEOrderRequest;
	}
	public void setUpdateEOrderRequest(UpdateEOrderRequest updateEOrderRequest) {
		this.updateEOrderRequest = updateEOrderRequest;
	}
    
    
}
