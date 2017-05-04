package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;

/**
 * 家装货物到达信息请求实体类
 * @author 243921-FOSS-zhangtingting
 * @date 2015-09-14 下午14:56：35
 */
public class ArrivalGoodsRequestDto implements Serializable {

	/**
	 * 类的序列号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 运单号
	 */
	private String mailNo;

	/**
	 * 货物到达信息
	 */
	private String arriveInfo;

	public String getMailNo() {
		return this.mailNo;
	}

	public void setMailNo(String mailNo) {
		this.mailNo = mailNo;
	}

	public String getArriveInfo() {
		return this.arriveInfo;
	}

	public void setArriveInfo(String arriveInfo) {
		this.arriveInfo = arriveInfo;
	}

}
