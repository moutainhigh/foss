package com.deppon.foss.module.pickup.order.api.shared.domain;

/**
 * 
 */
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 
 * @ClassName: OrderPdaReturnRecordEntity 
 * @Description: PDA退回记录实体
 * @author YANGBIN
 * @date 2014-5-9 下午2:36:33 
 *
 */
public class OrderPdaReturnRecordEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;
	//订单ID
	private String dispatchOrderId;
	//订单号
	private String orderNo;
	//操作类型(退回、转发)
	private String operateType;
	//订单状态 (退回、揽货失败)
	private String orderStatus;
	//退回原因类型
	private String returnType;
	//退回原因备注
	private String returnRemark;
	//退回人编码
	private String createCode;
	//退回人姓名
	private String createName;
	//操作时间
	private Date operateTime;
	//退回地址
	private String pickupAddress;
	private String productCode; //14.8.11 gcl 零担 退回后记录退回日志
	
	private List<String>productCodes;

	
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getDispatchOrderId() {
		return dispatchOrderId;
	}
	public void setDispatchOrderId(String dispatchOrderId) {
		this.dispatchOrderId = dispatchOrderId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public String getReturnRemark() {
		return returnRemark;
	}
	public void setReturnRemark(String returnRemark) {
		this.returnRemark = returnRemark;
	}
	public String getCreateCode() {
		return createCode;
	}
	public void setCreateCode(String createCode) {
		this.createCode = createCode;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public String getPickupAddress() {
		return pickupAddress;
	}
	public void setPickupAddress(String pickupAddress) {
		this.pickupAddress = pickupAddress;
	}
	/**
	 * @return the productCodes
	 */
	public List<String> getProductCodes() {
		return productCodes;
	}
	/**
	 * @param productCodes the productCodes to set
	 */
	public void setProductCodes(List<String> productCodes) {
		this.productCodes = productCodes;
	}
	
}