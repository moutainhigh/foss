package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 同步权限信息给OMS  Web Service客户端服务接口实现
 * @author 310854
 * @date 2016-4-8
 */
public interface ISyncResourceService {

	/**
	 * 权限信息
	 * @author 310854
	 * @date 2016-4-8
	 */
	void syncResource(List<?> entitys) throws BusinessException;
}
