package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.HeavyBubbleRatioEntity;

/**
 * @author 218392 张永雪
 *
 * 2014-11-19下午6:29:58
 */
public class HeavyBubbleRatioVo implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * HeavyBubbleRatio重泡比信息维护实体类 entity
	 */
	private HeavyBubbleRatioEntity entity;
	
	/**
	 * 重泡比信息集合
	 */
	private List<HeavyBubbleRatioEntity> entityList;
	
	/**
	 * 重泡比信息的ID
	 */
	private String id;
	
	/**
	 * 重泡比信息集合
	 */
	private List<String> idList;

	/**
	 * 外场
	 * @return
	 */
	private String outfield;
	
	/**
	 * 外场编码
	 * @return
	 */
	private String outfieldCode;
	
	/**
	 * 备注
	 * @return
	 */
	private String remark;
	
	
	public String getOutfield() {
		return outfield;
	}

	public void setOutfield(String outfield) {
		this.outfield = outfield;
	}

	public String getOutfieldCode() {
		return outfieldCode;
	}

	public void setOutfieldCode(String outfieldCode) {
		this.outfieldCode = outfieldCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public HeavyBubbleRatioEntity getEntity() {
		return entity;
	}

	public void setEntity(HeavyBubbleRatioEntity entity) {
		this.entity = entity;
	}

	public List<HeavyBubbleRatioEntity> getEntityList() {
		return entityList;
	}

	public void setEntityList(List<HeavyBubbleRatioEntity> entityList) {
		this.entityList = entityList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getIdList() {
		return idList;
	}

	public void setIdList(List<String> idList) {
		this.idList = idList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
