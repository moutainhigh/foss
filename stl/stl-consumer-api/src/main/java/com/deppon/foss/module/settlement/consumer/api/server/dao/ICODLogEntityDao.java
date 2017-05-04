package com.deppon.foss.module.settlement.consumer.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODLogEntity;


/**
 * 
 * 代收货款日志查询
 * @author dp-huangxb
 * @date 2012-10-13 下午2:03:22
 */
public interface ICODLogEntityDao {

	/**
	 * 
	 * 插入代收货款日志
	 * @author dp-huangxb
	 * @date 2012-10-13 下午2:15:29
	 */
    int addCODLog(CODLogEntity entity);

    /**
     * 
     * 查询代收货款日志
     * @author dp-huangxb
     * @date 2012-10-13 下午3:24:09
     * @param waybillNumber 运单单号
     */
    List<CODLogEntity> queryByWaybill(String waybillNumber);
    /**
     *@author 310970
     *@date  2016-5-11 
     *@param   waybillNo
     */
	List<CODLogEntity> queryAuditLogByWaybill(String waybillNo);

}
