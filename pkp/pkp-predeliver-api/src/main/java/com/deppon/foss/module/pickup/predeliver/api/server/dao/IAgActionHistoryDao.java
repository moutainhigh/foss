package com.deppon.foss.module.pickup.predeliver.api.server.dao;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.AgActionHistoryEntity;

/**
 * 
 * 弃货处理历史记录表
 * 
 * @author ibm-wangfei
 * @date Apr 24, 2013 5:47:21 PM
 */
public interface IAgActionHistoryDao {

    int insertSelective(AgActionHistoryEntity record);
}