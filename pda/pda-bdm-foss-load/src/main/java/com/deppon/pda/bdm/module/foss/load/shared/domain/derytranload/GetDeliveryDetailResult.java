package com.deppon.pda.bdm.module.foss.load.shared.domain.derytranload;

import java.math.BigDecimal;

/**
 *  返回下拉派件交接明细
 * @ClassName GetDeliveryDetailResult.java 
 * @Description 
 * @author 245955
 * @date 2015-4-18
 */
public class GetDeliveryDetailResult {

	/**
	 *  派送单号
	 */
	private String deryCode;

	/**
	 * 运单号
	 */
	private String wblCode;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 总件数
	 */
	private Integer sumPieces;
	/**
	 * 扫描状态
	 */
	private String scanState;
	/**
	 * 重量
	 */
	private BigDecimal weight;
	/**
	 * 体积
	 */
	private BigDecimal volume;
	/**
	 * 流水号
	 */
	private String labelCodes;

	/**
	 * 已扫件数
	 */
	private Integer scanQty;

	public String getDeryCode() {
		return deryCode;
	}

	public void setDeryCode(String deryCode) {
		this.deryCode = deryCode;
	}

	public String getWblCode() {
		return wblCode;
	}

	public void setWblCode(String wblCode) {
		this.wblCode = wblCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getSumPieces() {
		return sumPieces;
	}

	public void setSumPieces(Integer sumPieces) {
		this.sumPieces = sumPieces;
	}

	public String getScanState() {
		return scanState;
	}

	public void setScanState(String scanState) {
		this.scanState = scanState;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public String getLabelCodes() {
		return labelCodes;
	}

	public void setLabelCodes(String labelCodes) {
		this.labelCodes = labelCodes;
	}

	public Integer getScanQty() {
		return scanQty;
	}

	public void setScanQty(Integer scanQty) {
		this.scanQty = scanQty;
	}
	
}
