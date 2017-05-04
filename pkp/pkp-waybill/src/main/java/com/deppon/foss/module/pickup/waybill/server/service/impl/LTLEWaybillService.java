package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.context.SessionContext;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.server.web.message.IMessageBundle;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IBigcusDeliveryAddressDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IEmployeeDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILineDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBankService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusBargainService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerCircleRelationNewService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.INonfixedCusAccountService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BigcusDeliveryAddressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LimitedWarrantyItemsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NonfixedCusAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerCircleNewDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CusBargainVo;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderEntityDao;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;
import com.deppon.foss.module.pickup.pricing.api.server.service.IBillCaculateService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IEffectivePlanDetailService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IInempDiscountPlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.InempDiscountPlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.EffectivePlanDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateSubDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PriceDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.BillCaculateServiceException;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IInsurGoodsDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IOmsOrderDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IPdaAppInfoDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDisDtlPendingDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillProcessDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillProcessLogDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWoodenRequirementsPgDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICrmOrderJMSService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICrmOrderService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILTLEWaybillProcessService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILTLEWaybillService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabelPushProcessService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IOmsOrderService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IQueryCustomerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISysConfigService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillFreightRouteService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.OrgInfoDto;
import com.deppon.foss.module.pickup.waybill.server.service.IWaybillStockService;
import com.deppon.foss.module.pickup.waybill.server.utils.CommonUtils;
import com.deppon.foss.module.pickup.waybill.server.utils.StringHandlerUtil;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.OmsOrderEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PdaAppInfoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillCHDtlPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillChargeDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPaymentEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPictureEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillProcessEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirementsEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirementsPgEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.AmountInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponWaybillInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmPaymentTypeEnum;
import com.deppon.foss.module.pickup.waybill.shared.dto.EffectiveDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LTLEWaybillChangeWeightDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LTLEWaybillChangeWeightQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WoodenRequirePdaDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.BaseInfoInvokeException;
import com.deppon.foss.module.pickup.waybill.shared.exception.HandleTransportPathException;
import com.deppon.foss.module.pickup.waybill.shared.exception.PdaInterfaceException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillOrderHandleException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillRfcChangeException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillStoreException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillSubmitException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.module.pickup.waybill.shared.vo.DeliverChargeEntity;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillPickupInfoDto;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TransportPathEntity;
import com.deppon.foss.util.NumberUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 零担电子面单激活服务类
 *
 * @author 325220-foss-liuhui
 * @date 2016年5月10日
 */
public class LTLEWaybillService implements ILTLEWaybillService {
	private static final double ONEPOINTFOUR= 1.4;
	
	private static final double TWOPOINTFIVE= 2.5;
	//zero point zero one
	private static final double ZPZO= 0.01;
	
	private static final int ZERO =0;
	
	private static final int TWO =2;
	
	private static final int THREE =3;
	
	private static final int FOUR =4;
	
	private static final int FORTY_TWO=42;
	
	private static final int EIGHT = 8;

	private static final int ElEVEN = 11;
	
	private static final int FIFTEEN = 15;
	
	private static final int FIFTY =50;
	
	private static final int SIXTY_FIVE =65;
	
	private static final int ONE_HUNDRED_THIRTY =130;
	
	private static final int ONE_HUNDRED_SEVENTY =170;
	
	private static final int FIVE_HUNDRED=500;
	
	private static final int ONE_HUNDRED=100;
	
	private static final int TWO_HUNDRED=200;
	
	private static final int ONE_HUNDRED_FIFTY=150;
	
	private static final int THREE_HUNDRED_FIFTY=350;
	
	//thirteen thirty-seven
	private static final int TTS =1337;
	/**
	 * 超重货操作服务费
	 */
	private static final String PRICING_CODE_CZHCZFWF="CZHCZFWF";
	
	private static final String PRICING_CODE_CZHCZFWF_SDTJ="CZHCZFWF_SDTJ";
	
	/**
	 * 代理过关费
	 */
	private static final String PRICING_CODE_DLGGF="DLGGF";
	
	private static final String PRICING_CODE_DGDLGGF="DGDLGGF";
	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LTLEWaybillService.class);
	/**
	 * 业务锁定超时自动释放时间:15秒
	 */
	private static final int LOCK_TIMEOUT = 0;
	/**
	 * 待处理运单DAO
	 */
	private IWaybillPendingDao waybillPendingDao;
	/**
	 * 运单DAO
	 */
	private IWaybillDao waybillDao;
	/**
	 * 走货路线DAO
	 */
	private ILineDao lineDao;
	
	/**
	 * 实际承运DAO
	 */
	private IActualFreightService actualFreightService;
	/**
	 * 运单服务
	 */
	private IWaybillManagerService waybillManagerService;
	/**
	 * 限保物品
	 */
	private IInsurGoodsDao pkpinsurGoodsDao;
	/**
	 * 待处理运单费用折扣dao
	 */
    IWaybillDisDtlPendingDao waybillDisDtlPendingDao;
    /**
	 * 组织服务
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 营业部服务
	 */
	private ISaleDepartmentService saleDepartmentService;
	/**
	 * 货签DAO
	 */
	private ILabeledGoodDao labeledGoodDao;
	/**
	 * 系统配置服务层接口
	 */
	private ISysConfigService pkpsysConfigService;
	/**
	 * 系统参数配置项服务
	 */
	private IConfigurationParamsService configurationParamsService;
	/**
	 * CRM订单服务
	 */
	private ICrmOrderService crmOrderService;
    /**
	 * 价格计算服务
	 */
	private IBillCaculateService billCaculateService;
	/**
	 * 内部员工折扣方案
	 */
	private IInempDiscountPlanService inempDiscountPlanService;
	/**
	 * 查询会员、客户信息接口
	 */
	private IQueryCustomerService queryCustomerService;
	/**
	 * 人员接口
	 */
	private IEmployeeService employeeService;
	/**
	 * 业务互斥锁服务  提供业务互斥锁服务接口
	 */
	private IBusinessLockService businessLockService;
	/**
	 * 零担电子面单激活处理DAO
	 */
	@Resource
	private IWaybillProcessDao waybillProcessDao;
	@Resource
	private IOmsOrderService omsOrderService;
	@Resource
	private IPdaAppInfoDao pdaAppInfoDao;
	@Resource
	private ILTLEWaybillProcessService ltleWaybillProcessService;

	/**
	 * 零担电子面单日志DAO
	 */
	private IWaybillProcessLogDao waybillProcessLogDao;
// 2016年5月19日11:30:48
	/**
	 * GUI查询走货路径相关服务接口
	 */
	IWaybillFreightRouteService waybillFreightRouteService;
	/**
	 * 快递运单Service
	 */
	IWaybillExpressService waybillExpressService;	
	/** 
	 * ProductService 
	 */
	IProductService productService;
	/**
	 * 约车订单Dao
	 */
	private IDispatchOrderEntityDao dispatchOrderEntityDao;
	/**
	 * 运单库存服务
	 */
	private IWaybillStockService waybillStockService;
	/**
	 * 国际化信息
	 */
	private IMessageBundle messageBundle;
	/**
	 * 计价
	 */
	private IEffectivePlanDetailService effectivePlanDetailService;
	private ICalculateTransportPathService calculateTransportPathService;
	
	private ICrmOrderJMSService crmOrderJMSService;
	private IOmsOrderDao omsOrderDao;
	private ILabelPushProcessService labelPushProcessService;
	/**
	 * 散客、临客银行帐号服务
	 */
	private INonfixedCusAccountService nonfixedCusAccountService;
	/**
	 * 银行服务 提供与银行相关的服务接口
	 */
	private IBankService bankService;
	/**
	 * 客户服务
	 */
	private ICustomerService customerService;
	/**
	 * 客户合同信息Service接口实现
	 */
	private ICusBargainService cusBargainService;
	private IWoodenRequirementsPgDao woodenRequirementsPgDao;
	private IEmployeeDao employeeDao;
	
	private ILTLEWaybillProcessService lTLEWaybillProcessService;
	//派送地址库DTO,查询超远派送费使用
	private IBigcusDeliveryAddressDao bigcusDeliveryAddressDao;
	
	/**
     * 客户圈service
     */
    private ICustomerCircleRelationNewService customerCircleRelationNewService;

    public void setCustomerCircleRelationNewService(ICustomerCircleRelationNewService customerCircleRelationNewService) {
        this.customerCircleRelationNewService = customerCircleRelationNewService;
    }
	
	/**
	 * int 5
	 */
	private static final int FIVE=5;
	
	/**
	 * int 1
	 */
	private static final int ONE=1;
	/**
	 * 系统异常
	 */
	private final String SYS_ERROR_FAILURE ="foss.pkp.waybill.lTLEwaybillService.exception.sysErrorFailure";
	
	/**
	 * 根据运单job记录批量激活运单
	 * 
	 * @author 325220-foss-liuhui
	 * @param waybillNoList 运单job记录List
	 */
	@Override
	public void batchActiveLTLEWaybillByWaybillJobs(List<WaybillProcessEntity> waybillProcessEntityList) {
		if (CollectionUtils.isEmpty(waybillProcessEntityList)) {
			return;
		}
		LOGGER.info("零担电子面单激活处理|开始");
		for (WaybillProcessEntity waybillProcessEntity : waybillProcessEntityList) {
			String currentWaybillNo = null;
			// 查询待补录表 1
			WaybillPendingEntity waybillPendingEntity = null;
			//订单表信息 
			OmsOrderEntity omsOrderEntity = null;
			//扫描表
			PdaAppInfoEntity pdaAppInfoEntity=null;
			// 查询运单表 2
			WaybillEntity waybillEntityTemp =null;
			// 查询实际承运信息表 3
			ActualFreightEntity  actualFreightEntity=null;
			//打木架信息
			WoodenRequirementsEntity woodenRequirementsEntity=null;
			//业务异常
			BusinessException  businessException=null;
			//系统异常
			Exception exception=null;
			try {
				//查询
				if (null == waybillProcessEntity ) {
//					LOGGER.info("传入的线程激活对象为空！");
					throw new WaybillValidateException("传入的线程激活对象为空！");
				}
				// 取得运单号
				currentWaybillNo = waybillProcessEntity.getWaybillNo();
				LOGGER.info("获取到的运单号："+currentWaybillNo);
				// 查扫描表
				/**1、是否有重量体积、2是否已扫描**/
				pdaAppInfoEntity = pdaAppInfoDao.queryPdaAppInfoByWaybillNO(currentWaybillNo);
				//校验扫描表
				checkUpPdaAppInfoEntity(currentWaybillNo, pdaAppInfoEntity);
				//总体积
				BigDecimal goodsVolumeTotal = pdaAppInfoEntity.getGoodsVolumeTotal();
				//总重量
				BigDecimal goodsWeightTotal = pdaAppInfoEntity.getGoodsWeightTotal();
				//总件数
				int goodsQtyTotal = pdaAppInfoEntity.getGoodsQtyTotal();
				// 查询运单表
				waybillEntityTemp = waybillDao.queryWaybillByNo(currentWaybillNo);
				//校验运单表
				checkUpWaybillEntity(currentWaybillNo, pdaAppInfoEntity,
						waybillEntityTemp, goodsVolumeTotal, goodsWeightTotal,
						goodsQtyTotal);
				// 查询待补录表
				waybillPendingEntity = waybillPendingDao.queryPendingByNo(currentWaybillNo);
				//校验待补录表
				checkUpWaybillPending(currentWaybillNo, waybillPendingEntity,
						pdaAppInfoEntity, goodsVolumeTotal, goodsWeightTotal,
						goodsQtyTotal);
				// 查询实际承运信息表
				actualFreightEntity = actualFreightService.queryByWaybillNo(currentWaybillNo);
				//校验实际承运表
				checkUpActualFreight(currentWaybillNo, pdaAppInfoEntity,
						actualFreightEntity, goodsVolumeTotal,
						goodsWeightTotal, goodsQtyTotal);
				/**查找订单表信息，没有直接coutinue 有的话用于异常的记录**/
				//根据运单号查找订单表信息
				omsOrderEntity = omsOrderService.queryOmsOrderByWaybillNo(currentWaybillNo);
				//校验订单表信息
				omsOrderEntity = checkUpOmsOrder(currentWaybillNo,
						waybillPendingEntity, omsOrderEntity);
				// 如果已开单，不作任何处理
				isPendingActive(currentWaybillNo, waybillPendingEntity,
						waybillEntityTemp);
				//查询包装信息
				WoodenRequirementsPgEntity woodenEntity = woodenRequirementsPgDao.queryByWayBillNo(currentWaybillNo);
				//处理包装相关信息
				woodenRequirementsEntity = setWoodenEntityInfo(
						waybillPendingEntity, woodenRequirementsEntity,
						goodsWeightTotal, woodenEntity);
				//激活操作
				activeOperation(waybillProcessEntity,currentWaybillNo,pdaAppInfoEntity,waybillEntityTemp
						,waybillPendingEntity,actualFreightEntity,omsOrderEntity,woodenRequirementsEntity);
			} catch (BusinessException e) {
				businessException=e;
				LOGGER.error("执行批量激活发生业务异常");
				/** 更新Job记录为执行失败，次数直接置为5（业务异常不再尝试激活） **/
				updateWaybillProcessEntity(waybillProcessEntity,WaybillConstants.LTL_EWAYBILL_ACTIVE_FAILURE,FIVE,FossConstants.NO);
			} catch (Exception ee) {
				exception=ee;
				LOGGER.error("执行批量激活发生系统异常");
				//执行次数
				updateWaybillProcessEntity(waybillProcessEntity,WaybillConstants.LTL_EWAYBILL_ACTIVE_FAILURE,waybillProcessEntity.getProcessCount()+ONE,FossConstants.NO);
			}finally{
				//最终进行消息推送
				String waybillStatus=null;
				String logType=WaybillConstants.LTLEWAYBILL_ACTIVATE_LOG; //日志类型，激活日志
				StringBuilder message=new StringBuilder();
				Exception e=null;
				String errorCode=null;
				if(null==businessException&&null==exception){
					//运单状态为已开单
					waybillStatus=WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_ACTIVE;
				}else if(null!=businessException){
					e=businessException;
					//获取errorCode
					errorCode=classifyException(businessException);
					waybillStatus=WaybillConstants.WAYBILL_PICTURE_TYPE_RETURN;
					Object[] errorArguments = businessException.getErrorArguments();
					ObjectMapper om=new ObjectMapper();
					String newErrorArguments=null;
					try {
						if(null!=errorArguments&&errorArguments.length>0){
							//某些异常会将异常信息放入ErrorArguments中,这边使用ObjectMapper将ErrorArguments中的对象序列化显示出来
							newErrorArguments=om.writeValueAsString(errorArguments);
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					LOGGER.error("执行批量激活发生业务异常:["+"ErrorArguments:"+newErrorArguments+"ErrorCode:"+businessException.getErrorCode()+"Message:"+e.getMessage()+"]");
					/** 业务级别的异常，记录错误日志后，不再尝试激活    message用于日志的记录*/
					message.append("执行批量激活发生业务异常:["+"ErrorArguments:"+newErrorArguments+"||ErrorCode:"+businessException.getErrorCode()+"||Message:"+e.getMessage()+"]");
					//当捕捉到大件上楼异常时候，将PdaAppInfo的大件上楼强制设置为N
					updateForIsBigUp(currentWaybillNo, pdaAppInfoEntity,businessException);
				}else if(null!=exception){
					e=exception;
					errorCode=SYS_ERROR_FAILURE;//系统异常
					waybillStatus=WaybillConstants.WAYBILL_PICTURE_TYPE_RETURN;
					/** 系统级别的异常，记录错误日志后，需要再次尝试激活 */
					message.append("执行批量激活发生系统异常:"+exception.getMessage());
					//获取异常堆栈信息
					String excepMessage = ExceptionUtils.getFullStackTrace(exception);
					if (StringUtil.isNotBlank(excepMessage)) {
						message = message.append(excepMessage);
					}
					LOGGER.error("执行批量激活发生系统异常"+message.toString());
				}
				/**更新订单表信息**/
				updateOmsOrderEntity(omsOrderEntity,errorCode,waybillStatus);
				sendingMessageToOms(errorCode,message.toString(),waybillProcessEntity,waybillPendingEntity,pdaAppInfoEntity,waybillStatus,logType,e);
			}
		}
		LOGGER.info("零担电子面单激活处理|结束");
	}


	/**
	 * 处理包装信息
	 * @param waybillPendingEntity
	 * @param woodenRequirementsEntity
	 * @param goodsWeightTotal
	 * @param woodenEntity
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private WoodenRequirementsEntity setWoodenEntityInfo(
			WaybillPendingEntity waybillPendingEntity,
			WoodenRequirementsEntity woodenRequirementsEntity,
			BigDecimal goodsWeightTotal, WoodenRequirementsPgEntity woodenEntity)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		if(null!=woodenEntity){
			BigDecimal onePointFour = new BigDecimal(ONEPOINTFOUR).setScale(1, BigDecimal.ROUND_HALF_UP);//1.4
			// 打木架体积
			BigDecimal newStandGoodsVolume = (woodenEntity.getStandGoodsVolume()==null?new BigDecimal(0):woodenEntity.getStandGoodsVolume()).multiply(onePointFour);
			BigDecimal oldStandGoodsVolume = woodenEntity.getStandGoodsVolume()==null?new BigDecimal(0):woodenEntity.getStandGoodsVolume();
			woodenEntity.setStandGoodsVolume(newStandGoodsVolume.setScale(NumberConstants.NUMERAL_THREE, BigDecimal.ROUND_HALF_UP));//四舍五入，取小数点后三位
			// 打木箱体积
			BigDecimal newBoxGoodsVolume = (woodenEntity.getBoxGoodsVolume()==null?new BigDecimal(0):woodenEntity.getBoxGoodsVolume()).multiply(onePointFour);
			BigDecimal oldBoxGoodsVolume = woodenEntity.getBoxGoodsVolume()==null?new BigDecimal(0):woodenEntity.getBoxGoodsVolume();
			woodenEntity.setBoxGoodsVolume(newBoxGoodsVolume.setScale(NumberConstants.NUMERAL_THREE, BigDecimal.ROUND_HALF_UP));//四舍五入，取小数点后三位
			//包装体积
			BigDecimal packGoodsVolume = newStandGoodsVolume.add(newBoxGoodsVolume);
			BigDecimal oldPackGoodsVolume = oldStandGoodsVolume.add(oldBoxGoodsVolume);
			//为包装公式11*11*11*3+11*11*11*2
			String noPackGoodsSize = woodenEntity.getNoPackGoodsSize();
			//未包装体积
			BigDecimal noPackGoodsVolume = new BigDecimal(0);
			if(StringUtils.isNotBlank(noPackGoodsSize)){
				if(noPackGoodsSize.contains("+")){
					String[] split = StringUtils.split(noPackGoodsSize, "+");
					for (String string : split) {
						String[] nums = StringUtils.split(string, "*");
						BigDecimal length=new BigDecimal(nums[ZERO]);
						BigDecimal breadth=new BigDecimal(nums[ONE]);
						BigDecimal high=new BigDecimal(nums[TWO]);
						BigDecimal number=new BigDecimal(nums[THREE]);
						BigDecimal multiply = length.multiply(breadth).multiply(high).multiply(number);
						noPackGoodsVolume=noPackGoodsVolume.add(multiply);
					}
				}else{
					String[] nums = StringUtils.split(noPackGoodsSize, "*");
					BigDecimal length=new BigDecimal(nums[ZERO]);
					BigDecimal breadth=new BigDecimal(nums[ONE]);
					BigDecimal high=new BigDecimal(nums[TWO]);
					BigDecimal number=new BigDecimal(nums[THREE]);
					BigDecimal multiply = length.multiply(breadth).multiply(high).multiply(number);
					noPackGoodsVolume=noPackGoodsVolume.add(multiply);
				}
			}
			//总体积：
			BigDecimal totalGoodsVolume = packGoodsVolume.add(noPackGoodsVolume);
			
			//包装重量
			BigDecimal bhWeight = oldPackGoodsVolume.multiply(new BigDecimal(FORTY_TWO));
			
			//设置总重量：
			BigDecimal totalWeight= goodsWeightTotal.add(bhWeight);
			//设置计价重量
			waybillPendingEntity.setGoodsWeightTotal(totalWeight.setScale(NumberConstants.NUMERAL_THREE, BigDecimal.ROUND_HALF_UP));
			//设置计价体积
			if(totalGoodsVolume.compareTo(new BigDecimal(ZERO))>0){
				//如果上面算下来的计价体积不大于0，则不设置这个体积信息
				waybillPendingEntity.setGoodsVolumeTotal(totalGoodsVolume.setScale(NumberConstants.NUMERAL_THREE, BigDecimal.ROUND_HALF_UP));
			}
			waybillPendingEntity.setDoPacking(FossConstants.YES);
			//获取打木架信息
			woodenRequirementsEntity=copyWoodenParameter(woodenEntity);
			//更新待补录表，是否打木架信息为Y
			WaybillPendingEntity waybillPending=new WaybillPendingEntity();
			waybillPending.setId(waybillPendingEntity.getId());
			waybillPending.setDoPacking(FossConstants.YES);
			waybillPendingDao.updateByPrimaryKeySelective(waybillPending);
		}
		return woodenRequirementsEntity;
	}
	
	
	
	/**
	 * 通过Pad扫描中的信息，判断是否需要进一步更新待补录表
	 * @param currentWaybillNo
	 * @param waybillPendingEntity
	 * @param waybillEntity
	 * @param originateOrgCode
	 */
	private void updateWaybillPendingByPadInfo(String currentWaybillNo,
			WaybillPendingEntity waybillPendingEntity,
			WaybillEntity waybillEntity, String originateOrgCode) {
		if(StringUtils.isNotBlank(originateOrgCode)){
			//若待补录表中的ReceiveOrgCode以及CreateOrgCode与pdaAppInfo中的OriginateOrgCode不同
			if(!originateOrgCode.equals(waybillPendingEntity.getReceiveOrgCode())){
				LOGGER.info("运单："+currentWaybillNo+"修改始发部门信息,同时更新Pending表中的信息");
				//设置收货部门
				waybillEntity.setReceiveOrgCode(originateOrgCode);
				//查找收货部门名称
				String receiveOrgName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(originateOrgCode);
				//设置收货部门名称
				waybillEntity.setReceiveOrgName(receiveOrgName);
				//修改待补录表中的数据
				waybillPendingEntity.setReceiveOrgCode(originateOrgCode);
				//更新待补录表
				WaybillPendingEntity waybillPending=new WaybillPendingEntity();
				waybillPending.setReceiveOrgCode(originateOrgCode);
				waybillPending.setId(waybillPendingEntity.getId());
				waybillPendingDao.updateByPrimaryKeySelective(waybillPending);
			}
		}
	}
	
	
	/**
	 * 当捕捉到大件上楼异常时候，将PdaAppInfo的大件上楼强制设置为N
	 * @param currentWaybillNo
	 * @param pdaAppInfoEntity
	 * @param businessException
	 */
	private void updateForIsBigUp(String currentWaybillNo,
			PdaAppInfoEntity pdaAppInfoEntity,
			BusinessException businessException) {
		if(pdaAppInfoEntity!=null&&omsOrderService.isBigUpException(businessException.getErrorCode())){
			//修改PdaAppInfo中的isBigUp信息
			LOGGER.info("运单:"+currentWaybillNo+"更新PdaAppInfo中的isBigUp信息，原有的信息状态为："+pdaAppInfoEntity.getIsBigUp());
			PdaAppInfoEntity upai = new PdaAppInfoEntity();
			upai.setWaybillNo(currentWaybillNo);
			upai.setIsBigUp(WaybillConstants.NO);
			pdaAppInfoDao.updateSelectiveByWaybillNo(upai);
		}
	}

	private void updateWaybillProcessEntity(
			WaybillProcessEntity waybillProcessEntity,String operateResult,int processCount,String active) {
		waybillProcessEntity.setModifyTime(new Date());
		waybillProcessEntity.setOperateResult(operateResult);//直接标示失败
		waybillProcessEntity.setProcessCount((short) processCount);
		waybillProcessEntity.setActive(active);
		waybillProcessDao.updateWaybillProcess(waybillProcessEntity,UUIDUtils.getUUID(),WaybillConstants.YES);
		LOGGER.info("更新Job表记录完成 jobID:"+waybillProcessEntity.getJobId()+",运单号："+waybillProcessEntity.getWaybillNo());
	}
	
	/**
	 * 更新omsOrder表
	 * @param omsOrderEntity
	 * @param FailReason
	 * @param waybillStatus
	 */
	private void updateOmsOrderEntity(OmsOrderEntity omsOrderEntity,String failReason,String waybillStatus) {
		if(null!=omsOrderEntity){
			omsOrderEntity.setWaybillStatus(waybillStatus);
			omsOrderEntity.setFailReason(failReason);//业务异常
			omsOrderService.updateOmsOrderByPrimaryKey(omsOrderEntity);
			LOGGER.info("完成订单表更新,运单号："+omsOrderEntity.getWaybillNo()+"，订单号："+omsOrderEntity.getOrderNo());
		}
	}
	
	/**
	 * 校验是否已开单
	 * @param currentWaybillNo
	 * @param waybillPendingEntity
	 * @param waybillEntityTemp
	 */
	private void isPendingActive(String currentWaybillNo,
			WaybillPendingEntity waybillPendingEntity,
			WaybillEntity waybillEntityTemp) {
		if (WaybillConstants.WAYBILL_STATUS_PENDING_ACTIVE.equals(waybillEntityTemp.getPendingType())
				|| WaybillConstants.WAYBILL_STATUS_PC_ACTIVE.equals(waybillEntityTemp.getPendingType())) {
			LOGGER.info("运单:"+currentWaybillNo+"已开单！");
			throw new WaybillValidateException("运单:"+currentWaybillNo+"已开单！");
		}
		if (StringUtil.isNotBlank(waybillPendingEntity.getOrderNo())) {
			// 根据订单号查询运单基本信息
			WaybillEntity waybillEntityTemp2 = waybillDao.queryWaybillByOrderNo(waybillPendingEntity.getOrderNo());
			if (null != waybillEntityTemp2 && (WaybillConstants.WAYBILL_STATUS_PENDING_ACTIVE
					.equals(waybillEntityTemp2.getPendingType())
					|| WaybillConstants.WAYBILL_STATUS_PC_ACTIVE.equals(waybillEntityTemp2.getPendingType()))) {
				// 该订单号已开单，不做任何处理
				LOGGER.info("运单:"+currentWaybillNo+"的订单号"+waybillPendingEntity.getOrderNo()+"已开单！");
				throw new WaybillValidateException("运单:"+currentWaybillNo+"的订单号"+waybillPendingEntity.getOrderNo()+"已开单！");
			}
		}
	}
	/**
	 * 校验订单表信息
	 * @param currentWaybillNo
	 * @param waybillPendingEntity
	 * @param omsOrderEntity
	 * @return
	 */
	private OmsOrderEntity checkUpOmsOrder(String currentWaybillNo,
			WaybillPendingEntity waybillPendingEntity,
			OmsOrderEntity omsOrderEntity) {
		if(null==omsOrderEntity){
			LOGGER.info("根据运单号查询订单表信息为空："+currentWaybillNo);
			//通过订单号查找订单表信息
			if(StringUtil.isNotBlank(waybillPendingEntity.getOrderNo())){
				omsOrderEntity = omsOrderService.queryOmsOrderByOrderNo(waybillPendingEntity.getOrderNo());
				if(null==omsOrderEntity){
					LOGGER.info("根据订单号查询订单表信息为空！运单号："+currentWaybillNo+"，订单号："+waybillPendingEntity.getOrderNo());
					throw new WaybillValidateException("根据订单号查询订单表信息为空！运单号："+currentWaybillNo+"，订单号："+waybillPendingEntity.getOrderNo());
				}
			}else{
				LOGGER.info("订单号为空！");
				throw new WaybillValidateException("订单号为空！");
			}
		}
		return omsOrderEntity;
	}
	
	/**
	 * 校验实际承运表
	 * @param currentWaybillNo
	 * @param pdaAppInfoEntity
	 * @param actualFreightEntity
	 * @param goodsVolumeTotal
	 * @param goodsWeightTotal
	 * @param goodsQtyTotal
	 */
	private void checkUpActualFreight(String currentWaybillNo,
			PdaAppInfoEntity pdaAppInfoEntity,
			ActualFreightEntity actualFreightEntity,
			BigDecimal goodsVolumeTotal, BigDecimal goodsWeightTotal,
			int goodsQtyTotal) {
		if (null == actualFreightEntity) {
			LOGGER.info("实际承运信息为空，运单号："+currentWaybillNo);
			throw new WaybillValidateException("实际承运信息为空，运单号："+currentWaybillNo);
		}
		if(!WaybillConstants.LTLEWAYBILL.equals(actualFreightEntity.getWaybillType())){
			//若不是零担电子运单标示,进行更新操作
			actualFreightEntity.setWaybillType(WaybillConstants.LTLEWAYBILL);
			//更新实际承运表
			ActualFreightEntity actualFreight=new ActualFreightEntity();
			actualFreight.setId(actualFreightEntity.getId());
			actualFreight.setWaybillType(WaybillConstants.LTLEWAYBILL);
			actualFreightService.updateActualById(actualFreight);
		}
		//设置实际承运信息表---体积、重量、件数
		actualFreightEntity.setVolume(goodsVolumeTotal);
		actualFreightEntity.setWeight(goodsWeightTotal);
		actualFreightEntity.setGoodsQty(goodsQtyTotal);
		if(FossConstants.YES.equals(pdaAppInfoEntity.getIsBigUp())){
			//如果是大件上楼 实际承运表  提货方式为大件上楼
			actualFreightEntity.setActualDeliverType(WaybillConstants.LARGE_DELIVER_UP);
		}
	}
	/**
	 * 校验待补录表
	 * @param currentWaybillNo
	 * @param waybillPendingEntity
	 * @param pdaAppInfoEntity
	 * @param goodsVolumeTotal
	 * @param goodsWeightTotal
	 * @param goodsQtyTotal
	 */
	private void checkUpWaybillPending(String currentWaybillNo,
			WaybillPendingEntity waybillPendingEntity,
			PdaAppInfoEntity pdaAppInfoEntity, BigDecimal goodsVolumeTotal,
			BigDecimal goodsWeightTotal, int goodsQtyTotal) {
		if (null == waybillPendingEntity) {
			LOGGER.info("待补录表信息为空，运单号："+currentWaybillNo);
			throw new WaybillValidateException("待补录表信息为空，运单号："+currentWaybillNo);
		}
		//设置运单表---体积、重量、件数
		waybillPendingEntity.setGoodsWeightTotal(goodsWeightTotal);
		waybillPendingEntity.setGoodsVolumeTotal(goodsVolumeTotal);
		waybillPendingEntity.setGoodsQtyTotal(goodsQtyTotal);
		//新增app扫描上传的包装信息
		if (pdaAppInfoEntity.getPackInfo() != null) {
			waybillPendingEntity.setGoodsPackage(pdaAppInfoEntity.getPackInfo());
			waybillPendingEntity.setOtherPackage(pdaAppInfoEntity.getPackInfo());
		}
		if(FossConstants.YES.equals(pdaAppInfoEntity.getIsBigUp())){
			//如果是大件上楼 待补录表 提货方式为大件上楼
			waybillPendingEntity.setReceiveMethod(WaybillConstants.LARGE_DELIVER_UP);
		}
	}
	
	/**
	 * 校验运单表
	 * @param currentWaybillNo
	 * @param pdaAppInfoEntity
	 * @param waybillEntityTemp
	 * @param goodsVolumeTotal
	 * @param goodsWeightTotal
	 * @param goodsQtyTotal
	 */
	private void checkUpWaybillEntity(String currentWaybillNo,
			PdaAppInfoEntity pdaAppInfoEntity, WaybillEntity waybillEntityTemp,
			BigDecimal goodsVolumeTotal, BigDecimal goodsWeightTotal,
			int goodsQtyTotal) {
		if (null == waybillEntityTemp) {
			LOGGER.info("运单表信息为空，运单号："+currentWaybillNo);
			throw new WaybillValidateException("运单表信息为空，运单号："+currentWaybillNo);
		}
		//设置运单表---体积、重量、件数
		waybillEntityTemp.setGoodsVolumeTotal(goodsVolumeTotal);
		waybillEntityTemp.setGoodsWeightTotal(goodsWeightTotal);
		waybillEntityTemp.setGoodsQtyTotal(goodsQtyTotal);
		//新增app扫描上传的包装信息
		if (pdaAppInfoEntity.getPackInfo() != null) {
			waybillEntityTemp.setGoodsPackage(pdaAppInfoEntity.getPackInfo());
			waybillEntityTemp.setOtherPackage(pdaAppInfoEntity.getPackInfo());
			waybillDao.updateWaybill(waybillEntityTemp);
		}
		if(FossConstants.YES.equals(pdaAppInfoEntity.getIsBigUp())){
			//如果是大件上楼 运单表 提货方式为大件上楼
			waybillEntityTemp.setReceiveMethod(WaybillConstants.LARGE_DELIVER_UP);
		}
	}

	/**
	 * 校验扫描表
	 * @param currentWaybillNo
	 * @param pdaAppInfoEntity
	 */
	private void checkUpPdaAppInfoEntity(String currentWaybillNo,
			PdaAppInfoEntity pdaAppInfoEntity) {
		if(null == pdaAppInfoEntity){
			//扫描表未找到数据
			LOGGER.info("未找到扫描表信息，运单号："+currentWaybillNo);
			throw new WaybillValidateException("未找到扫描表信息，运单号："+currentWaybillNo);
		}else{
			if(null == pdaAppInfoEntity.getGoodsVolumeTotal()||null == pdaAppInfoEntity.getGoodsWeightTotal()||null==pdaAppInfoEntity.getGoodsQtyTotal()
					||pdaAppInfoEntity.getGoodsVolumeTotal().compareTo(new BigDecimal(WaybillConstants.ZERO))==Integer.valueOf(WaybillConstants.ZERO)
					||pdaAppInfoEntity.getGoodsWeightTotal().compareTo(new BigDecimal(WaybillConstants.ZERO))==Integer.valueOf(WaybillConstants.ZERO)
					||pdaAppInfoEntity.getGoodsQtyTotal().compareTo(Integer.valueOf(WaybillConstants.ZERO))==Integer.valueOf(WaybillConstants.ZERO)
					||WaybillConstants.LTLEWAYBILL_NOT_SCAN.equals(pdaAppInfoEntity.getScan())){
				//没有重量体积或者未扫描或者或者总件数信息为空
				LOGGER.info("没有重量体积或无总件数或者未扫描，运单号："+currentWaybillNo+";此运单扫描状态："+pdaAppInfoEntity.getScan());
				throw new WaybillValidateException("没有重量体积或无总件数或者未扫描，运单号："+currentWaybillNo+";此运单扫描状态："+pdaAppInfoEntity.getScan());
			}
		}
	}
	
	
	/**
	 * 执行激活操作
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	private void activeOperation(WaybillProcessEntity waybillProcessEntity,String currentWaybillNo,PdaAppInfoEntity pdaAppInfoEntity,WaybillEntity waybillEntityTemp
			,WaybillPendingEntity waybillPendingEntity,ActualFreightEntity actualFreightEntity,OmsOrderEntity omsOrderEntity,WoodenRequirementsEntity woodenRequirementsEntity) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		// 封装计价条件、查询出价格、封装运单DTO
		WaybillDto waybillDto = new WaybillDto(); // 最终提交的DTO
		// 组装运单基本信息
		WaybillEntity waybillEntity = waybillEntity(waybillPendingEntity);
		//app有上传包装信息时以app上传为准
		if (pdaAppInfoEntity.getPackInfo() != null) {
			//设置包装信息
			waybillEntity.setGoodsPackage(pdaAppInfoEntity.getPackInfo());
			waybillEntity.setOtherPackage(pdaAppInfoEntity.getPackInfo());
		}else {
			//app没有上传时，走原有逻辑
			setWoodenRequirements(waybillEntity,woodenRequirementsEntity);
		}
		waybillEntity.setDriverCode(pdaAppInfoEntity.getDriverCode());//设置司机信息
		String originateOrgCode = pdaAppInfoEntity.getOriginateOrgCode();
		updateWaybillPendingByPadInfo(currentWaybillNo,waybillPendingEntity,waybillEntity,originateOrgCode);
		
		/**
         * 根据客户编码查询客户圈
         * @author wangfeng 311417
         * @date 2017/1/23
         */
        //实际发货客户
        String actualCustomer = waybillEntity.getDeliveryCustomerCode();
        //客户圈dto
        CustomerCircleNewDto circleDto = new CustomerCircleNewDto();
       //是否主客户
        String isMainCustomer="";
        //主客户编码
        String mainCustomer;
        if (StringUtils.isNotEmpty(waybillEntity.getDeliveryCustomerCode())  && waybillPendingEntity.getBillTime() != null) {
            circleDto = customerCircleRelationNewService.getByCustCode(waybillEntity.getDeliveryCustomerCode(), waybillPendingEntity.getBillTime(), "Y");
            if (circleDto != null
                    && circleDto.getCustomerCircleEntity() != null
                    && circleDto.getCustomerCircleEntity().getCustCode() != null
                    && circleDto.getCusBargainNewEntity() != null
                    && ("Y").equals(circleDto.getCustomerCircleEntity().getIsMainCust())) {
                isMainCustomer = circleDto.getCustomerCircleEntity().getIsMainCust();
                mainCustomer = circleDto.getCustomerCircleEntity().getCustCode();
                waybillEntity.setDeliveryCustomerCode(mainCustomer);
                waybillPendingEntity.setDeliveryCustomerCode(mainCustomer);
                waybillEntity.setDeliveryBigCustomer(circleDto.getCustomerNewEntity().getIsElecBillBigCust());
                //主客户设置发票标记
                setInvoiceInfo(mainCustomer, waybillPendingEntity);
                //当是主客户时重新设置是否统一结算
                setDeliverySettleAndContactAndRemendingForMCust(circleDto, actualFreightEntity);
                //实际承运表设置客户分群
                actualFreightEntity.setFlabelleavemonth(circleDto.getCustomerNewEntity().getFlabelleavemonth());
            }

        }
		//校验月结相关信息
		verifyPaidMethod(waybillEntity,actualFreightEntity);
		waybillDto.setOldWaybillNo(waybillPendingEntity.getWaybillNo());
		waybillDto.setWoodenRequirementsEntity(woodenRequirementsEntity);
		waybillPendingEntity.setInternalDeliveryType(actualFreightEntity.getInternalDeliveryType());
		waybillPendingEntity.setEmployeeNo(actualFreightEntity.getEmployeeNo());
		// 基本验证
		validate(waybillPendingEntity, null, woodenRequirementsEntity, null);
		//设置收货方式
		waybillEntity.setReceiveMethod(waybillPendingEntity.getReceiveMethod());
		//防止空指针异常
		WoodenRequirePdaDto woodenPdaDto=new WoodenRequirePdaDto();
		// 运单明细集合
		@SuppressWarnings("rawtypes")
		Map waybillMap = getWaybillMap(waybillPendingEntity, waybillEntity, null, woodenRequirementsEntity, woodenPdaDto,waybillDto,omsOrderEntity);

		  // 开户银行信息以实际开单客户
        CusAccountEntity cusAccountEntity = queryCusAccountByAccount(actualCustomer, waybillEntity.getAccountCode());
		// 开户银行信息
		waybillDto.setOpenBank(cusAccountEntity);

		// 运单基本信息
		waybillDto.setWaybillEntity(waybillEntity);

		// 运单费用明细
		List<WaybillChargeDtlEntity> chargeList = (List<WaybillChargeDtlEntity>) waybillMap
				.get("chargeDtlList");
		waybillDto.setWaybillChargeDtlEntity(chargeList);

		// 运单折扣明细
		waybillDto.setWaybillDisDtlEntity((List<WaybillDisDtlEntity>) waybillMap.get("WaybillDisList"));

		// 运单付款明细
		waybillDto.setWaybillPaymentEntity((List<WaybillPaymentEntity>) waybillMap.get("paymentList"));

		// 优惠卷信息
		waybillDto.setCouponInfoDto((CouponInfoDto) waybillMap.get("couponInfoDto"));

		// 实际承运信息
		waybillDto.setActualFreightEntity((ActualFreightEntity) waybillMap.get("actualFreight"));

		// 添加统一结算信息到实际承运实体中调用结算接口使用
		setActualFreightEntityInfo(actualFreightEntity, waybillDto);
		
		// 货签信息
		waybillDto.setLabeledGoodEntities((List<LabeledGoodEntity>) waybillMap.get("labeledGoods"));
		
		// 获取理货员信息
		CurrentInfo currentInfo = getCurrentInfo(waybillEntity.getCreateUserCode(),waybillPendingEntity.getCreateOrgCode());
		waybillDto.setCurrentInfo(currentInfo);
		
		// 是否快递
		waybillDto.setIsExpress(FossConstants.NO);
		
		//结算校验
		statementValidate(waybillDto);
		
		/**
		 * 设置实际开单客户  @author wangfeng 311417 @Date 2017/1/10
		 */
        if(circleDto !=null && "Y".equals(isMainCustomer) && circleDto.getCustomerCircleEntity() !=null){
            waybillDto.getWaybillEntity().setDeliveryCustomerCode(actualCustomer);
            //设置实际开单是否统一结算
            waybillDto.getActualFreightEntity().setStartCentralizedSettlement(circleDto.getCustomerCircleEntity().getIsFocusPay());

        }
		
		/** 提交运单DTO **/
		submitWaybill(waybillDto);

		/** 业务监控 **/
		businessMonitor(waybillDto);

		updateWaybillProcessForSuccess(waybillProcessEntity);
		LOGGER.info("完成日志记录,运单:"+currentWaybillNo+"激活成功");
	}
	
	/**
	 * 结算相关校验
	 * @param waybillDto
	 */
	private void statementValidate(WaybillDto waybillDto) {
		//获取运单开单信息dto
		WaybillPickupInfoDto gainWaybillPickupInfo = gainWaybillPickupInfo(waybillDto);
		//验证新增运单合法性
		validAddedWaybillParam(gainWaybillPickupInfo);
	}
	/**
     * 异地调货当主客户时 设置是否统一结算 标杆合同  催款部门
     * @author wangfeng  311417 @date 2017/1/11
     */
    private void setDeliverySettleAndContactAndRemendingForMCust(CustomerCircleNewDto customerCircleDto, ActualFreightEntity actualFreightEntity) {
        //设置始发客户
        if (WaybillConstants.YES.equals(customerCircleDto.getCustomerCircleEntity().getIsFocusPay())) {
            actualFreightEntity.setStartCentralizedSettlement(WaybillConstants.YES);//是否统一结算
            actualFreightEntity.setStartContractOrgCode(customerCircleDto.getCusBargainNewEntity().getUnifiedCode());//标杆部门编号
            OrgAdministrativeInfoEntity administrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCode(customerCircleDto.getCusBargainNewEntity().getUnifiedCode());
            if (null != administrativeInfoEntity && StringUtils.isNotEmpty(administrativeInfoEntity.getName())) {
                actualFreightEntity.setStartContractOrgName(administrativeInfoEntity.getName());
            }
            actualFreightEntity.setStartReminderOrgCode(customerCircleDto.getCusBargainNewEntity().getHastenfunddeptCode());//催款部门
        } else {
            actualFreightEntity.setStartCentralizedSettlement(WaybillConstants.NO);
            actualFreightEntity.setStartContractOrgCode(null);
            actualFreightEntity.setStartContractOrgName(null);
            actualFreightEntity.setStartReminderOrgCode(null);
        }
    }
	
	/**
	 * 验证新增运单合法性
	 * @param gainWaybillPickupInfo
	 */
	private void validAddedWaybillParam(
			WaybillPickupInfoDto waybill) {
		// 校验运单内部金额是否正确。如果运单的（预付+到付-代收货款）不等于（公布价运费、接货费、送货费、包装费、代收货款手续费、保价费、其他费用之和时
		// 公布价运费已经包含了优惠费用，不再扣减
		BigDecimal totalTransFee = waybill.getTotalFee().subtract(
				waybill.getCodAmount());
		BigDecimal totalTransFee1 = waybill.getPrePayAmount()
				.add(waybill.getToPayAmount()).subtract(waybill.getCodAmount());
		BigDecimal totalTransFee2 = NumberUtils.sum(waybill.getTransportFee(),
				waybill.getPickupFee(), waybill.getDeliveryGoodsFee(),
				waybill.getPackageFee(), waybill.getCodFee(),
				waybill.getInsuranceFee(), waybill.getOtherFee());

		if (totalTransFee.compareTo(totalTransFee1) != 0
				|| totalTransFee.compareTo(totalTransFee2) != 0) {
			//明细之和不等于总运费，不能继续操作
			throw new WaybillValidateException(WaybillValidateException.ACCOUNTING_ERROR);
		}
		
		// 确定开单时代收货款金额的最大值和最小值限制
		if (waybill.getCodAmount() != null
				&& waybill.getCodAmount().compareTo(BigDecimal.ZERO) > 0) {
			
			//默认获取零担的代收货款上下限配置参数
			String codMaxParam = ConfigurationParamsConstants.PKP_COD_MAX_AMOUNT;
			String codMinParam = ConfigurationParamsConstants.PKP_COD_MIN_AMOUNT;
			
			String maxAmount = configurationParamsService
					.queryConfValueByCode(codMaxParam);
			String minAmount = configurationParamsService
					.queryConfValueByCode(codMinParam);

			if (waybill.getCodAmount().compareTo(
					NumberUtils.createBigDecimal(maxAmount)) > 0) {
				throw new WaybillValidateException("代收货款金额超过最大值：" + maxAmount
						+ "，不能继续操作！");
			}
			if (waybill.getCodAmount().compareTo(
					NumberUtils.createBigDecimal(minAmount)) < 0) {
				throw new WaybillValidateException("代收货款金额低于最小值：" + minAmount
						+ "，不能继续操作！");
			}
			//处理开代收款生成应付单前验证
			validateHandleCod(waybill);
		}
	}


	/**
	 * 处理开代收款生成应付单前验证
	 * 
	 * @author guxinhua
	 * @param
	 * @date 2013-1-29 下午8:03:03
	 * @return
	 */
	private void validateHandleCod(WaybillPickupInfoDto waybill) {
		// 对公对私标记，需要根据综合管理里面的属性进行处理--运单开单信息，把综合规定的编码“对公对私标记”转为结算规定的编码
		boolean bl = false;
		if (DictionaryValueConstants.CRM_ACCOUNT_NATURE_PRIVATE_ACCOUNT
				.equals(waybill.getPublicPrivateFlag())) {
			// 对私账户
			waybill.setPublicPrivateFlag(SettlementDictionaryConstants.COD__PUBLIC_PRIVATE_FLAG__RESIDENT);
			bl = true;
		} else if (DictionaryValueConstants.CRM_ACCOUNT_NATURE_PUBLIC_ACCOUNT
				.equals(waybill.getPublicPrivateFlag())) {
			// 对公账户
			waybill.setPublicPrivateFlag(SettlementDictionaryConstants.COD__PUBLIC_PRIVATE_FLAG__COMPANY);
			bl = true;
		}
		// 代收货款类型
		String refundType = waybill.getRefundType();
		if (StringUtils.isBlank(refundType)) {
			//当代收不为0的时候,代收货款类型（即日退、三日退、审核退）不能为空
			throw new WaybillValidateException(WaybillValidateException.REFUND_TYPE_IS_EMPTY);
		}
		// 代收货款应付单客户编码不能为空
		if (StringUtils.isEmpty(waybill.getDeliveryCustomerCode())) {
			//当代收不为0的时候,发货客户编码不能为空
			throw new WaybillValidateException(WaybillValidateException.DELIVERY_CUSTOMER_CODE_EMPTY);
		}
		// 银行账户
		if (SettlementDictionaryConstants.COD__COD_TYPE__RETURN_1_DAY
				.equals(refundType)
				|| SettlementDictionaryConstants.COD__COD_TYPE__RETURN_3_DAY
						.equals(refundType)) {
			if (!bl) {// 即日退和3日退需要校验账号的对公对私标记是否合法
				throw new WaybillValidateException("客户账号对公对私标记不合法！"
						+ waybill.getPublicPrivateFlag());
			}
			if (StringUtils.isBlank(waybill.getAccountName())) {
				//即日退、三日退类型代收货款:收款人不能为空
				throw new WaybillValidateException(WaybillValidateException.ACCOUNT_NAME_EMPTY);
			}
			if (StringUtils.isBlank(waybill.getAccountCode())) {
				//即日退、三日退类型代收货款:银行帐号不能为空
				throw new WaybillValidateException(WaybillValidateException.ACCOUNT_CODE_EMPTY);
			}
			if (StringUtils.isBlank(waybill.getBankHQCode())) {
				//即日退、三日退类型代收货款:开户行编码不能为空
				throw new WaybillValidateException(WaybillValidateException.BANK_HQ_CODE_EMPTY);
			}
			if (StringUtils.isBlank(waybill.getAccountBank())) {
				//即日退、三日退类型代收货款:开户行不能为空
				throw new WaybillValidateException(WaybillValidateException.ACCOUNT_BANK_EMPTY);
			}
			if (StringUtils.isBlank(waybill.getProvinceCode())
					|| StringUtils.isBlank(waybill.getProvinceName())) {
				//即日退、三日退类型代收货款:省不能为空
				throw new WaybillValidateException(WaybillValidateException.PROVINCE_CODE_EMPTY);
			}
			if (StringUtils.isBlank(waybill.getCityCode())
					|| StringUtils.isBlank(waybill.getCityName())) {
				//即日退、三日退类型代收货款:市不能为空
				throw new WaybillValidateException(WaybillValidateException.CITY_CODE_OR_NAME_EMPTY);
			}
			if (StringUtils.isBlank(waybill.getPublicPrivateFlag())) {
				//即日退、三日退类型代收货款,对公对私标志不能为空
				throw new WaybillValidateException(WaybillValidateException.PUBLIC_PRIVATE_FLAG_EMPTY);
			}
//			if (StringUtils.isBlank(waybill.getPayeePhone())) {
//				throw new SettlementException(headMsg + "手机号码不能为空");
//			}
			if (StringUtils.isBlank(waybill.getBankBranchCode())
					|| StringUtils.isBlank(waybill.getBankBranchName())) {
				//即日退、三日退类型代收货款,支行编码/名称不能为空
				throw new WaybillValidateException(WaybillValidateException.BANK_BRANCH_CODE_OR_NAME_EMPTY);
			}
			// 如果代收货款类型为"即日退"，则代收货款的退款账号类型不能为"对公帐号"
			if (SettlementDictionaryConstants.COD__COD_TYPE__RETURN_1_DAY
					.equals(refundType)
					&& (!StringUtils
							.equals(waybill.getPublicPrivateFlag(),
									SettlementDictionaryConstants.COD__PUBLIC_PRIVATE_FLAG__RESIDENT))) {
				//代收货款类型为即日退，则代收货款的退款账号类型不能为对公帐号 
				throw new WaybillValidateException(
						WaybillValidateException.REFUND_ACCOUNT_TYPES_CANNOT_ACCOUNT);
			}
			// 如果代收货款类型为"即日退"，则其开户银行必须在即日退所属银行范围内（即日退所属银行范围已经提交给综合管理做基础资料了）
//			if (SettlementDictionaryConstants.COD__COD_TYPE__RETURN_1_DAY
//					.equals(refundType)) {
//				BankEntity entity = new BankEntity();
//				// 银行编码
//				entity.setCode(waybill.getBankHQCode());
//				boolean bool = salesPayCODService
//						.checkBankIntraDayTypeByBankEntity(entity);
//				if (!bool) {
//					throw new SettlementException(
//							"如果代收货款类型为即日退，则其开户银行必须在即日退所属银行范围内 ");
//				}
//			}
		}
	}
	
	
	/**
	 * 获得代收货款银企帐号信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-28 下午5:22:49
	 */
	private WaybillPickupInfoDto gainWaybillPickupInfo(WaybillDto waybillDto) {
		// 运单基本信息
		WaybillEntity waybill = waybillDto.getWaybillEntity();
		// 开户银行信息
		CusAccountEntity openBank = waybillDto.getOpenBank();
		// 运单冗余信息
		ActualFreightEntity afhe = waybillDto.getActualFreightEntity();
		// 定义封闭对象
		WaybillPickupInfoDto dto = new WaybillPickupInfoDto();

		// 在这个地方设置值
		// 將是【否統一結算】【合同部門】【催款部門】傳遞給結算部門
		dto.setOrigUnifiedSettlement(afhe.getStartCentralizedSettlement());
		dto.setDestUnifiedSettlement(afhe.getArriveCentralizedSettlement());
		dto.setOrigContractUnifiedCode(afhe.getStartContractOrgCode());
		dto.setDestContractUnifiedCode(afhe.getArriveContractOrgCode());
		dto.setOrigUnifiedDuningCode(afhe.getStartReminderOrgCode());
		dto.setDestUnifiedDuningCode(afhe.getArriveReminderOrgCode());

		// 捕捉对象值拷贝可能出现的异常
		try {
			// 将waybill值拷贝到DTO变量中
			PropertyUtils.copyProperties(dto, waybill);
		} catch (Exception e) {
			// 添加异常日志
			LOGGER.error("Excepton", e);
			// 抛出异常信息
			throw new WaybillSubmitException(WaybillSubmitException.COPYWAYBILLINFO_FAIL,
					new Object[] { messageBundle.getMessage(e.getMessage()) });
		}

		// 出发部门
//		SaleDepartmentEntity leaveDept = saleDepartmentService
//				.querySaleDepartmentByCode(waybill.getReceiveOrgCode());
		// 最终配载部门
//		SaleDepartmentEntity arriveDept = saleDepartmentService
//				.querySaleDepartmentByCode(waybill.getLastLoadOrgCode());
		// 出发部门
		SaleDepartmentEntity leaveDept = saleDepartmentService
				.querySimpleSaleDepartmentByCode(waybill.getReceiveOrgCode());
		// 最终配载部门
		SaleDepartmentEntity arriveDept = saleDepartmentService
				.querySimpleSaleDepartmentByCode(waybill.getLastLoadOrgCode());
		
		// 若出发部门对象不为空则，设置出发部门名称
		if (leaveDept != null) {
			// 出发部门名称
			dto.setReceiveOrgName(leaveDept.getName());
		}

		// 若到达部门对象不为空则，设置到达部门名称
		if (arriveDept != null) {
			// 到达部门名称
			dto.setLastLoadOrgName(arriveDept.getName());
		}

		// 非空判断
		if (null != openBank) {
			// 开户行编码
			dto.setBankHQCode(openBank.getBankCode());
			// 开户行名称
			dto.setBankHQName(openBank.getOpeningBankName());
			// 省份编码
			dto.setProvinceCode(openBank.getProvCode());
			// 省份名
			dto.setProvinceName(openBank.getProvinceName());
			// 城市编码
			dto.setCityCode(openBank.getCityCode());
			// 城市名
			dto.setCityName(openBank.getCityName());
			// 支行编码（行号）
			dto.setBankBranchCode(openBank.getBranchBankCode());
			// 支行名称
			dto.setBankBranchName(openBank.getBranchBankName());
			// 对公对私标志
			dto.setPublicPrivateFlag(openBank.getAccountNature());
			// 收款人与发货人关系
			dto.setPayeeRelationship(openBank.getCustomer());
			// 收款人手机号码
			dto.setPayeePhone(openBank.getMobilePhone());
		}

		// 小件新加字段
		if (waybillDto.getWaybillExpressEntity() != null) {
			dto.setPosSerialNum(waybillDto.getWaybillExpressEntity().getPdaSerial());
			dto.setBatchNo(waybillDto.getWaybillExpressEntity().getBankTradeSerail());
		}

		dto.setInvoiceMark(afhe.getInvoice());
		/**
		 * Dmana-4437
		 * 
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-04-02下午19:39
		 */
		dto.setBatchNo(afhe.getTransactionSerialNumber());
		return dto;
	}
	
	/**
	 * 设置包装信息
	 * @param waybillEntity
	 * @param woodenRequirementsEntity
	 */
	private void setWoodenRequirements(WaybillEntity waybillEntity,
			WoodenRequirementsEntity woodenRequirementsEntity) {
		if(null!=woodenRequirementsEntity){
			StringBuilder sb=new StringBuilder();
			//打木箱货物件数
			Integer boxGoodsNum = woodenRequirementsEntity.getBoxGoodsNum();
			if(null!=boxGoodsNum&&boxGoodsNum>0){
				sb.append(boxGoodsNum+"木箱");
			}
			//打木架货物件数
			Integer standGoodsNum = woodenRequirementsEntity.getStandGoodsNum();
			if(null!=standGoodsNum&&standGoodsNum>0){
				sb.append(standGoodsNum+"木");
			}
			//打木托货物件数
			Integer salverGoodsNum = woodenRequirementsEntity.getSalverGoodsNum();
			if(null!=salverGoodsNum&&salverGoodsNum>0){
				sb.append(salverGoodsNum+"托");
			}
			//设置包装信息
			waybillEntity.setGoodsPackage(sb.toString());
			//设置包装其他信息
			waybillEntity.setOtherPackage(sb.toString());
		}
	}
	
	
	/**
	 * 校验支付方式为月结的相关信息
	 * @param waybillPendingEntity
	 */
	private void verifyPaidMethod(WaybillEntity waybillEntity,ActualFreightEntity actualFreightEntity){
		// 只有月结客户才能开月结
		if (WaybillConstants.MONTH_PAYMENT.equals(waybillEntity.getPaidMethod())) {
			if(StringUtils.isBlank(waybillEntity.getDeliveryCustomerCode())){
				//客户编码不能为空 
				throw new WaybillValidateException(WaybillValidateException.CUSTOMER_CODE_NULL);
			}
			CusBargainVo vo = new CusBargainVo();
			vo.setChargeType(WaybillConstants.MONTH_END);
			vo.setCustomerCode(waybillEntity.getDeliveryCustomerCode());//发货客户编码
			vo.setBillDate(waybillEntity.getBillTime());//业务时间
			vo.setBillOrgCode(waybillEntity.getReceiveOrgCode());//开单部门
			if (null == cusBargainService.queryCusBargainByVo(vo)) {
				//该客户编码与收货部门之间无归属或绑定关系，不允许开月结
				LOGGER.info("运单："+waybillEntity.getWaybillNo()+"的该客户编码与收货部门之间无归属或绑定关系，不允许开月结");
				throw new WaybillValidateException(WaybillValidateException.CAN_NOT_OPEN_MONTH_PAYMENT);
			}
			//付款方式为月结时，且始发是否统一结算字段为是，则校验发货人的合同部门编码且催款部门是否为空，为空时则不能选择月结，并提醒“发货人的合同部门编码且催款部门是为空，不能开月结”
			if(WaybillConstants.YES.equals(actualFreightEntity.getStartCentralizedSettlement())){
				//且始发是否统一结算字段为是
				if(StringUtils.isBlank(actualFreightEntity.getStartContractOrgCode())||StringUtils.isBlank(actualFreightEntity.getStartReminderOrgCode())){
					//校验发货人的合同部门编码且催款部门是否为空，为空时则不能选择月结 
					LOGGER.info("运单："+waybillEntity.getWaybillNo()+"发货人的合同部门编码且催款部门是为空，不能开月结");
					throw new WaybillValidateException(WaybillValidateException.START_CONTRACT_OR_REMINDER_ORGCODE_EMPTY);
				}
			}
		}
	}
	
	/**
	 * 添加统一结算信息到实际承运实体中调用结算接口使用
	 * @param actualFreightEntity
	 * @param waybillDto
	 */
	private void setActualFreightEntityInfo(
			ActualFreightEntity actualFreightEntity, WaybillDto waybillDto) {
		if (waybillDto.getActualFreightEntity() != null) {
			// 始发客户统一结算信息
			waybillDto.getActualFreightEntity()
					.setStartCentralizedSettlement(actualFreightEntity.getStartCentralizedSettlement());
			waybillDto.getActualFreightEntity()
					.setStartContractOrgCode(actualFreightEntity.getStartContractOrgCode());
			waybillDto.getActualFreightEntity()
					.setStartContractOrgName(actualFreightEntity.getStartContractOrgName());
			waybillDto.getActualFreightEntity()
					.setStartReminderOrgCode(actualFreightEntity.getStartReminderOrgCode());
			// 到达客户统一结算信息
			waybillDto.getActualFreightEntity()
					.setArriveCentralizedSettlement(actualFreightEntity.getArriveCentralizedSettlement());
			waybillDto.getActualFreightEntity()
					.setArriveContractOrgCode(actualFreightEntity.getArriveContractOrgCode());
			waybillDto.getActualFreightEntity()
					.setArriveContractOrgName(actualFreightEntity.getArriveContractOrgName());
			waybillDto.getActualFreightEntity()
					.setArriveReminderOrgCode(actualFreightEntity.getArriveReminderOrgCode());
		}
	}
	
	/**
	 * 激活成功后 更新线程表信息
	 * @param waybillProcessEntity
	 */
	private void updateWaybillProcessForSuccess(
			WaybillProcessEntity waybillProcessEntity) {
		/** 更新Job表记录：执行成功，执行次数+1 **/
		waybillProcessEntity.setModifyTime(new Date());
		waybillProcessEntity.setOperateResult(WaybillConstants.LTL_EWAYBILL_ACTIVE_SUCCESS);
		waybillProcessEntity.setProcessCount((short) (waybillProcessEntity.getProcessCount() + 1));
		waybillProcessDao.updateWaybillProcess(waybillProcessEntity,null,WaybillConstants.YES);
		LOGGER.info("更新Job表记录完成 jobID:"+waybillProcessEntity.getJobId()+",运单号："+waybillProcessEntity.getWaybillNo());
	}
	
	/**
	 * 推送消息至OMS
	 * @param error
	 */
	private void sendingMessageToOms(String errorCode,String message ,WaybillProcessEntity waybillProcessEntity,WaybillPendingEntity waybillPendingEntity,PdaAppInfoEntity pdaAppInfoEntity,String waybillStatus,String tpye,Exception e) {
		LOGGER.info("开始信息推送");
		if(null==waybillPendingEntity){
			//防止空指针异常
			waybillPendingEntity=new WaybillPendingEntity();
		}
		String driverCode = waybillPendingEntity.getDriverCode();//司机工号
		//推送消息(订单号,运单号,运单状态,司机工号,错误code,异常堆栈信息)
		crmOrderJMSService.pushOmsOrderInfoStatust(waybillProcessEntity.getOrderNo(), waybillProcessEntity.getWaybillNo(), tpye,pdaAppInfoEntity,waybillStatus, driverCode, errorCode,message+ExceptionUtils.getFullStackTrace(e));
		LOGGER.info("完成信息推送");
	}	
	
	/**
	 * 拷贝打木架信息
	 * @param woodenEntity
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private WoodenRequirementsEntity copyWoodenParameter(WoodenRequirementsPgEntity woodenEntity) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		WoodenRequirementsEntity dest=new WoodenRequirementsEntity();
		PropertyUtils.copyProperties(dest, woodenEntity);
		//如果代打木架要求为空，这边默认甚至为NONE
		if(StringUtils.isBlank(dest.getStandRequirement())){
			dest.setStandRequirement("NONE");
		}
		return dest;
	}


	/**
	 * 业务异常分类
	 * @param e
	 * @return
	 */
	private String classifyException(RuntimeException e) {
		/**
		 * 用于推送给OMS的异常code
		 */
		String error = null;
		//运单异常 信息校验异常
		if (e instanceof WaybillValidateException) {
			error = getWaybillValidateError(e);
		//运单更改异常
		}else if(e instanceof WaybillRfcChangeException){
			error = getWaybillRfcChangeError(e);
		//结算异常
		}else if(e instanceof SettlementException){
			error = getSettlementError(e);
		//中转线路处理异
		}else if(e instanceof HandleTransportPathException){
			error = getHandleTransportError(e);
		//订单处理异常
		}else if(e instanceof WaybillOrderHandleException){
			error = getWaybillOrderError(e);
		//运单提交异常
		}else if(e instanceof WaybillSubmitException){
			error = getWaybillSubmitError(e);
		//不合法参数异常
		}else if(e instanceof IllegalArgumentException){
			error = getIllegalArgumentError(e);
		//PDA接口异常
		}else if(e instanceof PdaInterfaceException){
			error = getPdaInterfaceError(e);
		//业务异常
		}else if(e instanceof BusinessException){
			error = getBusinessError(e);
		//计算总费用抛出的异常
		}else if(e instanceof BillCaculateServiceException){
			error = getBillCaculateError(e);
		//其他异常
		}else{
			LOGGER.error("其他异常："+e.getMessage(), e);
		}
		return error;
	}

	/**
	 * 计算总费用抛出的异常
	 * @param e
	 * @return
	 */
	private String getBillCaculateError(RuntimeException e) {
		String error;
		BillCaculateServiceException exception = (BillCaculateServiceException) e;
		//捕捉错误类型
		if(StringUtils.isNotEmpty(exception.getErrorCode())){
			LOGGER.error("计算总费用抛出的异常："+exception.getErrorCode(), e);
			error = exception.getErrorCode();
		}else{
			LOGGER.error("计算总费用抛出的异常："+e.getMessage(), e);
			error = WaybillValidateException.EWAYBILL_CALCULATE_ALL_FEE_UNKNOW_EXCEPTION;
		}
		return error;
	}

	/**
	 * 业务异常
	 * @param e
	 * @return
	 */
	private String getBusinessError(RuntimeException e) {
		String error;
		BusinessException exception = (BusinessException) e;
		//捕捉错误类型
		if(StringUtils.isNotEmpty(exception.getErrorCode())){
			LOGGER.error("业务异常："+exception.getErrorCode(), e);
			error = exception.getErrorCode();
		}else{
			LOGGER.error("业务异常："+e.getMessage(), e);
			error = WaybillValidateException.WAYBILL_SUBMIT_UNKOWN_EXCEPTION;
		}
		return error;
	}

	/**
	 * PDA接口异常
	 * @param e
	 * @return
	 */
	private String getPdaInterfaceError(RuntimeException e) {
		String error;
		PdaInterfaceException exception = (PdaInterfaceException) e;
		//捕捉错误类型
		if(StringUtils.isNotEmpty(exception.getErrorCode())){
			LOGGER.error("PDA接口异常："+exception.getErrorCode(), e);
			error = exception.getErrorCode();
		}else{
			LOGGER.error("PDA接口异常："+e.getMessage(), e);
			error = WaybillValidateException.PDA_INTERFACE_INPUT_EXCEPTION;
		}
		return error;
	}

	/**
	 * 不合法的参数异常
	 * @param e
	 * @return
	 */
	private String getIllegalArgumentError(RuntimeException e) {
		String error;
		IllegalArgumentException exception = (IllegalArgumentException) e;
		//捕捉错误类型
		if(StringUtils.isNotEmpty(exception.getMessage())){
			LOGGER.error("不合法的参数异常:"+e.getMessage(), e);
			error = exception.getMessage();
		}else{
			LOGGER.error("不合法的参数异常:"+e.getMessage(), e);
			error = WaybillValidateException.ILLEGAL_ARGUMENT_INPUT_EXCEPTION;
		}
		return error;
	}

	/**
	 * 运单提交异常
	 * @param e
	 * @return
	 */
	private String getWaybillSubmitError(RuntimeException e) {
		String error;
		WaybillSubmitException exception = (WaybillSubmitException) e;
		//捕捉错误类型
		if(StringUtils.isNotEmpty(exception.getErrorCode())){
			LOGGER.error("运单提交异常："+e.getMessage(), e);
			error = exception.getErrorCode();
		}else{
			LOGGER.error("运单提交异常："+e.getMessage(), e);
			error = WaybillValidateException.WAYBILL_SUBMIT_UNKOWN_EXCEPTION;
		}
		return error;
	}

	/**
	 * 订单处理异常
	 * @param e
	 * @return
	 */
	private String getWaybillOrderError(RuntimeException e) {
		String error;
		WaybillOrderHandleException exception = (WaybillOrderHandleException) e;
		//捕捉错误类型
		if(StringUtils.isNotEmpty(exception.getErrorCode())){
			LOGGER.error("订单处理异常："+e.getMessage(), e);
			error = exception.getErrorCode();
		}else{
			LOGGER.error("订单处理异常："+e.getMessage(), e);
			error = WaybillValidateException.WAYBILL_ORDER_HANDLE_VALIDATE_UNKOWN_EXCEPTION;
		}
		return error;
	}

	/**
	 * 中转线路处理异常
	 * @param e
	 * @return
	 */
	private String getHandleTransportError(RuntimeException e) {
		String error;
		HandleTransportPathException exception = (HandleTransportPathException) e;
		//捕捉错误类型
		if(StringUtils.isNotEmpty(exception.getErrorCode())){
			LOGGER.error("中转线路处理异常："+e.getMessage(), e);
			error = exception.getErrorCode();
		}else{
			LOGGER.error("中转线路处理异常："+e.getMessage(), e);
			error = WaybillValidateException.HANDLE_TRANSPORT_PATH_UNKOWN_EXCEPTION;
		}
		return error;
	}

	/**
	 * 结算异常
	 * @param e
	 * @return
	 */
	private String getSettlementError(RuntimeException e) {
		String error;
		SettlementException exception = (SettlementException) e;
		//捕捉错误类型
		if(StringUtils.isNotEmpty(exception.getErrorCode())){
			LOGGER.error("结算异常:"+e.getMessage(), e);
			error = exception.getErrorCode();
		}else{
			LOGGER.error("结算异常:"+e.getMessage(), e);
			error = WaybillValidateException.WAYBILL_SETTLE_VALIDATE_UNKOWN_EXCEPTION;
		}
		return error;
	}

	/**
	 * 运单更改异常
	 * @param e
	 * @return
	 */
	private String getWaybillRfcChangeError(RuntimeException e) {
		String error;
		WaybillRfcChangeException exception = (WaybillRfcChangeException) e;
		//捕捉错误类型
		if(StringUtils.isNotEmpty(exception.getErrorCode())){
			LOGGER.error("运单更改异常:"+e.getMessage(), e);
			error = exception.getErrorCode();
		}else{
			LOGGER.error("运单更改异常:"+e.getMessage(), e);
			error = WaybillValidateException.WAYBILLRFC_VALIDATE_UNKOWN_EXCEPTION;
		}
		return error;
	}

	/**
	 * 运单校验异常
	 * @param e
	 * @return
	 */
	private String getWaybillValidateError(RuntimeException e) {
		String error;
		WaybillValidateException exception = (WaybillValidateException) e;
		//捕捉错误类型
		if(StringUtils.isNotEmpty(exception.getErrorCode())){
			LOGGER.error("运单校验异常:"+e.getMessage(), e);
			error = exception.getErrorCode();
		}else{
			LOGGER.error("运单校验异常:"+e.getMessage(), e);
			error = WaybillValidateException.WAYBILL_VALIDATE_UNKOWN_EXCEPTION;
		}
		return error;
	}	
	
	/**
     * 运单明细集合
     * @author foss-PanGuoYang
     * @date 2014-10-08
     */
	@SuppressWarnings("rawtypes")
	private Map getWaybillMap(WaybillPendingEntity entity,
			                  WaybillEntity waybillEntity,
			                  List<WaybillCHDtlPendingEntity> chargePendList,
			                  WoodenRequirementsEntity woodEntity,
			                  WoodenRequirePdaDto woodenPdaDto,
			                  WaybillDto waybillDto,
			                  OmsOrderEntity omsOrderEntity){
		LOGGER.info("开始运单明细集合获取……");
		Map<String,Object> map = new HashMap<String, Object>();
		// 运单费用明细
		List<WaybillChargeDtlEntity> chargeDtlEntityList = new ArrayList<WaybillChargeDtlEntity>();
		//折扣明细
		List<WaybillDisDtlEntity> disDtlEntityList = new ArrayList<WaybillDisDtlEntity>();
		//付款明细
		List<WaybillPaymentEntity> paymentEntityList = new ArrayList<WaybillPaymentEntity>();
		//优惠券
		 CouponInfoDto couponInfoDto=null;
		 String remark =null;
		 GuiResultBillCalculateDto tdo = null;
		// 非内部带货，内部带货不产生任何与费用相关的数据
		if (!WaybillConstants.INNER_PICKUP.equals(entity.getReceiveMethod())) {
			//统一返回的计价值
			List<GuiResultBillCalculateDto> guiResultBillCalculateDtos=queryGuiBillPrice(entity,woodEntity,waybillDto);
			//实际收取的其他费用项
			List<String> actualOtherFee=new ArrayList<String>();
			//获取代收货款手续费
			GuiResultBillCalculateDto codCollection = getGuiResultBillCalculateDto(guiResultBillCalculateDtos, PriceEntityConstants.PRICING_CODE_HK, null);
			//费用明细添加代收费用
			setCodCollection(codCollection,entity,chargeDtlEntityList);
			
			//获取送货费
			List<DeliverChargeEntity> delivetList= getDeliverChargeEntity(guiResultBillCalculateDtos,entity,actualOtherFee);
			//费用明细添加送货费
			setDeliveryFeeCollection(delivetList,entity,chargeDtlEntityList);
			
			//获取接货费
			GuiResultBillCalculateDto dtoJH= getGuiResultBillCalculateDto(guiResultBillCalculateDtos, PriceEntityConstants.PRICING_CODE_JH, null);
			// 获取公布价运费
			GuiResultBillCalculateDto dto = getGuiResultBillCalculateDto(guiResultBillCalculateDtos, PriceEntityConstants.PRICING_CODE_FRT, null);
			//保存一份公布价运费
			tdo = dto;
			//费用明细添加接货费
			setProductPriceDtoCollection(dto,dtoJH,entity,chargeDtlEntityList,remark);
			//费用明细添加公布价费
			setTransportFee(dto,waybillEntity, entity,chargeDtlEntityList);
			
			//获取保价费
			GuiResultBillCalculateDto insuranceCollection = getGuiResultBillCalculateDto(guiResultBillCalculateDtos, PriceEntityConstants.PRICING_CODE_BF, null);
			
			//保费费率四舍五入后重新计算
			insuranceRateRoundUp(insuranceCollection);
			
			//费用明细添加保价费
			setInsurance(insuranceCollection,entity,chargeDtlEntityList);
			
			//获取打木架
			GuiResultBillCalculateDto packageCollectionFRAME=getGuiResultBillCalculateDto(guiResultBillCalculateDtos, PriceEntityConstants.PRICING_CODE_BZ, DictionaryValueConstants.PACKAGE_TYPE__FRAME);
			//费用明细添加打木架费
			setStandChargeCollection(packageCollectionFRAME,entity,woodEntity,chargeDtlEntityList);
			
			//获取打木箱
			GuiResultBillCalculateDto packageCollectionBOX=	 getGuiResultBillCalculateDto(guiResultBillCalculateDtos, PriceEntityConstants.PRICING_CODE_BZ, DictionaryValueConstants.PACKAGE_TYPE__BOX);
			//费用明细添加打木箱费
			setBoxChargeCollection(packageCollectionBOX,entity,woodEntity,chargeDtlEntityList);
			//获取打木托信息  DictionaryValueConstants.PACKAGE_TYPE__SALVER
			GuiResultBillCalculateDto packageCollectionSALVER=getGuiResultBillCalculateDto(guiResultBillCalculateDtos, PriceEntityConstants.PRICING_CODE_BZ, DictionaryValueConstants.PACKAGE_TYPE__SALVER);
			//设置打木托价格
			if(null!=packageCollectionSALVER){
				woodEntity.setSalverGoodsAmount(packageCollectionSALVER.getCaculateFee());
			}
			//包装费
			WaybillChargeDtlEntity packageCharge =setPackageCharge(packageCollectionFRAME,packageCollectionBOX,woodEntity,entity,chargeDtlEntityList);
			
			//其他费用
			List<GuiResultBillCalculateDto> otherCalculateDtos=otherCalculateDtos(guiResultBillCalculateDtos, PriceEntityConstants.PRICING_CODE_QT);
			
			BigDecimal otherFee=  otherFee(entity,chargePendList,chargeDtlEntityList,woodenPdaDto,otherCalculateDtos,disDtlEntityList,actualOtherFee,omsOrderEntity);
			
			//设置付款明细
			BigDecimal totalFee=setWaybillPaymentEntity(dto,
					                codCollection,
					                delivetList,
					                dtoJH,
					                insuranceCollection,
					                packageCharge,
					                otherFee,
					                entity,waybillEntity,
					                paymentEntityList,
					                couponInfoDto,
					                woodenPdaDto);
			
			//优惠券
			couponInfoDto=getCouponInfoDto(chargeDtlEntityList,totalFee,entity,disDtlEntityList,dto);
			
			if(null!=couponInfoDto.getRemark()){
				remark =couponInfoDto.getRemark();
			}
			//设置折扣明细
			setWaybillDisDtlEntity(waybillEntity,guiResultBillCalculateDtos,disDtlEntityList,actualOtherFee);
			
		    //如果优惠券不为空重新计算价格
			if(couponInfoDto!=null && couponInfoDto.getCouponFree()!=null 
					  && couponInfoDto.getCouponFree().compareTo(BigDecimal.ZERO)>0){
				setWaybillPaymentEntity(dto,
		                codCollection,
		                delivetList,
		                dtoJH,
		                insuranceCollection,
		                packageCharge,
		                otherFee,
		                entity,waybillEntity,
		                paymentEntityList,
		                couponInfoDto,
		                woodenPdaDto);
			}else{
				couponInfoDto=null;
			}
		}
		
		
		//实际承运信息
		ActualFreightEntity actuaf= getActualFreightEntity(waybillEntity,entity,chargeDtlEntityList,tdo);
		
		//"货签信息"实体类 
		List<LabeledGoodEntity>  labeledGoods= getLabeledGoodEntities(waybillEntity, woodenPdaDto);
		
		map.put("chargeDtlList", chargeDtlEntityList);
		map.put("WaybillDisList", disDtlEntityList);
		map.put("paymentList", paymentEntityList);
		map.put("couponInfoDto", couponInfoDto);
		map.put("actualFreight", actuaf);
		map.put("labeledGoods", labeledGoods);
		map.put("remark", remark);
		LOGGER.info("完成运单明细集合获取");
		return map;
	}
	
	
	/**
	 * //处理保费费率(进行四舍五入)
	 * @param insuranceCollection
	 */
	private void insuranceRateRoundUp(
			GuiResultBillCalculateDto insuranceCollection) {
		if(null!=insuranceCollection){
			List<GuiResultDiscountDto> discountPrograms = insuranceCollection.getDiscountPrograms();
			if(null!=discountPrograms&&discountPrograms.size()<=1
					&&null!=insuranceCollection.getActualFeeRate()
					&&null!=insuranceCollection.getDefaultBF()){
				//四舍五入,保留4位有效小数
				BigDecimal newActualFeeRate = insuranceCollection.getActualFeeRate().setScale(NumberConstants.NUMBER_4, BigDecimal.ROUND_HALF_UP);
				//重新计算保费
				BigDecimal caculateFee = insuranceCollection.getDefaultBF().multiply(newActualFeeRate).setScale(NumberConstants.NUMBER_3, BigDecimal.ROUND_HALF_UP);
				//重新计算之后的保费与最低费用相比较，如果小于最低费用，则取最低费用
				if(caculateFee.compareTo(insuranceCollection.getMinFee())<0){
					caculateFee=insuranceCollection.getMinFee();
				}
				//设置实际费率
				insuranceCollection.setActualFeeRate(newActualFeeRate);
				//设置保费
				insuranceCollection.setCaculateFee(caculateFee);
				//设置打折后的费用
				insuranceCollection.setDiscountFee(caculateFee);
				//设置轻货费率
				if(null!=insuranceCollection.getLightFeeRate()){
					insuranceCollection.setLightFeeRate(insuranceCollection.getLightFeeRate().setScale(NumberConstants.NUMBER_4, BigDecimal.ROUND_HALF_UP));
				}
				if(discountPrograms.size()>0&&null!=insuranceCollection.getFeeRate()){
					//保费的折扣不计入优惠费用项以及折扣明细中
					List<GuiResultDiscountDto> dp=new ArrayList<GuiResultDiscountDto>();
					insuranceCollection.setDiscountPrograms(dp);
				}
			}
		}
	}
	/**
	 * 计算价格
	 * @param entity
	 * @param woodEntity
	 */
	public List<GuiResultBillCalculateDto> queryGuiBillPrice(WaybillPendingEntity entity,WoodenRequirementsEntity woodEntity,WaybillDto waybillDto){
		
		//获取产品价格主参数
		GuiQueryBillCalculateDto productPriceDtoCollection = getQueryParamCollection(entity);
		productPriceDtoCollection.setWaybillNo(entity.getWaybillNo());
		List<GuiQueryBillCalculateSubDto> priceEntities = productPriceDtoCollection.getPriceEntities();

		if(woodEntity!=null){
			// 打木架/大木箱 计算信息收集
			LOGGER.info("打木架/打木箱计算信息收集");
			List<GuiQueryBillCalculateSubDto> yokeChargeCollections = getYokeChargeCollection(woodEntity);
			if (yokeChargeCollections != null && !yokeChargeCollections.isEmpty()) {
				priceEntities.addAll(yokeChargeCollections);//
			}
		}
		
		
		//获取保价费
		GuiQueryBillCalculateSubDto insuranceCollection = getInsuranceParam(entity);
		if (insuranceCollection != null) {
			LOGGER.info("保价费计算信息收集");
			priceEntities.add(insuranceCollection);//加入增值服务
		}

		//代收货款手续费
		GuiQueryBillCalculateSubDto codCollection = getCodCollection(entity);
		if (codCollection != null) {
			LOGGER.info("代收货款手续费计算信息收集");
			priceEntities.add(codCollection);//代收货款手续费
		}
		
		//送货费
		List<GuiQueryBillCalculateSubDto> deliveryFees = getDeliveryFeeCollection(entity);
		if (deliveryFees != null && !deliveryFees.isEmpty()) {
			LOGGER.info("送货费计算信息收集");
			priceEntities.addAll(deliveryFees);
		}

		//其他费用
		GuiQueryBillCalculateSubDto otherChargeDataCollection = getQueryOtherChargeParam();
		if (otherChargeDataCollection != null) {
			LOGGER.info("其他费用计算信息收集");
			priceEntities.add(otherChargeDataCollection);
		}

		productPriceDtoCollection.setPriceEntities(priceEntities);
		// 是否经济自提件
		productPriceDtoCollection.setEconomySince(entity.getIsEconomyGoods());
		
		//最终配载部门(计算偏线中转费时用得到)
		productPriceDtoCollection.setLastOrgCode(entity.getLastLoadOrgCode());
		
		WaybillDisDtlPendingEntity  disDtlPend =waybillDisDtlPendingDao.queryActiveInfoByNoAndType(entity.getWaybillNo());
		if(disDtlPend!=null && disDtlPend.getActiveCode()!=null){
			//营销活动DTO
			//=productPriceDtoCollection.setActiveDto(bean.getActiveDto());
			
			//是否计算市场营销折扣
			productPriceDtoCollection.setCalActiveDiscount(false);
			
			//封装市场营销活动校验条件
			settterActiveParamInfo(productPriceDtoCollection, entity,disDtlPend.getActiveCode());
		}
		productPriceDtoCollection.setBillTime(entity.getBillTime());
		//封装内部发货条件
				/**
				 * 根据条件查询当前月份的优惠总额
				 */
				BigDecimal amount = null;
				if(StringUtil.isNotBlank(entity.getEmployeeNo())) {
					amount = waybillManagerService.queryDiscountFeeByEmployeeNo(entity.getEmployeeNo(),entity.getBillTime());
					LOGGER.info("查询当前月份的优惠总额:"+amount);
				}
				productPriceDtoCollection.setInternalDeliveryType(entity.getInternalDeliveryType());
				
				productPriceDtoCollection.setEmployeeNo(entity.getEmployeeNo());
				productPriceDtoCollection.setAmount(amount);
				/**
				 * 封装梯度折扣
				 */
				if(StringUtil.isNotBlank(entity.getDeliveryCustomerCode())) {
					amount = waybillManagerService.queryTotalFeeByDelevyCode(entity.getDeliveryCustomerCode(),entity.getBillTime());
					productPriceDtoCollection.setTotalAmount(amount);
					LOGGER.info("根据当前时间和发货客货编码查询当前月份的发货总金额"+amount);
				}
				productPriceDtoCollection.setReceiveOrgCode(entity.getReceiveOrgCode());
		 //将产品价格主参数设置进waybillDto中;
		 waybillDto.setProductPriceDtoCollection(productPriceDtoCollection);
		//统一返回的计价值
		 return waybillManagerService.queryGuiBillPrice(productPriceDtoCollection);
	}
	
	/**
	 * 
	 * 获取产品价格查询参数
	 * 
	 * @author 076234-FOSS-PanGuoYang
	 * @date 2014-10-03 上午11:02:10
	 */
	public  GuiQueryBillCalculateDto  getQueryParamCollection(WaybillPendingEntity entity) {
		LOGGER.info("获取产品价格主参数……");
		GuiQueryBillCalculateDto queryDto = new GuiQueryBillCalculateDto();
		if(entity.getFreightMethod() != null )
			queryDto.setCombBillTypeCode(entity.getFreightMethod());
		queryDto.setOriginalOrgCode(entity.getReceiveOrgCode());// 出发部门CODE
		if(entity.getCustomerPickupOrgCode() != null){
			queryDto.setDestinationOrgCode(entity.getCustomerPickupOrgCode());// 到达部门CODE			
		}else{
			LOGGER.error("提货网点不能空");
			throw new PdaInterfaceException("提货网点不能空");
		}
		queryDto.setProductCode(entity.getProductCode());// 产品CODE
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(entity.getProductCode())) {
			queryDto.setGoodsCode(entity.getGoodsTypeCode());// 货物类型CODE
		}
		
		queryDto.setReceiveDate(entity.getBillTime());// 营业部收货日期(电子运单即生成待补录时间)
		// 是否接货
		if(entity.getPickupToDoor()!=null && FossConstants.ACTIVE.equals(entity.getPickupToDoor())){
			queryDto.setIsReceiveGoods(FossConstants.ACTIVE);// 是否接货
		}else{
			queryDto.setIsReceiveGoods(FossConstants.NO);// 是否接货
		}
		
		queryDto.setWeight(entity.getGoodsWeightTotal());// 重量
		/*EffectiveDto effectiveDto = waybillManagerService.searchPreSelfPickupTime(entity.getReceiveOrgCode(), entity.getLastLoadOrgCode(), entity.getProductCode(), entity.getPreDepartureTime(), new Date());
		if(effectiveDto!=null){
			queryDto.setLongOrShort(effectiveDto.getLongOrShort());// 长途 还是短途
		}*/
		queryDto.setLongOrShort(entity.getLongOrShort());// 长途 还是短途
		queryDto.setVolume(entity.getGoodsVolumeTotal());// 体积
		if (entity.getFlightNumberType() == null) {
			queryDto.setFlightShift(null);// 航班号
		} else {
			queryDto.setFlightShift(entity.getFlightNumberType());// 航班号
		}
		//保价费率
		queryDto.setFeeRate(entity.getInsuranceRate());
		queryDto.setChannelCode(entity.getOrderChannel());//设置CRM渠道
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		queryDto.setCustomerCode(entity.getDeliveryCustomerCode());// 发货客户编码
		queryDto.setKilom(entity.getKilometer());//设置公里数
		if(entity.getIsEconomyGoods()!=null && FossConstants.ACTIVE.equals(entity.getIsEconomyGoods())){
			queryDto.setEconomySince(FossConstants.ACTIVE);
		}else{
			queryDto.setEconomySince(FossConstants.INACTIVE);
		}
		//最终配载部门(计算偏线中转费时用得到)
		queryDto.setLastOrgCode(entity.getLastLoadOrgCode());
		List<GuiQueryBillCalculateSubDto> priceEntities =new ArrayList<GuiQueryBillCalculateSubDto>();
		GuiQueryBillCalculateSubDto guiQueryBillCalculateSubDto= new GuiQueryBillCalculateSubDto();
		guiQueryBillCalculateSubDto.setPriceEntityCode(PriceEntityConstants.PRICING_CODE_FRT);
		priceEntities.add(guiQueryBillCalculateSubDto);
		queryDto.setPriceEntities(priceEntities);
		//为了在后期计算保价做标识符，状态为true时，不走保费折扣计算---206860
		queryDto.setCalDiscount(false);
		return queryDto;
	}
	
	
	/**
	 * 获取取打木架费用
	 * @param guiQueryBillCalculateSubDtos
	 * @author 076234 PanGuoYang
	 * @date 2014-10-03 上午11:02:10
	 * @return
	 */
	private List<GuiQueryBillCalculateSubDto> getYokeChargeCollection(WoodenRequirementsEntity woodEntity){
			List<GuiQueryBillCalculateSubDto> guiQueryBillCalculateSubDtos = new ArrayList<GuiQueryBillCalculateSubDto>();
			// 获取木架费用
			GuiQueryBillCalculateSubDto standChargeCollection = getStandChargeCollection(woodEntity);
			if (standChargeCollection != null) {
				guiQueryBillCalculateSubDtos.add(standChargeCollection);
			}
			// 获取木箱费用
			GuiQueryBillCalculateSubDto boxChargeCollection = getBoxChargeCollection(woodEntity);
			if (boxChargeCollection != null) {
				guiQueryBillCalculateSubDtos.add(boxChargeCollection);

			}
			// 获取木托费用 zxy 20131118 ISSUE-4391
			GuiQueryBillCalculateSubDto salverChargeCollection = getSalverChargeCollection(woodEntity);
			if (salverChargeCollection != null) {
				guiQueryBillCalculateSubDtos.add(salverChargeCollection);
			}
			return guiQueryBillCalculateSubDtos;
	}
	
	/**
	 * 获取代打木架
	* @author 076234-FOSS-PanGuoYang
	 * @date 2014-10-03 上午11:02:10
	 */
	public  GuiQueryBillCalculateSubDto getStandChargeCollection(WoodenRequirementsEntity woodEntity) {

		if (woodEntity.getStandGoodsNum() != null && woodEntity.getStandGoodsNum() > 0) {
			return getQueryYokeParam(DictionaryValueConstants.PACKAGE_TYPE__FRAME, woodEntity.getStandGoodsVolume());
		} else {
			return null;
		}
	}
	
	/**
	 * 获取代打木托
	 * @author 076234-FOSS-PanGuoYang
	 * @date 2014-10-03 上午11:02:10
	 */
	private GuiQueryBillCalculateSubDto getSalverChargeCollection(
			WoodenRequirementsEntity woodEntity) {
		if (woodEntity.getSalverGoodsNum() != null && woodEntity.getSalverGoodsNum() > 0) {
			// 打木托
			return getQueryYokeParam(DictionaryValueConstants.PACKAGE_TYPE__SALVER, new BigDecimal(woodEntity.getSalverGoodsNum()));

		} else {
			return null;
		}
	}

	/**
	 * 获取代打木箱
	* @author 076234-FOSS-PanGuoYang
	 * @date 2014-10-03 上午11:02:10
	 */
	private GuiQueryBillCalculateSubDto getBoxChargeCollection(
			WoodenRequirementsEntity woodEntity) {
		if (woodEntity.getBoxGoodsNum() != null && woodEntity.getBoxGoodsNum() > 0) {
			// 打木箱
			return getQueryYokeParam(DictionaryValueConstants.PACKAGE_TYPE__BOX, woodEntity.getBoxGoodsVolume());
		} else {
			return null;
		}
	}
	
	

	/**
	 * 
	 * 获取查询参数
	* @author 076234-FOSS-PanGuoYang
	 * @date 2014-10-03 上午11:02:10
	 */
	private  GuiQueryBillCalculateSubDto getQueryYokeParam(String subType, BigDecimal volume) {
		GuiQueryBillCalculateSubDto queryDto = new GuiQueryBillCalculateSubDto();
		queryDto.setWoodenVolume(volume);//代打体积
		queryDto.setOriginnalCost(null);// 原始费用
		queryDto.setPriceEntityCode(PriceEntityConstants.PRICING_CODE_BZ);// 计价条目CODE
		queryDto.setSubType(subType);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		return queryDto;
	}
	
	
	/**
	 * 
	 * 获取查询参数
	 * @author 076234 PanGuoYang
	 * @date 2014-10-03 上午11:02:10
	 */
	private GuiQueryBillCalculateSubDto getInsuranceParam(WaybillPendingEntity pendingEntity) {
		GuiQueryBillCalculateSubDto queryDto = new GuiQueryBillCalculateSubDto();
		queryDto.setOriginnalCost(pendingEntity.getInsuranceAmount());// 原始费用
		LimitedWarrantyItemsEntity entity= pkpinsurGoodsDao.isInsurGoods(pendingEntity.getGoodsName());
		if (entity != null 
				&& entity.getVirtualCode()==null 
			    && !FossConstants.ACTIVE.equals(pendingEntity.getIsWholeVehicle())) {
			queryDto.setSubType(entity.getVirtualCode());// 限保物品才会具备的虚拟code
		}
		queryDto.setPriceEntityCode(PriceEntityConstants.PRICING_CODE_BF);// 计价条目CODE
		queryDto.setWoodenVolume(null);// 木架体积
		queryDto.setInsuranceAmount(pendingEntity.getInsuranceAmount());//保险声明价值
		queryDto.setMinFeeRate(pendingEntity.getMinFeeRate());//最小费率
		queryDto.setMaxFeeRate(pendingEntity.getMaxFeeRate());//最大费率
		return queryDto;
	}
	
	/**
	 * 封装营销活动参数信息
	 * @创建时间 2014-10-28 下午8:05:29   
	 * @创建人： PanGuoYang
	 */
	public  void settterActiveParamInfo(GuiQueryBillCalculateDto productPriceDtoCollection,WaybillPendingEntity entity,String activeCode){
		if(activeCode!=null){
			productPriceDtoCollection.setActiveCode(activeCode);
		}		
		productPriceDtoCollection.setGoodsName(entity.getGoodsName());
		productPriceDtoCollection.setDeliveryCustomerCode(entity.getDeliveryCustomerCode());
		productPriceDtoCollection.setOrderChannel(entity.getOrderChannel());
		productPriceDtoCollection.setReceiveOrgCode(entity.getReceiveOrgCode());
		productPriceDtoCollection.setLoadOrgCode(entity.getLoadOrgCode());
		productPriceDtoCollection.setLastOutLoadOrgCode(entity.getLastOutLoadOrgCode());
		productPriceDtoCollection.setCustomerPickupOrgCode(entity.getCustomerPickupOrgCode());
		productPriceDtoCollection.setBillTime(entity.getBillTime());
		productPriceDtoCollection.setTransportFee(entity.getTransportFee());
		productPriceDtoCollection.setGoodsWeightTotal(entity.getGoodsWeightTotal());
		productPriceDtoCollection.setGoodsVolumeTotal(entity.getGoodsVolumeTotal());
	}
	
	/**
	 *获取代收货款费率
	 * @param entity
	 * @author 076234 PanGuoYang
	 * @date 2014-10-03 上午11:02:10
	 * @return
	 */
	private GuiQueryBillCalculateSubDto getCodCollection(
			WaybillPendingEntity entity) {
		// 判断是否查询到保险费
		BigDecimal zero = BigDecimal.ZERO;
		// 获得输入的代收货款金额
		BigDecimal codAmount = entity.getCodAmount();
		if (codAmount != null && codAmount.compareTo(zero) > 0 && entity.getRefundType() != null) {
			return getQueryParam(PriceEntityConstants.PRICING_CODE_HK, entity.getCodAmount(), entity.getRefundType());
		}else{
			return null;
		}

	}
	
	
	/**
	 * 获取查询参数
	 * @author 076234 PanGuoYang
	 * @date 2014-10-03 上午11:02:10
	 */
	private GuiQueryBillCalculateSubDto getQueryParam(String valueAddType, BigDecimal cost, String subType) {
		GuiQueryBillCalculateSubDto queryDto = new GuiQueryBillCalculateSubDto();
		queryDto.setOriginnalCost(cost);// 原始费用
		queryDto.setSubType(subType);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setPriceEntityCode(valueAddType);// 计价条目CODE
		return queryDto;
	}
	
	
	/**
	 * 送货费
	 * @param entity
	 * @author 076234 PanGuoYang
	 * @date 2014-10-03 上午11:02:10
	 * @return
	 */
	private List<GuiQueryBillCalculateSubDto> getDeliveryFeeCollection(
			WaybillPendingEntity entity) {
		List<GuiQueryBillCalculateSubDto> queryBillCacilateValueAddDto = new ArrayList<GuiQueryBillCalculateSubDto>();
		// 整车没有送货费
		if (entity.getIsWholeVehicle() != null 
				&& FossConstants.ACTIVE.equals(entity.getIsWholeVehicle())) {
			return null;
		}
		//提货方式编码
		String code = entity.getReceiveMethod();
		// 送货不上楼
		if (WaybillConstants.DELIVER_NOUP.equals(code) || WaybillConstants.DELIVER_NOUP_AIR.equals(code)) {
			queryBillCacilateValueAddDto.add(getQueryParam(PriceEntityConstants.PRICING_CODE_SH, null, null));
			// 超远派送费
			if (entity.getKilometer() != null) {
				queryBillCacilateValueAddDto.add(getVeryFarQueryParam(PriceEntityConstants.PRICING_CODE_CY, BigDecimal.ZERO, null));
			}
		} else if (WaybillConstants.DELIVER_UP.equals(code)// 送货上楼
				|| WaybillConstants.DELIVER_UP_AIR.equals(code)) {
			queryBillCacilateValueAddDto.add(getQueryParam(PriceEntityConstants.PRICING_CODE_SH, null, null));
			queryBillCacilateValueAddDto.add(getQueryParam(PriceEntityConstants.PRICING_CODE_SHSL, null, null));
			// 超远派送费
			if (entity.getKilometer() != null) {
				queryBillCacilateValueAddDto.add(getVeryFarQueryParam(PriceEntityConstants.PRICING_CODE_CY, BigDecimal.ZERO, null));
			}
		} else if (WaybillConstants.DELIVER_STORAGE.equals(code)// 送货进仓
				|| WaybillConstants.DELIVER_INGA_AIR.equals(code)) {
			queryBillCacilateValueAddDto.add(getQueryParam(PriceEntityConstants.PRICING_CODE_SH, null, null));
			queryBillCacilateValueAddDto.add(getQueryParam(PriceEntityConstants.PRICING_CODE_QT, null, PriceEntityConstants.PRICING_CODE_SHJC));
			// 超远派送费
			if (entity.getKilometer() != null) {
				queryBillCacilateValueAddDto.add(getVeryFarQueryParam(PriceEntityConstants.PRICING_CODE_CY, BigDecimal.ZERO, null));
			}
		}else if (WaybillConstants.LARGE_DELIVER_UP.equals(code)// 大件上楼费
				|| WaybillConstants.LARGE_DELIVER_UP_AIR.equals(code)) {
			queryBillCacilateValueAddDto.add(getQueryParam(PriceEntityConstants.PRICING_CODE_SH, null, null));
			queryBillCacilateValueAddDto.add(getQueryParam(PriceEntityConstants.PRICING_CODE_DJSL, null, null));
			// 超远派送费
			if (entity.getKilometer() != null) {
				queryBillCacilateValueAddDto.add(getVeryFarQueryParam(PriceEntityConstants.PRICING_CODE_CY, BigDecimal.ZERO, null));
			}
		}
		return queryBillCacilateValueAddDto;
	}
	
	
	/** 
	 * 获取超远派送费查询参数 
	 * @author 076234-FOSS-PanGuoYang
	 * @date 2014-10-03 下午04:54:59
	 * @param valueAddType
	 * @param cost
	 * @param subType
	 * @return
	 */
	private GuiQueryBillCalculateSubDto getVeryFarQueryParam(String valueAddType, BigDecimal cost, String subType) {
		GuiQueryBillCalculateSubDto queryDto = new GuiQueryBillCalculateSubDto();
		queryDto.setOriginnalCost(cost);// 原始费用
		queryDto.setSubType(subType);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setPriceEntityCode(valueAddType);// 计价条目CODE
		return queryDto;
	}
	
	
	/**
	 * 获取其他费用查询参数
	 * 
	 * @author 076234-foss-PanGuoYang
	 * @date 2014-10-03 上午11:02:10
	 */
	private GuiQueryBillCalculateSubDto getQueryOtherChargeParam() {
		GuiQueryBillCalculateSubDto queryDto = new GuiQueryBillCalculateSubDto();

		queryDto.setOriginnalCost(BigDecimal.ZERO);// 原始费用
		queryDto.setSubType(null);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setPriceEntityCode(PriceEntityConstants.PRICING_CODE_QT);// 计价条目CODE
		return queryDto;
	}
	
	
	/**
	 * 
	 * <p>
	 * 运单其他信息封装<br />
	 * </p>
	 * @author PanGuoYang
	 * @return
	 * ActualFreightEntity
	 */
	private ActualFreightEntity getActualFreightEntity(WaybillEntity waybillEntity,WaybillPendingEntity pendingEntity,List<WaybillChargeDtlEntity> chargeDtlEntityList,GuiResultBillCalculateDto dto){
		ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
		//收货地址
		actualFreightEntity.setReceiveCustomerAddressNote(pendingEntity.getReceiveCustomerAddressNote());
		//发货地址
		actualFreightEntity.setDeliveryCustomerAddressNote(pendingEntity.getDeliveryCustomerAddressNote());
		//开始开单时间
		actualFreightEntity.setStartBillTime(waybillEntity.getBillTime());
		//运单号
		actualFreightEntity.setWaybillNo(waybillEntity.getWaybillNo());
		//货物名称
		actualFreightEntity.setGoodsName(waybillEntity.getGoodsName());
		//重量
		actualFreightEntity.setWeight(waybillEntity.getBillWeight());
		//体积
		actualFreightEntity.setVolume(waybillEntity.getGoodsVolumeTotal());
		//件数
		actualFreightEntity.setGoodsQty(waybillEntity.getGoodsQtyTotal());
		//尺寸
		actualFreightEntity.setDimension(StringUtils.isNotEmpty(waybillEntity.getGoodsSize()) ? waybillEntity.getGoodsSize() : "1*1*1*1");
		//保险声明价值
		actualFreightEntity.setInsuranceValue(waybillEntity.getInsuranceAmount());
		//包装费
		actualFreightEntity.setPackingFee( waybillEntity.getPackageFee()!=null ? waybillEntity.getPackageFee() : BigDecimal.valueOf(0));
		//送货费
		actualFreightEntity.setDeliverFee( waybillEntity.getDeliveryGoodsFee() != null ? waybillEntity.getDeliveryGoodsFee(): BigDecimal.valueOf(0));
		//装卸费
		actualFreightEntity.setLaborFee( waybillEntity.getServiceFee()!=null ? waybillEntity.getServiceFee() :  BigDecimal.valueOf(0));
		//代收货款
		actualFreightEntity.setCodAmount( waybillEntity.getCodAmount() != null ? waybillEntity.getCodAmount(): BigDecimal.valueOf(0));
		//增值费
		actualFreightEntity.setValueaddFee( waybillEntity.getValueAddFee() !=null ? waybillEntity.getValueAddFee(): BigDecimal.valueOf(0));
		//公布价运费
		actualFreightEntity.setFreight( waybillEntity.getTransportFee()!=null ? waybillEntity.getTransportFee(): BigDecimal.valueOf(0));
		//结清状态 
		actualFreightEntity.setSettleStatus(FossConstants.NO);
		//结清时间
		actualFreightEntity.setSettleTime(new Date());
		//通知状态
		//actualFreightEntity.setNotificationType(vo.get)
		//通知时间
		//actualFreightEntity.setNotificationTime(vo);
		//送货时间 
		//actualFreightEntity.setDeliverDate(vo.getPreCustomerPickupTime());
		//实际送货方式
		actualFreightEntity.setActualDeliverType(waybillEntity.getReceiveMethod());
		//运单状态
		actualFreightEntity.setStatus(WaybillConstants.EFFECTIVE);
		//库存天数
		//actualFreightEntity.setStorageDay(vo.getst)
		//库存费用
		//actualFreightEntity.setStorageCharge(vo.get);
		actualFreightEntity.setStartStockOrgCode(queryStartStocksDepartmentService(waybillEntity));
		actualFreightEntity.setEndStockOrgCode(queryEndStocksDepartmentService(waybillEntity));
		//actualFreightEntity.setActualDeliverType(vo.getdelivery)
		// 已生效
		actualFreightEntity.setStatus(WaybillConstants.EFFECTIVE);
		//是否是大票货
        if(waybillEntity.getGoodsWeightTotal().compareTo(new BigDecimal(FIVE_HUNDRED))>0 
        		||
        		waybillEntity.getGoodsVolumeTotal().compareTo(new BigDecimal(TWOPOINTFIVE)) >0){	
        	actualFreightEntity.setBigTicketGoods(FossConstants.YES);
		} 
		//含有超重费不是大票货
		/*for(WaybillChargeDtlEntity chargeDtl:chargeDtlEntityList){
			String chargecode = chargeDtl.getPricingEntryCode();
			if("CZHCZFWF_SDTJ".equals(chargecode)||"CZHCZFWF".equals(chargecode)){
				actualFreightEntity.setBigTicketGoods(FossConstants.NO);
			}
		}
		//内部带货 不是大票货
		if (WaybillConstants.INNER_PICKUP.equals(waybillEntity.getReceiveMethod())) {
			actualFreightEntity.setBigTicketGoods(FossConstants.NO);
		}*/
		
		//整车开单的时候 对actual Freight表的字段做如下调整
		if(waybillEntity.getIsWholeVehicle()!= null && FossConstants.ACTIVE.equals(waybillEntity.getIsWholeVehicle())){
			//1  ARRIVE_GOODS_QTY = 开单件数
			actualFreightEntity.setArriveGoodsQty(waybillEntity.getGoodsQtyTotal());
			//2  ARRIVE_NOTOUT_GOODS_QTY 到达未签收件数  = 开单件数
			actualFreightEntity.setArriveNotoutGoodsQty(waybillEntity.getGoodsQtyTotal());
			//3  若预计到达时间 不为空则为预计到达时间，否则为当前时间
			//预计到达时间
			Date preArrivedTime = waybillEntity.getPreCustomerPickupTime();
			if(null != preArrivedTime){
				actualFreightEntity.setArriveTime(preArrivedTime);
			}else{
				actualFreightEntity.setArriveTime(new Date());
			}
		}
	 /* if(waybillEntity.getOrderNo()!=null){
		  CrmOrderDetailDto crmOrderDetailDto = crmOrderService.importOrder(waybillEntity.getOrderNo());
			//保存官网用户名
		  actualFreightEntity.setChannelCustId(crmOrderDetailDto.getChannelCustId());
	  } */
		
	    //保存官网用户名
	    actualFreightEntity.setChannelCustId(pendingEntity.getChannelCustId());
	    //住宅区
	    actualFreightEntity.setBusinessZone(pendingEntity.getBusinessZone());
	    //商业区
	    actualFreightEntity.setResidentialDistrict(pendingEntity.getResidentialDistrict());
	    //包装备注
	    actualFreightEntity.setPackageRemark(pendingEntity.getPackageRemark());
		//发票标记
		if(StringUtil.isNotEmpty(pendingEntity.getInvoice())){
			actualFreightEntity.setInvoice(pendingEntity.getInvoice());
		}else{
			actualFreightEntity.setInvoice(WaybillConstants.INVOICE_02);
		}
		//最低一票标记
		if(dto !=null) {
			actualFreightEntity.setMinTransportFee(dto.getMinFee());
		}
		return actualFreightEntity;
	}
	
	
	/**
	 * 
	 * 获取始发库存部门
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-4-25 下午3:00:58
	 */
	private String queryStartStocksDepartmentService(WaybillEntity waybillEntry) {
		if (waybillEntry == null) {
			throw new WaybillStoreException("运单为空");
		}
		if (waybillEntry.getCreateOrgCode() == null) {
			throw new WaybillStoreException("创建部门为空");
		}

		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(waybillEntry.getCreateOrgCode());

		if (orgAdministrativeInfoEntity == null) {
			throw new WaybillStoreException("查询创建部门为空");
		}

		//在集中开单的情况下  配载部门取法不同
		if (FossConstants.YES.equals(waybillEntry.getPickupCentralized())) {
			if (waybillEntry.getLoadOrgCode() == null) {
				throw new WaybillStoreException("配载部门为空");
			}

			return waybillEntry.getLoadOrgCode();// 返回配载部门
		} else {

			if (FossConstants.YES.equals(orgAdministrativeInfoEntity
					.getSalesDepartment()))// 是否营业部
			{

				SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService
						.querySaleDepartmentByCode(waybillEntry.getCreateOrgCode());

				if (saleDepartmentEntity == null) {
					throw new WaybillStoreException("查询营业部为空:" + waybillEntry.getCreateOrgCode());
				}

				if (FossConstants.YES.equals(saleDepartmentEntity.getStation()))// 是否驻地部门
				{
					return saleDepartmentEntity.getTransferCenter();// 如果是，返回驻地营业部
				} else {
					return waybillEntry.getCreateOrgCode();
				}

			} else {
				throw new WaybillStoreException("不是营业部" + ReflectionToStringBuilder.toString(orgAdministrativeInfoEntity));
			}

		}

	}
	
	
	/**
	 * 查询基础信息
	 * @param waybillEntry
	 * @return
	 * @author 076234 PanGuoYang
	 */
	private String queryEndStocksDepartmentService(WaybillEntity waybillEntry) {
		if (waybillEntry == null) {
			throw new WaybillStoreException("运单基础信息不能为空");
		}
		if (waybillEntry.getLastLoadOrgCode() == null) {
			throw new WaybillStoreException("最终配载部门为空");
		}
		SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService
				.querySaleDepartmentByCode(waybillEntry.getLastLoadOrgCode());
		// 如果是驻地部门，返回驻地部门外场
		if (saleDepartmentEntity != null && FossConstants.YES.equals(saleDepartmentEntity.getStation())) {
			return saleDepartmentEntity.getTransferCenter();// 如果是，返回驻地营业部
		}

		return waybillEntry.getLastLoadOrgCode();// 如果不是驻地部门，返回最终配载部门

	}
	
	
	/**
	 * @author	076234 PanGuoYang
	 * @param bean
	 */
	public List<LabeledGoodEntity>  getLabeledGoodEntities(WaybillEntity entity,WoodenRequirePdaDto woodenPdaDto){
		List<LabeledGoodEntity>   labeledGoodEntitys= new ArrayList<LabeledGoodEntity>();
		if(woodenPdaDto!=null && woodenPdaDto.getWoodenStockNum()!=null && woodenPdaDto.getWoodenStockNum()>0){
			Integer woodenStockNum = woodenPdaDto.getWoodenStockNum() ;
			Integer goodsTotal = entity.getGoodsQtyTotal();
			if(goodsTotal != null && goodsTotal > 0){
				for(int i = 0; i < goodsTotal; i++){
					LabeledGoodEntity labeledGoodEntity = new LabeledGoodEntity();
					String serialNo = StringHandlerUtil.lpad(String.valueOf(i + 1), FOUR, "0");
					labeledGoodEntity.setSerialNo(serialNo);
					if(i<woodenStockNum){
						labeledGoodEntity.setPackageType(WaybillConstants.PACKAGE_TYPE_SALVER);
					}
					labeledGoodEntitys.add(labeledGoodEntity);
				}
				return labeledGoodEntitys;
			}
		}else{
			labeledGoodEntitys =labeledGoodDao.queryAllSerialByWaybillNo(entity.getWaybillNo());
		}
		
		if(CollectionUtils.isNotEmpty(labeledGoodEntitys)){
			return labeledGoodEntitys;
		}else{
			Integer goodsTotal = entity.getGoodsQtyTotal();
			if(goodsTotal != null && goodsTotal > 0){
				
				for(int i = 0; i < goodsTotal; i++){
					LabeledGoodEntity labeledGoodEntity = new LabeledGoodEntity();
					String serialNo = StringHandlerUtil.lpad(String.valueOf(i + 1), FOUR, "0");
					labeledGoodEntity.setSerialNo(serialNo);
		            //labeledGoodEntity.setPackageType(WaybillConstants.PACKAGE_TYPE_SALVER);
					labeledGoodEntitys.add(labeledGoodEntity);
				}
				return labeledGoodEntitys;
			}
		}
		return null;	 
	}
	
	
	/**
	 * 获取GuiResultBillCalculateDto
	 * @param dtos
	 * @param valueAddType
	 * @param subType
	 * @author 026113-foss-PanGuoYang
	 * @date 2014-10-08 下午7:43:29
	 */
	public GuiResultBillCalculateDto getGuiResultBillCalculateDto(List<GuiResultBillCalculateDto> dtos, String valueAddType, String subType) {

		for (GuiResultBillCalculateDto guiResultBillCalculateDto : dtos) {
			if (subType == null) {
				if (valueAddType.equals(guiResultBillCalculateDto.getPriceEntryCode())) {
					return guiResultBillCalculateDto;
				}
			} else {

				if (valueAddType.equals(guiResultBillCalculateDto.getPriceEntryCode()) && subType.equals(guiResultBillCalculateDto.getSubType())) {
					return guiResultBillCalculateDto;
				}
			}

		}

		return null;
	}
	
	
	/**
	 * 费用明细添加代收费用
	 * @param codCollection
	 * @param entity
	 * @param chargeDtlEntityList
	 * @author 076234 PanGuoYang
	 */
	private void setCodCollection(GuiResultBillCalculateDto codCollection,
			WaybillPendingEntity entity,
			List<WaybillChargeDtlEntity> chargeDtlEntityList) {
		if(codCollection!=null){
			WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
			chargeEntity.setId(UUIDUtils.getUUID());
			chargeEntity.setPricingEntryCode(codCollection.getPriceEntryCode());
			chargeEntity.setAmount(codCollection.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP));
			chargeEntity.setWaybillNo(entity.getWaybillNo());
			chargeEntity.setPricingCriDetailId(codCollection.getId());
			chargeEntity.setActive(FossConstants.ACTIVE);
			chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			chargeDtlEntityList.add(chargeEntity);
		}
		
	}
	
	
	/**
	 * 获取派送费集合
	 * @author 026113-foss-PanGuoYang
	 * @date 2014-10-08 下午7:43:29
	 */
	private List<DeliverChargeEntity> getDeliverChargeEntity(List<GuiResultBillCalculateDto> dtos ,WaybillPendingEntity entity,List<String> actualOtherFee){
		GuiResultBillCalculateDto guiResultBillCalculateDto;
		// 整车没有送货费
		if (entity.getIsWholeVehicle() != null && FossConstants.ACTIVE.equals(entity.getIsWholeVehicle())) {
			//cleanDeliverCharge(bean);
			return null;
		}

		//提货方式编码
		String code = entity.getReceiveMethod();
		// 获取派送费集合
		List<DeliverChargeEntity> delivetList = null;
		//获取超远派送费加收
		guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos, PriceEntityConstants.PRICING_CODE_QT, PriceEntityConstants.PRICING_CODE_CCDDJS);
		
		if(guiResultBillCalculateDto!=null){
			// 设置超远派送费加收
			delivetList=setDeliverFeeCcddjs(guiResultBillCalculateDto, false,entity,delivetList);
			if(CollectionUtils.isNotEmpty(delivetList)){
				actualOtherFee.add(PriceEntityConstants.PRICING_CODE_CCDDJS);
			}
		}
		
		// 送货不上楼
		if (WaybillConstants.DELIVER_NOUP.equals(code) || WaybillConstants.DELIVER_NOUP_AIR.equals(code)) {

			guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos, PriceEntityConstants.PRICING_CODE_SH, null);
			/**
			 * 如果是汽运送货费，那么需要判断是否超过最高派送费，如果超过，赋值为派送费最大值
			 */
			if (WaybillConstants.DELIVER_NOUP.equals(code)) {
				//获取送货不上楼
				getDeliveNoUpFee(guiResultBillCalculateDto);
			}
			
			// 设置送货费 
			delivetList= setDeliverFee(guiResultBillCalculateDto, false,entity,delivetList);
			// 超远派送费
			guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos, PriceEntityConstants.PRICING_CODE_CY, null);

			if (guiResultBillCalculateDto != null) {
				// 设置超远派送费
				delivetList=setDeliverFee(guiResultBillCalculateDto, false,entity,delivetList);
			}
			

		} else if (WaybillConstants.DELIVER_UP.equals(code)// 送货上楼
				|| WaybillConstants.DELIVER_UP_AIR.equals(code)) {
			//送货上楼
			delivetList = getDeliveUpFee(dtos, entity, delivetList);

		} else if (WaybillConstants.DELIVER_STORAGE.equals(code)// 送货进仓
				|| WaybillConstants.DELIVER_INGA_AIR.equals(code)) {
			//送货进仓
			delivetList = getDeliverStorageFee(dtos, entity, delivetList);
		}else if (WaybillConstants.LARGE_DELIVER_UP.equals(code)// 大件上楼
				|| WaybillConstants.LARGE_DELIVER_UP_AIR.equals(code)) {
			//大件上楼
			delivetList = getLargeDeliverUpFee(dtos, entity, delivetList);
		}
		/*
		// 判断是否自提
		if (WaybillConstants.SELF_PICKUP.equals(code) || WaybillConstants.AIR_SELF_PICKUP.equals(code)
				|| WaybillConstants.AIR_PICKUP_FREE.equals(code) || WaybillConstants.AIRPORT_PICKUP.equals(code)
				|| WaybillConstants.INNER_PICKUP.equals(code)) {
			bean.setCalculateDeliveryGoodsFee(BigDecimal.ZERO);
		}

		//送货费集合
		List<DeliverChargeEntity> deliverList = bean.getDeliverList();
		if (deliverList != null) {
			ui.canvasContentPanel.getOtherCost().setChangeDetail(deliverList);
		}*/
		return delivetList;
	}

	/**
	 * 大件上楼
	 * @param dtos
	 * @param entity
	 * @param delivetList
	 * @return
	 */
	private List<DeliverChargeEntity> getLargeDeliverUpFee(
			List<GuiResultBillCalculateDto> dtos, WaybillPendingEntity entity,
			List<DeliverChargeEntity> delivetList) {
		GuiResultBillCalculateDto guiResultBillCalculateDto;
		//获取送货费
		guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos, PriceEntityConstants.PRICING_CODE_SH, null);
		// 设置送货费
		delivetList=setDeliverFee(guiResultBillCalculateDto, false,entity,delivetList);
		//获取大件上楼费
		guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos, PriceEntityConstants.PRICING_CODE_DJSL, null);
		/**
		 * @需求：大件上楼优化
		 * @功能：当大件上楼计算费用为null时，说明没有查询到相应的大件上楼费用，需要维护相关资料
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-04-16
		 */
		if(guiResultBillCalculateDto==null){
			throw new WaybillValidateException(WaybillValidateException.NEED_LARGE_DELIVER_UP_DATA);
		}
		// 设置大件上楼费
		delivetList=setDeliverFee(guiResultBillCalculateDto, false,entity,delivetList);
		// 超远派送费
		guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos, PriceEntityConstants.PRICING_CODE_CY, null);

		if (guiResultBillCalculateDto != null) {
			// 设置超远派送费
			delivetList=setDeliverFee(guiResultBillCalculateDto, false,entity,delivetList);
		}
		return delivetList;
	}

	/**
	 * 送货进仓
	 * @param dtos
	 * @param entity
	 * @param delivetList
	 * @return
	 */
	private List<DeliverChargeEntity> getDeliverStorageFee(
			List<GuiResultBillCalculateDto> dtos, WaybillPendingEntity entity,
			List<DeliverChargeEntity> delivetList) {
		GuiResultBillCalculateDto guiResultBillCalculateDto;
		//获取送货费 
		guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos, PriceEntityConstants.PRICING_CODE_SH, null);
		// 设置送货费
		delivetList=setDeliverFee(guiResultBillCalculateDto, false,entity,delivetList);

		//获取送货费
		guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos, PriceEntityConstants.PRICING_CODE_QT, PriceEntityConstants.PRICING_CODE_SHJC);
		
		// 设置进仓费
		delivetList=setDeliverFee(guiResultBillCalculateDto,true,entity,delivetList);
		// 获取超远派送费
		guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos, PriceEntityConstants.PRICING_CODE_CY, null);

		if (guiResultBillCalculateDto != null) {
			// 设置超远派送费
			delivetList=setDeliverFee(guiResultBillCalculateDto,false,entity,delivetList);
		}
		return delivetList;
	}

	/**
	 * 送货上楼费
	 * @param dtos
	 * @param entity
	 * @param delivetList
	 * @return
	 */
	private List<DeliverChargeEntity> getDeliveUpFee(
			List<GuiResultBillCalculateDto> dtos, WaybillPendingEntity entity,
			List<DeliverChargeEntity> delivetList) {
		GuiResultBillCalculateDto guiResultBillCalculateDto;
		//获取送货费
		guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos, PriceEntityConstants.PRICING_CODE_SH, null);
		// 设置送货费
		delivetList=setDeliverFee(guiResultBillCalculateDto,false,entity,delivetList);
		//获取上楼费
		guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos, PriceEntityConstants.PRICING_CODE_SHSL, null);
		
		// 设置上楼费
		delivetList=setDeliverFee(guiResultBillCalculateDto, false,entity,delivetList);
		// 超远派送费
		guiResultBillCalculateDto = getGuiResultBillCalculateDto(dtos, PriceEntityConstants.PRICING_CODE_CY, null);

		if (guiResultBillCalculateDto != null) {
			// 设置超远派送费
			delivetList=setDeliverFee(guiResultBillCalculateDto, false,entity,delivetList);
		}
		return delivetList;
	}

	/**
	 * 获取送货不上楼
	 * @param guiResultBillCalculateDto
	 */
	private void getDeliveNoUpFee(
			GuiResultBillCalculateDto guiResultBillCalculateDto) {
		BigDecimal maxDeliveFee = null;//设置最大派送费
		// 最大派送费
		ConfigurationParamsEntity maxDeliverFeeForConfig = 
		pkpsysConfigService.queryConfigurationParamsByEntity(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP, ConfigurationParamsConstants.PKP_DELIVER_NOUP_MAX_FEE, FossConstants.ROOT_ORG_CODE);
		if (maxDeliverFeeForConfig != null
				&& StringUtils.isNotEmpty(maxDeliverFeeForConfig
						.getConfValue())) {
			maxDeliveFee = new BigDecimal(
					maxDeliverFeeForConfig.getConfValue());
		}
		
		if (guiResultBillCalculateDto != null && maxDeliveFee!=null)
		{
			BigDecimal caculateFee = guiResultBillCalculateDto
					.getCaculateFee();
			if (caculateFee.compareTo(maxDeliveFee) > 0) {
				guiResultBillCalculateDto.setCaculateFee(maxDeliveFee);

			}
		}
	}
	
	
	/**
	 * 设置超远派送加收费
	 */
	private List<DeliverChargeEntity> setDeliverFeeCcddjs(GuiResultBillCalculateDto dto, Boolean isDeliverStorge,WaybillPendingEntity entity,List<DeliverChargeEntity> delivetList) {
		if (dto != null&&FossConstants.NO.equals(dto.getCandelete())) {//这里是其他费用，不为空，并且不可删除，才进行收取操作

			DeliverChargeEntity deliver = new DeliverChargeEntity();
			// 是否激活
			deliver.setActive(FossConstants.ACTIVE);

			BigDecimal deliveryGoodsFee = dto.getCaculateFee();
			if (deliveryGoodsFee == null) {
				deliveryGoodsFee = BigDecimal.ZERO;
			} else {
				deliveryGoodsFee = deliveryGoodsFee.setScale(0, BigDecimal.ROUND_HALF_UP);
			}
			// 金额
			deliver.setAmount(deliveryGoodsFee);
			// 币种
			deliver.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			// 费用ID
			deliver.setId(dto.getId());
			// 运单号
			deliver.setWaybillNo(entity.getWaybillNo());
			// 费用编码
			deliver.setCode(dto.getSubType());
			//费用名称
			deliver.setName(dto.getSubTypeName());
			// 送货费合计
			//BigDecimal chargeTotal = BigDecimal.ZERO;
			// 送货费合计 = 送货费+上楼费/进仓费/超远派送费
			//chargeTotal = deliveryGoodsFee.add(bean.getDeliveryGoodsFee());
			//entity.setDeliveryGoodsFee(chargeTotal);
			// 画布-送货费
			//entity.setDeliveryGoodsFeeCanvas(chargeTotal.toString());
			// 计算的送货费
			//entity.setCalculateDeliveryGoodsFee(chargeTotal);
			// 获取派送费集合
			if (CollectionUtils.isEmpty(delivetList)) {
				delivetList = new ArrayList<DeliverChargeEntity>();
			}
			// 将新的派送费添加到派送费集合
			delivetList.add(deliver);
			// 将派送费集合进行更新
			//bean.setDeliverList(delivetList);
			// 此标识用来标记是否送货费,如果查询出来是送货费，则将送货费的最大上限设置
//			if (flag) {
//				bean.setMaxDeliveryGoodsFee(dto.getMaxFee());
//			}
			// 费用折扣
			//Common.setFavorableDiscount(dto.getDiscountPrograms(), ui, bean);
		}
		return delivetList;
	}
	
	
	/**
	 * 
	 * 设置送货费、送货进仓费、送货上楼等全部送货费
	 * 
	 * @param flag = true 表示是送货费中的基础送货费
	 * @param isDeliverStorge = true
	 *            表示送货费中的送货进仓费
	 * @author 076234-FOSS-PanGuoYang
	 * @date 2014-10-08 上午11:52:25
	 */
	private List<DeliverChargeEntity> setDeliverFee(GuiResultBillCalculateDto dto, Boolean isDeliverStorge,WaybillPendingEntity entity,List<DeliverChargeEntity> delivetList) {
		if (dto != null) {

			DeliverChargeEntity deliver = new DeliverChargeEntity();
			// 是否激活
			deliver.setActive(FossConstants.ACTIVE);

			BigDecimal deliveryGoodsFee = dto.getCaculateFee();
			if (deliveryGoodsFee == null) {
				deliveryGoodsFee = BigDecimal.ZERO;
			} else {
				deliveryGoodsFee = deliveryGoodsFee.setScale(0, BigDecimal.ROUND_HALF_UP);
			}
			// 金额
			deliver.setAmount(deliveryGoodsFee);
			// 币种
			deliver.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			// 费用ID
			deliver.setId(dto.getId());
			// 运单号
			deliver.setWaybillNo(entity.getWaybillNo());
			//提货方式编码
			if (isDeliverStorge)// 送货进仓
			{
				// 费用编码
				deliver.setCode(dto.getSubType());
				//费用名称
				deliver.setName(dto.getSubTypeName());
			} else {
				// 费用编码
				deliver.setCode(dto.getPriceEntryCode());

				//费用名称
				deliver.setName(dto.getPriceEntryName());
			}
			// 送货费合计
			//BigDecimal chargeTotal = BigDecimal.ZERO;
			// 送货费合计 = 送货费+上楼费/进仓费/超远派送费
			//chargeTotal = deliveryGoodsFee.add(bean.getDeliveryGoodsFee());
			//entity.setDeliveryGoodsFee(chargeTotal);
			// 画布-送货费
			//entity.setDeliveryGoodsFeeCanvas(chargeTotal.toString());
			// 计算的送货费
			//entity.setCalculateDeliveryGoodsFee(chargeTotal);
			// 获取派送费集合
			if (CollectionUtils.isEmpty(delivetList)) {
				delivetList = new ArrayList<DeliverChargeEntity>();
			}
			// 将新的派送费添加到派送费集合
			delivetList.add(deliver);
			// 将派送费集合进行更新
			//bean.setDeliverList(delivetList);
			// 此标识用来标记是否送货费,如果查询出来是送货费，则将送货费的最大上限设置
//			if (flag) {
//				bean.setMaxDeliveryGoodsFee(dto.getMaxFee());
//			}
			// 费用折扣
			//Common.setFavorableDiscount(dto.getDiscountPrograms(), ui, bean);
		}
		return delivetList;
	}
	
	
	/**
	 * 费用明细添加送货费
	 * @param delivetList
	 * @param entity
	 * @param chargeDtlEntityList
	 * @author 076234 PanGuoYang
	 */
	private void setDeliveryFeeCollection(
			List<DeliverChargeEntity> delivetList, WaybillPendingEntity entity,
			List<WaybillChargeDtlEntity> chargeDtlEntityList) {
		if(CollectionUtils.isNotEmpty(delivetList))
		{
			for(int i=0;i<delivetList.size();i++)
			{
				WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
				DeliverChargeEntity deliverCharge = delivetList.get(i);
				//送货费编码
				chargeEntity.setId(UUIDUtils.getUUID());
				chargeEntity.setPricingEntryCode(deliverCharge.getCode());
				chargeEntity.setAmount(deliverCharge.getAmount());
				chargeEntity.setWaybillNo(entity.getWaybillNo());
				chargeEntity.setPricingCriDetailId(deliverCharge.getId());
				chargeEntity.setActive(deliverCharge.getActive());
				chargeEntity.setCurrencyCode(deliverCharge.getCurrencyCode());
				chargeDtlEntityList.add(chargeEntity);
			}
		}
		
	}
	
	/**
	 * 获取接货费
	 * @param dtoJH
	 * @param entity
	 * @author foss-PanGuoYang
     * @date 2014-10-01 
	 */
	private void setProductPriceDtoCollection(GuiResultBillCalculateDto dto,GuiResultBillCalculateDto dtoJH,
			WaybillPendingEntity entity,List<WaybillChargeDtlEntity> chargeList,String remark) {
		if(entity.getPickupToDoor() != null && FossConstants.ACTIVE.equals(entity.getPickupToDoor())){
			if (FossConstants.ACTIVE.equals(dto.getCentralizePickup())) {
				entity.setPickupFee(BigDecimal.ZERO);//设置接货费
				
			}else{
				if(dtoJH==null){
					throw new PdaInterfaceException("接货费不能为空");
				}
				
				WaybillEntity wentity= getWaybillEntity(entity);
				// 是否为发货人在当前收货部门的当天8时到次日8时间的第一票
				boolean firstWaybill =waybillManagerService.isFirstDeliveryWaybill(wentity);
				//GUI端手输接货费
				BigDecimal pickupFee = wentity.getPackageFee()!=null?wentity.getPackageFee(): BigDecimal.ZERO;
				if(firstWaybill){
					//是第一单接货
					isFirstWaybill(dtoJH, entity);
			
				}else{
					if(pickupFee.compareTo(BigDecimal.ZERO)>0 && dtoJH.getMinFee()!=null){
						if(pickupFee.compareTo(dtoJH.getMinFee())>0){
							entity.setPickupFee(pickupFee);// 设置接货费
						}else{
							entity.setPickupFee(dtoJH.getMinFee());// 设置接货费
						}
						remark = "页面手动添加接货费:"+pickupFee; 
					}
					if(pickupFee.compareTo(BigDecimal.ZERO)>0 && dtoJH.getMaxFee()!=null){
						if(pickupFee.compareTo(dtoJH.getMaxFee())>0){
							entity.setPickupFee(dtoJH.getMaxFee());// 设置接货费
						}
						remark = "";
						remark = "页面手动添加接货费:"+pickupFee;
					}
					if(pickupFee==null || pickupFee.compareTo(BigDecimal.ZERO)==0){
						entity.setPickupFee(BigDecimal.ZERO);//设置接货费
					}
				}
			}
		}else{
			entity.setPickupFee(BigDecimal.ZERO);//设置接货费
		}
		
		if(dtoJH!=null){
			BigDecimal jhf =entity.getPickupFee()!=null?  entity.getPickupFee():BigDecimal.ZERO;
			dtoJH.setCaculateFee(jhf);
		}
		
		
		//费用明细添加接货费
		getPickupFeeDetail(dtoJH, entity, chargeList);
	}

	/**
	 * 是第一单接货
	 * @param dtoJH
	 * @param entity
	 */
	private void isFirstWaybill(GuiResultBillCalculateDto dtoJH,
			WaybillPendingEntity entity) {
		entity.setPickupFee(dtoJH.getCaculateFee());// 设置接货费

		if(dtoJH.getMinFee() != null){
			if(null == dtoJH.getFee() && dtoJH.getCaculateFee().compareTo(dtoJH.getMinFee()) < 0){
				entity.setPickupFee(dtoJH.getMinFee());// 设置接货费
			}
		}
		//判断最高一票与折扣金额
		if(dtoJH.getMaxFee() != null){
			if(null == dtoJH.getFee() && dtoJH.getCaculateFee().compareTo(dtoJH.getMaxFee()) > 0){
				entity.setPickupFee(dtoJH.getMaxFee());// 设置接货费
			}
		}
		// 返回的接货费值不为空，则表示有客户编码且有设置客户接货费值
		if (null != dtoJH.getFee() && null == dtoJH.getCaculateFee()) {
			entity.setPickupFee(dtoJH.getFee());// 设置接货费
		}
	}

	/**
	 * 获取接货费明细
	 * @param dtoJH
	 * @param entity
	 * @param chargeList
	 */
	private void getPickupFeeDetail(GuiResultBillCalculateDto dtoJH,
			WaybillPendingEntity entity, List<WaybillChargeDtlEntity> chargeList) {
		if (entity.getPickupFee() != null && entity.getPickupFee().compareTo(BigDecimal.ZERO) != 0) {
			WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
			chargeEntity.setId(UUIDUtils.getUUID());
			chargeEntity.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_JH);
			if(dtoJH!=null && StringUtil.isNotBlank(dtoJH.getId())){
				chargeEntity.setPricingCriDetailId(dtoJH.getId());
			}else{
			chargeEntity.setPricingCriDetailId(WaybillConstants.HAND_INPUT);
			}
			chargeEntity.setAmount(entity.getPickupFee());
			chargeEntity.setWaybillNo(entity.getWaybillNo());
			chargeEntity.setActive(FossConstants.ACTIVE);
			chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			chargeList.add(chargeEntity);
		}
	}
	
	/**
	 * 封装运单部分数据
	 * @author 026123-foss-lifengteng
	 * @date 2014-2-28 下午7:21:05
	 */
	public  WaybillEntity getWaybillEntity(WaybillPendingEntity entity) {
		WaybillEntity wentity = new WaybillEntity();
		wentity.setWaybillNo(entity.getWaybillNo());// 运单号
		wentity.setOrderNo(entity.getOrderNo());// 订单号
		wentity.setDeliveryCustomerCode(entity.getDeliveryCustomerCode());// 发货客户编码
		wentity.setDeliveryCustomerName(entity.getDeliveryCustomerName());// 发货客户名称
		wentity.setDeliveryCustomerMobilephone(entity.getDeliveryCustomerMobilephone());// 发货客户手机
		wentity.setDeliveryCustomerPhone(entity.getDeliveryCustomerPhone());// 发货客户电话
		wentity.setBillTime(entity.getBillTime());//开单时间
		wentity.setReceiveOrgCode(entity.getReceiveOrgCode());//收货部门编码
		//entity.setReceiveOrgName(entity.getReceiveOrgName());//收货部门名称
		wentity.setActive(FossConstants.YES);
		return wentity;
	}

	/**
	 * 费用明细添加公布价费
	 * @param dto
	 * @param entity
	 * @param chargeDtlEntityList
	 * @author 076234 PanGuoYang
	 */
	private void setTransportFee(GuiResultBillCalculateDto dto,
			WaybillEntity waybillEntity,
			WaybillPendingEntity entity,
			List<WaybillChargeDtlEntity> chargeList) {
		//装卸费  （装卸费放在运费里）
		BigDecimal  serviceFee = getServiceFee(entity,dto);
		waybillEntity.setServiceFee(serviceFee);
		dto.setCaculateFee(dto.getCaculateFee().add(serviceFee));
		WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
		if(FossConstants.ACTIVE.equals(entity.getIsWholeVehicle())){
			chargeEntity.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_FRT);
    		chargeEntity.setPricingCriDetailId(WaybillConstants.HAND_INPUT);
		}else{
    		chargeEntity.setPricingEntryCode(dto.getPriceEntryCode());
    		chargeEntity.setPricingCriDetailId(dto.getId());
		}
		chargeEntity.setId(UUIDUtils.getUUID());
		chargeEntity.setAmount(dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP));
		chargeEntity.setWaybillNo(entity.getWaybillNo());
		chargeEntity.setActive(FossConstants.ACTIVE);
		chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		chargeList.add(chargeEntity);
	}
	
	
	/**
	 * 获取装卸费
	 * @param entity
	 * @return
	 * @author 076234 PanGuoYang
	 */
	private BigDecimal getServiceFee(WaybillPendingEntity entity,GuiResultBillCalculateDto dto){
		 //运费
		BigDecimal	transportFee=dto.getCaculateFee();
		//装卸费
		BigDecimal serviceFee =BigDecimal.ZERO;
		//装卸费百分比
		BigDecimal serviceRate = entity.getServiceRate();
		if(serviceRate!=null){
			serviceRate=serviceRate.divide(new BigDecimal(ONE_HUNDRED));
		}
		
		/***********************add by hehaisu**************************/
		//读取装卸费率系统配置参数
		//初始化15%
	    BigDecimal  pct =	new BigDecimal(FIFTEEN).divide(new BigDecimal(ONE_HUNDRED));
	    String productCode = entity.getProductCode();
		// 调用接口读取装卸费费率
		ConfigurationParamsEntity paramsEntity = null;
		if(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productCode))
		{
			paramsEntity = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__STL,
					ConfigurationParamsConstants.STL_SERVICE_AIR_FEE_RATIO,entity.getReceiveOrgCode());
		}else
		{
			paramsEntity = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__STL,
					ConfigurationParamsConstants.STL_SERVICE_FEE_RATIO,entity.getReceiveOrgCode());
		}
		
		if (paramsEntity == null) {
			LOGGER.error("当前部门不存在装卸费比率，无法进行装卸费计算");
			throw new WaybillValidateException(WaybillValidateException.NULL_PARAMS_ENTITY);
		} else {
			// 判断是否存在装卸费费率
			if (paramsEntity.getConfValue() == null) {
				LOGGER.error("装卸费率为空");
				throw new WaybillValidateException(WaybillValidateException.NULL_PARAMS_ENTITY_CONFVALUE);
			} else {
				pct = new BigDecimal(paramsEntity.getConfValue());
			}
		}
		/******************************end***********************************/
		
		//图片装卸费
		BigDecimal service=entity.getServiceFee();
		if(serviceRate!=null && service!=null){
		    //百分15的运费
		    BigDecimal freePCT = transportFee.multiply(pct);
		    //装卸费大于百分15的运费
		    if(service.compareTo(freePCT)>0){
		    	serviceFee=transportFee.multiply(serviceRate).compareTo(BigDecimal.ZERO)>0?transportFee.multiply(serviceRate):freePCT;
		    }else if(service.compareTo(transportFee.multiply(serviceRate))>0){
		    	serviceFee=service;
		    }else {
		    	serviceFee=transportFee.multiply(serviceRate);
		    }
		    
		}
		if(serviceRate!=null && service==null){
			serviceFee=transportFee.multiply(serviceRate);
		}
		if(service!=null && serviceRate ==null){
		    //百分15的运费
		    BigDecimal freePCT = transportFee.multiply(pct);
		    if(service.compareTo(freePCT)>0){
		    	throw new PdaInterfaceException("装卸费不能大于运费的15%");	
		    }else{
		    	serviceFee=service;
		    }
		    
		}
		return  serviceFee.setScale(0, BigDecimal.ROUND_HALF_UP);
    	
    }
	
	
	/**
	 * 获取报价费
	 * @param insuranceCollection
	 * @param entity
	 * @author foss-PanGuoYang
     * @date 2014-10-08
	 */
	private void setInsurance(GuiResultBillCalculateDto dto,
			WaybillPendingEntity entity,List<WaybillChargeDtlEntity> chargeList) {
		if(dto!=null){
			
			BigDecimal insuranceFee = dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
			if(!"0".equals(entity.getInsuranceAmount().toString())
					&&
				(BigDecimal.ZERO.compareTo(entity.getInsuranceAmount())<0)
			  ){
				WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
				chargeEntity.setId(UUIDUtils.getUUID());
				chargeEntity.setPricingEntryCode(dto.getPriceEntryCode());
				chargeEntity.setAmount(insuranceFee);
				chargeEntity.setWaybillNo(entity.getWaybillNo());
				chargeEntity.setPricingCriDetailId(dto.getId());
				chargeEntity.setActive(FossConstants.ACTIVE);
				chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
				chargeList.add(chargeEntity);
			}else{
				dto.setCaculateFee(BigDecimal.ZERO);
			} 
			
	  }
	}
	
	
	/**
	 * 费用明细添加打木架费
	 * @param packageCollectionFRAME
	 * @param entity
	 * @author 076234 PanGuoYang
	 */
	private void setStandChargeCollection(
			GuiResultBillCalculateDto dto,WaybillPendingEntity entity,
			WoodenRequirementsEntity woodEntity,List<WaybillChargeDtlEntity> chargeList ) {
		if(woodEntity!=null){
			if (woodEntity.getStandGoodsNum() != null && woodEntity.getStandGoodsNum() > 0) {
				if (dto != null) {
					WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
					chargeEntity.setId(UUIDUtils.getUUID());
					chargeEntity.setPricingEntryCode(dto.getSubType());
					chargeEntity.setAmount(dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP));
					chargeEntity.setPricingCriDetailId(dto.getId());
					chargeEntity.setWaybillNo(entity.getWaybillNo());
					chargeEntity.setActive(FossConstants.ACTIVE);
					chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
					chargeList.add(chargeEntity);
				} else {
					throw new PdaInterfaceException("没有打木架费用");
				}
			}
		}
	}
	
	
	/**
	 * 费用明细添加打木箱费
	 * @param packageCollectionFRAME
	 * @param entity
	 * @author 076234 PanGuoYang
	 */
	private void setBoxChargeCollection(
			GuiResultBillCalculateDto dto,
			WaybillPendingEntity entity, WoodenRequirementsEntity woodEntity,
			List<WaybillChargeDtlEntity> chargeList) {
		if(woodEntity!=null){
			if (woodEntity.getBoxGoodsNum() != null && woodEntity.getBoxGoodsNum() > 0) {
				if (dto != null) {
					WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
					chargeEntity.setId(UUIDUtils.getUUID());
					chargeEntity.setPricingEntryCode(dto.getSubType());
					chargeEntity.setAmount(dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP));
					chargeEntity.setPricingCriDetailId(dto.getId());
					chargeEntity.setWaybillNo(entity.getWaybillNo());
					chargeEntity.setActive(FossConstants.ACTIVE);
					chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
					chargeList.add(chargeEntity);
				} else {
					throw new PdaInterfaceException("没有打木箱费用");			
				}
			}
		}
	}
	
	
	/**
	 * 包装费
	 * @param packageCollectionFRAME
	 * @param packageCollectionBOX
	 * @param entity
	 * @param chargeDtlEntityList
	 * @author 076234 PanGuoYang
	 */
	private WaybillChargeDtlEntity setPackageCharge(
			GuiResultBillCalculateDto packageCollectionFRAME,
			GuiResultBillCalculateDto packageCollectionBOX,
			WoodenRequirementsEntity woodEntity,
			WaybillPendingEntity entity,
			List<WaybillChargeDtlEntity> chargeDtlEntityList) {
		BigDecimal packageFee= BigDecimal.ZERO;
		boolean isok=false;
		//木架
		if(packageCollectionFRAME!=null){
			packageFee=packageFee.add(packageCollectionFRAME.getCaculateFee());
			isok=true;
		}
		//木箱
		if(packageCollectionBOX!=null){
			packageFee=packageFee.add(packageCollectionBOX.getCaculateFee());
			isok=true;
		}
		//木托
		BigDecimal mt= BigDecimal.ZERO;
		if(woodEntity!=null && woodEntity.getSalverGoodsAmount()!=null 
				 && 
		  woodEntity.getSalverGoodsAmount().compareTo(BigDecimal.ZERO)>0){
			mt=woodEntity.getSalverGoodsAmount();
			isok=true;
		}
		//手输包装费
		BigDecimal sspackageFee= BigDecimal.ZERO; 
		if(entity.getPackageFee()!=null){
			sspackageFee=entity.getPackageFee();
			isok=true;
		}
		
		if(isok){
			WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
			chargeEntity.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_BZ);
			chargeEntity.setAmount(packageFee.add(mt).add(sspackageFee));
			chargeEntity.setPricingCriDetailId(WaybillConstants.HAND_INPUT);
			chargeEntity.setWaybillNo(entity.getWaybillNo());
			chargeEntity.setActive(FossConstants.ACTIVE);
			chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			chargeDtlEntityList.add(chargeEntity);
			return chargeEntity;
		}
		
		return null;
		
	}
	
	
	
	
	/**
	 * 其他费用
	 * @param entity
	 * @param chargePendList
	 * @param chargeDtlEntityList
	 * @author 076234 PanGuoYang  雷锋
	 */
	private BigDecimal otherFee(WaybillPendingEntity entity,
			List<WaybillCHDtlPendingEntity> chargePendList,
			List<WaybillChargeDtlEntity> chargeDtlEntityList,
			WoodenRequirePdaDto woodenPdaDto,
			List<GuiResultBillCalculateDto> otherCalculateDtos,
			List<WaybillDisDtlEntity> disDtlEntityList,
			List<String> actualOtherFee,
			OmsOrderEntity omsOrderEntity) {
		BigDecimal otherFee = BigDecimal.ZERO;
		
		//添加其他费用（除综合信息费、超重服务费），其他费用是开单时暂存的
		 //综合信息费(ZHXX)、超重服务费   需从新计算
		otherFee = handleChargePendList(entity, chargePendList,
				chargeDtlEntityList, otherFee);
		
		//获取其他费用明细
		otherFee = getOtherFeeDetail(entity, chargeDtlEntityList,
				otherCalculateDtos, actualOtherFee, otherFee);
		
		//超重货操作服务费
		otherFee = getOverweightFee(entity, chargePendList,
				chargeDtlEntityList, woodenPdaDto, otherCalculateDtos,
				actualOtherFee, otherFee);
		
		//签收单费用
		otherFee = getSingleFee(entity, chargeDtlEntityList, disDtlEntityList,
				actualOtherFee, otherFee);
		
		//到港代理过关费 
		otherFee = clearCustomsFee(entity, chargeDtlEntityList, actualOtherFee,
				otherFee);
		//如果订单传入时为超远派送则添加配置资料里的超远派送费用，根据区域编码查询该区域的超远派送费用
		BigcusDeliveryAddressEntity bigcusDeliveryAddressEntity = null;
		if (WaybillConstants.YES.equals(omsOrderEntity.getIsMuchHigherDelivery())) {
			bigcusDeliveryAddressEntity = bigcusDeliveryAddressDao.queryBigcusDeliveryAddressEntityByCode(omsOrderEntity.getReceiveCustomerTownCode());
		}
		if (bigcusDeliveryAddressEntity != null && bigcusDeliveryAddressEntity.getMuchHigherDelivery() != null) {
			otherFee = otherFee.add(new BigDecimal(bigcusDeliveryAddressEntity.getMuchHigherDelivery()));
		}
		return otherFee;
	}

	/**
	 * 获取超重服务费
	 * @param entity
	 * @param chargePendList
	 * @return
	 * 500kg - 1000 一件收100元
	 * 1000 -2000 一件收200元
	 */
	private BigDecimal getOverweightFee(WaybillPendingEntity entity,
			List<WaybillCHDtlPendingEntity> chargePendList,
			List<WaybillChargeDtlEntity> chargeDtlEntityList,
			WoodenRequirePdaDto woodenPdaDto,
			List<GuiResultBillCalculateDto> otherCalculateDtos,
			List<String> actualOtherFee, BigDecimal otherFee) {
		Integer fhToOtOverQty =woodenPdaDto.getFhToOtOverQty();
		Integer otToTtOverQty =woodenPdaDto.getOtToTtOverQty();
		WaybillChargeDtlEntity chargeDtl = null;
		//是否需计算超重货操作服务费
		boolean isok=true;
		if( fhToOtOverQty!=null && fhToOtOverQty>0){
			chargeDtl = new WaybillChargeDtlEntity();
			// 超重货操作服务费
			chargeDtl.setPricingEntryCode(PRICING_CODE_CZHCZFWF);//"CZHCZFWF"
			chargeDtl.setAmount(new BigDecimal(fhToOtOverQty*ONE_HUNDRED));
			chargeDtl.setWaybillNo(entity.getWaybillNo());
			chargeDtl.setPricingCriDetailId(UUIDUtils.getUUID());
			chargeDtl.setActive(FossConstants.ACTIVE);
			chargeDtl.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		}
		if(otToTtOverQty!=null && otToTtOverQty>0){
			if(chargeDtl==null){
				chargeDtl = new WaybillChargeDtlEntity();
				chargeDtl.setId(UUIDUtils.getUUID());
				chargeDtl.setPricingEntryCode(PRICING_CODE_CZHCZFWF);
				chargeDtl.setAmount(new BigDecimal(otToTtOverQty*TWO_HUNDRED));
				chargeDtl.setWaybillNo(entity.getWaybillNo());
				chargeDtl.setPricingCriDetailId(UUIDUtils.getUUID());
				chargeDtl.setActive(FossConstants.ACTIVE);
				chargeDtl.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
				chargeDtlEntityList.add(chargeDtl);
			}else{
				chargeDtl.setAmount(chargeDtl.getAmount().add(new BigDecimal(otToTtOverQty*TWO_HUNDRED)));
			}
		} 
		if(chargeDtl!=null){
			isok=false;
			otherFee=otherFee.add(chargeDtl.getAmount());
			chargeDtlEntityList.add(chargeDtl);
		}
		
		//如果没有超重服务费，从开单时暂存的其他费用取超重服务费
		if(isok){
			if(CollectionUtils.isNotEmpty(chargePendList)){
				for(WaybillCHDtlPendingEntity chDtl:chargePendList){
					String code = chDtl.getPricingEntryCode();
					if(PRICING_CODE_CZHCZFWF_SDTJ.equals(code)|| PRICING_CODE_CZHCZFWF.equals(code)){
						WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
						chargeEntity.setId(UUIDUtils.getUUID());
						chargeEntity.setPricingEntryCode(code);
						chargeEntity.setAmount(chDtl.getAmount());
						chargeEntity.setWaybillNo(entity.getWaybillNo());
						chargeEntity.setPricingCriDetailId(UUIDUtils.getUUID());
						chargeEntity.setActive(FossConstants.ACTIVE);
						chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
						otherFee=otherFee.add(chargeEntity.getAmount());
						chargeDtlEntityList.add(chargeEntity);
						isok=false;
					}
				}
			}
		}
		//如果没有超重服务费，货物平均总量>500则添加超值服务费
		if(isok){
			//货物总量
			BigDecimal goodsWeightTotal= entity.getGoodsWeightTotal();
			//货物件数
			BigDecimal goodsQtyTotal =new BigDecimal( entity.getGoodsQtyTotal());
			BigDecimal pjzl =goodsWeightTotal.divide(goodsQtyTotal,BigDecimal.ROUND_HALF_UP);
			//如果是必须收取，那就不管是不是500公斤了
			for(GuiResultBillCalculateDto dto:otherCalculateDtos){
				String code =dto.getSubType();
				if(PRICING_CODE_CZHCZFWF.equals(code)){//"CZHCZFWF"
					//只有不大于500公斤，并且为非必须收取的时候，才不会收取超重或服务费//王丹要求
					if(!(pjzl.compareTo(new BigDecimal(FIVE_HUNDRED))<0&&FossConstants.YES.equals(dto.getCandelete()))){
						WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
						chargeEntity.setId(UUIDUtils.getUUID());
						chargeEntity.setPricingEntryCode(code);
						chargeEntity.setAmount(dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP));//dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP)
						chargeEntity.setWaybillNo(entity.getWaybillNo());
						chargeEntity.setPricingCriDetailId(UUIDUtils.getUUID());
						chargeEntity.setActive(FossConstants.ACTIVE);
						chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
						otherFee=otherFee.add(chargeEntity.getAmount());
						chargeDtlEntityList.add(chargeEntity);
						actualOtherFee.add(code);
						isok=false;
					}
				}
			}
		}
		return otherFee;
	}

	/**
	 * 到港代理过关费 
	 * 目的站为香港的货物，单票100kg（含）以下，加收150元/票，100kg以上，加收350元/票。
	 */
	private BigDecimal clearCustomsFee(WaybillPendingEntity entity,
			List<WaybillChargeDtlEntity> chargeDtlEntityList,
			List<String> actualOtherFee, BigDecimal otherFee) {
		String pickupOrgCode =entity.getCustomerPickupOrgCode();
		boolean isHK = isHK(entity.getCustomerPickupOrgCode());
	     //货物重量
	     BigDecimal goodsWeightTotal= entity.getGoodsWeightTotal();
	     //目的为香港的 才有商业区和住宅区
	     if(isHK){
	    	 if(goodsWeightTotal.compareTo(new BigDecimal(ONE_HUNDRED))<0){
	    		 WaybillChargeDtlEntity  chargeDtlEntity= new WaybillChargeDtlEntity();
	    		 String type =ConfigurationParamsConstants.HK_GG_FEE_0To100;
				 ConfigurationParamsEntity config =queryConfigurationParamsByOrgCode(type,pickupOrgCode);
				 if(config!=null){
					 chargeDtlEntity.setAmount(new BigDecimal(config.getConfValue()));
				 }else{
					 chargeDtlEntity.setAmount(new BigDecimal(ONE_HUNDRED_FIFTY));
				 }
				 chargeDtlEntity.setId(UUIDUtils.getUUID());
	    		 chargeDtlEntity.setPricingEntryCode(PRICING_CODE_DLGGF);
	    		 chargeDtlEntity.setWaybillNo(entity.getWaybillNo());
	    		 chargeDtlEntity.setPricingCriDetailId(UUIDUtils.getUUID());
	    		 chargeDtlEntity.setActive(FossConstants.ACTIVE);
	    		 chargeDtlEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
	    		 otherFee=otherFee.add(chargeDtlEntity.getAmount());
				 chargeDtlEntityList.add(chargeDtlEntity);
				 actualOtherFee.add(PRICING_CODE_DLGGF);
	    	 }else{
	    		 WaybillChargeDtlEntity  chargeDtlEntity= new WaybillChargeDtlEntity();
	    		 String type =ConfigurationParamsConstants.HK_GG_FEE_100ToBG;
				 ConfigurationParamsEntity config =queryConfigurationParamsByOrgCode(type,pickupOrgCode);
				 if(config!=null){
					 chargeDtlEntity.setAmount(new BigDecimal(config.getConfValue()));
				 }else{
					 chargeDtlEntity.setAmount(new BigDecimal(THREE_HUNDRED_FIFTY));
				 }
				 chargeDtlEntity.setId(UUIDUtils.getUUID());
	    		 chargeDtlEntity.setPricingEntryCode(PRICING_CODE_DGDLGGF);//"DGDLGGF"
	    		 chargeDtlEntity.setWaybillNo(entity.getWaybillNo());
	    		 chargeDtlEntity.setPricingCriDetailId(UUIDUtils.getUUID());
	    		 chargeDtlEntity.setActive(FossConstants.ACTIVE);
	    		 chargeDtlEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
	    		 otherFee=otherFee.add(chargeDtlEntity.getAmount());
				 chargeDtlEntityList.add(chargeDtlEntity);
				 actualOtherFee.add(PRICING_CODE_DGDLGGF);
	    	 }
	    	
	     }
		return otherFee;
	}

	/**
	 * 获取签收单费
	 * @param entity
	 * @param chargeDtlEntityList
	 * @param disDtlEntityList
	 * @param actualOtherFee
	 * @param otherFee
	 * @return
	 */
	private BigDecimal getSingleFee(WaybillPendingEntity entity,
			List<WaybillChargeDtlEntity> chargeDtlEntityList,
			List<WaybillDisDtlEntity> disDtlEntityList,
			List<String> actualOtherFee, BigDecimal otherFee) {
		ValueAddDto valueAddDto = setReturnBillCollection(entity);
		if(valueAddDto!=null){
			WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
			chargeEntity.setId(UUIDUtils.getUUID());
			chargeEntity.setPricingEntryCode(valueAddDto.getPriceEntityCode());
			chargeEntity.setAmount(valueAddDto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP));//dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP)
			chargeEntity.setWaybillNo(entity.getWaybillNo());
			chargeEntity.setPricingCriDetailId(UUIDUtils.getUUID());
			chargeEntity.setActive(FossConstants.ACTIVE);
			chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			otherFee=otherFee.add(chargeEntity.getAmount());
			chargeDtlEntityList.add(chargeEntity);
			actualOtherFee.add(valueAddDto.getPriceEntityCode());
			//签收单折扣
			List<PriceDiscountDto> discountPrograms= valueAddDto.getDiscountPrograms();
			if(CollectionUtils.isNotEmpty(discountPrograms)){
				for (PriceDiscountDto dt : discountPrograms) {
					WaybillDisDtlEntity disDtlEntity = new WaybillDisDtlEntity();
					//优惠项目名称
	        		disDtlEntity.setPricingEntryName(dt.getPriceEntryName());
	        		//优惠项目CODE
	        		disDtlEntity.setPricingEntryCode(dt.getPriceEntryCode());
	        		//优惠类型名称
	        		disDtlEntity.setTypeName(dt.getTypeName());
	        		//优惠类型CODE
	    			disDtlEntity.setType(dt.getType());
	        		//优惠子类型名称
	        		disDtlEntity.setSubTypeName(dt.getSaleChannelName());
	        		//优惠子类型CODE
	    			disDtlEntity.setSubType(dt.getSaleChannelCode());
	    			//折扣费率
	        		disDtlEntity.setRate(dt.getDiscountRate());
	        		//折扣金额
	        		disDtlEntity.setAmount(dt.getReduceFee().setScale(0, BigDecimal.ROUND_HALF_UP));
	        		//运单号
	        		disDtlEntity.setWaybillNo(entity.getWaybillNo());
	        		if(dt.getDiscountId()!=null){
	        			disDtlEntity.setDicountId(dt.getDiscountId());
	        		}else{
	        			disDtlEntity.setDicountId(UUIDUtils.getUUID());
	        		}
	        		if(dt.getChargeDetailId()!=null){
	        			disDtlEntity.setWaybillChargeDetailId(dt.getChargeDetailId());
	        		}else{
	        			disDtlEntity.setWaybillChargeDetailId(UUIDUtils.getUUID());
	        		}
	        		disDtlEntity.setCreateTime(new Date());
	        		disDtlEntity.setModifyTime(new Date());
	        		disDtlEntity.setActive(FossConstants.ACTIVE);
	        		disDtlEntity.setBillTime(new Date());
	        		disDtlEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
	        		//营销活动编码
	        		disDtlEntity.setActiveCode(dt.getActiveCode());
	        		//营销活动名称
	        		disDtlEntity.setActiveName(dt.getActiveName());
	        		//营销活动开始时间
	        		disDtlEntity.setActiveStartTime(dt.getActiveStartTime());
	        		//营销活动结束时间
	        		disDtlEntity.setActiveEndTime(dt.getActiveEndTime());
	        		//营销活动折扣相应的CRM_ID
	        		disDtlEntity.setOptionsCrmId(dt.getOptionsCrmId());
	        		disDtlEntityList.add(disDtlEntity);
				}
			}
		}
		return otherFee;
	}

	/**
	 * 获取其他费用明细
	 * @param entity
	 * @param chargeDtlEntityList
	 * @param otherCalculateDtos
	 * @param actualOtherFee
	 * @param otherFee
	 * @return
	 */
	private BigDecimal getOtherFeeDetail(WaybillPendingEntity entity,
			List<WaybillChargeDtlEntity> chargeDtlEntityList,
			List<GuiResultBillCalculateDto> otherCalculateDtos,
			List<String> actualOtherFee, BigDecimal otherFee) {
		/**
		 * 添加综合信息费
		 */
		if(CollectionUtils.isNotEmpty(otherCalculateDtos)){
			for(GuiResultBillCalculateDto dto:otherCalculateDtos){
				String code =dto.getSubType();
				if(PriceEntityConstants.PRICING_CODE_ZHXX.equals(code)&&FossConstants.NO.equals(dto.getCandelete())){
					WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
					chargeEntity.setId(UUIDUtils.getUUID());
					chargeEntity.setPricingEntryCode(code);
					chargeEntity.setAmount(dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP));
					chargeEntity.setWaybillNo(entity.getWaybillNo());
					chargeEntity.setPricingCriDetailId(dto.getId());
					chargeEntity.setActive(FossConstants.ACTIVE);
					chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
					otherFee=otherFee.add(chargeEntity.getAmount());
					chargeDtlEntityList.add(chargeEntity);
					actualOtherFee.add(code);
					break;
				}
			}
		}
		
		/** 
		 * 燃油附加费  
		 */
		if(CollectionUtils.isNotEmpty(otherCalculateDtos)){
			for(GuiResultBillCalculateDto dto:otherCalculateDtos){
				String code =dto.getSubType();
				if(PriceEntityConstants.PRICING_CODE_RYFJ.equals(code)&&FossConstants.NO.equals(dto.getCandelete())){
					WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
					chargeEntity.setId(UUIDUtils.getUUID());
					chargeEntity.setPricingEntryCode(code);
					chargeEntity.setAmount(dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP));
					chargeEntity.setWaybillNo(entity.getWaybillNo());
					chargeEntity.setPricingCriDetailId(dto.getId());
					chargeEntity.setActive(FossConstants.ACTIVE);
					chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
					otherFee=otherFee.add(chargeEntity.getAmount());
					chargeDtlEntityList.add(chargeEntity);
					actualOtherFee.add(code);
					break;
				}
			}
		}
		
		/**
		 * 其他自定义的费用
		 */
		if(CollectionUtils.isNotEmpty(otherCalculateDtos)){
			for(GuiResultBillCalculateDto dto:otherCalculateDtos){
				String code =dto.getSubType();
				//对于非“综合信息服务费”、“燃油附加费”、“超重货操作服务费”，并且费用为不可删除的，添加到其他费用中（其除掉超远派送加收）
				if(!PriceEntityConstants.PRICING_CODE_ZHXX.equals(code)&&!PriceEntityConstants.PRICING_CODE_RYFJ.equals(code)
					&&!PRICING_CODE_CZHCZFWF.equals(code)&&
					!PriceEntityConstants.PRICING_CODE_CCDDJS.equals(code)&&
					!PriceEntityConstants.PRICING_CODE_DWTBF.equals(code)&&
					FossConstants.NO.equals(dto.getCandelete())){
					WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
					chargeEntity.setId(UUIDUtils.getUUID());
					chargeEntity.setPricingEntryCode(code);
					chargeEntity.setAmount(dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP));
					chargeEntity.setWaybillNo(entity.getWaybillNo());
					chargeEntity.setPricingCriDetailId(dto.getId());
					chargeEntity.setActive(FossConstants.ACTIVE);
					chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
					otherFee=otherFee.add(chargeEntity.getAmount());
					chargeDtlEntityList.add(chargeEntity);
					actualOtherFee.add(code);
				}
			}
		}
		return otherFee;
	}

	/**
	 * 添加其他费用（除综合信息费、超重服务费），其他费用是开单时暂存的
	 * @param entity
	 * @param chargePendList
	 * @param chargeDtlEntityList
	 * @param otherFee
	 * @return
	 */
	private BigDecimal handleChargePendList(WaybillPendingEntity entity,
			List<WaybillCHDtlPendingEntity> chargePendList,
			List<WaybillChargeDtlEntity> chargeDtlEntityList,
			BigDecimal otherFee) {
		if (CollectionUtils.isNotEmpty(chargePendList)) {
			for(WaybillCHDtlPendingEntity chDtl:chargePendList){
				String code = chDtl.getPricingEntryCode();
				if(PriceEntityConstants.PRICING_CODE_FRT.equals(code)
						||
						PriceEntityConstants.PRICING_CODE_BF.equals(code)
						||
						PriceEntityConstants.PRICING_CODE_HK.equals(code)
						||
						PriceEntityConstants.PRICING_CODE_SH.equals(code)
						||
						PriceEntityConstants.PRICING_CODE_SHSL.equals(code)
						||
						PriceEntityConstants.PRICING_CODE_SHJC.equals(code)
						||
						PriceEntityConstants.PRICING_CODE_DJSL.equals(code)
						||
						PriceEntityConstants.PRICING_CODE_BZ.equals(code)
						||
						PriceEntityConstants.PRICING_CODE_QS.equals(code)
						||
						PriceEntityConstants.PRICING_CODE_JH.equals(code)
						||
						PriceEntityConstants.PRICING_CODE_RYFJ.equals(code)
						||
						PriceEntityConstants.PRICING_CODE_ZHXX.equals(code)
						||
						PriceEntityConstants.QT_CODE_CZHCZFWF.equals(code)
						||
				        PriceEntityConstants.QT_CODE_CZHCZFWF_SDTJ.equals(code)
						){
					continue;
				}
				WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
				chargeEntity.setId(UUIDUtils.getUUID());
				chargeEntity.setPricingEntryCode(code);
				chargeEntity.setAmount(chDtl.getAmount());
				chargeEntity.setWaybillNo(entity.getWaybillNo());
				chargeEntity.setPricingCriDetailId(UUIDUtils.getUUID());
				chargeEntity.setActive(FossConstants.ACTIVE);
				chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
				otherFee=otherFee.add(chargeEntity.getAmount());
				chargeDtlEntityList.add(chargeEntity);
			}
				
		}
		return otherFee;
	}
	
	/**
	 * 签收单费用
	 * @author 076234
	 */
	private ValueAddDto setReturnBillCollection(WaybillPendingEntity entity){
		ValueAddDto valueAddDto=null;
		String returnBillType = entity.getReturnBillType();
		if (!WaybillConstants.NOT_RETURN_BILL.equals(returnBillType)) {
			String type = "";
			// 到达联传真要转成传真类型
			if (WaybillConstants.RETURNBILLTYPE_ARRIVE.equals(returnBillType)) {
				type = WaybillConstants.RETURNBILLTYPE_FAX;
			} else {
				type = returnBillType;
			}
			List<ValueAddDto> list = billCaculateService.searchValueAddPriceList(getQueryOtherChargeParam(entity, type));
			
			if(CollectionUtils.isNotEmpty(list)){
				valueAddDto=list.get(0);
			}
		}
		return valueAddDto;
	}
	
	
	/**
	 * 
	 * 获取其他费用查询参数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:02:10
	 */
	public  QueryBillCacilateValueAddDto getQueryOtherChargeParam(WaybillPendingEntity entity, String type) {
		QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
		queryDto.setOriginalOrgCode(entity.getReceiveOrgCode());// 出发部门CODE
		queryDto.setDestinationOrgCode(entity.getCustomerPickupOrgCode());// 到达部门CODE
		queryDto.setProductCode(entity.getProductCode());// 产品CODE
		queryDto.setGoodsTypeCode(null);// 货物类型CODE
		queryDto.setReceiveDate(entity.getBillTime());// 营业部收货日期（可选，无则表示当前日期）
		queryDto.setCustomerCode(entity.getDeliveryCustomerCode());
		if(entity.getGoodsWeightTotal() == null)
		{
			queryDto.setWeight(BigDecimal.ZERO);// 重量
		}else
		{
			queryDto.setWeight(entity.getGoodsWeightTotal());// 重量
		}

		if(entity.getGoodsVolumeTotal() == null)
		{
			queryDto.setVolume(BigDecimal.ZERO);// 体积
		}else
		{
			queryDto.setVolume(entity.getGoodsVolumeTotal());// 体积
		}
		queryDto.setOriginnalCost(BigDecimal.ZERO);// 原始费用
		queryDto.setFlightShift(null);// 航班号
		EffectiveDto effectiveDto = waybillManagerService.searchPreSelfPickupTime(entity.getReceiveOrgCode(), entity.getLastLoadOrgCode(), entity.getProductCode(), entity.getPreDepartureTime(), new Date());
		if(effectiveDto!=null){
			queryDto.setLongOrShort(effectiveDto.getLongOrShort());// 长途 还是短途
		}
		// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setSubType(type);
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		if (WaybillConstants.NOT_RETURN_BILL.equals(entity.getReturnBillType())) {// 判断有无返单类型
			queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_QT);// 计价条目CODE
																				// 其他费用
		} else {
			queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_QS);// 计价条目CODE
																				// 签收回单
		}
		queryDto.setPricingEntryName(null);// 计价名称
		return queryDto;
	}
	
	

	/**
	 * 是否是香港
	 * @param code
	 * @return
	 */
	private  boolean  isHK(String code){
		OrgAdministrativeInfoEntity org= waybillManagerService.queryOrgInfoByCode(code);
		if(org!=null){
			String citycode = org.getCityCode();
			if("810000-1".equals(citycode)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取配置参数
	 * @param type
	 * @retur
	 * @author 076234
	 */
	private ConfigurationParamsEntity queryConfigurationParamsByOrgCode(String type,String pickupOrgCode){
		 ConfigurationParamsEntity config =configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,type,pickupOrgCode);
		return config;
	}
	
	
	/**
	 * 获取其他费用
	 * @param guiResultBillCalculateDtos
	 * @param pricingCodeQt
	 * @return
	 */
	@SuppressWarnings("serial")
	private List<GuiResultBillCalculateDto> otherCalculateDtos(
			List<GuiResultBillCalculateDto> guiResultBillCalculateDtos,
			String pricingCodeQt) {
		List<GuiResultBillCalculateDto> calculateDtos = new ArrayList<GuiResultBillCalculateDto>(){};
		for(GuiResultBillCalculateDto dto:guiResultBillCalculateDtos){
			if(pricingCodeQt.equals(dto.getPriceEntryCode())){
				calculateDtos.add(dto);
			}
		}
          
		return calculateDtos;
	}
	
	
	/**
	 * 设置付款明细
	 * @param entity
	 * @param dto
	 * @param paymentEntityList
	 * @author 076234 PanGuoYang
	 */
	private BigDecimal setWaybillPaymentEntity(GuiResultBillCalculateDto dto,
			GuiResultBillCalculateDto codCollection,
			List<DeliverChargeEntity> delivetList,
			GuiResultBillCalculateDto dtoJH,
			GuiResultBillCalculateDto insuranceCollection,
			WaybillChargeDtlEntity packageCharge,
			BigDecimal otherFee,
			WaybillPendingEntity entity,
			WaybillEntity waybillEntity,
			List<WaybillPaymentEntity> paymentEntityList,
			CouponInfoDto couponInfoDto,
			WoodenRequirePdaDto woodenPdaDto
			) {
		    paymentEntityList.clear();
		   
		    //运费
			BigDecimal	transportFee=dto.getCaculateFee();
			
			//增值费
			BigDecimal	valueAddFee= BigDecimal.ZERO;
			//优惠金额
			BigDecimal  couponFree= BigDecimal.ZERO;
			//包装费
			BigDecimal  packageFee = BigDecimal.ZERO;
			String couponRankType= null;
			if(couponInfoDto!=null && couponInfoDto.getCouponFree()!=null){
				couponFree=couponInfoDto.getCouponFree();
				couponRankType = couponInfoDto.getCouponRankType();
			}
			/**
			 * 若冲减类型为运费时，才可以对运费进行冲减
			 * MANA-1961 营销活动与优惠券编码关联
			 * 2014-04-10 026123
			 */
			if(couponFree!=null && PriceEntityConstants.PRICING_CODE_FRT.equals(couponRankType)){
				couponFree = couponFree.setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
			}else{
				couponFree=BigDecimal.ZERO;
			}
			transportFee=transportFee.subtract(couponFree);	
			//RFOSS2015052801（DN201505260016）  取消优惠券最低一票校验  --206860
			if(transportFee!=null){
				//当运费小于0，将运费修改为0
				if(transportFee.compareTo(BigDecimal.ZERO) < 0){
					transportFee = BigDecimal.ZERO;
				}
			}
			
			//优惠费用
			BigDecimal promotionsFee = waybillEntity.getPromotionsFee()!=null?waybillEntity.getPromotionsFee():BigDecimal.ZERO;
			waybillEntity.setPromotionsFee(promotionsFee);
			
			//代收货款手续费
			BigDecimal codFee = BigDecimal.ZERO;
			//代收货款 
			BigDecimal codAmount = waybillEntity.getCodAmount()!=null?waybillEntity.getCodAmount():BigDecimal.ZERO;
			if(codCollection!=null){
				codFee=codCollection.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP);
				valueAddFee=valueAddFee.add(codFee);
				waybillEntity.setCodFee(codFee);
				//实际费率
				BigDecimal actualFeeRate = codCollection.getActualFeeRate();
				if(null==actualFeeRate){
					actualFeeRate=codCollection.getFeeRate();
				}
				waybillEntity.setCodRate(actualFeeRate);
			}else{
				waybillEntity.setCodFee(BigDecimal.ZERO);
				waybillEntity.setCodRate(BigDecimal.ZERO);
			}
			
			//送货费
			BigDecimal deliverFee = BigDecimal.ZERO;
			if(CollectionUtils.isNotEmpty(delivetList)){
				for(DeliverChargeEntity deliver:delivetList){
					  deliverFee = deliverFee.add(deliver.getAmount());
				}
				deliverFee=deliverFee.setScale(0, BigDecimal.ROUND_HALF_UP);
				if(deliverFee.compareTo(BigDecimal.ZERO)!=0){
					waybillEntity.setDeliveryGoodsFee(deliverFee);
				}
				
				valueAddFee=valueAddFee.add(deliverFee);
				
			}
			//接货费
			if(dtoJH!=null){
				BigDecimal jh =dtoJH.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP);
				valueAddFee=valueAddFee.add(jh);
				waybillEntity.setPickupFee(jh);
			}
			//保价费
			if(insuranceCollection!=null){
				BigDecimal insurance=insuranceCollection.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP);
				valueAddFee=valueAddFee.add(insurance);
				waybillEntity.setInsuranceFee(insurance);
				//实际费率
				BigDecimal actualFeeRate = insuranceCollection.getActualFeeRate();
				if(null==actualFeeRate){
					actualFeeRate=insuranceCollection.getFeeRate();
				}
				waybillEntity.setInsuranceRate(actualFeeRate);
			}else{
				waybillEntity.setInsuranceFee(BigDecimal.ZERO);
				waybillEntity.setInsuranceRate(BigDecimal.ZERO);
			}
			
			//包装费
			if(packageCharge!=null){
				packageFee=packageCharge.getAmount();
			}
			waybillEntity.setPackageFee(packageFee.setScale(0, BigDecimal.ROUND_HALF_UP));
			valueAddFee=valueAddFee.add(packageFee.setScale(0, BigDecimal.ROUND_HALF_UP));
			
			//其他费用
			if(otherFee.compareTo(BigDecimal.ZERO)>0){
				otherFee=otherFee.setScale(0, BigDecimal.ROUND_HALF_UP);
				valueAddFee=valueAddFee.add(otherFee);
				waybillEntity.setOtherFee(otherFee);
			}
			
			//增值费用
			waybillEntity.setValueAddFee(valueAddFee);
			
			
			//计费规则  
			setWaybillUnitPrice(dto, entity, waybillEntity, transportFee);
			
			//公布价运费
			transportFee=transportFee.setScale(0, BigDecimal.ROUND_HALF_UP);
			//公布价运费
			waybillEntity.setTransportFee(transportFee);
			//总费用   =公布价运费+增值费用(增值费用=代收货款手续费+送货费+接货费+保价费+打木架费+打木箱费+其他费用)+代收货款
			BigDecimal totalFee=(transportFee.add(valueAddFee).add(codAmount)).setScale(0, BigDecimal.ROUND_HALF_UP);
			waybillEntity.setTotalFee(totalFee);
			
			//判断是否为PAD导入开单
			isPdaPending(entity, paymentEntityList, totalFee);
			
			//1.当预付金额为“0”时（开单员没有输入预付金额），计算总运费后，“预付金额”与“到付金额”文本框规则不变
			if(entity.getPrePayAmount() ==null){
				entity.setPrePayAmount(BigDecimal.ZERO);
			}
			
			setToPayAmountInfo(entity, waybillEntity, paymentEntityList,
					codAmount, totalFee);
			if(null==waybillEntity.getPrePayAmount()){
				//预付金额为空时，设置为0
				waybillEntity.setPrePayAmount(BigDecimal.ZERO);
			}
			if(null==waybillEntity.getToPayAmount()){
				//到付金额为空时，设置为0
				waybillEntity.setToPayAmount(BigDecimal.ZERO);
			}
			return  totalFee;
		
	}

	/**
	 * 设置预付到付相关信息
	 * @param entity
	 * @param waybillEntity
	 * @param paymentEntityList
	 * @param codAmount
	 * @param totalFee
	 */
	private void setToPayAmountInfo(WaybillPendingEntity entity,
			WaybillEntity waybillEntity,
			List<WaybillPaymentEntity> paymentEntityList, BigDecimal codAmount,
			BigDecimal totalFee) {
		if(entity.getPrePayAmount().equals(BigDecimal.ZERO) ){
		if(entity.getPaidMethod()!=null){
			if (WaybillConstants.ARRIVE_PAYMENT.equals(entity.getPaidMethod())) {
				WaybillPaymentEntity toPayAmount = new WaybillPaymentEntity();
				toPayAmount.setWaybillNo(entity.getWaybillNo());
				toPayAmount.setType(WaybillConstants.PAYMENT_TO_PAY);// 到付金额类型
				toPayAmount.setAmount(totalFee);// 到付金额
				toPayAmount.setActive(FossConstants.ACTIVE);
				toPayAmount.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);// RMB
				toPayAmount.setPaymentTime(new Date());
				waybillEntity.setToPayAmount(toPayAmount.getAmount());
				paymentEntityList.add(toPayAmount);
			} else {
				//预付
				WaybillPaymentEntity prementEntity = new WaybillPaymentEntity();
				prementEntity.setWaybillNo(entity.getWaybillNo());
				prementEntity.setType(WaybillConstants.PAYMENT_PRE_PAY);//预付金额类型
				prementEntity.setAmount(totalFee.subtract(codAmount));//预付金额
				prementEntity.setActive(FossConstants.ACTIVE);
				prementEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);//RMB
				prementEntity.setPaymentTime(new Date());
				waybillEntity.setPrePayAmount(prementEntity.getAmount());
				paymentEntityList.add(prementEntity);
				//到付
				WaybillPaymentEntity toPayAmount = new WaybillPaymentEntity();
				toPayAmount.setWaybillNo(entity.getWaybillNo());
				toPayAmount.setType(WaybillConstants.PAYMENT_TO_PAY);//到付金额类型
				toPayAmount.setAmount(codAmount);//到付金额
				toPayAmount.setActive(FossConstants.ACTIVE);
				toPayAmount.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);//RMB
				toPayAmount.setPaymentTime(new Date());
				waybillEntity.setToPayAmount(toPayAmount.getAmount());
				paymentEntityList.add(toPayAmount);								
			}
		}else{
			WaybillPaymentEntity prePayAmount = new WaybillPaymentEntity();
			prePayAmount.setWaybillNo(entity.getWaybillNo());
			prePayAmount.setType(WaybillConstants.PAYMENT_PRE_PAY);//预付金额类型
			prePayAmount.setAmount(totalFee);//预付金额
			prePayAmount.setActive(FossConstants.ACTIVE);
			prePayAmount.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);//RMB
			prePayAmount.setPaymentTime(new Date());
			waybillEntity.setPrePayAmount(prePayAmount.getAmount());
			paymentEntityList.add(prePayAmount);
		}
		}
		//2.当预付金额不为“0”时（开单员输入预付金额数字）
		else{
			//(1)当预付金额≤总运费时，foss自动生成运单，到付金额=总运费-预付金额
			//(2)当预付金额>总运费（不含代收货款）时，此单生成异常运单，且提醒“预付金额大于总运费（不含代收货款），请核实更改”
			BigDecimal prePayAmounts = entity.getPrePayAmount();
			if (entity.getPrePayAmount().compareTo(totalFee.subtract(codAmount)) <= 0) {
				//预付
				//huangwei 2015.11.14 付款方式为非到付的运单，若图片录入时预付金额≠0，取消（到付金额=总运费-预付金额）规则；即：非到付的运单，补录重量体积后，不生成到付金额（除含有代收货款运单）
				if(entity.getPaidMethod()!=null && WaybillConstants.ARRIVE_PAYMENT.equals(entity.getPaidMethod())){
					//预付
					WaybillPaymentEntity prementEntity = new WaybillPaymentEntity();
					prementEntity.setWaybillNo(entity.getWaybillNo());
					prementEntity.setType(WaybillConstants.PAYMENT_PRE_PAY);//预付金额类型
					prementEntity.setAmount(prePayAmounts);//预付金额
					prementEntity.setActive(FossConstants.ACTIVE);
					prementEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);//RMB
					prementEntity.setPaymentTime(new Date());
					waybillEntity.setPrePayAmount(prementEntity.getAmount());
					paymentEntityList.add(prementEntity);
				//到付
					WaybillPaymentEntity toPayAmount = new WaybillPaymentEntity();
					toPayAmount.setWaybillNo(entity.getWaybillNo());
					toPayAmount.setType(WaybillConstants.PAYMENT_TO_PAY);//到付金额类型
					toPayAmount.setAmount(totalFee.subtract(prePayAmounts));//到付金额
					toPayAmount.setActive(FossConstants.ACTIVE);
					toPayAmount.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);//RMB
					toPayAmount.setPaymentTime(new Date());
					waybillEntity.setToPayAmount(toPayAmount.getAmount());
					paymentEntityList.add(toPayAmount);		
				}else{
					WaybillPaymentEntity prePayAmount = new WaybillPaymentEntity();
					prePayAmount.setWaybillNo(entity.getWaybillNo());
					prePayAmount.setType(WaybillConstants.PAYMENT_PRE_PAY);//预付金额类型
					prePayAmount.setAmount(totalFee);//预付金额
					prePayAmount.setActive(FossConstants.ACTIVE);
					prePayAmount.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);//RMB
					prePayAmount.setPaymentTime(new Date());
					waybillEntity.setPrePayAmount(prePayAmount.getAmount());
					paymentEntityList.add(prePayAmount);
				}
			}else{
				throw new PdaInterfaceException("预付金额大于总运费（不含代收货款），请核实更改");
			}
		}
	}

	/**
	 * 判断是否为PAD导入开单
	 * @param entity
	 * @param paymentEntityList
	 * @param totalFee
	 */
	private void isPdaPending(WaybillPendingEntity entity,
			List<WaybillPaymentEntity> paymentEntityList, BigDecimal totalFee) {
		if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(entity.getPendingType())) {
			WaybillPaymentEntity totalFeePDA = new WaybillPaymentEntity();
			totalFeePDA.setWaybillNo(entity.getWaybillNo());
			totalFeePDA.setType(WaybillConstants.PAYMENT_PDA_TOTAL_PAY);//pda总运费类型
			totalFeePDA.setAmount(totalFee);
			totalFeePDA.setActive(FossConstants.ACTIVE);
			totalFeePDA.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);//RMB
			totalFeePDA.setPaymentTime(new Date());
			paymentEntityList.add(totalFeePDA);
			
		}
	}

	/**
	 * 设置计费规则
	 * @param dto
	 * @param entity
	 * @param waybillEntity
	 * @param transportFee
	 */
	private void setWaybillUnitPrice(GuiResultBillCalculateDto dto,
			WaybillPendingEntity entity, WaybillEntity waybillEntity,
			BigDecimal transportFee) {
		String caculateType =dto.getCaculateType();
		waybillEntity.setBillingType(caculateType);
		if(ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(waybillEntity.getProductCode())){
			if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(caculateType)){
				BigDecimal  volumeTotal = waybillEntity.getGoodsVolumeTotal();
				volumeTotal=volumeTotal.multiply(FossConstants.VOLUME_TO_WEIGHT);
				waybillEntity.setUnitPrice(transportFee.divide(volumeTotal,2, BigDecimal.ROUND_HALF_DOWN));
			}
			if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(caculateType)){
				waybillEntity.setUnitPrice(transportFee.divide(entity.getGoodsWeightTotal(),2, BigDecimal.ROUND_HALF_DOWN));
			}
		}else{
			//运费费率
			if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(caculateType)){
				waybillEntity.setUnitPrice(transportFee.divide(entity.getGoodsWeightTotal(),2, BigDecimal.ROUND_HALF_DOWN));
			}
			if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(caculateType)){
				waybillEntity.setUnitPrice(transportFee.divide(entity.getGoodsVolumeTotal(),2, BigDecimal.ROUND_HALF_DOWN));
			}
		}
	}
	
	/**
	 * 获取优惠券信息
	 * @param chargeList
	 * @param pendingEntity
	 * @author 076234 PanGuoYang
	 */
	private CouponInfoDto getCouponInfoDto(
			List<WaybillChargeDtlEntity> chargeList,
			BigDecimal totalFee,
			WaybillPendingEntity entity,
			List<WaybillDisDtlEntity> disDtlEntityList,
			GuiResultBillCalculateDto gDto) {
		// 优惠信息DTO
		CouponInfoDto couponInfo = new CouponInfoDto();
		if (!StringUtils.isEmpty(entity.getPromotionsCode())) {
			/**
			 * 不是整车才处理优惠券，因为整车没有走货路径，获取最终配载部门时会报异常			
			 */
			if(!FossConstants.ACTIVE.equals(entity.getIsWholeVehicle())){
				//处理优惠卷
				handleCoupons(chargeList, totalFee, entity, couponInfo);					
			}
		}
		if (couponInfo.getCouponNumber()!=null ) {

			CouponInfoResultDto dto = crmOrderService.validateCoupon(couponInfo);
			if (dto != null) {
				if (!dto.isCanUse()) {			
					
					String canNotUseReason = StringUtil.defaultIfNull(dto.getCanNotUseReason());
					String waybill = StringUtils.substringBetween(canNotUseReason, ":", ";");

					// 判断：该优惠券是否是被该运单使用的（从不可使用原因的字符串中截取使用该优惠券的运单号）
					if (!entity.getWaybillNo().equals(StringUtil.defaultIfNull(waybill).trim())) {
						// 不能使用优惠券的原因
						//MsgBox.showInfo(canNotUseReason);
						couponInfo.setCouponNumber(null);
						couponInfo.setCouponFree(BigDecimal.ZERO);
						couponInfo.setRemark(canNotUseReason);
					}else{
						String lastAmount = StringUtils.substringAfterLast(canNotUseReason, "value:");
						lastAmount = 	StringUtils.substringBeforeLast(lastAmount, ")");
						lastAmount = 	StringUtils.substringBeforeLast(lastAmount, "}");
						
						/**
						 * 设置优惠券费用
						 */
						try{
							couponInfo.setCouponFree(new BigDecimal(lastAmount));							
						}catch(Exception e){
							couponInfo.setCouponFree(BigDecimal.ZERO);
						}
						/**
						 * 设置优惠券返回实体
						 */
						//bean.setCouponInfoDto(couponInfoDto);
						
						/**
						 * 设置优惠券冲减类型
						 * MANA-1961 营销活动与优惠券编码关联
						 * 2014-04-10 026123
						 */
						couponInfo.setCouponRankType(dto.getDeductibleType());
					}
					
				} else {
					// 优惠金额
					if (dto.getCouponAmount() != null) {
						/**
						 * 设置优惠券费用
						 */
						couponInfo.setCouponFree(dto.getCouponAmount());
						/**
						 * 设置优惠券返回实体
						 */
						//bean.setCouponInfoDto(couponInfoDto);
						
						/**
						 * 设置优惠券冲减类型
						 * MANA-1961 营销活动与优惠券编码关联
						 * 2014-04-10 026123
						 */
						couponInfo.setCouponRankType(dto.getDeductibleType());
					} else {
						couponInfo.setCouponNumber(null);
						//bean.setPromotionsCode("");
					}
				}
				
			}
			
		}
		//优化折扣
		optimizeDiscount(entity, disDtlEntityList, gDto, couponInfo);
		if(couponInfo.getCouponFree()==null || BigDecimal.ZERO.compareTo(couponInfo.getCouponFree()) == 0){
			couponInfo.setUsed(false);
		}
		return couponInfo;	
	}

	/**
	 * 处理优惠卷信息
	 * @param chargeList
	 * @param totalFee
	 * @param entity
	 * @param couponInfo
	 */
	private void handleCoupons(List<WaybillChargeDtlEntity> chargeList,
			BigDecimal totalFee, WaybillPendingEntity entity,
			CouponInfoDto couponInfo) {
		/**
		 * 不是内部发货，才处理优惠券
		 */
		if(StringUtil.isBlank(entity.getInternalDeliveryType())
				|| StringUtil.isBlank(entity.getEmployeeNo()))
		{
		
//		new CouponInfoDto();
		// 处理优惠券
		// 运单信息
		CouponWaybillInfoDto waybillInfo = new CouponWaybillInfoDto();
		// 运单号
		waybillInfo.setWaybillNumber(entity.getWaybillNo());
		// 产品号
		waybillInfo.setProduceType(entity.getProductCode());
		// 订单号
		waybillInfo.setOrderNumber(entity.getOrderNo());
		// 判断总金额是否已有
		if (totalFee != null && totalFee.compareTo(BigDecimal.ZERO) == 0) {
			throw new PdaInterfaceException("计算总金额为0！");
		}

		// 总金额
		waybillInfo.setTotalAmount(totalFee);
		// 发货人手机
		waybillInfo.setLeaveMobile(entity.getDeliveryCustomerMobilephone());
		// 发货人电话
		waybillInfo.setLeavePhone(entity.getDeliveryCustomerPhone());
		// 客户编码
		waybillInfo.setCustNumber(entity.getDeliveryCustomerCode());
		// 获取出发部门
		String receiveOrgCode = entity.getReceiveOrgCode();

		OrgAdministrativeInfoEntity receiveOrgAdministrative = waybillManagerService.queryOrgInfoByCode(receiveOrgCode);

		if (receiveOrgAdministrative == null) {
			throw new PdaInterfaceException("未查询出有效的出发部门！");
		}

		// 发货部门-标杆编码
		waybillInfo.setLeaveDept(receiveOrgAdministrative.getUnifiedCode());

		if (entity.getLastLoadOrgCode() == null) {
			throw new PdaInterfaceException("未获取到有效的最终配载部门编码！");
		}
		// 最终配载部门-也就是最后一个自有网点
		String lastLoadOrgCode = entity.getLastLoadOrgCode();

		OrgAdministrativeInfoEntity lastLoadOrgAdministrative = waybillManagerService.queryOrgInfoByCode(lastLoadOrgCode);
		if (lastLoadOrgAdministrative == null) {
			throw new PdaInterfaceException("未查询出有效的最终配载部门！");
		}
		if (entity.getLoadOrgCode() == null) {
			throw new PdaInterfaceException("未获取到有效的始发配载部门编码！");
		}

		// 始发配载部门
		String firstLoadOutOrgInfoCode = waybillManagerService.queryOrgInfoByCode(entity.getLoadOrgCode()).getUnifiedCode();
		// 最终配载部门 标杆编码
		String lastLoadOutOrgInfoCode = null;
		if (!StringUtils.isEmpty(entity.getLastOutLoadOrgCode())) {
			// 获取最终配载部门 标杆编码
			lastLoadOutOrgInfoCode =  waybillManagerService.queryOrgInfoByCode(entity.getLastOutLoadOrgCode()).getUnifiedCode();
		} else {

			throw new PdaInterfaceException("最终配载部门编码为空");
		}

		// 到达部门-标杆编码-由于偏线最后到达部门是代理，这里是最后一个只有部门
		waybillInfo.setArrivedDept(lastLoadOrgAdministrative.getUnifiedCode());

		// 发货部门所在外场
		waybillInfo.setLeaveOutDept(firstLoadOutOrgInfoCode);
		// 到达部门所在外场
		waybillInfo.setArrivedOutDept(lastLoadOutOrgInfoCode);

		//WaybillOtherCharge model = (WaybillOtherCharge) ui.incrementPanel.getTblOther().getModel();
		// 获取费用明细 
		//List<WaybillChargeDtlEntity> waybillChargeDtlEntitys = WaybillDtoFactory.getWaybillChargeDtlEntity(model, bean);
		handleWaybillCharge(chargeList, entity, couponInfo, waybillInfo);
		}
	}

	/**
	 * 处理计费明细
	 * @param chargeList
	 * @param entity
	 * @param couponInfo
	 * @param waybillInfo
	 */
	private void handleWaybillCharge(List<WaybillChargeDtlEntity> chargeList,
			WaybillPendingEntity entity, CouponInfoDto couponInfo,
			CouponWaybillInfoDto waybillInfo) {
		List<AmountInfoDto> amountInfoList = new ArrayList<AmountInfoDto>();
		BigDecimal deliverGoodsFee= BigDecimal.ZERO;
		
		for (WaybillChargeDtlEntity waybillChargeDtlEntity : chargeList) { // 计价明细
			 		
			if(PriceEntityConstants.PRICING_CODE_SH.equals(waybillChargeDtlEntity.getPricingEntryCode())
					|| PriceEntityConstants.PRICING_CODE_SHSL.equals(waybillChargeDtlEntity.getPricingEntryCode()) 
					|| PriceEntityConstants.PRICING_CODE_SHJC.equals(waybillChargeDtlEntity.getPricingEntryCode())){
				deliverGoodsFee = deliverGoodsFee.add(waybillChargeDtlEntity.getAmount());
			}
		}
		
		for (WaybillChargeDtlEntity waybillChargeDtlEntity : chargeList) { // 计价明细
			AmountInfoDto amountInfo = new AmountInfoDto();
						
			if(PriceEntityConstants.PRICING_CODE_SH.equals(waybillChargeDtlEntity.getPricingEntryCode())
					|| PriceEntityConstants.PRICING_CODE_SHSL.equals(waybillChargeDtlEntity.getPricingEntryCode()) 
					|| PriceEntityConstants.PRICING_CODE_SHJC.equals(waybillChargeDtlEntity.getPricingEntryCode())){
				// 计价条目编码-送货费
				amountInfo.setValuationCode(PriceEntityConstants.PRICING_CODE_SH);
				// 获取通过计算得到的送货费
				amountInfo.setValuationAmonut(deliverGoodsFee);
			}else{
				// 计价条目编码-保险费
				amountInfo.setValuationCode(waybillChargeDtlEntity.getPricingEntryCode());
				// 计价金额
				amountInfo.setValuationAmonut(waybillChargeDtlEntity.getAmount());				
			}
			amountInfoList.add(amountInfo);
		}
		waybillInfo.setAmountInfoList(amountInfoList);
		couponInfo.setWaybillInfo(waybillInfo);
		couponInfo.setCouponNumber(entity.getPromotionsCode());
	}

	/**
	 * 优化折扣
	 * @param entity
	 * @param disDtlEntityList
	 * @param gDto
	 * @param couponInfo
	 */
	private void optimizeDiscount(WaybillPendingEntity entity,
			List<WaybillDisDtlEntity> disDtlEntityList,
			GuiResultBillCalculateDto gDto, CouponInfoDto couponInfo) {
		if(couponInfo.getCouponFree()!=null && BigDecimal.ZERO.compareTo(couponInfo.getCouponFree()) != 0){
			couponInfo.setUsed(true);
			WaybillDisDtlEntity disDtlEntity = new WaybillDisDtlEntity();
			disDtlEntity.setId(UUIDUtils.getUUID());
			disDtlEntity.setWaybillChargeDetailId(UUIDUtils.getUUID());
    		//优惠项目名称
    		disDtlEntity.setPricingEntryName(convertFeeToName(couponInfo.getCouponRankType()));
    		//优惠项目CODE
    		disDtlEntity.setPricingEntryCode(couponInfo.getCouponRankType());
    		//优惠类型名称
    		disDtlEntity.setTypeName("优惠券");
    		//优惠类型CODE
			disDtlEntity.setType("DISCOUNT");
			
			//RFOSS2015052801（DN201505260016）  取消优惠券最低一票校验  --206860
			BigDecimal	transportFee=gDto.getCaculateFee();
			if(transportFee != null){
				//当公布价运费不为空且公布价运费-优惠券费用小于0时，将优惠券金额设置为运费，即最大冲减为公布价运费
				if((transportFee.subtract(couponInfo.getCouponFree())).compareTo(BigDecimal.ZERO) < 0){
					disDtlEntity.setAmount(transportFee.setScale(0, BigDecimal.ROUND_HALF_UP));
				}else{
					disDtlEntity.setAmount(couponInfo.getCouponFree().setScale(0, BigDecimal.ROUND_HALF_UP));
				}
			}
    		//运单号
    		disDtlEntity.setWaybillNo(entity.getWaybillNo());
    		
    		disDtlEntity.setDicountId(entity.getPromotionsCode());
    		 
    		disDtlEntity.setCreateTime(new Date());
    		disDtlEntity.setModifyTime(new Date());
    		disDtlEntity.setActive(FossConstants.ACTIVE);
    		disDtlEntity.setBillTime(new Date());
    		disDtlEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
    		disDtlEntityList.add(disDtlEntity);
		}
	}
	
	/**
	 * 根据费用类型编码获取对应中文名
	 * MANA-1961 营销活动与优惠券编码关联
	 * 2014-04-10 076234
	 */
	public  String convertFeeToName(String code){
		 
		//费用名称
		String name = "";
		if(PriceEntityConstants.PRICING_CODE_TOTAL.equals(code)){
			name = "总费用";
		}else if(PriceEntityConstants.PRICING_CODE_FRT.equals(code)){
			name = "运费";
		}else if(PriceEntityConstants.PRICING_CODE_VALUEADDED.equals(code)){
			name = "增值服务";
		}else if(PriceEntityConstants.PRICING_CODE_BF.equals(code)){
			name = "保费";
		}else if(PriceEntityConstants.PRICING_CODE_HK.equals(code)){
			name = "代收货款";
		}else if(PriceEntityConstants.PRICING_CODE_SH.equals(code)){
			name = "送货费";
		}else if(PriceEntityConstants.PRICING_CODE_SHSL.equals(code)){
			name = "送货上楼费";
		}else if(PriceEntityConstants.PRICING_CODE_SHJC.equals(code)){
			name = "送货进仓费";
		}else if(PriceEntityConstants.PRICING_CODE_CY.equals(code)){
			name = "超远派送费";
		}else if(PriceEntityConstants.PRICING_CODE_JH.equals(code)){
			name = "接货费";
		}else if(PriceEntityConstants.PRICING_CODE_CCF.equals(code)){
			name = "仓储费";
		}else if(PriceEntityConstants.PRICING_CODE_QT.equals(code)){
			name = "其他费用";
		}else if(PriceEntityConstants.PRICING_CODE_BZ.equals(code)){
			name = "包装费用";
		}else if(PriceEntityConstants.PRICING_CODE_ZZ.equals(code)){
			name = "中转费";
		}else if(PriceEntityConstants.PRICING_CODE_ZHXX.equals(code)){
			name = "综合信息服务费";
		}else if(PriceEntityConstants.PRICING_CODE_KDBZF.equals(code)){
			name = "快递包装费";
		}
		return name;
	}
	
	
	/**
	 * 设置折扣明细
	 * @param entity
	 * @param guiResultBillCalculateDtos
	 * @param disDtlEntityList
	 * @author 076234 PanGuoYang
	 */
	private void setWaybillDisDtlEntity(WaybillEntity entity,
			List<GuiResultBillCalculateDto> dtos,
			List<WaybillDisDtlEntity> disDtlEntityList,
			List<String> actualOtherFee) {
		BigDecimal promotionsFee = BigDecimal.ZERO;
		if(CollectionUtils.isNotEmpty(dtos)){
			for(GuiResultBillCalculateDto dto:dtos){
				if(PriceEntityConstants.PRICING_CODE_QT.equals(dto.getPriceEntryCode())&&!actualOtherFee.contains(dto.getSubType())){
					//如果是其他费用，并且费用子类型不在实际收集的其他费用项中，不收取相关折扣费用
					continue;
				}
				List<GuiResultDiscountDto> discountPrograms =dto.getDiscountPrograms();
				if(CollectionUtils.isNotEmpty(discountPrograms)){
					for(GuiResultDiscountDto dt:discountPrograms){
						WaybillDisDtlEntity disDtlEntity = new WaybillDisDtlEntity();
						//优惠项目名称
		        		disDtlEntity.setPricingEntryName(dt.getPriceEntryName());
		        		//优惠项目CODE
		        		disDtlEntity.setPricingEntryCode(dt.getPriceEntryCode());
		        		//优惠类型名称
		        		disDtlEntity.setTypeName(dt.getDiscountTypeName());
		        		//优惠类型CODE
		    			disDtlEntity.setType(dt.getDiscountType());
		        		//优惠子类型名称
		        		disDtlEntity.setSubTypeName(dt.getSaleChannelName());
		        		//优惠子类型CODE
		    			disDtlEntity.setSubType(dt.getSaleChannelCode());
		    			//折扣费率
		        		disDtlEntity.setRate(dt.getDiscountRate());
		        		//折扣金额
		        		disDtlEntity.setAmount(dt.getReduceFee().setScale(0, BigDecimal.ROUND_HALF_UP));
		        		//运单号
		        		disDtlEntity.setWaybillNo(entity.getWaybillNo());
		        		if(dt.getDiscountId()!=null){
		        			disDtlEntity.setDicountId(dt.getDiscountId());
		        		}else{
		        			disDtlEntity.setDicountId(UUIDUtils.getUUID());
		        		}
		        		if(dt.getChargeDetailId()!=null){
		        			disDtlEntity.setWaybillChargeDetailId(dt.getChargeDetailId());
		        		}else{
		        			disDtlEntity.setWaybillChargeDetailId(UUIDUtils.getUUID());
		        		}
		        		disDtlEntity.setCreateTime(new Date());
		        		disDtlEntity.setModifyTime(new Date());
		        		disDtlEntity.setActive(FossConstants.ACTIVE);
		        		disDtlEntity.setBillTime(new Date());
		        		disDtlEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		        		//营销活动编码
		        		disDtlEntity.setActiveCode(dt.getActiveCode());
		        		//营销活动名称
		        		disDtlEntity.setActiveName(dt.getActiveName());
		        		//营销活动开始时间
		        		disDtlEntity.setActiveStartTime(dt.getActiveStartTime());
		        		//营销活动结束时间
		        		disDtlEntity.setActiveEndTime(dt.getActiveEndTime());
		        		//营销活动折扣相应的CRM_ID
		        		disDtlEntity.setOptionsCrmId(dt.getOptionsCrmId());
		        		disDtlEntityList.add(disDtlEntity);
					}
				}
			}
			
		}
		//优惠总金额
		if(CollectionUtils.isNotEmpty(disDtlEntityList)){
			for(WaybillDisDtlEntity dis:disDtlEntityList){
				promotionsFee=promotionsFee.add(dis.getAmount());
			}
		}
		//优惠费用
		entity.setPromotionsFee(promotionsFee);
		
	}
	
	/**
	 * 获取运单信息
	 * @param pendingEntity
	 * @return
	 */
	private WaybillEntity waybillEntity(WaybillPendingEntity pendingEntity) {
		if(null==pendingEntity){
			throw new WaybillValidateException("获取运单信息时，运单带处理信息为空！");
		}
		// 拷贝属性值
		WaybillEntity waybillEntity = new WaybillEntity();
		try {
			if (pendingEntity != null) {
				PropertyUtils.copyProperties(waybillEntity, pendingEntity);
			}
		} catch (Exception e) {
			// 添加异常日志
			LOGGER.error("对象拷贝失败！\n原因：" + e.getMessage());
			// 抛出异常信息
			throw new WaybillValidateException("对象拷贝失败！\n原因：" + e.getMessage());
		}
		String goodsTypeCode = waybillEntity.getGoodsTypeCode();
		if ("A".equals(goodsTypeCode) || "B".equals(goodsTypeCode)) {
			// 总重量
			BigDecimal goodsWeightTotal = pendingEntity.getGoodsWeightTotal();
			// 件数
			BigDecimal goodsQtyTotal = new BigDecimal(pendingEntity.getGoodsQtyTotal());
			// 单件>50 是B货
			BigDecimal pjzl = goodsWeightTotal.divide(goodsQtyTotal, BigDecimal.ROUND_HALF_UP);
			if (pjzl.compareTo(new BigDecimal(FIFTY)) > 0) {
				waybillEntity.setGoodsTypeCode("B");
			}
			// 木包装
			Integer woodNum = pendingEntity.getWoodNum();
			if (woodNum != null && woodNum > 0) {
				waybillEntity.setGoodsTypeCode("B");
			}
		}
		waybillEntity.setCreateTime(new Date());
		return waybillEntity;
	}
	
	/**
	 * 自动生成运单前的基本验证
	 * 
	 * @param focusWaybillDto
	 */
	private void validate(WaybillPendingEntity entity, List<WaybillCHDtlPendingEntity> chargePendList,
			WoodenRequirementsEntity woodenEntity, WaybillPictureEntity pictureEntity) {
		LOGGER.info("开始自动生成运单前的基本验证："+entity.getWaybillNo());
		// 运单信息验证
		validateWaybillentity(entity);
		// 包装信息不能大于50字符
		validateGoodPackage(entity.getGoodsPackage());
		// 货物名称校验
		waybillGoogNameCheck(entity.getGoodsName());
		// 发票标记
		setInvoiceType(entity);
		// 重量体积件数验证
		validateWeightVolume(entity, woodenEntity);
		// 产品验证
		validateProduct(entity);
		// 精准大票验证
		validateIsBigGoods(entity, chargePendList);
		// 校验提货网点单票以及单件重量与体积上限
		validateVW(entity);
		// 验证是否满足大件上楼和送货上楼的条件
		validateShslOrDjsl(entity, woodenEntity);
		// 验证额度
		validateEmployeeAmount(entity);
		LOGGER.info("完成自动生成运单前的基本验证："+entity.getWaybillNo());
	}
	
	/**
	 * 验证运单信息
	 * 
	 * @param entity
	 * @author 076234
	 */
	public void validateWaybillentity(WaybillPendingEntity entity) {
		if (null == entity) {
			LOGGER.error("货物基本信息不能为空");
			throw new WaybillValidateException(WaybillValidateException.WAYBILLPENDINGENTITY_NULL);
		}
		BigDecimal volume = entity.getGoodsVolumeTotal();
		BigDecimal weigth = entity.getGoodsWeightTotal();
		if (volume == null || volume.compareTo(BigDecimal.ZERO) == 0) {
			LOGGER.error("货物体积不能为空或0");
			throw new WaybillValidateException(WaybillValidateException.NULLORZERO_GOODSVOLUME);
		}
		if (weigth == null || weigth.compareTo(BigDecimal.ZERO) == 0) {
			LOGGER.error("货物重量不能为空或0");
			throw new WaybillValidateException(WaybillValidateException.NULLORZERO_GOODSWEIGHT);
		}
		String receiveMethod = entity.getReceiveMethod();
		if (WaybillConstants.DELIVER_FREE_AIR.equals(receiveMethod)
				|| WaybillConstants.DELIVER_NOUP_AIR.equals(receiveMethod)
				|| WaybillConstants.DELIVER_UP_AIR.equals(receiveMethod)
				|| WaybillConstants.DELIVER_INGA_AIR.equals(receiveMethod)) {
			LOGGER.error("该单为空运单据且送货");
			throw new WaybillValidateException(WaybillValidateException.DELIVER_AIR);
		}
		boolean isHK = isHK(entity.getCustomerPickupOrgCode());
		if (isHK) {

			if (!WaybillConstants.SELF_PICKUP.equals(receiveMethod)) {
				LOGGER.error("目的是香港的，提货方式不为送货");
				throw new WaybillValidateException(WaybillValidateException.IS_HK_NOT_DELIVER);
			}
		}
	}
	
	/**
	 * 包装信息不能大于50字符
	 * 
	 * @author 076234
	 * @param goodsPackage
	 */
	public void validateGoodPackage(String goodsPackage) {
		try {
			if (StringUtils.isEmpty(goodsPackage)) {
				return;
			}
			String a = new String(goodsPackage.getBytes("GBK"), "ISO-8859-1");
			if (a.length() > FIFTY) {
				LOGGER.error("包装信息不能大于50字符(注意:中文占2个字符)");
				throw new WaybillValidateException(WaybillValidateException.PACKAGE_CH_OVER_50);
			}
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("包装信息不能大于50字符(注意:中文占2个字符)");
			throw new WaybillValidateException(WaybillValidateException.PACKAGE_CH_OVER_50);
		}
	}
	
	/**
	 * 货物名称不能为空
	 * 
	 * @author 076234
	 * @param goodsName
	 */
	public void waybillGoogNameCheck(String goodsName) {
		if (StringUtil.isEmpty(goodsName)) {
			LOGGER.error("货物名称不能空");
			throw new WaybillValidateException(WaybillValidateException.NULL_GOODS_NAME);
		}
	}
	
	/**
	 * 验证重量、体积、件数不能为默认值0
	 * 
	 * @author 076234
	 */
	public void validateWeightVolume(WaybillPendingEntity entity, WoodenRequirementsEntity woodenEntity) {
		if (entity.getGoodsQtyTotal().intValue() == 0 || entity.getGoodsQtyTotal().intValue() < 0) {
			LOGGER.error("件数不能小于等于0");
			throw new WaybillValidateException(WaybillValidateException.NUMBER_OF_CASES_LE_ZERO);
		}
		if (entity.getGoodsWeightTotal().compareTo(BigDecimal.ZERO) == 0) {
			LOGGER.error("货物重量不能等于0");
			throw new WaybillValidateException(WaybillValidateException.GOODSWEIGHTTOTAL_LE_ZERO);
		}
		if (entity.getGoodsVolumeTotal().compareTo(BigDecimal.ZERO) == 0) {
			LOGGER.error("货物总体积不能等于0");
			throw new WaybillValidateException(WaybillValidateException.GOODSVOLUMETOTAL_LE_ZERO);
		}
		if (woodenEntity != null) {
			//校验包装其他信息
			validateWeightVolumeInfo(entity, woodenEntity);
		}
	}

	/**
	 * 校验包装其他信息
	 * @param entity
	 * @param woodenEntity
	 */
	private void validateWeightVolumeInfo(WaybillPendingEntity entity,
			WoodenRequirementsEntity woodenEntity) {
		int boxGoodsNum = woodenEntity.getBoxGoodsNum() != null ? woodenEntity.getBoxGoodsNum() : 0;
		int standGoodsNum = woodenEntity.getStandGoodsNum() != null ? woodenEntity.getStandGoodsNum() : 0;
		if (boxGoodsNum > 0 || standGoodsNum > 0) {
			if (!FossConstants.YES.equals(entity.getDoPacking())) {
				LOGGER.error("当前线路无法代打木架，如果需要打木架请重新选择线路");
				throw new WaybillValidateException(WaybillValidateException.UNABLE_MAKE_YOKE);
			}
		}
		// 木架体积+木箱体积=总体积(上下浮动0.01F)，若不成立抛出异常
		int intBoxPieces = woodenEntity.getBoxGoodsNum() == null ? 0 : woodenEntity.getBoxGoodsNum();
		int intYokePieces = woodenEntity.getStandGoodsNum() == null ? 0 : woodenEntity.getStandGoodsNum();
		if ((intBoxPieces + intYokePieces) == entity.getGoodsQtyTotal()) {
			// 打木架体积
			BigDecimal decYokeGoodsVolume = woodenEntity.getStandGoodsVolume();
			// 打木箱体积
			BigDecimal decBoxGoodsVolume = woodenEntity.getBoxGoodsVolume();
			BigDecimal decMoodenGoodsValumn = decYokeGoodsVolume.add(decBoxGoodsVolume);
			if (decMoodenGoodsValumn.add(new BigDecimal(ZPZO)).compareTo(entity.getGoodsVolumeTotal()) < 0
					|| decMoodenGoodsValumn.subtract(new BigDecimal(ZPZO))
							.compareTo(entity.getGoodsVolumeTotal()) > 0) {
				LOGGER.error("单票全打包装时,打木架体积+打木箱体积必须等于开单总体积");
				throw new WaybillValidateException(WaybillValidateException.SINGLE_VOTE_PACKAGE_ERROR);
			}
		}
	}
	
	/**
	 * 发票标记验证
	 * 
	 * @param bean
	 * @param date
	 * @author 076234 PanGuoYany
	 */
	public void setInvoiceType(WaybillPendingEntity entity) {
		if (entity.getInvoice() == null) {
			LOGGER.error("发票标记不能为空");
			throw new WaybillValidateException(WaybillValidateException.NULL_INVOICETYPE);
		}
		// 判断是否内部带货自提
		if (WaybillConstants.INNER_PICKUP.equals(entity.getReceiveMethod())) {
			// 发票标记
			entity.setInvoice(WaybillConstants.INVOICE_02);
		}
	}

	/**
	 * 产品验证
	 * 
	 * @param waybillEntity
	 * @author 076234
	 */
	public void validateProduct(WaybillPendingEntity waybillEntity) {
		String productCode = waybillEntity.getProductCode();
		if (productCode == null) {
			LOGGER.error("运输性质不能为空");
			throw new WaybillValidateException(WaybillValidateException.NULL_PRODUCTCODE);
		}
	}

	/**
	 * 是否符合精准大票校验
	 * @param entity
	 * @param chargePendList
	 */
	public void validateIsBigGoods(WaybillPendingEntity entity, List<WaybillCHDtlPendingEntity> chargePendList) {
		String productCode = entity.getProductCode();
		if (ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT_BG.equals(productCode)
				|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT_BG.equals(productCode)
				|| ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT_BG.equals(productCode)
				|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT_BG.equals(productCode)) {
			Boolean falg = isHeavyWeight(entity);
			if (falg) {
				LOGGER.error("不符合开精准大票条件！");
				throw new WaybillValidateException(WaybillValidateException.UNABLE_RECISION_PRODUCTS_BG);
			} else {
				if (isOverweightFee(chargePendList)) {
					LOGGER.error("包超重货操作服务费不能开精准大票");
					throw new WaybillValidateException(WaybillValidateException.OVER_WEIGHTFEE_RECISION_PRODUCTS_BG);
				} else {
					BigDecimal goodsWeightTotal = entity.getGoodsWeightTotal();
					BigDecimal goodsVolumeTotal = entity.getGoodsVolumeTotal();
					if (goodsWeightTotal.compareTo(new BigDecimal(FIVE_HUNDRED)) <= 0
							&& goodsVolumeTotal.compareTo(new BigDecimal(TWOPOINTFIVE)) <= 0) {
						LOGGER.error("不符合开精准大票条件！");
						throw new WaybillValidateException(WaybillValidateException.UNABLE_RECISION_PRODUCTS_BG);
					}
				}
			}
		}
	}

	/**
	 * 是否超重货
	 * 
	 * @author 076234
	 */
	public boolean isHeavyWeight(WaybillPendingEntity entity) {
		boolean bHeavyWeight = false; // 超重货标志
		String productCode = entity.getProductCode();
		if (productCode != null && !ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productCode)) {
			if (entity.getGoodsWeightTotal() == null || entity.getGoodsQtyTotal() == null) {
				return bHeavyWeight;
			}
			BigDecimal goodsWeightTotal = entity.getGoodsWeightTotal();
			int goodsQtyTotal = entity.getGoodsQtyTotal();
			if (goodsQtyTotal == 0) {
				return bHeavyWeight;
			}
			BigDecimal unitWeight = goodsWeightTotal.divide(new BigDecimal(goodsQtyTotal), 1, BigDecimal.ROUND_HALF_UP);
			if (unitWeight.compareTo(new BigDecimal(FIVE_HUNDRED)) > 0) {
				bHeavyWeight = true;
			}
		}
		return bHeavyWeight;
	}
	
	/*
	 * 是否包含超重货操作服务费
	 */
	public  boolean isOverweightFee(List<WaybillCHDtlPendingEntity> chargePendList){
		Boolean isOverweightFee = false;
		if(CollectionUtils.isNotEmpty(chargePendList)){
			for(WaybillCHDtlPendingEntity vo : chargePendList){
				if(PriceEntityConstants.QT_CODE_CZHCZFWF.equals(vo.getPricingEntryCode()) ||
						PriceEntityConstants.QT_CODE_CZHCZFWF_SDTJ.equals(vo.getPricingEntryCode())){
					isOverweightFee = true;
				}
			}
		}
		return isOverweightFee;
	}

	/**
	 * 内部发货额度校验
	 */
	public void validateEmployeeAmount(WaybillPendingEntity waybillEntity) {
		if ((WaybillConstants.DELIVERY_PAY.equals(waybillEntity.getInternalDeliveryType())
				|| WaybillConstants.RECIVE_PAY.equals(waybillEntity.getInternalDeliveryType()))
				&& StringUtil.isNotBlank(waybillEntity.getEmployeeNo())) {
			InempDiscountPlanEntity entity = new InempDiscountPlanEntity();
			entity.setBillTime(waybillEntity.getBillTime());
			entity.setActive(FossConstants.ACTIVE);
			List<InempDiscountPlanEntity> list = inempDiscountPlanService.queryInempDiscountPanByCondition(entity);
			if (CollectionUtils.isNotEmpty(list)) {
				InempDiscountPlanEntity inempDiscountPlanEntity = list.get(0);
				if (inempDiscountPlanEntity.getHighstPreferentialLimit() != null
						&& inempDiscountPlanEntity.getHighstPreferentialLimit().compareTo(BigDecimal.ZERO) > 0) {
					BigDecimal amount = waybillManagerService
							.queryDiscountFeeByEmployeeNo(waybillEntity.getEmployeeNo(), waybillEntity.getBillTime());
					if (amount == null) {
						amount = BigDecimal.ZERO;
					}
					BigDecimal differenceValue = inempDiscountPlanEntity.getHighstPreferentialLimit()
							.subtract(amount.divide(BigDecimal.valueOf(ONE_HUNDRED)));
					if (differenceValue.compareTo(BigDecimal.ZERO) <= 0) {
						LOGGER.error("内部发货额度校验,此工号:"+waybillEntity.getEmployeeNo()+"优惠额度已用完，不能开内部发货运单");
						throw new WaybillValidateException(WaybillValidateException.OVER_HIGHST_PREFERENTIAL);
					}
				} else {
					LOGGER.error("内部发货额度校验,此工号:"+waybillEntity.getEmployeeNo()+"优惠额度已用完，不能开内部发货运单");
					throw new WaybillValidateException(WaybillValidateException.OVER_HIGHST_PREFERENTIAL);
				}
			} else {
				LOGGER.error("内部发货额度校验,此工号:"+waybillEntity.getEmployeeNo()+"优惠额度已用完，不能开内部发货运单");
				throw new WaybillValidateException(WaybillValidateException.OVER_HIGHST_PREFERENTIAL);
			}
		}
	}
	
	/**
	 * 
	 * 校验提货网点单票以及单件重量与体积上限
	 * 
	 * @author 076234
	 */
	public void validateVW(WaybillPendingEntity entity) {
		// 对内备注
		int innerNotes = StringUtil.defaultIfNull(entity.getInnerNotes()).length();
		// 储运事项
		int transNotes = StringUtil.defaultIfNull(entity.getTransportationRemark()).length();
		if (innerNotes > TTS) {
			LOGGER.info("对内备注录入错误：最大字符不能超过1300个");
			throw new WaybillValidateException(WaybillValidateException.INNERNOTES_CH_OVER_1300);
		}
		if (transNotes > TTS) {
			// 储运事项 录入错误：最大字符不能超过1300个！
			LOGGER.error("储运事项 录入错误：最大字符不能超过1300个！");
			throw new WaybillValidateException(WaybillValidateException.TRANSNOTES_CH_OVER_1300);
		}
//		BigDecimal goodsWeight = entity.getGoodsWeightTotal();// 总重量
//		BigDecimal goodsVolume = entity.getGoodsVolumeTotal();// 总体积
//		BigDecimal goodsQty = new BigDecimal(entity.getGoodsQtyTotal());// 总件数
//		BigDecimal pieceWeight = goodsWeight.divide(goodsQty, 2, BigDecimal.ROUND_HALF_EVEN);// 单件重量
//		BigDecimal pieceVolume = goodsVolume.divide(goodsQty, 2, BigDecimal.ROUND_HALF_EVEN);// 单件体积
//		String customerPickupOrgCode = entity.getCustomerPickupOrgCode();
//		String productCode = entity.getProductCode();
//		SaleDepartmentEntity saleDepartment = getCustomerPickupOrg(customerPickupOrgCode, productCode, new Date());
		/**
		 * 整车不校验重量和体积
		 * 电子运单项目 不校验到达部门重量与体积
		 */
//		validateWeightVolumeLimit(entity, goodsWeight, goodsVolume,
//				pieceWeight, pieceVolume, saleDepartment);
	}

	/**
	 * 校验到达部门重量与体积
	 * @param entity
	 * @param goodsWeight
	 * @param goodsVolume
	 * @param pieceWeight
	 * @param pieceVolume
	 * @param saleDepartment
	 */
	@SuppressWarnings("unused")
	private void validateWeightVolumeLimit(WaybillPendingEntity entity,
			BigDecimal goodsWeight, BigDecimal goodsVolume,
			BigDecimal pieceWeight, BigDecimal pieceVolume,
			SaleDepartmentEntity saleDepartment) {
		
		if (saleDepartment != null && !FossConstants.ACTIVE.equals(entity.getIsWholeVehicle())) {
			if (saleDepartment.getSinglePieceLimitkg() != null) {
				// 单件重量上限
				BigDecimal singlePieceLimitkg = BigDecimal.valueOf(saleDepartment.getSinglePieceLimitkg())
						.divide(new BigDecimal(ONE_HUNDRED));
				if (pieceWeight.compareTo(singlePieceLimitkg) > 0) {
					LOGGER.error("当前运单单件重量超出该提货网点单件重量上限，请选择其他网点(网点单件重量上限为:" + singlePieceLimitkg + ")");
					throw new WaybillValidateException(
							WaybillValidateException.OVER_SINGLETON_WEIGHT_LIMIT);
				}
			}

			if (saleDepartment.getSinglePieceLimitvol() != null) {
				// 单件体积上限
				BigDecimal singlePieceLimitvol = BigDecimal.valueOf(saleDepartment.getSinglePieceLimitvol())
						.divide(new BigDecimal(ONE_HUNDRED));
				if (pieceVolume.compareTo(singlePieceLimitvol) > 0) {
					LOGGER.error("当前运单单件体积超出该提货网点单件体积上限，请选择其他网点");
					throw new WaybillValidateException(WaybillValidateException.OVER_SINGLETON_VOLUME_LIMIT);
				}
			}

			if (saleDepartment.getSingleBillLimitkg() != null) {
				// 单票重量上限
				BigDecimal singleBillLimitkg = BigDecimal.valueOf(saleDepartment.getSingleBillLimitkg())
						.divide(new BigDecimal(ONE_HUNDRED));
				if (goodsWeight.compareTo(singleBillLimitkg) > 0) {
					LOGGER.error("当前运单总重量超出该提货网点单票重量上限，请选择其他网点");
					throw new WaybillValidateException(WaybillValidateException.OVER_TOTAL_WEIGHT_LIMIT);
				}
			}

			if (saleDepartment.getSingleBillLimitvol() != null) {
				// 单票体积上限
				BigDecimal singleBillLimitvol = BigDecimal.valueOf(saleDepartment.getSingleBillLimitvol())
						.divide(new BigDecimal(ONE_HUNDRED));
				if (goodsVolume.compareTo(singleBillLimitvol) > 0) {
					LOGGER.error("当前运单总体积超出该提货网点单票体积上限，请选择其他网点");
					throw new WaybillValidateException(WaybillValidateException.OVER_TOTAL_VOLUME_LIMIT);
				}
			}
		}
	}
	
	/**
	 * 获取提货网点信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-24 下午3:34:24
	 */
	public SaleDepartmentEntity getCustomerPickupOrg(String customerPickupOrgCode, String productCode, Date billTime) {
		if (customerPickupOrgCode != null && productCode != null) {
			// 根据产品来判断是查询自有网点还是代理网点
			if (!isAgentDept(productCode)) {
				return saleDepartmentService.querySaleDepartmentByCode(customerPickupOrgCode, billTime);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	/**
	 * 根据产品来判断是查询自有网点还是代理网点
	 * 
	 * @author 076234 PanGuoYang
	 */
	public boolean isAgentDept(String productCode) {
		if (!ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(productCode)
				&& !ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productCode)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 验证是否满足大件上楼和送货上楼的条件
	 * 
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-05-05下午16:55
	 */
	public void validateShslOrDjsl(WaybillPendingEntity entity, WoodenRequirementsEntity woodenEntity) {
		judgeBeanForLDU(entity, woodenEntity);
		judgeBeanForDU(entity, woodenEntity);
	}

	/**
	 * 判断是否满足送货上楼开单
	 * 
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-04-02上午11:28
	 */
	public void judgeBeanForDU(WaybillPendingEntity bean, WoodenRequirementsEntity woodenEntity) {
		if (bean.getReceiveMethod() == null) {
			// 提货方式不能为空
			LOGGER.error("提货方式不能为空");
			throw new WaybillValidateException(WaybillValidateException.NULL_RECEIVE_METHOD);
		} else {
			String valueCode = bean.getReceiveMethod();
			if (WaybillConstants.DELIVER_UP.equals(valueCode) || WaybillConstants.DELIVER_UP_AIR.equals(valueCode)) {
				BigDecimal goodsWeightTotal = bean.getGoodsWeightTotal();
				Integer goodsQtyTotal = bean.getGoodsQtyTotal();
				if (goodsWeightTotal == null || goodsWeightTotal.compareTo(BigDecimal.ZERO) <= 0) {
					// 货物总重量为null或者为0时，提示不可开单送货上楼
					LOGGER.error("货物总重量不可为空或者小于等于0");
					throw new WaybillValidateException(WaybillValidateException.NULLORZERO_GOODSWEIGHTTOTAL);
				} else if (goodsQtyTotal == null || goodsQtyTotal <= 0) {
					// 货物总件数为null或者为0时，提示不可开单送货上楼
					LOGGER.error("货物总件数不可为空或者小于等于0");
					throw new WaybillValidateException(WaybillValidateException.NULLORZERO_NUMBER_OF_CASES_TOTAL);
				} else {
					BigDecimal qty = new BigDecimal(goodsQtyTotal);
					BigDecimal pertotle = (goodsWeightTotal.divide(qty, 2, BigDecimal.ROUND_HALF_DOWN));
					if (woodenEntity == null || !WhetherMakeWoodYoke(woodenEntity)) {
						if (pertotle.compareTo(new BigDecimal(ONE_HUNDRED_THIRTY).setScale(0)) > 0) {
							LOGGER.error("单件货物重量大于130公斤不可开上楼");
							throw new WaybillValidateException(WaybillValidateException.LARGE_DELIVER_UP_SINGLETON_WEIGHT_OVER_130);
						}
						// 没有打木架时，单件货物重量超过50公斤，提货方式改为大件上楼
						BigDecimal judgeStandard = new BigDecimal(FIFTY).setScale(0);
						if (pertotle.compareTo(judgeStandard) > 0) {
							//将提货方式改为大件上楼
							bean.setReceiveMethod(WaybillConstants.LARGE_DELIVER_UP);
							//更新待补录表
							WaybillPendingEntity waybillPending=new WaybillPendingEntity();
							waybillPending.setReceiveMethod(WaybillConstants.LARGE_DELIVER_UP);
							waybillPending.setId(bean.getId());
							waybillPendingDao.updateByPrimaryKeySelective(waybillPending);
						}
					} else {
						if (pertotle.compareTo(new BigDecimal(ONE_HUNDRED_SEVENTY).setScale(0)) > 0) {
							LOGGER.error("有打木架情况下，单件货物重量大于170公斤不可开上楼");
							throw new WaybillValidateException(WaybillValidateException.WOODYOKE_LARGE_DELIVER_UP_SINGLETON_WEIGHT_OVER_170);
						}
						// 您选择了打木架，只有在单件重量超过65kg时，提货方式改为大件上楼
						BigDecimal judgeStandard = new BigDecimal(SIXTY_FIVE).setScale(0);
						if (pertotle.compareTo(judgeStandard) > 0) {
							//将提货方式改为大件上楼
							bean.setReceiveMethod(WaybillConstants.LARGE_DELIVER_UP);
							//更新待补录表
							WaybillPendingEntity waybillPending=new WaybillPendingEntity();
							waybillPending.setReceiveMethod(WaybillConstants.LARGE_DELIVER_UP);
							waybillPending.setId(bean.getId());
							waybillPendingDao.updateByPrimaryKeySelective(waybillPending);
						}
					}
				}
			}
		}
	}

	/**
	 * 判断大件上楼是否满足开单规则
	 * 
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-04-02 下午15:50
	 */
	public void judgeBeanForLDU(WaybillPendingEntity bean, WoodenRequirementsEntity woodenEntity) {
		if (bean.getReceiveMethod() == null) {
			// 提货方式不能为空
			LOGGER.error("提货方式不能为空");
			throw new WaybillValidateException(WaybillValidateException.NULL_RECEIVE_METHOD);
		} else {
			String valueCode = bean.getReceiveMethod();
			if (WaybillConstants.LARGE_DELIVER_UP.equals(valueCode)
					|| WaybillConstants.LARGE_DELIVER_UP_AIR.equals(valueCode)) {
				BigDecimal goodsWeightTotal = bean.getGoodsWeightTotal();
				Integer goodsQtyTotal = bean.getGoodsQtyTotal();
				if (goodsWeightTotal == null || goodsWeightTotal.compareTo(BigDecimal.ZERO) <= 0) {
					// 货物总重量为null或者为0时，提示不可开单送货上楼
					LOGGER.error("货物总重量不可为空或者小于等于0");
					throw new WaybillValidateException(WaybillValidateException.NULLORZERO_GOODSWEIGHTTOTAL);
				} else if (goodsQtyTotal == null || goodsQtyTotal <= 0) {
					// 货物总件数为null或者为0时，提示不可开单送货上楼
					LOGGER.error("货物总件数不可为空或者小于等于0");
					throw new WaybillValidateException(WaybillValidateException.NULLORZERO_NUMBER_OF_CASES_TOTAL);
				} else {
					validateLduOtherInfo(bean,woodenEntity, goodsWeightTotal,
							goodsQtyTotal);
				}
			}
		}
	}

	/**
	 * 校验LDU其他信息
	 * @param woodenEntity
	 * @param goodsWeightTotal
	 * @param goodsQtyTotal
	 */
	private void validateLduOtherInfo(WaybillPendingEntity bean,WoodenRequirementsEntity woodenEntity,
			BigDecimal goodsWeightTotal, Integer goodsQtyTotal) {
		BigDecimal qty = new BigDecimal(goodsQtyTotal);
		//件均重量
		BigDecimal pertotle = (goodsWeightTotal.divide(qty, 2, BigDecimal.ROUND_HALF_DOWN));
		if (woodenEntity == null || !WhetherMakeWoodYoke(woodenEntity)) {
			// 没有打木架时，单件货物重量超过130公斤不可开大件上楼
			if (pertotle.compareTo(new BigDecimal(ONE_HUNDRED_THIRTY).setScale(0)) > 0) {
				LOGGER.error("单件货物重量大于130公斤不可开大件上楼");
				throw new WaybillValidateException(WaybillValidateException.LARGE_DELIVER_UP_SINGLETON_WEIGHT_OVER_130);
			}
			//当一票总重量小于等于50KG时，按照送货上楼激活
			if (goodsWeightTotal.compareTo(new BigDecimal(FIFTY).setScale(0)) <= 0) {
				//将提货方式改为送货上楼
				bean.setReceiveMethod(WaybillConstants.DELIVER_UP);
				//更新待补录表
				WaybillPendingEntity waybillPending=new WaybillPendingEntity();
				waybillPending.setReceiveMethod(WaybillConstants.DELIVER_UP);
				waybillPending.setId(bean.getId());
				waybillPendingDao.updateByPrimaryKeySelective(waybillPending);
			}
		} else {
			// 您选择了打木架，单件货物重量超过170公斤不可开大件上楼
			if (pertotle.compareTo(new BigDecimal(ONE_HUNDRED_SEVENTY).setScale(0)) > 0) {
				LOGGER.error("有打木架情况下，单件货物重量大于170公斤不可开大件上楼");
				throw new WaybillValidateException(WaybillValidateException.WOODYOKE_LARGE_DELIVER_UP_SINGLETON_WEIGHT_OVER_170);
			}
			//打木架是，当一票总重量小于等于65KG时，按照送货上楼激活
			if (goodsWeightTotal.compareTo(new BigDecimal(SIXTY_FIVE).setScale(0)) <= 0) {
				//将提货方式改为送货上楼
				bean.setReceiveMethod(WaybillConstants.DELIVER_UP);
				//更新待补录表
				WaybillPendingEntity waybillPending=new WaybillPendingEntity();
				waybillPending.setReceiveMethod(WaybillConstants.DELIVER_UP);
				waybillPending.setId(bean.getId());
				waybillPendingDao.updateByPrimaryKeySelective(waybillPending);
			}
		}
	}

	/**
	 * 判断是否有打木架信息
	 * 
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-04-02 下午15:50
	 */
	public boolean WhetherMakeWoodYoke(WoodenRequirementsEntity bean) {
		Integer salverGoodsNum = bean.getSalverGoodsNum();
		if (salverGoodsNum == null || salverGoodsNum == 0) {
			boolean flagStandGoodsNum = false;
			boolean flagBoxGoodsNum = false;
			Integer standGoodsNum = bean.getStandGoodsNum();
			Integer boxGoodsNum = bean.getBoxGoodsNum();
			if (standGoodsNum != null && standGoodsNum != 0) {
				flagStandGoodsNum = true;
			}
			if (boxGoodsNum != null && boxGoodsNum != 0) {
				flagBoxGoodsNum = true;
			}
			if (flagStandGoodsNum || flagBoxGoodsNum) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}
	
	/**
	 * 根据客户编码和银行帐号编码查询客户开户银行实体类
	 * 
	 * @author 076234-foss-PanGuoYany
	 * @date 2014-10-02 下午7:24:18
	 */
	public CusAccountEntity queryCusAccountByAccount(String custCode, String account) {
		// 客户开户银行实体类集合对象
		List<CusAccountEntity> accountList = null;
		if (account != null) {
			accountList = queryBankAccountByCode(custCode);
		}
		CusAccountEntity cusAccount = null;
		if (StringUtil.isNotEmpty(account)) {
			if (CollectionUtils.isNotEmpty(accountList)) {
				// 根据银行帐号进行遍历
				for (CusAccountEntity entity : accountList) {
					// 查询银行帐号信息
					if (StringUtil.defaultIfNull(account).equals(entity.getAccountNo())) {
						cusAccount = entity;
					}
				}
			}
		}
		return cusAccount;
	}
	
	/**
	 * 根据客户编码查询客户银行信息
	 * 
	 * @author 076234-foss-PanGuoYany
	 * @date 2014-10-02 下午7:24:18
	 */
	public List<CusAccountEntity> queryBankAccountByCode(String customerCode) {
		// 从CRM正式客户中查询开户行信息
		CustomerDto custDto = queryCustomerService.queryCustInfoByCode(customerCode);
		// 判断是否为空
		if (custDto == null) {
			// 散客帐户集合
			List<NonfixedCusAccountEntity> nonfixAccountList = queryCustomerService
					.queryBankAccountByCode(customerCode);
			// 判空操作
			if (CollectionUtils.isNotEmpty(nonfixAccountList)) {
				List<CusAccountEntity> cusAccountList = new ArrayList<CusAccountEntity>();
				// 转换帐户对象
				for (NonfixedCusAccountEntity nonfixedAccount : nonfixAccountList) {
					CusAccountEntity cusAccount = convertCusToNonfixedAccount(nonfixedAccount);
					cusAccountList.add(cusAccount);
				}
				return cusAccountList;
			}
		} else {
			return custDto.getBankAccountList();
		}
		return null;
	}

	/**
	 * 将散客银行帐户信息转换为正式客户的银行账户信息
	 * 
	 * @author 076234-foss-PanGuoYany
	 * @date 2014-10-02 下午7:24:18
	 */
	private CusAccountEntity convertCusToNonfixedAccount(NonfixedCusAccountEntity nonfixed) {
		if (null != nonfixed) {
			CusAccountEntity account = new CusAccountEntity();
			// 其他支行名称.
			account.setOtherBranchBankName(nonfixed.getOtherBranchBankName());
			// 开户账号.
			account.setAccountNo(nonfixed.getAccountNo());
			// 开户人姓名.
			account.setAccountName(nonfixed.getAccountName());
			// 开户行城市编码.
			account.setCityCode(nonfixed.getCityCode());
			// 开户行省份编码.
			account.setProvCode(nonfixed.getProvCode());
			// 开户行编码.
			account.setBankCode(nonfixed.getBankCode());
			// 手机号码.
			account.setMobilePhone(nonfixed.getMobilePhone());
			// 账号与客户关系.
			account.setCustomer(nonfixed.getCustomer());
			// 是否默认账号.
			account.setDefaultAccount(nonfixed.getDefaultAccount());
			// 支行编码.
			account.setBranchBankCode(nonfixed.getBranchBankCode());
			// 备注.
			account.setNotes(nonfixed.getNotes());
			// 是否启用.
			account.setActive(nonfixed.getActive());
			// 账户性质 对公；对私两种.
			account.setAccountNature(nonfixed.getAccountNature());
			// 在CRM中fid.
			// 如果crm id不为null
			if (nonfixed.getCrmId() != null) {
				account.setCrmId(BigDecimal.valueOf(Long.valueOf(nonfixed.getCrmId().toString())));
			}
			// 开户行所在城市名称.
			account.setCityName(nonfixed.getCityName());
			// 开户行省份名称.
			account.setProvinceName(nonfixed.getProvinceName());
			// 开户行名称.
			account.setOpeningBankName(nonfixed.getOpeningBankName());
			// 支行名称.
			account.setBranchBankName(nonfixed.getBranchBankName());
			// 所属客户ID.
			if (nonfixed.getNoncustCrmId() != null) {
				account.setBelongCustom(BigDecimal.valueOf(Long.valueOf(nonfixed.getNoncustCrmId().toString())));
			}
			return account;
		} else {
			return null;
		}
	}
	
	/**
	 * 获取开单司机信息
	 * @author 076234 PanGuoYang
	 * @param billUserNo
	 * @return
	 */
	public CurrentInfo getCurrentInfo(String billUserNo,String createOrgCode){
		// pda补录消息提醒,通知司机所在车队的集中开单组
		UserEntity userEntity = new UserEntity();
		CurrentInfo currentInfo;
		OrgAdministrativeInfoEntity orgInfo=null;
		if (billUserNo != null) {
			EmployeeEntity employEntity = employeeService.queryEmployeeByEmpCode(billUserNo);
			if(null!=createOrgCode){
				//根据部门code查询部门信息
				orgInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(createOrgCode);
				if(null==orgInfo){
					throw new PdaInterfaceException("查询不到部门信息！");
				}
			}
			if (employEntity != null) {
				userEntity.setEmpCode(billUserNo);
				userEntity.setEmpName(employEntity.getEmpName());
				userEntity.setEmployee(employEntity);
				userEntity.setUserName(employEntity.getEmpName());
				currentInfo= new CurrentInfo (userEntity,orgInfo);//修改部门信息
				// 转换成HttpServletRequest
				javax.servlet.http.HttpSession session=new MockHttpSession();
				// 初始化sessionContext
				SessionContext.setSession(session);
				if(employEntity.getDepartment()!=null){
					if(StringUtil.isNotBlank(employEntity.getDepartment().getCode())){
						SessionContext.getSession().setObject("FOSS_KEY_CURRENT_DEPTCODE",
								(Object)employEntity.getDepartment().getCode());
					}				
					if(StringUtil.isNotBlank(employEntity.getDepartment().getName())){
						SessionContext.getSession().setObject("FOSS_KEY_CURRENT_DEPTNAME",
								(Object)employEntity.getDepartment().getName());
					}
				}
			} else {
				throw new PdaInterfaceException("查询不到该员工信息！");
			}
		}else{
			throw new PdaInterfaceException("createUserCode人员编号为空！");
		}
		UserContext.setCurrentUser(userEntity);
		return currentInfo;
	}

	/**
	 * 提交运单信息
	 * 
	 * @author 076234 PanGuoYang
	 * @param:
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public void submitWaybill(WaybillDto waybill) {
		List<MutexElement> mutexes = new ArrayList<MutexElement>();
		// 创建运单互斥对象
		MutexElement mutexElement = new MutexElement(waybill.getWaybillEntity().getWaybillNo(),
				WaybillConstants.WAYBILL_SUBMIT, MutexElementType.WABYILL_SUBMIT);// 创建对象
		mutexes.add(mutexElement);
		// 判断订单号是否为空
		if (waybill.getWaybillEntity().getOrderNo() != null && !"".equals(waybill.getWaybillEntity().getOrderNo())) {
			// 根据订单号获取运单信息
			WaybillEntity waybillEntity = waybillManagerService
					.queryWaybillByOrderNo(waybill.getWaybillEntity().getOrderNo());
			// 判断运单是否为空，如果不为空，说明此订单已存在，否则创建订单互斥对象
			if (waybillEntity != null && !WaybillConstants.WAYBILL_STATUS_PDA_PENDING
					.equals(waybill.getWaybillEntity().getPendingType())) {
				ActualFreightEntity af = actualFreightService.queryByWaybillNo(waybillEntity.getWaybillNo());
				if (af != null) {
					if (WaybillConstants.EFFECTIVE.equals(af.getStatus())
					/** || af.getStatus() == null */
					) {
						throw new WaybillSubmitException(WaybillSubmitException.WAYBILL_ORDER_SUBMIT_LOCKED);
					}
				}
			}
			MutexElement mutexElementOrder = new MutexElement(waybill.getWaybillEntity().getOrderNo(),
					WaybillConstants.WAYBILL_SUBMIT, MutexElementType.WABYILL_SUBMIT);// 创建对象
			mutexes.add(mutexElementOrder);
		}
		// 互斥锁定
		boolean isLocked = false;
		try {
			isLocked = businessLockService.lock(mutexes, LOCK_TIMEOUT);
		} catch (Exception e) {
			throw new WaybillSubmitException(WaybillSubmitException.WAYBILL_SUBMIT_LOCKED);
		}
		if (!isLocked) {
			throw new WaybillSubmitException(WaybillSubmitException.WAYBILL_SUBMIT_LOCKED);
		}
		try {
			LOGGER.info("提交运单信息");
			waybillManagerService.submitLTLEWaybill(waybill);
		} finally {
			// 释放锁
			businessLockService.unlock(mutexes);
		}
	}
	
	/**
	 * 应用监控数据添加
	 * @author 076234-FOSS-PanGuoYang
	 */
	private void businessMonitor(WaybillDto waybillDto) {
		LOGGER.info("应用监控数据添加");
		waybillManagerService.businessMonitor(waybillDto);
	}
	
	/**
	 * 构造激活操作日志
	 * @param waybillNo 运单号
	 * @param orderNo 订单号
	 * @param jobId jobId
	 * @param OperateResult 激活结果
	 * @param message 错误信息-只有激活失败的时候才会记录
	 * @return
	 */
//	private WaybillProcessLogEntity buildActiveProcessLog(String waybillNo, String orderNo, String jobId, String OperateResult,
//			String message,String logType,Map<String,Object> map) {
//		WaybillProcessLogEntity logEntity = new WaybillProcessLogEntity();
//		Date currentDate = new Date();
//		logEntity.setId(UUIDUtils.getUUID());
//		logEntity.setJobId(jobId);
//		logEntity.setWaybillNo(waybillNo);
//		logEntity.setOrderNo(orderNo);
//		logEntity.setCreateTime(currentDate);
//		logEntity.setModifyTime(currentDate);
//		logEntity.setOperateResult(OperateResult);
//		logEntity.setLogType(logType);
//		if(null!=map){
//			Set<String> keySet = map.keySet();
//			//记录关键对象信息
//			StringBuilder sb=new StringBuilder();
//			for (String string : keySet) {
//				sb.append(string+":");
//				Object object = map.get(string);
//				if(null!=object){
//					String content = ReflectionToStringBuilder.toString(object);
//					sb.append(content);
//				}else{
//					sb.append("NULL");
//				}
//			}
//			logEntity.setContent(sb.toString());
//		}
//		if (!WaybillConstants.LTL_EWAYBILL_ACTIVE_SUCCESS.equals(OperateResult)){
//			logEntity.setFailResion(message);
//		} else {
//			logEntity.setFailResion(null);
//		}
//		return logEntity;
//	}

	/**
	 * @author 305082
	 * 零担电子运单导入重量体积
	 */
	@Override
	public void ltlEWaybillChangeWeight(
			LTLEWaybillChangeWeightDto expBatchChangeWeightDto,
			CurrentInfo currentInfo) {
		//首先校验重量体积是否合格
		//重量
		double weight = Double.parseDouble(expBatchChangeWeightDto.getWeightChanged().toString());
		String weightTemp = weight+"";
		//体积
		double volume = Double.parseDouble(expBatchChangeWeightDto.getVolumeChanged().toString());
		String volumeTemp = volume+"";
		
        int weightValidate = weightTemp.length() - (weightTemp.indexOf(".") + 1);
        int volumeValidate = volumeTemp.length() - (volumeTemp.indexOf(".") + 1);
        if (weightValidate > 1) {
        	//foss.gui.creating.LTLEwaybillImportAction.exception.WeightTotal=重量最多精确到小数点后一位,如0.1
        	String temp = "foss.gui.creating.LTLEwaybillImportAction.exception.WeightTotal";
        	insertFail(expBatchChangeWeightDto, temp);
        }else if (volumeValidate > 2) {
        	//foss.gui.creating.LTLEwaybillImportAction.exception.VolumeTotal=体积最多精确到小数点后两位,如0.01
        	String temp = "foss.gui.creating.LTLEwaybillImportAction.exception.VolumeTotal";
        	insertFail(expBatchChangeWeightDto, temp);
		}else if (new BigDecimal(weight).equals(new BigDecimal(0.0))) {
			//foss.gui.creating.calculateAction.exception.nullGoodsWeightTotal=重量不能为0！
			String temp = "foss.gui.creating.calculateAction.exception.nullGoodsWeightTotal";
			insertFail(expBatchChangeWeightDto, temp);
		}else if (new BigDecimal(volume).equals(new BigDecimal(0.0))) {
			//foss.gui.creating.calculateAction.exception.nullGoodsVolumeTotal=体积不能为0！
			String temp = "foss.gui.creating.calculateAction.exception.nullGoodsVolumeTotal";
			insertFail(expBatchChangeWeightDto, temp);
		}else {
			//重量体积无误，开始查询校验数据
			validateWeightVolume(expBatchChangeWeightDto, currentInfo);
		}
		
		
	}

	/**
	 * 查询运单和订单表信息以做校验
	 * @param expBatchChangeWeightDto
	 * @param currentInfo
	 */
	public void validateWeightVolume(
			LTLEWaybillChangeWeightDto expBatchChangeWeightDto,
			CurrentInfo currentInfo) {
		//查询订单表，订单表如果有数据，证明是零担电子运单，否则，不插入数据
		OmsOrderEntity omsOrderEntity = omsOrderService.queryOmsOrderByWaybillNo(expBatchChangeWeightDto.getWaybillNo());
		expBatchChangeWeightDto.setImportDeptCode(currentInfo.getCurrentDeptCode());
		//订单表不存在数据,非零担电子运单，插入更改失败数据
		if (omsOrderEntity == null) {
			//插入日志记录，运单不存在
			String temp = "foss.gui.creating.waybillDescriptor.waybillNo.noExist";
			insertFail(expBatchChangeWeightDto,temp);
		}else{
			WaybillEntity waybillEntity = waybillDao.queryWaybillByNoAndOrderNo(omsOrderEntity.getWaybillNo(),omsOrderEntity.getOrderNo());
			if (null != waybillEntity) {
				if (null != waybillEntity.getReceiveOrgCode() && currentInfo.getCurrentDeptCode().equals(waybillEntity.getReceiveOrgCode())&&
						WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_ACTIVE.equals(omsOrderEntity.getWaybillStatus())) {
					//插入失败日志记录，已开单运单
					String temp = "foss.gui.creating.importWaybillNo.exist";
					expBatchChangeWeightDto.setProductCode(omsOrderEntity.getProductCode());
					insertFail(expBatchChangeWeightDto,temp);
					//插入失败信息后直接退出此方法
					return;
				}else if (null != waybillEntity.getReceiveOrgCode() && currentInfo.getCurrentDeptCode().equals(waybillEntity.getReceiveOrgCode())&&
						!WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_ACTIVE.equals(omsOrderEntity.getWaybillStatus())){
					//导入重量体积的校验
					importWeightVolumeValidate(expBatchChangeWeightDto, omsOrderEntity);
					return;
				}else if(null != waybillEntity.getReceiveOrgCode() && !currentInfo.getCurrentDeptCode().equals(waybillEntity.getReceiveOrgCode())){
					//插入失败日志记录，非本部门运单
					String temp = "foss.gui.creating.waybillPendingCompleteAction.exception.notTheSameDept";
					expBatchChangeWeightDto.setProductCode(omsOrderEntity.getProductCode());
					insertFail(expBatchChangeWeightDto,temp);
					//插入失败信息后直接退出此方法
					return;
				}
			}
			//非本部门运单
			if (omsOrderEntity.getIncomeOrgCode() != null && 
					!omsOrderEntity.getIncomeOrgCode().equals(currentInfo.getCurrentDeptCode())) {
				//插入失败日志记录，非本部门运单
				String temp = "foss.gui.creating.waybillPendingCompleteAction.exception.notTheSameDept";
				expBatchChangeWeightDto.setProductCode(omsOrderEntity.getProductCode());
				insertFail(expBatchChangeWeightDto,temp);
				//插入失败信息后直接退出此方法
				return;
			}
			//导入重量体积的校验
			importWeightVolumeValidate(expBatchChangeWeightDto, omsOrderEntity);
		}
			
	}
	/**
	 * 导入重量体积的校验
	 * @param expBatchChangeWeightDto
	 * @param currentInfo
	 * @param omsOrderEntity
	 */

	public void importWeightVolumeValidate(
			LTLEWaybillChangeWeightDto expBatchChangeWeightDto,
			OmsOrderEntity omsOrderEntity) {
		//订单表存在数据，判断运单状态为待补录和退回时插入重量体积
		if (WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_PENDING.contains(omsOrderEntity.getWaybillStatus())) {
			//更新重量体积，并做记录
			updateWeightVolume(expBatchChangeWeightDto,omsOrderEntity);
			//查询扫描表信息
			PdaAppInfoEntity pdaAppInfoEntity = pdaAppInfoDao.queryPdaAppInfoByWaybillNO(omsOrderEntity.getWaybillNo());
			/**
			 * 存在重量体积中任何一项时候,且OMS中的订单中的运单状态非已退回,开单，和撤销时候，进行插入激活线程动作
			 * 已退回时候有可能是在生成代补录时候进行退回的也有可能是在激活运单时候进行退回的所有PKP.T_SRV_WAYBILL_PENDING可能不存在数据
			 * 但退回时候一定会同时更新OMS订单数据中的运单状态，所以我们可以不关心PKP.T_SRV_WAYBILL_PENDING是否存在数据
			 */
			if (pdaAppInfoEntity != null&&WaybillConstants.LTLEWAYBILL_SCAN.contains(pdaAppInfoEntity.getScan())) {
				//此时扫描表有数据，并且有重量体积和扫描记录，给线程表插入记录
				ltleWaybillProcessService.addWaybillProcessEntity(omsOrderEntity.getWaybillNo(), omsOrderEntity.getOrderNo() , WaybillConstants.LTLEWAYBILL_PDA_ACTIVE_PROCESS);
			}
		//已开单
		}else if(WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_ACTIVE.contains(omsOrderEntity.getWaybillStatus())){
			
			expBatchChangeWeightDto.setProductCode(omsOrderEntity.getProductCode());
			//设置失败原因,运单已开单
			String temp = "foss.gui.creating.importWaybillNo.exist";
			insertFail(expBatchChangeWeightDto, temp);
		//已撤消
		}else if (WaybillConstants.WAYBILL_PICTURE_TYPE_CANCEL.contains(omsOrderEntity.getWaybillStatus())) {
			
			expBatchChangeWeightDto.setProductCode(omsOrderEntity.getProductCode());
			//设置失败原因,订单已撤销不允许导入
			String temp = "foss.gui.creating.LTLEWaybillManageUI.column.ewaybill.revocation";
			insertFail(expBatchChangeWeightDto, temp);
		}else if (WaybillConstants.WAYBILL_PICTURE_TYPE_RETURN.contains(omsOrderEntity.getWaybillStatus())) {
			//更新重量体积，并做记录
			updateWeightVolume(expBatchChangeWeightDto,omsOrderEntity);
		}
			
	}
	/**
	 * 更改扫描表并做记录
	 * @param expBatchChangeWeightDto
	 * @param omsOrderEntity
	 */
	public void updateWeightVolume(LTLEWaybillChangeWeightDto expBatchChangeWeightDto,OmsOrderEntity omsOrderEntity) {
		
			//转换封装pdaAppInfo实体
			PdaAppInfoEntity pdaAppInfo = new PdaAppInfoEntity();
			pdaAppInfo.setGoodsWeightTotal(expBatchChangeWeightDto.getWeightChanged());
			pdaAppInfo.setGoodsVolumeTotal(expBatchChangeWeightDto.getVolumeChanged());
			pdaAppInfo.setWaybillNo(expBatchChangeWeightDto.getWaybillNo());
			pdaAppInfo.setModifyTime(new Date());
			//更新扫描表记录
			pdaAppInfoDao.updateSelectiveByWaybillNo(pdaAppInfo);
			//插入日志记录
			//设置id
			expBatchChangeWeightDto.setId(UUID.randomUUID().toString());
			expBatchChangeWeightDto.setProductCode(omsOrderEntity.getProductCode());
			expBatchChangeWeightDto.setDeliverCustomerCode(omsOrderEntity.getDeliveryCustomerCode());
			expBatchChangeWeightDto.setImportStartTime(new Date());
			expBatchChangeWeightDto.setFailReason("N");
			expBatchChangeWeightDto.setImportEndTime(expBatchChangeWeightDto.getImportStartTime());
			//设置为更改成功
			expBatchChangeWeightDto.setChangeStatus("RFC_SUCCESS");
			pdaAppInfoDao.insertImportWeightAndVolumeLog(expBatchChangeWeightDto);
			
	}
	/**
	 * 给日志表插入失败记录，
	 * @param expBatchChangeWeightDto
	 * @param failReason
	 */
	public void insertFail(LTLEWaybillChangeWeightDto expBatchChangeWeightDto,String failReason) {
		//设置id
		expBatchChangeWeightDto.setId(UUID.randomUUID().toString());
		//设置为更改失败
		expBatchChangeWeightDto.setChangeStatus("RFC_FAIL");
		expBatchChangeWeightDto.setImportStartTime(new Date());
		expBatchChangeWeightDto.setImportEndTime(expBatchChangeWeightDto.getImportStartTime());
		//设置失败原因
		expBatchChangeWeightDto.setFailReason(failReason);
		pdaAppInfoDao.insertImportWeightAndVolumeLog(expBatchChangeWeightDto);
	}

	/**
	 * 查询更改重量体积的结果信息
	 */
	public List<LTLEWaybillChangeWeightDto> queryLTLEWaybillChangeWeightResult(LTLEWaybillChangeWeightQueryDto dto){
		return pdaAppInfoDao.queryLTLEWaybillChangeWeightResult(dto);
	}

	//2016年5月19日11:35:18
	@Override
	public void addLTLEWaybillPending(List<WaybillProcessEntity> waybillProcessEntityList) {
		
	}
	/**
	 * @description 处理添加激活线程,更新待补录线程，及更新OMS状态
	 * @param entity 用于更新待补录线程,将SecondKey修改成UUID
	 * @param addActiveProcess 是否添加激活线程
	 * @param waybillStatus 用于修改OMS状态
	 * @param exceptionCode 用于记录到OMS订单异常
	 */
	public void handleActiveWaybillProcess(WaybillProcessEntity entity,boolean addActiveProcess,String waybillStatus,String exceptionCode){
		/**
		 * 更新待补录线程记录，
		 */
		entity.setModifyTime(new Date());
		entity.setActive(WaybillConstants.YES);
		waybillProcessDao.updateWaybillProcess(entity, entity.getJobId() , WaybillConstants.YES);
		/**
		 * 更新OMS订单
		 */
		OmsOrderEntity updateOmsOrder = new OmsOrderEntity();
		updateOmsOrder.setOrderNo(entity.getOrderNo());
		updateOmsOrder.setWaybillStatus(waybillStatus);
		if(StringUtils.isEmpty(exceptionCode)&&!addActiveProcess){
			updateOmsOrder.setFailReason("foss.gui.creating.LTLEWaybillManageUI.column.ewaybill.notWeightOrNotScan");
		}else{
			updateOmsOrder.setFailReason(exceptionCode);
		}
		omsOrderDao.updateOmsOrderByOrderNoSelective(updateOmsOrder);
		/**
		 * 判断是否添加激活线程
		 */
		if(addActiveProcess){
			lTLEWaybillProcessService.addWaybillProcessEntity( entity.getWaybillNo() , entity.getOrderNo() , WaybillConstants.LTLEWAYBILL_PDA_ACTIVE_PROCESS);
		}
	}
	
	
	@Transactional
	public void insertAndUpdatePending(OmsOrderEntity omsOrder) {
		//验证合法性
		validateWaybillPeding(omsOrder);
		//插激活线程表
		//insertThreadTable(omsOrder);
		//插入标签表
		insertLabel(omsOrder);
		//创建走货路径
		if(null==waybillPendingDao.getPendingByWaybillNoAndType(omsOrder.getWaybillNo(),WaybillConstants.WAYBILL_STATUS_PDA_PENDING))
			createTransportPath(omsOrder);
		//生成待补录信息
		encapEntity(omsOrder);
		//插标签线程表
		//labelPushProcessService.insertLabelPushProcessEntity(omsOrder.getOrderNo(), omsOrder.getWaybillNo());
	}

	private void encapEntity(OmsOrderEntity omsOrder) {
		/**
		 *  封装待补录表
		 */
		//打印接收到的oms实体信息
		LOGGER.info("OmsOrderEntity运单信息"+ReflectionToStringBuilder.toString(omsOrder)) ;
		WaybillPendingEntity waybillPendingEntity = new WaybillPendingEntity();
		encapWaybillPendgEntity(omsOrder, waybillPendingEntity);
		WaybillEntity waybillEntity = new WaybillEntity();
		/**
		 * 封装运单表
		 */
		//设置免费接货字段
		waybillEntity.setFreePickupGoods(omsOrder.getFreePickupGoods());
		encapWaybillEntity(waybillPendingEntity, waybillEntity);
		/**
		 *  封装实际承运表
		 */
		ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
		//设置是否统一结算
		setDeliverySettleAndContactAndRemending(waybillPendingEntity, actualFreightEntity);
		encapActualFreight(actualFreightEntity, waybillEntity);
		
		/**
		 * 插入数据前先判断是否要需要就行更新
		 */
		if (null == queryPendingIsNotByWaybillNo(omsOrder)) {
			// 生成待补录信息
			waybillPendingDao.insertForWaybillType(waybillPendingEntity);
			waybillDao.addWaybillEntity(waybillEntity);
			actualFreightService
			.insertWaybillActualFreight(actualFreightEntity);
		} else {
			waybillPendingEntity.setBillTime(null);
			waybillPendingDao.updateByWaybillNo(waybillPendingEntity);
			waybillEntity.setBillTime(null);
			waybillDao.updateWaybillByWaybillNo(waybillEntity,
					waybillEntity.getWaybillNo());
			actualFreightService.updateByWaybillNo(actualFreightEntity,
					actualFreightEntity.getWaybillNo());
		}
	}


	private void insertLabel(OmsOrderEntity omsOrder) {
		if (null == queryPendingIsNotByWaybillNo(omsOrder)){
			//插标签线程表
			labelPushProcessService.insertLabelPushProcessEntity(omsOrder.getOrderNo(), omsOrder.getWaybillNo());
		}
	}

	/*
	private void isInsertWaybillProcess(OmsOrderEntity omsOrder) {
		// 先要在扫描表判断是否要添加到线程表
		PdaAppInfoEntity pdaAppInfoEntity = pdaAppInfoDao
				.queryPdaAppInfoByWaybillNO(omsOrder.getWaybillNo());
		if (pdaAppInfoEntity != null
				&& pdaAppInfoEntity.getScan() != null
				&& pdaAppInfoEntity.getScan().equals(WaybillConstants.LTLEWAYBILL_SCAN)
				&& null!=pdaAppInfoEntity.getGoodsVolumeTotal()
				&& StringUtils.isNotEmpty(pdaAppInfoEntity.getGoodsVolumeTotal().toString())
				&& pdaAppInfoEntity.getGoodsVolumeTotal().compareTo(new BigDecimal(0))==1
				&& null!=pdaAppInfoEntity.getGoodsVolumeTotal()
				&& StringUtils.isNotEmpty(pdaAppInfoEntity.getGoodsVolumeTotal().toString())
				&& pdaAppInfoEntity.getGoodsVolumeTotal().compareTo(new BigDecimal(0))==1) {
			// 插入线程表
			WaybillProcessEntity waybillProcessEntity = new WaybillProcessEntity();
			waybillProcessEntity.setWaybillNo(omsOrder.getWaybillNo());
			ltleWaybillProcessService.addWaybillProcessEntity(
					omsOrder.getWaybillNo(), omsOrder.getOrderNo());
		}else{
			//oms表未能插入线程激活表的原因
			omsOrder.setFailReason("foss.gui.creating.LTLEWaybillManageUI.column.ewaybill.notWeightOrNotScan");
			omsOrder.setModifyTime(new Date());
			omsOrderDao.updateOmsOrderByOrderNoSelective(omsOrder);
		}
	}
	*/

	private WaybillPendingEntity queryPendingIsNotByWaybillNo(
			OmsOrderEntity omsOrder) {
		return waybillPendingDao.queryPendingByNo(omsOrder
				.getWaybillNo());
	}
	
	/*
	//往线程表插入数据
	@Transactional
	private void insertThreadTable(OmsOrderEntity omsOrder){
		//更新oms表的订单状态
		omsOrder.setWaybillStatus(WaybillConstants.WAYBILL_STATUS_PDA_PENDING);
		omsOrder.setModifyTime(new Date());
		omsOrderDao.updateOmsOrderByOrderNoSelective(omsOrder);
		//插入线程表
		isInsertWaybillProcess(omsOrder);
	}
	*/
	
	public String getSalesDepoment(OmsOrderEntity omsOrder){
		String currentOrgCode = null;
		// 通过订单受 部门查询始发线路
		List<LineEntity> lineEntities= (null==omsOrder.getOrderAcceptOrgCode()?null:lineDao.queryLineList(omsOrder.getOrderAcceptOrgCode()));
		if(null!=lineEntities && lineEntities.size()>0){
			currentOrgCode = lineEntities.get(0).getDestinationCityCode();
		}else {
			throw new WaybillValidateException(WaybillValidateException.CURRENT_ORG_CODE_NOT_ALLOW_NULL);
		}
		// 根据外场的部门编码查询其可出发驻地营业部的部门编码
		String billingOrgCode=saleDepartmentService.queryLeaveStationSaleCodeByTransferCenterCode(currentOrgCode);
		if (null != billingOrgCode) {
			return billingOrgCode;
		}
		else{
			throw new WaybillValidateException(WaybillValidateException.BILLING_DEPARTMENTENTITY_NULL);
		}
	}
	//封装走货路径
	/**
	 * 
	  * encapTransportPath(用来封装走货路径)
	  * 用来封装走货路径，调用中转的方法
	  * @author wangpengtao-323098
	  * @Title: encapTransportPath
	  * @param omsOrder 参数名称
	  * @return void   返回类型
	 */
	private void createTransportPath(OmsOrderEntity omsOrder) {
		TransportPathEntity transportPath = new TransportPathEntity();
		
		// 运单号 
		transportPath.setWaybillNo(omsOrder.getWaybillNo());
		// 开单时间
		transportPath.setBillingTime(omsOrder.getOrderTime());
		// 通过订单受 部门查询始发线路
		List<LineEntity> lineEntities= (null==omsOrder.getOrderAcceptOrgCode()?null:lineDao.queryLineList(omsOrder.getOrderAcceptOrgCode()));
		if(null!=lineEntities && lineEntities.size()>0){
			transportPath.setCurrentOrgCode(lineEntities.get(0).getDestinationOrganizationCode());
		}else {
			throw new WaybillValidateException(WaybillValidateException.CURRENT_ORG_CODE_NOT_ALLOW_NULL);
		}
		// 根据外场的部门编码查询其可出发驻地营业部的部门编码
		String billingOrgCode=saleDepartmentService.queryLeaveStationSaleCodeByTransferCenterCode(transportPath.getCurrentOrgCode());
		if (null != billingOrgCode) {
			transportPath.setBillingOrgCode(billingOrgCode);
		}
		else
			throw new WaybillValidateException(WaybillValidateException.BILLING_DEPARTMENTENTITY_NULL);
		// 最终到达部门编号
		transportPath.setDestOrgCode(omsOrder.getCustomerPickupOrgCode());
		// 总重量
		transportPath.setTotalWeight(omsOrder.getGoodsWeightTotal());
		// 总体积
		transportPath.setTotalVolume(omsOrder.getGoodsVolumeTotal());
		// 总件数
		transportPath.setGoodsQtyTotal(omsOrder.getGoodsQtyTotal());
		// 运输类型   此处的运输类型就是运输性质（产品编码）
		//transportPath.setTransportModel(getTransType(omsOrder.getProductCode()));
		transportPath.setTransportModel(omsOrder.getProductCode());
		
		// 调用生成线路接口 开单新增走货路径
		calculateTransportPathService.createTransportPath(transportPath);
	}
	/**
	 * 
	  * encapWaybillEntity(用来封装运单实体)
	  * 通过待补录运单表，用来封装运单实体
	  * @author wangpengtao-323098
	  * @Title: encapWaybillEntity
	  * @param waybillPendingEntity
	  * @param waybillEntity 参数名称
	  * @return void   返回类型
	 */
	private void encapWaybillEntity(WaybillPendingEntity waybillPendingEntity,
			WaybillEntity waybillEntity) {
		BeanUtils.copyProperties(waybillPendingEntity, waybillEntity);
		
	}
	/**
	  * encapWaybillPendgEntity用来封装待补录运单的实体
	  * @author wangpengtao-323098
	  * @Title: encapWaybillPendgEntity
	  * @param omsOrder
	  * @param waybillPendingEntity 参数名称
	  * @return void   返回类型
	 */
	private void encapWaybillPendgEntity(OmsOrderEntity omsOrder,WaybillPendingEntity waybillPendingEntity) {
		
		/**
		 * 用来封装一些发货的信息
		 */
		waybillPendingEntity.setWaybillNo(omsOrder.getWaybillNo());// 运单号
		waybillPendingEntity.setOrderNo(omsOrder.getOrderNo());// 订单号
		waybillPendingEntity.setOrderChannel(omsOrder.getOrderChannel());// 订单来源
		waybillPendingEntity.setOrderPaidMethod(omsOrder.getPaidMethod());// 订单付款方式（这个字段只有待补录表有，oms表没有）
		waybillPendingEntity.setPaidMethod(omsOrder.getPaidMethod());// 开单付款方式
		waybillPendingEntity.setDeliveryCustomerCode(omsOrder.getDeliveryCustomerCode());// 发货客户编码
		waybillPendingEntity.setDeliveryBigCustomer(omsOrder.getLtlewBigCustomer());// 大客户标记
		CustomerDto custDto = queryCustomerService.queryCustInfoByCode(waybillPendingEntity.getDeliveryCustomerCode());
		if(null!=custDto){
			waybillPendingEntity.setDeliveryCustomerName(custDto.getName());// 发货客户名称
		}
		waybillPendingEntity.setDeliveryCustomerMobilephone(omsOrder.getDeliveryCustomerMobilephone());// 发货客户手机
		waybillPendingEntity.setDeliveryCustomerPhone(omsOrder.getDeliveryCustomerPhone());// 发货客户电话
		waybillPendingEntity.setDeliveryCustomerContact(omsOrder.getDeliveryCustomerContact());// 发货客户联系人
		// 发货人地址
		waybillPendingEntity.setDeliveryCustomerAddress(omsOrder.getDeliveryCustomerAddress());
		//发货信息详细地址备注
		waybillPendingEntity.setDeliveryCustomerAddressNote(omsOrder.getDeliveryCustomerAddressCmt());
		// 发货省份
		waybillPendingEntity.setDeliveryCustomerProvCode(omsOrder.getDeliveryCustomerProvCode());
		// 发货市
		waybillPendingEntity.setDeliveryCustomerCityCode(omsOrder.getDeliveryCustomerCityCode());
		// 发货区
		waybillPendingEntity.setDeliveryCustomerDistCode(omsOrder.getDeliveryCustomerDistCode());
		//开单时间
		waybillPendingEntity.setBillTime(omsOrder.getOrderTime());
		//创建时间
		WaybillPendingEntity pendingEntityIsNot = queryPendingIsNotByWaybillNo(omsOrder); 
		if(null == pendingEntityIsNot){
			waybillPendingEntity.setCreateTime(new Date());
			//修改时间
			waybillPendingEntity.setModifyTime(waybillPendingEntity.getCreateTime());
		}
		else{
			if(null!=pendingEntityIsNot.getCreateTime()){
				waybillPendingEntity.setCreateTime(pendingEntityIsNot.getCreateTime());
				//修改时间
				waybillPendingEntity.setModifyTime(new Date());
			}else{
				waybillPendingEntity.setCreateTime(new Date());
				//修改时间
				waybillPendingEntity.setModifyTime(waybillPendingEntity.getCreateTime());
			}
		}
		//收入部门，收货部门
		waybillPendingEntity.setReceiveOrgCode(omsOrder.getIncomeOrgCode());
		//始发站名称，收入部门名称
		waybillPendingEntity.setStartName(omsOrder.getIncomeOrgName());
		// 通过订单受 部门查询始发线路
		List<LineEntity> lineEntities= (null==omsOrder.getOrderAcceptOrgCode()?null:lineDao.queryLineList(omsOrder.getOrderAcceptOrgCode()));
		String orginalOrganizationCode;
		if(null!=lineEntities && lineEntities.size()>0){
			//得到始发线路的到达城市编码，也就是外场的部门编码
			orginalOrganizationCode=lineEntities.get(0).getDestinationOrganizationCode();
		}else {
			throw new WaybillValidateException(WaybillValidateException.CURRENT_ORG_CODE_NOT_ALLOW_NULL);
		}
		// 根据外场的部门编码查询其可出发驻地营业部的部门编码
		String billingOrgCode=saleDepartmentService.queryLeaveStationSaleCodeByTransferCenterCode(orginalOrganizationCode);
		if (StringUtils.isNotEmpty(billingOrgCode)) {
			//设置开单部门编码
			waybillPendingEntity.setCreateOrgCode(billingOrgCode);
		}
		else
			throw new WaybillValidateException(WaybillValidateException.BILLING_DEPARTMENTENTITY_NULL);
		
		/**
		 * 用来封装一些收货的信息
		 */
		waybillPendingEntity.setReceiveCustomerCode(omsOrder.getReceiveCustomerCode());// 收货客户编码
		waybillPendingEntity.setReceiveCustomerName(omsOrder.getReceiveCustomerName());// 收货客户名称
		waybillPendingEntity.setReceiveCustomerMobilephone(omsOrder.getReceiveCustomerMobilephone());// 收货客户手机
		waybillPendingEntity.setReceiveCustomerPhone(omsOrder.getReceiveCustomerPhone());// 收货客户电话
		waybillPendingEntity.setReceiveCustomerAddress(omsOrder.getReceiveCustomerAddress());// 收货具体地址
		waybillPendingEntity.setReceiveCustomerAddressNote(omsOrder.getReceiveCustomerAddressCmt());//收货详细地址的备注
		waybillPendingEntity.setReceiveCustomerContact(omsOrder.getReceiveCustomerContact());//收货客户联系人
		// 收货省份
		waybillPendingEntity.setReceiveCustomerProvCode(omsOrder.getReceiveCustomerProvCode());
		// 收货市
		waybillPendingEntity.setReceiveCustomerCityCode(omsOrder.getReceiveCustomerCityCode());
		// 收货区
		waybillPendingEntity.setReceiveCustomerDistCode(omsOrder.getReceiveCustomerDictCode());
		waybillPendingEntity.setCustomerPickupOrgCode(omsOrder.getCustomerPickupOrgCode());// 收货部门  提货网点（到达部门，目的地）
		//waybillPendingEntity.setOrderVehicleNum(omsOrder.getVehicleNumber());// 约车编号
		ProductEntity productEntity = productService.getProductByCache(omsOrder.getProductCode(), omsOrder.getOrderTime());
		if(null!=productEntity){
			waybillPendingEntity.setProductId(productEntity.getId());// 产品ID
		}
		
		/**
		 * 运输信息的封装
		 */
		waybillPendingEntity.setProductCode(omsOrder.getProductCode());// 运输性质
		waybillPendingEntity.setReceiveMethod(omsOrder.getReceiveMethod());// 提货方式
		waybillPendingEntity.setIsWholeVehicle(WaybillConstants.NO);// 是否是整车
		waybillPendingEntity.setCustomerPickupOrgCode(omsOrder.getCustomerPickupOrgCode());// 提货网点（到达部门，目的地）
		//waybillPendingEntity.setLoadMethod(WaybillConstants.LDZX);// 配载类型
		OrgAdministrativeInfoEntity orgAdministrative = waybillManagerService.queryOrgInfoByCode(omsOrder.getCustomerPickupOrgCode());
		if(null ==orgAdministrative){
			OuterBranchEntity branchEntity = waybillManagerService.queryAgencyBranchInfo(omsOrder.getCustomerPickupOrgCode());
			if(null==branchEntity){
				throw new WaybillValidateException(WaybillValidateException.DESTINATION_NULL); 
			}
			waybillPendingEntity.setTargetOrgCode(branchEntity.getSimplename());// 目的站SIMPLENAME
		}else{
			waybillPendingEntity.setTargetOrgCode(orgAdministrative.getOrgSimpleName());// 目的站SIMPLENAME
		}
		waybillPendingEntity.setPickupToDoor(omsOrder.getPickupToDoor());// 是否上门接货
		//有关 最终配载部门，配载路线的设置
		queryLodeDepartmentInfo(waybillPendingEntity);
		//预计出发时间
		waybillPendingEntity.setPreDepartureTime(getLeaveTime(waybillPendingEntity));
		//预计派送/提货时间
		EffectiveDto effectiveDto = waybillManagerService.searchPreSelfPickupTime(waybillPendingEntity.getCreateOrgCode(), waybillPendingEntity.getCustomerPickupOrgCode(), waybillPendingEntity.getProductCode(), waybillPendingEntity.getPreDepartureTime(), new Date());
		if(effectiveDto != null){
			if(isPickup(waybillPendingEntity)){
				waybillPendingEntity.setPreArriveTime(effectiveDto.getDeliveryDate());
				waybillPendingEntity.setPreCustomerPickupTime(effectiveDto.getDeliveryDate());
			}else{
				waybillPendingEntity.setPreArriveTime(effectiveDto.getSelfPickupTime());
				waybillPendingEntity.setPreCustomerPickupTime(effectiveDto.getSelfPickupTime());
			}
		}
		waybillPendingEntity.setCarDirectDelivery(WaybillConstants.NO);// 是否大车直送
		waybillPendingEntity.setGoodsName(omsOrder.getGoodsName());// 货物名称

		waybillPendingEntity.setGoodsQtyTotal(omsOrder.getGoodsQtyTotal());// 货物总件数
//		waybillPendingEntity.setGoodsWeightTotal(omsOrder.getGoodsWeightTotal());// 货物总重量
//		waybillPendingEntity.setGoodsVolumeTotal(omsOrder.getGoodsVolumeTotal());// 货物总体积
		waybillPendingEntity.setGoodsSize(omsOrder.getDimension());
		/**
		 * 货物类型
		 */
		waybillPendingEntity.setGoodsTypeCode(WaybillConstants.GOODS_TYPE_A);// 货物类型
		waybillPendingEntity.setPreciousGoods(WaybillConstants.NO);// 是否贵重物品
		waybillPendingEntity.setSpecialShapedGoods(WaybillConstants.NO);// 是否异形物品
		waybillPendingEntity.setTransportationRemark(omsOrder.getOtherComment());// 储运事项
		//纸包装
	    waybillPendingEntity.setPaperNum(0);
	    //木包装
	    waybillPendingEntity.setWoodNum(0);
	    //纤包装
	    waybillPendingEntity.setFibreNum(0);
	    //托包装
	    waybillPendingEntity.setSalverNum(0);
	    //膜包装
	    waybillPendingEntity.setMembraneNum(0);
		waybillPendingEntity.setOtherPackage(omsOrder.getOtherPackage());//其他包装类型
		//由于oms没有goodpackage字段，所以取oms的otherpackage字段
		waybillPendingEntity.setGoodsPackage(omsOrder.getOtherPackage());
		//L数据集中接货直接设置成N， （零担电子运单开单确实不属于OCB开单和集中开单）
		waybillPendingEntity.setPickupCentralized(WaybillConstants.NO);
		/**
		 * 有关费用封装
		 */
		waybillPendingEntity.setInsuranceAmount(omsOrder.getInsuranceAmount());// 保价声明价值
		waybillPendingEntity.setCodAmount(omsOrder.getCodAmount());// 代收货款
		waybillPendingEntity.setOtherFee(omsOrder.getOtherFee());// 其他费用
		//依据产品类型获取运输类型
		//运输类型
		waybillPendingEntity.setTransportType(getTransType(waybillPendingEntity.getProductCode()));
		
		setFeeRelatedInfo(waybillPendingEntity,omsOrder);
		waybillPendingEntity.setPendingType(WaybillConstants.WAYBILL_STATUS_PDA_PENDING);
		waybillPendingEntity.setActive(WaybillConstants.YES);
		/**
		 * 设置开单人 
		 * 开单人取开单部门的营业部的经理，如果经理不存在，就去开单部门的任意一个员工
		 */
		OrgAdministrativeInfoEntity administrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillPendingEntity.getCreateOrgCode());
		if(null != administrativeInfoEntity && StringUtils.isNotEmpty(administrativeInfoEntity.getPrincipalNo())){
			waybillPendingEntity.setCreateUserCode(administrativeInfoEntity.getPrincipalNo());
		}else if(null != administrativeInfoEntity) {
			EmployeeEntity entity = new EmployeeEntity();
			entity.setOrgCode(waybillPendingEntity.getCreateOrgCode());
			List<EmployeeEntity> employeeEntities = employeeDao.queryEmployeeByOrgCode(entity);
			if(null!=employeeEntities && employeeEntities.size()>0){
				waybillPendingEntity.setCreateUserCode(employeeEntities.get(0).getEmpCode());
			}else{
				throw new WaybillValidateException(WaybillValidateException.BILLING_USER_NULL); 
			}
		}else {
			throw new WaybillValidateException(WaybillValidateException.BILLING_USER_NULL);
		}
		String empName = employeeDao.queryEmpNameByEmpCode(waybillPendingEntity.getCreateUserCode());
		if(StringUtils.isEmpty(empName)){
			throw new WaybillValidateException(WaybillValidateException.BILLING_USER_NULL); 
		}else{
			waybillPendingEntity.setCreateUserName(empName);
		}
		//设置发票标记信息
		setInvoiceInfo(omsOrder.getDeliveryCustomerCode(), waybillPendingEntity);
		//运单类型设置
		waybillPendingEntity.setWaybillType(WaybillConstants.LTLEWAYBILL);
		//以下数据封装是为了测试
		waybillPendingEntity.setForbiddenLine(WaybillConstants.NO);
		waybillPendingEntity.setTotalFee(new BigDecimal(WaybillConstants.ZERO));
		waybillPendingEntity.setBillWeight(new BigDecimal(WaybillConstants.ZERO));
		waybillPendingEntity.setPickupFee(new BigDecimal(WaybillConstants.ZERO));
		waybillPendingEntity.setServiceFee(new BigDecimal(WaybillConstants.ZERO));
	}
	private String getTransType(String productCode) {
		return waybillExpressService.getTransTypeByLevel3ProductCode(productCode);
	}

	/**
	 * 
	  * queryLodeDepartmentInfo(查询并设置始发配载部门、最终配载部门以及线路)
	  *
	  * @Title: queryLodeDepartmentInfo
	  * @param waybillEntity 运单实体
	  * @return void   返回类型
	 */
	private void queryLodeDepartmentInfo(WaybillPendingEntity waybillEntity){
		//没有找到走路路径的，应该进行补录；
		try {
			//运输性质非空判断
			if(StringUtils.isEmpty(waybillEntity.getProductCode())){
				throw new WaybillValidateException(WaybillValidateException.PRODUCT_CODE_NULL);
			}
			
			Boolean isPickupCentralized = WaybillConstants.YES.equals(waybillEntity.getPickupCentralized()) ? true : false;
			
			OrgInfoDto orgInfoDto = waybillFreightRouteService.queryLodeDepartmentInfoOnline(isPickupCentralized,
					waybillEntity.getCreateOrgCode(), waybillEntity.getCustomerPickupOrgCode(), waybillEntity.getProductCode());
			if (orgInfoDto == null || orgInfoDto.getFreightRoute() == null) {
				throw new WaybillValidateException(WaybillValidateException.FREIGHT_ROUTE_NOT_FOUND);
				//补录操作
			} else {
				FreightRouteEntity freightRoute = orgInfoDto.getFreightRoute();

				//waybillEntity.setLoadLineName(orgInfoDto.getRouteLineName());// 配载线路名称
	
				if(freightRoute!=null){
					waybillEntity.setLoadLineCode(freightRoute.getVirtualCode());// 配载线路编码
					//waybillEntity.setPackageOrgCode(freightRoute.getPackingOrganizationCode());// 代打木架部门编码	
					//waybillEntity.setPackingOrganizationName(freightRoute.getPackingOrganizationName());// 代打木架部门名称
					//waybillEntity.setDoPacking(freightRoute.getDoPacking());// 是否可以打木架
				}else{
					waybillEntity.setLoadLineCode("");// 配载线路编码
					//waybillEntity.setPackageOrgCode("");// 代打木架部门编码	
					//waybillEntity.setPackingOrganizationName("");// 代打木架部门名称		
					//waybillEntity.setDoPacking("");// 是否可以打木架	
				}			
				waybillEntity.setLoadOrgCode(orgInfoDto.getFirstLoadOrgCode());// 配载部门编号
				waybillEntity.setLoadOrgName(orgInfoDto.getFirstLoadOrgName());// 配载部门名称
				waybillEntity.setLastLoadOrgCode(orgInfoDto.getLastLoadOrgCode());// 最终配载部门编号
				waybillEntity.setLastLoadOrgName(orgInfoDto.getLastLoadOrgName());// 最终配载部门名称
				//waybillEntity.setLastOutLoadOrgCode(orgInfoDto.getLastOutLoadOrgCode());//最终外场编码
				
				//waybillEntity.setGoodsTypeIsAB(orgInfoDto.getGoodsTypeIsAB());//是否AB货
		
			}
		} catch(BaseInfoInvokeException e) {
			throw new WaybillValidateException(WaybillValidateException.FREIGHT_ROUTE_NOT_FOUND); 
			
		} catch(BusinessException w){
			//waybillEntity.setLoadLineName("");// 配载线路名称
			waybillEntity.setLoadLineCode("");// 配载线路编码
			waybillEntity.setLoadOrgCode("");// 配载部门编号
			waybillEntity.setLoadOrgName("");// 配载部门名称
			waybillEntity.setLastLoadOrgCode("");// 最终配载部门编号
			waybillEntity.setLastLoadOrgName("");// 最终配载部门名称
			//waybillEntity.setPackageOrgCode("");// 代打木架部门编码
			//waybillEntity.setPackingOrganizationName("");// 代打木架部门名称
			//waybillEntity.setDoPacking("");// 是否可以打木架
			//waybillEntity.setLastOutLoadOrgCode("");//最终配置外场
			throw new WaybillValidateException(WaybillValidateException.FREIGHT_ROUTE_NOT_FOUND); 
		}
	
	}
	/**
	 * 设置增值服务及费用基础信息
	 * 
	 * @author 136334-foss-BaiLei
	 * @date 2014-9-13下午5:22:49
	 */
	private void setFeeRelatedInfo(WaybillPendingEntity waybillEntity,OmsOrderEntity order){
	
		//保价声明价值insuranceAmount
		if(order.getInsuranceAmount()==null){
			waybillEntity.setInsuranceAmount(BigDecimal.ZERO);
		}else{
			waybillEntity.setInsuranceAmount(order.getInsuranceAmount());
		}
		
		//代收货款
		if(order.getCodAmount()==null){
			//代收货款
			waybillEntity.setCodAmount(BigDecimal.ZERO);
			//代收货款手续费
			waybillEntity.setCodFee(BigDecimal.ZERO);
		}else{
			//代收货款
			waybillEntity.setCodAmount(order.getCodAmount());
			//(代收货款手续费)
			waybillEntity.setCodFee(BigDecimal.ZERO);
		}

		//退款类型(如果代收为0，则退款类型置空)
		/**
		 * 2016年5月14日16:15:55
		 * （323098王鹏涛）
		 */
		/*if(order.getReviceMoneyAmount()==null || order.getReviceMoneyAmount().equals(BigDecimal.ZERO)){
			waybillEntity.setRefundType(null);
		}else{
			waybillEntity.setRefundType(order.getReciveLoanType());
		}
		 */
		waybillEntity.setReturnBillType(WaybillConstants.NOT_RETURN_BILL);//返单类别  
		waybillEntity.setRefundType(order.getCodType());//退款类型  
		waybillEntity.setSecretPrepaid(WaybillConstants.NO);//预付费保密
		waybillEntity.setCurrencyCode(WaybillConstants.CURRENCY_CODE);//付款币种
		waybillEntity.setWholeVehicleAppfee(BigDecimal.ZERO);//非整车，费用置0
		waybillEntity.setServiceFee(BigDecimal.ZERO);//无装卸费，费用置0
		
		
		waybillEntity.setIsEconomyGoods(WaybillConstants.NO);
		
		//包装手续费
		waybillEntity.setPackageFee(BigDecimal.ZERO);
		//送货费
		waybillEntity.setDeliveryGoodsFee(BigDecimal.ZERO);
		//其他费用
		if(order.getOtherFee()==null){
		waybillEntity.setOtherFee(BigDecimal.ZERO);
		}else{
			waybillEntity.setOtherFee(order.getOtherFee());
		}
		//waybillEntity.setPromotionsCode(order.getCouponNumber());//优惠编码
		//判定付款方式s
		if(StringUtils.isNotEmpty(order.getPaidMethod())){
			for(CrmPaymentTypeEnum crm : CrmPaymentTypeEnum.values()){
			    // 订单付款方式
			    if(crm.getCrmCode().equals(order.getPaidMethod())){
			    	String fossCode = crm.getFossCode();
			    	if(StringUtils.isNotEmpty(fossCode)) {
			    		waybillEntity.setPaidMethod(crm.getFossCode());
			    	}
			    }
			}
		}else{
			//判定是否存在客户编码 
			if(StringUtils.isNotEmpty(waybillEntity.getDeliveryCustomerCode())){
				waybillEntity.setPaidMethod(WaybillConstants.MONTH_PAYMENT);
			}else{
				waybillEntity.setPaidMethod(WaybillConstants.CASH_PAYMENT);
			}
		}
		
		//只有在有代收货款的情况下才会设置对应的账号信息
		if(order.getInsuranceAmount() != null && order.getInsuranceAmount().compareTo(BigDecimal.ZERO) > 0){
			waybillEntity.setAccountName(order.getAccountName());//返款帐户开户名称
			waybillEntity.setAccountCode(order.getAccountCode());//返款帐户开户账户
			/**
			 * 2016年5月14日16:21:41
			 * （323098王鹏涛）
			 */
			/*CusAccountEntity cusAccountEntity = waybillExpressService.queryEWaybillCusAccountInfo(waybillEntity);
			if(null == cusAccountEntity){
				throw new WaybillValidateException(WaybillValidateException.CUSTOMER_ACCOUNT_NULL);
			}*/
			waybillEntity.setAccountBank(order.getAccountBank());//返款帐户开户银行
		}
		
		waybillEntity.setOrderPaidMethod(order.getPaidMethod());//支付方式
		//设置长短途
		waybillEntity.setLongOrShort(getLongOrShort(order.getIncomeOrgCode(), order.getCustomerPickupOrgCode(), waybillEntity.getProductCode(), waybillEntity.getBillTime()));
		//设置外场编码
		OrgInfoDto orgInfoDto = waybillFreightRouteService.queryLodeDepartmentInfoOnline(WaybillConstants.FALSE,order.getIncomeOrgCode(), order.getCustomerPickupOrgCode(), waybillEntity.getProductCode()); 
		waybillEntity.setLastOutLoadOrgCode(orgInfoDto.getLastLoadOrgCode());
		LOGGER.info("费用相关信息设置完毕.....................货物信息为" + waybillEntity.getInsuranceAmount()) ;
	}	
	/**
	 * 
	 * 获得预计出发时间
	 * 
	 */
	public Date getLeaveTime(WaybillPendingEntity entity) {
		
		Date leaveTime = waybillManagerService.searchPreLeaveTime(entity.getCreateOrgCode(), 
				entity.getLoadOrgCode(), entity.getProductCode(), new Date());
		return leaveTime;
	}
	/**
	 * 判定是否自提货物
	 * @param waybillEntity
	 * @param order
	 * @return
	 */
	private Boolean isPickup(WaybillPendingEntity waybillEntity) {
		if(StringUtils.isNotEmpty(waybillEntity.getReceiveMethod())
				&& waybillEntity.getReceiveMethod().indexOf(WaybillConstants.STATUS_PICKUP) >= 0){
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 
	 * 增加ActualFreight
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-3-23 下午3:31:05
	 */
	private void encapActualFreight(ActualFreightEntity actualFreight,WaybillEntity waybillEntity) {
		//添加地址备注
		DispatchOrderEntity dispatchOrderEntity = dispatchOrderEntityDao.queryBasicDispachOrderEntity(waybillEntity.getOrderNo());
		if(dispatchOrderEntity != null){
			if(StringUtils.isNotEmpty(dispatchOrderEntity.getDeliveryCustomerAddressNote())){
				dispatchOrderEntity.setDeliveryCustomerAddressNote(dispatchOrderEntity.getDeliveryCustomerAddressNote());
			}
			if(StringUtils.isNotEmpty(dispatchOrderEntity.getReceiveCustomerAddressNote())){
				dispatchOrderEntity.setReceiveCustomerAddressNote(dispatchOrderEntity.getReceiveCustomerAddressNote());
			}
		}
		// id
		actualFreight.setId(UUIDUtils.getUUID());
		// 运单号
		actualFreight.setWaybillNo(waybillEntity.getWaybillNo());
		// 货物名称
		actualFreight.setGoodsName(WaybillConstants.TO_MAKEUP);
		// 货物重量
		actualFreight.setWeight(new BigDecimal(WaybillConstants.ZERO));
		// 货物体积
		actualFreight.setVolume(new BigDecimal(WaybillConstants.ZERO));
		// 件数
		actualFreight.setGoodsQty(waybillEntity.getGoodsQtyTotal());
		//PDA付款方式 addby 254615
		actualFreight.setPdaPaidMethod(waybillEntity.getPaidMethod());
		//PDA现付金额 addby 254615
		actualFreight.setPdaPrePayAmount(waybillEntity.getPrePayAmount());
		
		// 尺寸
		actualFreight.setDimension(WaybillConstants.UNKNOWN);
		// 保价费
		actualFreight.setInsuranceValue(BigDecimal.ZERO);
		// 包装费
		actualFreight.setPackingFee(BigDecimal.ZERO);
		// 送货费
		actualFreight.setDeliverFee(BigDecimal.ZERO);
		// 装卸费
		actualFreight.setLaborFee(BigDecimal.ZERO);
		// 代收货款
		actualFreight.setCodAmount(BigDecimal.ZERO);
		// 增值服务费
		actualFreight.setValueaddFee(BigDecimal.ZERO);
		// 公布价运费
		actualFreight.setFreight(BigDecimal.ZERO);
		// 设置始发库存部门
		String startStockOrgCode = queryStartStocksDepartmentService(waybillEntity);
		actualFreight.setStartStockOrgCode(startStockOrgCode);
		//创建时间
		actualFreight.setCreateTime(waybillEntity.getBillTime());
		//设置运单类型
		actualFreight.setWaybillType(WaybillConstants.LTLEWAYBILL);
		// 设置最终库存部门和库区编号
		waybillStockService.getEndStockCodeAndAreaCode(actualFreight, waybillEntity);
		//设置实际承运表的客户分群字段,根据发货客户的客户编码查找
		CustomerDto custDto = queryCustomerService.queryCustInfoByCode(waybillEntity.getDeliveryCustomerCode());
		if(null==custDto){
			 throw new WaybillValidateException(WaybillValidateException.CUSTOMER_NOT_ALLOW);
		 }
		actualFreight.setFlabelleavemonth(custDto.getFlabelleavemonth());
		//往实际承运表插入数据
	}
	/**
	 * 根据出发始发区域ID,目的地区域ID,产品编码,营业日期,确定获得唯一时效明细信息返回长短途标识.
	 *
	 * @param originalOrgCode the original org code
	 * @param destinationOrgCode the destination org code
	 * @param productCode 产品编码
	 * @param receiveDate 收货日期
	 * @return the long or short
	 * @author DP-Foss-YueHongJie
	 * @date 2012-11-9 下午2:37:08
	 */
	private String getLongOrShort(String originalOrgCode, String destinationOrgCode,String productCode,Date receiveDate){
	    List<EffectivePlanDto> effPlanDetailList = effectivePlanDetailService.queryEffectivePlanDetailListByOrgCode(originalOrgCode, destinationOrgCode, productCode,receiveDate);
	    if(CollectionUtils.isNotEmpty(effPlanDetailList)){
	    	return effPlanDetailList.get(0).getLongOrShort();    
	    }
	    return null;
	}
	/**
	 * 
	 * validate(用于订单的校验)
	 * 
	 * @author wangpengtao-323098
	 * @Title: validate
	 * @param bean
	 *            参数名称
	 * @return void 返回类型
	 */
	private void validateWaybillPeding(OmsOrderEntity bean) {
		//根据扫描信息设置待补录表的收货部门 与开单部门 1，如果扫描过了，都以app收入部门的为主    2,如果没扫描 ，都以oms收入部门为主
		PdaAppInfoEntity pdaAppInfoEntity = pdaAppInfoDao.queryPdaAppInfoByWaybillNO(bean.getWaybillNo());
		if(null!=pdaAppInfoEntity
				&&WaybillConstants.YES.equals(pdaAppInfoEntity.getScan())
				&&StringUtils.isNotEmpty(pdaAppInfoEntity.getOriginateOrgCode())){
			bean.setIncomeOrgCode(pdaAppInfoEntity.getOriginateOrgCode());
			SaleDepartmentEntity salesDept = saleDepartmentService.querySimpleSaleDepartmentByCode(bean.getIncomeOrgCode());
			if(null!=salesDept&&StringUtils.isNotEmpty(salesDept.getName())){
				bean.setIncomeOrgName(salesDept.getName());
			}
			bean.setOrderTime(pdaAppInfoEntity.getOverTaskTime());
		}
		/**
		 * 校验运单号
		 */
		validateWaybillNo(bean.getWaybillNo());
		/**
		 * 校验运输类型
		 */
		validateWaybillTransType(bean.getProductCode());
		/**
		 * 校验提货方式
		 */
		validateWaybillPickUp(bean.getReceiveMethod());
		/**
		 * 货物名称校验
		 */
		waybillGoogNameCheck(bean.getGoodsName());
		/**
		 * 校验客户信息
		 */
		validateCustomer(bean);
		// begin 以前开单的校验
		/**
		 * 开单省市区校验 ;
		 */
		validateCity(bean);
		/**
		 * checkAddress //地址校验
		 */
		checkAddress(bean);
		/**
		 * 目的站校验
		 * 
		 */
		validateDistination(bean);
		/**
		 * 付款方式校验
		 */
		validatePayment(bean);
		/**
		 * 代收货款校验
		 */
		validateCod(bean);
		/**
		 *  重量、体积、件数校验
		 */
		validateWeightVol(bean);
	}

	/**
	 * 校验运单号 运单号：为7开头九位数，未被占用；不满足则退回，提示运单号错误或已被占用；生成待激活环节完成，激活环节不再校验；
	 */
	private void validateWaybillNo(String waybillNoS) {
		int waybillNo = 0;
		try {
			//java的integer是32位的。范围在-2147483648 到2147483648
			waybillNo = Integer.parseInt(waybillNoS);
		} catch (NumberFormatException e) {
			throw new WaybillValidateException(
					WaybillValidateException.WAYBILL_ERROR);
		}
		if (Math.max(WaybillConstants.PKP_LTL_EWAYBILL_NO_MIN, waybillNo) != Math.min(waybillNo, WaybillConstants.PKP_LTL_EWAYBILL_NO_MAX))
			throw new WaybillValidateException(
					WaybillValidateException.WAYBILL_ERROR);
		//除了待补录的状态是pda_pending的除外，因为有更新的要求
		
		WaybillPendingEntity waybillPendingEntityPending = waybillPendingDao.getPendingByWaybillNoAndType(waybillNoS,WaybillConstants.WAYBILL_STATUS_PDA_PENDING);
		WaybillPendingEntity waybillPendingEntity =waybillPendingDao.queryWaybillByWaybillNo(waybillNoS);
		if (null!=waybillPendingEntity && null == waybillPendingEntityPending)
			throw new WaybillValidateException(
					WaybillValidateException.WAYBILL_EXSITS);
	}

	/**
	 * 为汽车偏线、精准卡航、精准城运、精准汽运（长）、精准汽运（短） productCode 
	 * //三级产品-汽运偏线 public static
	 * final String PRICING_PRODUCT_PARTIAL_LINE = "PLF";// C30007 TRANS_VEHICLE
	 * //三级产品-精准卡航 public static final String
	 * PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT = "FLF";//C30001 TRANS_VEHICLE
	 * //三级产品-精准城运 public static final String
	 * PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT = "FSF";//C30002
	 * TRANS_VEHICLE //三级产品-精准汽运(长途) public static final String
	 * PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT = "LRF";//C30003 TRANS_VEHICLE
	 * //三级产品-精准汽运(短途) public static final String
	 * PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT = "SRF";// C30004
	 * TRANS_VEHICLE
	 */
	private void validateWaybillTransType(String productCode) {
		if (!ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(productCode)
				&& !ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT.equals(productCode)
				&& !ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT.equals(productCode)
				&& !ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT.equals(productCode)
				&& !ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT.equals(productCode)
				&& !ProductEntityConstants.PRICING_PRODUCT_PCP.equals(productCode)) {
			throw new WaybillValidateException(
					WaybillValidateException.PRODUCT_CODE_NO_ALLOW);
		}
	}

	/**
	 * 提货方式 为自提、送货进仓、送货上楼、送货（不含上楼）、大件上楼，不满足则退回，提示不支持所选提货方式
	 * 
	 */
	private void validateWaybillPickUp(String pickUpMethod) {
		if (!CommonUtils.verdictPickUpSelf(pickUpMethod))
			if (!CommonUtils.verdictPickUpDoor(pickUpMethod))
				throw new WaybillValidateException(
						WaybillValidateException.PICKUP_METHOD_NO_ALLOW);
	}

	/**
	 * 货物名称：生成待补录环节校验货物名称字段， 已经有公共方法waybillGoogNameCheck
	 */

	/**
	 * 
	 * 校验客户信息
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-3 上午11:15:38
	 */
	private void validateCustomer(OmsOrderEntity bean) {
		validateCustom1(bean);

		validateCustom2(bean);

		validateCustom3(bean);
	}

	private void validateCustom3(OmsOrderEntity bean) {
		if (StringUtils.isEmpty(bean.getDeliveryCustomerMobilephone())
				&& StringUtils.isEmpty(bean.getDeliveryCustomerPhone())) {
			// DELIVERY_CONTACT_NULL发货人手机或者电话号码必须有一项不为空
			throw new WaybillValidateException(
					WaybillValidateException.DELIVERY_CONTACT_NULL);
		}

		if (StringUtils.isEmpty(bean.getReceiveCustomerMobilephone())
				&& StringUtils.isEmpty(bean.getReceiveCustomerPhone())) {
			// RECEIVER_CONTACT_NULL收货人手机或者电话号码必须有一项不为空
			throw new WaybillValidateException(
					WaybillValidateException.RECEIVER_CONTACT_NULL);
		}
		if (StringUtils.isEmpty(bean.getReceiveMethod())) {
			// RECEIVE_METHOD_NULL"提货方式为空"
			throw new WaybillValidateException(
					WaybillValidateException.RECEIVE_METHOD_NULL);
		}
	}

	private void validateCustom2(OmsOrderEntity bean) {
		if (StringUtils.isNotEmpty(bean.getReceiveCustomerMobilephone())) {
			String mobilePhone = bean.getReceiveCustomerMobilephone();
			if (mobilePhone.length() > 0) {
				int phoneLength = mobilePhone.trim().length();
				int lengthLimitEight = EIGHT;
				int lengthLimitEleven = ElEVEN;
				if (phoneLength != lengthLimitEight
						&& phoneLength != lengthLimitEleven) {
					// 收货客户手机号码必须是8位或者11位
					throw new WaybillValidateException(
							WaybillValidateException.RECEIVER_MOBILEPHONE_LENGTH_ILLEGAL);
				}

				if (phoneLength == lengthLimitEleven) {
					if (!"1".equals(mobilePhone.substring(0, 1))) {
						// 收货客户手机号码11位时，必须1开头
						throw new WaybillValidateException(
								WaybillValidateException.RECEIVER_MOBILEPHONE_FIRST_NUMBER_ILLEGAL);
					}
				}
				// 判定手机号码与电话号码是否存在一致的情况：存在电话号码组成：0+手机号码这种情况，需要过滤掉
				if (StringUtils
						.isNotEmpty(bean.getReceiveCustomerPhone())) {
					if (mobilePhone
							.equals(bean.getReceiveCustomerPhone())) {
						// 固定电话和手机号码重复，请重新输入
						throw new WaybillValidateException(
								WaybillValidateException.MOBILE_TELEPHONE_RE);
					}
						String deliveryCustomerPhone = bean
								.getReceiveCustomerPhone().replaceFirst(
										"^0*", "");
						if (StringUtils.isNotEmpty(deliveryCustomerPhone)// 不为空
								&& (deliveryCustomerPhone.length() == NumberConstants.NUMBER_8// 并且满足手机号码的校验条件
								|| (deliveryCustomerPhone.length() == NumberConstants.NUMBER_11 && deliveryCustomerPhone
										.startsWith(String.valueOf(1))))) {
							if (StringUtils.isNotEmpty(bean
									.getReceiveCustomerMobilephone())
									&& mobilePhone
											.equals(deliveryCustomerPhone)) {
								// 收货客户手机号码必须是8位或者11位
								throw new WaybillValidateException(
										WaybillValidateException.RECEIVER_MOBILEPHONE_LENGTH_ILLEGAL);
							}
						}
				}

			}

		}
	}

	private void validateCustom1(OmsOrderEntity bean) {
		if (StringUtils.isNotEmpty(bean.getDeliveryCustomerMobilephone())) {
			String mobilePhone = bean.getDeliveryCustomerMobilephone();
			if (mobilePhone.length() > 0) {
				int phoneLength = mobilePhone.trim().length();
				int lengthLimitEight = EIGHT;
				int lengthLimitEleven = ElEVEN;
				if (phoneLength != lengthLimitEight
						&& phoneLength != lengthLimitEleven) {
					// 发货客户手机号码必须是8位或者11位
					throw new WaybillValidateException(
							WaybillValidateException.DELIVERY_MOBILEPHONE_LENGTH_ILLEGAL);
				}

				if (phoneLength == lengthLimitEleven) {
					if (!"1".equals(mobilePhone.substring(0, 1))) {
						// 发货客户手机号码11位时，必须1开头
						throw new WaybillValidateException(
								WaybillValidateException.DELIVERY_MOBILEPHONE_FIRST_NUMBER_ILLEGAL);
					}
				}
				// 判定手机号码与电话号码是否存在一致的情况：存在电话号码组成：0+手机号码这种情况，需要过滤掉
				if (StringUtils.isNotEmpty(bean.getDeliveryCustomerPhone())) {
					if (mobilePhone.equals(bean.getDeliveryCustomerPhone())) {
						// 固定电话和手机号码重复，请重新输入
						throw new WaybillValidateException(
								WaybillValidateException.MOBILE_TELEPHONE_RE);
					}
					
					String deliveryCustomerPhone = bean
							.getDeliveryCustomerPhone().replaceFirst("^0*",
									"");
					if (StringUtils.isNotEmpty(deliveryCustomerPhone)// 不为空
							&& (deliveryCustomerPhone.length() == NumberConstants.NUMBER_8// 并且满足手机号码的校验条件
							|| (deliveryCustomerPhone.length() == NumberConstants.NUMBER_11 && deliveryCustomerPhone
									.startsWith(String.valueOf(1))))) {
						if (StringUtils.isNotEmpty(bean
								.getDeliveryCustomerMobilephone())
								&& mobilePhone
										.equals(deliveryCustomerPhone)) {
							throw new WaybillValidateException(
									WaybillValidateException.DELIVERY_MOBILEPHONE_LENGTH_ILLEGAL);
						}
					}
					
				}

			}

		}
	}

	public void validateCity(OmsOrderEntity bean) {
		String code = bean.getReceiveMethod();
		// 判断是否为自提
		if (WaybillConstants.SELF_PICKUP.equals(code)
				|| WaybillConstants.AIR_SELF_PICKUP.equals(code)
				|| WaybillConstants.AIR_PICKUP_FREE.equals(code)
				|| WaybillConstants.AIRPORT_PICKUP.equals(code)
				|| WaybillConstants.INNER_PICKUP.equals(code)
				|| WaybillConstants.DELIVER_FREE.equals(code)
				|| WaybillConstants.DELIVER_FREE_AIR.equals(code)) {
			return;
		}
		if (StringUtils.isEmpty(bean.getReceiveCustomerProvCode())
				|| StringUtils.isEmpty(bean.getReceiveCustomerCityCode())
				|| StringUtils.isEmpty(bean.getReceiveCustomerDictCode())) {
			throw new WaybillValidateException(
					WaybillValidateException.PRO_CITY_COUNTY);
		}
		if (StringUtils.isEmpty(bean.getReceiveCustomerAddress())) {
			throw new WaybillValidateException(
					WaybillValidateException.RECEIVER_CUSTOMER_ADDRESS_NULL);
		}
	}

	public void checkAddress(OmsOrderEntity bean) {
		// 判断收货方式是否为自提 如果不是自提 就判断收货人地址输入的是否含有特殊字符 可输入中文、英文、数字、空格
		// WaybillConstants.SELF_PICKUP.equals(bean.getReceiveMethod().getValueCode()
		if (StringUtils.isNotEmpty(bean.getReceiveMethod())
				&& (WaybillConstants.SELF_PICKUP
						.equals(bean.getReceiveMethod())
						|| WaybillConstants.INNER_PICKUP.equals(bean
								.getReceiveMethod())
						|| WaybillConstants.AIR_PICKUP_FREE.equals(bean
								.getReceiveMethod())
						|| WaybillConstants.AIR_SELF_PICKUP.equals(bean
								.getReceiveMethod()) || WaybillConstants.AIRPORT_PICKUP
							.equals(bean.getReceiveMethod()))) {
			LOGGER.info("是自提的提货方式");
		} else {
			if (StringUtil.isNotEmpty(bean.getReceiveCustomerAddress())) {
				// String a = "^[\\s\u4e00-\u9fa5A-Za-z0-9]+$";
				String a = "^[\\-\u4e00-\u9fa5A-Za-z0-9]+$";
				if (!bean.getReceiveCustomerAddress().matches(a)) {
					// 收货客户地址只能录入（汉字、-、数字、字母）！
					throw new WaybillValidateException(
							WaybillValidateException.RECEIVE_ADDRESS_ERROR);
				}
			}
		}
	}

	/**
	 * 
	 * 校验目的站
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-4 下午02:26:27
	 */
	private void validateDistination(OmsOrderEntity bean) {
		if (StringUtils.isEmpty(bean.getCustomerPickupOrgCode())) {
			throw new WaybillValidateException(
					WaybillValidateException.PICK_UP_ORGCODE_NO_ALLOW);
		}
	}

	/**
	 * 
	 * 验证提货方式为机场自提时，不能开到付
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-13 下午03:21:42
	 */
	private void validatePayment(OmsOrderEntity bean) {

		if (bean.getPaidMethod() == null) {
			throw new WaybillValidateException(
					WaybillValidateException.PAID_METHOD_NULL);
		}
		// 付款方式如果是网上支付需要限制有订单且来自于官网，并且在官网下单时要求的付款方式也应该是网上支付
		if (WaybillConstants.ONLINE_PAYMENT.equals(bean.getPaidMethod())) {
			if (!WaybillConstants.CRM_ORDER_CHANNEL_ONLINE.equals(bean
					.getOrderChannel())
					|| !WaybillConstants.CRM_ORDER_PAYMENT_ONLINE.equals(bean
							.getPaidMethod())) {
				throw new WaybillValidateException(WaybillValidateException.INTERNET_PAY_FOR_DOP);
			}
		}
		// 只有月结客户才能开月结
		validateMonthEnd(bean);
		// 提货方式为机场自提，付款方式不能选择到付
		if (WaybillConstants.AIRPORT_PICKUP.equals(bean.getReceiveMethod())) {
			if (StringUtils.isNotEmpty(bean.getPaidMethod())) {
				if (WaybillConstants.ARRIVE_PAYMENT
						.equals(bean.getPaidMethod())) {
					// 提货方式为【机场自提】时，到付金额必须为0，付款方式不能为到付
					throw new WaybillValidateException(
							WaybillValidateException.PAID_METHOD_FC_ILLEGAL);
				}
			}
			/**
			 * 没有到付选项
			 */
			/*
			 * if (bean.getToPayAmount().compareTo(BigDecimal.ZERO) > 0) { throw
			 * new WaybillValidateException(i18n.get(
			 * "foss.gui.creating.calculateAction.exception.validateArrivePayment.two"
			 * )); }
			 */
		}
		// 空运以及偏线无法选择网上支付  
		// 运输类型
		valiOtherPaidMethod(bean);
	}

	/**
	 * 校验其他支付类型
	 * @param bean
	 */
	private void valiOtherPaidMethod(OmsOrderEntity bean) {
		String productVo = getTransType(bean.getProductCode());
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT
				.equals(productVo)
				|| ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE
						.equals(productVo)) {
			if (WaybillConstants.ONLINE_PAYMENT.equals(bean.getPaidMethod())) {
				// 无法进行网上支付，请重新选择付款方式或重新确认运输性质
				throw new WaybillValidateException(productVo
						+ WaybillValidateException.PAID_METHOD_OL_ILLEGAL);
			}
		}
		/**
		 * 临欠、散客开单付款方式为临时欠款时，客户编码不允许为空
		 */
		if (WaybillConstants.TEMPORARY_DEBT.equals(bean.getPaidMethod())) {
			// 判断客户编码是否为空
			if (StringUtil.isEmpty(bean.getDeliveryCustomerCode())) {
				throw new WaybillValidateException(WaybillValidateException.CUSTOMER_CODE_NULL);
			}
		}
	}

	/**
	 * 校验月结
	 * @param bean
	 */
	private void validateMonthEnd(OmsOrderEntity bean) {
		if (WaybillConstants.MONTH_PAYMENT.equals(bean.getPaidMethod())) {
			CusBargainVo vo = new CusBargainVo();
			vo.setChargeType(WaybillConstants.MONTH_END);
			vo.setCustomerCode(bean.getDeliveryCustomerCode());
			WaybillPendingEntity waybillPendingEntity = waybillPendingDao
					.queryByWaybillNumber(bean.getWaybillNo());
			if (null != waybillPendingEntity
					&& StringUtils.isNotEmpty(waybillPendingEntity
							.getPendingType())) {
				// 判断运单是否是补录
				if (WaybillConstants.WAYBILL_PICTURE_TYPE_PDA_PENDING
						.equals(waybillPendingEntity.getPendingType())
						|| WaybillConstants.WAYBILL_STATUS_PC_PENDING
								.equals(waybillPendingEntity.getPendingType())) {
					// 如果是补录运单，将开单时间设为运单开单时间
					vo.setBillDate(bean.getOrderTime());
				}
				// 设置开单时间为当前时间
				vo.setBillDate(new Date());
			} else {
				// 设置开单时间为当前时间
				vo.setBillDate(new Date());
			}
			vo.setBillOrgCode(bean.getIncomeOrgCode());
			if (null == cusBargainService.queryCusBargainByVo(vo)) {
				// 该客户不能开月结单
				throw new WaybillValidateException(
						WaybillValidateException.CAN_NOT_OPEN_MONTH_PAYMENT);
			}
		}
	}

	/**
	 * 
	 * 代收货款业务规则校验
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-5 上午11:35:22
	 */
	private void validateCod(OmsOrderEntity bean) {
		BigDecimal zero = BigDecimal.ZERO;
		BigDecimal codAmount = bean.getCodAmount();

		// 代收货款不能小于0
		if (codAmount != null && codAmount.compareTo(zero) < 0) {
			LOGGER.error("代收货款不能小于0!");
			throw new WaybillValidateException(WaybillValidateException.NULLORZERO_COD_AMOUNT);
		}

		if (codAmount == null || codAmount.compareTo(zero) == 0) {
			// 退款类型
			bean.setCodType("");
		} else {
				//根据提货网点查询该营业部是否能开代收款的订单
			SaleDepartmentEntity salesDept = saleDepartmentService.querySimpleSaleDepartmentByCode(bean.getCustomerPickupOrgCode());
			if(null==salesDept){
				OuterBranchEntity branchEntity = waybillManagerService.queryAgencyBranchInfo(bean.getCustomerPickupOrgCode());
				if(null==branchEntity){
					throw new WaybillValidateException(WaybillValidateException.SALESDEPT_NOT_FOUND);
				}
				// 如果该提货网点不能开代收货款，则报错不能开提货网点单
				if (branchEntity != null&& !FossConstants.YES.equals(branchEntity.getHelpCharge())) {
					throw new WaybillValidateException(WaybillValidateException.PICKUP_STATSION_CANNOT_COD);
				}
			}else{
				// 如果该提货网点不能开代收货款，则报错不能开提货网点单
				if (salesDept != null&& !FossConstants.YES.equals(salesDept.getCanAgentCollected())) {
					throw new WaybillValidateException(WaybillValidateException.PICKUP_STATSION_CANNOT_COD);
				}
				// 营业部代收货款上限校验
				if (null != salesDept
						&& StringUtils.isNotEmpty(salesDept.getAgentCollectedUpperLimit())
						&& Double.valueOf(salesDept.getAgentCollectedUpperLimit()) < bean.getCodAmount().doubleValue()) {
					throw new WaybillValidateException(WaybillValidateException.COD_OVERLIMIT);
				}
			}
			validateBankInfo(bean);
			//此行代码应该替换为StringUtils.isEmpty(bean.getCodType());
			if (StringUtils.isEmpty(bean.getCodType())) {
				// 请选择退款类型
				throw new WaybillValidateException(
						WaybillValidateException.CODE_TYPE_NO_ALLOW);
			} else {
				if (!WaybillConstants.REFUND_TYPE_VALUE.equals(bean.getCodType())) {
					if (StringUtils.isEmpty(bean.getAccountName())
							|| StringUtils.isEmpty(bean.getAccountCode())
							|| StringUtils.isEmpty(bean.getAccountBank())) {
						// if
						// (WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get(WaybillConstants.LOGIN_TYPE).toString()))
						// {
						// 除审核退的代收货款业务类型必须填写客户银行账号信息
						throw new WaybillValidateException(
								WaybillValidateException.AUDIT_BANK_NO_ALLOW);
						// }
					}
				}
			}
		}
	}

	/**
	 * 
	 * 设置银行信息
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-11 下午08:11:49
	 */
	public void validateBankInfo(OmsOrderEntity bean) {
		CusAccountEntity entity =new CusAccountEntity();
		// 退款类型
		String codType = bean.getCodType();
		if (StringUtils.isEmpty(codType)){ 
			cleanBankInfo(bean);
			return;
		}
		if (bean.getDeliveryCustomerCode() == null
				|| "".equals(bean.getDeliveryCustomerCode())) {
			// 此发货客户不是公司会员客户，沒有银行账户信息
			throw new WaybillValidateException(
					WaybillValidateException.CUSTOMER_CODE_NULL);
		} else {
			List<CusAccountEntity> list = queryBankAccount(bean);
			//实际上应该进行非空判断，但是业务上不会存在
			for(int i=0;i<list.size();i++){
				if(list.get(i).getAccountNo().equals(bean.getAccountCode())){
					entity=list.get(i);
					break;
				}
			}
			if (entity != null) {
				if (StringUtils.isEmpty(entity.getAccountNature())) {
					// 选中的银行账户没有设置对公或者对私属性，
					throw new WaybillValidateException(
							WaybillValidateException.ACCOUNT_NATURE_NULL);
					// MsgBox.showInfo(i18n.get("foss.gui.creating.common.exception.nullAccountNature"));
				} else {
					// bean.setOpenBank(entity);
					// 即日退只能选择对私账户
					if (WaybillConstants.REFUND_TYPE_ONE_DAY.equals(bean.getCodType())) {
						if (DictionaryValueConstants.CRM_ACCOUNT_NATURE_PRIVATE_ACCOUNT
								.equals(entity.getAccountNature())) {
							bean.setAccountName(entity.getAccountName());// 开户人名称
							bean.setAccountCode(entity.getAccountNo());// 开户账号
							bean.setAccountBank(entity.getOpeningBankName());// 开户行名称
						} else {
							// 选中的银行账户没有设置对公或者对私属性，
							throw new WaybillValidateException(
									WaybillValidateException.ACCOUNT_NATURE_NULL);
						}
					} else {
						bean.setAccountName(entity.getAccountName());// 开户人名称
						bean.setAccountCode(entity.getAccountNo());// 开户账号
						bean.setAccountBank(entity.getOpeningBankName());// 开户行名称
					}
				}
			}
		}
	}

	/**
	 * 
	 * 清理银行信息
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-10 下午05:07:44
	 */
	public static void cleanBankInfo(OmsOrderEntity bean) {
		// 收款人名称
		bean.setAccountName("");
		// 收款人开户行
		bean.setAccountBank("");
		// 收款人银行账号
		bean.setAccountCode("");
		// 收款人银行信息
		// bean.setOpenBank(null);
	}

	/**
	 * 
	 * 查询客户银行账号信息
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-6 下午04:50:56
	 */
	public List<CusAccountEntity> queryBankAccount(OmsOrderEntity bean) {
		List<CusAccountEntity> list = null;

		// 从CRM正式客户中查询开户行信息
		// CustomerDto custDto =
		// customerRemotingService.queryCustInfoByCode(customerCode);
		CustomerDto custDto;
		if (StringUtil.isBlank(bean.getDeliveryCustomerCode())) {
			custDto = null;
		} else {
			custDto = customerService.queryCustInfoByCode(bean
					.getDeliveryCustomerCode());
		}
		// 判断是否为空
		if (custDto == null) {
			// 散客帐户集合
			// List<NonfixedCusAccountEntity> nonfixAccountList =
			// customerRemotingService.queryBankAccountByCode(customerCode);
			List<NonfixedCusAccountEntity> nonfixAccountList = nonfixedCusAccountService
					.queryCusAccountByCustCode(bean.getDeliveryCustomerCode());
			// 判空操作
			if (CollectionUtils.isNotEmpty(nonfixAccountList)) {
				List<CusAccountEntity> cusAccountList = new ArrayList<CusAccountEntity>();
				// 转换帐户对象
				for (NonfixedCusAccountEntity nonfixedAccount : nonfixAccountList) {
					CusAccountEntity cusAccount = convertCusToNonfixedAccount(nonfixedAccount);
					cusAccountList.add(cusAccount);
				}
				list = cusAccountList;
			}
		} else {
			list = custDto.getBankAccountList();
		}
		if (WaybillConstants.REFUND_TYPE_ONE_DAY.equals(bean.getCodType())) {
			if (list != null && list.size() > 0) {
				List<CusAccountEntity> newList = new ArrayList<CusAccountEntity>();
				for (int i = 0; i < list.size(); i++) {
					// BankEntity bank =
					// waybillService.queryBankByCode(list.get(i).getBankCode());
					// bankService.queryBankInfoByCode(code)
					BankEntity bank = bankService.queryBankInfoByCode(list.get(
							i).getBankCode());
					if (bank != null) {
						if (FossConstants.YES.equals(bank.getIntraDayType())) {
							newList.add(list.get(i));
						}
					}
				}
				if (newList.size() == 0) {
					return null;
				} else {
					return newList;
				}
			} else {
				return list;
			}
		} else {
			return list;
		}
	}
	/**
	 * 
	 * 验证件数不能为默认值0
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-12 下午08:16:30
	 */
	private void validateWeightVol(OmsOrderEntity bean) {
		if (bean.getGoodsQtyTotal() == null || bean.getGoodsQtyTotal().intValue() == 0 || bean.getGoodsQtyTotal().intValue()<0 ) {
			throw new WaybillValidateException(WaybillValidateException.NULL_GOODSQTYTOTAL);
		}
	}
	//设置发票标记TODO
	private void setInvoiceInfo(String customerCode, WaybillPendingEntity pendingEntity) {
		// 查询条件
		 CustomerDto customerDto = queryCustomerService.queryCustInvoiceTypeByCode(customerCode);
		 if(null==customerDto){
			 throw new WaybillValidateException(WaybillValidateException.CUSTOMER_NOT_ALLOW);
		 }
		//发票标记
		if(StringUtil.isNotEmpty(customerDto.getInvoiceType())){
			pendingEntity.setInvoice(customerDto.getInvoiceType());
		}else{
			pendingEntity.setInvoice(WaybillConstants.INVOICE_02);
		}
	}
	//设置是否统一结算 标杆合同  催款部门
	private void setDeliverySettleAndContactAndRemending(WaybillPendingEntity pendingEntity,ActualFreightEntity actualFreightEntity){
		//设置到达客户
		CusBargainEntity cusBargainEntityReceive =queryCustomerService.queryCusBargainByCustCode(pendingEntity.getReceiveCustomerCode());
		if (cusBargainEntityReceive != null && WaybillConstants.YES.equals(cusBargainEntityReceive.getAsyntakegoodsCode())) {
			actualFreightEntity.setArriveCentralizedSettlement(WaybillConstants.YES);//是否统一结算
			actualFreightEntity.setArriveContractOrgCode(cusBargainEntityReceive.getUnifiedCode());//标杆部门编号
			OrgAdministrativeInfoEntity administrativeInfoEntity =orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCode(cusBargainEntityReceive.getUnifiedCode());
			if(null!=administrativeInfoEntity&&StringUtils.isNotEmpty(administrativeInfoEntity.getName())){
				actualFreightEntity.setArriveContractOrgName(administrativeInfoEntity.getName());
			}
			actualFreightEntity.setArriveReminderOrgCode(cusBargainEntityReceive.getHastenfunddeptCode());//催款部门
		}else{
			actualFreightEntity.setArriveCentralizedSettlement(WaybillConstants.NO);
			actualFreightEntity.setArriveContractOrgCode(null);
			actualFreightEntity.setArriveContractOrgName(null);
			actualFreightEntity.setArriveReminderOrgCode(null);
		}
		//设置始发客户
		CusBargainEntity cusBargainEntityDelivery =queryCustomerService.queryCusBargainByCustCode(pendingEntity.getDeliveryCustomerCode());
		if (cusBargainEntityDelivery != null && WaybillConstants.YES.equals(cusBargainEntityDelivery.getAsyntakegoodsCode())) {
			
			actualFreightEntity.setStartCentralizedSettlement(WaybillConstants.YES);//是否统一结算
			actualFreightEntity.setStartContractOrgCode(cusBargainEntityDelivery.getUnifiedCode());//标杆部门编号
			OrgAdministrativeInfoEntity administrativeInfoEntity =orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCode(cusBargainEntityDelivery.getUnifiedCode());
			if(null!=administrativeInfoEntity&&StringUtils.isNotEmpty(administrativeInfoEntity.getName())){
				actualFreightEntity.setStartContractOrgName(administrativeInfoEntity.getName());
			}
			actualFreightEntity.setStartReminderOrgCode(cusBargainEntityDelivery.getHastenfunddeptCode());//催款部门
		}else{
			actualFreightEntity.setStartCentralizedSettlement(WaybillConstants.NO);
			actualFreightEntity.setStartContractOrgCode(null);
			actualFreightEntity.setStartContractOrgName(null);
			actualFreightEntity.setStartReminderOrgCode(null);
		}
	}
	
	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setPkpinsurGoodsDao(IInsurGoodsDao pkpinsurGoodsDao) {
		this.pkpinsurGoodsDao = pkpinsurGoodsDao;
	}

	public void setWaybillDisDtlPendingDao(IWaybillDisDtlPendingDao waybillDisDtlPendingDao) {
		this.waybillDisDtlPendingDao = waybillDisDtlPendingDao;
	}

	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setSaleDepartmentService(ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	public void setLabeledGoodDao(ILabeledGoodDao labeledGoodDao) {
		this.labeledGoodDao = labeledGoodDao;
	}

	public void setPkpsysConfigService(ISysConfigService pkpsysConfigService) {
		this.pkpsysConfigService = pkpsysConfigService;
	}

	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public void setCrmOrderService(ICrmOrderService crmOrderService) {
		this.crmOrderService = crmOrderService;
	}

	public void setBillCaculateService(IBillCaculateService billCaculateService) {
		this.billCaculateService = billCaculateService;
	}

	public void setWaybillPendingDao(IWaybillPendingDao waybillPendingDao) {
		this.waybillPendingDao = waybillPendingDao;
	}

	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}

	public void setActualFreightService(IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}
	
	public void setInempDiscountPlanService(IInempDiscountPlanService inempDiscountPlanService) {
		this.inempDiscountPlanService = inempDiscountPlanService;
	}

	public void setQueryCustomerService(IQueryCustomerService queryCustomerService) {
		this.queryCustomerService = queryCustomerService;
	}

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}
	public IWaybillProcessDao getWaybillProcessDao() {
		return waybillProcessDao;
	}
	public void setWaybillProcessDao(IWaybillProcessDao waybillProcessDao) {
		this.waybillProcessDao = waybillProcessDao;
	}
	public IWaybillFreightRouteService getWaybillFreightRouteService() {
		return waybillFreightRouteService;
	}
	public void setWaybillFreightRouteService(
			IWaybillFreightRouteService waybillFreightRouteService) {
		this.waybillFreightRouteService = waybillFreightRouteService;
	}
	public IWaybillExpressService getWaybillExpressService() {
		return waybillExpressService;
	}
	public void setWaybillExpressService(
			IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}
	public IProductService getProductService() {
		return productService;
	}
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	public IDispatchOrderEntityDao getDispatchOrderEntityDao() {
		return dispatchOrderEntityDao;
	}
	public void setDispatchOrderEntityDao(
			IDispatchOrderEntityDao dispatchOrderEntityDao) {
		this.dispatchOrderEntityDao = dispatchOrderEntityDao;
	}
	public IWaybillStockService getWaybillStockService() {
		return waybillStockService;
	}
	public void setWaybillStockService(IWaybillStockService waybillStockService) {
		this.waybillStockService = waybillStockService;
	}
	public IWaybillPendingDao getWaybillPendingDao() {
		return waybillPendingDao;
	}
	public IWaybillDao getWaybillDao() {
		return waybillDao;
	}
	public IActualFreightService getActualFreightService() {
		return actualFreightService;
	}
	public IWaybillManagerService getWaybillManagerService() {
		return waybillManagerService;
	}
	public IInsurGoodsDao getPkpinsurGoodsDao() {
		return pkpinsurGoodsDao;
	}
	public IWaybillDisDtlPendingDao getWaybillDisDtlPendingDao() {
		return waybillDisDtlPendingDao;
	}
	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		return orgAdministrativeInfoService;
	}
	public ISaleDepartmentService getSaleDepartmentService() {
		return saleDepartmentService;
	}
	public ILabeledGoodDao getLabeledGoodDao() {
		return labeledGoodDao;
	}
	public ISysConfigService getPkpsysConfigService() {
		return pkpsysConfigService;
	}
	public IConfigurationParamsService getConfigurationParamsService() {
		return configurationParamsService;
	}
	public ICrmOrderService getCrmOrderService() {
		return crmOrderService;
	}
	public IBillCaculateService getBillCaculateService() {
		return billCaculateService;
	}
	public IInempDiscountPlanService getInempDiscountPlanService() {
		return inempDiscountPlanService;
	}
	public IQueryCustomerService getQueryCustomerService() {
		return queryCustomerService;
	}
	public IEmployeeService getEmployeeService() {
		return employeeService;
	}
	public IBusinessLockService getBusinessLockService() {
		return businessLockService;
	}
	public void setWaybillProcessLogDao(IWaybillProcessLogDao waybillProcessLogDao) {
		this.waybillProcessLogDao = waybillProcessLogDao;
	}
	public void setEffectivePlanDetailService(
			IEffectivePlanDetailService effectivePlanDetailService) {
		this.effectivePlanDetailService = effectivePlanDetailService;
	}
	public ILineDao getLineDao() {
		return lineDao;
	}
	public void setLineDao(ILineDao lineDao) {
		this.lineDao = lineDao;
	}
	public ICalculateTransportPathService getCalculateTransportPathService() {
		return calculateTransportPathService;
	}
	public void setCalculateTransportPathService(
			ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}
	public IWaybillProcessLogDao getWaybillProcessLogDao() {
		return waybillProcessLogDao;
	}
	public IEffectivePlanDetailService getEffectivePlanDetailService() {
		return effectivePlanDetailService;
	}

	public IOmsOrderService getOmsOrderService() {
		return omsOrderService;
	}


	public void setOmsOrderService(IOmsOrderService omsOrderService) {
		this.omsOrderService = omsOrderService;
	}


	public IPdaAppInfoDao getPdaAppInfoDao() {
		return pdaAppInfoDao;
	}


	public void setPdaAppInfoDao(IPdaAppInfoDao pdaAppInfoDao) {
		this.pdaAppInfoDao = pdaAppInfoDao;
	}


	public ILTLEWaybillProcessService getLtleWaybillProcessService() {
		return ltleWaybillProcessService;
	}


	public void setLtleWaybillProcessService(
			ILTLEWaybillProcessService ltleWaybillProcessService) {
		this.ltleWaybillProcessService = ltleWaybillProcessService;
	}


	public IOmsOrderDao getOmsOrderDao() {
		return omsOrderDao;
	}


	public void setOmsOrderDao(IOmsOrderDao omsOrderDao) {
		this.omsOrderDao = omsOrderDao;
	}


	public ILabelPushProcessService getLabelPushProcessService() {
		return labelPushProcessService;
	}


	public void setLabelPushProcessService(
			ILabelPushProcessService labelPushProcessService) {
		this.labelPushProcessService = labelPushProcessService;
	}


	public INonfixedCusAccountService getNonfixedCusAccountService() {
		return nonfixedCusAccountService;
	}


	public void setNonfixedCusAccountService(
			INonfixedCusAccountService nonfixedCusAccountService) {
		this.nonfixedCusAccountService = nonfixedCusAccountService;
	}


	public IBankService getBankService() {
		return bankService;
	}


	public void setBankService(IBankService bankService) {
		this.bankService = bankService;
	}


	public ICustomerService getCustomerService() {
		return customerService;
	}


	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}


	public ICusBargainService getCusBargainService() {
		return cusBargainService;
	}


	public void setCusBargainService(ICusBargainService cusBargainService) {
		this.cusBargainService = cusBargainService;
	}

	public void setWoodenRequirementsPgDao(
			IWoodenRequirementsPgDao woodenRequirementsPgDao) {
		this.woodenRequirementsPgDao = woodenRequirementsPgDao;
	}

	public void setCrmOrderJMSService(ICrmOrderJMSService crmOrderJMSService) {
		this.crmOrderJMSService = crmOrderJMSService;
	}

	public void setEmployeeDao(IEmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}
	
	public void setlTLEWaybillProcessService(
			ILTLEWaybillProcessService lTLEWaybillProcessService) {
		this.lTLEWaybillProcessService = lTLEWaybillProcessService;
	}


	public void setBigcusDeliveryAddressDao(
			IBigcusDeliveryAddressDao bigcusDeliveryAddressDao) {
		this.bigcusDeliveryAddressDao = bigcusDeliveryAddressDao;
	}
	
}
