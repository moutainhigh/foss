package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PlatformGoodsAreaDistanceDto implements Serializable{


	private static final long serialVersionUID = 106640693259893603L;
	
    //外场
	private String transferCode;
	
	//月台(虚拟code)
	private String platform;
	
	//待叉区（虚拟code）
	private String waiteFork;
	
	//库区（虚拟code）
	private String goodsArea;
	
	//距离
	private BigDecimal  distance;

	
	
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

	public BigDecimal getDistance() {
		return distance;
	}

	public void setDistance(BigDecimal distance) {
		this.distance = distance;
	}


	

}
