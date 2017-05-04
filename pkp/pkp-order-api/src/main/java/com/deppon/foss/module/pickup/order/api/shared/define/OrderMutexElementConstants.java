package com.deppon.foss.module.pickup.order.api.shared.define;

/**
 * 锁定业务类型
 * 
 */
public enum OrderMutexElementConstants {
	
	DISPATCHORDERNO_LOCK("DispatchOrderNo_Lock","订单更新");
	
	private String prefix;

	private String name;

	private OrderMutexElementConstants(String prefix, String name) {
		this.prefix = prefix;
		this.name = name;
	}

	/**
	 * @return prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @param prefix
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
}
