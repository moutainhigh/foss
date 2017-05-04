package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import java.util.List;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity;

public interface ISendPackagingSupplierToCUBCService {
	/**
	 * 
	 * 给结算中心同步包装商信息
	 * @author 269231
	 * @param dtos
	 * @param OperateType  操作类型
	 */
	void SyncPackagingSupplier(List<PackagingSupplierEntity> packagingSupplier, Integer operateType);
}
