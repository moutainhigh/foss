package com.deppon.foss.module.settlement.consumer.api.server.service;

import java.util.List;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.EcsFossErrorLogJobEntity;


/**
 * 快递对接FOSS,JOB定时执行service  
 * @author 326181
 * @date 2016-9-20
 */
public interface IEcsFossErrorLogJobService {  
    
    /**
     * 添加快递对接FOSS,JOB定时执行该表异常日志
     * @param codeType 代码类型 
     * @param codeName 代码对应的需求名称
     * @param obj
     * @param errorMsg 初始異常消息
     */
    void addEcsFossErrorLogJob(String codeType, String codeName, Object obj, String errorMsg);
    
    /**
     * 修改快递对接FOSS,JOB定时执行该表异常日志</br>
     * errorMsg为空，则说明该条数据对应业务job执行成功</br>
     * active修改为N，下次不再执行。
     * @param logId
     * @param jobErrorMsg job执行异常信息 
     */
    void updateEcsFossErrorLogJobByLogId(String logId, String jobErrorMsg);
    
    /**
     * 查询补码在开单之前修改运单的异常数据
     * @param codeType 对应编码类型
     * @return
     */
    List<EcsFossErrorLogJobEntity> queryEcsFossErrorLogJob(String codeType);

    /**
	 * 每隔30分钟执行此方法。推送第三方付款数据到财务自助
	 * @author 326181
	 * 
	 */
	void doExecuteFossToFinsRemittanceJob();
}