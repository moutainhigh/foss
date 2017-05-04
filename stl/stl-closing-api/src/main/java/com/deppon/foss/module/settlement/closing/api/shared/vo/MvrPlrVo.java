package com.deppon.foss.module.settlement.closing.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPlrEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrPlrDto;



/**
 * 偏线月报VO.
 * @author 095793-foss-LiQin
 * @date 2013-3-19 上午8:42:12
 */
public class MvrPlrVo implements Serializable {

	/** 偏线月报序列化. */
	private static final long serialVersionUID = 8556923795321514861L;
	
	/** 偏线月报dto. */
	private MvrPlrDto mvrplrDto;
	
	
	
	/** The query list. */
	private List<MvrPlrEntity> queryList;

	/**
	 * Gets the mvrplr dto.
	 *
	 * @return list
	 */
	public MvrPlrDto getMvrplrDto() {
		return mvrplrDto;
	}

	/**
	 * Sets the mvrplr dto.
	 *
	 * @param mvrplrDto the new mvrplr dto
	 */
	public void setMvrplrDto(MvrPlrDto mvrplrDto) {
		this.mvrplrDto = mvrplrDto;
	}

	
	/**
	 * Gets the query list.
	 *
	 * @return queryList
	 */
	public List<MvrPlrEntity> getQueryList() {
		return queryList;
	}

	
	/**
	 * Sets the query list.
	 *
	 * @param queryList the new query list
	 */
	public void setQueryList(List<MvrPlrEntity> queryList) {
		this.queryList = queryList;
	}
	
}
