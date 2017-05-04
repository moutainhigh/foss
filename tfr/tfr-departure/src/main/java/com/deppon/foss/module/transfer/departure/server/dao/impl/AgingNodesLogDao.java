package com.deppon.foss.module.transfer.departure.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.departure.api.server.dao.IAgingNodesLogDao;
import com.deppon.foss.module.transfer.departure.api.shared.domain.AgingNodesLogEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity;

public class AgingNodesLogDao extends iBatis3DaoImpl implements IAgingNodesLogDao{
	public static final String NAMESPACE="tfr-AgingNodesLog.";
	/**
	 *查询修改出发到达时间日志
	 *@author foss-heyongdong
	 *@date 2014年4月18日 18:01:16
	 *@param billNo  
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IAgingNodesLogDao#queryAgingNodesLog(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AgingNodesLogEntity> queryAgingNodesLog(String billNo) {
		
		return this.getSqlSession().selectList(NAMESPACE+"queryAgingNodesLog", billNo);
	}
	/**
	 * 更新出发到达时间
	 *@author foss-heyongdong
	 *@date 2014年4月22日 10:05:23
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IAgingNodesLogDao#updateTrucktastDeteil(com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity)
	 */
	@Override
	public int updateTrucktastDeteil(TruckTaskDetailEntity temp) {
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("id", temp.getId());
		map.put("actualDepartTime", temp.getActualDepartTime());
		map.put("actualArriveTime", temp.getActualArriveTime());
		map.put("manualArriveTime", temp.getManualArriveTime());
		map.put("manualDepartTime", temp.getManualDepartTime());
		return this.getSqlSession().update(NAMESPACE+"updateTrucktastDeteil", map);
	}
	/**
	 * 保存修改时间节点的日志
	 *@author foss-heyongdong
	 *@date 2014年4月22日 10:06:15
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IAgingNodesLogDao#saveAgingNodesLog(com.deppon.foss.module.transfer.departure.api.shared.domain.AgingNodesLogEntity)
	 */
	@Override
	public int saveAgingNodesLog(AgingNodesLogEntity aglogs) {
		return this.getSqlSession().insert(NAMESPACE+"saveAgingNodesLog", aglogs);
	}
	
	/**
	 * 更具单号查询同一个车辆任务下的相关联单号
	 * @author foss-heyongdong
	 * @date 2014年4月29日 09:42:45
	 * @param billNo
	 * @return List<String>
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.IAgingNodesLogDao#queryRelationbillNos(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<String> queryRelationbillNos(String billNo) {
		return this.getSqlSession().selectList(NAMESPACE+"queryRelationbillNos",billNo);
	}

}
