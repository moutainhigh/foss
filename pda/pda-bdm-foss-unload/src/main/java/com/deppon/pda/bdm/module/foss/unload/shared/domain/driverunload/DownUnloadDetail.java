package com.deppon.pda.bdm.module.foss.unload.shared.domain.driverunload;

import java.util.List;
/**
 * 下拉卸车货物明细
 * 
 * @ClassName DownUnloadDetail.java
 * @Description
 * @author 245955
 * @date 2015-4-14
 */
public class DownUnloadDetail {
	/**
	 * 到达部门
	 */
	private String arrDeptCode;
	/**
	 * 到达部门名称
	 */
	private String arrDeptName;

	private List<DownUnloadSerialNoModel> serialNo;
	
	/**
	 * 卸车任务号
	 */
	private String taskCode;

	/**
	 * 运单号
	 */
	private String wblCode;
	/**
	 * 总件数
	 */
	private String sumPieces;
	
	
	/**
	 * 已卸车件数
	 */
	private int unldPieces;

	/**
	 * 流水号二进制字符串
	 */
	private String serialNoStr;
	/**
	 * 状态为“Y”的二进制字符串
	 */
	private String unPackingStr;
    /**
     * 开单件数
     */
	private Integer goodsQtyTotal;

	
	public String getSumPieces() {
		return sumPieces;
	}

	public void setSumPieces(String sumPieces) {
		this.sumPieces = sumPieces;
	}

	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	public String getArrDeptCode() {
		return arrDeptCode;
	}

	public void setArrDeptCode(String arrDeptCode) {
		this.arrDeptCode = arrDeptCode;
	}

	public String getArrDeptName() {
		return arrDeptName;
	}

	public void setArrDeptName(String arrDeptName) {
		this.arrDeptName = arrDeptName;
	}

	public List<DownUnloadSerialNoModel> getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(List<DownUnloadSerialNoModel> serialNo) {
		this.serialNo = serialNo;
	}



	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	

	public String getWblCode() {
		return wblCode;
	}

	public void setWblCode(String wblCode) {
		this.wblCode = wblCode;
	}

	public int getUnldPieces() {
		return unldPieces;
	}

	public void setUnldPieces(int unldPieces) {
		this.unldPieces = unldPieces;
	}

	
	public String getSerialNoStr() {
		return serialNoStr;
	}

	public void setSerialNoStr(String serialNoStr) {
		this.serialNoStr = serialNoStr;
	}

	public String getUnPackingStr() {
		return unPackingStr;
	}

	public void setUnPackingStr(String unPackingStr) {
		this.unPackingStr = unPackingStr;
	}
}
