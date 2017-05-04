package com.deppon.foss.module.transfer.load.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.ConnectionBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ConnectionBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillSerialNoDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.NewConnectionBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryConnectionBillConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryWayBillForConnectionBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.UpdateConnectionBillDto;

/** 
 * @className: ConnectionBillVo
 * @author: 205109-foss-zenghaibin
 * @description: 接驳交接单模块之Vo类
 * @date: 2015-04-09 上午8:52:53
 */

public class ConnectionBillVo implements Serializable {

	private static final long serialVersionUID = -7990369287797626870L;
	
	private String orgCode;//当前登录部门编码
	
	private String orgName;//部门名称
	
	private String transferCenter;//是否外场
	
	private String connectionBillNo;//接驳交接单号
	
	private String waybillNo;//运单号
	
	private String errorMessage;//错误信息
	
	//车牌号
	private String vehicleNo;
	
	//交接单号list
	//流水号的list
	private  List<HandOverBillSerialNoDetailEntity> serialNoList;
	
	/**接驳交接单实体**/
	private ConnectionBillEntity connectionBillEntity;
	
	//接驳交接单管理-->查询交接单的条件
	private QueryConnectionBillConditionDto queryConnectionBillConditionDto;
	//交接单查询结果集
	private List<ConnectionBillEntity> connectionBillList;
	//
	private NewConnectionBillDto newConnectionBillDto ;
	/**
	 * 定义查询交接运单界面的库存运单list
	 */
	private List<ConnectionBillDetailEntity> waybillStockList;
	
	private QueryWayBillForConnectionBillDto queryWayBillForConnectionBillDto;
	
	private UpdateConnectionBillDto updateConnectionBillDto;
	
	public List<ConnectionBillEntity> getConnectionBillList() {
		return connectionBillList;
	}

	public void setConnectionBillList(List<ConnectionBillEntity> connectionBillList) {
		this.connectionBillList = connectionBillList;
	}

	public QueryConnectionBillConditionDto getQueryConnectionBillConditionDto() {
		return queryConnectionBillConditionDto;
	}

	public void setQueryConnectionBillConditionDto(
			QueryConnectionBillConditionDto queryConnectionBillConditionDto) {
		this.queryConnectionBillConditionDto = queryConnectionBillConditionDto;
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

	public String getConnectionBillNo() {
		return connectionBillNo;
	}

	public void setConnectionBillNo(String connectionBillNo) {
		this.connectionBillNo = connectionBillNo;
	}
	
	public List<ConnectionBillDetailEntity> getWaybillStockList() {
		return waybillStockList;
	}

	public void setWaybillStockList(
			List<ConnectionBillDetailEntity> waybillStockList) {
		this.waybillStockList = waybillStockList;
	}

	public QueryWayBillForConnectionBillDto getQueryWayBillForConnectionBillDto() {
		return queryWayBillForConnectionBillDto;
	}

	public void setQueryWayBillForConnectionBillDto(
			QueryWayBillForConnectionBillDto queryWayBillForConnectionBillDto) {
		this.queryWayBillForConnectionBillDto = queryWayBillForConnectionBillDto;
	}

	public NewConnectionBillDto getNewConnectionBillDto() {
		return newConnectionBillDto;
	}

	public void setNewConnectionBillDto(NewConnectionBillDto newConnectionBillDto) {
		this.newConnectionBillDto = newConnectionBillDto;
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

	public List<HandOverBillSerialNoDetailEntity> getSerialNoList() {
		return serialNoList;
	}

	public void setSerialNoList(List<HandOverBillSerialNoDetailEntity> serialNoList) {
		this.serialNoList = serialNoList;
	}

	public UpdateConnectionBillDto getUpdateConnectionBillDto() {
		return updateConnectionBillDto;
	}

	public void setUpdateConnectionBillDto(
			UpdateConnectionBillDto updateConnectionBillDto) {
		this.updateConnectionBillDto = updateConnectionBillDto;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	
}