package com.deppon.foss.module.pickup.predeliver.api.server.service;

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.InvoiceInfomationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerConditionDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;

/**
 * 计划提前通知接口
* @ClassName: IArriveNoticeService 
* @author 329757-foss-liuxiangcheng 
* @date 2016-6-15 下午5:26:37 
*
 */
public interface IArriveNoticeService extends IService{

	/**
	 * 根据条件查询通知信息总条数
	 * @author 329757-foss-liuxiangcheng 
	 * @date 2016-6-15 下午5:31:51
	 */
	Long queryWaybillInfoCount(NotifyCustomerConditionDto conditionDto);

	/**
	 * 根据条件查询通知信息列表
	* @author 329757-foss-liuxiangcheng 
	* @date 2016-6-15 下午5:33:25
	 */
	List<NotifyCustomerDto> queryWaybillInfoList(
			NotifyCustomerConditionDto conditionDto, int start, int limit);

	/**
	 * 查询计划提前通知信息  不分页  导出表格用
	* @author 329757-foss-liuxiangcheng 
	* @date 2016-6-16 上午9:43:50
	 */
	InputStream queryArriveNotices(NotifyCustomerConditionDto conditionDto);

	/**
	 * add by 329757
	 * 保存计划到达通知信息
	 * @param conditionDto
	 */
	void addNotificationInfo(NotifyCustomerConditionDto conditionDto);

	/**
	 * add by 329757
	 * 批量保存计划到达通知信息
	 * @param conditionDto
	 */
	void batchNotify(NotifyCustomerConditionDto conditionDto);

	/**
	 * add by 329757
	 * 添加运单通知信息
	 * @param notificationEntity
	 * @param invoiceInfomationEntity
	 */
	void addNotifyCustomer(NotificationEntity notificationEntity,
			InvoiceInfomationEntity invoiceInfomationEntity);

	/**
	 * add by 329757
	 * 根据运单编号，更新运单附属表的通知信息.
	 * @param actualFreightEntity
	 * @return
	 */
	int updateActualFreightEntityByWaybillNo(
			ActualFreightEntity actualFreightEntity);
}
