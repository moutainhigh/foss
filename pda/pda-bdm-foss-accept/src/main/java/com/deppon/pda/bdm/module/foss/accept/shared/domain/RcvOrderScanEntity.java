package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.shared.domain
 * @file RcvOrderScanEntity.java
 * @description 已接收实体
 * @author ChenLiang
 * @created 2012-12-31 下午2:16:22
 * @version 1.0
 */
public class RcvOrderScanEntity extends ScanMsgEntity {
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 车牌号
	 */
	private String truckCode;

	/**
	 * 订单号/约车编号
	 */
	private String orderCode;

	/**
	 * 订单状态
	 */
	private String orderStatus;

	/**
	 * 订单类型
	 */
	private String orderType;

	public String getTruckCode() {
		return truckCode;
	}

	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

}
