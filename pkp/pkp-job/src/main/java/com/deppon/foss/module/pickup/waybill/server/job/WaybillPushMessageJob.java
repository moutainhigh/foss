package com.deppon.foss.module.pickup.waybill.server.job;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPictureDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISendSmsVoiceService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPendingService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.NotificationEntity2;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPushMessageEntity;
import com.deppon.foss.module.pickup.waybill.shared.util.PropertiesUtil;
import com.deppon.foss.util.CollectionUtils;

             
public class WaybillPushMessageJob extends GridJob implements StatefulJob {

	private static final Logger LOGGER = LoggerFactory.getLogger(WaybillPushMessageJob.class);
	
	@Override
	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			LOGGER.info("WaybillPushMessageJob begin");
			//执行监控运单号
			IWaybillPendingService waybillPendingService = getBean("waybillPendingService", IWaybillPendingService.class);
			ISendSmsVoiceService  sendSmsVoiceService= getBean("sendSmsVoiceService", ISendSmsVoiceService.class);
			IWaybillPictureDao waybillPictureDao  =  getBean("waybillPictureDao", IWaybillPictureDao.class);
			//执行任务 间隔时间
			String minute = PropertiesUtil.getKeyValue(WaybillConstants.PUSH_MESSAGE_WAYBILL_MINUTE);
			int min=NumberConstants.NUMBER_5;
			if(minute!=null&&!minute.equals("")){
				min=Integer.parseInt(minute);
			}
			List<WaybillPushMessageEntity> list=waybillPendingService.queryWaybillPushMessageEntity(min);
			if(CollectionUtils.isNotEmpty(list)){
				LOGGER.info("WaybillPushMessageJob begin" + list.size());
				NotificationEntity2 notificationEntity=null;
				for(WaybillPushMessageEntity e :list){
					try{
						notificationEntity = new NotificationEntity2();
						// 设置订单号
						notificationEntity.setWaybillNo(e.getWaybillCode());
						// 设置通知模块名  
						notificationEntity.setModuleName(NotifyCustomerConstants.BS_TYPE_PKP_WAYBILL);
						// 设置通知类型
						notificationEntity.setNoticeType(NotifyCustomerConstants.NOTIFY_TYPE_SMS_NOTICE);
						// 设置通知内容
						notificationEntity.setNoticeContent(e.getPushMessage());
						// 设置手机号
						
						notificationEntity.setMobile(e.getPhone());
						notificationEntity.setConsignee(e.getDriverName());
						// 设置发送对象
						// 设置操作人
						notificationEntity.setOperator(e.getCreateUserName());
						// 设置操作人编号
						notificationEntity.setOperatorNo(e.getCreateUserCode());
						// 设置操作部门名称
						notificationEntity.setOperateOrgName(e.getCreateDeptName());
						// 设置操作部门编码
						notificationEntity.setOperateOrgCode(e.getCreateDeptCode());
						sendSmsVoiceService.sendDriverCodeSms(notificationEntity);
						waybillPictureDao.delWaybillPushMessage(e);
					}catch(Exception ee){
						LOGGER.error("error", ee);
						throw new JobExecutionException("执行监控运单号还未收到手机端反馈已接 服务异常", ee);
					}
					
				}
			}
			LOGGER.info("WaybillPushMessageJob end");
		} catch (Exception e) {
			LOGGER.error("error", e);
			throw new JobExecutionException("执行监控运单号还未收到手机端反馈已接 服务异常", e);
		}
	}

}
