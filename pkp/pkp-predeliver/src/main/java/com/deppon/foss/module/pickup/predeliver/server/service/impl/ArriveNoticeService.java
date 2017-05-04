package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.INotifyCustomerDao;
import com.deppon.foss.module.pickup.predeliver.api.server.process.IHandleQueryOutfieldService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveNoticeService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.ICustomerReceiptAddressService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IExceptionProcessService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.context.FossUserContextHelper;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ExceptionProcessConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.CustomerReceiptAddressEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ExceptionEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ExceptionProcessDetailEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.InvoiceInfomationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveSheetDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ExceptionProcessConditionDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ExceptionProcessDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerConditionDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.ExceptionProcessException;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.NotifyCustomerException;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.sign.api.server.service.ISendWaybillTrackService;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillTrackDto;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IActualFreightDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.ICustomerBargainService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.dto.DebitDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.WaybillReceivableDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.trackings.api.shared.define.WaybillTrackingsConstants;
import com.deppon.foss.module.transfer.load.api.shared.define.TaskTruckConstant;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 计划提前通知Service
* @author 329757-foss-liuxiangcheng 
* @date 2016-6-16 上午8:47:00 
*
 */
public class ArriveNoticeService implements IArriveNoticeService{
	
	/**
	 * 日志实例
	 */
	private static final Logger LOGGER =LoggerFactory.getLogger(ArriveNoticeService.class);
	/**
	 * 注入通知客户DAO
	 */
	private INotifyCustomerDao notifyCustomerDao;
	
	/** 
	 * 常量20000.
	 */
	private static final int NUMBER = 20000;

	/**
	 * 处理异常Service
	 */
	private IExceptionProcessService exceptionProcessService;

	/**
	 * 综合Service
	 */
	private ICustomerService customerService;

	/**
	 * 到达联Service
	 */
	private IArriveSheetManngerService arriveSheetManngerService;

	/**
	 * 客户信用额度管理服务
	 */
	private ICustomerBargainService customerBargainService;
	/**
	 * 组织信息 Service接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 外场相关共通接口
	 */
	private IHandleQueryOutfieldService handleQueryOutfieldService;

	/**
	 * 应收单服务
	 */
	private IBillReceivableService billReceivableService;
	/**
	 * 业务互斥锁服务
	 */
	private IBusinessLockService businessLockService;
	/**
	 * 运单管理服务
	 */
	private IWaybillManagerService waybillManagerService;
	
	private IActualFreightDao actualFreightDao;
	
	/**
	 * 轨迹推送接口 
	 */
	private ISendWaybillTrackService sendWaybillTrackService;
	/**
	 * @param sendWaybillTrackService the sendWaybillTrackService to set
	 */
	public void setSendWaybillTrackService(
			ISendWaybillTrackService sendWaybillTrackService) {
		this.sendWaybillTrackService = sendWaybillTrackService;
	}
	/**
	 * 实际收货地址服务
	 */
	private ICustomerReceiptAddressService customerReceiptAddressService;
	
	private INotifyCustomerService notifyCustomerService;
	

	/**
	* 根据查询条件查询计划提前通知信息总条数
	* @author 329757-foss-liuxiangcheng 
	* @date 2016-6-21 下午4:22:00
	* @param @param conditionDto
	* @param @return    设定文件
	 */
	@Override
	public Long queryWaybillInfoCount(NotifyCustomerConditionDto conditionDto) {
		// 判断页面传入dot
				if (conditionDto == null) {
					// 为null时，返回null值
					return null;
				}
				// 打印页面传入参数
				LOGGER.info("客户通知页面查询COUNT参数：" + ReflectionToStringBuilder.toString(conditionDto));
				// 设置默认查询条件.
				initNotifyCustomerQuery(conditionDto);
				// 判断页面查询类型.
				int queryType = getQueryType(conditionDto);
				// 打印设置默认值后的查询参数
				LOGGER.info(ReflectionToStringBuilder.toString(conditionDto));
				// 对页面查询类型进行switch判断
				switch (queryType) {
				case NotifyCustomerConstants.SELECT_TYPE_WAYBILLNO:
					String[] waybillNos = conditionDto.getArrayWaybillNos();
					if(waybillNos.length==1){
						WaybillEntity  waybill =waybillManagerService.queryWaybillBasicByNo(conditionDto.getWaybillNo());
						if(waybill!= null){
							if(waybill.getLastLoadOrgCode()!=null && waybill.getLastLoadOrgCode().equals(FossUserContextHelper.getOrgCode())){
								// 4.按运单 号查询
								return this.notifyCustomerDao.queryArriveNoticeCountForWaybillNos(conditionDto);
							}else {
								throw new NotifyCustomerException("运单目的站非本部门!");
							}
						}
						else {
							throw new NotifyCustomerException("运单未开单!");
						}
					}else if(waybillNos.length>1){
						Long long2=0L;
						for (String waybillNo : waybillNos) {
							WaybillEntity  waybill =waybillManagerService.queryWaybillBasicByNo(waybillNo);
							if(waybill!= null){
								if(waybill.getLastLoadOrgCode()!=null && waybill.getLastLoadOrgCode().equals(FossUserContextHelper.getOrgCode())){
									// 4.按运单 号查询
									String[] waybills={waybillNo};
									conditionDto.setArrayWaybillNos(waybills);
									Long long1 = this.notifyCustomerDao.queryArriveNoticeCountForWaybillNos(conditionDto);
									long2=long2+long1;
								}
							}
						}
						return long2;
					}
				case NotifyCustomerConstants.SELECT_TYPE_PLANARRIVE:
					// 5.页面选择计划到达时间查询
					return this.notifyCustomerDao.queryArriveNoticeCountForPlanArriveTime(conditionDto);
				case NotifyCustomerConstants.SELECT_TYPE_ARRIVETIME:
					//6.页面选择实际到达时间查询
					return this.notifyCustomerDao.queryArriveNoticeCountForArriveTime(conditionDto);
				case NotifyCustomerConstants.SELECT_TYPE_NOTICETIME:
					//7.页面选择通知时间查询
					return this.notifyCustomerDao.queryArriveNoticeCountForNoticeTime(conditionDto);
				case NotifyCustomerConstants.SELECT_TYPE_STOCK_ERROR:
					// 3.页面应该录入运单号，但没有录入，直接返回null
					return null;
				default:
					// 默认为库存查询
					return null;
				}
	}

	/**
	 * 判断页面查询类型
	* @author 329757-foss-liuxiangcheng 
	* @date 2016-6-21 下午5:00:40
	* @param @param conditionDto
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	private int getQueryType(NotifyCustomerConditionDto conditionDto) {
		//收集行政区域列表-239284
		if (StringUtil.isNotBlank(conditionDto.getDeliverDistCode())) {
			conditionDto.setDeliverDistCodes(conditionDto.getDeliverDistCode().replace(" ", "").split(","));
		}
		//收集运输性质列表-239284
		if (StringUtil.isNotBlank(conditionDto.getProductCode())) {
			conditionDto.setProductCodeCons(conditionDto.getProductCode().replace(" ", "").split(","));
		}
		//收集派送方式列表-239284
		if (StringUtil.isNotBlank(conditionDto.getReceiveMethodCon())) {
			conditionDto.setReceiveMethodCons(conditionDto.getReceiveMethodCon().replace(" ", "").split(","));
		} else {
			//默认的提货方式为送货
			conditionDto.setReceiveMethodTmp(ArriveSheetConstants.SIGN_RECEIVE_METHOD_DELIVER);
		}
		
		if (StringUtil.isNotBlank(conditionDto.getWaybillNo())) {
			// 根据运单编号查询 忽略其他全部
			conditionDto.setArriveTimeStatus(null);
			conditionDto.setPlanArriveTimeFrom(null);
			conditionDto.setPlanArriveTimeTo(null);
			conditionDto.setProductCode(null);
			conditionDto.setProductCodeCons(null);
			conditionDto.setDeliverProv(null);
			conditionDto.setDeliverCity(null);
			conditionDto.setDeliverDistCodes(null);
			conditionDto.setReceiveMethodCons(null);
			conditionDto.setReceiveMethodTmp(null);
			conditionDto.setSelectType(String.valueOf(NotifyCustomerConstants.SELECT_TYPE_WAYBILLNO));
			// 解析运单号为列表
			conditionDto.setArrayWaybillNos(conditionDto.getWaybillNo().split("\\n"));
			if (conditionDto.getArrayWaybillNos().length == 0) {
				return NotifyCustomerConstants.SELECT_TYPE_STOCK_ERROR;
			}
			conditionDto.setIsQueryDeliveyDate(FossConstants.YES);
			return NotifyCustomerConstants.SELECT_TYPE_WAYBILLNO;
		}
			//获取具体的到达时间状态
			//'计划到达时间'
		if(conditionDto.getArriveTimeStatus().equals("计划到达时间")){
			if(StringUtil.isNotBlank(conditionDto.getPlanArriveTimeFrom()) || StringUtil.isNotEmpty(conditionDto.getPlanArriveTimeTo())){
				//计划到达时间。
				conditionDto.setSelectType(String.valueOf(NotifyCustomerConstants.SELECT_TYPE_PLANARRIVE));
				return NotifyCustomerConstants.SELECT_TYPE_PLANARRIVE;
			}
		}
		//'实际到达时间'
		if(conditionDto.getArriveTimeStatus().equals("实际到达时间")){
			if(StringUtil.isNotBlank(conditionDto.getPlanArriveTimeFrom()) || StringUtil.isNotEmpty(conditionDto.getPlanArriveTimeTo())){
				//实际到达时间、
				conditionDto.setSelectType(String.valueOf(NotifyCustomerConstants.SELECT_TYPE_ARRIVETIME));
				return NotifyCustomerConstants.SELECT_TYPE_ARRIVETIME;
			}
		}
		//'通知时间'
		if(conditionDto.getArriveTimeStatus().equals("通知时间")){
			if(StringUtil.isNotBlank(conditionDto.getPlanArriveTimeFrom()) || StringUtil.isNotEmpty(conditionDto.getPlanArriveTimeTo())){
				//通知时间、
				conditionDto.setSelectType(String.valueOf(NotifyCustomerConstants.SELECT_TYPE_NOTICETIME));
				return NotifyCustomerConstants.SELECT_TYPE_NOTICETIME;
			}
		}
		return 0;
	}

	/**
	 * 设置默认查询条件
	* @author 329757-foss-liuxiangcheng 
	* @date 2016-6-21 下午4:40:33
	* @param @param conditionDto    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private void initNotifyCustomerQuery(NotifyCustomerConditionDto notifyCustomerConditionDto) {
		String currOrgCode = FossUserContextHelper.getOrgCode();
		// 登录人员所在部门
		notifyCustomerConditionDto.setLastLoadOrgCode(currOrgCode);
		// 默认查询中派送方式--自提（自动过滤掉）
		notifyCustomerConditionDto.setReceiveMethod(ArriveSheetConstants.SIGN_RECEIVE_METHOD_PICKUP);
		List<String> afStatus = new ArrayList<String>();  //运单的状态--已中止已作废
		afStatus.add(WaybillConstants.ABORTED);
		afStatus.add(WaybillConstants.OBSOLETE); 
		// 关联已生成、派送中的到达联
		List<String> arriveSheetStatus = new ArrayList<String>();
		arriveSheetStatus.add(ArriveSheetConstants.STATUS_GENERATE);
		arriveSheetStatus.add(ArriveSheetConstants.STATUS_DELIVER);
		notifyCustomerConditionDto.setArrStatus(arriveSheetStatus);
		notifyCustomerConditionDto.setAfStatus(afStatus);
		// 查询中变更状态 -- 已受理
		List<String> wbrStatus = new ArrayList<String>();
		// 起草
		wbrStatus.add(WaybillRfcConstants.PRE_AUDIT);
		// 待受理、审核同意
		wbrStatus.add(WaybillRfcConstants.PRE_ACCECPT);
		notifyCustomerConditionDto.setWbrStatus(wbrStatus);
		// 当前日期
		notifyCustomerConditionDto.setDeliverDateTmp(new Date());
		// 默认日期
		notifyCustomerConditionDto.setDeliverDateDef(DateUtils.getEndDatetime(new Date()));
		// 异常状态-运单异常
		notifyCustomerConditionDto.setExceptionType(ExceptionProcessConstants.WAYBILL_EXCEPTION);
		// 异常类型-客户通知、客户自提
		notifyCustomerConditionDto.setExceptionLink1(ExceptionProcessConstants.CUSTOMER_NOTICE);
		notifyCustomerConditionDto.setExceptionLink2(ExceptionProcessConstants.CUSTOMER_PICKUP);
		// 异常状态-处理中、已转弃货
		String[] exceptionStatus = { ExceptionProcessConstants.HANDLING, ExceptionProcessConstants.ALREADY_ABANDON_GOODS };
		notifyCustomerConditionDto.setExceptionStatus(exceptionStatus);
		// 车辆状态-已废弃
		notifyCustomerConditionDto.setTaskStatus(TaskTruckConstant.TASK_TRUCK_STATE_CANCLED);
		// 默认运单不等于空运、偏线、快递   
		String productCodes[] = { ProductEntityConstants.PRICING_PRODUCT_ROUND_COUPON_PACKAGE,ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT, ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE,ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE,ProductEntityConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE,ProductEntityConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT};
		
		notifyCustomerConditionDto.setProductCodes(productCodes);
		//使用是否快递字段来过滤快递的运单  排除后续快递产品增加的影响  add  by  yangkang
		//notifyCustomerConditionDto.setIsExpress(FossConstants.YES);
		
		// 默认运单版本
		notifyCustomerConditionDto.setActive(FossConstants.ACTIVE);
		//设置所属部门的外场部门名称
		notifyCustomerConditionDto.setOutName(notifyCustomerDao.selectOutNameByOrgName(notifyCustomerConditionDto.getLastLoadOrgCode()));
		// 添加库存外场、库区默认查询条件
//		List<String> list = handleQueryOutfieldService.getEndStockCodeAndAreaCode(currOrgCode);
//		if (CollectionUtils.isNotEmpty(list)) {
//			notifyCustomerConditionDto.setEndStockOrgCode(list.get(0));
//			notifyCustomerConditionDto.setGoodsAreaCode(list.get(1));
//		}

		LOGGER.info("默认查询条件为：" + ReflectionToStringBuilder.toString(notifyCustomerConditionDto));
	}

	/**
	 * 查询计划到达通知列表信息
	* @author 329757-foss-liuxiangcheng 
	* @date 2016-6-23 下午1:39:00
	* @param @param conditionDto
	* @param @param start
	* @param @param limit
	* @param @return    设定文件
	 */
	@Override
	public List<NotifyCustomerDto> queryWaybillInfoList(
			NotifyCustomerConditionDto conditionDto, int start, int limit) {
		// 判断页面传入dot
		if (conditionDto == null) {
			// 为null时，返回null值
			return null;
		}
		// 查询列表.
		List<NotifyCustomerDto> dtos = getNotifyCustomerDto(conditionDto, start, limit);
		// 判断查询结果是否存在
		if (CollectionUtils.isEmpty(dtos)) {
			// 不存在，返回null值
			return null;
		}
		// 对查询结果进行循环
		for (NotifyCustomerDto dto : dtos) {
			// 设置dto的查询属性
			dto.setSelectType(conditionDto.getSelectType());

			// 是否为网上支付
			if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.equals(dto.getPaidMethod())) {
				// 根据运单号，查询开单付款方式为网上支付的应收单的未核销金额
				WaybillReceivableDto receivableDto = billReceivableService.queryReceivableAmountByWaybillNO(dto.getWaybillNo());
				// 没有数据的话，返回为空,没有未结清欠款,
				if (receivableDto == null) {
					// no为未结清货款
					dto.setIsPay(FossConstants.YES);
				} else {
					// yes为结清货款
					dto.setIsPay(FossConstants.NO);
				}
			}
		}
		// 返回查询结果
		return dtos;
	}
	


	/**
	 * 查询列表
	* @author 329757-foss-liuxiangcheng 
	* @date 2016-6-23 上午11:52:48
	* @param @param conditionDto
	* @param @param start
	* @param @param limit
	* @param @return    设定文件 
	* @return List<NotifyCustomerDto>    返回类型 
	* @throws
	 */
	private List<NotifyCustomerDto> getNotifyCustomerDto(
			NotifyCustomerConditionDto conditionDto, int start, int limit) {
		// 判断页面查询类型.
		int queryType = getQueryType(conditionDto);
		switch (queryType) {
		case NotifyCustomerConstants.SELECT_TYPE_WAYBILLNO:
			// 4.按运单 号查询
			List<NotifyCustomerDto> queryArriveNoticeListForWaybillNos = this.notifyCustomerDao.queryArriveNoticeListForWaybillNos(conditionDto,start,limit);
			this.setGoodsStatusAndAddress(queryArriveNoticeListForWaybillNos);
			this.setDeliver(queryArriveNoticeListForWaybillNos);
			return queryArriveNoticeListForWaybillNos;
		case NotifyCustomerConstants.SELECT_TYPE_PLANARRIVE:
			// 5.页面选择计划到达时间查询
			List<NotifyCustomerDto> queryArriveNoticeListForPlanArriveTime = this.notifyCustomerDao.queryArriveNoticeListForPlanArriveTime(conditionDto,start,limit);
			this.setGoodsStatusAndAddress(queryArriveNoticeListForPlanArriveTime);
			this.setDeliver(queryArriveNoticeListForPlanArriveTime);
			return queryArriveNoticeListForPlanArriveTime;
		case NotifyCustomerConstants.SELECT_TYPE_ARRIVETIME:
			//6.页面选择实际到达时间查询
			List<NotifyCustomerDto> queryArriveNoticeListForArriveTime = this.notifyCustomerDao.queryArriveNoticeListForArriveTime(conditionDto,start,limit);
			this.setGoodsStatusAndAddress(queryArriveNoticeListForArriveTime);
			this.setDeliver(queryArriveNoticeListForArriveTime);
			return queryArriveNoticeListForArriveTime;
		case NotifyCustomerConstants.SELECT_TYPE_NOTICETIME:
			//7.页面选择通知时间查询
			List<NotifyCustomerDto> queryArriveNoticeListForNoticeTime = this.notifyCustomerDao.queryArriveNoticeListForNoticeTime(conditionDto,start,limit);
			this.setGoodsStatusAndAddress(queryArriveNoticeListForNoticeTime);
			this.setDeliver(queryArriveNoticeListForNoticeTime);
			return queryArriveNoticeListForNoticeTime;
		case NotifyCustomerConstants.SELECT_TYPE_STOCK_ERROR:
			// 3.页面应该录入运单号，但没有录入，直接返回null
			return null;
		default:
			// 默认为库存查询
			return null;
		}
	}

	/**
	 * 给空运的派送方式设值
	 * @param queryArriveNoticeListForWaybillNos
	 */
	private void setDeliver(
			List<NotifyCustomerDto> queryArriveNoticeListForWaybillNos) {
		for (NotifyCustomerDto notifyCustomerDto : queryArriveNoticeListForWaybillNos) {
			if("DELIVER_INGA_AIR".equals(notifyCustomerDto.getReceiveMethod())){
				notifyCustomerDto.setReceiveMethod("DELIVER_INGA");
			}else if("DELIVER_UP_AIR".equals(notifyCustomerDto.getReceiveMethod())){
				notifyCustomerDto.setReceiveMethod("DELIVER_UP");
			}else if("DELIVER_AIR".equals(notifyCustomerDto.getReceiveMethod())){
				notifyCustomerDto.setReceiveMethod("DELIVER");
			}else if("DELIVER_NOUP_AIR".equals(notifyCustomerDto.getReceiveMethod())){
				notifyCustomerDto.setReceiveMethod("DELIVER_NOUP");
			}
		}
	}

	/** 
	* @author 329757-foss-liuxiangcheng 
	* @date 2016-7-4 上午11:13:24
	* @param @param queryArriveNoticeListForWaybillNos    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	private void setGoodsStatusAndAddress(
			List<NotifyCustomerDto> arriveNoticeList) {
		for (NotifyCustomerDto notifyCustomerDto : arriveNoticeList) {
			//读取库存表  如果不为空 则显示库存中
			String orgName = notifyCustomerDao.queryOrgNameByNo(notifyCustomerDto.getWaybillNo());
			//查询当前登录部门的驻地外场
			String outOrgName = notifyCustomerDao.selectOutNameByOrgName(FossUserContextHelper.getOrgCode());
			if(null!=orgName && !"".equals(orgName)){
				notifyCustomerDto.setGoodsStatus("库存中");
				//设置库存部门为当前部门名称
				notifyCustomerDto.setNowAddress(orgName);
				//判断当前库存部门是否是最终到达部门或者登陆部门的驻地外场
				if(orgName.equals(notifyCustomerDto.getDestOrgName()) || 
						orgName.equals(outOrgName)){
					//设置实际到达到达时间
					if(null==notifyCustomerDto.getActualArriveTime() || "".equals(notifyCustomerDto.getActualArriveTime())){
						notifyCustomerDto.setActualArriveTime(notifyCustomerDao.selectOneActualArriveTime(notifyCustomerDto.getWaybillNo()));
					}
				}else{
					//设置实际到达时间为空
					notifyCustomerDto.setActualArriveTime(null);
				}
			}else{
				//查询最新一条交接单记录的交接状态
				Integer handoverBillState = notifyCustomerDao.selectOneHandoverBillState(notifyCustomerDto.getWaybillNo());
				//根据运单号查询最新一条交接单状态
				if(handoverBillState!=null && handoverBillState!=0){
					//根据运单号查询交接单中的到达部门
					String destOrgName = notifyCustomerDao.selectOneDestOrgName(notifyCustomerDto.getWaybillNo());
					//设置实际到达时间
					//交接单中的到达部门是否是最终到达部门或者交接单中的到达部门是否是当前登录部门的外场
					if(destOrgName.equals(notifyCustomerDto.getDestOrgName()) ||
							destOrgName.equals(outOrgName)){
						if(null==notifyCustomerDto.getActualArriveTime() || "".equals(notifyCustomerDto.getActualArriveTime())){
							notifyCustomerDto.setActualArriveTime(notifyCustomerDao.selectOneActualArriveTime(notifyCustomerDto.getWaybillNo()));
						}
					}else{
						notifyCustomerDto.setActualArriveTime(null);
					}
					if(handoverBillState==21 || handoverBillState==20 ||handoverBillState==10){
						//状态是已装车 设置部门
						notifyCustomerDto.setGoodsStatus("装车中");
						//货物位置是始发部门
						notifyCustomerDto.setNowAddress(notifyCustomerDao.selectOneOrigOrgName(notifyCustomerDto.getWaybillNo()));
					}else if(handoverBillState==30){
						//状态是已发车 设置部门
						notifyCustomerDto.setGoodsStatus("已出发");
						//货物位置是始发部门
						notifyCustomerDto.setNowAddress(notifyCustomerDao.selectOneOrigOrgName(notifyCustomerDto.getWaybillNo()));
					}else if(handoverBillState==40){
						//状态是已到达
						notifyCustomerDto.setGoodsStatus("已到达");
						//货物位置是到达部门
						notifyCustomerDto.setNowAddress(destOrgName);
					}
				}else{
					//查询到达联
					String status = notifyCustomerDao.selectArriveSheetStatus(notifyCustomerDto.getWaybillNo());
					if("NEW".equals(status)){
						notifyCustomerDto.setGoodsStatus("生成派送单");
					}else if("DELIVER".equals(status)){
						notifyCustomerDto.setGoodsStatus("派送中");
					}else if("SIGN".equals(status)){
						notifyCustomerDto.setGoodsStatus("签收");
					}else if("REFUSE".equals(status)){
						notifyCustomerDto.setGoodsStatus("拒收");
					}
					//货物位置是到达部门
					notifyCustomerDto.setNowAddress(notifyCustomerDao.selectArriveSheetAddress(notifyCustomerDto.getWaybillNo()));
					//设置实际到达时间
					if(null==notifyCustomerDto.getActualArriveTime() || "".equals(notifyCustomerDto.getActualArriveTime())){
						notifyCustomerDto.setActualArriveTime(notifyCustomerDao.selectArriveSheetTime(notifyCustomerDto.getWaybillNo()));
					}
				}
			}
		}
	}
	
	/**
	 * 不分页查询计划提前通知信息  导出用
	* @author 329757-foss-liuxiangcheng 
	* @date 2016-6-22 下午7:23:45
	 */
	@Override
	public InputStream queryArriveNotices(
			NotifyCustomerConditionDto conditionDto) {
		// 设置默认查询条件.
		initNotifyCustomerQuery(conditionDto);
		// 判断页面查询类型.
		int queryType = getQueryType(conditionDto);
		List<NotifyCustomerDto> resultList = null;
		LOGGER.info("导出计划到达通知开始");
		switch (queryType) {
		case NotifyCustomerConstants.SELECT_TYPE_WAYBILLNO:
			// 4.按运单 号查询
			resultList = this.notifyCustomerDao.queryArriveNoticeListForWaybillNos(conditionDto,NumberConstants.ZERO,NUMBER);
			setGoodsStatusAndAddress(resultList);
			break;
		case NotifyCustomerConstants.SELECT_TYPE_PLANARRIVE:
			// 5.页面选择计划到达时间查询
			resultList = this.notifyCustomerDao.queryArriveNoticeListForPlanArriveTime(conditionDto,NumberConstants.ZERO,NUMBER);
			setGoodsStatusAndAddress(resultList);
			break;
		case NotifyCustomerConstants.SELECT_TYPE_ARRIVETIME:
			//6.页面选择实际到达时间查询
			resultList = this.notifyCustomerDao.queryArriveNoticeListForArriveTime(conditionDto,NumberConstants.ZERO,NUMBER);
			setGoodsStatusAndAddress(resultList);
			break;
		case NotifyCustomerConstants.SELECT_TYPE_NOTICETIME:
			//7.页面选择通知时间查询
			resultList = this.notifyCustomerDao.queryArriveNoticeListForNoticeTime(conditionDto,NumberConstants.ZERO,NUMBER);
			setGoodsStatusAndAddress(resultList);
			break;
		case NotifyCustomerConstants.SELECT_TYPE_STOCK_ERROR:
			// 3.页面应该录入运单号，但没有录入，直接返回null
			resultList = null;
			break;
		default:
			// 默认为库存查询
			resultList = null;
		}
		List<List<String>> rowList = new ArrayList<List<String>>();
		for(NotifyCustomerDto row : resultList){
			List<String> columnList = new ArrayList<String>();
			// 状态
			if("SUCCESS".equals(row.getNoticeResult())){
				columnList.add("通知成功");
			}else if("NONE_NOTICE".equals(row.getNoticeResult())){
				columnList.add("未通知");
			}else if("FAILURE".equals(row.getNoticeResult())){
				columnList.add("通知失败");
			}
			// 运单号
			columnList.add(row.getWaybillNo());
			// 到付总额
			columnList.add(row.getToPayAmount().toString());
			// 计划到达时间
			columnList.add(DateUtils.convert(row.getPreCustomerPickupTime(),DateUtils.DATE_TIME_FORMAT));
			// 实际到达时间
			columnList.add(DateUtils.convert(row.getActualArriveTime(),DateUtils.DATE_TIME_FORMAT));
			// 通知时间
			columnList.add(DateUtils.convert(row.getNotificationTime(),DateUtils.DATE_TIME_FORMAT));
			// 当前位置
			columnList.add(row.getNowAddress());
			// 货物状态
			columnList.add(row.getGoodsStatus());
			// 开单件数
			columnList.add(row.getGoodsQtyTotal().toString());
			//开单重量
			columnList.add(row.getGoodsWeightTotal().toString());
			//开单体积
			columnList.add(row.getGoodsVolumeTotal().toString());
			//派送方式
			setReceiveMethodes(row, columnList);
			//运输性质
			setProductCodes(row, columnList);
			rowList.add(columnList);
		}
		// 列头
		String[] rowHeads = {"状态","运单号","到付总额","计划到达时间","实际到达时间","通知时间","当前位置","货物状态","开单件数（件）","开单重量（kg）","开单体积（m³）","派送方式","运输性质"};
		
		ExportResource exportResource = new ExportResource();
		exportResource.setHeads(rowHeads);
		exportResource.setRowList(rowList);
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName("计划到达通知");
		exportSetting.setSize(NUMBER);
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		LOGGER.info("导出计划到达通知结束");
		return objExporterExecutor.exportSync(exportResource, exportSetting);
	}

	/**
	 * 抽取设置运输性质方法
	 * @param row
	 * @param columnList
	 */
	private void setProductCodes(NotifyCustomerDto row, List<String> columnList) {
		if("YTY".equals(row.getProductCode())){
			columnList.add("精准大票.标准件");
		}else if("BGLRF".equals(row.getProductCode())){
			columnList.add("精准大票汽运(长)");
		}else if("BGFLF".equals(row.getProductCode())){
			columnList.add("精准大票卡航");
		}else if("DTD".equals(row.getProductCode())){
			columnList.add("精准大票.经济件");
		}else if("FSF".equals(row.getProductCode())){
			columnList.add("精准城运");
		}else if("BGFSF".equals(row.getProductCode())){
			columnList.add("精准大票城运");
		}else if("BGSRF".equals(row.getProductCode())){
			columnList.add("精准大票汽运(短)");
		}else if("FLF".equals(row.getProductCode())){
			columnList.add("精准卡航");
		}else if("LRF".equals(row.getProductCode())){
			columnList.add("精准汽运(长途)");
		}else if("SRF".equals(row.getProductCode())){
			columnList.add("精准汽运(短途)");
		}else if("WVH".equals(row.getProductCode())){
			columnList.add("整车(三级)");
		}else{
			columnList.add(row.getProductCode());
		}
	}

	/**
	 * 抽取设置派送方式方法
	 * @param row
	 * @param columnList
	 */
	private void setReceiveMethodes(NotifyCustomerDto row,
			List<String> columnList) {
		if("DELIVER_UP".equals(row.getReceiveMethod())){
			columnList.add("送货上楼");
		}else if("DELIVER".equals(row.getReceiveMethod())){
			columnList.add("免费送货");
		}else if("DELIVER_NOUP".equals(row.getReceiveMethod())){
			columnList.add("送货（不含上楼）");
		}else if("DELIVER_INGA".equals(row.getReceiveMethod())){
			columnList.add("送货进仓空运");
		}else if("DELIVER_INGA_AIR".equals(row.getReceiveMethod())){
			columnList.add("送货进仓");
		}else if("DELIVER_UP_AIR".equals(row.getReceiveMethod())){
			columnList.add("送货上楼");
		}else if("DELIVER_AIR".equals(row.getReceiveMethod())){
			columnList.add("免费送货");
		}else if("DELIVER_NOUP_AIR".equals(row.getReceiveMethod())){
			columnList.add("送货（不含上楼）");
		}else if("LARGE_DELIVER_UP".equals(row.getReceiveMethod())){
			columnList.add("大件上楼");
		}else{
			columnList.add(row.getReceiveMethod());
		}
	}
	/**
	 * 新增通知相关信息.
	 *  @author 159231-foss-meiying
	 * @date 2014-3-5 下午5:17:14
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IBeforeNoticeService#addNotificationInfo(com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerConditionDto)
	 */
	@Override
	public void addNotificationInfo(NotifyCustomerConditionDto conditionDto) {
		// 判断传入参数
		if (conditionDto == null ||conditionDto.getNotificationEntity()==null|| StringUtil.isBlank(conditionDto.getNotificationEntity().getWaybillNo())) {
			// 打印异常信息
			LOGGER.error("通知信息异常，传入参数为NULL");
			// 抛出异常
			throw new NotifyCustomerException("通知信息异常，传入参数为NULL");
		}
		
		//保存页面实际收货地址到数据库实际收货地址表
		CustomerReceiptAddressEntity addressEntity = new CustomerReceiptAddressEntity();
		if (null != conditionDto.getNotifyCustomerDto()) {
			if (StringUtil.isNotBlank(conditionDto.getNotificationEntity().getReceiveCustomerCode()) 
					&& StringUtil.isNotBlank(conditionDto.getNotifyCustomerDto().getReceiveCustomerName())) {
				addressEntity.setCustomerCode(conditionDto.getNotificationEntity().getReceiveCustomerCode());
				addressEntity.setCustomerName(conditionDto.getNotifyCustomerDto().getReceiveCustomerName());
				addressEntity.setCustomerContactName(conditionDto.getNotificationEntity().getReceiveCustomerContact());
				addressEntity.setReceiveProvCode(conditionDto.getNotificationEntity().getActualProvCode());
				addressEntity.setReceiveCityCode(conditionDto.getNotificationEntity().getActualCityCode());
				addressEntity.setReceiveDistCode(conditionDto.getNotificationEntity().getActualDistrictCode());
				String addressNotes = (conditionDto.getNotificationEntity().getActualStreetn() == null ? "" : conditionDto.getNotificationEntity().getActualStreetn());
				addressEntity.setReceiveStreet(addressNotes);
				addressEntity.setReceiveAddressDetails(conditionDto.getNotificationEntity().getActualAddressDetail() );
				addressEntity.setCustomerMobilePhone(conditionDto.getNotificationEntity().getReceiveCustomerMobilephone());
				addressEntity.setCustomerPhone(conditionDto.getNotifyCustomerDto().getReceiveCustomerPhone());
				addressEntity.setModifyUserCode(FossUserContext.getCurrentUser().getCreateUser());
				addressEntity.setModifyUserName(FossUserContext.getCurrentUser().getEmpName());
				addressEntity.setModifyDate(new Date());
				addressEntity.setCreaterCode(FossUserContext.getCurrentUser().getCreateUser());
				addressEntity.setCreaterName(FossUserContext.getCurrentUser().getEmpName());
				addressEntity.setCreateDate(new Date());
				addressEntity.setCreateOrgCode(FossUserContext.getCurrentDeptCode());
				addressEntity.setCreateOrgName(FossUserContext.getCurrentDeptName());
				int result = customerReceiptAddressService.insertReceiptAddress(addressEntity);
				conditionDto.setNotifyCustomerDto(null);
			}
		}
		
		//运单当前页面的实际收货地址(可能是通知记录最新的实际收货地址， 也可能是开单地址)
		//如果当前页面的实际送货地址和开单地址一样，则通知保存实际送货地址为null； 否则保存新的实际收货地址(包括null)
		WaybillEntity  waybill =waybillManagerService.queryWaybillBasicByNo(conditionDto.getNotificationEntity().getWaybillNo());
		if (null != waybill) {
			boolean flag = compareWayBill2ActualAddress(waybill, conditionDto.getNotificationEntity());
			if (flag) {
				modifyActualAddress2Null(conditionDto.getNotificationEntity());
			} 
		}
		
		// 打印传入参数
		LOGGER.info("新增通知:" + ReflectionToStringBuilder.toString(conditionDto));
		List<String> waybillParams = new ArrayList<String>(); 
		waybillParams.add(conditionDto.getNotificationEntity().getWaybillNo());
		// 添加运单通知信息
		this.addNotifyCustomer(conditionDto.getNotificationEntity(), conditionDto.getInvoiceInfomationEntity());
	}
	/**
	 * 添加运单通知信息.
	 * 
	 * @param notificationEntity the notification entity
	 * @param invoiceInfomationEntity the invoice infomation entity
	 * @throws NotifyCustomerException the notify customer exception
	 * @author ibm-wangfei
	 * @date Dec 24, 2012 12:52:57 PM
	 */
	@Transactional
	@Override
	public void addNotifyCustomer(NotificationEntity notificationEntity, InvoiceInfomationEntity invoiceInfomationEntity) {
		
		if (notificationEntity == null) {
			throw new NotifyCustomerException("通知信息异常");
		}
		MutexElement mutexElement = new MutexElement(notificationEntity.getWaybillNo(), "运单号", MutexElementType.CUSTOMER_NOTIFY);
		//互斥锁定
		boolean isLocked = businessLockService.lock(mutexElement, NumberConstants.ZERO);
		//若未上锁
		if (!isLocked) {
			//抛出派送单异常
			throw new NotifyCustomerException(NotifyCustomerException.NOTIFY_UNDERWAY);
		}
		try {
			WaybillEntity  waybill =waybillManagerService.queryWaybillBasicByNo(notificationEntity.getWaybillNo());
			if(waybill!=  null){
				if(waybill.getLastLoadOrgCode()!=null && waybill.getLastLoadOrgCode().equals(FossUserContextHelper.getOrgCode())){
					//不作处理
				}else {
					throw new NotifyCustomerException("目的站不是当前营业部，不能保存!");
				}
			}else {
				throw new NotifyCustomerException("查询运单信息失败");
			}
			notificationEntity.setReceiveCustomerCode(waybill.getReceiveCustomerCode());
			// 对传入的通知信息实体进行实例化处理
			initNotificationEntity(notificationEntity);
			// 新增运单通知明细信息
			addNotificationEntity(notificationEntity);
			
			//客户通知,电话通知成功 提前通知都是电话通知 --231438，快递100，轨迹推送需求RFOSS2015031706
			WaybillTrackDto trackDto = new WaybillTrackDto();
			//当前用户登录信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			if(null == currentInfo){
				throw new NotifyCustomerException("登录信息为空！");
			}//运单号
			trackDto.setWaybillNo(notificationEntity.getWaybillNo());
			//登录信息
			trackDto.setCurrentInfo(currentInfo);
			//操作类型
			trackDto.setOperateType(WaybillTrackingsConstants.OPERATE_TYPE_NOTIFY);
			
			// 通知成功
			if (NotifyCustomerConstants.SUCCESS.equals(notificationEntity.getNoticeResult())) {
				// 判断能否月结/欠款
				checkIsBeBebt(notificationEntity);
				// 电话通知并且通知成功时,新增到达联信息
				ArriveSheetEntity entity = initArriveSheetEntity(notificationEntity);
				// 根据运单号校验生成到达联
				arriveSheetManngerService.checkGenerateArriveSheet(entity);
				// 更新到达联的通知客户录入信息
				arriveSheetManngerService.updateArriveSheetEntityByWaybillNo(entity);
				
				//通知客户，操作结果描述
				trackDto.setOperateDesc("客户通知，电话通知成功");
			} else {//客户通知,短信通知失败 --add by 231438
				//通知客户，操作结果描述
				trackDto.setOperateDesc("客户通知，电话通知失败");
			}
			//调用轨迹接口，推送轨迹 --add by 231438
			sendWaybillTrackService.sendTrackings(trackDto); 
			
			// 根据通知结果，进行异常流程处理
			updateExceptionProcess(notificationEntity);
			//DP-FOSS-零担-派送兑现时效显示功能优化需求DN201606230002  by370196
			notificationEntity.setCashTime(notifyCustomerService.executeCashTime(notificationEntity.getWaybillNo()));
			LOGGER.info("规定兑现时间和运单号：" + notificationEntity.getCashTime()+":"+notificationEntity.getWaybillNo());
			// 更新运单关联表通知状态、最后通知时间、送货日期、付款方式
			updateActualFreightEntityByWaybillNo(notificationEntity);
		} catch (NotifyCustomerException e) {
			businessLockService.unlock(mutexElement);
			throw new NotifyCustomerException(e.getErrorCode());
		}
		businessLockService.unlock(mutexElement);
	}
	/**
	 * 更新运单关联表通知状态、最后通知时间、送货日期、付款方式.
	 * 
	 * @param notificationEntity the notification entity
	 *            notificationEntity.waybillNo 运单号
	 *            notificationEntity.operateTime 通知时间
	 *            notificationEntity.deliverDate 送货日期
	 *            notificationEntity.notificationResult 通知结果
	 *            notificationEntity.paymentType 付款方式
	 * @author ibm-wangfei
	 * @date Nov 5, 2012 5:22:57 PM
	 */
	private void updateActualFreightEntityByWaybillNo(NotificationEntity notificationEntity) {
		ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
		// 运单号
		actualFreightEntity.setWaybillNo(notificationEntity.getWaybillNo());
		// 通知日期
		actualFreightEntity.setNotificationTime(notificationEntity.getOperateTime());
		// 要求送货日期
		actualFreightEntity.setDeliverDate(DateUtils.convert(notificationEntity.getDeliverDate(), DateUtils.DATE_FORMAT));

		//送货时间段，送货时间范围，发票类型
		actualFreightEntity.setDeliveryTimeInterval(notificationEntity.getDeliveryTimeInterval());
		actualFreightEntity.setDeliveryTimeStart(notificationEntity.getDeliveryTimeStart());
		actualFreightEntity.setDeliveryTimeOver(notificationEntity.getDeliveryTimeOver());
		actualFreightEntity.setInvoiceType(notificationEntity.getInvoiceType());
		
		// 通知结果
		actualFreightEntity.setNotificationResult(notificationEntity.getNoticeResult());
		// 付款方式
		actualFreightEntity.setPaymentType(notificationEntity.getPaymentType());
		// 如果是电话通知且通知成功，则设置曾经电话通知成功过
		if (NotifyCustomerConstants.NOTIFY_TYPE_TEL_NOTICE.equals(notificationEntity.getNoticeType()) && NotifyCustomerConstants.SUCCESS.equals(notificationEntity.getNoticeResult())) {
			actualFreightEntity.setEverTelnoticeSuccess(FossConstants.YES);
		}
		// 设置最后通知部门code
		actualFreightEntity.setNotificationOrgCode(notificationEntity.getOperateOrgCode());
		// 设置规定兑现时间
		actualFreightEntity.setCashTime(notificationEntity.getCashTime());
		// 根据运单编号，更新运单附属表的通知信息.
		this.updateActualFreightEntityByWaybillNo(actualFreightEntity);
	}
	/**
	 * 到达联信息实例化.
	 * 
	 * @param notificationEntity the notification entity
	 * @return the arrive sheet entity
	 * @author ibm-wangfei
	 * @date Nov 13, 2012 7:38:08 PM
	 */
	private ArriveSheetEntity initArriveSheetEntity(NotificationEntity notificationEntity) {
		ArriveSheetEntity entity = new ArriveSheetEntity();
		// 运单号
		entity.setWaybillNo(notificationEntity.getWaybillNo());
		// 是否打印
		entity.setIsPrinted(FossConstants.NO);
		// 打印次数
		entity.setPrinttimes(0);
		// 是否有效
		entity.setActive(FossConstants.YES);
		// 创建时间
		entity.setCreateDate(new Date());
		// 创建人
		entity.setCreateUserName(FossUserContextHelper.getUserName());
		//创建人code 
		entity.setCreateUserCode(FossUserContextHelper.getUserCode());
		// 创建部门
		entity.setCreateOrgName(FossUserContextHelper.getOrgName());
		// 创建部门code
		entity.setCreateOrgCode(FossUserContextHelper.getOrgCode());
		// 是否必送货
		entity.setIsSentRequired(notificationEntity.getIsSentRequired());
		// 是否需要发票
		entity.setIsNeedInvoice(notificationEntity.getIsNeedInvoice());
		// 提前通知内容
		entity.setPreNoticeContent(notificationEntity.getNoticeContent());
		// 送货要求
		entity.setDeliverRequire(notificationEntity.getDeliverRequire());
		// 要求送货日期
		entity.setDeliverDate(DateUtils.convert(notificationEntity.getDeliverDate()));
		return entity;
	}
	/**
	 * 根据运单编号，更新运单附属表的通知信息.
	 * 
	 * @param actualFreightEntity the actual freight entity
	 *            actualFreightEntity.waybillNo 运单号
	 *            actualFreightEntity.notificationTime 通知时间
	 *            actualFreightEntity.deliverDate 送货日期
	 *            actualFreightEntity.notificationResult 通知结果
	 *            actualFreightEntity.paymentType 付款方式
	 * @return the int
	 * @author ibm-wangfei
	 * @date Dec 18, 2012 7:02:41 PM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService#updateActualFreightEntityByWaybillNo(com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity)
	 */
	@Override
	public int updateActualFreightEntityByWaybillNo(ActualFreightEntity actualFreightEntity) {
		// 打印日志
		LOGGER.info("待更新的actualFreightEntity：" + ReflectionToStringBuilder.toString(actualFreightEntity));
		return notifyCustomerDao.updateActualFreightEntityByWaybillNo(actualFreightEntity);
	}
	
	/**
	 * 电话通知并且成功时、判断能否月结/欠款.
	 * 
	 * @param isTelAndSuccess the is tel and success
	 * @param notificationEntity the notification entity
	 * @throws NotifyCustomerException the notify customer exception
	 * @author ibm-wangfei
	 * @date Nov 14, 2012 2:57:31 PM
	 */
	private void checkIsBeBebt(NotificationEntity notificationEntity) {
		try {
			if (StringUtil.isEmpty(notificationEntity.getPaymentType())) {
				return;
			}
			if (!SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH.equals(notificationEntity.getPaymentType())) {
				// 如果不是现金
				DebitDto debitDto = customerBargainService.isBeBebt(notificationEntity.getReceiveCustomerCode(), notificationEntity.getOperateOrgCode(), notificationEntity.getPaymentType(), notificationEntity.getToPayAmount());
				if (!debitDto.isBeBebt()) {
					throw new NotifyCustomerException(debitDto.getMessage());
				}
			}
		} catch (SettlementException se) {
			LOGGER.error(NotifyCustomerException.ERROR, se);
			throw new NotifyCustomerException(se.getErrorCode(), se);
		}
	}
	/**
	 * 根据通知结果，进行异常流程处理.
	 * 
	 * @param notificationEntity the notification entity
	 *            notificationEntity.waybillNo 运单号
	 * @author ibm-wangfei
	 * @date Nov 5, 2012 5:25:43 PM
	 */
	private void updateExceptionProcess(NotificationEntity notificationEntity) {
		try {
			// 1:判断是否插入异常信息
			ExceptionProcessConditionDto exceptionProcessConditionDto = new ExceptionProcessConditionDto();
			// 运单号
			exceptionProcessConditionDto.setWaybillNo(notificationEntity.getWaybillNo());
			// 异常类型
			exceptionProcessConditionDto.setExceptionType(ExceptionProcessConstants.WAYBILL_EXCEPTION);
			// 异常信息
			exceptionProcessConditionDto.setExceptionLink(ExceptionProcessConstants.CUSTOMER_NOTICE);
			// 异常状态
			exceptionProcessConditionDto.setStatus(ExceptionProcessConstants.HANDLING);
			//通知内容
			//exceptionProcessConditionDto.setNoticeContext(notificationEntity.getNoticeContent());
			
			LOGGER.info("派送提前通知--送货要求"+notificationEntity.getDeliverRequire());
			// modify by foss-sunyanfei 2015-7-30 
			//将“送货要求”放入“通知内容”字段
			exceptionProcessConditionDto.setNoticeContext(notificationEntity.getDeliverRequire());
			// modify by foss-sunyanfei 2015-7-30
			
			//设置运单状态
			exceptionProcessConditionDto.setActive(FossConstants.YES);
			
			//通知成功，且勾选异常的
			if ("Y".equals(notificationEntity.getIsException()) && NotifyCustomerConstants.SUCCESS.equals(notificationEntity.getNoticeResult())) {
				exceptionProcessConditionDto.setExceptionOperate(ExceptionProcessConstants.PKP_EXCEPTION_INFORM_SUCCESS);
			}
			if (NotifyCustomerConstants.FAILURE.equals(notificationEntity.getNoticeResult())) {
				exceptionProcessConditionDto.setExceptionOperate(ExceptionProcessConstants.PKP_EXCEPTION_INFORM_FAIL);
			}
			
			List<ExceptionProcessDto> exceptionProcessDtos = exceptionProcessService.queryExceptionProcess(exceptionProcessConditionDto);
			if (exceptionProcessDtos == null || exceptionProcessDtos.size() == 0) {
				//新增通知成功且异常的也插入记录
				if ((NotifyCustomerConstants.FAILURE.equals(notificationEntity.getNoticeResult())) || (
						NotifyCustomerConstants.SUCCESS.equals(notificationEntity.getNoticeResult()) && "Y".equals(notificationEntity.getIsException()))) {
					// 如果通知失败，新增异常主子表
					ExceptionEntity exceptionEntity = initExceptionEntity(notificationEntity.getWaybillNo(), ExceptionProcessConstants.WAYBILL_EXCEPTION, ExceptionProcessConstants.CUSTOMER_NOTICE, null, null, null, null);
					//新增异常原因、异常操作、通知内容
					exceptionEntity.setExceptiOperate(exceptionProcessConditionDto.getExceptionOperate());
					exceptionEntity.setExceptionReason(DictUtil.rendererSubmitToDisplay(notificationEntity.getExceptionReason(), DictionaryConstants.PKP_NOTICE_EXCEPTION_REASON));
					exceptionEntity.setNoticeContext(exceptionProcessConditionDto.getNoticeContext());
					exceptionProcessService.addExceptionProcessInfo(exceptionEntity);
					
					if (StringUtil.isNotEmpty(exceptionEntity.getId())) {
						
						//通知成功且异常-异常详细里的异常原因下拉框异常原因
						if ((NotifyCustomerConstants.SUCCESS.equals(notificationEntity.getNoticeResult())  && FossConstants.YES.equals(notificationEntity.getIsException()))) {
							String exceptionSeason = DictUtil.rendererSubmitToDisplay(notificationEntity.getExceptionReason(), DictionaryConstants.PKP_NOTICE_EXCEPTION_REASON);
							if (StringUtil.isNotEmpty(exceptionSeason)) {
								notificationEntity.setExceptionNotes(exceptionSeason);
							} else {
								notificationEntity.setExceptionNotes("通知成功且异常");
							}
						}
						
						exceptionProcessService.addExceptionProcessDetail(initExceptionProcessDetailEntity(exceptionEntity, notificationEntity.getExceptionNotes(), null, null, null, null));
					} else {
						LOGGER.error("异常信息插入失败。");
					}
				}
			} else if (exceptionProcessDtos.size() == 1) {
				ExceptionEntity exceptionEntity = new ExceptionEntity();
				exceptionEntity.setId(exceptionProcessDtos.get(0).getExceptionProcessId());
				//只有通知成功无异常
				if (NotifyCustomerConstants.SUCCESS.equals(notificationEntity.getNoticeResult()) && FossConstants.NO.equals(notificationEntity.getIsException())) {
					// 如果通知成功,更新异常主表状态为已处理
					exceptionEntity.setStatus(ExceptionProcessConstants.HANDLED);
					exceptionEntity.setExceptionTime(new Date());
					exceptionProcessService.updateExceptionProcessInfo(exceptionEntity);
					
				} else if (NotifyCustomerConstants.FAILURE.equals(notificationEntity.getNoticeResult()) ||(
						NotifyCustomerConstants.SUCCESS.equals(notificationEntity.getNoticeResult()) && FossConstants.YES.equals(notificationEntity.getIsException())  )) {
					
					String exceptionSeason = "";
					//通知成功且异常-异常详细里的异常原因下拉框异常原因
					if ((NotifyCustomerConstants.SUCCESS.equals(notificationEntity.getNoticeResult())  && FossConstants.YES.equals(notificationEntity.getIsException()))) {
						exceptionSeason = DictUtil.rendererSubmitToDisplay(notificationEntity.getExceptionReason(), DictionaryConstants.PKP_NOTICE_EXCEPTION_REASON);
						if (StringUtil.isNotEmpty(exceptionSeason)) {
							notificationEntity.setExceptionNotes(exceptionSeason);
						} else {
							notificationEntity.setExceptionNotes("通知成功且异常原因空");
						}
					}
					
					//更新异常主表
					exceptionEntity.setExceptionTime(new Date());
					exceptionEntity.setExceptionReason(exceptionSeason);
					
					LOGGER.info("派送提前通知--送货要求--更新"+exceptionProcessConditionDto.getNoticeContext());
					// add by foss-sunyanfei 2015-7-30
					exceptionEntity.setNoticeContext(exceptionProcessConditionDto.getNoticeContext());//通知内容
					// add by foss-sunyanfei 2015-7-30
					exceptionProcessService.updateExceptionProcessInfo(exceptionEntity);
					exceptionEntity.setExceptionLink(ExceptionProcessConstants.CUSTOMER_NOTICE);
					
					// 插入异常明细信息
					exceptionProcessService.addExceptionProcessDetail(initExceptionProcessDetailEntity(exceptionEntity, notificationEntity.getExceptionNotes(), null, null, null, null));
				}  
			} else if (exceptionProcessDtos.size() == 2) {// 通知成功 (1)把只通知异常的待处理，(2)通知成功且异常的待处理； 同时更新成已处理
				for(ExceptionProcessDto eDto : exceptionProcessDtos) {
					ExceptionEntity exceptionEntity = new ExceptionEntity();
					exceptionEntity.setId(eDto.getExceptionProcessId());
					if (NotifyCustomerConstants.SUCCESS.equals(notificationEntity.getNoticeResult()) && FossConstants.NO.equals(notificationEntity.getIsException())) {
						// 如果通知成功,更新异常主表状态为已处理
						exceptionEntity.setStatus(ExceptionProcessConstants.HANDLED);
						exceptionEntity.setExceptionTime(new Date());
						exceptionProcessService.updateExceptionProcessInfo(exceptionEntity);
					} 
				}
			}  else {
				// 这里不做处理
				LOGGER.info("不做处理");
			}
		} catch (ExceptionProcessException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	/**
	 * 初始化异常分录表.
	 * 
	 * @param exceptionEntity the exception entity
	 * @param notes the notes
	 * @param lastLoadOrgCode the last load org code
	 * @param lastLoadOrgName the last load org name
	 * @param createUserCode the create user code
	 * @param createUserName the create user name
	 * @return the exception process detail entity
	 * @author ibm-wangfei
	 * @date Nov 5, 2012 6:55:52 PM
	 */
	private ExceptionProcessDetailEntity initExceptionProcessDetailEntity(ExceptionEntity exceptionEntity, String notes, String lastLoadOrgCode, String lastLoadOrgName, String createUserCode, String createUserName) {
		ExceptionProcessDetailEntity exceptionProcessDetailEntity = new ExceptionProcessDetailEntity();
		// 异常主信息ID
		exceptionProcessDetailEntity.settSrvExceptionId(exceptionEntity.getId());
		// 异常信息
		exceptionProcessDetailEntity.setNotes(notes);
		// 操作时间
		exceptionProcessDetailEntity.setOperateTime(new Date());

		if (ExceptionProcessConstants.CUSTOMER_NOTICE.equals(exceptionEntity.getExceptionLink())) {
			// 页面操作调用
			// 创建人
			exceptionProcessDetailEntity.setOperator(FossUserContextHelper.getUserName());
			// 创建人code
			exceptionProcessDetailEntity.setOperatorCode(FossUserContextHelper.getUserCode());
			// 创建部门
			exceptionProcessDetailEntity.setOperateOrgName(FossUserContextHelper.getOrgName());
			// 创建部门code
			exceptionProcessDetailEntity.setOperateOrgCode(FossUserContextHelper.getOrgCode());
		} else {
			// 设置定时任务操作人，操作部门
			// 创建人
			exceptionEntity.setCreateUserName(createUserName);
			// 创建人code
			exceptionEntity.setCreateUserCode(createUserCode);
			// 创建部门
			exceptionEntity.setCreateOrgName(lastLoadOrgName);
			// 创建部门code
			exceptionEntity.setCreateOrgCode(lastLoadOrgCode);
		}
		return exceptionProcessDetailEntity;
	}

	/**
	 * 初始化异常信息.
	 * 
	 * @param waybillNo the waybill no
	 * @param exceptionType the exception type
	 * @param exceptionLink the exception link
	 * @param lastLoadOrgCode the last load org code
	 * @param lastLoadOrgName the last load org name
	 * @param createUserCode the create user code
	 * @param createUserName the create user name
	 * @return the exception entity
	 * @author ibm-wangfei
	 * @date Nov 5, 2012 6:49:14 PM
	 */
	private ExceptionEntity initExceptionEntity(String waybillNo, String exceptionType, String exceptionLink, String lastLoadOrgCode, String lastLoadOrgName, String createUserCode, String createUserName) {
		ExceptionEntity exceptionEntity = new ExceptionEntity();
		// 运单号
		exceptionEntity.setWaybillNo(waybillNo);
		// 异常类型
		exceptionEntity.setExceptionType(exceptionType);
		// 异常环节
		exceptionEntity.setExceptionLink(exceptionLink);
		// 异常状态
		exceptionEntity.setStatus(ExceptionProcessConstants.HANDLING);
		// 异常时间
		exceptionEntity.setExceptionTime(new Date());
		if (ExceptionProcessConstants.CUSTOMER_NOTICE.equals(exceptionLink)) {
			// 创建人
			exceptionEntity.setCreateUserName(FossUserContextHelper.getUserName());
			// 创建人code
			exceptionEntity.setCreateUserCode(FossUserContextHelper.getUserCode());
			// 操作部门
			exceptionEntity.setCreateOrgName(FossUserContextHelper.getOrgName());
			// 操作部门code
			exceptionEntity.setCreateOrgCode(FossUserContextHelper.getOrgCode());
		} else {
			// 设置定时任务操作人，操作部门
			// 创建人
			exceptionEntity.setCreateUserName(createUserName);
			// 创建人code
			exceptionEntity.setCreateUserCode(createUserCode);
			// 操作部门
			exceptionEntity.setCreateOrgName(lastLoadOrgName);
			// 操作部门code
			exceptionEntity.setCreateOrgCode(lastLoadOrgCode);
		}
		return exceptionEntity;
	}
	/**
	 * 新增运单通知明细信息.
	 * 
	 * @param notificationEntity the notification entity
	 * @author ibm-wangfei
	 * @date Oct 26, 2012 4:38:35 PM
	 */
	private void addNotificationEntity(NotificationEntity notificationEntity) {
		notifyCustomerDao.addNotificationEntity(notificationEntity);
	}
	/**
	 * 对传入的通知信息实体进行实例化处理.
	 * 
	 * @param inputEntity the input entity
	 * @author ibm-wangfei
	 * @date Oct 22, 2012 11:25:02 AM
	 */
	private void initNotificationEntity(NotificationEntity inputEntity) {
		// 是否征收保管费
		if (StringUtil.isEmpty(inputEntity.getIsStorageCharge())) {
			inputEntity.setIsStorageCharge(FossConstants.NO);
		} else {
			inputEntity.setIsStorageCharge(FossConstants.YES);
		}
		// 付款方式
		if (inputEntity.getPaymentType() == null) {
			inputEntity.setPaymentType("");
		}
		//是否异常
		if (StringUtil.isEmpty(inputEntity.getIsException())) {
			inputEntity.setIsException(FossConstants.NO);
		} else {
			inputEntity.setIsException(FossConstants.YES);
		}
		//是否会展货
		if (StringUtil.isEmpty(inputEntity.getIsExhibition())) {
			inputEntity.setIsExhibition(FossConstants.NO);
		} else {
			inputEntity.setIsExhibition(FossConstants.YES);
		}
		inputEntity.setNoticeType(NotifyCustomerConstants.NOTIFY_TYPE_TEL_NOTICE);
		// 收货人
		inputEntity.setConsignee(inputEntity.getReceiveCustomerContact());
		// 联系方式
		inputEntity.setMobile(inputEntity.getReceiveCustomerMobilephone());
		if (NotifyCustomerConstants.FAILURE.equals(inputEntity.getNoticeResult())) {
			inputEntity.setNoticeContent(NotifyCustomerConstants.NOTICE_CONTENT_FAILURE);
			// 错误信息    modify by foss-sunyanfei 2015-8-6
			inputEntity.setExceptionNotes(inputEntity.getDeliverRequire());
			// 通知失败时，付款方式设置为空，即数据库不更新
			inputEntity.setPaymentType("");
		} else if (NotifyCustomerConstants.SUCCESS.equals(inputEntity.getNoticeResult()) && FossConstants.YES.equals(inputEntity.getIsException())) {
			inputEntity.setNoticeContent(NotifyCustomerConstants.NOTICE_CONTENT_SUCCESS);
			// 错误信息   modify by foss-sunyanfei 2015-8-6
			inputEntity.setExceptionNotes(inputEntity.getDeliverRequire());
		} else {
			inputEntity.setNoticeContent(NotifyCustomerConstants.NOTICE_CONTENT_SUCCESS);//通知成功
			inputEntity.setExceptionNotes("");
		}
		// 提货方式
		inputEntity.setDeliverType(inputEntity.getReceiveMethod());
		if (StringUtil.isEmpty(inputEntity.getIsNeedInvoice())) {
			// 默认为不需要发票
			inputEntity.setIsNeedInvoice(FossConstants.NO);
		}
		if (StringUtil.isEmpty(inputEntity.getIsSentRequired())) {
			// 默认为不需要送货
			inputEntity.setIsSentRequired(FossConstants.NO);
		}
		// 操作时间
		inputEntity.setOperateTime(new Date());
		// 操作人
		inputEntity.setOperator(FossUserContextHelper.getUserName());
		// 操作人编码
		inputEntity.setOperatorNo(FossUserContextHelper.getUserCode());
		// 操作部门
		inputEntity.setOperateOrgName(FossUserContextHelper.getOrgName());
		// 操作部门编码
		inputEntity.setOperateOrgCode(FossUserContextHelper.getOrgCode());
		// 币种
		inputEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		// 短信业务编码
		inputEntity.setModuleName(NotifyCustomerConstants.BS_TYPE_PKP_NOTIFY);
		// 是否提前通知
		if (StringUtil.equals(inputEntity.getTaskStatus(), TaskTruckConstant.TASK_TRUCK_STATE_UNDEPART) || 
				StringUtil.equals(inputEntity.getTaskStatus(), TaskTruckConstant.TASK_TRUCK_STATE_ONTHEWAY)) {
			inputEntity.setIsPreNotify(FossConstants.YES);
		}
		
	}
	/**
	 * 设置实际收货地址为null
	 * @param notificationEntity
	 */
	private void modifyActualAddress2Null(NotificationEntity notificationEntity) {
		notificationEntity.setActualProvCode(null);
		notificationEntity.setActualCityCode(null);
		notificationEntity.setActualDistrictCode(null);
		notificationEntity.setActualAddressDetail(null);
		notificationEntity.setActualStreetn(null);
	}
	private String toNull2Str(String str) {
		if (null == str) {
			return "";
		}
		return str;
	}
	/**
	 * 比较开单地址与当前实际收货地址是否相等
	 * @param notificationEntity
	 */
	private boolean compareWayBill2ActualAddress(WaybillEntity  waybill, NotificationEntity notificationEntity) {
		//拼接开单地址信息
		String waybillAdress = toNull2Str(waybill.getReceiveCustomerProvCode())+ toNull2Str(waybill.getReceiveCustomerCityCode())+toNull2Str(waybill.getReceiveCustomerDistCode()) +
				toNull2Str(waybill.getReceiveCustomerAddress());

		ActualFreightEntity actualFreight = this.actualFreightDao.queryByWaybillNo(notificationEntity.getWaybillNo());
		waybillAdress  = waybillAdress + toNull2Str(actualFreight.getReceiveCustomerAddressNote());
		
		//拼接通知界面实际地址
		String noticeAddress = toNull2Str(notificationEntity.getActualProvCode()) + toNull2Str(notificationEntity.getActualCityCode()) + toNull2Str(notificationEntity.getActualDistrictCode()) + 
				toNull2Str(notificationEntity.getActualAddressDetail()) + toNull2Str(notificationEntity.getActualStreetn());
		
		if (waybillAdress.equals(noticeAddress)) {
			return true;
		}
		
		return false;
	}

	/**
	 * 批量通知.
	 * @param conditionDto
	 */
	@Transactional
	@Override
	public void batchNotify(NotifyCustomerConditionDto conditionDto) {
		// 判断传入参数
		if (conditionDto == null || CollectionUtils.isEmpty(conditionDto.getNotificationEntityList())) {
			LOGGER.info("传入参数为NULL");
			return;
		}
		// 对传入的运单列表进行批量通知
		for (NotificationEntity notificationEntity : conditionDto.getNotificationEntityList()) {
			
			CustomerReceiptAddressEntity addressEntity = new CustomerReceiptAddressEntity();
			if (null != notificationEntity && null != conditionDto.getNotifyCustomerDto()) {
				//保存页面实际收货地址到数据库实际收货地址表
				if (StringUtil.isNotBlank(notificationEntity.getReceiveCustomerCode())  && StringUtil.isNotBlank(conditionDto.getNotifyCustomerDto().getReceiveCustomerName())) {
					addressEntity.setCustomerCode(notificationEntity.getReceiveCustomerCode());
					addressEntity.setCustomerName(conditionDto.getNotifyCustomerDto().getReceiveCustomerName());
					addressEntity.setCustomerContactName(notificationEntity.getReceiveCustomerContact());
					addressEntity.setReceiveProvCode(notificationEntity.getActualProvCode());
					addressEntity.setReceiveCityCode(notificationEntity.getActualCityCode());
					addressEntity.setReceiveDistCode(notificationEntity.getActualDistrictCode());
					String addressNotes = (notificationEntity.getActualStreetn() == null ? "" : notificationEntity.getActualStreetn());
					addressEntity.setReceiveStreet(addressNotes);
					addressEntity.setReceiveAddressDetails(notificationEntity.getActualAddressDetail() );
					addressEntity.setCustomerMobilePhone(notificationEntity.getReceiveCustomerMobilephone());
					addressEntity.setCustomerPhone(conditionDto.getNotifyCustomerDto().getReceiveCustomerPhone());
					addressEntity.setModifyUserCode(FossUserContext.getCurrentUser().getCreateUser());
					addressEntity.setModifyUserName(FossUserContext.getCurrentUser().getEmpName());
					addressEntity.setModifyDate(new Date());
					addressEntity.setCreaterCode(FossUserContext.getCurrentUser().getCreateUser());
					addressEntity.setCreaterName(FossUserContext.getCurrentUser().getEmpName());
					addressEntity.setCreateDate(new Date());
					addressEntity.setCreateOrgCode(FossUserContext.getCurrentDeptCode());
					addressEntity.setCreateOrgName(FossUserContext.getCurrentDeptName());
					int result = customerReceiptAddressService.insertReceiptAddress(addressEntity);
					conditionDto.setNotifyCustomerDto(null);
				} 
				
				//运单当前页面的实际收货地址(可能是通知记录最新的实际收货地址， 也可能是开单地址)
				//如果当前页面的实际送货地址和开单地址一样，则通知保存实际送货地址为null； 否则保存新的实际收货地址(包括null)
				WaybillEntity  waybill =waybillManagerService.queryWaybillBasicByNo(notificationEntity.getWaybillNo());
				if (null != waybill) {
					boolean flag = compareWayBill2ActualAddress(waybill, notificationEntity);
					if (flag) {
						modifyActualAddress2Null(notificationEntity);
					} 
				}
				
			} else {
				//合送-批量通知, 无法带修改非当前通知运单的实际送货地址，因此保存为null
				modifyActualAddress2Null(notificationEntity);
			}
			
			// 添加运单通知信息.
			this.addBeforeNotifyCustomer(notificationEntity, conditionDto.getIsTogetherSend());
		}
	}
	private void addBeforeNotifyCustomer(NotificationEntity notificationEntity,String isTogetherSend) {
		
		if (notificationEntity == null) {
			throw new NotifyCustomerException("通知信息异常");
		}
		MutexElement mutexElement = new MutexElement(notificationEntity.getWaybillNo(), "运单号", MutexElementType.CUSTOMER_NOTIFY);
		//互斥锁定
		boolean isLocked = businessLockService.lock(mutexElement, NumberConstants.ZERO);
		//若未上锁
		if (!isLocked) {
			//抛出派送单异常
			throw new NotifyCustomerException(NotifyCustomerException.NOTIFY_UNDERWAY);
		}
		try {
			WaybillEntity  waybill =waybillManagerService.queryWaybillBasicByNo(notificationEntity.getWaybillNo());
			if(waybill!=  null){
				if(waybill.getLastLoadOrgCode()!=null && waybill.getLastLoadOrgCode().equals(FossUserContextHelper.getOrgCode())){
					//不作处理
				}else {
					throw new NotifyCustomerException("目的站不是当前营业部，不能保存!");
				}
			}else {
				throw new NotifyCustomerException("查询运单信息失败");
			}
			notificationEntity.setReceiveCustomerCode(waybill.getReceiveCustomerCode());
			notificationEntity.setReceiveCustomerContact(waybill.getReceiveCustomerContact());
			notificationEntity.setReceiveMethod(DictUtil.rendererSubmitToDisplay(waybill.getReceiveMethod(), DictionaryConstants.PICKUP_GOODS));
			notificationEntity.setToPayAmount(waybill.getToPayAmount());
			// 对传入的通知信息实体进行实例化处理
			initNotificationEntity(notificationEntity);
			// 新增运单通知明细信息
			addNotificationEntity(notificationEntity);
			ActualFreightEntity actualFreight = this.actualFreightDao.queryByWaybillNo(notificationEntity.getWaybillNo());
			if(actualFreight != null){
				if(StringUtils.isNotBlank(actualFreight.getTogetherSendCode())&&FossConstants.NO.equals(isTogetherSend)){
					throw new NotifyCustomerException("保存失败，当前运单已经合送，请重新加载！");
				}else if(StringUtils.isBlank(actualFreight.getTogetherSendCode())&&FossConstants.YES.equals(isTogetherSend)){
					throw new NotifyCustomerException("保存失败，运单号："+notificationEntity.getWaybillNo()+"未合送，请重新加载！");
				}
			}
			// 通知成功
			if (NotifyCustomerConstants.SUCCESS.equals(notificationEntity.getNoticeResult())) {
				// 判断能否月结/欠款
				checkIsBeBebt(notificationEntity);
				// 电话通知并且通知成功时,新增到达联信息
				ArriveSheetEntity entity = initArriveSheetEntity(notificationEntity);
				// 根据运单号校验生成到达联
				arriveSheetManngerService.checkGenerateArriveSheet(entity);
				if(FossConstants.YES.equals(isTogetherSend)){
					entity.setTogetherSendCode(actualFreight.getTogetherSendCode());
				}
				// 更新到达联的通知客户录入信息
				arriveSheetManngerService.updateArriveSheetEntityByWaybillNo(entity);
			}else {
				if(FossConstants.YES.equals(isTogetherSend)){
					actualFreight.setTogetherSendCode(NotifyCustomerConstants.DEFAULT_GOODS_AREA_CODE);
					actualFreight.setTogetherSendMark(NotifyCustomerConstants.DEFAULT_GOODS_AREA_CODE);
					// 更新到达联的通知客户录入信息
					ArriveSheetDto dto =new ArriveSheetDto();
					String [] waybillNos={notificationEntity.getWaybillNo()};
					dto.setWaybillNos(waybillNos);
					dto.setTogetherSendCode(NotifyCustomerConstants.DEFAULT_GOODS_AREA_CODE);
					// 更新到达联的通知客户录入信息
					arriveSheetManngerService.updateTogetherSendCodeByWaybillNos(dto);
				}
			}
			//客户通知,电话通知成功 提前通知都是电话通知 --231438，快递100，轨迹推送需求RFOSS2015031706 
			WaybillTrackDto trackDto = new WaybillTrackDto();
			//当前用户登录信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			if(null == currentInfo){
				throw new NotifyCustomerException("登录信息为空！");
			}//运单号
			trackDto.setWaybillNo(notificationEntity.getWaybillNo());
			//登录信息
			trackDto.setCurrentInfo(currentInfo);
			//操作类型
			trackDto.setOperateType(WaybillTrackingsConstants.OPERATE_TYPE_NOTIFY);
			// 通知成功
			if(NotifyCustomerConstants.SUCCESS.equals(notificationEntity.getNoticeResult())) {
				//通知客户，操作结果描述
				trackDto.setOperateDesc("客户通知，电话通知成功");
			}else{//通知失败
				//通知客户，操作结果描述
				trackDto.setOperateDesc("客户通知，电话通知失败");
			}
			//调用轨迹接口，推送轨迹
//			sendWaybillTrackService.sendTrackings(trackDto);// --add by 231438
			//推送菜鸟轨迹--add by 339073
			sendWaybillTrackService.rookieTrackingByNotification(notificationEntity);
			// 根据通知结果，进行异常流程处理
			updateExceptionProcess(notificationEntity);
			//DP-FOSS-零担-派送兑现时效显示功能优化需求DN201606230002  by370196
			notificationEntity.setCashTime(notifyCustomerService.executeCashTime(notificationEntity.getWaybillNo()));
			LOGGER.info("规定兑现时间和运单号：" + notificationEntity.getCashTime()+":"+notificationEntity.getWaybillNo());
			// 更新运单关联表通知状态、最后通知时间、送货日期、付款方式,合送标识、合送编码
			updateActualFreightByWaybillNo(notificationEntity,actualFreight.getTogetherSendCode(),actualFreight.getTogetherSendMark());
			
		} catch (NotifyCustomerException e) {
			businessLockService.unlock(mutexElement);
			throw new NotifyCustomerException(e.getErrorCode());
		}
		businessLockService.unlock(mutexElement);
	}
	private void updateActualFreightByWaybillNo(NotificationEntity notificationEntity,String togetherSendCode,String togetherSendMark){
		ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
		// 运单号
		actualFreightEntity.setWaybillNo(notificationEntity.getWaybillNo());
		// 通知日期
		actualFreightEntity.setNotificationTime(notificationEntity.getOperateTime());
		// 要求送货日期
		actualFreightEntity.setDeliverDate(DateUtils.convert(notificationEntity.getDeliverDate(), DateUtils.DATE_FORMAT));

		//送货时间段，送货时间范围，发票类型
		actualFreightEntity.setDeliveryTimeInterval(notificationEntity.getDeliveryTimeInterval());
		actualFreightEntity.setDeliveryTimeStart(notificationEntity.getDeliveryTimeStart());
		actualFreightEntity.setDeliveryTimeOver(notificationEntity.getDeliveryTimeOver());
		actualFreightEntity.setInvoiceType(notificationEntity.getInvoiceType());
		
		// 通知结果
		actualFreightEntity.setNotificationResult(notificationEntity.getNoticeResult());
		// 付款方式
		actualFreightEntity.setPaymentType(notificationEntity.getPaymentType());
		// 如果是电话通知且通知成功，则设置曾经电话通知成功过
		if (NotifyCustomerConstants.NOTIFY_TYPE_TEL_NOTICE.equals(notificationEntity.getNoticeType()) && NotifyCustomerConstants.SUCCESS.equals(notificationEntity.getNoticeResult())) {
			actualFreightEntity.setEverTelnoticeSuccess(FossConstants.YES);
		}
		// 设置最后通知部门code
		actualFreightEntity.setNotificationOrgCode(notificationEntity.getOperateOrgCode());
		actualFreightEntity.setTogetherSendCode(togetherSendCode);
		actualFreightEntity.setTogetherSendMark(togetherSendMark);
		// 根据运单编号，更新运单附属表的通知信息.
		this.updateActualFreightEntityByWaybillNo(actualFreightEntity);
	}
	
	public INotifyCustomerDao getNotifyCustomerDao() {
		return notifyCustomerDao;
	}

	public void setNotifyCustomerDao(INotifyCustomerDao notifyCustomerDao) {
		this.notifyCustomerDao = notifyCustomerDao;
	}

	public IExceptionProcessService getExceptionProcessService() {
		return exceptionProcessService;
	}

	public void setExceptionProcessService(
			IExceptionProcessService exceptionProcessService) {
		this.exceptionProcessService = exceptionProcessService;
	}

	public ICustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	public IArriveSheetManngerService getArriveSheetManngerService() {
		return arriveSheetManngerService;
	}

	public void setArriveSheetManngerService(
			IArriveSheetManngerService arriveSheetManngerService) {
		this.arriveSheetManngerService = arriveSheetManngerService;
	}

	public ICustomerBargainService getCustomerBargainService() {
		return customerBargainService;
	}

	public void setCustomerBargainService(
			ICustomerBargainService customerBargainService) {
		this.customerBargainService = customerBargainService;
	}

	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		return orgAdministrativeInfoService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public IHandleQueryOutfieldService getHandleQueryOutfieldService() {
		return handleQueryOutfieldService;
	}

	public void setHandleQueryOutfieldService(
			IHandleQueryOutfieldService handleQueryOutfieldService) {
		this.handleQueryOutfieldService = handleQueryOutfieldService;
	}

	public IBillReceivableService getBillReceivableService() {
		return billReceivableService;
	}

	public void setBillReceivableService(
			IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

	public IBusinessLockService getBusinessLockService() {
		return businessLockService;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	public IWaybillManagerService getWaybillManagerService() {
		return waybillManagerService;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public IActualFreightDao getActualFreightDao() {
		return actualFreightDao;
	}

	public void setActualFreightDao(IActualFreightDao actualFreightDao) {
		this.actualFreightDao = actualFreightDao;
	}

	public ICustomerReceiptAddressService getCustomerReceiptAddressService() {
		return customerReceiptAddressService;
	}

	public void setCustomerReceiptAddressService(
			ICustomerReceiptAddressService customerReceiptAddressService) {
		this.customerReceiptAddressService = customerReceiptAddressService;
	}

	public INotifyCustomerService getNotifyCustomerService() {
		return notifyCustomerService;
	}

	public void setNotifyCustomerService(
			INotifyCustomerService notifyCustomerService) {
		this.notifyCustomerService = notifyCustomerService;
	}

	public ISendWaybillTrackService getSendWaybillTrackService() {
		return sendWaybillTrackService;
	}
}
