package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.DepartureStandardEntity;

public interface ISendDepartureStandardToWDGHService {
	/**
	 * 
	 * 给网点规划同步发车标准客户端接口
	 * @author 269231
	 * @param dtos
	 * @param OperateType  操作类型
	 */
	void syncDepartureStandard(DepartureStandardEntity dtos,String operateType);
	
	
}
