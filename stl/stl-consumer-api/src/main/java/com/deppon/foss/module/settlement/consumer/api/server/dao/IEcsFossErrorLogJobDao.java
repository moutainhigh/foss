package com.deppon.foss.module.settlement.consumer.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.settlement.consumer.api.shared.domain.EcsFossErrorLogJobEntity;

/**
 * job执行快递对接FOSS异常日志Dao接口
 * @author 326181
 * @date 2013-9-20
 */
public interface IEcsFossErrorLogJobDao {

	/**
     * 将快递对接FOSS补码异步接口在同步开单之前修改运单异常记录到日志表
     * @param obj
     */
    void insertEcsFossErrorLogJob(EcsFossErrorLogJobEntity errorLogJobEntity);
    
    /**
     * 将成功修改运单的数据根据id改为无效
     * @param logId
     */
    void updateEcsFossErrorLogJob(Map<String, Object> params);
    
    /**
     * 查询补码在开单之前修改运单的异常数据
     * 
     * @return
     */
    List<EcsFossErrorLogJobEntity> findEcsFossErrorLogJob(String codeType);
	
}
