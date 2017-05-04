package com.deppon.foss.module.settlement.closing.api.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfontEntity;

/**
 * 始发应收结果Dto
 * 
 * @author foss-qiaolifeng
 * @date 2013-3-6 下午2:37:18
 */
public class MvrNrfontEntityResultDto implements Serializable {

	/**
	 * 始发应收结果Dto序列号
	 */
	private static final long serialVersionUID = -5705755996586186463L;

	/**
	 * 存储始发应收统计信息的实体
	 */
	private MvrNrfontEntity mvrNrfontEntity = new MvrNrfontEntity();

	/**
	 * 始发应收结果列表
	 */
	private List<MvrNrfontEntity> mvrNrfontEntityList = new ArrayList<MvrNrfontEntity>();

	/**
	 * 始发应收总条数
	 */
	private Long mvrNrfontEntityTotalRows;

	public List<MvrNrfontEntity> getMvrNrfontEntityList() {
		return mvrNrfontEntityList;
	}

	public void setMvrNrfontEntityList(List<MvrNrfontEntity> mvrNrfontEntityList) {
		this.mvrNrfontEntityList = mvrNrfontEntityList;
	}

	public Long getMvrNrfontEntityTotalRows() {
		return mvrNrfontEntityTotalRows;
	}

	public void setMvrNrfontEntityTotalRows(Long mvrNrfontEntityTotalRows) {
		this.mvrNrfontEntityTotalRows = mvrNrfontEntityTotalRows;
	}

	public MvrNrfontEntity getMvrNrfontEntity() {
		return mvrNrfontEntity;
	}

	public void setMvrNrfontEntity(MvrNrfontEntity mvrNrfontEntity) {
		this.mvrNrfontEntity = mvrNrfontEntity;
	}

}
