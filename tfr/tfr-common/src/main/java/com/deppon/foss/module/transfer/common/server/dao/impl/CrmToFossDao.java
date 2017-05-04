package com.deppon.foss.module.transfer.common.server.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.common.api.server.dao.ICrmToFossDao;
import com.deppon.foss.util.CollectionUtils;
public class CrmToFossDao  extends iBatis3DaoImpl implements ICrmToFossDao{

	@SuppressWarnings("unchecked")
	@Override
	/**通过运单号，卸车部门找所在的 卸车任务编号*/
	public List<String> queryUnloadTaskNoByWaybillNo(String waybillNo,String unloadOrgCode) {
		Map<String,String> dataMap=new HashMap<String,String>();
		dataMap.put("waybillNo", waybillNo);
		dataMap.put("unloadOrgCode", unloadOrgCode);
		// TODO Auto-generated method stub
		return  this.getSqlSession().selectList("foss.tfr.SerialNumberRuleDao.queryUnloadTaskNoByWaybillNo", dataMap);

	}

	@Override
	/**通过卸车任务统计卸车任务下的总重量 （顿）*/
	public BigDecimal queryTotalWeightByUnloadTaskNo(String unloadTaskId) {
		// TODO Auto-generated method stub
		return (BigDecimal) this.getSqlSession().selectOne("foss.tfr.SerialNumberRuleDao.queryTotalWeightByUnloadTaskNo", unloadTaskId);
		

	}

	@Override
	/**查询运单最后一件货的扫描时间 */
	public Date queryUnloadLastScanTimeByWaybillNo(String waybillNo,String unloadTaskId,String unloadOrgCode) {
		// TODO Auto-generated method stub
		Map<String,String> dataMap=new HashMap<String,String>();
		dataMap.put("waybillNo", waybillNo);
		dataMap.put("unloadTaskId", unloadTaskId);
		dataMap.put("unloadOrgCode", unloadOrgCode);
		// TODO Auto-generated method stub
		return  (Date) this.getSqlSession().selectOne("foss.tfr.SerialNumberRuleDao.queryUnloadLastScanTimeByWaybillNo", dataMap);
		
	}

    /**判断车辆归属性 */
	@SuppressWarnings("unchecked")
	public List<String> queryBelongVehicleByVehicleNo(String vehicleNo){
	
		//车辆可能是公司车也可能是外请车，这里做判断
		List<String> result=this.getSqlSession().selectList("foss.tfr.SerialNumberRuleDao.queryBelongVehicleByVehicleNo", vehicleNo);
		if(CollectionUtils.isEmpty(result)){
			return null;
		}else{
			
			return result;
			
		}
		
	}
	
}
