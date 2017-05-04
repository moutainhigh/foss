package com.deppon.foss.module.pickup.sign.server.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.common.api.server.service.ISMSTempleteService;
import com.deppon.foss.module.base.common.api.shared.dto.SmsParamDto;
import com.deppon.foss.module.base.common.api.shared.exception.SMSTempleteException;
import com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.NotifyCustomerException;
import com.deppon.foss.module.pickup.sign.api.server.service.IBatchSendSMSJobService;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.pickup.waybill.shared.dto.BatchSendSMSDto;

/***
 * <3>
 * @author:yuting@163.com
 * @description: 批量发送短信相关的job
 * @date:2014年7月11日 下午7:08:41
 * @clasaName:<p>com.deppon.foss.module.pickup.sign.server.service.impl</p>
 */
public class BatchSendSMSWeekJobService implements IBatchSendSMSJobService {
	
	private static final Logger logger = LoggerFactory.getLogger(BatchSendSMSWeekJobService.class);
	
	// 客户相关
	private ICustomerDao customerDao;
	
	// 运单相关
	private IWaybillDao waybillDao;
	
	// 短信模板service接口 
	 
	private ISMSTempleteService sMSTempleteService;
	
	// 发送短信相关接口
	private INotifyCustomerService notifyCustomerService;
	
	/***
	 * <3>
	 * @author: yuting@163.com
	 * @description: 批量发送短信    次日相关  分成两种情况
	 * 1.发货#票和签单需返回的有#票  2.货物被成功签收#票和成功返回的签单共有#票
	 * If 后期的逻辑判断  针对这两种情况 通过定义标记的方式进行判断   第一种情况标记定义为1   第二种情况标记定义为0  <br/>
	 * @date:2014年7月12日 上午8:55:37
	 */
	@Override
	public void processBatchSendExpress() {
		List<CustomerEntity> resultList=customerDao.queryCustomersByCollection();
		if(CollectionUtils.isNotEmpty(resultList)){
			for (CustomerEntity customerEntity : resultList) {
				String cusCode = customerEntity.getCusCode();
				String unifiedCode=customerEntity.getUnifiedCode();
				if(StringUtils.isNotEmpty(cusCode)){
					//>>1
					List<BatchSendSMSDto> sendAndReceiveGoodsList=waybillDao.querySendAndReceiveGoodsWeekCount(cusCode);
					//>>2
					//List<BatchSendSMSDto> receiveAndSignBillGoods=waybillDao.queryReceiveAndSignBillGoodsWeekCount(cusCode);
					List<BatchSendSMSDto> signBillGoodsDtoList=waybillDao.querySignBillGoodsCount(cusCode,SettlementReportNumber.SEVEN);
					List<BatchSendSMSDto> receiveGoodsDtoList=waybillDao.queryReceiveGoodsCount(cusCode,SettlementReportNumber.SEVEN);
					List<BatchSendSMSDto> receiveAndSignBillGoodsDtoList=getReceiveAndSignBillGoodsDtoList(signBillGoodsDtoList,receiveGoodsDtoList);
					List<BatchSendSMSDto> resultGoodsList=getSendAndReceiveGoodsAndSignBillGoodsList(sendAndReceiveGoodsList,receiveAndSignBillGoodsDtoList);
					if(CollectionUtils.isNotEmpty(resultGoodsList)&&StringUtils.isNotEmpty(unifiedCode)){
						combineSMSData(resultGoodsList,cusCode,unifiedCode);
					}
				}
			}
		}
	}
	
	
	/****
	 * 拼装发送签单和返单短信需要的数据
	 * @param signBillGoodsDtoList 货物被成功签收#票
	 * @param receiveGoodsDtoList 成功返回的签单共有#票
	 * @return 数据拼装后的值
	 */
	private List<BatchSendSMSDto> getReceiveAndSignBillGoodsDtoList(
			List<BatchSendSMSDto> signBillGoodsDtoList,
			List<BatchSendSMSDto> receiveGoodsDtoList) {
		List<BatchSendSMSDto> rstList=new ArrayList<BatchSendSMSDto>();
		if(CollectionUtils.isNotEmpty(signBillGoodsDtoList)&&CollectionUtils.isEmpty(receiveGoodsDtoList)){
			return new ArrayList<BatchSendSMSDto>();
		}
		if(CollectionUtils.isNotEmpty(signBillGoodsDtoList)&&CollectionUtils.isEmpty(receiveGoodsDtoList)){
			return signBillGoodsDtoList;
		}
		if(CollectionUtils.isEmpty(signBillGoodsDtoList)&&CollectionUtils.isNotEmpty(receiveGoodsDtoList)){
			return receiveGoodsDtoList;
		}
	    Map<String,BatchSendSMSDto> map=new HashMap<String,BatchSendSMSDto>();
		if(CollectionUtils.isNotEmpty(signBillGoodsDtoList)&&CollectionUtils.isNotEmpty(receiveGoodsDtoList)){
			for (BatchSendSMSDto receiveAndSignBill: receiveGoodsDtoList) {
				for (BatchSendSMSDto sendAndReceive : signBillGoodsDtoList) {
					if(StringUtils.equals(sendAndReceive.getCustomerMobile(),receiveAndSignBill.getCustomerMobile())){
						//电话号码相同  赋值
						sendAndReceive.setSignBillSuccessBackCount(receiveAndSignBill.getSignBillSuccessBackCount());
						sendAndReceive.setFlag(SignConstants.EXIST);
						map.put(sendAndReceive.getCustomerMobile(), sendAndReceive);
					}else{
						validMapExtracted(map, sendAndReceive);
						validMapExtracted(map, receiveAndSignBill);
					}
				}
			}
		}
		if(MapUtils.isNotEmpty(map)){
			for(Map.Entry<String, BatchSendSMSDto> m:map.entrySet()){
				rstList.add(m.getValue());
			}
		}
		return rstList;
	}



	/***
	 * 发送短信需要的数据   主要是针对 两种情况  <br/>
	 * 为了保证代码的通用性  针对两种情况 我使用一个标记进行区分  第一种情况我设置flag为1  第二种情况  我设置标记为2
	 * 2.短信发送模板：以9日为例：
		1）尊敬的客户您好，8日全天，您共发货#票。8日全天，您共有签单需返回的有#票。所发货物已在运输中，详情请至德邦官网或致电4008305555查询，德邦快递祝您生活愉快！
		（发送规则：当8日当天没有签单需要返回时，则在短信模板中隐去“8日全天，您共有签单需返回的有#票”该句话，短信内容变为：尊敬的客户您好，8日全天，您共发货#票。所发货物已在运输中，详情请至德邦官网或致电4008305555查询，德邦快递祝您生活愉快！发货票数不统计中止，作废的运单）
		2）尊敬的客户您好，8日当天您的货物被成功签收#票。8日当天您成功返回的签单共有#票。详情请至德邦官网或致电4008305555查询，德邦快递祝您生活愉快！
		（发送规则：当8日当天没有签单返回时，则在短信模板中隐去“8日当天您成功返回的签单共有#票”该句话。短信内容变为：尊敬的客户您好，8日当天您的货物被成功签收#票。详情请至德邦官网或致电4008305555查询，德邦快递祝您生活愉快！）
	 * @param flag 区分标记
	 * @param custCode 客户编码
	 * @param unifiedCode 标杆编码
	 * @param sendAndReceiveGoodsDtoList
	 * @date:2014年7月12日 上午8:55:37
	 */
	private  void combineSMSData(List<BatchSendSMSDto> list,String custCode,String unifiedCode) {
		String code=StringUtils.EMPTY;
		synchronized (BatchSendSMSWeekJobService.class) {
			logger.info("拼装短信发送需要的数据......");
			SmsParamDto smsParamDto =null;
			//模块名称也需要定义一个 默认的常量
			String moduleCode=NotifyCustomerConstants.BATCH_SMS_WEEK_EXP;
			if(CollectionUtils.isNotEmpty(list)){
				for (BatchSendSMSDto batchSendSMSDto : list) {
				    smsParamDto = getTemplateInfo(batchSendSMSDto,unifiedCode);
				    if(smsParamDto!=null){
				    	String content = getSMSContent(smsParamDto);
						if(StringUtils.isNotEmpty(content)){
							//发送短信
							logger.info("短信内容不为空,执行批量发送短信");
							ResultDto result = sendMsg(batchSendSMSDto , moduleCode, content,custCode,unifiedCode);
							code = result.getCode();
							if(StringUtils.isNotEmpty(code)&&code.equals(ResultDto.FAIL)){
								break;
							}
						}
				    }
				}
			}
		}
	}

	/***
	 * 获取符合条件的模板数据
	 * @param list 所要发送短信的数据
	 * @param unifiedCode 客户标杆编码
	 * @param flag 标记 主要是用来判断 是哪一种情况   
	 * 次日发送短信相关：从大的方向 主要有两种情况  1.发货#票和签单需返回的有#票 次日相关  2.货物被成功签收#票和成功返回的签单共有#票 3.发货#票和签单需返回的有#票 周日相关
	 * 细分的话1和2又可以分成两种情况 
	 * 1：签单返回的有0票  这个时候短信模板内容需去除签单需返回的有#票的字样   次日相关 <br/>
	 * 2：成功返回的签单共有0票   这个时候短信模板内容需去除成功返回的签单共有#票的字样 <br/> 
	 * 3：签单返回的有0票  这个时候短信模板内容需去除签单需返回的有#票的字样  周次相关
	 * 所以综合起来的话  有六种情况   但是最终发送的短信数量次日只会有两条  周次的有一条
	 * @return 获取完整短信内容需要的查询参数
	 * @date:2014年7月12日 上午8:55:37
	 */
	private SmsParamDto getTemplateInfo(BatchSendSMSDto batchSendSMSDto,String unifiedCode){
		logger.info("获取符合条件的短信模板");
		if(batchSendSMSDto!=null){
			if((batchSendSMSDto.getSignBillSuccessBackCount()==null||batchSendSMSDto.getSignBillSuccessBackCount()<=0)&&(batchSendSMSDto.getSignBillSuccessCount()==null||batchSendSMSDto.getSignBillSuccessCount()<=0)
					&&(batchSendSMSDto.getDeliveryCount()==null||batchSendSMSDto.getDeliveryCount()<=0)&&(batchSendSMSDto.getSignBillBackCount()==null&&batchSendSMSDto.getSignBillBackCount()<=0)){
				return null;
			}
		}
		SmsParamDto dto=new SmsParamDto();
		Map<String,String> map=new HashMap<String,String>();
		//<3
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH,cal.get(Calendar.MONTH));
		cal.set(Calendar.DAY_OF_MONTH,1);
		cal.set(Calendar.DATE,cal.get(Calendar.DATE)-1);
		int maxDay = cal.get(Calendar.DAY_OF_MONTH);
        int firstDay=Calendar.getInstance().get(Calendar.DAY_OF_MONTH)-SettlementReportNumber.SEVEN;
		if(firstDay<=0){
			//获取上月是多少天
			firstDay=maxDay+firstDay;
		}
		int lastDay=Calendar.getInstance().get(Calendar.DAY_OF_MONTH)-1;
		if(lastDay<=0){
			lastDay=maxDay;
		}
		if(batchSendSMSDto.getSignBillBackCount()!=null&&batchSendSMSDto.getSignBillBackCount().intValue()>0){
			dto.setOrgCode(getOrgCode(unifiedCode)!=null?getOrgCode(unifiedCode):"");
			dto.setSmsCode(SignConstants.BATCH_SEND_LW_BILL_SIGN_PROCESS_BILL_RETURN_QTY);
			map.put("deliveryCount", batchSendSMSDto.getDeliveryCount()!=null&&batchSendSMSDto.getDeliveryCount()>0?batchSendSMSDto.getDeliveryCount().toString():"0");
			map.put("signBillBackCount", batchSendSMSDto.getSignBillBackCount()!=null&&batchSendSMSDto.getSignBillBackCount()>0?batchSendSMSDto.getSignBillBackCount().toString():"0");
			map.put("signBillSuccessCount", batchSendSMSDto.getSignBillSuccessCount()!=null&&batchSendSMSDto.getSignBillSuccessCount()>0?batchSendSMSDto.getSignBillSuccessCount().toString():"0");
			map.put("signBillSuccessBackCount", batchSendSMSDto.getSignBillSuccessBackCount()!=null&&batchSendSMSDto.getSignBillSuccessBackCount()>0?batchSendSMSDto.getSignBillSuccessBackCount().toString():"0");
			map.put("firstDay", String.valueOf(firstDay));
			map.put("lastDay", String.valueOf(lastDay));
		}else{
			dto.setOrgCode(getOrgCode(unifiedCode)!=null?getOrgCode(unifiedCode):"");
			dto.setSmsCode(SignConstants.BATCH_SEND_LW_BILL_SIGN_QTY);
			map.put("deliveryCount", batchSendSMSDto.getDeliveryCount()!=null&&batchSendSMSDto.getDeliveryCount()>0?batchSendSMSDto.getDeliveryCount().toString():"0");
			map.put("signBillSuccessCount", batchSendSMSDto.getSignBillSuccessCount()!=null&&batchSendSMSDto.getSignBillSuccessCount()>0?batchSendSMSDto.getSignBillSuccessCount().toString():"0");
			map.put("firstDay", String.valueOf(firstDay));
			map.put("lastDay", String.valueOf(lastDay));
		}
		dto.setMap(map);
		return dto;
	}
	
	/***
	 * 通过客户的标杆编码 得到 部门编码
	 * @param unifiedCode 标杆编码
	 * @return 部门编码
	 * @date:2014年7月12日 上午8:55:37
	 */
	private String getOrgCode(String unifiedCode){
		String orgCode = customerDao.queryOrgCodeByUnifiedCode(unifiedCode);
		return orgCode;
	}
	/***
	 * 发送批量短信
	 * @param moduleCode  所属模块
	 * @param content  短信通知内容
	 * @param custCode  客户编码
	 * @return 返回短信相关的结果集
	 * @date:2014年7月12日 上午8:55:37
	 */
	private ResultDto sendMsg(BatchSendSMSDto batchSendSMSDto,String moduleCode, String customerSms,String custCode,String unifiedCode) {
		ResultDto dto = new ResultDto();
		//结果返回对象
		try {
			//短信接口NotificationEntity对象
			NotificationEntity notificationEntity = new NotificationEntity();
			//这个参数是必须的 
			notificationEntity.setWaybillNo(custCode);
			// 通知类型为短信通知   这个要改一下 和 需求那边说一下
			notificationEntity.setNoticeType(NotifyCustomerConstants.NOTIFY_TYPE_SMS_NOTICE);	
			// 通知内容  
			notificationEntity.setNoticeContent(customerSms);
			// 操作人--
			notificationEntity.setOperator("--");
			// 操作人编码000000
			notificationEntity.setOperatorNo("000000");
			// 操作部门
			notificationEntity.setOperateOrgName(batchSendSMSDto.getCurrentDeptName());
			// 操作部门编码
			String orgCode = getOrgCode(unifiedCode);
			// 手机号
			notificationEntity.setMobile(batchSendSMSDto.getCustomerMobile());
			// 操作时间
			notificationEntity.setOperateTime(new Date());
			// 模块名称
			notificationEntity.setModuleName(moduleCode);
			// 收货人  这个参数也不能为空  
			String contactName=StringUtils.EMPTY;
			String customerMobile = batchSendSMSDto.getCustomerMobile();
			if(StringUtils.isNotEmpty(customerMobile)){
				contactName=getContactName(custCode,customerMobile);
				 if(StringUtils.isEmpty(contactName)){
					 contactName="客户";
				 }
			}
			if(StringUtils.isNotEmpty(contactName)&&StringUtils.isNotEmpty(orgCode)){
				//根据手机号  获取联系人的姓名
				notificationEntity.setConsignee(contactName);
				// 操作部门编码
				notificationEntity.setOperateOrgCode(orgCode);
				//发送短信给发货人
				notifyCustomerService.sendMessage(notificationEntity);
				//成功标志
				dto.setCode(ResultDto.SUCCESS);
				dto.setMsg("短信发送成功");
				return dto;
			}else{
				logger.error("联系人姓名或者部门编码为空,不能发送短信......");
				//短信发送失败
				dto.setCode(ResultDto.FAIL);
				dto.setMsg("短信发送失败");
				return dto;
			}
		} catch (NotifyCustomerException e) {
			//出现异常
			logger.error("短信发送出现异常", e);
			dto.setCode(ResultDto.FAIL);
			dto.setMsg("短信发送出现异常");
			//短信发送失败
			return dto;
		}	
	}

	/***
	 * 获取联系人的名字
	 * @param custCode 客户编码
	 * @param customerMobile 客户的手机号
	 * @return  联系人的名称
	 * @date:2014年7月12日 上午8:55:37
	 */
	private String getContactName(String custCode, String customerMobile) {
		String contactName=customerDao.queryContactNameByCustCodeAndcustMobile(custCode,customerMobile);
		return contactName;
	}

	/***
	 * 根据参数获取短信内容
	 * @return 短信完整信息
	 * @date:2014年7月12日 上午8:55:37
	 */
	private String getSMSContent(SmsParamDto params) {
		String sms=StringUtils.EMPTY;
		if(params!=null){
			try {
				sms = sMSTempleteService.querySmsByParam(params);
			} catch (SMSTempleteException e) {//捕获异常
				logger.error("短信内容为空", e);//记录日志
				throw new SignException(SignException.MESS_CONTENT_ISNULL, e);//短信内容为空
			}
			if (StringUtil.isEmpty(sms)) {
				logger.error("没有对应的短信模版");//记录日志
				throw new SignException(SignException.NO_SMS_TEMPLATES);//没有对应的短信模版
			}
		}
		return sms;
	}
	
	/***
	 * 拼装发送需要的数据
	 * @param sendAndReceiveGoodsDtoList
	 * @param receiveAndSignBillGoodsList 
	 * @return List
	 */
	private  List<BatchSendSMSDto> getSendAndReceiveGoodsAndSignBillGoodsList(List<BatchSendSMSDto> sendAndReceiveGoodsDtoList,List<BatchSendSMSDto> receiveAndSignBillGoodsList) {
		List<BatchSendSMSDto> rstList=new ArrayList<BatchSendSMSDto>();
		if(CollectionUtils.isEmpty(sendAndReceiveGoodsDtoList)&&CollectionUtils.isEmpty(receiveAndSignBillGoodsList)){
			return new ArrayList<BatchSendSMSDto>();
		}
		if(CollectionUtils.isEmpty(receiveAndSignBillGoodsList)&&CollectionUtils.isNotEmpty(sendAndReceiveGoodsDtoList)){
			return sendAndReceiveGoodsDtoList;
		}
		if(CollectionUtils.isEmpty(sendAndReceiveGoodsDtoList)&&CollectionUtils.isNotEmpty(receiveAndSignBillGoodsList)){
			return receiveAndSignBillGoodsList;
		}
		Map<String,BatchSendSMSDto> map=new HashMap<String,BatchSendSMSDto>();
		if(CollectionUtils.isNotEmpty(sendAndReceiveGoodsDtoList)&&CollectionUtils.isNotEmpty(receiveAndSignBillGoodsList)){
				for (BatchSendSMSDto receiveAndSignBill: receiveAndSignBillGoodsList) {
					for (BatchSendSMSDto sendAndReceive : sendAndReceiveGoodsDtoList) {
						if(StringUtils.equals(sendAndReceive.getCustomerMobile(),receiveAndSignBill.getCustomerMobile())){
							//电话号码相同  赋值
							receiveAndSignBill.setDeliveryCount(sendAndReceive.getDeliveryCount());
							receiveAndSignBill.setSignBillBackCount(sendAndReceive.getSignBillBackCount());
							receiveAndSignBill.setFlag(SignConstants.EXIST);
							map.put(receiveAndSignBill.getCustomerMobile(), receiveAndSignBill);
						}else{
							validMapExtracted(map, sendAndReceive);
							validMapExtracted(map, receiveAndSignBill);
						}
					}
				}
			}
		if(MapUtils.isNotEmpty(map)){
			for(Map.Entry<String, BatchSendSMSDto> m:map.entrySet()){
				rstList.add(m.getValue());
			}
		}
		return rstList;
	}


	private void validMapExtracted(Map<String, BatchSendSMSDto> map,
			BatchSendSMSDto receiveAndSignBill) {
		if(map.containsKey(receiveAndSignBill.getCustomerMobile())){
			validBatchExtracted(map, receiveAndSignBill);
		}else{
			map.put(receiveAndSignBill.getCustomerMobile(), receiveAndSignBill);
			receiveAndSignBill.setFlag(SignConstants.NOTEXIST);
		}
	}


	private void validBatchExtracted(Map<String, BatchSendSMSDto> map,
			BatchSendSMSDto receiveAndSignBill) {
		if(map.get(receiveAndSignBill.getCustomerMobile()).getFlag().equals(SignConstants.EXIST)){
			
		}else{
			map.put(receiveAndSignBill.getCustomerMobile(), receiveAndSignBill);
			receiveAndSignBill.setFlag(SignConstants.NOTEXIST);
		}
	}
	
	




	public ICustomerDao getCustomerDao() {
		return customerDao;
	}


	public void setCustomerDao(ICustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public IWaybillDao getWaybillDao() {
		return waybillDao;
	}

	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}

	public ISMSTempleteService getsMSTempleteService() {
		return sMSTempleteService;
	}

	public void setsMSTempleteService(ISMSTempleteService sMSTempleteService) {
		this.sMSTempleteService = sMSTempleteService;
	}

	public INotifyCustomerService getNotifyCustomerService() {
		return notifyCustomerService;
	}

	public void setNotifyCustomerService(
			INotifyCustomerService notifyCustomerService) {
		this.notifyCustomerService = notifyCustomerService;
	}

}
