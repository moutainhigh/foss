/**
 * 
 */
package com.deppon.foss.module.pickup.sign.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillDetailEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignRfcEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillTrackDto;

/**
 * @author 231438_chenjunying
 * 2015-4-10 下午5:24:23
 */
public interface ISendWaybillTrackService extends IService{
	/**
	 * 快递100 轨迹推送 
	 * @author 231438-chenjunying
	 * @return 
	 */
	void sendTrackings(WaybillTrackDto trackingsDto);
	/**
	 * 封装的方法签收推送轨迹：签收时判断正常/异常签收，赋值，调用方法推送轨迹
	 * @author 231438_chenjunying
	 * 2015-4-14 下午4:26:58
	 */
	void packagingMethodForSign(WaybillSignResultEntity wayEntity,CurrentInfo currentInfo);
	/**
	 * 封装的方法 反签收推送轨迹：赋值，调用方法推送轨迹
	 * @author 231438_chenjunying
	 * 2015-4-14 下午4:26:58
	 */
	void packagingMethodForSignRfc(SignRfcEntity entity,CurrentInfo currentInfo);
	/**
	 * 封装的方法 派送（确认派送单）零担
	 * 赋值，调用方法推送轨迹
	 * @author 231438_chenjunying
	 * 2015-4-14 下午4:26:58
	 */
	void packagingMethodForConfirm(DeliverbillEntity deliverEntity,List<DeliverbillDetailEntity> entityList);
	/**
	 * 封装的方法 派送（确认派送单）快递
	 * 赋值，调用方法推送轨迹
	 * @author 231438_chenjunying
	 * 2015-4-14 下午4:26:58
	 */
	void packagingMethodForConfirm(DeliverbillEntity entity,DeliverbillDetailEntity detailEntity);
	/**
	 * 封装的方法  派送拉回
	 * 赋值，调用方法推送轨迹
	 * @author 231438_chenjunying
	 * 2015-4-14 下午4:26:58
	 */
	void packagingMethodForDeliveryBack(ArriveSheetEntity entity,CurrentInfo currentInfo);
	/**
	 * 菜鸟轨迹 推送（快递）
	 */
	public void rookieTrackingsForSign(WaybillSignResultEntity signEntity);

	/**
	 * WQS(菜鸟)轨迹 推送:派送拉回 赋值，调用方法推送轨迹
	 * @author 243921_zhangtingitng 2016-2-22 上午8:46:34
	 */
	public void rookieTrackingsForDeliveryBack(ArriveSheetEntity entity);

	/**
	 * 封装的方法 客户通知(推送给中转) 赋值,调用方法推送轨迹
	 * @author 339073_guojing
	 */
	public void rookieTrackingByNotification(NotificationEntity entity);
}
