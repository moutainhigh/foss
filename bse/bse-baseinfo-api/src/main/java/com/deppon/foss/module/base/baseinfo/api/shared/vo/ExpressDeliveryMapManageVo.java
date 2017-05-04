package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliveryMapManageEntity;

/**
 * 快递派送电子地图管理的VO类
 * @author 187862-dujunhui
 * @date 2014-12-18 下午7:33:19
 * @since
 * @version v1.0
 */
public class ExpressDeliveryMapManageVo implements Serializable {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -5242412564776134693L;
	
	//快递派送电子地图管理实体
	private ExpressDeliveryMapManageEntity entity;
	
	//快递派送电子地图管理实体实体链表
	private List<ExpressDeliveryMapManageEntity> entityList;
	
	//信息批量处理的codeList
	private String[] codeList;

	public ExpressDeliveryMapManageEntity getEntity() {
		return entity;
	}

	public void setEntity(ExpressDeliveryMapManageEntity entity) {
		this.entity = entity;
	}

	public List<ExpressDeliveryMapManageEntity> getEntityList() {
		return entityList;
	}

	public void setEntityList(List<ExpressDeliveryMapManageEntity> entityList) {
		this.entityList = entityList;
	}

	public String[] getCodeList() {
		return codeList;
	}

	public void setCodeList(String[] codeList) {
		this.codeList = codeList;
	}
	
}
