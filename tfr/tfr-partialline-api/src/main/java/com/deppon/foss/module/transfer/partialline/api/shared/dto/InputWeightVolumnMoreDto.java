/**
 * @author foss 257200
 * 2015-11-13
 * 257220
 */
package com.deppon.foss.module.transfer.partialline.api.shared.dto;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.partialline.api.shared.domain.InputWeightVolumnEntity;

/**
 * @author 257220
 *
 */
public class InputWeightVolumnMoreDto extends InputWeightVolumnEntity{
	//是否能修改
	private String canModify;
	//交接单号
	private List<String> handOverBillList;
	//运单号
	private List<String> waybillNoList;
	//交接开始时间
	private Date beginHandOverTime;
	//交接结束时间
	private Date endHandOverTime;
	//入库开始时间
	private Date beginInStockTime;
	//入库结束时间
	private Date endInStockTime;
	//外场code
	private String deptCode;
	//件数
	private String goodsQtyTotal;
	public String getCanModify() {
		return canModify;
	}
	public void setCanModify(String canModify) {
		this.canModify = canModify;
	}
	public List<String> getHandOverBillList() {
		return handOverBillList;
	}
	public void setHandOverBillList(List<String> handOverBillList) {
		this.handOverBillList = handOverBillList;
	}
	public List<String> getWaybillNoList() {
		return waybillNoList;
	}
	public void setWaybillNoList(List<String> waybillNoList) {
		this.waybillNoList = waybillNoList;
	}
	public Date getBeginHandOverTime() {
		return beginHandOverTime;
	}
	public void setBeginHandOverTime(Date beginHandOverTime) {
		this.beginHandOverTime = beginHandOverTime;
	}
	public Date getEndHandOverTime() {
		return endHandOverTime;
	}
	public void setEndHandOverTime(Date endHandOverTime) {
		this.endHandOverTime = endHandOverTime;
	}
	public Date getBeginInStockTime() {
		return beginInStockTime;
	}
	public void setBeginInStockTime(Date beginInStockTime) {
		this.beginInStockTime = beginInStockTime;
	}
	public Date getEndInStockTime() {
		return endInStockTime;
	}
	public void setEndInStockTime(Date endInStockTime) {
		this.endInStockTime = endInStockTime;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getGoodsQtyTotal() {
		return goodsQtyTotal;
	}
	public void setGoodsQtyTotal(String goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}
}
