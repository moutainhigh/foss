package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IReModifyRouteDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.ReModifyRouteEntity;
import com.deppon.foss.util.UUIDUtils;

/**
 * 接送货单独修改走货路径方法
 * @author Foss-105888-Zhangxingwang
 * @date 2014-6-24 16:49:50
 */
public class ReModifyRouteDao extends iBatis3DaoImpl implements IReModifyRouteDao {

	private static final String NAMESPACE = "foss.pkp.waybill.ReModifyRouteMapper.";
	
	private static final String queryJobId = "N/A";
	
	/**
	 * 新增一条欲修改走货路径数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-6-24 16:03:00
	 * @param record
	 * @return
	 */
	@Override
	public int addReModifyRouteRecord(ReModifyRouteEntity record) {
		record.setId(UUIDUtils.getUUID());
		if(record.getCreateTime() != null){
			record.setModifyTime(record.getCreateTime());
		}else{
			record.setCreateTime(new Date());
			record.setModifyTime(record.getCreateTime());
		}
		record.setJobId(queryJobId);
		return this.getSqlSession().insert(NAMESPACE+"addReModifyRouteRecord", record);
	}

	
	/**
	 * 修改数据
	 * @param record
	 * @return
	 */
	@Override
	public int updateReModifyRouteRecord(ReModifyRouteEntity record) {
		record.setModifyTime(new Date());
		return this.getSqlSession().update(NAMESPACE+"updateReModifyRouteRecord", record);
	}

	/**
	 * 删除数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-6-24 16:03:24
	 */
	@Override
	public int deleteReModifyRouteRecord(String id) {
		return this.getSqlSession().delete(NAMESPACE+"deleteReModifyRouteRecord", id);
	}

	/**
	 * 分页查询数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-6-24 16:06:54
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ReModifyRouteEntity> searchReModifyRouteRecord(int start, int limited) {
		RowBounds bounds = new RowBounds(start, limited);
		Map<String, String> param = new HashMap<String, String>();
		return this.getSqlSession().selectList(NAMESPACE+"searchReModifyRouteRecord", param, bounds);
	}

	/**
	 * @deprecated
	 * 批量插入数据,此方法不能使用，作废
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-6-24 18:54:13
	 */
	@Override
	public int insertSelectiveBatch(List<ReModifyRouteEntity> reModifyRouteList) {
//		Map<Object, Object> param = new HashMap<Object, Object>();
//		param.put("ReModifyRouteList", ReModifyRouteList);
		return this.getSqlSession().insert(NAMESPACE+"insertSelectiveBatch", reModifyRouteList);
	}


	/**
	 * 根据运单号,流水号ID查询是否后台存在此类数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-7-9 11:12:58
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ReModifyRouteEntity> queryIsExistRecordByWaybillSerial(String waybillNo, String labelGoodId) {
		Map<Object, Object> param = new HashMap<Object, Object>();
		param.put("waybillNo", waybillNo);
		param.put("labelGoodId", labelGoodId);
		return this.getSqlSession().selectList(NAMESPACE+"queryIsExistRecordByWaybillSerial", param);
	}
	
	@Override
	public int updateReModifyRouteByJobId(String jobId, int updateNum) {		
		Map<Object, Object> parameter =new HashMap<Object, Object>();
		parameter.put("jobId", jobId);
		parameter.put("updateNum", updateNum);
		parameter.put("queryJobId", queryJobId);
		return this.getSqlSession().update(NAMESPACE+"updateReModifyRouteByJobId", parameter);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ReModifyRouteEntity> searchReModifyRouteRecordByJobId(int start, int limited, String jobId) {
		Map<Object, Object> parameter =new HashMap<Object, Object>();
		RowBounds rowBounds = new RowBounds(start, limited);
		parameter.put("jobId", jobId);
		return this.getSqlSession().selectList(NAMESPACE+"searchReModifyRouteRecordByJobId", parameter, rowBounds);
	}


}
