package com.deppon.foss.module.settlement.closing.api.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfosoEntity;

/**
 * 始发应收结果Dto
 * 
 * @author ddw
 * @date 2013-11-08
 */
public class MvrNrfosoEntityResultDto implements Serializable {

	/**
	 * 始发应收结果Dto序列号
	 */
	private static final long serialVersionUID = -5705755996586186463L;

	/**
	 * 存储始发应收统计信息的实体
	 */
	private MvrNrfosoEntity mvrNrfosoEntity = new MvrNrfosoEntity();

	/**
	 * 始发应收结果列表
	 */
	private List<MvrNrfosoEntity> mvrNrfosoEntityList = new ArrayList<MvrNrfosoEntity>();

	/**
	 * 始发应收总条数
	 */
	private Long mvrNrfosoEntityTotalRows;

	public List<MvrNrfosoEntity> getMvrNrfosoEntityList() {
		return mvrNrfosoEntityList;
	}

	public void setMvrNrfosoEntityList(List<MvrNrfosoEntity> mvrNrfosoEntityList) {
		this.mvrNrfosoEntityList = mvrNrfosoEntityList;
	}

	public Long getMvrNrfosoEntityTotalRows() {
		return mvrNrfosoEntityTotalRows;
	}

	public void setMvrNrfosoEntityTotalRows(Long mvrNrfosoEntityTotalRows) {
		this.mvrNrfosoEntityTotalRows = mvrNrfosoEntityTotalRows;
	}

	public MvrNrfosoEntity getMvrNrfosoEntity() {
		return mvrNrfosoEntity;
	}

	public void setMvrNrfosoEntity(MvrNrfosoEntity mvrNrfosoEntity) {
		this.mvrNrfosoEntity = mvrNrfosoEntity;
	}

}
