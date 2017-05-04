package com.deppon.foss.module.transfer.unload.server.service.impl;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.common.api.server.service.ISMSSendLogService;
import com.deppon.foss.module.base.common.api.server.service.ISMSTempleteService;
import com.deppon.foss.module.base.common.api.shared.domain.SMSSendLogEntity;
import com.deppon.foss.module.base.common.api.shared.dto.SmsParamDto;
import com.deppon.foss.module.base.common.api.shared.exception.SMSTempleteException;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrJobTodoService;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessGoalContants;
import com.deppon.foss.module.transfer.common.api.shared.define.BusinessSceneConstants;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobTodoEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.TfrJobTodoQueryDto;
import com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillDto;
import com.deppon.foss.module.transfer.scheduling.api.define.TransportPathConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TransportPathEntity;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskService;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskSmsService;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadBillDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadTaskEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.eos.foundation.common.utils.StringUtil;

public class UnloadTaskSmsService implements IUnloadTaskSmsService{
//	private static final Logger LOGGER = LoggerFactory
//			.getLogger(ArrivalService.class);
	private  static final Logger LOGGER=LoggerFactory.getLogger(UnloadTaskSmsService.class);
	/**
	 * ************************ 走货路径服务
	 */
	private ICalculateTransportPathService calculateTransportPathService;
	/**
	 * 营业部
	 */
	private ISaleDepartmentService saleDepartmentService;
	/**
	 * 结算 通知客户Service
	 */
	private INotifyCustomerService notifyCustomerService;
	/**
	 * 单次处理100条
	 */
	private static final int count = 100;
	/**
	 *查询代办job
	 */
	private ITfrJobTodoService tfrJobTodoService;
	/**
	 * 记日志
	 */
	private ITfrCommonService tfrCommonService;

	/**
	 * 查询卸车任务
	 */
	private IUnloadTaskService unloadTaskService;
	/**
	 *  综合管理 组织信息 Service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 短信模板service接口
	 */
	private ISMSTempleteService sMSTempleteService;
	/**
	 * 运单service，根据运单号获取运单货物品名，包装，运输性质等
	 */
	private IWaybillManagerService waybillManagerService;
	/**
	 * 发送短信接口
	 */
	private ISMSSendLogService smsSendLogService;	
	/**
	 *设置 结算 通知客户Service
	 *setNotifyCustomerService
	 * @param notifyCustomerService the notifyCustomerService to set
	 * @return the notifyCustomerService
	 */
	public void setNotifyCustomerService(
			INotifyCustomerService notifyCustomerService) {
		this.notifyCustomerService = notifyCustomerService;
	}
	/**
	 *设置走货路徑
	 *setCalculateTransportPathService
	 * @param calculateTransportPathService the calculateTransportPathService to set
	 * @return the calculateTransportPathService
	 */
	public void setCalculateTransportPathService(
			ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}
	/**
	 *设置营业部
	 * @param saleDepartmentService the saleDepartmentService to set
	 * @return the saleDepartmentService
	 */
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
	/**
	 * PDA中转装车dao接口
	 */
	private IPDALoadDao pdaLoadDao;
	
	/**
	 * 
	* @Title: setPdaLoadDao 
	* @Description: PDA中转装车dao接口
	* @param @param pdaLoadDao    设定文件 
	* @return void    返回类型 
	* @author 189284 ZhangXu
	* @date 2015-9-8 下午7:35:57 
	* @throws
	 */
	public void setPdaLoadDao(IPDALoadDao pdaLoadDao) {
		this.pdaLoadDao = pdaLoadDao;
	}
	public void setSmsSendLogService(ISMSSendLogService smsSendLogService) {
		this.smsSendLogService = smsSendLogService;
	}
	public void setTfrJobTodoService(ITfrJobTodoService tfrJobTodoService) {
		this.tfrJobTodoService = tfrJobTodoService;
	}
	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}	
	public void setUnloadTaskService(IUnloadTaskService unloadTaskService) {
		this.unloadTaskService = unloadTaskService;
	}
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	public void setsMSTempleteService(ISMSTempleteService sMSTempleteService) {
		this.sMSTempleteService = sMSTempleteService;
	}
	
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	/**
	 * 
	* @Title: sendUnloadTaskSms 
	* @Description: 卸车任务创建的时候 发送短信
	* @param    job入口方法.
	* @return void    返回类型 
	* @author 189284-ZhangXu
	* @Date 2015-6-24  上午9:59:36
	* @throws
	 */
    @Override
	public void sendUnloadTaskSms() {
    	LOGGER.info("卸车发短信开始");
		TfrJobTodoQueryDto qDto = new TfrJobTodoQueryDto();
		//卸车任务，发短信
		qDto.setBusinessSceneList(new String[]{BusinessSceneConstants.BUSINESS_SCENE_UNLOADTASK_SMS});
		
		qDto.setBusinessGoalList(new String[]{BusinessGoalContants.BUSINESS_GOAL_UNLOADTASK_SMS});
//		Calendar cal= Calendar.getInstance();
//		//每日早上八点
//	    cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 8, 0, 0);
//		qDto.setBizStartTime(cal.getTime());
//		//每日下午17:30点
//	    cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 17, 30, 0);
//		qDto.setBizEndTime(cal.getTime());
		qDto.setCount(count);
		List<TfrJobTodoEntity> list = tfrJobTodoService.queryJobTodo(qDto);
		if(list!=null&& list.size()>0){
			for(TfrJobTodoEntity entity : list){
				try{
					if(StringUtils.equals(entity.getBusinessGoal(), BusinessGoalContants.BUSINESS_GOAL_UNLOADTASK_SMS)){
						LOGGER.info("BusinessID="+entity.getBusinessID()+",,,id="+entity.getId());
						sendUnloadTaskSmsBegin(entity.getBusinessID(),entity.getId());
					}
					/**
					 * step 3：更新待处理信息
					 */
					tfrJobTodoService.updateJobTodoByID(entity.getId());
					LOGGER.info("卸车发短信结束");
				}catch(BusinessException e){
					//记录出错日志
					TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
					jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.SEND_VEHICLESTATUS_2TPS_JOB.getBizName());
					jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.SEND_VEHICLESTATUS_2TPS_JOB.getBizCode());
					jobProcessLogEntity.setRemark("任务执行失败！");
					jobProcessLogEntity.setExceptionInfo(e.getMessage());
					jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
					tfrCommonService.addJobProcessLog(jobProcessLogEntity);
					
					continue;
				}
				
			}
		}else{
			LOGGER.error("没有卸车任务需要发短信");
		}
		
	}
    /**
     * 
    * @Title: sendUnloadTaskSmsBegin 
    * @Description: 根据 任务编号 发送短信
    * @param @param unloadTaskId   卸车任务Id
    * @return void    返回类型 
    * @author 189284-ZhangXu
    * @Date 2015-8-10  下午6:43:23
    * @throws
     */
	private void sendUnloadTaskSmsBegin(String unloadTaskId, String jobTodoID) {
		if (StringUtils.isNotBlank(unloadTaskId)) {
			UnloadTaskEntity baseEntity = unloadTaskService.queryUnloadTaskBaseInfoById(unloadTaskId);
			if (baseEntity != null) {
				// 根据卸车任务ID获取其下单据列表
				List<UnloadBillDetailEntity> unloadBillDetailEntitys = unloadTaskService.queryUnloadTaskBillDetailListById(unloadTaskId);
				LOGGER.info(unloadTaskId+ "的单据列表"+ JSONArray.fromObject(unloadBillDetailEntitys).toString());
				List<HandOverBillDetailEntity> handOverBillDetailEntitys = new ArrayList<HandOverBillDetailEntity>();
				if (CollectionUtils.isNotEmpty(unloadBillDetailEntitys)) {
					for (UnloadBillDetailEntity unloadBillDetailEntity : unloadBillDetailEntitys) {
					  if(StringUtils.isNotEmpty(unloadBillDetailEntity.getBillNo())){
							LOGGER.info("单据类型："+ unloadBillDetailEntity.getBillType());
							// 卸车任务类型为短途 为交接单 查询交接单
							/**
							 * 单据类型：HANDOVER-交接单，VEHICLEASSEMBLE-汽运配载单,接送货交接-PICKUP, 快递集中卸货-EWAYBILL
							 */
							if (StringUtils.equals(UnloadConstants.BILL_TYPE_HANDOVER,
									unloadBillDetailEntity.getBillType())) {
								handOverBillDetailEntitys = 
								unloadTaskService.queryWaybillListByHandOverBillNo(unloadBillDetailEntity.getBillNo());
							}
							// 卸车任务类型为长途 为配载单 查询配载单
							if (StringUtils.equals(UnloadConstants.BILL_TYPE_VEHICLEASSEMBLE,
									unloadBillDetailEntity.getBillType())) {
								List<QueryHandOverBillDto> queryHandOverBillDtos = 
								unloadTaskService.queryHandOverBillListByVehicleAssembleNo(unloadBillDetailEntity.getBillNo());
								if (CollectionUtils.isNotEmpty(queryHandOverBillDtos)) {
									for (QueryHandOverBillDto queryHandOverBillDto : queryHandOverBillDtos) {
										List<HandOverBillDetailEntity> handOverBillDetailEntity =
										 unloadTaskService.queryWaybillListByHandOverBillNo(queryHandOverBillDto.getHandOverBillNo());
										if(CollectionUtils.isNotEmpty(handOverBillDetailEntity)){
											handOverBillDetailEntitys.addAll(handOverBillDetailEntity);
										}else{
										 LOGGER.info(queryHandOverBillDto.getHandOverBillNo()+"查询出的的交接单为空");	
										}
										
									}
								} else {
									LOGGER.info(unloadBillDetailEntity.getBillNo()
											+ "的配载单为空！");
								}
							}
					  }else{
						  LOGGER.info(unloadBillDetailEntity+"的单据号为空");
					  }
					}
				} else {
					LOGGER.info(unloadTaskId + "根据卸车任务ID获取其下单据列表为空！");
				}
				// 根据卸车任务编号获取卸车明细
				// List<UnloadWaybillDetailDto> unloadWaybillDetailDtoList =
				// unloadTaskQueryService.queryUnloadTaskDetailByUnloadTaskNo(baseEntity.getUnloadTaskNo());
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByCode(baseEntity
								.getUnloadOrgCode());
				
				LoaderParticipationEntity loaderParticipationEntity = pdaLoadDao
						.queryLoaderByTaskID(unloadTaskId);
				if (CollectionUtils.isNotEmpty(handOverBillDetailEntitys)
						&& loaderParticipationEntity != null) {
					// PC卸车完成时，确定发短信通知
					for (HandOverBillDetailEntity handOverBillDetailEntity : handOverBillDetailEntitys) {
						// 默认发短信 返回false是未通知或者通知不成功的
						boolean falge = notifyCustomerService
								.isNotificationByWaybillNoForTfr(handOverBillDetailEntity
										.getWaybillNo());
						LOGGER.info("运单号："
								+ handOverBillDetailEntity.getWaybillNo()
								+ "调用结算结果" + falge);
						// 调用结算接口 判断是否发短信（代做）
						if (!falge) {
							/**
							 * step 2：逐个运单发送短信通知
							 */
							// 查询运单信息 根据运单号
							WaybillEntity waybillEntity = waybillManagerService
									.queryWaybillBasicByNo(handOverBillDetailEntity
											.getWaybillNo());
							//运单提货网点部门
							OrgAdministrativeInfoEntity receiveOrg = null;
							/**
							 * 1零担物流班车到达最终外场或者非驻地营业部时，
							 * 2提货方式为送货（送货上楼，送货不含上楼，送货进仓）的货物，
							 * 3运输性质为精准卡航/城运/汽运（长短途）、精准大票卡航/城运/汽运（长短途）
							 * 4联系方式为非手机号码系统不触发短信,分批配载货物系统不自动通知; 满足四种条件返回true
							 * 发短信
							 */
							boolean isSendMess = false;
							if (waybillEntity != null) {
								/**
								 * 提货网点信息
								 */
								receiveOrg = orgAdministrativeInfoService
										.queryOrgAdministrativeInfoByCode(waybillEntity.getCustomerPickupOrgCode());
								// 判断是否在8个大区之内
								isSendMess = getIsLimit(waybillEntity);
								if(isSendMess){
								 isSendMess = isSendMess(baseEntity,waybillEntity);
								}
								
							} else {
								LOGGER.info(handOverBillDetailEntity
										.getWaybillNo() + "的运单信息 没有查询到");
							}

							if (isSendMess) {
								String sms = getSmsContent(
										handOverBillDetailEntity,
										receiveOrg,
										waybillEntity, "TFR_UNTASK_SMSDEPTP");
								UserEntity user = new UserEntity();
								user.setUserName(loaderParticipationEntity
										.getLoaderName());
								EmployeeEntity employee = new EmployeeEntity();
								employee.setEmpCode(loaderParticipationEntity
										.getLoaderCode());
								employee.setEmpName(loaderParticipationEntity
										.getLoaderName());
								user.setEmployee(employee);
								CurrentInfo currentInfo = new CurrentInfo(user,
										orgAdministrativeInfoEntity);
								// waybillEntity.setReceiveCustomerMobilephone("13681805584");
								// waybillEntity.setDeliveryCustomerMobilephone("13681805584");
								// NotifyCustomerConstants.BS_TYPE_PKP_NOTIFY
								// 【客户通知】这种业务类型 结算发短信才会记录到表中（才知道发过短息）
								try {
									// waybillSignResultService.sendMess(currentInfo,
									// sms, true,
									// NotifyCustomerConstants.BS_TYPE_PKP_NOTIFY//"FOSS"
									// , waybillEntity);
									NotificationEntity notificationEntity = getNotificationEntity(
											waybillEntity,
											sms,
											currentInfo,
											NotifyCustomerConstants.BS_TYPE_PKP_NOTIFY,
											true);
									notifyCustomerService
											.sendMessage(notificationEntity);
								} catch (BusinessException e) {
									e.printStackTrace();
								}
							}
						}
					}
				} else {
					LOGGER.info(unloadTaskId + "交接单，或者 理货员，主卸车人为空");
				}
			} else {
				LOGGER.info(unloadTaskId + "根据卸车任务Id 查询结果为空");
			}

		}

	}
	/**
	 * 
	* @Title: getNotificationEntity 
	* @Description: 获取发短信 实体
	* @param @param waybillEntity 运单
	* @param @param sms 短信内容
	* @param @param currentInfo 操作人和 部门
	* @param @param moduleCode 模板
	* @param @param isToReceiveCustomer 发给收货人 还是发货人 true 表示发给 收货人
	* @return NotificationEntity    返回类型 
	* @author 189284 ZhangXu
	* @date 2015-9-9 下午5:53:51 
	* @throws
	 */
	private NotificationEntity  getNotificationEntity(WaybillEntity waybillEntity,
			String sms,CurrentInfo currentInfo ,
			String moduleCode,boolean isToReceiveCustomer ){
		NotificationEntity notificationEntity = new NotificationEntity();
		// 运单号
		notificationEntity.setWaybillNo(waybillEntity.getWaybillNo());
		// 通知类型为短信通知
		notificationEntity.setNoticeType(NotifyCustomerConstants.NOTIFY_TYPE_SMS_NOTICE);	
		// 通知内容  
		notificationEntity.setNoticeContent(sms);
		// 操作人
		notificationEntity.setOperator(currentInfo.getEmpName());
		// 操作人编码
		notificationEntity.setOperatorNo(currentInfo.getEmpCode());
		// 操作部门
		notificationEntity.setOperateOrgName(currentInfo.getCurrentDeptName());
		// 操作部门编码
		notificationEntity.setOperateOrgCode(currentInfo.getCurrentDeptCode());
		if(isToReceiveCustomer){
			// 收货人
			notificationEntity.setConsignee(waybillEntity.getReceiveCustomerContact());
			// 手机号
			notificationEntity.setMobile(waybillEntity.getReceiveCustomerMobilephone());
		}else {
			// 发货人
			notificationEntity.setConsignee(waybillEntity.getDeliveryCustomerContact());
			// 手机号
			notificationEntity.setMobile(waybillEntity.getDeliveryCustomerMobilephone());
		}
		// 操作时间
		notificationEntity.setOperateTime(new Date());
		// 模块名称
		notificationEntity.setModuleName(moduleCode);
		return notificationEntity;
	}

	/**
	 * 
	 * @Title: isSendMess
	 * @Description: 判断是否 满足 发短信条件
	 * 
	 *  1零担物流班车到达最终外场或者非驻地营业部时
	 *   2提货方式为送货（送货上楼，送货不含上楼，送货进仓）的货物，
	 *  3运输性质为精准卡航/城运/汽运（长短途）、精准大票卡航/城运/汽运（长短途）
	 * @param @param baseEntity
	 * @param @param waybillEntity
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @author 189284 ZhangXu
	 * @date 2015-9-9 上午11:40:28
	 * @throws
	 */
	private boolean isSendMess(UnloadTaskEntity baseEntity,WaybillEntity waybillEntity){
		//查询营运部信息
		SaleDepartmentEntity saleDetp = saleDepartmentService.querySaleDepartmentByCode(waybillEntity.getCustomerPickupOrgCode());
		boolean isSendMess=false;
	    /**
         * 1零担物流班车到达最终外场或者非驻地营业部时，
         * 2提货方式为送货（送货上楼，送货不含上楼，送货进仓）的货物，
         * 3运输性质为精准卡航/城运/汽运（长短途）、精准大票卡航/城运/汽运（长短途）
         * 联系方式为非手机号码系统不触发短信,分批配载货物系统不自动通知;
         */
		// 判断是否分批配载，如果分批配载，取最慢的一条，如果不是，取随机的一条
		TransportPathEntity transportPathEntity = calculateTransportPathService.queryTransportPath(waybillEntity.getWaybillNo());
		// 判断是否存在条目
		if (null != transportPathEntity) {
		// 如果不是分批 分批配载货物系统不自动通知;
		  if (StringUtils.equals(transportPathEntity.getIfPartialStowage(),
								TransportPathConstants.NOTPARTIALSTOWAGE)) {
				isSendMess=true;
			}
		}
//		//判断收货联系方式 是否手机号
		if(StringUtils.isEmpty(waybillEntity.getReceiveCustomerMobilephone())){
			isSendMess=false;
		}
		if(StringUtils.isNotEmpty(waybillEntity.getReceiveCustomerMobilephone())){
			String regExp = "^[1][3-9][0-9]{9}$";  

			Pattern p = Pattern.compile(regExp);  

			Matcher m = p.matcher(waybillEntity.getReceiveCustomerMobilephone());  

			isSendMess= m.find();//boolean
		}
		//1零担物流班车到达最终外场或者非驻地营业部时，
		if(saleDetp!=null){
			if(StringUtils.equals(saleDetp.getStation(), FossConstants.YES)
					&&StringUtils.equals(saleDetp.getTransferCenter(),baseEntity.getUnloadOrgCode())){
				isSendMess=true;
			}
			if(StringUtils.equals(saleDetp.getStation(), FossConstants.NO)
					&&StringUtils.equals(saleDetp.getCode(), baseEntity.getUnloadOrgCode())){
				isSendMess=true;
			}
			LOGGER.info("最终外场::"+saleDetp.getStation()+
					",所属"+saleDetp.getTransferCenter()+
					",到达部门"+saleDetp.getCode()+
					",卸车任务创建部门"+baseEntity.getUnloadOrgCode());
		}else{
			if(StringUtils.equals(waybillEntity.getCustomerPickupOrgCode(), baseEntity.getUnloadOrgCode())){
				isSendMess=true;
			}
			LOGGER.info("最终外场::"+"到达部门"+waybillEntity.getCustomerPickupOrgCode()+
					",卸车任务创建部门"+baseEntity.getUnloadOrgCode());
		}
		//所有的送货方式
		List<String> deliverWayList=new ArrayList<String>();
		/*初始化送货方式*/
		//汽运送货(不含上楼)
		deliverWayList.add(WaybillConstants.DELIVER_NOUP);
		//汽运免费派送
		deliverWayList.add(WaybillConstants.DELIVER_FREE);
		//汽运送货进仓
		deliverWayList.add(WaybillConstants.DELIVER_STORAGE);
		//汽运送货（上楼）
		deliverWayList.add(WaybillConstants.DELIVER_UP);
		//2判断提货方式为送货
		if(deliverWayList.contains(waybillEntity.getReceiveMethod()))
		{
			isSendMess=true;
		}
		List<String> productCodeList=new ArrayList<String>();
		productCodeList.add(WaybillConstants.TRUCK_FLIGHT);//精准卡航"FLF"
		productCodeList.add(WaybillConstants.FSF_FLIGHT);//精准城运"FSF"
		productCodeList.add(WaybillConstants.LRF_FLIGHT);//精准汽运（长）"LRF"
		productCodeList.add(WaybillConstants.SRF_FLIGHT);//精准汽运（短）"SRF"
		productCodeList.add("BGFLF");//精准大票卡航"BGFLF"
		productCodeList.add("BGLRF");//精准大票汽运(长)"BGLRF"
		productCodeList.add("BGFSF");//精准大票城运"BGFSF"
		productCodeList.add("BGSRF");//精准大票汽运(短)"BGSRF"
		//3判断运输性质为精准卡航/城运/汽运（长短途）、精准大票卡航/城运/汽运（长短途）
		if(productCodeList.contains(waybillEntity.getProductCode()))
		{
			isSendMess=true;
		}
		if(transportPathEntity == null){
			transportPathEntity = new TransportPathEntity();
		}
	
		LOGGER.info("是否分批配载"+transportPathEntity.getIfPartialStowage()+
				"手机号："+waybillEntity.getReceiveCustomerMobilephone()+
				"送货方式"+waybillEntity.getReceiveMethod()+
				"运输性质"+waybillEntity.getProductCode());
		LOGGER.info("是否满足条件："+isSendMess);
		return isSendMess;
	}
	/**
	 * 
	* @Title: sendMessge 
	* @Description:  
	* @param @param loaderParticipationEntity 理货员 实体
	* @param @param unloadWaybillDetailDto 卸车运单明细
	* @param @param orgAdministrativeInfoEntity 创建卸车任务部门
	* @param @param waybillEntity 运单实体
	* @param @param smsCode   短息编码
	* @return void    返回类型 
	* @author 189284-ZhangXu
	* @Date 2015-6-30  下午2:31:46
	* @throws
	 */
	@SuppressWarnings("unused")
	private void sendMessge(LoaderParticipationEntity loaderParticipationEntity,
			HandOverBillDetailEntity handOverBillDetailEntity,
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity,
			WaybillEntity waybillEntity,String smsCode){
			//短信内容
			String smscontent = "";			
			 smscontent=getSmsContent(handOverBillDetailEntity,orgAdministrativeInfoEntity,waybillEntity,smsCode);
			try {
				sendMess( loaderParticipationEntity,
						handOverBillDetailEntity,
						 orgAdministrativeInfoEntity,
						 waybillEntity, smscontent);
					
			} catch (BusinessException e) {
				LOGGER.error("--短信发送失败!" + e.getMessage(), e);//记录日志
			}
		
	} 
	/**
	 * 
	* @Title: sendMess 
	* @Description:  
	 @param @param loaderParticipationEntity 理货员 实体
	* @param @param unloadWaybillDetailDto 卸车运单明细
	* @param @param orgAdministrativeInfoEntity 创建卸车任务部门
	* @param @param waybillEntity 运单实体
	* @param @param smscontent 短信内容
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @author 189284-ZhangXu
	* @Date 2015-6-30  下午2:51:33
	* @throws
	 */
	public boolean sendMess(LoaderParticipationEntity loaderParticipationEntity,
			HandOverBillDetailEntity handOverBillDetailEntity,
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity,
			WaybillEntity waybillEntity,String smscontent) {
		boolean sendMessResult=false;
		LOGGER.info("短信发送开始!");//记录日志
		SMSSendLogEntity smsSendLog = new SMSSendLogEntity();
		try {
			//发送部门编码
			smsSendLog.setSenddeptCode(orgAdministrativeInfoEntity.getCode());
			//发送人员编码
			smsSendLog.setSenderCode(loaderParticipationEntity.getLoaderCode());
			// 电话
			smsSendLog.setMobile(waybillEntity.getDeliveryCustomerMobilephone());
			// 短信内容
			smsSendLog.setContent(smscontent);
			// 发送部门
			smsSendLog.setSenddept(orgAdministrativeInfoEntity.getName());
			// 发送人
			smsSendLog.setSender(loaderParticipationEntity.getLoaderName());
			// 业务类型
			smsSendLog.setMsgtype(UnloadConstants.SMS_UNLOAD_TASK);
			// 短信来源
			smsSendLog.setMsgsource(NotifyCustomerConstants.SYS_SOURCE);
			// 唯一标识
			if (StringUtil.isBlank(waybillEntity.getWaybillNo())) {
				smsSendLog.setUnionId(UUIDUtils.getUUID());
			} else {
				smsSendLog.setUnionId(waybillEntity.getWaybillNo());
			}
			// 运单号
			smsSendLog.setWaybillNo(waybillEntity.getWaybillNo());
			// 发送时间
			smsSendLog.setSendTime(new Date());
			// 服务类型 （1:短信、2:语音、3:短信语音）
				smsSendLog.setServiceType(NumberConstants.NUMERAL_S_ONE);
				LOGGER.info("短信内容：" + ReflectionToStringBuilder.toString(smsSendLog));
				// 发送短信内容
				smsSendLogService.sendSMS(smsSendLog);
			} catch (BusinessException e) {//捕获异常
			// 异常处理
			LOGGER.error("--短信发送失败!" + e.getErrorCode(), e);//记录日志
			sendMessResult =false;
		}
		return sendMessResult;

	}
	/**
	 * 
	* @Title: getSmsContent 
	* @Description: 调用综合接口  获取短信内容
	* @param @param unloadWaybillDetailDto 卸车明细
	* @param @param orgAdministrativeInfoEntity 卸车任务创建 部门信息
	* @param @param waybillEntity 运单信息
	* @param @return    设定文件 
	* @return String    返回类型 
	* @author 189284-ZhangXu
	* @Date 2015-6-25  上午10:43:48
	* @throws
	 */
	private String getSmsContent(HandOverBillDetailEntity handOverBillDetailEntity,
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity,
			WaybillEntity waybillEntity,String smsCode ){
		
		String sms = ""; // 返回短信
		// 模版参数
		SmsParamDto smsParamDto = new SmsParamDto();
		// 短信编码
		smsParamDto.setSmsCode(smsCode);
//		// 部门编码
//		smsParamDto.setOrgCode(orgCode);
		smsParamDto.setMap(getSmsParam( handOverBillDetailEntity, orgAdministrativeInfoEntity,waybillEntity));
		try {
			sms = sMSTempleteService.querySmsByParam(smsParamDto);
		} catch (SMSTempleteException e) {//捕获异常
			LOGGER.error("短信内容为空", e);//记录日志
			throw new SignException(SignException.MESS_CONTENT_ISNULL, e);//短信内容为空
		}
		if (StringUtils.isBlank(sms)) {
			LOGGER.error("没有对应的短信模版");//记录日志
			throw new SignException(SignException.NO_SMS_TEMPLATES);//没有对应的短信模版
		}
		LOGGER.error("短信模版"+sms);//记录日志
		return sms;
	}
	private Map<String, String> getSmsParam(HandOverBillDetailEntity handOverBillDetailEntity,
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity,
			WaybillEntity waybillEntity) {
		Map<String, String> map = new HashMap<String, String>();
		//发货人名称
		map.put("deliveryCustomerName", waybillEntity.getDeliveryCustomerName());
		// 运单号
		map.put("waybillNo", waybillEntity.getWaybillNo());
		//货物件数
	//	map.put("handOverPieces", unloadWaybillDetailDto.getHandOverPieces()+"");
		if(null!=waybillEntity.getGoodsQtyTotal()){
			map.put("handOverPieces",waybillEntity.getGoodsQtyTotal()+"");
		}else{
			map.put("handOverPieces","0");
		}
		//运单 到达部门
		map.put("deptName", orgAdministrativeInfoEntity.getName());
		//运单 到达部门电话
		map.put("depTelephone", orgAdministrativeInfoEntity.getDepTelephone());
		return map;
	}
	/**
	 * 
	* @Title: getIsLimit 
	* @Description:根据 到达部门 判断是否在8个试点大区内 
	* @param waybillEntity 运单
	* @param @return    设定文件 
	* @return Boolean    返回类型 
	* @author 189284-ZhangXu
	* @Date 2015-8-26  下午1:59:35
	* @throws
	 */
	public Boolean getIsLimit(WaybillEntity waybillEntity) {
		Long count = 0L;
		if (waybillEntity != null) {
			DataDictionaryEntity unLoadTaskSmsDept = DictUtil
					.getDataByTermsCode(DictionaryConstants.TFR_UNTASK_SMSDEPT);
			List<String> codes = new ArrayList<String>();

			if (unLoadTaskSmsDept != null
					&& CollectionUtils.isNotEmpty(unLoadTaskSmsDept
							.getDataDictionaryValueEntityList())) {
				for (DataDictionaryValueEntity dataDictionaryValueEntity : unLoadTaskSmsDept
						.getDataDictionaryValueEntityList()) {
					codes.add(dataDictionaryValueEntity.getValueCode());
				}
				/**
				 * 试点期间全国只针对这8个到货大区开通权限，其余大区按原先形式不变，杭州大区，北京丰台大区，广州白云大区，深圳西部大区，
				 * 台州大区，保定大区，泉州大区，新乡大区 下的营业部
				 */
				count = orgAdministrativeInfoService.queryDeptInfoByCode(codes,
						waybillEntity.getCustomerPickupOrgCode());

			}
			//sonar优化 代码上移一层 避免为空  218427
			LOGGER.info(waybillEntity.getWaybillNo()+"到达部门："+waybillEntity.getCustomerPickupOrgCode()+"是否在8个大区内：" + count);
		}
		
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}
}


