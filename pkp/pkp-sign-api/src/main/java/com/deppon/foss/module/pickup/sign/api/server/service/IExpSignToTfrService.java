package com.deppon.foss.module.pickup.sign.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;

/**
 * 提供给中转的营业部签收接口  快递100
 * @author 159231
 *
 */
public interface IExpSignToTfrService extends IService {
	/**
	 * 提供给中转的营业部签收接口  快递100
	 * @param entity
	 * @return
	 */
	String addExpressArrivesheetForTfr(ArriveSheetEntity entity);
}