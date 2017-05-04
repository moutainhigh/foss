package com.deppon.foss.module.transfer.load.api.shared.dto;

import com.deppon.foss.module.transfer.load.api.shared.domain.SerialNoStockEntity;

/**
 * 快递库存流水号Dto，区别交接单库存流水号（SerialNoStockEntity），多了一个是否建包的属性，
 * 用于在PC端新建包的时候，已建包的流水号显示已建包，不能被选中。类似交接单中的预配的功能。
 * @author 200978  xiaobingcheng
 * 2014-9-23
 */
public class ExpressPackageSerialNoStockDto extends SerialNoStockEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -682533059707857325L;

	/**是否已建包*/
	private String isCreatedPackage;

	
	
	
	
	public String getIsCreatedPackage() {
		return isCreatedPackage;
	}

	public void setIsCreatedPackage(String isCreatedPackage) {
		this.isCreatedPackage = isCreatedPackage;
	}
	
}
