package com.deppon.foss.module.pickup.order.api.shared.dto;

import com.deppon.foss.module.pickup.order.api.shared.define.OrderMutexElementConstants;


/**
 * 订单更新专用
 * 互斥对象
 * 
 */
public class OrderMutexElement {

	/**
	 * 锁定业务对象编号，如订单号
	 */
	private String businessNo;

	/**
	 * 锁定业务对象描述，如自动调度
	 */
	private String businessDesc;

	/**
	 * 锁定业务对象类型
	 */
	private OrderMutexElementConstants type;

	/**
	 * 锁定时间，超时后自动解除锁定 单位：秒
	 */
	private int ttl = 300;

	/**
	 * 构造函数
	 * 
	 */
	public OrderMutexElement(String businessNo, String businessDesc,
			OrderMutexElementConstants type ,int ttl) {
		this.businessNo = businessNo;
		this.businessDesc = businessDesc;
		this.type = type;
		this.ttl = ttl;
	}
	public OrderMutexElement(String businessNo, String businessDesc,
			OrderMutexElementConstants type ) {
		this.businessNo = businessNo;
		this.businessDesc = businessDesc;
		this.type = type;
	}

	/**
	 * @return businessNo
	 */
	public String getBusinessNo() {
		return businessNo;
	}

	/**
	 * @return businessDesc
	 */
	public String getBusinessDesc() {
		return businessDesc;
	}

	/**
	 * @return type
	 */
	public OrderMutexElementConstants getType() {
		return type;
	}

	/**
	 * @return ttl
	 */
	public int getTtl() {
		return ttl;
	}

	/**
	 * @param ttl
	 */
	public void setTtl(int ttl) {
		this.ttl = ttl;
	}

}
