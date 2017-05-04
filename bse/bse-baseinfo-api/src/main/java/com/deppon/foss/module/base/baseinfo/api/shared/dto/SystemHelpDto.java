package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SystemHelpEntity;


/**
 * 系统帮助Dto
 * @author foss-qiaolifeng
 * @date 2013-8-6 下午3:45:08
 */
public class SystemHelpDto {
	
	/**
	 * 系统帮助实体
	 */
	private SystemHelpEntity systemHelpEntity;

	
	/**
	 * @get
	 * @return systemHelpEntity
	 */
	public SystemHelpEntity getSystemHelpEntity() {
		/*
		 * @get
		 * @return systemHelpEntity
		 */
		return systemHelpEntity;
	}

	
	/**
	 * @set
	 * @param systemHelpEntity
	 */
	public void setSystemHelpEntity(SystemHelpEntity systemHelpEntity) {
		/*
		 *@set
		 *@this.systemHelpEntity = systemHelpEntity
		 */
		this.systemHelpEntity = systemHelpEntity;
	}
	
	
}
