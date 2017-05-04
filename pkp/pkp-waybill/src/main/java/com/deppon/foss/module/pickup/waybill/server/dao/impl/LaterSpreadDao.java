package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ILaterSpreadDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.LaterSpreadEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LaterSpreadDto;
import com.deppon.foss.util.define.FossConstants;

public class LaterSpreadDao extends iBatis3DaoImpl implements ILaterSpreadDao {
	private static final String namespace = "foss.pkp.laterSpreadMapper.";

	@Override
	public void save(LaterSpreadEntity e) {
		this.getSqlSession().insert(namespace + "save", e);
	}

	@Override
	public LaterSpreadDto updateLaterSpreadForJobTopNum(String jobId, int num) {
		LaterSpreadDto lsd = new LaterSpreadDto();
		lsd.setJobId(jobId);
		lsd.setQueryNum(num);
		Map<String, Object> params =new HashMap<String, Object>();
		params.put("queryJobId", jobId);
		params.put("queryNum", num);
		params.put("isSend", FossConstants.YES);
		int resultNum= this.getSqlSession().update(namespace + "updateLaterSpreadForJobTopNum", params);
		lsd.setResultNum(resultNum);
		return lsd;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LaterSpreadEntity> queryLaterSpreadByJobID(String jobId) {
		Map<String, Object> params =new HashMap<String, Object>();
		params.put("queryJobId", jobId);
		params.put("isSend", FossConstants.YES);
		return this.getSqlSession().selectList(namespace + "queryLaterSpreadByJobID", params);
	}

	@Override
	public void executeSendSMSFail(LaterSpreadEntity e) {
		this.getSqlSession().update(namespace + "executeSendSMSFail", e);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LaterSpreadEntity> queryLaterSpreadList(
			LaterSpreadEntity laterSpread) {
		if(laterSpread == null){
			return null;
		}
		return this.getSqlSession().selectList(namespace + "queryLaterSpreadList", laterSpread);
	}

	@Override
	public Long queryWaybillChangeDestinationAndReceiveMethod(String waybillNo) {
		Map<String, Object> params =new HashMap<String, Object>();
		params.put("waybillNo",waybillNo);
		Long  changeReceiveMethod=  queryWaybillChangeReceiveMethod(waybillNo);
		if(changeReceiveMethod > 0){
			return changeReceiveMethod;
		}
		Long  changeDestination = (Long) this.getSqlSession().selectOne(namespace + "queryWaybillChangeDestination", params);
		return changeDestination;
	}

	@Override
	public Long queryWaybillChangeReceiveMethod(String waybillNo) {
		Map<String, Object> params =new HashMap<String, Object>();
		params.put("waybillNo",waybillNo);
		return  (Long) this.getSqlSession().selectOne(namespace + "queryWaybillChangeReceiveMethod", params);
	}

}
