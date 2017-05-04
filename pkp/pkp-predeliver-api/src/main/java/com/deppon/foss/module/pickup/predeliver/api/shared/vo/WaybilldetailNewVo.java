package com.deppon.foss.module.pickup.predeliver.api.shared.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverHandoverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillDeliverNewCountDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillDetailBillArrageDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillDetailNewQueryDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillToArrangeDto;
import com.deppon.foss.module.transfer.load.api.shared.domain.DiffReportReturnNumEntity;


/**
 * 
 * @author 159231 meiying
 * 2015-6-3  下午4:25:28
 */
public class WaybilldetailNewVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WaybillDetailNewQueryDto waybillDetailNewQueryDto;
	private List<WaybillDetailDto> WaybillDetailDtos;
	private WaybillDeliverNewCountDto waybillDeliverNewCountDto=new WaybillDeliverNewCountDto();
	// 派送单
	private DeliverbillEntity deliverbill = new DeliverbillEntity();
	private List<WaybillDetailBillArrageDto> waybillDetailArrageDtos;
	//派送单状态
	private String deliverStatus;
	/**
	 * 运单号
	 */
	private String waybillNo;
	private Date deliverDate;
	/**
     * 实际车牌号(人工在创建派送单(新)预处理建议里修改)
     */
    private String actualVehicleNo;
	private List<WaybillToArrangeDto> waybillToArrangeDtoList;
	/**
	 * 派送交单实体
	 */
	private DeliverHandoverbillEntity deliverHandoverbillEntity;
	
	/*
	 * 运单中的收货联系人
	 */
	private String receiveCustomerContact;
	
	/*
	 * 退单次数实体
	 */
	private DiffReportReturnNumEntity diffReportReturnNumEntity;
	
	/**
	 * 当前已排gird的id和运单号
	 */
	private String dragStr;
	
	/**
	 * 批量修改预处理建议
	 */
	private List<DeliverHandoverbillEntity> deliverHandoverbillEntityList;
	
	public List<DeliverHandoverbillEntity> getDeliverHandoverbillEntityList() {
		return deliverHandoverbillEntityList;
	}
	public void setDeliverHandoverbillEntityList(
			List<DeliverHandoverbillEntity> deliverHandoverbillEntityList) {
		this.deliverHandoverbillEntityList = deliverHandoverbillEntityList;
	}
	
	/**
	 * 获取waybillDetailNewQueryDto  
	 * @return waybillDetailNewQueryDto waybillDetailNewQueryDto
	 */
	public WaybillDetailNewQueryDto getWaybillDetailNewQueryDto() {
		return waybillDetailNewQueryDto;
	}
	/**
	 * 设置waybillDetailNewQueryDto  
	 * @param waybillDetailNewQueryDto waybillDetailNewQueryDto 
	 */
	public void setWaybillDetailNewQueryDto(
			WaybillDetailNewQueryDto waybillDetailNewQueryDto) {
		this.waybillDetailNewQueryDto = waybillDetailNewQueryDto;
	}
	/**
	 * 获取waybillDetailDtos  
	 * @return waybillDetailDtos waybillDetailDtos
	 */
	public List<WaybillDetailDto> getWaybillDetailDtos() {
		return WaybillDetailDtos;
	}
	/**
	 * 设置waybillDetailDtos  
	 * @param waybillDetailDtos waybillDetailDtos 
	 */
	public void setWaybillDetailDtos(List<WaybillDetailDto> waybillDetailDtos) {
		WaybillDetailDtos = waybillDetailDtos;
	}
	/**
	 * 获取waybillDeliverNewCountDto  
	 * @return waybillDeliverNewCountDto waybillDeliverNewCountDto
	 */
	public WaybillDeliverNewCountDto getWaybillDeliverNewCountDto() {
		return waybillDeliverNewCountDto;
	}
	/**
	 * 设置waybillDeliverNewCountDto  
	 * @param waybillDeliverNewCountDto waybillDeliverNewCountDto 
	 */
	public void setWaybillDeliverNewCountDto(
			WaybillDeliverNewCountDto waybillDeliverNewCountDto) {
		this.waybillDeliverNewCountDto = waybillDeliverNewCountDto;
	}
	/**
	 * 获取deliverHandoverbillEntity  
	 * @return deliverHandoverbillEntity deliverHandoverbillEntity
	 */
	public DeliverHandoverbillEntity getDeliverHandoverbillEntity() {
		return deliverHandoverbillEntity;
	}
	/**
	 * 设置deliverHandoverbillEntity  
	 * @param deliverHandoverbillEntity deliverHandoverbillEntity 
	 */
	public void setDeliverHandoverbillEntity(
			DeliverHandoverbillEntity deliverHandoverbillEntity) {
		this.deliverHandoverbillEntity = deliverHandoverbillEntity;
	}
	/**
	 * 获取deliverbill  
	 * @return deliverbill deliverbill
	 */
	public DeliverbillEntity getDeliverbill() {
		return deliverbill;
	}
	/**
	 * 设置deliverbill  
	 * @param deliverbill deliverbill 
	 */
	public void setDeliverbill(DeliverbillEntity deliverbill) {
		this.deliverbill = deliverbill;
	}
	/**
	 * 获取deliverStatus  
	 * @return deliverStatus deliverStatus
	 */
	public String getDeliverStatus() {
		return deliverStatus;
	}
	/**
	 * 设置deliverStatus  
	 * @param deliverStatus deliverStatus 
	 */
	public void setDeliverStatus(String deliverStatus) {
		this.deliverStatus = deliverStatus;
	}
	/**
	 * 获取waybillToArrangeDtoList  
	 * @return waybillToArrangeDtoList waybillToArrangeDtoList
	 */
	public List<WaybillToArrangeDto> getWaybillToArrangeDtoList() {
		return waybillToArrangeDtoList;
	}
	/**
	 * 设置waybillToArrangeDtoList  
	 * @param waybillToArrangeDtoList waybillToArrangeDtoList 
	 */
	public void setWaybillToArrangeDtoList(
			List<WaybillToArrangeDto> waybillToArrangeDtoList) {
		this.waybillToArrangeDtoList = waybillToArrangeDtoList;
	}
	/**
	 * 获取waybillDetailArrageDtos  
	 * @return waybillDetailArrageDtos waybillDetailArrageDtos
	 */
	public List<WaybillDetailBillArrageDto> getWaybillDetailArrageDtos() {
		return waybillDetailArrageDtos;
	}
	/**
	 * 设置waybillDetailArrageDtos  
	 * @param waybillDetailArrageDtos waybillDetailArrageDtos 
	 */
	public void setWaybillDetailArrageDtos(
			List<WaybillDetailBillArrageDto> waybillDetailArrageDtos) {
		this.waybillDetailArrageDtos = waybillDetailArrageDtos;
	}
	/**
	 * 获取waybillNo  
	 * @return waybillNo waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	/**
	 * 设置waybillNo  
	 * @param waybillNo waybillNo 
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	/**
	 * 获取deliverDate  
	 * @return deliverDate deliverDate
	 */
	public Date getDeliverDate() {
		return deliverDate;
	}
	/**
	 * 设置deliverDate  
	 * @param deliverDate deliverDate 
	 */
	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}
	/**
	 * 获取actualVehicleNo  
	 * @return actualVehicleNo actualVehicleNo
	 */
	public String getActualVehicleNo() {
		return actualVehicleNo;
	}
	/**
	 * 设置actualVehicleNo  
	 * @param actualVehicleNo actualVehicleNo 
	 */
	public void setActualVehicleNo(String actualVehicleNo) {
		this.actualVehicleNo = actualVehicleNo;
	}
	
	public String getDragStr() {
		return dragStr;
	}
	
	public void setDragStr(String dragStr) {
		this.dragStr = dragStr;
	}
	
	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}
	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public DiffReportReturnNumEntity getDiffReportReturnNumEntity() {
		return diffReportReturnNumEntity;
	}
	public void setDiffReportReturnNumEntity(
			DiffReportReturnNumEntity diffReportReturnNumEntity) {
		this.diffReportReturnNumEntity = diffReportReturnNumEntity;
	}
	
}
