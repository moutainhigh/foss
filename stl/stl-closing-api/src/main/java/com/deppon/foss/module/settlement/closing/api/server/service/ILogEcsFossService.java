package com.deppon.foss.module.settlement.closing.api.server.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MqLogEntity;
  
/** 
 * 日志记录业务逻辑接口 
 */  
public interface ILogEcsFossService {  
    /**
     * 设置日志实体参数
     * @param request
     * @param response
     * @param esbCode
     * @param waybillNo
     * @param flag 
     * 					true:插入成功表<br/>
     * 					false:插入失败表
     * @param date 进入接口方法时间
     */
    public void setLog(Object request,Object response, String esbCode, String waybillNo, Boolean flag, Date date);
    
    /**
     * 重发成功之后将对应的日志记录修改为成功状态
     * @param waybillNo
     * @param esbCode
     */
    void doUpdateLogEntity(MqLogEntity log);
    
    /**
     * 根据条件查询异常日志记录
     * @param waybillNo 运单号
     * @param esbCode esb编码
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     */
    List<MqLogEntity> queryMqLogEntity(Map<String, Object> params, int start, int limit);
    
    /**
     * 记录成功日志
     * @param log
     */
    void addSuccessLog(MqLogEntity log);
    
    /**
     * 记录失败日志
     * @param log
     */
    void addFailLog(MqLogEntity log);
    
    /**
     * 根據条件查询异常日志总行数
     * @param params
     * @return
     */
    Long queryLogTotalCount(Map<String, Object> params);
}