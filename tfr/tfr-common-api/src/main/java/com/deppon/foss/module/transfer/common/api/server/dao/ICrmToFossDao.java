package com.deppon.foss.module.transfer.common.api.server.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ICrmToFossDao {

	/**通过运单号，卸车部门找所在的 卸车任务编号*/
	public List<String> queryUnloadTaskNoByWaybillNo(String waybillNo,String unloadOrgCode);

	/**通过卸车任务统计卸车任务下的总重量(顿) */
    public BigDecimal queryTotalWeightByUnloadTaskNo(String unloadTaskNo); 
    
    /**查询运单最后一件货的扫描时间 */
    public Date queryUnloadLastScanTimeByWaybillNo(String waybillNo,String unloadTaskId,String unloadOrgCode);
    
    /**判断车辆归属性 */
	public List<String> queryBelongVehicleByVehicleNo(String vehicleNo);
}
