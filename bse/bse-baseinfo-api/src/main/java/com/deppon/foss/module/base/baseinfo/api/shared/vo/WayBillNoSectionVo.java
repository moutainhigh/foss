package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.WayBillNoSectionEntity;

/**
 * 运单号段信息VO
 * @author 262036 HuangWei
 *
 * @date 2015-6-17 上午10:53:59
 */
public class WayBillNoSectionVo implements Serializable{

	/**
     * serialVersionUID.
     */
	private static final long serialVersionUID = 10593343951L;

	/**
	 * 运单号段维护实体类
	 */
	private WayBillNoSectionEntity entity;
	
	/**
	 * 运单号段信息集合
	 */
	private List<WayBillNoSectionEntity> entityList;
	
	/**
	 * 运单号段ID
	 */
	private String id;
	
	/**
	 * 运单号段ID集合
	 */
	private List<String> idList;
	
	public WayBillNoSectionEntity getEntity() {
		return entity;
	}

	public void setEntity(WayBillNoSectionEntity entity) {
		this.entity = entity;
	}

	public List<WayBillNoSectionEntity> getEntityList() {
		return entityList;
	}

	public void setEntityList(List<WayBillNoSectionEntity> entityList) {
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
	
}
