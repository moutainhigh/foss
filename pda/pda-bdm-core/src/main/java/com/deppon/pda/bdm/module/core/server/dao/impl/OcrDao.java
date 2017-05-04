package com.deppon.pda.bdm.module.core.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPicturePdaDto;
import com.deppon.pda.bdm.module.core.server.dao.IOcrDao;
import com.deppon.pda.bdm.module.core.shared.domain.WaybillPictureOcrDto;

public class OcrDao extends  iBatis3DaoImpl implements IOcrDao{
	//查询N秒前未发送给foss的运单
	@Override
	public List<WaybillPictureOcrDto> queryWaybillsWithoutSend(String jobId) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(getClass().getName()+".queryWaybillsWithoutSend",jobId);
	}
	
	//修改所有当次发送给foss的状态
	@Override
	public void updateWetherSendForApp(List<WaybillPictureOcrDto> list) {
		// TODO Auto-generated method stub
		getSqlSession().update(getClass().getName()+".updateWetherSendForApp", list);
	}
	
	//修改所有当次发送给foss的状态
	@Override
	public void updateWetherSendForOcr(List<WaybillPictureOcrDto> list) {
		// TODO Auto-generated method stub
		getSqlSession().update(getClass().getName()+".updateWetherSendForOcr", list);
	}
	
	//插入ocr表中
	@Override
	public void insertToOcr(WaybillPicturePdaDto wbp) {
		// TODO Auto-generated method stub
		getSqlSession().insert(getClass().getName()+".insertToOcr", wbp);
	}

	@Override
	public int updateJobID(int waitTime, String jobId) {
		// TODO Auto-generated method stub
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("waitTime", waitTime);
		map.put("jobId", jobId);
		return getSqlSession().update(getClass().getName()+".updateJobId", map);
	}
	
	@Override
	public void updateWetherSendForFailForApp(List<WaybillPictureOcrDto> list) {
		// TODO Auto-generated method stub
		getSqlSession().update(getClass().getName()+".updateWetherSendForFailForApp", list);
	}

	@Override
	public void updateWetherSendForFailForOcr(List<WaybillPictureOcrDto> list) {
		// TODO Auto-generated method stub
		getSqlSession().update(getClass().getName()+".updateWetherSendForFailForOcr", list);
	}
	
	//查询是否有运单没有发送给foss
	@Override
	public int waybillNoSend(int waittime) {
		// TODO Auto-generated method stub
		return (Integer) getSqlSession().selectOne(getClass().getName()+".waybillNoSend", waittime);
	}
}
