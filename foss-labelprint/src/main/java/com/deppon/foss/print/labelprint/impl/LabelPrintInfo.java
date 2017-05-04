package com.deppon.foss.print.labelprint.impl;

public class LabelPrintInfo {

	private String locationOne = " "; 
	private String locationTwo = " ";
	private String locationThree = " ";
	private String locationFour = " "; 
	private String locationFive = " ";  
	private String storeOneNum = " ";  
	private String storeTwoNum = " ";	
	private String storeThreeNum = " "; 
	private String storeFourNum = " ";
	private String storeFiveNum = " ";
	private String departure = " ";
	private String destination = " ";
	private String packing = " ";
	private String pieces = " ";
	private String consignee = " ";
	private String number = " ";
	private String goodsType = " ";
	private String currentDept = " ";
	private String transProperty = " ";
	private String deliverType = " ";
	private String userName = " ";
	private String destCode = " ";
	private String finalCode = " ";
	private String weight = " ";
	
	private int startPieceIndex = 0;
	private int endPieceIndex = 0;
	private boolean IsUnusual = false;
	
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getDestCode() {
		return destCode;
	}
	public void setDestCode(String destCode) {
		this.destCode = destCode;
	}
	public String getFinalCode() {
		return finalCode;
	}
	public void setFinalCode(String finalCode) {
		this.finalCode = finalCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTransProperty() {
		return transProperty;
	}
	public void setTransProperty(String transProperty) {
		this.transProperty = transProperty;
	}
	public String getCurrentDept() {
		return currentDept;
	}
	public void setCurrentDept(String currentDept) {
		this.currentDept = currentDept;
	}
	public String getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getPacking() {
		return packing;
	}
	public void setPacking(String packing) {
		this.packing = packing;
	}
	public String getPieces() {
		return pieces;
	}
	public void setPieces(String pieces) {
		this.pieces = pieces;
	}
	public String getLocationFive() {
		return locationFive;
	}
	public void setLocationFive(String locationFive) {
		this.locationFive = locationFive;
	}
	public String getLocationFour() {
		return locationFour;
	}
	public void setLocationFour(String locationFour) {
		this.locationFour = locationFour;
	}
	public String getLocationOne() {
		return locationOne;
	}
	public void setLocationOne(String locationOne) {
		this.locationOne = locationOne;
	}
	public String getLocationThree() {
		return locationThree;
	}
	public void setLocationThree(String locationThree) {
		this.locationThree = locationThree;
	}
	public String getLocationTwo() {
		return locationTwo;
	}
	public void setLocationTwo(String locationTwo) {
		this.locationTwo = locationTwo;
	}
	public String getStoreFiveNum() {
		return storeFiveNum;
	}
	public void setStoreFiveNum(String storeFiveNum) {
		this.storeFiveNum = storeFiveNum;
	}
	public String getStoreFourNum() {
		return storeFourNum;
	}
	public void setStoreFourNum(String storeFourNum) {
		this.storeFourNum = storeFourNum;
	}
	public String getStoreOneNum() {
		return storeOneNum;
	}
	public void setStoreOneNum(String storeOneNum) {
		this.storeOneNum = storeOneNum;
	}
	public String getStoreThreeNum() {
		return storeThreeNum;
	}
	public void setStoreThreeNum(String storeThreeNum) {
		this.storeThreeNum = storeThreeNum;
	}
	public String getStoreTwoNum() {
		return storeTwoNum;
	}
	public void setStoreTwoNum(String storeTwoNum) {
		this.storeTwoNum = storeTwoNum;
	}
	
	public String getDeparture() {
		return departure;
	}
	public void setDeparture(String departure) {
		this.departure = departure;
	}
	
	public String getDeliverType() {
		return deliverType;
	}
	public void setDeliverType(String deliverType) {
		this.deliverType = deliverType;
	}
	public int getStartPieceIndex() {
		return startPieceIndex;
	}
	public void setStartPieceIndex(int startPieceIndex) {
		this.startPieceIndex = startPieceIndex;
	}
	public int getEndPieceIndex() {
		return endPieceIndex;
	}
	public void setEndPieceIndex(int endPieceIndex) {
		this.endPieceIndex = endPieceIndex;
	}
	public boolean isIsUnusual() {
		return IsUnusual;
	}
	public void setIsUnusual(boolean isUnusual) {
		IsUnusual = isUnusual;
	}
	
}
