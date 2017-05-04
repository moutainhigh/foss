package com.deppon.foss.module.settlement.consumer.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODLogEntity;

/**
 * 代收货款操作日志服务
 * @author 000123-foss-huangxiaobo
 * @date 2012-11-8 上午10:19:36
 */
public interface ICodLogService extends IService {
	/**
	 * 
	 * 插入代收货款日志
	 * @author dp-huangxb
	 * @date 2012-10-13 下午2:15:29
	 */
    int addCodLog(CODLogEntity entity);

    /**
     * 
     * 查询代收货款日志
     * @author dp-huangxb
     * @date 2012-10-13 下午3:24:09
     * @param waybillNumber 运单单号
     */
    List<CODLogEntity> queryByWaybill(String waybillNumber);
    /**
     * 
     * 查询代收货款审核日志
     * @author 310970
     * @date 2016-05-11
     * @param waybillNumber 运单单号
     */
	List<CODLogEntity> queryAuditLogByWaybill(String waybillNo);
}
