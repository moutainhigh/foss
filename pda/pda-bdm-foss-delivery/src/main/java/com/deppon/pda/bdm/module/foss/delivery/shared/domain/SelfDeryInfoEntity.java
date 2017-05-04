package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.util.List;

/**
 * 
 * @ClassName SelfDeryInfo
 * @Description 到达联校验的返回信息实体
 * @author xujun cometzb@126.com
 * @date 2012-12-26
 */
public class SelfDeryInfoEntity {

	//运单号
	private String wblCode;
	//运输性质
	private String transType;
	//目的地编号
	private String destinationCode;
	//票件关系
	private List<PcVoteEntity> pcVotes;
	//重量
	private double weight;
	//体积
	private double volume;
	//件数
	private int pieces;
	//提货方式
	private String deliveryType;
	//流水号  2013-08-14
	private List<String> labelCodes;
	//投诉变更状态
	private String complainStatus;
	
	public String getWblCode() {
		return wblCode;
	}

	public void setWblCode(String wblCode) {
		this.wblCode = wblCode;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getDestinationCode() {
		return destinationCode;
	}

	public void setDestinationCode(String destinationCode) {
		this.destinationCode = destinationCode;
	}

	public List<PcVoteEntity> getPcVotes() {
		return pcVotes;
	}

	public void setPcVotes(List<PcVoteEntity> pcVotes) {
		this.pcVotes = pcVotes;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public int getPieces() {
		return pieces;
	}

	public void setPieces(int pieces) {
		this.pieces = pieces;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public List<String> getLabelCodes() {
		return labelCodes;
	}

	public void setLabelCodes(List<String> labelCodes) {
		this.labelCodes = labelCodes;
	}

	public String getComplainStatus() {
		return complainStatus;
	}

	public void setComplainStatus(String complainStatus) {
		this.complainStatus = complainStatus;
	}
	
}
