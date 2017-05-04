package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity;

/**
 * TODO(描述类的职责)
 * @author 187862-杜军辉
 * @date 2014-5-8 下午4:57:23
 * @since
 * @version
 */
public class PackagingSupplierVo implements Serializable{
	
	/**
	 * TODO（序列化）
	 */
	private static final long serialVersionUID = -8732146313017872796L;

	/**
	 * 供应商信息实体
	 **/
	private PackagingSupplierEntity entity;
	
	/**
	 * 供应商信息实体链表
	 **/
	private List<PackagingSupplierEntity> EntityList;
	
	/**
	 * 需要删除的供应商信息id数组
	 **/
	private String[] codeList;

	
	/**
	 * @return  the entity
	 */
	public PackagingSupplierEntity getEntity() {
		return entity;
	}

	/**
	 * @param entity the entity to set
	 */
	public void setEntity(PackagingSupplierEntity entity) {
		this.entity = entity;
	}

	/**
	 * @return  the entityList
	 */
	public List<PackagingSupplierEntity> getEntityList() {
		return EntityList;
	}

	/**
	 * @param entityList the entityList to set
	 */
	public void setEntityList(List<PackagingSupplierEntity> entityList) {
		EntityList = entityList;
	}

	/**
	 * @return  the codeList
	 */
	public String[] getCodeList() {
		return codeList;
	}

	/**
	 * @param codeList the codeList to set
	 */
	public void setCodeList(String[] codeList) {
		this.codeList = codeList;
	}
	
	

}
