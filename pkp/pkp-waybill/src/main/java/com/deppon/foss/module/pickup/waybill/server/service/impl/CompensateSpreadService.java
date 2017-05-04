package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.MediaType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILateCouponService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LateCouponEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateSubDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultBillCalculateDto;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ILaterSpreadDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IPendingSendCouponDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICompensateSpreadService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICustomerCouponService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPendingSendCouponService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.server.utils.JSONUtils;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.CustomerCouponEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LaterSpreadEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.EffectiveDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LaterSpreadDto;
import com.deppon.foss.module.pickup.waybill.shared.util.PropertiesUtil;
import com.deppon.foss.module.pickup.waybill.shared.vo.SendCouponRequest;
import com.deppon.foss.module.pickup.waybill.shared.vo.SendCouponResponse;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

@Transactional
@Service
public class CompensateSpreadService implements ICompensateSpreadService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CompensateSpreadService.class);
	
	private static final int NUMBER_490 = 490;
	
	/**
	 * 作为补差价定时任务类型
	 */
	public static final String TIMEOUT_SPREAD_TYPE = "FRT";
	//要查询的JobId
	public static final String queryJobId = "N/A";
		
	//每次要查询的条数
	private static final int queryNum = 100;
	
	@Autowired
	private IPendingSendCouponService pendingSendCouponService;
	/**
	 * 待处理返券Dao
	 */
	@Autowired
	private IPendingSendCouponDao pendingSendCouponDao;
	@Autowired
	private ICustomerCouponService customerCouponService;
	@Autowired
	private IWaybillManagerService waybillManagerService;
	@Autowired
	private ILaterSpreadDao laterSpreadDao;
	
	private WebClient webClient = null;
	@Autowired
	private ICustomerService customerService;
	
	@Autowired
	private ILateCouponService lateCouponService;
	
	// 引用配置参数服务接口
	@Autowired
	private IConfigurationParamsService configurationParamsService;

	
	private String address ;
	
	private WebClient  getWebClient(){
		if(webClient == null){
			address = PropertiesUtil
					.getKeyValue(WaybillConstants.SENDCOUPONSERVICE_LATER_ADDRESS);
			webClient = WebClient.create(address);
		}
		LOGGER.info("FOSS-CRM晚到补差价优惠券地址:"+address);
		return webClient;
	}

	@Override
	public void calculateSpread(String... wayBillNos) {
		// 1、根据运单号计算差价
		// 2、获取客户编码和手机号
		// 3、短息内容
		// 4、保存数据  供定时任务自动调用
		if (wayBillNos != null && wayBillNos.length == 0) {
			throw new BusinessException("晚到补差价运单号不能为空");
		}
		for (String wayBillNo : wayBillNos) {
			LaterSpreadEntity psc = new LaterSpreadEntity();
			if(StringUtils.isEmpty(wayBillNo)){
				LOGGER.info("运单号为空不作处理");
				continue;
			}
			LOGGER.info("处理运单号:"+wayBillNo+"晚到补差价开始");
			try {
				psc.setWaybillNo(wayBillNo);
				//检查运单号是否已经处理
				List<LaterSpreadEntity> laterSpreadList = laterSpreadDao.queryLaterSpreadList(psc);
				if(laterSpreadList!=null && !laterSpreadList.isEmpty()){
					throw new BusinessException("运单号:"+wayBillNo+"已处理");
				}
				WaybillEntity  waybill = waybillManagerService.queryWaybillBasicByNo(wayBillNo);
				if(waybill == null){
					throw new BusinessException("根据运单号:"+wayBillNo+"未查询到运单信息");
				}
				//提货方式 自提
				String receiveMethod = waybill.getReceiveMethod();
				//liding comment 
//				LateCouponEntity  lateCouponEntity = lateCouponService.returnSchemeToPKP();
				//liding add start 
				//收货部门(出发部门)
				String startOrgCode = waybill.getReceiveOrgCode();
				//提货网点(到达部门)
				String arriveOrgcode = waybill.getCustomerPickupOrgCode();
				//产品编码
				String productCode = waybill.getProductCode();
				//晚到补差价——方案实体 
				LateCouponEntity  lateCouponEntity = null;
				//业务上来讲传入三个参数不会为空,但是这里还是判断下,如果为空走接口虽然不会报错,但是没有这个必要。
				if (StringUtils.isNotEmpty(startOrgCode)
						&& StringUtils.isNotEmpty(arriveOrgcode)
						&& StringUtils.isNotEmpty(productCode)) {
					//晚到补差价接口优化,改成综合提供的新接口 20160413-273279-liding
					lateCouponEntity = lateCouponService.returnSchemeToPKPByOrg(startOrgCode,arriveOrgcode,productCode);
				}
				//liding add end 
				if(lateCouponEntity == null){
					throw new BusinessException("运单号:"+wayBillNo+"晚到补差价方案为空");
				}
				
				String deliveryMethod = lateCouponEntity.getDeliveryMethod();
				if(StringUtils.isEmpty(deliveryMethod)){
					throw new BusinessException("运单号:"+wayBillNo+"晚到补差价方案中提货方式为空");
				}
				//DN201512210004 晚到补差价liding mod
				// 提货方式变为多选
				//if(!deliveryMethod.equals(receiveMethod)){
				if(deliveryMethod.indexOf(receiveMethod, 0) == -1){
					throw new BusinessException("运单号:"+wayBillNo+"提货方式不在晚到补差价方案中提货方式");
				}
				//liding commnet
				//综合已校验运输方式接送货此处注释掉20160414
//				//运输方式 卡航与城运
//				String productCode = waybill.getProductCode();
//				List<String> proItemValue = lateCouponEntity.getProductCodes();
//				if(proItemValue==null || proItemValue.isEmpty()){
//					throw new RuntimeException("运单号:"+wayBillNo+"晚到补差价方案中运输方式为空");
//				}
//				if(!proItemValue.contains(productCode)){
//					throw new RuntimeException("运单号:"+wayBillNo+"运输方式不在晚到补差价方案中运输方式");
//				}
				//判断是否修改过目的站和提货方式
				Long changeDestinationAndReceiveMethod = laterSpreadDao.queryWaybillChangeDestinationAndReceiveMethod(wayBillNo);
				if(changeDestinationAndReceiveMethod > 0){
					throw new BusinessException("运单号:"+wayBillNo+"修改过目的站和提货方式");
				}
				String notSendReason = "";
				
				//方案开始时间
				Date beginTime  = lateCouponEntity.getBeginTime();
				Date endTime = lateCouponEntity.getEndTime();
				Date billTime = waybill.getBillTime();
				//比较开单时间是否在优惠券方案内
				if(!(billTime.getTime() >= beginTime.getTime() 
						&& billTime.getTime() <= endTime.getTime())){
					throw new BusinessException("运单号:"+wayBillNo+"开单时间不在晚到补差价方案时间内");
				}
				//客户编码
				String customerCode = null;
				//手机号
				String phone = null;
				//如果收货部门是这4个大区就给发货人，如果收货部门是另外4个大区就给收货人
				//获取 对应的 客户编码和手机号
				//boolean  deliverBigArea = true;
				//收货部门 判断是发给收货人还是发货人优惠券
				//String receiveOrgCode = waybill.getReceiveOrgCode();
				//出发大区
				//List<String> startSalesDept = lateCouponEntity.getStartSalesDept();
				//到达大区 
				//List<String>  arriveSalesDept = lateCouponEntity.getArriveSalesDept();
				//判断是否是 试点营业部
				//boolean  pilotBigArea = false;
				/*if(arriveSalesDept != null){
					if(arriveSalesDept.contains(receiveOrgCode)){
						pilotBigArea = true;
					}
				}*/
				/*if(startSalesDept != null){
					if(startSalesDept.contains(receiveOrgCode)){
						pilotBigArea = true;
					}
					if(!startSalesDept.contains(receiveOrgCode)){
						//收货客户
						deliverBigArea = false;
					}
				}*/
				/**
				 * 短息发送对象 start=出发 arrive=到达 all=全部
				 */
				//String smsSent = lateCouponEntity.getSmsSent();
				//都为null取消 试点优惠券发送给 发送人
//				if(CollectionUtils.isEmpty(startSalesDept) 
//						&& CollectionUtils.isEmpty(arriveSalesDept)){
//					pilotBigArea = true;
//				}
				//不在试点大区
				/*if(!pilotBigArea && ("all".equals(smsSent) || StringUtils.isEmpty(smsSent)) ){
					throw new RuntimeException("运单号:"+wayBillNo+"号不在试点大区");
				}*/
				/*if(!pilotBigArea && CollectionUtils.isEmpty(startSalesDept) 
						&& CollectionUtils.isEmpty(arriveSalesDept)){
					//取消试点
					if("start".equals(smsSent)){
						deliverBigArea = true;
					}else{
						deliverBigArea = false;
					}
				}*/
				
				customerCode = waybill.getDeliveryCustomerCode();
				phone = waybill.getDeliveryCustomerMobilephone();
				
				/*if(deliverBigArea){
					customerCode = waybill.getDeliveryCustomerCode();
					phone = waybill.getDeliveryCustomerMobilephone();
				}else{
					customerCode = waybill.getReceiveCustomerCode();
					phone = waybill.getReceiveCustomerMobilephone();
				}*/
				//判断是否是新客户
				CustomerEntity customerEntity = customerService.queryCustomerInfoByCusCode(waybill.getDeliveryCustomerCode());
				if(customerEntity == null){
					throw new BusinessException(wayBillNo+"未查询到对应客户信息");
				}
				String flabelleavemonth = customerEntity.getFlabelleavemonth();
				if(StringUtils.isEmpty(flabelleavemonth)){
					throw new BusinessException(wayBillNo+"对应客户分群信息为空");
				}
				//DN201512210004 晚到补差价liding add
				// 客户分群变为多选
				String customerGroup = lateCouponEntity.getCustomerGroup();
				if(StringUtils.isEmpty(customerGroup)){
					throw new BusinessException("运单号:"+wayBillNo+"晚到补差价方案中客户分群为空");
				}
				//if(!flabelleavemonth.equals("NEWCUST")){
				if(customerGroup.indexOf(flabelleavemonth, 0) == -1){
					//throw new RuntimeException(wayBillNo+"对应客户不是新客户分群");
					throw new BusinessException(wayBillNo+"对应客户不是晚到补差价方案中的客户分群");
				}
				if(StringUtils.isEmpty(customerCode)){
					notSendReason = "运单号:"+wayBillNo+"客户编码为空,不作处理";
					throw new BusinessException(notSendReason);				}
				
				if(StringUtils.isEmpty(phone)){
					notSendReason = "运单号:"+wayBillNo+"客户编码:"+customerCode+"手机号为空,不作处理";
					throw new BusinessException(notSendReason);	
				}
				BigDecimal resourceTransportFee = waybill.getTransportFee();
				if(resourceTransportFee==null){
					notSendReason = "运单号:"+wayBillNo+"未查询到运费价格,不作处理";
					throw new BusinessException(notSendReason);	
				}
				BigDecimal lrfTransportFee = getSpread(waybill);
				if(lrfTransportFee.compareTo(new BigDecimal(0))==0){
					notSendReason = "运单号:"+wayBillNo+"普运运费价格为0,不作处理";
					throw new BusinessException(notSendReason);	
				}
				if((resourceTransportFee.compareTo(lrfTransportFee)==-1 ||
						resourceTransportFee.compareTo(lrfTransportFee)==0)){
					notSendReason = "运单号:"+wayBillNo+"客户编码:"+customerCode+
							"计算普运运费("+lrfTransportFee+")大于等于卡航/城运运费("+resourceTransportFee+"),不作处理";
					throw new BusinessException(notSendReason);	
				}
				// 统一返回费用 从配置中获取设置的返回费用 
				BigDecimal spread = new BigDecimal(30);
				try {
					ConfigurationParamsEntity lateReturnCoupon = configurationParamsService.queryConfigurationParamsByOrgCode(
							DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,
							ConfigurationParamsConstants.LATE_RETURN_COUPON, FossConstants.ROOT_ORG_CODE);
					spread = BigDecimal.valueOf(Integer.valueOf(lateReturnCoupon.getConfValue()).intValue());
				} catch (Exception e) {
					LOGGER.error("获取配置参数失败",ExceptionUtils.getFullStackTrace(e));
				}
				//差价 卡航/城运与普运差价计算方式，20170105版本以前的，----376290
				//BigDecimal spread = resourceTransportFee.subtract(lrfTransportFee);
				/*LOGGER.info("运单号:"+wayBillNo+"卡航/城运与普运差价为:"+spread);
				NumberFormat format = NumberFormat.getNumberInstance();
				format.setMaximumFractionDigits(0);*/
				//去掉小数
			/*	String val  = format.format(spread);
				val = val.replaceAll(",", "");
				spread = new BigDecimal(val);*/
				/*LOGGER.info("运单号:"+wayBillNo+"卡航/城运与普运差价为:"+spread);*/
				LOGGER.info("运单号:"+wayBillNo+"晚到退回差价为:"+spread);
				// 根据差价生成短信内容,从综合获取 s%占位
				String sms = lateCouponEntity.getSmsInfo();
				StringBuilder couponAmount = new StringBuilder();
				Integer maxValue = Integer.parseInt(lateCouponEntity.getMaxValue());
				Integer spreadInt = Integer.parseInt(String.valueOf(spread));
				Integer couponNum = spreadInt/maxValue;
				if(couponNum == 0 ){
					couponNum = 1;
					couponAmount.append(spreadInt);
				}else{
					 for (int i = 0; i < couponNum; i++) {
						 if((i+1)==couponNum){
							 couponAmount.append(maxValue);
						 }else{
							 couponAmount.append(maxValue+",");
						 }
					 }
					 if(spreadInt%maxValue != 0){
						 couponAmount.append(","+spreadInt%maxValue);
						 couponNum = couponNum+1;
					 }
				}
				Integer activeDateNum = Integer.parseInt(lateCouponEntity.getValidDays());
				sms = String.format(sms, new Object[]{wayBillNo,couponNum,spread});
				psc = createPendingSendCoupon(waybill,psc);
				psc.setCouponAmount(couponAmount.toString());
				psc.setActiveDateNum(activeDateNum);
				psc.setCartageFee(resourceTransportFee);
				psc.setOrdinaryFee(lrfTransportFee);
				psc.setCustomerCode(customerCode);
				psc.setPhone(phone);
				psc.setSpread(spread); 
				psc.setSmsContent(sms);
				psc.setIsSend(FossConstants.YES);
				psc.setNotSendReason(notSendReason);
				psc.setJobId(queryJobId);
				//保存数据  供定时任务自动调用
				laterSpreadDao.save(psc);
				LOGGER.info("处理运单号:"+wayBillNo+"晚到补差价结束");
			} catch (Exception e) {
				//处理异常
				LOGGER.error("晚到补差价错误信息:"+e.getMessage(), e);
				psc.setJobId(queryJobId);
				psc.setId(UUIDUtils.getUUID());
				psc.setWaybillNo(wayBillNo);
				psc.setIsSend(FossConstants.NO);
				//创建时间
				psc.setCreateDate(new Date());
				String error = ExceptionUtils.getFullStackTrace(e);
				if(error.length() > NUMBER_490){
					psc.setNotSendReason("异常："+error.substring(0, NUMBER_490));
				}else{
					psc.setNotSendReason("异常："+error);
				}
				laterSpreadDao.save(psc);
			}
		}
	}
	
	private LaterSpreadEntity createPendingSendCoupon(WaybillEntity waybillEntity,LaterSpreadEntity pendingSendCoupon ) {
		//创建待处理返券实体类
		pendingSendCoupon.setId(UUIDUtils.getUUID());
		//运单号
		pendingSendCoupon.setWaybillNo(waybillEntity.getWaybillNo());
		//开单时间
		pendingSendCoupon.setBillTime(waybillEntity.getBillTime());
		//创建时间
		pendingSendCoupon.setCreateDate(new Date());
		//修改时间
		pendingSendCoupon.setModifyDate(new Date());
		//失败原因
		pendingSendCoupon.setFailReason("");
		pendingSendCoupon.setProductCode(waybillEntity.getProductCode());
		//开单金额
		pendingSendCoupon.setBillAmount(waybillEntity.getTotalFee().subtract(waybillEntity.getCodAmount()));
		
		return pendingSendCoupon;
		
	}
	/**
	 * 
	* @Description: 计算差价
	* @author hbhk 
	* @date 2015-6-10 上午9:00:05 
	  @param waybill
	  @return
	 */
	private BigDecimal getSpread(WaybillEntity waybill) {
			BigDecimal lrfTransportFee = new BigDecimal(0);
			GuiQueryBillCalculateDto billCalculateDto = getQueryParamCollection(waybill, WaybillConstants.LRF_FLIGHT);
			List<GuiResultBillCalculateDto>  grbcList = waybillManagerService.queryGuiBillPrice(billCalculateDto);
			if(grbcList!=null && !grbcList.isEmpty()){
				for (GuiResultBillCalculateDto grbc : grbcList) {
					//获取运费
					String transportFeeType = grbc.getPriceEntryCode();
					if(StringUtils.isNotEmpty(transportFeeType)){
						if(PriceEntityConstants.PRICING_CODE_FRT.equals(transportFeeType)){
							if(grbc.getCaculateFee()!=null){
								lrfTransportFee = grbc.getCaculateFee();
							}
							break;
						}
					}
				}
			}
			return lrfTransportFee;
	}
	/**
	 * 
	* @Description: 封装计算费用的查询条件
	* @author hbhk 
	* @date 2015-6-9 下午4:16:50 
	  @param bean
	  @param transportType
	  @return
	 */
	public  GuiQueryBillCalculateDto getQueryParamCollection(WaybillEntity bean,String transportType) {
		GuiQueryBillCalculateDto queryDto = new GuiQueryBillCalculateDto();
		//zxy 20140509 MANA-1253 start 新增
		if(bean.getFreightMethod() != null ){
			queryDto.setCombBillTypeCode(bean.getFreightMethod());
		}
		//zxy 20140509 MANA-1253 end 新增
		queryDto.setOriginalOrgCode(bean.getReceiveOrgCode());// 出发部门CODE
		queryDto.setDestinationOrgCode(bean.getCustomerPickupOrgCode());// 到达部门CODE
		//产品CODE
		if(StringUtils.isNotEmpty(transportType)){
			queryDto.setProductCode(transportType);
		}else{
			queryDto.setProductCode(bean.getProductCode());
		}
		// 货物类型CODE
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(bean.getProductCode())) {
			queryDto.setGoodsCode(bean.getGoodsTypeCode());// 货物类型CODE
		}
		queryDto.setReceiveDate(bean.getBillTime());// 更改单设置为当前时间
		// 是否接货
		queryDto.setIsReceiveGoods(bean.getPickupToDoor());
		queryDto.setWeight(bean.getGoodsWeightTotal());// 重量
		EffectiveDto effectiveDto=  waybillManagerService.searchPreSelfPickupTime(bean.getReceiveOrgCode(),
				bean.getCustomerPickupOrgCode(), bean.getProductCode(), bean.getPreDepartureTime(), bean.getBillTime());
		if(effectiveDto != null){
			queryDto.setLongOrShort(effectiveDto.getLongOrShort());// 长途 还是短途
		}
		queryDto.setVolume(bean.getGoodsVolumeTotal());// 体积
		if (bean.getFlightNumberType() == null) {
			queryDto.setFlightShift(null);// 航班号
		} else {
			queryDto.setFlightShift(bean.getFlightNumberType());// 航班号
		}

		queryDto.setChannelCode(bean.getOrderChannel());//设置CRM渠道
		queryDto.setCurrencyCdoe(bean.getCurrencyCode());// 币种
		queryDto.setCustomerCode(bean.getDeliveryCustomerCode());// 发货客户编码
		queryDto.setKilom(bean.getKilometer());//设置公里数
		List<GuiQueryBillCalculateSubDto> priceEntities =new ArrayList<GuiQueryBillCalculateSubDto>();
		GuiQueryBillCalculateSubDto guiQueryBillCalculateSubDto= new GuiQueryBillCalculateSubDto();
		guiQueryBillCalculateSubDto.setPriceEntityCode(PriceEntityConstants.PRICING_CODE_FRT);
		priceEntities.add(guiQueryBillCalculateSubDto);
		queryDto.setPriceEntities(priceEntities);
		//送货费
		List<GuiQueryBillCalculateSubDto> deliveryFees = getDeliveryFeeCollection(bean);
		if (deliveryFees != null && !deliveryFees.isEmpty()) {
			priceEntities.addAll(deliveryFees);
		}
		queryDto.setPriceEntities(priceEntities);
		//最终配载部门(计算偏线中转费时用得到)
		queryDto.setLastOrgCode(bean.getLastLoadOrgCode());
		queryDto.setBillTime(bean.getBillTime());
		return queryDto;
	}

	/**
	 * 获取送货费参数
	 * @author WangQianJin
	 * @date 2013-6-17 下午9:34:43
	 */
	private  List<GuiQueryBillCalculateSubDto> getDeliveryFeeCollection(WaybillEntity bean) {

		List<GuiQueryBillCalculateSubDto> queryBillCacilateValueAddDto = new ArrayList<GuiQueryBillCalculateSubDto>();
		
		//提货方式编码
		String code = bean.getReceiveMethod();
		// 送货不上楼
		if (WaybillConstants.DELIVER_NOUP.equals(code) || WaybillConstants.DELIVER_NOUP_AIR.equals(code)) {
			queryBillCacilateValueAddDto.add(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_SH, null, null));
			// 超远派送费
			if (bean.getKilometer() != null) {
				queryBillCacilateValueAddDto.add(getVeryFarQueryParam(bean, PriceEntityConstants.PRICING_CODE_CY, BigDecimal.ZERO, null));
			}
		} else if (WaybillConstants.DELIVER_UP.equals(code)// 送货上楼
				|| WaybillConstants.DELIVER_UP_AIR.equals(code)) {
			queryBillCacilateValueAddDto.add(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_SH, null, null));
			queryBillCacilateValueAddDto.add(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_SHSL, null, null));
			// 超远派送费
			if (bean.getKilometer() != null) {
				queryBillCacilateValueAddDto.add(getVeryFarQueryParam(bean, PriceEntityConstants.PRICING_CODE_CY, BigDecimal.ZERO, null));
			}
		} else if (WaybillConstants.DELIVER_STORAGE.equals(code)// 送货进仓
				|| WaybillConstants.DELIVER_INGA_AIR.equals(code)) {
			queryBillCacilateValueAddDto.add(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_SH, null, null));
			queryBillCacilateValueAddDto.add(getQueryParam(bean, PriceEntityConstants.PRICING_CODE_QT, null, PriceEntityConstants.PRICING_CODE_SHJC));
			// 超远派送费
			if (bean.getKilometer() != null) {
				queryBillCacilateValueAddDto.add(getVeryFarQueryParam(bean, PriceEntityConstants.PRICING_CODE_CY, BigDecimal.ZERO, null));
			}
		}
		return queryBillCacilateValueAddDto;
	}
	/**
	 * 获取超远派送费查询参数
	 * @author WangQianJin
	 * @date 2013-6-17 下午9:37:46
	 */
	private  GuiQueryBillCalculateSubDto getVeryFarQueryParam(WaybillEntity bean, String valueAddType, BigDecimal cost, String subType) {
		GuiQueryBillCalculateSubDto queryDto = new GuiQueryBillCalculateSubDto();
		queryDto.setOriginnalCost(cost);// 原始费用
		queryDto.setSubType(subType);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setPriceEntityCode(valueAddType);// 计价条目CODE
		return queryDto;
	}

	/**
	 * 获取查询参数
	 * @author WangQianJin
	 * @date 2013-6-17 下午9:37:15
	 */
	private  GuiQueryBillCalculateSubDto getQueryParam(WaybillEntity bean, String valueAddType, BigDecimal cost, String subType) {
		GuiQueryBillCalculateSubDto queryDto = new GuiQueryBillCalculateSubDto();
		queryDto.setOriginnalCost(cost);// 原始费用
		queryDto.setSubType(subType);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setPriceEntityCode(valueAddType);// 计价条目CODE
		return queryDto;
	}
	
	
	@Override
	public void autoSendSMSAndGetCoupon() {
		//查询出晚到运单
		//调用CRM短息接口
		//根据CRM返回优惠券与客户编码绑定
		//生成短息内容
		LaterSpreadDto vo = new LaterSpreadDto();
		vo.setResultNum(queryNum);
		while(vo.getResultNum() == queryNum){
			String jobId = UUIDUtils.getUUID();
			//更新一定数量的JobId
			vo = laterSpreadDao.updateLaterSpreadForJobTopNum(jobId,queryNum);	
			//根据JobId查询待处理信息--状态为N的待返券信息
			List<LaterSpreadEntity> psList = laterSpreadDao.queryLaterSpreadByJobID(jobId);
			if (psList!=null && psList.size() > 0) {	
				LOGGER.info("本次处理晚到补差价信息的条数:"+psList.size());
				for (int i = 0; i < psList.size(); i++) {
					//调用crm发送短信并获取优惠券编码
					sendSMSAndGetCoupon(psList.get(i));
				}            
			}else{
				LOGGER.info("本次没有晚到补差价信息");
			}
		}
	}
	/**
	 * 
	* @Description: 调用CRM的返券接口，删除已返券的返券信息--添加一条已删除的返券信息至日志表
	* @author hbhk 
	* @date 2015-6-9 上午11:19:35 
	  @param psc
	 */
	private void sendSMSAndGetCoupon(LaterSpreadEntity psc) {
		try{
			LOGGER.info("处理晚到运单号开始:"+psc.getWaybillNo());
			//手机号
			String phone = psc.getPhone();
			if(StringUtils.isEmpty(phone)){
				return;
			}
			//短信内容
			String sms = psc.getSmsContent();
			int  activeDateNum = psc.getActiveDateNum();
			String couponAmount = psc.getCouponAmount();
			if(StringUtils.isEmpty(couponAmount)){
				return;
			}
			String[] couponAmountArr = couponAmount.split(",");
			LOGGER.info("优惠券面额:"+couponAmount);
			//面额
			List<String> couponAmountList = new ArrayList<String>();
			for (String ca : couponAmountArr) {
				couponAmountList.add(ca);
			}
			//运单号
			String waybillNo = psc.getWaybillNo();
			//检查是否已经处理
			CustomerCouponEntity entity = new CustomerCouponEntity();
			entity.setWaybillNo(waybillNo);
			List<CustomerCouponEntity> list = customerCouponService.queryCustomerCouponList(entity, 0, NumberConstants.NUMBER_10);
			if(CollectionUtils.isNotEmpty(list)){
				LOGGER.info("运单号:"+waybillNo+"已经生成了优惠券信息");
				return;
			}
			//调用crm发送短息和客户编码与优惠券编码对应关系
			//构建参数
			SendCouponRequest couponRequest = new SendCouponRequest();
			couponRequest.setRequest("FOSS");
			//运单号
			couponRequest.setSourceWBNumber(waybillNo);
			couponRequest.setSendtelPhone(phone);
			couponRequest.setSmsContent(sms);
			//开始时间
			Date  currentDate = new Date();
			//延后2分钟
			Long begintime = DateUtils.addMinutes(currentDate, NumberConstants.NUMBER_5).getTime();
			couponRequest.setBegintime(begintime);
			//加上有效天数
			Date endDate = DateUtils.addDays(DateUtils.addMinutes(currentDate, NumberConstants.NUMBER_5), activeDateNum);
			Long endtime = endDate.getTime();
			couponRequest.setEndtime(endtime);
			couponRequest.setDiscountType(TIMEOUT_SPREAD_TYPE);
			couponRequest.setUseCouponValue(couponAmountList);
			couponRequest.setCostType("FRT");
			couponRequest.setCostMode("1");
			couponRequest.setValue("11");
			couponRequest.setDescribe("晚到补差价优惠券生成和发送短信");
			ObjectMapper objectMapper = JSONUtils.obtainObjectMapper();
			String requestJson = objectMapper.writeValueAsString(couponRequest);
			LOGGER.info("请求参数:"+requestJson);
			String responseJson =  getWebClient().accept(MediaType.APPLICATION_JSON)
					.type(MediaType.APPLICATION_XML).post(requestJson,String.class);
			//String responseJson = (String) response.getEntity();
			LOGGER.info("返回信息:"+responseJson);
			SendCouponResponse  couponResponse = objectMapper.readValue(responseJson, SendCouponResponse.class);
			String ifSuccess =couponResponse.getIfSuccess();
			if(FossConstants.NO.equals(ifSuccess)){
				throw new BusinessException(couponResponse.getErrorMsg());
			}
			Map<String, String> couponCodes = couponResponse.getCouponCodes();
			if(couponCodes == null || couponCodes.isEmpty()){
				throw new BusinessException("生成优惠券为空");
			}
			Set<String> coupons = couponCodes.keySet();
			LOGGER.info("优惠券返回条数:"+coupons.size());
			for (String coupon : coupons) {
				CustomerCouponEntity cc = new CustomerCouponEntity();
				cc.setCouponCode(coupon);
				cc.setCustomerCode(psc.getCustomerCode());
				cc.setPhone(phone);
				cc.setUsed(FossConstants.YES);
				cc.setCreateDate(new Date());
				cc.setId(UUIDUtils.getUUID());
				//有效时间
				cc.setActiveDate(endDate);
				cc.setWaybillNo(waybillNo);
				//面额
				cc.setAmount(new BigDecimal(couponCodes.get(coupon)));
				customerCouponService.insert(cc);
			}
			//S 标示发送成功
			psc.setIsSend("S");
			psc.setModifyDate(new Date());
			laterSpreadDao.executeSendSMSFail(psc);
			LOGGER.info("处理晚到运单号结束:"+psc.getWaybillNo());
		}catch(Exception e){
			LOGGER.error("发送短信失败",e);
			String error = ExceptionUtils.getFullStackTrace(e);
			if(error.length() > NUMBER_490){
				psc.setFailReason("异常："+error.substring(0, NUMBER_490));
			}else{
				psc.setFailReason("异常："+error);
			}
			//发送失败之后后面还需要发
			psc.setJobId(queryJobId);
			psc.setIsSend(FossConstants.YES);
			psc.setModifyDate(new Date());
			//发送短信失败--->记录失败原因
			laterSpreadDao.executeSendSMSFail(psc);
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<CustomerCouponEntity> getCustomerCoupon(String customerCode) {
		//可能需要获取优惠券对面的面额
		CustomerCouponEntity entity = new CustomerCouponEntity();
		entity.setCustomerCode(customerCode);
		entity.setUsed(FossConstants.YES);
		return customerCouponService.queryCustomerCouponList(entity, 0, Integer.MAX_VALUE);
	}

	public IPendingSendCouponService getPendingSendCouponService() {
		return pendingSendCouponService;
	}

	public void setPendingSendCouponService(
			IPendingSendCouponService pendingSendCouponService) {
		this.pendingSendCouponService = pendingSendCouponService;
	}

	public IPendingSendCouponDao getPendingSendCouponDao() {
		return pendingSendCouponDao;
	}

	public void setPendingSendCouponDao(IPendingSendCouponDao pendingSendCouponDao) {
		this.pendingSendCouponDao = pendingSendCouponDao;
	}

	public ICustomerCouponService getCustomerCouponService() {
		return customerCouponService;
	}

	public void setCustomerCouponService(
			ICustomerCouponService customerCouponService) {
		this.customerCouponService = customerCouponService;
	}
	public IWaybillManagerService getWaybillManagerService() {
		return waybillManagerService;
	}
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}


}
