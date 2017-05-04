/**  
 * Project Name:tfr-departure  
 * File Name:SendSmsAfterDeliverDepartJobService.java  
 * Package Name:com.deppon.foss.module.transfer.departure.server.service.impl  
 * Date:2015年5月24日下午3:45:13  
 * Copyright (c) 2015, shiwei@outlook.com All Rights Reserved.  
 *  
*/  
  
package com.deppon.foss.module.transfer.departure.server.service.impl;  

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.common.api.server.service.ISMSSendLogService;
import com.deppon.foss.module.base.common.api.server.service.ISMSTempleteService;
import com.deppon.foss.module.base.common.api.shared.domain.SMSSendLogEntity;
import com.deppon.foss.module.base.common.api.shared.dto.SmsParamDto;
import com.deppon.foss.module.base.common.api.shared.exception.SMSTempleteException;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrJobTodoService;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessGoalContants;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessSceneConstants;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobTodoEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.TfrJobTodoQueryDto;
import com.deppon.foss.module.transfer.departure.api.server.dao.ISendSmsAfterDeliverDepartJobDao;
import com.deppon.foss.module.transfer.departure.api.server.service.ISendSmsAfterDeliverDepartJobService;
import com.deppon.foss.module.transfer.departure.api.shared.define.DepartureConstant;
import com.deppon.foss.module.transfer.departure.api.shared.dto.SendSmsAfterDeliverDepartDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**  
 * ClassName:SendSmsAfterDeliverDepartJobService <br/>  
 * Reason:   派送放行时，给收货客户发短信，供job调用. <br/>  
 * Date:     2015年5月24日 下午3:45:13 <br/>  
 * @author   shiwei-045923 shiwei@outlook.com  
 * @version    
 * @since    JDK 1.6  
 * @see        
 */
public class SendSmsAfterDeliverDepartJobService implements
		ISendSmsAfterDeliverDepartJobService {
	
	/**
	 * 每次处理1000条
	 */
	private static final int COUNT = 1000;
	
	private static final String SYSTEM_CODE = "FOSS";
	
	/**
	 * 用于短信发送前查询、短信发送后更新
	 */
	private ITfrJobTodoService tfrJobTodoService;
	
	/**
	 * 用于根据短信模板拼接短信内容
	 */
	private ISMSTempleteService sMSTempleteService;
	
	/**
	 * 用于发送短信
	 */
	private ISMSSendLogService smsSendLogService;
	
	/**
	 * 用于记录错误日志
	 */
	private ITfrCommonService tfrCommonService;
	
	public void setSmsSendLogService(ISMSSendLogService smsSendLogService) {
		this.smsSendLogService = smsSendLogService;
	}
	
	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}

	/**
	 * 根据车辆放行ID获取运单明细
	 */
	private ISendSmsAfterDeliverDepartJobDao sendSmsAfterDeliverDepartJobDao;
	
	public void setSendSmsAfterDeliverDepartJobDao(
			ISendSmsAfterDeliverDepartJobDao sendSmsAfterDeliverDepartJobDao) {
		this.sendSmsAfterDeliverDepartJobDao = sendSmsAfterDeliverDepartJobDao;
	}

	/**
	 * log
	 */
	public static final Logger LOGGER = LoggerFactory.getLogger(SendSmsAfterDeliverDepartJobService.class);

	public void setsMSTempleteService(ISMSTempleteService sMSTempleteService) {
		this.sMSTempleteService = sMSTempleteService;
	}

	public void setTfrJobTodoService(ITfrJobTodoService tfrJobTodoService) {
		this.tfrJobTodoService = tfrJobTodoService;
	}

	/**
	 * 
	 * job入口方法.  
	 * @see com.deppon.foss.module.transfer.departure.api.server.service.ISendSmsAfterDeliverDepartJobService#SendSmsAfterDeliverDepart()
	 */
	@Override
	public int SendSmsAfterDeliverDepart() {
		/**
		 * step 1，构造查询条件
		 */
		TfrJobTodoQueryDto queryDto = new TfrJobTodoQueryDto();
		//发生场景：派送车辆放行
		queryDto.setBusinessSceneList(new String[]{BusinessSceneConstants.BUSINESS_SCENE_DELIVER_DEPART});
		//下一目标：给收货客户发送短信
		queryDto.setBusinessGoalList(new String[]{BusinessGoalContants.BUSINESS_GOAL_SENDSMS_AFTER_DELIVERDEPART});
		queryDto.setCount(COUNT);
		
		/**
		 * step 2：查询
		 */
		List<TfrJobTodoEntity> todoList = tfrJobTodoService.queryJobTodo(queryDto);
		/**
		 * step 3：逐个处理
		 */
		for(TfrJobTodoEntity entity : todoList){
			this.SendSmsAfterDeliverDepartByTruckDepartID(entity.getBusinessID(),entity.getId());
		}
		return FossConstants.SUCCESS;
	}

	/**
	 * 
	 * 按派送放行任务来处理，每次放行一个事务.  
	 * @see com.deppon.foss.module.transfer.departure.api.server.service.ISendSmsAfterDeliverDepartJobService#SendSmsAfterDeliverDepartByTruckDepartID(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public int SendSmsAfterDeliverDepartByTruckDepartID(String truckDepartID,String jobTodoID) {
		/**
		 * step 1：根据车辆放行ID获取派送运单明细
		 */
		List<SendSmsAfterDeliverDepartDto> waybillDtoList = sendSmsAfterDeliverDepartJobDao.queryDeliverDetailByTruckDepartID(truckDepartID);
		
		/**
		 * step 2：逐个运单发送短信通知
		 */
		for(SendSmsAfterDeliverDepartDto dto : waybillDtoList){
			try{
				//获取待发送短信实体
				SMSSendLogEntity smsEntity = this.getSmsEntity(dto);
				//发送短信
				smsSendLogService.sendSMS(smsEntity);
			}catch(Exception e){
				//记录出错日志
				TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
				jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.SEND_SMS_AFTER_DELIVER_DEPART_JOB.getBizName());
				jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.SEND_SMS_AFTER_DELIVER_DEPART_JOB.getBizCode());
				jobProcessLogEntity.setRemark("运单号【" + dto.getWaybillNo() + "】派送放行后发送短信失败");
				jobProcessLogEntity.setExceptionInfo(e.getMessage());
				jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
				tfrCommonService.addJobProcessLog(jobProcessLogEntity);
				continue;
			}
		}
		
		/**
		 * step 3：更新待处理信息
		 */
		tfrJobTodoService.updateJobTodoByID(jobTodoID);
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 
	 * getSmsEntity:构造待发送短信实体. <br/>  
	 *  
	 *  Date:2015年5月24日下午5:02:59  
	 * @author shiwei-045923 shiwei@outlook.com  
	 * @param dto
	 * @return  
	 * @since JDK 1.6
	 */
	private SMSSendLogEntity getSmsEntity(SendSmsAfterDeliverDepartDto dto){
		
		SMSSendLogEntity smsEntity = new SMSSendLogEntity();
		//发送部门编码
		smsEntity.setSenddeptCode(dto.getOrgCode());
		//发送人员编码-使用司机工号
		smsEntity.setSenderCode(dto.getDriverCode());
		// 电话-使用运单收件人的手机号码
		smsEntity.setMobile(dto.getReceiveCustomerMobilephone());
		//运单号
		smsEntity.setWaybillNo(dto.getWaybillNo());
		// 唯一标识
		smsEntity.setUnionId(UUIDUtils.getUUID());
		// 短信内容
		smsEntity.setContent(this.getSmsContent(dto));
		//发送时间
		smsEntity.setSendTime(new Date());
		// 业务类型
		smsEntity.setMsgtype(DepartureConstant.SMS_TEMPLATE_CODE_DELIVER_DEPART);
		// 短信来源
		smsEntity.setMsgsource(SYSTEM_CODE);
		//服务类型，传入1，表示只发送短信，不发送语音
		smsEntity.setServiceType(NumberConstants.NUMERAL_S_ONE);
		return smsEntity;
	}
	
	/**
	 * 
	 * getSmsContent : 获取待发送的短信的内容. <br/>  
	 *  
	 *  Date:2015年5月24日下午4:06:25  
	 * @author shiwei-045923 shiwei@outlook.com  
	 * @param dto
	 * @return  
	 * @since JDK 1.6
	 */
	private String getSmsContent(SendSmsAfterDeliverDepartDto dto){
		String sms = ""; // 返回短信
		// 查询参数
		SmsParamDto smsParamDto = new SmsParamDto();
		// 短信模板编码
		smsParamDto.setSmsCode(DepartureConstant.SMS_TEMPLATE_CODE_DELIVER_DEPART);
		// 部门编码
		smsParamDto.setOrgCode(dto.getOrgCode());
		smsParamDto.setMap(this.getSmsMap(dto));
		try {
			sms = sMSTempleteService.querySmsByParam(smsParamDto);
		} catch (SMSTempleteException e) {//捕获异常
			LOGGER.error("获取短信模板发生异常", e);//记录日志
			throw new BusinessException("获取短信模板发生异常");
		}
		if (StringUtil.isBlank(sms)) {
			LOGGER.error("没有对应的短信模版");//记录日志
			throw new BusinessException("没有对应的短信模板");//没有对应的短信模版
		}
		return sms;
	}
	
	/**
	 * 
	 * getSmsMap:构造查询短信模板map，用于获取短信内容. <br/>  
	 *  
	 *  Date:2015年5月24日下午4:21:21  
	 * @author shiwei-045923 shiwei@outlook.com  
	 * @param dto
	 * @return  
	 * @since JDK 1.6
	 */
	private Map<String,String> getSmsMap(SendSmsAfterDeliverDepartDto dto){
		Map<String, String> map = new HashMap<String, String>();
		// 运单号
		map.put("waybillNo", dto.getWaybillNo());
		// 发货客户姓名
		map.put("deliveryCustomerName", dto.getDeliveryCustomerName());
		//收货客户姓名
		map.put("receiveCustomerName", dto.getReceiveCustomerName());
		//开单件数
		map.put("goodsQtyTotal", dto.getGoodsQtyTotal().toString());
		//到付款
		map.put("toPayAmount", dto.getToPayAmount().toString());
		//派送件数
		map.put("deliverQtyTotal", dto.getDeliverQtyTotal().toString());
		//派送重量
		map.put("deliverWeight", dto.getDeliverWeight().toString());
		//派送体积
		map.put("deliverVolume", dto.getDeliverVolume().toString());
		//派送司机
		map.put("driverName", dto.getDriverName());
		//派送司机手机号码
		map.put("driverMobilephone", dto.getDriverMobilephone());
		return map;
	}

}
  
