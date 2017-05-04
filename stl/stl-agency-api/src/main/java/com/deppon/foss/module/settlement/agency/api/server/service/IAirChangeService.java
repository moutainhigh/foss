package com.deppon.foss.module.settlement.agency.api.server.service;

import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.agency.api.shared.domain.AirChangeEntity;


/**
 * 变更清单服务
 * @author Foss-chenmingchun
 * @date 2013-4-11 下午3:17:14
 */
public interface IAirChangeService {

	

	/**
	 * 变更清单明细
	 * 
	 * @author Foss-chenmingchun
 * @date 2013-4-11 下午3:17:14
	 * @param addedDetails 新增明细
	 * @param modifiedDetails 修改明细
	 * @param removedDetails 删除明细（运单集合）
	 */
	void modifyAirChangeDetail(List<AirChangeEntity> addedDetails, List<AirChangeEntity> modifiedDetails,
			List<AirChangeEntity> removedDetails, CurrentInfo currentInfo);

}
