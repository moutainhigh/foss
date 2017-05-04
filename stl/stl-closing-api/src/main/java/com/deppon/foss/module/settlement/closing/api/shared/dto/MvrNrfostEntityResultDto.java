package com.deppon.foss.module.settlement.closing.api.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfostEntity;

/**
 * 始发应收结果Dto
 * 
 * @author foss-qiaolifeng
 * @date 2013-3-6 下午2:37:18
 */
public class MvrNrfostEntityResultDto implements Serializable {

	/**
	 * 始发应收结果Dto序列号
	 */
	private static final long serialVersionUID = -5705755996586186463L;

	/**
	 * 存储始发应收统计信息的实体
	 */
	private MvrNrfostEntity mvrNrfostEntity = new MvrNrfostEntity();

	/**
	 * 始发应收结果列表
	 */
	private List<MvrNrfostEntity> mvrNrfostEntityList = new ArrayList<MvrNrfostEntity>();

	/**
	 * 始发应收总条数
	 */
	private Long mvrNrfostEntityTotalRows;

	public List<MvrNrfostEntity> getMvrNrfostEntityList() {
		return mvrNrfostEntityList;
	}

	public void setMvrNrfostEntityList(List<MvrNrfostEntity> mvrNrfostEntityList) {
		this.mvrNrfostEntityList = mvrNrfostEntityList;
	}

	public Long getMvrNrfostEntityTotalRows() {
		return mvrNrfostEntityTotalRows;
	}

	public void setMvrNrfostEntityTotalRows(Long mvrNrfostEntityTotalRows) {
		this.mvrNrfostEntityTotalRows = mvrNrfostEntityTotalRows;
	}

	public MvrNrfostEntity getMvrNrfostEntity() {
		return mvrNrfostEntity;
	}

	public void setMvrNrfostEntity(MvrNrfostEntity mvrNrfostEntity) {
		this.mvrNrfostEntity = mvrNrfostEntity;
	}

}
