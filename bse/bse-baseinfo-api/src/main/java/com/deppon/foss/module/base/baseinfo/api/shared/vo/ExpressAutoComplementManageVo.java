package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressAutoComplementManageEntity;
/**
 * 
 * @author 218392 zhangyongxue
 * @date 2015-05-11 14:16:33
 * 快递自动补码管理
 *
 */
public class ExpressAutoComplementManageVo implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 快递自动补码管理实体expressAutoComplementManageEntity
	 */
	private ExpressAutoComplementManageEntity expressAutoComplementManageEntity;
	
	/**
	 * 快递自动补码管理实体类集合expressAutoComplementManageEntityList
	 */
	private List<ExpressAutoComplementManageEntity> expressAutoComplementManageEntityList;
	
	/**
	 * 快递自动补码管理id
	 */
	private String id;
	
	/**
	 * 快递自动补码管理集合id
	 */
	private List<String> idList;

	public ExpressAutoComplementManageEntity getExpressAutoComplementManageEntity() {
		return expressAutoComplementManageEntity;
	}

	public void setExpressAutoComplementManageEntity(
			ExpressAutoComplementManageEntity expressAutoComplementManageEntity) {
		this.expressAutoComplementManageEntity = expressAutoComplementManageEntity;
	}

	public List<ExpressAutoComplementManageEntity> getExpressAutoComplementManageEntityList() {
		return expressAutoComplementManageEntityList;
	}

	public void setExpressAutoComplementManageEntityList(
			List<ExpressAutoComplementManageEntity> expressAutoComplementManageEntityList) {
		this.expressAutoComplementManageEntityList = expressAutoComplementManageEntityList;
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
