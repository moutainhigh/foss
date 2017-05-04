package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.Date;

import com.deppon.foss.module.pickup.waybill.shared.domain.PendingSendCouponEntity;
import com.deppon.foss.module.pickup.waybill.shared.vo.PendingSendCouponVo;

/**
 * 待处理返券服务Service
 * **/
public interface IPendingSendCouponService {
	/**
	 * 添加待处理返券
	 * 
	 * @param pendingSendCoupon  待返券的返券实体
	 * 
	 */
	void addPendingSendCoupon(PendingSendCouponEntity pendingSendCoupon);
	
	/**
	 * 定时任务处理待发返券
	 * @author 
	 * @date 
	 */
	void batchjobs();
	
	/**
	 * 每次更新一定数量待处理返券JobId
	 * @param jobId 任务编码
	 * @date num 更新条数
	 */
	PendingSendCouponVo updatePendingSendCouponForJobTopNum(String jobId,String num);

		
	/**
	 * 根据运单号和开单时间确定PKP.T_SRV_PENDING_SENDCOUPON表中的唯一  加上条件 是否返券 = N
	 *
	 * @param waybillNo 运单号
	 * @param billTime 开单时间 
	 * */
	PendingSendCouponEntity queryPendingSendCoupon(String waybillNo,
			Date billTime);
	
	/**
	 * 更改单操作----根据ID更新返券的信息
	 * 
	 * @param pendingSendCoupon 待返券的返券实体
	 * */
	int updateSendCouponByID(PendingSendCouponEntity pendingSendCoupon);
	
	/**
	 * 返券完成-----根据ID更新返券的信息
	 * 
	 * @param id 待返券的返券实体ID
	 * */
	int updateSendCouponStatusByID(String id);
}
