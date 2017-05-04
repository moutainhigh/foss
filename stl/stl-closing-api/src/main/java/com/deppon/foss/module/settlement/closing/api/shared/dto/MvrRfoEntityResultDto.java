package com.deppon.foss.module.settlement.closing.api.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrRfoEntity;

/**
 * 始发应收结果Dto
 * 
 * @author foss-qiaolifeng
 * @date 2013-3-6 下午2:37:18
 */
public class MvrRfoEntityResultDto implements Serializable {

	/**
	 * 始发应收结果Dto序列号
	 */
	private static final long serialVersionUID = -5705755996586186463L;

	/**
	 * 存储始发应收统计信息的实体
	 */
	private MvrRfoEntity mvrRfoEntity = new MvrRfoEntity();

	/**
	 * 始发应收结果列表
	 */
	private List<MvrRfoEntity> mvrRfoEntityList = new ArrayList<MvrRfoEntity>();

	/**
	 * 始发应收总条数
	 */
	private Long mvrRfoEntityTotalRows;

	public List<MvrRfoEntity> getMvrRfoEntityList() {
		return mvrRfoEntityList;
	}

	public void setMvrRfoEntityList(List<MvrRfoEntity> mvrRfoEntityList) {
		this.mvrRfoEntityList = mvrRfoEntityList;
	}

	public Long getMvrRfoEntityTotalRows() {
		return mvrRfoEntityTotalRows;
	}

	public void setMvrRfoEntityTotalRows(Long mvrRfoEntityTotalRows) {
		this.mvrRfoEntityTotalRows = mvrRfoEntityTotalRows;
	}

	public MvrRfoEntity getMvrRfoEntity() {
		return mvrRfoEntity;
	}

	public void setMvrRfoEntity(MvrRfoEntity mvrRfoEntity) {
		this.mvrRfoEntity = mvrRfoEntity;
	}

}
