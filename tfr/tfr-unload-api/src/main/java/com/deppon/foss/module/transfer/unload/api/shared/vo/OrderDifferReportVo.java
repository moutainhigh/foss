package com.deppon.foss.module.transfer.unload.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.unload.api.shared.domain.OrderDifferReportEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderDifferReportDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderReportDetailDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderReportSerialNoDto;


public class OrderDifferReportVo  implements Serializable{

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 点单差异报告编号
	 */
	private String reportNo;
	/**
	 * 交单编号
	 */
	private String handoverNo;
	/**
	 * 运单号
	 */
	private String  waybillNo;
	/**
	 * 差异原因
	 */
	private String id;
	/**
	 * 点单差异报告 查询实体
	 */
	private OrderDifferReportEntity orderDifferReportEntity;
	/**
	 * 点单差异报告 流水
	 */
	private OrderReportSerialNoDto orderReportSerialNoDto;
	/**
	 * 点单差异报告List
	 */
	private List<OrderDifferReportDto> orderDifferReportList;
	/**
	 * 点单差异报告运单明细List
	 */
	private List<OrderReportDetailDto> orderReportDetailList;
	/**
	 * 点单差异报告流水明细List
	 */
	private List<OrderReportSerialNoDto> orderReportSerialNoList;
	/**
	 * 点单任务实体
	 */
	private OrderDifferReportEntity orderDifferReport;
	/**
	 * @return  the orderDifferReport
	 */
	public OrderDifferReportEntity getOrderDifferReport() {
		return orderDifferReport;
	}
	/**
	 * @param orderDifferReport the orderDifferReport to set
	 */
	public void setOrderDifferReport(OrderDifferReportEntity orderDifferReport) {
		this.orderDifferReport = orderDifferReport;
	}
	/**
	 * 点单差异报告 查询实体
	 * @return  the orderDifferReportEntity
	 */
	public OrderDifferReportEntity getOrderDifferReportEntity() {
		return orderDifferReportEntity;
	}
	/**
	 * 点单差异报告 查询实体
	 * @param orderDifferReportEntity the orderDifferReportEntity to set
	 */
	public void setOrderDifferReportEntity(
			OrderDifferReportEntity orderDifferReportEntity) {
		this.orderDifferReportEntity = orderDifferReportEntity;
	}
	/**
	 * 点单差异报告List
	 * @return  the orderDifferReportList
	 */
	public List<OrderDifferReportDto> getOrderDifferReportList() {
		return orderDifferReportList;
	}
	/**
	 * 点单差异报告List
	 * @param orderDifferReportList the orderDifferReportList to set
	 */
	public void setOrderDifferReportList(
			List<OrderDifferReportDto> orderDifferReportList) {
		this.orderDifferReportList = orderDifferReportList;
	}
	/**
	 * 点单差异报告编号
	 * @return  the reportNo
	 */
	public String getReportNo() {
		return reportNo;
	}
	/**
	 * 点单差异报告编号
	 * @param reportNo the reportNo to set
	 */
	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}
	/**
	 * 点单差异报告运单明细List
	 * @return  the orderReportDetailList
	 */
	public List<OrderReportDetailDto> getOrderReportDetailList() {
		return orderReportDetailList;
	}
	/**
	 * 点单差异报告运单明细List
	 * @param orderReportDetailList the orderReportDetailList to set
	 */
	public void setOrderReportDetailList(
			List<OrderReportDetailDto> orderReportDetailList) {
		this.orderReportDetailList = orderReportDetailList;
	}

	/**
	 * 点单差异报告流水明细List
	 * @return  the orderReportSerialNoList
	 */
	public List<OrderReportSerialNoDto> getOrderReportSerialNoList() {
		return orderReportSerialNoList;
	}
	/**
	 * 点单差异报告流水明细List
	 * @param orderReportSerialNoList the orderReportSerialNoList to set
	 */
	public void setOrderReportSerialNoList(
			List<OrderReportSerialNoDto> orderReportSerialNoList) {
		this.orderReportSerialNoList = orderReportSerialNoList;
	}
	/**
	 * 交单编号
	 * @return  the handoverNo
	 */
	public String getHandoverNo() {
		return handoverNo;
	}
	/**
	 * 交单编号
	 * @param handoverNo the handoverNo to set
	 */
	public void setHandoverNo(String handoverNo) {
		this.handoverNo = handoverNo;
	}
	/**
	 * 运单号
	 * @return  the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	/**
	 * 运单号
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	/**
	 * @return  the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 点单差异报告 流水
	 * @return  the orderReportSerialNoDto
	 */
	public OrderReportSerialNoDto getOrderReportSerialNoDto() {
		return orderReportSerialNoDto;
	}
	/**
	 * 点单差异报告 流水
	 * @param orderReportSerialNoDto the orderReportSerialNoDto to set
	 */
	public void setOrderReportSerialNoDto(
			OrderReportSerialNoDto orderReportSerialNoDto) {
		this.orderReportSerialNoDto = orderReportSerialNoDto;
	}
	
}
