package com.deppon.esb.pojo.domain.foss2oms;

import java.io.Serializable;

/**
 * <p>OMS转货订单信息状态推送给FOSS request对象</p>
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:326915,date:2016-11-18 下午4:34:19 </p>
 * @author 326915 郑飞
 * @date 2016-11-18 下午4:34:19
 * @since
 * @version
 */
public class TransformOrderInfoStatusPushRequest implements Serializable {

	/**
	 * <p>用一句话描述这个变量表示什么</p>
	 */
	private static final long serialVersionUID = 7049384627814126776L;
	//订单号
	private String orderNumber;
	
	//订单状态
	private String orderStatus;

	/**
	 * @return  the orderNumber
	 */
	public String getOrderNumber() {
		return orderNumber;
	}

	/**
	 * @param orderNumber the orderNumber to set
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	/**
	 * @return  the orderStatus
	 */
	public String getOrderStatus() {
		return orderStatus;
	}

	/**
	 * @param orderStatus the orderStatus to set
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	
	
}
