package com.deppon.foss.module.transfer.load.api.shared.vo;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadWaybillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SerialNoStockEntity;

/** 
 * @className : DeliverLoadTaskVo
 * @author : ShiWei shiwei@outlook.com
 * @description : 手工确认派送装车任务
 * @date: 2013-4-13 下午4:25:21
 * 
 */
public class DeliverLoadTaskVo {
	
	/**
	 * 派送单号
	 */
	private String billNo;
	
	/**
	 * 派送单信息
	 */
	private DeliverBillEntity billInfo;
	
	/**
	 * 派送单详细信息(运单)
	 */
	private List<LoadWaybillDetailEntity> billDetailList;
	
	/**
	 * 流水号库存list
	 */
	private List<SerialNoStockEntity> serialNoList;
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 派送单最后修改时间，打开时读取，确认时传回，起到时间戳的作用
	 */
	private Date operateTime;

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public List<SerialNoStockEntity> getSerialNoList() {
		return serialNoList;
	}

	public void setSerialNoList(List<SerialNoStockEntity> serialNoList) {
		this.serialNoList = serialNoList;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public DeliverBillEntity getBillInfo() {
		return billInfo;
	}

	public void setBillInfo(DeliverBillEntity billInfo) {
		this.billInfo = billInfo;
	}

	public List<LoadWaybillDetailEntity> getBillDetailList() {
		return billDetailList;
	}

	public void setBillDetailList(List<LoadWaybillDetailEntity> billDetailList) {
		this.billDetailList = billDetailList;
	}

}
