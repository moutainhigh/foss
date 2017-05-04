/**
 * 
 */
package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IEWaybillProcessDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.EWaybillProcessEntity;



/**
 * 待激活运单处理DAO
 * @author FOSS-045925
 * @date 2014-12-30 14:43:42
 * 
 */
@SuppressWarnings("unchecked")
public class EWaybillProcessDao  extends iBatis3DaoImpl implements IEWaybillProcessDao{
	private static final String NAMESPACE = "com.deppon.foss.module.pickup.waybill.shared.domain.EWaybillProcessEntity.";

	@Override
	public int insert(EWaybillProcessEntity entity) {
		return getSqlSession().insert(NAMESPACE+"insert", entity);
	}
	
	@Override
	public List<EWaybillProcessEntity> queryAllByCommon(String jobId) {
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("jobId", jobId);
		return getSqlSession().selectList(NAMESPACE+"queryAllByCommon", maps);
	}

	@Override
	public int updateForJob(Map<String, Object> map) {
		String jobId = (String) map.get("jobId");
		String strRownum = (String) map.get("rownum");
		Integer rownum = Integer.valueOf(strRownum);
		Date modifyTime = new Date();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("jobId",jobId);
		paramMap.put("modifyTime", modifyTime);
		paramMap.put("rownum", rownum);
		return getSqlSession().update(NAMESPACE+"updateForJob", map);
	}

	@Override
	public int deleteByWaybillNo(String waybillNo) {
		return getSqlSession().delete(NAMESPACE+"deleteByWaybillNo", waybillNo);
	}

	/**
     * 根据条件查询对应的条件个数
     * @author Foss-105888-Zhangxingwang
     * @date 2015-3-17 20:37:34
     * @param maps
     * @return
     */
	@Override
	public List<EWaybillProcessEntity> queryEWaybillByCondition(Map<String, Object> maps) {
		return this.getSqlSession().selectList(NAMESPACE+"queryEWaybillByCondition", maps);
	}

    /**
     * 根据运单号更新对应数据
     * @author Foss-105888-Zhangxingwang
     * @date 2015-3-18 10:44:33
     * @param entity
     * @return
     */
	@Override
	public int updateEWaybillProcessByWaybillNo(EWaybillProcessEntity entity) {
		return this.getSqlSession().update(NAMESPACE+"updateEWaybillProcessByWaybillNo", entity);
	}
	
	/**
	 * <p>激活线程处理，线程池满数据回滚操作，将pkp.t_srv_ewaybil_process jobId 置为N/A,状态置为N待于再执行一次激活后续流程</p> 
	 * @author Foss-151211-yangtaohong 
	 * @date 2016-8-14 下午5:10:51
	 * @param id
	 * @return
	 * @see
	 */
	@Override
	public int updateProcessJobIdByIdForActive(String jobId) {
		return getSqlSession().update(NAMESPACE+"updateProcessJobIdByIdForActive",jobId);
	}
}
