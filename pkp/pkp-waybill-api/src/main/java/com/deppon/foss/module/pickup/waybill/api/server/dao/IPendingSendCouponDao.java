package com.deppon.foss.module.pickup.waybill.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.PendingSendCouponEntity;
import com.deppon.foss.module.pickup.waybill.shared.vo.PendingSendCouponVo;
/**
 * 待处理返券服务DAO
 * */
public interface IPendingSendCouponDao {

	/**
	 * 添加待处理返券信息
	 * @author 
	 * @param record 待返券的返券实体
	 */
	int addPendingSendCouponEntity(PendingSendCouponEntity record);
	
	/**
	 * 更新一定数量的待处理返券JobId
	 * @author 
	 * @param vo 待处理发送返券VO
	 */
	int updateJobIDTopByRowNum(PendingSendCouponVo vo);
	
	/**
	 * 根据jobId查询待处理返券信息
	 * @author 
	 * @param jobId 返券的任务编码
	 */
	List<PendingSendCouponEntity> queryPendingSendCouponEntityByJobID(
			String jobId);
	
	/**
	 * 根据运单号和开单时间确定PKP.T_SRV_PENDING_SENDCOUPON表中的唯一  加上条件 是否返券 = N
	 * 
	 * @param waybillNo 运单号
	 * @param billTime 开单时间
	 * **/
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
	 * @param id 待返券的返券实体主键ID
	 * */
	int updateSendCouponStatusByID(String id);
	
	
	/**
	 * 根据ID删除待处理返券信息
	 * 
	 * @param id 待返券的返券实体主键ID
	 */
	void  deleteSendCouponById(String id);

	/**
	 * 当没有执行返券时，将jobid更改为N/A
	 * */
	int updatePendingSendCouponJobToNA(PendingSendCouponEntity pendingSendCouponEntity);
	
	/**
	 * 当执行返券失败时，更新jobid、优惠券编码、异常信息、修改时间
	 * */
	int updatePendingSendCoupon(PendingSendCouponEntity pendingSendCouponEntity);



}
