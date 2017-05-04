package com.deppon.foss.module.transfer.pda.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class PDAUnloadDiffReportDto implements Serializable {

	private static final long serialVersionUID = 5860761012354946507L;

	//卸车差异报告编号
	private String diffReportNo;
	//车牌号
	private String vehicleNo;
	//少货件数
	private int lackGoodsPieces;
	//报告生成时间
	private Date reportCreateTime;
	//多货件数
	private int exceedGoodsQty;
	//操作员工号
	private String opreateCode;

	public String getDiffReportNo() {
		return diffReportNo;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public int getLackGoodsPieces() {
		return lackGoodsPieces;
	}
	public Date getReportCreateTime() {
		return reportCreateTime;
	}
	public void setDiffReportNo(String diffReportNo) {
		this.diffReportNo = diffReportNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public void setLackGoodsPieces(int lackGoodsPieces) {
		this.lackGoodsPieces = lackGoodsPieces;
	}
	public void setReportCreateTime(Date reportCreateTime) {
		this.reportCreateTime = reportCreateTime;
	}
	public int getExceedGoodsQty() {
		return exceedGoodsQty;
	}
	public void setExceedGoodsQty(int exceedGoodsQty) {
		this.exceedGoodsQty = exceedGoodsQty;
	}
	public String getOpreateCode() {
		return opreateCode;
	}
	public void setOpreateCode(String opreateCode) {
		this.opreateCode = opreateCode;
	}
}
