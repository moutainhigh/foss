package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ShortFieldMapEntity;



public class ShortFieldMapVo  implements Serializable{
	/**
	 *  序列化UID
	 */
	private static final long serialVersionUID = -7940843383948274243L;
	/**
	 *  短距离外场映射关系的实体
	 */
	private ShortFieldMapEntity shortFieldMapEntity;
	/**
	 *  短距离外场映射关系的实体集合
	 */
	private List<ShortFieldMapEntity> shortFieldMapEntityList;
	/**
	 *  id集合，用于批量删除
	 */
	private List<String> ids;
	
	
	/**
	 * 下面为以上三个属性的get、set方法
	 */
	public ShortFieldMapEntity getShortFieldMapEntity() {
		return shortFieldMapEntity;
	}
	public void setShortFieldMapEntity(ShortFieldMapEntity shortFieldMapEntity) {
		this.shortFieldMapEntity = shortFieldMapEntity;
	}
	public List<ShortFieldMapEntity> getShortFieldMapEntityList() {
		return shortFieldMapEntityList;
	}
	public void setShortFieldMapEntityList(
			List<ShortFieldMapEntity> shortFieldMapEntityList) {
		this.shortFieldMapEntityList = shortFieldMapEntityList;
	}
	public List<String> getIds() {
		return ids;
	}
	public void setIds(List<String> ids) {
		this.ids = ids;
	}
}
