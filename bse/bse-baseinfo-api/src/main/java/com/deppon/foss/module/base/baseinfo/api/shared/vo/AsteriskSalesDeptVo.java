package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AsteriskSalesDeptEntity;

/**
 * 加星标营业部信息VO
 * 
 * @author 132599-foss-shenweihua
 * @date 2013-5-4 上午9:28:12
 * @since
 * @version
 */

public class AsteriskSalesDeptVo implements Serializable{

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -9076695065332030828L;
	
	/**
	 * 加星标营业部信息维护实体类
	 */
	private AsteriskSalesDeptEntity entity;
	
	/**
	 * 加星标营业部信息集合
	 */
	private List<AsteriskSalesDeptEntity> entityList;
	
	/**
	 * 加星标营业部VirtualCode集合
	 */
	private List<String> asteriskDeptVirtualCodes;
	
	/**
	 * 加星标营业部VirtualCode
	 */
	private String asteriskDeptVirtualCode;
	
	
	/**
	 * 获取加星标营业部信息维护实体类
	 */
	public AsteriskSalesDeptEntity getEntity() {
		return entity;
	}
	
	/**
	 * 设置加星标营业部信息维护实体类
	 */
	public void setEntity(AsteriskSalesDeptEntity entity) {
		this.entity = entity;
	}
	
	/**
	 * 获取加星标营业部信息维护实体类集合
	 */
	public List<AsteriskSalesDeptEntity> getEntityList() {
		return entityList;
	}
	
	/**
	 * 设置加星标营业部信息维护实体类集合
	 */
	public void setEntityList(List<AsteriskSalesDeptEntity> entityList) {
		this.entityList = entityList;
	}
	
	/**
	 * 获取加星标营业部VirtualCode集合
	 */
	public List<String> getAsteriskDeptVirtualCodes() {
		return asteriskDeptVirtualCodes;
	}
	
	/**
	 * 设置加星标营业部VirtualCode集合
	 */
	public void setAsteriskDeptVirtualCodes(List<String> asteriskDeptVirtualCodes) {
		this.asteriskDeptVirtualCodes = asteriskDeptVirtualCodes;
	}
	
	/**
	 * 获取加星标营业部VirtualCode
	 */
	public String getAsteriskDeptVirtualCode() {
		return asteriskDeptVirtualCode;
	}
	
	/**
	 * 设置加星标营业部VirtualCode
	 */
	public void setAsteriskDeptVirtualCode(String asteriskDeptVirtualCode) {
		this.asteriskDeptVirtualCode = asteriskDeptVirtualCode;
	}
	
}
