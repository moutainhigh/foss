package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.SqlUtil;
import com.deppon.foss.framework.server.web.message.IMessageBundle;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IEmployeeDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.common.api.server.service.ISMSTempleteService;
import com.deppon.foss.module.base.common.api.shared.dto.SmsParamDto;
import com.deppon.foss.module.base.common.api.shared.exception.SMSTempleteException;
import com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.NotifyCustomerException;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IPendingSendSMSDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IPendingSendSMSLogDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPictureDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPendingSendSMSService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWeixinSendService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WeixinConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.PendingSendSMSEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PendingSendSMSLogEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPictureEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.SmsVoiceParamDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WeixinSendDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.PendingSendSMSException;
import com.deppon.foss.module.pickup.waybill.shared.vo.PendingSendSMSVo;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 待处理发送短信服务实现类
 * @author WangQianJin
 * @date 2013-4-11 上午11:08:45
 */
public class PendingSendSMSService implements IPendingSendSMSService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PendingSendSMSService.class);
	//每次要查询的条数
	private static final String queryNum = "1000";
	//要查询的JobId
	public static final String queryJobId = "N/A";
	
	private static final int NUMBER_490 = 490;
	
	/**
	 * 待处理发送短信Dao
	 */
	private IPendingSendSMSDao pendingSendSMSDao;
	/**
	 * 待处理发送短信日志Dao
	 */
	private IPendingSendSMSLogDao pendingSendSMSLogDao;
	
	/**
	 * 组织信息 Service接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	private IWaybillExpressService waybillExpressService;
	/**
	 * 短信模板服务
	 */
	private ISMSTempleteService sMSTempleteService;
	
	/**
	 * 通知客户服务
	 */
	private INotifyCustomerService notifyCustomerService;
	
	/**
	 * 国际化信息
	 * 
	 * @author WangQianJin
	 * @date 2013-04-11
	 */
	private IMessageBundle messageBundle; 
	
	//国家
	private static final String CN = "CN";
	//中文
	private static final String ZH = "zh";
	
	/**
	 * 注入pendingSendSMSService，为了实现事务
	 */
	private IPendingSendSMSService pendingSendSMSService;	
	/**
	 * 微信服务类
	 */
	private IWeixinSendService weixinSendService;
	
	/**
	 * 图片开单持久化层
	 * foss-278328-hujinyang  20151031
	 */
	private IWaybillPictureDao waybillPictureDao;
	
	/**
	 * 雇员DAO
	 */                  
	private IEmployeeDao employeeDao;
	
	public IEmployeeDao getEmployeeDao() {
		return employeeDao;
	}

	public void setEmployeeDao(IEmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}
	
	/**
	 * 微信服务类
	 */
	public void setWeixinSendService(IWeixinSendService weixinSendService) {
		this.weixinSendService = weixinSendService;
	}
	
	public void setPendingSendSMSService(
			IPendingSendSMSService pendingSendSMSService) {
		this.pendingSendSMSService = pendingSendSMSService;
	}

	public void setWaybillPictureDao(IWaybillPictureDao waybillPictureDao) {
		this.waybillPictureDao = waybillPictureDao;
	}

	/**
	 * @param waybillExpressService the waybillExpressService to set
	 */
	public void setWaybillExpressService(
			IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}



	/**
	 * 本地语言信息，用于国际化在JOB中出现的异常
	 * 
	 * @author WangQianJin
	 * @date 2013-04-11
	 */
	private final static Locale LOCALE = new Locale(ZH, CN);

	public void setMessageBundle(IMessageBundle messageBundle) {
		this.messageBundle = messageBundle;
	}
	
	/**
	 * 设置 通知客户服务.
	 */
	public void setNotifyCustomerService(INotifyCustomerService notifyCustomerService) {
		this.notifyCustomerService = notifyCustomerService;
	}
	
	/**
	 * 设置 短信模板服务
	 */
	public void setsMSTempleteService(ISMSTempleteService sMSTempleteService) {
		this.sMSTempleteService = sMSTempleteService;
	}
	
	/**
	 *@param orgAdministrativeInfoService the orgAdministrativeInfoService to set.
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	

	

	
	/**
	 * 添加待处理发送短信
	 * @author WangQianJin
	 * @date 2013-4-11 上午11:10:38
	 */
	@Override
	public void addPendingSendmail(PendingSendSMSEntity pendingSendmail) {
		/**
		 * 判断添加待处理发送短信信息各个属性是否为空，如果为空，则抛出异常给予提示
		 */
		if (pendingSendmail == null) {
			throw new PendingSendSMSException(PendingSendSMSException.PENDING_SENDMAIL_ENTITY_IS_NULL);
		}		
		//添加待处理发送短信信息
		pendingSendSMSDao.addPendingSendmailEntity(pendingSendmail);
	}
	
	/**
	 * 定时任务处理待发送短信
	 * @author WangQianJin
	 * @date 2013-4-11 上午11:10:38
	 */
	@Override
	public void batchjobs() {		
		/**
		 * 创建VO，并设置受影响条数为queryNum，为了第一次能进入循环
		 */
		PendingSendSMSVo vo=new PendingSendSMSVo();
		vo.setResultNum(Integer.parseInt(queryNum));
		while(vo.getResultNum()==Integer.parseInt(queryNum)){
			String jobId = UUIDUtils.getUUID();
			//更新一定数量的JobId
			vo=pendingSendSMSService.updatePendingSendSMSForJobTopNum(jobId,queryNum);	
			//根据JobId查询待处理信息
			List<PendingSendSMSEntity> psList = pendingSendSMSDao.queryPendingSendmailEntityByJobID(vo.getJobId());
			if (psList!=null && psList.size() > 0) {			
				batchSendSMS(psList);
			}
		}
	}
	
	/**
	 * 批处理，发送短信
	 * 
	 * @author WangQianJin
	 * @date 2013-04-11
	 */
	private void batchSendSMS(List<PendingSendSMSEntity> list) {
		if(CollectionUtils.isEmpty(list)){
			return;
		}
		for (PendingSendSMSEntity pendingSendmailEntity : list) {
			this.singleHandlePendingSendMail(pendingSendmailEntity);
		}
	}
	

	/**
	 * 执行单个的短信发送
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-6-12 11:20:16
	 */
	/**
	 * (非 Javadoc) 
	* @author 270293-pkpfoss-zhangfeng 
	* @date 2015-7-25 下午5:20:46 
	* <p>Title: singleHandlePendingSendMail</p> 
	* <p>Description:DP-FOSS快递返货短信通知优化需求V0.1 </p> 
	* @param pendingSendmailEntity 
	* @see com.deppon.foss.module.pickup.waybill.api.server.service.IPendingSendSMSService#singleHandlePendingSendMail(com.deppon.foss.module.pickup.waybill.shared.domain.PendingSendSMSEntity)
	 */
	@Override
	public void singleHandlePendingSendMail(PendingSendSMSEntity pendingSendmailEntity) {
		try {
			//创建客户通知记录
			NotificationEntity entity = new NotificationEntity();
			 //运单号
			entity.setWaybillNo(pendingSendmailEntity.getWaybillNo());
			//通知内容
			String content = null;
			if(NotifyCustomerConstants.BS_TYPE_PKP_WAYBILLRFC.equals(pendingSendmailEntity.getModuleName())){
				content = buildDeliverySmsContent(pendingSendmailEntity);
			}else if(NotifyCustomerConstants.BS_TYPE_PKP_WAYBILLEXP.equals(pendingSendmailEntity.getModuleName())){
				//EXPRESS_WAYBILL_CONSIGNEE
				// 获取返货单开单对象
				WaybillExpressEntity newWaybillNoEntity = waybillExpressService.queryWaybillByWaybillNo(pendingSendmailEntity.getWaybillNo(),WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO);
				if (null != newWaybillNoEntity&& StringUtil.isNotEmpty(newWaybillNoEntity.getOriginalWaybillNo())&& newWaybillNoEntity.getReturnType().equals("RETURN_CARGO")) {
					// 判断是否是返单
					// 获取返单要发短信内容1.返货单发货人+2.原单收货人
					if (NotifyCustomerConstants.SENDMAIL_TARGET_CONSIGNER
							.equals(pendingSendmailEntity.getSendTarget())) {
						content = querySmsTemplate(
								gainSmsVoiceParamDto(pendingSendmailEntity,
										null, newWaybillNoEntity),
								NotifyCustomerConstants.EXPRESS_RETURN_TO_CONSIGNOR_SMS,
								null);

					} else if (NotifyCustomerConstants.SENDMAIL_TARGET_CONSIGNEE
							.equals(pendingSendmailEntity.getSendTarget())) {
						content = querySmsTemplate(
								gainSmsVoiceParamDto(pendingSendmailEntity,
										null, newWaybillNoEntity),
								NotifyCustomerConstants.EXPRESS_RETURN_TO_RECEIVER_SMS,
								null);
					}
				} else {
					if (null == newWaybillNoEntity&& NotifyCustomerConstants.SENDMAIL_TARGET_CONSIGNEE
							.equals(pendingSendmailEntity.getSendTarget())) {
						content = querySmsTemplate(
								gainSmsVoiceParamDto(pendingSendmailEntity,
										null),
								NotifyCustomerConstants.EXPRESS_WAYBILL_RECEIVE_CUSTOMER_SMS,
								pendingSendmailEntity.getOperateOrgCode());
					}
				}
			} else{					
				/**
				 * 判断发送目标是否是发货人
				 */
				if(NotifyCustomerConstants.SENDMAIL_TARGET_CONSIGNEE.equals(pendingSendmailEntity.getSendTarget())){
					content = querySmsTemplate(gainSmsVoiceParamDto(pendingSendmailEntity,null),NotifyCustomerConstants.SMS_CODE_CONSIGNEE_SELF_PICKUP,pendingSendmailEntity.getOperateOrgCode());
				}else if(judgeFour(pendingSendmailEntity.getSendTarget())){
					content = querySmsTemplate(gainSmsVoiceParamDto(pendingSendmailEntity,null),pendingSendmailEntity.getSendTarget(),pendingSendmailEntity.getOperateOrgCode());
				}else{
					content = querySmsTemplate(gainSmsVoiceParamDto(pendingSendmailEntity,null),NotifyCustomerConstants.SMS_CODE_CONSIGNOR_TO_DOOR,pendingSendmailEntity.getOperateOrgCode());
				}	
			}
			entity.setNoticeContent(content);
			pendingSendmailEntity.setNoticeContent(content);
			//pendingSendmailEntity.setNoticeContent(content);
			//接收人姓名
			entity.setConsignee(pendingSendmailEntity.getConsignee());
			// 手机号
			entity.setMobile(pendingSendmailEntity.getMobile());
			 //模块名称
			entity.setModuleName(pendingSendmailEntity.getModuleName());
			//通知类型
			entity.setNoticeType(pendingSendmailEntity.getNoticeType());
			 //操作人
			entity.setOperator(pendingSendmailEntity.getOperator());
			 //操作人编码
			entity.setOperatorNo(pendingSendmailEntity.getOperatorNo());
			 //操作部门
			entity.setOperateOrgName(pendingSendmailEntity.getOperateOrgName());
			 //操作部门编码
			entity.setOperateOrgCode(pendingSendmailEntity.getOperateOrgCode());
			//操作时间
			entity.setOperateTime(new Date());		
			//执行发送短信接口
			if (executeSendMail(entity, pendingSendmailEntity)) {
				afterExecuteSendMail(pendingSendmailEntity);
			}
			//给微信平台推送消息
			if(!NotifyCustomerConstants.BS_TYPE_PKP_WAYBILLRFC.equals(pendingSendmailEntity.getModuleName())){
				//因为目前开单都会给开单和收货人推送消息，造成重复，所以只取给发货人的就成，由微信平台在开单这一环节自行发送两次
				if(NotifyCustomerConstants.SENDMAIL_TARGET_CONSIGNEE.equals(pendingSendmailEntity.getSendTarget())){
					LOGGER.info("=========短信发送完毕，微信消息推送开始=========单号："+pendingSendmailEntity.getWaybillNo());
					WeixinSendDto dto = new WeixinSendDto();
					//运单号
					dto.setWaybillNo(pendingSendmailEntity.getWaybillNo());
					//设置状态类型
					dto.setStateType(WeixinConstants.WEIXIN_CREATE_TYPE);
					//得到返回类型
					ResultDto resultDto = weixinSendService.sysnWeixinInfoForWebSiteUnDirectly(dto,WeixinConstants.WEIXIN_CREATE_TYPE);
					//错误详情日志,突然发现记录日志是件很高尚的事情,很方便去排查问题耶
					if(ResultDto.FAIL.equals(resultDto.getCode())){
						LOGGER.info("推送消息失败,错误详情："+resultDto.getMsg());
					}
					LOGGER.info("微信功能处理完毕");
				}
			}
		} catch (Exception e) {
			LOGGER.info("执行批量发送短信出现异常,异常信息为:");
			e.printStackTrace();
			String faileReason = ExceptionUtils.getFullStackTrace(e);
			if (StringUtils.isNotEmpty(faileReason) && faileReason.length() > NUMBER_490) {
				faileReason = faileReason.substring(0, NUMBER_490);
			}
			pendingSendmailEntity.setFaileReason(faileReason);
			pendingSendSMSDao.updateOperateTypeByID(pendingSendmailEntity);
		}
	}
	
	/**
	 * 更改单短信模版
	 * @param pendingSendmailEntity
	 * @param contact
	 * @return
	 */
	protected String buildDeliverySmsContent(PendingSendSMSEntity pendingSendmailEntity){
		//根据部门编码查询部门信息
		SmsParamDto paramDto = new SmsParamDto();
		paramDto.setSmsCode(NotifyCustomerConstants.SMS_CODE_WAYBILL_RFC_ACCEPT);
		paramDto.setOrgCode(pendingSendmailEntity.getOperateOrgCode());
		Map<String,String> placeFieldMap = new HashMap<String, String>();
		placeFieldMap.put("customerContact", pendingSendmailEntity.getConsignee());
		placeFieldMap.put("waybillNo", pendingSendmailEntity.getWaybillNo());
		placeFieldMap.put("changedItems", pendingSendmailEntity.getReceiveCustomerAddress());
		placeFieldMap.put("drafterOrgPhone",pendingSendmailEntity.getCustomerPickupOrgCode());
		paramDto.setMap(placeFieldMap);
		//获得短信内容
		String sendContent = sMSTempleteService.querySmsByParam(paramDto);
		return sendContent;
	}
	
	/**
	 * 调用发送短信之后
	 */
	@Transactional
	private void afterExecuteSendMail(PendingSendSMSEntity pendingSendmailEntity) {		
		/**
		 * 封装待处理发送短信日志实体
		 */
		PendingSendSMSLogEntity logEntity = new PendingSendSMSLogEntity();		
		logEntity.setJobId(pendingSendmailEntity.getJobId());
		logEntity.setWaybillNo(pendingSendmailEntity.getWaybillNo());
		logEntity.setNoticeType(pendingSendmailEntity.getNoticeType());
		logEntity.setNoticeContent(pendingSendmailEntity.getNoticeContent());
		logEntity.setOperator(pendingSendmailEntity.getOperator());
		logEntity.setOperatorNo(pendingSendmailEntity.getOperatorNo());
		logEntity.setOperateOrgName(pendingSendmailEntity.getOperateOrgName());
		logEntity.setOperateOrgCode(pendingSendmailEntity.getOperateOrgCode());
		logEntity.setConsignee(pendingSendmailEntity.getConsignee());
		logEntity.setMobile(pendingSendmailEntity.getMobile());
		logEntity.setOperateTime(new Date());
		logEntity.setModuleName(pendingSendmailEntity.getModuleName());
		logEntity.setDeliveryCustomerContact(pendingSendmailEntity.getDeliveryCustomerContact());
		logEntity.setGoodsQtyTotal(pendingSendmailEntity.getGoodsQtyTotal());
		logEntity.setReceiveCustomerAddress(pendingSendmailEntity.getReceiveCustomerAddress());
		logEntity.setTotalFee(pendingSendmailEntity.getTotalFee());
		logEntity.setCustomerPickupOrgCode(pendingSendmailEntity.getCustomerPickupOrgCode());
		logEntity.setSendTarget(pendingSendmailEntity.getSendTarget());
		logEntity.setOperateType(pendingSendmailEntity.getOperateType());
		/**
		 * 新增日志
		 */
		pendingSendSMSLogDao.addPendingSendmailLogEntity(logEntity);
		/**
		 * 删除待处理发送短信的信息
		 */
		pendingSendSMSDao.deleteById(pendingSendmailEntity.getId());
	}
	
	/**
	 * 执行发送短信接口
	 * @author WangQianJin
	 * @date 2013-4-11 下午7:25:33
	 */
	@Transactional
	public boolean executeSendMail(NotificationEntity entity, PendingSendSMSEntity pendingSendmailEntity) {
		//调用接口发送短信
		try {
			notifyCustomerService.sendMessage(entity);
			/**
			 * 设置操作类型
			 */
			pendingSendmailEntity.setOperateType(WaybillConstants.WAYBILL_STOCK_TYPE_SUCCESS);
			return true;
		} catch (NotifyCustomerException e) {
			LOGGER.error(e.getMessage(), e);
			/**
			 * 设置失败原因
			 */
			pendingSendmailEntity.setFaileReason(messageBundle.getMessage(LOCALE, e.getErrorCode(), e.getErrorArguments()));
			/**
			 * 设置操作类型
			 */
			pendingSendmailEntity.setOperateType(WaybillConstants.WAYBILL_STOCK_TYPE_FAILURE);
			/**
			 * 通过ID更新操作类型
			 */
			pendingSendSMSDao.updateOperateTypeByID(pendingSendmailEntity);
			return false;
		}catch (Exception e) {
			/**
			 * 把异常进行字符串转换，把其转换为一串字符串
			 */
			String error = ExceptionUtils.getFullStackTrace(e);
			/**
			 * 如果错误字符串的长度大于490
			 */
			if (error.length() > NUMBER_490) {
				/**
				 * 设置失败原因
				 */
				pendingSendmailEntity.setFaileReason("异常：" + error.substring(0, NUMBER_490));
			} else {
				/**
				 * 如果小于等于490自己设置失败原因
				 */
				pendingSendmailEntity.setFaileReason("异常：" + error);
			}
			/**
			 * 设置操作类型为失败
			 */
			pendingSendmailEntity.setOperateType(WaybillConstants.WAYBILL_STOCK_TYPE_FAILURE);
			/**
			 * 通过ID更新操作类型
			 */
			pendingSendSMSDao.updateOperateTypeByID(pendingSendmailEntity);
			return false;
		}
	}
	
	/**
	 * 根据短信模板编码查询出短信内容并替换相关变量值后返回短信内容
	 * @author WangQianJin
	 * @date 2013-04-12
	 */
	 private String querySmsTemplate(SmsVoiceParamDto dto,String smsCode,String operateOrgCode){
		// 返回短信
		String sms = ""; 
		//非空判断
		if (dto == null) {
			return sms;
		}

		// 模版参数
		SmsParamDto smsParamDto = new SmsParamDto();
		// 短信编码
		smsParamDto.setSmsCode(smsCode);
		// 部门编码
		smsParamDto.setOrgCode(operateOrgCode);
		// 参数Map
		smsParamDto.setMap(this.getSmsCode(dto,smsCode));
		try {
			sms = sMSTempleteService.querySmsByParam(smsParamDto);
			/**
			 * @项目：新客户体验项目
			 * @功能：若为新客户，增加短信内容
			 * @author:218371-foss-zhaoyanjun
			 * @date:2015-06-25下午16:08
			 */
			sms=newCustomerSMS(sms,dto);
		} catch (SMSTempleteException e) {
			LOGGER.error(e.getMessage(), e);
			throw new NotifyCustomerException(NotifyCustomerException.SMSTEMPLETE_NULL, sms);
		} 
		//判断短信语音内容是否为空
		if (StringUtil.isBlank(sms)) {
			throw new NotifyCustomerException(NotifyCustomerException.SMSTEMPLETE_NULL, sms);
		}
		return sms;
	}
	 
	 /**
	 * 封装模板中需要动态赋值的变更信息
	 * @author WangQianJin
	 * @date 2013-04-11
	 */
	private Map<String, String> getSmsCode(SmsVoiceParamDto dto,String smsCode){
		Map<String, String> paramMap = new HashMap<String, String>();
		// 运单号
		if (StringUtil.isNotEmpty(dto.getWaybillNo())) {
			paramMap.put("waybillNo", dto.getWaybillNo());
		}
		// 发货客户联系人
		if (StringUtil.isNotEmpty(dto.getDeliveryCustomerContact())) {
			paramMap.put("deliveryCustomerContact", dto.getDeliveryCustomerContact());
		}
		// 发货市
		if (StringUtil.isNotEmpty(dto.getDeliveryCustomerCity())) {
			paramMap.put("deliveryCustomerCity", dto.getDeliveryCustomerCity());
		}
		// 收货客户地址
		if (StringUtil.isNotEmpty(dto.getDeliveryCustomerContact())) {
			paramMap.put("deliveryCustomerContact",dto.getDeliveryCustomerContact());
		}
		// 货物总件数
		if (StringUtil.isNotEmpty(dto.getGoodsQtyTotal())) {
			paramMap.put("goodsQtyTotal", dto.getGoodsQtyTotal());
		}
		// 总费用 
		if (StringUtil.isNotEmpty(dto.getTotalFee())) {
			paramMap.put("totalFee", dto.getTotalFee());
		}
		// 提货网点名称
		if (StringUtil.isNotEmpty(dto.getCustomerPickupOrg())) {
			paramMap.put("customerPickupOrg", dto.getCustomerPickupOrg());
		}
		// 广告内容
		if (StringUtil.isNotEmpty(dto.getAdContent())) {
			paramMap.put("adContent", dto.getAdContent());
		}
		// 短信模板CODE
		if (StringUtil.isNotEmpty(smsCode)) {
			paramMap.put("smsCode", smsCode);
		}
		// 收货人地址 
		if (StringUtil.isNotEmpty(dto.getReceiveCustomerAddress())) {
			paramMap.put("receiveCustomerAddress", dto.getReceiveCustomerAddress());
		}
		//原单号
		if (StringUtil.isNotEmpty(dto.getOriginalWaybillNo())) {
			paramMap.put("originalWaybillNo", dto.getOriginalWaybillNo());
		}
		
		return paramMap;
	}
	
	/**
	 * 获取短信语音参数DTO
	 * @author WangQianJin
	 * @date 2013-04-11
	 */
	private SmsVoiceParamDto gainSmsVoiceParamDto(PendingSendSMSEntity pendingSendmailEntity,String adContent){
		SmsVoiceParamDto sms = null;
		if(null != pendingSendmailEntity){
			sms = new SmsVoiceParamDto();
			//发货人名称
			sms.setDeliveryCustomerContact(pendingSendmailEntity.getDeliveryCustomerContact());
			//总件数
			sms.setGoodsQtyTotal(String.valueOf(pendingSendmailEntity.getGoodsQtyTotal()));			
			//总费用
			sms.setTotalFee(String.valueOf(pendingSendmailEntity.getTotalFee()));
			//运单号
			sms.setWaybillNo(pendingSendmailEntity.getWaybillNo());
			//广告
			if(adContent != null){
				sms.setAdContent(StringUtil.defaultIfNull(adContent));
			}
			SqlUtil.loadCache = false;   //不读缓存
			/**
			 * 根据部门编码查询部门信息
			 */
			OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(pendingSendmailEntity.getCustomerPickupOrgCode());
//			OrgAdministrativeInfoEntity orgDeliveryInfo = orgAdinistrativeInfoService.queryOrgAdministrativeInfoByCode(pendingSendmailEntity.getOperateOrgCode());
			//处理发送目标类型 图片异地开单出发部门为异地部门   短信出发城市为异地的问题
			OrgAdministrativeInfoEntity orgDeliveryInfo = queryDeliveryCustomerCity(pendingSendmailEntity);
			
			OrgAdministrativeInfoEntity receiveOrgInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(pendingSendmailEntity.getReceiveOrgCode());
			SqlUtil.loadCache = true;    //重新设置是否读缓存
			if(null != orgInfo){
				//提货网点名称
				sms.setCustomerPickupOrg(StringUtil.defaultIfNull(orgInfo.getName()));				
				//收货人城市
				sms.setReceiveCustomerAddress(StringUtil.defaultIfNull(orgInfo.getCityName()));
			}			
			if(null != orgDeliveryInfo){
				//发货人城市名							
				sms.setDeliveryCustomerCity(StringUtil.defaultIfNull(orgDeliveryInfo.getCityName()));				
			}
			/**
			 * @项目：新客户体验项目
			 * @功能：若为新客户，则将费用带入
			 * @author:218371-FOSS-zhaoyanjun
			 * @date:2015-06-23下午16:52
			 */
			if("A".equals(pendingSendmailEntity.getNewOrOld())){
				sms.setTransportFee(String.valueOf(pendingSendmailEntity.getTransportFee()));
				sms.setInsuranceFee(String.valueOf(pendingSendmailEntity.getInsuranceFee()));
				sms.setCodAmount(String.valueOf(pendingSendmailEntity.getCodAmount()));
				sms.setPickupFee(String.valueOf(pendingSendmailEntity.getPickupFee()));
				sms.setDeliveryGoodsFee(String.valueOf(pendingSendmailEntity.getDeliveryGoodsFee()));
				sms.setPackageFee(String.valueOf(pendingSendmailEntity.getPackageFee()));
//				sms.setServiceFee(String.valueOf(pendingSendmailEntity.getServiceFee()));
				sms.setOtherFee(String.valueOf(pendingSendmailEntity.getOtherFee()));
				sms.setNewOrOld(String.valueOf(pendingSendmailEntity.getNewOrOld()));
				sms.setDeptTelephone(receiveOrgInfo.getDepTelephone());
				sms.setCodFee(String.valueOf(pendingSendmailEntity.getCodFee()));
				//查询发货营业部经理电话号码
				String empCode = receiveOrgInfo.getPrincipalNo();
				EmployeeEntity empLoyee = employeeDao.queryEmployeeByEmpCode(empCode);
				if(empLoyee!=null&&StringUtils.isNotBlank(empLoyee.getMobilePhone())){
					sms.setDeliverOrgManagerMP(empLoyee.getMobilePhone());
				}
			}
		}
		return sms;
	}
	
	/**
	 * (非 Javadoc) 
		* @author 270293-foss-zhangfeng
		* @date 2015-7-25 下午5:16:46 
	    * <p>Title: gainSmsVoiceParamDto</p> 
	    * <p>Description:因为上个方法用到的地方比较多，故重载该方法</p> 
	    * @param @param pendingSendmailEntity
	    * @param @param adContent
	    * @param @param newWaybillNoEntity
	    * @return SmsVoiceParamDto
	 */
	private SmsVoiceParamDto gainSmsVoiceParamDto(PendingSendSMSEntity pendingSendmailEntity,String adContent,WaybillExpressEntity newWaybillNoEntity ){
		SmsVoiceParamDto sms = null;
		if(null != pendingSendmailEntity){
			sms = new SmsVoiceParamDto();
			//发货人名称
			sms.setDeliveryCustomerContact(pendingSendmailEntity.getDeliveryCustomerContact());
			//总件数
			sms.setGoodsQtyTotal(String.valueOf(pendingSendmailEntity.getGoodsQtyTotal()));			
			//总费用
			sms.setTotalFee(String.valueOf(pendingSendmailEntity.getTotalFee()));
			//运单号
			sms.setWaybillNo(pendingSendmailEntity.getWaybillNo());
			//广告
			if(adContent != null){
				sms.setAdContent(StringUtil.defaultIfNull(adContent));
			}
			//原单号
			sms.setOriginalWaybillNo(newWaybillNoEntity.getOriginalWaybillNo());
			SqlUtil.loadCache = false;   //不读缓存
			/**
			 * 根据部门编码查询部门信息
			 */
			OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(pendingSendmailEntity.getCustomerPickupOrgCode());
			OrgAdministrativeInfoEntity orgDeliveryInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(pendingSendmailEntity.getOperateOrgCode());
			
			SqlUtil.loadCache = true;    //重新设置是否读缓存
			if(null != orgInfo){
				//提货网点名称
				sms.setCustomerPickupOrg(StringUtil.defaultIfNull(orgInfo.getName()));				
				//收货人城市
				sms.setReceiveCustomerAddress(StringUtil.defaultIfNull(orgInfo.getCityName()));
			}			
			if(null != orgDeliveryInfo){
				//发货人城市名							
				sms.setDeliveryCustomerCity(StringUtil.defaultIfNull(orgDeliveryInfo.getCityName()));				
			}
		}
		return sms;
	}
	
	
	
	/**
	 * 获取发货人城市名	
	 * @param pendingSendmailEntity
	 * @return 发货人城市名	
	 */
	private OrgAdministrativeInfoEntity queryDeliveryCustomerCity(PendingSendSMSEntity pendingSendmailEntity){
		
		//获取本地机构代码
		String  nativeDeptCode = queryNativeDeptNo(pendingSendmailEntity);
		
		OrgAdministrativeInfoEntity orgDeliveryInfo = null;
		try {
			orgDeliveryInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(nativeDeptCode);
		} catch (Exception e) {
			LOGGER.info("PendingSendSMSService----->queryDeliveryCustomerCity 获取发货人城市名异常:"+e.getMessage());
			e.printStackTrace();
		} 
		
		return orgDeliveryInfo;
		
	}
	
	
	/**
	 * 查询图片开单 异地开单 本地机构号
	 * @author 278328-foss-hujinyang
	 * @param pendingSendmailEntity
	 * @param contact
	 * @return 本地机构号 
	 */
	private String queryNativeDeptNo(PendingSendSMSEntity pendingSendmailEntity){
		
		//模块名称
		String moduleName = pendingSendmailEntity.getModuleName();
		
		//只处理判断发送目标类型的
		if(!(NotifyCustomerConstants.BS_TYPE_PKP_WAYBILLRFC.equals(moduleName)) 
				&& !(NotifyCustomerConstants.BS_TYPE_PKP_WAYBILLEXP.equals(moduleName))){
			
			String wayBillNo =pendingSendmailEntity.getWaybillNo();
			
			try {
				if (StringUtils.isNotBlank(wayBillNo)) {
					
					//根据运单号查询图片开单信息
					WaybillPictureEntity waybillPictureEntity = new WaybillPictureEntity();
					waybillPictureEntity.setActive(FossConstants.ACTIVE);
//					waybillPictureEntity
//					.setPendgingType(WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_ACTIVE);
					waybillPictureEntity.setWaybillNo(wayBillNo);
					
					@SuppressWarnings("unchecked")
					List<WaybillPictureEntity> waybillLists = waybillPictureDao
							.queryWaybillPictureByEntity(waybillPictureEntity);
					
					if (CollectionUtils.isNotEmpty(waybillLists)) {
						
						LOGGER.info("PendingSendSMSService----->queryNativeDeptNo 存在图片开单，运单号为: "+wayBillNo);
						
						//开单中运单号唯一
						WaybillPictureEntity entity = waybillLists.get(0);
						String local = entity.getLocal();
						
						if (FossConstants.NO.equals(local)) {//如果为异地  设置机构设置本地开单组
							
							String localGroupCode = entity.getLocalBillGroupCode();

							return localGroupCode;
						}
					}
					
				}
			} catch (Exception e) {
				LOGGER.error("PendingSendSMSService----->queryNativeDeptNo 查询图片开单 异地开单 本地机构号异常:"+e.getMessage());
				e.printStackTrace();
			}
		}
		return  pendingSendmailEntity.getOperateOrgCode();
		
	}
	
	/**
	 * 更新job id.
	 * 
	 * @param uuid the uuid
	 * @return the int
	 * @author WangQianJin
	 * @date 2013-04-12
	 */
	@Transactional
	public String updatePendingSendSMSForJob(List<PendingSendSMSEntity> psList) {
		/**
		 * 获取UUID 
		 */
		String jobId = UUIDUtils.getUUID();
		/**
		 * 通过ID更新JOBID
		 */
		for (PendingSendSMSEntity pendingSendmailEntity : psList) {
			pendingSendmailEntity.setJobId(jobId);
			pendingSendSMSDao.updateJobIDByID(pendingSendmailEntity);
		}
		/**
		 * 返回JOBID
		 */
		return jobId;
	}
	
	
	/**
	 * 每次更新一定数量待处理短信JobId
	 * @author WangQianJin
	 * @date 2013-05-08
	 */
	@Transactional
	public PendingSendSMSVo updatePendingSendSMSForJobTopNum(String jobId,String num){
		PendingSendSMSVo vo=new PendingSendSMSVo();
		vo.setJobId(jobId);
		vo.setQueryNum(num);
		vo.setQueryJobId(queryJobId);
		int result=pendingSendSMSDao.updateJobIDTopByRowNum(vo);
		vo.setResultNum(result);
		return vo;
	}

	
	public IPendingSendSMSDao getPendingSendSMSDao() {
		return pendingSendSMSDao;
	}

	
	public void setPendingSendSMSDao(IPendingSendSMSDao pendingSendSMSDao) {
		this.pendingSendSMSDao = pendingSendSMSDao;
	}

	
	public IPendingSendSMSLogDao getPendingSendSMSLogDao() {
		return pendingSendSMSLogDao;
	}

	
	public void setPendingSendSMSLogDao(IPendingSendSMSLogDao pendingSendSMSLogDao) {
		this.pendingSendSMSLogDao = pendingSendSMSLogDao;
	}
/**
	 * @需求：短信模版需求
	 * @功能：判断是否2015-06-25的四种情况
	 */
	private boolean judgeFour(String type){
		if(NotifyCustomerConstants.SMS_CODE_CONSIGNEE_SELF_PICKUP_FC.equals(type)
				||NotifyCustomerConstants.SMS_CODE_CONSIGNEE_NO_SELF_PICKUP.equals(type)
				||NotifyCustomerConstants.SMS_CODE_CONSIGNEE_PICKUP_SENDGOODS_FC.equals(type)
				||NotifyCustomerConstants.SMS_CODE_CONSIGNEE_PICKUP_SENDGOODS_NO_FC.equals(type)){
			return true;
		}
		return false;
	}    /**
	 * @项目：新客户体验项目
	 * @功能：编辑发送短信信息
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-06-25下午18:41
	 * @modify 2016-03-17 （297064）零担增加判断新客户条件
	 */
	public String newCustomerSMS(String sms, SmsVoiceParamDto dto){
		if("A".equals(dto.getNewOrOld())){
			int i=sms.indexOf("，即日出发");
			if(i<0){
				throw new SMSTempleteException("数据库短信模板格式有问题！");
			}
			String before=sms.substring(0,i);
			/**
			 * @项目：新客户体验项目
			 * @bug号：CEM-30
			 * @功能：修改前部分短信
			 * @author:218371-foss-zhaoyanjun
			 * @date:2015-08-08下午14:55
			 */
			before=updatebefore(before);
			String after=sms.substring(i,sms.length());
			String middle=",包括";
			if(updateMessage(dto.getTransportFee())){
				middle=middle+"运费"+dto.getTransportFee()+"元、";
			}
			if(updateMessage(dto.getInsuranceFee())){
				middle=middle+"保价费"+dto.getInsuranceFee()+"元、";
			}
			if(updateMessage(dto.getCodAmount())){
				middle=middle+"代收货款"+dto.getCodAmount()+"元、";
			}
			if(updateMessage(dto.getCodFee())){
				middle=middle+"代收货款手续费"+dto.getCodFee()+"元、";
			}
			if(updateMessage(dto.getPickupFee())){
				middle=middle+"接货费"+dto.getPickupFee()+"元、";
			}
			if(updateMessage(dto.getDeliveryGoodsFee())){
				middle=middle+"送货费"+dto.getDeliveryGoodsFee()+"元、";
			}
			if(updateMessage(dto.getPackageFee())){
				middle=middle+"包装费"+dto.getPackageFee()+"元、";
			}
//			if(!dto.getServiceFee().isEmpty()){
//				middle=middle+"装卸费"+dto.getServiceFee()+"元、";
//			}
			if(updateMessage(dto.getOtherFee())){
				middle=middle+"其它费用"+dto.getOtherFee()+"元、";
			}
			middle=middle.substring(0, middle.length()-1);
			if(updateMessage(dto.getDeptTelephone())){
				after=after+"如有疑问，请联系营业部："+dto.getDeptTelephone();
			}
			/**
			 * 短信内容增加发货营业部经理联系电话（零担）
			 * @author:297064-foss-yanggang
			 * @date:2016-03-17
			 */
			if(StringUtils.isNotBlank(dto.getDeliverOrgManagerMP())){
				after = after+",经理："+dto.getDeliverOrgManagerMP();
			}
			return before+middle+after;
		}else{
			return sms;
		}
	}
	
	/**
	 * @项目：新客户体验项目
	 * @bug号：CEM-27
	 * @功能：判断是否修改短信
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-08-08下午14:56
	 */
	private boolean updateMessage(String str){
		if(str.isEmpty()||"0".equals(str)){
			return false;
		}
		return true;
	}
	
	/**
	 * @项目：新客户体验项目
	 * @bug号：CEM-30
	 * @功能：修改前部分短信
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-08-08下午14:55
	 */
	private String updatebefore(String str){
		int i=str.indexOf("！您发往");
		if(i<0){
			throw new SMSTempleteException("数据库短信模板格式有问题！");
		}
		return "尊敬的客户"+str.substring(i,str.length());
	}
	
}
