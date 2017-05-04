package com.deppon.foss.module.base.baseinfo.api.server.dao;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CrmpushfossLogBackupsEntity;

public interface ICrmpushfossLogBackupsDao {
   
    Long countByCrmpushfossLogBackups(CrmpushfossLogBackupsEntity example);

    int deleteByPrimaryKey(String id);

    int insert(CrmpushfossLogBackupsEntity record);

    CrmpushfossLogBackupsEntity selectByPrimaryKey(String id);
 
}