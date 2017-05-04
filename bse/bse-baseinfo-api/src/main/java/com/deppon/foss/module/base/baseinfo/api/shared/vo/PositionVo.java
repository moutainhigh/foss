package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.PositionEntity;
/**
 * 职位VO
 * 
 * @author 130346-foss-lifanghong
 * @date 2014-4-29 下午4:10:24
 */
public class PositionVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7531788370655564157L;
	  // 行政区域列表
    private List<PositionEntity> positionList;
    private PositionEntity positionEntity;
    
	public PositionEntity getPositionEntity() {
		return positionEntity;
	}
	public void setPositionEntity(PositionEntity positionEntity) {
		this.positionEntity = positionEntity;
	}
	public List<PositionEntity> getPositionList() {
		return positionList;
	}
	public void setPositionList(List<PositionEntity> positionList) {
		this.positionList = positionList;
	}
    
    
}
