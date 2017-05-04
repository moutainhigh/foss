package com.deppon.foss.module.transfer.departure.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.departure.api.shared.domain.AgingNodesLogEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity;

public interface IAgingNodesLogDao {
	/**
	 * 查询时效节点修改日志
	 * @author foss-heyongdong
	 * @data 2014年4月11日 14:42:09
	 * @param billNo
	 * @return list
	 */
	List<AgingNodesLogEntity> queryAgingNodesLog(String billNo);
	/**
	 * 跟新车辆任务明细出发到达时间
	 * @author foss-heyongdong
	 * @data 2014年4月11日 14:42:09
	 * @param temp
	 * @return int
	 */
	int updateTrucktastDeteil(TruckTaskDetailEntity temp);
	/**
	 * 保存修改车辆任务明细出发到达时间日志
	 * @author foss-heyongdong
	 * @data 2014年4月11日 14:42:09
	 * @param aglogs
	 * @return int
	 */
	int  saveAgingNodesLog(AgingNodesLogEntity aglogs);
	
	/**
	 * 更具单号查询同一个车辆任务下的相关联单号
	 * @author foss-heyongdong
	 * @date 2014年4月29日 09:19:20
	 * @param billNo
	 * @return List<String>
	 */
	List<String> queryRelationbillNos(String billNo);
}
