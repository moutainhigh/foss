package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillProcessDao;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillProcessEntity;
import com.deppon.foss.util.UUIDUtils;

/**
 * WaybillProcessDao
 *
 * @author 325220-foss-liuhui
 * @date 2016年5月14日
 */
public class WaybillProcessDao extends iBatis3DaoImpl implements IWaybillProcessDao {

	private static final String NAMESPACE_PROCESS_ENTITY = "foss.pkp.waybillProcessMapper.";

	
	
	public List<WaybillProcessEntity> queryWaybillProcessListByJobId(String jobId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jobId", jobId);
		return getSqlSession().selectList(NAMESPACE_PROCESS_ENTITY + "queryWaybillProcessListByJobId", map);
	}

	
	public int updateForJob(Map<String, Object> map) {
		if(map!=null){
			Object rownum = map.get("rownum");
			if(rownum == null || (StringUtils.isBlank(rownum.toString())))
			{
				return 0;
			}
		}
		
		return getSqlSession().update(NAMESPACE_PROCESS_ENTITY + "updateForJob", map);
	}
	
	
	//updateWaybillProcessByJobId
	@Override
	public int updateWaybillProcessByJobId(Map<String,Object> map) {
		return getSqlSession().update(NAMESPACE_PROCESS_ENTITY + "updateWaybillProcessByJobId", map);
	}
	
	@Override
	public int addWaybillProcessEntity(WaybillProcessEntity waybillProcessEntity) {
		// TODO Auto-generated method stub
		return getSqlSession().insert(NAMESPACE_PROCESS_ENTITY+"insert", waybillProcessEntity);
	}
	
	
	@Override
	public int updateWaybillProcessSecondKey(String waybillNo,String newSecondKey){
		if( StringUtils.isNotEmpty(waybillNo) && StringUtils.isNotEmpty(newSecondKey) ){
			Map<String,String> params = new HashMap<String,String>();
			params.put("newSecondKey", newSecondKey);
			params.put("waybillNo", waybillNo);
			params.put("secondKey", "Y");
			return getSqlSession().update(NAMESPACE_PROCESS_ENTITY+"updateWaybillProcessSecondKey",params);
		}else{
			return 0;
		}
	}
	
	@Override
	public int updateWaybillProcess(WaybillProcessEntity waybillProcessEntity,String newSecondKey,String oldSecondKey){
		if(null!=waybillProcessEntity){
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("orderNo", waybillProcessEntity.getOrderNo());
			params.put("createTime", waybillProcessEntity.getCreateTime());
			params.put("modifyTime", waybillProcessEntity.getModifyTime());
			params.put("operateResult", waybillProcessEntity.getOperateResult());
			params.put("jobId", waybillProcessEntity.getJobId());
			params.put("processCount", waybillProcessEntity.getProcessCount());
			params.put("newSecondKey", newSecondKey);
			params.put("active", waybillProcessEntity.getActive());
			params.put("waybillNo", waybillProcessEntity.getWaybillNo());
			params.put("oldSecondKey", oldSecondKey);
			return getSqlSession().update(NAMESPACE_PROCESS_ENTITY+"updateWaybillProcess",params);
		}
		return 0;
	}
	
	/**
	 * 查询未开始,运行中，的运单
	 */
	public WaybillProcessEntity queryNotFinishedByWaybillNo(String waybillNo){
		if(StringUtils.isEmpty(waybillNo)){
			return null;
		}else{
			Map<String,String> params = new HashMap<String,String>();
			params.put("secondKey", WaybillConstants.YES);
			params.put("waybillNo", waybillNo);
			return (WaybillProcessEntity)getSqlSession().selectOne(NAMESPACE_PROCESS_ENTITY+"queryNotFinishedByWaybillNo",params);
		}
	}
	
	/**
	 * @description 强行解锁(是否为)
	 * @return
	 */
	public int unlockedById(String id){
		if(StringUtils.isEmpty(id)){
			return 0;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", id);
		params.put("newSecondKey", UUIDUtils.getUUID());
		params.put("secondKey", WaybillConstants.YES);
		params.put("modifyTime", new Date());
		return getSqlSession().update(NAMESPACE_PROCESS_ENTITY+"unlockedById", params);
	}
}
