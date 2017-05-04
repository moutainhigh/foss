package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ProductEntity;

/**
 * action和前台传递值实体
 * 
 * @author dujunhui-187862
 * @date 2014-10-24 下午3:44:52
 */
public class CommonThirdLevelProductVo implements Serializable {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 2629160509193950089L;

	/**
	 * 传递到前台的组织集合
	 */
	private List<ProductEntity> thirdLevelProductEntities;
	
	/**
	 * 公共选择器实体
	 */
	private ProductEntity thirdLevelProductEntity;

	/**
	 * @return  the thirdLevelProductEntities
	 */
	public List<ProductEntity> getThirdLevelProductEntities() {
		return thirdLevelProductEntities;
	}

	/**
	 * @param thirdLevelProductEntities the thirdLevelProductEntities to set
	 */
	public void setThirdLevelProductEntities(
			List<ProductEntity> thirdLevelProductEntities) {
		this.thirdLevelProductEntities = thirdLevelProductEntities;
	}

	/**
	 * @return  the thirdLevelProductEntity
	 */
	public ProductEntity getThirdLevelProductEntity() {
		return thirdLevelProductEntity;
	}

	/**
	 * @param thirdLevelProductEntity the thirdLevelProductEntity to set
	 */
	public void setThirdLevelProductEntity(ProductEntity thirdLevelProductEntity) {
		this.thirdLevelProductEntity = thirdLevelProductEntity;
	}

}