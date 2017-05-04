package com.deppon.foss.module.settlement.agency.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.agency.api.shared.dto.BillPayableAgencyDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;

/**
 * 查询_审核_作废空运其它应付Vo参数实体
 * @author foss-pengzhen 
 * @date 2012-10-24 下午4:44:56
 * @since
 * @version
 */
public class BillPayableAgencyVo implements Serializable {

	/**
	 * Vo参数实体类序列号
	 */
	private static final long serialVersionUID = 2627063750840599353L;
	

	/**
	 * 应付单列表
	 */
	private List<BillPayableAgencyDto> billPayableAgencyDtos;

	/**
	 * 应付单dto
	 */
	private BillPayableAgencyDto billPayableAgencyDto;

	/**
	 * 合大票清单
	 */
	private AirPickupbillEntity airPickupbillEntity;
	

	/**
	 * 航空正单
	 */
	private AirWaybillEntity airWaybillEntity;

	/**
	 * @return  the billPayableAgencyDtos
	 */
	public List<BillPayableAgencyDto> getBillPayableAgencyDtos() {
		return billPayableAgencyDtos;
	}


	
	/**
	 * @param billPayableAgencyDtos the billPayableAgencyDtos to set
	 */
	public void setBillPayableAgencyDtos(
			List<BillPayableAgencyDto> billPayableAgencyDtos) {
		this.billPayableAgencyDtos = billPayableAgencyDtos;
	}


	/**
	 * @return  the billPayableAgencyDto
	 */
	public BillPayableAgencyDto getBillPayableAgencyDto() {
		return billPayableAgencyDto;
	}

	
	/**
	 * @param billPayableAgencyDto the billPayableAgencyDto to set
	 */
	public void setBillPayableAgencyDto(BillPayableAgencyDto billPayableAgencyDto) {
		this.billPayableAgencyDto = billPayableAgencyDto;
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
