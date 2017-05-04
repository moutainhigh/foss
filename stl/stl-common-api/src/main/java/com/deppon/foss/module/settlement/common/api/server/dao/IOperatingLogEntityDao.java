package com.deppon.foss.module.settlement.common.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.OperatingLogEntity;


/**
 * 操作日志
 * @author 000123-foss-huangxiaobo
 * @date 2012-11-8 上午10:49:34
 */
public interface IOperatingLogEntityDao {

    /**
     * 新加操作日志
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-8 上午10:50:15
     * @param entity
     * @return
     */
    int addOperatingLog(OperatingLogEntity entity);
    
    /**
     * 按操作单号进行查询
     * @author 000123-foss-huangxiaobo
     * @date 2012-11-8 上午11:09:36
     * @param billNo
     * @return
     */
    List<OperatingLogEntity> queryByOperateBillNo(String billNo);
    
    /**
     * 新增多条操作日志信息
     * 
     * @author 099995-foss-wujiangtao
     * @date 2013-1-25 下午2:26:21
     * @param list
     * @return
     */
    int addBatchOperatingLog(List<OperatingLogEntity> list);
}
