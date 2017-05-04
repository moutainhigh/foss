package com.deppon.foss.module.pickup.common.client.vo;

/**
 * WangQianJin
 * add by 2013-08-26
 * 自提件VO
 */
public class EconomyVo {

	//能否使用自提件
	private boolean result;
	//订单渠道是否符合自提件要求
	private boolean bchannel;
	//运输性质是否符合自提件要求
	private boolean bproduct;
	//提货方式是否符合自提件要求
	private boolean bdelivery;
	//上门接货是否符合自提件要求
	private boolean bdoor;
	//自提件名称是否符合自提件要求
	private boolean bchannelName;
	
	
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public boolean isBchannel() {
		return bchannel;
	}
	public void setBchannel(boolean bchannel) {
		this.bchannel = bchannel;
	}
	public boolean isBproduct() {
		return bproduct;
	}
	public void setBproduct(boolean bproduct) {
		this.bproduct = bproduct;
	}
	public boolean isBdelivery() {
		return bdelivery;
	}
	public void setBdelivery(boolean bdelivery) {
		this.bdelivery = bdelivery;
	}
	public boolean isBdoor() {
		return bdoor;
	}
	public void setBdoor(boolean bdoor) {
		this.bdoor = bdoor;
	}
	public boolean isBchannelName() {
		return bchannelName;
	}
	public void setBchannelName(boolean bchannelName) {
		this.bchannelName = bchannelName;
	}
	
	
}
