package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * @author 130346-lifanghong
 * @date   2014-04-14
 * @description 最优月台
 *
 */
public class ZuiYouYueTaiEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 106640693259893603L;
	
    //外场
	private String transferCode;
	
	//月台
	private String platform;
	
	//待叉区
	private String waiteFork;
	
	//库区
	private String goodsArea;
	
	//距离
	private String  distance;

	
	
	public String getWaiteFork() {
		return waiteFork;
	}

	public void setWaiteFork(String waiteFork) {
		this.waiteFork = waiteFork;
	}

	public String getTransferCode() {
		return transferCode;
	}

	public void setTransferCode(String transferCode) {
		this.transferCode = transferCode;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getGoodsArea() {
		return goodsArea;
	}

	public void setGoodsArea(String goodsArea) {
		this.goodsArea = goodsArea;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}
	
}
