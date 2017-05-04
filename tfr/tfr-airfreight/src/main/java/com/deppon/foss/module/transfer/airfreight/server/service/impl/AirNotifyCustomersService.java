package com.deppon.foss.module.transfer.airfreight.server.service.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAirAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.common.api.server.service.ISMSFailLogService;
import com.deppon.foss.module.base.common.api.server.service.ISMSSendLogService;
import com.deppon.foss.module.base.common.api.server.service.ISMSTempleteService;
import com.deppon.foss.module.base.common.api.shared.domain.SMSFailLogEntity;
import com.deppon.foss.module.base.common.api.shared.domain.SMSSendLogEntity;
import com.deppon.foss.module.base.common.api.shared.dto.SmsParamDto;
import com.deppon.foss.module.base.common.api.shared.exception.SMSSendLogException;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.NotifyCustomerException;
import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.shared.define.FossUserContextHelper;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillInfoByWaybillNoReultDto;
import com.deppon.foss.module.transfer.airfreight.api.define.AirfreightConstants;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirQueryFlightArriveDao;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirDispatchUtilService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirNotifyCustomersService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirNotifyCustomersSmsInfo;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirNotifyCustomersDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirQueryFlightArriveDto;
import com.deppon.foss.module.transfer.airfreight.server.dao.impl.AirNotifyCustomersDao;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;

import javax.annotation.Resource;

public class AirNotifyCustomersService implements IAirNotifyCustomersService {
	
	/** 日志. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AirNotifyCustomersService.class);

	/** 产品 Service. */
	@Resource
	private IProductService productService4Dubbo;
	
//	public void setProductService(IProductService productService) {
//		this.productService = productService;
//	}

	private AirNotifyCustomersDao airNotifyCustomersDao;

	  // 空运代理网点service接口
    private IAirAgencyDeptService airAgencyDeptService;
	
	private IAirDispatchUtilService airDispatchUtilService;

	public void setAirDispatchUtilService(
			IAirDispatchUtilService airDispatchUtilService) {
		this.airDispatchUtilService = airDispatchUtilService;
	}

	public void setAirAgencyDeptService(IAirAgencyDeptService airAgencyDeptService) {
		this.airAgencyDeptService = airAgencyDeptService;
	}
	
	/**
	 * 短信发送日志接口
	 */
	private ISMSFailLogService smsFailLogService;
	public void setSmsFailLogService(ISMSFailLogService smsFailLogService) {
		this.smsFailLogService = smsFailLogService;
	}

	/**
	 * 短信模板service接口
	 */
	private ISMSTempleteService sMSTempleteService;
	/**
	 * 发送短信接口
	 */
	private ISMSSendLogService smsSendLogService;
	
	public void setSmsSendLogService(ISMSSendLogService smsSendLogService) {
		this.smsSendLogService = smsSendLogService;
	}

	public void setsMSTempleteService(ISMSTempleteService sMSTempleteService) {
		this.sMSTempleteService = sMSTempleteService;
	}


	//运单承运服务接口
	private IActualFreightService actualFreightService; 
	public void setActualFreightService(IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}

	/*运单*/
	private IAirQueryFlightArriveDao airQueryFlightArriveDao;
	private IWaybillDao waybillDao;
	
	
	public void setAirQueryFlightArriveDao(
			IAirQueryFlightArriveDao airQueryFlightArriveDao) {
		this.airQueryFlightArriveDao = airQueryFlightArriveDao;
	}



	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}



	public void setAirNotifyCustomersDao(AirNotifyCustomersDao airNotifyCustomersDao) {
		this.airNotifyCustomersDao = airNotifyCustomersDao;
	}

	

	/***
	 * 去除参数空格  完成数据查询
	 */
	@SuppressWarnings("unused")
	private AirNotifyCustomersDto getAirNotifyCustomerConditionDto(
			AirNotifyCustomersDto conditionDto) {
		if(conditionDto!=null){
			String[] arrayWaybillNos = conditionDto.getArrayWaybillNos();
			if(ArrayUtils.isNotEmpty(arrayWaybillNos)){
				String[] rstArray=new String[arrayWaybillNos.length];
				for (int i = 0; i < arrayWaybillNos.length; i++) {
					rstArray[i]=arrayWaybillNos[i].trim();
				}
				conditionDto.setArrayWaybillNos(rstArray);
			}
		}
		return conditionDto;
	}
	
	/**
	 * 判断页面查询类型.
	 */
	private int getQueryType(AirNotifyCustomersDto conditionDto) {
		// 查询方式  空运通知客户  查询类型:正单号  运单号 一般查询 
		if (StringUtil.isNotBlank(conditionDto.getWaybillNo())) {
			// 解析运单号为列表
			conditionDto.setArrayWaybillNos(conditionDto.getWaybillNo().split("\\n"));
			if (conditionDto.getArrayWaybillNos().length > 0) {
				return AirfreightConstants.AIR_NOTIFY_CUSTOMERS_WAYBILLNO;
			}
		}
		if (StringUtil.isNotBlank(conditionDto.getAirWaybillNo())) {
			return AirfreightConstants.AIR_NOTIFY_CUSTOMERS_AIRWAYBILLNO;
		}
		return AirfreightConstants.AIR_NOTIFY_CUSTOMERS_COMMON;
	}


	
	/**
	 * 根据正单号得到正单信息
	 */
	@Override
	public AirWaybillEntity queryAirwaybillBywaybillNo(String waybillNo) {
		 List<AirWaybillEntity> airWaybillEntityList = new ArrayList<AirWaybillEntity>();
		 airWaybillEntityList = airNotifyCustomersDao.queryAirwaybillBywaybillNo(waybillNo);
		 if(CollectionUtils.isEmpty(airWaybillEntityList)){
			 return null;
		 }else{
			 return airWaybillEntityList.get(0);
		 }
	}

	/**
	 * 运单号去重
	 */

	@SuppressWarnings("unused")
	private Set<String> querywaybill(String[] arrayWaybillNos){
		
		Set<String> strSet = new HashSet<String>();
		if(arrayWaybillNos.length>0){
			for (int i = 0; i < arrayWaybillNos.length; i++) {
				strSet.add(arrayWaybillNos[i]);
			}
		}
		return strSet;
	}

	/**
	 * 主界面查询
	 */
	@Override                          
	public List<AirNotifyCustomersDto> queryAirNotifyCustomers(
			AirNotifyCustomersDto conditionDto, int start, int limit) {

		AirNotifyCustomersDto airNotifyCustomersDto = new AirNotifyCustomersDto();
		List<AirNotifyCustomersDto> airNotifyCustomersDtoList = new ArrayList<AirNotifyCustomersDto>();
		
		// 判断页面传入dot
		if (conditionDto == null) {
			// 为null时，返回null值
			return null;
		}
		// 判断页面查询类型.
		int queryType = getQueryType(conditionDto);
		// 对页面查询类型进行switch判断
		switch (queryType) {
		case AirfreightConstants.AIR_NOTIFY_CUSTOMERS_AIRWAYBILLNO:
			// 0.正单号查询
			airNotifyCustomersDtoList = airNotifyCustomersDao.queryAirNotifyCustomersByAirWaybillNo(conditionDto,start,limit);
			if(CollectionUtils.isNotEmpty(airNotifyCustomersDtoList)){
			
				for (AirNotifyCustomersDto airNotifyCus : airNotifyCustomersDtoList) {
				    managerAirNotifyCustomersDtoList(airNotifyCus);
			    }
			}
			break;
		case AirfreightConstants.AIR_NOTIFY_CUSTOMERS_WAYBILLNO:
			// 1.运单号查询
			airNotifyCustomersDto = getAirNotifyCustomerConditionDto(conditionDto);
			String waybills[] = airNotifyCustomersDto.getArrayWaybillNos();
			Set<String> waybillSet = querywaybill(waybills);
			if(CollectionUtils.isNotEmpty(waybillSet)){
				String[] arrayWaybillNos = new String[waybillSet.size()];
				int i = 0;
				for (String str : waybillSet) {
					arrayWaybillNos[i] = str;
					i++;
				}
				conditionDto.setArrayWaybillNos(arrayWaybillNos);
				airNotifyCustomersDtoList = (List<AirNotifyCustomersDto>) airNotifyCustomersDao.queryAirNotifyCustomersByWaybillNo(conditionDto,start,limit);
             if(CollectionUtils.isNotEmpty(airNotifyCustomersDtoList)){
            	 for (AirNotifyCustomersDto airNotifyDto : airNotifyCustomersDtoList) {
 				    managerAirNotifyCustomersDtoList(airNotifyDto);
				}
             }
		}
			break;
		default:
			// 2.默认查询
			airNotifyCustomersDtoList = airNotifyCustomersDao.queryAirNotifyCustomersByCommon(conditionDto,start,limit);
			if(CollectionUtils.isNotEmpty(airNotifyCustomersDtoList)){
				for (AirNotifyCustomersDto airNotifyCus : airNotifyCustomersDtoList) {
				    managerAirNotifyCustomersDtoList(airNotifyCus);
			    }
			}
		}
		for (AirNotifyCustomersDto airNotifyCustomersDto2 : airNotifyCustomersDtoList) {
			/**
			 * 提货方式
			 */
			
			
			if(airNotifyCustomersDto2.getReceiveMethod() != null ){
				if(airNotifyCustomersDto2.getReceiveMethod().contains("PICKUP")){
					
				if((airNotifyCustomersDto2.getReceiveMethod()).equals("AIRPORT_PICKUP")){
					airNotifyCustomersDto2.setReceiveMethod("空运机场自提");
				}else if((airNotifyCustomersDto2.getReceiveMethod()).equals("SELF_PICKUP_AIR")){
					airNotifyCustomersDto2.setReceiveMethod("空运自提(不含机场提货费)");
				}else if((airNotifyCustomersDto2.getReceiveMethod()).equals("SELF_PICKUP_FREE_AIR")){
					airNotifyCustomersDto2.setReceiveMethod("空运免费自提");
				}else{
					airNotifyCustomersDto2.setReceiveMethod("自提");
				}
			}
			else if(airNotifyCustomersDto2.getReceiveMethod().contains("DELIVER")){
				
				if((airNotifyCustomersDto2.getReceiveMethod()).equals("DELIVER_INGA_AIR")){
					airNotifyCustomersDto2.setReceiveMethod("空运送货进仓");
				}else if((airNotifyCustomersDto2.getReceiveMethod()).equals("DELIVER_UP_AIR")){
					airNotifyCustomersDto2.setReceiveMethod("空运送货上楼");
				}else if((airNotifyCustomersDto2.getReceiveMethod()).equals("DELIVER_NOUP_AIR")){
					airNotifyCustomersDto2.setReceiveMethod("送货(不含上楼)");
				}else if((airNotifyCustomersDto2.getReceiveMethod()).equals("LARGE_DELIVER_UP_AIR")){
					airNotifyCustomersDto2.setReceiveMethod("大件上楼费");
				}else if((airNotifyCustomersDto2.getReceiveMethod()).equals("DELIVER_AIR")){
					airNotifyCustomersDto2.setReceiveMethod("空运免费送货");
				}else{
					airNotifyCustomersDto2.setReceiveMethod("送货");
				}
			 }	
		   }
		}
		return airNotifyCustomersDtoList;
	}

	/**
	 * sonar 优化，311396 wwb 抽取处理list处理，避免多重内嵌
	 * @param airNotifyDto
	 */
	private void managerAirNotifyCustomersDtoList(
			AirNotifyCustomersDto airNotifyDto) {
		String waybillno = airNotifyDto.getWaybillNo();
		AirWaybillEntity airWaybillEntity = new AirWaybillEntity();
		airWaybillEntity = queryAirwaybillBywaybillNo(waybillno);
		if(airWaybillEntity != null){
			airNotifyDto.setDedtOrgName(airWaybillEntity.getDedtOrgName());
		}
		AirQueryFlightArriveDto airQueryFlightArrive  = new AirQueryFlightArriveDto();
		//GOODS_ARRIVE_AGENCY
		airQueryFlightArrive.setWaybillNo(waybillno);
		airQueryFlightArrive.setFlightArriveType("GOODS_ARRIVE_AGENCY");
		long num = airQueryFlightArriveDao.getSerialsCount(airQueryFlightArrive);
		int cont = Integer.parseInt(String.valueOf(num));
		WaybillEntity waybill = new WaybillEntity();
		waybill = waybillDao.queryWaybillByNo(airQueryFlightArrive.getWaybillNo());
		if(waybill!=null){
			int waybillNum = waybill.getGoodsQtyTotal();
			if(cont>waybillNum){
				airQueryFlightArrive.setArriveGoodsQty(waybillNum);
			}else{
				airQueryFlightArrive.setArriveGoodsQty(cont);
			}
		}
		airNotifyDto.setArriveGoodsQty(airQueryFlightArrive.getArriveGoodsQty());
	}

	
	/**
	 * 主界面查询统计运单条数
	 */
	@Override
	public long queryAirNotifyCustomersCount(AirNotifyCustomersDto conditionDto) {
		AirNotifyCustomersDto airNotifyCustomersDto = new AirNotifyCustomersDto();
		long count = 0;
		
		// 判断页面传入dot
		if (conditionDto == null) {
			// 为null时，返回null值
			return 0;
		}
		// 判断页面查询类型.
		int queryType = getQueryType(conditionDto);
		// 对页面查询类型进行switch判断
		switch (queryType) {
		case AirfreightConstants.AIR_NOTIFY_CUSTOMERS_AIRWAYBILLNO:
			// 0.正单号查询
			count = airNotifyCustomersDao.queryAirNotifyCustomersByAirWaybillNoCount(conditionDto);
			break;
		case AirfreightConstants.AIR_NOTIFY_CUSTOMERS_WAYBILLNO:
			// 1.运单号查询
			airNotifyCustomersDto = getAirNotifyCustomerConditionDto(conditionDto);
			String waybills[] = airNotifyCustomersDto.getArrayWaybillNos();
			Set<String> waybillSet = querywaybill(waybills);
			if(CollectionUtils.isNotEmpty(waybillSet)){
				String[] arrayWaybillNos = new String[waybillSet.size()];
				int i = 0;
				for (String str : waybillSet) {
					arrayWaybillNos[i] = str;
					i++;
				}
				conditionDto.setArrayWaybillNos(arrayWaybillNos);
				count =  airNotifyCustomersDao.queryAirNotifyCustomersByWaybillNoCount(conditionDto);
			}
			break;
		default:
			// 2.默认查询
			count = airNotifyCustomersDao.queryAirNotifyCustomersByCommonCount(conditionDto);
		}
		return count;
	}


	/**
	 * 查询通知信息和运单信息
	 */
	@Override
	public AirNotifyCustomersDto queryAirNotifyWaybillInfo(
			AirNotifyCustomersDto conditionDto) {
		// 判断页面传入dto
		if (conditionDto == null) {
			// 为null时，返回null值
			return null;
		}
		AirNotifyCustomersDto airNotifyCustomersDto = new AirNotifyCustomersDto();
		/**
		 * 运单号
		 */
		airNotifyCustomersDto.setWaybillNo(conditionDto.getWaybillNo());
		WaybillEntity waybill = new WaybillEntity();
		waybill = waybillDao.queryWaybillByNo(conditionDto.getWaybillNo());
		if(waybill != null){
			
		/**
		 * 收货部门名称   --始发部门
		 */
		airNotifyCustomersDto.setReceiveOrgName(waybill.getReceiveOrgName());
		
		/**
		 * 运输性质
		 */
		airNotifyCustomersDto.setProductCode(waybill.getProductCode());
		
		/**
		 * 运输性质
		 */
		ProductEntity productEntity = productService4Dubbo.getProductByCache(waybill.getProductCode(), null);
		if(productEntity != null){
			airNotifyCustomersDto.setProductName(productEntity.getName());
		}
		
		
		/**
		 * 收货人姓名  联系人-收货人
		 */
		airNotifyCustomersDto.setReceiveCustomerName(waybill.getReceiveCustomerName());
		
		/**
		 * 收货人电话
		 */
		airNotifyCustomersDto.setReceiveCustomerPhone(waybill.getReceiveCustomerPhone());
		
		/**
		 * 收货人手机
		 */
		airNotifyCustomersDto.setReceiveCustomerMobilephone(waybill.getReceiveCustomerMobilephone());
		
		/**
		 * 货物名称
		 */
		airNotifyCustomersDto.setGoodsName(waybill.getGoodsName());
		
		/**
		 * 到达代理处时间
		 */
		airNotifyCustomersDto.setArriveTime(conditionDto.getArriveTime());
		
		/**
		 * 提货网点
		 */
		airNotifyCustomersDto.setCustomerPickupOrgName(waybill.getCustomerPickupOrgName());
		
		/**
		 * 提货方式
		 */
		airNotifyCustomersDto.setReceiveMethod(waybill.getReceiveMethod()); 
		/**
		 * 正单到达网单
		 */
		airNotifyCustomersDto.setDedtOrgName(conditionDto.getDedtOrgName());
		
		/**
		 * 合票方式
		 */
		airNotifyCustomersDto.setFreightMethod(waybill.getFreightMethod());
		
		/**
		 * 运费
		 */
		airNotifyCustomersDto.setTransportFee(waybill.getTransportFee());
		
		/**
		 * 送货地址
		 */
       WaybillInfoByWaybillNoReultDto result = waybillDao.queryWaybillInfoByWaybillNo(conditionDto.getWaybillNo());

       //收货地址备注
        String receiveCustomerAddress = result.getReceiveCustomerAddress();
		if(StringUtils.isNotEmpty(result.getReceiveCustomerAddressNote())){
		     receiveCustomerAddress = result.getReceiveCustomerAddress()+"("+result.getReceiveCustomerAddressNote()+")";
			airNotifyCustomersDto.setReceiveCustomerAddressSum(receiveCustomerAddress);
		}else{
			airNotifyCustomersDto.setReceiveCustomerAddressSum(receiveCustomerAddress);
		}
		
		/**
		 * 航班类型
		 */
		airNotifyCustomersDto.setFlightNumberType(waybill.getFlightNumberType());
		
		/**
		 * 送货费
		 */
		airNotifyCustomersDto.setDeliveryGoodsFee(waybill.getDeliveryGoodsFee());
		
		/**
		 * 开单件数
		 */
		airNotifyCustomersDto.setGoodsQtyTotal(conditionDto.getGoodsQtyTotal());
		
		/**
		 * 录入到达件数
		 */
		airNotifyCustomersDto.setArriveGoodsQty(conditionDto.getArriveGoodsQty());
		
		/**
		 * 其他费用
		 */
		airNotifyCustomersDto.setOtherFee(waybill.getOtherFee());

		/**
		 * 重量
		 */
		airNotifyCustomersDto.setGoodsWeightTotal(waybill.getGoodsWeightTotal());
		
		/**
		 * 货物价值
		 */
		airNotifyCustomersDto.setInsuranceAmount(waybill.getInsuranceAmount());
			
		/**
		 * 体积
		 */
		airNotifyCustomersDto.setGoodsVolumeTotal(waybill.getGoodsVolumeTotal());
		
		/**
		 * 保价费
		 */
		airNotifyCustomersDto.setInsuranceFee(waybill.getInsuranceFee());	
			
		/**
		 * 发货人
		 */
		airNotifyCustomersDto.setDeliveryCustomerName(waybill.getDeliveryCustomerName());
		
		/**
		 * 发货人电话
		 */
		airNotifyCustomersDto.setDeliveryCustomerPhone(waybill.getDeliveryCustomerPhone());
		
		/**
		 * 发货人手机
		 */
		airNotifyCustomersDto.setDeliveryCustomerMobilephone(waybill.getDeliveryCustomerMobilephone());
		
		/**
		 * 货物尺寸
		 */
		airNotifyCustomersDto.setGoodsSize(waybill.getGoodsSize());
		/**
		 * 储运事项
		 */
		airNotifyCustomersDto.setTransportationRemark(waybill.getTransportationRemark());
		
		/**
		 * 货物包装
		 */
		airNotifyCustomersDto.setGoodsPackage(waybill.getGoodsPackage());
		
		/**
		 * 代收货款
		 */
		airNotifyCustomersDto.setCodAmount(waybill.getCodAmount());
		/**
		 * 到付金额
		 */
		airNotifyCustomersDto.setToPayAmount(waybill.getToPayAmount());
		
		/**
		 * 开单付款方式
		 */
		airNotifyCustomersDto.setPaidMethod(waybill.getPaidMethod());
			
		}
		/**
		 * 通知方式:默认为短信,不可编辑
		 */
		airNotifyCustomersDto.setNoticeType(NotifyCustomerConstants.NOTIFY_TYPE_SMS_NOTICE);
		
		List<OuterBranchEntity> outerBranchEntityList = new ArrayList<OuterBranchEntity>();
		OuterBranchEntity outerBranchEntity = new OuterBranchEntity();
		/*AirWaybillEntity  airWaybillEntity = queryAirwaybillBywaybillNo(conditionDto.getWaybillNo());
		if(airWaybillEntity == null){
			LOGGER.info("运单对应的正单号不存在。");
			throw new NotifyCustomerException("运单对应的正单号不存在。");
		}*/
		//outerBranchEntity.setAgentDeptCode(airWaybillEntity.getAgenctCode()); //agentDeptCode
		if(waybill != null){
			outerBranchEntity.setAgentDeptName(waybill.getCustomerPickupOrgName()); //agentDeptCode
		}
		outerBranchEntityList = airAgencyDeptService.queryAirAgencyDepts(outerBranchEntity, ConstantsNumberSonar.SONAR_NUMBER_5, 0);
		//airAgencyDeptService.queryAirAgencyDepts
		OuterBranchEntity outerBranch =  new OuterBranchEntity();
		if(CollectionUtils.isNotEmpty(outerBranchEntityList)){
			outerBranch = outerBranchEntityList.get(0);
		}
		AirNotifyCustomersSmsInfo airNotifyCustomersSmsInfo = new AirNotifyCustomersSmsInfo();
		
		//正单联系电话  getCustomerPickupOrgName
		airNotifyCustomersSmsInfo.setAirwaybillphone(outerBranch.getAirWaybillTel());
		//空运代理公司
		airNotifyCustomersSmsInfo.setAirAgentAddress(outerBranch.getAddress());
		
		airNotifyCustomersSmsInfo.setWaybillNo(waybill.getWaybillNo());
		airNotifyCustomersSmsInfo.setDeliveryCustomerName(waybill.getDeliveryCustomerName());
		airNotifyCustomersSmsInfo.setReceiveCustomerName(waybill.getReceiveCustomerName());
		airNotifyCustomersSmsInfo.setReceiveCustomerMobilephone(waybill.getReceiveCustomerMobilephone());
		airNotifyCustomersSmsInfo.setDeliverType(waybill.getReceiveMethod());
		airNotifyCustomersSmsInfo.setTransportationRemark(waybill.getTransportationRemark());
		airNotifyCustomersSmsInfo.setDaysPickUp(ConstantsNumberSonar.SONAR_NUMBER_3);
		//查询空运总调
		OrgAdministrativeInfoEntity org=airDispatchUtilService.queryAirDispatchDept(FossUserContext.getCurrentDeptCode());
		airNotifyCustomersSmsInfo.setOrgCode(org.getCode());
		airNotifyCustomersSmsInfo.setOrgName(org.getName());
		
		//到达件数
		AirQueryFlightArriveDto airQueryFlightArrive  = new AirQueryFlightArriveDto();
    	//GOODS_ARRIVE_AGENCY
    	airQueryFlightArrive.setWaybillNo(waybill.getWaybillNo());
    	airQueryFlightArrive.setFlightArriveType("GOODS_ARRIVE_AGENCY");
		long num = airQueryFlightArriveDao.getSerialsCount(airQueryFlightArrive);
		int cont = Integer.parseInt(String.valueOf(num));
		
		int waybillNum = waybill.getGoodsQtyTotal();
		if(cont>waybillNum){
			airNotifyCustomersSmsInfo.setGoodsQtyTotal(waybillNum);
		}else{
			airNotifyCustomersSmsInfo.setGoodsQtyTotal(cont);
		}
		//通知内容
		airNotifyCustomersDto.setNoticeContent(queryNoticeContent(airNotifyCustomersSmsInfo, airNotifyCustomersDto));
		
		/**
		 * 提货方式
		 */
		if(airNotifyCustomersDto.getReceiveMethod() != null ){
			if(airNotifyCustomersDto.getReceiveMethod().contains("PICKUP")){
				
			if((airNotifyCustomersDto.getReceiveMethod()).equals("AIRPORT_PICKUP")){
				airNotifyCustomersDto.setReceiveMethod("空运机场自提");
			}else if((airNotifyCustomersDto.getReceiveMethod()).equals("SELF_PICKUP_AIR")){
				airNotifyCustomersDto.setReceiveMethod("空运自提(不含机场提货费)");
			}else if((airNotifyCustomersDto.getReceiveMethod()).equals("SELF_PICKUP_FREE_AIR")){
				airNotifyCustomersDto.setReceiveMethod("空运免费自提");
			}else{
				airNotifyCustomersDto.setReceiveMethod("自提");
			}
			
		}
		else if(airNotifyCustomersDto.getReceiveMethod().contains("DELIVER")){
			
			if((airNotifyCustomersDto.getReceiveMethod()).equals("DELIVER_INGA_AIR")){
				airNotifyCustomersDto.setReceiveMethod("空运送货进仓");
			}else if((airNotifyCustomersDto.getReceiveMethod()).equals("DELIVER_UP_AIR")){
				airNotifyCustomersDto.setReceiveMethod("空运送货上楼");
			}else if((airNotifyCustomersDto.getReceiveMethod()).equals("DELIVER_NOUP_AIR")){
				airNotifyCustomersDto.setReceiveMethod("送货(不含上楼)");
			}else if((airNotifyCustomersDto.getReceiveMethod()).equals("LARGE_DELIVER_UP_AIR")){
				airNotifyCustomersDto.setReceiveMethod("大件上楼费");
			}else if((airNotifyCustomersDto.getReceiveMethod()).equals("DELIVER_AIR")){
				airNotifyCustomersDto.setReceiveMethod("空运免费送货");
			}else{
				airNotifyCustomersDto.setReceiveMethod("送货");
			}
		 }	
	   }
		
	   return airNotifyCustomersDto;
		
	}
	
	/**
	 * 获取可发送短信的时间间隔.
	 * 
	 */
	@Override
	public String getInformationReceiveTimeRange() {
		// 返回可发送短信的时间间隔.
		return "8:00-21:00";
	}
	
	
	/**
	 * 通知内容验证.
	 * 
	 */
	private void validateNotificationEntity(AirNotifyCustomersSmsInfo airNotifyCustomersSmsInfo) {
		if (airNotifyCustomersSmsInfo == null) {
			LOGGER.info("短信airNotifyCustomersSmsInfo不能为null");
			throw new NotifyCustomerException("短信airNotifyCustomersSmsInfo不能为null");
		}
		
		if (StringUtil.isBlank(airNotifyCustomersSmsInfo.getNoticeType())) {
			LOGGER.info("通知类型不能为空");
			throw new NotifyCustomerException("通知类型不能为空");
		}
	
		// 验证电话号码
		if (StringUtil.isBlank(airNotifyCustomersSmsInfo.getReceiveCustomerMobilephone())) {
			LOGGER.info("手机号码不能为空");
			throw new NotifyCustomerException("手机号码不能为空");
		}
		// 验证通知内容
		if (StringUtil.isBlank(airNotifyCustomersSmsInfo.getNoticeContent())) {
			LOGGER.info("短信/语音内容不能为空");
			throw new NotifyCustomerException("短信/语音内容不能为空");
		}
		// 验证接收人姓名
		if (StringUtil.isBlank(airNotifyCustomersSmsInfo.getReceiveCustomerName())) {
			LOGGER.info("接收人姓名不能为空");
			throw new NotifyCustomerException("接收人姓名不能为空");
		}

		// 运单号
		if (StringUtil.isBlank(airNotifyCustomersSmsInfo.getWaybillNo())) {
			LOGGER.info("运单号不能为空");
			throw new NotifyCustomerException("运单号不能为空");
		}

	}

	/**
	 * 发送短信
	 * 
	 */
	private void sendSms(AirNotifyCustomersSmsInfo airNotifyCustomersSmsInfo) {
		SMSSendLogEntity smsSendLog = new SMSSendLogEntity();
		try {
			//发送部门编码
			smsSendLog.setSenddeptCode(airNotifyCustomersSmsInfo.getOrgCode());
			//发送人员编码
			smsSendLog.setSenderCode(airNotifyCustomersSmsInfo.getOperatorNo());
			// 电话
			smsSendLog.setMobile(airNotifyCustomersSmsInfo.getReceiveCustomerMobilephone());
			// 短信内容
			smsSendLog.setContent(airNotifyCustomersSmsInfo.getNoticeContent());
			// 发送部门
			smsSendLog.setSenddept(airNotifyCustomersSmsInfo.getOrgName());
			// 发送人
			smsSendLog.setSender(airNotifyCustomersSmsInfo.getOperatorName());
			// 业务类型
			smsSendLog.setMsgtype("PKP_NOTIFY");
			// 短信来源
			smsSendLog.setMsgsource(NotifyCustomerConstants.SYS_SOURCE);
			// 唯一标识
			if (StringUtil.isBlank(airNotifyCustomersSmsInfo.getId())) {
				smsSendLog.setUnionId(UUIDUtils.getUUID());
			} else {
				smsSendLog.setUnionId(airNotifyCustomersSmsInfo.getId());
			}
			// 运单号
			smsSendLog.setWaybillNo(airNotifyCustomersSmsInfo.getWaybillNo());
			// 发送时间
			smsSendLog.setSendTime(new Date());
		
			// 服务类型 （1:短信、2:语音、3:短信语音）
			if (StringUtil.equals(NotifyCustomerConstants.NOTIFY_TYPE_SMS_NOTICE, airNotifyCustomersSmsInfo.getNoticeType())) {
				// 服务类型（1:短信）
				smsSendLog.setServiceType(NumberConstants.NUMERAL_S_ONE);
				LOGGER.info("短信内容：" + ReflectionToStringBuilder.toString(smsSendLog));
				// 发送短信内容
				smsSendLogService.sendSMS(smsSendLog);
			} 
		} catch (SMSSendLogException se) {
			LOGGER.error(NotifyCustomerException.ERROR, se);
			throw new NotifyCustomerException(se.getMessage(), se);
		}
	}
	/**
	 * 发送短信.
	 * 
	 */
	public void sendMessage(AirNotifyCustomersSmsInfo airNotifyCustomersSmsInfo) {
		// 必输字段验证
		validateNotificationEntity(airNotifyCustomersSmsInfo);
	
		// 通知成功
		airNotifyCustomersSmsInfo.setNoticeResult(NotifyCustomerConstants.SUCCESS);
		
		// 发送短信
		sendSms(airNotifyCustomersSmsInfo);
		
		
		} 
	
	
    /**
	 * 
	 * 发送短信  --获取短信息内容
	 */
	public void sendMessageDetail(String estimatedPickupTime, AirNotifyCustomersSmsInfo airNotifyCustomersSmsInfo) {
		// 发送短信
	   WaybillEntity waybill = new WaybillEntity();
	   AirNotifyCustomersSmsInfo airSmsInfo = new AirNotifyCustomersSmsInfo();
		waybill = waybillDao.queryWaybillByNo(airNotifyCustomersSmsInfo.getWaybillNo());
		String noticeContents = null;
		if(waybill != null){
			airSmsInfo.setWaybillNo(waybill.getWaybillNo());
			airSmsInfo.setDeliveryCustomerName(waybill.getDeliveryCustomerName());
			airSmsInfo.setReceiveCustomerName(waybill.getReceiveCustomerName());
			airSmsInfo.setReceiveCustomerMobilephone(waybill.getReceiveCustomerMobilephone());
			airSmsInfo.setDeliverType(waybill.getReceiveMethod());
			airSmsInfo.setTransportationRemark(waybill.getTransportationRemark());
			//提货天数
			if(airNotifyCustomersSmsInfo.getDaysPickUp() != null && airNotifyCustomersSmsInfo.getDaysPickUp().intValue() != 0){
				airSmsInfo.setDaysPickUp(airNotifyCustomersSmsInfo.getDaysPickUp());
			}else{
				//默认为3天
				airSmsInfo.setDaysPickUp(ConstantsNumberSonar.SONAR_NUMBER_3);
			}
			
			//查询空运总调
			OrgAdministrativeInfoEntity org=airDispatchUtilService.queryAirDispatchDept(FossUserContext.getCurrentDeptCode());
			airSmsInfo.setOrgCode(org.getCode());
			airSmsInfo.setOrgName(org.getName());
			//发送人员编码
			//获取当前操作人信息
			UserEntity userEntity=FossUserContext.getCurrentUser();
			//操作人工号 
			airSmsInfo.setOperatorNo(userEntity.getEmployee().getEmpCode());
			//操作人姓名
			airSmsInfo.setOperatorName(userEntity.getEmployee().getEmpName());
			//通知类型
			airSmsInfo.setNoticeType(NotifyCustomerConstants.NOTIFY_TYPE_SMS_NOTICE);
			//到达件数
			AirQueryFlightArriveDto airQueryFlightArrive  = new AirQueryFlightArriveDto();
    	//GOODS_ARRIVE_AGENCY
    	airQueryFlightArrive.setWaybillNo(waybill.getWaybillNo());
    	airQueryFlightArrive.setFlightArriveType("GOODS_ARRIVE_AGENCY");
 		long num = airQueryFlightArriveDao.getSerialsCount(airQueryFlightArrive);
 		int cont = Integer.parseInt(String.valueOf(num));
 		
		int waybillNum = waybill.getGoodsQtyTotal();
    		if(cont>waybillNum){
    			airSmsInfo.setGoodsQtyTotal(waybillNum);
    		}else{
    			airSmsInfo.setGoodsQtyTotal(cont);
    		}
    		
    		//正单联系电话 && 空运代理公司
    		List<OuterBranchEntity> outerBranchEntityList = new ArrayList<OuterBranchEntity>();
			OuterBranchEntity outerBranchEntity = new OuterBranchEntity();
			/*AirWaybillEntity  airWaybillEntity = queryAirwaybillBywaybillNo(waybill.getWaybillNo());
			if(airWaybillEntity == null){
				LOGGER.info("运单对应的正单号不存在。");
				throw new NotifyCustomerException("运单对应的正单号不存在。");
			}*/
			//outerBranchEntity.setAgentDeptCode(airWaybillEntity.getAgenctCode()); //agentDeptCode
			outerBranchEntity.setAgentDeptName(waybill.getCustomerPickupOrgName());			
			outerBranchEntityList = airAgencyDeptService.queryAirAgencyDepts(outerBranchEntity, ConstantsNumberSonar.SONAR_NUMBER_5, 0);
			OuterBranchEntity outerBranch =  new OuterBranchEntity();
			if(CollectionUtils.isNotEmpty(outerBranchEntityList)){
				outerBranch = outerBranchEntityList.get(0);
			}
			//正单联系电话
			airSmsInfo.setAirwaybillphone(outerBranch.getAirWaybillTel());
			//空运代理公司
			airSmsInfo.setAirAgentAddress(outerBranch.getAddress());
			AirNotifyCustomersDto airNotifyCustomersDto = new AirNotifyCustomersDto();
			//提货方式
			airNotifyCustomersDto.setReceiveMethod(waybill.getReceiveMethod());
			//合票方式
			airNotifyCustomersDto.setFreightMethod(waybill.getFreightMethod());
			//到付费用
			airNotifyCustomersDto.setToPayAmount(waybill.getToPayAmount());
			// 获取短信内容
			noticeContents = queryNoticeContent(airSmsInfo, airNotifyCustomersDto);
		}
	
		if (noticeContents != null) {
			airSmsInfo.setNoticeContent(noticeContents);
		} else {
			return;
		}
		// 发送短信
		try {
			sendMessage(airSmsInfo);
		} catch (NotifyCustomerException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	

   /**
	 * 获取短信信息.
	 */
	public String queryNoticeContent(AirNotifyCustomersSmsInfo airNotifyCustomersSmsInfo,AirNotifyCustomersDto airNotifyCustomersDto) {
		String noticeContents = null;
		if (airNotifyCustomersSmsInfo == null) {
			return noticeContents;
		}
		// 判断运单号是否为空
		if (StringUtil.isEmpty(airNotifyCustomersSmsInfo.getWaybillNo())) {
			return noticeContents;
		}
		
		// 短信模版CODE
		String smsTemplateCode = "";
		
		//获取短信模版编码
		smsTemplateCode = getSmsCode(airNotifyCustomersSmsInfo, airNotifyCustomersDto);
		
		// 短信内容
		String smsConten = "";
		if (StringUtil.isEmpty(smsTemplateCode)) {
			return noticeContents;
		}
			
		// 模版参数
		SmsParamDto smsParamDto = new SmsParamDto();
		// 短信编码
		smsParamDto.setSmsCode(smsTemplateCode);
		// 部门编码
		smsParamDto.setOrgCode(StringUtil.isEmpty(airNotifyCustomersSmsInfo.getOrgCode()) ? FossUserContextHelper.getOrgCode() : airNotifyCustomersSmsInfo.getOrgCode());
		// 参数Map
		smsParamDto.setMap(getSmsParam(airNotifyCustomersSmsInfo, airNotifyCustomersDto));
		//根据传入的参数查询符合条件的短信
		smsConten = sMSTempleteService.querySmsByParam(smsParamDto);

		if (StringUtil.isBlank(smsConten)) {
			//抛出异常
		}
		// 设置短信内容
		noticeContents = smsConten;
		// 设置语音内容

		return noticeContents;
	}
	
/**
 * 获取短信模版编码.
 * @return the smsCode
 */
private String getSmsCode(AirNotifyCustomersSmsInfo airNotifyCustomersSmsInfo,AirNotifyCustomersDto airNotifyCustomersDto) {
	
	//提货方式
	String receiveMethod = airNotifyCustomersDto.getReceiveMethod();
	//合票方式
	String freightMethod = airNotifyCustomersDto.getFreightMethod();
	//到付费用
	BigDecimal toPayAmount = airNotifyCustomersDto.getToPayAmount();
	
	//提货方式 
	//DELIVER_INGA_AIR = "DELIVER_INGA_AIR";//空运送货进仓
	//DELIVER_UP_AIR = "DELIVER_UP_AIR";//空运送货上楼
	//DELIVER_NOUP_AIR = "DELIVER_NOUP_AIR";//送货(不含上楼)
	//LARGE_DELIVER_UP_AIR="LARGE_DELIVER_UP_AIR";//大件上楼费
	//DELIVER_FREE_AIR = "DELIVER_AIR";//空运免费送货
	
	//AIRPORT_PICKUP = "AIRPORT_PICKUP";//空运机场自提
	//AIR_SELF_PICKUP = "SELF_PICKUP_AIR";//空运自提(不含机场提货费)
	// AIR_PICKUP_FREE = "SELF_PICKUP_FREE_AIR";//空运免费自提
	
	//合票方式为合大票
	if(freightMethod.equals(WaybillConstants.FREIGHT_METHOD_HDP)){

		// 送货  包含关键字DELIVER的都是送货
		if (StringUtil.isNotEmpty(airNotifyCustomersDto.getReceiveMethod()) &&
				airNotifyCustomersDto.getReceiveMethod().contains(WaybillConstants.DELIVER_FREE)) {
			return AirfreightConstants.AIR_NOTIFY_CUSTOMERS_A;
		}
		
		
		// 自提
		if (toPayAmount != null && toPayAmount.compareTo(BigDecimal.valueOf(0)) > 0) { // 有到付款
			
			//空运自提(不含机场提货费)
			if(receiveMethod.equals(WaybillConstants.AIR_SELF_PICKUP)){
				return AirfreightConstants.AIR_NOTIFY_CUSTOMERS_C;
			}
			
			//空运免费自提
			if(receiveMethod.equals(WaybillConstants.AIR_PICKUP_FREE)){
				return AirfreightConstants.AIR_NOTIFY_CUSTOMERS_E;
			}
			
		} else { 
			
			//空运自提(不含机场提货费)
			if(receiveMethod.equals(WaybillConstants.AIR_SELF_PICKUP)){
				return AirfreightConstants.AIR_NOTIFY_CUSTOMERS_B;
			}
			
			//空运免费自提
			if(receiveMethod.equals(WaybillConstants.AIR_PICKUP_FREE)){
				return AirfreightConstants.AIR_NOTIFY_CUSTOMERS_D;
			}
		}
		
	}
	
	return null;
}
/**
 * 设置短信模版内容的参数.
 * @return the smsParam
 */
private Map<String, String> getSmsParam(AirNotifyCustomersSmsInfo airNotifyCustomersSmsInfo,AirNotifyCustomersDto airNotifyCustomersDto) {
	Map<String, String> paramMap = new HashMap<String, String>();

	// 发货联系人--A
	if (StringUtil.isNotEmpty(airNotifyCustomersSmsInfo.getDeliveryCustomerName())) {
		paramMap.put("deliveryCustomerName", airNotifyCustomersSmsInfo.getDeliveryCustomerName());
	} else {
		paramMap.put("deliveryCustomerName", "");
	}
	
	// 运单号--B
	if (StringUtil.isNotEmpty(airNotifyCustomersSmsInfo.getWaybillNo())) {
		paramMap.put("waybillNo", airNotifyCustomersSmsInfo.getWaybillNo());
	} else {
		paramMap.put("waybillNo", "");
	}

	// 通知件数--C
	if (airNotifyCustomersSmsInfo.getGoodsQtyTotal() != null) {
		paramMap.put("goodsQtyTotal", airNotifyCustomersSmsInfo.getGoodsQtyTotal().toString());
	} else {
		paramMap.put("goodsQtyTotal", "");
	}
	
	//正单联系电话 --D
	if(StringUtil.isNotEmpty(airNotifyCustomersSmsInfo.getAirwaybillphone())){
		paramMap.put("airwaybillphone", airNotifyCustomersSmsInfo.getAirwaybillphone());
	}else{
		paramMap.put("airwaybillphone", "");
	}
	
	// 收货联系人姓名 --E
	if (StringUtil.isNotEmpty(airNotifyCustomersSmsInfo.getReceiveCustomerName())) {
		paramMap.put("receiveCustomerName", airNotifyCustomersSmsInfo.getReceiveCustomerName());
	} else {
		paramMap.put("receiveCustomerName", "");
	}
	
	// 预计天数 -- F
	if (StringUtil.isNotEmpty(airNotifyCustomersSmsInfo.getDaysPickUp().toString())) {
		paramMap.put("daysPickUp", airNotifyCustomersSmsInfo.getDaysPickUp().toString());
	} else {
		paramMap.put("daysPickUp", "");
	}
	
	// 空运代理地址 -- G setCustomerPickupOrgName
		if (StringUtil.isNotEmpty(airNotifyCustomersSmsInfo.getAirAgentAddress())) {
			paramMap.put("airAgentAddress", airNotifyCustomersSmsInfo.getAirAgentAddress());
		} else {
			paramMap.put("airAgentAddress", "");
		}
		
	// 到付款金额--M
	if (airNotifyCustomersDto.getToPayAmount() != null) {
		paramMap.put("toPayAmount", String.valueOf(airNotifyCustomersDto.getToPayAmount()));
	} else {
		paramMap.put("toPayAmount", "");
	}
	
	return paramMap;
}
  /**
   * 获取并设置批量通知页面显示列表信息
   * @param waybillNos
   * @param airNotifyCustomersSmsInfo
   * @param airNotifyCustomersSmsInfoList
   * @return
   */
	public List<AirNotifyCustomersSmsInfo> queryWaybillsByNos(String waybillNos,
			AirNotifyCustomersSmsInfo airNotifyCustomersSmsInfo,
			List<AirNotifyCustomersSmsInfo> airNotifyCustomersSmsInfoList) {

		List<AirNotifyCustomersSmsInfo> airInfoList = new ArrayList<AirNotifyCustomersSmsInfo>();
		if(CollectionUtils.isEmpty(airNotifyCustomersSmsInfoList)){
		       String a[] = waybillNos.split(",");  
		       for (String waybillNo : a) {
		    	   WaybillEntity waybill = new WaybillEntity();
				   AirNotifyCustomersSmsInfo airSmsInfo = new AirNotifyCustomersSmsInfo();
					waybill = waybillDao.queryWaybillByNo(waybillNo);
					if(waybill != null){
						airSmsInfo.setWaybillNo(waybill.getWaybillNo());
						airSmsInfo.setDeliveryCustomerName(waybill.getDeliveryCustomerName());
						airSmsInfo.setReceiveCustomerName(waybill.getReceiveCustomerName());
						airSmsInfo.setReceiveCustomerMobilephone(waybill.getReceiveCustomerMobilephone());
						airSmsInfo.setDeliverType(waybill.getReceiveMethod());
						airSmsInfo.setTransportationRemark(waybill.getTransportationRemark());
						airSmsInfo.setDaysPickUp(ConstantsNumberSonar.SONAR_NUMBER_3);
						//查询空运总调
						OrgAdministrativeInfoEntity org=airDispatchUtilService.queryAirDispatchDept(FossUserContext.getCurrentDeptCode());
						airSmsInfo.setOrgCode(org.getCode());
						airSmsInfo.setOrgName(org.getName());
						
						//到达件数
						AirQueryFlightArriveDto airQueryFlightArrive  = new AirQueryFlightArriveDto();
	        	    	//GOODS_ARRIVE_AGENCY
	        	    	airQueryFlightArrive.setWaybillNo(waybill.getWaybillNo());
	        	    	airQueryFlightArrive.setFlightArriveType("GOODS_ARRIVE_AGENCY");
	            		long num = airQueryFlightArriveDao.getSerialsCount(airQueryFlightArrive);
	            		int cont = Integer.parseInt(String.valueOf(num));
	            		
            			int waybillNum = waybill.getGoodsQtyTotal();
        	    		if(cont>waybillNum){
        	    			airSmsInfo.setGoodsQtyTotal(waybillNum);
        	    		}else{
        	    			airSmsInfo.setGoodsQtyTotal(cont);
        	    		}
        	    		
        	    		//正单联系电话 && 空运代理公司
        	    		List<OuterBranchEntity> outerBranchEntityList = new ArrayList<OuterBranchEntity>();
        				OuterBranchEntity outerBranchEntity = new OuterBranchEntity();
        				/*AirWaybillEntity  airWaybillEntity = queryAirwaybillBywaybillNo(waybill.getWaybillNo());
        				if(airWaybillEntity == null){
        					LOGGER.info("运单对应的正单号不存在。");
        					throw new NotifyCustomerException("运单对应的正单号不存在。");
        				}*/
        				//outerBranchEntity.setAgentDeptCode(airWaybillEntity.getAgenctCode()); //agentDeptCode
        				outerBranchEntity.setAgentDeptName(waybill.getCustomerPickupOrgName());        				
        				outerBranchEntityList = airAgencyDeptService.queryAirAgencyDepts(outerBranchEntity, ConstantsNumberSonar.SONAR_NUMBER_5, 0);
        				OuterBranchEntity outerBranch =  new OuterBranchEntity();
        				if(CollectionUtils.isNotEmpty(outerBranchEntityList)){
        					outerBranch = outerBranchEntityList.get(0);
        				}
        				//正单联系电话
        				airSmsInfo.setAirwaybillphone(outerBranch.getAirWaybillTel());
        				//空运代理公司
        				airSmsInfo.setAirAgentAddress(outerBranch.getAddress());
        				
						AirNotifyCustomersDto airNotifyCustomersDto = new AirNotifyCustomersDto();
						//TODO
						//提货方式
						airNotifyCustomersDto.setReceiveMethod(waybill.getReceiveMethod());
						//合票方式
						airNotifyCustomersDto.setFreightMethod(waybill.getFreightMethod());
						//到付费用
						airNotifyCustomersDto.setToPayAmount(waybill.getToPayAmount());
						//通知内容
						airSmsInfo.setNoticeContent(queryNoticeContent(airSmsInfo, airNotifyCustomersDto));
					}
					airInfoList.add(airSmsInfo);
			}
		}else{
			for (AirNotifyCustomersSmsInfo airInfo : airNotifyCustomersSmsInfoList) {
			   String waybillNo = airInfo.getWaybillNo();
			   WaybillEntity waybill = new WaybillEntity();
			   AirNotifyCustomersSmsInfo airSmsInfo = new AirNotifyCustomersSmsInfo();
				waybill = waybillDao.queryWaybillByNo(waybillNo);
				if(waybill != null){
					airSmsInfo.setWaybillNo(waybill.getWaybillNo());
					airSmsInfo.setDeliveryCustomerName(waybill.getDeliveryCustomerName());
					airSmsInfo.setReceiveCustomerName(waybill.getReceiveCustomerName());
					airSmsInfo.setReceiveCustomerMobilephone(waybill.getReceiveCustomerMobilephone());
					airSmsInfo.setDeliverType(waybill.getReceiveMethod());
					airSmsInfo.setTransportationRemark(waybill.getTransportationRemark());
					//查询空运总调
					OrgAdministrativeInfoEntity org=airDispatchUtilService.queryAirDispatchDept(FossUserContext.getCurrentDeptCode());
					airSmsInfo.setOrgCode(org.getCode());
					airSmsInfo.setOrgName(org.getName());
					airSmsInfo.setDaysPickUp(ConstantsNumberSonar.SONAR_NUMBER_3);
					//到达件数
					AirQueryFlightArriveDto airQueryFlightArrive  = new AirQueryFlightArriveDto();
        	    	//GOODS_ARRIVE_AGENCY
        	    	airQueryFlightArrive.setWaybillNo(waybill.getWaybillNo());
        	    	airQueryFlightArrive.setFlightArriveType("GOODS_ARRIVE_AGENCY");
            		long num = airQueryFlightArriveDao.getSerialsCount(airQueryFlightArrive);
            		int cont = Integer.parseInt(String.valueOf(num));
        			int waybillNum = waybill.getGoodsQtyTotal();
    	    		if(cont>waybillNum){
    	    			airSmsInfo.setGoodsQtyTotal(waybillNum);
    	    		}else{
    	    			airSmsInfo.setGoodsQtyTotal(cont);
    	    		}
    	    		
    	    		
    	    		//正单联系电话 && 空运代理公司
    	    		List<OuterBranchEntity> outerBranchEntityList = new ArrayList<OuterBranchEntity>();
    				OuterBranchEntity outerBranchEntity = new OuterBranchEntity();
    				/*AirWaybillEntity  airWaybillEntity = queryAirwaybillBywaybillNo(waybill.getWaybillNo());
    				if(airWaybillEntity == null){
    					LOGGER.info("运单对应的正单号不存在。");
    					throw new NotifyCustomerException("运单对应的正单号不存在。");
    				}*/
    				//outerBranchEntity.setAgentDeptCode(airWaybillEntity.getAgenctCode()); //agentDeptCode
    				outerBranchEntity.setAgentDeptName(waybill.getCustomerPickupOrgName());    	
    				outerBranchEntityList = airAgencyDeptService.queryAirAgencyDepts(outerBranchEntity, ConstantsNumberSonar.SONAR_NUMBER_5, 0);
    				OuterBranchEntity outerBranch =  new OuterBranchEntity();
    				if(CollectionUtils.isNotEmpty(outerBranchEntityList)){
    					outerBranch = outerBranchEntityList.get(0);
    				}
    				//正单联系电话
    				airSmsInfo.setAirwaybillphone(outerBranch.getAirWaybillTel());
    				//空运代理公司
    				airSmsInfo.setAirAgentAddress(outerBranch.getAddress());
            		
    				
					AirNotifyCustomersDto airNotifyCustomersDto = new AirNotifyCustomersDto();
					//TODO
					//提货方式
					airNotifyCustomersDto.setReceiveMethod(waybill.getReceiveMethod());
					//合票方式
					airNotifyCustomersDto.setFreightMethod(waybill.getFreightMethod());
					//到付费用
					airNotifyCustomersDto.setToPayAmount(waybill.getToPayAmount());
					//通知内容
					airSmsInfo.setNoticeContent(queryNoticeContent(airSmsInfo, airNotifyCustomersDto));
				}
				airInfoList.add(airSmsInfo);
			}
		}
		
		for (AirNotifyCustomersSmsInfo airNotifyCustomersSmsInfo2 : airInfoList) {
			/**
			 * 提货方式
			 */
			managerAirInfoList(airNotifyCustomersSmsInfo2);
		}
		
		return airInfoList;
	}
	
	/**
	 * sonar优化 311396 wwb 2016年12月24日09:39:14
	 * @param airNotifyCustomersSmsInfo2
	 */
	private void managerAirInfoList(
			AirNotifyCustomersSmsInfo airNotifyCustomersSmsInfo2) {
		if(airNotifyCustomersSmsInfo2.getDeliverType() != null ){
			if(airNotifyCustomersSmsInfo2.getDeliverType().contains("PICKUP")){
				
			if((airNotifyCustomersSmsInfo2.getDeliverType()).equals("AIRPORT_PICKUP")){
				airNotifyCustomersSmsInfo2.setDeliverType("空运机场自提");
			}else if((airNotifyCustomersSmsInfo2.getDeliverType()).equals("SELF_PICKUP_AIR")){
				airNotifyCustomersSmsInfo2.setDeliverType("空运自提(不含机场提货费)");
			}else if((airNotifyCustomersSmsInfo2.getDeliverType()).equals(" SELF_PICKUP_FREE_AIR")){
				airNotifyCustomersSmsInfo2.setDeliverType("空运免费自提");
			}else{
				airNotifyCustomersSmsInfo2.setDeliverType("自提");
			}
		}
		else if(airNotifyCustomersSmsInfo2.getDeliverType().contains("DELIVER")){
			
			if((airNotifyCustomersSmsInfo2.getDeliverType()).equals("DELIVER_INGA_AIR")){
				airNotifyCustomersSmsInfo2.setDeliverType("空运送货进仓");
			}else if((airNotifyCustomersSmsInfo2.getDeliverType()).equals("DELIVER_UP_AIR")){
				airNotifyCustomersSmsInfo2.setDeliverType("空运送货上楼");
			}else if((airNotifyCustomersSmsInfo2.getDeliverType()).equals("DELIVER_NOUP_AIR")){
				airNotifyCustomersSmsInfo2.setDeliverType("送货(不含上楼)");
			}else if((airNotifyCustomersSmsInfo2.getDeliverType()).equals("LARGE_DELIVER_UP_AIR")){
				airNotifyCustomersSmsInfo2.setDeliverType("大件上楼费");
			}else if((airNotifyCustomersSmsInfo2.getDeliverType()).equals("DELIVER_AIR")){
				airNotifyCustomersSmsInfo2.setDeliverType("空运免费送货");
			}else{
				airNotifyCustomersSmsInfo2.setDeliverType("送货");
			}
		 }	
	   }
	}
	
	/**
	 * 保存空运通知客户信息     
	 */
	
	public int addAirNotifyCustomersSmsInfo(
			List<AirNotifyCustomersSmsInfo> airNotifyCustomersSmsInfoList){
		if(CollectionUtils.isNotEmpty(airNotifyCustomersSmsInfoList)){
		
		for (AirNotifyCustomersSmsInfo airNCSmsInfo : airNotifyCustomersSmsInfoList) {
			AirNotifyCustomersSmsInfo airSmsInfo = new AirNotifyCustomersSmsInfo();
			//TODO
			airSmsInfo = queryAirNotifyCustomersSmsInfo(airNCSmsInfo);
			if(airSmsInfo == null){
				WaybillEntity waybill = new WaybillEntity();
				waybill = waybillDao.queryWaybillByNo(airNCSmsInfo.getWaybillNo());
				if(waybill != null){
					if(airNCSmsInfo.getId() == null){
						airNCSmsInfo.setId(UUIDUtils.getUUID());
					}
					airNCSmsInfo.setWaybillNo(waybill.getWaybillNo());
					airNCSmsInfo.setNoticeType(NotifyCustomerConstants.NOTIFY_TYPE_SMS_NOTICE);
					airNCSmsInfo.setNoticeContent(airNCSmsInfo.getNoticeContent());
					airNCSmsInfo.setDeliverType(waybill.getReceiveMethod());
					airNCSmsInfo.setNoticeResult("SUCCESS");
					airNCSmsInfo.setReceiveCustomerCode(waybill.getReceiveCustomerCode());
					airNCSmsInfo.setReceiveCustomerMobilephone(airNCSmsInfo.getReceiveCustomerMobilephone());
					airNCSmsInfo.setReceiveCustomerPhone(waybill.getReceiveCustomerPhone());
					airNCSmsInfo.setReceiveCustomerName(airNCSmsInfo.getReceiveCustomerName());
					airNCSmsInfo.setDeliveryCustomerCode(waybill.getDeliveryCustomerCode());
					airNCSmsInfo.setDeliveryCustomerMobilephone(waybill.getDeliveryCustomerMobilephone());
					airNCSmsInfo.setDeliveryCustomerName(waybill.getDeliveryCustomerName());
					airNCSmsInfo.setOperateTime(new Date());
					//通知设为1
					airNCSmsInfo.setNotifyQty(1);
					airNCSmsInfo.setModifyTime(new Date());
					OrgAdministrativeInfoEntity org=airDispatchUtilService.queryAirDispatchDept(FossUserContext.getCurrentDeptCode());

					airNCSmsInfo.setOrgCode(org.getCode());
					airNCSmsInfo.setOrgName(org.getName());
					
					//获取当前操作人信息
					UserEntity userEntity=FossUserContext.getCurrentUser();
					//操作人工号
					airNCSmsInfo.setOperatorNo(userEntity.getEmployee().getEmpCode());
					//操作人姓名
					airNCSmsInfo.setOperatorName(userEntity.getEmployee().getEmpName());
					airNCSmsInfo.setGoodsQtyTotal(waybill.getGoodsQtyTotal());
					
					List<OuterBranchEntity> outerBranchEntityList = new ArrayList<OuterBranchEntity>();
					OuterBranchEntity outerBranchEntity = new OuterBranchEntity();
					/*AirWaybillEntity  airWaybillEntity = queryAirwaybillBywaybillNo(waybill.getWaybillNo());
					if(airWaybillEntity == null){
						LOGGER.info("运单对应的正单号不存在。");
						throw new NotifyCustomerException("运单对应的正单号不存在。");
					}*/
					outerBranchEntity.setAgentDeptName(waybill.getCustomerPickupOrgName());		
					//outerBranchEntity.setAgentDeptName(airWaybillEntity.getAgencyName()); //agentDeptCode

					outerBranchEntityList = airAgencyDeptService.queryAirAgencyDepts(outerBranchEntity, ConstantsNumberSonar.SONAR_NUMBER_5, 0);
					//airAgencyDeptService.queryAirAgencyDepts
					OuterBranchEntity outerBranch =  new OuterBranchEntity();
					if(CollectionUtils.isNotEmpty(outerBranchEntityList)){
						outerBranch = outerBranchEntityList.get(0);
					}
					
					//正单联系电话
					airNCSmsInfo.setAirwaybillphone(outerBranch.getAirWaybillTel());
					//提货天数
					if(airNCSmsInfo.getDaysPickUp() != null && airNCSmsInfo.getDaysPickUp().intValue() != 0){
						airNCSmsInfo.setDaysPickUp(airNCSmsInfo.getDaysPickUp());
					}else{
						//默认为3天
						airNCSmsInfo.setDaysPickUp(ConstantsNumberSonar.SONAR_NUMBER_3);
					}
					//空运代理公司
					airNCSmsInfo.setAirAgentAddress(outerBranch.getAddress());
					airNCSmsInfo.setToPayAmount(waybill.getToPayAmount());
					airNCSmsInfo.setTransportationRemark(airNCSmsInfo.getTransportationRemark());
					
					
					if(StringUtils.isNotEmpty(airNCSmsInfo.getReceiveCustomerMobilephone())){
						airNotifyCustomersDao.addAirNotifyCustomers(airNCSmsInfo);
					}
				}	
			}else{
				//更新操作
				airSmsInfo.setModifyTime(new Date());
				//通知次数 +1
				airSmsInfo.setNotifyQty(airSmsInfo.getNotifyQty()+1);
				updateAirNotifyCustomersSmsInfo(airSmsInfo);
			}
			
		  }
		}
	   return 0;
	}


	/**
	 * 通知客户
	 *1、 保存保存空运通知客户信息 
	 *2、更新实际承运信息表的通知状态
	 *3、 发送短信
	 */
	public void airBatchNotify(
			List<AirNotifyCustomersSmsInfo> airNotifyCustomersSmsInfoList) {
		/**
		 * 保存保存空运通知客户信息 
		 */
		addAirNotifyCustomersSmsInfo(airNotifyCustomersSmsInfoList);
		
		
		for (AirNotifyCustomersSmsInfo airNCSmsInfo : airNotifyCustomersSmsInfoList) {
			/**
			 * 实际承运信息表的通知状态
			 */
			ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
			ActualFreightEntity actualFreight = new ActualFreightEntity();
			
			String waybillNo = airNCSmsInfo.getWaybillNo();
			if(StringUtils.isNotEmpty(waybillNo)){
				actualFreightEntity = actualFreightService.queryByWaybillNo(waybillNo);
			}
			if(actualFreightEntity!= null){
				actualFreight.setId(actualFreightEntity.getId());
				actualFreight.setNotificationResult(NotifyCustomerConstants.SUCCESS);
			}
			actualFreightService.updateWaybillActualFreight(actualFreight);
		}
		
		
		/**
		 * 发送短信
		 */
		for (AirNotifyCustomersSmsInfo airNotifyCustomersSmsInfo : airNotifyCustomersSmsInfoList) {
			
			sendMessageDetail((new Date()).toString(), airNotifyCustomersSmsInfo);
		}
		
	}
	
	
	/**
	 * 查询空运通知客户信息
	 */
	
	public AirNotifyCustomersSmsInfo queryAirNotifyCustomersSmsInfo(AirNotifyCustomersSmsInfo airNCSmsInfo){
		return airNotifyCustomersDao.queryAirNotifyCustomersSmsInfo(airNCSmsInfo);
	}
	
	/**
	 * 更新空运通知客户信息
	 */
	public int updateAirNotifyCustomersSmsInfo(AirNotifyCustomersSmsInfo airNCSmsInfo){
		
		return airNotifyCustomersDao.updateAirNotifyCustomersSmsInfo(airNCSmsInfo);
	}

	/**
	 * 更新空运通知客户表
	 */
	@Override
	public int updateAirNotifyCustomersSmsInfoNoticeResult(
			AirNotifyCustomersSmsInfo airNCSmsInfo) {
		return airNotifyCustomersDao.updateAirNotifyCustomersSmsInfoNoticeResult(airNCSmsInfo);
	}
	
	/**
	 * 通过JOB来定时更新空运通知客户表和实际承运信息表的通知状态
	 */
	@Override
	public void airNotifyCustomersJobRun() {
		
		AirNotifyCustomersSmsInfo airNCInfo = new AirNotifyCustomersSmsInfo();
		Date currDate = new Date();
		//开始时间
		airNCInfo.setOperateTime(DateUtils.getStartDatetime(currDate));
		//结束时间
		airNCInfo.setModifyTime(DateUtils.getEndDatetime(currDate));
		
		List<AirNotifyCustomersSmsInfo> airNotifyCustomersSmsInfoList 
		= new ArrayList<AirNotifyCustomersSmsInfo>();	
		
		airNotifyCustomersSmsInfoList = airNotifyCustomersDao.queryAirNotifyCustomersSmsInfoList(airNCInfo);

		for (AirNotifyCustomersSmsInfo airNCSmsInfo : airNotifyCustomersSmsInfoList) {
		
			if (DateUtils.getMinuteDiff(airNCSmsInfo.getModifyTime(), currDate) < NotifyCustomerConstants.SMS_NOTICE_MAX_MINUTE){
			
				SMSFailLogEntity smsFailLog = new SMSFailLogEntity();
				AirNotifyCustomersSmsInfo airNotifyCustomersSmsInfo = new AirNotifyCustomersSmsInfo();
				airNotifyCustomersSmsInfo.setId(airNCSmsInfo.getId());
				smsFailLog.setFailIdentity(airNCSmsInfo.getId());
				smsFailLog = smsFailLogService.querySMSFailLog(smsFailLog);
				if (smsFailLog != null) {
					if (FossConstants.NO.equals(smsFailLog.getIsSuccessed())) {
						// 发送失败
						airNotifyCustomersSmsInfo.setNoticeResult(NotifyCustomerConstants.FAILURE);
					} else {
						// 发送成功
						airNotifyCustomersSmsInfo.setNoticeResult(NotifyCustomerConstants.SUCCESS);
					}
					
					/**
					 * 更新空运通知客户表的通知结果  通过id
					 */
					updateAirNotifyCustomersSmsInfoNoticeResult(airNotifyCustomersSmsInfo);
					
					/**
					 * 实际承运信息表的通知状态
					 */
					ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
					ActualFreightEntity actualFreight = new ActualFreightEntity();
					
					String waybillNo = airNCSmsInfo.getWaybillNo();
					if(StringUtils.isNotEmpty(waybillNo)){
						actualFreightEntity = actualFreightService.queryByWaybillNo(waybillNo);
					}
					if(actualFreightEntity!= null){
						actualFreight.setId(actualFreightEntity.getId());
						actualFreight.setNotificationResult(airNotifyCustomersSmsInfo.getNoticeResult());
					}
					actualFreightService.updateWaybillActualFreight(actualFreight);
				}
			}
		}
	}
	
	
}