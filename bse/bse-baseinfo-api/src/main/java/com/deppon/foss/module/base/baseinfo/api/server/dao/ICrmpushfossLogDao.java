package com.deppon.foss.module.base.baseinfo.api.server.dao;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CrmpushfossLogEntity;

public interface ICrmpushfossLogDao {

	Long countByCrmpushfossLog(CrmpushfossLogEntity example);

	int deleteByPrimaryKey(String id);

	int insert(CrmpushfossLogEntity record);

	CrmpushfossLogEntity selectByPrimaryKey(String id);
}