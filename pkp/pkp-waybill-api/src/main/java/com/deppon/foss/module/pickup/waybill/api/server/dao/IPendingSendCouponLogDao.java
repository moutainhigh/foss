package com.deppon.foss.module.pickup.waybill.api.server.dao;

import java.util.Date;

import com.deppon.foss.module.pickup.waybill.shared.domain.PendingSendCouponLogEntity;



/**
 * 待处理返券日志接口
 * @author Foss-206860
 * @date 
 */
public interface IPendingSendCouponLogDao {

	/**
	 * 添加待处理返券日志
	 * @author Foss-206860
	 * @date 
	 */
	int addPendingSendCouponLogEntity(PendingSendCouponLogEntity record);

	
	/**
	 * 根据新运单号和开单时间查询返券日志信息
	 * @author Foss-206860
	 * @date 
	 */
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
