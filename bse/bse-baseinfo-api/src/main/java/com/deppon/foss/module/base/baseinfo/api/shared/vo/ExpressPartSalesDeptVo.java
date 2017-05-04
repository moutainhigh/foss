package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptQueryDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptResultDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressSaleDepartmentResultDto;


/**
 * 快递点部营业部映射关系Vo
 * @author foss-qiaolifeng
 * @date 2013-7-25 上午9:50:12
 */
public class ExpressPartSalesDeptVo implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 7442649950434497743L;
	
	/**
	 * 快递点部营业部关系查询dto
	 */
	private ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto;
	
	/**
	 * 快递点部营业部关系结果dto
	 */
	private ExpressPartSalesDeptResultDto expressPartSalesDeptResultDto;
		
	/**
	 * 快递点部营业部关系结果dto列表
	 */
	private List<ExpressPartSalesDeptResultDto> expressPartSalesDeptResultDtoList;
	
	/**
	 * 营业部扩展实体列表
	 */
	private List<ExpressSaleDepartmentResultDto> saleDepartmentResultDtoList;
	
	/**
	 * 快递点部信息
	 */
	private OrgAdministrativeInfoEntity orgAdministrativeInfoEntity;

	public OrgAdministrativeInfoEntity getOrgAdministrativeInfoEntity() {
		return orgAdministrativeInfoEntity;
	}

	public void setOrgAdministrativeInfoEntity(
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity) {
		this.orgAdministrativeInfoEntity = orgAdministrativeInfoEntity;
	}

	public ExpressPartSalesDeptQueryDto getExpressPartSalesDeptQueryDto() {
		return expressPartSalesDeptQueryDto;
	}

	public void setExpressPartSalesDeptQueryDto(
			ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto) {
		this.expressPartSalesDeptQueryDto = expressPartSalesDeptQueryDto;
	}

	public ExpressPartSalesDeptResultDto getExpressPartSalesDeptResultDto() {
		return expressPartSalesDeptResultDto;
	}

	public void setExpressPartSalesDeptResultDto(
			ExpressPartSalesDeptResultDto expressPartSalesDeptResultDto) {
		this.expressPartSalesDeptResultDto = expressPartSalesDeptResultDto;
	}

	public List<ExpressPartSalesDeptResultDto> getExpressPartSalesDeptResultDtoList() {
		return expressPartSalesDeptResultDtoList;
	}

	public void setExpressPartSalesDeptResultDtoList(
			List<ExpressPartSalesDeptResultDto> expressPartSalesDeptResultDtoList) {
		this.expressPartSalesDeptResultDtoList = expressPartSalesDeptResultDtoList;
	}

	public List<ExpressSaleDepartmentResultDto> getSaleDepartmentResultDtoList() {
		return saleDepartmentResultDtoList;
	}

	public void setSaleDepartmentResultDtoList(
			List<ExpressSaleDepartmentResultDto> saleDepartmentResultDtoList) {
		this.saleDepartmentResultDtoList = saleDepartmentResultDtoList;
	}

}
