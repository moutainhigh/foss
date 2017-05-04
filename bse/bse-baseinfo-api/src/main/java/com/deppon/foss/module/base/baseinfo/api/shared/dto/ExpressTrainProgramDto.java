/**
 * 
 */
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressTrainProgramEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;

/**
 *<p>Title: ExpressTrainProgramDto</p>
 * <p>Description:快递支线班车方案Dto </p>
 * <p>Company: Deppon</p>
 * @author    130566-ZengJunfan
 * @date       2014-5-8
 */
public class ExpressTrainProgramDto {
	/**
	 * 方案实体
	 */
	private ExpressTrainProgramEntity expressTrainProgramEntity;
	/**
	 * 给GIS的外场实体
	 */
	private SysToGisOrgDto outFieldDto;
	/**
	 * 给GIS的营业部集合
	 */
	private List<SysToGisOrgDto> salesDtoList;
	/**
	 * 所覆盖的营业部集合
	 */
	private List<SaleDepartmentEntity> salesList;
	/**
	 * @return the expressTrainProgramEntity
	 */
	public ExpressTrainProgramEntity getExpressTrainProgramEntity() {
		return expressTrainProgramEntity;
	}
	/**
	 * @param expressTrainProgramEntity the expressTrainProgramEntity to set
	 */
	public void setExpressTrainProgramEntity(
			ExpressTrainProgramEntity expressTrainProgramEntity) {
		this.expressTrainProgramEntity = expressTrainProgramEntity;
	}
	/**
	 * @return the salesList
	 */
	public List<SaleDepartmentEntity> getSalesList() {
		return salesList;
	}
	/**
	 * @param salesList the salesList to set
	 */
	public void setSalesList(List<SaleDepartmentEntity> salesList) {
		this.salesList = salesList;
	}
	public SysToGisOrgDto getOutFieldDto() {
		return outFieldDto;
	}
	public void setOutFieldDto(SysToGisOrgDto outFieldDto) {
		this.outFieldDto = outFieldDto;
	}
	public List<SysToGisOrgDto> getSalesDtoList() {
		return salesDtoList;
	}
	public void setSalesDtoList(List<SysToGisOrgDto> salesDtoList) {
		this.salesDtoList = salesDtoList;
	}
		
}
