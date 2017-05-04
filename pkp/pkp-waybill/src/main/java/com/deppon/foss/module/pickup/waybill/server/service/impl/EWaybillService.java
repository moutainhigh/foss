package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

import com.deppon.esb.pojo.domain.crm2foss.UpdateEOrderRequest;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.context.SessionContext;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.server.web.message.IMessageBundle;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAsteriskSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBargainAppOrgService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusBargainService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPartSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPrintStarService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressVehiclesService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFreightRouteService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IGoodsAreaService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BargainAppOrgEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressPrintStarEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressVehiclesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptQueryDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptResultDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.FreightRouteLineDto;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IEWaybillOrderEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IEWaybillOrderLogEntityDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IModifEwaybillOrderRecordDao;
import com.deppon.foss.module.pickup.order.api.server.dao.IPreHandEWaybillOrderDao;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderTaskHandleService;
import com.deppon.foss.module.pickup.order.api.shared.define.DispatchOrderStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.EWaybillOrderEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.EWaybillOrderLogEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.ModifyEwaybillOrderLogEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.PreHandEWaybillOrderEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OrderHandleDto;
import com.deppon.foss.module.pickup.pricing.api.server.service.IBillCaculateService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillTransactionService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillTransactionEntity;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IEWaybillMessageDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IPdaScanDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillCHDtlPendingDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillToSuppleDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightBIService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICrmOrderJMSService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IEWaybillProcessService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IEWaybillService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPdaScanService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPickupToDoMsgService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISysConfigService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillChargeDtlService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillDisDtlService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillFreightRouteService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPackBIService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPaymentService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPendingService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRelateDetailEntityService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWoodenRequirementsService;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.OrgInfoDto;
import com.deppon.foss.module.pickup.waybill.server.hessian.WaybillHessianRemoting;
import com.deppon.foss.module.pickup.waybill.server.service.IWaybillStockService;
import com.deppon.foss.module.pickup.waybill.server.utils.Constants;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.RequestDO;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.VestBatchResult;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.VestResponse;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.service.IGrayScaleService;
import com.deppon.foss.module.pickup.waybill.shared.define.ExpWaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightBIEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.EWaybillMessageEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.EWaybillProcessEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.EcomWaybillRelateEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.EwaybillRelateEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PdaScanEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillCHDtlPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillChargeDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPaymentEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRelateDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillSupplementLogEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirementsEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmModifyInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmPaymentTypeEnum;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmReturnBillTypeEnum;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EffectiveDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LastWaybillRfcQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PdaScanQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ValueAddServiceDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillExpressPdaDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillSystemDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.BaseInfoInvokeException;
import com.deppon.foss.module.pickup.waybill.shared.exception.PdaInterfaceException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillLabelPrintException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillOrderHandleException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillPendingSaveException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillRfcException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillStoreException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillSubmitException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.module.pickup.waybill.shared.vo.GenerateUnActiveEWaybillVo;
import com.deppon.foss.module.settlement.consumer.api.server.service.IWaybillPickupService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillPickupInfoDto;
import com.deppon.foss.module.transfer.load.api.server.service.IAutoAddCodeService;
import com.deppon.foss.module.transfer.load.api.shared.domain.AutoAddCodeEntity;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TransportPathEntity;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillStockEntity;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * <p>电子运单服务类</p>
 * @author 136334-foss-bailei
 *
 */
public class EWaybillService implements IEWaybillService{
	/**
	 * 记录日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(EWaybillService.class);
	
	/**
	 * 常量值0
	 */
	public static final int ZERO = 0;
	
	/**
	 * 常量值1
	 */
	public static final int ONE = 1;
	
	private static final int NUMBER_10000 = 10000;
	
	private static final int NUMBER_3000 = 3000;
	
	private static final double POINT_001 = 0.01;

	
	 /**
	 * 定义常量值：0 
	 * 1、不可继承 
	 * 2、避免魔法数字
	 */
	private static final String ZEROSTR = "0";
	
	//电子运单后台数据作废自动受理
	private static final String EWAYBILL_OPERATE_INVALID_REASON = "电子运单后台数据作废自动受理";
	
	/**
	 * 常量值999999999
	 */
	public static final int MAX = 999999999;
	
	/**
	 * 运单管理接口
	 */
	IWaybillManagerService waybillManagerService;
	
	/**
	 * CUBC灰度接口
	 */
	private IGrayScaleService grayScaleService;
	
	/**
	 * 用来操作交互"用户信息"的数据库对应数据访问Service接口
	 */
	IUserService userService;
	
	/**
	 * 运单待处理保存接口
	 */
	IWaybillPendingService waybillPendingService;
	
	/**
	 * 人员 Service接口
	 */
	IEmployeeService employeeService; 
	
	/**
	 * 营业部 Service接口
	 */
	ISaleDepartmentService saleDepartmentService;
	
	/**
	 * GUI查询走货路径相关服务接口
	 */
	IWaybillFreightRouteService waybillFreightRouteService;
	
	/**
	 * 产品定义Service接口
	 */
	IProductService productService;
	
	/**
	 * 客户合同信息Service接口 
	 */
	ICusBargainService cusBargainService;
	
	/**
	 * 快递车辆Service接口
	 */
	IExpressVehiclesService expressVehiclesService; 
	
	/**
	 * 合同适用部门Service接口 
	 */
	IBargainAppOrgService bargainAppOrgService;	
	
	/**
	 * 快递运单Service
	 */
	IWaybillExpressService waybillExpressService;	
	
	/**
	 * 组织信息 Service接口
	 */
	IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 快递点部营业部映射关系Service
	 */
	IExpressPartSalesDeptService expressPartSalesDeptService;
	
	/**
	 * 运单开单服务
	 */
	IWaybillPickupService  waybillPickupService;
	
	/**
	 * 国际化资源接口
	 */
	IMessageBundle messageBundle; 
	
	/**
	 * 付款明细服务接口
	 */
	IWaybillPaymentService waybillPaymentService;
	
	/**
	 * 运单折扣费用明细服务接口 
	 */
	IWaybillDisDtlService waybillDisDtlService;
	
	/**
	 * 运单费用明细服务接口
	 */
	IWaybillChargeDtlService waybillChargeDtlService;
	
	/**
	 * 运单入库接口
	 */
	IWaybillStockService waybillStockService;
	
	/**
	 * 客户信息Dao接口
	 */
	ICustomerDao customerDao;
	
	/**
	 * 电子运单订单实体DAO
	 */
	IEWaybillOrderEntityDao ewaybillOrderEntityDao;
	
	/**
	 * 电子运单订单日志实体DAO
	 */
	IEWaybillOrderLogEntityDao ewaybillOrderLogEntityDao ;
	
	/**
	 * 
	 * 调度订单实体DAO
	 *
	 */
	private IDispatchOrderEntityDao dispatchOrderEntityDao; 
	
	/**
	 * 接送货配置参数
	 */
	private ISysConfigService pkpsysConfigService;
	
	
	/**
	 * 实际承运信息表服务类
	 */
	private IActualFreightService actualFreightService;
	
	/**
	 * GUI服务类
	 */
	private IPickupToDoMsgService pickupToDoMsgService;
	
	/**
	 * 
	 * 部门复杂查询接口
	 *
	 */
	IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	/**
	 * 
	 * 走货路径服务类
	 *
	 */
	IFreightRouteService freightRouteService;
	
	/**
	 * 
	 * 加星标营业部Service接口
	 *
	 */
	IAsteriskSalesDeptService asteriskSalesDeptService;
	
	/**
	 * 
	 * ExpressPrintStar的Service接口
	 *
	 */
	IExpressPrintStarService expressPrintStarService;
	
	/**
	 * 
	 * CRM订单数据JMS服务接口
	 *
	 */
	ICrmOrderJMSService crmOrderJMSService;
	
	IBillCaculateService billCaculateService;

	/**
	 * 作废运单数据接口
	 */
	private IWaybillToSuppleDao waybillToSuppleDao;
	
	/**
	 * 运单BI服务类
	 */
	private IWaybillPackBIService waybillPackBIService;
	
	/**
	 * 实际承运信息表BI服务类
	 */
	private IActualFreightBIService actualFreightBIService;
	
	/**
	 * 运单数据持久层
	 */
	private IWaybillDao waybillDao;
	
	/**
	 * 运单完结状态操作Service
	 */
	private IWaybillTransactionService waybillTransactionService;
	
	/**
	 * 库区服务类
	 */
	private IGoodsAreaService goodsAreaService;
	
	/**
	 * 快递代理置服务类
	 */
	private ILdpAgencyDeptService ldpAgencyDeptService;
	
	/**
	 * 外场服务类
	 */
	private IOutfieldService outfieldService;
	
	/**
	 * 待补录运单实体类
	 */
	private IWaybillPendingDao waybillPendingDao;
	
	/**
	 * 带打木架需求服务类
	 */
	private IWoodenRequirementsService woodenRequirementsService;
	
	/**
	 * 行政区域服务类
	 */
	private IAdministrativeRegionsService administrativeRegionsService;
	
	/**
	 * 待处理运单费用服务类
	 */
	private IWaybillCHDtlPendingDao waybillCHDtlPendingDao;
	/**
	 * 约车订单状态修改接口
	 */
	private IOrderTaskHandleService orderTaskHandleService;
	
	/**
	 * 走货路径
	 */
	private ICalculateTransportPathService calculateTransportPathService;
	
	/**
	 * 库存服务类
	 */
	private IStockService stockService;
	
	private IPdaScanDao pdaScanDao;
	
	private IEWaybillProcessService eWaybillProcessService;
	
	private IPreHandEWaybillOrderDao preHandEWaybillOrderDao;
	/**
	 * 自动补码 电子运单提交
	 */
	private  IAutoAddCodeService  autoAddCodeService ;

	private IModifEwaybillOrderRecordDao modifEwaybillOrderRecordDao;
	
	/**
	 * 业务锁
	 */
	private IBusinessLockService businessLockService;
	
	/**
	 * PDA扫描信息处理相关服务
	 */
	private IPdaScanService pdaScanService;
	
	private IWaybillRelateDetailEntityService waybillRelateDetailEntityService;
	
	private IEWaybillService ewaybillService;
	
	private IEWaybillMessageDao eWaybillMessageDao;
	
	/**
	 * 业务锁定超时自动释放时间:15秒
	 */
	private static final int LOCK_TIMEOUT = 0;
	/**
	 * 将订单转化为可校验的运单
	 * 
	 * @author 136334-foss-BaiLei
	 * @date 2014-9-13下午5:22:49
	 */
 	private WaybillDto generatePreEWaybill(DispatchOrderEntity order , EWaybillOrderEntity eWaybill){
		if(order == null || eWaybill == null){
			throw new WaybillValidateException(WaybillValidateException.DISPATCH_ORDER_NULL);
		}
	
		WaybillDto waybillDto = new WaybillDto();
		
		//waybillEntity运单实体
		waybillDto.setWaybillEntity(getWaybillEntity(order, eWaybill));
		
		WaybillEntity waybillEntity = waybillDto.getWaybillEntity();
		
		//设置内部带货部门与费用承担部门
		WaybillExpressEntity waybillExpressEntity = getWaybillExpressEntity(waybillEntity, order);
		
		//将值赋给WaybillExpressEntity
		waybillDto.setWaybillExpressEntity(waybillExpressEntity);
		
		//currentInfo当前用户(根据用户工号查询currentInfo)
		waybillDto.setCurrentInfo(getEWaybilCurrentInfo(waybillEntity));
		
		//计算费用，并填充waybillChargeDtlEntity、waybillDisDtlEntity,waybillPayment信息；
		//woodenRequirementsEntity快递没有，不予处理
		waybillExpressService.calculateExpressAllFee(waybillDto,FossConstants.INACTIVE);
		
		//actualFreightEntity运单冗余明细
		waybillDto.setActualFreightEntity(getActualFreightEntity(waybillEntity,eWaybill));
		 
		//开户行信息
		if(waybillEntity.getCodAmount() != null && waybillEntity.getCodAmount().compareTo(BigDecimal.ZERO) > 0){
			waybillDto.setOpenBank(waybillExpressService.queryEWaybillCusAccountInfo(waybillEntity));
		}
		
		return waybillDto;
	}
 	
 	/**
 	 * <P>将PDA传过来的散客信息转化为可校验的运单</P>
 	 * @author Foss-105888-Zhangxingwang
 	 * @date 2014-10-16 15:35:08
 	 * @param pdaDto
 	 * @return
 	 */
 	private WaybillDto generatePrePdaDto(WaybillExpressPdaDto pdaDto){
 		if(pdaDto == null){
			throw new WaybillValidateException(WaybillValidateException.DISPATCH_ORDER_NULL);
		}
 		EWaybillOrderEntity ewaybill = ewaybillOrderEntityDao.queryEWaybillByOrderNo(pdaDto.getOrderNo());
 		if(ewaybill == null){
			throw new WaybillValidateException(WaybillValidateException.DISPATCH_ORDER_NULL);
		}
 		WaybillDto waybillDto = new WaybillDto();
 		//初始化运单实体
 		WaybillEntity waybillEntity = new WaybillEntity();
 		waybillEntity.setWaybillNo(pdaDto.getWaybillNo());
 		waybillEntity.setOrderNo(pdaDto.getOrderNo());
// 		WaybillPendingEntity waybillPendingEntity = waybillPendingService.queryPendingByNo(pdaDto.getWaybillNo());
// 		waybillEntity.setOrderChannel(waybillPendingEntity.getOrderChannel());
// 		waybillEntity.setOrderPaidMethod(waybillPendingEntity.getOrderPaidMethod());
 		waybillEntity.setDeliveryCustomerId(pdaDto.getDeliveryCustomerId());
 		waybillEntity.setDeliveryCustomerCode(pdaDto.getDeliveryCustomerCode());
// 		waybillEntity.setDeliveryBigCustomer(waybillPendingEntity.getDeliveryBigCustomer());
 		waybillEntity.setDeliveryCustomerName(pdaDto.getDeliveryCustomerName());
 		waybillEntity.setDeliveryCustomerMobilephone(pdaDto.getDeliveryCustomerMobilephone());
 		waybillEntity.setDeliveryCustomerPhone(pdaDto.getDeliveryCustomerPhone());
 		waybillEntity.setDeliveryCustomerContact(pdaDto.getDeliveryCustomerContact());
 		waybillEntity.setDeliveryCustomerNationCode(pdaDto.getDeliveryCustomerNationCode());
 		waybillEntity.setDeliveryCustomerProvCode(pdaDto.getDeliveryCustomerProvCode());
 		waybillEntity.setDeliveryCustomerCityCode(pdaDto.getDeliveryCustomerCityCode());
 		waybillEntity.setDeliveryCustomerDistCode(pdaDto.getDeliveryCustomerDistCode());
 		waybillEntity.setDeliveryCustomerAddress(pdaDto.getDeliveryCustomerAddress());
 		waybillEntity.setReceiveCustomerId(pdaDto.getReceiveCustomerId());
 		waybillEntity.setReceiveCustomerCode(pdaDto.getReceiveCustomerCode());
// 		waybillEntity.setReceiveBigCustomer(waybillPendingEntity.getReceiveBigCustomer());
 		waybillEntity.setReceiveCustomerName(pdaDto.getReceiveCustomerName());
 		waybillEntity.setReceiveCustomerMobilephone(pdaDto.getReceiveCustomerMobilephone());
 		waybillEntity.setReceiveCustomerPhone(pdaDto.getReceiveCustomerPhone());
 		waybillEntity.setReceiveCustomerContact(pdaDto.getReceiveCustomerContact());
 		waybillEntity.setReceiveCustomerNationCode(pdaDto.getReceiveCustomerNationCode());
 		waybillEntity.setReceiveCustomerProvCode(pdaDto.getReceiveCustomerProvCode());
 		waybillEntity.setReceiveCustomerCityCode(pdaDto.getReceiveCustomerCityCode());
 		waybillEntity.setReceiveCustomerDistCode(pdaDto.getReceiveCustomerDistCode());
 		waybillEntity.setReceiveCustomerAddress(pdaDto.getReceiveCustomerAddress());
 		waybillEntity.setReceiveOrgCode(pdaDto.getStartOrg());
// 		waybillEntity.setProductId(waybillPendingEntity.getProductId());
 		waybillEntity.setProductCode(pdaDto.getProductCode());
 		waybillEntity.setReceiveMethod(pdaDto.getReceiveMethod());
// 		waybillEntity.setCustomerPickupOrgCode(waybillPendingEntity.getCustomerPickupOrgCode());
 		waybillEntity.setLoadMethod(pdaDto.getLoadMethod());
 		waybillEntity.setTargetOrgCode(pdaDto.getTargetOrgCode());
// 		waybillEntity.setPickupToDoor(waybillPendingEntity.getPickupToDoor());
 		waybillEntity.setDriverCode(pdaDto.getCreateUserCode());
// 		waybillEntity.setPickupCentralized(waybillPendingEntity.getPickupCentralized());
// 		waybillEntity.setLoadLineCode(waybillPendingEntity.getLoadLineCode());
// 		waybillEntity.setLoadOrgCode(waybillPendingEntity.getLoadOrgCode());
 		waybillEntity.setLastLoadOrgCode(pdaDto.getLastLoadOrgCode());
// 		waybillEntity.setPreDepartureTime(waybillPendingEntity.getPreDepartureTime());
 		waybillEntity.setPreCustomerPickupTime(pdaDto.getPreCustomerPickupTime());
// 		waybillEntity.setCarDirectDelivery(waybillPendingEntity.getCarDirectDelivery());
 		waybillEntity.setGoodsName(pdaDto.getGoodsName());
 		waybillEntity.setGoodsQtyTotal(pdaDto.getGoodsQty());
 		waybillEntity.setGoodsWeightTotal(pdaDto.getGoodsWeightTotal());
 		waybillEntity.setGoodsVolumeTotal(pdaDto.getGoodsVolumeTotal());
 		waybillEntity.setGoodsSize(pdaDto.getGoodsSize());
 		waybillEntity.setGoodsTypeCode(pdaDto.getGoodsTypeCode());
 		waybillEntity.setPreciousGoods(pdaDto.getPreciousGoods());
 		waybillEntity.setSpecialShapedGoods(pdaDto.getSpecialShapedGoods());
 		waybillEntity.setOuterNotes(pdaDto.getOuterNotes());
 		waybillEntity.setInnerNotes(pdaDto.getInnerNotes());
// 		waybillEntity.setGoodsPackage(waybillPendingEntity.getGoodsPackage());
 		waybillEntity.setInsuranceAmount(pdaDto.getInsuranceAmount());
 		waybillEntity.setInsuranceRate(pdaDto.getInsuranceRate());
 		waybillEntity.setInsuranceFee(pdaDto.getInsuranceFee());
 		//设置代收货款
 		waybillEntity.setCodAmount(pdaDto.getCodAmount());
 		waybillEntity.setCodRate(pdaDto.getCodRate());
 		waybillEntity.setCodFee(pdaDto.getCodFee());
 		waybillEntity.setRefundType(pdaDto.getRefundType());
 		waybillEntity.setReturnBillType(pdaDto.getReturnBillType());
 		waybillEntity.setSecretPrepaid(pdaDto.getSecretPrepaid());
 		waybillEntity.setToPayAmount(pdaDto.getToPayAmount());
 		waybillEntity.setPrePayAmount(pdaDto.getPrePayAmount());
 		waybillEntity.setDeliveryGoodsFee(pdaDto.getDeliveryGoodsFee());
 		waybillEntity.setOtherFee(pdaDto.getOtherFee());
 		waybillEntity.setPackageFee(pdaDto.getPackageFee());
// 		waybillEntity.setPromotionsFee(waybillPendingEntity.getPromotionsFee());
// 		waybillEntity.setBillingType(waybillPendingEntity.getBillingType());
// 		waybillEntity.setUnitPrice(waybillPendingEntity.getUnitPrice());
// 		waybillEntity.setTransportFee(waybillPendingEntity.getTransportFee());
 		BigDecimal valueAddFee = BigDecimal.ZERO;
 		List<ValueAddServiceDto> list = pdaDto.getValueAddServiceDtoList();
 		if(list!=null&& list.size()>0){
 			valueAddFee = BigDecimal.ZERO;
 			for(ValueAddServiceDto dto : list){
 				valueAddFee = valueAddFee.add(dto.getAmount());
 			}
 		}
 		waybillEntity.setValueAddFee(valueAddFee);
 		waybillEntity.setPaidMethod(pdaDto.getPaidMethod());
// 		waybillEntity.setArriveType(waybillPendingEntity.getArriveType());
// 		waybillEntity.setForbiddenLine(waybillPendingEntity.getForbiddenLine());
// 		waybillEntity.setFreightMethod(waybillPendingEntity.getFreightMethod());
// 		waybillEntity.setFlightShift(waybillPendingEntity.getFlightShift());
 		waybillEntity.setTotalFee(pdaDto.getAmount());
 		waybillEntity.setPromotionsCode(pdaDto.getDiscountNo());
 		waybillEntity.setCreateTime(pdaDto.getCreateTime());
 		waybillEntity.setBillTime(pdaDto.getBillStart());
// 		waybillEntity.setCreateUserCode(WaybillConstants.CREATOR_SYSTEM);//开单人工号
// 		EmployeeEntity entity = employeeService.queryEmployeeByEmpCode(WaybillConstants.CREATOR_SYSTEM);
// 		waybillEntity.setCreateOrgCode(entity.getOrgCode());//开单人组织号
// 		waybillEntity.setCreateOrgCodeList(waybillPendingEntity.getCreateOrgCodeList());
// 		waybillEntity.setReceiveOrgCodeList(waybillPendingEntity.getReceiveOrgCodeList());
// 		waybillEntity.setRefId(waybillPendingEntity.getRefId());
// 		waybillEntity.setRefCode(waybillPendingEntity.getRefCode());
// 		waybillEntity.setCurrencyCode(waybillPendingEntity.getCurrencyCode());
// 		waybillEntity.setIsWholeVehicle(waybillPendingEntity.getIsWholeVehicle());
// 		waybillEntity.setWholeVehicleAppfee(waybillPendingEntity.getWholeVehicleAppfee());
// 		waybillEntity.setWholeVehicleActualfee(waybillPendingEntity.getWholeVehicleActualfee());
 		waybillEntity.setAccountName(pdaDto.getAccountName());
 		waybillEntity.setAccountCode(pdaDto.getAccountCode());
 		waybillEntity.setAccountBank(pdaDto.getAccountBank());
 		waybillEntity.setBillWeight(pdaDto.getBillWeight());
 		waybillEntity.setPickupFee(pdaDto.getPickupFee());
 		waybillEntity.setServiceFee(pdaDto.getServiceFee());
// 		waybillEntity.setPreArriveTime(waybillPendingEntity.getPreArriveTime());
// 		waybillEntity.setTransportType(waybillPendingEntity.getTransportType());
// 		waybillEntity.setPrintTimes(waybillPendingEntity.getp)打印次数
// 		waybillEntity.setAddTime(waybillPendingEntity.getAddTime());
 		waybillEntity.setContactAddressId(pdaDto.getContactAddressId());
// 		waybillEntity.setFlightNumberType(waybillPendingEntity.getFlightNumberType());
 		waybillEntity.setCollectionDepartment(null);//收款部门
 		waybillEntity.setTransportationRemark(pdaDto.getTransportationRemark());
 		waybillEntity.setActive(FossConstants.YES);
// 		waybillEntity.setIsPassOwnDepartment(waybillPendingEntity.getIsPassOwnDepartment());
// 		waybillEntity.setOtherPackage(waybillPendingEntity.getOtherPackage());
 		waybillEntity.setPaperNum(pdaDto.getPaper());
 		waybillEntity.setWoodNum(pdaDto.getWood());
 		waybillEntity.setFibreNum(pdaDto.getFibre());
 		waybillEntity.setSalverNum(pdaDto.getSalver());
 		waybillEntity.setMembraneNum(pdaDto.getMembrane());
 		waybillEntity.setDeliverCustContactId(pdaDto.getDeliverCustContactId());
 		waybillEntity.setReceiverCustContactId(pdaDto.getReceiveCustomerContactId());
// 		waybillEntity.setPendingType(waybillPendingEntity.getPendingType());
 		waybillEntity.setLicensePlateNum(pdaDto.getLicensePlateNum());
// 		waybillEntity.setOrderVehicleNum(waybillPendingEntity.getOrderVehicleNum());
// 		waybillEntity.setCreateUserName(entity.getCreateUser());
// 		waybillEntity.setCreateUserDeptName(entity.getOrgName());
 		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(pdaDto.getStartOrg());
 		waybillEntity.setReceiveOrgName(orgEntity.getName());
// 		waybillEntity.setCustomerPickupOrgName(customerPickupOrgName)//提货网点名称
// 		waybillEntity.setLoadOrgName(pdaDto.getL)//配载部门名称
 		OrgAdministrativeInfoEntity loadOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(pdaDto.getLastLoadOrgCode());
 		waybillEntity.setLastLoadOrgName(loadOrg.getName());
// 		waybillEntity.setTransferStartOrgCode(transferStartOrgCode)//中转 返货的时候的中转起点外场
// 		waybillEntity.setTransferStartOrgName(transferStartOrgName)//中转 返货的时候的中转起点外场名称
 		waybillEntity.setKilometer(pdaDto.getKilometer());
// 		waybillEntity.setIsInit(isInit)//是否迁移数据
// 		waybillEntity.setIsEconomyGoods(isEconomyGoods)//是否经济自提
 		waybillEntity.setEconomyGoodsType(pdaDto.getEconomyGoodsType());
 		waybillEntity.setSpecialOffer(pdaDto.getSpecialOffer());
 		waybillEntity.setIsExpress(FossConstants.YES);
 		waybillEntity.setWaybillType(WaybillConstants.WAYBILL_EWAYBILL_TYPE);
		//将值赋给WaybillExpressEntity
 		WaybillExpressEntity waybillExpressEntity = getWaybillExpressEntity(waybillEntity, null);
		
		if(FossConstants.YES.equals(pdaDto.getNeedDepponCustomerCode())){
			//如果是内部带货
			if(StringUtils.isNotEmpty(pdaDto.getSendEmployeeCode())){
				EmployeeEntity entitys = employeeService.queryEmployeeByEmpCode(pdaDto.getSendEmployeeCode());
				waybillExpressEntity.setDeliveryEmployeeCode(pdaDto.getSendEmployeeCode());
				if(entitys!=null && entitys.getOrgCode()!=null){
					if(!entitys.getOrgCode().equals(pdaDto.getInnerPickupFeeBurdenDept())){//若pda传递过来的内部带货发货人工号查询出来的承担费用部门与pda传递过来的承担费用部门不一样，则以查询出来的为主
						waybillExpressEntity.setInnerPickupFeeBurdenDept(entitys.getOrgCode());
					}else{
						waybillExpressEntity.setInnerPickupFeeBurdenDept(pdaDto.getInnerPickupFeeBurdenDept());
					}
				}
			}
		}
		waybillDto.setWaybillExpressEntity(waybillExpressEntity);
		//currentInfo当前用户(根据用户工号查询currentInfo)
		waybillDto.setCurrentInfo(getEWaybilCurrentInfo(waybillEntity));
		
		//计算费用，并填充waybillChargeDtlEntity、waybillDisDtlEntity信息；
		//waybillPaymentEntity电子运单没有，不予处理、woodenRequirementsEntity快递没有，不予处理
		waybillExpressService.calculateExpressAllFee(waybillDto,FossConstants.ACTIVE);
		
		//actualFreightEntity运单冗余明细
		waybillDto.setActualFreightEntity(getActualFreightEntity(waybillEntity,ewaybill));
		
		//开户行信息
		waybillDto.setOpenBank(waybillExpressService.queryEWaybillCusAccountInfo(waybillEntity));
		
		return waybillDto;
 	}

 	/**
	 * 校验运单
	 * 
	 * @author 136334-foss-BaiLei
	 * @date 2014-9-13下午5:22:49
	 */
	public void validateEWaybill(WaybillDto waybillDto){
		//接送货校验
		validateEWaybillPKP(waybillDto);
		WaybillPickupInfoDto waybillPickupInfoDto = new WaybillPickupInfoDto();
		waybillPickupInfoDto = gainWaybillPickupInfo(waybillDto);
		//结算校验
		String pickupMode = StringUtil.defaultIfNull(waybillDto.getWaybillEntity().getReceiveMethod());
		//非内部带货走结算验证
		if (!WaybillConstants.INNER_PICKUP.equals(pickupMode) && !(waybillDto.getWaybillEntity().getTotalFee() != null
				&& BigDecimal.ZERO.compareTo(waybillDto.getWaybillEntity().getTotalFee()) == 0)) {
			RequestDO requestDO = new RequestDO();
			requestDO.setServiceCode(EWaybillService.class.getName()+".validateEWaybill");
			requestDO.setOrigin(Constants.GRAY_VESTSYSTEM_CODE_FOSS_JSH);
			requestDO.setSourceBillNos(waybillPickupInfoDto.getWaybillNo());
			requestDO.setSourceBillType(Constants.GRAY_SOURCE_BILLTYPE_W);
			VestResponse response = new VestResponse();
			try {
				response = grayScaleService.vestAscription(requestDO);
			} catch (Exception e1) {
			    throw new WaybillRfcException(Constants.GRAY_SERVICE_EXCEPTION);
			}
			//如果灰度接口异常，那么为null
			List<VestBatchResult> vestBatchResults = response.getVestBatchResult();
			VestBatchResult vestResult =  CollectionUtils.isEmpty(vestBatchResults)?new VestBatchResult():vestBatchResults.get(0);
			if(!Constants.GRAY_VESTSYSTEM_CODE_CUBC.equals(vestResult.getVestSystemCode())){
				// 结算接口：生成结算相关财务单据
				waybillPickupService.electriWaybillCanAdd(waybillPickupInfoDto,waybillDto.getCurrentInfo());
			}
		}
	}
	
	
	/**
	 * 权益之计，设置各种信息不报错
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-10-2 15:47:17
	 * @param waybillPickupInfoDto
	 */
	private void initWaybillFeeToZera(WaybillPickupInfoDto waybillPickupInfoDto) {
		if(waybillPickupInfoDto.getTotalFee() == null){
			waybillPickupInfoDto.setTotalFee(BigDecimal.ZERO);
		}
		if(waybillPickupInfoDto.getPrePayAmount() == null){
			waybillPickupInfoDto.setPrePayAmount(BigDecimal.ZERO);
		}
		if(waybillPickupInfoDto.getToPayAmount() == null){
			waybillPickupInfoDto.setToPayAmount(BigDecimal.ZERO);
		}
		if(waybillPickupInfoDto.getCodAmount() == null){
			waybillPickupInfoDto.setCodAmount(BigDecimal.ZERO);					
		}
		if(waybillPickupInfoDto.getTransportFee() == null){
			waybillPickupInfoDto.setTransportFee(BigDecimal.ZERO);
		}
		if(waybillPickupInfoDto.getPickupFee() == null){
			waybillPickupInfoDto.setPickupFee(BigDecimal.ZERO);
		}
		if(waybillPickupInfoDto.getDeliveryGoodsFee() == null){
			waybillPickupInfoDto.setDeliveryGoodsFee(BigDecimal.ZERO);
		}
		if(waybillPickupInfoDto.getPackageFee() == null){
			waybillPickupInfoDto.setPackageFee(BigDecimal.ZERO);
		}
		if(waybillPickupInfoDto.getCodFee() == null){
			waybillPickupInfoDto.setCodFee(BigDecimal.ZERO);
		}
		if(waybillPickupInfoDto.getInsuranceFee() == null){
			waybillPickupInfoDto.setInsuranceFee(BigDecimal.ZERO);
		}
		if(waybillPickupInfoDto.getOtherFee() == null){
			waybillPickupInfoDto.setValueAddFee(BigDecimal.ZERO);
		}
		if(waybillPickupInfoDto.getPromotionsFee() == null){
			waybillPickupInfoDto.setPromotionsFee(BigDecimal.ZERO);
		}
	}

	/**
	 * 获得代收货款银企帐号信息
	 * 
	 * @author 136334-foss-BaiLei
	 * @date 2014-9-3下午5:22:49
	 */
	private WaybillPickupInfoDto gainWaybillPickupInfo(WaybillDto waybillDto) {
		// 运单基本信息
		WaybillEntity waybill = waybillDto.getWaybillEntity();
		// 开户银行信息
		CusAccountEntity openBank = waybillDto.getOpenBank();
		// 运单冗余信息
		ActualFreightEntity afhe =waybillDto.getActualFreightEntity();
		// 定义封闭对象
		WaybillPickupInfoDto dto = new WaybillPickupInfoDto();
		
		//捕捉对象值拷贝可能出现的异常
		try {
			//将waybill值拷贝到DTO变量中
			PropertyUtils.copyProperties(dto, waybill);
		} catch (Exception e) {
			//添加异常日志
			LOGGER.error("Excepton", e);
			//抛出异常信息
			throw new WaybillSubmitException(WaybillSubmitException.COPYWAYBILLINFO_FAIL,new Object[]{messageBundle.getMessage(e.getMessage())});
		}

		// 出发部门
		SaleDepartmentEntity leaveDept = saleDepartmentService.querySaleDepartmentByCode(waybill.getReceiveOrgCode());
		// 最终配载部门
		SaleDepartmentEntity arriveDept = saleDepartmentService.querySaleDepartmentByCode(waybill.getLastLoadOrgCode());

		if(waybillExpressService.onlineDetermineIsExpressByProductCode(waybill.getProductCode(), waybill.getBillTime())){
			OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.
					queryOrgAdministrativeInfoByCode(waybill.getCustomerPickupOrgCode());
			if(org==null || (org!=null && org.checkExpressSalesDepartment())){
				dto.setIsSelfStation(FossConstants.NO);
			}else{
				dto.setIsSelfStation(FossConstants.YES );
			}
		}
		
		//若出发部门对象不为空则，设置出发部门名称
		if (leaveDept != null) {
			// 出发部门名称
			dto.setReceiveOrgName(leaveDept.getName());
		}

		//若到达部门对象不为空则，设置到达部门名称
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
		//小件新加字段
		if(waybillDto.getWaybillExpressEntity() != null){
			dto.setPosSerialNum(waybillDto.getWaybillExpressEntity().getPdaSerial());
			dto.setBatchNo(waybillDto.getWaybillExpressEntity().getBankTradeSerail());
		}
		dto.setInvoiceMark(afhe.getInvoice());
		return dto;
	}
	
	/**
	 * 生成电子运单运单号(里面已经进行判断了运单是否存在已经被占用的情况)
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-10-3 09:53:31
	 * @return
	 */
	@Override
	public String generateEWaybillNO(){
		String waybillNo = waybillPendingService.getNextEWaybillNo();		
		while(StringUtils.isNotEmpty(waybillNo) && isExsistWaybillNo(waybillNo)){
			waybillNo = waybillPendingService.getNextEWaybillNo();
		}
		return waybillNo;
	}

	/**
	 * 查询运单是否被占用
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-10-3 09:53:57
	 * @param waybillNo
	 * @return
	 */
	private boolean isExsistWaybillNo(String waybillNo) {
		if(waybillManagerService.isExistWaybillTransaction(waybillNo) || actualFreightService.isExistActualFreight(waybillNo)){
			return true;
		}
		return false;
	}
	
	/**
	 * 获得当前用户信息
	 * 
	 * @author 136334-foss-BaiLei
	 * @date 2014-9-3下午5:22:49
	 */
	public CurrentInfo getEWaybilCurrentInfo(WaybillEntity entity) {
		//创建人（快递员）
		UserEntity user = userService.queryUserByEmpCode(entity.getDriverCode());
		if(user == null){
			throw new WaybillValidateException(WaybillValidateException.DRIVER_NOT_HAVE_AUTHORITY_IN_FOSS);
		}
		//必须通过快递员来查其车辆，进而匹配组织部门，不能通过车牌号，因为在激活时，有可能快递员更改，但是车牌号没更改的情况	
		String createOrgCode = pdaScanService.getRadomCreateOrgCodeByDriverCode(entity.getDriverCode());
		if(StringUtils.isEmpty(createOrgCode)){
			throw new WaybillValidateException(WaybillValidateException.EXPRESS_VEHICLE_IS_NOT_BIND_SALESDEPARTMENT);
		}
		//获取组织
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(createOrgCode);
		EmployeeEntity employee = employeeService.queryEmployeeByEmpCodeNoCache(entity.getDriverCode());
		if(employee == null){
			throw new WaybillValidateException(WaybillValidateException.DRIVER_NOT_OWNER_EMPLOYEE);
		}
		user.setEmployee(employee);
		CurrentInfo currentInfo = new CurrentInfo(user, org);
		return currentInfo;
	}


	/**
	 * 将订单转化为运单实体
	 * 
	 * @author 136334-foss-BaiLei
	 * @date 2014-9-13下午5:22:49
	 */
	public WaybillEntity getWaybillEntity(DispatchOrderEntity order , EWaybillOrderEntity eWaybill) {
		//每一项数据要对比waybill表和dispatchOrder表，确保口径和含义一致
		validateOrder(order,eWaybill);
		
		WaybillEntity waybillEntity = new WaybillEntity();
		
		//WaybillEntity基本信息
		setWaybillEntityBasicInfo(waybillEntity,order);
		
		//设置发货人信息
		setDeliveryCustomerInfo(waybillEntity,order,eWaybill);
		
		//设置收货人信息
		setReceiveCustomerInfo(waybillEntity,eWaybill);
		
		//设置收货部门
		setReceiveOrgCode(waybillEntity,order);
		
		//设置运输信息
		setTransportInfo(waybillEntity,order);
		
		//设置货物信息
		setGoodsInfo(waybillEntity,order);
		
		//设置产品类型
		setProductCode(waybillEntity,order);
		
		//简单的设置返单类别
		waybillEntity.setReturnBillType(eWaybill.getReturnBillType());
		
		//设置目的站和走货路径
		setTargetOrgCode(waybillEntity,order);
			
		//设置配载信息
		setLoadInfo(waybillEntity,order);

		//设置增值服务及费用基础信息
		setFeeRelatedInfo(waybillEntity,order,eWaybill);
		
		//是否快递
		if(StringUtils.isNotEmpty(waybillEntity.getProductCode())){
			if(waybillExpressService.onlineDetermineIsExpressByProductCode(waybillEntity.getProductCode(), waybillEntity.getBillTime())){
				waybillEntity.setIsExpress(FossConstants.YES);
			}else{
				waybillEntity.setIsExpress(FossConstants.NO);
			}
		}
		//设置是否经过本部门,该字段为PDA传递过来的数据，默认设置为NO吧
		waybillEntity.setIsPassOwnDepartment(FossConstants.NO);
		return waybillEntity;
	}
	
	/**
	 * 将订单转化为运单实体
	 * 电子运单二期需求，当司机信息更改后，要将运单中信息更改过来
	 * 
	 */

	public WaybillEntity setSecondWaybillEntity(WaybillEntity waybillEntity) {
		 	
		//二期电子运单WaybillEntity基本信息
		setSecondWaybillEntityBasicInfo(waybillEntity);
		
		//设置收货部门
		setSecondReceiveOrgCode(waybillEntity);
		
		//设置运输信息
		setSecondTransportInfo(waybillEntity);	
		
		//设置目的站和走货路径
		setSecondTargetOrgCode(waybillEntity);	
		
		//设置产品类型
		if(!FossConstants.YES.equals(waybillEntity.getIsPicPackage())){
		setSecondProductCode(waybillEntity);
		}
		//设置包装信息(件数发生变化时，包装也要调整)
		setSecondPackage(waybillEntity);
		return waybillEntity;
	}
	
	/**
	 * 二期电子运单订单基本信息设置
	 * 
	 * @author 
	 * @date 
	 */
	private void setSecondWaybillEntityBasicInfo(WaybillEntity waybillEntity){
		LOGGER.info("二期电子运单订单基本信息设置开始.....................运单号为" + waybillEntity.getWaybillNo());
		//获取扫描司机工号
		String driverCode = waybillEntity.getDriverCode();	
		waybillEntity.setCreateUserCode(driverCode);//创建人工号
		
		//必须通过快递员来查其车辆，进而匹配组织部门，不能通过车牌号，因为在激活时，有可能快递员更改，但是车牌号没更改的情况
		ExpressVehiclesEntity expressVehiclesEntityPara = new ExpressVehiclesEntity();
		expressVehiclesEntityPara.setEmpCode(waybillEntity.getDriverCode());
		expressVehiclesEntityPara.setActive(WaybillConstants.YES);
		List<ExpressVehiclesEntity> expressVehiclesEntityList = 
				expressVehiclesService.queryExpressVehiclesByEntity(expressVehiclesEntityPara);
		if(CollectionUtils.isNotEmpty(expressVehiclesEntityList)){
			ExpressVehiclesEntity expressVehiclesEntityResult = expressVehiclesEntityList.get(ZERO);
			waybillEntity.setLicensePlateNum(expressVehiclesEntityResult.getVehicleNo());//设置车牌号
			SaleDepartmentEntity saleDepartmentEntity= saleDepartmentService.
					querySaleDepartmentByCode(expressVehiclesEntityResult.getOrgCode());
		
			if(saleDepartmentEntity == null){
				throw new WaybillValidateException(WaybillValidateException.SALESDEPT_NOT_FOUND);
			}
		
			EmployeeEntity employee = employeeService.queryEmployeeByEmpCode(driverCode);
			if(employee != null){
				waybillEntity.setCreateUserName(employee.getEmpName());//创建人姓名			
				//开单部门所在组织
				waybillEntity.setCreateOrgCode(saleDepartmentEntity.getCode());//创建部门编码
				waybillEntity.setCreateUserCode(employee.getEmpCode());//设置开单人信息
				waybillEntity.setCreateUserDeptName(saleDepartmentEntity.getName());//创建部门名称
				waybillEntity.setModifyUserCode(employee.getEmpCode());//设置修改开单人信息
				waybillEntity.setModifyOrgCode(saleDepartmentEntity.getCode());//创建时，修改部门即为创建部门
			}
		}
		LOGGER.info("二期电子运单订单基本信息设置完毕.....................运单号为" + waybillEntity.getWaybillNo());
	}
	
	/**
	 * 二期电子运单收货部门设置
	 * 
	 * 
	 */
	private void setSecondReceiveOrgCode(WaybillEntity waybillEntity){
		//月结客户如果没有绑定部门，则归属部门为收货部门；
		//月结客户有绑定部门，则根据快递员所在点部匹配点部营业部映射关系，匹配出的营业部和归属部门、绑定部门进行比对，
		//比对成功为收货部门；如果比对出来再有多个，优先选定归属部门，如都为绑定部门，则系统随机选定一个；
		//如果比对不成功，则月结客户归属部门、绑定部门所在城市和快递员所在城市相同的部门为收货部门，
		//如果出现多个优先选定归属部门，如都为绑定部门，则系统随机选定一个；
		LOGGER.info("收货部门设置开始.....................订单号为" + waybillEntity.getOrderNo());
		//获取合同实体
		CusBargainEntity cusBargainEntity = cusBargainService.queryCusBargainByCustCode(waybillEntity.getDeliveryCustomerCode());
		if(null == cusBargainEntity){
			throw new WaybillValidateException(WaybillValidateException.CUSTOMER_BARGAIN_NULL);
		}
		
		//归属部门unifiedCode编码
		String cusBarginSaleDeptUnifiedCode = cusBargainEntity.getUnifiedCode();
		//归属部门编码
		String cusBarginSaleDeptCode = null;
		OrgAdministrativeInfoEntity cusBarginSaleDept = orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCodeClean(cusBarginSaleDeptUnifiedCode);
		if(cusBarginSaleDept == null){
			throw new WaybillValidateException(WaybillValidateException.RECEIVE_SALESDEPARTMENT_UNMATCHED);
		}
		cusBarginSaleDeptCode = cusBarginSaleDept.getCode();
		
		//合同适用部门List
		List<BargainAppOrgEntity> applicateOrgEntityList = bargainAppOrgService.queryAppOrgByBargainCrmId(cusBargainEntity.getCrmId(),null);
		//合同适用部门编码List
		List<String> applicateOrgCodeList = new ArrayList<String>();
		//合同适用部门组织List
		List<OrgAdministrativeInfoEntity> applicateOrgList = null;
		
		OrgAdministrativeInfoEntity applicateOrg = null;
		if(CollectionUtils.isNotEmpty(applicateOrgEntityList)){
			applicateOrgList = new ArrayList<OrgAdministrativeInfoEntity>();
			for (BargainAppOrgEntity bargainAppOrgEntity : applicateOrgEntityList) {
				if ( bargainAppOrgEntity.getUnifiedCode() != null && StringUtils.isNotEmpty(bargainAppOrgEntity.getUnifiedCode())) {
					applicateOrg = orgAdministrativeInfoService
							.queryOrgAdministrativeInfoByUnifiedCodeClean(bargainAppOrgEntity.getUnifiedCode());
					if (applicateOrg != null && StringUtils.isNotEmpty(applicateOrg.getCode())) {
						applicateOrgCodeList.add(applicateOrg.getCode());
						applicateOrgList.add(applicateOrg);
					}
				}
			}
		}
		
		//快递员所在组织(点部)
		OrgAdministrativeInfoEntity org = null;
		String driverCode = waybillEntity.getDriverCode();
		EmployeeEntity expEmp = employeeService.queryEmployeeByEmpCode(driverCode);
		if(expEmp != null && expEmp.getDepartment() != null){
			org = expEmp.getDepartment();
		}
		ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto = new ExpressPartSalesDeptQueryDto();
		if(org != null && StringUtils.isNotEmpty(org.getCode())){
			expressPartSalesDeptQueryDto.setExpressPartCode(org.getCode());				
		}
		List<ExpressPartSalesDeptResultDto> expressPartSalesDeptResultDto = 
				expressPartSalesDeptService.queryExpressPartSalesDeptByExpressPartCode(expressPartSalesDeptQueryDto);
		//获得快递员点部匹配的营业部编码list
		List<String> expressSalesPartCodeList = null;
		if(CollectionUtils.isNotEmpty(expressPartSalesDeptResultDto)){
			expressSalesPartCodeList = new ArrayList<String>();
			for(ExpressPartSalesDeptResultDto temp : expressPartSalesDeptResultDto){
				if(null != temp && StringUtils.isNotBlank(temp.getSalesDeptCode())){
					expressSalesPartCodeList.add(temp.getSalesDeptCode());
				}
			}
		}			
		
		//归属部门所在城市编码
		String  cusBarginSaleDeptCityCode= queryRegionsBySaleDeptCode(cusBarginSaleDeptCode);
		//绑定部门(合同适用部门)所在城市编码list
		List<String> applicateOrgCityCodeList = null;
		//OrgAdministrativeInfoEntity applicateOrg = null;
		if(CollectionUtils.isNotEmpty(applicateOrgEntityList)){
			applicateOrgCityCodeList = new ArrayList<String>();
			for(BargainAppOrgEntity bargainAppOrgEntity : applicateOrgEntityList){
				if(null != bargainAppOrgEntity && StringUtils.isNotBlank(bargainAppOrgEntity.getUnifiedCode())){
					applicateOrg = orgAdministrativeInfoService.
							queryOrgAdministrativeInfoByUnifiedCodeClean(bargainAppOrgEntity.getUnifiedCode());
					if(applicateOrg != null && StringUtils.isNotEmpty(applicateOrg.getCode())){
						applicateOrgCityCodeList.add(queryRegionsBySaleDeptCode(applicateOrg.getCode()));
					}
				}
			}
		}
		//快递员所在城市（所在点部的城市）编码
		//String  driverDeptCityCode= org.getCityCode();
				
		//营业部code列表：expressSalesPartCodeList，归属部门Code：cusBarginSaleDeptCode，绑定部门Code列表：applicateOrgCodeList；
		//快递员所在城市（点部城市）：driverDeptCityCode(org.getCityCode())，归属部门城市：cusBarginSaleDeptCityCode，
		//绑定部门城市Code列表：applicateOrgCityCodeList
		
		//获得绑定部门
		if(StringUtils.isBlank(cusBargainEntity.getApplicateOrgId())){
			//没有绑定部门，则归属部门为收货部门
			waybillEntity.setReceiveOrgCode(cusBarginSaleDeptCode);
		}else{
			//营业部Code列表与绑定部门Code列表对比，有相等则为收货部门
			if(CollectionUtils.isNotEmpty(expressSalesPartCodeList)){
				for(String tempSalesPartCode : expressSalesPartCodeList){
					for(String tempApplicateOrgCode : applicateOrgCodeList){
						if(StringUtils.isNotEmpty(tempSalesPartCode) && tempSalesPartCode.equals(tempApplicateOrgCode)){
							waybillEntity.setReceiveOrgCode(tempApplicateOrgCode);
							break;
						}
					}
				}
			}
		}
		
		//如果部门对比不成功
		if(StringUtils.isBlank(waybillEntity.getReceiveOrgCode()) && org != null){
			//月结客户归属部门、绑定部门所在城市和快递员所在城市相同的部门为收货部门
			if(cusBarginSaleDeptCityCode!=null &&  cusBarginSaleDeptCityCode.equals(org.getCityCode()) ){		
				waybillEntity.setReceiveOrgCode(cusBarginSaleDeptCode);				
			}else{ 
				if(applicateOrgList!=null && applicateOrgList.size()>0){
					//如果出现多个优先选定归属部门，如都为绑定部门，则系统随机选定一个					
					for(OrgAdministrativeInfoEntity tempOrg:applicateOrgList){	
						//适用部门城市对比快递员所在城市
						if(StringUtils.isNotEmpty(org.getCityCode()) && org.getCityCode().equals(tempOrg.getCityCode())){
							waybillEntity.setReceiveOrgCode(tempOrg.getCode());
							break;
						}
					}
				}
			}
		}
		
		OrgAdministrativeInfoEntity receiveOrg = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillEntity.getReceiveOrgCode());
		if(receiveOrg != null){
			waybillEntity.setReceiveOrgName(receiveOrg.getName());
		}
		
		if(StringUtils.isBlank(waybillEntity.getReceiveOrgCode())){
			throw new WaybillValidateException(WaybillValidateException.RECEIVE_ORG_NULL);
		}
		//设置开单部门与收货部门不一致的情况
		if(StringUtils.isNotEmpty(waybillEntity.getReceiveOrgCode())){
			waybillEntity.setCreateOrgCode(waybillEntity.getReceiveOrgCode());
		}
		LOGGER.info("收货部门设置完毕.....................收货部门为" + waybillEntity.getReceiveOrgCode());
	}
	
	
	/**
	 * 设置二期电子运单运输信息
	 * 
	 */	
	private void  setSecondTransportInfo(WaybillEntity waybillEntity){
		LOGGER.info("运输信息设置开始.....................订单号为" + waybillEntity.getOrderNo());
		String driverCode = waybillEntity.getDriverCode();
		String driverVeNo = waybillEntity.getOrderVehicleNum();
		waybillEntity.setDriverCode(driverCode);//司机工号
		waybillEntity.setLicensePlateNum(driverVeNo);//车牌号
		waybillEntity.setPickupCentralized(WaybillConstants.NO);//是否集中接货，全部置N
		waybillEntity.setIsWholeVehicle(WaybillConstants.NO);
		
		if(StringUtils.isNotEmpty(waybillEntity.getReceiveMethod())){
			if(waybillEntity.getReceiveMethod().indexOf("PICKUP") >= 0){
				waybillEntity.setReceiveMethod(WaybillConstants.SELF_PICKUP);//汽运自提
				waybillEntity.setPickupToDoor(WaybillConstants.NO);
			}else {
				waybillEntity.setReceiveMethod(WaybillConstants.DELIVER_UP);//快递默认汽运送货（上楼）
				waybillEntity.setPickupToDoor(WaybillConstants.YES);
			}
		}
		LOGGER.info("运输信息设置完毕.....................快递员工号为" + waybillEntity.getDriverCode());
	}
	
	
	/**
	 * 设置二期电子运单目的站
	 * 
	 */	
	private	void setSecondTargetOrgCode(WaybillEntity waybillEntity){
		//根据收货省市区查询快递营业网点，优先取快递营业网点
		//目的站,订单有则取订单,没有则取快递营业点部
		SaleDepartmentEntity salesDept = null;
		if(StringUtils.isNotEmpty(waybillEntity.getCustomerPickupOrgCode())){
			salesDept = saleDepartmentService.querySimpleSaleDepartmentByCode(waybillEntity.getCustomerPickupOrgCode());
		}else{
			//进行统一化处理
			salesDept =waybillExpressService.getTargetOrgCode(waybillEntity);
		}
		if(salesDept == null){
			throw new WaybillValidateException(WaybillValidateException.DESTINATION_NULL);
		}
		//如果该提货网点不能开代收货款，则报错不能开提货网点单
		if(waybillEntity.getCodAmount() !=null && waybillEntity.getCodAmount().compareTo(BigDecimal.ZERO)>0){
			if(!FossConstants.YES.equals(salesDept.getCanAgentCollected())){
				throw new WaybillValidateException(WaybillValidateException.PICKUP_STATSION_CANNOT_COD);
			}
		}
		
		//是否支持开多件--废除到达部门一票多件的校验：与罗益文确认
		/*if(waybillEntity.getGoodsQtyTotal() != null && waybillEntity.getGoodsQtyTotal() > 1){
			//如果不支持开多件，则抛出异常
			if(!FossConstants.YES.equals(salesDept.getCanExpressOneMany())){
				throw new WaybillValidateException(WaybillValidateException.CANNOT_CREATE_ONETOMANY_EXP);
			}
		}*/
		
		//营业部是否支持签收单返单
		if(!WaybillConstants.NOT_RETURN_BILL.equals(waybillEntity.getReturnBillType())){
			if(!FossConstants.YES.equals(salesDept.getCanExpressReturnSignBill())){
				throw new WaybillValidateException(WaybillValidateException.SALESDEPT_CAN_NOT_RETURN_SIGNBILL);
			}
		}
		
		//否则进行赋值
		waybillEntity.setCustomerPickupOrgCode(waybillEntity.getCustomerPickupOrgCode());//提货网点CODE
		waybillEntity.setCustomerPickupOrgName(salesDept.getName());//提货网点名称
		//提货网点部门简称
		OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillEntity.getCustomerPickupOrgCode());
		if(orgInfo != null){
			waybillEntity.setTargetOrgCode(orgInfo.getOrgSimpleName());
		}
		LOGGER.info("目的站设置完毕.....................目的站编码为" + waybillEntity.getTargetOrgCode()) ;
	}

	//设置包装信息(件数发生变化时，包装也要调整)
	private void setSecondPackage(WaybillEntity waybillEntity){
		
		String orderNo = waybillEntity.getOrderNo();
		//查询订单数据
		DispatchOrderEntity dispatchOrderEntity = dispatchOrderEntityDao.queryOrderByOrderNo(orderNo);
		//如果订单的件数和扫描的件数不一致，则直接将包装件数更新为扫描件数，包装更新为纸
		if(dispatchOrderEntity!=null && 
				waybillEntity.getGoodsQtyTotal() != dispatchOrderEntity.getGoodsQty()){
			waybillEntity.setPaperNum(waybillEntity.getGoodsQtyTotal());//纸
			waybillEntity.setGoodsPackage(waybillEntity.getGoodsQtyTotal()+ExpWaybillConstants.GOOD_PACKAGE_DEFAULT);
			waybillEntity.setWoodNum(BigDecimal.ZERO.intValue());//木
			waybillEntity.setFibreNum(BigDecimal.ZERO.intValue());// 纤
			waybillEntity.setSalverNum(BigDecimal.ZERO.intValue());// 托
			waybillEntity.setMembraneNum(BigDecimal.ZERO.intValue());// 膜
		}
	}
	
	
	/**
	 * 转化运单前校验订单
	 * 
	 * @author 136334-foss-BaiLei
	 * @date 2014-9-13下午5:22:49
	 */
	private void validateOrder(DispatchOrderEntity order , EWaybillOrderEntity eWaybill){
		LOGGER.info("订单基本信息验证开始....................." );
		//订单号是否为空
		if(StringUtils.isBlank(order.getOrderNo()) || StringUtils.isBlank(eWaybill.getOrderNO())){
			throw new WaybillValidateException(WaybillValidateException.DISPATCH_ORDERNO_NULL);
		}
		
		//是否电子运单
		if(!WaybillConstants.EWAYBILL.equals(order.getWaybillType())){
			throw new WaybillValidateException(WaybillValidateException.NOT_EWAYBILL_ORDER);
		}
		//目前只针对零担快递
		if(!waybillExpressService.onlineDetermineIsExpressByProductCode(order.getProductCode(), order.getOrderTime())){
			throw new WaybillValidateException(WaybillValidateException.NOT_EXPRESS_EWAYBILL_ORDER);
		}
		
		if(DispatchOrderStatusConstants.STATUS_DISPATCH_VEHICLE.equals(order.getOrderStatus())){
			//throw new WaybillValidateException(WaybillValidateException.NOT_EWAYBILL_ORDER);
		}
		//总件数不能为0
		if (order.getGoodsQty() == null || order.getGoodsQty().intValue() == 0) {
			LOGGER.error(WaybillValidateException.NULL_GOODSQTYTOTAL);
			throw new WaybillValidateException(WaybillValidateException.NULL_GOODSQTYTOTAL);
		}
		//重量不能为0
		if (order.getWeight() == null || order.getWeight().compareTo(BigDecimal.ZERO) == 0) {
			LOGGER.error(WaybillValidateException.NULL_GOODSWEIGHTTOTAL);
			throw new WaybillValidateException(WaybillValidateException.NULL_GOODSWEIGHTTOTAL);
		}
		//如果总件数不能超过1W
		if(order.getGoodsQty() > NUMBER_10000){
			LOGGER.error(WaybillValidateException.GOODS_QTY_TOTAL_NO_MORE_THAN_TEN_THOUSAND);
			throw new WaybillValidateException(WaybillValidateException.GOODS_QTY_TOTAL_NO_MORE_THAN_TEN_THOUSAND);
		}
		//快递员必须存在
		if(StringUtils.isBlank(order.getDriverCode())){
			throw new WaybillValidateException(WaybillValidateException.DRIVER_CODE_NULL);
		}
		//快递员必须有对应的用户信息
		UserEntity user = userService.queryUserByEmpCode(order.getDriverCode());
		if(user == null){
			throw new WaybillValidateException(WaybillValidateException.DRIVER_NOT_HAVE_AUTHORITY_IN_FOSS);
		}
		//快递员必须是公司现有雇员
		EmployeeEntity employee = employeeService.queryEmployeeByEmpCode(order.getDriverCode());
		if(employee == null){
			throw new WaybillValidateException(WaybillValidateException.DRIVER_NOT_OWNER_EMPLOYEE);
		}
		/**
		 * 约车信息从FOSS迁移至OMS，注释快递员与约车信息校验 297064
		 */
		/*
		//判定快递员与车辆绑定关系
		ExpressVehiclesEntity expressVehiclesEntity = new ExpressVehiclesEntity();
		expressVehiclesEntity.setActive(FossConstants.YES);
		expressVehiclesEntity.setEmpCode(order.getDriverCode());
		List<ExpressVehiclesEntity> expressVehiclesList = expressVehiclesService.queryExpressVehiclesByEntity(expressVehiclesEntity);
		if(CollectionUtils.isEmpty(expressVehiclesList) || StringUtils.isEmpty(expressVehiclesList.get(0).getOrgCode())){
			throw new WaybillValidateException(WaybillValidateException.EXPRESS_VEHICLE_IS_NOT_BIND_SALESDEPARTMENT);
		}
		*/
		//判定产品类型是否为空
		if(StringUtils.isEmpty(order.getProductCode())){
			throw new WaybillValidateException(WaybillValidateException.PRODUCT_CODE_NULL);
		}
		validateSetinnerPickupFeeBurdenDept(order, null);
		//判定是否可以享受电商尊享件
		if(WaybillConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE.equals(order.getProductCode())){
			//付款方式是否为月结
			boolean isMonthEnd = false;
			if(CrmPaymentTypeEnum.MONTHPAY.getCrmCode().equals(order.getPaidMethod())
					||CrmPaymentTypeEnum.PAYARIIVE.getCrmCode().equals(order.getPaidMethod())
					){
				isMonthEnd = true;
			}
			//如果不是月结或到付，则抛出异常
			if(!isMonthEnd){
				LOGGER.info("运输性质为电商尊享件,但开单方式不为月结或到付，该订单号为:" + order.getOrderNo());
				throw new WaybillValidateException(WaybillValidateException.IS_ECOMMERCE_PROMOTIONAL_ONLY_MONTH_END);
			}
			//判定客户是否电商尊享客户
			boolean isNotSpecialCustomerEnjoy = false;
			if(StringUtils.isEmpty(order.getDeliveryCustomerCode())){
				isNotSpecialCustomerEnjoy = true;
			}else{
				//默认是没有开的权利的
				isNotSpecialCustomerEnjoy = true;
				//获取合同实体
				CusBargainEntity cusBargainEntity = cusBargainService.queryCusBargainByCustCode(order.getDeliveryCustomerCode());
				//合同为空,自然不是月结客户
				if(cusBargainEntity != null && DictionaryValueConstants.CLEARING_TYPE_MONTH.equals(cusBargainEntity.getExPayWay())){
					isNotSpecialCustomerEnjoy = false;
				}else{
					//非月结客户不能开月结
					throw new WaybillValidateException(WaybillValidateException.PAID_METHOD_CT_ILLEGAL);
				}
				//根据客户编码去查询是否有开电商尊享的权限
				if(cusBargainEntity != null && FossConstants.YES.equals(cusBargainEntity.getIfElecEnjoy())){
					isNotSpecialCustomerEnjoy = false;
				}else{
					LOGGER.info("运输性质为电商尊享件,开单方式为月结或到付，但客户不为电商尊享客户，该订单号为:" + order.getOrderNo());
					throw new WaybillValidateException(WaybillValidateException.IS_NOT_ECOMMERCE_CUSTOMER);
				}
			}
			//校验是否有开电商尊享的权利
			if(isNotSpecialCustomerEnjoy){
				LOGGER.info("运输性质为电商尊享件,但客户没有电商尊享的权利，该订单号为:" + order.getOrderNo());
				throw new WaybillValidateException(WaybillValidateException.IS_NOT_ECOMMERCE_PROMOTIONAL_CUSTOMER);
			}
		}
		LOGGER.info("订单基本信息验证完毕....................." );
	}
	
	/**
	 * 校验内部带货员工与内部带货所在部门信息是否符合FOSS规范
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-17 21:54:26
	 * @param order
	 * @param waybillExpressEntity
	 */
	private void validateSetinnerPickupFeeBurdenDept(DispatchOrderEntity order, WaybillExpressEntity waybillExpressEntity){
		String senderCode = null;
		String paymentOrgCode = null;
		//设置 内部带货  员工工号和 费用承担部门 从dispatchOrderEntity获取
		if(WaybillConstants.DEPPON_CUSTOMER.equals(order.getDeliveryCustomerCode())){
			//内部带货发货人工号为空
			if(StringUtils.isEmpty(order.getSenderCode())){
				throw new WaybillValidateException(WaybillValidateException.INNER_PICKUP_DELIVERY_EMPLOYEE_CODE_NULL);
			}
			//内部带货费用承担部门不能为空
			if(StringUtils.isEmpty(order.getPaymentOrgCode())){
				throw new WaybillValidateException(WaybillValidateException.INNER_PICKUP_DEPT_NAME_IS_NULL);
			}
			EmployeeEntity employee = employeeService.queryEmployeeByEmpCode(order.getSenderCode());
			if(employee == null){
				employee = new EmployeeEntity();
				employee.setEmpName(order.getSenderCode());
				employee.setActive(FossConstants.YES);
				List<EmployeeEntity> employeeSenderList = employeeService.queryEmployeeAndUserByEntity(employee, 1, NumberConstants.NUMBER_10);
				if(CollectionUtils.isEmpty(employeeSenderList)){
					throw new WaybillValidateException(WaybillValidateException.INNER_PICKUP_DELIVERY_EMPLOYEE_CODE_IS_INVALID);
				}else{
					employee = employeeSenderList.get(0);
					senderCode = employee.getEmpCode();
				}
			}
			//因为官网同步过来的是部门名称需要转换成编码
			OrgAdministrativeInfoEntity entity = new OrgAdministrativeInfoEntity();
			entity.setName(order.getPaymentOrgCode());
			List<OrgAdministrativeInfoEntity>  orgList = orgAdministrativeInfoService.querySimpleOrgAdministrativeInfoByEntity(entity, 0, 1);
			//内部带货费用承担部门不能为空
			if(CollectionUtils.isEmpty(orgList)){
				throw new WaybillValidateException(WaybillValidateException.INNER_PICKUP_DEPT_INFO_IS_VALID);
			}else{
				entity = orgList.get(0);
				paymentOrgCode = entity.getName();
			}
		}
		if(waybillExpressEntity != null){
			waybillExpressEntity.setInnerPickupFeeBurdenDept(paymentOrgCode);
			waybillExpressEntity.setDeliveryEmployeeCode(senderCode);
		}
	}
	
	/**
	 * 订单基本信息设置
	 * 
	 * @author 136334-foss-BaiLei
	 * @date 2014-9-13下午5:22:49
	 */
	private void setWaybillEntityBasicInfo(WaybillEntity waybillEntity,DispatchOrderEntity order){
		LOGGER.info("订单基本信息设置开始.....................订单号为" + waybillEntity.getOrderNo());
		//运单号
		if(StringUtils.isNotBlank(order.getWaybillNo())){
			waybillEntity.setWaybillNo(order.getWaybillNo());
		}
		//waybillEntity.setActive();//运单状态
		waybillEntity.setOrderNo(order.getOrderNo());//订单号   
		//是否子母件
		waybillEntity.setIsPicPackage(order.getIsPicPackage());
		//订单来源
		if(StringUtils.isNotBlank(order.getOrderSource())){
			waybillEntity.setOrderChannel(order.getOrderSource());
		}else{
			waybillEntity.setOrderChannel(WaybillConstants.CRM_ORDER_CHANNEL_ONLINE);
		}
			
		waybillEntity.setCreateUserCode(order.getDriverCode());//创建人工号
		EmployeeEntity employee=employeeService.queryEmployeeByEmpCode(order.getDriverCode());
		if(employee != null && StringUtils.isNotEmpty(employee.getEmpName())){			
			waybillEntity.setCreateUserName(employee.getEmpName());//创建人姓名
		}
		//将约车成功生成的收货人乡镇code 设置到运单实体中
		waybillEntity.setReceiveCustomerVillageCode(order.getConsigneeVillageCode());
		waybillEntity.setCreateTime(new Date());
		waybillEntity.setBillTime(new Date());
		waybillEntity.setAddTime(new Date());
		
		//根据快递员车牌号所在的营业部设置创建营业部
		SaleDepartmentEntity sales=querySaleDepartmentByVehicleNo(order.getVehicleNo());
		if(sales != null){
			//开单部门所在组织
			waybillEntity.setCreateOrgCode(sales.getCode());//创建部门编码
			waybillEntity.setCreateUserDeptName(sales.getName());//创建部门名称
			waybillEntity.setModifyOrgCode(sales.getCode());//创建时，修改部门即为创建部门
		}
		LOGGER.info("订单基本信息设置完毕.....................订单号为" + waybillEntity.getOrderNo());
	}
	

	/**
	 * 收货部门设置
	 * 
	 * @author 136334-foss-BaiLei
	 * @date 2014-9-13下午5:22:49
	 */
	private void setReceiveOrgCode(WaybillEntity waybillEntity,DispatchOrderEntity order){
		//月结客户如果没有绑定部门，则归属部门为收货部门；
		//月结客户有绑定部门，则根据快递员所在点部匹配点部营业部映射关系，匹配出的营业部和归属部门、绑定部门进行比对，
		//比对成功为收货部门；如果比对出来再有多个，优先选定归属部门，如都为绑定部门，则系统随机选定一个；
		//如果比对不成功，则月结客户归属部门、绑定部门所在城市和快递员所在城市相同的部门为收货部门，
		//如果出现多个优先选定归属部门，如都为绑定部门，则系统随机选定一个；
		LOGGER.info("收货部门设置开始.....................订单号为" + waybillEntity.getOrderNo());
		 //大客户才有收货部门设置，散客木有
		if(WaybillConstants.YES.equals(order.getIsEWaybillBigCustomer()) && StringUtils.isNotEmpty(waybillEntity.getDeliveryCustomerCode())){
			//获取合同实体
			CusBargainEntity cusBargainEntity = cusBargainService.queryCusBargainByCustCode(waybillEntity.getDeliveryCustomerCode());
			if(null == cusBargainEntity){
				throw new WaybillValidateException(WaybillValidateException.CUSTOMER_BARGAIN_NULL);
			}
			
			//归属部门unifiedCode编码
			String cusBarginSaleDeptUnifiedCode = cusBargainEntity.getUnifiedCode();
			//归属部门编码
			String cusBarginSaleDeptCode = null;
			OrgAdministrativeInfoEntity cusBarginSaleDept = orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCodeClean(cusBarginSaleDeptUnifiedCode);
			if(cusBarginSaleDept == null){
				throw new WaybillValidateException(WaybillValidateException.RECEIVE_SALESDEPARTMENT_UNMATCHED);
			}
			cusBarginSaleDeptCode = cusBarginSaleDept.getCode();
			
			//合同适用部门List
			List<BargainAppOrgEntity> applicateOrgEntityList = bargainAppOrgService.queryAppOrgByBargainCrmId(cusBargainEntity.getCrmId(),null);
			//合同适用部门编码List
			List<String> applicateOrgCodeList = new ArrayList<String>();
			//合同适用部门组织List
			List<OrgAdministrativeInfoEntity> applicateOrgList = null;
			
			OrgAdministrativeInfoEntity applicateOrg = null;
			if(CollectionUtils.isNotEmpty(applicateOrgEntityList)){
				applicateOrgList = new ArrayList<OrgAdministrativeInfoEntity>();
				for (BargainAppOrgEntity bargainAppOrgEntity : applicateOrgEntityList) {
					if (bargainAppOrgEntity.getUnifiedCode() != null && StringUtils.isNotEmpty(bargainAppOrgEntity.getUnifiedCode())) {
						applicateOrg = orgAdministrativeInfoService
								.queryOrgAdministrativeInfoByUnifiedCodeClean(bargainAppOrgEntity
										.getUnifiedCode());
						if (applicateOrg != null && StringUtils.isNotEmpty(applicateOrg.getCode())) {
							applicateOrgCodeList.add(applicateOrg.getCode());
							applicateOrgList.add(applicateOrg);
						}
					}
				}
			}
			
			//快递员所在组织(点部)
			OrgAdministrativeInfoEntity org = null;
			EmployeeEntity expEmp = employeeService.queryEmployeeByEmpCode(order.getDriverCode());
			if(expEmp != null && expEmp.getDepartment() != null){
				org = expEmp.getDepartment();
			}
			ExpressPartSalesDeptQueryDto expressPartSalesDeptQueryDto = new ExpressPartSalesDeptQueryDto();
			if(org != null && StringUtils.isNotEmpty(org.getCode())){
				expressPartSalesDeptQueryDto.setExpressPartCode(org.getCode());				
			}
			List<ExpressPartSalesDeptResultDto> expressPartSalesDeptResultDto = 
					expressPartSalesDeptService.queryExpressPartSalesDeptByExpressPartCode(expressPartSalesDeptQueryDto);
			//获得快递员点部匹配的营业部编码list
			List<String> expressSalesPartCodeList = null; 
			if(CollectionUtils.isNotEmpty(expressPartSalesDeptResultDto)){
				expressSalesPartCodeList = new ArrayList<String>();
				for(ExpressPartSalesDeptResultDto temp : expressPartSalesDeptResultDto){
					if(null != temp && StringUtils.isNotBlank(temp.getSalesDeptCode())){
						expressSalesPartCodeList.add(temp.getSalesDeptCode());
					}
				}
			}			
			
			//归属部门所在城市编码
			String  cusBarginSaleDeptCityCode= queryRegionsBySaleDeptCode(cusBarginSaleDeptCode);
			//绑定部门(合同适用部门)所在城市编码list
			List<String>  applicateOrgCityCodeList = null; 
			//OrgAdministrativeInfoEntity applicateOrg = null;
			if(CollectionUtils.isNotEmpty(applicateOrgEntityList)){
				applicateOrgCityCodeList = new ArrayList<String>();
				for(BargainAppOrgEntity bargainAppOrgEntity : applicateOrgEntityList){
					if(null != bargainAppOrgEntity && StringUtils.isNotBlank(bargainAppOrgEntity.getUnifiedCode())){
						applicateOrg = orgAdministrativeInfoService.
								queryOrgAdministrativeInfoByUnifiedCodeClean(bargainAppOrgEntity.getUnifiedCode());
						if(applicateOrg != null && StringUtils.isNotEmpty(applicateOrg.getCode())){
							applicateOrgCityCodeList.add(queryRegionsBySaleDeptCode(applicateOrg.getCode()));
						}
					}
				}
			}
			//快递员所在城市（所在点部的城市）编码
			//String  driverDeptCityCode= org.getCityCode();
					
			//营业部code列表：expressSalesPartCodeList，归属部门Code：cusBarginSaleDeptCode，绑定部门Code列表：applicateOrgCodeList；
			//快递员所在城市（点部城市）：driverDeptCityCode(org.getCityCode())，归属部门城市：cusBarginSaleDeptCityCode，
			//绑定部门城市Code列表：applicateOrgCityCodeList
			
			//点部匹配的营业部编码list 与 合同适用部门编码List 交集数据
			List<String> tempSalesPartAndtempApplicateOrgCodeList=new ArrayList<String>();
			
			//获得绑定部门
			if(StringUtils.isBlank(cusBargainEntity.getApplicateOrgId())){
				//没有绑定部门，则归属部门为收货部门
				waybillEntity.setReceiveOrgCode(cusBarginSaleDeptCode);
			}else{
				//营业部Code列表与绑定部门Code列表对比，有相等则为收货部门
				if(CollectionUtils.isNotEmpty(expressSalesPartCodeList)){
					for(String tempSalesPartCode : expressSalesPartCodeList){
						for(String tempApplicateOrgCode : applicateOrgCodeList){
							if(StringUtils.isNotEmpty(tempSalesPartCode) && tempSalesPartCode.equals(tempApplicateOrgCode)){
								tempSalesPartAndtempApplicateOrgCodeList.add(tempSalesPartCode);
								//waybillEntity.setReceiveOrgCode(tempApplicateOrgCode);
								//break;
							}
						}
					}
				}
				if(tempSalesPartAndtempApplicateOrgCodeList.size()==1){
					waybillEntity.setReceiveOrgCode(tempSalesPartAndtempApplicateOrgCodeList.get(0));
				}else if(tempSalesPartAndtempApplicateOrgCodeList.size()>1){
					for(String tempSalesPartAndtempApplicateOrgCode : tempSalesPartAndtempApplicateOrgCodeList){
						waybillEntity.setReceiveOrgCode(tempSalesPartAndtempApplicateOrgCode);
						if(StringUtils.isNotEmpty(cusBarginSaleDeptCode) && cusBarginSaleDeptCode.equals(tempSalesPartAndtempApplicateOrgCode)){
							waybillEntity.setReceiveOrgCode(tempSalesPartAndtempApplicateOrgCode);
								break;
							}
						}
					}
				}
			
			//如果部门对比不成功
			if(StringUtils.isBlank(waybillEntity.getReceiveOrgCode()) && org != null){
				//月结客户归属部门、绑定部门所在城市和快递员所在城市相同的部门为收货部门
				if(cusBarginSaleDeptCityCode!=null &&  cusBarginSaleDeptCityCode.equals(org.getCityCode()) ){		
					waybillEntity.setReceiveOrgCode(cusBarginSaleDeptCode);				
				}else{ 
					if(CollectionUtils.isNotEmpty(applicateOrgList)){
						//如果出现多个优先选定归属部门，如都为绑定部门，则系统随机选定一个					
						for(OrgAdministrativeInfoEntity tempOrg:applicateOrgList){	
							//适用部门城市对比快递员所在城市
							if(StringUtils.isNotEmpty(org.getCityCode()) && org.getCityCode().equals(tempOrg.getCityCode())){
								waybillEntity.setReceiveOrgCode(tempOrg.getCode());
								break;
							}
						}
					}
				}
			}
			
			OrgAdministrativeInfoEntity receiveOrg = orgAdministrativeInfoService.
					queryOrgAdministrativeInfoByCode(waybillEntity.getReceiveOrgCode());
			if(receiveOrg !=null){
				waybillEntity.setReceiveOrgName(receiveOrg.getName());
			}
			if(StringUtils.isBlank(waybillEntity.getReceiveOrgCode())){
				throw new WaybillValidateException(WaybillValidateException.RECEIVE_ORG_NULL);
			}
			LOGGER.info("收货部门设置完毕.....................收货部门为" + waybillEntity.getReceiveOrgCode());
		}
	}
	
	/**
	 * 查询快递员车辆所在营业部
	 * 
	 * @author 136334-foss-BaiLei
	 * @date 2014-9-13下午5:22:49
	 */
	public SaleDepartmentEntity querySaleDepartmentByVehicleNo(String vehicleNo){
		//IExpressVehiclesService
		String saleDeptCode = expressVehiclesService.queryOrgCodeByVehicleNo(vehicleNo);
		SaleDepartmentEntity saleDepartmentEntityquery = saleDepartmentService.querySaleDepartmentByCode(saleDeptCode);
		return saleDepartmentEntityquery;
	}
	
	
	/**
	 * 查询营业部所在城市
	 * @author 136334-foss-BaiLei
	 * @date 2014-9-13下午5:22:49
	 */
	public String queryRegionsBySaleDeptCode(String saleDeptCode){
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(saleDeptCode);
		if(null != org && StringUtils.isNotEmpty(org.getCityCode())){
			//AdministrativeRegionsEntity entity = administrativeRegionsService.queryAdministrativeRegionsByCode(org.getCityCode());
			return org.getCityCode();
		}
		return null;
	}
	
	
	/**
	 * 设置发货客户信息
	 *  
	 * @author 136334-foss-BaiLei
	 * @date 2014-9-13下午5:22:49
	 */	
	private void setDeliveryCustomerInfo(WaybillEntity waybillEntity,DispatchOrderEntity order,EWaybillOrderEntity ewaybillOrder){
		/*
		 * 发货客户信息
		 */
		CustomerQueryConditionDto condition= new CustomerQueryConditionDto();
		if(StringUtils.isNotEmpty(order.getDeliveryCustomerCode())){
			waybillEntity.setDeliveryCustomerCode(order.getDeliveryCustomerCode());//发货客户编码
			condition.setCustCode(order.getDeliveryCustomerCode());
			//liyongfei 解决LIHUAHUA 账号可以查询到wlfnihao4444 开的单子问题
//			List<CustomerContactDto> list = customerDao.queryCustomerInfo(condition);
//			if(list != null && list.size() > 0){
//				CustomerContactDto customerContactDto = list.get(ZERO);
//				waybillEntity.setDeliverCustContactId(customerContactDto.getContactId());//发货客户ID
//			}
		}
		//判定客户编码是否为空
		if(StringUtils.isNotEmpty(order.getDeliveryCustomerCode())){
			CustomerDto customerDto = customerDao.queryCustInfoByCode(order.getDeliveryCustomerCode());
			if(customerDto!=null){
				//是否是大客户
				waybillEntity.setDeliveryBigCustomer(customerDto.getIsLargeCustomers());
			}
		}
		waybillEntity.setDeliverCustContactId(ewaybillOrder.getDeliveryCustomerContactId());
		waybillEntity.setDeliveryCustomerName(order.getCustomerName());//发货客户名称
		waybillEntity.setDeliveryCustomerMobilephone(order.getMobile());//发货客户手机
		waybillEntity.setDeliveryCustomerPhone(order.getTel());//发货客户电话
		//发货联系人取ewaybillOrder的数据
		waybillEntity.setDeliveryCustomerContact(ewaybillOrder.getDeliveryCustomerContact());//发货客户联系人	发货联系人为空，抛出异常
		//waybillEntity.setDeliveryCustomerNationCode();//发货国家
		waybillEntity.setDeliveryCustomerProvCode(order.getPickupProvinceCode()) ;//发货省份
		waybillEntity.setDeliveryCustomerCityCode(order.getPickupCityCode());//发货市
		waybillEntity.setDeliveryCustomerDistCode(order.getPickupCountyCode());//发货区
		waybillEntity.setDeliveryCustomerAddress(order.getPickupElseAddress());//发货具体地址
		
		LOGGER.info("发货客户基本信息设置完毕.....................发货人客户编码为" + waybillEntity.getDeliveryCustomerCode()) ;
	}
	
	/**
	 * 设置收货客户信息
	 * 
	 * @author 136334-foss-BaiLei
	 * @date 2014-9-13下午5:22:49
	 */	
	private void setReceiveCustomerInfo(WaybillEntity waybillEntity,EWaybillOrderEntity eWaybill){
		/*
		 * 收货客户信息
		 */
		//private String receiveCustomerId;//收货客户ID
		//private String receiveCustomerCode;//收货客户编码
		if(StringUtils.isNotEmpty(eWaybill.getReceiveCustomerCode())){
			CustomerDto customerDto = customerDao.queryCustInfoByCode(eWaybill.getReceiveCustomerCode());
			if(customerDto!=null){
				//判断收货客户是否是大客户
				waybillEntity.setReceiveBigCustomer(customerDto.getIsLargeCustomers());
			}
		}
		
		waybillEntity.setReceiveCustomerName(eWaybill.getReceiveCustomerName());//发货客户名称
		waybillEntity.setReceiveCustomerMobilephone(eWaybill.getReceiveCustomerMobilephone());//发货客户手机
		waybillEntity.setReceiveCustomerPhone(eWaybill.getReceiveCustomerPhone());//发货客户电话
		waybillEntity.setReceiveCustomerContact(eWaybill.getReceiveCustomerContact());//发货客户联系人	收货联系人为空，抛出异常
		//dto.setReceiveCustomerNationCode();//发货国家
		waybillEntity.setReceiveCustomerProvCode(eWaybill.getReceiveCustomerProvCode()) ;//发货省份
		waybillEntity.setReceiveCustomerCityCode(eWaybill.getReceiveCustomerCityCode());//发货市
		waybillEntity.setReceiveCustomerDistCode(eWaybill.getReceiveCustomerDistCode());//发货区
		waybillEntity.setReceiveCustomerAddress(eWaybill.getReceiveCustomerAddress());//发货具体地址
		LOGGER.info("发货客户基本信息设置完毕.....................收货人姓名为" + waybillEntity.getReceiveCustomerName()) ;
	}
	
	/**
	 * 设置运输信息
	 * 
	 * @author 136334-foss-BaiLei
	 * @date 2014-9-13下午5:22:49
	 */	
	private void  setTransportInfo(WaybillEntity waybillEntity,DispatchOrderEntity order){
		LOGGER.info("运输信息设置开始.....................订单号为" + waybillEntity.getOrderNo());
		waybillEntity.setDriverCode(order.getDriverCode());//司机工号
		waybillEntity.setLicensePlateNum(order.getVehicleNo());//车牌号
		waybillEntity.setPickupCentralized(WaybillConstants.NO);//是否集中接货，全部置N
		waybillEntity.setIsWholeVehicle(WaybillConstants.NO);
		
		if(StringUtils.isNotEmpty(order.getReceiveMethod())){
			if(order.getReceiveMethod().indexOf(WaybillConstants.STATUS_PICKUP) >= 0){
				waybillEntity.setReceiveMethod(WaybillConstants.SELF_PICKUP);//汽运自提
				waybillEntity.setPickupToDoor(WaybillConstants.NO);
			}else {
				waybillEntity.setReceiveMethod(WaybillConstants.DELIVER_UP);//快递默认汽运送货（上楼）
				waybillEntity.setPickupToDoor(WaybillConstants.YES);
			}
		}
		LOGGER.info("运输信息设置完毕.....................快递员工号为" + waybillEntity.getDriverCode());
	}
	
	/**
	 * 设置目的站
	 * 
	 * @author 136334-foss-BaiLei
	 * @date 2014-9-13下午5:22:49
	 */	
	private	void setTargetOrgCode(WaybillEntity waybillEntity,DispatchOrderEntity order){
		//根据收货省市区查询快递营业网点，优先取快递营业网点
		//目的站,订单有则取订单,没有则取快递营业点部
		SaleDepartmentEntity salesDept = null;
		if(StringUtils.isNotEmpty(order.getCustomerPickupOrgCode())){
			salesDept = saleDepartmentService.querySimpleSaleDepartmentByCode(order.getCustomerPickupOrgCode());
		}else{
			//进行统一化处理
			salesDept = waybillExpressService.getTargetOrgCode(waybillEntity);
		}
		if(salesDept == null){
			throw new WaybillValidateException(WaybillValidateException.DESTINATION_NULL);
		}
		//如果该提货网点不能开代收货款，则报错不能开提货网点单
		if(waybillEntity.getCodAmount() !=null && waybillEntity.getCodAmount().compareTo(BigDecimal.ZERO)>0){
			if(!FossConstants.YES.equals(salesDept.getCanAgentCollected())){
				throw new WaybillValidateException(WaybillValidateException.PICKUP_STATSION_CANNOT_COD);
			}
		}
		
		//是否支持开多件--废除到达部门一票多件的校验：与罗益文确认
		/*if(waybillEntity.getGoodsQtyTotal() != null && waybillEntity.getGoodsQtyTotal() > 1){
			//如果不支持开多件，则抛出异常
			if(!FossConstants.YES.equals(salesDept.getCanExpressOneMany())){
				throw new WaybillValidateException(WaybillValidateException.CANNOT_CREATE_ONETOMANY_EXP);
			}
		}*/
		
		//营业部是否支持签收单返单
		if(!WaybillConstants.NOT_RETURN_BILL.equals(waybillEntity.getReturnBillType())){
			if(!FossConstants.YES.equals(salesDept.getCanExpressReturnSignBill())){
				throw new WaybillValidateException(WaybillValidateException.SALESDEPT_CAN_NOT_RETURN_SIGNBILL);
			}
		}
		
		//否则进行赋值
		waybillEntity.setCustomerPickupOrgCode(salesDept.getCode());//提货网点CODE
		waybillEntity.setCustomerPickupOrgName(salesDept.getName());//提货网点名称
		//提货网点部门简称
		OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillEntity.getCustomerPickupOrgCode());
		if(orgInfo != null){
			waybillEntity.setTargetOrgCode(orgInfo.getOrgSimpleName());
		}
		LOGGER.info("目的站设置完毕.....................目的站编码为" + waybillEntity.getTargetOrgCode()) ;
	}
	
	
	public SaleDepartmentEntity getBestSalesDepartment(List<SaleDepartmentEntity> saleDepartmentEntityList){
		SaleDepartmentEntity saleDeprtTemp = null;
		//如果是普通快递营业部，直接赋值不解释
		for(SaleDepartmentEntity entity : saleDepartmentEntityList){
			//判定该部门是否为虚拟营业部
	    	OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(entity.getCode());
	    	if(orgInfo != null && !FossConstants.YES.equals(orgInfo.getExpressSalesDepartment())){
	    		continue;
	    	}
			if(!(entity.getName().contains("出发")||entity.getName().contains("外发") || (entity.getName().contains("远郊")))){
				//普通营业部
				saleDeprtTemp = entity;
				break;
			}
		}
		//如果没有找到普通快递营业部，先判定是否出发快递营业部，没有再次选择外发的
		if(saleDeprtTemp == null){
			for(SaleDepartmentEntity entity:saleDepartmentEntityList){
				//判定该部门是否为虚拟营业部
		    	OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(entity.getCode());
		    	if(orgInfo != null && !FossConstants.YES.equals(orgInfo.getExpressSalesDepartment())){
		    		continue;
		    	}
				//再次选择出发快递营业部，如果还没有，就只能选择外发了
				if(entity.getName().contains("出发")||entity.getName().contains("外发")){
					//出发营业部
					saleDeprtTemp = entity;
					break;
				}else {
					//远郊营业部 
					if(saleDeprtTemp!=null && saleDeprtTemp.getName().contains("出发")||entity.getName().contains("外发")){
						continue;
					}else{
						saleDeprtTemp = entity;
					}
				}
			}
		}
		return saleDeprtTemp;
	}

	/**
	 * 查询并设置始发配载部门、最终配载部门以及线路
	 * 
	 * @author 136334-foss-BaiLei
	 * @date 2014-9-13下午5:22:49
	 */
	private void queryLodeDepartmentInfo(WaybillEntity waybillEntity){
		Boolean isPickupCentralized = WaybillConstants.YES.equals(waybillEntity.getPickupCentralized()) ? true : false;
		//没有找到走路路径的，应该进行补录；
		try {
			//运输性质非空判断
			if(StringUtils.isEmpty(waybillEntity.getProductCode())){
				throw new WaybillValidateException(WaybillValidateException.PRODUCT_CODE_NULL);
			}
						
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
			LOGGER.error("激活电子面单调用走货路径失败："+ e.getMessage() + ","+ e.getStackTrace() +","+ isPickupCentralized +","+
					waybillEntity.getCreateOrgCode() +","+ waybillEntity.getCustomerPickupOrgCode() + "," +waybillEntity.getProductCode());
			throw new WaybillValidateException(WaybillValidateException.FREIGHT_ROUTE_NOT_FOUND); 
			
		} catch(BusinessException w){
			LOGGER.error("激活电子面单调用走货路径失败："+ w.getMessage() + ","+ w.getStackTrace() +","+ isPickupCentralized +","+
					waybillEntity.getCreateOrgCode() +","+ waybillEntity.getCustomerPickupOrgCode() + "," +waybillEntity.getProductCode());
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
	 * 设置配载信息
	 * 
	 * @author 136334-foss-BaiLei
	 * @date 2014-9-13下午5:22:49
	 */	
	private void setLoadInfo(WaybillEntity waybillEntity,DispatchOrderEntity order){
		LOGGER.info("配置信息设置开始.....................订单号为" + waybillEntity.getOrderNo());
		//配载部门、配载部门名称、最终配载部门、最终配置部门名称
		queryLodeDepartmentInfo(waybillEntity);
		waybillEntity.setCarDirectDelivery(WaybillConstants.NO);//是否大车直送	
		waybillEntity.setForbiddenLine(WaybillConstants.YES);//禁行
		//预计出发时间
		waybillEntity.setPreDepartureTime(waybillExpressService.getLeaveTime(waybillEntity));
		//预计派送/提货时间
		EffectiveDto effectiveDto = waybillManagerService.searchPreSelfPickupTime(waybillEntity.getCreateOrgCode(), waybillEntity.getCustomerPickupOrgCode(), waybillEntity.getProductCode(), waybillEntity.getPreDepartureTime(), new Date());
		//如果是自提货物的话
		if(effectiveDto != null){
			if(isPickup(waybillEntity, order)){
				waybillEntity.setPreArriveTime(effectiveDto.getDeliveryDate());
				waybillEntity.setPreCustomerPickupTime(effectiveDto.getDeliveryDate());
			}else{
				waybillEntity.setPreArriveTime(effectiveDto.getSelfPickupTime());
				waybillEntity.setPreCustomerPickupTime(effectiveDto.getSelfPickupTime());
			}
		}
		//如果是标准空运件,需要设定和票类型和航班类型(需求默认为合大票与中班类型)
		if(WaybillConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT.equals(waybillEntity.getProductCode())){
			waybillEntity.setFreightMethod(ProductEntityConstants.PRICING_PRODUCT_FREIGNT_HDP);
			waybillEntity.setFlightNumberType(DictionaryValueConstants.FLIGHT_TYPE_MIDDLE);
			/*FlightEntity entity = new FlightEntity();
			// 航班类型（早班、中班、晚班）
			entity.setFlightSort(waybillEntity.getFlightNumberType());
			OrgAdministrativeInfoEntity orgAdministrativeInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(waybillEntity.getCustomerPickupOrgCode());
			entity.setDestinationAirport(orgAdministrativeInfo == null ? null : orgAdministrativeInfo.getCityName());
			PaginationDto paginationDto = flightService.queryFlightDtoListBySelectiveCondition(entity , 0, Integer.MAX_VALUE);
			if(null != paginationDto && CollectionUtils.isNotEmpty(paginationDto.getPaginationDtos())){
				
			}
			waybillEntity.setFlightShift(DictionaryValueConstants);*/
		}
		//waybillEntity.setKilometer();//公里数	
		//waybillEntity.setLoadMethod();//配载类型
		//waybillEntity.setTransferStartOrgCode();//中转 返货的时候的中转起点外场
		//waybillEntity.setTransferStartOrgName();//中转 返货的时候的中转起点外场名称
		//private String freightMethod;//合票方式
		//private String arriveType;//到达类型
		//private String flightShift;//航班时间
		
		LOGGER.info("配置信息设置完毕.....................配置线路编码为" + waybillEntity.getLoadLineCode());
	}
	
	/**
	 * 判定是否自提货物
	 * @param waybillEntity
	 * @param order
	 * @return
	 */
	private Boolean isPickup(WaybillEntity waybillEntity,DispatchOrderEntity order) {
		if(StringUtils.isNotEmpty(waybillEntity.getReceiveMethod())
				&& waybillEntity.getReceiveMethod().indexOf(WaybillConstants.STATUS_PICKUP) >= 0){
			return true;
		} else {
			return false;
		}
	}
	
	
	/*
	 * 设置货物信息
	 * 
	 * @author 136334-foss-BaiLei
	 * @date 2014-9-13下午5:22:49
	 */
	private void setGoodsInfo(WaybillEntity waybillEntity,DispatchOrderEntity order){
		LOGGER.info("货物信息设置开始.....................订单号为" + waybillEntity.getOrderNo());
		waybillEntity.setGoodsName(order.getGoodsName());//货物名称
		waybillEntity.setGoodsQtyTotal(order.getGoodsQty());//货物总件数
		waybillEntity.setGoodsWeightTotal(order.getWeight());//货物总重量
		waybillEntity.setGoodsVolumeTotal(order.getVolume());//货物总体积
		if(order.getVolume() == null){
			waybillEntity.setGoodsSize(BigDecimal.ZERO.toString());
		}else{
			waybillEntity.setGoodsSize(order.getGoodsSize());//货物尺寸
		}
		waybillEntity.setGoodsPackage(order.getGoodsPackage());//包装
		//设置包装类型详情
		setPackInfo(waybillEntity);
		
		waybillEntity.setInnerNotes(order.getOrderNotes());//对内备注
		waybillEntity.setTransportationRemark(order.getOrderNotes());
		waybillEntity.setGoodsTypeCode(WaybillConstants.GOODS_TYPE_A);//货物类型
		waybillEntity.setPreciousGoods(WaybillConstants.NO);//是否贵重物品
		waybillEntity.setSpecialShapedGoods(WaybillConstants.NO);//是否异形物品
		
		//private String outerNotes;//对外备注不处理
		
		LOGGER.info("货物信息设置完毕.....................货物信息为" + waybillEntity.getGoodsName());
	}
	
	/*
	 * 设置货物运输性质
	 * 
	 * @author 136334-foss-BaiLei
	 * @date 2014-9-13下午5:22:49
	 */
	private void setProductCode(WaybillEntity waybillEntity,DispatchOrderEntity order){
		//判定产品类型是否为空
		if(StringUtils.isEmpty(waybillEntity.getProductCode()) && StringUtils.isEmpty(order.getProductCode())){
			LOGGER.info("运输性质为空,该定单号为:" + order.getOrderNo());
			throw new WaybillValidateException(WaybillValidateException.PRODUCT_CODE_NULL);
		}
		LOGGER.info("订单号为:"+waybillEntity.getOrderNo()+"运输性质设置开始.....................运输性质为" + waybillEntity.getProductCode());
		//设置产品类型
		if(StringUtils.isEmpty(waybillEntity.getProductCode())){
			waybillEntity.setProductCode(order.getProductCode());
		}
		//获取对应的产品类型:当3.60与标准快递时需要进行产品的转换
		setDivideProductCode(waybillEntity);
		//产品ID的设定
		ProductEntity product = productService.getLevel3ProductInfo(waybillEntity.getProductCode());
		waybillEntity.setProductId(product == null ? waybillEntity.getProductCode() : product.getId());
		LOGGER.info("订单号为:"+waybillEntity.getOrderNo()+"运输性质设置完毕.....................运输性质为" + waybillEntity.getProductCode());
	}
	
	
	/*
	 * 设置电子运单二期货物运输性质
	 * 
	 * @author 
	 * @date
	 */
	private void setSecondProductCode(WaybillEntity waybillEntity){
		//判定产品类型是否为空
		if(StringUtils.isEmpty(waybillEntity.getProductCode())){
			DispatchOrderEntity order = dispatchOrderEntityDao.queryAllInfoByOrderNo(waybillEntity.getOrderNo());
			if(null == order){
				LOGGER.info("运输性质为空,该定单号为:" + waybillEntity.getOrderNo());
				throw new WaybillValidateException(WaybillValidateException.PRODUCT_CODE_NULL);
			}else{
				waybillEntity.setProductCode(order.getProductCode());
			}
		}
		LOGGER.info("订单号为:"+waybillEntity.getOrderNo()+"运输性质设置开始.....................运输性质为" + waybillEntity.getProductCode());
		//获取对应的产品类型:当3.60与标准快递时需要进行产品的转换
		setDivideProductCode(waybillEntity);
		//产品ID的设定
		ProductEntity product = productService.getLevel3ProductInfo(waybillEntity.getProductCode());
		waybillEntity.setProductId(product == null ? waybillEntity.getProductCode() : product.getId());
		LOGGER.info("运输性质设置完毕.....................运输性质为" + waybillEntity.getProductCode()) ;
	}
	
	/**
	 * 产品转换:目前只有标准快递与3.60特惠件需要进行重量体积变化，其他快递产品不需要进行变化(可能后期添加物流这块数据)
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-17 19:24:08
	 * @param waybillEntity
	 */
	private void setDivideProductCode(WaybillEntity waybillEntity) {
		//目前只有标准快递与3.60特惠件需要进行重量体积变化，其他快递产品不需要进行变化(可能后期添加物流这块数据)
		if(WaybillConstants.ROUND_COUPON_PACKAGE.equals(waybillEntity.getProductCode())
				|| WaybillConstants.PACKAGE.equals(waybillEntity.getProductCode())){
			BigDecimal volumeWeight = waybillEntity.getGoodsWeightTotal();
			String prarentProductCode = null;
			if(waybillExpressService.onlineDetermineIsExpressByProductCode(waybillEntity.getProductCode(), waybillEntity.getBillTime())){
				prarentProductCode = PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20005;
			}
			
			if(waybillEntity.getGoodsVolumeTotal() != null && waybillEntity.getGoodsVolumeTotal().compareTo(BigDecimal.ZERO) > 0){
				//根据重抛比计算体积重
				volumeWeight = billCaculateService.validateWeightBubbleRate(waybillEntity.getDeliveryCustomerCode(),
						waybillEntity.getBillTime(), prarentProductCode, waybillEntity.getGoodsVolumeTotal(), waybillEntity.getReceiveOrgCode());
				if(null != volumeWeight && volumeWeight.compareTo(waybillEntity.getGoodsWeightTotal()) < 0){
					volumeWeight=waybillEntity.getGoodsWeightTotal(); 
				}
			}
			//取值重泡比：用于产品的切换
			ConfigurationParamsEntity config = pkpsysConfigService.queryConfigurationParamsByEntity
					(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP, ConfigurationParamsConstants.PKP_EXPRESS_WEIGHT_CUT,FossConstants.ROOT_ORG_CODE);
			
			if(config != null && StringUtils.isNotEmpty(config.getConfValue())){
				BigDecimal confValue = new BigDecimal(config.getConfValue());
				if(volumeWeight != null && volumeWeight.compareTo(confValue) < 0){
					waybillEntity.setProductCode(WaybillConstants.PACKAGE);//普通快递
				}else{
					waybillEntity.setProductCode(WaybillConstants.ROUND_COUPON_PACKAGE);//3.60
				}
			}
		}
	}
	
	/*
	 * 设置增值服务及费用基础信息
	 * 
	 * @author 136334-foss-BaiLei
	 * @date 2014-9-13下午5:22:49
	 */
	private void setFeeRelatedInfo(WaybillEntity waybillEntity,DispatchOrderEntity order, EWaybillOrderEntity eWaybill){
	
		//保价声明价值
		if(order.getInsuredAmount()==null){
			waybillEntity.setInsuranceAmount(BigDecimal.ZERO);
		}else{
			waybillEntity.setInsuranceAmount(order.getInsuredAmount());
		}
		
		//private BigDecimal insuranceRate;//保价费率
		//private BigDecimal insuranceFee;//保价费
		
		//代收货款
		if(order.getReviceMoneyAmount()==null){
			//代收货款
			waybillEntity.setCodAmount(BigDecimal.ZERO);
			//代收货款手续费
			waybillEntity.setCodFee(BigDecimal.ZERO);
		}else{
			//代收货款
			waybillEntity.setCodAmount(order.getReviceMoneyAmount());
			
			waybillEntity.setCodFee(BigDecimal.ZERO);
		}

		//private BigDecimal codRate;//代收费率
		//private BigDecimal codFee;//代收货款手续费
		//退款类型(如果代收为0，则退款类型置空)
		if(order.getReviceMoneyAmount()==null || order.getReviceMoneyAmount().equals(BigDecimal.ZERO)){
			waybillEntity.setRefundType(null);
		}else{
			waybillEntity.setRefundType(order.getReciveLoanType());
		}
		waybillEntity.setReturnBillType(eWaybill.getReturnBillType());//返单类别  
		waybillEntity.setSecretPrepaid(WaybillConstants.NO);//预付费保密
		waybillEntity.setCurrencyCode(WaybillConstants.CURRENCY_CODE);//付款币种
		waybillEntity.setWholeVehicleAppfee(BigDecimal.ZERO);//非整车，费用置0
		waybillEntity.setServiceFee(BigDecimal.ZERO);//无装卸费，费用置0
		
		//private BigDecimal deliveryGoodsFee;//送货费
		//private BigDecimal otherFee;//其他费用
		//private BigDecimal packageFee;//包装手续费
		//private BigDecimal promotionsFee;//优惠费用
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
		waybillEntity.setPromotionsCode(order.getCouponNumber());//优惠编码
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
		if(order.getReviceMoneyAmount() != null && order.getReviceMoneyAmount().compareTo(BigDecimal.ZERO) > 0){
			waybillEntity.setAccountName(order.getCodRefundAccountName());//返款帐户开户名称
			waybillEntity.setAccountCode(order.getCodRefundAccount());//返款帐户开户账户
			CusAccountEntity cusAccountEntity = waybillExpressService.queryEWaybillCusAccountInfo(waybillEntity);
			if(null == cusAccountEntity){
				throw new WaybillValidateException(WaybillValidateException.CUSTOMER_ACCOUNT_NULL);
			}
			waybillEntity.setAccountBank(cusAccountEntity.getOpeningBankName());//返款帐户开户银行
		}
		
		waybillEntity.setOrderPaidMethod(order.getPaidMethod());//支付方式
		
		LOGGER.info("费用相关信息设置完毕.....................货物信息为" + waybillEntity.getInsuranceAmount()) ;
	}	
	
	/*
	 * 初始化包装信息
	 * 
	 * @author 136334-foss-BaiLei
	 * @date 2014-9-13下午5:22:49
	 */
	private void setPackInfo(WaybillEntity waybillEntity){
		waybillEntity.setGoodsPackage(waybillEntity.getGoodsQtyTotal()+ExpWaybillConstants.GOOD_PACKAGE_DEFAULT);		
		waybillEntity.setPaperNum(waybillEntity.getGoodsQtyTotal());
		waybillEntity.setWoodNum(ZERO);
		waybillEntity.setFibreNum(ZERO);
		waybillEntity.setSalverNum(ZERO);
		waybillEntity.setMembraneNum(ZERO);
		waybillEntity.setOtherPackage(null);
		
		
		/*
		 * 正则表达式分词，拆分包装信息，以后可能用到
		else{
			waybillEntity.setGoodsPackage(pack);
			
			//正则表达式分词，把包装信息都拆分开来
			Pattern patternPack = Pattern.compile(WaybillConstants.GOOD_PACKAGE_SLIPT);
			java.util.regex.Matcher matcherPack = patternPack.matcher(pack);
			
			while(matcherPack.find()){
				String packMatched = matcherPack.group();
				if(packMatched.contains("纸")){
					
					//纸包装
					if(packMatched.substring(ZERO, packMatched.lastIndexOf("纸")) == null || 
							"".equals(packMatched.substring(ZERO, packMatched.lastIndexOf("纸")))){
						waybillEntity.setPaperNum(ZERO);
					}else{
						waybillEntity.setPaperNum(Integer.parseInt(
							(packMatched.substring(ZERO, packMatched.lastIndexOf("纸")))));
					}
					
				}else if(packMatched.contains("木")){
					
					//木包装
					if(packMatched.substring(ZERO, packMatched.lastIndexOf("木")) == null || 
							"".equals(packMatched.substring(ZERO, packMatched.lastIndexOf("木")))){
						waybillEntity.setWoodNum(ZERO);
					}else{
						waybillEntity.setWoodNum(Integer.parseInt(
							(packMatched.substring(ZERO, packMatched.lastIndexOf("木")))));
					}
					
				}else if(packMatched.contains( "纤")){
					
					//纤包装
					if(packMatched.substring(ZERO, packMatched.lastIndexOf("纤")) == null || 
							"".equals(packMatched.substring(ZERO, packMatched.lastIndexOf("纤")))){
						waybillEntity.setFibreNum(ZERO);
					}else{
						waybillEntity.setFibreNum(Integer.parseInt(
								(packMatched.substring(ZERO, packMatched.lastIndexOf("纤")))));
					}
					
				}else if(packMatched.contains("托")){
					
					//托包装
					if(packMatched.substring(ZERO, packMatched.lastIndexOf("托")) == null || 
							"".equals(packMatched.substring(ZERO, packMatched.lastIndexOf("托")))){
						waybillEntity.setSalverNum(ZERO);
					}else{
						waybillEntity.setSalverNum(Integer.parseInt(
								(packMatched.substring(ZERO, packMatched.lastIndexOf("托")))));
					}
					
				}else if(packMatched.contains("膜")){
					
					//膜包装
					if(packMatched.substring(ZERO, packMatched.lastIndexOf("膜")) == null || 
							"".equals(packMatched.substring(ZERO, packMatched.lastIndexOf("膜")))){
						waybillEntity.setMembraneNum(ZERO);
					}else{
						waybillEntity.setMembraneNum(Integer.parseInt(
								(packMatched.substring(ZERO, packMatched.lastIndexOf("膜")))));
					}
					
				}else{
					waybillEntity.setOtherPackage(packMatched);//其他包装
				}
			}
		}*/
	}


	/*
	 * 校验电子运单能否生成运单（PKP）
	 * 
	 * @author 136334-foss-BaiLei
	 * @date 2014-9-13下午5:22:49
	 */
	private void validateEWaybillPKP(WaybillDto waybillDto){
		WaybillEntity waybillEntity = waybillDto.getWaybillEntity();	
		WaybillExpressEntity waybillExpressEntity =  waybillDto.getWaybillExpressEntity();
		
		// PDA运单信息
		//validateWaybillNo(bean);
				
		// 整车约车校验
		//validateVehicleNumber(bean);
		
		// 快递重量、体积、件数校验
		waybillExpressService.validateExpWeightVolume(waybillEntity);
		
		// 目的站校验
		waybillExpressService.validateExpDestination(waybillEntity);

		// 产品校验
		waybillExpressService.validateExpProduct(waybillEntity);

		// 包装校验
		waybillExpressService.validateExpPack(waybillEntity);

		// 客户校验
		waybillExpressService.validateExpCustomer(waybillEntity);

		// 校验提货网点重量、体积上限
		waybillExpressService.validateExpVW(waybillEntity);

		// 付款方式校验
		waybillExpressService.validateExpPaymentMode(waybillEntity);

		//验证返单
		//validateReturnBill(bean);
		
		// 代收货款校验
		waybillExpressService.validateExpCod(waybillEntity);

		// 只允许合同客户才可以选择免费送货
		waybillExpressService.validateExpDeliverFree(waybillEntity);
		
		//验证重量与提货方式
		waybillExpressService.validateExpWeightDeliveryMode(waybillEntity);
		
		//检查试点城市和试点营业部的逻辑
		if(StringUtils.isNotBlank(waybillExpressEntity.getReturnType())|| 
				!WaybillConstants. WAYBILL_EXPRESSTYPE_RETURN_CARGO.equals(waybillExpressEntity.getReturnType())){
			waybillExpressService.validateExpressCity(waybillEntity,waybillExpressEntity);
		}
		
		//检查保险声明价值
		waybillExpressService.validateExpInsuranceFee(waybillEntity);
		
		//检查德邦客户和发货人工号
		waybillExpressService.validateExpDepponExpressEmpCode(waybillExpressEntity);	//生成待激活运单不用校验德邦客户及发货人工号
		
		//校验优惠券是否开启
		//waybillExpressService.validateExpPromotionsCode(waybillEntity);
		
		//校验营销活动是否开启
		//waybillExpressService.validateExpActiveStart(waybillEntity);
		
		LOGGER.info("信息校验完毕..........................................................." ) ;
		
	}

	/*
	 * 将WaybillEntity转化为WaybilPendingEntity
	 * 
	 * @author 136334-foss-BaiLei
	 * @date 2014-9-13下午5:22:49
	 */
	private WaybillPendingEntity convertWaybillEntityToPending(WaybillEntity waybillEntity){
		WaybillPendingEntity pending = new WaybillPendingEntity();
		if(waybillEntity != null){
			
			pending.setWaybillNo(waybillEntity.getWaybillNo());
			pending.setOrderNo(waybillEntity.getOrderNo());
			pending.setOrderChannel(waybillEntity.getOrderChannel());
			pending.setOrderPaidMethod(waybillEntity.getPaidMethod());
			
			pending.setDeliveryCustomerId(waybillEntity.getDeliveryCustomerId());
			pending.setDeliveryCustomerCode(waybillEntity.getDeliveryCustomerCode());
			pending.setDeliveryBigCustomer(waybillEntity.getDeliveryBigCustomer());
		    pending.setDeliveryCustomerName(waybillEntity.getDeliveryCustomerName());
		    pending.setDeliveryCustomerMobilephone(waybillEntity.getDeliveryCustomerMobilephone());
		    pending.setDeliveryCustomerPhone(waybillEntity.getDeliveryCustomerPhone());
		    pending.setDeliveryCustomerContact(waybillEntity.getDeliveryCustomerContact());
		    //PDA下拉是通过DeliveryCustomerContactId来下拉的
		    pending.setDeliveryCustomerContactId(waybillEntity.getDeliverCustContactId());
		    pending.setDeliveryCustomerNationCode(waybillEntity.getDeliveryCustomerNationCode());
		    pending.setDeliveryCustomerProvCode(waybillEntity.getDeliveryCustomerProvCode());
		    pending.setDeliveryCustomerCityCode(waybillEntity.getDeliveryCustomerCityCode());
		    pending.setDeliveryCustomerDistCode(waybillEntity.getDeliveryCustomerDistCode());
		    pending.setDeliveryCustomerAddress((waybillEntity.getDeliveryCustomerAddress()));
		    
		    pending.setReceiveCustomerId(waybillEntity.getReceiveCustomerId());
		    pending.setReceiveCustomerCode(waybillEntity.getReceiveCustomerCode());
		    pending.setReceiveBigCustomer(waybillEntity.getReceiveBigCustomer());
		    pending.setReceiveCustomerName(waybillEntity.getReceiveCustomerName());
		    pending.setReceiveCustomerMobilephone(waybillEntity.getReceiveCustomerMobilephone());
		    pending.setReceiveCustomerPhone(waybillEntity.getReceiveCustomerPhone());
		    pending.setReceiveCustomerContact(waybillEntity.getReceiveCustomerContact());
		    //pending.setReceiveCustomerContactId();
		    pending.setReceiveCustomerNationCode(waybillEntity.getReceiveCustomerNationCode());
		    pending.setReceiveCustomerProvCode(waybillEntity.getReceiveCustomerProvCode());
		    pending.setReceiveCustomerCityCode(waybillEntity.getReceiveCustomerCityCode());
		    pending.setReceiveCustomerDistCode(waybillEntity.getReceiveCustomerDistCode());
		    pending.setReceiveCustomerAddress(waybillEntity.getReceiveCustomerAddress());
		    //收货人乡镇信息
		    pending.setReceiveCustomerVillageCode(waybillEntity.getReceiveCustomerVillageCode());

		    pending.setReceiveOrgCode(waybillEntity.getReceiveOrgCode());
		    pending.setProductId(waybillEntity.getProductId());
		    pending.setProductCode(waybillEntity.getProductCode());
		    pending.setReceiveMethod(waybillEntity.getReceiveMethod());

		    pending.setCustomerPickupOrgCode(waybillEntity.getCustomerPickupOrgCode());
		    pending.setLoadMethod(waybillEntity.getLoadMethod());
		    pending.setTargetOrgCode(waybillEntity.getTargetOrgCode());
		    pending.setPickupToDoor(waybillEntity.getPickupToDoor());
		    pending.setDriverCode(waybillEntity.getDriverCode());
		    pending.setPickupCentralized(waybillEntity.getPickupCentralized());
		    pending.setLoadLineCode(waybillEntity.getLoadLineCode());
		    pending.setLoadOrgCode(waybillEntity.getLoadOrgCode());
		    pending.setLastLoadOrgCode(waybillEntity.getLastLoadOrgCode());

		    pending.setPreDepartureTime(waybillEntity.getPreDepartureTime());
		    pending.setPreCustomerPickupTime(waybillEntity.getPreCustomerPickupTime());
		    pending.setCarDirectDelivery(waybillEntity.getCarDirectDelivery());

		    pending.setGoodsName(waybillEntity.getGoodsName());
		    pending.setGoodsQtyTotal(waybillEntity.getGoodsQtyTotal());
		    pending.setGoodsWeightTotal(waybillEntity.getGoodsWeightTotal());
		    pending.setGoodsVolumeTotal(waybillEntity.getGoodsVolumeTotal());
		    pending.setGoodsSize(waybillEntity.getGoodsSize());
		    pending.setGoodsTypeCode(waybillEntity.getGoodsTypeCode());
		    pending.setPreciousGoods(waybillEntity.getPreciousGoods());
		    pending.setSpecialShapedGoods(waybillEntity.getSpecialShapedGoods());
		    pending.setOuterNotes(waybillEntity.getOuterNotes());
		    pending.setInnerNotes(waybillEntity.getInnerNotes());
		    pending.setGoodsPackage(waybillEntity.getGoodsPackage());

		    pending.setInsuranceAmount(waybillEntity.getInsuranceAmount());
		    pending.setInsuranceRate(waybillEntity.getInsuranceRate());
		    pending.setInsuranceFee(waybillEntity.getInsuranceFee());
		    pending.setCodAmount(waybillEntity.getCodAmount());
		    pending.setCodRate(waybillEntity.getCodRate());
		    pending.setCodFee(waybillEntity.getCodFee());
		    pending.setRefundType(waybillEntity.getRefundType());
		    pending.setReturnBillType(waybillEntity.getReturnBillType());
		    pending.setSecretPrepaid(waybillEntity.getSecretPrepaid());
		    pending.setToPayAmount(waybillEntity.getToPayAmount());
		    pending.setPrePayAmount(waybillEntity.getPrePayAmount());
		    pending.setDeliveryGoodsFee(waybillEntity.getDeliveryGoodsFee());
		    pending.setOtherFee(waybillEntity.getOtherFee());
		    pending.setPackageFee(waybillEntity.getPackageFee());
		    pending.setPromotionsFee(waybillEntity.getPromotionsFee());
		    pending.setBillingType(waybillEntity.getBillingType());
		    pending.setUnitPrice(waybillEntity.getUnitPrice());
		    pending.setTransportFee(waybillEntity.getTransportFee());
		    pending.setValueAddFee(waybillEntity.getValueAddFee());
		    pending.setPaidMethod(waybillEntity.getPaidMethod());
		    pending.setArriveType(waybillEntity.getArriveType());
		    pending.setForbiddenLine(waybillEntity.getForbiddenLine());
		    pending.setFreightMethod(waybillEntity.getFreightMethod());
		    pending.setFlightShift(waybillEntity.getFlightShift());
		    pending.setTotalFee(waybillEntity.getTotalFee());
		    pending.setPromotionsCode(waybillEntity.getPromotionsCode());

		    pending.setCreateTime(waybillEntity.getCreateTime());
		    pending.setModifyTime(waybillEntity.getModifyTime());
		    pending.setBillTime(waybillEntity.getBillTime());

		    pending.setCreateUserCode(waybillEntity.getCreateUserCode());
		    pending.setModifyUserCode(waybillEntity.getModifyUserCode());
		    pending.setCreateOrgCode(waybillEntity.getCreateOrgCode());
		    pending.setModifyOrgCode(waybillEntity.getModifyOrgCode());
		    pending.setCurrencyCode(waybillEntity.getCurrencyCode());
		    pending.setIsWholeVehicle(waybillEntity.getIsWholeVehicle());
		    pending.setWholeVehicleAppfee(waybillEntity.getWholeVehicleAppfee());
		    pending.setWholeVehicleActualfee(waybillEntity.getWholeVehicleActualfee());
		    pending.setAccountName(waybillEntity.getAccountName());
		    pending.setAccountCode(waybillEntity.getAccountCode());
		    pending.setAccountBank(waybillEntity.getAccountBank());

		    pending.setBillWeight(waybillEntity.getBillWeight());
		    pending.setPickupFee(waybillEntity.getPickupFee());
		    pending.setServiceFee(waybillEntity.getServiceFee());
		    pending.setPreArriveTime(waybillEntity.getPreArriveTime());
		    pending.setAddTime(waybillEntity.getAddTime());
		    pending.setPaperNum(waybillEntity.getPaperNum());
		    pending.setWoodNum(waybillEntity.getWoodNum());
		    pending.setFibreNum(waybillEntity.getFibreNum());
		    pending.setSalverNum(waybillEntity.getSalverNum());
		    pending.setMembraneNum(waybillEntity.getMembraneNum());
		    pending.setOtherPackage(waybillEntity.getOtherPackage());
		    pending.setTransportType(waybillEntity.getTransportType());
		    pending.setTransportationRemark(waybillEntity.getTransportationRemark());
		    //private String contactAddressId;//联系人收货地址，接送货赋值
		    //private String flightNumberType;//航班类型
		    //private String isPassOwnDepartment;//是否经过营业部（整车）
		    //private String collectionDepartment;//集中开单部门
		    //车牌号
		    pending.setLicensePlateNum(waybillEntity.getLicensePlateNum());
		    //private String handoverStatus;//是否已生成交接
		    //private String isOuterBranch;//是否外发
		    //private String orderVehicleNum; //约车编号
			//private BigDecimal kilometer;
			//private String isSMS;
		    pending.setFreightMethod(waybillEntity.getFreightMethod());//和票类型
		    pending.setFlightNumberType(waybillEntity.getFlightNumberType());//航班类型
		    pending.setIsPassOwnDepartment(waybillEntity.getIsPassOwnDepartment());//是否经过营业部（整车）
		    pending.setCollectionDepartment(waybillEntity.getCollectionDepartment());//集中开单部门
		    pending.setKilometer(waybillEntity.getKilometer());//公里数
		    pending.setOrderVehicleNum(waybillEntity.getOrderVehicleNum());//约车编号
		}
			
	    pending.setActive(WaybillConstants.NO);
		pending.setPendingType(WaybillConstants.WAYBILL_STATUS_EWAYBILL_PENDING);
		pending.setWaybillType(WaybillConstants.EWAYBILL);	
		return pending;
		
	}
	
	/*
	 * 获取系统信息WaybillSystemDto
	 * 
	 * @author 136334-foss-BaiLei
	 * @date 2014-9-13下午5:22:49
	 */
	public WaybillSystemDto getSystemDtoByWaybillDto(WaybillDto waybillDto){
		Date nowDate = new Date();
		// 设置统一的创建人、创建时间、更新人、更新时间
		WaybillSystemDto systemDto = new WaybillSystemDto();
		CurrentInfo currentInfo = waybillDto.getCurrentInfo();
		systemDto.setCurrentUser(currentInfo);
		systemDto.setCreateTime(nowDate);
		systemDto.setModifyTime(nowDate);
		systemDto.setBillTime(nowDate);
		return systemDto;
	}
 
	/**
	 * 填充实际承运信息
	 * 
	 * @author 136334-foss-BaiLei
	 * @date 2014-9-13下午5:22:49
	 */
	private ActualFreightEntity getActualFreightEntity(WaybillEntity waybillEntity, EWaybillOrderEntity eWaybill) {
		if(waybillEntity != null){
			ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
			//装卸费(折让方式)
			actualFreightEntity.setDcServicefee(waybillEntity.getDcServicefee());
			//开始开单时间
			actualFreightEntity.setStartBillTime(waybillEntity.getCreateTime());
			// 运单号
			actualFreightEntity.setWaybillNo(waybillEntity.getWaybillNo());
			// 货物名称
			if(StringUtils.isNotEmpty(waybillEntity.getGoodsName())){
				actualFreightEntity.setGoodsName(waybillEntity.getGoodsName());
			}
		 
			// 重量
			actualFreightEntity.setWeight(waybillEntity.getBillWeight());
			// 体积
			if(waybillEntity.getGoodsVolumeTotal()!=null &&waybillEntity.getGoodsVolumeTotal().doubleValue()>0){
				actualFreightEntity.setVolume(waybillEntity.getGoodsVolumeTotal());
			}else{
				actualFreightEntity.setVolume(BigDecimal.valueOf(POINT_001));
			}
			// 件数
			actualFreightEntity.setGoodsQty(waybillEntity.getGoodsQtyTotal());
			// 尺寸
			actualFreightEntity.setDimension(StringUtils.isNotEmpty(waybillEntity
					.getGoodsSize()) ? waybillEntity.getGoodsSize() : "1*1*1*1");
			// 保险声明价值
			actualFreightEntity.setInsuranceValue(waybillEntity.getInsuranceAmount());
			// 包装费
			actualFreightEntity.setPackingFee(waybillEntity.getPackageFee() != null ? waybillEntity
					.getPackageFee() : BigDecimal.ZERO);
			// 送货费
			actualFreightEntity.setDeliverFee(waybillEntity.getDeliveryGoodsFee() != null ? waybillEntity
					.getDeliveryGoodsFee() : BigDecimal.ZERO);
			// 装卸费
			actualFreightEntity.setLaborFee(waybillEntity.getServiceFee() != null ? waybillEntity
					.getServiceFee() : BigDecimal.ZERO);
			// 代收货款
			actualFreightEntity.setCodAmount(waybillEntity.getCodAmount() != null ? waybillEntity
					.getCodAmount() : BigDecimal.ZERO);
			// 增值费
			actualFreightEntity.setValueaddFee(waybillEntity.getValueAddFee() != null ? waybillEntity
					.getValueAddFee() : BigDecimal.ZERO);
			// 公布价运费
			actualFreightEntity.setFreight(waybillEntity.getTransportFee() != null ? waybillEntity
					.getTransportFee() : BigDecimal.ZERO);
			// 结清状态
			actualFreightEntity.setSettleStatus(FossConstants.NO);
			// 结清时间
			actualFreightEntity.setSettleTime(new Date());
			// 通知状态
			// actualFreightEntity.setNotificationType(vo.get)
			// 通知时间
			// actualFreightEntity.setNotificationTime(vo);
			// 送货时间
			// actualFreightEntity.setDeliverDate(vo.getPreCustomerPickupTime());
			// 实际送货方式
			actualFreightEntity.setActualDeliverType(waybillEntity.getReceiveMethod());
			// 运单状态
			actualFreightEntity.setStatus(WaybillConstants.UNACTIVE);
			// 库存天数
			// actualFreightEntity.setStorageDay(vo.getst)
			// 库存费用
			// actualFreightEntity.setStorageCharge(vo.get);
			
			//设置出发部门
			actualFreightEntity.setStartStockOrgCode(waybillEntity.getCreateOrgCode());
			// 获取最终库存部门和货区
			actualFreightEntity = queryEndStocksDepartmentService(waybillEntity, actualFreightEntity);
			
			// actualFreightEntity.setActualDeliverType(vo.getdelivery)
	
			// 整车开单的时候 对actual Freight表的字段做如下调整
			if (WaybillConstants.YES.equals(waybillEntity.getIsWholeVehicle())) {
				// 1 ARRIVE_GOODS_QTY = 开单件数
				actualFreightEntity.setArriveGoodsQty(waybillEntity.getGoodsQtyTotal());
				// 2 ARRIVE_NOTOUT_GOODS_QTY 到达未签收件数 = 开单件数
				actualFreightEntity.setArriveNotoutGoodsQty(waybillEntity.getGoodsQtyTotal());
				// 3 若预计到达时间 不为空则为预计到达时间，否则为当前时间
				// 预计到达时间
				Date preArrivedTime = waybillEntity.getPreCustomerPickupTime();
				if (null != preArrivedTime) {
					actualFreightEntity.setArriveTime(preArrivedTime);
				} else {
					actualFreightEntity.setArriveTime(new Date());
				}
			}
			if(eWaybill != null){
				// 保存官网用户名
				actualFreightEntity.setChannelCustId(eWaybill.getChannelCustID());
			}
			//短信标识
			actualFreightEntity.setIsSMS(WaybillConstants.NO);
			//快递优惠类型
			if(StringUtil.isNotEmpty(waybillEntity.getSpecialOffer())){
				actualFreightEntity.setSpecialOffer(waybillEntity.getSpecialOffer());
			}
			actualFreightEntity.setWaybillType(WaybillConstants.EWAYBILL);
			//发票标记
			actualFreightEntity.setInvoice(WaybillConstants.INVOICE_02);
			//设置收货人地址信息
			actualFreightEntity.setReceiveCustomerVillageCode(waybillEntity.getReceiveCustomerVillageCode());
			
			//设置是否统一结算标志
			//发货人客户编码
			if(StringUtils.isNotEmpty(waybillEntity.getDeliveryCustomerCode())){
				CusBargainEntity deliverCustBargaon = cusBargainService.queryCusBargainByCustCode(waybillEntity.getDeliveryCustomerCode());
				if(deliverCustBargaon != null && FossConstants.YES.equals(deliverCustBargaon.getAsyntakegoodsCode())){
					actualFreightEntity.setStartCentralizedSettlement(deliverCustBargaon.getAsyntakegoodsCode());
					actualFreightEntity.setStartContractOrgCode(deliverCustBargaon.getUnifiedCode());
					/*actualFreightEntity.setStartContractOrgName(orgAdministrativeInfoService.
							queryDeptNameByUnifiedCode(deliverCustBargaon.getUnifiedCode()));*/
					actualFreightEntity.setStartReminderOrgCode(deliverCustBargaon.getHastenfunddeptCode());
				}else{
					actualFreightEntity.setStartCentralizedSettlement(FossConstants.NO);
				}
			}
			if(StringUtils.isNotEmpty(waybillEntity.getReceiveCustomerCode())){
				CusBargainEntity receiverCustBargaon = cusBargainService.queryCusBargainByCustCode(waybillEntity.getReceiveCustomerCode());
				if(receiverCustBargaon != null && FossConstants.YES.equals(receiverCustBargaon.getAsyntakegoodsCode())){
					actualFreightEntity.setArriveCentralizedSettlement(receiverCustBargaon.getAsyntakegoodsCode());
					actualFreightEntity.setArriveContractOrgCode(receiverCustBargaon.getUnifiedCode());
					/*actualFreightEntity.setArriveContractOrgName(orgAdministrativeInfoService.
							queryDeptNameByUnifiedCode(receiverCustBargaon.getUnifiedCode()));*/
					actualFreightEntity.setArriveReminderOrgCode(receiverCustBargaon.getHastenfunddeptCode());
				}else{
					actualFreightEntity.setStartCentralizedSettlement(FossConstants.NO);
				}
			}
			return actualFreightEntity;
		}
		return null;
	}
	
	/**
	 * 获取开单组织
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-10-1 09:49:23
	 * @param waybillEntry
	 * @return
	 */
	public String queryStartStocksDepartmentService(WaybillEntity waybillEntry) {
		//传入的运单实体数据为空
		if (waybillEntry == null) {
			throw new WaybillStoreException(WaybillValidateException.WAYBILLENTITY_NULL);
		}
		//开单部门不可为空
		if (waybillEntry.getCreateOrgCode() == null) {
			throw new WaybillValidateException(WaybillValidateException.CREATEORG_CODE_NULL);
		}
		
		//获取对应的组织信息
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillEntry.getCreateOrgCode());
		if (orgAdministrativeInfoEntity == null) {
			throw new WaybillStoreException(WaybillValidateException.CREATEORG_INFO_NULL);
		}

		// 在集中开单的情况下 配载部门取法不同
		if (FossConstants.YES.equals(waybillEntry.getPickupCentralized())) {
			if (StringUtils.isEmpty(waybillEntry.getLoadOrgCode())) {
				throw new WaybillStoreException(WaybillValidateException.LOAD_ORG_CODE_NULL);
			}
			return waybillEntry.getLoadOrgCode();// 返回配载部门
		} else {
			// 是否营业部
			if (FossConstants.YES.equals(orgAdministrativeInfoEntity.getSalesDepartment())){
				SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(waybillEntry.getCreateOrgCode());
				if (saleDepartmentEntity == null) {
					throw new WaybillStoreException(WaybillValidateException.CREATEORG_SALEDEPT_INFO_NULL);
				}
				// 是否驻地部门
				if(FossConstants.YES.equals(saleDepartmentEntity.getStation())){
					// 如果是，返回驻地营业部
					return saleDepartmentEntity.getTransferCenter();
				}else{
					return waybillEntry.getCreateOrgCode();
				}
			}else{
				throw new WaybillStoreException(WaybillValidateException.IS_NOT_SALEDEPT);
			}
		}
	}
	
	/**
	 * 获取提货网点组织
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-10-1 09:49:23
	 * @param waybillEntry
	 * @return
	 */
	public ActualFreightEntity queryEndStocksDepartmentService(WaybillEntity waybillEntity, ActualFreightEntity actualFreightEntity) {
		String productCode = waybillEntity.getProductCode();
		/**
		 * 获取最终配载部门
		 */
		String lastLoadOrgCode = waybillEntity.getLastLoadOrgCode();

		String goodsAreaCode = null;
		String endStockOrgCode = null;
		String errorMessage = null;
		if (lastLoadOrgCode == null) {
			throw new WaybillValidateException(WaybillValidateException.LAST_LOAD_ORG_CODE_NULL);
		}

		//如果是经济快递
		if(waybillExpressService.onlineDetermineIsExpressByProductCode(productCode, waybillEntity.getBillTime())){
			//快递最终外场
			String lastOutFieldCode = waybillManagerService.queryLastOutFieldCode(waybillEntity);
			
			//快递代理信息
			OuterBranchExpressEntity entity = ldpAgencyDeptService.queryLdpAgencyDeptByCode(waybillEntity.getCustomerPickupOrgCode(), FossConstants.ACTIVE);
			//判断是不是快递代理
			if(null != entity){
				//是快递代理，则最终库存部门是最终外场
				endStockOrgCode = lastOutFieldCode;
				errorMessage = "快递代理对应的外场";
				if(waybillExpressService.onlineDetermineIsExpressByProductCode(productCode, waybillEntity.getBillTime())){
					goodsAreaCode = getExpressGoodsAreaCode(endStockOrgCode,ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE, errorMessage);
				}else{
					goodsAreaCode = getExpressGoodsAreaCode(endStockOrgCode,ExpWaybillConstants.ROUND_COUPON_PACKAGE, errorMessage);
				}
			}else{
				//提货网点所属组织
				OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybillEntity.getCustomerPickupOrgCode());
				if(null == org){
					throw new WaybillStoreException(WaybillStoreException.DEPT_NULL, new Object[] { "【" + waybillEntity.getLastLoadOrgCode() + "】" });
				}
				
				//判断是否为虚拟营业部
				if(FossConstants.YES.equals(org.getExpressSalesDepartment())){
					//虚拟营业部对应的库存部门为最终外场
					endStockOrgCode = lastOutFieldCode; 
				}else{
					//是否为营业部
					SaleDepartmentEntity saleDepartment = saleDepartmentService.querySaleDepartmentByCode(waybillEntity.getLastLoadOrgCode(), waybillEntity.getBillTime());
					//不是营业部
					if (saleDepartment == null) {
						throw new WaybillStoreException("不是快递代理，也不是营业部，数据有问题！");
					}
					//是营业部
					else {
						// 是否驻地部门
						if (saleDepartment.checkStation()) {
							endStockOrgCode = saleDepartment.getTransferCenter();
							errorMessage = "驻地部门" + saleDepartment.getName() + "对应的外场";
							goodsAreaCode = getGoodsAreaCode(endStockOrgCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION, errorMessage);
						} else {
							endStockOrgCode = lastLoadOrgCode;// 如果不是驻地部门，那么最终库存部门是最终配载部门
						}
					}
				}
			}
		}
		// 如果是偏线
		else if (PricingConstants.ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(productCode)) {
			endStockOrgCode = lastLoadOrgCode;// 最终库存部门是最终配载部门
			errorMessage = "偏线对应的外场";
			goodsAreaCode = getGoodsAreaCode(endStockOrgCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_OTHER, errorMessage);// 偏线货区编码
		} 
		// 如果是空运
		else if (PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productCode)){
			endStockOrgCode = outfieldService.queryTransferCenterByAirDispatchCode(lastLoadOrgCode);// 获取空运对应的外场
			errorMessage = "空运总调对应的外场";
			goodsAreaCode = getGoodsAreaCode(endStockOrgCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_AIRFREIGHT, errorMessage);// 获取空运库区
		}else {
			SaleDepartmentEntity saleDepartment = saleDepartmentService.querySaleDepartmentByCode(waybillEntity.getLastLoadOrgCode(), waybillEntity.getBillTime());
			if (saleDepartment == null) {
				throw new WaybillStoreException(WaybillStoreException.DEPT_NULL, new Object[] { "【" + waybillEntity.getLastLoadOrgCode() + "】" });
			}
			// 是否驻地部门
			if (saleDepartment.checkStation()) {
				endStockOrgCode = saleDepartment.getTransferCenter();
				errorMessage = "驻地部门" + saleDepartment.getName() + "对应的外场";
				goodsAreaCode = getGoodsAreaCode(endStockOrgCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION, errorMessage);
			} else {
				endStockOrgCode = lastLoadOrgCode;// 如果不是驻地部门，那么最终库存部门是最终配载部门
			}
		}

		actualFreightEntity.setGoodsAreaCode(goodsAreaCode);// 设置最终库存部门
		actualFreightEntity.setEndStockOrgCode(endStockOrgCode);// 设置对应库区

		if (StringUtils.isEmpty(actualFreightEntity.getStartStockOrgCode())) {
			throw new WaybillStoreException(WaybillStoreException.START_STOCK_ORG_CODE_NULL);
		}
		return actualFreightEntity;
	}
	
	/**
	 * 获取库区编码
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-11-6 上午9:17:13
	 */
	private String getExpressGoodsAreaCode(String transferCenter, String type, String errorMessage) {
		/**
		 * 通过获取服务，获取货区
		 */
		GoodsAreaEntity goodsArea = goodsAreaService.queryExpressGoodsAreaByTransCenterCode(transferCenter, type);
		if (goodsArea == null) {
			throw new WaybillValidateException(WaybillValidateException.GOODS_AREAS_NULL, new Object[] { errorMessage });
		} else {
			/**
			 * 获取货区编码
			 */
			if (StringUtils.isEmpty(goodsArea.getGoodsAreaCode())) {
				throw new WaybillValidateException(WaybillValidateException.GOODS_AREAS_CODE_NULL);
			} else {
				/**
				 * 返回获取编码
				 */
				return goodsArea.getGoodsAreaCode();
			}
		}
	}
	
	/**
	 * 获取库区编码
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-11-6 上午9:17:13
	 */
	private String getGoodsAreaCode(String transferCenter, String type,String errorMessage) {
		List<GoodsAreaEntity> goodsAreas = goodsAreaService.queryGoodsAreaListByType(transferCenter, type);
		if (goodsAreas == null || goodsAreas.isEmpty()) {
			throw new WaybillValidateException(WaybillValidateException.GOODS_AREAS_NULL,new Object[]{errorMessage});
		} else {
			GoodsAreaEntity goodsAreaEntity = goodsAreas.get(0);
			if (goodsAreaEntity.getGoodsAreaCode() == null) {
				throw new WaybillValidateException(WaybillValidateException.GOODS_AREAS_CODE_NULL);
			} else {
				return goodsAreaEntity.getGoodsAreaCode();
			}
		}
	}
	
	/**
	 * 填充快递实体
	 * @author 136334-foss-BaiLei
	 * @date 2014-9-13下午5:22:49
	 */
	private WaybillExpressEntity getWaybillExpressEntity(WaybillEntity waybillEntity, DispatchOrderEntity dispatchOrderEntity){
		if(waybillEntity == null){
			return null;
		}
		WaybillExpressEntity waybillExpressEntity = new WaybillExpressEntity();
		if(StringUtils.isNotBlank(waybillEntity.getWaybillNo())){
			waybillExpressEntity.setWaybillNo(waybillEntity.getWaybillNo());
		}
		//设置 内部带货  员工工号和 费用承担部门  从dispatchOrderEntity获取
		if(dispatchOrderEntity != null){
			validateSetinnerPickupFeeBurdenDept(dispatchOrderEntity, waybillExpressEntity);
		}
		waybillExpressEntity.setDeliveryCustomerProvCode(waybillEntity.getDeliveryCustomerProvCode());
		waybillExpressEntity.setDeliveryCustomerCityCode(waybillEntity.getDeliveryCustomerCityCode());
		waybillExpressEntity.setDeliveryCustomerDistCode(waybillEntity.getDeliveryCustomerDistCode());
		//entiy.setInnerPickupFeeBurdenDept(vo.getInnerPickupFeeBurdenDept());//获得内部带货费用承担部门
		waybillExpressEntity.setReceiveCustomerCode(waybillEntity.getReceiveCustomerCode());
		waybillExpressEntity.setReceiveCustomerName(waybillEntity.getReceiveCustomerName());
		waybillExpressEntity.setReceiveCustomerMobilephone(waybillEntity.getReceiveCustomerMobilephone());
		waybillExpressEntity.setReceiveCustomerPhone(waybillEntity.getReceiveCustomerPhone());
		waybillExpressEntity.setReceiveCustomerContact(waybillEntity.getReceiveCustomerContact());
		waybillExpressEntity.setReceiveCustomerProvCode(waybillEntity.getReceiveCustomerProvCode());
		waybillExpressEntity.setReceiveCustomerCityCode(waybillEntity.getReceiveCustomerCityCode());
		waybillExpressEntity.setReceiveCustomerDistCode(waybillEntity.getReceiveCustomerDistCode());
			
		waybillExpressEntity.setVolumeWeight(waybillEntity.getBillWeight());
		
		waybillExpressEntity.setReceiveCustomerAddress(StringUtils.substring(waybillEntity.getReceiveCustomerAddress(), 0, NumberConstants.NUMBER_100));// 收货具体地址
	
		waybillExpressEntity.setReceiveCustomerAddress(StringUtils.substring(waybillEntity.getReceiveCustomerAddress(), 0, NumberConstants.NUMBER_100));// 收货具体地址
	
		waybillExpressEntity.setReceiveMethod(waybillEntity.getReceiveMethod());
		waybillExpressEntity.setTargetOrgCode(waybillEntity.getTargetOrgCode());
		waybillExpressEntity.setBillTime(waybillEntity.getBillTime());
		waybillExpressEntity.setCreateOrgCode(waybillEntity.getCreateOrgCode());
		waybillExpressEntity.setCustomerPickupOrgCode(waybillEntity.getCustomerPickupOrgCode());
		waybillExpressEntity.setCustomerPickupOrgName(waybillEntity.getCustomerPickupOrgName());
		
		// 是否补码  
		waybillExpressEntity.setIsAddCode(FossConstants.NO);
					
		waybillExpressEntity.setExpressEmpCode(waybillEntity.getDriverCode());
		EmployeeEntity employeeEntity = employeeService.queryEmployeeByEmpCode(waybillEntity.getDriverCode());
		if(employeeEntity != null){
			waybillExpressEntity.setExpressEmpName(employeeEntity.getEmpName());
			waybillExpressEntity.setExpressOrgCode(employeeEntity.getOrgCode());
			waybillExpressEntity.setExpressOrgName(employeeEntity.getOrgName());	
		}
		//waybillExpressEntity.setPdaSerial(vo.getPdaSerial());
		//waybillExpressEntity.setBankTradeSerail(vo.getBankTradeSerail());
		waybillExpressEntity.setReceiveOrgCode(waybillEntity.getReceiveOrgCode());
		waybillExpressEntity.setReceiveOrgName(waybillEntity.getReceiveOrgName());
		waybillExpressEntity.setCanReturnCargo(FossConstants.YES);
		return waybillExpressEntity;
	}
	
	/**
	 * 生成待激活运单
	 * 
	 * @author 136334-foss-BaiLei
	 * @date 2014-9-13下午5:22:49
	 */
	@Transactional                 
	private void generateUnActiveEWaybill(DispatchOrderEntity order , EWaybillOrderEntity eWaybill){
		//生成WaybillDto
		WaybillDto waybillDto = generatePreEWaybill(order,eWaybill);
		//获取WaybillEntity
		WaybillEntity waybillEntity = waybillDto.getWaybillEntity();
		// 设置统一的创建人、创建时间、更新人、更新时间
		WaybillSystemDto systemDto = getSystemDtoByWaybillDto(waybillDto);	
		//校验
		validateEWaybill(waybillDto);
		//如果该订单在pending表存在，说明是待改接，若运单号存在，则不重复生成运单号
		WaybillPendingEntity pending = waybillPendingDao.queryEWaybillPendingByNo(order.getOrderNo(), FossConstants.INACTIVE);
		String orderStatus = null;
		String waybillNo = null;
		//如果是待改接，则不重新生成运单号，否则则需要自动生成运单号
		if(pending != null){
			waybillNo = pending.getWaybillNo();
			orderStatus = DispatchOrderStatusConstants.STATUS_AGAIN_PICKUP;
		}
		if(waybillNo ==null || "".equals(waybillNo)){
			if(StringUtils.isNotBlank(order.getWaybillNo())){
				//外部渠道传入了运单号则直接用该运单号
				waybillNo = order.getWaybillNo();
			}else{
				//不是待改接并且外部渠道没有传入运单号的自动生成运单号
				waybillNo = generateEWaybillNO();
			}
		}
		waybillEntity.setWaybillNo(waybillNo);
		//转化为待补录WaybillPendingEntity
		WaybillPendingEntity waybillPendingEntity = convertWaybillEntityToPending(waybillEntity);
		
		//插入待补录表
		if(waybillPendingEntity != null){
			// 设置创建时间、创建人、修改时间、创建人工号、修改人工号、开单时间
			waybillPendingEntity.setCreateTime(systemDto.getCreateTime());
			waybillPendingEntity.setModifyTime(systemDto.getModifyTime());
			waybillPendingEntity.setBillTime(systemDto.getBillTime());
			waybillPendingEntity.setCreateUser(systemDto.getCurrentUser().getEmpCode());
			waybillPendingEntity.setModifyUserCode(systemDto.getCurrentUser().getEmpCode()); 
			// 运输类型
			waybillPendingEntity.setTransportType(waybillExpressService.getTransTypeByLevel3ProductCode(waybillPendingEntity.getProductCode()));
			// 设置是否有效
			waybillPendingEntity.setActive(FossConstants.NO);
			//添加地址备注
			waybillPendingEntity.setDeliveryCustomerAddressNote(order.getDeliveryCustomerAddressNote());
			waybillPendingEntity.setReceiveCustomerAddressNote(order.getReceiveCustomerAddressNote());
			try {
				if(DispatchOrderStatusConstants.STATUS_AGAIN_PICKUP.equals(orderStatus)){
					//如果是待改接后自动生成的pending数据，则只更新快递员个收货部门等信息
					WaybillPendingEntity entity = waybillPendingEntity;
					entity.setWaybillNo(waybillNo);
					entity.setActive(FossConstants.INACTIVE);
					//设置集中开单部门
					entity.setCollectionDepartment(waybillPendingEntity.getCollectionDepartment());
					//设置司机
					entity.setDriverCode(waybillPendingEntity.getDriverCode());
					//快递员code
					entity.setExpressEmpCode(waybillPendingEntity.getExpressEmpCode());
					//快递员姓名
					entity.setExpressEmpName(waybillPendingEntity.getExpressEmpName());
					//快递点部code
					entity.setExpressOrgCode(waybillPendingEntity.getExpressOrgCode());
					//快递员点部名称
					entity.setExpressOrgName(waybillPendingEntity.getExpressOrgName());
					//车牌号
					entity.setLicensePlateNum(waybillPendingEntity.getLicensePlateNum());
					//约车编号
					entity.setOrderVehicleNum(waybillPendingEntity.getOrderVehicleNum());
					//把货收上来的部门
					entity.setReceiveOrgCode(waybillPendingEntity.getReceiveOrgCode());
					//开单组织
					entity.setCreateOrgCode(waybillPendingEntity.getCreateOrgCode());
					//开单人
					entity.setCreateUser(waybillPendingEntity.getCreateUser());
					entity.setCreateUserCode(waybillPendingEntity.getCreateUserCode());	
					//收货人乡镇
					entity.setReceiveCustomerVillageCode(waybillPendingEntity.getReceiveCustomerVillageCode());
					waybillPendingDao.updateByWaybillNo(entity);
				}else{
					waybillPendingDao.insertSelective(waybillPendingEntity);
				}
			} catch (BusinessException e) {
				throw new WaybillPendingSaveException(WaybillPendingSaveException.SAVEPENDINGBILL_FAIL, new Object[]{messageBundle.getMessage(e.getMessage())});
			}
		}
		//如果不是待改接的，则执行以下操作，待改接的不执行
		if(!DispatchOrderStatusConstants.STATUS_AGAIN_PICKUP.equals(orderStatus)){
			//插入实际承运信息表
			//waybillManagerService.addActualFreightInfo(waybillDto,systemDto,false);
			if(waybillDto.getActualFreightEntity() != null){
				ActualFreightEntity actualFreightEntity = waybillDto.getActualFreightEntity();
				//设置运单号
				actualFreightEntity.setWaybillNo(waybillNo);
				//设置记录创建时间
				actualFreightEntity.setCreateTime(systemDto.getBillTime());
				//设置地址备注
				actualFreightEntity.setDeliveryCustomerAddressNote(order.getDeliveryCustomerAddressNote());
				actualFreightEntity.setReceiveCustomerAddressNote(order.getReceiveCustomerAddressNote());
				// 判断PDA补录是否已在运单表中生成记录（若在运单表生成了记录，则会在标签表、actualFreight表中生成记录）
				//根据运单号查询
				if(actualFreightService.isExistActualFreight(waybillNo)){
					LOGGER.error("运单号对应的ActualFreight数据已存在！");
					throw new WaybillSubmitException("运单号["+ waybillNo +"],对应的ActualFreight对象已存在!");
				}
				
				int i =actualFreightService.insertWaybillActualFreight(actualFreightEntity);
				if(i<=0){
					throw new WaybillSubmitException("运单号["+ waybillNo +"],对应的ActualFreight对象已存在!");
				}
			}
			
			//控制运单的唯一性
			if(StringUtils.isNotEmpty(waybillDto.getWaybillEntity().getWaybillNo())){
				WaybillTransactionEntity transacionStat = new WaybillTransactionEntity();
				// 主键
				transacionStat.setId(UUIDUtils.getUUID());
				// 运单号
				transacionStat.setWaybillNo(waybillDto.getWaybillEntity().getWaybillNo());
				// 业务完结情况
				transacionStat.setBusinessOver(ZEROSTR);
				// 财务完结情况
				transacionStat.setFinanceOver(ZEROSTR);
				// 创建日期
				transacionStat.setCreateDate(new Date());
				// 创建人
				transacionStat.setCreateUser(systemDto.getCurrentUser().getEmpCode());
				// 新增业务标识
				waybillTransactionService.addWaybillTransaction(transacionStat);
			}
			
			// 费用信息
			List<WaybillChargeDtlEntity> chargeDetail = waybillDto.getWaybillChargeDtlEntity();
			if (CollectionUtils.isNotEmpty(chargeDetail) && chargeDetail.size() > 0) {
				List<WaybillChargeDtlEntity> chargeDetailTemp = new ArrayList<WaybillChargeDtlEntity>();
				for(WaybillChargeDtlEntity chargeDtl : chargeDetail){
					chargeDtl.setWaybillNo(waybillNo);
					chargeDetailTemp.add(chargeDtl);
				}
				//插入费用表
				waybillChargeDtlService.appendWaybillChargeDtlEntityBatchAfterChange(chargeDetailTemp, systemDto);
			}
			
			// 折扣信息
			List<WaybillDisDtlEntity> discoutDetail = waybillDto.getWaybillDisDtlEntity();
			if (CollectionUtils.isNotEmpty(discoutDetail) && discoutDetail.size() > 0) {
				List<WaybillDisDtlEntity> discoutDetailTemp = new ArrayList<WaybillDisDtlEntity>();
				for(WaybillDisDtlEntity disDtl : discoutDetail){
					disDtl.setWaybillNo(waybillNo);
					discoutDetailTemp.add(disDtl);
				}
				//插入折扣明细表
				waybillDisDtlService.appendWaybillDisDtlEntityBatchAfterChange(discoutDetailTemp, systemDto);
			}
			
			// 付款信息
			List<WaybillPaymentEntity> paymentList = waybillDto.getWaybillPaymentEntity();
			if (CollectionUtils.isNotEmpty(paymentList)) {
				List<WaybillPaymentEntity> paymentListTemp = new ArrayList<WaybillPaymentEntity>();
				for(WaybillPaymentEntity payMent : paymentList){
					payMent.setWaybillNo(waybillNo);
					paymentListTemp.add(payMent);
				}
				//插入付款信息表
				waybillPaymentService.appendWaybillPaymentEntityBatchAfterChange(paymentListTemp, systemDto);
			}
		}else{
			//待改接的修改数据表数据
			//修改实际承运信息表
			if(waybillDto.getActualFreightEntity() != null){
				ActualFreightEntity actualFreightEntity = waybillDto.getActualFreightEntity();
				//设置运单号
				actualFreightEntity.setWaybillNo(waybillNo);
				//设置记录创建时间
				actualFreightEntity.setCreateTime(systemDto.getBillTime());
				//设置地址备注
				actualFreightEntity.setDeliveryCustomerAddressNote(order.getDeliveryCustomerAddressNote());
				actualFreightEntity.setReceiveCustomerAddressNote(order.getReceiveCustomerAddressNote());
//				// 判断PDA补录是否已在运单表中生成记录（若在运单表生成了记录，则会在标签表、actualFreight表中生成记录）
//				//根据运单号查询
//				if(actualFreightService.isExistActualFreight(waybillNo)){
//					LOGGER.error("运单号对应的ActualFreight数据已存在！");
//					throw new WaybillSubmitException("运单号["+ waybillNo +"],对应的ActualFreight对象已存在!");
//				}
				
				actualFreightService.updateByWaybillNoSelective(actualFreightEntity);
			}
			
			// 费用信息
			List<WaybillChargeDtlEntity> chargeDetail = waybillDto.getWaybillChargeDtlEntity();
			if (CollectionUtils.isNotEmpty(chargeDetail)) {
				List<WaybillChargeDtlEntity> chargeDetailTemp = new ArrayList<WaybillChargeDtlEntity>();
				for(WaybillChargeDtlEntity chargeDtl : chargeDetail){
					chargeDtl.setWaybillNo(waybillNo);
					//删除费用信息
					waybillChargeDtlService.deleteWaybillChargeDtlEntityByWaybillNo(waybillNo);
					chargeDetailTemp.add(chargeDtl);
				}
				//插入费用表
				waybillChargeDtlService.appendWaybillChargeDtlEntityBatchAfterChange(chargeDetailTemp, systemDto);
			}
			
			// 折扣信息
			List<WaybillDisDtlEntity> discoutDetail = waybillDto.getWaybillDisDtlEntity();
			if (CollectionUtils.isNotEmpty(discoutDetail)) {
				List<WaybillDisDtlEntity> discoutDetailTemp = new ArrayList<WaybillDisDtlEntity>();
				for(WaybillDisDtlEntity disDtl : discoutDetail){
					disDtl.setWaybillNo(waybillNo);
					//删除折扣明细表
					waybillDisDtlService.deleteWaybillDisDtlEntityByWaybillNo(waybillNo);
					discoutDetailTemp.add(disDtl);
				}
				//插入折扣明细表
				waybillDisDtlService.appendWaybillDisDtlEntityBatchAfterChange(discoutDetailTemp, systemDto);
			}
			
			// 付款信息
			List<WaybillPaymentEntity> paymentList = waybillDto.getWaybillPaymentEntity();
			if (CollectionUtils.isNotEmpty(paymentList)) {
				List<WaybillPaymentEntity> paymentListTemp = new ArrayList<WaybillPaymentEntity>();
				for(WaybillPaymentEntity payMent : paymentList){
					payMent.setWaybillNo(waybillNo);
					waybillPaymentService.deleteWaybillPaymentEntityByWaybillNo(waybillNo);
					paymentListTemp.add(payMent);
				}
				//插入付款信息表
				waybillPaymentService.appendWaybillPaymentEntityBatchAfterChange(paymentListTemp, systemDto);
			}
		}
		//运单号插入电子运单订单表
		eWaybill.setWaybillNO(waybillNo);
		ewaybillOrderEntityDao.updateEWaybillOrderEntitySelective(eWaybill);
	}
	

	//要查询的JobId
	public static final String queryJobId = WaybillConstants.UNKNOWN;
	//国家
	private static final String CN = "CN";
	//中文
	private static final String ZH = "zh";
	//本地语言信息，用于国际化在JOB中出现的异常
	private final static Locale LOCALE = new Locale(ZH, CN);

	/*
	 * 生成待激活运单JOB
	 * 
	 * @author 136334-foss-BaiLei
	 * @date 2014-9-13下午5:22:49
	 */
	public void batchGenerateUnActiveEWaybillJobs(){
		//每次要查询的条数
		String queryNum = "500";
		
		ConfigurationParamsEntity config = pkpsysConfigService.queryConfigurationParamsByEntity
				(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,WaybillConstants.EWAYBILL_GENERATE_JOB_SWITCH,FossConstants.ROOT_ORG_CODE);
		
		if(null!=config && StringUtils.isNotBlank(config.getConfValue())&& 
				WaybillConstants.YES.equals(config.getConfValue())){
			/**
			 * 创建VO，并设置受影响条数为queryNum，为了第一次能进入循环
			 */
			GenerateUnActiveEWaybillVo vo=new GenerateUnActiveEWaybillVo();
			ConfigurationParamsEntity configQueryNum = pkpsysConfigService.queryConfigurationParamsByEntity
					(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,WaybillConstants.EWAYBILL_GENERATE_NUMBER_PER_CYCLE,FossConstants.ROOT_ORG_CODE);
			if(configQueryNum != null && StringUtils.isNotBlank(configQueryNum.getConfValue())){
				queryNum = configQueryNum.getConfValue();
			}
			vo.setResultNum(Integer.parseInt(queryNum));
			while(vo.getResultNum()==Integer.parseInt(queryNum)){
				String jobId = UUIDUtils.getUUID();
				//更新一定数量的JobId	
				vo = updateGenearateUnActiveEWaybillForJobTopNum(jobId,queryNum);
				//根据JobId查询待处理信息
				List<EWaybillOrderEntity> eWaybillOrderEntityList = ewaybillOrderEntityDao.queryGenerateUnActiveEWaybillByJobID(vo.getJobId());
				if (eWaybillOrderEntityList!=null && eWaybillOrderEntityList.size() > 0) {
					batchGenearte(eWaybillOrderEntityList);
				}
			}
		}
	}
	

	/**
	 * 每次更新一定数量待处理电子运单JobId
	 * @author BaiLei
	 * @date 2014-09-08
	 */
	@Transactional
	public GenerateUnActiveEWaybillVo updateGenearateUnActiveEWaybillForJobTopNum(String jobId,String queryNum){
		GenerateUnActiveEWaybillVo vo=new GenerateUnActiveEWaybillVo();
		vo.setJobId(jobId);
		vo.setQueryNum(queryNum);
		vo.setQueryJobId(queryJobId);
		int result=ewaybillOrderEntityDao.updateJobIDTopByRowNum(vo);
		vo.setResultNum(result);
		return vo;
	}

	/**
	 * 执行JOB
	 * @author BaiLei
	 * @date 2014-09-08
	 */
	private void  batchGenearte(List<EWaybillOrderEntity> eWaybillOrderEntityList){
		if(CollectionUtils.isEmpty(eWaybillOrderEntityList)){
			return;
		}
		for (EWaybillOrderEntity eWaybillOrderEntity : eWaybillOrderEntityList) {
			//判定订单数据是否为空
			DispatchOrderEntity dispatchOrderEntity = dispatchOrderEntityDao.queryAllInfoByOrderNo(eWaybillOrderEntity.getOrderNO());
			if(dispatchOrderEntity == null){
				eWaybillOrderEntity.setFailReason("dispatchOrder Entity is null");
				eWaybillOrderEntity.setOperateResult(WaybillConstants.UNACTIVE_EWAYBILL_GENERATE_FAILURE);
				ewaybillOrderEntityDao.updateEWaybillOrderEntitySelective(eWaybillOrderEntity);
				addEWaybillOrderLog(eWaybillOrderEntity);
				continue;
			}
			
			//生成待激活
			Boolean wetherGenerateSuccess = executeGenearteUnActiveEWaybill(dispatchOrderEntity,eWaybillOrderEntity);
			
			if(!wetherGenerateSuccess){
				//生成待激活运单失败，则更新订单表状态为已退回
				dispatchOrderEntityDao.updateSatusByOrderNo(eWaybillOrderEntity.getOrderNO(), DispatchOrderStatusConstants.STATUS_RETURN);
			}else{
				//判定数据是否存在
				List<PdaScanEntity> pdaScanList = null;
				if(StringUtils.isNotEmpty(eWaybillOrderEntity.getWaybillNO())){
					PdaScanQueryDto pdaScanQueryDto = new PdaScanQueryDto();			
					pdaScanQueryDto.setWaybillNo(eWaybillOrderEntity.getWaybillNO());
					pdaScanQueryDto.setActive(WaybillConstants.YES);
					pdaScanQueryDto.setWhetherComplete(WaybillConstants.YES);
					pdaScanQueryDto.setTaskType(WaybillConstants.STATUS_PICKUP);
					pdaScanList = pdaScanDao.queryScanInforBySecondCondition(pdaScanQueryDto);
				}
				if(CollectionUtils.isNotEmpty(pdaScanList)){
					//调用生成激活运单
					Exception ebill = null;
					try {
						//TODO 后续在此需要判定该运单数据是否存在子母件的情况
						//进行运单数据的校验，如果运单数据存在，则不能继续进行运单数据的激活
						WaybillEntity waybillEntity = waybillManagerService.queryPartWaybillByNo(eWaybillOrderEntity.getWaybillNO());
						if(waybillEntity != null){
							throw new WaybillValidateException(WaybillValidateException.WAYBILL_EXSITS);
						}
						//实际承运信息数据的校验
						ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(eWaybillOrderEntity.getWaybillNO());
						//如果实际承运信息表数据存在，且是有效的运单或者激活失败的数据是不能进行激活的
						if(actualFreightEntity != null && (WaybillConstants.EFFECTIVE.equals(actualFreightEntity.getStatus())
								|| WaybillConstants.EWAYBILL_ACTIVE_FAIL.equals(actualFreightEntity.getStatus()))){
							throw new WaybillValidateException(WaybillValidateException.WAYBILL_EXSITS);
						}
						List<MutexElement> mutexes = new ArrayList<MutexElement>();	
						//创建运单互斥对象
						MutexElement mutexElement = new MutexElement(eWaybillOrderEntity.getWaybillNO(), WaybillConstants.WAYBILL_SUBMIT, MutexElementType.WABYILL_SUBMIT);//创建对象
						mutexes.add(mutexElement);
						//判断订单号是否为空
						if(StringUtils.isNotEmpty(eWaybillOrderEntity.getOrderNO())){
							MutexElement mutexElementOrder = new MutexElement(eWaybillOrderEntity.getOrderNO(), WaybillConstants.WAYBILL_SUBMIT, MutexElementType.WABYILL_SUBMIT);//创建对象
							mutexes.add(mutexElementOrder);
						}
						//zxy 20130924 BUG-55409 start 修改：临时解决此问题，弹出友好界面,目前大概确定是运单号为空导致但是运单号为什么为空未找出原因
						//互斥锁定
						boolean isLocked = false;
						try{
							isLocked = businessLockService.lock(mutexes, LOCK_TIMEOUT);
						}catch(Exception e){
							throw new WaybillSubmitException(WaybillSubmitException.WAYBILL_SUBMIT_LOCKED);
						}
						//zxy 20130924 BUG-55409 end 修改：临时解决此问题，弹出友好界面,目前大概确定是运单号为空导致但是运单号为什么为空未找出原因
						if(!isLocked){
							throw new WaybillSubmitException(WaybillSubmitException.WAYBILL_SUBMIT_LOCKED);
						}
						try{
							batchGenerateAntiveEwaybill(eWaybillOrderEntity.getWaybillNO());
						}finally{
							//释放锁
							businessLockService.unlock(mutexes);
						}
					} catch (Exception e) {
						eWaybillOrderEntity.setOperateResult(WaybillConstants.UNACTIVE_EWAYBILL_GENERATE_FAILURE);
						//获取异常信息数据
						String error = waybillExpressService.getExceptionErrorInfo(e);
						eWaybillOrderEntity.setFailReason(error);
						ewaybillOrderEntityDao.updateOperateResultByID(eWaybillOrderEntity);
						ebill = e;
						updateEWaybillOrderInfo(dispatchOrderEntity, eWaybillOrderEntity);
					}finally {
						if(ebill != null){
							//throw new Exception(ebill.getMessage());
							String error = ExceptionUtils.getFullStackTrace(ebill);
							if (StringUtils.isNotEmpty(error) && error.length() > NUMBER_3000) {				
								eWaybillOrderEntity.setFailReason("异常：" + error.substring(0, NUMBER_3000));
							} else {
								eWaybillOrderEntity.setFailReason("异常：" + error);
							}
							//添加异常消息记录
							addEWaybillOrderLog(eWaybillOrderEntity);
						}
					}
					
				}
			}
			//不论是否执行成功都新增ewaybillOrderLog数据
			afterGenearteUnActiveEWaybill(eWaybillOrderEntity);
		}
	}
	
	/**
	 * <p>单个处理待激活的待补录数据,此方法不建议直接使用.
	 * 只有在处理异常数据时候进行时候为佳</p>
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-10-27 16:14:36
	 */
	public void singleGenerateUnActiveEWaybill(String orderNo){
		//进行电子运单订单数据的封装
		EWaybillOrderEntity ewaybillOrder = ewaybillOrderEntityDao.queryEWaybillByOrderNo(orderNo);
		if(ewaybillOrder == null){
			EWaybillOrderLogEntity logEntity = new EWaybillOrderLogEntity();
			logEntity.setOrderNO(orderNo);
			List<EWaybillOrderLogEntity> eWaybillOrderLogList = ewaybillOrderLogEntityDao.selectEWaybillOrderByBasicParams(logEntity );
			if(CollectionUtils.isEmpty(eWaybillOrderLogList)){
				throw new WaybillValidateException(WaybillValidateException.NOT_EXPRESS_EWAYBILL_ORDER);
			}
			ewaybillOrder = new EWaybillOrderEntity();
			BeanUtils.copyProperties(eWaybillOrderLogList.get(0), ewaybillOrder);
		}
		//查询订单数据
		DispatchOrderEntity dispatchOrderEntity = dispatchOrderEntityDao.queryAllInfoByOrderNo(orderNo);
		if(dispatchOrderEntity == null){
			throw new WaybillValidateException(WaybillValidateException.DISPATCH_ORDER_NULL);
		}
		//如果执行不成功
		if(!executeGenearteUnActiveEWaybill(dispatchOrderEntity,ewaybillOrder)){
			//生成待激活运单失败，则更新订单表状态为已退回
			dispatchOrderEntityDao.updateSatusByOrderNo(ewaybillOrder.getOrderNO(), DispatchOrderStatusConstants.STATUS_RETURN);
		}
		//不论是否执行成功都新增ewaybillOrderLog数据
		afterGenearteUnActiveEWaybill(ewaybillOrder);
	}

	/**
	 * 执行生成待激活运单动作
	 * @author BaiLei
	 * @date 2014-09-08
	 */
	private boolean executeGenearteUnActiveEWaybill(DispatchOrderEntity order , EWaybillOrderEntity eWaybill) {
		Exception ebill=null;
		//调用生成待激活运单
		try {
			//判定已经激活激活数据是否存在
			WaybillEntity waybillEntity = waybillDao.queryWaybillByOrderNo(eWaybill.getOrderNO());
			if(waybillEntity != null){
				throw new WaybillValidateException(WaybillValidateException.EXIST_ACTIVE_WAYBILL_DATA);
			}
			
			//查询待补录电子运单数据是否存在
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("orderNo", eWaybill.getOrderNO());
			maps.put("active", FossConstants.NO);
			maps.put("pendingType", WaybillConstants.WAYBILL_STATUS_EWAYBILL_PENDING);
			maps.put("waybillType", WaybillConstants.EWAYBILL);
			//查询电子运单待补录表信息
			WaybillPendingEntity waybillPendingEntity = waybillPendingService.queryUnActiveEWaybillPendingByWaybilllNo(maps);
			//如果已经存在已经待激活的数据
			if(waybillPendingEntity != null){
				throw new WaybillValidateException(WaybillValidateException.IS_EXISIST_UNACTIVE_EWAYBILL_INFO);
			}

			List<MutexElement> mutexes = new ArrayList<MutexElement>();	
			//创建运单互斥对象
			MutexElement mutexElement = new MutexElement(eWaybill.getOrderNO(), WaybillConstants.WAYBILL_SUBMIT, MutexElementType.WABYILL_SUBMIT);//创建对象
			mutexes.add(mutexElement);
			//判断订单号是否为空
			if(StringUtils.isNotEmpty(eWaybill.getWaybillNO())){
				MutexElement mutexElementOrder = new MutexElement(eWaybill.getWaybillNO(), WaybillConstants.WAYBILL_SUBMIT, MutexElementType.WABYILL_SUBMIT);//创建对象
				mutexes.add(mutexElementOrder);
			}
			//zxy 20130924 BUG-55409 start 修改：临时解决此问题，弹出友好界面,目前大概确定是运单号为空导致但是运单号为什么为空未找出原因
			//互斥锁定
			boolean isLocked = false;
			try{
				isLocked = businessLockService.lock(mutexes, LOCK_TIMEOUT);
			}catch(Exception e){
				throw new WaybillSubmitException(WaybillSubmitException.WAYBILL_SUBMIT_LOCKED);
			}
			//zxy 20130924 BUG-55409 end 修改：临时解决此问题，弹出友好界面,目前大概确定是运单号为空导致但是运单号为什么为空未找出原因
			if(!isLocked){
				throw new WaybillSubmitException(WaybillSubmitException.WAYBILL_SUBMIT_LOCKED);
			}
			try{
				//生成待激活数据
				generateUnActiveEWaybill(order, eWaybill);
			}finally{
				//释放锁
				businessLockService.unlock(mutexes);
			}
			//设置操作类型
			eWaybill.setOperateResult(WaybillConstants.UNACTIVE_EWAYBILL_GENERATE_SUCCESS);
			updateEWaybillOrderInfo(order,eWaybill);
			ewaybillOrderEntityDao.updateOperateResultByID(eWaybill);
			return true;
		} catch (Exception e) {
			eWaybill.setOperateResult(WaybillConstants.UNACTIVE_EWAYBILL_GENERATE_FAILURE);
			//获取异常信息数据
			String error = waybillExpressService.getExceptionErrorInfo(e);
			eWaybill.setFailReason(error);
			ewaybillOrderEntityDao.updateOperateResultByID(eWaybill);
			ebill = e;
			updateEWaybillOrderInfo(order,eWaybill);
			return false;
		}finally {
			if(ebill != null){
				//throw new Exception(ebill.getMessage());
				String error = ExceptionUtils.getFullStackTrace(ebill);
				if (StringUtils.isNotEmpty(error) && error.length() > NUMBER_3000) {				
					eWaybill.setFailReason("异常：" + error.substring(0, NUMBER_3000));
				} else {
					eWaybill.setFailReason("异常：" + error);
				}
				//添加异常消息记录
				addEWaybillOrderLog(eWaybill);
				return false;
			}
		}
		
	}
	
	private void updateEWaybillOrderInfo(DispatchOrderEntity order,EWaybillOrderEntity eWaybill){	
		
		try {
			ResultDto res = crmOrderJMSService.sendModifyOrder(gainCrmModifyInfoDto(order,eWaybill));
			if(ResultDto.FAIL.equals(res.getCode())){
				LOGGER.error("更新订单状态失败：" + res.getMsg());
				throw new WaybillOrderHandleException(res.getMsg());
			}
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			String msg = e.getMessage();
			if(StringUtils.isEmpty(msg)){
				msg = e.getErrorCode();
			}
			throw new WaybillOrderHandleException(WaybillOrderHandleException.UPDATESTATUS_FAILREASON,
					new Object[]{messageBundle.getMessage(msg)} );
		}
	}
	

	/**
	 * 根据运单基本信息封装需要更新的对象
	 * 
	 * @author 136334-foss-Bailei
	 * @date 2014-09-17 下午10:02:13
	 */
	private CrmModifyInfoDto gainCrmModifyInfoDto(DispatchOrderEntity order,EWaybillOrderEntity eWaybill){
		CrmModifyInfoDto dto = new CrmModifyInfoDto();
		
		// 订单编号
		dto.setOrderNumber(order.getOrderNo());
		// 运单号码
		dto.setWaybillNumber(eWaybill.getWaybillNO());
		// 操作人员
		dto.setOprateUserNum(order.getDriverCode());
		
		// 操作人员所在部门
		SaleDepartmentEntity saleDepart = querySaleDepartmentByVehicleNo(order.getVehicleNo());
		if(saleDepart != null && StringUtils.isNotBlank(saleDepart.getCode())){
			OrgAdministrativeInfoEntity orgEntity = 
					orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(saleDepart.getCode());
			if(orgEntity != null &&StringUtils.isNotBlank(orgEntity.getUnifiedCode())){
				dto.setOprateDeptCode(orgEntity.getUnifiedCode());
			}
		}
		
		// 订单状态(必填)
		if(WaybillConstants.UNACTIVE_EWAYBILL_GENERATE_FAILURE.equals(eWaybill.getOperateResult())){
			dto.setGoodsStatus(DispatchOrderStatusConstants.CRM_ORDER_STATUS_MAPPING.get(DispatchOrderStatusConstants.STATUS_RETURN));
			//处理CRM显示异常信息为国际化的问题
			String failReason = null;
			try {
//				failReason=messageBundle.getMessage(eWaybill.getFailReason(),"");
				EWaybillMessageEntity eWaybillMessageEntity = eWaybillMessageDao.getEWaybillMessageByFailCode(eWaybill.getFailReason());
				if(eWaybillMessageEntity!=null&&StringUtils.isNotBlank(eWaybillMessageEntity.getMessage())){
					failReason = eWaybillMessageEntity.getMessage();
				}else{
					failReason = messageBundle.getMessage(eWaybill.getFailReason(),""); 
				}
				LOGGER.info("国际化信息为:"+failReason);
			} catch (Exception e) {
				LOGGER.info("国际化失败,异常信息为:");
				e.printStackTrace();
			}
			// 错误信息
			dto.setBackInfo(StringUtils.isEmpty(failReason) ? eWaybill.getFailReason() : failReason);
		}else if(WaybillConstants.UNACTIVE_EWAYBILL_GENERATE_SUCCESS.equals(eWaybill.getOperateResult())){
			//订单状态不变
			dto.setGoodsStatus(DispatchOrderStatusConstants.CRM_ORDER_STATUS_MAPPING.get("PICKUPING"));
		}
		
		// 判断司机工号是否为空，根据DEFECT-3385进行修改		
		if(StringUtil.isNotEmpty(order.getDriverCode())){
			// 员工信息
			EmployeeEntity emp = employeeService.queryEmployeeByEmpCode(order.getDriverCode());
			if (null != emp) {
				// 司机姓名
				dto.setDriverName(emp.getEmpName());
				// 司机手机
				dto.setDriverMobile(emp.getMobilePhone());	
			}
		}	
		return dto;
	}
	
	/**
	 * 生成日志
	 * 
	 * @author BaiLei
	 * @date 2014-09-08
	 */
	private void afterGenearteUnActiveEWaybill(EWaybillOrderEntity eWaybillOrderEntity){
		/**
		 * 封装待生成待激活运单日志实体
		 */
		EWaybillOrderLogEntity logEntity = new EWaybillOrderLogEntity();
		
		logEntity.setOrderNO(eWaybillOrderEntity.getOrderNO());
		if(WaybillConstants.UNACTIVE_EWAYBILL_GENERATE_SUCCESS.equals(eWaybillOrderEntity.getOperateResult())){
			logEntity.setWaybillNO(eWaybillOrderEntity.getWaybillNO());
		}
		logEntity.setReceiveCustomerID(eWaybillOrderEntity.getReceiveCustomerID());
		logEntity.setReceiveCustomerCode(eWaybillOrderEntity.getReceiveCustomerCode());
		logEntity.setReceiveCustomerName(eWaybillOrderEntity.getReceiveCustomerName());
		logEntity.setReceiveCustomerMobilephone(eWaybillOrderEntity.getReceiveCustomerMobilephone());
		logEntity.setReceiveCustomerPhone(eWaybillOrderEntity.getReceiveCustomerPhone());
		logEntity.setReceiveCustomerContact(eWaybillOrderEntity.getReceiveCustomerContact());
		logEntity.setReceiveCustomerNationCode(eWaybillOrderEntity.getReceiveCustomerNationCode());
		logEntity.setReceiveCustomerProvCode(eWaybillOrderEntity.getReceiveCustomerProvCode());
		logEntity.setReceiveCustomerCityCode(eWaybillOrderEntity.getReceiveCustomerCityCode());
		logEntity.setReceiveCustomerDistCode(eWaybillOrderEntity.getReceiveCustomerDistCode());
		logEntity.setReceiveCustomerAddress(eWaybillOrderEntity.getReceiveCustomerAddress());
		logEntity.setChannelCustID(eWaybillOrderEntity.getChannelCustID());
		logEntity.setGoodsSize(eWaybillOrderEntity.getGoodsSize());
		logEntity.setReturnBillType(eWaybillOrderEntity.getReturnBillType());
		logEntity.setCreateTime(new Date());
		logEntity.setModifyTime(eWaybillOrderEntity.getModifyTime());
		logEntity.setJobID(eWaybillOrderEntity.getJobID());
		logEntity.setOperateResult(eWaybillOrderEntity.getOperateResult());
		logEntity.setFailReason(eWaybillOrderEntity.getFailReason());
		logEntity.setDeliveryCustomerContact(eWaybillOrderEntity.getDeliveryCustomerContact());
		logEntity.setDeliveryCustomerContactId(eWaybillOrderEntity.getDeliveryCustomerContactId());
		
		/**
		 * 新增日志
		 */
		ewaybillOrderLogEntityDao.addEWaybillOrderLogEntity(logEntity);
		/**
		 * 删除需要生成待激活的订单信息
		 */
		//ewaybillOrderEntityDao.deleteEWaybillOrderbyOrderNO(eWaybillOrderEntity.getOrderNO());
	}

	
	
	/**
	 * 逾期后台自动作废数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-8-1 10:49:14
	 */
	@Override
	public void invalidEWaybillOrverDays() {
		int overDays = NumberConstants.NUMBER_7;
		int start = 0;
		int limited = NumberConstants.NUMBER_500;
		Date startDate = null;
		//获取系统设定的逾期几天的数据，如果失效，默认7天
		try{
			// 获取配置参数
			ConfigurationParamsEntity config = pkpsysConfigService.queryConfigurationParamsByEntity(
							DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,WaybillConstants.EWAYBILL_OVER_DAYS_INVALID_CODE,FossConstants.ROOT_ORG_CODE);
			//获取系统设定的时间，默认是7天
			if(config != null && StringUtils.isNotBlank(config.getConfValue())){
				overDays = Integer.parseInt(config.getConfValue());
			}
		}catch (Exception e) {
			overDays = NumberConstants.NUMBER_7;
		}
		//设定时间，方便走索引而不是全表扫描
		try {
			// 获取配置参数
			ConfigurationParamsEntity config = pkpsysConfigService.queryConfigurationParamsByEntity(
							DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,WaybillConstants.EWAYBILL_ONLINE_DAYS_CODE,FossConstants.ROOT_ORG_CODE);
			//获取系统设定的时间，默认是7天
			if(config != null && StringUtils.isNotBlank(config.getConfValue())){
				startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(config.getConfValue());
			}
		}catch (Exception e) {
			startDate = DateUtils.convert("2014-09-15");
		}
		List<WaybillPendingEntity> list = waybillPendingService.queryOverDaysEWaybillData(overDays, startDate, start, limited);
		while(CollectionUtils.isNotEmpty(list)){
			try{
				for(int i=0;i<list.size();i++){
					ewaybillService.invalidSingleEWaybillOrverDay(list.get(i));
				}
			}catch(BusinessException e){
				LOGGER.info("异常信息为:"+e.getMessage());
			}
			list = waybillPendingService.queryOverDaysEWaybillData(overDays, startDate, start, limited);
		}
	}
	
	/**
	 * 批量作废电子运单数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-9-14 18:33:14
	 */
	@Transactional
	@Override
	public void invalidSingleEWaybillOrverDay(WaybillPendingEntity waybillPendingEntity) {
		Date nowDate = new Date();
		if(waybillPendingEntity != null && StringUtils.isEmpty(waybillPendingEntity.getWaybillNo())){
			LOGGER.info("电子运单对应的单号不存在");
			throw new BusinessException("电子运单数据不存在");
		}
		//运单操作日志表
		WaybillSupplementLogEntity waybillSupplementLogEntity = new WaybillSupplementLogEntity();
		//作废待补录运单数据表
		if(waybillPendingEntity != null){
			waybillPendingService.deleteByWaybillNo(waybillPendingEntity.getWaybillNo());
			waybillSupplementLogEntity.setOldWaybillNo(waybillPendingEntity.getWaybillNo());
		}
		//运单表
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillPendingEntity.getWaybillNo());
		
		if(waybillEntity != null && WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(waybillEntity.getPendingType())){
			waybillManagerService.deleteWaybillEntityById(waybillEntity.getId());
			//作废运单数据
			waybillEntity.setActive(FossConstants.NO);
			waybillEntity.setModifyTime(nowDate);
			waybillPackBIService.addWaybillPackBIEntity(waybillEntity);
			waybillSupplementLogEntity.setWaybillId(waybillEntity.getId());
		}
		
		//实际承运信息表
		ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(waybillPendingEntity.getWaybillNo());
		if(actualFreightEntity != null && WaybillConstants.WAYBILL_EWAYBILL_TYPE.equals(actualFreightEntity.getWaybillType())){
			actualFreightService.deleteActualFreightByWaybillNo(actualFreightEntity.getWaybillNo());
			ActualFreightBIEntity actualFreightBIEntity = new ActualFreightBIEntity();
			actualFreightBIEntity.setActualFreightId(actualFreightEntity.getId());
			actualFreightBIEntity.setModifyTime(nowDate);
			actualFreightBIService.insert(actualFreightBIEntity);
			waybillSupplementLogEntity.setActualFreightId(actualFreightEntity.getId());
		}
		
		//按照更改单的逻辑走，封装查询参数
		LastWaybillRfcQueryDto queryDto = new LastWaybillRfcQueryDto();
		queryDto.setActive(FossConstants.NO);
		queryDto.setWaybillNo(waybillPendingEntity.getWaybillNo());
		queryDto.setCreateTime(waybillPendingEntity.getCreateTime());
		queryDto.setModifyTime(waybillPendingEntity.getModifyTime());
		
		//作废计费明细
		List<WaybillChargeDtlEntity> waybillDtlList = waybillChargeDtlService.queryNewChargeDtlEntityByNO(queryDto);
		//进行数据的作废
		if(CollectionUtils.isNotEmpty(waybillDtlList)){
			for(WaybillChargeDtlEntity entity : waybillDtlList){
				waybillChargeDtlService.deleteWaybillChargeDtlEntityById(entity.getId());
			}
		}
		
		//作废折扣明细
		List<WaybillDisDtlEntity>  waybillDisList = waybillDisDtlService.queryNewDisDtlEntityByNo(queryDto);
		if(CollectionUtils.isNotEmpty(waybillDisList)){
			for(WaybillDisDtlEntity entity : waybillDisList){
				waybillDisDtlService.deleteWaybillDisDtlEntityById(entity.getId());
			}
		}
		
		//删除带打包装明细
		WoodenRequirementsEntity woodenRequirement = woodenRequirementsService.queryNewWoodenRequirements(queryDto);
		//进行数据的作废
		if(woodenRequirement != null){
			woodenRequirementsService.deleteWoodRequirementsById(woodenRequirement.getId());
		}
		//查询应付款数据
		List<WaybillPaymentEntity> paymentList = waybillPaymentService.queryNewWaybillPaymentEntityByNo(queryDto);
		//进行数据的作废
		if(CollectionUtils.isNotEmpty(paymentList)){
			for(WaybillPaymentEntity payment : paymentList){
				waybillPaymentService.deleteWaybillPaymentEntityById(payment.getId());
			}
		}
		
		//删除运单状态表中的数据，释放运单号
		waybillTransactionService.deleteWaybillTransactionByWaybillNo(waybillPendingEntity.getWaybillNo());
		
		//更新订单表和crm状态
		EWaybillConditionDto eWaybillConditionDto = new EWaybillConditionDto();
		eWaybillConditionDto.setDriverCode(waybillPendingEntity.getDriverCode());
		eWaybillConditionDto.setVehicleNo(waybillPendingEntity.getLicensePlateNum());
		List<String> eWaybillNoList = new ArrayList<String>();
		eWaybillNoList.add(waybillPendingEntity.getWaybillNo());
		eWaybillConditionDto.seteWaybillNoList(eWaybillNoList);
		updateOrderCrmStatus(eWaybillConditionDto, waybillPendingEntity, DispatchOrderStatusConstants.STATUS_RETURN);
		
		//直接删除数据，不用管他是否存在数据
		ewaybillOrderEntityDao.deleteEWaybillOrderbyOrderNO(waybillPendingEntity.getOrderNo());
		//删除电子运单预处理日志记录
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderNo", waybillPendingEntity.getOrderNo());
		preHandEWaybillOrderDao.deletePreEWaybillOrderByParams(params);
		//删除库存
		WaybillStockEntity waybillStockEntity = new WaybillStockEntity();
		waybillStockEntity.setWaybillNO(waybillSupplementLogEntity.getOldWaybillNo());
		List<StockEntity> labelGoodList = stockService.queryStockByWaybillNo(waybillStockEntity);
		//是否存在，存在直接删除库存
		if(CollectionUtils.isNotEmpty(labelGoodList)){
			for(StockEntity serialsNo : labelGoodList){
				InOutStockEntity inOutStockEntity = new InOutStockEntity();
				inOutStockEntity.setWaybillNO(serialsNo.getWaybillNO());
				inOutStockEntity.setSerialNO(serialsNo.getSerialNO());

				EmployeeEntity emp = employeeService.queryEmployeeByEmpCode(WaybillConstants.CREATOR_SYSTEM);
				inOutStockEntity.setOrgCode(emp == null ? null : emp.getOrgCode());
				inOutStockEntity.setOperatorCode(WaybillConstants.CREATOR_SYSTEM);
				inOutStockEntity.setOperatorName(WaybillConstants.CREATOR_SYSTEM);
				inOutStockEntity.setInOutStockType(StockConstants.INVALID_GOODS_OUT_STOCK_TYPE);
				stockService.outStockDelivery(inOutStockEntity);
			}
		}

		//删除PDA盲扫记录
		PdaScanQueryDto pdaScanQueryDto = new PdaScanQueryDto();
		//添加运单号
		pdaScanQueryDto.setWaybillNo(waybillPendingEntity.getWaybillNo());
		//根据运单号进行删除,不需要知道处理结果，只是纯属进行数据的删除
		int deleteCount = pdaScanDao.deletePdaScanEntityByIdOrNo(pdaScanQueryDto);
		LOGGER.info("共删除了"+deleteCount+"条Pda盲扫记录");
		/**
		 * 删除GUI待补录消息的提醒
		 */
		pickupToDoMsgService.removeOneToDoMsg(DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__PDA_WAYBILL,
				null, waybillSupplementLogEntity.getOldWaybillNo());
		
		//增添一条后台作废的记录，用于记录对应的记录
		waybillSupplementLogEntity.setId(UUIDUtils.getUUID());
		waybillSupplementLogEntity.setCreateTime(nowDate);
		waybillSupplementLogEntity.setModifyTime(nowDate);
		//电子运单数据作废类型
		waybillSupplementLogEntity.setInvalidType(WaybillConstants.EWAYBILL_OPERATE_INVALID);
		EmployeeEntity emp = employeeService.queryEmployeeByEmpCode(WaybillConstants.CREATOR_SYSTEM);
		waybillSupplementLogEntity.setInvalidOrgCode(emp == null ? null : emp.getOrgCode());
		waybillSupplementLogEntity.setOperateCode(WaybillConstants.CREATOR_SYSTEM);
		waybillSupplementLogEntity.setInvalidReason(EWAYBILL_OPERATE_INVALID_REASON);
		waybillToSuppleDao.addWaybillToSuppleRecord(waybillSupplementLogEntity);
		//删除多线程跑的记录
		eWaybillProcessService.deleteByWaybillNo(waybillPendingEntity.getWaybillNo());
	}
	
	
	@Override
	public void activeEWaybillByPda(WaybillExpressPdaDto waybillExpressPdaDto, String billOrgCode) {
		WaybillDto waybillDto = generatePrePdaDto(waybillExpressPdaDto);
		validateEWaybill(waybillDto);
		try {
			waybillManagerService.submitEWaybill(waybillDto);
		} catch (Exception e) {
			e.printStackTrace();
			//更新承运信息状态为激活失败
			actualFreightService.setActualFreightStatus(waybillDto.getWaybillEntity().getWaybillNo(), WaybillConstants.EWAYBILL_ACTIVE_FAIL);
		}
		//激活不管成功或者失败，都删除电子订单
		ewaybillOrderEntityDao.deleteEWaybillOrderbyOrderNO(waybillDto.getOrderNo());
	}
	
	/**
	 * 查询是否电子运单数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-9-14 18:11:56
	 */
	@Override
	public boolean queryIsEWaybill(String eWaybillNo) {
		ActualFreightEntity actualFreight = actualFreightService.queryByWaybillNo(eWaybillNo);
		if(actualFreight != null && !WaybillConstants.OBSOLETE.equals(actualFreight.getStatus())){
			if(WaybillConstants.WAYBILL_EWAYBILL_TYPE.equals(actualFreight.getWaybillType()))
				return true;
		}
		return false;
	}

	/**
	 * 批量激活电子运单数据，根据运单号激活批量激活
	 * @param eWaybillConditionDto
	 * @date 2014-9-14 18:41:03
	 * @return
	 */
	@Override
	public void batchGenerateActiveEWaybillByPda(EWaybillConditionDto eWaybillConditionDto) {
		if(eWaybillConditionDto ==null){
			throw new IllegalArgumentException("传入激活电子运单数据为空");
		}
		List<String> waybillNoList = eWaybillConditionDto.geteWaybillNoList();
		if(CollectionUtils.isEmpty(waybillNoList)){
			throw new IllegalArgumentException("传入激活电子运单号集合数据为空");
		}
		String waybillNo = null;
		for(int i=0; i<waybillNoList.size(); i++){
			//运单号
			waybillNo = waybillNoList.get(i);
			try {
				LOGGER.info("激活的运单号为:"+(waybillNo));
				//进行运单数据的校验，如果运单数据存在，则不能继续进行运单数据的激活
				WaybillEntity waybillEntity = waybillManagerService.queryPartWaybillByNo(waybillNo);
				if(waybillEntity != null){
					throw new WaybillValidateException(WaybillValidateException.WAYBILL_EXSITS);
				}
				//实际承运信息数据的校验
				ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(waybillNo);
				//如果实际承运信息表数据存在，且是有效的运单或者激活失败的数据是不能进行激活的
				if(actualFreightEntity != null && (WaybillConstants.EFFECTIVE.equals(actualFreightEntity.getStatus())
						|| WaybillConstants.EWAYBILL_ACTIVE_FAIL.equals(actualFreightEntity.getStatus()))){
					throw new WaybillValidateException(WaybillValidateException.WAYBILL_EXSITS);
				}
				
				//获取订单号
				String orderNo = null;
				EWaybillOrderEntity ewaybillOrderEntity = ewaybillOrderEntityDao.queryEWaybillByWaybillNo(waybillNo);
				if(ewaybillOrderEntity == null){
					//查询待补录电子运单数据是否存在
					Map<String, Object> maps = new HashMap<String, Object>();
					maps.put("waybillNo", waybillNo);
					maps.put("active", FossConstants.NO);
					maps.put("pendingType", WaybillConstants.WAYBILL_STATUS_EWAYBILL_PENDING);
					maps.put("waybillType", WaybillConstants.EWAYBILL);
					WaybillPendingEntity waybillPendingEntity = waybillPendingService.queryUnActiveEWaybillPendingByWaybilllNo(maps);
					if(waybillPendingEntity != null){
						orderNo = waybillPendingEntity.getOrderNo();
					}
				}else{
					orderNo = ewaybillOrderEntity.getOrderNO();
				}
				
				List<MutexElement> mutexes = new ArrayList<MutexElement>();	
				//创建运单互斥对象
				MutexElement mutexElement = new MutexElement(waybillNo, WaybillConstants.WAYBILL_SUBMIT, MutexElementType.WABYILL_SUBMIT);//创建对象
				mutexes.add(mutexElement);
				//判断订单号是否为空
				if(StringUtils.isNotEmpty(orderNo)){
					MutexElement mutexElementOrder = new MutexElement(orderNo, WaybillConstants.WAYBILL_SUBMIT, MutexElementType.WABYILL_SUBMIT);//创建对象
					mutexes.add(mutexElementOrder);
				}
				//zxy 20130924 BUG-55409 start 修改：临时解决此问题，弹出友好界面,目前大概确定是运单号为空导致但是运单号为什么为空未找出原因
				//互斥锁定
				boolean isLocked = false;
				try{
					isLocked = businessLockService.lock(mutexes, LOCK_TIMEOUT);
				}catch(Exception e){
					throw new WaybillSubmitException(WaybillSubmitException.WAYBILL_SUBMIT_LOCKED);
				}
				//zxy 20130924 BUG-55409 end 修改：临时解决此问题，弹出友好界面,目前大概确定是运单号为空导致但是运单号为什么为空未找出原因
				if(!isLocked){
					throw new WaybillSubmitException(WaybillSubmitException.WAYBILL_SUBMIT_LOCKED);
				}
				try{
					batchGenerateAntiveEwaybill(waybillNo);
				}finally{
					//释放锁
					businessLockService.unlock(mutexes);
				}
			} catch (Exception e) {
				LOGGER.info("多线程执行出错，错误详情为:"+e.getMessage());
				EWaybillProcessEntity entity = new EWaybillProcessEntity();
				entity.setWaybillNo(waybillNoList.get(i));
				//设置异常信息
				String error = ExceptionUtils.getFullStackTrace(e);
				if (StringUtils.isNotEmpty(error) && error.length() > NUMBER_3000) {
					error = error.substring(0, NUMBER_3000);
				}
				entity.setFailReason(error);
				eWaybillProcessService.updateEWaybillProcessByWaybillNo(entity);
				EWaybillOrderEntity ewaybillOrderEntity = ewaybillOrderEntityDao.queryEWaybillByWaybillNo(waybillNoList.get(i));
				if(ewaybillOrderEntity != null){
					ewaybillOrderEntity.setFailReason(error);
					ewaybillOrderEntity.setOperateResult(WaybillConstants.UNACTIVE_EWAYBILL_GENERATE_FAILURE);
					ewaybillOrderEntityDao.updateEWaybillOrderEntitySelective(ewaybillOrderEntity);
				}
			}
		}
	}
	
	//PDA扫描信息的校验以及激活电子运单
	public void batchGenerateAntiveEwaybill(String waybillNo){
		//运单号
		if(StringUtils.isEmpty(waybillNo)){
			throw new WaybillValidateException(WaybillValidateException.EWAYBILL_ACTIVE_WAYBILL_NO_IS_NULL);
		}
		//查询待补录电子运单数据是否存在
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("waybillNo", waybillNo);
		maps.put("active", FossConstants.NO);
		maps.put("pendingType", WaybillConstants.WAYBILL_STATUS_EWAYBILL_PENDING);
		maps.put("waybillType", WaybillConstants.EWAYBILL);
		
		//查询电子运单待补录表信息
		WaybillPendingEntity waybillPendingEntity = waybillPendingService.queryUnActiveEWaybillPendingByWaybilllNo(maps);
		if(waybillPendingEntity == null){
			throw new WaybillValidateException(WaybillValidateException.CANNOT_FIND_PENDING_COMPLETE_INFO);
		}
		List<WaybillPendingEntity> waybillPendingEntityList = new ArrayList<WaybillPendingEntity>();
		waybillPendingEntityList.add(waybillPendingEntity);
		//运单号
		if(StringUtils.isEmpty(waybillPendingEntity.getOrderNo())){
			throw new WaybillValidateException(WaybillValidateException.EWAYBILL_ACTIVE_ORDER_NO_IS_NULL);
		}
		//电子运单订单数据
		EWaybillOrderEntity ewaybill = ewaybillOrderEntityDao.queryEWaybillByOrderNo(waybillPendingEntity.getOrderNo());
		if(ewaybill == null){
			throw new WaybillValidateException(WaybillValidateException.CANNOT_FIND_UNACTIVE_EWAYBILL_ORDER);
		}
		//订单数据
		DispatchOrderEntity dispatchOrderEntity = dispatchOrderEntityDao.queryAllInfoByOrderNo(waybillPendingEntity.getOrderNo());
		if(dispatchOrderEntity == null){
			throw new WaybillValidateException(WaybillValidateException.DISPATCH_ORDER_NULL);
		}
		//异常信息
		Exception eActive = null;
		WaybillDto waybillDto = null;
		try {
			//查询子母件数据详情
			/*Map<String, Object> params = new HashMap<String, Object>();
			params.put("parentWaybillNo", waybillNo);
			params.put("active", FossConstants.YES);
			List<WaybillRelateDetailEntity> relateDetailList = waybillRelateDetailEntityService.queryWaybillRelateDetailListByOrderOrWaybillNo(params);*/
			WaybillEntity waybillEntity = new WaybillEntity();
			BeanUtils.copyProperties(waybillPendingEntity, waybillEntity);
			//封装查询参数
			LastWaybillRfcQueryDto queryDto = new LastWaybillRfcQueryDto();
			queryDto.setActive(FossConstants.NO);
			queryDto.setWaybillNo(waybillPendingEntity.getWaybillNo());
			queryDto.setCreateTime(waybillPendingEntity.getCreateTime());
			queryDto.setModifyTime(waybillPendingEntity.getModifyTime());

			waybillDto = new WaybillDto();
			waybillDto.setWaybillType(waybillPendingEntity.getWaybillType());
			waybillDto.setWaybillEntity(waybillEntity);
			//waybillDto.setWaybillRelateDetailEntityList(relateDetailList);
			
			CusAccountEntity cusAccountEntity = waybillExpressService.queryEWaybillCusAccountInfo(waybillEntity);
			if(cusAccountEntity!= null){
				waybillDto.setOpenBank(cusAccountEntity);
			}
			
			WaybillExpressEntity waybillExpressEntity = getWaybillExpressEntity(waybillEntity, dispatchOrderEntity);
			if(waybillExpressEntity != null){
				waybillDto.setWaybillExpressEntity(waybillExpressEntity);
			}
			
			ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(waybillEntity.getWaybillNo());
			if(actualFreightEntity != null){
				waybillDto.setActualFreightEntity(actualFreightEntity);
			}
			
			List<WaybillChargeDtlEntity> charegeDtlList = waybillChargeDtlService.queryNewChargeDtlEntityByNO(queryDto);
			if(CollectionUtils.isNotEmpty(charegeDtlList)){
				waybillDto.setWaybillChargeDtlEntity(charegeDtlList);
			}
			List<WaybillDisDtlEntity> disDtlList = waybillDisDtlService.queryNewDisDtlEntityByNo(queryDto);
			if(CollectionUtils.isNotEmpty(disDtlList)){
				waybillDto.setWaybillDisDtlEntity(disDtlList);
			}
			WoodenRequirementsEntity woodRequerement = woodenRequirementsService.queryNewWoodenRequirements(queryDto);
			if(woodRequerement != null){
				waybillDto.setWoodenRequirementsEntity(woodRequerement);
			}
			List<WaybillPaymentEntity> waybillPaymentEntity = waybillPaymentService.queryNewWaybillPaymentEntityByNo(queryDto);
			if(CollectionUtils.isNotEmpty(waybillPaymentEntity)){
				waybillDto.setWaybillPaymentEntity(waybillPaymentEntity);
			}
			//校验快递员信息和重量体积比
			validateDriverAndWeight(waybillDto,waybillPendingEntity);
			if(waybillExpressEntity != null){
				//设置 内部带货  员工工号和 费用承担部门  从dispatchOrderEntity获取
				String deliveryCustomerCode = dispatchOrderEntity.getDeliveryCustomerCode();
				if(WaybillConstants.DEPPON_CUSTOMER.equals(deliveryCustomerCode)){
					String senderCode = dispatchOrderEntity.getSenderCode();
					String orgName = dispatchOrderEntity.getPaymentOrgCode();
					if(StringUtils.isEmpty(senderCode)){
						throw new WaybillValidateException(WaybillValidateException.INNER_PICKUP_DELIVERY_EMPLOYEE_CODE_NULL);
					}
					if(StringUtils.isEmpty(orgName)){
						throw new WaybillValidateException(WaybillValidateException.INNER_PICKUP_DEPT_NAME_IS_NULL);
					}
					waybillExpressEntity.setDeliveryEmployeeCode(senderCode);
					//因为官网同步过来的是部门名称需要转换成编码
					if(StringUtils.isNotEmpty(orgName)){
						OrgAdministrativeInfoEntity entity = new OrgAdministrativeInfoEntity();
						entity.setName(orgName);
						List<OrgAdministrativeInfoEntity>  orgList = orgAdministrativeInfoService.querySimpleOrgAdministrativeInfoByEntity(entity, 0, 1);
						if(CollectionUtils.isNotEmpty(orgList)){
							waybillExpressEntity.setInnerPickupFeeBurdenDept(orgList.get(0).getCode());
						}else{
							throw new WaybillValidateException(WaybillValidateException.INNER_PICKUP_DEPT_INFO_IS_VALID);
						}
					}
				}
				//设置快递员里面的信息，省的绩效考核数据算错
				if(null != waybillDto && null != waybillDto.getWaybillEntity()
						&& StringUtils.isNotEmpty(waybillDto.getWaybillEntity().getDriverCode())){
					EmployeeEntity employee = employeeService.queryEmployeeByEmpCodeNoCache(waybillDto.getWaybillEntity().getDriverCode());
					if(null != employee){
						//设置员工工号姓名
						waybillExpressEntity.setExpressEmpName(employee.getEmpName());
						//判定快递点部信息是否为空，不为空设置数据
						if(null != employee.getDepartment()){
							waybillExpressEntity.setExpressOrgCode(employee.getDepartment().getCode());
							waybillExpressEntity.setExpressOrgCode(employee.getDepartment().getName());
						}
					}
					waybillExpressEntity.setExpressEmpCode(waybillDto.getWaybillEntity().getDriverCode());
				}
				waybillDto.setWaybillExpressEntity(waybillExpressEntity);
			}
			
			CurrentInfo currentInfo = getEWaybilCurrentInfo(waybillDto.getWaybillEntity());
			if(currentInfo != null){
				waybillDto.setCurrentInfo(currentInfo);
				waybillDto.getWaybillEntity().setCreateUserName(currentInfo.getEmpName());
			}
			//进行当前时间的换算
			Date nowDate = new Date();
			//WaybillDto waybillDto = generatePreEWaybill(dispatchOrderEntity,ewaybill);
			//因为是进行数据的封装，故此时的运单号不存在，需要进行重新赋值
			if(waybillDto.getWaybillEntity() != null){
				waybillDto.getWaybillEntity().setWaybillNo(waybillNo);
				waybillDto.getWaybillEntity().setId(UUIDUtils.getUUID());
				waybillDto.getWaybillEntity().setActive(FossConstants.YES);
				waybillDto.getWaybillEntity().setPendingType(WaybillConstants.WAYBILL_STATUS_PDA_PENDING);
				waybillDto.getWaybillEntity().setCreateTime(nowDate);
				waybillDto.getWaybillEntity().setModifyTime(nowDate);
			}
			//实际承运信息表的Modify时间在Dao层有设置，在这里设置无用
			if(waybillDto.getActualFreightEntity() != null){
				waybillDto.getActualFreightEntity().setWaybillNo(waybillNo);
			}
			if(waybillDto.getWaybillExpressEntity() != null){
				waybillDto.getWaybillExpressEntity().setWaybillNo(waybillNo);
				waybillDto.getWaybillExpressEntity().setWaybillId(waybillDto.getWaybillEntity().getId());
				//增加是否可做返单标识
				waybillDto.getWaybillExpressEntity().setCanReturnCargo(WaybillConstants.YES);
				waybillDto.getWaybillExpressEntity().setCreateTime(nowDate);
			}
			//因为主要是按照PDA提交运单的方式进行运单数据的提价，需要把oldWaybillNo弄进去
			waybillDto.setOldWaybillNo(waybillNo);
			
			//校验快递员信息和重量体积比
			validateEWaybill(waybillDto);
			//这里提前设置一下数据，尽管waybillMangeService设置了，这里只是为了说明这里的时间不能随意修改
			if(CollectionUtils.isNotEmpty(waybillDto.getWaybillChargeDtlEntity())){
				for(WaybillChargeDtlEntity chargeDtlEntity : waybillDto.getWaybillChargeDtlEntity()){
					chargeDtlEntity.setActive(FossConstants.YES);
					chargeDtlEntity.setWaybillNo(waybillNo);
					chargeDtlEntity.setCreateTime(nowDate);
					chargeDtlEntity.setModifyTime(nowDate);
				}
			}
			if(CollectionUtils.isNotEmpty(waybillDto.getWaybillDisDtlEntity())){
				for(WaybillDisDtlEntity disDtlEntity : waybillDto.getWaybillDisDtlEntity()){
					disDtlEntity.setActive(FossConstants.YES);
					disDtlEntity.setWaybillNo(waybillNo);
					disDtlEntity.setCreateTime(nowDate);
					disDtlEntity.setModifyTime(nowDate);
				}
			}
			if(CollectionUtils.isNotEmpty(waybillDto.getWaybillPaymentEntity())){
				for(WaybillPaymentEntity waybillPayment : waybillDto.getWaybillPaymentEntity()){
					waybillPayment.setActive(FossConstants.YES);
					waybillPayment.setWaybillNo(waybillNo);
					waybillPayment.setCreateTime(nowDate);
					waybillPayment.setModifyTime(nowDate);
				}
			}
			if(CollectionUtils.isNotEmpty(waybillDto.getLabeledGoodEntities())){
				for(LabeledGoodEntity LabeledGoodEntity : waybillDto.getLabeledGoodEntities()){
					LabeledGoodEntity.setActive(FossConstants.YES);
					LabeledGoodEntity.setWaybillNo(waybillNo);
					LabeledGoodEntity.setCreateTime(nowDate);
					LabeledGoodEntity.setModifyTime(nowDate);
				}
			}
			if(waybillDto.getWoodenRequirementsEntity() != null){
				waybillDto.getWoodenRequirementsEntity().setActive(FossConstants.YES);
				waybillDto.getWoodenRequirementsEntity().setWaybillNo(waybillNo);
				waybillDto.getWoodenRequirementsEntity().setCreateTime(nowDate);
				waybillDto.getWoodenRequirementsEntity().setModifyTime(nowDate);
			}
			//提交电子运单
			waybillManagerService.submitEWaybill(waybillDto);
			// 添加货签信息PDA
			pdaScanService.addLabelGoodPda(waybillDto.getWaybillEntity());
			//激活成功，删除电子订单
			ewaybillOrderEntityDao.deleteEWaybillOrderbyOrderNO(waybillPendingEntity.getOrderNo());
			//删除pdaScan数据，保证开单后数据成功
			PdaScanQueryDto pdaScanQueryDto = new PdaScanQueryDto();
			pdaScanQueryDto.setWaybillNo(waybillPendingEntity.getOrderNo());
			pdaScanQueryDto.setTaskType(WaybillConstants.STATUS_UNLOAD);
			pdaScanDao.deletePdaScanEntityByIdOrNo(pdaScanQueryDto);
			autoAddCode(waybillDto,ewaybill);
		} catch (Exception e) {
			LOGGER.info("激活运单失败.运单号为:"+waybillNo+",错误详情:"+e);
			e.printStackTrace();
			ewaybill.setOperateResult(WaybillConstants.UNACTIVE_EWAYBILL_GENERATE_FAILURE);
			eActive = e;
			//進和PDA提交数据类型一致，进行数据的提交
			ewaybillService.afterActiveFail(waybillDto, waybillPendingEntityList);
			//设置异常编码
			ewaybill.setOperateResult(WaybillConstants.UNACTIVE_EWAYBILL_GENERATE_FAILURE);
			//获取异常信息
			String error = waybillExpressService.getExceptionErrorInfo(eActive);
			ewaybill.setFailReason(error);
			ewaybillOrderEntityDao.updateEWaybillOrderEntitySelective(ewaybill);
		} finally{
			if(eActive != null){
				//获取异常信息
				String error = waybillExpressService.getExceptionErrorInfo(eActive);
				ewaybill.setFailReason(error);
				//设置异常编码
				ewaybill.setOperateResult(WaybillConstants.UNACTIVE_EWAYBILL_GENERATE_FAILURE);
				//记录错误详情
				ewaybillOrderEntityDao.updateEWaybillOrderEntitySelective(ewaybill);
			}
			try {
				//判定是否出现异常
				if(eActive != null){
					//设置异常编码
					ewaybill.setOperateResult(WaybillConstants.UNACTIVE_EWAYBILL_GENERATE_FAILURE);
					String error = ExceptionUtils.getFullStackTrace(eActive);
					if (StringUtils.isNotEmpty(error) && error.length() > NUMBER_3000) {				
						ewaybill.setFailReason("异常：" + error.substring(0, NUMBER_3000));
					} else {
						ewaybill.setFailReason("异常：" + error);
					}
				}else{
					ewaybill.setOperateResult(WaybillConstants.UNACTIVE_EWAYBILL_GENERATE_SUCCESS);
				}
				//添加激活记录
				addEWaybillOrderLog(ewaybill);
				//按照更改单的逻辑走，封装查询参数
				LastWaybillRfcQueryDto queryDto = new LastWaybillRfcQueryDto();
				queryDto.setActive(FossConstants.NO);
				queryDto.setWaybillNo(waybillPendingEntity.getWaybillNo());
				queryDto.setCreateTime(waybillPendingEntity.getCreateTime());
				queryDto.setModifyTime(waybillPendingEntity.getModifyTime());
				//进行数据的删除
				//这些纯无效数据，你也许有疑问，为何待激活的时候需要生成，因为需要进行价格计算，待激活和集合生成数据存在重复，我们未来保证价格一致就提前生成，再做一次插入在删除
				List<WaybillChargeDtlEntity> charegeDtlListOld = waybillChargeDtlService.queryNewChargeDtlEntityByNO(queryDto);
				if(CollectionUtils.isNotEmpty(charegeDtlListOld)){
					for(WaybillChargeDtlEntity entity : charegeDtlListOld){
						waybillChargeDtlService.deleteWaybillChargeDtlEntityById(entity.getId());
					}
				}
				List<WaybillDisDtlEntity> disDtlListOld = waybillDisDtlService.queryNewDisDtlEntityByNo(queryDto);
				if(CollectionUtils.isNotEmpty(disDtlListOld)){
					for(WaybillDisDtlEntity entity : disDtlListOld){
						waybillDisDtlService.deleteWaybillDisDtlEntityById(entity.getId());
					}
				}
				WoodenRequirementsEntity woodRequerementOld = woodenRequirementsService.queryNewWoodenRequirements(queryDto);
				if(woodRequerementOld != null){
					woodenRequirementsService.deleteWoodRequirementsById(woodRequerementOld.getId());
				}
				List<WaybillPaymentEntity> waybillPaymentListOld = waybillPaymentService.queryNewWaybillPaymentEntityByNo(queryDto);
				if(CollectionUtils.isNotEmpty(waybillPaymentListOld)){
					for(WaybillPaymentEntity entity : waybillPaymentListOld){
						waybillPaymentService.deleteWaybillPaymentEntityById(entity.getId());
					}
				}
			} catch (Exception e) {
				LOGGER.info("删除无效数据失败，错误详情");
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * //激活前对快递员信息和重量体积的校验
	 * 徐丹153687
	 * 
	 * */
	public void validateDriverAndWeight(WaybillDto waybillDto,WaybillPendingEntity waybillPendingEntity){
		//对对象数据的引用
		WaybillEntity waybillEntity = new WaybillEntity();
		BeanUtils.copyProperties(waybillDto.getWaybillEntity(), waybillEntity);
		String waybillNo = waybillEntity.getWaybillNo();
		//实际承运信息
		ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
		BeanUtils.copyProperties(waybillDto.getActualFreightEntity(), actualFreightEntity);
		//查询出PDA扫描信息---
		PdaScanQueryDto pdaScanQueryDto = new PdaScanQueryDto();
		pdaScanQueryDto.setWaybillNo(waybillNo);
		pdaScanQueryDto.setActive(FossConstants.YES);
		pdaScanQueryDto.setWaybillType(WaybillConstants.EWAYBILL);
		pdaScanQueryDto.setScanType(WaybillConstants.SCAN_TYPE_SACN);
		pdaScanQueryDto.setWhetherComplete(FossConstants.YES);
		pdaScanQueryDto.setTaskType(WaybillConstants.STATUS_PICKUP);
		List<PdaScanEntity> pdaScanList = pdaScanDao.queryScanInforBySecondCondition(pdaScanQueryDto);
		if(CollectionUtils.isNotEmpty(pdaScanList)){
			//比较pending表中和scan表中司机code
			//重量体积
			BigDecimal weight = BigDecimal.ZERO;
			BigDecimal volume = BigDecimal.ZERO;
			//开单件数
			int goodQtycount = 0;
			//去除PDA重复提交的数据(比如0001提交了两次，需要只记录一次量)
			List<String> serialNoList = new ArrayList<String>();
			for(PdaScanEntity pdaSum : pdaScanList){
				if(!serialNoList.contains(pdaSum.getSerialNo())){
					serialNoList.add(pdaSum.getSerialNo());
					if(pdaSum.getWeight() != null && pdaSum.getWeight().compareTo(BigDecimal.ZERO) >= 0){
						weight = weight.add(pdaSum.getWeight());
					}
					if(pdaSum.getVolume() != null && pdaSum.getVolume().compareTo(BigDecimal.ZERO) >= 0){
						volume = volume.add(pdaSum.getVolume());
					}
					goodQtycount++;
				}
			}
			//说明没有修改过
			boolean isNeedReCalculate = false;
			String pdaScanDriverCode = pdaScanList.get(0).getDriverCode();
			String pendDriverCode = waybillPendingEntity.getDriverCode();	
			
			if(weight.compareTo(BigDecimal.ZERO) >= 0 || volume.compareTo(BigDecimal.ZERO) >= 0
					|| (StringUtil.isNotBlank(pendDriverCode) && StringUtil.isNotBlank(pdaScanDriverCode)&&(!pdaScanDriverCode.equals(pendDriverCode))) ){
					isNeedReCalculate = true;
			}
			if(isNeedReCalculate){
				//查询出待激活电子运单信息实体
				waybillEntity.setGoodsWeightTotal(weight.compareTo(BigDecimal.ZERO) == 0 ? waybillEntity.getGoodsWeightTotal() : weight);
				waybillEntity.setGoodsVolumeTotal(volume.compareTo(BigDecimal.ZERO) == 0 ? waybillEntity.getGoodsVolumeTotal() : volume);
				waybillEntity.setGoodsQtyTotal(goodQtycount);
				waybillEntity.setDriverCode(pdaScanDriverCode);
				//实际承运信息表
				actualFreightEntity.setWeight(weight.compareTo(BigDecimal.ZERO) == 0 ? waybillEntity.getGoodsWeightTotal() : weight);
				actualFreightEntity.setVolume(volume.compareTo(BigDecimal.ZERO) == 0 ? waybillEntity.getGoodsVolumeTotal() : volume);
				actualFreightEntity.setGoodsQty(goodQtycount);
				//更新待激活电子运单信息
				waybillDto.setWaybillEntity(setSecondWaybillEntity(waybillEntity));
				//必须要重新设置currentInfo，否则创建用户是以前的快递员
				CurrentInfo currentInfo = getEWaybilCurrentInfo(waybillEntity);
				waybillDto.setCurrentInfo(currentInfo);
				//更新对应的实际承运信息表
				waybillDto.setActualFreightEntity(actualFreightEntity);
				
				String orderNo = waybillPendingEntity.getOrderNo();
				//查询订单数据
				DispatchOrderEntity dispatchOrderEntity = dispatchOrderEntityDao.queryOrderByOrderNo(orderNo);
				if(dispatchOrderEntity != null){
					dispatchOrderEntity.setOrderNo(orderNo);
					dispatchOrderEntity.setWeight(weight.compareTo(BigDecimal.ZERO) == 0 ? waybillEntity.getGoodsWeightTotal() : weight);
					dispatchOrderEntity.setVolume(volume.compareTo(BigDecimal.ZERO) == 0 ? waybillEntity.getGoodsVolumeTotal() : volume);
					dispatchOrderEntity.setGoodsQty(goodQtycount);
					dispatchOrderEntity.setDriverCode(pdaScanDriverCode);
					ExpressVehiclesEntity expressVehiclesEntityPara = new ExpressVehiclesEntity();
					expressVehiclesEntityPara.setEmpCode(pdaScanDriverCode);
					expressVehiclesEntityPara.setActive(WaybillConstants.YES);
					List<ExpressVehiclesEntity> expressVehiclesEntityList = 
							expressVehiclesService.queryExpressVehiclesByEntity(expressVehiclesEntityPara);
					if(CollectionUtils.isNotEmpty(expressVehiclesEntityList)){
						ExpressVehiclesEntity expressVehiclesEntityResult = expressVehiclesEntityList.get(ZERO);
						dispatchOrderEntity.setDriverName(expressVehiclesEntityResult.getEmpName());
						dispatchOrderEntity.setVehicleNo(expressVehiclesEntityResult.getVehicleNo());
					}
					dispatchOrderEntityDao.updateDriverInfo(dispatchOrderEntity);
				}
				/*PDA提交激活信息重量为空时，无需重新计算运费，当重量不为空时，需根据最新重量值重新计算运费*/
				if(isNeedReCalculate){
				   waybillExpressService.calculateExpressAllFee(waybillDto,FossConstants.ACTIVE);		
				}
			}
		}
	}
	
	/**
	 * 激活失败的相关处理
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-10-26 18:41:03
	 * @param eWaybillConditionDto
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void afterActiveFail(WaybillDto waybillDto, List<WaybillPendingEntity> waybillPendingEntityList){
		Date nowDate = new Date();
		//进行数据的封装,重新进行数据的计算，但不抛错
		try {
			//为不影响以前电子运单的业务逻辑
			if (FossConstants.YES.equals(waybillDto.getWaybillEntity().getIsPicPackage())) {
				//addWeightForWaybillRelate(waybillDto, waybillPendingEntityList);
			} else {
				validateDriverAndWeight(waybillDto,waybillPendingEntityList.get(0));
			}
		} catch (Exception e1) {
			LOGGER.info("更换重量体积快递员出错,异常信息为:");
			e1.printStackTrace();
		}
		WaybillPendingEntity waybillPendingEntity = waybillPendingEntityList.get(0);
		WaybillEntity waybillEntity = waybillDto.getWaybillEntity();
		//待补录运单数据ID
		String waybillPendingId = waybillPendingEntity.getId();
		try {
			BeanUtils.copyProperties(waybillEntity, waybillPendingEntity);
			setCreateOrgCodeAndDriver(waybillPendingEntity);
		} catch (Exception e1) {
			LOGGER.info("复制给waybillPendingEntity出错,异常信息为:");
			e1.printStackTrace();
		}
		//按照更改单的逻辑走，封装查询参数
		LastWaybillRfcQueryDto queryDto = new LastWaybillRfcQueryDto();
		//进行一些必要的数据的插入
		queryDto.setActive(FossConstants.NO);
		queryDto.setWaybillNo(waybillPendingEntity.getWaybillNo());
		queryDto.setCreateTime(waybillPendingEntity.getCreateTime());
		queryDto.setModifyTime(waybillPendingEntity.getModifyTime());
		if(CollectionUtils.isNotEmpty(waybillDto.getWaybillRelateDetailEntityList()) && CollectionUtils.isNotEmpty(waybillDto.getWaybillChargeDtlEntity())){
			addWaybillCHDtlPendingEntityList(waybillDto.getWaybillChargeDtlEntity());
		}else{
			//作废计费明细
			List<WaybillChargeDtlEntity> waybillChgDtlList = waybillChargeDtlService.queryNewChargeDtlEntityByNO(queryDto);
			//进行数据的作废
			if(CollectionUtils.isNotEmpty(waybillChgDtlList)){
				addWaybillCHDtlPendingEntityList(waybillChgDtlList);
			}
		}
		waybillPendingEntity.setId(waybillPendingId);
		//设置待补录运单数据有效状态为Y
		waybillPendingEntity.setActive(FossConstants.YES);
		//设置业务开单时间为当前时间，否则会出现问题
		Date orderTime = waybillPendingEntity.getBillTime();
		waybillPendingEntity.setBillTime(nowDate);
		//设置运单pendingType为PDA_PENDING
		waybillPendingEntity.setPendingType(WaybillConstants.WAYBILL_STATUS_PDA_PENDING);
		//激活失败子母单业务逻辑
		if(FossConstants.YES.equals(waybillEntity.getIsPicPackage())){
			for(WaybillRelateDetailEntity waybillRelateDetailEntity : waybillDto.getWaybillRelateDetailEntityList()){
				String waybillNo=waybillRelateDetailEntity.getWaybillNo();
				String orderNo=waybillRelateDetailEntity.getOrderNo();
				waybillEntity.setWaybillNo(waybillNo);
				waybillEntity.setOrderNo(orderNo);
				//设置子单的重量体积
				waybillEntity.setGoodsVolumeTotal(waybillRelateDetailEntity.getVolume());
				waybillEntity.setGoodsWeightTotal(waybillRelateDetailEntity.getWeight());
				waybillDto.setWaybillEntity(waybillEntity);
				if(FossConstants.YES.equals(waybillRelateDetailEntity.getIsPicPackage())){
					 waybillPendingEntity.setWaybillNo(waybillNo);
					 waybillPendingEntity.setOrderNo(orderNo);
					 /**
					   * 297064 设置子母件激活失败时候waybillPending为EWAYBILL类型
					   */
					 waybillPendingEntity.setWaybillType(WaybillConstants.EWAYBILL);	
					 waybillPendingDao.insertSelective(waybillPendingEntity);
					 addExpressWaybillData(waybillDto, waybillPendingEntity);
				}else{
					ActualFreightEntity actualFreightforUpdate=new ActualFreightEntity();
					BeanUtils.copyProperties(waybillDto.getActualFreightEntity(), actualFreightforUpdate);
					BigDecimal zero =BigDecimal.ZERO;
					actualFreightforUpdate.setInsuranceValue(zero);
					// 包装费
					actualFreightforUpdate.setPackingFee(zero);
					// 送货费
					actualFreightforUpdate.setDeliverFee(zero);
					// 装卸费
					actualFreightforUpdate.setLaborFee(zero);
					// 代收货款
					actualFreightforUpdate.setCodAmount(zero);
					// 增值费
					actualFreightforUpdate.setValueaddFee(zero);
					// 公布价运费
					actualFreightforUpdate.setFreight(zero);
					actualFreightforUpdate.setId(UUIDUtils.getUUID());
					actualFreightforUpdate.setWaybillNo(waybillNo);
					// 设置记录创建时间
					actualFreightforUpdate.setCreateTime(nowDate);
					//设置修改时间为当前时间
					actualFreightforUpdate.setModifyTime(nowDate);
					// 设置为有效
					actualFreightforUpdate.setStatus(WaybillConstants.EWAYBILL_ACTIVE_FAIL);
					actualFreightService.insertWaybillActualFreight(actualFreightforUpdate);
					//实际承认表子单费用清0
					waybillPendingEntity.setWaybillNo(waybillNo);
					waybillPendingEntity.setOrderNo(orderNo);
					waybillPendingEntity.setTotalFee(zero);
					waybillPendingEntity.setInsuranceFee(zero);
					waybillPendingEntity.setCodAmount(zero);
					waybillPendingEntity.setCodFee(zero);
					waybillPendingEntity.setToPayAmount(zero);
					waybillPendingEntity.setPrePayAmount(zero);
					waybillPendingEntity.setDeliveryGoodsFee(zero);
					waybillPendingEntity.setOtherFee(zero);
					waybillPendingEntity.setPackageFee(zero);
					waybillPendingEntity.setPromotionsFee(zero);
					waybillPendingEntity.setTransportFee(zero);
					waybillPendingEntity.setValueAddFee(zero);
					waybillPendingEntity.setTotalFee(zero);
					waybillPendingEntity.setServiceFee(zero);
					addExpressWaybillData(waybillDto, waybillPendingEntity);
				}
			}
			
		}else{
			waybillPendingService.updateByPrimaryKeySelective(waybillPendingEntity);
			//将时间还原回来，主要对后面删除价格时效数据有用
			waybillPendingEntity.setBillTime(orderTime);
			addExpressWaybillData(waybillDto, waybillPendingEntity);
		}
	}
	
	private void addWaybillCHDtlPendingEntityList(List<WaybillChargeDtlEntity> waybillChgDtlList){
		for(WaybillChargeDtlEntity entity : waybillChgDtlList){
			if (!PricingConstants.PriceEntityConstants.PRICING_CODE_BF.equals(entity.getPricingEntryCode())
					&& !PricingConstants.PriceEntityConstants.PRICING_CODE_HK.equals(entity.getPricingEntryCode())) {
				WaybillCHDtlPendingEntity waybillCHDtlPendingEntity = new WaybillCHDtlPendingEntity();
				waybillCHDtlPendingEntity.setActive(FossConstants.YES);
				waybillCHDtlPendingEntity.setAmount(entity.getAmount());
				waybillCHDtlPendingEntity.setBillTime(entity.getBillTime());
				waybillCHDtlPendingEntity.setWaybillNo(entity.getWaybillNo());
				waybillCHDtlPendingEntity.setPricingEntryCode(entity.getPricingEntryCode());
				waybillCHDtlPendingEntity.setCreateTime(entity.getCreateTime());
				waybillCHDtlPendingEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
				waybillCHDtlPendingEntity.setPricingCriDetailId(entity.getPricingCriDetailId());												
				waybillCHDtlPendingDao.addWaybillCHDtlPendingSelective(waybillCHDtlPendingEntity);
			}
		}
	}
	
	/**
	 * 设置一些数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-15 18:45:24
	 * @param waybillDto
	 * @param waybillPendingEntity
	 */
	private void addExpressWaybillData(WaybillDto waybillDto, WaybillPendingEntity waybillPendingEntity){
		try{
			//将实际业务时间提前,主要对接菜鸟2小时时间的问题
			if(null != waybillDto && null != waybillDto.getActualFreightEntity()){
				waybillDto.getActualFreightEntity().setCreateTime(waybillPendingEntity.getCreateTime());
			}
			if(null != waybillDto && null != waybillDto.getWaybillEntity()){
				waybillDto.getWaybillEntity().setBillTime(waybillPendingEntity.getCreateTime());
			}
			ewaybillService.addWaybillExpress(waybillDto, waybillPendingEntity);
		}catch(Exception e){
			LOGGER.info("添加waybillEntity、actualFreightEntity，走货路径出错,异常信息为:");
			e.printStackTrace();
		}
		//删除运单状态表中的数据，释放运单号
		waybillTransactionService.deleteWaybillTransactionByWaybillNo(waybillPendingEntity.getWaybillNo());
		//添加待补录标示
		pickupToDoMsgService.addOneToDoMsg(DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__PDA_WAYBILL, DictionaryValueConstants.TODOMSG__BUSINESS_TYPE__PDA_WAYBILL, null, waybillPendingEntity.getCreateOrgCode(),
				waybillPendingEntity.getWaybillNo(), waybillPendingEntity.getWaybillNo());
		//添加活动信息
		//addCrmActiveInfoExpress(waybillPdaDto);
		// 订单号是否空
		try {
			if (StringUtils.isNotEmpty(waybillPendingEntity.getOrderNo())) {
				// PDA开单后修改约车订单状态
				OrderHandleDto orderHandleDto = new OrderHandleDto();
				// 订单号
				orderHandleDto.setOrderNo(waybillPendingEntity.getOrderNo());
				// 订单状态--已开单
				orderHandleDto.setOrderStatus(DispatchOrderStatusConstants.STATUS_WAYBILL);
				//设置订单操作类型为接货
				orderHandleDto.setOrderType(DispatchOrderStatusConstants.TYPE_PICKUP_ORDER);
				// 操作人编码
				orderHandleDto.setOperatorCode(waybillPendingEntity.getCreateUserCode());
				// 操作时间
				orderHandleDto.setOperateTime(waybillPendingEntity.getBillTime());
				// 操作部门编码
				orderHandleDto.setOperateOrgCode(waybillPendingEntity.getCreateOrgCode());
				// 更新订单状态
				orderTaskHandleService.orderWaybill(orderHandleDto);
			}
		} catch (Exception e) {
			//更新订单信息出现异常
			LOGGER.info("更新订单信息出现异常,异常信息:");
			e.printStackTrace();
		}
		//查询是否存在已经卸车的记录,如果存在，需要进行入库
		try {
			pdaScanService.waybillInStockByWaybillNo(waybillPendingEntity.getWaybillNo());
		} catch (Exception e) {
			//更新订单信息出现异常
			LOGGER.info("入库订单信息异常,异常信息:");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 设置一票多件重量体积
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-15 18:46:05
	 * @param waybillPendingEntity
	 */
	private void setCreateOrgCodeAndDriver(WaybillPendingEntity waybillPendingEntity) {
		// 获取pda扫描信息
		PdaScanQueryDto pdaScanQueryDto = new PdaScanQueryDto();
		pdaScanQueryDto.setWaybillNo(waybillPendingEntity.getWaybillNo());
		pdaScanQueryDto.setScanType(WaybillConstants.STATUS_PICKUP);
		pdaScanQueryDto.setActive(FossConstants.YES);
		List<PdaScanEntity> pdaScanList = pdaScanDao.queryScanInforBySecondCondition(pdaScanQueryDto);
		if(CollectionUtils.isEmpty(pdaScanList)){
			return;
		}
		String pdaDriverCode = pdaScanList.get(0).getDriverCode();
		if(StringUtils.isNotEmpty(pdaDriverCode)){
			ExpressVehiclesEntity expressVehiclesEntityPara = new ExpressVehiclesEntity();
			expressVehiclesEntityPara.setEmpCode(pdaDriverCode);
			expressVehiclesEntityPara.setActive(WaybillConstants.YES);
			List<ExpressVehiclesEntity> expressVehiclesEntityList = 
					expressVehiclesService.queryExpressVehiclesByEntity(expressVehiclesEntityPara);
			if(CollectionUtils.isNotEmpty(expressVehiclesEntityList)){
				waybillPendingEntity.setCreateOrgCode(expressVehiclesEntityList.get(0).getOrgCode());
				waybillPendingEntity.setDriverCode(pdaDriverCode);
				waybillPendingEntity.setLicensePlateNum(expressVehiclesEntityList.get(0).getVehicleNo());
			}
		}
		
	}

	/**
	 * 激活失败的相关处理
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-10-26 18:41:03
	 * @param waybillDto
	 * @param waybillPendingEntity
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public void addWaybillExpress(WaybillDto waybillDto, WaybillPendingEntity waybillPendingEntity) {
		UserEntity userEntity = new UserEntity();
		WaybillEntity waybillEntity = waybillDto.getWaybillEntity();
		//实际承运信息表
		ActualFreightEntity actualFreightEntity = waybillDto.getActualFreightEntity();
		String createUserCode = null;
		if(StringUtils.isNotEmpty(waybillEntity.getCreateUserCode())){
			createUserCode = waybillEntity.getCreateUserCode();
		}else if(StringUtils.isNotEmpty(waybillEntity.getDriverCode())){
			createUserCode = waybillEntity.getDriverCode();
		}else{
			createUserCode = waybillPendingEntity.getDriverCode();
		}
		if (StringUtils.isNotEmpty(createUserCode)) {
			userEntity.setEmpCode(waybillEntity.getCreateUserCode());
			EmployeeEntity employEntity = null;
			if (StringUtils.isNotEmpty(waybillEntity.getCreateUserCode())) {
				employEntity = employeeService.queryEmployeeByEmpCode(waybillEntity.getCreateUserCode());
			} else {
				if(StringUtils.isNotEmpty(waybillEntity.getDriverCode())){
					employEntity = employeeService.queryEmployeeByEmpCode(waybillEntity.getDriverCode());
				}
				if(employEntity == null){
					throw new PdaInterfaceException("司机编号为空！");
				}
			}
			if (employEntity != null) {
				userEntity.setEmpName(employEntity.getEmpName());
				userEntity.setEmpCode(employEntity.getEmpCode());
				userEntity.setEmployee(employEntity);	
				// 转换成HttpServletRequest
				javax.servlet.http.HttpSession session=new MockHttpSession();
				// 初始化sessionContext
				SessionContext.setSession(session);
				SessionContext.getSession().setObject("FOSS_KEY_CURRENT_DEPTCODE",(Object)employEntity.getOrgCode());
				if(StringUtils.isEmpty(employEntity.getOrgName())){
					if(employEntity.getDepartment()!=null){
						SessionContext.getSession().setObject("FOSS_KEY_CURRENT_DEPTNAME",employEntity.getDepartment().getName());
					}
				}else{
					SessionContext.getSession().setObject("FOSS_KEY_CURRENT_DEPTNAME",(Object)employEntity.getOrgName());
				}								
			} else {
				throw new PdaInterfaceException("查询不到该司机！");
			}
		}
		UserContext.setCurrentUser(userEntity);
		if(null == actualFreightEntity){
			actualFreightEntity = actualFreightService.queryByWaybillNo(waybillPendingEntity.getWaybillNo());
			if(null == actualFreightEntity){
				throw new WaybillValidateException(WaybillValidateException.WAYBILLENTITY_NULL);
			}
		}
		//设置运单数据为无效状态
		actualFreightEntity.setStatus(WaybillConstants.EWAYBILL_ACTIVE_FAIL);
		//更新承运信息状态为激活失败
		actualFreightService.updateByWaybillNoSelective(actualFreightEntity);
		try {
			//进行waybillEntity的数据插入
			BeanUtils.copyProperties(waybillPendingEntity, waybillEntity);
			waybillEntity.setId(UUIDUtils.getUUID());
			waybillEntity.setActive(FossConstants.YES);
			waybillEntity.setPendingType(WaybillConstants.WAYBILL_STATUS_PDA_PENDING);
			//生成运单基础数据
			waybillDao.addWaybillEntity(waybillEntity);
			// 对象传递
			TransportPathEntity transportPath = new TransportPathEntity();
			//运单号
			transportPath.setWaybillNo(waybillPendingEntity.getWaybillNo());
			//开单时间
			transportPath.setBillingTime(waybillPendingEntity.getBillTime());
			//开单组织 
			transportPath.setBillingOrgCode(waybillPendingEntity.getCreateOrgCode());
			//当前库存部门编码
			String currentOrgCode = waybillPendingEntity.getCreateOrgCode();// "";
			//当前部门编码
			transportPath.setCurrentOrgCode(currentOrgCode);
			//最终到达部门编号
			transportPath.setDestOrgCode(waybillPendingEntity.getCustomerPickupOrgCode());
			//总重量
			transportPath.setTotalWeight(waybillPendingEntity.getGoodsWeightTotal());
			//总体积
			transportPath.setTotalVolume(waybillPendingEntity.getGoodsVolumeTotal());
			//总件数
			transportPath.setGoodsQtyTotal(waybillPendingEntity.getGoodsQtyTotal());
			//运输类型
			transportPath.setTransportModel(waybillPendingEntity.getProductCode());
			// 生成走货路径
			calculateTransportPathService.createTransportPath(transportPath);
			// 添加货签信息PDA
			pdaScanService.addLabelGoodPda(waybillEntity);
			WaybillSystemDto systemDto = new WaybillSystemDto();
			systemDto.setBillTime(waybillEntity.getBillTime());
			systemDto.setCreateTime(waybillEntity.getCreateTime());
			systemDto.setModifyTime(waybillEntity.getModifyTime());
			systemDto.setCurrentUser(getEWaybilCurrentInfo(waybillEntity));
			//新增货签信息
			pdaScanService.addLabelGoodsInfo(waybillEntity, systemDto);
		} catch (Exception e) {
			LOGGER.info("生成运单信息或者走货路径创建失败,错误详情:");
			e.printStackTrace();
		}
	}

	public void addEWaybillOrderLog(EWaybillOrderEntity ewaybill){
		EWaybillOrderLogEntity logEntity = new EWaybillOrderLogEntity();
		logEntity.setOrderNO(ewaybill.getOrderNO());
		logEntity.setWaybillNO(ewaybill.getWaybillNO());
		logEntity.setOperateResult(ewaybill.getOperateResult());
		logEntity.setReceiveCustomerID(ewaybill.getReceiveCustomerID());
		logEntity.setReceiveCustomerCode(ewaybill.getReceiveCustomerCode());
		logEntity.setReceiveCustomerName(ewaybill.getReceiveCustomerName());
		logEntity.setReceiveCustomerMobilephone(ewaybill.getReceiveCustomerMobilephone());
		logEntity.setReceiveCustomerPhone(ewaybill.getReceiveCustomerPhone());
		logEntity.setReceiveCustomerContact(ewaybill.getReceiveCustomerContact());
		logEntity.setReceiveCustomerNationCode(ewaybill.getReceiveCustomerNationCode());
		logEntity.setReceiveCustomerProvCode(ewaybill.getReceiveCustomerProvCode());
		logEntity.setReceiveCustomerCityCode(ewaybill.getReceiveCustomerCityCode());
		logEntity.setReceiveCustomerDistCode(ewaybill.getReceiveCustomerDistCode());
		logEntity.setReceiveCustomerAddress(ewaybill.getReceiveCustomerAddress());
		logEntity.setChannelCustID(ewaybill.getChannelCustID());
		logEntity.setGoodsSize(ewaybill.getGoodsSize());
		logEntity.setReturnBillType(ewaybill.getReturnBillType());
		logEntity.setJobID(ewaybill.getJobID());
		logEntity.setDeliveryCustomerContact(ewaybill.getDeliveryCustomerContact());
		logEntity.setFailReason(ewaybill.getFailReason());
		//插入
		ewaybillOrderLogEntityDao.addEWaybillOrderLogEntity(logEntity);
	}
	
	
	/**
	 * 获取待激活电子运单有星标记,对外提供用于判断该标签是否支持自动分拣
	 * @author:foss-136334-BaiLei
	 * @date 2014-09-16
	 * @see LabelPrintInfoService getFreightRoute()
	 */
	public String getExpressIsStarFlag(WaybillPendingEntity waybillPendingEntity){
		String isStarFlag = WaybillConstants.NO;
					
		if(StringUtils.isEmpty(waybillPendingEntity.getProductCode())){
			throw new WaybillValidateException(WaybillValidateException.PRODUCT_CODE_NULL);
		}
			
		// 到达部门 ==到达营业部
		String lastLoadOrgCode = waybillPendingEntity.getCustomerPickupOrgCode();
		
		//根据到达部门判断是否追加星符号
		if(asteriskSalesDeptService.queryAsteriskDeptByCode(lastLoadOrgCode)){
			isStarFlag = WaybillConstants.YES;
		}
		
		// 收货部门 ==出发营业部
		String startOrg = waybillPendingEntity.getCreateOrgCode();

		/**
		 * 判断是否是集中开单
		 */
		if (StringUtils.isNotEmpty(waybillPendingEntity.getPickupCentralized()) && 
				StringUtils.equals(FossConstants.YES, waybillPendingEntity.getPickupCentralized())) {
			//查询组织信息
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.
					queryOrgAdministrativeInfoByCode(waybillPendingEntity.getCreateOrgCode(),waybillPendingEntity.getBillTime());
			//这里相当于集中开单组最多走三次查询，先查历史记录，如果没有，在找实时的数据，实在找不到，再走中转的走货路径
			if(FossConstants.YES.equals(orgAdministrativeInfoEntity.getBillingGroup())){
				//历史查找
				SaleDepartmentEntity saleDepartment = waybillManagerService.
						queryPickupCentralizedDeptCodeAndBillTime(waybillPendingEntity.getCreateOrgCode(),waybillPendingEntity.getBillTime());
				if (saleDepartment != null) {
					startOrg = saleDepartment.getCode();
				}else{
					//再实时的查找当前数据
					String transCenterCode = waybillManagerService.queryTransCenterByBillingGroupCode(waybillPendingEntity.getCreateOrgCode());
					SaleDepartmentEntity deliverDepartment = orgAdministrativeInfoComplexService.
							queryStationLeaveOrgByOutfieldCode(transCenterCode);
					if(deliverDepartment == null){
						//如果再找不到，则抛出异常，直接调用中转的走货路径：GUI和Web都有相应的扑捉Exception 方法
						throw new WaybillLabelPrintException("根据集中开单组"+
						orgAdministrativeInfoEntity.getName()+"("+orgAdministrativeInfoEntity.getCode()+")没有找到对应的外场出发驻地部门!");
					}
					startOrg = deliverDepartment.getCode();
				}
			}
		}
		

		// 根据出发到达 营业部得到 走货路径 List A-B B-C C-D 包括始发营业部 和到达营业部门
		List<FreightRouteLineDto> freightRouteLineList = freightRouteService.queryFreightRouteBySourceTarget
				(startOrg, lastLoadOrgCode, waybillPendingEntity.getProductCode(), waybillPendingEntity.getBillTime());

		// 得到走货路径去掉始发营业部 A-B-C-D-d
		List<String> addressInfoList = new ArrayList<String>();
		// 最终外场org编码
		if (CollectionUtils.isNotEmpty(freightRouteLineList)) {
			// 最终外场编码 == 最终外场编码等于 走货路径的最后一条的 出发部门
			addressInfoList.add(freightRouteLineList.get(0).getTargetCode());
			// 空运、偏线查货区
			if (ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(waybillPendingEntity.getProductCode()) ||
					ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(waybillPendingEntity.getProductCode())) {
				for (int i = 1; i < freightRouteLineList.size(); i++) {
					addressInfoList.add(freightRouteLineList.get(i).getTargetCode());
					LOGGER.info("===除出发营业部以外走货路径===" + freightRouteLineList.get(i).getTargetCode());
					// 根据走货路径获得货区号
					if (!(i == freightRouteLineList.size() - 1)){
						//	是否追加★符号，不是最后一个记录且还没有打过标记
				    	if(!StringUtils.equalsIgnoreCase(isStarFlag,FossConstants.YES)){
				    		isStarFlag = isPrintBseAsteriskGoodsArea(freightRouteLineList.get(i),waybillPendingEntity);
				    	}
					}
				}
			} else {
				// 专线查货区--------快递库区编码查询3357
				for (int i = 1; i < freightRouteLineList.size(); i++) {
					addressInfoList.add(freightRouteLineList.get(i).getTargetCode());
					LOGGER.info("===除出发营业部以外走货路径===" + freightRouteLineList.get(i).getTargetCode());
					//是否追加★符号,不看最后一个且星标记没有被标记
					if(!StringUtils.equalsIgnoreCase(isStarFlag,FossConstants.YES)){
						isStarFlag = isPrintBseAsteriskGoodsArea(freightRouteLineList.get(i),waybillPendingEntity);
					}
				}
			}
		}
		return isStarFlag;
	}
		
	
	/**
	 * 
	 * <p>
	 * 电子运单综合走货路径快递标签是否打印"★"符号
	 * </p> 
	 * @author DP-Foss-BaiLei
	 * @date 2013-9-17 下午4:45:08
	 * @see LabeiPrintInfoService
	 */
	private String isPrintBseAsteriskGoodsArea(FreightRouteLineDto currentRouteLineDto,WaybillPendingEntity waybillPendingEntity){
		String isStarFlag = WaybillConstants.NO;
	
		//如果是经济快递运单,判断是否需要打星号标记
		if(waybillExpressService.onlineDetermineIsExpressByProductCode(waybillPendingEntity.getProductCode(), waybillPendingEntity.getBillTime())){
			ExpressPrintStarEntity expressPrintStarEntity = null;
			expressPrintStarEntity = expressPrintStarService.queryExpressPrintStarByArriveRegionCode
					(currentRouteLineDto.getSourceCode(), currentRouteLineDto.getTargetCode());
			if(null!=expressPrintStarEntity){
			    if(StringUtils.equalsIgnoreCase(expressPrintStarEntity.getAsteriskCode(), DictionaryValueConstants.ASTERISK_TYPE_LINE1)){
			    	isStarFlag = WaybillConstants.YES;
			    	return isStarFlag;
			    }
			}		
		}
		return isStarFlag;
	}
	
	/**
	 * PDA退回电子运单数据，需要进行相关数据的删除
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-9-19 10:42:15
	 */
	@Transactional
	public ResultDto returnEWaybillByPda(EWaybillConditionDto eWaybillConditionDto) {
		ResultDto result = new ResultDto();
		if(eWaybillConditionDto == null){
			LOGGER.info("传入的参数不能为空");
			result.setCode(ResultDto.FAIL);
			result.setMsg("传入的参数不能为空");
			return result;
		}
		// DZYD-74  添加日志信息
		LOGGER.info("dzyd_return start : "+ ReflectionToStringBuilder.toString(eWaybillConditionDto));
		StringBuffer sb = new StringBuffer();
		if(eWaybillConditionDto.getOrderNoList()==null ||eWaybillConditionDto.getOrderNoList().size()==0){
			sb.append("订单集合");
		}
		if(eWaybillConditionDto.getReturnReason()==null && !"".equals(eWaybillConditionDto.getReturnReason())){
			sb.append("退回原因");
		}
		if(StringUtils.isNotBlank(sb.toString())){
			LOGGER.info(sb.toString()+"不能为空");
			result.setCode(ResultDto.FAIL);
			result.setMsg(sb.toString()+"不能为空");
			return result;
		}
		
		List<String> orderNoList = eWaybillConditionDto.getOrderNoList();
		if(orderNoList==null || orderNoList.size()<0){
			return null;
		}
		
		if(StringUtils.isBlank(eWaybillConditionDto.getReturnReason())){
			return null;
		}
		//FOSS订单状态
		String orderStatus = null;
		String crmStatus = null;
		//FOSS-CRM订单状态
		String returnReason = null;
		if(StringUtils.isNotBlank(eWaybillConditionDto.getReturnReason())){
			returnReason = eWaybillConditionDto.getReturnReason();
			if(DispatchOrderStatusConstants.REJECT_CONTACT_NO_CUSTOMER.equals(returnReason)
					||DispatchOrderStatusConstants.REJECT_PICKUP_ADDRESS_ERROR.equals(returnReason)
	                ||DispatchOrderStatusConstants.REJECT_BEYOND_SERVICE_SCOPE.equals(returnReason)
					|| DispatchOrderStatusConstants.REJECT_REFUSE_CONTRABAND.equals(returnReason)){
				orderStatus = DispatchOrderStatusConstants.STATUS_RETURN;
				crmStatus = DispatchOrderStatusConstants.STATUS_RETURN;
			}else if(DispatchOrderStatusConstants.REJECT_TFR_NONE_ORDER_CREATE_ORDER.equals(returnReason)){
				orderStatus = DispatchOrderStatusConstants.STATUS_RETURN;
				crmStatus = DispatchOrderStatusConstants.UNACTIVE_EWAYBILL_GENERATE_FAILURE;
			}else {
				orderStatus = DispatchOrderStatusConstants.STATUS_AGAIN_PICKUP;
			}
		}
		
		for(String orderNo : orderNoList){
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("orderNo", orderNo);
			maps.put("active", FossConstants.NO);
			maps.put("pendingType", WaybillConstants.WAYBILL_STATUS_EWAYBILL_PENDING);
			maps.put("waybillType", WaybillConstants.EWAYBILL);
			WaybillPendingEntity waybillPendingEntity = waybillPendingService.queryUnActiveEWaybillPendingByWaybilllNo(maps);
			
			//增加判空条件
			if(waybillPendingEntity == null){
				LOGGER.info("没有查询到有效的待补录运单数据");
				if("".equals(sb.toString())){
					sb.append("没有查询到有效的待补录运单数据.订单号为:"+orderNo);
				}else{
					sb.append(","+orderNo);
				}
				continue;
			}

			//揽货失败
			if(DispatchOrderStatusConstants.STATUS_PICKUP_FAILURE.equals(orderStatus)
					||	DispatchOrderStatusConstants.STATUS_RETURN.equals(orderStatus)){
				EWaybillOrderEntity eWaybillOrderEntity = ewaybillOrderEntityDao.queryEWaybillByOrderNo(orderNo);
				eWaybillOrderEntity.setFailReason(orderNo+"该订单号已通过PDA拒绝，拒绝原因为："+eWaybillConditionDto.getReturnReason());
				//添加激活失败记录
				eWaybillOrderEntity.setOperateResult(DispatchOrderStatusConstants.STATUS_RETURN);
				eWaybillOrderEntity.setFailReason("PDA return ewaybillOrder");
				addEWaybillOrderLog(eWaybillOrderEntity);
				//更新crm状态
				updateCrmStatus(eWaybillConditionDto,waybillPendingEntity,crmStatus);
				//更新订单状态与退回原因
				updateOrderStatus(orderNo,returnReason,orderStatus);
				//删除相关数据
				afterReject(waybillPendingEntity);
			}else if(DispatchOrderStatusConstants.STATUS_AGAIN_PICKUP.equals(orderStatus)){
				//更新订单状态与退回原因
				updateOrderStatus(orderNo,returnReason,orderStatus);
			}
		}
		
		if("".equals(sb.toString())){
			result.setCode(ResultDto.SUCCESS);
			result.setMsg("操作成功");
		}else{
			result.setCode(ResultDto.FAIL);
			result.setMsg(sb.toString());
		}
		
		return result;
	}

	/**
	 * PDA拒绝订单后的相关处理
	 * @param eWaybillConditionDto
	 * @date 2014-10-26 18:41:03
	 * @return
	 */
	@Transactional
	public void afterReject(WaybillPendingEntity waybillPendingEntity){

		if(StringUtils.isNotBlank(waybillPendingEntity.getWaybillNo())){
			waybillPendingService.deleteByWaybillNo(waybillPendingEntity.getWaybillNo());
		}
		
		if(StringUtils.isNotBlank(waybillPendingEntity.getOrderNo())){
			EWaybillOrderEntity eWaybillOrderEntity = ewaybillOrderEntityDao.queryEWaybillByOrderNo(waybillPendingEntity.getOrderNo());
			if(eWaybillOrderEntity!=null){
				ewaybillOrderEntityDao.deleteEWaybillOrderbyOrderNO(waybillPendingEntity.getOrderNo());
			}
		}
		
		//实际承运信息表
		ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(waybillPendingEntity.getWaybillNo());
		if(actualFreightEntity != null && WaybillConstants.WAYBILL_EWAYBILL_TYPE.equals(actualFreightEntity.getWaybillType())){
			actualFreightService.deleteActualFreightByWaybillNo(actualFreightEntity.getWaybillNo());
		}
		
		//按照更改单的逻辑走，封装查询参数
		LastWaybillRfcQueryDto queryDto = new LastWaybillRfcQueryDto();
		queryDto.setActive(FossConstants.NO);
		queryDto.setWaybillNo(waybillPendingEntity.getWaybillNo());
		queryDto.setCreateTime(waybillPendingEntity.getCreateTime());
		queryDto.setModifyTime(waybillPendingEntity.getModifyTime());
		
		//作废计费明细
		List<WaybillChargeDtlEntity> waybillDtlList = waybillChargeDtlService.queryNewChargeDtlEntityByNO(queryDto);
		//进行数据的作废
		if(CollectionUtils.isNotEmpty(waybillDtlList)){
			for(WaybillChargeDtlEntity entity : waybillDtlList){
				waybillChargeDtlService.deleteWaybillChargeDtlEntityById(entity.getId());
			}
		}
		
		//作废折扣明细
		List<WaybillDisDtlEntity>  waybillDisList = waybillDisDtlService.queryNewDisDtlEntityByNo(queryDto);
		if(CollectionUtils.isNotEmpty(waybillDisList)){
			for(WaybillDisDtlEntity entity : waybillDisList){
				waybillDisDtlService.deleteWaybillDisDtlEntityById(entity.getId());
			}
		}
		
		//删除带打包装明细
		WoodenRequirementsEntity woodenRequirement = woodenRequirementsService.queryNewWoodenRequirements(queryDto);
		//进行数据的作废
		if(woodenRequirement != null){
			woodenRequirementsService.deleteWoodRequirementsById(woodenRequirement.getId());
		}
		//查询应付款数据
		List<WaybillPaymentEntity> paymentList = waybillPaymentService.queryNewWaybillPaymentEntityByNo(queryDto);
		//进行数据的作废
		if(CollectionUtils.isNotEmpty(paymentList)){
			for(WaybillPaymentEntity payment : paymentList){
				waybillPaymentService.deleteWaybillPaymentEntityById(payment.getId());
			}
		}
		
		//删除运单状态表中的数据，释放运单号
		waybillTransactionService.deleteWaybillTransactionByWaybillNo(waybillPendingEntity.getWaybillNo());
		
	}
	

	/**
	 * 更新订单表状态，并将状态传递给CRM更新
	 * @param eWaybillConditionDto
	 * @param waybillPendingEntity
	 * @param status 状态值
	 */
	private void updateOrderCrmStatus(EWaybillConditionDto eWaybillConditionDto,WaybillPendingEntity waybillPendingEntity,String status){
		//封装参数
		Map<String,Object> condition = new HashMap<String, Object>();
		if(eWaybillConditionDto==null || eWaybillConditionDto.geteWaybillNoList()==null ||eWaybillConditionDto.geteWaybillNoList().size()==0){
			return;
		}
		condition.put("orderNo", waybillPendingEntity.getOrderNo());
		condition.put("waybillNo", waybillPendingEntity.getWaybillNo());
		// 操作人员
		condition.put("oprateUserCode", eWaybillConditionDto.getDriverCode());
		// 操作人员所在部门
		SaleDepartmentEntity saleDepart = querySaleDepartmentByVehicleNo(waybillPendingEntity.getLicensePlateNum());
		if(saleDepart != null && StringUtils.isNotBlank(saleDepart.getCode())){
			OrgAdministrativeInfoEntity orgEntity = 
					orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(saleDepart.getCode());
			if(orgEntity != null &&StringUtils.isNotBlank(orgEntity.getUnifiedCode())){
				condition.put("oprateDeptCode", orgEntity.getUnifiedCode());
			}
		}
		
		EmployeeEntity eentity = employeeService.queryEmployeeByEmpCode(eWaybillConditionDto.getDriverCode());
		if(eentity != null){
			condition.put("employeeEntity", eentity);
		}

		//收货部门编码(电子运单收货的部门实际是创建部门)
		condition.put("receiveOrgCode", waybillPendingEntity.getCreateOrgCode());
		//更新订单表的订单状态
		dispatchOrderEntityDao.updateSatusByOrderNo(waybillPendingEntity.getOrderNo(), status);
		try {
			ResultDto res = crmOrderJMSService.sendModifyOrder(gainCrmModifyInfoDto(condition,DispatchOrderStatusConstants.CRM_ORDER_STATUS_MAPPING.get(status)));
			if("0".equals(res.getCode())){
				LOGGER.error("更新订单状态失败：" + res.getMsg());
				throw new WaybillOrderHandleException(res.getMsg());
			}
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			String msg = e.getMessage();
			if(StringUtils.isEmpty(msg)){
				msg = e.getErrorCode();
			}
			throw new WaybillOrderHandleException(WaybillOrderHandleException.UPDATESTATUS_FAILREASON,new Object[]{messageBundle.getMessage(msg)} );
		}
	}
	
	/**
	 * 根据运单基本信息封装需要更新的对象
	 * 
	 * @author liyongfei
	 * @date 2012-12-27 下午10:02:13
	 */
	private CrmModifyInfoDto gainCrmModifyInfoDto(Map<String,Object> condition,String status) {
		CrmModifyInfoDto dto = new CrmModifyInfoDto();
		// 订单编号
		dto.setOrderNumber(condition.get("orderNo").toString());
		// 运单号码
		if(!(StringUtils.isNotBlank(status) && (DispatchOrderStatusConstants.STATUS_PICKUP_FAILURE.equals(status)
				||DispatchOrderStatusConstants.STATUS_RETURN.equals(status)))){
			dto.setWaybillNumber(condition.get("waybillNo").toString());
		}
		
		EmployeeEntity emp = (EmployeeEntity)condition.get("employeeEntity");
		// 操作人员
		dto.setOprateUserNum(condition.get("oprateUserCode").toString());
		// 操作部门
		dto.setOprateDeptCode(condition.get("oprateDeptCode").toString());
		
		// 货物状态
		dto.setGoodsStatus(status);
		if(condition.get("receiveOrgCode")!=null){
			// 收入部门标杆编码
			OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(condition.get("receiveOrgCode").toString());
			if (null != org) {
				String unifedCode = StringUtil.defaultIfNull(org.getUnifiedCode());
				dto.setEarningDeptStandardCode(unifedCode);
				// 收入部门名称
				dto.setEarningDeptStandardName(StringUtil.defaultIfNull(org.getName()));
			}
		}	
		if (null != emp) {
			// 司机姓名
			dto.setDriverName(emp.getEmpName());
			// 司机手机
			dto.setDriverMobile(emp.getMobilePhone());	
		}

		return dto;
	}
	
	private void updateOrderStatus(String orderNo,String returnReason,String orderStatus){
		DispatchOrderConditionDto dispatchOrderConditionDto = new DispatchOrderConditionDto();
		dispatchOrderConditionDto.setOrderNo(orderNo);
		// 校验订单是否存在
		DispatchOrderEntity orderEntity = dispatchOrderEntityDao.queryOrdersByOrderNo(dispatchOrderConditionDto);
		if(null == orderEntity){
			return;
		}
		DispatchOrderEntity newEntity = new DispatchOrderEntity();
		newEntity.setId(orderEntity.getId());
		newEntity.setOperateNotes(returnReason);
		// 订单状态
		newEntity.setOrderStatus(orderStatus);
		// 订单id
		newEntity.setOrderNo(orderNo);
		// 设置订单发送状态为发送成功
		newEntity.setOrderSendStatus(DispatchOrderStatusConstants.ORDER_SENDSUCCESS);
		dispatchOrderEntityDao.updateByPrimaryKeySelective(newEntity);
	}
	
	private void updateCrmStatus(EWaybillConditionDto eWaybillConditionDto,WaybillPendingEntity waybillPendingEntity,String crmStatus){
		//封装参数
		Map<String,Object> condition = new HashMap<String, Object>();
		if(eWaybillConditionDto==null || eWaybillConditionDto.getOrderNoList()==null ||eWaybillConditionDto.getOrderNoList().size()==0){
			return;
		}
		condition.put("orderNo", waybillPendingEntity.getOrderNo());
		condition.put("waybillNo", waybillPendingEntity.getWaybillNo());
		// 操作人员
		condition.put("oprateUserCode", eWaybillConditionDto.getDriverCode());
		// 操作人员所在部门
		SaleDepartmentEntity saleDepart = querySaleDepartmentByVehicleNo(waybillPendingEntity.getLicensePlateNum());
		if(saleDepart != null && StringUtils.isNotBlank(saleDepart.getCode())){
			OrgAdministrativeInfoEntity orgEntity = 
					orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(saleDepart.getCode());
			if(orgEntity != null &&StringUtils.isNotBlank(orgEntity.getUnifiedCode())){
				condition.put("oprateDeptCode", orgEntity.getUnifiedCode());
			}
		}
		
		EmployeeEntity eentity = employeeService.queryEmployeeByEmpCode(eWaybillConditionDto.getDriverCode());
		if(eentity!=null){
			condition.put("employeeEntity", eentity);
		}
		//收货部门编码
		condition.put("receiveOrgCode", waybillPendingEntity.getCreateOrgCode());
		try {
			ResultDto res = crmOrderJMSService.sendModifyOrder(gainCrmModifyInfoDto(condition,DispatchOrderStatusConstants.CRM_ORDER_STATUS_MAPPING.get(crmStatus)));
			if("0".equals(res.getCode())){
				LOGGER.error("更新订单状态失败：" + res.getMsg());
				throw new WaybillOrderHandleException(res.getMsg());
			}
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			String msg = e.getMessage();
			if(StringUtils.isEmpty(msg)){
				msg = e.getErrorCode();
			}
			throw new WaybillOrderHandleException(WaybillOrderHandleException.UPDATESTATUS_FAILREASON,new Object[]{messageBundle.getMessage(msg)} );
		}
	}
	
	@Override
	public int batchHandleEWaybillThread(String jobId) {
		int resultNum = 0;
		try {
			//根据JobId查询待处理信息
			List<EWaybillOrderEntity> eWaybillOrderEntityList = ewaybillOrderEntityDao.queryGenerateUnActiveEWaybillByJobID(jobId);
			if (CollectionUtils.isNotEmpty(eWaybillOrderEntityList)) {
				batchGenearte(eWaybillOrderEntityList);
				resultNum = eWaybillOrderEntityList.size();
			}
		} catch (Exception e) {
			LOGGER.info("线程处理电子运单异常") ;
		}
		return resultNum;
	}
	
	/**
	 * 更新订单信息
	 * @deprecated
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-6-9 16:18:18
	 */
	@Override
	public String singleUpdateEcommerceOrder(UpdateEOrderRequest updateEOrderRequest) {
		//如果对应的数据为空，则直接返回错误信息
		if(updateEOrderRequest == null){
			throw new WaybillValidateException(WaybillValidateException.REQUEST_DATA_IS_NULL);
		}
		//订单号数据为空,也没法进行后续操作
		if(StringUtils.isEmpty(updateEOrderRequest.getOrderNumber())){
			throw new WaybillValidateException(WaybillValidateException.DISPATCH_ORDER_NULL);
		}
		LOGGER.info("传入的订单更改详情为:"+ReflectionToStringBuilder.toString(updateEOrderRequest));
		//查询订单详情
		DispatchOrderEntity dispatchOrderEntity = dispatchOrderEntityDao.queryAllInfoByOrderNo(updateEOrderRequest.getOrderNumber());
		if(dispatchOrderEntity == null){
			throw new WaybillValidateException(WaybillValidateException.DISPATCH_ORDER_NULL);
		}
		//如果订单数据是否已经开单，已开单的数据无法进行删除
		if(DispatchOrderStatusConstants.STATUS_WAYBILL.equals(dispatchOrderEntity.getOrderStatus())){
			throw new WaybillValidateException(WaybillValidateException.WAYBILL_IMPORT);
		}
		
		//查询待补录 运单数据
		WaybillPendingDto waybillPendingDto = new WaybillPendingDto();
		waybillPendingDto.setOrderNo(updateEOrderRequest.getOrderNumber());
		waybillPendingDto.setActive(FossConstants.YES);
		WaybillPendingEntity waybillPendingEntity = waybillPendingService.queryBasicWaybillPendingData(waybillPendingDto);
		if(waybillPendingEntity != null){
			throw new WaybillValidateException(WaybillValidateException.WAYBILL_IMPORT);
		}
		
		//查询运单表中的数据是否存在
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillByOrderNo(updateEOrderRequest.getOrderNumber());
		//查询电子运单订单表数据
		//如果数据存在且对应的运单状态为已经补录则，则不能进行数据的删除
		if(waybillEntity != null){
			throw new WaybillValidateException(WaybillValidateException.WAYBILL_IMPORT);
		}
		String waybillNo = updateEOrderRequest.getWaybillNumber();
		//电子运单订单表数据的封装
		EWaybillOrderEntity eWaybillOrderEntity = ewaybillOrderEntityDao.queryEWaybillByOrderNo(updateEOrderRequest.getOrderNumber());
		//判定是否EWAybillOrderEntity存在数据
		if(eWaybillOrderEntity == null){
			eWaybillOrderEntity = new EWaybillOrderEntity();
			eWaybillOrderEntity.setID(UUIDUtils.getUUID());
			eWaybillOrderEntity.setCreateTime(new Date());
			eWaybillOrderEntity.setModifyTime(new Date());
		}
		//查询待激活运单数据
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderNo", updateEOrderRequest.getOrderNumber());
		params.put("pendingType", WaybillConstants.WAYBILL_STATUS_EWAYBILL_PENDING);
		params.put("active", FossConstants.NO);
		params.put("waybillType", WaybillConstants.EWAYBILL);
		waybillPendingEntity = waybillPendingService.queryUnActiveEWaybillPendingByWaybilllNo(params);
		//封装对应的电子运单数据
		setBasicEwaybillOrderEntity(updateEOrderRequest, eWaybillOrderEntity);
		//获取FOSS对应的运单号数据
		String fossWaybillNo = dispatchOrderEntity.getWaybillNo();
		if(StringUtils.isEmpty(fossWaybillNo)){
			if(eWaybillOrderEntity != null && StringUtils.isNotEmpty(eWaybillOrderEntity.getWaybillNO())){
				fossWaybillNo = eWaybillOrderEntity.getWaybillNO();
			}else if(waybillPendingEntity != null){
				fossWaybillNo = waybillPendingEntity.getWaybillNo();
			}
		}
		//校验订单传入的运单号数据与FOSS的运单号数据是否一致
		if(StringUtils.isNotEmpty(fossWaybillNo) && !fossWaybillNo.equals(waybillNo)){
			throw new WaybillValidateException(WaybillValidateException.REQUEST_DATA_OF_WAYBILL_IS_NOT_SAME);
		}
		//实际承运信息数据的校验
		ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(waybillNo);
		//如果实际承运信息表数据存在，且是有效的运单或者激活失败的数据是不能进行激活的
		if(actualFreightEntity != null && (WaybillConstants.EFFECTIVE.equals(actualFreightEntity.getStatus())
				|| WaybillConstants.EWAYBILL_ACTIVE_FAIL.equals(actualFreightEntity.getStatus()))){
			throw new WaybillValidateException(WaybillValidateException.WAYBILL_EXSITS);
		}
		try {
			//生成WaybillDto
			WaybillDto waybillDto = generatePreEWaybill(dispatchOrderEntity, eWaybillOrderEntity);
			//获取WaybillEntity
			waybillEntity = waybillDto.getWaybillEntity();
			// 设置统一的创建人、创建时间、更新人、更新时间
			WaybillSystemDto systemDto = getSystemDtoByWaybillDto(waybillDto);	
			//校验
			validateEWaybill(waybillDto);
			if(StringUtils.isEmpty(waybillNo)){
				if(eWaybillOrderEntity != null && StringUtils.isNotEmpty(eWaybillOrderEntity.getWaybillNO())){
					waybillNo = eWaybillOrderEntity.getWaybillNO();
				}else if(waybillEntity != null && StringUtils.isNotEmpty(waybillEntity.getWaybillNo())){
					waybillNo = waybillEntity.getWaybillNo();
				}else if(waybillPendingEntity != null && StringUtils.isNotEmpty(waybillPendingEntity.getWaybillNo())){
					waybillNo = waybillPendingEntity.getWaybillNo();
				}else{
					//不是待改接并且外部渠道没有传入运单号的自动生成运单号
					waybillNo = generateEWaybillNO();
				}
			}
			waybillEntity.setWaybillNo(waybillNo);
			//转化为待补录WaybillPendingEntity
			waybillPendingEntity = convertWaybillEntityToPending(waybillEntity);
			//插入待补录表
			if(waybillPendingEntity != null){
				// 设置创建时间、创建人、修改时间、创建人工号、修改人工号、开单时间
				waybillPendingEntity.setCreateTime(systemDto.getCreateTime());
				waybillPendingEntity.setModifyTime(systemDto.getModifyTime());
				waybillPendingEntity.setBillTime(systemDto.getBillTime());
				waybillPendingEntity.setCreateUser(systemDto.getCurrentUser().getEmpCode());
				waybillPendingEntity.setModifyUserCode(systemDto.getCurrentUser().getEmpCode()); 
				// 运输类型
				waybillPendingEntity.setTransportType(waybillExpressService.getTransTypeByLevel3ProductCode(waybillPendingEntity.getProductCode()));
				// 设置是否有效
				waybillPendingEntity.setActive(FossConstants.NO);
				//添加地址备注
				waybillPendingEntity.setDeliveryCustomerAddressNote(dispatchOrderEntity.getDeliveryCustomerAddressNote());
				waybillPendingEntity.setReceiveCustomerAddressNote(dispatchOrderEntity.getReceiveCustomerAddressNote());
				try {
					waybillPendingDao.updateByWaybillNo(waybillPendingEntity);
				} catch (BusinessException e) {
					throw new WaybillPendingSaveException(WaybillPendingSaveException.SAVEPENDINGBILL_FAIL, new Object[]{messageBundle.getMessage(e.getMessage())});
				}
			}
			//待改接的修改数据表数据
			//修改实际承运信息表
			if(waybillDto.getActualFreightEntity() != null){
				ActualFreightEntity actualFreightEntityUpdate = waybillDto.getActualFreightEntity();
				//设置运单号
				actualFreightEntityUpdate.setWaybillNo(waybillNo);
				//设置记录创建时间
				actualFreightEntityUpdate.setCreateTime(systemDto.getBillTime());
				//设置地址备注
				actualFreightEntityUpdate.setDeliveryCustomerAddressNote(dispatchOrderEntity.getDeliveryCustomerAddressNote());
				actualFreightEntityUpdate.setReceiveCustomerAddressNote(dispatchOrderEntity.getReceiveCustomerAddressNote());
				actualFreightService.updateByWaybillNoSelective(actualFreightEntityUpdate);
			}
			
			// 费用信息
			List<WaybillChargeDtlEntity> chargeDetail = waybillDto.getWaybillChargeDtlEntity();
			if (CollectionUtils.isNotEmpty(chargeDetail)) {
				List<WaybillChargeDtlEntity> chargeDetailTemp = new ArrayList<WaybillChargeDtlEntity>();
				for(WaybillChargeDtlEntity chargeDtl : chargeDetail){
					chargeDtl.setWaybillNo(waybillNo);
					//删除费用信息
					waybillChargeDtlService.deleteWaybillChargeDtlEntityByWaybillNo(waybillNo);
					chargeDetailTemp.add(chargeDtl);
				}
				//插入费用表
				waybillChargeDtlService.appendWaybillChargeDtlEntityBatchAfterChange(chargeDetailTemp, systemDto);
			}
			
			// 折扣信息
			List<WaybillDisDtlEntity> discoutDetail = waybillDto.getWaybillDisDtlEntity();
			if (CollectionUtils.isNotEmpty(discoutDetail)) {
				List<WaybillDisDtlEntity> discoutDetailTemp = new ArrayList<WaybillDisDtlEntity>();
				for(WaybillDisDtlEntity disDtl : discoutDetail){
					disDtl.setWaybillNo(waybillNo);
					//删除折扣明细表
					waybillDisDtlService.deleteWaybillDisDtlEntityByWaybillNo(waybillNo);
					discoutDetailTemp.add(disDtl);
				}
				//插入折扣明细表
				waybillDisDtlService.appendWaybillDisDtlEntityBatchAfterChange(discoutDetailTemp, systemDto);
			}
			
			// 付款信息
			List<WaybillPaymentEntity> paymentList = waybillDto.getWaybillPaymentEntity();
			if (CollectionUtils.isNotEmpty(paymentList)) {
				List<WaybillPaymentEntity> paymentListTemp = new ArrayList<WaybillPaymentEntity>();
				for(WaybillPaymentEntity payMent : paymentList){
					payMent.setWaybillNo(waybillNo);
					waybillPaymentService.deleteWaybillPaymentEntityByWaybillNo(waybillNo);
					paymentListTemp.add(payMent);
				}
				//插入付款信息表
				waybillPaymentService.appendWaybillPaymentEntityBatchAfterChange(paymentListTemp, systemDto);
			}
			//运单号插入电子运单订单表
			eWaybillOrderEntity.setJobID(FossConstants.YES);//不让待激活再次执行
			ewaybillOrderEntityDao.updateEWaybillOrderEntitySelective(eWaybillOrderEntity);
			
			ModifyEwaybillOrderLogEntity logEntity = new ModifyEwaybillOrderLogEntity();
			modifEwaybillOrderRecordDao.insertSelective(logEntity);
		} catch (Exception e) {
			LOGGER.info("执行订单数据更新出现异常:");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 设置电子运单订单数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-6-12 20:51:16
	 * @param record
	 */
	private EWaybillOrderEntity setBasicEwaybillOrderEntity(UpdateEOrderRequest updateEOrderRequest, EWaybillOrderEntity eWaybillOrderEntity) {
		//设置修改时间为当前时间
		eWaybillOrderEntity.setModifyTime(new Date());
		//订单号
		if(StringUtils.isNotEmpty(updateEOrderRequest.getOrderNumber())){
			eWaybillOrderEntity.setOrderNO(updateEOrderRequest.getOrderNumber());
		}
		//运单号
		if(StringUtils.isNotEmpty(updateEOrderRequest.getWaybillNumber())){
			eWaybillOrderEntity.setOrderNO(updateEOrderRequest.getWaybillNumber());
		}
		//收货客户ID
		if(StringUtils.isNotEmpty(updateEOrderRequest.getReceiverCustId())){
			eWaybillOrderEntity.setReceiveCustomerID(updateEOrderRequest.getReceiverCustId());
		}
		//收货客户编码
		if(StringUtils.isNotEmpty(updateEOrderRequest.getReceiverCustNumber())){
			eWaybillOrderEntity.setReceiveCustomerCode(updateEOrderRequest.getReceiverCustNumber());
		}
		//收货客户名称
		if(StringUtils.isNotEmpty(updateEOrderRequest.getReceiverCustName())){
			eWaybillOrderEntity.setReceiveCustomerName(updateEOrderRequest.getReceiverCustName());
		}
		if(StringUtils.isNotEmpty(updateEOrderRequest.getReceiverCustName())){
			eWaybillOrderEntity.setReceiveCustomerContact(updateEOrderRequest.getReceiverCustName());
		}
		//收货人客户编码
		if(StringUtils.isNotEmpty(updateEOrderRequest.getReceiverCustMobile())){
			eWaybillOrderEntity.setReceiveCustomerMobilephone(updateEOrderRequest.getReceiverCustMobile());
		}
		//收货人固定电话
		if(StringUtils.isNotEmpty(updateEOrderRequest.getReceiverCustPhone())){
			eWaybillOrderEntity.setReceiveCustomerPhone(updateEOrderRequest.getReceiverCustPhone());
		}
		//联系人ID
		if(StringUtils.isNotEmpty(updateEOrderRequest.getContactManId())){
			eWaybillOrderEntity.setDeliveryCustomerContactId(updateEOrderRequest.getContactManId());
		}
		//收货人所在的国家
		eWaybillOrderEntity.setReceiveCustomerNationCode(null);
		//收货人省份编码
		if(StringUtils.isNotEmpty(updateEOrderRequest.getReceiveProvinceCode())){
			eWaybillOrderEntity.setReceiveCustomerProvCode(updateEOrderRequest.getReceiveProvinceCode());
		}
		//收货人城市编码
		if(StringUtils.isNotEmpty(updateEOrderRequest.getReceiveCityCode())){
			eWaybillOrderEntity.setReceiveCustomerCityCode(updateEOrderRequest.getReceiveCityCode());
		}
		//收货人区县编码
		if(StringUtils.isNotEmpty(updateEOrderRequest.getReceiveCountyCode())){
			eWaybillOrderEntity.setReceiveCustomerDistCode(updateEOrderRequest.getReceiveCountyCode());
		}
		//收货人地址
		if(StringUtils.isNotEmpty(updateEOrderRequest.getReceiverCustAddress())){
			eWaybillOrderEntity.setReceiveCustomerAddress(updateEOrderRequest.getReceiverCustAddress());
		}
		//联系人名称
		if(StringUtils.isNotEmpty(updateEOrderRequest.getContactName())){
			eWaybillOrderEntity.setDeliveryCustomerContact(updateEOrderRequest.getContactName());
		}
		//渠道客户编码
		if(StringUtils.isNotEmpty(updateEOrderRequest.getChannelCustId())){
			eWaybillOrderEntity.setChannelCustID(updateEOrderRequest.getChannelCustId());
		}
		// 签收单
		if(StringUtils.isNotEmpty(updateEOrderRequest.getReturnBillType())){
			for (CrmReturnBillTypeEnum crm : CrmReturnBillTypeEnum.values()) {
				// 订单返单方式
				if (crm.getCrmCode().equals(updateEOrderRequest.getReturnBillType())) {
					eWaybillOrderEntity.setReturnBillType(crm.getFossCode());
				}
			}
		}else{
			eWaybillOrderEntity.setReturnBillType(WaybillConstants.NOT_RETURN_BILL);
		}
		//设置为未进行后台线程的跑记录
		eWaybillOrderEntity.setJobID(WaybillConstants.YES);
		//设置为无异常
		eWaybillOrderEntity.setFailReason(FossConstants.NO);
		return eWaybillOrderEntity;
	}
	
	/**
	 * 删除订单信息
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-6-9 16:18:18
	 */
	@Override
	public void singleDeleteEcommerceOrder(UpdateEOrderRequest updateEOrderRequest) {
		//如果对应的数据为空，则直接返回错误信息
		if(updateEOrderRequest == null){
			throw new WaybillValidateException(WaybillValidateException.REQUEST_DATA_IS_NULL);
		}
		//订单号数据为空,也没法进行后续操作
		if(StringUtils.isEmpty(updateEOrderRequest.getOrderNumber())){
			throw new WaybillValidateException(WaybillValidateException.DISPATCH_ORDER_NULL);
		}
		LOGGER.info("传入的订单更改详情为:"+ReflectionToStringBuilder.toString(updateEOrderRequest));
		//查询订单详情
		DispatchOrderEntity dispatchOrderEntity = dispatchOrderEntityDao.queryAllInfoByOrderNo(updateEOrderRequest.getOrderNumber());
		if(dispatchOrderEntity == null){
			throw new WaybillValidateException(WaybillValidateException.DISPATCH_ORDER_NULL);
		}
		//如果订单数据是否已经开单，已开单的数据无法进行删除
		if(DispatchOrderStatusConstants.STATUS_WAYBILL.equals(dispatchOrderEntity.getOrderStatus())){
			throw new WaybillValidateException(WaybillValidateException.WAYBILL_IMPORT);
		}
		//查询运单表中的数据是否存在
		WaybillEntity waybillEntity = waybillManagerService.queryWaybillByOrderNo(updateEOrderRequest.getOrderNumber());
		//如果数据存在且对应的运单状态为已经补录则，则不能进行数据的删除
		if(waybillEntity != null){
			throw new WaybillValidateException(WaybillValidateException.WAYBILL_IMPORT);
		}

		//查询待激活运单数据
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderNo", updateEOrderRequest.getOrderNumber());
		params.put("pendingType", WaybillConstants.WAYBILL_STATUS_EWAYBILL_PENDING);
		params.put("active", FossConstants.NO);
		params.put("waybillType", WaybillConstants.EWAYBILL);
		WaybillPendingEntity waybillPendingEntity = waybillPendingService.queryUnActiveEWaybillPendingByWaybilllNo(params);
		if(waybillPendingEntity == null){
			//查询待补录 运单数据
			WaybillPendingDto waybillPendingDto = new WaybillPendingDto();
			waybillPendingDto.setOrderNo(updateEOrderRequest.getOrderNumber());
			waybillPendingDto.setActive(FossConstants.YES);
			waybillPendingEntity = waybillPendingService.queryBasicWaybillPendingData(waybillPendingDto);
		}
		
		EWaybillOrderEntity ewaybillOrderEntity = ewaybillOrderEntityDao.queryEWaybillByOrderNo(updateEOrderRequest.getOrderNumber());

		//获取 运单号
		String waybillNo = updateEOrderRequest.getWaybillNumber();

		String fossWaybillNo = dispatchOrderEntity.getWaybillNo();
		if(StringUtils.isEmpty(fossWaybillNo)){
			if(ewaybillOrderEntity != null && StringUtils.isNotEmpty(ewaybillOrderEntity.getWaybillNO())){
				fossWaybillNo = ewaybillOrderEntity.getWaybillNO();
			}else if(waybillPendingEntity != null){
				fossWaybillNo = waybillPendingEntity.getWaybillNo();
			}
		}
		//校验订单传入的运单号数据与FOSS的运单号数据是否一致
		if(StringUtils.isNotEmpty(fossWaybillNo) && !fossWaybillNo.equals(waybillNo)){
			throw new WaybillValidateException(WaybillValidateException.REQUEST_DATA_OF_WAYBILL_IS_NOT_SAME);
		}
		//实际承运信息数据的校验
		ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(waybillNo);
		//如果实际承运信息表数据存在，且是有效的运单或者激活失败的数据是不能进行激活的
		if(actualFreightEntity != null && (WaybillConstants.EFFECTIVE.equals(actualFreightEntity.getStatus())
				|| WaybillConstants.EWAYBILL_ACTIVE_FAIL.equals(actualFreightEntity.getStatus()))){
			throw new WaybillValidateException(WaybillValidateException.WAYBILL_EXSITS);
		}
		//存在订单号的数据的删除
		//删除电子运单订单表
		ewaybillOrderEntityDao.deleteEWaybillOrderbyOrderNO(updateEOrderRequest.getOrderNumber());
		//删除电子运单预处理数据
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderNo", updateEOrderRequest.getOrderNumber());
		preHandEWaybillOrderDao.deletePreEWaybillOrderByParams(param);
		//删除待补录运单表
		waybillPendingDao.deleteWaybillPendingEntityByOrderNo(param);

		//根据订单号删除对应的子母件关系
		//Map<String, Object> maps = new HashMap<String, Object>();
		//maps.put("active", FossConstants.YES);
		//maps.put("orderNo", updateEOrderRequest.getOrderNumber());
		//waybillRelateDetailEntityService.deleteWaybillRelateDetailByParentWaybillNo(maps);
		//如果运单号存在，则进行数据的删除
		if(StringUtils.isNotEmpty(waybillNo)){
			//删除运单完结状态表中的数据
			waybillTransactionService.deleteWaybillTransactionByWaybillNo(waybillNo);
			//删除运单表中的数据
			waybillDao.deleteWaybillByWaybillNo(waybillNo);

			//删除待补录的运单数据
			waybillCHDtlPendingDao.deleteByWaybillNo(waybillNo);
			//增值费用明细
			waybillChargeDtlService.deleteWaybillChargeDtlEntityByWaybillNo(waybillNo);
			//应付明细
			waybillPaymentService.deleteWaybillPaymentEntityByWaybillNo(waybillNo);
			//折扣明细
			waybillDisDtlService.deleteWaybillDisDtlEntityByWaybillNo(waybillNo);
			//删除实际承运信息表
			actualFreightService.deleteActualFreightByWaybillNo(waybillNo);
		}
	}
	
	/**
	 * 修改前后2实体比较，获取更改字段 
	 * @author 219396-daolin
	 * @date 2015-06-09 上午9:49:49 
	 */
	private String getLogContent(String old, String now) {
		StringBuffer logBuffer = new StringBuffer();
		String s1 = old.substring(old.indexOf("[") + 1, old.length() - 1);
		String s2 = now.substring(now.indexOf("[") + 1, now.length() - 1);
		String[] strs1 = s1.split(",");
		String[] strs2 = s2.split(",");
		for (int i = 0; i < strs1.length; i++) {
			if (!strs1[i].equals(strs2[i])) {
				String key = strs1[i].split("=")[0];
				if(!strs2[i].split("=")[1].equals("null")) {
					String value = strs1[i].split("=")[1] + "=>"
							+ strs2[i].split("=")[1];
					logBuffer.append(key + ":[" + value + "]").append("\r\n");
				}
			}
		}
		return StringUtils.isBlank(logBuffer.toString())?null:logBuffer.toString().trim();
	}
	
	/**
	  * @description 子母件选择母单进行激活
	  * @author Foss-270293-Zhangfeng
	  * @date 2015-8-28 15:37:25
	  * @param dispatchOrderEntity
	  * @param eWaybill
	  * @param waybillDto
	  * void
	 */
	private void generateActiveEWaybill(DispatchOrderEntity dispatchOrderEntity, EWaybillOrderEntity eWaybill,WaybillDto waybillDto){
		// 设置统一的创建人、创建时间、更新人、更新时间
		WaybillSystemDto systemDto = getSystemDtoByWaybillDto(waybillDto);	
				
		//获取WaybillEntity
		WaybillEntity waybillEntity = waybillDto.getWaybillEntity();
		List<WaybillRelateDetailEntity> waybillRelateDetailList=waybillDto.getWaybillRelateDetailEntityList();
		String parentWaybillNo=dispatchOrderEntity.getWaybillNo();
		//校验
		//validateEWaybill(waybillDto);
		//如果该订单在actualFreight表存在，说明是待改接，若运单号存在，则不重复生成运单号
		String waybillNo = null;
		String orderStatus =null;
		ActualFreightEntity actualFreight=actualFreightService.queryByWaybillNo(dispatchOrderEntity.getWaybillNo());
		//如果是待改接，则不重新生成运单号，否则则需要自动生成运单号
		if (actualFreight != null) {
			waybillNo = actualFreight.getWaybillNo();
			orderStatus = DispatchOrderStatusConstants.STATUS_AGAIN_PICKUP;
		}
		if(StringUtils.isEmpty(waybillNo)){
			if(StringUtils.isNotBlank(dispatchOrderEntity.getWaybillNo())){
				//外部渠道传入了运单号则直接用该运单号
				waybillNo = dispatchOrderEntity.getWaybillNo();
			}else{
				//不是待改接并且外部渠道没有传入运单号的自动生成运单号
				waybillNo = generateEWaybillNO();
			}
		}
		Exception eActive = null;
		Date nowDate = new Date();
		try {
			CurrentInfo currentInfo = getEWaybilCurrentInfo(waybillDto.getWaybillEntity());
			if(currentInfo != null){
				waybillDto.setCurrentInfo(currentInfo);
				waybillDto.getWaybillEntity().setCreateUserName(currentInfo.getEmpName());
			}
			//因为是进行数据的封装，故此时的运单号不存在，需要进行重新赋值
			if(waybillDto.getWaybillEntity() != null){
				waybillDto.getWaybillEntity().setWaybillNo(waybillNo);
				waybillDto.getWaybillEntity().setId(UUIDUtils.getUUID());
				waybillDto.getWaybillEntity().setActive(FossConstants.YES);
				waybillDto.getWaybillEntity().setPendingType(WaybillConstants.WAYBILL_STATUS_PDA_PENDING);
				waybillDto.getWaybillEntity().setCreateTime(nowDate);
				waybillDto.getWaybillEntity().setModifyTime(nowDate);
			}
			//实际承运信息表的Modify时间在Dao层有设置，在这里设置无用
			if(waybillDto.getActualFreightEntity() != null){
				waybillDto.getActualFreightEntity().setWaybillNo(waybillNo);
			}
			if(waybillDto.getWaybillExpressEntity() != null){
				waybillDto.getWaybillExpressEntity().setWaybillNo(waybillNo);
				waybillDto.getWaybillExpressEntity().setWaybillId(waybillDto.getWaybillEntity().getId());
				//增加是否可做返单标识
				waybillDto.getWaybillExpressEntity().setCanReturnCargo(WaybillConstants.YES);
				waybillDto.getWaybillExpressEntity().setCreateTime(nowDate);
			}
			//因为主要是按照PDA提交运单的方式进行运单数据的提价，需要把oldWaybillNo弄进去
			waybillDto.setOldWaybillNo(waybillNo);
			
			//校验快递员信息和重量体积比
			validateEWaybill(waybillDto);
			//件数重新设置一下
			// 计算完需要重新设置母单的重量和体积、数量
			for (WaybillRelateDetailEntity waybillRelateDetailEntity : waybillRelateDetailList) {
				if (waybillNo.equals(waybillRelateDetailEntity.getWaybillNo())) {
					waybillEntity.setGoodsWeightTotal(waybillRelateDetailEntity.getWeight());
					waybillEntity.setGoodsVolumeTotal(waybillRelateDetailEntity.getVolume());
					waybillEntity.setGoodsQtyTotal(1);
					break;
				}
			}
			//这里提前设置一下数据，尽管waybillMangeService设置了，这里只是为了说明这里的时间不能随意修改
			if(CollectionUtils.isNotEmpty(waybillDto.getWaybillChargeDtlEntity())){
				for(WaybillChargeDtlEntity chargeDtlEntity : waybillDto.getWaybillChargeDtlEntity()){
					chargeDtlEntity.setActive(FossConstants.YES);
					chargeDtlEntity.setWaybillNo(waybillNo);
					chargeDtlEntity.setCreateTime(nowDate);
					chargeDtlEntity.setModifyTime(nowDate);
				}
			}
			if(CollectionUtils.isNotEmpty(waybillDto.getWaybillDisDtlEntity())){
				for(WaybillDisDtlEntity disDtlEntity : waybillDto.getWaybillDisDtlEntity()){
					disDtlEntity.setActive(FossConstants.YES);
					disDtlEntity.setWaybillNo(waybillNo);
					disDtlEntity.setCreateTime(nowDate);
					disDtlEntity.setModifyTime(nowDate);
				}
			}
			if(CollectionUtils.isNotEmpty(waybillDto.getWaybillPaymentEntity())){
				for(WaybillPaymentEntity waybillPayment : waybillDto.getWaybillPaymentEntity()){
					waybillPayment.setActive(FossConstants.YES);
					waybillPayment.setWaybillNo(waybillNo);
					waybillPayment.setCreateTime(nowDate);
					waybillPayment.setModifyTime(nowDate);
				}
			}
			if(CollectionUtils.isNotEmpty(waybillDto.getLabeledGoodEntities())){
				for(LabeledGoodEntity LabeledGoodEntity : waybillDto.getLabeledGoodEntities()){
					LabeledGoodEntity.setActive(FossConstants.YES);
					LabeledGoodEntity.setWaybillNo(waybillNo);
					LabeledGoodEntity.setCreateTime(nowDate);
					LabeledGoodEntity.setModifyTime(nowDate);
				}
			}
			if(waybillDto.getWoodenRequirementsEntity() != null){
				waybillDto.getWoodenRequirementsEntity().setActive(FossConstants.YES);
				waybillDto.getWoodenRequirementsEntity().setWaybillNo(waybillNo);
				waybillDto.getWoodenRequirementsEntity().setCreateTime(nowDate);
				waybillDto.getWoodenRequirementsEntity().setModifyTime(nowDate);
			}
			//如果不是待改接的，则执行以下操作，待改接的不执行
			if(!DispatchOrderStatusConstants.STATUS_AGAIN_PICKUP.equals(orderStatus)){
				//插入实际承运信息表
				//waybillManagerService.addActualFreightInfo(waybillDto,systemDto,false);
				if(waybillDto.getActualFreightEntity() != null){
					ActualFreightEntity actualFreightEntity = waybillDto.getActualFreightEntity();
					//设置运单号
					actualFreightEntity.setWaybillNo(waybillNo);
					//设置记录创建时间
					actualFreightEntity.setCreateTime(systemDto.getBillTime());
					//设置地址备注
					actualFreightEntity.setDeliveryCustomerAddressNote(dispatchOrderEntity.getDeliveryCustomerAddressNote());
					actualFreightEntity.setReceiveCustomerAddressNote(dispatchOrderEntity.getReceiveCustomerAddressNote());
					// 判断PDA补录是否已在运单表中生成记录（若在运单表生成了记录，则会在标签表、actualFreight表中生成记录）
					//根据运单号查询
					int i=0;
					if(actualFreightService.isExistActualFreight(waybillNo)){
						LOGGER.error("运单号对应的ActualFreight数据已存在！");
						throw new WaybillSubmitException("运单号["+ waybillNo +"],对应的ActualFreight对象已存在!");
					}
					i =actualFreightService.insertWaybillActualFreight(actualFreightEntity);	
					if(i<=0){
						throw new WaybillSubmitException("运单号["+ waybillNo +"],对应的ActualFreight对象已存在!");
					}
				}
				
				//控制运单的唯一性
				if(StringUtils.isNotEmpty(waybillDto.getWaybillEntity().getWaybillNo())){
					WaybillTransactionEntity transacionStat = new WaybillTransactionEntity();
					// 主键
					transacionStat.setId(UUIDUtils.getUUID());
					// 运单号
					transacionStat.setWaybillNo(waybillDto.getWaybillEntity().getWaybillNo());
					// 业务完结情况
					transacionStat.setBusinessOver(ZEROSTR);
					// 财务完结情况
					transacionStat.setFinanceOver(ZEROSTR);
					// 创建日期
					transacionStat.setCreateDate(new Date());
					// 创建人
					transacionStat.setCreateUser(systemDto.getCurrentUser().getEmpCode());
					// 新增业务标识
					waybillTransactionService.addWaybillTransaction(transacionStat);
				}
				
				// 费用信息
				List<WaybillChargeDtlEntity> chargeDetail = waybillDto.getWaybillChargeDtlEntity();
				if (CollectionUtils.isNotEmpty(chargeDetail) && chargeDetail.size() > 0) {
					List<WaybillChargeDtlEntity> chargeDetailTemp = new ArrayList<WaybillChargeDtlEntity>();
					for(WaybillChargeDtlEntity chargeDtl : chargeDetail){
						chargeDtl.setWaybillNo(waybillNo);
						chargeDetailTemp.add(chargeDtl);
					}
					//插入费用表
					waybillChargeDtlService.appendWaybillChargeDtlEntityBatchAfterChange(chargeDetailTemp, systemDto);
				}
				
				// 折扣信息
				List<WaybillDisDtlEntity> discoutDetail = waybillDto.getWaybillDisDtlEntity();
				if (CollectionUtils.isNotEmpty(discoutDetail) && discoutDetail.size() > 0) {
					List<WaybillDisDtlEntity> discoutDetailTemp = new ArrayList<WaybillDisDtlEntity>();
					for(WaybillDisDtlEntity disDtl : discoutDetail){
						disDtl.setWaybillNo(waybillNo);
						discoutDetailTemp.add(disDtl);
					}
					//插入折扣明细表
					waybillDisDtlService.appendWaybillDisDtlEntityBatchAfterChange(discoutDetailTemp, systemDto);
				}
				
				// 付款信息
				List<WaybillPaymentEntity> paymentList = waybillDto.getWaybillPaymentEntity();
				if (CollectionUtils.isNotEmpty(paymentList)) {
					List<WaybillPaymentEntity> paymentListTemp = new ArrayList<WaybillPaymentEntity>();
					for(WaybillPaymentEntity payMent : paymentList){
						payMent.setWaybillNo(waybillNo);
						paymentListTemp.add(payMent);
					}
					//插入付款信息表
					waybillPaymentService.appendWaybillPaymentEntityBatchAfterChange(paymentListTemp, systemDto);
				}
			}else{
				//待改接的修改数据表数据
				//修改实际承运信息表
				if(waybillDto.getActualFreightEntity() != null){
					ActualFreightEntity actualFreightEntity = waybillDto.getActualFreightEntity();
					//设置运单号
					actualFreightEntity.setWaybillNo(waybillNo);
					//设置记录创建时间
					actualFreightEntity.setCreateTime(systemDto.getBillTime());
					//设置地址备注
					actualFreightEntity.setDeliveryCustomerAddressNote(dispatchOrderEntity.getDeliveryCustomerAddressNote());
					actualFreightEntity.setReceiveCustomerAddressNote(dispatchOrderEntity.getReceiveCustomerAddressNote());
					actualFreightService.updateByWaybillNoSelective(actualFreightEntity);
				}
				
				// 费用信息
				List<WaybillChargeDtlEntity> chargeDetail = waybillDto.getWaybillChargeDtlEntity();
				if (CollectionUtils.isNotEmpty(chargeDetail)) {
					List<WaybillChargeDtlEntity> chargeDetailTemp = new ArrayList<WaybillChargeDtlEntity>();
					for(WaybillChargeDtlEntity chargeDtl : chargeDetail){
						chargeDtl.setWaybillNo(waybillNo);
						//删除费用信息
						waybillChargeDtlService.deleteWaybillChargeDtlEntityByWaybillNo(waybillNo);
						chargeDetailTemp.add(chargeDtl);
					}
					//插入费用表
					waybillChargeDtlService.appendWaybillChargeDtlEntityBatchAfterChange(chargeDetailTemp, systemDto);
				}
				
				// 折扣信息
				List<WaybillDisDtlEntity> discoutDetail = waybillDto.getWaybillDisDtlEntity();
				if (CollectionUtils.isNotEmpty(discoutDetail)) {
					List<WaybillDisDtlEntity> discoutDetailTemp = new ArrayList<WaybillDisDtlEntity>();
					for(WaybillDisDtlEntity disDtl : discoutDetail){
						disDtl.setWaybillNo(waybillNo);
						//删除折扣明细表
						waybillDisDtlService.deleteWaybillDisDtlEntityByWaybillNo(waybillNo);
						discoutDetailTemp.add(disDtl);
					}
					//插入折扣明细表
					waybillDisDtlService.appendWaybillDisDtlEntityBatchAfterChange(discoutDetailTemp, systemDto);
				}
				
				// 付款信息
				List<WaybillPaymentEntity> paymentList = waybillDto.getWaybillPaymentEntity();
				if (CollectionUtils.isNotEmpty(paymentList)) {
					List<WaybillPaymentEntity> paymentListTemp = new ArrayList<WaybillPaymentEntity>();
					for(WaybillPaymentEntity payMent : paymentList){
						payMent.setWaybillNo(waybillNo);
						waybillPaymentService.deleteWaybillPaymentEntityByWaybillNo(waybillNo);
						paymentListTemp.add(payMent);
					}
					//插入付款信息表
					waybillPaymentService.appendWaybillPaymentEntityBatchAfterChange(paymentListTemp, systemDto);
				}
			}
			//运单号插入电子运单订单表
			eWaybill.setWaybillNO(waybillNo);
			ewaybillOrderEntityDao.updateEWaybillOrderEntitySelective(eWaybill);
			
			//提交子母件关系数据
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("parentWaybillNo", waybillNo);
			//params.put("waybillType", "EWAYBILL");
			WaybillRelateDetailEntity detail=null;
			List<WaybillRelateDetailEntity> wayBillDetailList=waybillRelateDetailEntityService.queryWaybillRelateDetailListByOrderOrWaybillNo(params);
			if (CollectionUtils.isNotEmpty(wayBillDetailList)) {
				 detail = wayBillDetailList.get(0);
				if (FossConstants.INACTIVE.equals(detail.getActive())) {
					waybillRelateDetailEntityService.batchUpdateWaybillRelateDetailEntity(waybillRelateDetailList);
				}
			} else {
				waybillRelateDetailEntityService.addWaybillRelateDetailEntityBatch(waybillRelateDetailList);
			}
			//子母件运单集合
			List<WaybillEntity> waybillEntityList=new ArrayList<WaybillEntity>();
			waybillDto.setWaybillEntityList(waybillEntityList);
			//提交子母件电子运单
			waybillManagerService.submitEWaybill(waybillDto);
			//运单号插入电子运单订单表
			eWaybill.setWaybillNO(waybillNo);
			//ewaybillOrderEntityDao.updateEWaybillOrderEntitySelective(eWaybill);
			//更新对应的数据为PDA已经激活的数据
			PdaScanQueryDto pdaScanQueryDto = new PdaScanQueryDto();
			pdaScanQueryDto.setTaskType(WaybillConstants.STATUS_UNLOAD);
			if(CollectionUtils.isNotEmpty(waybillDto.getWaybillRelateDetailEntityList())){
				for(WaybillRelateDetailEntity waybillRelateDetailEntity : waybillDto.getWaybillRelateDetailEntityList()){
					// 添加货签信息PDA
					pdaScanService.addLabelGoodPda(waybillDto.getWaybillEntity());
					//激活成功，删除电子订单
					//ewaybillOrderEntityDao.deleteEWaybillOrderbyOrderNO(waybillRelateDetailEntity.getOrderNo());
					//删除pdaScan数据，保证开单后数据成功
					pdaScanQueryDto.setWaybillNo(waybillRelateDetailEntity.getWaybillNo());
					pdaScanDao.deletePdaScanEntityByIdOrNo(pdaScanQueryDto);
					}
				}
				
			//激活成功改变dop到foss表改为已激活
			EcomWaybillRelateEntity ecomWaybillRelateEntity=new EcomWaybillRelateEntity();
			ecomWaybillRelateEntity.setEffective(FossConstants.YES);
			ecomWaybillRelateEntity.setOrignalOrderNo(dispatchOrderEntity.getOriginalNumber());
			ecomWaybillRelateEntity.setModifyDate(nowDate);
			ecomWaybillRelateEntity.setActive(FossConstants.YES);
			//ecomWaybillRelateEntity.setTaskId(detail.getTaskId());
			//如果一次性更新就设置运单号2.更新订单号的话 设置运单号
			waybillRelateDetailEntityService.updateEcomWaybillRelateByWaybillByOrignalOrderNo(ecomWaybillRelateEntity);
			//添加补码数据
			autoAddCode(waybillDto, eWaybill);
		
		} catch (Exception e) {
			LOGGER.info("激活运单失败.运单号为:"+waybillNo+",错误详情:"+e);
			e.printStackTrace();
			eActive = e;
			//设置异常编码
			eWaybill.setOperateResult(WaybillConstants.UNACTIVE_EWAYBILL_GENERATE_FAILURE);
			//获取异常信息
			String error = waybillExpressService.getExceptionErrorInfo(eActive);
			eWaybill.setFailReason(error);
		
			//修改子母单关系表状态改为无效
			WaybillRelateDetailEntity waybillRelateDetail=new WaybillRelateDetailEntity();
			//waybillRelateDetail.setActive(FossConstants.NO);
			waybillRelateDetail.setParentOrderNo(parentWaybillNo);
			waybillRelateDetail.setModifyTime(new Date());
			waybillRelateDetail.setPendingType(WaybillConstants.WAYBILL_STATUS_PDA_PENDING);
			waybillRelateDetailEntityService.updateWaybillRelateDetailByWaybillNoSelective(waybillRelateDetail);
			//激活失败，加入待补录表
			WaybillPendingEntity waybillPendingEntity = new WaybillPendingEntity();
			BeanUtils.copyProperties(waybillEntity, waybillPendingEntity);
			List<WaybillPendingEntity> waybillPendingList = new ArrayList<WaybillPendingEntity>();
			waybillPendingList.add(waybillPendingEntity);
			
			//進和PDA提交数据类型一致，进行数据的提交
			ewaybillService.afterActiveFail(waybillDto, waybillPendingList);
			//改变ewaybill_order失败原因
			ewaybillOrderEntityDao.updateEWaybillOrderEntitySelective(eWaybill);
			//子母件job表是否要记载错误,暂时注释掉
			/*EwaybillRelateEntity ewaybillRelateEntity=ne w EwaybillRelateEntity();
			ewaybillRelateEntity.setModifyDate(new Date());
			ewaybillRelateEntity.setParentOrderNo(parentWaybillNo);
			ewaybillRelateEntity.setFailReason(WaybillConstants.UNACTIVE_EWAYBILL_GENERATE_FAILURE);
			waybillRelateDetailEntityService.updateEwaybillRelateByPrimaryKeyNoSelective(ewaybillRelateEntity);*/
		} finally{
			if(eActive != null){
				//获取异常信息
				String error = waybillExpressService.getExceptionErrorInfo(eActive);
				eWaybill.setFailReason(error);
				//设置异常编码
				eWaybill.setOperateResult(WaybillConstants.UNACTIVE_EWAYBILL_GENERATE_FAILURE);
				//记录错误详情
				ewaybillOrderEntityDao.updateEWaybillOrderEntitySelective(eWaybill);
			}
			try {
				//判定是否出现异常
				if(eActive != null){
					//设置异常编码
					eWaybill.setOperateResult(WaybillConstants.UNACTIVE_EWAYBILL_GENERATE_FAILURE);
					String error = ExceptionUtils.getFullStackTrace(eActive);
					if (StringUtils.isNotEmpty(error) && error.length() > NUMBER_3000) {				
						eWaybill.setFailReason("异常：" + error.substring(0, NUMBER_3000));
					} else {
						eWaybill.setFailReason("异常：" + error);
					}
				}else{
					eWaybill.setOperateResult(WaybillConstants.UNACTIVE_EWAYBILL_GENERATE_SUCCESS);
				}
				//添加激活记录
				addEWaybillOrderLog(eWaybill);
			} catch (Exception e) {
				LOGGER.info("删除无效数据失败，错误详情");
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void singleGenerateActiveEWaybill(String orderNo) {
		//进行电子运单订单数据的封装
		EWaybillOrderEntity ewaybillOrder = ewaybillOrderEntityDao.queryEWaybillByOrderNo(orderNo);
		if(ewaybillOrder == null){
			EWaybillOrderLogEntity logEntity = new EWaybillOrderLogEntity();
			logEntity.setOrderNO(orderNo);
			List<EWaybillOrderLogEntity> eWaybillOrderLogList = ewaybillOrderLogEntityDao.selectEWaybillOrderByBasicParams(logEntity );
			if(CollectionUtils.isEmpty(eWaybillOrderLogList)){
				throw new WaybillValidateException(WaybillValidateException.NOT_EXPRESS_EWAYBILL_ORDER);
			}
			ewaybillOrder = new EWaybillOrderEntity();
			BeanUtils.copyProperties(eWaybillOrderLogList.get(0), ewaybillOrder);
		}
		//查询订单数据
		DispatchOrderEntity dispatchOrderEntity = dispatchOrderEntityDao.queryAllInfoByOrderNo(orderNo);
		if(dispatchOrderEntity == null){
			throw new WaybillValidateException(WaybillValidateException.DISPATCH_ORDER_NULL);
		}
		String waybillNo = null;
		if(StringUtils.isNotEmpty(ewaybillOrder.getWaybillNO())){
			waybillNo = ewaybillOrder.getWaybillNO();
		}else if(StringUtils.isNotEmpty(dispatchOrderEntity.getWaybillNo())){
			waybillNo = dispatchOrderEntity.getWaybillNo();
		}
		List<PdaScanEntity> pdaScanList = null;
		if(StringUtils.isNotEmpty(waybillNo)){
			PdaScanQueryDto pdaScanQueryDto = new PdaScanQueryDto();
			pdaScanQueryDto.setWaybillNo(waybillNo);
			pdaScanQueryDto.setActive(FossConstants.YES);
			pdaScanQueryDto.setWaybillType(WaybillConstants.EWAYBILL);
			pdaScanQueryDto.setScanType(WaybillConstants.SCAN_TYPE_SACN);
			pdaScanQueryDto.setWhetherComplete(FossConstants.YES);
			pdaScanQueryDto.setTaskType(WaybillConstants.STATUS_PICKUP);
			pdaScanList = pdaScanService.queryScanInforBySecondCondition(pdaScanQueryDto);
			if(CollectionUtils.isNotEmpty(pdaScanList)){
				setDispatchOrderEntityData(dispatchOrderEntity, pdaScanList);
			}
		}
		if(CollectionUtils.isNotEmpty(pdaScanList)){
			//如果执行不成功
			if(!executeGenearteActiveEWaybill(dispatchOrderEntity, ewaybillOrder)){
				//生成待激活运单失败，则更新订单表状态为已退回
				dispatchOrderEntityDao.updateSatusByOrderNo(ewaybillOrder.getOrderNO(), DispatchOrderStatusConstants.STATUS_RETURN);
			}
			//不论是否执行成功都新增ewaybillOrderLog数据
			afterGenearteUnActiveEWaybill(ewaybillOrder);
		}else{
			//如果执行不成功
			if(!executeGenearteUnActiveEWaybill(dispatchOrderEntity, ewaybillOrder)){
				//生成待激活运单失败，则更新订单表状态为已退回
				dispatchOrderEntityDao.updateSatusByOrderNo(ewaybillOrder.getOrderNO(), DispatchOrderStatusConstants.STATUS_RETURN);
			}
			//不论是否执行成功都新增ewaybillOrderLog数据
			afterGenearteUnActiveEWaybill(ewaybillOrder);
		}
	}
	
	/**
	 * 设置重量体积
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-22 19:56:10
	 * @param dispatchOrderEntity
	 * @param pdaScanList
	 */
	private void setDispatchOrderEntityData(DispatchOrderEntity dispatchOrderEntity, List<PdaScanEntity> pdaScanList) {
		if(CollectionUtils.isNotEmpty(pdaScanList)){
			//比较pending表中和scan表中司机code
			//重量体积
			BigDecimal weight = BigDecimal.ZERO;
			BigDecimal volume = BigDecimal.ZERO;
			//开单件数
			int goodQtycount = 0;
			//去除PDA重复提交的数据(比如0001提交了两次，需要只记录一次量)
			List<String> serialNoList = new ArrayList<String>();
			for(PdaScanEntity pdaSum : pdaScanList){
				if(!serialNoList.contains(pdaSum.getSerialNo())){
					serialNoList.add(pdaSum.getSerialNo());
					if(pdaSum.getWeight() != null && pdaSum.getWeight().compareTo(BigDecimal.ZERO) > 0){
						weight = weight.add(pdaSum.getWeight());
					}
					if(pdaSum.getVolume() != null && pdaSum.getVolume().compareTo(BigDecimal.ZERO) > 0){
						volume = volume.add(pdaSum.getVolume());
					}
					goodQtycount ++;
				}
			}
			String pdaScanDriverCode = pdaScanList.get(0).getDriverCode();
			dispatchOrderEntity.setWeight(weight == BigDecimal.ZERO ? dispatchOrderEntity.getWeight() : weight);
			dispatchOrderEntity.setVolume(volume == BigDecimal.ZERO ? dispatchOrderEntity.getVolume() : volume);
			dispatchOrderEntity.setGoodsQty(goodQtycount);
			dispatchOrderEntity.setDriverCode(pdaScanDriverCode);
			ExpressVehiclesEntity expressVehiclesEntityPara = new ExpressVehiclesEntity();
			expressVehiclesEntityPara.setEmpCode(pdaScanDriverCode);
			expressVehiclesEntityPara.setActive(WaybillConstants.YES);
			List<ExpressVehiclesEntity> expressVehiclesEntityList = 
					expressVehiclesService.queryExpressVehiclesByEntity(expressVehiclesEntityPara);
			if(CollectionUtils.isNotEmpty(expressVehiclesEntityList)){
				ExpressVehiclesEntity expressVehiclesEntityResult = expressVehiclesEntityList.get(ZERO);
				dispatchOrderEntity.setDriverName(expressVehiclesEntityResult.getEmpName());
				dispatchOrderEntity.setVehicleNo(expressVehiclesEntityResult.getVehicleNo());
			}
			dispatchOrderEntityDao.updateDriverInfo(dispatchOrderEntity);
		}
	}

	
	/**
	 * 执行生成待激活运单动作
	 * @author BaiLei
	 * @date 2014-09-08
	 */
	private boolean executeGenearteActiveEWaybill(DispatchOrderEntity dispatchOrderEntity, EWaybillOrderEntity ewaybillOrderEntity) {
		Exception ebill=null;
		//调用生成待激活运单
		try {
			//判定已经激活激活数据是否存在
			WaybillEntity waybillEntity = waybillDao.queryWaybillByOrderNo(ewaybillOrderEntity.getOrderNO());
			if(waybillEntity != null){
				throw new WaybillValidateException(WaybillValidateException.EXIST_ACTIVE_WAYBILL_DATA);
			}
			//查询待补录电子运单数据是否存在
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("orderNo", ewaybillOrderEntity.getOrderNO());
			maps.put("active", FossConstants.NO);
			maps.put("pendingType", WaybillConstants.WAYBILL_STATUS_EWAYBILL_PENDING);
			maps.put("waybillType", WaybillConstants.EWAYBILL);
			//查询电子运单待补录表信息
			WaybillPendingEntity waybillPendingEntity = waybillPendingService.queryUnActiveEWaybillPendingByWaybilllNo(maps);
			//如果已经存在已经待激活的数据
			if(waybillPendingEntity != null){
				throw new WaybillValidateException(WaybillValidateException.IS_EXISIST_UNACTIVE_EWAYBILL_INFO);
			}

			List<MutexElement> mutexes = new ArrayList<MutexElement>();	
			//创建运单互斥对象
			MutexElement mutexElement = new MutexElement(ewaybillOrderEntity.getOrderNO(), WaybillConstants.WAYBILL_SUBMIT, MutexElementType.WABYILL_SUBMIT);//创建对象
			mutexes.add(mutexElement);
			//判断订单号是否为空
			if(StringUtils.isNotEmpty(ewaybillOrderEntity.getWaybillNO())){
				MutexElement mutexElementOrder = new MutexElement(ewaybillOrderEntity.getWaybillNO(), WaybillConstants.WAYBILL_SUBMIT, MutexElementType.WABYILL_SUBMIT);//创建对象
				mutexes.add(mutexElementOrder);
			}
			//zxy 20130924 BUG-55409 start 修改：临时解决此问题，弹出友好界面,目前大概确定是运单号为空导致但是运单号为什么为空未找出原因
			//互斥锁定
			boolean isLocked = false;
			try{
				isLocked = businessLockService.lock(mutexes, LOCK_TIMEOUT);
			}catch(Exception e){
				throw new WaybillSubmitException(WaybillSubmitException.WAYBILL_SUBMIT_LOCKED);
			}
			//zxy 20130924 BUG-55409 end 修改：临时解决此问题，弹出友好界面,目前大概确定是运单号为空导致但是运单号为什么为空未找出原因
			if(!isLocked){
				throw new WaybillSubmitException(WaybillSubmitException.WAYBILL_SUBMIT_LOCKED);
			}
			try{
				//生成待激活数据
				generateActiveEWaybill(dispatchOrderEntity, ewaybillOrderEntity,null);
			}finally{
				//释放锁
				businessLockService.unlock(mutexes);
			}
			//设置操作类型
			ewaybillOrderEntity.setOperateResult(WaybillConstants.UNACTIVE_EWAYBILL_GENERATE_SUCCESS);
			updateEWaybillOrderInfo(dispatchOrderEntity, ewaybillOrderEntity);
			ewaybillOrderEntityDao.updateOperateResultByID(ewaybillOrderEntity);
			return true;
		} catch (Exception e) {
			ewaybillOrderEntity.setOperateResult(WaybillConstants.UNACTIVE_EWAYBILL_GENERATE_FAILURE);
			//获取异常信息数据
			String error = waybillExpressService.getExceptionErrorInfo(e);
			ewaybillOrderEntity.setFailReason(error);
			ewaybillOrderEntityDao.updateOperateResultByID(ewaybillOrderEntity);
			ebill = e;
			updateEWaybillOrderInfo(dispatchOrderEntity, ewaybillOrderEntity);
			return false;
		}finally {
			if(ebill != null){
				//throw new Exception(ebill.getMessage());
				String error = ExceptionUtils.getFullStackTrace(ebill);
				if (StringUtils.isNotEmpty(error) && error.length() > NUMBER_3000) {				
					ewaybillOrderEntity.setFailReason("异常：" + error.substring(0, NUMBER_3000));
				} else {
					ewaybillOrderEntity.setFailReason("异常：" + error);
				}
				//添加异常消息记录
				addEWaybillOrderLog(ewaybillOrderEntity);
				return false;
			}
		}
	}
	

	
	/**
	  * @description 子母件激活前对快递员信息和重量体积的校验
	  * @author Foss-270293-Zhangfeng
	  * @date 2015-9-12 t下午1:51:27
	  * @param waybillDto
	  * @param waybillPendingEntityList
	  * void
	 */
	public void addWeightForWaybillRelate(WaybillDto waybillDto, List<WaybillPendingEntity> waybillPendingEntityList){
		//对对象数据的引用
		WaybillEntity waybillEntity = waybillDto.getWaybillEntity();
		//实际承运信息
		ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
		BeanUtils.copyProperties(waybillDto.getActualFreightEntity(), actualFreightEntity);
		BigDecimal totalWeight = BigDecimal.ZERO;
		BigDecimal totalVolume = BigDecimal.ZERO;
		//开单件数
		int goodQtycount = 0;
		String pdaScanDriverCode = null;
		if(CollectionUtils.isEmpty(waybillDto.getWaybillRelateDetailEntityList())){
			//validateDriverAndWeight(waybillDto, waybillPendingEntityList.get(0));
			return;
		}
		for(WaybillRelateDetailEntity waybillRelateDetailEntity : waybillDto.getWaybillRelateDetailEntityList()){
			//查询出PDA扫描信息
			PdaScanQueryDto pdaScanQueryDto = new PdaScanQueryDto();
			pdaScanQueryDto.setWaybillNo(waybillRelateDetailEntity.getWaybillNo());
			pdaScanQueryDto.setActive(FossConstants.YES);
			pdaScanQueryDto.setWaybillType(WaybillConstants.EWAYBILL);
			pdaScanQueryDto.setScanType("SCAN");
			pdaScanQueryDto.setWhetherComplete(FossConstants.YES);
			pdaScanQueryDto.setTaskType(WaybillConstants.STATUS_PICKUP);
			List<PdaScanEntity> pdaScanList = pdaScanDao.queryScanInforBySecondCondition(pdaScanQueryDto);
			if(CollectionUtils.isNotEmpty(pdaScanList)){
				pdaScanDriverCode = pdaScanList.get(0).getDriverCode();
				//重量体积
				BigDecimal weight = BigDecimal.ZERO;
				BigDecimal volume = BigDecimal.ZERO;
				//去除PDA重复提交的数据(比如0001提交了两次，需要只记录一次量)
				List<String> serialNoList = new ArrayList<String>();
				for(PdaScanEntity pdaSum : pdaScanList){
					if(!serialNoList.contains(pdaSum.getSerialNo())){
						serialNoList.add(pdaSum.getSerialNo());
						if(pdaSum.getWeight() != null && pdaSum.getWeight().compareTo(BigDecimal.ZERO) >= 0){
							weight = weight.add(pdaSum.getWeight());
						}
						if(pdaSum.getVolume() != null && pdaSum.getVolume().compareTo(BigDecimal.ZERO) >= 0){
							volume = volume.add(pdaSum.getVolume());
						}
						goodQtycount++;
					}
				}
				//说明没有修改过
				boolean isNeedReSet = false;
				if(weight.compareTo(BigDecimal.ZERO) > 0 || volume.compareTo(BigDecimal.ZERO) > 0){
					isNeedReSet = true;
					//进行数据的添加
					totalWeight=totalWeight.add(weight);
					totalVolume=totalVolume.add(volume);
					//有修改过以pda为准重量和体积
					waybillRelateDetailEntity.setVolume(volume);
					waybillRelateDetailEntity.setWeight(weight);
					
				}else{
					if(StringUtils.isNotEmpty(waybillRelateDetailEntity.getOrderNo())){
						//下面要修改取ecom_waybill_relate表数据
						//进行数据的添加
						totalWeight=totalWeight.add(waybillRelateDetailEntity.getWeight() == null ? BigDecimal.ZERO : waybillRelateDetailEntity.getWeight());
						totalVolume=totalVolume.add(waybillRelateDetailEntity.getVolume() == null ? BigDecimal.ZERO : waybillRelateDetailEntity.getVolume());
					}
				}
				if(isNeedReSet){
					//查询出待激活电子运单信息实体
					if(StringUtils.isNotEmpty(waybillRelateDetailEntity.getOrderNo())){
						DispatchOrderEntity dispatchOrderEntity = dispatchOrderEntityDao.queryOrderByOrderNo(waybillRelateDetailEntity.getOrderNo());
						if(dispatchOrderEntity != null){
							//dispatchOrderEntity.setOrderNo(waybillRelateDetailEntity.getOrderNo());
							dispatchOrderEntity.setWeight(weight);
							dispatchOrderEntity.setVolume(volume);
							//dispatchOrderEntity.setGoodsQty(GoodQtycount);
							//没有用到暂时注释
							dispatchOrderEntity.setDriverCode(pdaScanDriverCode);
							ExpressVehiclesEntity expressVehiclesEntityPara = new ExpressVehiclesEntity();
							expressVehiclesEntityPara.setEmpCode(pdaScanDriverCode);
							expressVehiclesEntityPara.setActive(WaybillConstants.YES);
							List<ExpressVehiclesEntity> expressVehiclesEntityList = expressVehiclesService.queryExpressVehiclesByEntity(expressVehiclesEntityPara);
							if(CollectionUtils.isNotEmpty(expressVehiclesEntityList)){
								ExpressVehiclesEntity expressVehiclesEntityResult = expressVehiclesEntityList.get(0);
								dispatchOrderEntity.setDriverName(expressVehiclesEntityResult.getEmpName());
								dispatchOrderEntity.setVehicleNo(expressVehiclesEntityResult.getVehicleNo());
							}
							dispatchOrderEntityDao.updateByPrimaryKeySelective(dispatchOrderEntity);
						}
					}
				}
			}
			
		}
		//设置运单的重量体积数据
		for(WaybillRelateDetailEntity waybillRelateDetailEntity : waybillDto.getWaybillRelateDetailEntityList()){
			waybillRelateDetailEntity.setGoodsWeightTotal(totalWeight);
			waybillRelateDetailEntity.setGoodsVolumeTotal(totalVolume);
		}
		//求平均值
		waybillEntity.setGoodsWeightTotal(totalWeight.divide(new BigDecimal(goodQtycount), NumberConstants.NUMBER_3, BigDecimal.ROUND_HALF_EVEN));
		waybillEntity.setGoodsVolumeTotal(totalVolume.divide(new BigDecimal(goodQtycount), NumberConstants.NUMBER_3, BigDecimal.ROUND_HALF_EVEN));
		
		//第二次设置产品类型
		DispatchOrderEntity dispatchOrderEntity=new DispatchOrderEntity();
		dispatchOrderEntity.setProductCode(waybillEntity.getProductCode());
		setProductCode(waybillEntity,dispatchOrderEntity);
		
		//查询出待激活电子运单信息实体
		waybillEntity.setGoodsWeightTotal(totalWeight);
		waybillEntity.setGoodsVolumeTotal(totalVolume);
		waybillEntity.setDriverCode(pdaScanDriverCode);
		waybillEntity.setGoodsQtyTotal(1);
		//实际承运信息表
		actualFreightEntity.setWeight(totalWeight);
		actualFreightEntity.setVolume(totalVolume);
		//更新待激活电子运单信息
		waybillDto.setWaybillEntity(setSecondWaybillEntity(waybillEntity));
		waybillEntity.setGoodsQtyTotal(goodQtycount);
		//必须要重新设置currentInfo，否则创建用户是以前的快递员
		CurrentInfo currentInfo = getEWaybilCurrentInfo(waybillEntity);
		waybillDto.setCurrentInfo(currentInfo);
		//更新对应的实际承运信息表
		waybillDto.setActualFreightEntity(actualFreightEntity);
		//计算各种费用
		waybillExpressService.calculateExpressAllFee(waybillDto,FossConstants.ACTIVE);
	}

	
	//子母件开始
	@Override
	public void batchGenerateActiveWaybillRelateJobs(String jobId) {
		Date beginTime=new Date();
		LOGGER.info("子母件开始运行======"+beginTime);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("jobId", jobId);
		List<EwaybillRelateEntity> ewaybillRelateList = waybillRelateDetailEntityService.queryAllEwaybillRelateByCommon(params);
		if(CollectionUtils.isEmpty(ewaybillRelateList)){
			return;
		}
		for(EwaybillRelateEntity ewaybillRelateEntity : ewaybillRelateList){
			try{
				ewaybillService.singleHandleActiveWaybillRelateJobs(ewaybillRelateEntity);
				ewaybillRelateEntity.setFailReason(FossConstants.YES);
				waybillRelateDetailEntityService.updateEwaybillRelateByPrimaryKeySelective(ewaybillRelateEntity);
			}catch(Exception e){
				LOGGER.info("处理异常，异常信息为:");
				e.printStackTrace();
				String error = ExceptionUtils.getFullStackTrace(e);
				if (StringUtils.isNotEmpty(error) && error.length() > NumberConstants.NUMBER_1000) {				
					ewaybillRelateEntity.setFailReason("异常：" + error.substring(0, NumberConstants.NUMBER_1000));
				} else {
					ewaybillRelateEntity.setFailReason("异常：" + error);
				}
				waybillRelateDetailEntityService.updateEwaybillRelateByPrimaryKeySelective(ewaybillRelateEntity);
			}
			Date endTime=new Date();
			LOGGER.info("子母件开始运行========"+endTime);
			LOGGER.info("原始订单号=========="+ewaybillRelateEntity.getParentOrderNo()+"激活总共消耗时间======="+(endTime.getTime()-beginTime.getTime()));
		}
		
	}
	
	/**
	 * 单个处理子母件激活操作
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-14 10:13:47
	 */
	@SuppressWarnings("unused")
	@Override
	public void singleHandleActiveWaybillRelateJobs(EwaybillRelateEntity ewaybillRelateEntity) {
		//主要判定母订单号是否为空
		Exception ebill=null;
		DispatchOrderEntity dispatchOrderEntity=null;
		EWaybillOrderEntity ewaybillOrderEntity =null;
		
		if(ewaybillRelateEntity == null || StringUtils.isEmpty(ewaybillRelateEntity.getParentOrderNo())){
			if(ewaybillRelateEntity != null){
				ewaybillRelateEntity.setFailReason("异常：" +WaybillValidateException.EWAYBILL_ACTIVE_ORIGINAL_NUMBER_IS_NULL);
			}
			throw new WaybillSubmitException(WaybillValidateException.EWAYBILL_ACTIVE_ORIGINAL_NUMBER_IS_NULL);
		}
		
		//2.开始查询子母件数据(pda_scan,T_SRV_ECOM_WAYBILL_RELATE)
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("active", FossConstants.YES);
		params.put("orignalOrderNo", ewaybillRelateEntity.getParentOrderNo());
		params.put("scanType","SCAN");
		params.put("taskType", WaybillConstants.STATUS_PICKUP);
		//params.put("effective", FossConstants.NO);
		params.put("taskId", ewaybillRelateEntity.getTaskId());
		//查询子母件的订单号和运单号
		List<EcomWaybillRelateEntity> ecomWaybillRelateList = waybillRelateDetailEntityService.queryAllEwaybillRelateByEcomWaybillRelateAndPdascan(params);
		if(CollectionUtils.isEmpty(ecomWaybillRelateList)){
			ewaybillRelateEntity.setFailReason("异常：" +WaybillValidateException.EWAYBILL_ACTIVE_EffectiveData_IS_NULL);
			throw new WaybillSubmitException(WaybillValidateException.EWAYBILL_ACTIVE_EffectiveData_IS_NULL);
		}
		List<String> waybillNos=new ArrayList<String>();
		for(EcomWaybillRelateEntity relate:ecomWaybillRelateList ){
			waybillNos.add(relate.getWaybillNo());
		}
		
		//此处要判断当前字母单号是否已经开单
		List<WaybillEntity> waybillList = waybillManagerService.queryWaybillListWayBillNo(waybillNos);
		if(CollectionUtils.isNotEmpty(waybillList)){
			StringBuilder waybillNo= new StringBuilder("");
			for(WaybillEntity waybillEntity:waybillList){
				waybillNo.append(waybillEntity.getWaybillNo()).append(",");
			}
			throw new WaybillSubmitException("运单号["+ waybillNo.toString() +"],运单已存在!");
		}
		//1.通过原始订单号来校验约车(ewaybill_order)、(dispatch_Order)表数据是否存在
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("originalNumber", ewaybillRelateEntity.getParentOrderNo());
		map.put("waybillNoList",waybillNos);
		List<DispatchOrderEntity> dispatchOrderList = dispatchOrderEntityDao.queryAllInfoByOriginalNumber(map);
		if (dispatchOrderList != null && dispatchOrderList.size() > 0) {
			for (DispatchOrderEntity dispatchOrderEntity_ : dispatchOrderList) {
				if(StringUtil.isEmpty(dispatchOrderEntity_.getOrderNo())){
					ewaybillRelateEntity.setFailReason("异常：" +WaybillValidateException.EWAYBILL_ACTIVE_ORDER_NO_IS_NULL);
				}
				ewaybillOrderEntity = ewaybillOrderEntityDao.queryEWaybillByOrderNo(dispatchOrderEntity_.getOrderNo());
				if (ewaybillOrderEntity != null) {
					//主要是做订单被退回到crm时候，无论修改子母，都给开单激活
					if(StringUtils.isNotEmpty(ewaybillRelateEntity.getOrderNo())){
						if(ewaybillRelateEntity.getOrderNo().equals(dispatchOrderEntity_.getOrderNo())){
							dispatchOrderEntity = dispatchOrderEntity_;
							break;
						}
					}else if(dispatchOrderEntity_.getOrderNo().equals(ewaybillOrderEntity.getOrderNO())) {
						dispatchOrderEntity = dispatchOrderEntity_;
						break;
					} else {
						ewaybillRelateEntity.setFailReason("异常：" +WaybillValidateException.CANNOT_FIND_UNACTIVE_EWAYBILL_ORDER);
						throw new WaybillSubmitException(WaybillValidateException.CANNOT_FIND_UNACTIVE_EWAYBILL_ORDER);
					}
				}
				
			}
		} else {
			ewaybillRelateEntity.setFailReason("异常：" +WaybillValidateException.DISPATCH_ORDER_NULL);
			throw new WaybillSubmitException(WaybillValidateException.DISPATCH_ORDER_NULL);
		}
		if(ewaybillOrderEntity==null){
			ewaybillRelateEntity.setFailReason("异常：" +WaybillValidateException.CANNOT_FIND_UNACTIVE_EWAYBILL_ORDER);
			throw new WaybillSubmitException(WaybillValidateException.CANNOT_FIND_UNACTIVE_EWAYBILL_ORDER);
		}
		if(dispatchOrderEntity == null){
			if(ewaybillRelateEntity != null){
				ewaybillRelateEntity.setFailReason("异常：调度订单实体为空");
			}
			throw new WaybillSubmitException("调度订单实体为空");
		}
		try{
		//创建运单互斥对象
		List<MutexElement> mutexes = new ArrayList<MutexElement>();
		MutexElement waybillNoMutexElement = null;
		//MutexElement orderNoMutexElement = null;
		for(EcomWaybillRelateEntity ecomRelateEntity:ecomWaybillRelateList){
			//运单号
			if(StringUtils.isNotEmpty(ecomRelateEntity.getWaybillNo())){
				waybillNoMutexElement = new MutexElement(ecomRelateEntity.getWaybillNo(), WaybillConstants.WAYBILL_SUBMIT, MutexElementType.WABYILL_SUBMIT);//创建对象
				mutexes.add(waybillNoMutexElement);
			}
			//订单号
			/*if(StringUtils.isNotEmpty(ecomRelateEntity.getOrderNo())){
				orderNoMutexElement = new MutexElement(ecomRelateEntity.getOrderNo(), WaybillConstants.WAYBILL_SUBMIT, MutexElementType.WABYILL_SUBMIT);//创建对象
				mutexes.add(orderNoMutexElement);
			}*/
		}
		//互斥锁定
		boolean isLocked = false;
		try{
			isLocked = businessLockService.lock(mutexes, LOCK_TIMEOUT);
		}catch(Exception e){
			ewaybillRelateEntity.setFailReason("异常：" +WaybillSubmitException.WAYBILL_SUBMIT_LOCKED);
			throw new WaybillSubmitException(WaybillSubmitException.WAYBILL_SUBMIT_LOCKED);
		}
		//zxy 20130924 BUG-55409 end 修改：临时解决此问题，弹出友好界面,目前大概确定是运单号为空导致但是运单号为什么为空未找出原因
		if(!isLocked){
			ewaybillRelateEntity.setFailReason("异常：" +WaybillSubmitException.WAYBILL_SUBMIT_LOCKED);
			throw new WaybillSubmitException(WaybillSubmitException.WAYBILL_SUBMIT_LOCKED);
		}
		try{
			//4.开始激活操作
			singleGenerateAntiveEwaybillByWaybillRelate(dispatchOrderList, dispatchOrderEntity, ewaybillOrderEntity,ewaybillRelateEntity,ecomWaybillRelateList);
		}finally{
			//释放锁
			businessLockService.unlock(mutexes);
		}
		//设置操作类型
		ewaybillOrderEntity.setOperateResult(WaybillConstants.UNACTIVE_EWAYBILL_GENERATE_SUCCESS);
		//生成激活运单成功，则更新订单表状态为已退回
		dispatchOrderEntityDao.updateSatusByOrderNo(ewaybillOrderEntity.getOrderNO(), DispatchOrderStatusConstants.STATUS_WAYBILL);
		//updateEWaybillOrderInfo(dispatchOrderEntity,ewaybillOrderEntity);
		//ewaybillOrderEntityDao.updateOperateResultByID(ewaybillOrderEntity);
		}catch(Exception e){
			//设置操作类型
			if(ewaybillOrderEntity!=null){
			//获取异常信息数据
			String error = waybillExpressService.getExceptionErrorInfo(e);
			if (StringUtils.isNotEmpty(error) && error.length() > NumberConstants.NUMBER_1000) {				
				ewaybillOrderEntity.setFailReason("异常：" + error.substring(0, NumberConstants.NUMBER_1000));
			} else {
				ewaybillOrderEntity.setFailReason("异常：" + error);
			}
			ebill = e;
			ewaybillOrderEntity.setFailReason(error);
			ewaybillOrderEntity.setOperateResult(WaybillConstants.UNACTIVE_EWAYBILL_GENERATE_FAILURE);
			for(DispatchOrderEntity order:dispatchOrderList){
			ewaybillOrderEntity.setOrderNO(order.getOrderNo());
			ewaybillOrderEntity.setWaybillNO(order.getWaybillNo());
			//生成激活运单失败，则更新ewayBill_order退回原因
			ewaybillOrderEntityDao.updateOperateResultByID(ewaybillOrderEntity);
			//生成激活运单失败，则更新订单表状态为已退回
			dispatchOrderEntityDao.updateSatusByOrderNo(order.getOrderNo(), DispatchOrderStatusConstants.STATUS_RETURN);
			//推送crm退回原因
			updateEWaybillOrderInfo(order,ewaybillOrderEntity);
			}
			}
			
		}finally{
			if(ebill != null){
				String error = ExceptionUtils.getFullStackTrace(ebill);
				if (StringUtils.isNotEmpty(error) && error.length() > NUMBER_3000) {				
					ewaybillOrderEntity.setFailReason("异常：" + error.substring(0, NUMBER_3000));
				} else {
					ewaybillOrderEntity.setFailReason("异常：" + error);
				}
			}
			//只是添加添加异常消息记录？有点纠结
			addEWaybillOrderLog(ewaybillOrderEntity);
		}
	}

	/**
	  * @description 子母件封装waybilldto
	  * @author Foss-270293-Zhangfeng
	  * @date 2015-9-13 t下午5:43:04
	  * @param dispatchOrderEntity
	  * @param ewaybillOrderEntity
	  * @param waybillRelateDetailList
	  * @return
	  * WaybillDto
	   */
	private WaybillDto getCzmWaybillDto(DispatchOrderEntity dispatchOrderEntity,EWaybillOrderEntity ewaybillOrderEntity,List<WaybillRelateDetailEntity> waybillRelateDetailList,WaybillSystemDto systemDto,WaybillDto waybillDto) {
		String waybillNo=dispatchOrderEntity.getWaybillNo();
		//封装waybillDto对象
		waybillDto = new WaybillDto();
		//封装运单对象
		WaybillEntity waybillEntity =getWaybillEntity(dispatchOrderEntity,ewaybillOrderEntity);
		waybillDto.setWaybillType(waybillEntity.getWaybillType());
		waybillDto.setWaybillEntity(waybillEntity);
		//封装子母件关系对象
		waybillDto.setWaybillRelateDetailEntityList(waybillRelateDetailList);
		//封装承认实际对象
		waybillDto.setActualFreightEntity(getActualFreightEntity(waybillEntity,ewaybillOrderEntity));
		CusAccountEntity cusAccountEntity = waybillExpressService.queryEWaybillCusAccountInfo(waybillEntity);
		if(cusAccountEntity!= null){
			waybillDto.setOpenBank(cusAccountEntity);
		}
		
		WaybillExpressEntity waybillExpressEntity = getWaybillExpressEntity(waybillEntity, dispatchOrderEntity);
		if(waybillExpressEntity != null){
			waybillDto.setWaybillExpressEntity(waybillExpressEntity);
		}
		
		//ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(waybillEntity.getWaybillNo());
		//if(actualFreightEntity != null){
		//	waybillDto.setActualFreightEntity(actualFreightEntity);
		//}
		//校验快递员信息和重量体积比
		addWeightForWaybillRelate(waybillDto,null);
		
		
		if(waybillExpressEntity != null){
			//设置 内部带货  员工工号和 费用承担部门  从dispatchOrderEntity获取
			String deliveryCustomerCode = dispatchOrderEntity.getDeliveryCustomerCode();
			if(WaybillConstants.DEPPON_CUSTOMER.equals(deliveryCustomerCode)){
				String senderCode = dispatchOrderEntity.getSenderCode();
				String orgName = dispatchOrderEntity.getPaymentOrgCode();
				if(StringUtils.isEmpty(senderCode)){
					
					throw new WaybillValidateException(WaybillValidateException.INNER_PICKUP_DELIVERY_EMPLOYEE_CODE_NULL);
				}
				if(StringUtils.isEmpty(orgName)){
					throw new WaybillValidateException(WaybillValidateException.INNER_PICKUP_DEPT_NAME_IS_NULL);
				}
				waybillExpressEntity.setDeliveryEmployeeCode(senderCode);
				//因为官网同步过来的是部门名称需要转换成编码
				if(StringUtils.isNotEmpty(orgName)){
					OrgAdministrativeInfoEntity entity = new OrgAdministrativeInfoEntity();
					entity.setName(orgName);
					List<OrgAdministrativeInfoEntity>  orgList = orgAdministrativeInfoService.querySimpleOrgAdministrativeInfoByEntity(entity, 0, 1);
					if(CollectionUtils.isNotEmpty(orgList)){
						waybillExpressEntity.setInnerPickupFeeBurdenDept(orgList.get(0).getCode());
					}else{
						throw new WaybillValidateException(WaybillValidateException.INNER_PICKUP_DEPT_INFO_IS_VALID);
					}
				}
			}
			//设置快递员里面的信息，省的绩效考核数据算错
			if(null != waybillDto && null != waybillDto.getWaybillEntity()
					&& StringUtils.isNotEmpty(waybillDto.getWaybillEntity().getDriverCode())){
				EmployeeEntity employee = employeeService.queryEmployeeByEmpCodeNoCache(waybillDto.getWaybillEntity().getDriverCode());
				if(null != employee){
					//设置员工工号姓名
					waybillExpressEntity.setExpressEmpName(employee.getEmpName());
					//判定快递点部信息是否为空，不为空设置数据
					if(null != employee.getDepartment()){
						waybillExpressEntity.setExpressOrgCode(employee.getDepartment().getCode());
						waybillExpressEntity.setExpressOrgCode(employee.getDepartment().getName());
					}
				}
				waybillExpressEntity.setExpressEmpCode(waybillDto.getWaybillEntity().getDriverCode());
			}
			waybillDto.setWaybillExpressEntity(waybillExpressEntity);
		}
		
		CurrentInfo currentInfo = getEWaybilCurrentInfo(waybillDto.getWaybillEntity());
		if(currentInfo != null){
			waybillDto.setCurrentInfo(currentInfo);
			waybillDto.getWaybillEntity().setCreateUserName(currentInfo.getEmpName());
		}
		//因为是进行数据的封装，故此时的运单号不存在，需要进行重新赋值
		if(waybillDto.getWaybillEntity() != null){
			waybillDto.getWaybillEntity().setWaybillNo(waybillNo);
			waybillDto.getWaybillEntity().setId(UUIDUtils.getUUID());
			waybillDto.getWaybillEntity().setActive(FossConstants.YES);
			waybillDto.getWaybillEntity().setPendingType(WaybillConstants.WAYBILL_STATUS_PDA_PENDING);
			waybillDto.getWaybillEntity().setCreateTime(systemDto.getCreateTime());
			waybillDto.getWaybillEntity().setModifyTime(systemDto.getModifyTime());
		}
		//实际承运信息表的Modify时间在Dao层有设置，在这里设置无用
		if(waybillDto.getActualFreightEntity() != null){
			waybillDto.getActualFreightEntity().setWaybillNo(waybillNo);
		}
		if(waybillDto.getWaybillExpressEntity() != null){
			waybillDto.getWaybillExpressEntity().setWaybillNo(waybillNo);
			waybillDto.getWaybillExpressEntity().setWaybillId(waybillDto.getWaybillEntity().getId());
			//增加是否可做返单标识
			waybillDto.getWaybillExpressEntity().setCanReturnCargo(WaybillConstants.YES);
			waybillDto.getWaybillExpressEntity().setCreateTime(systemDto.getCreateTime());
		}
		//因为主要是按照PDA提交运单的方式进行运单数据的提价，需要把oldWaybillNo弄进去
		waybillDto.setOldWaybillNo(waybillNo);
		
		//校验快递员信息和重量体积比
		validateEWaybill(waybillDto);
		
		//这里提前设置一下数据，尽管waybillMangeService设置了，这里只是为了说明这里的时间不能随意修改
		if(CollectionUtils.isNotEmpty(waybillDto.getWaybillChargeDtlEntity())){
			for(WaybillChargeDtlEntity chargeDtlEntity : waybillDto.getWaybillChargeDtlEntity()){
				chargeDtlEntity.setActive(FossConstants.YES);
				chargeDtlEntity.setWaybillNo(waybillNo);
				chargeDtlEntity.setCreateTime(systemDto.getCreateTime());
				chargeDtlEntity.setModifyTime(systemDto.getModifyTime());
			}
		}
		if(CollectionUtils.isNotEmpty(waybillDto.getWaybillDisDtlEntity())){
			for(WaybillDisDtlEntity disDtlEntity : waybillDto.getWaybillDisDtlEntity()){
				disDtlEntity.setActive(FossConstants.YES);
				disDtlEntity.setWaybillNo(waybillNo);
				disDtlEntity.setCreateTime(systemDto.getCreateTime());
				disDtlEntity.setModifyTime(systemDto.getModifyTime());
			}
		}
		if(CollectionUtils.isNotEmpty(waybillDto.getWaybillPaymentEntity())){
			for(WaybillPaymentEntity waybillPayment : waybillDto.getWaybillPaymentEntity()){
				waybillPayment.setActive(FossConstants.YES);
				waybillPayment.setWaybillNo(waybillNo);
				waybillPayment.setCreateTime(systemDto.getCreateTime());
				waybillPayment.setModifyTime(systemDto.getModifyTime());
			}
		}
		if(CollectionUtils.isNotEmpty(waybillDto.getLabeledGoodEntities())){
			for(LabeledGoodEntity LabeledGoodEntity : waybillDto.getLabeledGoodEntities()){
				LabeledGoodEntity.setActive(FossConstants.YES);
				LabeledGoodEntity.setWaybillNo(waybillNo);
				LabeledGoodEntity.setCreateTime(systemDto.getCreateTime());
				LabeledGoodEntity.setModifyTime(systemDto.getModifyTime());
			}
		}
		if(waybillDto.getWoodenRequirementsEntity() != null){
			waybillDto.getWoodenRequirementsEntity().setActive(FossConstants.YES);
			waybillDto.getWoodenRequirementsEntity().setWaybillNo(waybillNo);
			waybillDto.getWoodenRequirementsEntity().setCreateTime(systemDto.getCreateTime());
			waybillDto.getWoodenRequirementsEntity().setModifyTime(systemDto.getModifyTime());
		}
		return waybillDto;
	}

  	
	/**
	  * @description 指定子母件之间关系
	  * @author Foss-270293-Zhangfeng
	  * @date 2015-9-11 t上午9:40:07
	  * @param @param ecomWaybillRelateList
	  * @param @param systemDto
	  * @param @return
	  * @return List<WaybillRelateDetailEntity>
	 */
	public List<WaybillRelateDetailEntity> ecomWaybillRelateEntityToWaybillRelateDetailEntityDate(List<EcomWaybillRelateEntity> ecomWaybillRelateList,EwaybillRelateEntity ewaybillRelateEntity,DispatchOrderEntity dispatchOrder,List<DispatchOrderEntity> dispatchOrderList){
		LOGGER.info("开始设置子母件关系");
		List<WaybillRelateDetailEntity> waybillRelateDetailList=new ArrayList<WaybillRelateDetailEntity>();
		 String parentWayBill=dispatchOrder.getWaybillNo();
		 LOGGER.info("母单号============="+parentWayBill);
		 Date nowDate = new Date();
		//总子母件数不能超过10000
		if (ecomWaybillRelateList.size() > NUMBER_10000) {
			ewaybillRelateEntity.setFailReason("异常："+ WaybillValidateException.GOODS_QTY_TOTAL_NO_MORE_THAN_TEN_THOUSAND);
			throw new WaybillValidateException(WaybillValidateException.GOODS_QTY_TOTAL_NO_MORE_THAN_TEN_THOUSAND);
		}
		 for(EcomWaybillRelateEntity ecomWaybillRelateEntity:ecomWaybillRelateList){
			 LOGGER.info("子单号================"+ecomWaybillRelateEntity.getWaybillNo());
			 WaybillRelateDetailEntity waybillRelateDetail=new WaybillRelateDetailEntity();
			 waybillRelateDetail.setId(UUIDUtils.getUUID());
			 waybillRelateDetail.setParentOrderNo(dispatchOrder.getOriginalNumber());
			 waybillRelateDetail.setParentWaybillNo(parentWayBill);
			 waybillRelateDetail.setWaybillNo(ecomWaybillRelateEntity.getWaybillNo());
			 waybillRelateDetail.setActive(FossConstants.YES);
			 waybillRelateDetail.setVolume(ecomWaybillRelateEntity.getVolume());
			 waybillRelateDetail.setWeight(ecomWaybillRelateEntity.getWeight());
			 waybillRelateDetail.setWaybillType(ecomWaybillRelateEntity.getWaybillType());
			 waybillRelateDetail.setPendingType(WaybillConstants.WAYBILL_STATUS_EWAYBILL_ACTIVE);
			 waybillRelateDetail.setCreateTime(nowDate);
			 waybillRelateDetail.setModifyTime(nowDate);
			 waybillRelateDetail.setBillTime(nowDate);
			 waybillRelateDetail.setOrderNo(dispatchOrder.getOrderNo());
			 //暂时写dop只是表示数据来源于dop？
			 waybillRelateDetail.setCreateOrgCode("DOP");
			 waybillRelateDetail.setCreateUserCode("DOP");
			 waybillRelateDetail.setModifyOrgCode("DOP");
			 waybillRelateDetail.setModifyUserCode("DOP");
			 waybillRelateDetail.setReceiveOrgCode("DOP");
			 
			for (DispatchOrderEntity order : dispatchOrderList) {
				if (ecomWaybillRelateEntity.getWaybillNo().equals(order.getWaybillNo())) {
					waybillRelateDetail.setOrderNo(order.getOrderNo());
					break;
				}
			}
			 
			 waybillRelateDetail.setTaskId(ecomWaybillRelateEntity.getTaskId());
			 
			 if(ecomWaybillRelateEntity.getWaybillNo().equals(dispatchOrder.getWaybillNo())){
				 //是母单
				 waybillRelateDetail.setIsPicPackage("Y"); 
			 }else{
				 waybillRelateDetail.setIsPicPackage("N"); 
			 }
			 waybillRelateDetailList.add(waybillRelateDetail);
		}
		 LOGGER.info("结束设置子母件关系");
		return waybillRelateDetailList;
		
	}
	
	
	/**
	 * 不需要走待激活,不需要进行待激活操作直接激活:前提：前台能提供扫描的单号数据到后台
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-27 20:59:38
	 * @param waybillRelateDetailList
	 * @param waybillPendingEntityList
	 * @param ewaybillRelateEntity
	 */
	@Transactional
	@Override
	public void singleGenerateAntiveEwaybillByWaybillRelate(List<WaybillRelateDetailEntity> waybillRelateDetailList,
			List<WaybillPendingEntity> waybillPendingEntityList, EwaybillRelateEntity ewaybillRelateEntity) {
		if(CollectionUtils.isEmpty(waybillRelateDetailList)){
			return;
		}
		String waybillNo = null;
		//查询待补录电子运单数据是否存在
		EWaybillOrderEntity ewaybill = null;
		DispatchOrderEntity dispatchOrderEntity = null;
		if(CollectionUtils.isEmpty(waybillPendingEntityList)){
			//随机获取一个订单数据与电子运单数据都存在的数据然后进行激活操作
			getDispatchOrderAndEWaybillOrderEntity(waybillRelateDetailList, dispatchOrderEntity, ewaybill);
			//generateActiveEWaybill(null,dispatchOrderEntity, ewaybill);
		}else{
			waybillNo = waybillPendingEntityList.get(0).getWaybillNo();
			batchGenerateAntiveEwaybill(waybillNo);
		}
	}

	/**
	  * @description 不需要走待激活,不需要进行待激活操作直接激活:前提：前台能提供扫描的单号数据到后台
	  * @author Foss-270293-Zhangfeng
	  * @date 2015-9-11 t下午3:34:26
	  * @param @param waybillRelateDetailList
	  * @param @param waybillPendingEntityList
	  * @param @param ewaybillRelateEntity
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void singleGenerateAntiveEwaybillByWaybillRelate(List<DispatchOrderEntity> dispatchOrderList,DispatchOrderEntity dispatchOrderEntity, EWaybillOrderEntity ewaybill,EwaybillRelateEntity ewaybillRelateEntity,List<EcomWaybillRelateEntity> ecomWaybillRelateList) {	
		//设置子母件关系
		WaybillSystemDto systemDto = new WaybillSystemDto();
		Date nowDate = new Date();
		systemDto.setBillTime(nowDate);
		systemDto.setCreateTime(nowDate);
		systemDto.setModifyTime(nowDate);
		WaybillDto waybillDto=null;
		List<WaybillRelateDetailEntity> waybillRelateDetailList=ecomWaybillRelateEntityToWaybillRelateDetailEntityDate(ecomWaybillRelateList,ewaybillRelateEntity,dispatchOrderEntity,dispatchOrderList);
		//封装waybillDto
		waybillDto = getCzmWaybillDto(dispatchOrderEntity,ewaybill,waybillRelateDetailList,systemDto,waybillDto);
		//进入单个子母件激活操作
		generateActiveEWaybill(dispatchOrderEntity, ewaybill,waybillDto);
	
	}

	
	/**
	 * 拼接订单表与电子运单订单表数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-28 11:06:17
	 * @param waybillRelateDetailList
	 * @param dispatchOrderEntity
	 * @param EWaybillOrderEntity
	 */
	private void getDispatchOrderAndEWaybillOrderEntity(List<WaybillRelateDetailEntity> waybillRelateDetailList,
			DispatchOrderEntity dispatchOrderEntity, EWaybillOrderEntity ewaybill) {
		if(CollectionUtils.isEmpty(waybillRelateDetailList)){
			return;
		}
		String waybillNo = waybillRelateDetailList.get(0).getWaybillNo();
		String orderNo = waybillRelateDetailList.get(0).getOrderNo();
		if(StringUtils.isEmpty(waybillNo) || StringUtils.isEmpty(orderNo)){
			return;
		}
		//获取到对应的运单号
		if(StringUtils.isEmpty(orderNo)){
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("waybillNo", waybillNo);
			List<PreHandEWaybillOrderEntity> preHandList = preHandEWaybillOrderDao.selectPreEWaybillOrderByOrderNoOrWaybillNo(maps);
			if(CollectionUtils.isNotEmpty(preHandList)){
				orderNo = preHandList.get(0).getOrderNo();
			}
		}
		//如果订单号为空
		if(StringUtils.isEmpty(orderNo)){
			throw new WaybillValidateException(WaybillValidateException.EWAYBILL_ACTIVE_ORDER_NO_IS_NULL);
		}
		dispatchOrderEntity = dispatchOrderEntityDao.queryAllInfoByOrderNo(orderNo);
		ewaybill = ewaybillOrderEntityDao.queryEWaybillByOrderNo(orderNo);
		if(dispatchOrderEntity == null){
			throw new WaybillValidateException(WaybillValidateException.DISPATCH_ORDER_NULL);
		}
		if(ewaybill == null){
			throw new WaybillValidateException(WaybillValidateException.CANNOT_FIND_UNACTIVE_EWAYBILL_ORDER);
		}
	}

	@Override
	public void singleGenerateAntiveEwaybillByEwaybillEntity(EwaybillRelateEntity ewaybillRelateEntity) {
		//主要判定母订单号是否为空
		if(ewaybillRelateEntity == null || StringUtils.isEmpty(ewaybillRelateEntity.getParentOrderNo())){
			return;
		}
		//订单表的数据,主要是对应的订单号里面存在对应的重量
		//在这里最主要的目的是找到对应的母订单号，在找到对应的子运单信息
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("active", FossConstants.YES);
		params.put("parentOrderNo", ewaybillRelateEntity.getParentOrderNo());
		//查询子母件详情数据
		List<WaybillRelateDetailEntity> waybillRelateDetailList = waybillRelateDetailEntityService.queryCommonLevelRelateDetailListByOrderOrWaybillNo(params);
		if(CollectionUtils.isEmpty(waybillRelateDetailList)){
			return;
		}
		//运单号集合
		List<String> waybillNoList = new ArrayList<String>();
		List<String> orderNoList = new ArrayList<String>();
		for(WaybillRelateDetailEntity relateDetail : waybillRelateDetailList){
			waybillNoList.add(relateDetail.getWaybillNo());
			orderNoList.add(relateDetail.getOrderNo());
		}

		//创建运单互斥对象
		List<MutexElement> mutexes = new ArrayList<MutexElement>();
		MutexElement waybillNoMutexElement = null;
		MutexElement orderNoMutexElement = null;
		for(WaybillRelateDetailEntity relateDetail : waybillRelateDetailList){
			waybillNoMutexElement = new MutexElement(relateDetail.getWaybillNo(), WaybillConstants.WAYBILL_SUBMIT, MutexElementType.WABYILL_SUBMIT);//创建对象
			orderNoMutexElement = new MutexElement(relateDetail.getOrderNo(), WaybillConstants.WAYBILL_SUBMIT, MutexElementType.WABYILL_SUBMIT);//创建对象
			mutexes.add(waybillNoMutexElement);
			mutexes.add(orderNoMutexElement);
		}
		//互斥锁定
		boolean isLocked = false;
		try{
			isLocked = businessLockService.lock(mutexes, LOCK_TIMEOUT);
		}catch(Exception e){
			throw new WaybillSubmitException(WaybillSubmitException.WAYBILL_SUBMIT_LOCKED);
		}
		//zxy 20130924 BUG-55409 end 修改：临时解决此问题，弹出友好界面,目前大概确定是运单号为空导致但是运单号为什么为空未找出原因
		if(!isLocked){
			throw new WaybillSubmitException(WaybillSubmitException.WAYBILL_SUBMIT_LOCKED);
		}
		try{
			ewaybillService.singleGenerateAntiveEwaybillByWaybillRelate(waybillRelateDetailList, null, ewaybillRelateEntity);
		}finally{
			//释放锁
			businessLockService.unlock(mutexes);
		}
	}

	/**
	 * 一票多件需要添加自动补码的东西
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-13 21:41:37
	 * @param waybilldto
	 * @param waybillRelateDetail
	 * @param ewaybillOrderEntity
	 */
	private void autoAddCode(WaybillDto waybilldto, EWaybillOrderEntity ewaybillOrderEntity) {
		if(waybilldto == null || waybilldto.getWaybillEntity() == null){
			return;
		}
		List<WaybillEntity> waybillEntityList=null;
		AutoAddCodeEntity entity = null;
		if(CollectionUtils.isEmpty(waybilldto.getWaybillEntityList())){
			waybillEntityList=new ArrayList<WaybillEntity>();
			waybillEntityList.add(waybilldto.getWaybillEntity());
			waybilldto.setWaybillEntityList(waybillEntityList);
		}
		
		//判定运单详情数据是否为空
		//这样写减少重复代码量
		/*if(CollectionUtils.isEmpty(waybilldto.getWaybillRelateDetailEntityList())){
			entity = new AutoAddCodeEntity();
			entity.setWaybillNO(waybilldto.getWaybillEntity().getWaybillNo());
			entity.setReceiveCustomerProvCode(waybilldto.getWaybillEntity().getReceiveCustomerProvCode());
			entity.setReceiveCustomerCityCode(waybilldto.getWaybillEntity().getReceiveCustomerCityCode());
			entity.setReceiveCustomerDistCode(waybilldto.getWaybillEntity().getReceiveCustomerDistCode());
			entity.setReceiveCustomerTownCode(waybilldto.getActualFreightEntity().getReceiveCustomerVillageCode());
			entity.setBillTime(waybilldto.getWaybillEntity().getBillTime());
			entity.setCreateOrgCode(waybilldto.getWaybillEntity().getCreateOrgCode());
			entity.setCustomerPickupOrgCode(waybilldto.getWaybillEntity().getCustomerPickupOrgCode());
			entity.setId(waybilldto.getWaybillEntity().getId());
			entity.setReceiveCustomerAddress(waybilldto.getWaybillEntity().getReceiveCustomerAddress());
			entity.setTargetOrgCode(waybilldto.getWaybillEntity().getTargetOrgCode());
			entity.setWaybillID(waybilldto.getWaybillExpressEntity().getWaybillId());
			if(waybilldto.getReceiveCustomerAreaDto() != null){				
				entity.setReceiveCustomerProvName(waybilldto.getReceiveCustomerAreaDto().getProvinceName());
				entity.setReceiveCustomerCityName(waybilldto.getReceiveCustomerAreaDto().getCityName());
				entity.setReceiveCustomerDistName(waybilldto.getReceiveCustomerAreaDto().getCountyName());
				entity.setReceiveCustomerTownName(waybilldto.getReceiveCustomerAreaDto().getVillageTownName());
			}			
			try {
				autoAddCodeService.save(entity);
			} catch (Exception e) {
				try {
					ewaybillOrderEntity.setFailReason("自动补码中转插入数据异常");
					e.printStackTrace();
					addEWaybillOrderLog(ewaybillOrderEntity);
				} catch (Exception e1) {
					LOGGER.info("自动补码中转插入数据异常");
					e1.printStackTrace();
				}
			}
			return;
		}*/
		for(WaybillEntity waybillEntity : waybilldto.getWaybillEntityList()){
			    entity = new AutoAddCodeEntity();
				entity.setWaybillNO(waybillEntity.getWaybillNo());
				entity.setReceiveCustomerProvCode(waybillEntity.getReceiveCustomerProvCode());
				entity.setReceiveCustomerCityCode(waybillEntity.getReceiveCustomerCityCode());
				entity.setReceiveCustomerDistCode(waybillEntity.getReceiveCustomerDistCode());
				entity.setReceiveCustomerTownCode(waybillEntity.getReceiveCustomerVillageCode());
				entity.setBillTime(waybillEntity.getBillTime());
				entity.setCreateOrgCode(waybillEntity.getCreateOrgCode());
				entity.setCustomerPickupOrgCode(waybillEntity.getCustomerPickupOrgCode());
				entity.setId(waybillEntity.getId());
				entity.setReceiveCustomerAddress(waybillEntity.getReceiveCustomerAddress());
				entity.setTargetOrgCode(waybillEntity.getTargetOrgCode());
				entity.setWaybillID(waybillEntity.getId());
				if(waybilldto.getReceiveCustomerAreaDto() != null){				
					entity.setReceiveCustomerProvName(waybilldto.getReceiveCustomerAreaDto().getProvinceName());
					entity.setReceiveCustomerCityName(waybilldto.getReceiveCustomerAreaDto().getCityName());
					entity.setReceiveCustomerDistName(waybilldto.getReceiveCustomerAreaDto().getCountyName());
					entity.setReceiveCustomerTownName(waybilldto.getReceiveCustomerAreaDto().getVillageTownName());
				}			
				try {
					autoAddCodeService.save(entity);
				} catch (Exception e) {
					try {
						ewaybillOrderEntity.setFailReason("自动补码中转插入数据异常");
						e.printStackTrace();
						addEWaybillOrderLog(ewaybillOrderEntity);
					} catch (Exception e1) {
						LOGGER.info("自动补码中转插入数据异常");
						e1.printStackTrace();
					}
			}
		}
	}

	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public void setWaybillPendingService(IWaybillPendingService waybillPendingService) {
		this.waybillPendingService = waybillPendingService;
	}

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public void setSaleDepartmentService(ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	public void setWaybillFreightRouteService(IWaybillFreightRouteService waybillFreightRouteService) {
		this.waybillFreightRouteService = waybillFreightRouteService;
	}

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	public void setCusBargainService(ICusBargainService cusBargainService) {
		this.cusBargainService = cusBargainService;
	}

	public void setExpressVehiclesService(
			IExpressVehiclesService expressVehiclesService) {
		this.expressVehiclesService = expressVehiclesService;
	}

	public void setBargainAppOrgService(IBargainAppOrgService bargainAppOrgService) {
		this.bargainAppOrgService = bargainAppOrgService;
	}

	public void setWaybillExpressService(
			IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setExpressPartSalesDeptService(
			IExpressPartSalesDeptService expressPartSalesDeptService) {
		this.expressPartSalesDeptService = expressPartSalesDeptService;
	}
	
	public void setWaybillPickupService(IWaybillPickupService waybillPickupService) {
		this.waybillPickupService = waybillPickupService;
	}

	public void setMessageBundle(IMessageBundle messageBundle) {
		this.messageBundle = messageBundle;
	}

	public void setWaybillPaymentService(
			IWaybillPaymentService waybillPaymentService) {
		this.waybillPaymentService = waybillPaymentService;
	}

	public void setWaybillDisDtlService(IWaybillDisDtlService waybillDisDtlService) {
		this.waybillDisDtlService = waybillDisDtlService;
	}

	public void setWaybillChargeDtlService(
			IWaybillChargeDtlService waybillChargeDtlService) {
		this.waybillChargeDtlService = waybillChargeDtlService;
	}

	public void setWaybillStockService(IWaybillStockService waybillStockService) {
		this.waybillStockService = waybillStockService;
	}

	public void setCustomerDao(ICustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public void setDispatchOrderEntityDao(
			IDispatchOrderEntityDao dispatchOrderEntityDao) {
		this.dispatchOrderEntityDao = dispatchOrderEntityDao;
	}

	public void setPkpsysConfigService(ISysConfigService pkpsysConfigService) {
		this.pkpsysConfigService = pkpsysConfigService;
	}

	public void setActualFreightService(IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}

	public void setPickupToDoMsgService(IPickupToDoMsgService pickupToDoMsgService) {
		this.pickupToDoMsgService = pickupToDoMsgService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setFreightRouteService(IFreightRouteService freightRouteService) {
		this.freightRouteService = freightRouteService;
	}

	public void setAsteriskSalesDeptService(
			IAsteriskSalesDeptService asteriskSalesDeptService) {
		this.asteriskSalesDeptService = asteriskSalesDeptService;
	}

	public void setExpressPrintStarService(
			IExpressPrintStarService expressPrintStarService) {
		this.expressPrintStarService = expressPrintStarService;
	}

	public void setCrmOrderJMSService(ICrmOrderJMSService crmOrderJMSService) {
		this.crmOrderJMSService = crmOrderJMSService;
	}

	public static Logger getLogger() {
		return LOGGER;
	}

	public static int getZero() {
		return ZERO;
	}

	public static int getOne() {
		return ONE;
	}

	public static int getMax() {
		return MAX;
	}

	public static String getQueryjobid() {
		return queryJobId;
	}

	public static String getCn() {
		return CN;
	}

	public static String getZh() {
		return ZH;
	}

	public static Locale getLocale() {
		return LOCALE;
	}
	
	public void setEwaybillOrderEntityDao(
			IEWaybillOrderEntityDao ewaybillOrderEntityDao) {
		this.ewaybillOrderEntityDao = ewaybillOrderEntityDao;
	}

	public void setEwaybillOrderLogEntityDao(
			IEWaybillOrderLogEntityDao ewaybillOrderLogEntityDao) {
		this.ewaybillOrderLogEntityDao = ewaybillOrderLogEntityDao;
	}

	public void setWaybillToSuppleDao(IWaybillToSuppleDao waybillToSuppleDao) {
		this.waybillToSuppleDao = waybillToSuppleDao;
	}

	public void setWaybillPackBIService(IWaybillPackBIService waybillPackBIService) {
		this.waybillPackBIService = waybillPackBIService;
	}

	public void setActualFreightBIService(IActualFreightBIService actualFreightBIService) {
		this.actualFreightBIService = actualFreightBIService;
	}
	
	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}
	
	public void setWaybillTransactionService(IWaybillTransactionService waybillTransactionService) {
		this.waybillTransactionService = waybillTransactionService;
	}
	
	public void setGoodsAreaService(IGoodsAreaService goodsAreaService) {
		this.goodsAreaService = goodsAreaService;
	}
	
	public void setLdpAgencyDeptService(ILdpAgencyDeptService ldpAgencyDeptService) {
		this.ldpAgencyDeptService = ldpAgencyDeptService;
	}
	
	public void setOutfieldService(IOutfieldService outfieldService) {
		this.outfieldService = outfieldService;
	}
	
	public void setWaybillPendingDao(IWaybillPendingDao waybillPendingDao) {
		this.waybillPendingDao = waybillPendingDao;
	}
	
	public void setWoodenRequirementsService(IWoodenRequirementsService woodenRequirementsService) {
		this.woodenRequirementsService = woodenRequirementsService;
	}

	public IAdministrativeRegionsService getAdministrativeRegionsService() {
		return administrativeRegionsService;
	}

	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}
	
	public void setWaybillCHDtlPendingDao(IWaybillCHDtlPendingDao waybillCHDtlPendingDao) {
		this.waybillCHDtlPendingDao = waybillCHDtlPendingDao;
	}
	
	public void setOrderTaskHandleService(IOrderTaskHandleService orderTaskHandleService) {
		this.orderTaskHandleService = orderTaskHandleService;
	}
	
	public void setCalculateTransportPathService(ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}
	
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}
	
	public void setPdaScanDao(IPdaScanDao pdaScanDao) {
		this.pdaScanDao = pdaScanDao;
	}
	
	public void setBillCaculateService(IBillCaculateService billCaculateService) {
		this.billCaculateService = billCaculateService;
	}

	public void seteWaybillProcessService(
			IEWaybillProcessService eWaybillProcessService) {
		this.eWaybillProcessService = eWaybillProcessService;
	}
	public void setPreHandEWaybillOrderDao(IPreHandEWaybillOrderDao preHandEWaybillOrderDao) {
		this.preHandEWaybillOrderDao = preHandEWaybillOrderDao;
	}

	public void setModifEwaybillOrderRecordDao(
			IModifEwaybillOrderRecordDao modifEwaybillOrderRecordDao) {
		this.modifEwaybillOrderRecordDao = modifEwaybillOrderRecordDao;
	}
	
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	public IAutoAddCodeService getAutoAddCodeService() {
		return autoAddCodeService;
	}

	public void setAutoAddCodeService(IAutoAddCodeService autoAddCodeService) {
		this.autoAddCodeService = autoAddCodeService;
	}
	
	public void setPdaScanService(IPdaScanService pdaScanService) {
		this.pdaScanService = pdaScanService;
	}
	
	public void setWaybillRelateDetailEntityService(IWaybillRelateDetailEntityService waybillRelateDetailEntityService) {
		this.waybillRelateDetailEntityService = waybillRelateDetailEntityService;
	}
	
	public void setEwaybillService(IEWaybillService ewaybillService) {
		this.ewaybillService = ewaybillService;
	}

	public IEWaybillMessageDao geteWaybillMessageDao() {
		return eWaybillMessageDao;
	}

	public void seteWaybillMessageDao(IEWaybillMessageDao eWaybillMessageDao) {
		this.eWaybillMessageDao = eWaybillMessageDao;
	}

	public void setGrayScaleService(IGrayScaleService grayScaleService) {
		this.grayScaleService = grayScaleService;
	}

}