package com.deppon.foss.module.settlement.pay.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.pay.api.shared.domain.WithholdingEntity;
import com.deppon.foss.module.settlement.pay.api.shared.domain.WithholdingEntityDetail;
import com.deppon.foss.module.settlement.pay.api.shared.dto.RentCarReportDto;

/**
 * @author 045738
 *
 */
public class RentCarReportVo implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 5337851318731979487L;
	
	/**
	 * dto
	 */
	private RentCarReportDto dto;

	/**
	 * 结果集
	 */
	private List<RentCarReportDto> resultList;
	/**
	 * 定义重复运单集合
	 */
	private List<WithholdingEntityDetail> repeatList;
	
	/**
	 * 预提实体
	 */
	private WithholdingEntity withholdingEntity;
	
	public RentCarReportDto getDto() {
		return dto;
	}

	public void setDto(RentCarReportDto dto) {
		this.dto = dto;
	}

	public List<RentCarReportDto> getResultList() {
		return resultList;
	}

	public void setResultList(List<RentCarReportDto> resultList) {
		this.resultList = resultList;
	}

	public List<WithholdingEntityDetail> getRepeatList() {
		return repeatList;
	}

	public void setRepeatList(List<WithholdingEntityDetail> repeatList) {
		this.repeatList = repeatList;
	}

	public WithholdingEntity getWithholdingEntity() {
		return withholdingEntity;
	}

	public void setWithholdingEntity(WithholdingEntity withholdingEntity) {
		this.withholdingEntity = withholdingEntity;
	}
}
