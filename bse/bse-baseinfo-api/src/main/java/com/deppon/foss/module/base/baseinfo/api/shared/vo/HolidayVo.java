/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.HolidayEntity;

/**
 * 法定节假日基础资料Vo
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:187862,date:2015-3-19 下午3:04:39,content:TODO </p>
 * @author 187862-dujunhui 
 * @date 2015-3-19 下午3:04:39
 * @since
 * @version
 */
public class HolidayVo implements Serializable{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 法定节假日基础资料实体类
	 */
	private HolidayEntity entity;
	/**
	 * 法定节假日基础资料链表
	 */
	private List<HolidayEntity> entityList;
	/**
	 * 法定节假日名称数组
	 */
	private String[] ids;
	/**
	 * @return  the entity
	 */
	public HolidayEntity getEntity() {
		return entity;
	}
	/**
	 * @param entity the entity to set
	 */
	public void setEntity(HolidayEntity entity) {
		this.entity = entity;
	}
	/**
	 * @return  the entityList
	 */
	public List<HolidayEntity> getEntityList() {
		return entityList;
	}
	/**
	 * @param entityList the entityList to set
	 */
	public void setEntityList(List<HolidayEntity> entityList) {
		this.entityList = entityList;
	}
	/**
	 * @return  the ids
	 */
	public String[] getIds() {
		return ids;
	}
	/**
	 * @param ids the ids to set
	 */
	public void setIds(String[] ids) {
		this.ids = ids;
	}

}
