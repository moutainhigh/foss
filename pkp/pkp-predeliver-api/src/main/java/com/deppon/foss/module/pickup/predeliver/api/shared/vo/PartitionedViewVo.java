package com.deppon.foss.module.pickup.predeliver.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AddressLabel;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PartitionedViewDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillDetailBillArrageDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillToArrangeDto;

public class PartitionedViewVo implements Serializable{
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 输入单号右移单号
	 */
	private String waybillNo;
	
	/**
	 * 保存已分区查询结果集-大区结果集
	 */
	private List<PartitionedViewDto> partitionBigResults;
	
	/**
	 * 保存已分区查询结果集-小区结果集
	 */
	private List<PartitionedViewDto> partitionSmallResults;
	
	/**
	 * 地址标记集合
	 */
	private List<AddressLabel> addressLabel;
	
	public List<AddressLabel> getAddressLabel() {
		return addressLabel;
	}

	public void setAddressLabel(List<AddressLabel> addressLabel) {
		this.addressLabel = addressLabel;
	}

	/**
	 * 分区查看查询条件
	 */
	private PartitionedViewDto partitionedViewDto;
	
	/**
	 * 分区查看汇总信息
	 */
	private PartitionedViewDto partitionedViewTotal;
	
	/**
	 * 已排单运单的集合
	 */
	private List<WaybillDetailBillArrageDto> waybillDetailArrageDtos;
	
	public List<WaybillDetailBillArrageDto> getWaybillDetailArrageDtos() {
		return waybillDetailArrageDtos;
	}

	public void setWaybillDetailArrageDtos(
			List<WaybillDetailBillArrageDto> waybillDetailArrageDtos) {
		this.waybillDetailArrageDtos = waybillDetailArrageDtos;
	}

	public PartitionedViewDto getPartitionedViewTotal() {
		return partitionedViewTotal;
	}

	public void setPartitionedViewTotal(PartitionedViewDto partitionedViewTotal) {
		this.partitionedViewTotal = partitionedViewTotal;
	}

	private String[] waybillNos;//特殊查询条件
	private String[] notwaybillNos;//特殊查询条件
	
	public String[] getWaybillNos() {
		return waybillNos;
	}

	public void setWaybillNos(String[] waybillNos) {
		this.waybillNos = waybillNos;
	}

	public String[] getNotwaybillNos() {
		return notwaybillNos;
	}

	public void setNotwaybillNos(String[] notwaybillNos) {
		this.notwaybillNos = notwaybillNos;
	}

	/**
	 * 区域所属的运单集合
	 */
	private String[] waybills;
	
	/** 
	 * 派送单实体类
	 */
	private DeliverbillEntity deliverbill = new DeliverbillEntity();
	
	/**
	 * 已排单运单集合
	 */
	private List<WaybillToArrangeDto> waybillToArrangeDtoList;

	public List<WaybillToArrangeDto> getWaybillToArrangeDtoList() {
		return waybillToArrangeDtoList;
	}

	public void setWaybillToArrangeDtoList(
			List<WaybillToArrangeDto> waybillToArrangeDtoList) {
		this.waybillToArrangeDtoList = waybillToArrangeDtoList;
	}

	public DeliverbillEntity getDeliverbill() {
		return deliverbill;
	}

	public List<PartitionedViewDto> getPartitionBigResults() {
		return partitionBigResults;
	}

	public PartitionedViewDto getPartitionedViewDto() {
		return partitionedViewDto;
	}

	public List<PartitionedViewDto> getPartitionSmallResults() {
		return partitionSmallResults;
	}

	public void setDeliverbill(DeliverbillEntity deliverbill) {
		this.deliverbill = deliverbill;
	}

	public void setPartitionBigResults(List<PartitionedViewDto> partitionResults) {
		partitionBigResults = partitionResults;
	}

	public void setPartitionedViewDto(PartitionedViewDto partitionedViewDto) {
		this.partitionedViewDto = partitionedViewDto;
	}

	public void setPartitionSmallResults(
			List<PartitionedViewDto> partitionSmallResults) {
		this.partitionSmallResults = partitionSmallResults;
	}

	public String[] getWaybills() {
		return waybills;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public void setWaybills(String[] waybills) {
		this.waybills = waybills;
	}
	
	
}
