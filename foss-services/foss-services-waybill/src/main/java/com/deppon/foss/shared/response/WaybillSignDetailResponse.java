package com.deppon.foss.shared.response;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.vo.WaybillSignDetailVo;
import com.deppon.foss.util.define.FossConstants;
/**
 * 快递签收状态详细信息
 * @author 272311
 *
 */
public class WaybillSignDetailResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5027528588659195386L;

	/**
	 * 数据总数
	 */
	private int totalCount =0 ;
	
	/**
	 * 存放查询结果
	 */
	private List<WaybillSignDetailVo> waybillSignDetailVoList  ;
	
	/**
	 * 开始日期
	 */
	private Date startTime ;
	/**
	 * 结束日期
	 */
	private Date endTime ;
	
	/**
	 * 总票数
	 */
	private int totalWaybill = 0 ;
	/**
	 * 已签收票数
	 */
	private int signWaybill = 0 ;
	
	/**
	 * 运输中票数
	 */
	private int transWaybill = 0 ;
	/**
	 * 派件途中票数
	 */
	private int dispatchWaybill = 0 ;
	/**
	 * 异常签收票数
	 */
	private int excepSignWaybill = 0 ;
	/**
	 * 作废票数
	 */
	private int cancelWaybill = 0 ;
	/**
	 * 退回票数
	 */
	private int backWaybill = 0 ;
	/**
	 * 遗失票数
	 */
	private int loseWaybill = 0 ;
	/**
	 * 签收率
	 */
	private String signRatio  ;
	
	/**
	 * 是否成功
	 */
	private String success = FossConstants.YES;
	
	/**
	 * 错误信息
	 */
	private String errorMsg ;
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public List<WaybillSignDetailVo> getWaybillSignDetailVoList() {
		return waybillSignDetailVoList;
	}
	public void setWaybillSignDetailVoList(
			List<WaybillSignDetailVo> waybillSignDetailVoList) {
		this.waybillSignDetailVoList = waybillSignDetailVoList;
	}
	
	public int getTotalWaybill() {
		return totalWaybill;
	}
	public void setTotalWaybill(int totalWaybill) {
		this.totalWaybill = totalWaybill;
	}
	public int getSignWaybill() {
		return signWaybill;
	}
	public void setSignWaybill(int signWaybill) {
		this.signWaybill = signWaybill;
	}
	public int getTransWaybill() {
		return transWaybill;
	}
	public void setTransWaybill(int transWaybill) {
		this.transWaybill = transWaybill;
	}
	public int getDispatchWaybill() {
		return dispatchWaybill;
	}
	public void setDispatchWaybill(int dispatchWaybill) {
		this.dispatchWaybill = dispatchWaybill;
	}
	public int getExcepSignWaybill() {
		return excepSignWaybill;
	}
	public void setExcepSignWaybill(int excepSignWaybill) {
		this.excepSignWaybill = excepSignWaybill;
	}
	public int getCancelWaybill() {
		return cancelWaybill;
	}
	public void setCancelWaybill(int cancelWaybill) {
		this.cancelWaybill = cancelWaybill;
	}
	public int getBackWaybill() {
		return backWaybill;
	}
	public void setBackWaybill(int backWaybill) {
		this.backWaybill = backWaybill;
	}
	public String getSignRatio() {
		return signRatio;
	}
	public void setSignRatio(String signRatio) {
		this.signRatio = signRatio;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public int getLoseWaybill() {
		return loseWaybill;
	}
	public void setLoseWaybill(int loseWaybill) {
		this.loseWaybill = loseWaybill;
	}
	@Override
	public String toString() {
		return "WaybillSignDetailResponse [totalCount=" + totalCount
				+ ", totalWaybill=" + totalWaybill + ", signWaybill="
				+ signWaybill + ", transWaybill=" + transWaybill
				+ ", dispatchWaybill=" + dispatchWaybill
				+ ", excepSignWaybill=" + excepSignWaybill + ", cancelWaybill="
				+ cancelWaybill + ", backWaybill=" + backWaybill
				+ ", loseWaybill=" + loseWaybill + ", signRatio=" + signRatio
				+ ", success=" + success + ", errorMsg=" + errorMsg 
				+ ", startTime=" + startTime + ", endTime=" + endTime
				+ "]";
	}

	
}
