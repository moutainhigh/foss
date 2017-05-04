package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldTFCompanyEntity;

public class OutfieldTFCompanyVo implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 外场与所属运输财务公司关系信息实体类
	 */
	private OutfieldTFCompanyEntity entity;
	
	/**
	 * 外场与所属运输财务公司关系信息实体类集合
	 */
	private List<OutfieldTFCompanyEntity> entityList;
	
	/**
	 * 外场与所属运输财务公司关系信息 id
	 */
	private String id;
	
	/**
	 * 外场与所属运输财务公司关系信息 id集合
	 */
	private List<String> idList;
	
	/**
	 * 运输公司所属外场编码
	 */
	private String outfieldCode;
	
	/**
	 * 获取运输公司所属外场编码
	 * @return
	 */
	public String getOutfieldCode() {
		return outfieldCode;
	}
	
	/**
	 * 设置运输公司所属外场编码
	 * @param outfieldCode
	 */
	public void setOutfieldCode(String outfieldCode) {
		this.outfieldCode = outfieldCode;
	}

	/**
	 * 获取外场与所属运输财务公司关系信息实体类
	 * @return
	 */
	public OutfieldTFCompanyEntity getEntity() {
		return entity;
	}
	
	/**
	 * 设置外场与所属运输财务公司关系信息实体类
	 * @return
	 */
	public void setEntity(OutfieldTFCompanyEntity entity) {
		this.entity = entity;
	}
	
	/**
	 * 获取外场与所属运输财务公司关系信息实体类集合
	 * @return
	 */
	public List<OutfieldTFCompanyEntity> getEntityList() {
		return entityList;
	}
	
	/**
	 * 设置外场与所属运输财务公司关系信息实体类集合
	 * @return
	 */
	public void setEntityList(List<OutfieldTFCompanyEntity> entityList) {
		this.entityList = entityList;
	}
	
	/**
	 * 获取外场与所属运输财务公司关系信息ID
	 * @return
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * 设置外场与所属运输财务公司关系信息ID
	 * @return
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 获取外场与所属运输财务公司关系信息ID集合
	 * @return
	 */
	public List<String> getIdList() {
		return idList;
	}
	
	/**
	 * 设置外场与所属运输财务公司关系信息ID集合
	 * @return
	 */
	public void setIdList(List<String> idList) {
		this.idList = idList;
	}

	

}
