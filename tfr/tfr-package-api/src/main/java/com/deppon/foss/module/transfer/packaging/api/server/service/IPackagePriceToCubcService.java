package com.deppon.foss.module.transfer.packaging.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.PackingToCubcEntity;

/**
 * 同步打木架信息至CUBC
 * 
 * @author 316759-RuipengWang-foss
 */
public interface IPackagePriceToCubcService extends IService {

	/**
	 * 同步新增打木架信息至CUBC
	 * 
	 * @param packingToCubcEntitys
	 */
	public void pushAddPackingRecAndPay(List<PackingToCubcEntity> packingToCubcEntitys);

}
