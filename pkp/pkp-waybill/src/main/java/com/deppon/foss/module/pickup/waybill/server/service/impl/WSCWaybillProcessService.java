package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.api.server.dao.IWSCWaybillProcessDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWSCWaybillProcessService;
import com.deppon.foss.module.pickup.waybill.shared.domain.AddWSCWaybillProcessEntity;
import com.deppon.foss.module.pickup.waybill.shared.vo.WSCWaybillProcessVo;

/**
 * 
 * @author 350909    郭倩云
 * 主要与数据库表PKP.T_SRV_ADD_ASYN_WAYBILL交互
 *
 */
public class WSCWaybillProcessService implements IWSCWaybillProcessService{
	
	IWSCWaybillProcessDao wSCWaybillProcessDao;

	public IWSCWaybillProcessDao getwSCWaybillProcessDao() {
		return wSCWaybillProcessDao;
	}

	public void setwSCWaybillProcessDao(IWSCWaybillProcessDao wSCWaybillProcessDao) {
		this.wSCWaybillProcessDao = wSCWaybillProcessDao;
	}
	/**
	 * @author 350909    郭倩云
	 * 将生成财务单据所需要的运单信息和代刷卡数据相关信息插入到数据库表PKP.T_SRV_ADD_ASYN_WAYBILL
	 */
	public void addWSCWaybillProcessEntity(String requestType,String jsonString) {
		wSCWaybillProcessDao.insertWSCWaybillProcessEntity(requestType,jsonString);
	}
	/**
	 * @author 350909    郭倩云
	 * 根据jobId将生成财务单据所需要的运单信息和代刷卡数据相关信息从数据库表PKP.T_SRV_ADD_ASYN_WAYBILL查询出来
	 */
	public List<AddWSCWaybillProcessEntity> queryInfoByJobId(String jobId) {
		List<AddWSCWaybillProcessEntity> list=wSCWaybillProcessDao.queryInfoByJobId(jobId);
		return list;
	}
	/**
	 * @author 350909    郭倩云
	 * 假如线程执行失败,根据jobId将相关数据的jobId更新成N/A
	 */
	public int updateJobIdToNA(WSCWaybillProcessVo wSCWaybillProcessVo) {
		int result = wSCWaybillProcessDao.updateJobIdToNA(wSCWaybillProcessVo);
		return result;
	}

}
