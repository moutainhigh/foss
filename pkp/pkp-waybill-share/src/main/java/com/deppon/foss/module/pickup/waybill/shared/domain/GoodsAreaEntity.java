package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.io.Serializable;

/**
 * 货区信息实体-图片开单推送app
 *<p>Title: GoodsAreaEntity.java</p>
 * @author yuanjinbiao/ yuanjb@deppon.com
 * <p>Copyright: Copyright (c) 2014</p>
 * @version 1.0
 * 2014年7月28日
 */
public class GoodsAreaEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7620003985663275595L;
	/**
	 * 货区号
	 */
	private String goodsAreaCode;
	/**
	 * 外场编码
	 */
	private String transferCode;
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}
	public String getTransferCode() {
		return transferCode;
	}
	public void setTransferCode(String transferCode) {
		this.transferCode = transferCode;
	}
	
}
