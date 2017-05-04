package com.deppon.foss.module.pickup.pda.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesMotorcadeEntity;

/**
 * 司机所服务的部门接口
 * @author 310430
 *
 */
public interface IPsalesMotorcadeService extends IService {
	
	/**
	 * 车队所属的营业部查询
	 * @param entity
	 * @return
	 */
	public List<SalesMotorcadeEntity> querySalesMotorcadeExactByEntity(SalesMotorcadeEntity entity);

}
