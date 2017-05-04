package com.deppon.pda.bdm.module.foss.clear.shared.domain;

public class CreateClearTask {
	/**
	 * 货区编号
	 */
	private String crgAreaCode;
	/**
	 * 件数 （派送外场建立清仓任务时，需要输入件数，表示清仓的件数区。）
	 */
	private int crgPieces;
	/**
	 * 任务编号
	 */
	private String taskNo;
	/**
	 * 是否派送清仓 （Y  N）
	 */
	private String isDeryCleanStock;
	/**
	 * 起始件区
	 */
	private int startPieces;
	/**
	 * 结束件区
	 */
	private int endPieces;
	
	private String takeType;//提货方式
	
	private String administrativeRegion;//行政区域 
	/*
	 * 是否是快递
	 */
	private String isExpressInfo;
	
	public String getIsExpressInfo() {
		return isExpressInfo;
	}
	public void setIsExpressInfo(String isExpressInfo) {
		this.isExpressInfo = isExpressInfo;
	}
	public String getTaskNo() {
		return taskNo;
	}
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	public String getCrgAreaCode() {
		return crgAreaCode;
	}
	public void setCrgAreaCode(String crgAreaCode) {
		this.crgAreaCode = crgAreaCode;
	}
	public int getCrgPieces() {
		return crgPieces;
	}
	public void setCrgPieces(int crgPieces) {
		this.crgPieces = crgPieces;
	}
	public String getIsDeryCleanStock() {
		return isDeryCleanStock;
	}
	public void setIsDeryCleanStock(String isDeryCleanStock) {
		this.isDeryCleanStock = isDeryCleanStock;
	}
	public int getStartPieces() {
		return startPieces;
	}
	public void setStartPieces(int startPieces) {
		this.startPieces = startPieces;
	}
	public int getEndPieces() {
		return endPieces;
	}
	public void setEndPieces(int endPieces) {
		this.endPieces = endPieces;
	}
	public String getTakeType() {
		return takeType;
	}
	public void setTakeType(String takeType) {
		this.takeType = takeType;
	}
	public String getAdministrativeRegion() {
		return administrativeRegion;
	}
	public void setAdministrativeRegion(String administrativeRegion) {
		this.administrativeRegion = administrativeRegion;
	}
	
}
