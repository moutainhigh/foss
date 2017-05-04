package com.deppon.foss.module.settlement.closing.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrOrccIEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrOrcciQueryDto;

/**
 * 外请车往来报表查询VO
 * @author 073615
 *
 */
public class MvrOrccIVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 查询条件dto
	 */
	private MvrOrcciQueryDto mvrOrcciQueryDto;
	
	/**
	 * 查询结果实体
	 */

	private List<MvrOrccIEntity> orcciEntities;

	public MvrOrcciQueryDto getMvrOrcciQueryDto() {
		return mvrOrcciQueryDto;
	}

	public void setMvrOrcciQueryDto(MvrOrcciQueryDto mvrOrcciQueryDto) {
		this.mvrOrcciQueryDto = mvrOrcciQueryDto;
	}

	public List<MvrOrccIEntity> getOrcciEntities() {
		return orcciEntities;
	}

	public void setOrcciEntities(List<MvrOrccIEntity> orcciEntities) {
		this.orcciEntities = orcciEntities;
	}
	
	
}
