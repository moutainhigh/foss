package com.deppon.foss.module.transfer.load.api.shared.vo;

import java.io.Serializable;
import java.util.Date;

import com.deppon.foss.module.transfer.load.api.shared.domain.LoadSerialNoEntity;

public class ToFossWaybillVo implements Serializable {

	/**
	 * @fields serialVersionUID
	 * @author 328864-foss-xieyang
	 * @update 2016年4月20日 下午6:52:40
	 * @version V1.0
	 */

	private static final long serialVersionUID = 2810292556882614450L;

	// private String waybillNo; //运单号
	// private String beLoaded; // 是否装车
	// private String scanState; // 扫描状态
	// private Date operationTime; //操作时间

	private String cargoType;
	private String cargoNo;
	private Date operationTime;
	private String beLoaded;
	private String scanState;
	private String cargoState;
	
	public LoadSerialNoEntity getLoadSerialNoEntity() {
		LoadSerialNoEntity dto = new LoadSerialNoEntity();
		dto.setBeLoaded(beLoaded);
		dto.setCargoNo(cargoNo);
		dto.setCargoType(cargoType);
		dto.setCreateTime(operationTime);
//		dto.setDeviceNo(deviceNo);
//		dto.setGoodsState(goodsState);
//		dto.setId(id);
		dto.setLoadTime(operationTime);
		dto.setLoadWaybillDetailId(cargoNo);
//		dto.setOrigOrgCode(origOrgCode);
		dto.setScanState(scanState);
		dto.setSerialNo(cargoNo);
		dto.setTaskBeginTime(operationTime);
		dto.setGoodsState(cargoState);
		
		
		return dto;
	}

	public String getCargoType() {
		return cargoType;
	}

	public void setCargoType(String cargoType) {
		this.cargoType = cargoType;
	}

	public String getCargoNo() {
		return cargoNo;
	}

	public void setCargoNo(String cargoNo) {
		this.cargoNo = cargoNo;
	}

	public Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}

	public String getBeLoaded() {
		return beLoaded;
	}

	public void setBeLoaded(String beLoaded) {
		this.beLoaded = beLoaded;
	}

	public String getScanState() {
		return scanState;
	}

	public void setScanState(String scanState) {
		this.scanState = scanState;
	}

	public String getCargoState() {
		return cargoState;
	}

	public void setCargoState(String cargoState) {
		this.cargoState = cargoState;
	}
	
	

}
