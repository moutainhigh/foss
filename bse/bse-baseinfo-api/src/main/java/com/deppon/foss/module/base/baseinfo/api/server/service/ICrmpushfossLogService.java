package com.deppon.foss.module.base.baseinfo.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CrmpushfossLogEntity;

public interface ICrmpushfossLogService  extends IService {

	Long countByCrmpushfossLog(CrmpushfossLogEntity example);

	int deleteByPrimaryKey(String id);

	int insert(CrmpushfossLogEntity record);

	CrmpushfossLogEntity selectByPrimaryKey(String id);
}