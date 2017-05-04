package com.deppon.foss.module.settlement.closing.api.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfonoEntity;

/**
 * 始发应收结果Dto
 * 
 * @author ddw
 * @date 2013-11-08
 */
public class MvrNrfonoEntityResultDto implements Serializable {

	/**
	 * 始发应收结果Dto序列号
	 */
	private static final long serialVersionUID = -5705755996586186463L;

	/**
	 * 存储始发应收统计信息的实体
	 */
	private MvrNrfonoEntity mvrNrfonoEntity = new MvrNrfonoEntity();

	/**
	 * 始发应收结果列表
	 */
	private List<MvrNrfonoEntity> mvrNrfonoEntityList = new ArrayList<MvrNrfonoEntity>();

	/**
	 * 始发应收总条数
	 */
	private Long mvrNrfonoEntityTotalRows;

	public List<MvrNrfonoEntity> getMvrNrfonoEntityList() {
		return mvrNrfonoEntityList;
	}

	public void setMvrNrfonoEntityList(List<MvrNrfonoEntity> mvrNrfonoEntityList) {
		this.mvrNrfonoEntityList = mvrNrfonoEntityList;
	}

	public Long getMvrNrfonoEntityTotalRows() {
		return mvrNrfonoEntityTotalRows;
	}

	public void setMvrNrfonoEntityTotalRows(Long mvrNrfonoEntityTotalRows) {
		this.mvrNrfonoEntityTotalRows = mvrNrfonoEntityTotalRows;
	}

	public MvrNrfonoEntity getMvrNrfonoEntity() {
		return mvrNrfonoEntity;
	}

	public void setMvrNrfonoEntity(MvrNrfonoEntity mvrNrfonoEntity) {
		this.mvrNrfonoEntity = mvrNrfonoEntity;
	}

}
