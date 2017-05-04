package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAsteriskSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusAccountService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusBargainService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressCityService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressDeliveryRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPrintStarService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFreightRouteService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IGoodsAreaService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.INonfixedCustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliveryRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LimitedWarrantyItemsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerPaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressCityResultDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.FreightRouteLineDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.OuterBranchParamsDto;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CusBargainVo;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IArrivesheetDao;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillDao;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillDetailDao;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverbillService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.DeliverbillConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillDetailEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.util.BigDecimalOperationUtil;
import com.deppon.foss.module.pickup.pricing.api.server.service.IBillCaculateService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPublishPriceExpressService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.DiscountTypeConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.GoodsTypeEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PublishPriceExpressEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateSubDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.BillCaculateServiceException;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.WaybillDiscountVo;
import com.deppon.foss.module.pickup.sign.api.server.service.ISendWaybillTrackService;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IActualFreightDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IInsurGoodsDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillExpressDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICrmOrderService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISysConfigService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillDisDtlService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillFreightRouteService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPackBIService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillToPartnersService;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.OrgInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.define.ExpWaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillChargeDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPaymentEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.AmountInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ArriveSheetEntityDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponWaybillInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EffectiveDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.FreightRouteDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.QueryPickupPointDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.SalesDepartmentCityDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDestinationDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillExpressArrivalSheetDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillExpressDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.HandleTransportPathException;
import com.deppon.foss.module.pickup.waybill.shared.exception.PdaInterfaceException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillImportException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillOrderHandleException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillRfcChangeException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillSubmitException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.module.pickup.waybill.shared.vo.OtherChargeVo;
import com.deppon.foss.module.settlement.common.api.server.service.ICustomerBargainService;
import com.deppon.foss.module.settlement.common.api.shared.dto.DebitDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.transfer.departure.api.server.service.IWebDepartureService;
import com.deppon.foss.module.transfer.departure.api.shared.define.DepartureConstant;
import com.deppon.foss.module.transfer.departure.api.shared.dto.AutoDepartDTO;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IPathDetailDao;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PathDetailEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * @author 026123-foss-lifengteng
 *
 */
public class WaybillExpressService implements IWaybillExpressService{
	
	/**
	 * 定义常量值：0.01 
	 * 1、不可继承 
	 * 2、避免魔法数字
	 */
	private static final double KEY_VOLUME = 0.01;
	
	private static final double POINT_05 = 0.5;
	
	private static final double POINT_0005 = 0.005;


	
	 /**
     * 小数点保留位数
     */
    private static int newScale=2;
	
	private BigDecimal defaultFirstWeight = BigDecimal.valueOf(POINT_05);
	
	/**
	 * 常量值8
	 */
	public static final int EIGHT = 8;
	
	/**
	 * 常量值11
	 */
	public static final int ELEVEN = 11;
	
	/**
	 * clob通过to_char后最多存储的字符不能超过1300个
	 */
	private static final int CLOB_LIMIT = 1337;
	
	private static final double FIFTY = 50.00;
	
	private static final String TYPE_CODE = "YHJ";
	
	/**
	 * 记录日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(WaybillExpressService.class);
	private IWaybillExpressDao waybillExpressDao;
	
	/**
	 * 运单DAO 
	 * 运单基本信息DAO服务接口 
	 * 实现数据持久化
	 */
	private IWaybillDao waybillDao;
	/**
	 * 到达联DAO
	 */
	private IArrivesheetDao arrivesheetDao;
	
	//配合BI修改运单件数后续调接送接口修改modifytime
	private IWaybillPackBIService waybillPackBIService;
	
	/**
	 * 公布价查询服务接口
	 */
	private IPublishPriceExpressService publishPriceExpressService;
	
	private IActualFreightDao actualFreightDao;
	
	/**
	 * 部门 复杂查询 service
	 * 提供与部门 复杂查询相关的服务接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 派送单service
	 */
	private IDeliverbillService deliverbillService;
	
	
	private IWaybillManagerService waybillManagerService;
	
	private IWebDepartureService webDepartureService;
	
	/** 
	 * 营业部 Service接口
	 */
	private ISaleDepartmentService saleDepartmentService;
	
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	private IDeliverbillDao deliverbillDao;
	
	private IDeliverbillDetailDao deliverbillDetailDao;
	private  IPathDetailDao pathDetailDao;

	private ICusBargainService cusBargainService;
	
	private ICusAccountService cusAccountService;
	
	private ISysConfigService pkpsysConfigService;


	private IExpressCityService expressCityService;
	
	private IActualFreightService actualFreightService;
	
	private IWaybillDisDtlService waybillDisDtlService;
	
	private IBillCaculateService billCaculateService;
	
	private ICrmOrderService crmOrderService;
	
	private IWaybillFreightRouteService waybillFreightRouteService;
	
	private IWaybillToPartnersService waybillToPartnersService ;
	
	public IWaybillToPartnersService getWaybillToPartnersService() {
		return waybillToPartnersService;
	}

	public void setWaybillToPartnersService(
			IWaybillToPartnersService waybillToPartnersService) {
		this.waybillToPartnersService = waybillToPartnersService;
	}

	public IPathDetailDao getPathDetailDao() {
		return pathDetailDao;
	}

	public void setPathDetailDao(IPathDetailDao pathDetailDao) {
		this.pathDetailDao = pathDetailDao;
	}
	
	//外发网点service
	private IVehicleAgencyDeptService vehicleAgencyDeptService;
	//限保物品
	private IInsurGoodsDao pkpinsurGoodsDao;
	
	private ICustomerBargainService customerBargainService;
	
	/**
	 * 更改单DAO
	 * 提供更改单持久化接口
	 */
	private IWaybillRfcDao waybillRfcDao;

	public void setWaybillRfcDao(IWaybillRfcDao waybillRfcDao) {
		this.waybillRfcDao = waybillRfcDao;
	}

	private ILdpAgencyDeptService  ldpAgencyDeptService ;
	
	/**
	 * 库区服务类
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-11-30 上午8:59:57
	 */
	private IGoodsAreaService goodsAreaService;
	
	/**
	 * 快递派送区域的服务类
	 */
	private IExpressDeliveryRegionsService expressDeliveryRegionsService;
	
	/**
	 * 产品定义服务类
	 */
	private IProductService productService;
	
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	
	public void setExpressDeliveryRegionsService(IExpressDeliveryRegionsService expressDeliveryRegionsService) {
		this.expressDeliveryRegionsService = expressDeliveryRegionsService;
	}
	
	/**
	 * 轨迹推送接口 
	 */
	private ISendWaybillTrackService sendWaybillTrackService;
	public void setSendWaybillTrackService(
			ISendWaybillTrackService sendWaybillTrackService) {
		this.sendWaybillTrackService = sendWaybillTrackService;
	}

	public void setGoodsAreaService(IGoodsAreaService goodsAreaService) {
		this.goodsAreaService = goodsAreaService;
	}

	public void setLdpAgencyDeptService(ILdpAgencyDeptService ldpAgencyDeptService) {
		this.ldpAgencyDeptService = ldpAgencyDeptService;
	}

	public void setDeliverbillDetailDao(IDeliverbillDetailDao deliverbillDetailDao) {
		this.deliverbillDetailDao = deliverbillDetailDao;
	}

	public void setDeliverbillDao(IDeliverbillDao deliverbillDao) {
		this.deliverbillDao = deliverbillDao;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
	
	public void setPkpsysConfigService(ISysConfigService pkpsysConfigService) {
		this.pkpsysConfigService = pkpsysConfigService;
	}

	public void setWebDepartureService(IWebDepartureService webDepartureService) {
		this.webDepartureService = webDepartureService;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setDeliverbillService(IDeliverbillService deliverbillService) {
		this.deliverbillService = deliverbillService;
	}

	public void setPublishPriceExpressService(IPublishPriceExpressService publishPriceExpressService) {
		this.publishPriceExpressService = publishPriceExpressService;
	}

	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		return orgAdministrativeInfoService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public IWaybillDao getWaybillDao() {
		return waybillDao;
	}

	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}

	public IWaybillExpressDao getWaybillExpressDao() {
		return waybillExpressDao;
	}

	public void setWaybillExpressDao(IWaybillExpressDao waybillExpressDao) {
		this.waybillExpressDao = waybillExpressDao;
	}

	public void setVehicleAgencyDeptService(
			IVehicleAgencyDeptService vehicleAgencyDeptService) {
		this.vehicleAgencyDeptService = vehicleAgencyDeptService;
	}

	/**
	 * 提交时新增运单快递
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-5 上午11:35:42
	 */
	public String addWaybillExpressEntity(WaybillExpressEntity waybillExpress) {
		return waybillExpressDao.addWaybillExpressEntity(waybillExpress);
	}

	/**
	 *通过运单编号修改运单
	 * 
	 * @param waybill
	 */
	public int updateWaybillExpressByWaybillNo(WaybillExpressEntity waybillExpress) {
		return waybillExpressDao.updateWaybillExpressByWaybillNo(waybillExpress);
	}

	/**
	 * 修改运单
	 * 
	 * @param waybill
	 */
	public int updateWaybillExpressById(WaybillExpressEntity waybillExpress) {
		return waybillExpressDao.updateWaybillExpressById(waybillExpress);
	}

	/**
	 * 通过运单编号查询运单快递
	 * 
	 * @param waybill
	 */
	public WaybillExpressEntity queryWaybillExpressByNo(String waybillNo) {
		return waybillExpressDao.queryWaybillExpressByNo(waybillNo);
	}

	/**
	 * 通过运单Id查询运单快递
	 * 
	 * @param waybill
	 */
	public WaybillExpressEntity queryWaybillExpressByWaybillId(String waybillId) {
		return waybillExpressDao.queryWaybillExpressByWaybillId(waybillId);
	}

	/**
	 * 通过id询运单快递
	 * 
	 * @param waybill
	 */
	public WaybillExpressEntity queryWaybillExpressById(String id) {
		return waybillExpressDao.queryWaybillExpressById(id);
	}

	//纯运费
//	private static  BigDecimal PURE_FREIGHT = BigDecimal.ZERO;
	/**
	 * 内部接口：补码-更新运单提货网点等信息
	 * 输入 
	 * waybillNo  运单号          addCodeTime补码时间             customerPickupOrgCode新的客户提货网点
	 * @param dto
	 */
	public WaybillDestinationDto addWaybillExpressCode(WaybillExpressDto dto) {
		LOGGER.error("addWaybillExpressCode : " +ToStringBuilder.reflectionToString
				(dto, ToStringStyle.SIMPLE_STYLE));
		//需要将合伙人补码传送类返回 邹胜利 2016年5月30日 10:09:15
		if(dto==null || dto.getWaybillNo() == null ){
			return null;
		}
		
		ActualFreightEntity actualFreightEntity = actualFreightDao.queryByWaybillNo(dto.getWaybillNo());
		WaybillExpressEntity waybillExpress =  waybillExpressDao.queryWaybillExpressByNo(dto.getWaybillNo());
		if(waybillExpress==null){
			return null;
		}
		
		//处理直营开单先补码到合伙人（提成信息已经同步），再补码到直营，这样需要吧前面一条补码提成信息作废 2016年3月27日 08:23:33 葛亮亮
		//是否补码（获取的是本次补码前补码状态）
		String isLastAddCode = waybillExpress.getIsAddCode();
		//补码提货网点（获取的是本次补码前补码提货网点）
		String lastPickupCode = waybillExpress.getCustomerPickupOrgCode();
		//之前补码时间
		Date lastAddCodeTime = waybillExpress.getAddCodeTime();
		
		//校验是否还有未审核和未受理的更改单
		//validate(StringUtil.defaultIfNull(waybillExpress.getWaybillId()));
		
		waybillExpress.setIsAddCode(FossConstants.YES);
		waybillExpress.setAddCodeTime(dto.getAddCodeTime());
		
		WaybillEntity waybill = waybillDao.queryWaybillByNo(dto.getWaybillNo());
		List<WaybillEntity> waybillEntityList = new ArrayList<WaybillEntity>();
		waybillEntityList.add(waybill);
		
		//将未做任何修改的运单信息拷贝下来 2016年3月30日 11:50:34 葛亮亮
		WaybillEntity originalWaybill = new WaybillEntity();
		try {			
			PropertyUtils.copyProperties(originalWaybill, waybill);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<WaybillEntity> list2 = new ArrayList<WaybillEntity>();
		
		for(int i =0;i <waybillEntityList.size();i++){
			WaybillEntity	waybillEntity = waybillEntityList.get(i);
			WaybillEntity waybillEntity2 = new WaybillEntity();
			waybillEntity2.setId(waybillEntity.getId());
			list2.add(waybillEntity2);
		}
		WaybillDestinationDto waybillDestinationDto = null;
		SaleDepartmentEntity receiveDepartmentEntity =null;
		//上一次补码提货网点部门组织信息
		SaleDepartmentEntity lastPickupDepartmentEntity = null;
		//查询到达部门，判断是否向ptp推送数据
		OrgAdministrativeInfoEntity orgAdmin=orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(dto.getCustomerPickupOrgCode());
		
		if(null == waybill){
			throw new WaybillValidateException("未能获取该订单信息");
		}else{
			//查询出发部门信息
			receiveDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(waybill.getReceiveOrgCode());
			lastPickupDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(lastPickupCode);
		}
		 
		if(orgAdmin!=null ){
			
			for(int i =0;i <waybillEntityList.size();i++){
				WaybillEntity	waybillEntity = waybillEntityList.get(i);
				waybillEntity.setCustomerPickupOrgCode(orgAdmin.getCode());
				waybillEntity.setCustomerPickupOrgName(orgAdmin.getName());
				waybillEntity.setTargetOrgCode(orgAdmin.getOrgSimpleName());
				waybillEntity.setLastLoadOrgCode(orgAdmin.getCode());
				waybillEntity.setLastLoadOrgName(orgAdmin.getName());
			}
			
			for(int i =0;i <list2.size();i++){
				WaybillEntity	waybillEntity2 = list2.get(i);
				waybillEntity2.setCustomerPickupOrgCode(orgAdmin.getCode());
				waybillEntity2.setCustomerPickupOrgName(orgAdmin.getName());
				waybillEntity2.setTargetOrgCode(orgAdmin.getOrgSimpleName());
				waybillEntity2.setLastLoadOrgCode(orgAdmin.getCode());
				waybillEntity2.setLastLoadOrgName(orgAdmin.getName());
			}
			//公司营业部查询
			SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(orgAdmin.getCode());
			//是否存在
			if(null == saleDepartmentEntity){
				throw new WaybillValidateException("提货网点不正确，为公司外场[既非快递代理网点，也非公司营业部网点]");
			}else{
				// 如果是驻地部门，返回驻地部门外场
				if (FossConstants.YES.equals(saleDepartmentEntity.getStation())) {
					// 如果是,入驻地外场
					actualFreightEntity.setEndStockOrgCode(waybillExpress.getLastOutFieldCode());
				}else{
					actualFreightEntity.setEndStockOrgCode(orgAdmin.getCode());
				}
			}
			
			waybillExpress.setTargetOrgCode(orgAdmin.getOrgSimpleName());
			waybillExpress.setCustomerPickupOrgCode(orgAdmin.getCode());
			waybillExpress.setCustomerPickupOrgName(orgAdmin.getName());
			
			//判断开单部门是否是合伙人 2016年3月22日 19:06:31 葛亮亮	
				//推送信息给合伙人
				if(FossConstants.YES.equals(saleDepartmentEntity.getIsLeagueSaleDept())
				   ||FossConstants.YES.equals(receiveDepartmentEntity.getIsLeagueSaleDept())
				   ||(lastPickupDepartmentEntity != null && FossConstants.YES.equals(isLastAddCode) && FossConstants.YES.equals(lastPickupDepartmentEntity.getIsLeagueSaleDept()))){
					waybillDestinationDto = new WaybillDestinationDto();
					//运单ID
					waybillDestinationDto.setWaybillNoid(waybill.getId());
					waybillDestinationDto.setWaybillNo(dto.getWaybillNo());
					waybillDestinationDto.setLastLoadOrgCode(orgAdmin.getCode());//组织code 
					waybillDestinationDto.setCustomerPickupOrgCode(orgAdmin.getCode());
					waybillDestinationDto.setCustomerPickupOrgName(orgAdmin.getName());//组织name
					waybillDestinationDto.setTargetOrgCode(orgAdmin.getOrgSimpleName());//部门简称
					if(StringUtils.equals(receiveDepartmentEntity.getIsLeagueSaleDept(), FossConstants.YES)){
						waybillDestinationDto.setIsReceiveDepPartner(true);
					}else{
						waybillDestinationDto.setIsReceiveDepPartner(false);
					}
					if(StringUtils.equals(saleDepartmentEntity.getIsLeagueSaleDept(), FossConstants.YES)){
						waybillDestinationDto.setIsArriveDepPartner(true);
					}else{
						waybillDestinationDto.setIsArriveDepPartner(false);
					}
					
					waybillDestinationDto.setWaybillEntity(waybill);
					//未做任何修改的运单信息
					waybillDestinationDto.setOriginalWaybill(originalWaybill);					
					waybillDestinationDto.setActualFreightEntity(actualFreightEntity);
					//补码快递表
					waybillDestinationDto.setWaybillExpressEntity(waybillExpress);
					//本次补码之前的补码时间
					waybillDestinationDto.setLastAddCodeTime(lastAddCodeTime);
					//本次补码前是否补码
					waybillDestinationDto.setIsLastAddCode(isLastAddCode);
					//本次补码前是否补码到合伙人
					if(null != lastPickupDepartmentEntity && FossConstants.YES.equals(lastPickupDepartmentEntity.getIsLeagueSaleDept())){
						waybillDestinationDto.setIsLastPickUpPartner(true);
					}else{
						waybillDestinationDto.setIsLastPickUpPartner(false);
					}
					
	//				waybillToPartnersService.sendDestinationInfo(waybillDestinationDto);
			}
		}else{
			OuterBranchExpressEntity bpe = ldpAgencyDeptService.queryLdpAgencyDeptByCode(dto.getCustomerPickupOrgCode(), FossConstants.YES);
			if(bpe!=null){
				String originalPickupOrgCode =null;
				WaybillEntity waybillEntityt = null;
				for(int i =0;i <waybillEntityList.size();i++){
					WaybillEntity waybillEntity = waybillEntityList.get(i);
					
					originalPickupOrgCode =		waybillEntity.getCustomerPickupOrgCode();
					waybillEntity.setCustomerPickupOrgCode(bpe.getAgentDeptCode());
					waybillEntity.setCustomerPickupOrgName(bpe.getAgentDeptName());
					waybillEntity.setTargetOrgCode(bpe.getSimplename());
					if(FossConstants.YES.equals(waybillEntity.getActive())){
						waybillEntityt = waybillEntity;
					}
				}
				
				for(int i =0;i <list2.size();i++){
					WaybillEntity	waybillEntity2 = list2.get(i);
					waybillEntity2.setCustomerPickupOrgCode(bpe.getAgentDeptCode());
					waybillEntity2.setCustomerPickupOrgName(bpe.getAgentDeptName());
					waybillEntity2.setTargetOrgCode(bpe.getSimplename());
				}
				waybillExpress.setTargetOrgCode(bpe.getSimplename());
				waybillExpress.setCustomerPickupOrgCode(bpe.getAgentDeptCode());
				waybillExpress.setCustomerPickupOrgName(bpe.getAgentDeptName());
				
				//这里要修改最终配载部门
				String lastOutField = waybillExpress.getLastOutFieldCode();
				
				PathDetailEntity pathDetail = new PathDetailEntity();
				pathDetail.setWaybillNo(waybillExpress.getWaybillNo());
				List<PathDetailEntity> list = pathDetailDao.queryLastPathDetailEntity(pathDetail);

				if(list!=null && list.size()>0){
					PathDetailEntity path = list.get(0);
					if(path!=null && StringUtils.isNotEmpty(path.getOrigOrgCode())){
						lastOutField = path.getOrigOrgCode();
					}
				}
				
				if(StringUtils.isNotEmpty(lastOutField)){
					waybillExpress.setLastOutFieldCode(lastOutField);
					
					OrgAdministrativeInfoEntity lastDepartment = 
							orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(lastOutField);
					for(int i =0;i <waybillEntityList.size();i++){
						WaybillEntity	waybillEntity = waybillEntityList.get(i);
						waybillEntity.setLastLoadOrgCode(lastOutField);
						if(lastDepartment!=null){
							waybillEntity.setLastLoadOrgName(lastDepartment.getName());
						}
						if(FossConstants.YES.equals(waybillEntity.getActive())){
							waybillEntityt = waybillEntity;
						}
					}
					
					for(int i =0;i <list2.size();i++){
						WaybillEntity	waybillEntity2 = list2.get(i);
						waybillEntity2.setLastLoadOrgCode(lastOutField);
						if(lastDepartment!=null){
							waybillEntity2.setLastLoadOrgName(lastDepartment.getName());
						}
					}
					actualFreightEntity.setEndStockOrgCode(lastOutField);
					
					String goodsAreaCode = getGoodsAreaCode(lastOutField, DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXPRESS,"快递库区");
					actualFreightEntity.setGoodsAreaCode(goodsAreaCode);
					if(FossConstants.YES.equals(receiveDepartmentEntity.getIsLeagueSaleDept()) && bpe != null){
						waybillDestinationDto = new WaybillDestinationDto();
						//向ptp传值
						waybillDestinationDto.setLastLoadOrgCode(lastOutField);
						//运单ID
						waybillDestinationDto.setWaybillNoid(waybill.getId());
						waybillDestinationDto.setWaybillNo(dto.getWaybillNo());
						waybillDestinationDto.setCustomerPickupOrgCode(bpe.getAgentDeptCode());
						waybillDestinationDto.setCustomerPickupOrgName(bpe.getAgentDeptName());
						waybillDestinationDto.setTargetOrgCode(bpe.getSimplename());
						waybillDestinationDto.setIsArriveDepPartner(false);
						waybillDestinationDto.setIsReceiveDepPartner(true);
						waybillDestinationDto.setWaybillEntity(waybill);
						//未做任何修改的运单信息
						waybillDestinationDto.setOriginalWaybill(originalWaybill);					
						waybillDestinationDto.setActualFreightEntity(actualFreightEntity);
						//补码快递表
						waybillDestinationDto.setWaybillExpressEntity(waybillExpress);
						//本次补码之前的补码时间
						waybillDestinationDto.setLastAddCodeTime(lastAddCodeTime);
						//本次补码前是否补码
						waybillDestinationDto.setIsLastAddCode(isLastAddCode);
						//本次补码前是否补码到合伙人
						if(null != lastPickupDepartmentEntity && FossConstants.YES.equals(lastPickupDepartmentEntity.getIsLeagueSaleDept())){
							waybillDestinationDto.setIsLastPickUpPartner(true);
						}else{
							waybillDestinationDto.setIsLastPickUpPartner(false);
						}
					}
					
				}else{
					
					FreightRouteDto freightRouteDto = waybillManagerService.queryFreightRouteBySourceTarget(waybillEntityt.getCreateOrgCode(),
							originalPickupOrgCode, waybillEntityt.getProductCode(), new Date());
					if (freightRouteDto.getFreightRouteLinelist() != null) {
						List<FreightRouteLineDto> freightRouteLinelist = freightRouteDto.getFreightRouteLinelist();//获得走货路径list
						// 得到途径外场和 始发营业部, 到达营业部 编码集合LIST A-C C-D D-B 得到这种格式
						List<String> addressInfoList = new ArrayList<String>();

						for (FreightRouteLineDto f : freightRouteLinelist) {//拼接走货路径
							addressInfoList.add(f.getSourceCode() + "-" + f.getTargetCode());
							
						}
						
						// 根据始发外场code 和外场集合 删除重复的外场 得到A C D B 格式的 外场集合 同时包含 出发部门到达部门
						List<String> departmentInfoList = removeDuplicateRoute(addressInfoList);
					
						String lastLoadName = "";//最终配载部门名称
						String lastLoadCode = "";//最终配载部门 code

						OrgAdministrativeInfoEntity lastDepartment = null;
						// 代理网点的话从后往前取最后的一个自有部门
						for (int i = departmentInfoList.size() - 1; i > -1; i--) {
							lastLoadCode = departmentInfoList.get(i);
							lastDepartment = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(lastLoadCode);
							if (lastDepartment == null 
									|| lastDepartment.checkExpressSalesDepartment()) {
								continue;
							} else {
								lastLoadName = lastDepartment.getName();
								break;
							}
						}
						
						if(StringUtils.isNotEmpty(lastLoadCode)){	
							for(int i =0;i <waybillEntityList.size();i++){
								WaybillEntity	waybillEntity = waybillEntityList.get(i);
								waybillEntity.setLastLoadOrgCode(lastLoadCode);
								waybillEntity.setLastLoadOrgName(lastLoadName);
							}
							
							for(int i =0;i <list2.size();i++){
								WaybillEntity	waybillEntity2 = list2.get(i);
								waybillEntity2.setLastLoadOrgCode(lastLoadCode);
								waybillEntity2.setLastLoadOrgName(lastLoadName);
							}
							
							actualFreightEntity.setEndStockOrgCode(lastLoadCode);
							
							String goodsAreaCode = getGoodsAreaCode(lastLoadCode, DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXPRESS,"快递库区");
							actualFreightEntity.setGoodsAreaCode(goodsAreaCode);
							if(FossConstants.YES.equals(receiveDepartmentEntity.getIsLeagueSaleDept()) && bpe != null){
								waybillDestinationDto = new WaybillDestinationDto();
								//向ptp传值
								waybillDestinationDto.setLastLoadOrgCode(lastLoadCode);
								//运单ID
								waybillDestinationDto.setWaybillNoid(waybill.getId());
								waybillDestinationDto.setWaybillNo(dto.getWaybillNo());
								waybillDestinationDto.setCustomerPickupOrgCode(bpe.getAgentDeptCode());
								waybillDestinationDto.setCustomerPickupOrgName(bpe.getAgentDeptName());
								waybillDestinationDto.setTargetOrgCode(bpe.getSimplename());
								waybillDestinationDto.setIsArriveDepPartner(false);
								waybillDestinationDto.setIsReceiveDepPartner(true);
								waybillDestinationDto.setWaybillEntity(waybill);
								//未做任何修改的运单信息
								waybillDestinationDto.setOriginalWaybill(originalWaybill);					
								waybillDestinationDto.setActualFreightEntity(actualFreightEntity);
								//补码快递表
								waybillDestinationDto.setWaybillExpressEntity(waybillExpress);
								//本次补码之前的补码时间
								waybillDestinationDto.setLastAddCodeTime(lastAddCodeTime);
								//本次补码前是否补码
								waybillDestinationDto.setIsLastAddCode(isLastAddCode);
								//本次补码前是否补码到合伙人
								if(null != lastPickupDepartmentEntity && FossConstants.YES.equals(lastPickupDepartmentEntity.getIsLeagueSaleDept())){
									waybillDestinationDto.setIsLastPickUpPartner(true);
								}else{
									waybillDestinationDto.setIsLastPickUpPartner(false);
								}
							}
						}
					}
				}
			}	
		}
		
		ActualFreightEntity actualFreight2 = new ActualFreightEntity();
		actualFreight2.setId(actualFreightEntity.getId());
		actualFreight2.setEndStockOrgCode(actualFreightEntity.getEndStockOrgCode());
		if(StringUtils.isNotEmpty(actualFreightEntity.getGoodsAreaCode())){
			actualFreight2.setGoodsAreaCode(actualFreightEntity.getGoodsAreaCode());
		}
		
		this.actualFreightDao.updateByPrimaryKeySelective(actualFreight2);
		Date modifyTime = new Date() ;
		waybillExpressDao.updateWaybillExpressById(waybillExpress);
			for(int i =0;i <list2.size();i++){
			WaybillEntity	waybillEntity2 = list2.get(i);
			//sangwenhao-272311
			waybillEntity2.setModifyTime(modifyTime);
			waybillDao.updateWaybill(waybillEntity2);
		}
		
		/**
		 * DEFECT-3553
		 * FOSS快递运单有外场补码操作时，会修改运单表相关信息。将修改信息插入“异常运单信息表”pkp.t_srv_waybill_pack_bi中
		 */
		WaybillEntity waybillEntity = waybillDao.queryWaybillByNo(dto.getWaybillNo());
		waybillPackBIService.addWaybillPackBIEntity(waybillEntity);
	
		return waybillDestinationDto;
	}
		

	
	
	/**
	 * 获取库区编码
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-11-6 上午9:17:13
	 */
	private String getGoodsAreaCode(String transferCenter, String type,String errorMessage) {
		/**
		 * 通过获取服务，获取货区
		 */
		List<GoodsAreaEntity> goodsAreas = goodsAreaService.queryGoodsAreaListByType(transferCenter, type);
		if (goodsAreas == null || goodsAreas.isEmpty()) {
			throw new WaybillValidateException("中转场"+transferCenter+"没有找到对应的快递库区");
		} else {
			/**
			 * 如果货区有值，拿出获取实体
			 */
			GoodsAreaEntity goodsAreaEntity = goodsAreas.get(0);
			/**
			 * 获取货区编码
			 */
			if (goodsAreaEntity.getGoodsAreaCode() == null) {
				throw new WaybillValidateException("中转场"+transferCenter+"没有找到对应的快递库区");
			} else {
				/**
				 * 返回获取编码
				 */
				return goodsAreaEntity.getGoodsAreaCode();
			}
		}
	}
		
		/**
		 * <p>
		 * 根据第一个外场 从外场集合串 取出单个外场列表 删除重复的
		 * </p>
		 * 
		 * @author foss-jiangfei
		 * @date 2012-11-8 下午2:15:21
		 * @param addressInfoList
		 * @see
		 */
		private List<String> removeDuplicateRoute(List<String> routeList) {

			List<String> temp = new ArrayList<String>();
			for (int i = 0; i < routeList.size(); i++) {
				temp.add(routeList.get(i).substring(0,
						routeList.get(i).indexOf("-")));
			}
			
			return temp;
		}
	
	/**
	 * 根据出发部门编码和到达城市编码查询公布价详细信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-7 上午10:07:18
	 */
	@Override
	public List<PublishPriceExpressEntity> queryPublishPriceDetail(String startDeptNo, String destinationCode){
		//非空判断：若出发城市和目的城市编码都为空则返回空 
		if(StringUtils.isEmpty(startDeptNo) || StringUtils.isEmpty(destinationCode)){
			return null;
		}else{
			return publishPriceExpressService.queryPublishPriceDetailByCity(startDeptNo, destinationCode, new Date());
		}
	}
	
	/**
	 * 快递 提交派送装车任务后生成派送单和到达联 
	 * @param dto
	 * @return
	 */
	public WaybillExpressArrivalSheetDto createExpressArrivalSheetAndDeliveryBill(WaybillExpressArrivalSheetDto dto) {
		LOGGER.error("createExpressArrivalSheetAndDeliveryBill" +ToStringBuilder.reflectionToString
				(dto, ToStringStyle.SIMPLE_STYLE));
		if(dto==null ){
			return dto;
		}
		
		List<WaybillExpressArrivalSheetDto> list = dto.getList();
		DeliverbillEntity deliverbillEntity = new DeliverbillEntity();
		
		if(dto.getList()!=null && ! dto.getList().isEmpty()){
			for (Iterator<WaybillExpressArrivalSheetDto>  iterator1 = list.iterator(); iterator1.hasNext();) {
				WaybillExpressArrivalSheetDto dto2 = iterator1.next();
				ActualFreightEntity actualFreightEntity = actualFreightDao.queryByWaybillNo(dto2.getWaybillNo());
				checkDeliverbillDetail(dto2.getGoodQtyTotal().intValue(), actualFreightEntity);
			}
		}
		
		int goodTotal = 0;
		int waybillQty = 0;
		BigDecimal payAmountTotal = BigDecimal.ZERO;//总到付金额
		BigDecimal weightTotal = BigDecimal.ZERO;/** 总重量. */
		BigDecimal volumeTotal = BigDecimal.ZERO;	/** 总体积. */
		if(dto.getList()!=null && ! dto.getList().isEmpty()){
			for (Iterator<WaybillExpressArrivalSheetDto>  iterator = list.iterator(); iterator.hasNext();) {
				WaybillExpressArrivalSheetDto dto2 = iterator.next();
				waybillQty ++ ;//总票数
				Integer singleQty = dto2.getGoodQtyTotal();
				if(singleQty==null){
					singleQty = Integer.valueOf(0);//总件数
				}
				goodTotal = goodTotal+ singleQty.intValue();
				
				WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(dto2.getWaybillNo());
				dto2.setWaybillEntity(waybillEntity);
				ActualFreightEntity actualFreightEntity = actualFreightDao.queryByWaybillNo(dto2.getWaybillNo());
				dto2.setActualFreightEntity(actualFreightEntity);
				
				//校验是否有效且为“生成”状态的到达联
				List<ArriveSheetEntity> list2 = arrivesheetDao.queryArriveSheetByWaybillNo(new ArriveSheetEntity(
						dto2.getWaybillNo(), ArriveSheetConstants.STATUS_GENERATE, FossConstants.YES, FossConstants.NO));
				
				//已经生成过了到达联
				if(list2!=null && list2.size()>0){
					ArriveSheetEntity arriveEntity1 = list2.get(0);
					
					ArriveSheetEntityDto arrvieDto = new ArriveSheetEntityDto();
					
					try{
						PropertyUtils.copyProperties(arrvieDto, arriveEntity1);
					}catch(Exception e){
						LOGGER.info("copyProperties",e);
					}
					
					dto2.setArriveSheetEntityDto(arrvieDto);
					arriveEntity1.setStatus(ArriveSheetConstants.STATUS_DELIVER);
					arrivesheetDao.updateByPrimaryKeySelective(arriveEntity1);
				}else{
				//是否有生成状态的到达联
				ArriveSheetEntity existedArriveSheetEntity = new ArriveSheetEntity();
				existedArriveSheetEntity.setWaybillNo(dto2.getWaybillNo());
				existedArriveSheetEntity.setStatus(ArriveSheetConstants.STATUS_DELIVER);
				existedArriveSheetEntity.setActive( FossConstants.YES);
				existedArriveSheetEntity.setDestroyed(  FossConstants.NO);
				existedArriveSheetEntity.setCreateTime(new Date());
				existedArriveSheetEntity.setCreateUserCode(dto.getCreateUserCode());
				existedArriveSheetEntity.setCreateUserName(dto.getCreateUserName());
				existedArriveSheetEntity.setCreateOrgCode(dto.getCreateOrgCode());
				existedArriveSheetEntity.setCreateOrgName(dto.getCreateOrgName());
				existedArriveSheetEntity.setIsSentRequired(FossConstants.YES);
				existedArriveSheetEntity.setIsNeedInvoice(FossConstants.NO);
				existedArriveSheetEntity.setPreNoticeContent(FossConstants.NO);
				existedArriveSheetEntity.setArriveSheetGoodsQty(singleQty);
				addNewArriveSheet(actualFreightEntity.getGoodsQty(),actualFreightEntity,
						existedArriveSheetEntity, singleQty);
				
				ArriveSheetEntityDto arrvieDto = new ArriveSheetEntityDto();
				try{
					PropertyUtils.copyProperties(arrvieDto, existedArriveSheetEntity);
				}catch(Exception e){
					LOGGER.info("copyProperties",e);
				}
				
				dto2.setArriveSheetEntityDto(arrvieDto);
				}	
				
				//到达联全部生成了---------------
				BigDecimal singleToPayAmount = waybillEntity.getToPayAmount();
				if(singleToPayAmount == null){
					singleToPayAmount = BigDecimal.ZERO;
				}
				payAmountTotal = payAmountTotal.add(singleToPayAmount);
				
				Integer singGoodQtrTotal = waybillEntity.getGoodsQtyTotal();
				
				if(singGoodQtrTotal.intValue()>0){
					BigDecimal rate = BigDecimal.valueOf(singleQty.intValue())
							.divide(BigDecimal.valueOf(singGoodQtrTotal.intValue()), 0, BigDecimal.ROUND_HALF_UP);
					
					BigDecimal weightTotalSingle = rate.multiply(waybillEntity.getBillWeight());
					BigDecimal volumeTotalSingle = rate.multiply(waybillEntity.getGoodsVolumeTotal());
					weightTotal = weightTotal.add(weightTotalSingle);
					volumeTotal = volumeTotal.add(volumeTotalSingle);
				}
			}
		}
		
		deliverbillEntity.setDeliverbillNo("P" + deliverbillService.querySequence());
		
		dto.setDeliveryNo(deliverbillEntity.getDeliverbillNo());
		
		deliverbillEntity.setVehicleNo(dto.getVehicleNo());
		deliverbillEntity.setDriverCode(dto.getDriverCode());
		deliverbillEntity.setDriverName(dto.getDriverName());
		deliverbillEntity.setWaybillQtyTotal(waybillQty);
		deliverbillEntity.setGoodsQtyTotal(goodTotal);
		deliverbillEntity.setPayAmountTotal(payAmountTotal);
		deliverbillEntity.setWeightTotal(weightTotal);
		deliverbillEntity.setVolumeTotal(volumeTotal);
		deliverbillEntity.setCreateUserCode(dto.getCreateUserCode());
		deliverbillEntity.setCreateUserName(dto.getCreateUserName());
		deliverbillEntity.setSubmitTime(new Date());
		
		deliverbillEntity.setIsExpress(FossConstants.YES);
		
		AutoDepartDTO autoDto = new AutoDepartDTO();
		autoDto.setVehicleNo(dto.getVehicleNo());
		autoDto.setApplyUserName(dto.getCreateUserName());
		autoDto.setApplyUserCode(dto.getCreateUserCode());
		autoDto.setApplyOrgCode(dto.getCreateOrgCode());
		autoDto.setAutoDepartType(DepartureConstant.AUTO_DEPART_TYPE_DELIVERBILL);
		autoDto.setDepartItems(DepartureConstant.DEPART_ITEM_TYPE_PKP);
		autoDto.setDriverCode(dto.getDriverCode());
		autoDto.setDriverName(dto.getDriverName());
		
		
		SaleDepartmentEntity saleDepartment = saleDepartmentService.querySaleDepartmentByCode(dto.getCreateOrgCode());
		if (saleDepartment != null){
			//如果是营业部 并且 是驻地部门 才调用
			 if(saleDepartment.checkStation()) {
					//调用中转接口 获得车辆放行ID
					String tOptTruckDepartId = webDepartureService.autoDepart(autoDto);
					deliverbillEntity.settOptTruckDepartId(tOptTruckDepartId);
			 }
		}else {	//非营业部 也进行调用
			//调用中转接口 获得车辆放行ID
			String tOptTruckDepartId = webDepartureService.autoDepart(autoDto);
			deliverbillEntity.settOptTruckDepartId(tOptTruckDepartId);
		}
		deliverbillEntity.setStatus(DeliverbillConstants.STATUS_CONFIRMED);//已确认
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(dto.getCreateOrgCode());
		if(org!= null && !org.checkSaleDepartment()){
			List<OrgAdministrativeInfoEntity> orgSubList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoSubByBizType(dto.getCreateOrgCode() , 
					BizTypeConstants.ORG_SALES_DEPARTMENT);
			
			if(orgSubList!=null && orgSubList.size()>0){
				OrgAdministrativeInfoEntity suborg = orgSubList.get(0);
				org = suborg;
				deliverbillEntity.setCreateOrgCode(org.getCode());
				deliverbillEntity.setCreateOrgName(org.getName());
			}else{
				//如果在下一级别找不到营业部  就需要寻找同级别的营业部。
				String salesCode = saleDepartmentService.queryArriveStationSaleCodeByTransferCenterCode(dto.getCreateOrgCode());
				if(StringUtils.isEmpty(salesCode)){
					
					deliverbillEntity.setCreateOrgCode(dto.getCreateOrgCode());
					deliverbillEntity.setCreateOrgName(dto.getCreateOrgName());
					
				}else{
					deliverbillEntity.setCreateOrgCode(salesCode);
					OrgAdministrativeInfoEntity org2 = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(salesCode);
					if(org2!=null){

						deliverbillEntity.setCreateOrgName(org2.getName());
					}
				}
			}
		}else{
			deliverbillEntity.setCreateOrgCode(org.getCode());
			deliverbillEntity.setCreateOrgName(org.getName());
		}
		
		deliverbillEntity.setOperator(dto.getCreateUserName());
		deliverbillEntity.setOperatorCode(dto.getCreateUserCode());
		deliverbillEntity.setOperateOrgCode(dto.getCreateOrgCode());
		deliverbillEntity.setOperateOrgName(dto.getCreateOrgName());
		deliverbillEntity.setOperateTime(new Date());
		
		deliverbillEntity.setFastWaybillQty(Integer.valueOf(0));
		deliverbillEntity.setTransferCenter(dto.getCreateOrgCode());
		deliverbillEntity.setCurrencyCode(WaybillConstants.RMB);
		deliverbillEntity.setDeliverType(DeliverbillConstants.NOMAL);
		
		deliverbillEntity = deliverbillDao.add(deliverbillEntity);
		
		if(dto.getList()!=null && ! dto.getList().isEmpty()){
			for (Iterator<WaybillExpressArrivalSheetDto>  iterator = list.iterator(); iterator.hasNext();) {
				WaybillExpressArrivalSheetDto dto2 = iterator.next();
				ArriveSheetEntity arriveEntity = new ArriveSheetEntity();
				try{
					PropertyUtils.copyProperties(arriveEntity, dto2.getArriveSheetEntityDto());
				}catch(Exception e){
					LOGGER.info("copyProperties",e);
				}
				DeliverbillDetailEntity deliverbillDetail = generateDeliverbillDetailFromWaybill(deliverbillEntity.getId(), dto2.getWaybillEntity(), 
						dto2.getGoodQtyTotal().intValue(), dto2.getActualFreightEntity(),arriveEntity);
				
				if(deliverbillDetail!=null){
					this.deliverbillDetailDao.add(deliverbillDetail);
					//快递确认派送单，派送推送轨迹--add by 231438 
					//调用接口，推送轨迹
					sendWaybillTrackService.packagingMethodForConfirm(deliverbillEntity,deliverbillDetail);
					
					ActualFreightEntity actualFreight = dto2.getActualFreightEntity();
					// 更新运单排单件数
					String actualFreightId = actualFreight.getId();
					int newArrangeGoodsQty = actualFreight.getArrangeGoodsQty() + deliverbillDetail.getArrangeGoodsQty();
					
					// 需要保证已排单件数大于0，且小于开单件数
					//newArrangeGoodsQty = newArrangeGoodsQty > actualFreight.getGoodsQty() ? actualFreight.getGoodsQty() : newArrangeGoodsQty;
					newArrangeGoodsQty = newArrangeGoodsQty > 0 ? newArrangeGoodsQty : 0;

					actualFreight = new ActualFreightEntity();
					actualFreight.setId(actualFreightId);
					actualFreight.setArrangeGoodsQty(newArrangeGoodsQty);

					this.actualFreightDao.updateByPrimaryKeySelective(actualFreight);
				}
			}
		}
		return dto;
	}
	
	private DeliverbillDetailEntity generateDeliverbillDetailFromWaybill(String deliverbillId, WaybillEntity waybill, int arrangeGoodsQty, 
			ActualFreightEntity actualFreight, ArriveSheetEntity arriveSheet){
			if(waybill!=null && arrangeGoodsQty>0 ){
				DeliverbillDetailEntity deliverbillDetail = new DeliverbillDetailEntity();
				deliverbillDetail.settSrvDeliverbillId(deliverbillId);
				deliverbillDetail.setSerialNo(deliverbillDetailDao.queryMaxSerialNoByDeliverbillId(deliverbillId) + 1);
				deliverbillDetail.setWaybillNo(waybill.getWaybillNo());
				deliverbillDetail.setPreArrangeGoodsQty(0);
				deliverbillDetail.setArrangeGoodsQty(arrangeGoodsQty);
				
				deliverbillDetail.setWeight(BigDecimalOperationUtil.div(waybill.getGoodsWeightTotal().multiply(new BigDecimal(arrangeGoodsQty)),
						new BigDecimal(waybill.getGoodsQtyTotal()), DeliverbillConstants.WEIGHT_PRECISION));
				deliverbillDetail.setGoodsVolumeTotal(BigDecimalOperationUtil.div(waybill.getGoodsVolumeTotal().multiply(new BigDecimal(arrangeGoodsQty)),
						new BigDecimal(waybill.getGoodsQtyTotal()), DeliverbillConstants.VOLUME_PRECISION));
				
				deliverbillDetail.setDimension(waybill.getGoodsSize());
				deliverbillDetail.setGoodsName(waybill.getGoodsName());
				deliverbillDetail.setWaybillGoodsQty(waybill.getGoodsQtyTotal());
				deliverbillDetail.setTransportType(waybill.getProductCode());
				
				if(actualFreight!=null){
					deliverbillDetail.setArriveTime(actualFreight.getArriveTime());
					deliverbillDetail.setIsAlreadyContact(FossConstants.INACTIVE );
					deliverbillDetail.setEstimatedDeliverTime(actualFreight.getDeliverDate());
					deliverbillDetail.setPaymentType(actualFreight.getPaymentType());
				}
				
				deliverbillDetail.setConsignee(waybill.getReceiveCustomerContact());
				deliverbillDetail.setConsigneeContact(waybill.getReceiveCustomerMobilephone());
				deliverbillDetail.setConsigneeAddress(waybill.getReceiveCustomerAddress());
				deliverbillDetail.setNotes(waybill.getInnerNotes());
				deliverbillDetail.setDeliverType(waybill.getReceiveMethod());
				deliverbillDetail.setPayAmount(waybill.getToPayAmount());
				deliverbillDetail.setFastWaybillFlag(  Short
						.parseShort(0 + ""));
				deliverbillDetail.setCurrencyCode(waybill.getCurrencyCode());
				deliverbillDetail.setGoodsPackage(waybill.getGoodsPackage());
				deliverbillDetail.setReturnBillType(waybill.getReturnBillType());
				
				if(arriveSheet!=null){
					deliverbillDetail.setDeliverRequire(arriveSheet.getDeliverRequire());
					deliverbillDetail.setIsNeedInvoice(arriveSheet.getIsNeedInvoice());
					deliverbillDetail.setArrivesheetNo(arriveSheet.getArrivesheetNo());
				}
				return deliverbillDetail;
			}else{
				return null;
			}
	}
	
	/**
	 * 
	 * 如果传入的件数 大于 可排单件数 那么就不生产派送单明细 抛出异常  ISSUE-4353
	 * @author 043258-foss-zhaobin
	 * @date 2013-10-23 下午2:09:09
	 */
	private void checkDeliverbillDetail(int arrangeGoodsQty, 
			ActualFreightEntity actualFreight){
		//如果传入的件数 大于 可排单件数 那么就不生产派送单明细 返回空  ISSUE-4353
		if(actualFreight != null && arrangeGoodsQty>(actualFreight.getArriveGoodsQty()-actualFreight.getArrangeGoodsQty()))
		{
			throw new WaybillValidateException("运单号"+actualFreight.getWaybillNo()+"不能排单，请剔除此运单后再进行提交！");
		}
	}
	
	/**
	 * 
	 * 生成新的到达联
	 * 
	 * @param printNum
	 * @param actualFreightEntity
	 * @param entity
	 * @param generateGoodsQty
	 * @author ibm-wangfei
	 * @date Jun 26, 2013 5:08:31 PM
	 */
	private void addNewArriveSheet(Integer printNum, ActualFreightEntity actualFreightEntity, 
			ArriveSheetEntity entity, int generateGoodsQty) {
		//entity.setArriveSheetGoodsQty(printNum);
		//到达提货人
		entity.setDeliverymanName(actualFreightEntity.getDeliverymanName());
		//证件类型
		entity.setIdentifyType(actualFreightEntity.getIdentifyType());
		//证件号码
		entity.setIdentifyCode(actualFreightEntity.getIdentifyCode());
		//生成到达联
		String arrivesheetNo = this.generateArriveSheet(entity);
		if (StringUtil.isEmpty(arrivesheetNo)) {
			LOGGER.info(entity.getWaybillNo() + "无法生成0件的到达联。");
			return;
		}
		//更新 actualFreight 表的 "生成到达联件数"字段 
		ActualFreightEntity actualFreight = new ActualFreightEntity();
		actualFreight.setWaybillNo(entity.getWaybillNo());
		actualFreight.setGenerateGoodsQty((actualFreightEntity.getGenerateGoodsQty()+generateGoodsQty)>actualFreightEntity.getGoodsQty()
																?actualFreightEntity.getGoodsQty() : actualFreightEntity.getGenerateGoodsQty()+generateGoodsQty);
		actualFreightDao.updateGenerateGoodsQtyByWaybillNo(actualFreight);
	}
	
	
	/**
	 * 
	 * 生成到达联
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-13 下午6:34:07
	 * @param entity waybillNo 运单号 arrivesheetNo 到达联编号 deliverymanname 提货人名称
	 *            identifyType 证件类型 identifyCode 证件号码 situation 签收情况
	 *            arriveSheetGoodsQty 到达联件数 signGoodsQty 签收件数 signNote 签收备注
	 *            signTime 签收时间 status 到达联状态 isPdaSign 是否PDA签到 operateTime
	 *            签收操作时间 operator 操作人 operatorCode 操作人编码 operateOrgName 操作部门名称
	 *            operateOrgCode 操作部门编码 destroyed 是否作废 isPrinted 是否打印 printtimes
	 *            打印次数 createUserName 创建人 createUserCode 创建人编码 createOrgName
	 *            创建部门 createOrgCode 创建部门编码 createTime 创建时间 isSentRequired 是否必送货
	 *            isNeedInvoice 是否需要发票 preNoticeContent 提前通知内容 isRfcing 是否审批中
	 * @return
	 * @see
	 */
	private String generateArriveSheet(ArriveSheetEntity entity) {
		if (entity.getArriveSheetGoodsQty() == 0) {
			return "";
		}
		
		//产生到达联编号
		entity.setArrivesheetNo(this.generateArriveSheetId(entity.getWaybillNo()));
		entity.setId(UUIDUtils.getUUID());
		entity.setActive(FossConstants.YES);
		if (!ArriveSheetConstants.STATUS_DELIVER.equals(entity.getStatus())) {
			entity.setStatus(ArriveSheetConstants.STATUS_GENERATE);
		}
		//将状态作为有效
		entity.setDestroyed(FossConstants.NO);
		
		entity.setIsPrinted(FossConstants.NO);
		entity.setPrinttimes(0);
		arrivesheetDao.addArriveSheetData(entity);
		return entity.getArrivesheetNo();
	}
	
	
	/**
	 * 
	 * 生成到达联编号
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-13 下午4:31:10
	 * @param waybillNo 运单号
	 * @return
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService
	 *      #generateArriveSheetId(java.lang.String)
	 */
	@Transactional
	public String generateArriveSheetId(String waybillNo) {
		Long maxNum = arrivesheetDao.queryMaxArriveSheetNoByWayBillNo(waybillNo);
		StringBuffer arriveSheetNo = new StringBuffer(waybillNo);
		if (maxNum != null) {
			String maxArriveSheetNo = maxNum.toString();
			//截取最后三位到达联编号
			String idString = maxArriveSheetNo.substring(maxArriveSheetNo.length() - THREE);
			Integer lastNum = Integer.valueOf(idString);
			//累加
			lastNum = lastNum + 1;
			//少三位 补0
			String id = lastNum.toString();
			if (id.length() < THREE) {
				for (int i = 0; i < THREE - id.length(); i++) {
					arriveSheetNo.append(ZERO);
				}
				arriveSheetNo.append(id);
			}
			return arriveSheetNo.toString();
		} else {
			return arriveSheetNo.append(BENGIN_NUM).toString();
		}
	}
	
	/**
	 * 到达联开始计数
	 */
	public static final String BENGIN_NUM = "001";
	/**
	 * 常量值3
	 */
	public static final int THREE = 3;
	/**
	 * 常量值0
	 */
	public static final String ZERO = "0";

	public void setArrivesheetDao(IArrivesheetDao arrivesheetDao) {
		this.arrivesheetDao = arrivesheetDao;
	}

	public void setActualFreightDao(IActualFreightDao actualFreightDao) {
		this.actualFreightDao = actualFreightDao;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService#queryCustomerByCondition(com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto)
	 */
	@Override
	public List<CustomerQueryConditionDto> queryCustomerByCondition(
			CustomerQueryConditionDto condition) {
		/**
		 * 定义临时存储查询条件的对象 防止查询正式CRM客户未果时，修改了查询条件， 导致查询临欠散客时出现查询不正确的问题
		 */
		CustomerQueryConditionDto tempConditionDto = new CustomerQueryConditionDto();
		// 将传来的值赋值给临时存储查询条件的对象
		try {
			// 使用拷贝的方式，防止出现值无法拷贝的情况
			PropertyUtils.copyProperties(tempConditionDto, condition);
		} catch (Exception e) {
			return null;
		}
		CustomerPaginationDto tempConditionDto2 = new CustomerPaginationDto();
		// 将传来的值赋值给临时存储查询条件的对象
		try {
			// 使用拷贝的方式，防止出现值无法拷贝的情况
			PropertyUtils.copyProperties(tempConditionDto2, condition);
		} catch (Exception e) {
			return null;
		}

		// 联系人编码
		String linkmanCode = StringUtil.defaultIfNull(tempConditionDto.getLinkmanCode());
		// 身份证号码
		String idCard = StringUtil.defaultIfNull(tempConditionDto.getIdCard());

		// 存储CRM正式客户、临客、散客的集合信息
		List<CustomerQueryConditionDto> allList = new ArrayList<CustomerQueryConditionDto>();
		List<CustomerQueryConditionDto> tempList = new ArrayList<CustomerQueryConditionDto>();

		tempConditionDto2.setPageNum(1);
		tempConditionDto2.setPageSize(WaybillConstants.CUSTOMER_COUNT + NumberConstants.NUMBER_10);
		
		//部门编码（因为在查询正式客户时，会根据部门编码查询出标杆编码，并设置给dto进行后续查询条件进行查询，因此需要在变更前先存储起来）
		String deptCode = tempConditionDto2.getDeptCode();
		// 这种情况下只要查询第一张表
		// 存储CRM正式客户、临客、散客的集合信息
		allList = customerService.queryExpCustomerByConditionByPage(tempConditionDto2);
		// 设置集合中对象为CRM正式客户
		allList = addQueryCustomerType(allList, WaybillConstants.CUSTOMER_TYPE_FORMAL);
		//固定客户与散客合并为一张表，因此如果固定客户可以获取信息，则不再查询散客。
		if (CollectionUtils.isEmpty(allList)) {
			/**
			 * 由于临欠散客没有联系人编码、身份证号， 因此，若该2项不为空，则直接返回空
			 */
			// 这种情况要查询第二张表
			if ("".equals(linkmanCode) && "".equals(idCard)) {
				tempConditionDto2.setPageSize(WaybillConstants.CUSTOMER_COUNT);
				//设置最原始的部门编码
				tempConditionDto2.setDeptCode(deptCode);
				// 散客和临客
				tempList = nonfixedCustomerService.queryCustomerByConditionByPage(tempConditionDto2);
				// 设置集合中对象为CRM临欠散客
				tempList = addQueryCustomerType(tempList, WaybillConstants.CUSTOMER_TYPE_TEMP);
			}
		}

		// 判断是否为空
		if (CollectionUtils.isNotEmpty(allList)) {
			if (CollectionUtils.isNotEmpty(tempList)) {
				// 设置为临欠或散客
				allList.addAll(tempList);
			}
		} else {
			// 集合非空判断
			if (CollectionUtils.isNotEmpty(tempList)) {
				allList = tempList;
			}
		}

		return allList;
	}
	


	/**
	 * 设置查询出的客户类型
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-26 下午7:41:38
	 */
	private List<CustomerQueryConditionDto> addQueryCustomerType(List<CustomerQueryConditionDto> custList,String type){
		//判断集合是否为空
		if(CollectionUtils.isNotEmpty(custList)){
			//遍历集合设置客户类型
			for (CustomerQueryConditionDto dto : custList) {
				dto.setCustomerType(type);
				//根据部门编码查询部门名称
				OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCode(StringUtil.defaultIfNull(dto.getDeptCode()));
				if(null != org){
					dto.setDeptName(org.getName());
				}
			}
		}
		return custList;
	}
	
	
	/**
	 * 客户服务
	 */
	private ICustomerService customerService;
	/**
	 * 散客、临客服务
	 */
	private INonfixedCustomerService nonfixedCustomerService;
	
	

	public void setNonfixedCustomerService(INonfixedCustomerService nonfixedCustomerService) {
		this.nonfixedCustomerService = nonfixedCustomerService;
	}

	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}
	
	/**
	 * 检查运单变更状态
	 * @param waybillId
	 */
	@Override
	public void checkWayBillChange(String waybillId){
		List<String> waybillRfcStatus = new ArrayList<String>();//创建对象
		waybillRfcStatus.add(WaybillRfcConstants.PRE_AUDIT);
		waybillRfcStatus.add(WaybillRfcConstants.PRE_ACCECPT);
		WaybillRfcEntity rfcEntity = waybillRfcDao.queryRfcEntityByWaybillId(waybillId, waybillRfcStatus);
		if (rfcEntity != null) {
			if (WaybillRfcConstants.PRE_AUDIT.equals(rfcEntity.getStatus())) {
				/**
				 * 该运单有运单变更单还未被审核，不能起草变更运单！
				 */
				throw new WaybillImportException("该运单有运单变更单还未被审核，不能返货开单");
			} else if (WaybillRfcConstants.PRE_ACCECPT.equals(rfcEntity.getStatus())) {
				/**
				 * 该运单有运单变更单还未被受理，不能起草变更运单！
				 */
				throw new WaybillImportException("该运单有运单变更单还未被受理，不能返货开单");
			}
		}
	}

	public void setWaybillPackBIService(IWaybillPackBIService waybillPackBIService) {
		this.waybillPackBIService = waybillPackBIService;
	}
	
	/*
	 * 以下均为生成快递运单时进行的校验或相关公共方法@start
	 */
	
	/*
	* 
	*总的校验方法(仅供电子运单开单计算适用 )
	*
	* @author BaiLei
	* @date 2014-09-05
	*/
	private void validateExpWaybill(WaybillEntity entity,WaybillExpressEntity expEntity){
		// PDA运单信息
		//validateWaybillNo(entity);
		
		// 整车约车校验
		//validateVehicleNumber(entity);

		// 重量、体积、件数校验
		validateExpWeightVolume(entity);

		// 目的站校验
		validateExpDestination(entity);

		// 产品校验
		validateExpProduct(entity);

		// 包装校验
		validateExpPack(entity);

		// 客户校验
		validateExpCustomer(entity);

		// 校验提货网点重量、体积上限
		validateExpVW(entity);

		// 付款方式校验
		validateExpPaymentMode(entity);
		
		//验证返单
		//validateReturnBill(bean);

		// 代收货款校验
		validateExpCod(entity);

		// 验证空运合票方式和航班类型不能为空
		//validateAir(entity);

		// 验证空运货物类型不能为空
		//validateAirGoodsType(bean);

		// 只允许合同客户才可以选择免费送货
		validateExpDeliverFree(entity);
		
		//验证重量与提货方式
		validateExpWeightDeliveryMode(entity);
		
		//检查试点城市和试点营业部的逻辑
		if(expEntity.getReturnType() != null &&
				WaybillConstants. WAYBILL_EXPRESSTYPE_RETURN_CARGO.equals(expEntity.getReturnType())){
			//do noting
		}else{
			validateExpressCity(entity,expEntity);
		}
		
		//检查保险声明价值
		validateExpInsuranceFee(entity);
		
		//检查德邦客户和发货人工号及内部带货费用承担部门
		//validateDepponExpressEmpCode(entity);	
		
		//校验营销活动是否开启
		//validateExpActiveStart(entity); 
		
		//校验优惠券是否开启
		//validateExpPromotionsCode(entity);
	}
	
	
	/*
	* 
	*总的校验方法(仅供电子运单更改单计算适用 )
	*
	* @author BaiLei
	* @date 2014-09-05
	*/
	private void validateExpChangeWaybill(WaybillEntity entity,WaybillExpressEntity expEntity){
		
		// 整车约车校验
		//validateVehicleNumber(entity);

		// 重量、体积、件数校验
		validateExpWeightVolume(entity);

		// 目的站校验
		if(StringUtils.isBlank(entity.getCustomerPickupOrgCode()) || StringUtils.isBlank(entity.getTargetOrgCode())){
			LOGGER.error(WaybillValidateException.DESTINATION_NULL);
			throw new WaybillValidateException(WaybillValidateException.DESTINATION_NULL);
		}

		// 产品校验
		validateExpProduct(entity);

		// 包装校验
		validateExpPack(entity);

		// 客户校验
		validateExpCustomer(entity);

		// 校验提货网点重量、体积上限
		validateExpVW(entity);

		// 付款方式校验
		validateExpPaymentMode(entity);
		
		//验证返单
		//validateReturnBill(bean);

		// 代收货款校验
		validateExpCod(entity);

		// 验证空运合票方式和航班类型不能为空
		//validateAir(entity);

		// 验证空运货物类型不能为空
		//validateAirGoodsType(bean);

		// 只允许合同客户才可以选择免费送货
		validateExpDeliverFree(entity);
		
		//验证重量与提货方式
		validateExpWeightDeliveryMode(entity);
		
		/*
		//检查试点城市和试点营业部的逻辑(更改单没有此校验)
		if(expEntity.getReturnType() != null &&
				WaybillConstants. WAYBILL_EXPRESSTYPE_RETURN_CARGO.equals(expEntity.getReturnType())){
			//do noting
		}else{
			validateExpressCity(entity,expEntity);
		}*/
		
		//检查保险声明价值
		validateExpInsuranceFee(entity);
		
		//检查德邦客户和发货人工号及内部带货费用承担部门
		//validateDepponExpressEmpCode(entity);	
		
		//校验营销活动是否开启
		//validateExpActiveStart(entity); 
		
		//校验优惠券是否开启
		//validateExpPromotionsCode(entity);
	}
	
	/*
	* 
	*快递重量、体积、件数校验
	*
	* @author BaiLei
	* @date 2014-09-05
	*/
	public void validateExpWeightVolume(WaybillEntity entity) {
		if(this.onlineDetermineIsExpressByProductCode(entity.getProductCode(), entity.getBillTime())){
			//总件数不能为0
			if (entity.getGoodsQtyTotal()==null || entity.getGoodsQtyTotal().intValue() == 0) {
				LOGGER.error(WaybillValidateException.NULL_GOODSQTYTOTAL);
				throw new WaybillValidateException(WaybillValidateException.NULL_GOODSQTYTOTAL);
			}
			//重量不能为0
			if (entity.getGoodsWeightTotal()==null || entity.getGoodsWeightTotal().compareTo(BigDecimal.ZERO) == 0) {
				LOGGER.error(WaybillValidateException.NULL_GOODSWEIGHTTOTAL);
				throw new WaybillValidateException(WaybillValidateException.NULL_GOODSWEIGHTTOTAL);
			}
			//子母件件数
			BigDecimal czmQty = CollectionUtils.isEmpty(entity.getWaybillNos())  ? BigDecimal.ONE : BigDecimal.valueOf(entity.getWaybillNos().size());
			//毛重小于等于50kg*件数，体积小于等于0.3*件数
			BigDecimal qtyWeightUpperLimit = BigDecimal.valueOf(ExpWaybillConstants.WEIGHT_UPPER_LIMIT).multiply(BigDecimal.valueOf(entity.getGoodsQtyTotal()));
			BigDecimal qtyVolumeUpperLimit = BigDecimal.valueOf(ExpWaybillConstants.VOLUME_UPPER_LIMIT).multiply(BigDecimal.valueOf(entity.getGoodsQtyTotal()));
			if(entity.getGoodsWeightTotal() !=null && qtyWeightUpperLimit.compareTo(entity.getGoodsWeightTotal().divide(czmQty,NumberConstants.NUMBER_3,BigDecimal.ROUND_HALF_UP)) < 0){ 
				LOGGER.error(WaybillValidateException.EXPWAYBILL_WEIGHT_OVERLIMIT);
				throw new WaybillValidateException(WaybillValidateException.EXPWAYBILL_WEIGHT_OVERLIMIT);
			}
			if(entity.getGoodsVolumeTotal() != null && qtyVolumeUpperLimit.compareTo(entity.getGoodsVolumeTotal().divide(czmQty,NumberConstants.NUMBER_3,BigDecimal.ROUND_HALF_UP))<0){
				LOGGER.error(WaybillValidateException.EXPWAYBILL_VOLUME_OVERLIMIT);
				throw new WaybillValidateException(WaybillValidateException.EXPWAYBILL_VOLUME_OVERLIMIT);
			}
		}	
	}
		
	/*
	 * 目的站校验
	 * 
	 * @author BaiLei
	 * @date 2014-09-05
	 */	
	public void validateExpDestination(WaybillEntity waybillEntity) {
		if(StringUtils.isBlank(waybillEntity.getCustomerPickupOrgCode()) || StringUtils.isBlank(waybillEntity.getTargetOrgCode())){
			LOGGER.error(WaybillValidateException.DESTINATION_NULL);
			throw new WaybillValidateException(WaybillValidateException.DESTINATION_NULL);
		}
		
		SaleDepartmentEntity saleDeprtTemp =saleDepartmentService.querySaleDepartmentByCode(waybillEntity.getCustomerPickupOrgCode());
		
		if(saleDeprtTemp == null){
			throw new WaybillValidateException(WaybillValidateException.DESTINATION_NULL);
		}
		
		//是否支持开多件
		/*if(waybillEntity.getGoodsQtyTotal() != null && waybillEntity.getGoodsQtyTotal() > 1){
			//如果不支持开多件，则抛出异常
			if(!FossConstants.YES.equals(saleDeprtTemp.getCanExpressOneMany())){
				throw new WaybillValidateException(WaybillValidateException.CANNOT_CREATE_ONETOMANY_EXP);
			}
		}*/
		
		//判断是否能开代收货款
		if(waybillEntity.getCodAmount() !=null && waybillEntity.getCodAmount().compareTo(BigDecimal.ZERO)>0){
			if(!FossConstants.YES.equals(saleDeprtTemp.getCanAgentCollected())){
				throw new WaybillValidateException(WaybillValidateException.PICKUP_STATSION_CANNOT_COD);
			}
		}
		
		//营业部是否支持签收单返单
		if(waybillEntity.getReturnBillType() != null && StringUtils.isNotBlank(waybillEntity.getReturnBillType())
				&& !waybillEntity.getReturnBillType().equals(WaybillConstants.NOT_RETURN_BILL)){
			if(!FossConstants.YES.equals(saleDeprtTemp.getCanExpressReturnSignBill())){
				throw new WaybillValidateException(WaybillValidateException.SALESDEPT_CAN_NOT_RETURN_SIGNBILL);
			}
		}
		
	}

	/*
	 * 产品性质校验
	 * 
	 * @author BaiLei
	 * @date 2014-09-05
	 */	
	public void validateExpProduct(WaybillEntity entity) {
		if(entity.getProductCode() == null ||"".equals(entity.getProductCode())){
			LOGGER.error(WaybillValidateException.PRODUCT_CODE_NULL);
			throw new WaybillValidateException(WaybillValidateException.PRODUCT_CODE_NULL);
		}
	}

	/*
	 * 产品包装校验-包装件数不能大于开单件数
	 * 
	 * @author BaiLei
	 * @date 2014-09-05
	 */	
	public void validateExpPack(WaybillEntity entity) {
		Integer qtyTotal = entity.getGoodsQtyTotal();// 总件数

		// 不允许包装各项的数值为null
		if (null == entity.getPaperNum() || null == entity.getWoodNum() || 
				null == entity.getFibreNum() || null == entity.getSalverNum() || null == entity.getMembraneNum()) {
			LOGGER.error(WaybillValidateException.GOOD_PACKAGE_NULL);
			throw new WaybillValidateException(WaybillValidateException.GOOD_PACKAGE_NULL);
		}
		
		// 不允许包装信息全部为空
		if (0 == entity.getPaperNum().intValue() && 0 == entity.getWoodNum().intValue() 
				&& 0 == entity.getFibreNum().intValue() && 0 == entity.getSalverNum().intValue() 
				&& 0 == entity.getMembraneNum().intValue() && StringUtils.isEmpty(entity.getOtherPackage())) {
			LOGGER.error(WaybillValidateException.GOOD_PACKAGE_NULL);
			throw new WaybillValidateException(WaybillValidateException.GOOD_PACKAGE_NULL);
		}
		
		Integer paper = Integer.valueOf(entity.getPaperNum());// 纸
		Integer wood = Integer.valueOf(entity.getWoodNum());// 木
		Integer fibre = Integer.valueOf(entity.getFibreNum());// 纤
		Integer salver = Integer.valueOf(entity.getSalverNum());// 托
		Integer membrane = Integer.valueOf(entity.getMembraneNum());// 膜
		Integer packTotal = paper + wood + fibre + salver + membrane;

		if (packTotal > qtyTotal) {
			LOGGER.error(WaybillValidateException.GOOD_PACKAGE_OVERLIMIT);
			throw new WaybillValidateException(WaybillValidateException.GOOD_PACKAGE_OVERLIMIT);
		}
	}

	/*
	 * 客户信息校验 -校验客户联系人联系方式地址
	 * 
	 * @author BaiLei
	 * @date 2014-09-05
	 */	
	public void validateExpCustomer(WaybillEntity entity) {
		
		int lengthLimitEight = EIGHT;
		int lengthLimitEleven = ELEVEN;
		
		//发货联系人不能为空
		if(StringUtils.isEmpty(entity.getDeliveryCustomerContact())){
			throw new WaybillValidateException(WaybillValidateException.DELIVERY_CUSTOER_CONTACT_NULL);
		}
		//收货联系人不能为空
		if(StringUtils.isEmpty(entity.getReceiveCustomerContact())){
			throw new WaybillValidateException(WaybillValidateException.RECEIVE_CUSTOER_CONTACT_NULL);
		}
		
		//发货人手机号码规格校验
		if (StringUtils.isNotEmpty(entity.getDeliveryCustomerMobilephone())) {
			if (!"".equals(entity.getDeliveryCustomerMobilephone().trim())) {
				int phoneLength = entity.getDeliveryCustomerMobilephone().trim().length();

				if (phoneLength != lengthLimitEight && phoneLength != lengthLimitEleven) {
					LOGGER.error(WaybillValidateException.DELIVERY_MOBILEPHONE_LENGTH_ILLEGAL);
					throw new WaybillValidateException(WaybillValidateException.DELIVERY_MOBILEPHONE_LENGTH_ILLEGAL);
				}

				if (phoneLength == lengthLimitEleven) {
					if (!entity.getDeliveryCustomerMobilephone().startsWith("1")) {
						LOGGER.error(WaybillValidateException.DELIVERY_MOBILEPHONE_FIRST_NUMBER_ILLEGAL);
						throw new WaybillValidateException(WaybillValidateException.DELIVERY_MOBILEPHONE_FIRST_NUMBER_ILLEGAL);
					}
				}
			}
		}
		
		//收货人手机号码规格校验
		if (StringUtils.isNotEmpty(entity.getReceiveCustomerMobilephone())) {
			if (StringUtils.isNotBlank(entity.getReceiveCustomerMobilephone())) {
				int phoneLength = entity.getReceiveCustomerMobilephone().trim().length();

				if (phoneLength != lengthLimitEight && phoneLength != lengthLimitEleven) {
					LOGGER.error(WaybillValidateException.RECEIVER_MOBILEPHONE_LENGTH_ILLEGAL);
					throw new WaybillValidateException(WaybillValidateException.RECEIVER_MOBILEPHONE_LENGTH_ILLEGAL);
				}

				if (phoneLength == lengthLimitEleven) {
					if (!entity.getReceiveCustomerMobilephone().startsWith("1")) {
						LOGGER.error(WaybillValidateException.RECEIVER_MOBILEPHONE_FIRST_NUMBER_ILLEGAL);
						throw new WaybillValidateException(WaybillValidateException.RECEIVER_MOBILEPHONE_FIRST_NUMBER_ILLEGAL);
					}
				}
			}
		}
		
		//发货客户电话或手机必填一项
		if (StringUtils.isEmpty(entity.getDeliveryCustomerMobilephone()) && StringUtils.isEmpty(entity.getDeliveryCustomerPhone())) {
			throw new WaybillValidateException(WaybillValidateException.DELIVERY_CONTACT_NULL);
		}
		//收货客户电话或手机必填一项
		if (StringUtils.isEmpty(entity.getReceiveCustomerMobilephone()) && StringUtils.isEmpty(entity.getReceiveCustomerPhone())) {
			throw new WaybillValidateException(WaybillValidateException.RECEIVER_CONTACT_NULL);
		}

		// 原件签收单返回
		if (WaybillConstants.RETURNBILLTYPE_ORIGINAL.equals(entity.getReturnBillType())) {
			if(StringUtils.isEmpty(entity.getDeliveryCustomerProvCode()) || StringUtils.isEmpty(entity.getDeliveryCustomerCityCode())
					|| StringUtils.isEmpty(entity.getDeliveryCustomerAddress())){			
				throw new WaybillValidateException(WaybillValidateException.DELIVERY_CUSTOMER_ADDRESS_NULL);
			}
		}
		
		// 提货方式为空
		if(entity.getReceiveMethod()==null){
			throw new WaybillValidateException(WaybillValidateException.RECEIVE_METHOD_NULL);
		}
		
		// 收货人地址不能为空
		String code = entity.getReceiveMethod();
		if (WaybillConstants.DELIVER_NOUP.equals(code) || WaybillConstants.DELIVER_FREE.equals(code) || WaybillConstants.DELIVER_STORAGE.equals(code) || WaybillConstants.DELIVER_UP.equals(code) || WaybillConstants.DELIVER_FREE_AIR.equals(code)
				|| WaybillConstants.DELIVER_NOUP_AIR.equals(code) || WaybillConstants.DELIVER_UP_AIR.equals(code) || WaybillConstants.DELIVER_INGA_AIR.equals(code)) {
			if(StringUtils.isEmpty(entity.getReceiveCustomerProvCode()) || StringUtils.isEmpty(entity.getReceiveCustomerAddress()) ){
				throw new WaybillValidateException(WaybillValidateException.RECEIVER_CUSTOMER_ADDRESS_NULL);
			}
		}
		
	}

	/*
	 * 校验提货网点单票以及单件重量与体积上限
	 * 
	 * @author BaiLei
	 * @date 2014-09-05
	 */	
	public void validateExpVW(WaybillEntity entity) {
		//对内备注
		int innerNotes = StringUtil.defaultIfNull(entity.getInnerNotes()).length();
		//储运事项 
		int transNotes = StringUtil.defaultIfNull(entity.getTransportationRemark()).length();
		
		if(innerNotes > CLOB_LIMIT){
			///对内备注录入错误：最大字符不能超过1300个！
			throw new WaybillValidateException(WaybillValidateException.INNERNOTES_OVERLIMIT);
		}
		
		if(transNotes > CLOB_LIMIT){
			//储运事项 录入错误：最大字符不能超过1300个！
			throw new WaybillValidateException(WaybillValidateException.TRANSNOTES_OVERLIMIT);
		}
		
		BigDecimal goodsWeight = entity.getGoodsWeightTotal();// 总重量
		BigDecimal goodsVolume = entity.getGoodsVolumeTotal();// 总体积
		BigDecimal goodsQty = new BigDecimal(entity.getGoodsQtyTotal());// 总件数

		BigDecimal pieceWeight = goodsWeight.divide(goodsQty, 2, BigDecimal.ROUND_HALF_EVEN);// 单件重量
		//goodsVolume为空时置pieceVolume为0，防止非空异常
		BigDecimal pieceVolume = (goodsVolume == null) ?BigDecimal.ZERO :
			(goodsVolume.divide(goodsQty, 2, BigDecimal.ROUND_HALF_EVEN));// 单件体积
		
		//提货网点
		SaleDepartmentEntity pickupStationSaleDept = saleDepartmentService.querySaleDepartmentByCode(entity.getCustomerPickupOrgCode());
		
		/**
		 * 整车不校验重量和体积
		 */
		if (pickupStationSaleDept != null && WaybillConstants.NO.equals(entity.getIsWholeVehicle())) {
			if (pickupStationSaleDept.getSinglePieceLimitkg() != null) {
				// 单件重量上限
				BigDecimal singlePieceLimitkg = BigDecimal.valueOf(pickupStationSaleDept.getSinglePieceLimitkg()).divide(new BigDecimal(NumberConstants.NUMBER_100));
				if (pieceWeight.compareTo(singlePieceLimitkg) > 0) {
					throw new WaybillValidateException(WaybillValidateException.SINGLE_PIECE_WEIGHT_OVER_LIMIT);
				}
			}

			if (pickupStationSaleDept.getSinglePieceLimitvol() != null) {
				// 单件体积上限
				BigDecimal singlePieceLimitvol = BigDecimal.valueOf(pickupStationSaleDept.getSinglePieceLimitvol()).divide(new BigDecimal(NumberConstants.NUMBER_100));
				if (pieceVolume.compareTo(singlePieceLimitvol) > 0) {
					throw new WaybillValidateException(WaybillValidateException.SINGLE_PIECE_VOLUME_OVER_LIMIT);
				}
			}

			if (pickupStationSaleDept.getSingleBillLimitkg() != null) {
				// 单票重量上限
				BigDecimal singleBillLimitkg = BigDecimal.valueOf(pickupStationSaleDept.getSingleBillLimitkg()).divide(new BigDecimal(NumberConstants.NUMBER_100));
				if (goodsWeight.compareTo(singleBillLimitkg) > 0) {
					throw new WaybillValidateException(WaybillValidateException.SINGLE_BILL_WEIGHT_OVER_LIMIT);
				}
			}

			if (pickupStationSaleDept.getSingleBillLimitvol() != null) {
				// 单票体积上限
				BigDecimal singleBillLimitvol = BigDecimal.valueOf(pickupStationSaleDept.getSingleBillLimitvol()).divide(new BigDecimal(NumberConstants.NUMBER_100));
				if (goodsVolume != null && singleBillLimitvol != null && goodsVolume.compareTo(singleBillLimitvol) > 0) {
					throw new WaybillValidateException(WaybillValidateException.SINGLE_BILL_VOLUME_OVER_LIMIT);
				}
			}
		}		
	}
		
	/*
	 * 支付方式校验 -验证提货方式为机场自提时，不能开到付
	 * 
	 * @author BaiLei
	 * @date 2014-09-05
	 */	
	public void  validateExpPaymentMode(WaybillEntity entity){
		if (entity.getPaidMethod() == null) {
			throw new WaybillValidateException(WaybillValidateException.PAID_METHOD_NULL);
		}

		if(WaybillConstants.MONTH_PAYMENT.equals(entity.getPaidMethod())){
			CusBargainVo vo=new CusBargainVo();
			vo.setExpayway(WaybillConstants.MONTH_END);
			vo.setCustomerCode(entity.getDeliveryCustomerCode());
			vo.setBillDate(new Date());
			vo.setBillOrgCode(entity.getReceiveOrgCode());
			CusBargainEntity cusBargain = cusBargainService.queryCusBargainByVoExp(vo);

			if(cusBargain == null){
				//该客户非合同客户不能开月结运单
				throw new WaybillValidateException(WaybillValidateException.PAID_METHOD_CT_ILLEGAL);
			}
		}

		//提货方式为机场自提，付款方式不能选择到付
		if (WaybillConstants.AIRPORT_PICKUP.equals(entity.getReceiveMethod())) {
			if (entity.getPaidMethod() != null) {
				if (WaybillConstants.ARRIVE_PAYMENT.equals(entity.getPaidMethod())) {
					throw new WaybillValidateException(WaybillValidateException.PAID_METHOD_FC_ILLEGAL);
				}
			}

			//提货方式为【机场自提】时，到付金额必须为0
			if (entity.getToPayAmount().compareTo(BigDecimal.ZERO) > 0) {
				throw new WaybillValidateException(WaybillValidateException.AIRPORT_PICKUP_TOPAY_AMOUNT_ILLEGAL);
			}
		}
		
		if(!WaybillConstants.YES.equals(entity.getIsWholeVehicle()))
		{
			if (WaybillConstants.ARRIVE_PAYMENT.equals(entity.getPaidMethod())) {
				//自有网点是否可以货到付款
			SaleDepartmentEntity pickupStationSaleDept = saleDepartmentService.querySaleDepartmentByCode(entity.getCustomerPickupOrgCode());
				if(pickupStationSaleDept != null ){
					if(!FossConstants.YES.equals(pickupStationSaleDept.getCanCashOnDelivery())){
					throw new WaybillValidateException(WaybillValidateException.PICKUP_STATSION_CANNOT_CASH_ON_DELIVERY);
				}
				}else{
					//外发网点
					OuterBranchParamsDto dto = new OuterBranchParamsDto();
					dto.setAgentDeptCode(entity.getCustomerPickupOrgCode());
					List<OuterBranchEntity> outerBranchEntityList = vehicleAgencyDeptService.queryOuterBranchs(dto);
					if(CollectionUtils.isNotEmpty(outerBranchEntityList)){
						OuterBranchEntity outerBranchEntity = outerBranchEntityList.get(0);
						if(!FossConstants.YES.equals(outerBranchEntity.getArriveCharge())){
							throw new WaybillValidateException(WaybillValidateException.PICKUP_STATSION_CANNOT_CASH_ON_DELIVERY);
			}
		}
				}
			}
		}

		// 空运以及偏线无法选择网上支付
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(entity.getProductCode()) || 
				ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(entity.getProductCode())) {
			if (WaybillConstants.ONLINE_PAYMENT.equals(entity.getPaidMethod())) {
				throw new WaybillValidateException(WaybillValidateException.PAID_METHOD_OL_ILLEGAL);
			}
		}

		//临欠、散客开单付款方式为临时欠款时，客户编码不允许为空
		if (WaybillConstants.TEMPORARY_DEBT.equals(entity.getPaidMethod())) {
			//判断客户编码是否为空
			if (StringUtil.isEmpty(entity.getDeliveryCustomerCode())) {
				throw new WaybillValidateException(WaybillValidateException.CUSTOMER_CODE_NULL);
			}
		}
	}
		
	/*
	 * 代收货款校验
	 * 
	 * @author BaiLei
	 * @date 2014-09-05
	 */
	public void validateExpCod(WaybillEntity entity){	
		//代收货款为0，不能进行代收业务校验不处理（开单界面有此校验）
		
		//代收货款不为0的处理
		if(entity.getCodAmount() != null && entity.getCodAmount().compareTo(BigDecimal.ZERO) > 0){
			if(StringUtils.isBlank(entity.getRefundType())){
				//退款类型不能为空
				throw new WaybillValidateException(WaybillValidateException.CUSTOMER_CODE_NULL);
			}else{
				//除审核退的代收货款业务类型必须填写客户银行账号信息
				if(!WaybillConstants.REFUND_TYPE_VALUE.equals(entity.getRefundType())){
					if(StringUtils.isBlank(entity.getAccountName()) || StringUtils.isBlank(entity.getAccountCode())){
						throw new WaybillValidateException(WaybillValidateException.CUSTOMER_CODE_NULL);
					}
				}
			}
			
			//目的站所选网点不能开代收货款
			SaleDepartmentEntity saleDept = saleDepartmentService.querySimpleSaleDepartmentByCode(entity.getCustomerPickupOrgCode());
			if(!WaybillConstants.YES.equals(saleDept.getCanAgentCollected())){
				throw new WaybillValidateException(WaybillValidateException.PICKUP_STATSION_CANNOT_COD);
			}
				
			//校验银行信息
			validateExpBankInfo(entity);

		}
	}

	/*
	 * 校验银行信息
	 * 
	 * @author BaiLei
	 * @date 2014-09-05
	 */
	public void validateExpBankInfo(WaybillEntity entity){
		if (entity == null || StringUtils.isBlank(entity.getDeliveryCustomerCode())) {
			throw new WaybillValidateException(WaybillValidateException.CUSTOMER_CODE_NULL);
		} else {
				CusAccountEntity cusAccountEntity = queryEWaybillCusAccountInfo(entity);
				//代收大于0时，不允许帐号实体信息为空
				if(entity.getCodAmount().compareTo(BigDecimal.ZERO)>0 && cusAccountEntity == null){
					throw new WaybillValidateException(WaybillValidateException.CUSTOMER_ACCOUNT_NULL);
				}
				if (entity != null) {
					if (cusAccountEntity.getAccountNature() == null || "".equals(cusAccountEntity.getAccountNature())) {
						throw new WaybillValidateException(WaybillValidateException.ACCOUNT_NATURE_NULL);
					} else {
						// 即日退只能选择对私账户
						if (WaybillConstants.REFUND_TYPE_ONE_DAY.equals(entity.getRefundType())&& 
								!DictionaryValueConstants.CRM_ACCOUNT_NATURE_PRIVATE_ACCOUNT.equals(cusAccountEntity.getAccountNature())) {
							throw new WaybillValidateException(WaybillValidateException.REFUND_TYPE_R1_NOT_PRIVATE_ACCOUNT);
						} 
					}
				}
		}		
	}

	/*
	 * 电子运单通过银行帐号查询CusAccountEntity
	 * （电子运单不允许通过客户编码查询）
	 * @author BaiLei
	 * @date 2014-09-05
	 */
	public CusAccountEntity queryEWaybillCusAccountInfo(WaybillEntity entity){
		CusAccountEntity cusAccountEntity = null;
		if(StringUtils.isBlank(entity.getDeliveryCustomerCode())){
			throw new  WaybillValidateException(WaybillValidateException.CUSTOMER_CODE_NULL);
		}
		List<CusAccountEntity> cusAccountList = cusAccountService.
				queryAccountLatestNewInfoByCustCode(entity.getDeliveryCustomerCode());
		
		if(cusAccountList == null){
			//throw new WaybillValidateException(WaybillValidateException.CUSTOMER_ACCOUNT_NULL);
			return null;
		}else{
			for(CusAccountEntity cusAccountTemp: cusAccountList){
				if(cusAccountTemp.getAccountNo().equals(entity.getAccountCode())){
					cusAccountEntity = cusAccountTemp;
					break;
				}
			}
		}
		if(cusAccountEntity == null){
			return null;
		}else{
			return cusAccountEntity; 
		}
	}
		
	/*
	 * 只允许合同客户才可以选择免费送货
	 * 
	 * @author BaiLei
	 * @date 2014-09-05
	 */
	public void validateExpDeliverFree(WaybillEntity entity){
		if(entity.getReceiveMethod()==null){
			throw new WaybillValidateException(WaybillValidateException.RECEIVE_METHOD_NULL);
		}
		
		if (WaybillConstants.DELIVER_FREE.equals(entity.getReceiveMethod()) || 
				WaybillConstants.DELIVER_FREE_AIR.equals(entity.getReceiveMethod())) {
				CusBargainEntity cusBargainEntity = cusBargainService.queryCusBargainByCustCode(entity.getDeliveryCustomerCode());
				//只有合同客户才允许免费派送
				if(cusBargainEntity == null || StringUtils.isBlank(cusBargainEntity.getBargainCode())){
					throw new WaybillValidateException(WaybillValidateException.CUSTOMER_BARGAIN_NULL);
				}
		}
	}		
		
	/*
	 * 验证重量与提货方式
	 * 
	 * @author BaiLei
	 * @date 2014-09-05
	 */
	public void validateExpWeightDeliveryMode(WaybillEntity entity){
		//货物总重量
		BigDecimal goodsWeightTotal=entity.getGoodsWeightTotal();
		Integer goodsNum=entity.getGoodsQtyTotal();
		/**
		 * 单件重量大于50kg
		 */
		if (goodsWeightTotal != null && goodsNum!=null && goodsNum != 0 && (Double.parseDouble(goodsWeightTotal+"")/goodsNum.intValue()) > FIFTY) {
			/**
			 * 如果提货方式是“送货上楼”
			 */
			if(entity.getReceiveMethod()!=null && WaybillConstants.DELIVER_UP.equals(entity.getReceiveMethod())){
			//	throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.50kgNotDoorToDoor"));
			}
		}				
	}
	
	/**
	 * 根据营业部编码得到营业部的试点城城名称 code 类型等详细信息
	 * @author BaiLei
	 * @date 2014-09-05
	 */
	public SalesDepartmentCityDto querySalesDepartmentCityInfo(String salesDepartmentCode){
		 
		ExpressCityResultDto expressCityResultDto = expressCityService.queryExpressCityTypeByOrgCode(salesDepartmentCode);
		SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(salesDepartmentCode);
			
		SalesDepartmentCityDto result  = covertToSalesDepartmentCityDto(expressCityResultDto);
		result.setSalesDepartmentCode(salesDepartmentCode);
		if(saleDepartmentEntity!=null){
			//营业部是否可以快递接货，如果是的话 就是试点营业部
			result.setTestSalesDepartment(StringUtil.defaultIfNull(saleDepartmentEntity.getCanExpressPickupToDoor()));
		}
		return result;
	}		
	
	/**
	 * 将ExpressCityResultDto转化为SalesDepartmentCityDto
	 * @author BaiLei
	 * @date 2014-09-05
	 */
	private SalesDepartmentCityDto covertToSalesDepartmentCityDto(ExpressCityResultDto expressCityResultDto){
		SalesDepartmentCityDto result = new SalesDepartmentCityDto();
		if(expressCityResultDto!=null){
			result.setCityCode(expressCityResultDto.getCityCode());
			result.setCityName(expressCityResultDto.getCityName());
			result.setCityType(expressCityResultDto.getType());
			result.setProvCode(expressCityResultDto.getProvCode());
			result.setProvName(expressCityResultDto.getProvName());
		}
		return result;
	}
	
	/**
	 * 检查试点城市和试点营业部的逻辑
	 * 
	 * @author BaiLei
	 * @date 2014-09-05
	 */
	public void validateExpressCity(WaybillEntity waybillEntity, WaybillExpressEntity waybillExpressEntity){
		//代收货款不能超过系统配置的最大值
		ConfigurationParamsEntity config = pkpsysConfigService.queryByConfCode(ExpWaybillConstants.EXPRESS_CODE_FEE_UP);
		//判断是否为空，如果为空默认20000
		String value = null;
		if(config != null){
			value = config.getConfValue();
		}else{
			value = String.valueOf("20000");
		}
		if(waybillEntity.getCodAmount()!=null && waybillEntity.getCodAmount().compareTo(new BigDecimal(value)) > 0){
			//带参数的异常是不是这样抛出，待确认
			throw new WaybillValidateException(WaybillValidateException.COD_OVERLIMIT,value);
		}

		//收货部门不能为空
		if(StringUtils.isEmpty(waybillEntity.getReceiveOrgCode())){ 
			throw new WaybillValidateException(WaybillValidateException.RECEIVE_ORG_NULL);
		}
		
		//返单或者返货的情况下，必须填写原始单号
		if(StringUtils.isNotBlank(waybillExpressEntity.getReturnType())){
			if(StringUtils.isBlank(waybillExpressEntity.getOriginalWaybillNo())){
				throw new WaybillValidateException(WaybillValidateException.ORIGINAL_WAYBILL_NO_NULL);
			}
		}
		
		/**
		 * 试点到试点能开即日退和三日退，
		 * 试点到非试点、试点到快递代理、非试点到试点、
		 * 非试点到非试点只能开单三日退，
		 * 非试点到快递代理无代收业务。
		 */
		//ExpressCityResultDto expressCreateCityResultDto = expressCityService.queryExpressCityTypeByOrgCode(waybillEntity.getReceiveOrgCode());
		//SalesDepartmentCityDto  createDto = covertToSalesDepartmentCityDto(expressCreateCityResultDto);
		
		SalesDepartmentCityDto createDto = querySalesDepartmentCityInfo(waybillEntity.getCreateOrgCode());
		
		//ExpressCityResultDto expressEndCityResultDto = expressCityService.queryExpressCityTypeByOrgCode(waybillEntity.getReceiveOrgCode());
		//SalesDepartmentCityDto  endDto = covertToSalesDepartmentCityDto(expressEndCityResultDto);
		
		SalesDepartmentCityDto endDto = querySalesDepartmentCityInfo(waybillEntity.getCustomerPickupOrgCode());
		
		//SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentInfoByCode(endDto.getSalesDepartmentCode());
		//SalesDepartmentCityDto salesDepartmentCityDto  = new SalesDepartmentCityDto();
		//salesDepartmentCityDto.setSalesDepartmentCode(endDto.getSalesDepartmentCode());
		
		//SalesDepartmentCityDto result = querySalesDepartmentCityInfo(endDto.getSalesDepartmentCode());
		
		//if(saleDepartmentEntity!=null && saleDepartmentEntity!=null){
		//	//营业部是否可以快递接货，如果是的话 就是试点营业部
		//	endDto.setTestSalesDepartment(StringUtil.defaultIfNull(saleDepartmentEntity.getCanExpressPickupToDoor()));
		//	result.setTestSalesDepartment(StringUtil.defaultIfNull(saleDepartmentEntity.getCanExpressPickupToDoor()));
		//	//bean.setTargetSalesDepartmentCityDto(result);
		//	endDto = result;
		//}
		
		if(createDto==null){
			//对不起，您所在的部门不能开快递运单
			throw new WaybillValidateException(WaybillValidateException.CAN_NOT_EXPRESS_CREATESALES_NULL);
		}else if(ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(createDto.getCityType()) && 
				!FossConstants.YES.equals(createDto.getTestSalesDepartment())){
			//试点城市 非试点营业部不能开单
			throw new WaybillValidateException(WaybillValidateException.CAN_NOT_EXPRESS_SALEDEPT_UNTEST);
		}
		
		if(endDto==null){
			//请重新选择提货网点
			throw new WaybillValidateException(WaybillValidateException.CAN_NOT_EXPRESS_CREATESALES_NULL);
		}
		
		/*
		//到达和开始都是试点城市
		if(ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(createDto.getCityType()) && 
				ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(endDto.getCityType())){
			
			DataDictionaryValueVo vo = bean.getRefundType();
			if(vo!=null && StringUtils.isNotEmpty(vo.getValueCode()) && 
				!WaybillConstants.REFUND_TYPE_ONE_DAY.equals(vo.getValueCode())){
				throw new WaybillValidateException(i18n.get("foss.gui.creating.calculateAction.exception.validateSSCityDtoRefund"));
			}
			bean.setCanReturnCargo(FossConstants.YES);
		}*/
		
		
		//开始试点城市    到达非试点
		if(ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(createDto.getCityType()) && 
			StringUtils.isEmpty(endDto.getCityType())){
			//bean.setCanReturnCargo(FossConstants.NO);
			if(waybillEntity.getReceiveMethod()!=null){
				// 提货方式
				String receiveMethod = waybillEntity.getReceiveMethod();
				//是否自提
				if (!verdictPickUpSelf(receiveMethod)) {
					//试点城市和非试点城市之间提货方式只能开自提
					throw new WaybillValidateException(WaybillValidateException.CAN_SELF_PICKUP_ONLY_ONECITY_UNTEST);					
				}
			}
			
			//返单类别
			if(StringUtils.isNotBlank(waybillEntity.getReturnBillType()) 
					&& !WaybillConstants.NOT_RETURN_BILL.equals(waybillEntity.getReturnBillType())){
				//试点城市和非试点城市之间不能开返单
				throw new WaybillValidateException(
						WaybillValidateException.CAN_NOT_RETURN_BILL_ONECITY_UNTEST);
			}
		}
		
		
		//开始试点城市    到达-快递代理
		if(ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(createDto.getCityType()) && 
				ExpWaybillConstants.CITY_TYPE_PEIZAI.equals(endDto.getCityType())){
			
			//返单类别
			if(StringUtils.isNotBlank(waybillEntity.getReturnBillType())
					&& !WaybillConstants.NOT_RETURN_BILL.equals(waybillEntity.getReturnBillType())){
				//试点城市和非试点城市之间不能开返单
				throw new WaybillValidateException(WaybillValidateException.CAN_NOT_RETURN_BILL_ONECITY_UNTEST);
			}
			
			if(StringUtils.isNotBlank(waybillEntity.getRefundType()) && StringUtils.isNotBlank(waybillEntity.getReceiveMethod()) &&
				WaybillConstants.REFUND_TYPE_ONE_DAY.equals(waybillEntity.getRefundType())){
				//试点城市和快递代理城市之间不能开1日退代收货款款
				String receiveMethod =waybillEntity.getReceiveMethod();
				//是否自提
				if (!verdictPickUpSelf(receiveMethod)) {
					throw new WaybillValidateException(WaybillValidateException.CAN_NOT_REFUND_TYPE_ONE_DAY);					
				}	
			}
		}
		
		
		//开始-非试点   到达-试点城市 
		if(!ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(createDto.getCityType()) && 
				ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(endDto.getCityType())  ){
			String orderChannel = waybillEntity.getOrderChannel();
			if(!ExpWaybillConstants.CRM_ORDER_CHANNEL_TAOBAO.equals(orderChannel)
					&& !ExpWaybillConstants.CRM_ORDER_CHANNEL_ALIBABA.equals(orderChannel)
					&& !ExpWaybillConstants.CRM_ORDER_CHANNEL_SHANGCHENG.equals(orderChannel)){
				//非试点城市和其他城市之间只能开淘宝订单
				throw new WaybillValidateException(WaybillValidateException.CRM_ORDER_CHANNEL_TAOBAO_ONLY_ONECITY_UNTEST);
			}
		}
		
		
		//开始-非试点   到达-非试点城市 
		if(!ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(createDto.getCityType()) && 
				StringUtils.isBlank(endDto.getCityType())  ){
			String orderChannel = waybillEntity.getOrderChannel();
			if(!ExpWaybillConstants.CRM_ORDER_CHANNEL_TAOBAO.equals(orderChannel)
					&& !ExpWaybillConstants.CRM_ORDER_CHANNEL_ALIBABA.equals(orderChannel)
					&& !ExpWaybillConstants.CRM_ORDER_CHANNEL_SHANGCHENG.equals(orderChannel)){
				//非试点城市和非试点城市之间只能开淘宝订单
				throw new WaybillValidateException(WaybillValidateException.CRM_ORDER_CHANNEL_TAOBAO_ONLY_BOTHCITY_UNTEST);
			}
			
			if(StringUtils.isNotBlank(waybillEntity.getReceiveMethod())){
				// 提货方式
				String receiveMethod = waybillEntity.getReceiveMethod();
				//是否自提
				if (!verdictPickUpSelf(receiveMethod)) {
					//非试点城市和非试点城市之间提货方式只能开自提
					throw new WaybillValidateException(WaybillValidateException.CAN_SELF_PICKUP_ONLY_BOTHCITY_UNTEST);					
				}
			}
			
			
			//返单类别
			if(StringUtils.isNotBlank(waybillEntity.getReturnBillType())
					&& !WaybillConstants.NOT_RETURN_BILL.equals(waybillEntity.getReturnBillType())){
				//非试点城市和非试点城市之间不能开返单
				throw new WaybillValidateException(
						WaybillValidateException.CAN_NOT_RETURN_BILL_BOTHCITY_UNTEST);
			}
		}
		
		
		//开始-非试点   到达-快递代理
		if(!ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(createDto.getCityType()) && 
				ExpWaybillConstants.CITY_TYPE_PEIZAI.equals(endDto.getCityType() )	 ){
			
			if(StringUtils.isNotBlank(waybillEntity.getReceiveMethod())){
			// 提货方式
				String receiveMethod = waybillEntity.getReceiveMethod();
				//是否自提
				if (!verdictPickUpSelf(receiveMethod)) {
					//非试点城市和快递代理城市之间不能发送货快递运单
					throw new WaybillValidateException(WaybillValidateException.CAN_NOT_EXPRESS_BETWEEN_UNTEST_LDPCITY);
				}
			}
		}
		
		//到达城市是非试点  不能开返单
		if(!ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(endDto.getCityType())){
			if(StringUtils.isNotBlank(waybillEntity.getReturnBillType()) 
					&& !WaybillConstants.NOT_RETURN_BILL.equals(waybillEntity.getReturnBillType())){
				//到达城市是非试点的情况下，不能开返单
				throw new WaybillValidateException(
						WaybillValidateException.CAN_NOT_RETURN_BILL_TARGETCITY_UNTEST);
			}
		}
		
		
		String receiveMethod = waybillEntity.getReceiveMethod();
		String code= waybillEntity.getCustomerPickupOrgCode();
		if(StringUtils.isNotBlank(waybillEntity.getCustomerPickupOrgCode())){
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = waybillManagerService.queryOrgInfoByCode(code);
			if(orgAdministrativeInfoEntity!=null && FossConstants.YES.equals(orgAdministrativeInfoEntity.getExpressSalesDepartment())){
				//是否自提
				if (verdictPickUpSelf(receiveMethod)) {
					//自提不能选择虚拟营业部作为提货网点开单
					throw new WaybillValidateException(
						WaybillValidateException.CAN_CHOOSE_VIRTUAL_SALESDEPT_ONLY);
				}						
			}else{
				if(!verdictPickUpSelf(receiveMethod)) {
					//非自提只能选择虚拟营业部开单  
					throw new WaybillValidateException(
						WaybillValidateException.CAN_CHOOSE_SELFOWN_SALESDEPT_ONLY);
				}
			}
		}
	}

	/*
	 * 检查保险声明价值
	 * 
	 * @author BaiLei
	 * @date 2014-09-05
	 */
	public void validateExpInsuranceFee(WaybillEntity entity){
		BigDecimal insuranceAmount = entity.getInsuranceAmount();

		if(insuranceAmount==null || insuranceAmount.doubleValue()<=0){
			if(entity.getInsuranceFee()!=null && entity.getInsuranceFee().doubleValue()>0){
				throw new WaybillValidateException(WaybillValidateException.INSURANCE_AMOUNT_FEE_ILLEGAL);
			}			
		}
	}
		
	/*
	 * 检查德邦客户和发货人工号
	 * 
	 * @author BaiLei
	 * @date 2014-09-05
	 */
	public void validateExpDepponExpressEmpCode(WaybillExpressEntity expEntity){
		if(expEntity == null){
			return;
		}
		 if(WaybillConstants.DEPPON_CUSTOMER.equals(expEntity.getDeliveryEmployeeCode())){
			 if(StringUtils.isEmpty(expEntity.getDeliveryEmployeeCode())){
				 throw new WaybillValidateException(WaybillValidateException.INNER_PICKUP_DELIVERY_EMPLOYEE_CODE_NULL);					 
			 }
			 if(StringUtils.isEmpty(expEntity.getInnerPickupFeeBurdenDept())){
				 throw new WaybillValidateException(WaybillValidateException.INNER_PICKUP_DEPT_NAME_IS_NULL);
			 }
		 }
	}
		
	/*
	 * 校验优惠券配置参数是否开启(电子运单如果没开启，则将优惠券置空)
	 * 
	 * @author BaiLei
	 * @date 2014-09-05
	 */
	public void validateExpPromotionsCode(WaybillEntity entity){
		 ActualFreightEntity  actualFreightEntity = actualFreightService.queryByWaybillNo(entity.getWaybillNo());
		 //电子运单不用校验优惠券
		 if(actualFreightEntity!= null && !WaybillConstants.EWAYBILL.equals(actualFreightEntity.getWaybillType())){
				 //判断优惠券是否为空
				if (StringUtils.isNotEmpty(entity.getPromotionsCode())) {
					//是否启用优惠券
					boolean isStart = false;
					ConfigurationParamsEntity config = pkpsysConfigService.queryConfigurationParamsByEntity
							(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP, WaybillConstants.PROMOTIONS_START,FossConstants.ROOT_ORG_CODE);
					if(config!=null){
						if(WaybillConstants.YES.equals(config.getConfValue())){
							isStart = true;
						}
					}	
					if(!isStart){
						throw new WaybillValidateException(WaybillValidateException.PROMOTIONS_CODE_UNUSED);						
					}
				}
		 }
	}
	
	/*
	 * 校验营销活动是否开启
	 * 
	 * @author BaiLei
	 * @date 2014-09-05
	 */
	public void validateExpActiveStart(WaybillEntity entity){
		//判断营销活动是否为空
		WaybillDisDtlEntity waybillDisDtlEntity = waybillDisDtlService.queryActiveInfoByNoAndType(entity.getWaybillNo());
		ActualFreightEntity  actualFreightEntity = actualFreightService.queryByWaybillNo(entity.getWaybillNo());
		//电子运单不用校验优惠券
		if(actualFreightEntity!= null && !WaybillConstants.EWAYBILL.equals(actualFreightEntity.getWaybillType())){
			if(waybillDisDtlEntity!=null && StringUtil.isNotBlank(waybillDisDtlEntity.getActiveCode()) &&
					StringUtil.isNotBlank(waybillDisDtlEntity.getActiveName())){
				boolean isStart = false;
				ConfigurationParamsEntity config = pkpsysConfigService.queryConfigurationParamsByEntity(
						DictionaryConstants.SYSTEM_CONFIG_PARM__PKP, WaybillConstants.CRM_ACTIVE_START,FossConstants.ROOT_ORG_CODE);
				if(config!= null){
					if(FossConstants.YES.equals(config.getConfValue())){
						isStart=true;
					}
				}
				
				if(!isStart){
					throw new WaybillValidateException(WaybillValidateException.CRM_ACTIVE_UNUSED);						
				}
			}
		}
	}
		
		

	/*
	 * 以上均为生成快递运单时进行的校验或相关公共方法@end
	 */
	
	/*
	 * 以下均为计算快递费用或相关公共方法@start
	 * 计算所有快递费用并生成waybill相关实体
	 * 注1：零担或者空运有的而快递没有的已经去掉了，装卸费没有计算，优惠券如果不可用直接置空，不会抛异常
	 * 注2：如果改动了注1中的逻辑，请务必修改注释
	 */
	public void calculateExpressAllFee(com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto waybillDto ,String status ){
		//优惠券的票面费用
		BigDecimal couponFee = null;
		
		//优惠Dto
		CouponInfoDto couponInfoDtoParam = null;
		
		//优惠券优惠费用归集类型
		String couponRankType = null;
		
		//设置未冲减优惠券的运费
		BigDecimal beforeProTranFee = null;
		//运单基础数据
		WaybillEntity waybillEntity = waybillDto.getWaybillEntity();
		LOGGER.info("电子运单激活开始："+waybillEntity.isAccurateCost()+";"+waybillEntity.getTransportFee()+";"+waybillEntity.getTotalFee()+";"+waybillEntity.getDeliveryCustomerCode()+";"+waybillEntity.getPaidMethod());
		//快递基础数据
		WaybillExpressEntity waybilExpressEntity = waybillDto.getWaybillExpressEntity();
		//计费明细
		List<WaybillChargeDtlEntity> waybillChargeDtlEntityList = new ArrayList<WaybillChargeDtlEntity>();
		List<WaybillChargeDtlEntity> waybillChargeDtlEntityListTemp = waybillDto.getWaybillChargeDtlEntity();
		if(CollectionUtils.isEmpty(waybillChargeDtlEntityListTemp)){
			waybillChargeDtlEntityList = new ArrayList<WaybillChargeDtlEntity>();
		}else{
			BeanUtils.copyProperties(waybillChargeDtlEntityListTemp, waybillChargeDtlEntityList);
		}
		List<WaybillDisDtlEntity> waybillDisDtlEntityList = waybillDto.getWaybillDisDtlEntity();

		List<WaybillDisDtlEntity> waybillDisDtlEntityListTemp = waybillDto.getWaybillDisDtlEntity();
		if(CollectionUtils.isEmpty(waybillDisDtlEntityListTemp)){
			waybillDisDtlEntityList = new ArrayList<WaybillDisDtlEntity>();
		}else{
			BeanUtils.copyProperties(waybillDisDtlEntityListTemp, waybillDisDtlEntityList);
		}
		List<WaybillPaymentEntity> waybillPaymentEntityList = waybillDto.getWaybillPaymentEntity();
		List<WaybillPaymentEntity> waybillPaymentEntityListTemp = waybillDto.getWaybillPaymentEntity();
		if(CollectionUtils.isEmpty(waybillPaymentEntityListTemp)){
			waybillPaymentEntityList = new ArrayList<WaybillPaymentEntity>();
		}else{
			BeanUtils.copyProperties(waybillPaymentEntityListTemp, waybillPaymentEntityList);
		}
		
		List<WaybillDiscountVo>  waybillDiscountVos= new ArrayList<WaybillDiscountVo>();
		
		if(StringUtils.isNotEmpty(waybillDto.getIsChangeWaybill()) && 
				waybillDto.getIsChangeWaybill().equals(WaybillConstants.YES)){
			//更改单基本校验
			validateExpChangeWaybill(waybillEntity, waybilExpressEntity);
		}else {
			//开单基本校验
			validateExpWaybill(waybillEntity, waybilExpressEntity);
		}
		//是否精准计价
		boolean isAccurateCost=false;
		
		//是否精准计价
		if (WaybillConstants.MONTH_PAYMENT.equals(waybillEntity.getPaidMethod())) {
			CustomerEntity customerEntity=customerService.queryCustomerInfoByCusCode(waybillEntity.getDeliveryCustomerCode());
			if(customerEntity!=null&&"Y".equalsIgnoreCase(customerEntity.getIsAccuratePrice())){
				isAccurateCost=true;
			}	
		}
		waybillEntity.setAccurateCost(isAccurateCost);
		// 判断是否内部带货:如果内部带货，不能计算优惠券
		if (!WaybillConstants.DEPPON_CUSTOMER.equals(waybillEntity.getDeliveryCustomerCode()) && null != waybilExpressEntity 
				&& !WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_WAYBILL.equals(waybilExpressEntity.getReturnType())) {
			// 计算各种费用 -----------------------------------------------------营销活动还没计算，别忘记了
			calculateExpressFee(waybillEntity, false, waybillDiscountVos,
					waybillChargeDtlEntityList,couponFee,couponRankType);
			
			/**
			 * 如果有优惠券编号，
			 * 需要计算两次总运费：原因是，
			 * 优惠券要求把总运费传到CRM进行校验
			 */
			if (!StringUtils.isEmpty(waybillEntity.getPromotionsCode())) {
				//不是整车才处理优惠券，因为整车没有走货路径，获取最终配载部门时会报异常	
				if(!WaybillConstants.YES.equals(waybillEntity.getIsWholeVehicle())){
					// 处理优惠券
					executeCoupon(waybillEntity,waybillChargeDtlEntityList,couponFee,couponRankType,couponInfoDtoParam);
					//设置未冲减优惠券的运费
					beforeProTranFee = waybillEntity.getTransportFee();
				}						
			}
			//设置优惠总费用
			calcaulatePromotionsFee(waybillEntity,couponFee,waybillDiscountVos,couponRankType);
			
			//设置费用及费率的规格（计算完成之后，费用四舍五入，费率除以1000）
			setWaybillFeeScale(waybillEntity);
			
			// 需要重新计算运费
			calculateTotalFee(waybillEntity,true,couponFee,couponRankType);					
		}else{
			//计算内部带货公布价运费
			calculateInnerPickupTransFee(waybillEntity,waybillDiscountVos,waybillChargeDtlEntityList);	
			//设置费用及费率的规格（计算完成之后，费用四舍五入，费率除以1000）
			setWaybillFeeScale(waybillEntity);
			// 内部带货金额清零
			resetZero(waybillEntity);

		}				
		
		// 处理增值优惠券费用	
		offsetCouponFee(waybillEntity , beforeProTranFee, couponFee,waybillDiscountVos, couponRankType);
		// 处理完优惠券清空优惠券费用，防止再次冲减
		cleanCouponFee(couponFee);
		
		//生成List<WaybillChargeDtlEntity> waybillChargeDtlEntityList费用明细方法，最后根据类型set费用值，前面每一步设置其他属性
		//如果费用值计算为0，则该项置null
		//在calculateExpressFee已经计算过了，这里需要重新把费用信息赋值进去，如果状态需要调整，亦调整状态
		waybillChargeDtlEntityList = getWaybillChargeDtlEntityList(waybillEntity,waybillChargeDtlEntityList,status);
		if(CollectionUtils.isNotEmpty(waybillChargeDtlEntityList)){
			waybillDto.setWaybillChargeDtlEntity(waybillChargeDtlEntityList);
		}
		
		//生成List<WaybillDisDtlEntity> waybillDisDtlEntity折扣明细方法，通过WaybillDiscountVos
		waybillDisDtlEntityList = getWaybillDisDtlEntityList(waybillEntity,waybillDiscountVos,status);
		if(CollectionUtils.isNotEmpty(waybillDisDtlEntityList)){
			waybillDto.setWaybillDisDtlEntity(waybillDisDtlEntityList);
		}
		
		//生成List<WaybillPaymentEntity> WaybillPaymentEntityList支付明细方法
		waybillPaymentEntityList = getWaybillPaymentEntityList(waybillEntity,waybilExpressEntity);
		
		if(CollectionUtils.isNotEmpty(waybillPaymentEntityList)){
			waybillDto.setWaybillPaymentEntity(waybillPaymentEntityList);
		}
		LOGGER.info("电子运单激活结束："+waybillEntity.isAccurateCost()+";"+waybillEntity.getTransportFee()+";"+waybillEntity.getTotalFee());
	}
	
	/**
	 * 
	 * 设置费用及费率的规格（计算完成之后，费用四舍五入，费率除以1000）
	 * 
	 * @author 136334-FOSS-BaiLei
	 * @return 
	 * @date 2014-10-24 下午07:32:36
	 */
	private void setWaybillFeeScale(WaybillEntity waybillEntity){
		MathContext mc = new MathContext(NumberConstants.NUMBER_5, RoundingMode.HALF_DOWN);
		
		if(waybillEntity.getTransportFee()!= null && waybillEntity.getTransportFee().compareTo(BigDecimal.ZERO) >0){
			BigDecimal transportFee = waybillEntity.getTransportFee();
			if(waybillEntity.isAccurateCost()){
				waybillEntity.setTransportFee(transportFee.setScale(2,BigDecimal.ROUND_HALF_UP));
			}
			else{
				waybillEntity.setTransportFee(formatNumberInteger(transportFee));
			}	
		}
		
		if(waybillEntity.getInsuranceFee()!= null && waybillEntity.getInsuranceFee().compareTo(BigDecimal.ZERO) >0){
			BigDecimal insuranceFee = waybillEntity.getInsuranceFee();
			waybillEntity.setInsuranceFee(formatNumberInteger(insuranceFee));
			BigDecimal insuranceFeeRate = waybillEntity.getInsuranceRate();
			waybillEntity.setInsuranceRate(insuranceFeeRate.divide(BigDecimal.valueOf(NumberConstants.NUMBER_1000),mc));
		}
		
		if(waybillEntity.getCodFee()!= null && waybillEntity.getCodFee().compareTo(BigDecimal.ZERO) >0){
			BigDecimal codFee = waybillEntity.getCodFee();
			waybillEntity.setCodFee(formatNumberInteger(codFee));
			BigDecimal codFeeRate = waybillEntity.getCodRate();
			waybillEntity.setCodRate(codFeeRate.divide(BigDecimal.valueOf(NumberConstants.NUMBER_1000),mc));
		}
		
		if(waybillEntity.getOtherFee()!= null && waybillEntity.getOtherFee().compareTo(BigDecimal.ZERO) >0){
			BigDecimal otherFee = waybillEntity.getOtherFee();
			waybillEntity.setOtherFee(formatNumberInteger(otherFee));
		}
		
		if(waybillEntity.getServiceFee()!= null && waybillEntity.getServiceFee().compareTo(BigDecimal.ZERO) >0){
			BigDecimal serviceFee = waybillEntity.getServiceFee();
			waybillEntity.setServiceFee(formatNumberInteger(serviceFee));
		}
		
		if(waybillEntity.getPromotionsFee() != null && waybillEntity.getPromotionsFee().compareTo(BigDecimal.ZERO) >0){
			BigDecimal promotionsFee = waybillEntity.getPromotionsFee();
			waybillEntity.setPromotionsFee(formatNumberInteger(promotionsFee));
		}
		
		if(waybillEntity.getValueAddFee()!=null && waybillEntity.getValueAddFee().compareTo(BigDecimal.ZERO) >0){			
			BigDecimal valueAddFee = waybillEntity.getValueAddFee();
			waybillEntity.setValueAddFee(formatNumberInteger(valueAddFee));
		}
			
	}
	
	/**
	 * 
	 * 客户付款明细收集
	 * 
	 * @author 136334-FOSS-BaiLei
	 * @date 2014-10-15 下午07:32:36
	 */
	private List<WaybillPaymentEntity> getWaybillPaymentEntityList(
			WaybillEntity waybillEntity,WaybillExpressEntity waybillExpressEntity) {
		String returnType = (waybillEntity.getReturnBillType() == null ? null : waybillExpressEntity.getReturnType());
		String pickupMode = (waybillEntity.getReceiveMethod() == null ? null : waybillEntity.getReceiveMethod());
		
		if (!WaybillConstants.INNER_PICKUP.equals(pickupMode) &&
				!WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_WAYBILL.equals(returnType) ) {
			List<WaybillPaymentEntity> waybillPaymentEntityList = new ArrayList<WaybillPaymentEntity>();
			/*
			// 判断是否为PAD导入开单，处理补录会用到
			if (WaybillConstants.WAYBILL_STATUS_PDA_PENDING.equals(vo
					.getWaybillstatus())) {
				// 判断PDA总金额是否为空
				if (vo.getTotalCountPDA() != null
						&& vo.getTotalCountPDA().compareTo(BigDecimal.ZERO) != 0) {
					WaybillPaymentEntity totalFeePDA = getTotalFeePDA(vo);
					waybillPaymentEntityList.add(totalFeePDA);
				}
			}
			*/
			// 判断预付金额是否为空
			if (waybillEntity.getPrePayAmount() != null
					&& waybillEntity.getPrePayAmount().compareTo(BigDecimal.ZERO) != 0) {
				WaybillPaymentEntity prePayAmount = getPrePayAmount(waybillEntity);
				waybillPaymentEntityList.add(prePayAmount);// 预付金额
			}
			// 判断到付金额是否为空
			if (waybillEntity.getToPayAmount() != null
					&& waybillEntity.getToPayAmount().compareTo(BigDecimal.ZERO) != 0) {
				WaybillPaymentEntity toPayAmount = getToPayAmount(waybillEntity);
				waybillPaymentEntityList.add(toPayAmount);// 到付金额
			}
			/*
			// 判断手写现付金额是否为空
			if (vo.getHandWriteMoney() != null
					&& vo.getHandWriteMoney().compareTo(BigDecimal.ZERO) != 0) {
				WaybillPaymentEntity handWriteMoney = getHandWriteMoney(vo);
				waybillPaymentEntityList.add(handWriteMoney);// 手写现付金额
			}
			*/
			return waybillPaymentEntityList;
		}else{
			return null;
		}
		
	}
	
	/**
	 * 获取预付金额
	 * 
	 * @author 136334-FOSS-BaiLei
	 * @date 2014-10-15 下午07:32:36
	 */
	private WaybillPaymentEntity getPrePayAmount(WaybillEntity waybillEntity) {
		WaybillPaymentEntity prePayAmount = new WaybillPaymentEntity();
		prePayAmount.setWaybillNo(waybillEntity.getWaybillNo());
		prePayAmount.setType(WaybillConstants.PAYMENT_PRE_PAY);// 预付金额类型
		prePayAmount.setAmount(waybillEntity.getPrePayAmount());// 预付金额
		prePayAmount.setActive(FossConstants.ACTIVE);
		prePayAmount.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);// RMB
		prePayAmount.setPaymentTime(new Date());
		return prePayAmount;

	}

	/**
	 * 到付金额
	 * 
	 * @author 136334-FOSS-BaiLei
	 * @date 2014-10-15 下午07:32:36
	 */
	private WaybillPaymentEntity getToPayAmount(WaybillEntity waybillEntity) {

		WaybillPaymentEntity toPayAmount = new WaybillPaymentEntity();
		toPayAmount.setWaybillNo(waybillEntity.getWaybillNo());
		toPayAmount.setType(WaybillConstants.PAYMENT_TO_PAY);// 到付金额类型
		toPayAmount.setAmount(waybillEntity.getToPayAmount());// 到付金额
		toPayAmount.setActive(FossConstants.ACTIVE);
		toPayAmount.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);// RMB
		toPayAmount.setPaymentTime(new Date());
		return toPayAmount;
	}

	public void setDefaultFirstWeight(BigDecimal defaultFirstWeight) {
		this.defaultFirstWeight = defaultFirstWeight;
	}

	public void setCusBargainService(ICusBargainService cusBargainService) {
		this.cusBargainService = cusBargainService;
	}

	public void setCusAccountService(ICusAccountService cusAccountService) {
		this.cusAccountService = cusAccountService;
	}

	public void setExpressCityService(IExpressCityService expressCityService) {
		this.expressCityService = expressCityService;
	}

	public void setActualFreightService(IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}

	public void setWaybillDisDtlService(IWaybillDisDtlService waybillDisDtlService) {
		this.waybillDisDtlService = waybillDisDtlService;
	}

	public void setBillCaculateService(IBillCaculateService billCaculateService) {
		this.billCaculateService = billCaculateService;
	}

	public void setCrmOrderService(ICrmOrderService crmOrderService) {
		this.crmOrderService = crmOrderService;
	}

	public void setWaybillFreightRouteService(
			IWaybillFreightRouteService waybillFreightRouteService) {
		this.waybillFreightRouteService = waybillFreightRouteService;
	}

	public void setFreightRouteService(IFreightRouteService freightRouteService) {
	}

	public void setExpressPrintStarService(
			IExpressPrintStarService expressPrintStarService) {
	}

	public void setAsteriskSalesDeptService(
			IAsteriskSalesDeptService asteriskSalesDeptService) {
	}

	public void setPkpinsurGoodsDao(IInsurGoodsDao pkpinsurGoodsDao) {
		this.pkpinsurGoodsDao = pkpinsurGoodsDao;
	}

	public void setCustomerBargainService(
			ICustomerBargainService customerBargainService) {
		this.customerBargainService = customerBargainService;
	}

	/*
	 * 计算快递费用的方法（仅限于快递适用）
	 * @author BaiLei
	 * @date 2014-09-05
	 * 
	 */
	public void calculateExpressFee(WaybillEntity entity, boolean needMinusCoupen, 
			List<WaybillDiscountVo>  waybillDiscountVos, List<WaybillChargeDtlEntity> waybillChargeDtlEntityList,
			BigDecimal couponFee,String couponRankType){
		
		//根据运单号获取对应的快递运单实体(电子运单可能获取不到，因为不在数据库里，电子运单是临时组装的快递运单实体)
		WaybillExpressEntity  waybillExpressEntity = queryWaybillExpressByNo(entity.getWaybillNo());
		
		//获取产品价格主参数
		GuiQueryBillCalculateDto productPriceDtoCollection = getProductPriceDtoCollection(entity);
		
		List<GuiQueryBillCalculateSubDto> priceEntities = productPriceDtoCollection.getPriceEntities();

		//获取保价费
		GuiQueryBillCalculateSubDto insuranceCollection = getInsuranceCollection(entity);
		if (insuranceCollection != null) {
			priceEntities.add(insuranceCollection);//加入增值服务
		}

		//代收货款手续费	
		GuiQueryBillCalculateSubDto codCollection = getCodCollection(entity);
		if (codCollection != null) {
			priceEntities.add(codCollection);//代收货款手续费
		}

		//其他费用
		GuiQueryBillCalculateSubDto otherChargeDataCollection = getOtherChargeDataCollection(entity);
		if (otherChargeDataCollection != null) {
			priceEntities.add(otherChargeDataCollection);//代收货款手续费
		}

		productPriceDtoCollection.setPriceEntities(priceEntities);
//		// 最低一票
//		BigDecimal minTransportFee = BigDecimal.ZERO;
		
		/*重要注释：@ 营销活动需要算-----------------------------------------------------------------------
		//营销活动DTO
		//productPriceDtoCollection.setActiveDto(bean.getActiveDto());
		
		//是否计算市场营销折扣
		productPriceDtoCollection.setCalActiveDiscount(bean.isCalActiveDiscount());
		
		//封装市场营销活动校验条件
		Common.settterActiveParamInfo(productPriceDtoCollection,bean);
		*/

		//统一返回的计价值
		List<GuiResultBillCalculateDto> guiResultBillCalculateDtos =null;
		
		//德邦客户 金额都是0
		if(WaybillConstants.DEPPON_CUSTOMER.equals(entity.getDeliveryCustomerCode())){
			guiResultBillCalculateDtos = getInnerPickupGuiResultBillCalculateDto(entity);
		}else{
			guiResultBillCalculateDtos	= waybillManagerService.queryGuiExpressBillPrice(productPriceDtoCollection);
		}
//		if(guiResultBillCalculateDtos!=null&&guiResultBillCalculateDtos.size()!=0){
//        	for(GuiResultBillCalculateDto gto:guiResultBillCalculateDtos){
//        		if(PriceEntityConstants.PRICING_CODE_FRT.equals(gto.getPriceEntryCode())){
//        			PURE_FREIGHT = gto.getPurefreight();
//        		}
//        	}
//        }
		/*重要注释:电子运单不需要营销活动，因为工作量原因，将营销活动相关代码注释调，如果后续需要调用，必须新增这部分逻辑
		//设置计价信息
		if(bean.getActiveInfo()!=null && StringUtils.isNotEmpty(bean.getActiveInfo().getValueCode())){
			bean.setGuiResultBillCalculateDtos(guiResultBillCalculateDtos);
		}
		
		//推广活动会对其他费用打折，重新在其他费用面板赋值@ 营销活动需要算-----------------------------------------------------------------------
		//setterOtherFeeByMakActive(productPriceDtoCollection,guiResultBillCalculateDtos,bean);
		 */
	
		List<GuiResultBillCalculateDto> guiResultBillCalculateDtos2 = null;
		//小件  返单计算两次  第二次首重 把总费用加进去
		if(entity.getReturnBillType()!= null && WaybillConstants.RETURNBILLTYPE_ORIGINAL.equals(entity.getReturnBillType())
			&&  !WaybillConstants.DEPPON_CUSTOMER.equals(entity.getDeliveryCustomerCode()) ){
			
			GuiQueryBillCalculateDto dto2 = new GuiQueryBillCalculateDto();
			try{
				PropertyUtils.copyProperties(dto2, productPriceDtoCollection);
			}catch(Exception e){
				LOGGER.info(e.getMessage());
			}
			
			List<GuiQueryBillCalculateSubDto>  priceEntities2 = new ArrayList<GuiQueryBillCalculateSubDto> ();
			
			for(GuiQueryBillCalculateSubDto d : priceEntities){
				if(PriceEntityConstants.PRICING_CODE_FRT.equals(d.getPriceEntityCode())){
					GuiQueryBillCalculateSubDto d2 = new GuiQueryBillCalculateSubDto();
					try{
						PropertyUtils.copyProperties(d2, d);
					}catch(Exception e){
						LOGGER.info(e.getMessage());
					}
					priceEntities2.add(d2);
				}
			}
			dto2.setPriceEntities(priceEntities2);
			dto2.setWeight(defaultFirstWeight);
			dto2.setVolume(BigDecimal.ZERO);
			dto2.setCustomerCode("");
			dto2.setIsSelfPickUp(FossConstants.NO);
			//dto2.setProductCode(WaybillConstants.DELIVER_UP);
			guiResultBillCalculateDtos2 = waybillManagerService.queryGuiExpressBillPrice(dto2);

		}
		
		//如果返回的价格为空，抛出业务异常
		if (guiResultBillCalculateDtos == null || guiResultBillCalculateDtos.isEmpty()) {
			throw new WaybillValidateException(WaybillValidateException.PRICE_NOT_FOUND);
		}
		//是否整车
		if (!WaybillConstants.YES.equals(entity.getIsWholeVehicle())) {
			// 获取公布价运费
			GuiResultBillCalculateDto dto = getGuiResultBillCalculateDto(guiResultBillCalculateDtos, PriceEntityConstants.PRICING_CODE_FRT, null);
			GuiResultBillCalculateDto dtoJH= getGuiResultBillCalculateDto(guiResultBillCalculateDtos, PriceEntityConstants.PRICING_CODE_JH, null);

			if(guiResultBillCalculateDtos2!=null && guiResultBillCalculateDtos2.size()>0){
				GuiResultBillCalculateDto dto2 = guiResultBillCalculateDtos2.get(0);
				if(dto2!=null && dto2.getCaculateFee()!=null){
					dto.setCaculateFee(dto.getCaculateFee().add(dto2.getCaculateFee()));
				}
			}
			
			//快递返单开单公布价为0
			if(waybillExpressEntity!=null && waybillExpressEntity.getReturnType()!=null 
				&& WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_WAYBILL.equals(waybillExpressEntity.getReturnType())){
				dto.setCaculateFee(BigDecimal.ZERO);
			}
			
		
			//设置公布价运费的费用
			setProductPriceDtoCollection(dto,entity,dtoJH,waybillDiscountVos,waybillChargeDtlEntityList);
//			minTransportFee = dto.getMinFee();// 运费最低一票
			//bean.setMinTransportFee(minTransportFee);
		} 

		// 计算增值服务费
		if(waybillExpressEntity!=null && waybillExpressEntity.getReturnType()!=null 
				&& WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_WAYBILL.equals(waybillExpressEntity.getReturnType())){
			//返单开单增值服务为0
		}else{
			calculateExpressValueAdd(entity, guiResultBillCalculateDtos,waybillDiscountVos,waybillChargeDtlEntityList);
		}
		/**
		 * 计算总运费公共方法
		 */
		//ExpCommon.getYokeCharge(bean, ui);快递没有包装费，故注释掉
		calculateFee(entity, false, needMinusCoupen,couponFee,couponRankType);
		if (WaybillConstants.TEMPORARY_DEBT.equals(entity.getPaidMethod())) {
			isBeBebt(entity);// 判断是否可以开临时欠款
		}
	}		
	
	/**
	 * 
	 * 计算各种费用（公布价运费、装卸费、增值费、优惠、总费用、包装费、送货费）
	 * 清空包装费额外功能
	 * @author 025000-FOSS-helong
	 * @date 2012-12-4 下午02:35:20
	 */
	public void calculateFee(WaybillEntity waybillEntity, boolean cleanPackageFee, 
			boolean needMinusCoupen,BigDecimal couponFee,String couponRankType) {
		
		// 重新赋值是因为添加装卸费需要重新计算价格
		BigDecimal transportFee = waybillEntity.getTransportFee();			
		
		if(transportFee==null){
			transportFee = BigDecimal.ZERO;
		}	
		if(waybillEntity.isAccurateCost()){
			// 公布价运费
			transportFee = transportFee.setScale(2, BigDecimal.ROUND_HALF_UP);// 四舍五入
		}else{
			// 公布价运费
			transportFee = transportFee.setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
		}
	
		//bean.setTransportFeeCanvas(transportFee.toString());

		// 增值费
		BigDecimal incrementFee = calculateExpIncrement(waybillEntity, cleanPackageFee);
		incrementFee = incrementFee.setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
		

		waybillEntity.setTransportFee(transportFee);
		waybillEntity.setValueAddFee(incrementFee);
		//优惠费用
		//bean.setCouponFree(bean.getPromotionsFee());
		
		calculateTotalFee(waybillEntity,needMinusCoupen,couponFee,couponRankType);
			                   
	}
	
	/**
	 * 
	 * 计算增值费用
	 * 清空包装费额外功能
	 * @author 136334-FOSS-BaiLei
	 * @date 2014-9-2 下午08:16:23
	 */
	private BigDecimal calculateExpIncrement(WaybillEntity waybillEntity , boolean cleanPackageFee) {
		BigDecimal incrementFee = BigDecimal.ZERO;// 增值服务费
		BigDecimal deliveryGoodsFee = waybillEntity.getDeliveryGoodsFee();// 送货费
		if(waybillEntity.getProductCode() == null){
			throw new BusinessException("产品类型不允许为空~！");
		}
		if(deliveryGoodsFee==null || this.onlineDetermineIsExpressByProductCode(waybillEntity.getProductCode(), waybillEntity.getBillTime())){
			deliveryGoodsFee=BigDecimal.ZERO;
		}
		
		BigDecimal insuranceFee = waybillEntity.getInsuranceFee();// 保价费
		if(insuranceFee==null){
			insuranceFee=BigDecimal.ZERO;
		}
		BigDecimal codFee = waybillEntity.getCodFee();// 代收手续费
		if(codFee==null){
			codFee=BigDecimal.ZERO;
		}
		BigDecimal pickUpFee = waybillEntity.getPickupFee();// 接货费
		if(pickUpFee==null){
			pickUpFee=BigDecimal.ZERO;
		}
		BigDecimal otherFee = waybillEntity.getOtherFee();// 其他费用合计、
		if(otherFee==null){
			otherFee=BigDecimal.ZERO;
		}
		
		BigDecimal packageFee = waybillEntity.getPackageFee();// 包装费
		if(packageFee==null || this.onlineDetermineIsExpressByProductCode(waybillEntity.getProductCode(), waybillEntity.getBillTime())){
			//快递包装费为0
			packageFee=BigDecimal.ZERO;
		}
		
		waybillEntity.setPackageFee(packageFee);


		// 增值服务费=送货费+包装费+保价费+代收手续费+接货费+其他费用合计
		incrementFee = deliveryGoodsFee.add(packageFee).add(insuranceFee)
				.add(codFee).add(pickUpFee).add(otherFee);
		LOGGER.info("incrementFee fee" +incrementFee);
		return incrementFee;
	}	
	
	/**
	 * 
	 * 优惠劵（电子运单的优惠券如果不可用或已使用，则置空）
	 * 
	 * @author 136334-FOSS-BaiLei
	 * @date 2014-9-2 下午08:16:23
	 */
	private void executeCoupon(WaybillEntity waybillEntity,List<WaybillChargeDtlEntity> waybillChargeDtlEntityList,
			BigDecimal couponFee,String couponRankType,CouponInfoDto couponInfoDtoParam) {

		// 优惠券是否为空
		CouponInfoDto couponInfoDto = getCouponInfoDto(waybillEntity,waybillChargeDtlEntityList);
		if(couponInfoDto == null){
			waybillEntity.setPromotionsCode(null);
		}else {
			CouponInfoResultDto dto = crmOrderService.validateCoupon(couponInfoDto);
			if (dto != null) {
				// 判断：该优惠券是否已被使用
				if (!dto.isCanUse()) {
					
					dto.getCanNotUseReason();
					//您的优惠券已使用，不可重复使用！(waybill:110;value:50)
					//waybillNo 110
					//50
					
					// 不能使用优惠券的原因
//					MsgBox.showInfo(dto.getCanNotUseReason());
//					bean.setPromotionsCode(null);
					// "您的优惠券已使用，不可重复使用！(waybill:9876543210;value:50)"
					String canNotUseReason = StringUtil.defaultIfNull(dto.getCanNotUseReason());
					String waybill = StringUtils.substringBetween(canNotUseReason, ":", ";");

					// 判断：该优惠券是否是被该运单使用的（从不可使用原因的字符串中截取使用该优惠券的运单号）
					if (!waybillEntity.getWaybillNo().equals(StringUtil.defaultIfNull(waybill).trim())) {
						waybillEntity.setPromotionsCode(null);
					}else{
						String lastAmount = StringUtils.substringAfterLast(canNotUseReason, "value:");
						lastAmount = 	StringUtils.substringBeforeLast(lastAmount, ")");
						lastAmount = 	StringUtils.substringBeforeLast(lastAmount, "}");
						
						/**
						 * 设置优惠券费用
						 */
						try{
							couponFee = new BigDecimal(lastAmount);
						}catch(Exception e){
							couponFee = BigDecimal.ZERO;
						}
						/**
						 * 设置优惠券返回实体
						 */
						couponInfoDtoParam = couponInfoDto;
						/**
						 * 设置优惠券冲减类型
						 * MANA-1961 营销活动与优惠券编码关联
						 * 2014-04-10 026123
						 */
						couponRankType = dto.getDeductibleType();
					}
				}else {
					// 优惠金额
					if (dto.getCouponAmount() != null) {
						/**
						 * 设置优惠券费用
						 */
						couponFee = dto.getCouponAmount();
						/**
						 * 设置优惠券返回实体
						 */
						couponInfoDtoParam = couponInfoDto;
						/**
						 * 设置优惠券冲减类型
						 * MANA-1961 营销活动与优惠券编码关联
						 * 2014-04-10 026123
						 */
						couponRankType = dto.getDeductibleType();
					} else {
						waybillEntity.setPromotionsCode("");
					}
				}
			}
		}
	}	
	
	/**
	 * 
	 * 获取优惠传入参数
	 * 
	 * @author 136334-FOSS-BaiLei
	 * @date 2014-9-2 下午08:16:23
	 */
	private CouponInfoDto getCouponInfoDto(WaybillEntity waybillEntity,List<WaybillChargeDtlEntity> waybillChargeDtlEntityList) {
		// 优惠信息DTO
		CouponInfoDto couponInfo = new CouponInfoDto();
		// 运单信息
		CouponWaybillInfoDto waybillInfo = new CouponWaybillInfoDto();
		// 运单号
		waybillInfo.setWaybillNumber(waybillEntity.getWaybillNo());
		// 产品号
		waybillInfo.setProduceType(waybillEntity.getProductCode());
		// 订单号
		waybillInfo.setOrderNumber(waybillEntity.getOrderNo());
		// 判断总金额是否已有
		if (waybillEntity.getTotalFee() != null && waybillEntity.getTotalFee().compareTo(BigDecimal.ZERO) == 0) {
			throw new WaybillValidateException(WaybillValidateException.TOTAL_FEE_NULL);
			//MsgBox.showInfo(i18n.get("foss.gui.creating.calculateAction.msgBox.totalFeeNullException"));
			//return null;
		}

		// 总金额
		waybillInfo.setTotalAmount(waybillEntity.getTotalFee());
		// 发货人手机
		waybillInfo.setLeaveMobile(waybillEntity.getDeliveryCustomerMobilephone());
		// 发货人电话
		waybillInfo.setLeavePhone(waybillEntity.getDeliveryCustomerPhone());
		// 客户编码
		waybillInfo.setCustNumber(waybillEntity.getDeliveryCustomerCode());
		// 获取出发部门
		String receiveOrgCode = waybillEntity.getReceiveOrgCode();

		OrgAdministrativeInfoEntity receiveOrgAdministrative = 
				orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(receiveOrgCode);

		if (receiveOrgAdministrative == null) {
			throw new WaybillValidateException(WaybillValidateException.RECEIVE_ORG_NULL);
			//return null;
		}
		
		if(null == waybillEntity.getProductCode()){
			throw new WaybillValidateException(WaybillValidateException.PRODUCT_CODE_NULL);
		}
		
		Boolean isPickupCentralized = WaybillConstants.YES.equals(waybillEntity.getPickupCentralized()) ? true : false;
		//获取走货路径
		OrgInfoDto orgInfoDto = waybillFreightRouteService.queryLodeDepartmentInfoOnline(
				isPickupCentralized,waybillEntity.getCreateOrgCode(),waybillEntity.getCustomerPickupOrgCode(),
				waybillEntity.getProductCode());
		if (orgInfoDto == null || orgInfoDto.getFreightRoute() == null) {
			throw new WaybillValidateException(WaybillValidateException.FREIGHT_ROUTE_NOT_FOUND);
		}

		// 发货部门-标杆编码
		waybillInfo.setLeaveDept(receiveOrgAdministrative.getUnifiedCode());

		if (waybillEntity.getLastLoadOrgCode() == null) { 
			throw new WaybillValidateException(WaybillValidateException.LAST_LOAD_ORG_CODE_NULL);
			//return null;
		}
		// 最终配载部门-也就是最后一个自有网点
		String lastLoadOrgCode = waybillEntity.getLastLoadOrgCode();
		
		OrgAdministrativeInfoEntity lastLoadOrgAdministrative = 
				orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(lastLoadOrgCode);
		if (lastLoadOrgAdministrative == null) {
			throw new WaybillValidateException(WaybillValidateException.LAST_LOAD_ORG_CODE_NULL);
			//return null;
		}
		if (waybillEntity.getLoadOrgCode() == null) {
			throw new WaybillValidateException(WaybillValidateException.LOAD_ORG_CODE_NULL);
			//return null;
		}
		
		// 始发外场UnifiedCode编码
		String firstLoadOutOrgInfoCode = orgAdministrativeInfoService.
				queryOrgAdministrativeInfoByCode(waybillEntity.getLoadOrgCode()).getUnifiedCode();
		// 最终外场UnifiedCode编码
		String lastLoadOutOrgInfoCode = null;
		if (!StringUtils.isEmpty(orgInfoDto.getLastOutLoadOrgCode())) {
			// 最终外场UnifiedCode编码
			lastLoadOutOrgInfoCode = orgAdministrativeInfoService.
					queryOrgAdministrativeInfoByCode(orgInfoDto.getLastOutLoadOrgCode()).getUnifiedCode();
		} else {
			throw new WaybillValidateException(WaybillValidateException.LAST_LOAD_OUT_ORG_INFO_CODE_NULL);
			//return null;
		}

		// 到达部门-标杆编码-由于偏线最后到达部门是代理，这里是最后一个只有部门
		waybillInfo.setArrivedDept(lastLoadOrgAdministrative.getUnifiedCode());

		// 发货部门所在外场
		waybillInfo.setLeaveOutDept(firstLoadOutOrgInfoCode);
		// 到达部门所在外场
		waybillInfo.setArrivedOutDept(lastLoadOutOrgInfoCode);

		List<AmountInfoDto> amountInfoList = new ArrayList<AmountInfoDto>();
		for (WaybillChargeDtlEntity waybillChargeDtlEntity : waybillChargeDtlEntityList) { // 计价明细
			AmountInfoDto amountInfo = new AmountInfoDto();
						
			if(PriceEntityConstants.PRICING_CODE_SH.equals(waybillChargeDtlEntity.getPricingEntryCode())
					|| PriceEntityConstants.PRICING_CODE_SHSL.equals(waybillChargeDtlEntity.getPricingEntryCode()) 
					|| PriceEntityConstants.PRICING_CODE_SHJC.equals(waybillChargeDtlEntity.getPricingEntryCode())){
				//快递没有送货费，故什么都不做
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
		couponInfo.setCouponNumber(waybillEntity.getPromotionsCode());
		return couponInfo;
		
	}

	/**
	 * 
	 * 计算总费用
	 * @author 025000-FOSS-helong
	 * @author 136334-FOSS-BaiLei
	 * @date 2014-9-2 下午08:16:23
	 */
	public void calculateTotalFee(WaybillEntity waybillEntity, boolean needMinusCoupen, BigDecimal couponFee,String couponRankType){
			
		BigDecimal	transportFee=waybillEntity.getTransportFee();
		BigDecimal	incrementFee=waybillEntity.getValueAddFee();
		BigDecimal	otherFee=waybillEntity.getOtherFee();
		
		/**
		 * 判断公布价运费是否为空，如果为空，则设置为0
		 */
		if(transportFee == null){
			waybillEntity.setTransportFee(BigDecimal.ZERO);
		}
		/**
		 * 判断增值费用是否为空，如果为空，则设置为0
		 */
		if(incrementFee == null){
			waybillEntity.setInsuranceFee(BigDecimal.ZERO);
			incrementFee = BigDecimal.ZERO;
		}
		/**
		 * 判断代收货款是否为空，如果为空，则设置为0
		 */
		BigDecimal codAmout = waybillEntity.getCodAmount();
		if(codAmout == null){
			waybillEntity.setCodAmount(BigDecimal.ZERO);
		}
		
	
		/**
		 * 若冲减类型为运费时，才可以对运费进行冲减
		 * MANA-1961 营销活动与优惠券编码关联
		 * 2014-04-10 026123
		 */
		if(couponFee!=null && couponRankType!=null && PriceEntityConstants.PRICING_CODE_FRT.equals(couponRankType)){
			couponFee = couponFee.setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
		}else{
			couponFee=BigDecimal.ZERO;
		}
		
		if(transportFee == null){
			transportFee = BigDecimal.ZERO;
		}
		/**
		 * 公布价运费需要减去优惠费用
		 */
		if(needMinusCoupen){
			transportFee=transportFee.subtract(couponFee);		
		}
		
		//小件只能把运费冲减到零
		if(transportFee.doubleValue()<0){
			if(this.onlineDetermineIsExpressByProductCode(waybillEntity.getProductCode(), waybillEntity.getBillTime())){
				transportFee = BigDecimal.ZERO;
			}
		}
		waybillEntity.setTransportFee(transportFee);
		/**
		 * 总运费=公布价运费+增值服务费
		 */
		BigDecimal totalFee = transportFee.add(incrementFee);
		
		
		/**
		 * 如果总运费小于0，赋值为0
		 */
		if(BigDecimal.ZERO.compareTo(totalFee)>0)
		{
			totalFee = BigDecimal.ZERO;
		}
		
		//加其他费用
		if(otherFee != null && otherFee.compareTo(BigDecimal.ZERO) > 0){
			totalFee.add(otherFee);
		}
		
		try{
		if(waybillEntity.getPaidMethod()!=null){
			if (WaybillConstants.ARRIVE_PAYMENT.equals(waybillEntity.getPaidMethod())) {
				
				totalFee = totalFee.add(codAmout);
				// 总费用
				waybillEntity.setTotalFee(totalFee);
				//bean.setTotalFeeCanvas(totalFee.toString());
				//画布-总费用
				//bean.setTotalFeeCanvas(totalFee.toString());
				// 到付金额
				waybillEntity.setToPayAmount(totalFee);
				// 预付金额
				waybillEntity.setPrePayAmount(BigDecimal.ZERO);
			} else {
				// 预付金额
				waybillEntity.setPrePayAmount(totalFee);
				// 总金额加上代收货款
				totalFee = totalFee.add(codAmout);
				// 总费用
				waybillEntity.setTotalFee(totalFee);
				//bean.setTotalFeeCanvas(totalFee.toString());
				waybillEntity.setToPayAmount(codAmout);
			}
		}else{
			// 预付金额
			waybillEntity.setPrePayAmount(totalFee);
			// 总金额加上代收货款
			totalFee = totalFee.add(waybillEntity.getCodAmount());
			// 总费用
			waybillEntity.setTotalFee(totalFee);
			//bean.setTotalFeeCanvas(totalFee.toString());
			waybillEntity.setToPayAmount(waybillEntity.getCodAmount());
		}
		}catch(Exception e)
		{
			LOGGER.info(e.getMessage());
		}
		LOGGER.info("result setTotalFee" +totalFee);
		
		
	}	
	
	/**
	 * 计算优惠总费用
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-13 下午3:09:42
	 */
	private void calcaulatePromotionsFee(WaybillEntity waybillEntity, BigDecimal couponFee
			,List<WaybillDiscountVo> waybillDiscountVos,String couponRankType) {
		
		/**
		 * 如果优惠价格为空或者为0时
		 */
		if (couponFee != null && BigDecimal.ZERO.compareTo(couponFee) != 0) {
			if (waybillDiscountVos == null) {
				waybillDiscountVos = new ArrayList<WaybillDiscountVo>();

			}
			
			//判断是否需要添加到优惠费用中
			if(isAddPromotionsFeeByTypeExpress(couponRankType)){			
				/**
				 * MANA-1961 营销活动与优惠券编码关联
				 * 2014-04-10 026123
				 */
				WaybillDiscountVo waybillDiscountVo = new WaybillDiscountVo();
				// 优惠折扣项目名称
				waybillDiscountVo.setFavorableItemName(convertFeeToName(couponRankType));
				// 优惠折扣项目CODE
				waybillDiscountVo.setFavorableItemCode(defaultIfNull(couponRankType));
				// 优惠类别名称
				waybillDiscountVo.setFavorableTypeName("优惠券");//优惠券，暂时写死，后面优化
				// 优惠类别CODE
				waybillDiscountVo.setFavorableTypeCode(TYPE_CODE);
				waybillDiscountVo.setFavorableDiscount(BigDecimal.ZERO.toString());
				waybillDiscountVo.setFavorableAmount(couponFee.toString());
				// 折扣ID
				waybillDiscountVo.setDiscountId(waybillEntity.getPromotionsCode());
				// 类型 discount 为公布价折扣 promotion 为增值服务优惠
				waybillDiscountVo.setFavorableTypeCode(PricingConstants.VALUATION_TYPE_DISCOUNT);
	
				waybillDiscountVos.add(waybillDiscountVo);
			}
		}
		//优惠总费用
		BigDecimal totalPromotionsFee = BigDecimal.ZERO;
//		//是否享有推广活动折扣
//		boolean flagActive=false;
		if (waybillDiscountVos != null) {
			for (WaybillDiscountVo waybillDiscountVo : waybillDiscountVos) {
				if(waybillDiscountVo.getFavorableAmount() != null){
					totalPromotionsFee = totalPromotionsFee.add(new 
							BigDecimal(waybillDiscountVo.getFavorableAmount()).setScale(0,BigDecimal.ROUND_HALF_DOWN));
				
					//是否活动折扣
//					if(DiscountTypeConstants.DISCOUNT_TYPE__ACTIVE.equals(waybillDiscountVo.getFavorableTypeCode())){
//						flagActive=true;
//					}	
				}
			}
		}
		/**
		 * 设置优惠费用
		 */
		waybillEntity.setPromotionsFee(totalPromotionsFee);
		
		//快递是通过营销活动和折扣进行对比，哪个折扣更低取哪个，故不用抛异常，直接取较小值即可
		//选择了营销活动但未享受，给予用户提示
		//if(bean.getActiveInfo()!=null && StringUtils.isNotEmpty(bean.getActiveInfo().getValueCode())){
		//	if(!flagActive){
		//		MsgBox.showInfo(i18n.get("foss.gui.creating.expcalculateAction.activeinfo.nohave.actdis"));
		//	}
		//}
	}
	
	/**
	 * 根据优惠券类型判断是否要添加到优惠费用中(快递)
	 * @创建时间 2014-5-7 上午8:44:26   
	 * @创建人： WangQianJin
	 */
	public  boolean isAddPromotionsFeeByTypeExpress(String type){		
		boolean isAdd=true;				
		if(PriceEntityConstants.PRICING_CODE_JH.equals(type) 
				|| PriceEntityConstants.PRICING_CODE_SH.equals(type)
				|| PriceEntityConstants.PRICING_CODE_BF.equals(type)
				|| PriceEntityConstants.PRICING_CODE_BZ.equals(type)
				|| PriceEntityConstants.PRICING_CODE_ZHXX.equals(type)){			
			//快递只处理代收货款的优惠券
			isAdd=false;
		}		
		return isAdd;
	}
	
	/**
	 * 根据费用类型编码获取对应中文名
	 * MANA-1961 营销活动与优惠券编码关联
	 * 2014-04-10 026123
	 */
	public String convertFeeToName(String feeCode){
		//费用编码
		String code = defaultIfNull(feeCode);
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
	 * 优惠券冲减费用
	 * 对于运费在calculateTotalFee中进行冲减
	 * 对于综合信息费则在xxx中进行冲减
	 * 
	 * MANA-1961 营销活动与优惠券编码关联
	 * 2014-04-10 026123
	 */
	public void offsetCouponFee(WaybillEntity waybillEntity ,BigDecimal beforeProTranFee, BigDecimal couponFee,
			List<WaybillDiscountVo> waybillDiscountVos,String couponRankType) {
		// 优惠费
		couponFee = defaultIfNull(couponFee);
		
		// 优惠类型
		String type = defaultIfNull(couponRankType);
		
		// 是否有优惠金额,是否已冲减
		if (couponFee.compareTo(BigDecimal.ZERO) > 0) {
			// 校验优惠类型是否符合条件
			validateCouponTypeExpress(type);
			// 冲减费用类型
			if(PriceEntityConstants.PRICING_CODE_HK.equals(type)) {
				//校验费用是否符合要求
				validateFeeIsZero(waybillEntity.getCodFee(),type);
				// 冲减代收货款手续费
				BigDecimal codFee = defaultIfNull(waybillEntity.getCodFee());
				processPromotionsFee(waybillEntity,waybillDiscountVos,codFee,couponFee);
				//WaybillEntity waybillEntity,List<WaybillDiscountVo> waybillDiscountVos
				codFee=codFee.subtract(couponFee);
				codFee = convertFeeToZero(codFee);
				waybillEntity.setCodFee(codFee);				
			}else if(PriceEntityConstants.PRICING_CODE_ZHXX.equals(type)){				
				//冲减综合信息服务费
				//获取其他费用
				List<OtherChargeVo> data = getOtherChargeDetail(waybillEntity);
				//校验费用是否符合要求
				validateOtherFeeIsZero(data,type);
				if(CollectionUtils.isNotEmpty(data)){
					boolean flag=false;
					for(OtherChargeVo vo : data){
						if(PriceEntityConstants.PRICING_CODE_ZHXX.equals(vo.getCode())){
							//获取默认的综合信息费，否则会在冲减后基础上冲减
							BigDecimal zhxxFee = getDefaultZhxxFee(waybillEntity);
							if(zhxxFee!=null){
								processPromotionsFee(waybillEntity,waybillDiscountVos,zhxxFee,couponFee);
								zhxxFee = zhxxFee.subtract(couponFee);															
								zhxxFee=convertFeeToZero(zhxxFee);
								vo.setMoney(zhxxFee.toString());
								flag=true;									
								break;
							}								
						}
					}
					if(flag){
						//重新获取其他费用
						otherPanelSumFee(data,waybillEntity);
					}
				}
			}else if(PriceEntityConstants.PRICING_CODE_FRT.equals(type)){
				processPromotionsFee(waybillEntity,waybillDiscountVos, beforeProTranFee,couponFee);
			}		
			//重新计算增值服务费
			resetCalculateFee(waybillEntity,couponFee,couponRankType);
		}
	}
	
	/**
	 * 判断要快递使用优惠券的类型是否符合要求（电子运单的优惠券类型如果无法冲减，则直接将优惠券置空，不会抛出异常）
	 * @创建时间 2014-5-7 上午8:44:26   
	 * @创建人： WangQianJin
	 */
	private  void validateCouponTypeExpress(String type){
		//费用名称
		String feeName=convertFeeToName(type);
		if(PriceEntityConstants.PRICING_CODE_BF.equals(type) 
				|| PriceEntityConstants.PRICING_CODE_BZ.equals(type)){			
			//您输入的优惠券类型-feeName无法冲减，请手动调整费用！
			//throw new WaybillValidateException(i18n.get("foss.gui.common.commonutils.couponfee.validate.type.express",new Object[]{feeName}));	
			LOGGER.info(feeName+"该优惠券无法冲减类型无法冲减,需要手动调整费用");
		}else if(PriceEntityConstants.PRICING_CODE_JH.equals(type) 
				|| PriceEntityConstants.PRICING_CODE_SH.equals(type)			
				|| PriceEntityConstants.PRICING_CODE_ZHXX.equals(type)){
			//您输入的优惠券类型-feeName无法冲减，请选择营销活动！
			//throw new WaybillValidateException(i18n.get("foss.gui.common.commonutils.couponfee.validate.type",new Object[]{feeName}));
			LOGGER.info(feeName+"该优惠券无法冲减类型无法冲减,需要选择营销活动");
		}		
	}
	
	/**
	 * 判断要使用优惠券的费用是否符合要求 
	 * @创建时间 2014-9-5 上午8:44:26   
	 * @创建人： 136334 BaiLei
	 */
	public  void validateFeeIsZero(BigDecimal fee,String type){
		//费用名称
		String feeName=convertFeeToName(type);
		if(null == fee){			
			//feeName为空，无法使用优惠券！
			LOGGER.info(feeName+"为空，无法使用优惠券!");
			return;
		}
		if(fee.compareTo(new BigDecimal(0))==0){			
			//feeName等于0，无法使用优惠券！
			LOGGER.info(feeName+"等于0，无法使用优惠券!");
		}
		if(fee.doubleValue()<0){			
			//feeName小于0，无法使用优惠券！
			LOGGER.info(feeName+"小于0，无法使用优惠券!");
		}
	}
		
	//处理优惠券金额
	private  void processPromotionsFee(WaybillEntity waybillEntity,List<WaybillDiscountVo> waybillDiscountVos ,
			BigDecimal subFee,BigDecimal proFee) {
		if(subFee!=null && proFee!=null){
			//如果优惠券金额大于要冲减的金额，则重新设置优惠券金额为要冲减的金额
			if(proFee.compareTo(subFee) > 0 ){
				if(waybillDiscountVos!=null && waybillDiscountVos.size()>0){
					//优惠总费用
					BigDecimal totalPromotionsFee = BigDecimal.ZERO;
					for (WaybillDiscountVo waybillDiscountVo : waybillDiscountVos) {
						if(waybillEntity.getPromotionsCode()!=null && 
							waybillEntity.getPromotionsCode().equals(waybillDiscountVo.getDiscountId())){
							waybillDiscountVo.setFavorableAmount(subFee.toString());
						}
						totalPromotionsFee = totalPromotionsFee.add(new BigDecimal(waybillDiscountVo.getFavorableAmount()));
					}
					/**
					 * 设置优惠费用
					 */
					waybillEntity.setPromotionsFee(totalPromotionsFee);
				}
			}			
		}
	}
	
	/**
	 * 检验优惠冲减后的费用是否正确
	 * MANA-1961 营销活动与优惠券编码关联
	 * 2014-04-10 026123
	 */
	public  BigDecimal convertFeeToZero(BigDecimal fee){
		BigDecimal value = defaultIfNull(fee);
		//检验优惠费用是否小于0
		if(BigDecimal.ZERO.compareTo(value) > 0 ){
			return BigDecimal.ZERO;
		}else{
			return value;
		}
	}

	/**
	 * 判断要使用优惠券的费用是否符合要求
	 * @创建时间 2014-5-7 上午8:44:26   
	 * @创建人： WangQianJin
	 */
	public  void validateOtherFeeIsZero(List<OtherChargeVo> data,String type){
		//费用名称
		String feeName=convertFeeToName(type);
		BigDecimal fee=null;
		//综合信息费
		if(PriceEntityConstants.PRICING_CODE_ZHXX.equals(type)){		
			//遍历集合
			if(CollectionUtils.isNotEmpty(data)){
				for(OtherChargeVo vo : data){
					if(PriceEntityConstants.PRICING_CODE_ZHXX.equals(vo.getCode()) && vo.getMoney()!=null){
						fee=new BigDecimal(vo.getMoney());						
						break;
					}
				}
			}	
		//快递包装费
		}else if(PriceEntityConstants.PRICING_CODE_KDBZF.equals(type)){
			//遍历集合
			if(CollectionUtils.isNotEmpty(data)){
				for(OtherChargeVo vo : data){
					if(PriceEntityConstants.PRICING_CODE_KDBZF.equals(vo.getCode()) && vo.getMoney()!=null){
						fee=new BigDecimal(vo.getMoney());						
						break;
					}
				}
			}
		}			
		//判断费用是否符合条件
		if(null == fee){			
			//feeName为空，无法使用优惠券！
			LOGGER.info(feeName+"为空，无法使用优惠券!");
			return;
		}
		if(fee.compareTo(new BigDecimal(0))==0){			
			//feeName等于0，无法使用优惠券！
			LOGGER.info(feeName+"等于0，无法使用优惠券!");
		}
		if(fee.doubleValue()<0){			
			//feeName小于0，无法使用优惠券！
			LOGGER.info(feeName+"小于0，无法使用优惠券!");
		}
	
	}
	
	/**
	 * 获取默认综合信息服务费的值
	 * MANA-1961 营销活动与优惠券编码关联
	 * 2014-04-10 026123
	 */
	public  BigDecimal getDefaultZhxxFee(WaybillEntity waybillEntity){
		BigDecimal zhxx=null;
		List<ValueAddDto> list = billCaculateService.searchExpressValueAddPriceList(getQueryOtherChargeParam(waybillEntity));
		//遍历集合
		if (CollectionUtils.isNotEmpty(list)) {
			for (ValueAddDto dto : list) {
				if (PriceEntityConstants.PRICING_CODE_ZHXX.equals(dto.getSubType())) {
					zhxx = dto.getCaculateFee();
					break;
				}
			}
		}
		return zhxx;
	}
	
	/**
	 * 
	 * 累计其他费用所有费用
	 * @author 136334-FOSS-BaiLei
	 * @date 2013-9-5 下午04:17:58
	 */
	private void otherPanelSumFee(List<OtherChargeVo> data,WaybillEntity waybillEntity)
	{
		BigDecimal otherCharge = BigDecimal.ZERO;
		if (data != null && !data.isEmpty()) {
			for (int i = 0; i < data.size(); i++) {
				OtherChargeVo otherVo = data.get(i);
				otherCharge = otherCharge.add(new BigDecimal(otherVo.getMoney()));
			}
		}
		waybillEntity.setOtherFee(otherCharge);
	}
	
	
	
	
	/**
	 * 
	 * 获取其他费用查询参数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:02:10
	 */
	private  QueryBillCacilateValueAddDto getQueryOtherChargeParam(WaybillEntity waybillEntity) {
		QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
		// 出发部门CODE
		queryDto.setOriginalOrgCode(waybillEntity.getReceiveOrgCode());
		// 到达部门CODE
		queryDto.setDestinationOrgCode(waybillEntity.getCustomerPickupOrgCode());
		// 产品CODE
		queryDto.setProductCode(waybillEntity.getProductCode());
		// 货物类型CODE
		queryDto.setGoodsTypeCode(null);
		queryDto.setReceiveDate(new Date());// 营业部收货日期（可选，无则表示当前日期）
		queryDto.setWeight(BigDecimal.ZERO);// 重量
		queryDto.setVolume(BigDecimal.ZERO);// 体积
		queryDto.setOriginnalCost(BigDecimal.ZERO);// 原始费用
		queryDto.setFlightShift(null);// 航班号
		//queryDto.setLongOrShort(waybillEntity.getLongOrShort());// 长途 还是短途
		queryDto.setSubType(null);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_QT);// 计价条目CODE
		queryDto.setPricingEntryName(null);// 计价名称
		return queryDto;
	}
	
	/**
	 * 
	 * 重新计算各种费用（公布价运费、装卸费、增值费、优惠、总费用、包装费、送货费）
	 * @author BaiLei
	 * @date 2014-09-05
	 */
	public  void resetCalculateFee(WaybillEntity waybillEntity,BigDecimal couponFee,String couponRankType) {
		
		// 重新赋值是因为添加装卸费需要重新计算价格
		BigDecimal transportFee = waybillEntity.getTransportFee();			
		
		if(transportFee==null){
			transportFee = BigDecimal.ZERO;
		}	
		if(waybillEntity.isAccurateCost()){
			transportFee = transportFee.setScale(2, BigDecimal.ROUND_HALF_UP);// 四舍五入
		}else{
			// 公布价运费
			transportFee = transportFee.setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
		}
		// 增值费
		BigDecimal incrementFee = resetCalculateIncrement(waybillEntity);
		incrementFee = incrementFee.setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
		

		waybillEntity.setTransportFee(transportFee);
		waybillEntity.setValueAddFee(incrementFee);

		calculateTotalFee(waybillEntity,false,couponFee,couponRankType);
		
		                   
	}
	
	
	/**
	 * 
	 * 设置公布价集合
	 * @author BaiLei
	 * @date 2014-09-05
	 */
	private void setProductPriceDtoCollection(GuiResultBillCalculateDto dto, WaybillEntity waybillEntity,GuiResultBillCalculateDto dtoJH,
			List<WaybillDiscountVo>  waybillDiscountVos,List<WaybillChargeDtlEntity> waybillChargeDtlEntityList){
		if (dto == null) {
			throw new WaybillValidateException(WaybillValidateException.PRICE_NOT_FOUND);
		}
		if (dto.getCaculateFee() == null) {
			throw new WaybillValidateException(WaybillValidateException.TRANSPORT_FEE_NOT_FOUND);
		}
		BigDecimal transportFee = dto.getCaculateFee();
		if(waybillEntity.isAccurateCost()){
			transportFee=transportFee.setScale(2, BigDecimal.ROUND_HALF_UP);// 四舍五入
		}
		else{
			transportFee=transportFee.setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
		}
				
		 //设置运费价格ID
		String  transportFeeId = null;
		if(StringUtils.isNotEmpty(dto.getId())){
			transportFeeId = dto.getId();
		}else{
			transportFeeId ="NA";
		}
		// 设置运费价格CODE
		String transportFeeCode = dto.getPriceEntryCode();
		
		// 设置运费
		waybillEntity.setTransportFee(transportFee);
		// 设置费率
		if(dto.getActualFeeRate()!=null){
			waybillEntity.setUnitPrice(formatNumberTwoDecimal(
					dto.getActualFeeRate().divide(new BigDecimal(NumberConstants.NUMBER_100), newScale, RoundingMode.HALF_EVEN)));
		}else{
			if((transportFee!=null && waybillEntity.getBillWeight()!=null && waybillEntity.getBillWeight().doubleValue()>0)
					|| (transportFee!=null && waybillEntity.getGoodsWeightTotal()!=null && waybillEntity.getGoodsWeightTotal().doubleValue()>0)){
				waybillEntity.setUnitPrice(formatNumberTwoDecimal(
						transportFee.divide(waybillEntity.getGoodsWeightTotal(), newScale, RoundingMode.HALF_EVEN)));
			}
		}
			
		if(waybillEntity.getUnitPrice()==null){
			waybillEntity.setUnitPrice(BigDecimal.ZERO);
		}
		
		// 设置计费类型（重量计费或者体积计费）
		waybillEntity.setBillingType(dto.getCaculateType());
		
		// 设置计费重量
		setBillWeight(waybillEntity, dto);

		// 设置折扣优惠
		setFavorableDiscount(dto.getDiscountPrograms(),waybillDiscountVos,waybillEntity);

		// 设置折扣优惠率值(零担用该值设置装卸费费率，快递没有用此值，故注释)
		//setDiscount(dto.getDiscountPrograms(), waybillEntity,waybillDiscountVos);
		
		
		waybillEntity.setPickupFee(BigDecimal.ZERO);//设置接货费
		//bean.setBasePickupFee(BigDecimal.ZERO);//原始接货费
		
		//设置获取公布价运费明细实体
		if (waybillEntity.getTransportFee() != null && waybillEntity.getTransportFee().compareTo(BigDecimal.ZERO) != 0){
			WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
			if(!WaybillConstants.YES.equals(waybillEntity.getIsWholeVehicle()) ){
				chargeEntity.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_FRT);
	    		chargeEntity.setPricingCriDetailId(WaybillConstants.HAND_INPUT);
			}else{
	    		chargeEntity.setPricingEntryCode(transportFeeCode);
	    		chargeEntity.setPricingCriDetailId(transportFeeId);
			}
			chargeEntity.setAmount(waybillEntity.getTransportFee());
			chargeEntity.setWaybillNo(waybillEntity.getWaybillNo());
			chargeEntity.setActive(FossConstants.INACTIVE);
			chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			waybillChargeDtlEntityList.add(chargeEntity);
		}
	}
	
	/**
	 * 
	 * 设置折扣优惠
	 * 
	 */
	public void setFavorableDiscount(List<GuiResultDiscountDto> discountPrograms,
			List<WaybillDiscountVo>  waybillDiscountVos,
			WaybillEntity entity){
		if (discountPrograms != null && !discountPrograms.isEmpty()) {
			if (waybillDiscountVos == null) {
				waybillDiscountVos = new ArrayList<WaybillDiscountVo>();
			}
			for (GuiResultDiscountDto dto : discountPrograms) {
				//报价费为0，优惠金额为0
				if (entity.getInsuranceAmount() == null || (BigDecimal.ZERO.compareTo(entity.getInsuranceAmount()) == 0)) {
					if(PriceEntityConstants.PRICING_CODE_BF.equals(dto.getPriceEntryCode())){
						dto.setReduceFee(BigDecimal.ZERO);
					}
				}
				addDiscount(dto, waybillDiscountVos);
			}
		}
	}
	
	/**
	 * 清空优惠券费用
	 * @创建时间 2014-9-4 下午9:24:53   
	 * @创建人： 136334 - BaiLei
	 */
	private void cleanCouponFee(BigDecimal couponFee){
		couponFee = BigDecimal.ZERO;	
	}
	
	/**
	 * 
	 * 增加折扣信息
	 * 
	 */							
	private void addDiscount(GuiResultDiscountDto dto, List<WaybillDiscountVo> data) {
		
		WaybillDiscountVo vo = new WaybillDiscountVo();
		// 折扣ID
		vo.setDiscountId(dto.getDiscountId());
		// 费用类型id
		vo.setChargeDetailId(dto.getChargeDetailId());
		// 优惠折扣项目
		vo.setFavorableItemName(dto.getPriceEntryName());
		// 优惠项目CODE
		vo.setFavorableItemCode(dto.getPriceEntryCode());
		// 优惠折扣类型
		vo.setFavorableTypeName(dto.getDiscountTypeName());
		// 优惠折扣类型
		vo.setFavorableTypeCode(dto.getDiscountType());
		// 优惠折扣子类型
		vo.setFavorableSubTypeName(dto.getSaleChannelName());
		// 优惠折扣子类型
		vo.setFavorableSubTypeCode(dto.getSaleChannelCode());
		//营销活动编码
		vo.setActiveCode(dto.getActiveCode());
		//营销活动名称
		vo.setActiveName(dto.getActiveName());
		//营销活动开始时间
		vo.setActiveStartTime(dto.getActiveStartTime());
		//营销活动结束时间
		vo.setActiveEndTime(dto.getActiveEndTime());
		//营销活动折扣对应的CRM_ID
		vo.setOptionsCrmId(dto.getOptionsCrmId());
		if (dto.getDiscountRate() != null) {
			// 优惠折扣率
			vo.setFavorableDiscount(dto.getDiscountRate().toString());
		} else {
			// 优惠折扣率
			vo.setFavorableDiscount("无折扣费率，可能是数据有异常，请联系管理员");
		}
		if(dto.getRenewalDiscountRate() != null) {
			vo.setExpressContinueRate(dto.getRenewalDiscountRate());
		}
		// 优惠折扣金额
		if (dto.getReduceFee() != null) {
			//优惠金额
			vo.setFavorableAmount(dto.getReduceFee().toString());			
		} else {
			vo.setFavorableAmount("无折扣费率，可能是数据有异常，请联系管理员");
		}

		data.add(vo);

	}
	
	/**
	 * 
	 * 计算增值费用
	 * 
	 * @author 136334-FOSS-BaiLei
	 * @date 2014-9-1 下午08:58:39
	 */
	public void calculateExpressValueAdd(WaybillEntity waybillEntity, List<GuiResultBillCalculateDto> guiResultBillCalculateDtos,
			List<WaybillDiscountVo>  waybillDiscountVos,List<WaybillChargeDtlEntity> waybillChargeDtlEntityList) {

		//获取打木架（快递没有木架和木箱，故注释）
		//GuiResultBillCalculateDto packageCollectionFRAME=	 getGuiResultBillCalculateDto(guiResultBillCalculateDtos, PriceEntityConstants.PRICING_CODE_BZ, DictionaryValueConstants.PACKAGE_TYPE__FRAME);
		//设置打木架
		//ExpCommon.setStandChargeCollection(bean, ui, packageCollectionFRAME);
		
		//获取打木箱快递没有木架和木箱，故注释）
		//GuiResultBillCalculateDto packageCollectionBOX=	 getGuiResultBillCalculateDto(guiResultBillCalculateDtos, PriceEntityConstants.PRICING_CODE_BZ, DictionaryValueConstants.PACKAGE_TYPE__BOX);
		//设置打木箱
		//ExpCommon.setBoxChargeCollection(bean, ui, packageCollectionBOX);

		//获取保价费
		GuiResultBillCalculateDto insuranceCollection = getGuiResultBillCalculateDto(guiResultBillCalculateDtos, 
				PriceEntityConstants.PRICING_CODE_BF, null);
		//设置保价费
		setInsuranceCollection(waybillEntity, insuranceCollection, waybillDiscountVos,waybillChargeDtlEntityList);

		//获取代收货款手续费
		GuiResultBillCalculateDto codCollection = getGuiResultBillCalculateDto(guiResultBillCalculateDtos, 
				PriceEntityConstants.PRICING_CODE_HK, null);

		//设置代收货款手续费
		setCodCollection(waybillEntity, codCollection, waybillDiscountVos,waybillChargeDtlEntityList);

		// 送货费(快递没有送货费，故注释）
		//setDeliveryFeeCollection(waybillEntity, insuranceCollection, waybillDiscountVos,waybillChargeDtlEntityList);
		// 计算装卸费（电子运单无法输入装卸费，故先注释掉，后面需要调用此方法的，务必考虑装卸费）
		calculateServiceFee(waybillEntity);

		//添加其他费用
		setOtherChargeDataCollection(waybillEntity, guiResultBillCalculateDtos, waybillDiscountVos,waybillChargeDtlEntityList);
		
	} 
	/**
	 * 
	 * 根据发货客户编码读取CRM中配置的装卸费比例计算装卸费用
	 * 
	 * */
	private void setServiceCharge(WaybillEntity bean){
		String deliveryCustomerCode = "";
		if(StringUtil.isNotBlank(bean.getDeliveryCustomerCode())){
			deliveryCustomerCode = bean.getDeliveryCustomerCode();
		}
		CustomerEntity custinfo= customerService.queryCustomerInfoByCusCode(deliveryCustomerCode);
		if(custinfo!=null&&StringUtil.isNotBlank(custinfo.getSetProportion())){
			BigDecimal setProportion = new BigDecimal(custinfo.getSetProportion());
			BigDecimal servicefee = bean.getTransportFee().multiply(setProportion)
					.setScale(0,BigDecimal.ROUND_DOWN);
			//加收方式存入装卸费字段(劳务费)
			if("CHARGETYPE".equals(custinfo.getExpHandChargeBusiType())){
				bean.setServiceFee(servicefee);
		    //折让方式存入另一个字段给结算用，不存入装卸费字段，不算在公布价运费中
			}else if("DISCOUNTTYPE".equals(custinfo.getExpHandChargeBusiType())){
				bean.setServiceFee(BigDecimal.ZERO);
				bean.setDcServicefee(bean.getTransportFee().multiply(setProportion)
						.setScale(1,BigDecimal.ROUND_DOWN));
			}
		}
		if(custinfo==null||
				(custinfo!=null&&StringUtil.isBlank(custinfo.getSetProportion()))){
			bean.setServiceFee(BigDecimal.ZERO);
		}
	}
	/** 
	 * 
	 * 计算装卸费，输入装卸费之后的费率 = （纯运费+装卸费）/计费重量
	 * 
	 */
	private void calculateServiceFee(WaybillEntity bean) {
	
		// 设置是否允许修改装卸费
		setServiceCharge(bean);

		// 获取装卸费
		BigDecimal serivceFee = bean.getServiceFee();
		if (serivceFee != null && serivceFee.longValue() != 0) {
			// 输入的装卸费不为0的情况下，按照以下规则：
			// 输入装卸费之后的公布价运费 = 纯运费+装卸费
			// 输入装卸费之后的费率 = （纯运费+装卸费）/计费重量

			// 获取运费
			BigDecimal transportFee = bean.getTransportFee();
			// 获取费率
			BigDecimal unitPrice = bean.getUnitPrice();
			// 运费 = 运费+装卸费
			transportFee = transportFee.add(bean.getServiceFee());
			// 设置新的运费
			bean.setTransportFee(transportFee);
			// 费率 = 最新运费（运费+装卸费）/计费重量
			unitPrice = transportFee.divide(bean.getBillWeight(), 2,
					BigDecimal.ROUND_HALF_DOWN);
			// 设置新的费率
			bean.setUnitPrice(unitPrice);
		}
	}
	/**
	 * 
	 * 设置保价费
	 * @author 136334-FOSS-BaiLei
	 * @date 2014-9-1 下午08:58:39
	 * 
	 */
	private void setInsuranceCollection(WaybillEntity waybillEntity, GuiResultBillCalculateDto dto,
			List<WaybillDiscountVo>  waybillDiscountVos, List<WaybillChargeDtlEntity> waybillChargeDtlEntityList) {
		String insuranceId = null;
		String insuranceCode = null;
		if (dto == null) {
			// 保险声明值最大值
			if(waybillEntity.getInsuranceFee()==null || waybillEntity.getInsuranceFee().compareTo(BigDecimal.ZERO)==0){
				//bean.setMaxInsuranceAmount(BigDecimal.ZERO);
				// 保险费率
				waybillEntity.setInsuranceRate(BigDecimal.ZERO);
				// 保险手续费
				waybillEntity.setInsuranceFee(BigDecimal.ZERO);
				// 保险费ID
				insuranceId = "";
				// 保险费CODE
				insuranceCode ="";
				
				if(waybillEntity.getInsuranceAmount()!=null && waybillEntity.getInsuranceAmount().doubleValue()>0){
					throw new WaybillValidateException(WaybillValidateException.INSURANCE_FEE_NULL);
				}
			}
		} else {

			BigDecimal permillage = new BigDecimal(WaybillConstants.PERMILLAGE);
			BigDecimal feeRate = BigDecimal.ZERO;
			//保险声明价值不为0
			if(waybillEntity.getInsuranceAmount()!=null && waybillEntity.getInsuranceAmount().compareTo(BigDecimal.ZERO)>0){
				//保价手续费不为0
				if(waybillEntity.getInsuranceFee()!=null && waybillEntity.getInsuranceFee().compareTo(BigDecimal.ZERO)!=0){
					feeRate = waybillEntity.getInsuranceFee().divide(waybillEntity.getInsuranceAmount(),NumberConstants.NUMBER_5, BigDecimal.ROUND_HALF_UP);
					feeRate = feeRate.multiply(permillage);
					waybillEntity.setInsuranceRate(formatNumberTwoDecimal(feeRate));
				} else {
					if (dto != null) {
						feeRate = nullBigDecimalToZero(dto.getActualFeeRate());
						feeRate = feeRate.multiply(permillage);
						// 保险费率
						waybillEntity.setInsuranceRate(formatNumberTwoDecimal(feeRate));
						BigDecimal insuranceFee = dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
						if (waybillEntity.getInsuranceAmount() == null || 
								(BigDecimal.ZERO.compareTo(waybillEntity.getInsuranceAmount()) == 0)) {
							// 保险手续费
							waybillEntity.setInsuranceFee(BigDecimal.ZERO);
						} else {
							// 保险手续费
							waybillEntity.setInsuranceFee(formatNumberInteger(insuranceFee));
						}

						// 保险费ID
						insuranceId = dto.getId();
						// 保险费CODE
						insuranceCode = dto.getPriceEntryCode();
					}
				}
			}else{
				// 保险费率
				waybillEntity.setInsuranceRate(BigDecimal.ZERO);
				// 保险手续费
				waybillEntity.setInsuranceFee(BigDecimal.ZERO);
				// 保险费ID
				insuranceId = "";
				// 保险费CODE
				insuranceCode ="";
				
			}
			
			setFavorableDiscount(dto.getDiscountPrograms(),waybillDiscountVos,waybillEntity);
		}
		
		//费用为0的时候不放入费用明细表
		if (insuranceCode != null && !"".equals(insuranceCode) && 
				waybillEntity.getInsuranceFee().compareTo(BigDecimal.ZERO)>0) {
			WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
			chargeEntity.setPricingEntryCode(insuranceCode);
			chargeEntity.setAmount(waybillEntity.getInsuranceFee());
			chargeEntity.setWaybillNo(waybillEntity.getWaybillNo());
			chargeEntity.setPricingCriDetailId(insuranceId);
			chargeEntity.setActive(FossConstants.INACTIVE);
			chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			
			waybillChargeDtlEntityList.add(chargeEntity);
		}
	}
	
	/**
	 * 
	 * 设置代收货款手续费
	 * @author 136334-FOSS-BaiLei
	 * @date 2014-9-1 下午08:58:39
	 * 
	 */
	private void setCodCollection(WaybillEntity waybillEntity, GuiResultBillCalculateDto dto,
			List<WaybillDiscountVo>  waybillDiscountVos, List<WaybillChargeDtlEntity> waybillChargeDtlEntityList){
		String codCode = null;
		String codId = null;
		
		// 获得输入的代收货款金额
		BigDecimal codAmount = waybillEntity.getCodAmount();
		if (codAmount != null && codAmount.compareTo(BigDecimal.ZERO) > 0 && waybillEntity.getRefundType() != null) {
			if (dto != null) {
				//将代收货款费率转换成千分率的格式
				BigDecimal permillage = new BigDecimal(WaybillConstants.PERMILLAGE);
				BigDecimal feeRate = nullBigDecimalToZero(dto.getActualFeeRate());
				feeRate = feeRate.multiply(permillage);
				// 代收货款费率
				waybillEntity.setCodRate(formatNumberTwoDecimal(feeRate));

				BigDecimal codFee = dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
				// 代收货款金额
				waybillEntity.setCodFee(formatNumberInteger(codFee));

				// 代收货款编码
				codCode = dto.getPriceEntryCode();
				
				// 代收货款ID
				codId = dto.getId();
				
				// 设置折扣优惠
				setFavorableDiscount(dto.getDiscountPrograms(),waybillDiscountVos,waybillEntity);
			} else {
				// 代收货款费率
				waybillEntity.setCodRate(BigDecimal.ZERO);
				// 代收货款金额
				waybillEntity.setCodFee(BigDecimal.ZERO);
				// 代收货款编码
				codId = "";
				// 代收货款ID
				codId = "";
				
				throw new WaybillValidateException(WaybillValidateException.COD_FEE_NULL);
				
			}
		}else{
			// 代收货款费率
			waybillEntity.setCodRate(BigDecimal.ZERO);
			// 代收货款金额
			waybillEntity.setCodFee(BigDecimal.ZERO);
		}
			
		//费用为0的时候不放入费用明细表
		if (waybillEntity.getRefundType() != null && StringUtils.isNotBlank(codCode) &&
				waybillEntity.getInsuranceFee().compareTo(BigDecimal.ZERO)>0){
			WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
			chargeEntity.setPricingEntryCode(codCode);
			chargeEntity.setAmount(waybillEntity.getCodFee());
			chargeEntity.setWaybillNo(waybillEntity.getWaybillNo());
			chargeEntity.setPricingCriDetailId(codId);
			chargeEntity.setActive(FossConstants.INACTIVE);
			chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			
			waybillChargeDtlEntityList.add(chargeEntity);
		}
	}
	
	/**
	 * 
	 * 重新计算增值费用
	 * @author WangQianJin
	 * @date 2013-08-05
	 */
	private  BigDecimal resetCalculateIncrement(WaybillEntity waybillEntity) {
		BigDecimal incrementFee = BigDecimal.ZERO;// 增值服务费
		BigDecimal deliveryGoodsFee = waybillEntity.getDeliveryGoodsFee();// 送货费
		if(deliveryGoodsFee==null){
			deliveryGoodsFee=BigDecimal.ZERO;
		}
		BigDecimal packageFee = waybillEntity.getPackageFee();// 包装费
		if(packageFee==null){
			packageFee=BigDecimal.ZERO;
		}
		BigDecimal insuranceFee = waybillEntity.getInsuranceFee();// 保价费
		if(insuranceFee==null){
			insuranceFee=BigDecimal.ZERO;
		}
		BigDecimal codFee = waybillEntity.getCodFee();// 代收手续费
		if(codFee==null){
			codFee=BigDecimal.ZERO;
		}
		BigDecimal pickUpFee = waybillEntity.getPickupFee();// 接货费
		if(pickUpFee==null){
			pickUpFee=BigDecimal.ZERO;
		}
		BigDecimal otherFee = waybillEntity.getOtherFee();// 其他费用合计
		if(otherFee==null){
			otherFee=BigDecimal.ZERO;
		}
		// 增值服务费=送货费+包装费+保价费+代收手续费+接货费+其他费用合计
		incrementFee = deliveryGoodsFee.add(packageFee).add(insuranceFee)
				.add(codFee).add(pickUpFee).add(otherFee);
		LOGGER.info("incrementFee fee" +incrementFee);
		return incrementFee;
	}
	
	
	/**
	 * 
	 * 设置其他费用折扣
	 * 
	 * @author 136334-FOSS-BaiLei
	 * @date 2014-9-1 下午08:58:39
	 * 
	 */	
	private void setOtherChargeDataCollection(WaybillEntity waybillEntity, List<GuiResultBillCalculateDto> dtos,
			List<WaybillDiscountVo>  waybillDiscountVos, List<WaybillChargeDtlEntity> waybillChargeDtlEntityList){
		
		List<OtherChargeVo> data = getOtherChargeDetail(waybillEntity);	
		boolean flagZhxx=false;
		boolean flagKdbz=false;
		if(CollectionUtils.isNotEmpty(data)){			
			for(OtherChargeVo vo : data){
				if(PriceEntityConstants.PRICING_CODE_ZHXX.equals(vo.getCode())){
					flagZhxx=true;												
				}
				if(PriceEntityConstants.PRICING_CODE_KDBZF.equals(vo.getCode())){
					flagKdbz=true;												
				}
			}								
		}
		if (dtos != null && !dtos.isEmpty()) {
			//设置其他费用的折扣优惠
			for (GuiResultBillCalculateDto valueAddDto : dtos) {
				//是否为其他费用
				if (PriceEntityConstants.PRICING_CODE_QT.equals(valueAddDto.getPriceEntryCode())) {
					if(PriceEntityConstants.PRICING_CODE_ZHXX.equals(valueAddDto.getSubType())){
						if(flagZhxx){
							//setFavorableDiscount(valueAddDto.getDiscountPrograms(), ui, bean);
							setFavorableDiscount(valueAddDto.getDiscountPrograms(),waybillDiscountVos,waybillEntity);
						}
					}else if(PriceEntityConstants.PRICING_CODE_KDBZF.equals(valueAddDto.getSubType())){
						if(flagKdbz){
							//setFavorableDiscount(valueAddDto.getDiscountPrograms(), ui, bean);
							setFavorableDiscount(valueAddDto.getDiscountPrograms(),waybillDiscountVos,waybillEntity);
						}
					}else{
						//setFavorableDiscount(valueAddDto.getDiscountPrograms(), ui, bean);
						setFavorableDiscount(valueAddDto.getDiscountPrograms(),waybillDiscountVos,waybillEntity);
					}					
				}
			}
		}
		
		//设置其他费用
		if (CollectionUtils.isNotEmpty(data)) {
			for (OtherChargeVo otherVo : data) {
				WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
				if(otherVo.getMoney().compareTo(ZERO)>0){
					chargeEntity.setPricingEntryCode(otherVo.getCode());
					chargeEntity.setAmount(new BigDecimal(otherVo.getMoney()));
					chargeEntity.setWaybillNo(waybillEntity.getWaybillNo());
					if(otherVo.getId()!=null){
						chargeEntity.setPricingCriDetailId(otherVo.getId());
					}else{
						//如果数据库里面没有id 就使用一个其他id算了 参考FRT其他费用写法
						chargeEntity.setPricingCriDetailId("4567");
					}
					chargeEntity.setActive(FossConstants.ACTIVE);
					chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
					waybillChargeDtlEntityList.add(chargeEntity);
				}
			}
		} 
		
	}

	/**
	 * 
	 * 获取其他费用
	 * 
	 * @author 136334-FOSS-BaiLei
	 * @date 2014-9-1 下午08:58:39
	 */
	private List<OtherChargeVo> getOtherChargeDetail(WaybillEntity waybillEntity){
		List<ValueAddDto> list = billCaculateService.searchExpressValueAddPriceList(getQueryParam(waybillEntity));

		if(list!=null && list.size()>0){
			
			List<ValueAddDto> list2 = new ArrayList<ValueAddDto>();
			
			for(int i=0;i<list.size();i++){
				ValueAddDto dto = list.get(i);
				if(ExpWaybillConstants.OTHERFEE_SHJCF.equals(dto.getSubType())
					||WaybillConstants.OTHERFEE_RYFJ.equals(dto.getSubType())
					||WaybillConstants.OTHERFEE_ZHXX.equals(dto.getSubType())){
					continue;
				}else{
					list2.add(dto);
				}
			}
		
			List<OtherChargeVo> voList = getOtherChargeList(list2);
			return voList;
		}
		return null;
	}

	/**
	 * 获取查询条件参数
	 * 
	 * @author 136334-FOSS-BaiLei
	 * @date 2014-09-14 上午11:00:49
	 * 
	 */
	private QueryBillCacilateValueAddDto getQueryParam(WaybillEntity waybillEntity) {
		QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
		// 出发部门CODE
		queryDto.setOriginalOrgCode(waybillEntity.getReceiveOrgCode());
		// 到达部门CODE
		queryDto.setDestinationOrgCode(null);
		// 产品CODE
		queryDto.setProductCode(null);
		// 货物类型CODE
		queryDto.setGoodsTypeCode(null);
		// 营业部收货日期（可选，无则表示当前日期）
		queryDto.setReceiveDate(null);
		// 重量
		queryDto.setWeight(BigDecimal.ZERO);
		// 体积
		queryDto.setVolume(BigDecimal.ZERO);
		// 原始费用
		queryDto.setOriginnalCost(BigDecimal.ZERO);
		// 航班号
		queryDto.setFlightShift(null);
		// 长途 还是短途
		queryDto.setLongOrShort(null);
		// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setSubType(null);
		// 币种
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);
		// 计价条目CODE
		queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_QT);
		// 计价名称
		queryDto.setPricingEntryName(null);
		return queryDto;
	}
	
	/**
	 * 
	 * 将查询出的其他费用设置到OtherChargeVo list中
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:00:49
	 */
	private List<OtherChargeVo> getOtherChargeList(List<ValueAddDto> list) {
		List<OtherChargeVo> voList = new ArrayList<OtherChargeVo>();

		if (list != null) {
			for (ValueAddDto dto : list) {
				OtherChargeVo vo = new OtherChargeVo();
				if (dto.getCandelete() != null && !stringToBoolean(dto.getCandelete())) {
					// 费用编码
					vo.setCode(dto.getSubType());
					// 名称
					vo.setChargeName(dto.getSubTypeName());
					// 归集类别
					vo.setType(dto.getBelongToPriceEntityName());
					// 描述
					vo.setDescrition(dto.getPriceEntityCode());
					// 金额
					vo.setMoney(dto.getFee().toString());
					// 上限
					vo.setUpperLimit(dto.getMaxFee().toString());
					// 下限
					vo.setLowerLimit(dto.getMinFee().toString());
					// 是否可修改
					vo.setIsUpdate(stringToBoolean(dto.getCanmodify()));
					// 是否可删除
					vo.setIsDelete(stringToBoolean(dto.getCandelete()));
					vo.setId(dto.getId());
					voList.add(vo);
				}
			}
		}
		return voList;
	}

	
	
	
	/**
	 * 
	 * 是否可开临时欠款
	 * 
	 */
 	private void isBeBebt(WaybillEntity waybillEntity) {
		BigDecimal money = waybillEntity.getPrePayAmount();

		DebitDto dto = customerBargainService.isBeBebt(
				waybillEntity.getDeliveryCustomerCode(), waybillEntity.getReceiveOrgCode(),WaybillConstants.TEMPORARY_DEBT, money);
		if (!dto.isBeBebt()) {
			throw new WaybillValidateException(dto.getMessage());
		}
	}
	
	//设置德邦客户（内部带货）价格信息
	private List<GuiResultBillCalculateDto> getInnerPickupGuiResultBillCalculateDto(WaybillEntity entity){
		List<GuiResultBillCalculateDto> guiResultBillCalculateDtos =null;
		
		if(WaybillConstants.DEPPON_CUSTOMER.equals(entity.getDeliveryCustomerCode())){
			guiResultBillCalculateDtos = new ArrayList<GuiResultBillCalculateDto>();
			GuiResultBillCalculateDto dto1 = new GuiResultBillCalculateDto();
			dto1.setActualFeeRate(BigDecimal.ZERO);
			dto1.setCaculateFee(BigDecimal.ZERO);
			dto1.setCaculateType("WEIGHT");
			dto1.setCentralizePickup(FossConstants.NO);
			dto1.setDiscountFee(BigDecimal.ZERO);
			dto1.setPriceEntryCode(PriceEntityConstants.PRICING_CODE_FRT);
			guiResultBillCalculateDtos.add(dto1);
			
			GuiResultBillCalculateDto dto2 = new GuiResultBillCalculateDto();
			dto2.setActualFeeRate(BigDecimal.ZERO);
			dto2.setCaculateFee(BigDecimal.ZERO);
			dto2.setCaculateType("ORIGINALCOST");
			dto2.setCentralizePickup(FossConstants.NO);
			dto2.setDiscountFee(BigDecimal.ZERO);
			dto2.setFee(BigDecimal.ZERO);
			dto2.setFeeRate(BigDecimal.ZERO);
			dto2.setGoodsTypeCode(GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001);
			dto2.setLightFeeRate(BigDecimal.ZERO);
			dto2.setMaxFee(BigDecimal.ZERO);
			dto2.setMinFee(BigDecimal.ZERO);
			dto2.setPriceEntryCode(PriceEntityConstants.PRICING_CODE_BF);
			guiResultBillCalculateDtos.add(dto2);
			
			GuiResultBillCalculateDto dto3 = new GuiResultBillCalculateDto();
			dto3.setActualFeeRate(BigDecimal.ZERO);
			dto3.setCaculateFee(BigDecimal.ZERO);
			dto3.setCaculateType("ORIGINALCOST");
			dto3.setCentralizePickup(FossConstants.NO);
			dto3.setDiscountFee(BigDecimal.ZERO);
			dto3.setFee(BigDecimal.ZERO);
			dto3.setFeeRate(BigDecimal.ZERO);
			dto3.setGoodsTypeCode(GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001);
			dto3.setLightFeeRate(BigDecimal.ZERO);
			dto3.setMaxFee(BigDecimal.ZERO);
			dto3.setMinFee(BigDecimal.ZERO);
			dto3.setPriceEntryCode(PriceEntityConstants.PRICING_CODE_QT);
			dto3.setPriceEntryName("其他费用");
			dto3.setSubType(PriceEntityConstants.PRICING_CODE_KDBZF);
			dto3.setPriceEntryName("快递包装费");
			guiResultBillCalculateDtos.add(dto3);
		}
		
		return guiResultBillCalculateDtos;
	}
	
	//保价费
	private GuiQueryBillCalculateSubDto getInsuranceCollection(WaybillEntity entity){
		
		GuiQueryBillCalculateSubDto queryDto = new GuiQueryBillCalculateSubDto();
		queryDto.setOriginnalCost(entity.getInsuranceAmount());// 原始费用
		// 限保物品才会具备的虚拟code
		LimitedWarrantyItemsEntity limitedWarrantyItems = pkpinsurGoodsDao.isInsurGoods(entity.getGoodsName());
		if(limitedWarrantyItems != null ){
			queryDto.setSubType(limitedWarrantyItems.getVirtualCode());
		}else{
			queryDto.setSubType("");
		}
		queryDto.setPriceEntityCode(PriceEntityConstants.PRICING_CODE_BF);// 计价条目CODE
		queryDto.setWoodenVolume(null);// 木架体积
		return queryDto;
	}

	//代收手续费
	private GuiQueryBillCalculateSubDto getCodCollection(WaybillEntity entity){
		
		// 获得输入的代收货款金额
		BigDecimal codAmount = entity.getCodAmount();
		if (codAmount != null && codAmount.compareTo(new BigDecimal(ZERO)) > 0 && entity.getRefundType() != null) {
			return getQueryParam(entity, PriceEntityConstants.PRICING_CODE_HK, entity.getCodAmount(), entity.getRefundType());
		} else {
			return null;
		}
		
	}
		
	//其他费用
	private GuiQueryBillCalculateSubDto getOtherChargeDataCollection(WaybillEntity entity){
		GuiQueryBillCalculateSubDto queryDto = new GuiQueryBillCalculateSubDto();

		queryDto.setOriginnalCost(BigDecimal.ZERO);// 原始费用
		queryDto.setSubType(null);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setPriceEntityCode(PriceEntityConstants.PRICING_CODE_QT);// 计价条目CODE
		return queryDto;
	}
	
	/*
	 * 获取查询参数
	 */
	private GuiQueryBillCalculateSubDto getQueryParam(WaybillEntity entity, String valueAddType, BigDecimal cost, String subType) {
		GuiQueryBillCalculateSubDto queryDto = new GuiQueryBillCalculateSubDto();
		queryDto.setOriginnalCost(cost);// 原始费用
		queryDto.setSubType(subType);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setPriceEntityCode(valueAddType);// 计价条目CODE
		return queryDto;
	}
	
	/**
	 * 
	 * 获取GuiResultBillCalculateDto
	 * 
	 */
	private GuiResultBillCalculateDto getGuiResultBillCalculateDto(List<GuiResultBillCalculateDto> dtos, String valueAddType, String subType){

		for (GuiResultBillCalculateDto guiResultBillCalculateDto : dtos) {
			if (subType == null) {
				if (valueAddType.equals(guiResultBillCalculateDto.getPriceEntryCode())) {
					return guiResultBillCalculateDto;
				}
			} else {

				if (valueAddType.equals(guiResultBillCalculateDto.getPriceEntryCode()) && 
						subType.equals(guiResultBillCalculateDto.getSubType())) {
					return guiResultBillCalculateDto;
				}
			}
		}

		return null;
	}
	
	private GuiQueryBillCalculateDto getProductPriceDtoCollection(WaybillEntity entity){
		
		// 上门接货优先读取上门接货的价格
		if (WaybillConstants.YES.equals(entity.getPickupToDoor())) {
			return getQueryParamCollection(entity, FossConstants.YES);
		} else {
			return getQueryParamCollection(entity, FossConstants.NO);
		}

	}
		
	private GuiQueryBillCalculateDto getQueryParamCollection(WaybillEntity entity, String isPickupToDoor){	
		
		GuiQueryBillCalculateDto queryDto = new GuiQueryBillCalculateDto();
		/*重要注释：电子运单不需要营销活动，因为工作量原因，将营销活动相关代码注释调，如果后续需要调用，必须新增这部分逻辑
		//快递优惠活动
		if(bean.getSpecialOffer()!=null&&StringUtil.isNotEmpty(bean.getSpecialOffer().getValueCode()))
		{
			queryDto.setCityMarketCode(bean.getSpecialOffer().getValueCode());
		}
		*/
		queryDto.setOriginalOrgCode(entity.getReceiveOrgCode());// 出发部门CODE
		queryDto.setReceiveOrgCode(entity.getReceiveOrgCode());// 出发部门CODE
		queryDto.setDestinationOrgCode(entity.getCustomerPickupOrgCode());// 到达部门CODE
		queryDto.setProductCode(entity.getProductCode());// 产品CODE
		/*只有空运才和货物类型有关，故注释掉
		ProductEntityVo productVo = bean.getProductCode();
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getProductCode().getCode())) {
			queryDto.setGoodsCode(bean.getAirGoodsType().getValueCode());// 货物类型CODE
		}*/

		if(entity.getBillTime()==null){
			queryDto.setReceiveDate(new Date());// 营业部收货日期（可选，无则表示当前日期）
		}
		queryDto.setReceiveDate(entity.getBillTime());// 营业部收货日期（可选，无则表示当前日期）
		queryDto.setIsReceiveGoods(isPickupToDoor);// 是否接货
		queryDto.setWeight(entity.getGoodsWeightTotal());// 重量
		
		queryDto.setLongOrShort(getPickupDeliveryTime(entity).getLongOrShort());// 长途 还是短途  
		
		//这里要根据尺寸重新计算一下体积，计算出来的体积和输入的总体积对比，如果小于0.01，则置0
		if(getCalculateVolumeTotal(entity)!=null && entity.getGoodsVolumeTotal()!=null && 
				Math.abs(getCalculateVolumeTotal(entity).doubleValue() - entity.getGoodsVolumeTotal().doubleValue() )<KEY_VOLUME){
			queryDto.setVolume(BigDecimal.ZERO);// 体积
		}else{	
			queryDto.setVolume(entity.getGoodsVolumeTotal());// 体积
		}
		
		if(queryDto.getVolume()==null){
			queryDto.setVolume(BigDecimal.ZERO);// 体积
		}
		
		/*空运不计算，故注释掉
		if (bean.getFlightNumberType() == null) {
			queryDto.setFlightShift(null);// 航班号
		} else {
			queryDto.setFlightShift(bean.getFlightNumberType().getValueCode());// 航班号
		}*/
		
		queryDto.setChannelCode(entity.getOrderChannel());//设置CRM渠道
		
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		queryDto.setCustomerCode(entity.getDeliveryCustomerCode());// 发货客户编码
		queryDto.setKilom(entity.getKilometer());//设置公里数
		
		List<GuiQueryBillCalculateSubDto> priceEntities =new ArrayList<GuiQueryBillCalculateSubDto>();
		GuiQueryBillCalculateSubDto guiQueryBillCalculateSubDto= new GuiQueryBillCalculateSubDto();
		guiQueryBillCalculateSubDto.setPriceEntityCode(PriceEntityConstants.PRICING_CODE_FRT);
		priceEntities.add(guiQueryBillCalculateSubDto);
		queryDto.setPriceEntities(priceEntities);
		
		if(entity.getReceiveMethod()!=null){
			//提货方式是否自提
			if (verdictPickUpSelf(entity.getReceiveMethod())) {
				//试点城市和快递代理城市之间提货方式只能开派送
				queryDto.setIsSelfPickUp(FossConstants.YES);					
			}else{
				queryDto.setIsSelfPickUp(FossConstants.NO);
			}
		}else{
			queryDto.setIsSelfPickUp(FossConstants.YES);
		}
		
		return queryDto;
	}
	
	/**
	 * 
	 * 计算内部带货公布价运费
	 * 
	*/
	private void calculateInnerPickupTransFee(WaybillEntity waybillEntity,
			List<WaybillDiscountVo>  waybillDiscountVos, List<WaybillChargeDtlEntity> waybillChargeDtlEntityList) {
		
		if(WaybillConstants.DEPPON_CUSTOMER.equals(waybillEntity.getDeliveryCustomerCode())){
			
			//获取产品价格主参数
			GuiQueryBillCalculateDto productPriceDtoCollection = getProductPriceDtoCollection(waybillEntity);

			List<GuiQueryBillCalculateSubDto> priceEntities = productPriceDtoCollection.getPriceEntities();

			//获取GUI价格
			productPriceDtoCollection.setPriceEntities(priceEntities);

			List<GuiResultBillCalculateDto> guiResultBillCalculateDtos =null;
			
			guiResultBillCalculateDtos	= waybillManagerService.queryGuiExpressBillPrice(productPriceDtoCollection);
			
			//如果返回的价格为空，抛出业务异常
			if (guiResultBillCalculateDtos == null || guiResultBillCalculateDtos.isEmpty()) {
				throw new WaybillValidateException(WaybillValidateException.PRICE_NOT_FOUND);
			}

			GuiResultBillCalculateDto dto = getGuiResultBillCalculateDto(guiResultBillCalculateDtos, PriceEntityConstants.PRICING_CODE_FRT, null);
			GuiResultBillCalculateDto dtoJH= getGuiResultBillCalculateDto(guiResultBillCalculateDtos, PriceEntityConstants.PRICING_CODE_JH, null);
			
			//设置公布价运费的费用
			setProductPriceDtoCollection(dto,waybillEntity,dtoJH,waybillDiscountVos,waybillChargeDtlEntityList);
			
			// 设置最低一票
//			BigDecimal minTransportFee = BigDecimal.ZERO;
//			minTransportFee = dto.getMinFee();
			//bean.setMinTransportFee(minTransportFee);
			
		}
	}	
	
	/**
	 * 
	 * 内部带货，需要将金额相关全部清零
	 * 
	 */
	public void resetZero(WaybillEntity waybillEntity) {
		// 增值服务面板
		waybillEntity.setInsuranceAmount(BigDecimal.ZERO);// 保险声明价
		waybillEntity.setCodAmount(BigDecimal.ZERO);// 代收货款
		waybillEntity.setPackageFee(BigDecimal.ZERO);// 包装费
		waybillEntity.setDeliveryGoodsFee(BigDecimal.ZERO);// 送货费
		waybillEntity.setServiceFee(BigDecimal.ZERO);// 装卸费
		waybillEntity.setPickupFee(BigDecimal.ZERO);// 接货费
		waybillEntity.setOtherFee(BigDecimal.ZERO);// 其他费用合计
		waybillEntity.setAccountName("");// 收款人
		waybillEntity.setAccountCode("");// 收款账号
	
		// 计费付款面板
		//bean.setTransportFee(BigDecimal.ZERO);// 公布价运费
		waybillEntity.setValueAddFee(BigDecimal.ZERO);// 增值服务费
		waybillEntity.setPromotionsFee(BigDecimal.ZERO);// 优惠合计
		waybillEntity.setPrePayAmount(BigDecimal.ZERO);// 预付金额
		waybillEntity.setToPayAmount(BigDecimal.ZERO);// 到付金额
		//waybillEntity.setHandWriteMoney(BigDecimal.ZERO);// 手写现付金额
		waybillEntity.setTotalFee(BigDecimal.ZERO);

		waybillEntity.setInsuranceRate(BigDecimal.ZERO);// 保价费率
		waybillEntity.setInsuranceFee(BigDecimal.ZERO);// 保价费
		waybillEntity.setCodRate(BigDecimal.ZERO);// 代收费率
		waybillEntity.setCodFee(BigDecimal.ZERO);// 代收手续费

	}	
	
	//重新获取费用信息并赋值List<WaybillChargeDtlEntity>
	private List<WaybillChargeDtlEntity> getWaybillChargeDtlEntityList(WaybillEntity waybillEntity,
			List<WaybillChargeDtlEntity> waybillChargeDtlEntityList,String status){
		//根据费用类型判断，重新设置各项费用的值
		//List<WaybillChargeDtlEntity> waybillChargeDtlEntityListRusult = waybillChargeDtlEntityList;
		if(waybillEntity== null || waybillChargeDtlEntityList==null){
			return null;
		}
		
		for(WaybillChargeDtlEntity chargeDtlEntity:waybillChargeDtlEntityList){
			if(chargeDtlEntity!=null){
				if(PriceEntityConstants.PRICING_CODE_FRT.equals(chargeDtlEntity.getPricingEntryCode())){
					chargeDtlEntity.setAmount(waybillEntity.getTransportFee());
					chargeDtlEntity.setActive(status);
				}
				if(PriceEntityConstants.PRICING_CODE_BF.equals(chargeDtlEntity.getPricingEntryCode())){
					chargeDtlEntity.setAmount(waybillEntity.getInsuranceFee());
					chargeDtlEntity.setActive(status);
				}
				if(PriceEntityConstants.PRICING_CODE_HK.equals(chargeDtlEntity.getPricingEntryCode())){
					chargeDtlEntity.setAmount(waybillEntity.getCodFee());
					chargeDtlEntity.setActive(status);
				}
				if(PriceEntityConstants.PRICING_CODE_QT.equals(chargeDtlEntity.getPricingEntryCode())){
					chargeDtlEntity.setAmount(waybillEntity.getOtherFee());
					chargeDtlEntity.setActive(status);
				}
			}			
		}
		
		return waybillChargeDtlEntityList;
	}
	
	
	private List<WaybillDisDtlEntity>  getWaybillDisDtlEntityList(WaybillEntity waybillEntity,
			List<WaybillDiscountVo> waybillDiscountVos, String status){
		List<WaybillDisDtlEntity> disDtlEntityList = new ArrayList<WaybillDisDtlEntity>();
		if (waybillDiscountVos != null && !waybillDiscountVos.isEmpty()) {
			for (WaybillDiscountVo entity : waybillDiscountVos) {
				if(entity!=null){
					WaybillDisDtlEntity disDtlEntity = new WaybillDisDtlEntity();
					// 优惠项目名称
					disDtlEntity.setPricingEntryName(entity.getFavorableItemName());
					// 优惠项目CODE
					disDtlEntity.setPricingEntryCode(entity.getFavorableItemCode());
					// 优惠类型名称
					disDtlEntity.setTypeName(entity.getFavorableTypeName());
					// 优惠类型CODE
					disDtlEntity.setType(entity.getFavorableTypeCode());
					// 优惠子类型名称
					disDtlEntity.setSubTypeName(entity.getFavorableSubTypeName());
					// 优惠子类型CODE
					disDtlEntity.setSubType(entity.getFavorableSubTypeCode());
					// 折扣费率
					disDtlEntity.setRate(new BigDecimal(entity
							.getFavorableDiscount()));
					disDtlEntity.setExpressContinueRate(entity
							.getExpressContinueRate());
					// 折扣金额(四舍五入)
					String favorableAmount = formatNumberInteger(entity.getFavorableAmount());
					disDtlEntity.setAmount(new BigDecimal(favorableAmount));
					// 运单号
					disDtlEntity.setWaybillNo(waybillEntity.getWaybillNo());
					if (entity.getDiscountId() != null) {
						disDtlEntity.setDicountId(entity.getDiscountId());
					} else {
						disDtlEntity.setDicountId(UUIDUtils.getUUID());
					}
					if (entity.getChargeDetailId() != null) {
						disDtlEntity.setWaybillChargeDetailId(entity
								.getChargeDetailId());
					} else {
						disDtlEntity.setWaybillChargeDetailId(UUIDUtils.getUUID());
					}
					disDtlEntity.setCreateTime(new Date());
					disDtlEntity.setModifyTime(new Date());
					disDtlEntity.setActive(FossConstants.ACTIVE);
					disDtlEntity.setBillTime(new Date());
					disDtlEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
					//营销活动编码
	        		disDtlEntity.setActiveCode(entity.getActiveCode());
	        		//营销活动名称
	        		disDtlEntity.setActiveName(entity.getActiveName());
	        		//营销活动开始时间
	        		disDtlEntity.setActiveStartTime(entity.getActiveStartTime());
	        		//营销活动结束时间
	        		disDtlEntity.setActiveEndTime(entity.getActiveEndTime());
	        		//营销活动折扣相应的CRM_ID
	        		disDtlEntity.setOptionsCrmId(entity.getOptionsCrmId());
					disDtlEntityList.add(disDtlEntity);
				}				
			}
		}
		return disDtlEntityList;
}
	

	
	/**
	 * 根据提货方式判断：是否自提
	 */
	public  boolean verdictPickUpSelf(String pickupType){
		String type = StringUtil.defaultIfNull(pickupType);
		if (WaybillConstants.SELF_PICKUP.equals(type)|| WaybillConstants.INNER_PICKUP.equals(type)
				|| WaybillConstants.AIR_PICKUP_FREE.equals(type) || WaybillConstants.AIRPORT_PICKUP.equals(type) 
				|| WaybillConstants.AIR_SELF_PICKUP.equals(type)) 
		{
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 根据提货方式判断：是否送货上门 
	 */
	public boolean  verdictPickUpDoor(String pickupType) {
		String type = StringUtil.defaultIfNull(pickupType);
		if (WaybillConstants.DELIVER_FREE.equals(type) || WaybillConstants.DELIVER_STORAGE.equals(type) 
				|| WaybillConstants.DELIVER_UP.equals(type) || WaybillConstants.DELIVER_NOUP.equals(type) 
				|| WaybillConstants.DELIVER_FREE_AIR.equals(type) || WaybillConstants.DELIVER_NOUP_AIR.equals(type) 
				|| WaybillConstants.DELIVER_UP_AIR.equals(type) || WaybillConstants.DELIVER_INGA_AIR.equals(type)) 
		{
			return true;
		} else {
			return false;
		}
	}
	
	
	/**
	 * 获得预计派送/提货时间/长短途
	 * 
	 */
	public EffectiveDto getPickupDeliveryTime(WaybillEntity entity) {
		
		EffectiveDto effectiveDto = new EffectiveDto();
		if (isPickup(entity)) {
			effectiveDto = waybillManagerService.searchPreSelfPickupTime(entity.getCreateOrgCode(), 
					entity.getLastLoadOrgCode(), entity.getProductCode(), entity.getPreDepartureTime(), new Date());
			if (effectiveDto != null) {
				//bean.setLongOrShort(effectiveDto.getLongOrShort());
				//return effectiveDto.getSelfPickupTime();
				return effectiveDto;
			} else {
				//MsgBox.showInfo(i18n.get("foss.gui.creating.showPickupStationDialogAction.MsgBox.failQueryTime"));
				throw new WaybillValidateException(WaybillValidateException.TIME_EFFECTIVENESS_NULL);
			}
		} else {
			effectiveDto = waybillManagerService.searchPreDeliveryTime(entity.getCreateOrgCode(), 
					entity.getLastLoadOrgCode(), entity.getProductCode(), entity.getPreDepartureTime(), new Date());
			if (effectiveDto != null) {
				//bean.setLongOrShort(effectiveDto.getLongOrShort());
				//return effectiveDto.getDeliveryDate();
				return effectiveDto;
			} else {
				throw new WaybillValidateException(WaybillValidateException.TIME_EFFECTIVENESS_NULL);
			}
		}
	}
	
	/**
	 * 
	 * 判断提货方式是否自提
	 * 
	 */
	public  Boolean isPickup(WaybillEntity entity) {
		
		 //判断提货方式是否为空
		if(entity.getReceiveMethod()!=null){
			String code = entity.getReceiveMethod();
			if (WaybillConstants.SELF_PICKUP.equals(code) || WaybillConstants.INNER_PICKUP.equals(code) ||
					WaybillConstants.AIR_SELF_PICKUP.equals(code) || WaybillConstants.AIR_PICKUP_FREE.equals(code) || WaybillConstants.AIRPORT_PICKUP.equals(code))
			{
				return true;
			} else {
				return false;
			}
		}else{
			return false;
		}		
	}
	
	/**
	 * 
	 * 获得预计出发时间
	 * 
	 */
	public Date getLeaveTime(WaybillEntity entity) {
		
		Date leaveTime = waybillManagerService.searchPreLeaveTime(entity.getCreateOrgCode(), 
				entity.getLoadOrgCode(), entity.getProductCode(), new Date());
		return leaveTime;
	}

	/*
	 * 获取根据尺寸计算后的总体积
	 */
	public BigDecimal getCalculateVolumeTotal(WaybillEntity waybillEntity) {
		BigDecimal calculateVolumeTotal = BigDecimal.ZERO;
		
		if(StringUtils.isNotBlank(waybillEntity.getGoodsSize())){
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("JavaScript");
			Object result=null;
			try {
				result = engine.eval(waybillEntity.getGoodsSize());
			} catch (javax.script.ScriptException e) {
				e.printStackTrace();
			}
			if(result != null){
				//直接先用厘米计算，然后计算为米，保证精度
				BigDecimal bigDecimal = new BigDecimal(result.toString());
				bigDecimal = bigDecimal.setScale(NumberConstants.NUMBER_10, BigDecimal.ROUND_HALF_UP);
				BigDecimal m = new BigDecimal(WaybillConstants.VOLUME_M);// 将厘米转换成米
				bigDecimal = bigDecimal.divide(m);
				//bigDecimal = bigDecimal.setScale(6, BigDecimal.ROUND_HALF_UP);// 四舍五入
				
				//通过尺寸体积如果计算为0，则将体积置为0.01
				if (bigDecimal.compareTo(new BigDecimal(0)) == 0) {
					bigDecimal = new BigDecimal(KEY_VOLUME);
				}
				
				BigDecimal upLimit = new BigDecimal(WaybillConstants.VOLUME_UPLIMIT);
				if (bigDecimal.compareTo(upLimit) > 0) {
					throw new WaybillValidateException(WaybillValidateException.CALCULATED_VOLUME_OVER_LIMIT);
				}else if(BigDecimal.ZERO.compareTo(bigDecimal) > 0){
					throw new WaybillValidateException(WaybillValidateException.VOLUME_NEGATIVE);
				}else{
					calculateVolumeTotal = bigDecimal;
				}
			}
		}
		return calculateVolumeTotal.setScale(NumberConstants.NUMBER_6, BigDecimal.ROUND_HALF_UP);
	}
	
	/*
	 * 设置界面是否修改过
	 * 如果体积没输入或者输入0，要置为0.01，界面是否修改过置为Y
	 * 如果用户输入的尺寸计算小于0.005（四舍五入之后为0），要置为0.01，界面是否修改置为Y
	 *
	 */
	public void setChangeVolume(WaybillEntity waybillEntity, WaybillExpressEntity waybillExpressEntity) {
		
		if(WaybillConstants.PACKAGE.equals(waybillEntity.getProductCode())){
			if(waybillEntity.getGoodsVolumeTotal()!=null && waybillEntity.getGoodsVolumeTotal().doubleValue()<=Double.parseDouble(ZERO)){
				waybillEntity.setGoodsVolumeTotal(BigDecimal.valueOf(KEY_VOLUME));// 货物总体积
				if(waybillExpressEntity!=null){
					waybillExpressEntity.setChangeVolume(FossConstants.YES);
				}
			}
			
			if((new BigDecimal(KEY_VOLUME).compareTo(waybillEntity.getGoodsVolumeTotal())==0) && 
					waybillEntity.getGoodsSize()!=null && waybillExpressEntity!=null ){
				ScriptEngineManager manager = new ScriptEngineManager();
				ScriptEngine engine = manager.getEngineByName("JavaScript");
					Object result = null;
					try {
						result = engine.eval(waybillEntity.getGoodsSize());
					} catch (javax.script.ScriptException e) {
						e.printStackTrace();
					}
					if(result != null){
						@SuppressWarnings("null")
						BigDecimal bigDecimal = new BigDecimal(result.toString());
						bigDecimal = bigDecimal.setScale(NumberConstants.NUMBER_10, BigDecimal.ROUND_HALF_UP);
						BigDecimal m = new BigDecimal(WaybillConstants.VOLUME_M);// 将厘米转换成米
						bigDecimal = bigDecimal.divide(m);
						bigDecimal = bigDecimal.setScale(NumberConstants.NUMBER_6, BigDecimal.ROUND_HALF_UP);// 四舍五入
							
						if(bigDecimal.doubleValue() < POINT_0005){
							waybillExpressEntity.setChangeVolume(FossConstants.YES);
						}
					}
			}
		}		
	}
	

	/**
	 * 
	 * 设置计费重量及计费类型
	 * 
	 */
	public void setBillWeight(WaybillEntity waybillEntity, GuiResultBillCalculateDto dto) {
		if (dto.getVolumeWeight() != null) {
			waybillEntity.setBillWeight(dto.getVolumeWeight()); //设置计费重量
		}else{
			waybillEntity.setBillWeight(waybillEntity.getGoodsWeightTotal());
		}
	}
	
	public List<WaybillChargeDtlEntity> getExpWaybillChargeDtlEntity(WaybillEntity waybillEntity){
		List<WaybillChargeDtlEntity> chargeDtlEntityList = new ArrayList<WaybillChargeDtlEntity>();
		
		
		
		return chargeDtlEntityList;
	}
	
	public List<WaybillDisDtlEntity> getExpWaybillDisDtlEntity(WaybillEntity waybillEntity){
		List<WaybillDisDtlEntity> disDtlEntityList = new ArrayList<WaybillDisDtlEntity>();
		
		return disDtlEntityList;
	}
	
	public List<WaybillPaymentEntity> getExpWaybillPaymentEntity (WaybillEntity waybillEntity){
		List<WaybillPaymentEntity> paymentEntityList = new ArrayList<WaybillPaymentEntity>();
		
		return paymentEntityList;
	}
	

	/*
	 * 以上均为计算快递费用或相关公共方法@end
	 */
	

	/**
	 * 根据部门编码获取简称
	 * @author 136334-BaiLei
	 * @date 2013-08-28 
	 */
	public String getOrgAdminSimpleName(String deptCode,Date billTime){
		//根据部门编码查询部门简称
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgInfoByCodeAndTime(deptCode,billTime); 
		String simpleName = "";
		if(null != org){
			simpleName = StringUtil.defaultIfNull(org.getOrgSimpleName());
		}
		return simpleName;
	}
	
	/**
	 * 四舍五入取整数
	 * @author WangQianJin
	 * @date 2013-5-27 下午6:31:26
	 */
	public BigDecimal formatNumberInteger(BigDecimal number){
		BigDecimal result=number;
		if(number!=null && number.doubleValue()>0){
			result = number.setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
		}
		return result;
	}
	
	/**
	 * 四舍五入保留两位小数
	 * @author WangQianJin
	 * @date 2013-5-27 下午6:31:26
	 */
	public BigDecimal formatNumberTwoDecimal(BigDecimal number){
		BigDecimal result=number;
		if(number!=null && number.doubleValue()>0){
			result = number.setScale(2, BigDecimal.ROUND_HALF_UP);// 四舍五入
		}
		return result;
	}
	
	/**
	 * 四舍五入取整数
	 * @author WangQianJin
	 * @date 2013-5-27 下午6:31:26
	 */
	public String formatNumberInteger(String number){
		String result=number;		
		if(number!=null && !"".equals(result)){
			BigDecimal decimal=new BigDecimal(number);
			if(decimal.doubleValue()>0){
				result = decimal.setScale(0, BigDecimal.ROUND_HALF_UP).toString(); // 四舍五入
			}			
		}
		return result;
	}
	
	/**
	 * 四舍五入保留两位小数
	 * @author WangQianJin
	 * @date 2013-5-27 下午6:31:26
	 */
	public String formatNumberTwoDecimal(String number){
		String result=number;		
		if(number!=null && !"".equals(result)){
			BigDecimal decimal=new BigDecimal(number);
			if(decimal.doubleValue()>0){
				result = decimal.setScale(2, BigDecimal.ROUND_HALF_UP).toString(); // 四舍五入
			}			
		}
		return result;
	}
	
	/* 
	 * 判断数据是否为空，如果为空则返回零
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2013-1-24 下午2:13:14
	 */
	public BigDecimal nullBigDecimalToZero(BigDecimal big) {
		if (big == null) {
			return BigDecimal.ZERO;
		} else {
			return big;
		}
	}
	
    /**
     * 
     * 将布尔转换成字符yes 或者no
     * @author 025000-FOSS-helong
     * @date 2012-11-12 下午03:11:35
     */
    public Boolean stringToBoolean(String value)
    {
    	if(FossConstants.YES.equals(value))
    	{
    	    return true;
    	}else
    	{
    	    return false;
    	}
    }
    
	/**
	 * 若为null，则设置默认值
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-2 下午4:34:48
	 */
	public String defaultIfNull(String str){
		//若对象为空，则返回空字符串
		if(null == str){
			return "";
		}else{
			return str;
		}
	}
	
	/**
	 * 若为null，则设置默认值
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-2 下午4:34:48
	 */
	public BigDecimal defaultIfNull(BigDecimal num){
		//若对象为空，则返回0值
		if(null == num){
			//返回0值 
			return BigDecimal.valueOf(0);
		}
		return num;
	}

	/**
	 * 通过原单编号、开单类型查询运单快递
	 * @param waybillNo
	 * @param returnType
	 * @return
	 */
	@Override
	public WaybillExpressEntity queryWaybillByOriginalWaybillNo(
			String waybillNo, String returnType) {
		List<WaybillExpressEntity> entitys = waybillExpressDao
				.queryWaybillByOriginalWaybillNo(waybillNo);
		if (CollectionUtils.isNotEmpty(entitys)) {
			for (WaybillExpressEntity entity : entitys) {
				ActualFreightEntity actualFreightEntity = actualFreightService
						.queryByWaybillNo(entity.getWaybillNo());
				String status = actualFreightEntity.getStatus();
				if (WaybillConstants.OBSOLETE.equals(status)
						|| WaybillConstants.ABORTED.equals(status)) {
					continue;
				}
//				if (returnType.equals(entity.getReturnType())) {
					return entity;
//				}
			}
		}
		return null;
	}
	/**
	 * 通过原单号查询快递表中的信息
	 * 232608
	 */
	public WaybillExpressEntity queryExpressWaybillByOriginalWaybillNo(String waybillNo){
		if(StringUtils.isEmpty(waybillNo)){
			return null;
		}
		return waybillExpressDao.queryExpressWaybillByOriginalWaybillNo(waybillNo);
	}
	
	
	/**
	 * 通过运单号、开单类型查询运单快递
	 * @param waybillNo
	 * @param returnType
	 * @return
	 */
	@Override
	public WaybillExpressEntity queryWaybillByWaybillNo(String waybillNo,String returnType){
		List<WaybillExpressEntity> entitys = waybillExpressDao.queryWaybillByWaybillNo(waybillNo);
		if(CollectionUtils.isNotEmpty(entitys)){
			WaybillExpressEntity entity =entitys.get(0);
			if(returnType.equals(entity.getReturnType())){
				return entity;
			}
		}
		return null;	
		
	}
	
	/**
 	* 通过运单号、开单类型查询运单快递
	 * @param waybillNo
	 * @param returnType
	 * @return
	 */
	public WaybillExpressEntity queryWaybillByWaybillNo(String waybillNo){
		List<WaybillExpressEntity> entitys = waybillExpressDao.queryWaybillByWaybillNo(waybillNo);
		if(CollectionUtils.isNotEmpty(entitys)){
			WaybillExpressEntity entity =entitys.get(0);
			return entity;
		}
		return null;	
		
	}	
	/**
	 * TODO 通过返单号查询对应原单号
	 * @param waybillNo
	 * @param returnType
	 * @author foss-206860
	 * @date 2015-9-16 下午1:55:20
	 */
	@Override
	public List<WaybillExpressEntity> queryWaybillListByWaybillNo(String waybillNo){
		if(StringUtil.isNotEmpty(waybillNo)){
			List<WaybillExpressEntity> entitys = waybillExpressDao.queryWaybillByWaybillNo(waybillNo);
			if(CollectionUtils.isNotEmpty(entitys)){
				return entitys;
			}
		}
		return null;	
	}
	
	
	
	/**	 
      * 返货开单
	 */
	@Override
	public void addWaybillExpressEntityReturn(
			WaybillExpressEntity waybillExpress) {
		waybillExpressDao.addWaybillExpressEntityReturn(waybillExpress);
		
	}

	/**
	 * 根据查询参数进行查询对应目的站数据:该方法可以直接参考EWaybillService.setTargetOrgCode的方法
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-5-19 18:23:05
	 * @param queryPickupPointDto
	 * @return
	 */
	@Override
	public SaleDepartmentEntity getTargetOrgCode(WaybillEntity waybillEntity) {
		//能走这段逻辑一般是目的站为空或者找不到走货路径,否则调用这个方法属于无理取闹
		if(StringUtils.isEmpty(waybillEntity.getReceiveCustomerCityCode())
				&& StringUtils.isEmpty(waybillEntity.getReceiveCustomerDistCode())
				&& StringUtils.isEmpty(waybillEntity.getReceiveCustomerVillageCode())){
			throw new WaybillValidateException(WaybillValidateException.DESTINATION_IS_NULL_CITY_CODE_DIST_CODE_NOT_ALL_NULL);
		}
		QueryPickupPointDto queryPickupPointDto = new QueryPickupPointDto();
		// 网点类型标志
		ProductEntity productEntity = productService.getLevel3ProductInfo(waybillEntity.getProductCode());
		if(productEntity != null){
			queryPickupPointDto.setDestNetType(productEntity.getDestNetType());
			//判定是否快递
			if(this.onlineDetermineIsExpressByProductCode(productEntity.getCode(), waybillEntity.getBillTime())){
				queryPickupPointDto.setExpressSalesDepartment(FossConstants.YES);
			}
		}
		//收货城市编码
		/*if(StringUtils.isNotEmpty(waybillEntity.getReceiveCustomerCityCode())){
			queryPickupPointDto.setTargetCityCode(waybillEntity.getReceiveCustomerCityCode());
		}*/
		// 提货方式
		queryPickupPointDto.setPickUpType(waybillEntity.getReceiveMethod());
		// 产品编码
		queryPickupPointDto.setTransType(waybillEntity.getProductCode());
		// 出发营业部
		if(!WaybillRfcConstants.RETURN.equals(queryPickupPointDto.getSource())){
			queryPickupPointDto.setReceiveOrgCode(waybillEntity.getCreateOrgCode());
		}
		// 自有营业部类型
		queryPickupPointDto.setSalesType(DictionaryValueConstants.ORG_ARRIVE);
		
		if (queryPickupPointDto.getPickUpPoint() != null) {
			queryPickupPointDto.setPickUpPoint(queryPickupPointDto.getPickUpPoint().trim());
			//如果提货网点有值那么需要清空目的站查询条件
			queryPickupPointDto.setOrgSimpleName("");
		}
		//是否自提
		if (this.verdictPickUpSelf(waybillEntity.getReceiveMethod())) {
			queryPickupPointDto.setPickUpSelf(FossConstants.YES);
		}
		//是否派送
		if (this.verdictPickUpDoor(waybillEntity.getReceiveMethod())) {
			queryPickupPointDto.setPickUpDoor(FossConstants.YES);
		}
		//当前时间(当前日期要大于等于开业日期)
		queryPickupPointDto.setCurDate(new Date());
		queryPickupPointDto.setActive(FossConstants.YES);
		//判定是否区县是否查询到数据
		List<String> salesDepartmentCodeList = new ArrayList<String>();
		List<SaleDepartmentEntity> saleDepartmentEntityList = null;
		//根据乡镇ID来查找目的站
		if(StringUtils.isNotEmpty(waybillEntity.getReceiveCustomerVillageCode())){
			saleDepartmentEntityList = getSaleDepartmentEntityInfo(queryPickupPointDto, waybillEntity.getReceiveCustomerVillageCode());
			if(CollectionUtils.isEmpty(saleDepartmentEntityList)){
				//对之前的数据的清理
				salesDepartmentCodeList.clear();
				//通过收货人区域code查找提货网点
				if(StringUtils.isNotEmpty(waybillEntity.getReceiveCustomerDistCode())){
					saleDepartmentEntityList = getSaleDepartmentEntityInfo(queryPickupPointDto, waybillEntity.getReceiveCustomerDistCode());
				}
			}
			if(CollectionUtils.isEmpty(saleDepartmentEntityList)){
				//对之前的数据的清理
				salesDepartmentCodeList.clear();
				//城市的到达目的站的查找
				if(StringUtils.isNotEmpty(waybillEntity.getReceiveCustomerCityCode())){
					saleDepartmentEntityList = getSaleDepartmentEntityInfo(queryPickupPointDto, waybillEntity.getReceiveCustomerCityCode());
					if(CollectionUtils.isEmpty(saleDepartmentEntityList)){
						LOGGER.info("根据收货人所在城市编码:"+queryPickupPointDto.getTargetCityCode()+"在快递派送区域查询到对应虚拟营业部");
						throw new WaybillValidateException(WaybillValidateException.NOT_EXIST_EXPRESS_DELIVER_SALESDEPT_INFO_OF_CITY_REGION);
					}
				}
			}
		}else if(StringUtils.isNotEmpty(waybillEntity.getReceiveCustomerDistCode())){
			saleDepartmentEntityList = getSaleDepartmentEntityInfo(queryPickupPointDto, waybillEntity.getReceiveCustomerDistCode());
			if(CollectionUtils.isEmpty(saleDepartmentEntityList)){
				//对之前的数据的清理
				salesDepartmentCodeList.clear();
				//城市的到达目的站的查找
				if(StringUtils.isNotEmpty(waybillEntity.getReceiveCustomerCityCode())){
					saleDepartmentEntityList = getSaleDepartmentEntityInfo(queryPickupPointDto, waybillEntity.getReceiveCustomerCityCode());
					if(CollectionUtils.isEmpty(saleDepartmentEntityList)){
						LOGGER.info("根据收货人所在城市编码:"+queryPickupPointDto.getTargetCityCode()+"在快递派送区域查询到对应虚拟营业部");
						throw new WaybillValidateException(WaybillValidateException.NOT_EXIST_EXPRESS_DELIVER_SALESDEPT_INFO_OF_CITY_REGION);
					}
				}
			}
		}else if(StringUtils.isNotEmpty(waybillEntity.getReceiveCustomerCityCode())){
			//城市的到达目的站的查找
			saleDepartmentEntityList = getSaleDepartmentEntityInfo(queryPickupPointDto, waybillEntity.getReceiveCustomerCityCode());
			//如果对应的营业部编码不存在，后台执行脚本会出现问题,在市级派送区域不存在，需要进行抛错处理，不能进行后续操作
			if(CollectionUtils.isEmpty(saleDepartmentEntityList)){
				//设置对应可到达的编码
				LOGGER.info("根据收货人所在城市编码:"+queryPickupPointDto.getTargetCityCode()+"在快递派送区域查询到对应虚拟营业部");
				throw new WaybillValidateException(WaybillValidateException.NOT_EXIST_EXPRESS_DELIVER_SALESDEPT_INFO_OF_CITY_REGION);
			}
		}
		//判定查询到的最终数据是否为空
		if(CollectionUtils.isEmpty(saleDepartmentEntityList)){
			throw new WaybillValidateException(WaybillValidateException.DESTINATION_NULL);
		}
		return getBestSalesDepartment(saleDepartmentEntityList);
	}

	/**
	 * 根据乡镇、区域、城市查询快递虚拟营业部集合
	 * @author 218459-foss-dongsiwei
	 * @date 2015-07-29 19:08:04
	 * @param queryPickupPointDto
	 * @param cityCode
	 * @return
	 */
	private List<SaleDepartmentEntity> getSaleDepartmentEntityInfo(QueryPickupPointDto queryPickupPointDto, String cityCode) {
		List<SaleDepartmentEntity> saleDepartmentEntityList = null;
		List<String> salesDepartmentCodeList = new ArrayList<String>();
		//根据区县进行
		ExpressDeliveryRegionsEntity expressDeliveryRegionsEntity = expressDeliveryRegionsService.queryExpressDeliveryRegionsByCode(cityCode);
		if(expressDeliveryRegionsEntity != null && StringUtils.isNotEmpty(expressDeliveryRegionsEntity.getExpressSalesDeptCode())){
			salesDepartmentCodeList.add(expressDeliveryRegionsEntity.getExpressSalesDeptCode());
		}
		//设置对应可到达的编码
		if(CollectionUtils.isNotEmpty(salesDepartmentCodeList)){
			queryPickupPointDto.setSalesDepartmentCodeList(salesDepartmentCodeList);
			saleDepartmentEntityList = queryAllSuitableSalesDepartment(queryPickupPointDto);
		}else{
			LOGGER.info("根据收货人所在区县编码:"+queryPickupPointDto.getTargetCountyCode()+"在快递派送区域查询到对应虚拟营业部,继续按照市级编码查询对应的派送区域");
		}
		return saleDepartmentEntityList;
	}

	/**
	 * 根据市级、区县进行筛选对应的数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-3-3 14:42:13
	 * @param queryPickupPointDto
	 * @return
	 */
	private List<SaleDepartmentEntity> queryAllSuitableSalesDepartment(QueryPickupPointDto queryPickupPointDto) {
		if(FossConstants.YES.equals(queryPickupPointDto.getPickUpSelf())){
			if(this.onlineDetermineIsExpressByProductCode(queryPickupPointDto.getTransType(), queryPickupPointDto.getCurDate())){
				return waybillDao.queryPickupStationInforExpress(queryPickupPointDto);
			}else{
				return waybillDao.queryByDepartmentInfo(queryPickupPointDto);
			}
		}else{
			if(this.onlineDetermineIsExpressByProductCode(queryPickupPointDto.getTransType(), queryPickupPointDto.getCurDate())){
				//这段代码新加的 小件  查询虚拟部门
				return waybillDao.selectPickupStationInfoVirtualExpress(queryPickupPointDto);
			}else{
				return waybillDao.queryByDepartmentInfo(queryPickupPointDto);
			}
		}
	}
	
	/**
	 * 筛选对应适合的营业部编码
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-5-19 18:51:48
	 * @param saleDepartmentEntityList
	 * @return
	 */
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
	 * 判定是否快递
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-6-3 19:21:26
	 */
	@Override
	public boolean onlineDetermineIsExpressByProductCode(String productCode, Date billTime) {
		return productService.onlineDetermineIsExpressByProductCode(productCode, billTime);
	}
	
	/**
	 * 捕捉异常信息得到最终有效异常数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-6-9 19:57:30
	 * @param e
	 * @return
	 */
	@Override
	public String getExceptionErrorInfo(Exception e){
		String error = null;
		//运单异常 信息校验异常
		if (e instanceof WaybillValidateException) {
			WaybillValidateException exception = (WaybillValidateException) e;
			//捕捉错误类型
			if(StringUtils.isNotEmpty(exception.getErrorCode())){
				LOGGER.error(e.getMessage(), e);
				error = exception.getErrorCode();
			}else{
				LOGGER.error(e.getMessage(), e);
				error = WaybillValidateException.WAYBILL_VALIDATE_UNKOWN_EXCEPTION;
			}
		//运单更改异常
		}else if(e instanceof WaybillRfcChangeException){
			WaybillRfcChangeException exception = (WaybillRfcChangeException) e;
			//捕捉错误类型
			if(StringUtils.isNotEmpty(exception.getErrorCode())){
				LOGGER.error(e.getMessage(), e);
				error = exception.getErrorCode();
			}else{
				LOGGER.error(e.getMessage(), e);
				error = WaybillValidateException.WAYBILLRFC_VALIDATE_UNKOWN_EXCEPTION;
			}
		//结算异常
		}else if(e instanceof SettlementException){
			SettlementException exception = (SettlementException) e;
			//捕捉错误类型
			if(StringUtils.isNotEmpty(exception.getErrorCode())){
				LOGGER.error(e.getMessage(), e);
				error = exception.getErrorCode();
			}else{
				LOGGER.error(e.getMessage(), e);
				error = WaybillValidateException.WAYBILL_SETTLE_VALIDATE_UNKOWN_EXCEPTION;
			}
		//中转线路处理异
		}else if(e instanceof HandleTransportPathException){
			HandleTransportPathException exception = (HandleTransportPathException) e;
			//捕捉错误类型
			if(StringUtils.isNotEmpty(exception.getErrorCode())){
				LOGGER.error(e.getMessage(), e);
				error = exception.getErrorCode();
			}else{
				LOGGER.error(e.getMessage(), e);
				error = WaybillValidateException.HANDLE_TRANSPORT_PATH_UNKOWN_EXCEPTION;
			}
		//订单处理异常
		}else if(e instanceof WaybillOrderHandleException){
			WaybillOrderHandleException exception = (WaybillOrderHandleException) e;
			//捕捉错误类型
			if(StringUtils.isNotEmpty(exception.getErrorCode())){
				LOGGER.error(e.getMessage(), e);
				error = exception.getErrorCode();
			}else{
				LOGGER.error(e.getMessage(), e);
				error = WaybillValidateException.WAYBILL_ORDER_HANDLE_VALIDATE_UNKOWN_EXCEPTION;
			}
		//运单提交异常
		}else if(e instanceof WaybillSubmitException){
			WaybillSubmitException exception = (WaybillSubmitException) e;
			//捕捉错误类型
			if(StringUtils.isNotEmpty(exception.getErrorCode())){
				LOGGER.error(e.getMessage(), e);
				error = exception.getErrorCode();
			}else{
				LOGGER.error(e.getMessage(), e);
				error = WaybillValidateException.WAYBILL_SUBMIT_UNKOWN_EXCEPTION;
			}
		//
		}else if(e instanceof IllegalArgumentException){
			IllegalArgumentException exception = (IllegalArgumentException) e;
			//捕捉错误类型
			if(StringUtils.isNotEmpty(exception.getMessage())){
				LOGGER.error(e.getMessage(), e);
				error = exception.getMessage();
			}else{
				LOGGER.error(e.getMessage(), e);
				error = WaybillValidateException.ILLEGAL_ARGUMENT_INPUT_EXCEPTION;
			}
		}else if(e instanceof PdaInterfaceException){
			PdaInterfaceException exception = (PdaInterfaceException) e;
			//捕捉错误类型
			if(StringUtils.isNotEmpty(exception.getErrorCode())){
				LOGGER.error(exception.getErrorCode(), e);
				error = exception.getErrorCode();
			}else{
				LOGGER.error(e.getMessage(), e);
				error = WaybillValidateException.PDA_INTERFACE_INPUT_EXCEPTION;
			}
		}else if(e instanceof BusinessException){
			BusinessException exception = (BusinessException) e;
			//捕捉错误类型
			if(StringUtils.isNotEmpty(exception.getErrorCode())){
				LOGGER.error(exception.getErrorCode(), e);
				error = exception.getErrorCode();
			}else{
				LOGGER.error(e.getMessage(), e);
				error = WaybillValidateException.WAYBILL_SUBMIT_UNKOWN_EXCEPTION;
			}
		//计算总费用抛出的异常
		}else if(e instanceof BillCaculateServiceException){
			BillCaculateServiceException exception = (BillCaculateServiceException) e;
			//捕捉错误类型
			if(StringUtils.isNotEmpty(exception.getErrorCode())){
				LOGGER.error(exception.getErrorCode(), e);
				error = exception.getErrorCode();
			}else{
				LOGGER.error(e.getMessage(), e);
				error = WaybillValidateException.EWAYBILL_CALCULATE_ALL_FEE_UNKNOW_EXCEPTION;
			}
		}else{
			LOGGER.error(e.getMessage(), e);
			error = WaybillValidateException.EWAYBILL_UNKOWN_EXCEPTION;
		}
		return error;
	}

	/**
	 * 查询系统现有所有快递产品编码
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-14 20:12:44
	 * @return
	 */
	@Override
	public List<String> getAllLevels3ExpressProductCode() {
		return productService.getAllLevels3ExpressProductCode();
	}

	/**
	 * 查询系统现有所有零担产品编码
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-14 20:12:44
	 * @return
	 */
	@Override
	public List<String> getAllLevels3CargoProductCode() {
		return productService.getAllLevels3CargoProductCode();
	}

	/**
	 * 根据三级产品查询出对应的一级产品类型
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-14 20:12:19
	 * @param productCode
	 * @return
	 */
	@Override
	public String getTransTypeByLevel3ProductCode(String productCode) {
		return productService.getTransTypeByLevel3ProductCode(productCode);
	}
	
	@Override
	public List<WaybillExpressEntity> queryWaybillByOriginalWaybillNo(
			String waybillNo) {
		List<WaybillExpressEntity> entitys = waybillExpressDao
				.queryWaybillByOriginalWaybillNo(waybillNo);
		List<WaybillExpressEntity> entityList = new ArrayList<WaybillExpressEntity>();
		if (CollectionUtils.isNotEmpty(entitys)) {
			for (WaybillExpressEntity entity : entitys) {
				ActualFreightEntity actualFreightEntity = actualFreightService
						.queryByWaybillNo(entity.getWaybillNo());
				if(null==actualFreightEntity){
					continue;
				}
				String status = actualFreightEntity.getStatus();
				if (WaybillConstants.OBSOLETE.equals(status)
						|| WaybillConstants.ABORTED.equals(status)) {
					continue;
				}else{
					if(!WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_WAYBILL.equals(entity.getReturnType())){
						entityList.add(entity);
					}
				}
			}
			return entityList;
		}
		return null;
	}
	

	@Override
	//批量生成返货单
	public void addWaybillExpressEntityReturn(List<WaybillExpressEntity> entitys) {
		waybillExpressDao.addWaybillExpressEntityReturn(entitys);
	}

	/**
	 * 
	 */
	@Override
	public List<WaybillExpressEntity> queryWaybillReturnByWaybillNo(
			WaybillExpressEntity waybillExpress) {
		 
		return waybillExpressDao.queryWaybillReturnByWaybillNo(waybillExpress);
	}
}
