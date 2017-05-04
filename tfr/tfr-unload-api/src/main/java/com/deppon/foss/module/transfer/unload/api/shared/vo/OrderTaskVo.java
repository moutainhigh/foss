package com.deppon.foss.module.transfer.unload.api.shared.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillSerialNoDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.OrderSerialNoDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.OrderTaskEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.OtHandOverBillDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderTaskAddnewDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderTaskDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderTaskModifyDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderTaskMoreDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QuerySerialInfoBywaybillNoDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QuerySerialListByTskNumDto;



public class OrderTaskVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//点单任务明细id
	private String id;
	/**
	* 交接单编号
	*/
	private String handoverNo;
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 点单任务编号
	 */
	private String orderTaskNo;
	
	/**
	* 交接单信息
	*/
	private OtHandOverBillDetailEntity billInfo;
	
	private List<OtHandOverBillDetailEntity> billList;

	//流水号list
	private List<HandOverBillSerialNoDetailEntity> serialNoList;
	
	/**
	 * 新增时传入的dto
	 */
	private OrderTaskAddnewDto addnewDto;

	/**
	 * 当前登录部门所属的上级大部门code，包括营业部、派送部、外场、总调
	 */
	private String superOrgCode;
	
	/**
	 * 登录人code
	 */
	private String empCode;
	
	private OrderTaskDto orderTaskDto = new OrderTaskDto();
	
	private List<OrderTaskDto> orderTaskDtoList = new ArrayList<OrderTaskDto>();
	
	/**
	 * 卸车任务基本信息
	 */
	private OrderTaskEntity baseEntity;
	
	/**
	 * 点单任务单据列表
	 */
	private List<OtHandOverBillDetailEntity> billDetailList;
	
	private List<OrderSerialNoDetailEntity> serialNoDetailList;
	
	private List<QuerySerialListByTskNumDto> serialDetailDtoList;
	
	private List<QuerySerialInfoBywaybillNoDto> serialNoInfoList;
	
	/**
	 * 处理点单任务任务时传入的dto
	 */
	private OrderTaskModifyDto orderTaskModifyDto;
	
	private OrderTaskMoreDto  orderTaskMoreDto;
	
	public String getHandoverNo() {
		return handoverNo;
	}

	public void setHandoverNo(String handoverNo) {
		this.handoverNo = handoverNo;
	}

	public OtHandOverBillDetailEntity getBillInfo() {
		return billInfo;
	}

	public void setBillInfo(OtHandOverBillDetailEntity billInfo) {
		this.billInfo = billInfo;
	}

	public List<OtHandOverBillDetailEntity> getBillList() {
		return billList;
	}

	public void setBillList(List<OtHandOverBillDetailEntity> billList) {
		this.billList = billList;
	}

	public String getSuperOrgCode() {
		return superOrgCode;
	}

	public void setSuperOrgCode(String superOrgCode) {
		this.superOrgCode = superOrgCode;
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

	public OrderTaskAddnewDto getAddnewDto() {
		return addnewDto;
	}

	public void setAddnewDto(OrderTaskAddnewDto addnewDto) {
		this.addnewDto = addnewDto;
	}

	public String getOrderTaskNo() {
		return orderTaskNo;
	}

	public void setOrderTaskNo(String orderTaskNo) {
		this.orderTaskNo = orderTaskNo;
	}

	public OrderTaskDto getOrderTaskDto() {
		return orderTaskDto;
	}

	public void setOrderTaskDto(OrderTaskDto orderTaskDto) {
		this.orderTaskDto = orderTaskDto;
	}

	public List<OrderTaskDto> getOrderTaskDtoList() {
		return orderTaskDtoList;
	}

	public void setOrderTaskDtoList(List<OrderTaskDto> orderTaskDtoList) {
		this.orderTaskDtoList = orderTaskDtoList;
	}

	public OrderTaskEntity getBaseEntity() {
		return baseEntity;
	}

	public void setBaseEntity(OrderTaskEntity baseEntity) {
		this.baseEntity = baseEntity;
	}

	public List<OtHandOverBillDetailEntity> getBillDetailList() {
		return billDetailList;
	}

	public void setBillDetailList(List<OtHandOverBillDetailEntity> billDetailList) {
		this.billDetailList = billDetailList;
	}

	public List<OrderSerialNoDetailEntity> getSerialNoDetailList() {
		return serialNoDetailList;
	}

	public void setSerialNoDetailList(List<OrderSerialNoDetailEntity> serialNoDetailList) {
		this.serialNoDetailList = serialNoDetailList;
	}

	public List<QuerySerialInfoBywaybillNoDto> getSerialNoInfoList() {
		return serialNoInfoList;
	}

	public void setSerialNoInfoList(List<QuerySerialInfoBywaybillNoDto> serialNoInfoList) {
		this.serialNoInfoList = serialNoInfoList;
	}

	public OrderTaskModifyDto getOrderTaskModifyDto() {
		return orderTaskModifyDto;
	}

	public void setOrderTaskModifyDto(OrderTaskModifyDto orderTaskModifyDto) {
		this.orderTaskModifyDto = orderTaskModifyDto;
	}

	public OrderTaskMoreDto getOrderTaskMoreDto() {
		return orderTaskMoreDto;
	}

	public void setOrderTaskMoreDto(OrderTaskMoreDto orderTaskMoreDto) {
		this.orderTaskMoreDto = orderTaskMoreDto;
	}

	public List<QuerySerialListByTskNumDto> getSerialDetailDtoList() {
		return serialDetailDtoList;
	}

	public void setSerialDetailDtoList(
			List<QuerySerialListByTskNumDto> serialDetailDtoList) {
		this.serialDetailDtoList = serialDetailDtoList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	
}
