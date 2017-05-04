package com.deppon.foss.module.settlement.agency.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.agency.api.shared.dto.BillReceivableAgencyDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;

/**
 * 查询_审核_作废空运其它应收Vo参数实体
 * @author foss-pengzhen 
 * @date 2012-10-24 下午4:44:56
 * @since
 * @version
 */
public class BillReceivableAgencyVo implements Serializable {

	/**
	 * Vo参数实体类序列号
	 */
	private static final long serialVersionUID = 2627063750840599353L;

	/**
	 * 应收单列表
	 */
	private List<BillReceivableAgencyDto> billReceivableAgencyDtos;

	/**
	 * 应收单dto
	 */
	private BillReceivableAgencyDto billReceivableAgencyDto;
	
	/**
	 * 合大票清单
	 */
	private AirPickupbillEntity airPickupbillEntity;
	
	/**
	 * 航空正单
	 */
	private AirWaybillEntity airWaybillEntity;

	/**
	 * @return  the billReceivableAgencyDtos
	 */
	public List<BillReceivableAgencyDto> getBillReceivableAgencyDtos() {
		return billReceivableAgencyDtos;
	}


	
	/**
	 * @param billReceivableAgencyDtos the billReceivableAgencyDtos to set
	 */
	public void setBillReceivableAgencyDtos(
			List<BillReceivableAgencyDto> billReceivableAgencyDtos) {
		this.billReceivableAgencyDtos = billReceivableAgencyDtos;
	}


	/**
	 * @return  the billReceivableAgencyDto
	 */
	public BillReceivableAgencyDto getBillReceivableAgencyDto() {
		return billReceivableAgencyDto;
	}

	
	/**
	 * @param billReceivableAgencyDto the billReceivableAgencyDto to set
	 */
	public void setBillReceivableAgencyDto(
			BillReceivableAgencyDto billReceivableAgencyDto) {
		this.billReceivableAgencyDto = billReceivableAgencyDto;
	}
	
	
	public AirPickupbillEntity getAirPickupbillEntity() {
		return airPickupbillEntity;
	}

	public void setAirPickupbillEntity(AirPickupbillEntity airPickupbillEntity) {
		this.airPickupbillEntity = airPickupbillEntity;
	}
	

	public AirWaybillEntity getAirWaybillEntity() {
		return airWaybillEntity;
	}


	public void setAirWaybillEntity(AirWaybillEntity airWaybillEntity) {
		this.airWaybillEntity = airWaybillEntity;
	}


}
