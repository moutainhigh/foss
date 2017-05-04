package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SystemHelpEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SystemHelpDto;


/**
 * 系统帮助VO
 * @author foss-qiaolifeng
 * @date 2013-8-6 下午3:47:52
 */
public class SystemHelpVo implements Serializable {

	/**
	 * 系统帮助VO序列号
	 */
	private static final long serialVersionUID = 2853986035087238810L;

	/**
	 * 系统帮助Dto
	 */
	private SystemHelpDto systemHelpDto; 
	
	/**
	 * 系统帮助集合
	 */
	private List<SystemHelpEntity> systemHelpEntitys;
	
	/**
	 * ID集合
	 */
	private List<String> idlist;

	
	/**
	 * @get
	 * @return systemHelpDto
	 */
	public SystemHelpDto getSystemHelpDto() {
		/*
		 * @get
		 * @return systemHelpDto
		 */
		return systemHelpDto;
	}

	
	/**
	 * @set
	 * @param systemHelpDto
	 */
	public void setSystemHelpDto(SystemHelpDto systemHelpDto) {
		/*
		 *@set
		 *@this.systemHelpDto = systemHelpDto
		 */
		this.systemHelpDto = systemHelpDto;
	}

	
	/**
	 * @get
	 * @return systemHelpEntitys
	 */
	public List<SystemHelpEntity> getSystemHelpEntitys() {
		/*
		 * @get
		 * @return systemHelpEntitys
		 */
		return systemHelpEntitys;
	}

	
	/**
	 * @set
	 * @param systemHelpEntitys
	 */
	public void setSystemHelpEntitys(List<SystemHelpEntity> systemHelpEntitys) {
		/*
		 *@set
		 *@this.systemHelpEntitys = systemHelpEntitys
		 */
		this.systemHelpEntitys = systemHelpEntitys;
	}

	
	/**
	 * @get
	 * @return idlist
	 */
	public List<String> getIdlist() {
		/*
		 * @get
		 * @return idlist
		 */
		return idlist;
	}

	
	/**
	 * @set
	 * @param idlist
	 */
	public void setIdlist(List<String> idlist) {
		/*
		 *@set
		 *@this.idlist = idlist
		 */
		this.idlist = idlist;
	}
	
	
}
