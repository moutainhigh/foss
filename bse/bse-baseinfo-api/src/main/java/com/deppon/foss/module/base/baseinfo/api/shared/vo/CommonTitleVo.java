package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonTitleEntity;

/**
 * action和前台传递值实体
 * 
 * @author dujunhui-187862
 * @date 2014-08-07 下午6:07:58
 */
public class CommonTitleVo implements Serializable {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 2629160509193950089L;

	/**
	 * 传递到前台的组织集合
	 */
	private List<CommonTitleEntity> titleEntityList;
	
	/**
	 * 职位公共选择器实体
	 */
	private CommonTitleEntity entity;

	/**
	 * @return  the titleEntityList
	 */
	public List<CommonTitleEntity> getTitleEntityList() {
		return titleEntityList;
	}

	/**
	 * @param titleEntityList the titleEntityList to set
	 */
	public void setTitleEntityList(List<CommonTitleEntity> titleEntityList) {
		this.titleEntityList = titleEntityList;
	}

	/**
	 * @return  the entity
	 */
	public CommonTitleEntity getEntity() {
		return entity;
	}

	/**
	 * @param entity the entity to set
	 */
	public void setEntity(CommonTitleEntity entity) {
		this.entity = entity;
	}

}
