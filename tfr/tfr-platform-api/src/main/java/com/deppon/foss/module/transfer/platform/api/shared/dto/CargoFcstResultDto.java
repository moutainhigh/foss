package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CargoFcstResultDto implements Serializable {

	private static final long serialVersionUID = -7671909065991329269L;

	/**
	 * 出发(到达)名称
	 */
	private String lineName;

	/**
	 * 预测货量，长途到达，重量、体积、票数
	 */
	private BigDecimal fcstWeightArrLng;

	private BigDecimal fcstVolumeArrLng;

	private int fcstVoteArrLng;

	/**
	 * 预测货量，短途到达，重量、体积、票数
	 */
	private BigDecimal fcstWeightArrSht;

	private BigDecimal fcstVolumeArrSht;

	private int fcstVoteArrSht;

	/**
	 * 预测货量，接送货到达，重量、体积、票数
	 */
	private BigDecimal fcstWeightArrPickup;

	private BigDecimal fcstVolumeArrPickup;

	private int fcstVoteArrPickup;

	/**
	 * 预测货量，长途出发，重量、体积、票数
	 */
	private BigDecimal fcstWeightDptLng;

	private BigDecimal fcstVolumeDptLng;

	private int fcstVoteDptLng;

	/**
	 * 预测货量，短途出发，重量、体积、票数
	 */
	private BigDecimal fcstWeightDptSht;

	private BigDecimal fcstVolumeDptSht;

	private int fcstVoteDptSht;

	/**
	 * 预测货量，派送出发，重量、体积、票数
	 */
	private BigDecimal fcstWeightDptDispath;

	private BigDecimal fcstVolumeDptDispath;

	private int fcstVoteDptDispath;

	/**
	 * 预测总货量，重量、体积、票数
	 */
	private BigDecimal fcstWeight;

	private BigDecimal fcstVolume;

	private int fcstVote;

	/**
	 * 1周前的实际总货量，重量、体积、票数
	 */
	private BigDecimal actualWeight7;

	private BigDecimal actualVolume7;

	private int actualVote7;

	/**
	 * 2周前的实际总货量，重量、体积、票数
	 */
	private BigDecimal actualWeight14;

	private BigDecimal actualVolume14;

	private int actualVote14;

	/**
	 * 3周前的实际总货量，重量、体积、票数
	 */
	private BigDecimal actualWeight21;

	private BigDecimal actualVolume21;

	private int actualVote21;

	/**
	 * 4周前的实际总货量，重量、体积、票数
	 */
	private BigDecimal actualWeight28;

	private BigDecimal actualVolume28;

	private int actualVote28;

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public BigDecimal getFcstWeightArrLng() {
		return fcstWeightArrLng;
	}

	public void setFcstWeightArrLng(BigDecimal fcstWeightArrLng) {
		this.fcstWeightArrLng = fcstWeightArrLng;
	}

	public BigDecimal getFcstVolumeArrLng() {
		return fcstVolumeArrLng;
	}

	public void setFcstVolumeArrLng(BigDecimal fcstVolumeArrLng) {
		this.fcstVolumeArrLng = fcstVolumeArrLng;
	}

	public int getFcstVoteArrLng() {
		return fcstVoteArrLng;
	}

	public void setFcstVoteArrLng(int fcstVoteArrLng) {
		this.fcstVoteArrLng = fcstVoteArrLng;
	}

	public BigDecimal getFcstWeightArrSht() {
		return fcstWeightArrSht;
	}

	public void setFcstWeightArrSht(BigDecimal fcstWeightArrSht) {
		this.fcstWeightArrSht = fcstWeightArrSht;
	}

	public BigDecimal getFcstVolumeArrSht() {
		return fcstVolumeArrSht;
	}

	public void setFcstVolumeArrSht(BigDecimal fcstVolumeArrSht) {
		this.fcstVolumeArrSht = fcstVolumeArrSht;
	}

	public int getFcstVoteArrSht() {
		return fcstVoteArrSht;
	}

	public void setFcstVoteArrSht(int fcstVoteArrSht) {
		this.fcstVoteArrSht = fcstVoteArrSht;
	}

	public BigDecimal getFcstWeightArrPickup() {
		return fcstWeightArrPickup;
	}

	public void setFcstWeightArrPickup(BigDecimal fcstWeightArrPickup) {
		this.fcstWeightArrPickup = fcstWeightArrPickup;
	}

	public BigDecimal getFcstVolumeArrPickup() {
		return fcstVolumeArrPickup;
	}

	public void setFcstVolumeArrPickup(BigDecimal fcstVolumeArrPickup) {
		this.fcstVolumeArrPickup = fcstVolumeArrPickup;
	}

	public int getFcstVoteArrPickup() {
		return fcstVoteArrPickup;
	}

	public void setFcstVoteArrPickup(int fcstVoteArrPickup) {
		this.fcstVoteArrPickup = fcstVoteArrPickup;
	}

	public BigDecimal getFcstWeightDptLng() {
		return fcstWeightDptLng;
	}

	public void setFcstWeightDptLng(BigDecimal fcstWeightDptLng) {
		this.fcstWeightDptLng = fcstWeightDptLng;
	}

	public BigDecimal getFcstVolumeDptLng() {
		return fcstVolumeDptLng;
	}

	public void setFcstVolumeDptLng(BigDecimal fcstVolumeDptLng) {
		this.fcstVolumeDptLng = fcstVolumeDptLng;
	}

	public int getFcstVoteDptLng() {
		return fcstVoteDptLng;
	}

	public void setFcstVoteDptLng(int fcstVoteDptLng) {
		this.fcstVoteDptLng = fcstVoteDptLng;
	}

	public BigDecimal getFcstWeightDptSht() {
		return fcstWeightDptSht;
	}

	public void setFcstWeightDptSht(BigDecimal fcstWeightDptSht) {
		this.fcstWeightDptSht = fcstWeightDptSht;
	}

	public BigDecimal getFcstVolumeDptSht() {
		return fcstVolumeDptSht;
	}

	public void setFcstVolumeDptSht(BigDecimal fcstVolumeDptSht) {
		this.fcstVolumeDptSht = fcstVolumeDptSht;
	}

	public int getFcstVoteDptSht() {
		return fcstVoteDptSht;
	}

	public void setFcstVoteDptSht(int fcstVoteDptSht) {
		this.fcstVoteDptSht = fcstVoteDptSht;
	}

	public BigDecimal getFcstWeightDptDispath() {
		return fcstWeightDptDispath;
	}

	public void setFcstWeightDptDispath(BigDecimal fcstWeightDptDispath) {
		this.fcstWeightDptDispath = fcstWeightDptDispath;
	}

	public BigDecimal getFcstVolumeDptDispath() {
		return fcstVolumeDptDispath;
	}

	public void setFcstVolumeDptDispath(BigDecimal fcstVolumeDptDispath) {
		this.fcstVolumeDptDispath = fcstVolumeDptDispath;
	}

	public int getFcstVoteDptDispath() {
		return fcstVoteDptDispath;
	}

	public void setFcstVoteDptDispath(int fcstVoteDptDispath) {
		this.fcstVoteDptDispath = fcstVoteDptDispath;
	}

	public BigDecimal getFcstWeight() {
		return fcstWeight;
	}

	public void setFcstWeight(BigDecimal fcstWeight) {
		this.fcstWeight = fcstWeight;
	}

	public BigDecimal getFcstVolume() {
		return fcstVolume;
	}

	public void setFcstVolume(BigDecimal fcstVolume) {
		this.fcstVolume = fcstVolume;
	}

	public int getFcstVote() {
		return fcstVote;
	}

	public void setFcstVote(int fcstVote) {
		this.fcstVote = fcstVote;
	}

	public BigDecimal getActualWeight7() {
		return actualWeight7;
	}

	public void setActualWeight7(BigDecimal actualWeight7) {
		this.actualWeight7 = actualWeight7;
	}

	public BigDecimal getActualVolume7() {
		return actualVolume7;
	}

	public void setActualVolume7(BigDecimal actualVolume7) {
		this.actualVolume7 = actualVolume7;
	}

	public int getActualVote7() {
		return actualVote7;
	}

	public void setActualVote7(int actualVote7) {
		this.actualVote7 = actualVote7;
	}

	public BigDecimal getActualWeight14() {
		return actualWeight14;
	}

	public void setActualWeight14(BigDecimal actualWeight14) {
		this.actualWeight14 = actualWeight14;
	}

	public BigDecimal getActualVolume14() {
		return actualVolume14;
	}

	public void setActualVolume14(BigDecimal actualVolume14) {
		this.actualVolume14 = actualVolume14;
	}

	public int getActualVote14() {
		return actualVote14;
	}

	public void setActualVote14(int actualVote14) {
		this.actualVote14 = actualVote14;
	}

	public BigDecimal getActualWeight21() {
		return actualWeight21;
	}

	public void setActualWeight21(BigDecimal actualWeight21) {
		this.actualWeight21 = actualWeight21;
	}

	public BigDecimal getActualVolume21() {
		return actualVolume21;
	}

	public void setActualVolume21(BigDecimal actualVolume21) {
		this.actualVolume21 = actualVolume21;
	}

	public int getActualVote21() {
		return actualVote21;
	}

	public void setActualVote21(int actualVote21) {
		this.actualVote21 = actualVote21;
	}

	public BigDecimal getActualWeight28() {
		return actualWeight28;
	}

	public void setActualWeight28(BigDecimal actualWeight28) {
		this.actualWeight28 = actualWeight28;
	}

	public BigDecimal getActualVolume28() {
		return actualVolume28;
	}

	public void setActualVolume28(BigDecimal actualVolume28) {
		this.actualVolume28 = actualVolume28;
	}

	public int getActualVote28() {
		return actualVote28;
	}

	public void setActualVote28(int actualVote28) {
		this.actualVote28 = actualVote28;
	}

}
