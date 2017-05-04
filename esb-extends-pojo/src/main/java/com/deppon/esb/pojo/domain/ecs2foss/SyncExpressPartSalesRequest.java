package com.deppon.esb.pojo.domain.ecs2foss;

import java.util.List;

public class SyncExpressPartSalesRequest {
	private List<ExpressPartSalesDeptEcsEntity> expressPartSalesDeptList;

	public List<ExpressPartSalesDeptEcsEntity> getExpressPartSalesDeptList() {
		return expressPartSalesDeptList;
	}

	public void setExpressPartSalesDeptList(
			List<ExpressPartSalesDeptEcsEntity> expressPartSalesDeptList) {
		this.expressPartSalesDeptList = expressPartSalesDeptList;
	}

}
