package com.deppon.foss.module.pickup.order.api.shared.domain;

/**
 * 
 */
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 
 * @ClassName: OrderAutoExceptionLogEntity 
 * @Description: 自动调度业务异常日志实体
 * @author YANGBIN
 * @date 2014-5-9 下午2:00:19 
 *
 */
public class OrderAutoExceptionLogEntity  extends BaseEntity{ 

	private static final long serialVersionUID = 1L;
	//ID
	private String id;
	//订单ID
	private String dispatchOrderId;
	//订单号
	private String orderNo;
	//异常类型
	private String exceptionType;
	//异常原因
	private String exceptionReason;
	//创建时间
	private Date createTime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getExceptionType() {
		return exceptionType;
	}
	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}
	public String getExceptionReason() {
		return exceptionReason;
	}
	public void setExceptionReason(String exceptionReason) {
		this.exceptionReason = exceptionReason;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}