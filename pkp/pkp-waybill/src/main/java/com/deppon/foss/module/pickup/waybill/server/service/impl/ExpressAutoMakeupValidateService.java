package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAsteriskSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBargainAppOrgService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusAccountService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusBargainService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressCityService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPrintStarService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFreightRouteService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMarkActivitiesService;
import com.deppon.foss.module.base.baseinfo.api.server.service.INonfixedCustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPreferentialService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BargainAppOrgEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LimitedWarrantyItemsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerPaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressCityResultDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MarkActivitiesQueryConditionDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PreferentialInfoDto;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CusBargainVo;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.pricing.api.server.service.IBillCaculateService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPublishPriceExpressService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.GoodsTypeEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PublishPriceExpressEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateSubDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PriceDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.WaybillDiscountVo;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IInsurGoodsDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillExpressDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICrmOrderService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IExpressAutoMakeupValidateService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISysConfigService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillDisDtlService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillFreightRouteService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.OrgInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.define.ExpWaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.EamDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillChargeDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPaymentEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.AmountInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponWaybillInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EffectiveDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.SalesDepartmentCityDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillImportException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.module.pickup.waybill.shared.vo.OtherChargeVo;
import com.deppon.foss.module.settlement.common.api.server.service.ICustomerBargainService;
import com.deppon.foss.module.settlement.common.api.shared.dto.DebitDto;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IPathDetailDao;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * @author Foss-220125-yangxiaolong
 * @date 2015-05-29 
 * @param 
 * 由于快递自动补录涉及到的校验逻辑不同于电子运单，新加校验逻辑
 * @return
 * @throws Exception 
 */
public class ExpressAutoMakeupValidateService implements IExpressAutoMakeupValidateService{
	
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
	 * 公布价查询服务接口
	 */
	private IPublishPriceExpressService publishPriceExpressService;
	
	
	/**
	 * 部门 复杂查询 service
	 * 提供与部门 复杂查询相关的服务接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	private IWaybillManagerService waybillManagerService;
	
	
	/** 
	 * 营业部 Service接口
	 */
	private ISaleDepartmentService saleDepartmentService;
	
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
	
	public IPathDetailDao getPathDetailDao() {
		return pathDetailDao;
	}

	public void setPathDetailDao(IPathDetailDao pathDetailDao) {
		this.pathDetailDao = pathDetailDao;
	}
	
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

	/**
	 * 营销活动服务接口
	 */
	private IMarkActivitiesService markActivitiesService;

	public void setMarkActivitiesService(
			IMarkActivitiesService markActivitiesService) {
		this.markActivitiesService = markActivitiesService;
	}
	/**
	 * 客户合同优惠信息接口
	 */
	private IPreferentialService preferentialService;
	public void setPreferentialService(IPreferentialService preferentialService) {
		this.preferentialService = preferentialService;
	}
	private IBargainAppOrgService bargainAppOrgService;
	public void setBargainAppOrgService(
			IBargainAppOrgService bargainAppOrgService) {
		this.bargainAppOrgService = bargainAppOrgService;
	}
	
	


	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
	
	public void setPkpsysConfigService(ISysConfigService pkpsysConfigService) {
		this.pkpsysConfigService = pkpsysConfigService;
	}


	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
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
	/**
	 * 根据出发部门编码和到达城市编码查询公布价详细信息
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
	 * 常量值3
	 */
	public static final int THREE = 3;
	/**
	 * 常量值0
	 */
	public static final String ZERO = "0";


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
	
	

	public void setNonfixedCustomerService(
			INonfixedCustomerService nonfixedCustomerService) {
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
	/**
	 * 以下均为生成快递运单时进行的校验或相关公共方法
	 */
	
	private void validateExpWaybill(WaybillEntity entity,WaybillExpressEntity expEntity){
		// PDA运单信息
		//validateWaybillNo(entity);
		
		// 整车约车校验
		validateVehicleNumber(entity);

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
//		validateExpActiveStart(entity); 
		//校验优惠券是否开启
		//validateExpPromotionsCode(entity);
	}
	
	
   /**
	* 
	*总的校验方法(仅供电子运单更改单计算适用 )
	*
	*/
	private void validateExpChangeWaybill(WaybillEntity entity,WaybillExpressEntity expEntity){
		
		// 整车约车校验
		validateVehicleNumber(entity);

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
		//validateAirGoodsType(entity);

		// 只允许合同客户才可以选择免费送货
		validateExpDeliverFree(entity);
		
		//验证重量与提货方式
		validateExpWeightDeliveryMode(entity);
		
		
		//检查试点城市和试点营业部的逻辑(更改单没有此校验)
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
	
   /**
	* 
	*快递重量、体积、件数校验
	*
	*/
	public void validateExpWeightVolume(WaybillEntity entity) {
		if(WaybillConstants.PACKAGE.equals(entity.getProductCode()) || 
				WaybillConstants.ROUND_COUPON_PACKAGE.equals(entity.getProductCode())){
			//总件数不能为0
			if (entity.getGoodsQtyTotal()==null || entity.getGoodsQtyTotal().intValue() == 0) {
				LOGGER.error(WaybillValidateException.NULL_GOODSQTYTOTAL);
				throw new WaybillValidateException("件数不能为0");
			}
			//重量不能为0
			if (entity.getGoodsWeightTotal()==null || entity.getGoodsWeightTotal().compareTo(BigDecimal.ZERO) == 0) {
				LOGGER.error(WaybillValidateException.NULL_GOODSWEIGHTTOTAL);
				throw new WaybillValidateException("重量不能为0");
			}
			
			//毛重小于等于50kg*件数，体积小于等于0.3*件数
			BigDecimal qtyWeightUpperLimit = BigDecimal.valueOf(ExpWaybillConstants.WEIGHT_UPPER_LIMIT).multiply(BigDecimal.valueOf(entity.getGoodsQtyTotal()));
			BigDecimal qtyVolumeUpperLimit = BigDecimal.valueOf(ExpWaybillConstants.VOLUME_UPPER_LIMIT).multiply(BigDecimal.valueOf(entity.getGoodsQtyTotal()));
			if(entity.getGoodsWeightTotal() !=null && qtyWeightUpperLimit.compareTo(entity.getGoodsWeightTotal()) < 0){ 
				LOGGER.error(WaybillValidateException.EXPWAYBILL_WEIGHT_OVERLIMIT);
				throw new WaybillValidateException("快递单件重量超重");
			}
			if(entity.getGoodsVolumeTotal() != null && qtyVolumeUpperLimit.compareTo(entity.getGoodsVolumeTotal())<0){
				LOGGER.error(WaybillValidateException.EXPWAYBILL_VOLUME_OVERLIMIT);
				throw new WaybillValidateException("快递单件体积超方");
			}
		}	
	}
	/**
	 * 目的站校验
	 * 
	 */	
	public void validateExpDestination(WaybillEntity waybillEntity) {
		//StringUtils.isBlank(waybillEntity.getTargetOrgCode())????需要校验这个吗？？
		if(StringUtils.isBlank(waybillEntity.getCustomerPickupOrgCode()) ){
			LOGGER.error("目的站为空");
			throw new WaybillValidateException("目的站为空");
		}
		
		SaleDepartmentEntity saleDeprtTemp =saleDepartmentService.querySaleDepartmentByCode(waybillEntity.getCustomerPickupOrgCode());
		
		if(saleDeprtTemp == null){
			throw new WaybillValidateException("目的站为空");
		}
		
		//是否支持开多件
		if(waybillEntity.getGoodsQtyTotal() != null && waybillEntity.getGoodsQtyTotal() > 1){
			//如果不支持开多件，则抛出异常
			if(!FossConstants.YES.equals(saleDeprtTemp.getCanExpressOneMany())){
				//为了响应业务号召，12月28号取消目的站一票多件校验
				//throw new WaybillValidateException("该开单部门不能开一票多件");
			}
		}
//		
//		//判断是否能开代收货款
//		if(waybillEntity.getCodAmount() !=null && waybillEntity.getCodAmount().compareTo(BigDecimal.ZERO)>0){
//			if(!FossConstants.YES.equals(saleDeprtTemp.getCanAgentCollected())){
//				throw new WaybillValidateException(WaybillValidateException.PICKUP_STATSION_CANNOT_COD);
//			}
//		}
		
		//营业部是否支持签收单返单
		if(waybillEntity.getReturnBillType() != null && StringUtils.isNotBlank(waybillEntity.getReturnBillType())
				&& !waybillEntity.getReturnBillType().equals(WaybillConstants.NOT_RETURN_BILL)){
			if(!FossConstants.YES.equals(saleDeprtTemp.getCanExpressReturnSignBill())){
				throw new WaybillValidateException("提货网点不能做快递返单，请配置基础资料");
			}
		}
		
	}

	/**
	 * 产品性质校验
	 * 
	 */	
	public void validateExpProduct(WaybillEntity entity) {
		if(entity.getProductCode() == null ||"".equals(entity.getProductCode())){
			LOGGER.error(WaybillValidateException.PRODUCT_CODE_NULL);
			throw new WaybillValidateException("产品类型为空");
		}
	}

	/**
	 * 产品包装校验-包装件数不能大于开单件数
	 * 
	 */	
	public void validateExpPack(WaybillEntity entity) {
		Integer qtyTotal = entity.getGoodsQtyTotal();// 总件数

		// 不允许包装各项的数值为null
		if (null == entity.getPaperNum() || null == entity.getWoodNum() || 
				null == entity.getFibreNum() || null == entity.getSalverNum() || null == entity.getMembraneNum()) {
			LOGGER.error(WaybillValidateException.GOOD_PACKAGE_NULL);
			throw new WaybillValidateException("包装为空");
		}
		
		// 不允许包装信息全部为空
		if (0 == entity.getPaperNum().intValue() && 0 == entity.getWoodNum().intValue() 
				&& 0 == entity.getFibreNum().intValue() && 0 == entity.getSalverNum().intValue() 
				&& 0 == entity.getMembraneNum().intValue() && StringUtils.isEmpty(entity.getOtherPackage())) {
			LOGGER.error(WaybillValidateException.GOOD_PACKAGE_NULL);
			throw new WaybillValidateException("包装为空");
		}
		
		Integer paper = Integer.valueOf(entity.getPaperNum());// 纸
		Integer wood = Integer.valueOf(entity.getWoodNum());// 木
		Integer fibre = Integer.valueOf(entity.getFibreNum());// 纤
		Integer salver = Integer.valueOf(entity.getSalverNum());// 托
		Integer membrane = Integer.valueOf(entity.getMembraneNum());// 膜
		Integer packTotal = paper + wood + fibre + salver + membrane;

		if (packTotal > qtyTotal) {
			LOGGER.error(WaybillValidateException.GOOD_PACKAGE_OVERLIMIT);
			throw new WaybillValidateException("包装件数大于开单件数");
		}
	}

	/*
	 * 客户信息校验 -校验客户联系人联系方式地址
	 * 
	 */	
	public void validateExpCustomer(WaybillEntity entity) {
		
		int lengthLimitEight = EIGHT;
		int lengthLimitEleven = ELEVEN;
		
		//发货联系人不能为空
		if(StringUtils.isEmpty(entity.getDeliveryCustomerContact())){
			throw new WaybillValidateException("发货客户联系人不能为空 ");
		}
		//收货联系人不能为空
		if(StringUtils.isEmpty(entity.getReceiveCustomerContact())){
			throw new WaybillValidateException("收货客户联系人不能为空 ");
		}
			
		//发货人手机号码规格校验
		if (StringUtils.isNotEmpty(entity.getDeliveryCustomerMobilephone())) {
			if (!"".equals(entity.getDeliveryCustomerMobilephone().trim())) {
				int phoneLength = entity.getDeliveryCustomerMobilephone().trim().length();

				if (phoneLength != lengthLimitEight && phoneLength != lengthLimitEleven) {
					LOGGER.error(WaybillValidateException.DELIVERY_MOBILEPHONE_LENGTH_ILLEGAL);
					throw new WaybillValidateException("发货客户手机号码必须是8位或者11位");
				}

				if (phoneLength == lengthLimitEleven) {
					if (!entity.getDeliveryCustomerMobilephone().startsWith("1")) {
						LOGGER.error(WaybillValidateException.DELIVERY_MOBILEPHONE_FIRST_NUMBER_ILLEGAL);
						throw new WaybillValidateException("发货客户手机号码11位时，必须1开头");
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
					throw new WaybillValidateException("收货客户手机号码必须是8位或者11位");
				}

				if (phoneLength == lengthLimitEleven) {
					if (!entity.getReceiveCustomerMobilephone().startsWith("1")) {
						LOGGER.error(WaybillValidateException.RECEIVER_MOBILEPHONE_FIRST_NUMBER_ILLEGAL);
						throw new WaybillValidateException("收货客户手机号码11位时，必须1开头");
					}
				}
			}
		}
		
		//发货客户电话或手机必填一项
		if (StringUtils.isEmpty(entity.getDeliveryCustomerMobilephone()) && StringUtils.isEmpty(entity.getDeliveryCustomerPhone())) {
			throw new WaybillValidateException("发货人手机或者电话号码必须有一项不为空");
		}
		//收货客户电话或手机必填一项
		if (StringUtils.isEmpty(entity.getReceiveCustomerMobilephone()) && StringUtils.isEmpty(entity.getReceiveCustomerPhone())) {
			throw new WaybillValidateException("收货人手机或者电话号码必须有一项不为空");
		}

//		// 原件签收单返回
//		if (WaybillConstants.RETURNBILLTYPE_ORIGINAL.equals(entity.getReturnBillType())) {
//			if(StringUtils.isEmpty(entity.getDeliveryCustomerProvCode()) || StringUtils.isEmpty(entity.getDeliveryCustomerCityCode())
//					|| StringUtils.isEmpty(entity.getDeliveryCustomerAddress())){			
//				throw new WaybillValidateException("发货人地址为空");
//			}
//		}
//		
		// 提货方式为空
		if(entity.getReceiveMethod()==null){
			throw new WaybillValidateException("提货方式为空");
		}
		
		// 收货人地址不能为空
		String code = entity.getReceiveMethod();
		if (WaybillConstants.DELIVER_NOUP.equals(code) || WaybillConstants.DELIVER_FREE.equals(code) || WaybillConstants.DELIVER_STORAGE.equals(code) || WaybillConstants.DELIVER_UP.equals(code) || WaybillConstants.DELIVER_FREE_AIR.equals(code)
				|| WaybillConstants.DELIVER_NOUP_AIR.equals(code) || WaybillConstants.DELIVER_UP_AIR.equals(code) || WaybillConstants.DELIVER_INGA_AIR.equals(code)) {
			if(StringUtils.isEmpty(entity.getReceiveCustomerProvCode()) || StringUtils.isEmpty(entity.getReceiveCustomerAddress()) ){
				throw new WaybillValidateException("收货人地址为空");
			}
		}
		
	}

	/**
	 * 校验提货网点单票以及单件重量与体积上限
	 * 
	 */	
	public void validateExpVW(WaybillEntity entity) {
		//对内备注
		int innerNotes = StringUtil.defaultIfNull(entity.getInnerNotes()).length();
		//储运事项 
		int transNotes = StringUtil.defaultIfNull(entity.getTransportationRemark()).length();
		
		if(innerNotes > CLOB_LIMIT){
			///对内备注录入错误：最大字符不能超过1300个！
			throw new WaybillValidateException("对内备注超过1300字符");
		}
		
		if(transNotes > CLOB_LIMIT){
			//储运事项 录入错误：最大字符不能超过1300个！
			throw new WaybillValidateException("储运事项超过1300字符");
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
					throw new WaybillValidateException("当前运单单件重量超出该提货网点单件重量上限");
				}
			}

			if (pickupStationSaleDept.getSinglePieceLimitvol() != null) {
				// 单件体积上限
				BigDecimal singlePieceLimitvol = BigDecimal.valueOf(pickupStationSaleDept.getSinglePieceLimitvol()).divide(new BigDecimal(NumberConstants.NUMBER_100));
				if (pieceVolume.compareTo(singlePieceLimitvol) > 0) {
					throw new WaybillValidateException("当前运单单件体积超出该提货网点单件体积上限");
				}
			}

			if (pickupStationSaleDept.getSingleBillLimitkg() != null) {
				// 单票重量上限
				BigDecimal singleBillLimitkg = BigDecimal.valueOf(pickupStationSaleDept.getSingleBillLimitkg()).divide(new BigDecimal(NumberConstants.NUMBER_100));
				if (goodsWeight.compareTo(singleBillLimitkg) > 0) {
					throw new WaybillValidateException("当前运单总重量超出该提货网点单票重量上限");
				}
			}

			if (pickupStationSaleDept.getSingleBillLimitvol() != null) {
				// 单票体积上限
				BigDecimal singleBillLimitvol = BigDecimal.valueOf(pickupStationSaleDept.getSingleBillLimitvol()).divide(new BigDecimal(NumberConstants.NUMBER_100));
				if (goodsVolume != null && goodsVolume.compareTo(singleBillLimitvol) > 0) {
					throw new WaybillValidateException("当前运单总重量超出该提货网点单票体积上限");
				}
			}
		}		
	}
		
	/**
	 * 支付方式校验 -验证提货方式为机场自提时，不能开到付
	 * 
	 */	
	public void  validateExpPaymentMode(WaybillEntity entity){
		if (entity.getPaidMethod() == null) {
			throw new WaybillValidateException("支付方式为空");
		}

		if(WaybillConstants.MONTH_PAYMENT.equals(entity.getPaidMethod())){
			CusBargainVo vo=new CusBargainVo();
			vo.setExpayway(WaybillConstants.MONTH_END);
			vo.setCustomerCode(entity.getDeliveryCustomerCode());
			vo.setBillDate(new Date());
			vo.setBillOrgCode(entity.getReceiveOrgCode());
			if(null==entity.getDeliveryCustomerCode()){
				throw new WaybillValidateException("客户编码为空，不能开月结");
			}
			CusBargainEntity cusBargain = cusBargainService.queryCusBargainByVoExp(vo);
			if(cusBargain == null){
				//该客户非合同客户不能开月结运单
				throw new WaybillValidateException("非月结客户付款方式不能为月结");
			}
		}

		//提货方式为机场自提，付款方式不能选择到付
		if (WaybillConstants.AIRPORT_PICKUP.equals(entity.getReceiveMethod())) {
			if (entity.getPaidMethod() != null) {
				if (WaybillConstants.ARRIVE_PAYMENT.equals(entity.getPaidMethod())) {
					throw new WaybillValidateException("提货方式为机场自提，付款方式不能选择到付");
				}
			}

			//提货方式为【机场自提】时，到付金额必须为0
			if (entity.getToPayAmount().compareTo(BigDecimal.ZERO) > 0) {
				throw new WaybillValidateException("提货方式为【机场自提】时，到付金额必须为0");
			}
		}
		
		if(!WaybillConstants.YES.equals(entity.getIsWholeVehicle()))
		{
			//提货网点是否可以货到付款
			SaleDepartmentEntity pickupStationSaleDept = saleDepartmentService.querySaleDepartmentByCode(entity.getCustomerPickupOrgCode());
			if(!FossConstants.YES.equals(pickupStationSaleDept.getCanCashOnDelivery()))
			{
				if (WaybillConstants.ARRIVE_PAYMENT.equals(entity.getPaidMethod())) {
					throw new WaybillValidateException("提货网点不支持货到付款");
				}
			}
		}

		// 空运以及偏线无法选择网上支付
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(entity.getProductCode()) || 
				ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(entity.getProductCode())) {
			if (WaybillConstants.ONLINE_PAYMENT.equals(entity.getPaidMethod())) {
				throw new WaybillValidateException("空运以及偏线无法选择网上支付");
			}
		}

		//临欠、散客开单付款方式为临时欠款时，客户编码不允许为空
		if (WaybillConstants.TEMPORARY_DEBT.equals(entity.getPaidMethod())) {
			//判断客户编码是否为空
			if (StringUtil.isEmpty(entity.getDeliveryCustomerCode())) {
				throw new WaybillValidateException("客户编码不能为空");
			}
		}
	}
		
	/**
	 * 代收货款校验
	 * 
	 */
	public void validateExpCod(WaybillEntity entity){	
		//代收货款为0，不能进行代收业务校验不处理（开单界面有此校验）
		
		//代收货款不为0的处理
		if(entity.getCodAmount() != null && entity.getCodAmount().compareTo(BigDecimal.ZERO) > 0){
			if(StringUtils.isBlank(entity.getRefundType())){
				//退款类型不能为空
				throw new WaybillValidateException("退款类型不能为空");
			}else{
					if(StringUtils.isBlank(entity.getAccountName()) || StringUtils.isBlank(entity.getAccountCode())){
						throw new WaybillValidateException("开户信息不能为空");
					}
			}
			
			//目的站所选网点不能开代收货款
			SaleDepartmentEntity saleDept = saleDepartmentService.querySimpleSaleDepartmentByCode(entity.getCustomerPickupOrgCode());
			if(null!=saleDept&&!WaybillConstants.YES.equals(saleDept.getCanAgentCollected())){
				throw new WaybillValidateException("目的站所选网点不能开代收货款");
			}
				
			//校验银行信息
			validateExpBankInfo(entity);

		}
	}

	/**
	 * 校验银行信息
	 * 
	 */
	public void validateExpBankInfo(WaybillEntity entity){
		if(entity == null){
			throw new WaybillValidateException("运单信息实体不能为空");
		}else if (StringUtils.isBlank(entity.getDeliveryCustomerCode())) {
			throw new WaybillValidateException("客户编码不能为空");
		} else {
				CusAccountEntity cusAccountEntity = queryEWaybillCusAccountInfo(entity);
				//代收大于0时，不允许帐号实体信息为空
				if(entity.getCodAmount().compareTo(BigDecimal.ZERO)>0 && cusAccountEntity == null){
					throw new WaybillValidateException("找不到银行账户信息");
				}
				if (cusAccountEntity != null) {
					if (cusAccountEntity.getAccountNature() == null || "".equals(cusAccountEntity.getAccountNature())) {
						throw new WaybillValidateException("选中的银行账户没有设置对公或者对私属性");
					} else {
						// 即日退只能选择对私账户
						if (WaybillConstants.REFUND_TYPE_ONE_DAY.equals(entity.getRefundType())&& 
								!DictionaryValueConstants.CRM_ACCOUNT_NATURE_PRIVATE_ACCOUNT.equals(cusAccountEntity.getAccountNature())) {
							throw new WaybillValidateException("即日退只能选择对私账户");
						} 
						if(!StringUtil.equals(cusAccountEntity.getAccountNo(), entity.getAccountCode())
							||!StringUtil.equals(cusAccountEntity.getAccountName(), entity.getAccountName())){
							throw new WaybillValidateException("系统中存在的银行信息与供应商补录的银行信息不一致");
						}
					}
				}
		}		
	}

	/**
	 * 通过银行帐号查询CusAccountEntity
	 */
	public CusAccountEntity queryEWaybillCusAccountInfo(WaybillEntity entity){
		CusAccountEntity cusAccountEntity = null;
		if(StringUtils.isBlank(entity.getDeliveryCustomerCode())){
			throw new  WaybillValidateException("客户编码不能为空");
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
		
	/**
	 * 只允许合同客户才可以选择免费送货
	 * 
	 */
	public void validateExpDeliverFree(WaybillEntity entity){
		if(entity.getReceiveMethod()==null){
			throw new WaybillValidateException("提货方式为空");
		}
		
		if (WaybillConstants.DELIVER_FREE.equals(entity.getReceiveMethod()) || 
				WaybillConstants.DELIVER_FREE_AIR.equals(entity.getReceiveMethod())) {
				CusBargainEntity cusBargainEntity = cusBargainService.queryCusBargainByCustCode(entity.getDeliveryCustomerCode());
				//只有合同客户才允许免费派送
				if(cusBargainEntity == null || StringUtils.isBlank(cusBargainEntity.getBargainCode())){
					throw new WaybillValidateException("没有合同或者合同找不到");
				}
		}
	}		
		
	/**
	 * 验证重量与提货方式
	 * 
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
			throw new WaybillValidateException("代收货款超过规定最大值",value);
		}

		//收货部门不能为空
		if(StringUtils.isEmpty(waybillEntity.getReceiveOrgCode())){ 
			throw new WaybillValidateException("收货部门为空或未找到");
		}
		
		//返单或者返货的情况下，必须填写原始单号
		if(StringUtils.isNotBlank(waybillExpressEntity.getReturnType())){
			if(StringUtils.isBlank(waybillExpressEntity.getOriginalWaybillNo())){
				throw new WaybillValidateException("返单返货时，必须填写原始运单号");
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
			throw new WaybillValidateException("未找到开单部门，不能开快递单");
		}else if(ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(createDto.getCityType()) && 
				!FossConstants.YES.equals(createDto.getTestSalesDepartment())){
			//试点城市 非试点营业部不能开单
			throw new WaybillValidateException("非试点营业部，不能开快递单");
		}
		
		if(endDto==null){
			//请重新选择提货网点
			throw new WaybillValidateException("未找到开单部门，不能开快递单");
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
					throw new WaybillValidateException("试点城市和非试点城市之间提货方式只能开自提");					
				}
			}
			
			//返单类别
			if(StringUtils.isNotBlank(waybillEntity.getReturnBillType()) 
					&& !WaybillConstants.NOT_RETURN_BILL.equals(waybillEntity.getReturnBillType())){
				//试点城市和非试点城市之间不能开返单
				throw new WaybillValidateException("试点城市和非试点城市之间不能开返单");
			}
		}
		
		
		//开始试点城市    到达-快递代理
		if(ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(createDto.getCityType()) && 
				ExpWaybillConstants.CITY_TYPE_PEIZAI.equals(endDto.getCityType())){
			
			//返单类别
			if(StringUtils.isNotBlank(waybillEntity.getReturnBillType())
					&& !WaybillConstants.NOT_RETURN_BILL.equals(waybillEntity.getReturnBillType())){
				//试点城市和非试点城市之间不能开返单
				throw new WaybillValidateException("试点城市和非试点城市之间不能开返单");
			}
			
			if(StringUtils.isNotBlank(waybillEntity.getRefundType()) && StringUtils.isNotBlank(waybillEntity.getReceiveMethod()) &&
				WaybillConstants.REFUND_TYPE_ONE_DAY.equals(waybillEntity.getRefundType())){
				//试点城市和快递代理城市之间不能开1日退代收货款款
				String receiveMethod =waybillEntity.getReceiveMethod();
				//是否自提
				if (!verdictPickUpSelf(receiveMethod)) {
					throw new WaybillValidateException("试点城市和快递代理理城市之间不能开即日退代收货款");					
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
				throw new WaybillValidateException("非试点城市和其他城市之间只能开淘宝订单");
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
				throw new WaybillValidateException("非试点城市和非试点城市之间只能开淘宝订单");
			}
			
			if(StringUtils.isNotBlank(waybillEntity.getReceiveMethod())){
				// 提货方式
				String receiveMethod = waybillEntity.getReceiveMethod();
				//是否自提
				if (!verdictPickUpSelf(receiveMethod)) {
					//非试点城市和非试点城市之间提货方式只能开自提
					throw new WaybillValidateException("非试点城市和非试点城市之间提货方式只能开自提");					
				}
			}
			
			
			//返单类别
			if(StringUtils.isNotBlank(waybillEntity.getReturnBillType())
					&& !WaybillConstants.NOT_RETURN_BILL.equals(waybillEntity.getReturnBillType())){
				//非试点城市和非试点城市之间不能开返单
				throw new WaybillValidateException("试点城市和非试点城市之间不能开返单");
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
					throw new WaybillValidateException("非试点城市快递代理城市之间不能发送货快递运单");
				}
			}
		}
		
		//到达城市是非试点  不能开返单
		if(!ExpWaybillConstants.CITY_TYPE_SHIDIAN.equals(endDto.getCityType())){
			if(StringUtils.isNotBlank(waybillEntity.getReturnBillType()) 
					&& !WaybillConstants.NOT_RETURN_BILL.equals(waybillEntity.getReturnBillType())){
				//到达城市是非试点的情况下，不能开返单
				throw new WaybillValidateException("到达城市是非试点的情况下，不能开返单");
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
					throw new WaybillValidateException("自提不能选择虚拟营业部作为提货网点开单");
				}						
			}else{
				if(!verdictPickUpSelf(receiveMethod)) {
					//非自提只能选择虚拟营业部开单  
					throw new WaybillValidateException("非自提只能选择虚拟营业部开单");
				}
			}
		}
	}

	/**
	 * 检查保险声明价值
	 * 
	 */
	public void validateExpInsuranceFee(WaybillEntity entity){
		BigDecimal insuranceAmount = entity.getInsuranceAmount();

		if(insuranceAmount==null || insuranceAmount.doubleValue()<=0){
			if(entity.getInsuranceFee()!=null && entity.getInsuranceFee().doubleValue()>0){
				throw new WaybillValidateException("保险声明价值为0的情况下保费不能大于0");
			}			
		}
	}
		
	/**
	 * 检查德邦客户和发货人工号
	 * 
	 */
	public void validateExpDepponExpressEmpCode(WaybillExpressEntity expEntity){	
		 if(WaybillConstants.DEPPON_CUSTOMER.equals(expEntity.getDeliveryEmployeeCode())){
			 if(StringUtils.isEmpty(expEntity.getDeliveryEmployeeCode())){
				 throw new WaybillValidateException("内部带货发货人工号为空");					 
			 }
		 }
	}
		
	/**
	 * 校验优惠券配置参数是否开启(电子运单如果没开启，则将优惠券置空)
	 * 
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
						throw new WaybillValidateException("优惠券未启用");						
					}
				}
		 }
	}
	
	/**
	 * 校验营销活动是否开启
	 * 
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
					throw new WaybillValidateException("市场推广活动未启用");						
				}
			}
		}
	}
	/**
	 * 以上均为生成快递运单时进行的校验或相关公共方法@end
	 */
	
	/**
	 * 以下均为计算快递费用或相关公共方法@start
	 * 计算所有快递费用并生成waybill相关实体
	 * 注1：零担或者空运有的而快递没有的已经去掉了，装卸费没有计算，优惠券如果不可用直接置空，不会抛异常
	 * 注2：如果改动了注1中的逻辑，请务必修改注释
	 */
	public void calculateExpressAllFee(com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto waybillDto, String status,EamDto eamDto){
		//优惠券的票面费用
		BigDecimal couponFee = null;
		
		//优惠券优惠费用归集类型
		String couponRankType = null;
		
		//设置未冲减优惠券的运费
		BigDecimal beforeProTranFee = null;
		//运单基础数据
		WaybillEntity waybillEntity = waybillDto.getWaybillEntity();
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

		// 判断是否内部带货:如果内部带货，不能计算优惠券
		if (!WaybillConstants.DEPPON_CUSTOMER.equals(waybillEntity.getDeliveryCustomerCode()) && null != waybilExpressEntity 
				&& !WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_WAYBILL.equals(waybilExpressEntity.getReturnType())) {
			// 计算各种费用 -----------------------------------------------------营销活动还没计算，别忘记了
			calculateExpressFee(waybillEntity, false, waybillDiscountVos,
					waybillChargeDtlEntityList,couponFee,couponRankType,isAccurateCost);
			/**
			 * 新添加 (gyk)
			 * 若单票包装费字段不为空，计算运费时从CRM中直接读取CRM中的单票包装费的值，且包装费字段置灰，不可修改。
			 */
			PreferentialInfoDto preferentialInfoDto = queryPreferentialInfo(waybillEntity);
			if(preferentialInfoDto!=null){
					if(preferentialInfoDto.getSinTicketPackCharge()!=null){
						waybillEntity.setPackageFee(preferentialInfoDto.getSinTicketPackCharge());
					}
			}
			//计算完毕之后重新设置包装费
			if(null!=eamDto.getPackageFeeCanvas() && 
			new BigDecimal(eamDto.getPackageFeeCanvas()).compareTo(new BigDecimal("0"))>=0){
				waybillEntity.setPackageFee(new BigDecimal(eamDto.getPackageFeeCanvas()));
				if(null!=waybillEntity.getValueAddFee()){
				   waybillEntity.setValueAddFee(waybillEntity.getValueAddFee().add(waybillEntity.getPackageFee()));
				}else{
				   waybillEntity.setValueAddFee(new BigDecimal("0").add(waybillEntity.getPackageFee()));	
				}
			}
			
			/**
			 * 如果有优惠券编号，
			 * 需要计算两次总运费：原因是，
			 * 优惠券要求把总运费传到CRM进行校验
			 */
			if (!StringUtils.isEmpty(waybillEntity.getPromotionsCode())) {
				//不是整车才处理优惠券，因为整车没有走货路径，获取最终配载部门时会报异常	
				if(!WaybillConstants.YES.equals(waybillEntity.getIsWholeVehicle()) && !WaybillConstants.DEPPON_CUSTOMER.equals(waybillEntity.getDeliveryCustomerCode())){
					// 处理优惠券
					executeCoupon(waybillEntity,waybillChargeDtlEntityList);
					//设置未冲减优惠券的运费
					beforeProTranFee = waybillEntity.getTransportFee();
					//设置优惠券的票面费用
					 couponFee =waybillEntity.getCouponFee();
					//设置优惠券优惠费用归集类型
					 couponRankType = waybillEntity.getCouponRankType();
				}						
			}
			//设置优惠总费用
			calcaulatePromotionsFee(waybillEntity,couponFee,waybillDiscountVos,couponRankType);
			
			//设置费用及费率的规格（计算完成之后，费用四舍五入，费率除以1000）
			setWaybillFeeScale(waybillEntity,isAccurateCost);
			
			// 需要重新计算运费
			calculateTotalFee(waybillEntity,true,couponFee,couponRankType);					
		}else{
			//计算内部带货公布价运费
			calculateInnerPickupTransFee(waybillEntity,waybillDiscountVos,waybillChargeDtlEntityList,isAccurateCost);	
			//设置费用及费率的规格（计算完成之后，费用四舍五入，费率除以1000）
			setWaybillFeeScale(waybillEntity,isAccurateCost);
			// 内部带货金额清零
			resetZero(waybillEntity);

		}				
		
		// 处理增值优惠券费用	
		offsetCouponFee(waybillEntity , beforeProTranFee, couponFee,waybillDiscountVos, couponRankType,isAccurateCost);
		// 处理完优惠券清空优惠券费用，防止再次冲减
		cleanCouponFee(couponFee);
		
		//生成List<WaybillChargeDtlEntity> waybillChargeDtlEntityList费用明细方法，最后根据类型set费用值，前面每一步设置其他属性
		//如果费用值计算为0，则该项置null
		//在calculateExpressFee已经计算过了，这里需要重新把费用信息赋值进去，如果状态需要调整，亦调整状态
		waybillChargeDtlEntityList = getWaybillChargeDtlEntityList(waybillEntity,waybillChargeDtlEntityList,status);
		if(waybillChargeDtlEntityList != null){
			waybillDto.setWaybillChargeDtlEntity(waybillChargeDtlEntityList);
		}
		
		//生成List<WaybillDisDtlEntity> waybillDisDtlEntity折扣明细方法，通过WaybillDiscountVos
		waybillDisDtlEntityList = getWaybillDisDtlEntityList(waybillEntity,waybillDiscountVos,status);
		if(waybillDisDtlEntityList != null){
			waybillDto.setWaybillDisDtlEntity(waybillDisDtlEntityList);
		}
		
		//生成List<WaybillPaymentEntity> WaybillPaymentEntityList支付明细方法
		waybillPaymentEntityList = getWaybillPaymentEntityList(waybillEntity,waybilExpressEntity);
		
		if(waybillPaymentEntityList != null){
			waybillDto.setWaybillPaymentEntity(waybillPaymentEntityList);
		}
		
	}
	/**
	 * 验证整车业务规则
	 * 
	 * @author 220125 YangXiaoLong
	 * @date 2015-07-08 下午05:08:10
	 */
	private void validateVehicleNumber(WaybillEntity bean) {
		if ("Y".equals(bean.getIsWholeVehicle())) {
			if (StringUtils.isEmpty(bean.getOrderVehicleNum())) {
				throw new WaybillValidateException("整车时约车编号不能为空！");
			}
		}
	}
	/**
	 * 
	 * 设置费用及费率的规格（计算完成之后，费用四舍五入，费率除以1000）
	 * 
	 */
	private void setWaybillFeeScale(WaybillEntity waybillEntity,boolean isAccurateCost){
		MathContext mc = new MathContext(NumberConstants.NUMBER_5, RoundingMode.HALF_DOWN);
		
		if(waybillEntity.getTransportFee()!= null && waybillEntity.getTransportFee().compareTo(BigDecimal.ZERO) >0){
			BigDecimal transportFee = waybillEntity.getTransportFee();
			if(isAccurateCost){
				waybillEntity.setTransportFee(transportFee);
			}else{
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

	/**
	 * 计算快递费用的方法（仅限于快递适用）
	 * 
	 */
	public void calculateExpressFee(WaybillEntity entity, boolean needMinusCoupen, 
			List<WaybillDiscountVo>  waybillDiscountVos, List<WaybillChargeDtlEntity> waybillChargeDtlEntityList,
			BigDecimal couponFee,String couponRankType,boolean isAccurateCost){
		
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
		
		//重要注释：@ 营销活动需要算-----------------------------------------------------------------------
		//营销活动DTO
		
		validateActiveDiscount(entity,true,productPriceDtoCollection);
		
		//是否计算市场营销折扣
//		productPriceDtoCollection.setCalActiveDiscount(bean.isCalActiveDiscount());
		
		//封装市场营销活动校验条件
		settterActiveParamInfo(productPriceDtoCollection,entity);
		

		//统一返回的计价值
		List<GuiResultBillCalculateDto> guiResultBillCalculateDtos =null;
		
		//德邦客户 金额都是0
		if(WaybillConstants.DEPPON_CUSTOMER.equals(entity.getDeliveryCustomerCode())){
			guiResultBillCalculateDtos = getInnerPickupGuiResultBillCalculateDto(entity);
		}else{
			guiResultBillCalculateDtos	= waybillManagerService.queryGuiExpressBillPrice(productPriceDtoCollection);
		}
		
		//重要注释:电子运单不需要营销活动，因为工作量原因，将营销活动相关代码注释调，如果后续需要调用，必须新增这部分逻辑
		//设置计价信息
//		if(productPriceDtoCollection.getActiveDto()!=null && StringUtils.isNotEmpty(productPriceDtoCollection.getActiveDto().getCode())){
//			bean.setGuiResultBillCalculateDtos(guiResultBillCalculateDtos);
//		}
		
		//推广活动会对其他费用打折，重新在其他费用面板赋值@ 营销活动需要算-----------------------------------------------------------------------
		//setterOtherFeeByMakActive(productPriceDtoCollection,guiResultBillCalculateDtos,bean);
		 
	
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
			//根据DMANA-4938修改，标准快递和3.60特惠件两种产品的原件签单返回计费统一按照标准快递首重收费
			if(ExpWaybillConstants.ROUND_COUPON_PACKAGE.equals(dto2.getProductCode()) ||
					WaybillConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT.equals(dto2.getProductCode())){
				dto2.setProductCode(ExpWaybillConstants.PACKAGE);
			}			
			guiResultBillCalculateDtos2 = waybillManagerService.queryGuiExpressBillPrice(dto2);
			if(guiResultBillCalculateDtos2!=null && guiResultBillCalculateDtos2.size()>0){
				GuiResultBillCalculateDto qsDto = guiResultBillCalculateDtos2.get(0);
				if(qsDto!=null && qsDto.getCaculateFee()!=null){
					List<ValueAddDto> list = billCaculateService.searchExpressValueAddPriceList(getQueryOtherChargeParam(entity, entity.getReturnBillType()));
					//折扣优惠
					if(CollectionUtils.isNotEmpty(list)){
						for(ValueAddDto addDto:list){
							if(PriceEntityConstants.PRICING_CODE_QS.equals(addDto.getPriceEntityCode())){
								//优惠方案
								List<PriceDiscountDto> disDto =addDto.getDiscountPrograms();
								if(CollectionUtils.isNotEmpty(disDto)){
									PriceDiscountDto dto =disDto.get(0);
									//z折扣费率
									BigDecimal discountRate = dto.getDiscountRate();
									if(discountRate!=null){
										//折扣后费用
										BigDecimal fee =qsDto.getCaculateFee().multiply(discountRate);
										BigDecimal reduceFee =qsDto.getCaculateFee().subtract(fee);
										dto.setReduceFee(reduceFee);
										qsDto.setCaculateFee(fee.setScale(0, BigDecimal.ROUND_HALF_UP));
									}
								}
								setReturnBillDiscount(addDto.getDiscountPrograms(), waybillDiscountVos,entity);
								break;
							}
							
						}
					}
				}
			}
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
			setProductPriceDtoCollection(dto,entity,dtoJH,waybillDiscountVos,waybillChargeDtlEntityList,isAccurateCost);
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
		calculateFee(entity, false, needMinusCoupen,couponFee,couponRankType,isAccurateCost);
		if (WaybillConstants.TEMPORARY_DEBT.equals(entity.getPaidMethod())) {
			isBeBebt(entity);// 判断是否可以开临时欠款
		}
	}		
	
	/**
	 * 
	 * 计算各种费用（公布价运费、装卸费、增值费、优惠、总费用、包装费、送货费）
	 * 清空包装费额外功能
	 */
	public void calculateFee(WaybillEntity waybillEntity, boolean cleanPackageFee, 
			boolean needMinusCoupen,BigDecimal couponFee,String couponRankType,boolean isAccurateCost) {
		
		// 重新赋值是因为添加装卸费需要重新计算价格
		BigDecimal transportFee = waybillEntity.getTransportFee();			
		
		if(transportFee==null){
			transportFee = BigDecimal.ZERO;
		}	
		if(isAccurateCost){
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
	 */
	private BigDecimal calculateExpIncrement(WaybillEntity waybillEntity , boolean cleanPackageFee) {
		BigDecimal incrementFee = BigDecimal.ZERO;// 增值服务费
		BigDecimal deliveryGoodsFee = waybillEntity.getDeliveryGoodsFee();// 送货费
		if(waybillEntity.getProductCode() == null){
			throw new BusinessException("产品类型不允许为空~！");
		}
		if(deliveryGoodsFee==null || 
				ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE.equals(waybillEntity.getProductCode())||
				ExpWaybillConstants.ROUND_COUPON_PACKAGE.equals(waybillEntity.getProductCode())
				){
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
		if(packageFee==null || 
				ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE.equals(waybillEntity.getProductCode())||
				ExpWaybillConstants.ROUND_COUPON_PACKAGE.equals(waybillEntity.getProductCode())){
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
	 */
	private void executeCoupon(WaybillEntity waybillEntity,List<WaybillChargeDtlEntity> waybillChargeDtlEntityList
			) {
		CouponInfoResultDto dto=null;
		// 优惠券是否为空
		CouponInfoDto couponInfoDto = getCouponInfoDto(waybillEntity,waybillChargeDtlEntityList);
		if(couponInfoDto == null){
			waybillEntity.setPromotionsCode(null);
		}else {
			try {
				 dto = crmOrderService.validateCoupon(couponInfoDto);
			} catch (Exception e) {
				throw new WaybillValidateException("调用优惠劵失败！");
			}
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
							waybillEntity.setCouponFee(new BigDecimal(lastAmount));
							//couponFee = new BigDecimal(lastAmount)
						}catch(Exception e){
							waybillEntity.setCouponFee(new BigDecimal(0));
//							couponFee = new BigDecimal(0);
						}
						/**
						 * 设置优惠券返回实体
						 */
						waybillEntity.setCouponInfoDtoParam(couponInfoDto);
//						couponInfoDtoParam = couponInfoDto;
						/**
						 * 设置优惠券冲减类型
						 */
						waybillEntity.setCouponRankType(dto.getDeductibleType());
//						couponRankType = dto.getDeductibleType();
					}
				}else {
					// 优惠金额
					if (dto.getCouponAmount() != null) {
						/**
						 * 设置优惠券费用
						 */
						waybillEntity.setCouponFee(dto.getCouponAmount());
//						couponFee =dto.getCouponAmount();
						/**
						 * 设置优惠券返回实体
						 */
						waybillEntity.setCouponInfoDtoParam(couponInfoDto);
//						couponInfoDtoParam = couponInfoDto;
						/**
						 * 设置优惠券冲减类型
						 */
						waybillEntity.setCouponRankType(dto.getDeductibleType());
//						couponRankType = dto.getDeductibleType();
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
			throw new WaybillValidateException("总费用为空");
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
			throw new WaybillValidateException("收货部门为空或未找到");
			//return null;
		}
		
		if(null == waybillEntity.getProductCode()){
			throw new WaybillValidateException("产品类型为空");
		}
		
		Boolean isPickupCentralized = WaybillConstants.YES.equals(waybillEntity.getPickupCentralized()) ? true : false;
		//获取走货路径
		OrgInfoDto orgInfoDto = waybillFreightRouteService.queryLodeDepartmentInfoOnline(
				isPickupCentralized,waybillEntity.getCreateOrgCode(),waybillEntity.getCustomerPickupOrgCode(),
				waybillEntity.getProductCode());
		if (orgInfoDto == null || orgInfoDto.getFreightRoute() == null) {
			throw new WaybillValidateException("走货线路查询不到");
		}

		// 发货部门-标杆编码
		waybillInfo.setLeaveDept(receiveOrgAdministrative.getUnifiedCode());

		if (waybillEntity.getLastLoadOrgCode() == null) { 
			throw new WaybillValidateException("最终配载部门为空！");
			//return null;
		}
		// 最终配载部门-也就是最后一个自有网点
		String lastLoadOrgCode = waybillEntity.getLastLoadOrgCode();
		
		OrgAdministrativeInfoEntity lastLoadOrgAdministrative = 
				orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(lastLoadOrgCode);
		if (lastLoadOrgAdministrative == null) {
			throw new WaybillValidateException("最终配载部门为空！");
			//return null;
		}
		if (waybillEntity.getLoadOrgCode() == null) {
			throw new WaybillValidateException("始发配载部门为空！");
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
			throw new WaybillValidateException("最终外场编码为空！");
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
	 */
	public void calculateTotalFee(WaybillEntity waybillEntity, boolean needMinusCoupen, BigDecimal couponFee,String couponRankType){
			
		BigDecimal	transportFee=waybillEntity.getTransportFee();
		BigDecimal	incrementFee=waybillEntity.getValueAddFee();
		
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
		 *营销活动与优惠券编码关联
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
			if(waybillEntity.getProductCode()!= null && 
					(WaybillConstants.PACKAGE.equals(waybillEntity.getProductCode())||
							ExpWaybillConstants.ROUND_COUPON_PACKAGE.equals(waybillEntity.getProductCode()))){
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
				 * 营销活动与优惠券编码关联
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
		if (waybillDiscountVos != null) {
			for (WaybillDiscountVo waybillDiscountVo : waybillDiscountVos) {
				if(waybillDiscountVo.getFavorableAmount() != null){
					totalPromotionsFee = totalPromotionsFee.add(new 
							BigDecimal(waybillDiscountVo.getFavorableAmount()).setScale(0,BigDecimal.ROUND_HALF_DOWN));
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
	 * 营销活动与优惠券编码关联
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
	 * 营销活动与优惠券编码关联
	 */
	public void offsetCouponFee(WaybillEntity waybillEntity ,BigDecimal beforeProTranFee, BigDecimal couponFee,
			List<WaybillDiscountVo> waybillDiscountVos,String couponRankType,boolean isAccurateCost) {
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
			resetCalculateFee(waybillEntity,couponFee,couponRankType,isAccurateCost);
		}
	}
	/**
	 * 判断要快递使用优惠券的类型是否符合要求（电子运单的优惠券类型如果无法冲减，则直接将优惠券置空，不会抛出异常）
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
	 */
	public  BigDecimal getDefaultZhxxFee(WaybillEntity waybillEntity){
		BigDecimal zhxx=null;
		List<ValueAddDto> list = billCaculateService.searchExpressValueAddPriceList(getQueryOtherChargeParam(waybillEntity,null));
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
	 */
	private  QueryBillCacilateValueAddDto getQueryOtherChargeParam(WaybillEntity waybillEntity,String type) {
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
		queryDto.setCustomerCode(waybillEntity.getDeliveryCustomerCode());
		if(waybillEntity.getGoodsWeightTotal() == null){
			queryDto.setWeight(BigDecimal.ZERO);// 重量
		}else{
			queryDto.setWeight(waybillEntity.getGoodsWeightTotal());// 重量
		}
		if(waybillEntity.getGoodsVolumeTotal() == null){
			queryDto.setVolume(BigDecimal.ZERO);// 体积
		}else{
			queryDto.setVolume(waybillEntity.getGoodsVolumeTotal());// 体积
		}
		queryDto.setOriginnalCost(BigDecimal.ZERO);// 原始费用
		queryDto.setFlightShift(null);// 航班号
//		queryDto.setLongOrShort(waybillEntity.getLongOrShort());// 长途 还是短途
		queryDto.setSubType(type);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		if (null!=waybillEntity.getReturnBillType() && WaybillConstants.NOT_RETURN_BILL.equals(waybillEntity.getReturnBillType())) {// 判断有无返单类型
			queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_QT);// 计价条目CODE
		} else {
			queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_QS);// 计价条目CODE
		}
		queryDto.setPricingEntryName(null);// 计价名称
		return queryDto;
	}
	
	/**
	 * 
	 * 重新计算各种费用（公布价运费、装卸费、增值费、优惠、总费用、包装费、送货费）
	 */
	public  void resetCalculateFee(WaybillEntity waybillEntity,BigDecimal couponFee,String couponRankType,boolean isAccurateCost) {
		
		// 重新赋值是因为添加装卸费需要重新计算价格
		BigDecimal transportFee = waybillEntity.getTransportFee();			
		
		if(transportFee==null){
			transportFee = BigDecimal.ZERO;
		}	
		if(isAccurateCost){
			// 公布价运费
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
	 */
	private void setProductPriceDtoCollection(GuiResultBillCalculateDto dto, WaybillEntity waybillEntity,GuiResultBillCalculateDto dtoJH,
			List<WaybillDiscountVo>  waybillDiscountVos,List<WaybillChargeDtlEntity> waybillChargeDtlEntityList,boolean isAccurateCost){
		if (dto == null) {
			throw new WaybillValidateException("没有找到对应的价格");
		}
		if (dto.getCaculateFee() == null) {
			throw new WaybillValidateException("价格不存在,公布价运费为0");
		}
		BigDecimal transportFee = dto.getCaculateFee();
		if(isAccurateCost){
			transportFee =transportFee.setScale(2, BigDecimal.ROUND_HALF_UP);// 四舍五入
		}else{
			transportFee =transportFee.setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
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
				if (new BigDecimal(0).compareTo(entity.getInsuranceAmount()) == 0) {
					if(PriceEntityConstants.PRICING_CODE_BF.equals(dto.getPriceEntryCode())){
						dto.setReduceFee(new BigDecimal(0));
					}
				}
				addDiscount(dto, waybillDiscountVos);
			}
		}
	}
	
	/**
	 * 清空优惠券费用
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
		//calculateServiceFee(waybillEntity, insuranceCollection, waybillDiscountVos,waybillChargeDtlEntityList);

		//添加其他费用
		setOtherChargeDataCollection(waybillEntity, guiResultBillCalculateDtos, waybillDiscountVos,waybillChargeDtlEntityList);
		//添加返单费用
		setReturnBillCollection(waybillEntity, guiResultBillCalculateDtos, waybillDiscountVos,waybillChargeDtlEntityList);
	} 
	
	/**
	 * 
	 * 设置保价费
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
					throw new WaybillValidateException("未查询到对应的保险手续费，请添加");
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
						/**
						 * 新添加gyk
						 * 若单票保价手续费字段不为空，保价金额大于0时，保价手续费均按CRM中的单票保价手续费的值计算
						 */
						PreferentialInfoDto preferentialInfoDto = queryPreferentialInfo(waybillEntity);
						if(preferentialInfoDto!=null){
							if(preferentialInfoDto.getSinTicketCollCharge()!=null){
								dto.setCaculateFee(preferentialInfoDto.getSinTicketCollCharge());
								feeRate=preferentialInfoDto.getSinTicketCollCharge().divide(waybillEntity.getInsuranceAmount());
							}
						}else{
							if(null!=dto.getMinFee()&&null!=dto.getCaculateFee()&& dto.getMinFee().compareTo(dto.getCaculateFee())>0){
								//最低代收手续费大于实际计算手续费时，反推实际代收货款手续费
								feeRate=dto.getMinFee().divide(waybillEntity.getInsuranceAmount());
							}
						}
						feeRate = feeRate.multiply(permillage);
						// 保险费率
						waybillEntity.setInsuranceRate(formatNumberTwoDecimal(feeRate));
						BigDecimal insuranceFee = dto.getCaculateFee().setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
						if (new BigDecimal(0).compareTo(waybillEntity.getInsuranceAmount()) == 0) {
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
				/**
				 * 新添加gyk
				 * 若单票代收手续费字段不为空，代收金额大于0时，代收手续费均按CRM中的单票代收手续费的值计算。
				 */
				PreferentialInfoDto preferentialInfoDto = queryPreferentialInfo(waybillEntity);
				if(preferentialInfoDto!=null){
					if(preferentialInfoDto.getSinTicketCollCharge()!=null){
						dto.setCaculateFee(preferentialInfoDto.getSinTicketCollCharge());
						feeRate=preferentialInfoDto.getSinTicketCollCharge().divide(codAmount);
					}
				}else{
					if(null!=dto.getMinFee()&&null!=dto.getCaculateFee()&& dto.getMinFee().compareTo(dto.getCaculateFee())>0){
						//最低代收手续费大于实际计算手续费时，反推实际代收货款手续费
						feeRate=dto.getMinFee().divide(codAmount);
					}
				}
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
	 * 设置其他费用折扣
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
	 * 将查询出的其他费用设置到OtherChargeVo list中
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
	 * 是否可开临时欠款
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
				Math.abs(getCalculateVolumeTotal(entity).doubleValue() - entity.getGoodsVolumeTotal().doubleValue() ) < KEY_VOLUME  ){
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
			List<WaybillDiscountVo>  waybillDiscountVos, List<WaybillChargeDtlEntity> waybillChargeDtlEntityList,boolean isAccurateCost) {
		
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
				throw new WaybillValidateException("没有找到对应的价格");
			}

			GuiResultBillCalculateDto dto = getGuiResultBillCalculateDto(guiResultBillCalculateDtos, PriceEntityConstants.PRICING_CODE_FRT, null);
			GuiResultBillCalculateDto dtoJH= getGuiResultBillCalculateDto(guiResultBillCalculateDtos, PriceEntityConstants.PRICING_CODE_JH, null);
			
			//设置公布价运费的费用
			setProductPriceDtoCollection(dto,waybillEntity,dtoJH,waybillDiscountVos,waybillChargeDtlEntityList,isAccurateCost);
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
				throw new WaybillValidateException("查询不到时效");
			}
		} else {
			effectiveDto = waybillManagerService.searchPreDeliveryTime(entity.getCreateOrgCode(), 
					entity.getLastLoadOrgCode(), entity.getProductCode(), entity.getPreDepartureTime(), new Date());
			if (effectiveDto != null) {
				//bean.setLongOrShort(effectiveDto.getLongOrShort());
				//return effectiveDto.getDeliveryDate();
				return effectiveDto;
			} else {
				throw new WaybillValidateException("查询不到时效");
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
		BigDecimal calculateVolumeTotal = new BigDecimal(0);
		
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
					throw new WaybillValidateException("尺寸计算的结果超过最大值");
				}else if(BigDecimal.ZERO.compareTo(bigDecimal) > 0){
					throw new WaybillValidateException("体积不能为负值");
				}else{
					calculateVolumeTotal = bigDecimal;
				}
			}
		}
		return calculateVolumeTotal.setScale(NumberConstants.NUMBER_6, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
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
			
			if((new BigDecimal(KEY_VOLUME).equals(waybillEntity.getGoodsVolumeTotal())) && 
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

	/**
	 * 根据部门编码获取简称
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
	
	/**
	 * 判断数据是否为空，如果为空则返回零
	 * 
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
	 */
	@Override
	public WaybillExpressEntity queryWaybillByOriginalWaybillNo(String waybillNo,String returnType){
		List<WaybillExpressEntity> entitys = waybillExpressDao.queryWaybillByOriginalWaybillNo(waybillNo);
		if(CollectionUtils.isNotEmpty(entitys)){
		   for(WaybillExpressEntity entity:entitys){
			   ActualFreightEntity actualFreightEntity	= actualFreightService.queryByWaybillNo(entity.getWaybillNo());
			   String status = actualFreightEntity.getStatus();
			   if(WaybillConstants.OBSOLETE.equals(status)
					   ||
				  WaybillConstants.ABORTED.equals(status)){
				   continue;
			   }
			   if(returnType.equals(entity.getReturnType())){
					return entity;
			   }  
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
	 * 返货开单
	 */
	@Override
	public void addWaybillExpressEntityReturn(
			WaybillExpressEntity waybillExpress) {
		waybillExpressDao.addWaybillExpressEntityReturn(waybillExpress);
		
	}
	/**
	 * 校验是否可以享有市场营销活动
	 */
	private void validateActiveDiscount(WaybillEntity entity,boolean isValBillAmount,GuiQueryBillCalculateDto productPriceDtoCollection) {
		MarkActivitiesQueryConditionDto result=null;
		WaybillDisDtlEntity waybillDisDtlEntity = waybillDisDtlService.queryActiveInfoByNoAndType(entity.getWaybillNo());
		//判断市场营销活动是否为空
		if(waybillDisDtlEntity==null || waybillDisDtlEntity.getActiveCode()==null || StringUtils.isEmpty(waybillDisDtlEntity.getActiveName())){
			productPriceDtoCollection.setCalActiveDiscount(false);
		}else{
			MarkActivitiesQueryConditionDto activeDto=new MarkActivitiesQueryConditionDto();
			//开单品名
			activeDto.setGoodsName(entity.getGoodsName());
			if(StringUtils.isNotEmpty(entity.getDeliveryCustomerCode())){
				CustomerDto customer=customerService.queryCustInfoByCode(entity.getDeliveryCustomerCode());
				if(customer!=null){
					//一级行业
					activeDto.setFirstTrade(customer.getOneLevelIndustry());
					//二级行业
					activeDto.setSecondeTrade(customer.getTwoLevelIndustry());
				}
			}
			//订单来源
			activeDto.setOrderResource(entity.getOrderChannel());
			//产品类型
			activeDto.setProductType(entity.getProductCode());
			//开展部门
			activeDto.setDevelopDeptCode(entity.getReceiveOrgCode());
			//出发外场
			activeDto.setStartOutFieldCode(entity.getLoadOrgCode());
			//到达外场
			activeDto.setArriveOutFieldCode(entity.getLastLoadOrgCode());	
			//发货部门
			activeDto.setLeaveAreaCode(entity.getReceiveOrgCode());
			//到达部门
			activeDto.setArriveAreaCode(entity.getCustomerPickupOrgCode());
			//开单时间
			activeDto.setBilllingTime(new Date());
			if(isValBillAmount){
				//开单金额
				activeDto.setBilllingAmount(entity.getTotalFee());
			}			
			//开单重量
			activeDto.setBilllingWeight(entity.getGoodsWeightTotal());
			//开单体积
			activeDto.setBilllingVolumn(entity.getGoodsVolumeTotal());
			//活动编码
			activeDto.setCode(waybillDisDtlEntity.getActiveCode());
			//查询活动折扣信息  此处调综合模块自定义的方法（应业务需要去掉一切关于市场营销活动相关的校验）
			try {
				activeDto.setIsExpressFous("Y");
			     result=markActivitiesService.queryMarkActivitiesInfoByCondition(activeDto);
			} catch (Exception e) {
				throw new WaybillValidateException("调用市场营销活动失败");
			}
			if(result!=null && result.getOptionList()!=null && result.getOptionList().size()>0){
				//计算市场营销活动折扣信息
				productPriceDtoCollection.setCalActiveDiscount(true);
				//将营销活动设置到DTO中
				productPriceDtoCollection.setActiveDto(result);
				productPriceDtoCollection.getActiveDto().setIsExpressFous("Y");
			}else{
				productPriceDtoCollection.setCalActiveDiscount(false);
				//您选择的市场营销活动不符合条件！
				throw new WaybillValidateException("您选择的市场营销活动不符合条件！");
			}
		}	
		
	}
	/**
	 * 封装营销活动参数信息
	 */
	private  void settterActiveParamInfo(GuiQueryBillCalculateDto productPriceDtoCollection,WaybillEntity entity){
		if(productPriceDtoCollection.getActiveDto()!=null){
			productPriceDtoCollection.setActiveCode(productPriceDtoCollection.getActiveDto().getCode());
		}		
		productPriceDtoCollection.setGoodsName(entity.getGoodsName());
		productPriceDtoCollection.setDeliveryCustomerCode(entity.getDeliveryCustomerCode());
		productPriceDtoCollection.setOrderChannel(entity.getOrderChannel());
		productPriceDtoCollection.setReceiveOrgCode(entity.getReceiveOrgCode());
		productPriceDtoCollection.setLoadOrgCode(entity.getLoadOrgCode());
		productPriceDtoCollection.setLastOutLoadOrgCode(entity.getLastLoadOrgCode());
		productPriceDtoCollection.setCustomerPickupOrgCode(entity.getCustomerPickupOrgCode());
		productPriceDtoCollection.setBillTime(entity.getBillTime());
		productPriceDtoCollection.setTransportFee(entity.getTransportFee());
		productPriceDtoCollection.setGoodsWeightTotal(entity.getGoodsWeightTotal());
		productPriceDtoCollection.setGoodsVolumeTotal(entity.getGoodsVolumeTotal());
	}
	/**
	 * gyk
	 * 调用crm接口查询客户合同信息
	 * 同时验证客户是否是部门绑定客户
	 * @param bean
	 * @return
	 */
	private PreferentialInfoDto queryPreferentialInfo(WaybillEntity waybillEntity){
		String productCodeTemp = ProductEntityConstants.PRICING_PRODUCT_C2_C20005;
		PreferentialInfoDto preferentialInfoDto = preferentialService.queryPreferentialInfo(waybillEntity.getDeliveryCustomerCode(),null,productCodeTemp);
		boolean isDiscount = false;
		if(preferentialInfoDto!=null){
			String unicode  = orgAdministrativeInfoService.queryUnifiedCodeByCode(waybillEntity.getReceiveOrgCode());
			List<String> list = new ArrayList<String>();
			if(unicode!=null){
				list.add(unicode);
			}
			//验证客户是否是部门绑定客户
			List<BargainAppOrgEntity> bargainAppOrgEntities = bargainAppOrgService
					.queryAppOrgByBargainCrmId(preferentialInfoDto.getCusBargainId(),list);
			if (CollectionUtils.isNotEmpty(bargainAppOrgEntities)) {
				for (int i = 0; i < bargainAppOrgEntities.size(); i++) {
					BargainAppOrgEntity bargainAppOrgEntity = bargainAppOrgEntities
							.get(i);
					String unifiedCode = bargainAppOrgEntity.getUnifiedCode();
					OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService
							.queryOrgAdministrativeInfoByUnifiedCodeClean(unifiedCode);
					if (orgAdministrativeInfoEntity == null) {
						continue;
					}
					String orgCode = orgAdministrativeInfoEntity.getCode();
					if (StringUtils.equals(waybillEntity.getReceiveOrgCode(), orgCode)) {
						isDiscount = true;
					}
				}
			}
		}
		if(isDiscount){
			return preferentialInfoDto;
		}else{
			return null;
		}
	}
	 /**
     * pgy
     * 返回单折扣
     */
	private void setReturnBillDiscount(
			List<PriceDiscountDto> discountPrograms,List<WaybillDiscountVo> waybillDiscountVos,
			WaybillEntity entity) {
		if (discountPrograms != null && !discountPrograms.isEmpty()) {
			if (waybillDiscountVos == null) {
				waybillDiscountVos = new ArrayList<WaybillDiscountVo>();
			}
			for (PriceDiscountDto dto : discountPrograms) {
				add(dto, waybillDiscountVos,entity);
			}
		}
		
	}
	private void add(PriceDiscountDto dto, List<WaybillDiscountVo> data,
			WaybillEntity entity) {
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
		vo.setFavorableTypeName(dto.getTypeName());
		// 优惠折扣类型
		vo.setFavorableTypeCode(dto.getType());
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
		// 优惠折扣金额
		if (dto.getReduceFee() != null) {
			//优惠金额
			vo.setFavorableAmount(dto.getReduceFee().toString());			
		} else {
			vo.setFavorableDiscount("无折扣金额，可能是数据有异常，请联系管理员");
		}
		data.add(vo);
	}
    private void setReturnBillCollection(WaybillEntity waybillEntity, List<GuiResultBillCalculateDto> dtos,
			List<WaybillDiscountVo>  waybillDiscountVos, List<WaybillChargeDtlEntity> waybillChargeDtlEntityList) {
		if (!WaybillConstants.NOT_RETURN_BILL.equals(waybillEntity.getReturnBillType())&&
				!WaybillConstants.RETURNBILLTYPE_ORIGINAL.equals(waybillEntity.getReturnBillType())) {
			String type = "";
			// 到达联传真要转成传真类型
			if (WaybillConstants.RETURNBILLTYPE_ARRIVE.equals(waybillEntity.getReturnBillType())) {
				type = WaybillConstants.RETURNBILLTYPE_FAX;
			} else {
				type = waybillEntity.getReturnBillType();
			}
			List<ValueAddDto> list = billCaculateService.searchExpressValueAddPriceList(getQueryOtherChargeParam(waybillEntity, type));			
			if (CollectionUtils.isNotEmpty(list)) {
				for (ValueAddDto valueAddDto : list) {
					if(WaybillConstants.RETURNBILLTYPE_FAX.equals(valueAddDto.getSubType()) && valueAddDto.getCaculateFee().toString().compareTo(ZERO)>0){
						if(null!=waybillEntity.getValueAddFee()){
							   waybillEntity.setValueAddFee(waybillEntity.getValueAddFee().add(valueAddDto.getCaculateFee()));
						}else{
							   waybillEntity.setValueAddFee(new BigDecimal("0").add(valueAddDto.getCaculateFee()));	
							}
						if(null!=waybillEntity.getOtherFee()){
							   waybillEntity.setOtherFee(waybillEntity.getOtherFee().add(valueAddDto.getCaculateFee()));
					       }else{
							   waybillEntity.setOtherFee(new BigDecimal("0").add(valueAddDto.getCaculateFee()));	
						}
						WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
						chargeEntity.setPricingEntryCode(valueAddDto.getPriceEntityCode());
						chargeEntity.setAmount(valueAddDto.getCaculateFee());
						chargeEntity.setWaybillNo(waybillEntity.getWaybillNo());
						chargeEntity.setPricingCriDetailId(valueAddDto.getId());
						chargeEntity.setActive(FossConstants.INACTIVE);
						chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
						waybillChargeDtlEntityList.add(chargeEntity);
					}
				}
			} 
			//折扣优惠
			if(CollectionUtils.isNotEmpty(list)){
				for(ValueAddDto addDto:list){
					if(PriceEntityConstants.PRICING_CODE_QS.equals(addDto.getPriceEntityCode())){
						setReturnBillDiscount(addDto.getDiscountPrograms(), waybillDiscountVos, waybillEntity);
					}
					
				}
			}
		}
    }
}