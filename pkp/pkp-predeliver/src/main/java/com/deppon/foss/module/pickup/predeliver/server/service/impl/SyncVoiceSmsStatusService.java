package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.common.api.shared.domain.SMSFailLogEntity;
import com.deppon.foss.module.base.common.api.shared.domain.SMSSendLogEntity;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.INotifyCustomerDao;
import com.deppon.foss.module.pickup.predeliver.api.server.service.ISyncVoiceSmsStatusService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.util.define.FossConstants;
/**
 * 综合同步语音短信状态（仅限业务类型为NotifyCustomerConstants.BS_TYPE_PKP_NOTIFY和NotifyCustomerConstants.BS_TYPE_PKP_SIGNRETURNPROCESS）
 * @author 038590-foss-wanghui
 * @date 2013-6-6 下午5:44:46
 */
public class SyncVoiceSmsStatusService implements ISyncVoiceSmsStatusService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SyncVoiceSmsStatusService.class);
	// 通知客户dao
	private INotifyCustomerDao notifyCustomerDao;
	/**
	 * 
	 * 同步语音短信状态（仅限业务类型为NotifyCustomerConstants.BS_TYPE_PKP_NOTIFY和NotifyCustomerConstants.BS_TYPE_PKP_SIGNRETURNPROCESS）
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-6-6 下午5:49:10
	 */
	@Override
	@Transactional
	public void syncStatus(SMSFailLogEntity smsFailLog, SMSSendLogEntity smsSendLog) {
		// 如果是通知客户和签收单返单模块则需要同步短信语音状态
		if(smsSendLog != null 
				&& (StringUtil.equals(NotifyCustomerConstants.BS_TYPE_PKP_NOTIFY, smsSendLog.getMsgtype())
						|| StringUtil.equals(NotifyCustomerConstants.BS_TYPE_PKP_SIGNRETURNPROCESS, smsSendLog.getMsgtype())
						)){
			LOGGER.info("同步短信语音发送状态" + smsSendLog.getWaybillNo() + "-" + smsFailLog.getIsSuccessed() + "-" + smsFailLog.getId());
			// 客户通知信息entity
			NotificationEntity newEntity = new NotificationEntity();
			if (FossConstants.NO.equals(smsFailLog.getIsSuccessed())) {
				// 发送失败
				newEntity.setNoticeResult(NotifyCustomerConstants.FAILURE);
				newEntity.setExceptionNotes(smsFailLog.getFailReason());
			} else {
				// 发送成功
				newEntity.setNoticeResult(NotifyCustomerConstants.SUCCESS);
			}
			// 设置id
			newEntity.setId(smsFailLog.getFailIdentity());
			if (newEntity != null && StringUtil.isNotEmpty(newEntity.getNoticeResult())) {
				// 更新通知状态及错误信息
				int updateCount = this.notifyCustomerDao.updateNotificationEntity(newEntity);
				if (updateCount == 0) {
					// 没有记录，为语音附带短信
					return;
				}
				// 客户通知时，更新运单通知状态
				if (StringUtil.equals(NotifyCustomerConstants.BS_TYPE_PKP_NOTIFY, smsSendLog.getMsgtype())) {
					// 运单实际货物entity
					ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
					// 运单号
					actualFreightEntity.setWaybillNo(smsSendLog.getWaybillNo());
					// 通知状态
					actualFreightEntity.setNotificationResult(newEntity.getNoticeResult());
					this.updateActualFreightEntityByWaybillNo(actualFreightEntity);
				}
			}
		}
	}

	public void setNotifyCustomerDao(INotifyCustomerDao notifyCustomerDao) {
		this.notifyCustomerDao = notifyCustomerDao;
	}

	public int updateActualFreightEntityByWaybillNo(ActualFreightEntity actualFreightEntity) {
		// 打印日志
		LOGGER.info("待更新的actualFreightEntity：" + ReflectionToStringBuilder.toString(actualFreightEntity));
		return notifyCustomerDao.updateActualFreightEntityByWaybillNo(actualFreightEntity);
	}
	
}
