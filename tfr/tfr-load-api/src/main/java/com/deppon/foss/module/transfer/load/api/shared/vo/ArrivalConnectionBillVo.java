package com.deppon.foss.module.transfer.load.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.ConnectionBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ConnectionBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillSerialNoDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryArrivalConnectionBillConditionDto;

/** 
 * @className: ArrivalConnectionBillVo
 * @author: 218427-foss-hongwy
 * @description: 接驳交接单到达模块之Vo类
 * @date: 2015-10-20 上午9:51:53
 */

public class ArrivalConnectionBillVo implements Serializable {


	private static final long serialVersionUID = 1L;

	private String orgCode;//当前登录部门编码
	
	private String orgName;//部门名称
	
	private String transferCenter;//是否外场
	
	private String arrivalConnectionBillNo;//接驳交接单号
	
	private String waybillNo;//运单号
	
	private String errorMessage;//错误信息
	
	//车牌号
	private String vehicleNo;
	
	/**接驳交接单实体**/
	private ConnectionBillEntity connectionBillEntity;
	
	//接驳交接单管理-->查询交接单的条件
	private QueryArrivalConnectionBillConditionDto queryArrivalConnectionBillConditionDto;
	//交接单查询结果集
	private List<ConnectionBillEntity> connectionBillList;
	
	//交接单号list
	//流水号的list
	private  List<HandOverBillSerialNoDetailEntity> serialNoList;
	/**
	 * 定义查询交接运单界面的库存运单list
	 */
	private List<ConnectionBillDetailEntity> waybillStockList;
	
	
	public List<ConnectionBillEntity> getConnectionBillList() {
		return connectionBillList;
	}

	public void setConnectionBillList(List<ConnectionBillEntity> connectionBillList) {
		this.connectionBillList = connectionBillList;
	}



	public QueryArrivalConnectionBillConditionDto getQueryArrivalConnectionBillConditionDto() {
		return queryArrivalConnectionBillConditionDto;
	}

	public void setQueryArrivalConnectionBillConditionDto(
			QueryArrivalConnectionBillConditionDto queryArrivalConnectionBillConditionDto) {
		this.queryArrivalConnectionBillConditionDto = queryArrivalConnectionBillConditionDto;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getTransferCenter() {
		return transferCenter;
	}

	public void setTransferCenter(String transferCenter) {
		this.transferCenter = transferCenter;
	}

   
	public String getArrivalConnectionBillNo() {
		return arrivalConnectionBillNo;
	}

	public void setArrivalConnectionBillNo(String arrivalConnectionBillNo) {
		this.arrivalConnectionBillNo = arrivalConnectionBillNo;
	}

	public List<ConnectionBillDetailEntity> getWaybillStockList() {
		return waybillStockList;
	}

	public void setWaybillStockList(
			List<ConnectionBillDetailEntity> waybillStockList) {
		this.waybillStockList = waybillStockList;
	}

    public ConnectionBillEntity getConnectionBillEntity() {
		return connectionBillEntity;
	}

	public void setConnectionBillEntity(ConnectionBillEntity connectionBillEntity) {
		this.connectionBillEntity = connectionBillEntity;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public List<HandOverBillSerialNoDetailEntity> getSerialNoList() {
		return serialNoList;
	}

	public void setSerialNoList(List<HandOverBillSerialNoDetailEntity> serialNoList) {
		this.serialNoList = serialNoList;
	}
	
	
}