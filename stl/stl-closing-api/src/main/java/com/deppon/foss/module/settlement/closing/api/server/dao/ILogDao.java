package com.deppon.foss.module.settlement.closing.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MqLogEntity;


/**
 * mq日志
 * Created by 326181 on 2016/6/12.
 */
public interface ILogDao {

    /**
     * 根据条件查询异常日志记录T_STL_ECS2FOSS_FAIL_LOG
     * @param params
     * @return
     */
    List<MqLogEntity> queryFailLogEntity(Map<String, Object> params, int start, int limit);
    
    /**
     * 重发成功之后将对应的日志记录删除
     * @param log
     */
    void doDelFailLogById(String logId);
    
    /**
     * 新增成功的异步接口信息
     * @param log
     */
    void insertSuccessLog(MqLogEntity log);
    
    /**
     * 新增失败的异步接口信息
     * @param log
     */
    void insertFailLog(MqLogEntity log);
    
    /**
     * 查詢分頁總行數
     * @param params
     * @return
     */
    Long queryLogTotalCount(Map<String, Object> params);
}