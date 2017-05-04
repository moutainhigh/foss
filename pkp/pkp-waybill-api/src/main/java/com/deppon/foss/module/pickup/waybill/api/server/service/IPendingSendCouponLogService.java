package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.Date;

import com.deppon.foss.module.pickup.waybill.shared.domain.PendingSendCouponLogEntity;

/**
 * 待处理返券日志服务Service
 * **/
public interface IPendingSendCouponLogService {
	
		
	/**
	 * 根据运单号和开单时间确定PKP.T_SRV_PENDING_SENDCOUPON_LOG表中的唯一 
	 *
	 * @param waybillNo 运单号
	 * @param billTime 开单时间 
	 * */
	PendingSendCouponLogEntity queryPendingSendLogCoupon(String waybillNo,
			Date billTime);

	/**
	 * 定价优化项目
	 * 
	 * 根据优惠券编码获取区域线路限制
	 * 
	 * @author Foss-206860
	 */
	PendingSendCouponLogEntity queryLineAreaByNum(String couponNum);
	
}
