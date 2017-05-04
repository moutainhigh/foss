package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IPendingSendCouponLogDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.PendingSendCouponLogEntity;

/**
 * 待处理返券日志实现类
 * @author 
 * @date
 */
public class PendingSendCouponLogDao extends iBatis3DaoImpl implements
		IPendingSendCouponLogDao {

	private static final String NAMESPACE = "foss.pkp.PendingSendCouponLogEntityMapper.";
	
	/**
	 * 添加待处理返券日志
	 * @author Foss-206860
	 * @param record 待处理返券日志信息
	 */
	@Override
	public int addPendingSendCouponLogEntity(PendingSendCouponLogEntity record) {
		return getSqlSession().insert(NAMESPACE+"insert", record);
	}

	/**
	 * 根据运单号和开单时间查询返券日志信息
	 * 
	 * @author Foss-206860
	 * **/
	@Override
	public PendingSendCouponLogEntity queryPendingSendLogCoupon(
			String waybillNo, Date billTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("waybillNo", waybillNo);
		map.put("billTime", billTime);
		List<PendingSendCouponLogEntity> list = this.getSqlSession().selectList(
				NAMESPACE + "queryPendingSendCouponLog", map);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 定价优化项目
	 * 
	 * 根据优惠券编码获取区域线路限制
	 * 
	 * @author Foss-206860
	 */
	@Override
	public PendingSendCouponLogEntity queryLineAreaByNum(String couponNum) {
		List<PendingSendCouponLogEntity> list = this.getSqlSession().selectList(
				NAMESPACE + "queryLineAreaByNum", couponNum);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

}
