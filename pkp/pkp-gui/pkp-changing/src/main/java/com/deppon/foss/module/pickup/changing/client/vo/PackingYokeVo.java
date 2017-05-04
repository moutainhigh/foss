package com.deppon.foss.module.pickup.changing.client.vo;

import java.math.BigDecimal;

/**
 * 代打包装VO
 * @author 218371-foss-zhaoyanjun
 * @date:2014-10-17上午:09:02
 */

public class PackingYokeVo {
	//纸箱一号客户
	private Integer paperBoxOne;
	
	//纸箱二号客户
	private Integer paperBoxTwo;
	
	//纸箱三号客户
	private Integer paperBoxThree;
	
	//纸箱四号客户
	private Integer paperBoxFour;
	
	//纤袋1号 蓝
	private Integer fibelBagBlueOne;
	
	//纤袋2号 蓝
	private Integer fibelBagBlueTwo;
	
	//纤袋3号 蓝
	private Integer fibelBagBlueThree;
	
	//纤袋4号 蓝
	private Integer fibelBagBlueFour;
	
	//纤袋1号 白
	private Integer fibelBagWhiteOne;
	
	//纤袋2号 白
	private Integer fibelBagWhiteTwo;
	
	//纤袋3号 白
	private Integer fibelBagWhiteThree;
	
	//纤袋4号 白
	private Integer fibelBagWhiteFour;
	
	//纤袋5号 布匹 白
	private Integer fibelBagWhiteFiveCloth;
	
	//纤袋6号 布匹 白
	private Integer fibelBagWhiteSixCloth;
	
	//纸箱总价
	private BigDecimal paperBoxTotlePrice;
	
	//纤袋总价
	private BigDecimal fibelBagTotlePrice;
	
	//其他费用
	private BigDecimal other;
	
	//缓冲物费用
	private BigDecimal buffer;
		
	//折扣
	private BigDecimal discount;
	
	//合计
	private BigDecimal Totle;

	public Integer getPaperBoxOne() {
		return paperBoxOne;
	}

	public void setPaperBoxOne(Integer paperBoxOne) {
		this.paperBoxOne = paperBoxOne;
	}

	public Integer getPaperBoxTwo() {
		return paperBoxTwo;
	}

	public void setPaperBoxTwo(Integer paperBoxTwo) {
		this.paperBoxTwo = paperBoxTwo;
	}

	public Integer getPaperBoxThree() {
		return paperBoxThree;
	}

	public void setPaperBoxThree(Integer paperBoxThree) {
		this.paperBoxThree = paperBoxThree;
	}

	public Integer getFibelBagBlueOne() {
		return fibelBagBlueOne;
	}

	public void setFibelBagBlueOne(Integer fibelBagBlueOne) {
		this.fibelBagBlueOne = fibelBagBlueOne;
	}

	public Integer getFibelBagBlueTwo() {
		return fibelBagBlueTwo;
	}

	public void setFibelBagBlueTwo(Integer fibelBagBlueTwo) {
		this.fibelBagBlueTwo = fibelBagBlueTwo;
	}

	public Integer getFibelBagBlueThree() {
		return fibelBagBlueThree;
	}

	public void setFibelBagBlueThree(Integer fibelBagBlueThree) {
		this.fibelBagBlueThree = fibelBagBlueThree;
	}

	public Integer getFibelBagBlueFour() {
		return fibelBagBlueFour;
	}

	public void setFibelBagBlueFour(Integer fibelBagBlueFour) {
		this.fibelBagBlueFour = fibelBagBlueFour;
	}

	public Integer getFibelBagWhiteOne() {
		return fibelBagWhiteOne;
	}

	public void setFibelBagWhiteOne(Integer fibelBagWhiteOne) {
		this.fibelBagWhiteOne = fibelBagWhiteOne;
	}

	public Integer getFibelBagWhiteTwo() {
		return fibelBagWhiteTwo;
	}

	public void setFibelBagWhiteTwo(Integer fibelBagWhiteTwo) {
		this.fibelBagWhiteTwo = fibelBagWhiteTwo;
	}

	public Integer getFibelBagWhiteThree() {
		return fibelBagWhiteThree;
	}

	public void setFibelBagWhiteThree(Integer fibelBagWhiteThree) {
		this.fibelBagWhiteThree = fibelBagWhiteThree;
	}

	public Integer getFibelBagWhiteFour() {
		return fibelBagWhiteFour;
	}

	public void setFibelBagWhiteFour(Integer fibelBagWhiteFour) {
		this.fibelBagWhiteFour = fibelBagWhiteFour;
	}

	public Integer getFibelBagWhiteFiveCloth() {
		return fibelBagWhiteFiveCloth;
	}

	public void setFibelBagWhiteFiveCloth(Integer fibelBagWhiteFiveCloth) {
		this.fibelBagWhiteFiveCloth = fibelBagWhiteFiveCloth;
	}

	public Integer getFibelBagWhiteSixCloth() {
		return fibelBagWhiteSixCloth;
	}

	public void setFibelBagWhiteSixCloth(Integer fibelBagWhiteSixCloth) {
		this.fibelBagWhiteSixCloth = fibelBagWhiteSixCloth;
	}

	public BigDecimal getPaperBoxTotlePrice() {
		return paperBoxTotlePrice;
	}

	public void setPaperBoxTotlePrice(BigDecimal paperBoxTotlePrice) {
		this.paperBoxTotlePrice = paperBoxTotlePrice;
	}

	public BigDecimal getFibelBagTotlePrice() {
		return fibelBagTotlePrice;
	}

	public void setFibelBagTotlePrice(BigDecimal fibelBagTotlePrice) {
		this.fibelBagTotlePrice = fibelBagTotlePrice;
	}

	public BigDecimal getOther() {
		return other;
	}

	public void setOther(BigDecimal other) {
		this.other = other;
	}

	public BigDecimal getTotle() {
		return Totle;
	}

	public void setTotle(BigDecimal totle) {
		Totle = totle;
	}

	public Integer getPaperBoxFour() {
		return paperBoxFour;
	}

	public void setPaperBoxFour(Integer paperBoxFour) {
		this.paperBoxFour = paperBoxFour;
	}

	public BigDecimal getBuffer() {
		return buffer;
	}

	public void setBuffer(BigDecimal buffer) {
		this.buffer = buffer;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	
}
