package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.Date;

import com.deppon.foss.module.pickup.waybill.api.server.dao.IPendingSendCouponLogDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPendingSendCouponLogService;
import com.deppon.foss.module.pickup.waybill.shared.domain.PendingSendCouponLogEntity;

public class PendingSendCouponLogService implements IPendingSendCouponLogService {
	

	/**
	 * 待处理返券日志DAO
	 * 
	 * **/
	private IPendingSendCouponLogDao pendingSendCouponLogDao; 
	

	public void setPendingSendCouponLogDao(
			IPendingSendCouponLogDao pendingSendCouponLogDao) {
		this.pendingSendCouponLogDao = pendingSendCouponLogDao;
	}

	
	/**
	 * 根据运单号和开单时间确定PKP.T_SRV_PENDING_SENDCOUPON表中的唯一  
	 * */
	@Override
	public PendingSendCouponLogEntity queryPendingSendLogCoupon(
			String waybillNo, Date billTime) {
		return pendingSendCouponLogDao.queryPendingSendLogCoupon(waybillNo,billTime);
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
		return pendingSendCouponLogDao.queryLineAreaByNum(couponNum);
	}

}
