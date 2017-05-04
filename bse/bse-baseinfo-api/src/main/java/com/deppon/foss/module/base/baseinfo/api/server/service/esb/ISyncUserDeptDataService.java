package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserDeptDataEntity;

/**
 * 同步用户部门管理信息给OMS  Web Service客户端服务接口实现
 * @author 310854
 * @date 2016-4-6
 */
public interface ISyncUserDeptDataService {

	/**
	 * 用户部门管理信息
	 * @author 310854
	 * @date 2016-4-6
	 */
	void syncUserDeptData(List<UserDeptDataEntity> entitys, EmployeeEntity userEntity, int operateType) throws BusinessException;
}
