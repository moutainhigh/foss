package com.deppon.foss.module.base.baseinfo.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CrmpushfossLogBackupsEntity;

public interface ICrmpushfossLogBackupsService  extends IService {
   
    Long countByCrmpushfossLogBackups(CrmpushfossLogBackupsEntity example);

    int deleteByPrimaryKey(String id);

    int insert(CrmpushfossLogBackupsEntity record);

    CrmpushfossLogBackupsEntity selectByPrimaryKey(String id);
 
}