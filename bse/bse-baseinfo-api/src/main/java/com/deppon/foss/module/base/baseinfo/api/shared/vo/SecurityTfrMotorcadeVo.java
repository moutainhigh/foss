package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SecurityTfrMotorcadeEntity;

/**
 * TODO(SecurityTfrMotorcade的Vo类)
 * @author 187862-dujunhui
 * @date 2014-5-15 上午10:24:18
 * @since
 * @version
 */
public class SecurityTfrMotorcadeVo implements Serializable {

	/**
	 * TODO（序列化）
	 */
	private static final long serialVersionUID = 7489077383477845131L;
    private SecurityTfrMotorcadeEntity entity;
    private List<SecurityTfrMotorcadeEntity> entityList;
    private String[] codeList;
	/**
	 * @return  the entity
	 */
	public SecurityTfrMotorcadeEntity getEntity() {
		return entity;
	}
	/**
	 * @param entity the entity to set
	 */
	public void setEntity(SecurityTfrMotorcadeEntity entity) {
		this.entity = entity;
	}
	/**
	 * @return  the entityList
	 */
	public List<SecurityTfrMotorcadeEntity> getEntityList() {
		return entityList;
	}
	/**
	 * @param entityList the entityList to set
	 */
	public void setEntityList(List<SecurityTfrMotorcadeEntity> entityList) {
		this.entityList = entityList;
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
