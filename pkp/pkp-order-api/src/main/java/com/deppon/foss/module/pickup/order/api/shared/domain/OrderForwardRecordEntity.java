package com.deppon.foss.module.pickup.order.api.shared.domain;

/**
 * 
 */
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 
 * @ClassName: OrderForwardRecordEntity 
 * @Description: 快递订单PDA转发记录实体 
 * @author YANGBIN
 * @date 2014-5-9 下午2:05:24 
 *
 */
public class OrderForwardRecordEntity  extends BaseEntity{ 

	private static final long serialVersionUID = 1L;
	//订单ID
	private String dispatchOrderId;
	//订单号
	private String orderNo;
	//快递员工号
	private String expressDriverCode;
	//快递员姓名
	private String expressDriverName;
	//操作时间
	private Date operateTime;

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
	public String getExpressDriverCode() {
		return expressDriverCode;
	}
	public void setExpressDriverCode(String expressDriverCode) {
		this.expressDriverCode = expressDriverCode;
	}
	public String getExpressDriverName() {
		return expressDriverName;
	}
	public void setExpressDriverName(String expressDriverName) {
		this.expressDriverName = expressDriverName;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

}