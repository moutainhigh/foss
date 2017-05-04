package com.deppon.foss.module.transfer.platform.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrPersonnelActualEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrPersonnelBudgetEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrPersonnelBudgetDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrPersonnelBudgetQcDto;

public class TfrCtrPersonnelBudgetVo implements Serializable{

	private static final long serialVersionUID = 7464115943691628158L;

	/**
	 * 外场预算人员查询条件
	 */
	private TfrCtrPersonnelBudgetQcDto tfrCtrPersonnelBudgetQcDto;
	
	/**
	 * 外场预算人员查询结果列表
	 */
	private List<TfrCtrPersonnelBudgetDto> tfrCtrPersonnelBudgetDtos;
	
	/**
	 * 外场预算人员
	 * 用于新增、修改
	 */
	private TfrCtrPersonnelBudgetEntity tfrCtrPersonnelBudgetEntity;
	
	/**
	 * 外场实际人员
	 * 用于新增、修改
	 */
	private TfrCtrPersonnelActualEntity tfrCtrPersonnelActualEntity;
	
	/**
	 * 当前部门所属外场编码
	 * 1、用于判断查询界面的部门是否可编辑
	 */
	private String parentTfrCtrCode;
	
	/**
	 * 当前部门所属外场名称
	 */
	private String parentTfrCtrName;

	public TfrCtrPersonnelBudgetQcDto getTfrCtrPersonnelBudgetQcDto() {
		return tfrCtrPersonnelBudgetQcDto;
	}

	public void setTfrCtrPersonnelBudgetQcDto(
			TfrCtrPersonnelBudgetQcDto tfrCtrPersonnelBudgetQcDto) {
		this.tfrCtrPersonnelBudgetQcDto = tfrCtrPersonnelBudgetQcDto;
	}

	public List<TfrCtrPersonnelBudgetDto> getTfrCtrPersonnelBudgetDtos() {
		return tfrCtrPersonnelBudgetDtos;
	}

	public void setTfrCtrPersonnelBudgetDtos(
			List<TfrCtrPersonnelBudgetDto> tfrCtrPersonnelBudgetDtos) {
		this.tfrCtrPersonnelBudgetDtos = tfrCtrPersonnelBudgetDtos;
	}

	public TfrCtrPersonnelBudgetEntity getTfrCtrPersonnelBudgetEntity() {
		return tfrCtrPersonnelBudgetEntity;
	}

	public void setTfrCtrPersonnelBudgetEntity(
			TfrCtrPersonnelBudgetEntity tfrCtrPersonnelBudgetEntity) {
		this.tfrCtrPersonnelBudgetEntity = tfrCtrPersonnelBudgetEntity;
	}

	public TfrCtrPersonnelActualEntity getTfrCtrPersonnelActualEntity() {
		return tfrCtrPersonnelActualEntity;
	}

	public void setTfrCtrPersonnelActualEntity(
			TfrCtrPersonnelActualEntity tfrCtrPersonnelActualEntity) {
		this.tfrCtrPersonnelActualEntity = tfrCtrPersonnelActualEntity;
	}

	public String getParentTfrCtrCode() {
		return parentTfrCtrCode;
	}

	public void setParentTfrCtrCode(String parentTfrCtrCode) {
		this.parentTfrCtrCode = parentTfrCtrCode;
	}

	public String getParentTfrCtrName() {
		return parentTfrCtrName;
	}

	public void setParentTfrCtrName(String parentTfrCtrName) {
		this.parentTfrCtrName = parentTfrCtrName;
	}
}
