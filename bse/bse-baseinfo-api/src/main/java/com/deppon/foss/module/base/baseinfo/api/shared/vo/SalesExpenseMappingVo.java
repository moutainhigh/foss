package com.deppon.foss.module.base.baseinfo.api.shared.vo;
import java.util.List;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesExpenseMappingEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SalesExpenseMappingQueryDto;
/**
 * 营业部与外请车费用承担部门映射信息
 * @author 307196
 *
 */
public class SalesExpenseMappingVo {


	private SalesExpenseMappingQueryDto salesExpenseMappingQueryDto;
	/**
	 * 营业部与外请车费用承担部门映射信息
	 */
	private SalesExpenseMappingEntity salesExpenseMappingEntity;
	
	/**
	 * 营业部与外请车费用承担部门映射信息返回结果集
	 */
	private List<SalesExpenseMappingEntity> salesExpenseMappingEntityList;

	public SalesExpenseMappingQueryDto getSalesExpenseMappingQueryDto() {
		return salesExpenseMappingQueryDto;
	}

	public void setSalesExpenseMappingQueryDto(
			SalesExpenseMappingQueryDto salesExpenseMappingQueryDto) {
		this.salesExpenseMappingQueryDto = salesExpenseMappingQueryDto;
	}

	public SalesExpenseMappingEntity getSalesExpenseMappingEntity() {
		return salesExpenseMappingEntity;
	}

	public void setSalesExpenseMappingEntity(
			SalesExpenseMappingEntity salesExpenseMappingEntity) {
		this.salesExpenseMappingEntity = salesExpenseMappingEntity;
	}

	public List<SalesExpenseMappingEntity> getSalesExpenseMappingEntityList() {
		return salesExpenseMappingEntityList;
	}

	public void setSalesExpenseMappingEntityList(
			List<SalesExpenseMappingEntity> salesExpenseMappingEntityList) {
		this.salesExpenseMappingEntityList = salesExpenseMappingEntityList;
	}
	



	

	

	



}
