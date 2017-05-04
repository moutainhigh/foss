package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IPendingSendCouponDao;
import com.deppon.foss.module.pickup.waybill.server.service.impl.PendingSendCouponService;
import com.deppon.foss.module.pickup.waybill.shared.domain.PendingSendCouponEntity;
import com.deppon.foss.module.pickup.waybill.shared.vo.PendingSendCouponVo;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class PendingSendCouponDao extends iBatis3DaoImpl implements
		IPendingSendCouponDao {
	
	private static final String NAMESPACE = "foss.pkp.PendingSendCouponEntityMapper.";

	@Override
	public int addPendingSendCouponEntity(PendingSendCouponEntity record) {
		//主键
		record.setId(UUIDUtils.getUUID());
		//JobId默认为'N/A'
		record.setJobId(PendingSendCouponService.queryJobId);
		return getSqlSession().insert(NAMESPACE+"insert", record);
	}

	@Override
	public int updateJobIDTopByRowNum(PendingSendCouponVo vo) {
		return getSqlSession().update(NAMESPACE+"updateJobIDTopByRowNum", vo);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PendingSendCouponEntity> queryPendingSendCouponEntityByJobID(
			String jobId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jobId", jobId);
		map.put("sendCoupon", FossConstants.NO);
		return getSqlSession().selectList(NAMESPACE+"selectPendingSendCouponEntityByJobID",map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PendingSendCouponEntity queryPendingSendCoupon(String waybillNo,
			Date billTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("waybillNo", waybillNo);
		map.put("billTime", billTime);
//		map.put("sendCoupon", FossConstants.NO);
		List<PendingSendCouponEntity> list = this.getSqlSession().selectList(
				NAMESPACE + "queryPendingSendCoupon", map);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public int updateSendCouponByID(PendingSendCouponEntity pendingSendCoupon) {
		pendingSendCoupon.setJobId(PendingSendCouponService.queryJobId);
		return getSqlSession().update(NAMESPACE+"updateSendCouponByID", pendingSendCoupon);
	}

	@Override
	public int updateSendCouponStatusByID(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("sendCoupon", FossConstants.YES);
		map.put("modifyTime", new Date());
		return getSqlSession().update(NAMESPACE+"updateSendCouponStatusByID", map);
	}

	@Override
	public void deleteSendCouponById(String id) {
		getSqlSession().delete(NAMESPACE+"deleteSendCouponById",id);
	}

	@Override
	public int updatePendingSendCouponJobToNA(PendingSendCouponEntity pendingSendCouponEntity) {
		return getSqlSession().update(NAMESPACE+"updatePendingSendCouponJobToNA", pendingSendCouponEntity);
	}

	@Override
	public int updatePendingSendCoupon(
			PendingSendCouponEntity pendingSendCouponEntity) {
		return getSqlSession().update(NAMESPACE+"updatePendingSendCoupon", pendingSendCouponEntity);
	}

}
