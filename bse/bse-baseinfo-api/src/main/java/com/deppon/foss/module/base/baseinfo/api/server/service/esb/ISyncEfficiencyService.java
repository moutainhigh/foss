package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 同步装卸车标准信息给OMS  Web Service客户端服务接口实现
 * @author 310854
 * @date 2016-4-6
 */
public interface ISyncEfficiencyService {

	/**
	 * 装卸车标准信息
	 * @author 310854
	 * @date 2016-4-6
	 */
	void syncEfficiency(List<?> entitys,int operationType) throws BusinessException;
}
