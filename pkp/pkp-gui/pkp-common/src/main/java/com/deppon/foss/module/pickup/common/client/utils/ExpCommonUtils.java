/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BargainAppOrgEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PreferentialInfoDto;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.base.dict.server.service.impl.ConfigurationParamsService;
import com.deppon.foss.module.pickup.common.client.service.BaseDataServiceFactory;
import com.deppon.foss.module.pickup.common.client.service.DownLoadDataServiceFactory;
import com.deppon.foss.module.pickup.common.client.service.IAddressService;
import com.deppon.foss.module.pickup.common.client.service.IBaseDataService;
import com.deppon.foss.module.pickup.common.client.service.ICustomerService;
import com.deppon.foss.module.pickup.common.client.service.IDataDictionaryValueService;
import com.deppon.foss.module.pickup.common.client.service.IOrgInfoService;
import com.deppon.foss.module.pickup.common.client.service.IOuterBranchService;
import com.deppon.foss.module.pickup.common.client.service.IUserService;
import com.deppon.foss.module.pickup.common.client.service.impl.AddressService;
import com.deppon.foss.module.pickup.common.client.service.impl.BaseDataService;
import com.deppon.foss.module.pickup.common.client.service.impl.CustomerService;
import com.deppon.foss.module.pickup.common.client.service.impl.DataDictionaryValueService;
import com.deppon.foss.module.pickup.common.client.service.impl.OrgInfoService;
import com.deppon.foss.module.pickup.common.client.service.impl.OuterBranchService;
import com.deppon.foss.module.pickup.common.client.service.impl.SalesDepartmentService;
import com.deppon.foss.module.pickup.common.client.service.impl.UserService;
import com.deppon.foss.module.pickup.common.client.vo.DeliverChargeEntity;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.common.client.vo.QueryMemberDialogVo;
import com.deppon.foss.module.pickup.common.client.vo.ValidateVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillDiscountVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PriceDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.waybill.shared.define.GisConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillCHDtlPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmReceiveMethodEnum;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmTransportTypeEnum;
import com.deppon.foss.module.pickup.waybill.shared.dto.SalesDepartmentCityDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Injector;

/**
 * @author ibm-foss-sxw
 *
 */
public class ExpCommonUtils {

	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(CommonUtils.class);

	private static final int NUM_300000 = 300000;

	private static final int THREE = 3;

	/**
	 * 运单基础数据服务
	 */
	private static IBaseDataService baseDataService = GuiceContextFactroy.getInjector().getInstance(BaseDataService.class);
	
	
	/**
	 * 把三级产品类型转换成始发和到达线路的运输类型(汽运，空运) : 1、精准卡航、 精准城运 精准汽运（长途）、 精准汽运（短途）、 汽运偏线 =>
	 * 的汽运 2、精准空运 => 的空运
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-27 下午7:23:24
	 */
	public static String convertProductCodeToTransType(String productCode) {
		// 根据三级产品判断是否是汽运
		if (StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT, productCode)
				|| StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT, productCode)
				|| StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT, productCode)
				|| StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT, productCode)
				|| StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE, productCode)
				|| StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE, productCode)) {
			return DictionaryValueConstants.BSE_LINE_TRANSTYPE_QIYUN;
		}
		// 根据三级产品判断是否是空运
		else if (StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT, productCode)) {
			return DictionaryValueConstants.BSE_LINE_TRANSTYPE_KONGYUN;
		}else{
			return StringUtil.defaultIfNull(productCode);
		}
	}

	/**
	 * 把三级产品类型转换成始发和到达线路的运输类型(汽运，空运) : 1、精准卡航、 精准城运 精准汽运（长途）、 精准汽运（短途）、 汽运偏线 =>
	 * GIS的汽运 2、精准空运 => GIS的空运
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-27 下午5:11:35
	 */
	public static String covertProductToGisType(String productCode) {
		// 根据三级产品判断是否是汽运
		if (StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT, productCode)
				|| StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT, productCode)
				|| StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT, productCode)
				|| StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT, productCode)
				|| StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE, productCode)) {
			return GisConstants.GIS_TRANS_HIGHWAYS;
		}
		// 根据三级产品判断是否是空运
		if (StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT, productCode)) {
			return GisConstants.GIS_TRANS_AIR;
		}

		return null;
	}

	/**
	 * 判断是否为精准汽运
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-6-24 下午4:38:26
	 */
	public static boolean isTransFreight(String productCode) {
		String code = StringUtil.defaultIfNull(productCode);
		// 根据三级产品判断是否是精准汽运
		if (StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT, code)
				|| StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT, code)
				|| StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT, code)
				|| StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT, code)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 1、自提、空运自提、空运免费自提、机场自提 ==> GIS自提 2、免费派送、送货进仓、免费派送（上楼）==> GIS送货
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-27 下午5:41:55
	 */
	public static String covertReceiveMethod(String method) {
		String rm = StringUtil.defaultIfNull(method);
		// 根据提货方式判断是不是自提
		if (rm.equals(WaybillConstants.SELF_PICKUP) || rm.equals(WaybillConstants.INNER_PICKUP) || rm.equals(WaybillConstants.AIR_SELF_PICKUP)
				|| rm.equals(WaybillConstants.AIR_PICKUP_FREE) || rm.equals(WaybillConstants.AIRPORT_PICKUP)) {
			return GisConstants.GIS_MATCH_PICKUP;
		}

		// 根据提货方式判断是不是送货
		if (rm.equals(WaybillConstants.DELIVER_FREE) || rm.equals(WaybillConstants.DELIVER_NOUP) || rm.equals(WaybillConstants.DELIVER_STORAGE)
				|| rm.equals(WaybillConstants.DELIVER_UP) || rm.equals(WaybillConstants.DELIVER_FREE_AIR) || rm.equals(WaybillConstants.DELIVER_NOUP_AIR)
				|| rm.equals(WaybillConstants.DELIVER_UP_AIR) || rm.equals(WaybillConstants.DELIVER_INGA_AIR)) {
			return GisConstants.GIS_MATCH_DELIVER;
		}

		return null;
	}

	/**
	 * 根据提货方式判断：是否送货上门
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-26 下午3:32:48
	 */
	public static boolean verdictPickUpDoor(String pickupType) {
		String type = StringUtil.defaultIfNull(pickupType);
		if (WaybillConstants.DELIVER_FREE.equals(type) || WaybillConstants.DELIVER_STORAGE.equals(type) || WaybillConstants.DELIVER_UP.equals(type)
				|| WaybillConstants.DELIVER_NOUP.equals(type) || WaybillConstants.DELIVER_FREE_AIR.equals(type) || WaybillConstants.DELIVER_NOUP_AIR.equals(type)
				|| WaybillConstants.DELIVER_UP_AIR.equals(type) || WaybillConstants.DELIVER_INGA_AIR.equals(type)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据提货方式判断：是否自提
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-26 下午3:52:17
	 */
	public static boolean verdictPickUpSelf(String pickupType) {
		String type = StringUtil.defaultIfNull(pickupType);
		if (WaybillConstants.SELF_PICKUP.equals(type) || WaybillConstants.INNER_PICKUP.equals(type) || WaybillConstants.AIR_PICKUP_FREE.equals(type)
				|| WaybillConstants.AIRPORT_PICKUP.equals(type) || WaybillConstants.AIR_SELF_PICKUP.equals(type)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据常量值获得常量名
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-28 下午3:21:50
	 */
	public static String getConstantsName(String value) {
		if (WaybillConstants.CRM_ORDER_WAIT_ACCEPT.equals(value)) {
			return i18n.get("foss.gui.common.commonUtils.constantsName.waitAccept");
		} else if (WaybillConstants.CRM_ORDER_ACCEPT.equals(value)) {
			return i18n.get("foss.gui.common.commonUtils.constantsName.accepted");
		} else if (WaybillConstants.CRM_ORDER_WAYBILL.equals(value)) {
			return i18n.get("foss.gui.common.commonUtils.constantsName.waybillOrdered");
		} else if (WaybillConstants.CRM_ORDER_CHANNEL_ONLINE.equals(value)) {
			return i18n.get("foss.gui.common.commonUtils.constantsName.channelOnline");
		} else if (WaybillConstants.CRM_ORDER_PAYMENT_ONLINE.equals(value)) {
			return i18n.get("foss.gui.common.commonUtils.constantsName.paymentOnline");
		} else {
			return value;
		}
	}

	/**
	 * 根据产品来判断是查询自有网点还是代理网点
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-1 下午3:33:17
	 */
	public static boolean isAgentDept(String productCode) {
		if (!ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(productCode) && !ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productCode)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * 数据转换 将Y或N转换成中文是和否
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-2-19 上午09:14:06
	 * @param data
	 * @return
	 */
	public static String dataTransform(String data) {
		if (FossConstants.YES.equals(data)) {
			return i18n.get("foss.gui.common.waybillEditUI.common.yes");
		} else {
			return i18n.get("foss.gui.common.waybillEditUI.common.no");
		}
	}

	/**
	 * 将CustomerQueryConditionDto对象转换成QueryMemberDialogVo对象
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-16 上午8:40:32
	 */
	public static List<QueryMemberDialogVo> convertToMemberVo(List<CustomerQueryConditionDto> contactList) {
		if (contactList == null || contactList.size() == 0) {
			return null;
		}

		List<QueryMemberDialogVo> memberList = new ArrayList<QueryMemberDialogVo>();
		for (CustomerQueryConditionDto contact : contactList) {
			QueryMemberDialogVo dialogVo = new QueryMemberDialogVo();
			// 客户ID
			dialogVo.setCustId(StringUtil.defaultIfNull(contact.getCustId()));
			// 客户编码
			dialogVo.setCustomerCode(StringUtil.defaultIfNull(contact.getCustCode()));
			// 大客户标记
			//dialogVo.setIsBigCustomer(StringUtil.defaultIfNull(contact.getIsLargeCustomers()));
			/**getIsBigCustomer
			 * START
			 * @author wutao 200945
			 * 添加快递大客户
			 * DMANA-2815:关于在FOSS开单界面、查询界面、补单界面添加快递大客户标识的IT需求
			 */
			dialogVo.setIsExpressBigCustomer(StringUtil.defaultIfNull(contact.getIsExpressBigCust()));
			//END
			
			// 客户名称
			dialogVo.setCustomerName(StringUtil.defaultIfNull(contact.getCustName()));
			// 联系人
			dialogVo.setLinkman(StringUtil.defaultIfNull(contact.getContactName()));
			// 联系人ID
			dialogVo.setConsignorId(StringUtil.defaultIfNull(contact.getLinkmanId()));
			// 联系人编码
			dialogVo.setConsignorCode(StringUtil.defaultIfNull(contact.getLinkmanCode()));
			// 身份证号码
			dialogVo.setIdentityCard(StringUtil.defaultIfNull(contact.getIdCard()));
			// 电话
			dialogVo.setPhone(StringUtil.defaultIfNull(contact.getContactPhone()));
			// 手机
			dialogVo.setMobilePhone(StringUtil.defaultIfNull(contact.getMobilePhone()));
			// 地址
			dialogVo.setAddress(StringUtil.defaultIfNull(contact.getAddress()));
			// 信用额度
			dialogVo.setCreditAmount(contact.getCreditAmount() == null ? BigDecimal.valueOf(0) : contact.getCreditAmount());
			// 结算方式
			dialogVo.setChargeMode(DictionaryValueConstants.CLEARING_TYPE_MONTH.equals(contact.getExPayWay()) ? true : false);
			// 合同编号
			dialogVo.setAuditNo(contact.getBargainCode());
			// 生效日期
			dialogVo.setValidTime(contact.getBeginTime());
			// 失效日期
			dialogVo.setInvalidTime(contact.getEndTime());
			// 　客户类型
			dialogVo.setCustomerType(contact.getCustomerType());
			// 区域编码
			dialogVo.setCountyCode(StringUtil.defaultIfNull(contact.getCountyCode()));
			// 城市编码
			dialogVo.setCityCode(StringUtil.defaultIfNull(contact.getCityCode()));
			// 省份编码
			dialogVo.setProvinceCode(StringUtil.defaultIfNull(contact.getProvinceCode()));
			// 接送货地址ID
			dialogVo.setAddressId(StringUtil.defaultIfNull(contact.getAddressId()));
			// 优惠类型
			dialogVo.setPreferentialType(StringUtil.defaultIfNull(contact.getExPreferentialType()));
			// 所属部门
			dialogVo.setDeptName(StringUtil.defaultIfNull(contact.getDeptName()));
			//liyongfei-DMANA-2352 2014-09-18 是否精确代收
			dialogVo.setAccurateCollection(contact.getAccurateCollection());
			//电商尊享客户
			dialogVo.setIsElectricityToEnjoy(contact.getIfElecEnjoy());
			
			/**
			 * 将查询的特安上限数据存入dialogVo中的特安客户保价上限中
			 * @author:218371-foss-zhaoyanjun
			 * @date:2014-11-19下午19:32
			 */
			if(contact.getTeanLimit()!=null){
				dialogVo.setVipInsuranceAmount(new BigDecimal(contact.getTeanLimit()));
			}
			//客户接送货地址备注信息
			dialogVo.setCustAddrRemark(StringUtil.defaultIfNull(contact.getCustAddrRemark()));
			//发票标记
			if(WaybillConstants.INVOICE_01.equals(contact.getInvoiceType())){
				dialogVo.setInvoice(WaybillConstants.INVOICE_01);
			}else{
				dialogVo.setInvoice(WaybillConstants.INVOICE_02);
			}
			/**
			 * wutao -- 设置
			 */
			CusBargainEntity cusBargainEntity = queryCusBargainByCustCode(contact);
			if(null==cusBargainEntity){
				dialogVo.setCentralizedSettlement("");
				dialogVo.setContractOrgCode("");
				dialogVo.setContractOrgName("");
				dialogVo.setReminderOrgCode("");
			}else{
				dialogVo.setCentralizedSettlement(cusBargainEntity.getAsyntakegoodsCode());
				dialogVo.setContractOrgCode(cusBargainEntity.getUnifiedCode());
				dialogVo.setContractOrgName(queryContractOrgName(cusBargainEntity.getUnifiedCode()));
				dialogVo.setReminderOrgCode(cusBargainEntity.getHastenfunddeptCode());
			}
			memberList.add(dialogVo);
		}
		return memberList;
	}

	/**
	 * 业务逻辑：
	 * 因为所需要【是否统一结算】【合同部门标杆编码】【催款部门编码】
	 */
	private static CusBargainEntity queryCusBargainByCustCode(CustomerQueryConditionDto contact){
		Injector injector = GuiceContextFactroy.getInjector();
		ICustomerService customerService = injector.getInstance(CustomerService.class);
		CusBargainEntity cusBargainEntity = customerService.queryCusBargainByCustCode(contact.getCustCode());
		if(cusBargainEntity ==null){
			return null;
		}else{
			return cusBargainEntity;
		}
	}
	/**
	 * 获取部门编码
	 * @param code
	 * @return
	 */
	public static String  queryContractOrgName(String code){
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = baseDataService.queryOrgAdministrativeInfoEntityByUnifiedCode(code);
		return (orgAdministrativeInfoEntity!=null)? orgAdministrativeInfoEntity.getName(): "";
	}
	/**
	 * 
	 * 比较折扣优惠表格中是否已经存在相同的项
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-27 下午05:57:52
	 * @return
	 */
	public static void add(PriceDiscountDto dto, List<WaybillDiscountVo> data, WaybillPanelVo bean) {

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

		if (dto.getDiscountRate() != null) {
			// 优惠折扣率
			vo.setFavorableDiscount(dto.getDiscountRate().toString());
		} else {
			// 优惠折扣率
			vo.setFavorableDiscount(i18n.get("foss.gui.creating.calculateAction.msgBox.nullFavorableDiscount"));
		}
		// 优惠折扣金额
		if (dto.getReduceFee() != null) {
			// 优惠金额
			vo.setFavorableAmount(dto.getReduceFee().toString());
		} else {
			vo.setFavorableAmount(i18n.get("foss.gui.creating.calculateAction.msgBox.nullFavorableAmount"));
		}

		data.add(vo);

	}

	/**
	 * 根据运单状态判断是否为已开单
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-16 下午12:50:11
	 */
	public static boolean isHandleActive(String type) {
		// 是否为运单已开单
		if (WaybillConstants.WAYBILL_STATUS_PC_ACTIVE.equals(type) || WaybillConstants.WAYBILL_STATUS_PENDING_ACTIVE.equals(type)
				|| WaybillConstants.WAYBILL_STATUS_RFC_ACTIVE.equals(type) || WaybillConstants.WAYBILL_STATUS_RFC_PENDING.equals(type)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * 获取省份编码
	 * 
	 * @param orgCode
	 * @return
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-27 下午4:36:48
	 */
	public static String getReceiveOrgProvCode(String orgCode) {
		Injector injector = GuiceContextFactroy.getInjector();
		IOrgInfoService orgInfoService = injector.getInstance(OrgInfoService.class);
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgInfoService.queryByCode(orgCode);
		if (null != orgAdministrativeInfoEntity) {
			return orgAdministrativeInfoEntity.getProvCode();
		} else {
			return "";
		}

	}

	/**
	 * 若为null，则设置默认值
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-2 下午4:34:48
	 */
	public static BigDecimal defaultIfNull(BigDecimal num) {
		// 若对象为空，则返回0值
		if (null == num) {
			// 返回0值
			return BigDecimal.valueOf(0);
		}
		/**
		 * 返回原值 不能四舍五入，否则体积为0.01的，在导入或补录时就变成0了
		 */
		// return num.setScale(2, BigDecimal.ROUND_HALF_UP);
		return num;
	}

	/**
	 * 将空字符串转换为数字
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-2 下午4:34:48
	 */
	public static int strToNum(String num) {
		// 若对象为空，则返回0值
		if (null == num || "".equals(num.trim())) {
			return Integer.parseInt("0");
		}
		return Integer.parseInt(num);
	}

	/**
	 * 若为null，则设置默认值
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-2 下午4:34:48
	 */
	public static Integer defaultIfNull(Integer num) {
		// 若对象为空，则返回0值
		if (null == num) {
			// 返回0值
			return Integer.valueOf(0);
		}
		return num;
	}

	/**
	 * 若为null，则设置默认值
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-2 下午4:34:48
	 */
	public static Long defaultIfNull(Long num) {
		// 若对象为空，则返回0值
		if (null == num) {
			// 返回0值
			return Long.valueOf(0);
		}
		return num;
	}

	/**
	 * 若为null，则设置默认值
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-2 下午4:34:48
	 */
	public static Double defaultIfNull(Double num) {
		// 若对象为空，则返回0值
		if (null == num) {
			// 返回0值
			return Double.valueOf(0);
		}
		return num;
	}

	/**
	 * 若为null，则设置默认值
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-2 下午4:34:48
	 */
	public static String defaultIfNull(String str) {
		// 若对象为空，则返回空字符串
		if (null == str) {
			return "";
		} else {
			return str;
		}
	}

	/**
	 * 若为null，则设置默认值
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-2 下午4:34:48
	 */
	public static Boolean defaultIfNull(Boolean num) {
		// 若对象为空，则返回0值
		if (null == num) {
			// 返回0值
			return Boolean.FALSE;
		}
		return num;
	}

	/**
	 * 根据部门编码查询自有网点信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-6-24 下午3:28:06
	 */
	public static OrgAdministrativeInfoEntity queryOwerDeptByCode(String code) {
		// 判断部门编码是否为空
		if (StringUtils.isEmpty(code)) {
			return null;
		}
		// 获得部门服务接口
		IOrgInfoService orgService = GuiceContextFactroy.getInjector().getInstance(OrgInfoService.class);
		// 获得组织对象
		OrgAdministrativeInfoEntity e = orgService.queryByCode(code);
		// 判断组织部门是否为空
		if (null == e) {
			return null;
		} else {
			return e;
		}
	}

	/**
	 * 根据部门编码查询外发网点信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-6-24 下午3:30:43
	 */
	public static OuterBranchEntity queryAgentDeptByCode(String code) {
		// 判断部门编码是否为空
		if (StringUtils.isEmpty(code)) {
			return null;
		}
		// 获得代理服务接口
		IOuterBranchService agentService = GuiceContextFactroy.getInjector().getInstance(OuterBranchService.class);
		// 查询是否为代理网点
		OuterBranchEntity agent = agentService.queryOuterBranchByCode(code, new Date());
		// 判断组织部门是否为空
		if (null == agent) {
			return null;
		} else {
			return agent;
		}
	}

	/**
	 * 根据部门编码查询部门名称
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-7 下午5:22:53
	 */
	public static String getDeptNameByCode(String code) {
		OrgAdministrativeInfoEntity e = queryOwerDeptByCode(code);
		// 对象非空判断
		if (e != null) {
			// 返回部门名称
			return e.getName();
		} else {
			OuterBranchEntity agent = queryAgentDeptByCode(code);
			// 对象非空判断
			if (null != agent) {
				return StringUtil.defaultIfNull(agent.getAgentDeptName());
			} else {
				return "";
			}
		}
	}

	/**
	 * 根据城市编码获得城市名称
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-7 下午5:30:56
	 */
	public static String getCityNameByCode(String code) {
		// 获得地址对象
		IAddressService addService = GuiceContextFactroy.getInjector().getInstance(AddressService.class);
		// 获得地址名称
		String name = addService.queryCityByCode(code);
		// 对象非空判断
		if (StringUtil.isNotEmpty(name)) {
			return name;
		} else {
			return i18n.get("foss.gui.common.commonUtils.constantsName.errorCityCode");
		}
	}

	/**
	 * 根据组织编码获得城市名
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-7 下午5:45:16
	 */
	public static String getCityNameByOrgCode(String orgCode) {
		// 得到orgService注入service对象
		IOrgInfoService orgService = GuiceContextFactroy.getInjector().getInstance(OrgInfoService.class);
		// 查询得到org对象
		OrgAdministrativeInfoEntity e = orgService.queryByCode(orgCode);
		// 如果对象存在， 返回城市名称
		if (e != null) {
			// 城市编码
			String cityCode = e.getCityCode();
			// 非空判断
			if (StringUtil.isNotEmpty(cityCode)) {
				return CommonUtils.getCityNameByCode(cityCode);
			}
		}

		// return
		// i18n.get("foss.gui.common.commonUtils.constantsName.errorOrgDeptCode");
		return "";
	}
	
	/**
	 * 根据组织编码获得城市编码
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-7 下午5:45:16
	 */
	public static String getCityCodeNameByOrgCode(String orgCode) {
		// 得到orgService注入service对象
		IOrgInfoService orgService = GuiceContextFactroy.getInjector().getInstance(OrgInfoService.class);
		// 查询得到org对象
		OrgAdministrativeInfoEntity e = orgService.queryByCode(orgCode);
		// 如果对象存在， 返回城市名称
		if (e != null) {
			// 城市编码
			return StringUtil.defaultIfNull(e.getCityCode());
		}
		return "";
	}


	/**
	 * 根据组织编码获得代理部门所属城市名
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-6-13 上午10:54:23
	 */
	public static String getAgentCityNameByOrgCode(String orgCode) {
		// 得到orgService注入service对象
		IOuterBranchService agentService = GuiceContextFactroy.getInjector().getInstance(OuterBranchService.class);
		// 查询得到OuterBranch对象
		OuterBranchEntity e = agentService.queryOuterBranchByCode(orgCode, new Date());
		// 如果对象存在， 返回城市名称
		if (e != null) {
			// 城市编码
			String cityCode = e.getCityCode();
			// 非空判断
			if (StringUtil.isNotEmpty(cityCode)) {
				return CommonUtils.getCityNameByCode(cityCode);
			}
		}
		return "";
	}

	/**
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-6-13 上午11:06:05
	 */
	public static String getDestCityNameByCode(String code) {
		String cityName = CommonUtils.getCityNameByOrgCode(code);
		if (StringUtil.isEmpty(cityName)) {
			return CommonUtils.getAgentCityNameByOrgCode(code);
		}

		return cityName;
	}

	/**
	 * 根据数据字典的值代码和词条代码，获得值代码对应的名称
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-7 下午6:30:21
	 */
	public static String getNameFromDict(String activeTmp, String termsCode) {
		// 从本地获得数据字典接口
		IDataDictionaryValueService dataService = GuiceContextFactroy.getInjector().getInstance(DataDictionaryValueService.class);
		// 词条值的名称
		String valueName = "";
		// 通过词条代码获取数据集合
		List<DataDictionaryValueEntity> list = dataService.queryByTermsCode(StringUtil.defaultIfNull(termsCode));
		// 集合非空判断
		if (CollectionUtils.isNotEmpty(list)) {
			// 遍历集合
			for (Iterator<DataDictionaryValueEntity> iterator = list.iterator(); iterator.hasNext();) {
				// 获得数据字典对象
				DataDictionaryValueEntity dataDictionaryValueEntity = iterator.next();
				// 判断值代码是否一致
				if (dataDictionaryValueEntity.getValueCode().equals(StringUtil.defaultIfNull(activeTmp))) {
					// 返回值值代码对应的值名称
					valueName = StringUtil.defaultIfNull(dataDictionaryValueEntity.getValueName());
					return valueName;
				}
			}
		}
		return valueName;
	}

	/**
	 * 根据产品类型编码获得产品名称，包括一级产品、二级产品、三级产品
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-7 下午8:39:38
	 */
	public static String getNameByProductCode(String code) {
		// 获得产品编号
		String productCode = StringUtil.defaultIfNull(code);
		if (PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20001.equals(productCode)) {
			// 一级产品-汽运
			productCode = i18n.get("foss.gui.creating.product.truck");
		} else if (PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C1_C20002.equals(productCode)
				|| PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20004.equals(productCode)// 二级产品-空运
				) {
			// 一级产品-空运
			productCode = i18n.get("foss.gui.creating.product.air");
		} else if (PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20001.equals(productCode)) {
			// 二级产品-精准
			productCode = i18n.get("foss.gui.creating.product.accurate");
		} else if (PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20002.equals(productCode)) {
			// 二级产品-普货
			productCode = i18n.get("foss.gui.creating.product.normal");
		} else if (PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20003.equals(productCode)
				|| PricingConstants.ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(productCode)// 三级产品-整车
				) {
			// 二级产品-整车
			productCode = i18n.get("foss.gui.creating.product.whole");
		} else if (PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT.equals(productCode)) {
			// 三级产品-精准卡航
			productCode = i18n.get("foss.gui.creating.product.airTruck");
		} else if (PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT.equals(productCode)) {
			// 三级产品-精准城运
			productCode = i18n.get("foss.gui.creating.product.accurateCity");
		} else if (PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT.equals(productCode)) {
			// 三级产品-精准汽运(长途)
			productCode = i18n.get("foss.gui.creating.product.accurateLong");
		} else if (PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT.equals(productCode)) {
			// 三级产品-精准汽运(短途)
			productCode = i18n.get("foss.gui.creating.product.accurateShort");
		} else if (PricingConstants.ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(productCode)) {
			// 三级产品-汽运偏线
			productCode = i18n.get("foss.gui.creating.product.partial");
		} else if (PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productCode)) {
			// 三级产品-精准空运
			productCode = i18n.get("foss.gui.creating.product.accurateAir");
		} else {
			productCode = "";
		}

		return productCode;
	}

	/**
	 * 根据产品类型、值编码、货物类型编码查询名称
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-22 上午9:53:11
	 */
	public static String getGoodsTypeByCode(String productCode, String valueCode, String goodsTypeCode) {
		// 若产品类型或货物类型为空，则返回空
		if (StringUtil.isEmpty(productCode) || StringUtil.isEmpty(goodsTypeCode)) {
			return "";
		} else {
			String valueName = CommonUtils.getNameFromDict(valueCode, goodsTypeCode);
			// 货物类型判断
			if (ProductEntityConstants.PRICING_PRODUCT_C1_C20001.equals(productCode)) {
				// 汽运
				return valueName;
			} else {
				// 空运
				return "H00001".equals(valueCode) ? i18n.get("foss.gui.common.waybillEditUI.common.all") : "";
			}
		}
	}

	/**
	 * get user name from service
	 * 
	 * @param dataService
	 * @param activeTmp
	 * @return
	 */
	public static String getUserNameFromUserService(String code) {
		IUserService userService = GuiceContextFactroy.getInjector().getInstance(UserService.class);
		UserEntity e = userService.queryUserByCode(code);
		if (e != null) {
			return e.getUserName();
		} else {
			return "";
		}
	}

	/**
	 * 根据CRM中的产品类型转换为FOSS中的产品类型
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-5-8 下午7:37:33
	 */
	public static String convertCrmProductCode(String crmCode) {
		// 产品类型
		String productCode = "";
		if (CrmTransportTypeEnum.JZQYLONG.getCode().equals(crmCode)) {
			// 精准汽运(长)
			productCode = ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT;
		} else if (CrmTransportTypeEnum.JZQYSHORT.getCode().equals(crmCode)) {
			// 精准汽运(短)
			productCode = ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT;
		} else if (CrmTransportTypeEnum.JZKY.getCode().equals(crmCode)) {
			// 精准空运
			productCode = ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT;
		} else if (CrmTransportTypeEnum.JZKH.getCode().equals(crmCode)) {
			// 精准卡航
			productCode = ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT;
		} else if (CrmTransportTypeEnum.JZCY.getCode().equals(crmCode)) {
			// 精准城运
			productCode = ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT;
		} else if (CrmTransportTypeEnum.AGENTVEHICLE.getCode().equals(crmCode)) {
			// 汽运偏线
			productCode = ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE;
		}
		return productCode;
	}

	/**
	 * 根据CRM的产品类型和提货方式，转换为FOSS的提货方式 （因为在数据库中FOSS的提货方式分为汽运与空运，CRM没有）
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-5-8 下午6:11:05
	 */
	public static String convertCrmReceiveMethod(String productCode, String crmReceiveMethod) {
		// 运输类型
		String transType = CommonUtils.convertProductCodeToTransType(StringUtil.defaultIfNull(productCode));
		// 提货方式
		String receiveMethod = "";
		// 判断是汽运还是空运
		if (DictionaryValueConstants.BSE_LINE_TRANSTYPE_QIYUN.equals(transType)) {
			receiveMethod = convertCrmTranstypeQiyun(crmReceiveMethod,receiveMethod);
		} else if (DictionaryValueConstants.BSE_LINE_TRANSTYPE_KONGYUN.equals(transType)) {
			receiveMethod = converCrmTranstypeKongyun(crmReceiveMethod,receiveMethod);
		}else{
			receiveMethod = converCrmTranstypeOthers(crmReceiveMethod,receiveMethod);
			
		}
		return receiveMethod;
	}

	private static String converCrmTranstypeOthers(String crmReceiveMethod,
			String receiveMethod) {
		if (CrmReceiveMethodEnum.PICKNOTUPSTAIRS.getCrmCode().equals(crmReceiveMethod)//送货(不含上楼)
				|| CrmReceiveMethodEnum.PICKUPSTAIRS.getCrmCode().equals(crmReceiveMethod)//送货上楼
				|| CrmReceiveMethodEnum.PICKFOOR.getCrmCode().equals(crmReceiveMethod)//送货上门
				|| CrmReceiveMethodEnum.FREE_DELIVERY.getCrmCode().equals(crmReceiveMethod)//免费送货
				||	CrmReceiveMethodEnum.DELIVER_UP.getCrmCode().equals(crmReceiveMethod)//送货上楼
				) {
			// 送货上楼
			receiveMethod = WaybillConstants.DELIVER_UP;
		}else  if (CrmReceiveMethodEnum.INNER_PICKUP.getCrmCode().equals(crmReceiveMethod)) {//内部带货自提
			// 内部自提
			receiveMethod = WaybillConstants.INNER_PICKUP;
		}else if (CrmReceiveMethodEnum.PICKONAIEPORT.getCrmCode().equals(crmReceiveMethod)//自提
				|| CrmReceiveMethodEnum.SELF_PICKUP.getCrmCode().equals(crmReceiveMethod)//自提（不含机场提货费）
				|| CrmReceiveMethodEnum.FREE_PICKUP.getCrmCode().equals(crmReceiveMethod)//免费自提
				) {
			// 自提
			receiveMethod = WaybillConstants.SELF_PICKUP;
		}
		return receiveMethod;
	}

	private static String converCrmTranstypeKongyun(String crmReceiveMethod,
			String receiveMethod) {
		// 空运
		if (CrmReceiveMethodEnum.FREE_PICKUP.getCrmCode().equals(crmReceiveMethod)) {
			// 空运免费自提
			receiveMethod = WaybillConstants.AIR_PICKUP_FREE;
		} else if (CrmReceiveMethodEnum.SELF_PICKUP.getCrmCode().equals(crmReceiveMethod)) {
			// 空运自提(不含机场提货费)
			receiveMethod = WaybillConstants.AIR_SELF_PICKUP;
		} else if (CrmReceiveMethodEnum.FREE_DELIVERY.getCrmCode().equals(crmReceiveMethod)) {
			// 空运免费送货
			receiveMethod = WaybillConstants.DELIVER_FREE_AIR;
		} else if (CrmReceiveMethodEnum.PICKONAIEPORT.getCrmCode().equals(crmReceiveMethod)) {
			// 空运机场自提
			receiveMethod = WaybillConstants.AIRPORT_PICKUP;
		} else if (CrmReceiveMethodEnum.PICKNOTUPSTAIRS.getCrmCode().equals(crmReceiveMethod)) {
			// 送货(不含上楼)
			receiveMethod = WaybillConstants.DELIVER_NOUP_AIR;
		} else if (CrmReceiveMethodEnum.PICKUPSTAIRS.getCrmCode().equals(crmReceiveMethod)) {
			// 空运送货上楼
			receiveMethod = WaybillConstants.DELIVER_UP_AIR;
		} else if (CrmReceiveMethodEnum.DELIVERY_STORE.getCrmCode().equals(crmReceiveMethod)) {
			// 空运送货进仓
			receiveMethod = WaybillConstants.DELIVER_INGA_AIR;
		}
		return receiveMethod;
	}

	private static String convertCrmTranstypeQiyun(String crmReceiveMethod,
			String receiveMethod) {
		// 汽运
		if (CrmReceiveMethodEnum.PICKNOTUPSTAIRS.getCrmCode().equals(crmReceiveMethod)) {
			// 汽运送货(不含上楼)
			receiveMethod = WaybillConstants.DELIVER_NOUP;
		} else if (CrmReceiveMethodEnum.PICKSELF.getCrmCode().equals(crmReceiveMethod)) {
			// 汽运自提
			receiveMethod = WaybillConstants.SELF_PICKUP;
		} else if (CrmReceiveMethodEnum.FREE_DELIVERY.getCrmCode().equals(crmReceiveMethod)) {
			// 汽运免费派送
			receiveMethod = WaybillConstants.DELIVER_FREE;
		} else if (CrmReceiveMethodEnum.DELIVERY_STORE.getCrmCode().equals(crmReceiveMethod)) {
			// 汽运送货进仓
			receiveMethod = WaybillConstants.DELIVER_STORAGE;
		} else if (CrmReceiveMethodEnum.PICKUPSTAIRS.getCrmCode().equals(crmReceiveMethod)) {
			// /汽运送货（上楼）
			receiveMethod = WaybillConstants.DELIVER_UP;
		} else if (CrmReceiveMethodEnum.INNER_PICKUP.getCrmCode().equals(crmReceiveMethod)) {
			// 汽运内部自提
			receiveMethod = WaybillConstants.INNER_PICKUP;
		}
		return receiveMethod;
	}
    
    /**
     * 根据CRM提货方式，转换为FOSS的提货方式 
     * 
     * WangQianJin
     * @date 2013-08-28
     */
	public static String convertCrmReceiveMethodByCrmCode(String crmReceiveMethod) {
		// 提货方式
		String receiveMethod = "";

		// 汽运
		if (CrmReceiveMethodEnum.PICKNOTUPSTAIRS.getCrmCode().equals(crmReceiveMethod)) {
			// 汽运送货(不含上楼)
			receiveMethod = WaybillConstants.DELIVER_NOUP;
		} else if (CrmReceiveMethodEnum.PICKSELF.getCrmCode().equals(crmReceiveMethod)) {
			// 汽运自提
			receiveMethod = WaybillConstants.SELF_PICKUP;
		} else if (CrmReceiveMethodEnum.FREE_DELIVERY.getCrmCode().equals(crmReceiveMethod)) {
			// 汽运免费派送
			receiveMethod = WaybillConstants.DELIVER_FREE;
		} else if (CrmReceiveMethodEnum.DELIVERY_STORE.getCrmCode().equals(crmReceiveMethod)) {
			// 汽运送货进仓
			receiveMethod = WaybillConstants.DELIVER_STORAGE;
		} else if (CrmReceiveMethodEnum.PICKUPSTAIRS.getCrmCode().equals(crmReceiveMethod)
				|| CrmReceiveMethodEnum.DELIVER_UP.getCrmCode().equals(crmReceiveMethod)) {
			// /汽运送货（上楼）
			receiveMethod = WaybillConstants.DELIVER_UP;
		} else if (CrmReceiveMethodEnum.INNER_PICKUP.getCrmCode().equals(crmReceiveMethod)) {
			// 汽运内部自提
			receiveMethod = WaybillConstants.INNER_PICKUP;
		} else
			receiveMethod = converCrmTranstypeKongyun(crmReceiveMethod,
					receiveMethod); 

		return receiveMethod;
	}

	/**
	 * 校验电子地图传过的自有网点部门是否与运单界面相关信息符合（如可到达、可派送等）
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-6-15 下午3:07:45
	 */
	public static void validateOwerGisDept(WaybillPanelVo bean, SaleDepartmentEntity dept) {
		// 传入对象非空
		if (bean == null || dept == null) {
			return;
		}

		// 判断营业部是否开业
		if (dept.getOpeningDate() != null && dept.getOpeningDate().after(new Date())) {
			// 对不起，您所选网点还没有开始营业！
			throw new WaybillValidateException(i18n.get("foss.gui.common.commonutils.noopeningdate"));
		}

		// 是否自提
		if (bean.getReceiveMethod() != null && CommonUtils.verdictPickUpSelf(bean.getReceiveMethod().getValueCode())) {
			// 是否支持自提
			if (!BooleanConvertYesOrNo.stringToBoolean(dept.getPickupSelf())) {
				throw new WaybillValidateException("对不起，您所选网点不正确！\n该网点不支持[自提]！");
			}
		}

		// 是否派送
		if (bean.getReceiveMethod() != null && CommonUtils.verdictPickUpDoor(bean.getReceiveMethod().getValueCode())) {
			// 是否支持送货上门
			if (!BooleanConvertYesOrNo.stringToBoolean(dept.getDelivery())) {
				throw new WaybillValidateException("对不起，您所选网点不正确！\n该网点不支持[派送]！");
			}
		}

		// 是否到达，若不做到达，则校验不通过
		if (!BooleanConvertYesOrNo.stringToBoolean(dept.getArrive())) {
			throw new WaybillValidateException("对不起，您所选网点不正确！\n该网点不支持[到达]");
		}
	}

	/**
	 * 校验电子地图传过的代理网点部门是否与运单界面相关信息符合（如可到达、可派送等）
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-6-15 下午3:07:49
	 */
	public static void validateAgentGisDept(WaybillPanelVo bean, OuterBranchEntity dept) {
		// 传入对象非空
		if (bean == null || dept == null) {
			return;
		}

		// 是否自提
		if (bean.getReceiveMethod() != null && CommonUtils.verdictPickUpSelf(bean.getReceiveMethod().getValueCode())) {
			// 是否支持自提
			if (!BooleanConvertYesOrNo.stringToBoolean(dept.getPickupSelf())) {
				throw new WaybillValidateException("对不起，您所选网点不正确！\n该网点不支持[自提]");
			}
		}

		// 是否派送
		if (bean.getReceiveMethod() != null && CommonUtils.verdictPickUpDoor(bean.getReceiveMethod().getValueCode())) {
			// 是否支持送货上门
			if (!BooleanConvertYesOrNo.stringToBoolean(dept.getPickupToDoor())) {
				throw new WaybillValidateException("对不起，您所选网点不正确！\n该网点不支持[派送]");
			}
		}

		// 是否到达，若不做到达，则校验不通过
		if (!BooleanConvertYesOrNo.stringToBoolean(dept.getArrive())) {
			throw new WaybillValidateException("对不起，您所选网点不正确！\n该网点不支持[到达]");
		}

		/**
		 * 判断是否为空运，且配载类型是否为单独开单，如果是，则提货网点只显示机场
		 */
		validateAirAgentGis(bean, dept);

	}

	private static void validateAirAgentGis(WaybillPanelVo bean,
			OuterBranchEntity dept) {
		if (bean.getProductCode() != null && ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(bean.getProductCode().getCode())) {
			/**
			 * 单独开单只能选择机场、合大票只能选择代理
			 */
			if (bean.getFreightMethod() != null) {
				// 对不起，您所选网点不正确！单独开单只能选择机场!
				if (ProductEntityConstants.PRICING_PRODUCT_FREIGNT_DDKD.equals(bean.getFreightMethod().getValueCode())
						&& !BooleanConvertYesOrNo.stringToBoolean(dept.getIsAirport())) {
					throw new WaybillValidateException("对不起，您所选网点不正确！\n单独开单只能选择机场!");
				}
				// 对不起，您所选网点不正确！合大票只能选择代理!
				else if (ProductEntityConstants.PRICING_PRODUCT_FREIGNT_HDP.equals(bean.getFreightMethod().getValueCode())
						&& BooleanConvertYesOrNo.stringToBoolean(dept.getIsAirport())) {
					throw new WaybillValidateException("对不起，您所选网点不正确！\n合大票只能选择代理!");
				}
			}
		}
	}

	/**
	 * 获取系统参数
	 * 
	 * @param type
	 * @return
	 */
	public static ConfigurationParamsEntity getBaseConfigurationParamsEntity(IConfigurationParamsService configurationParamsService, String type) {
		/**
		 * 根据组织的配置参数查询系统参数实体
		 * 
		 * 组织配置参数-综合模块使用：DictionaryConstants.SYSTEM_CONFIG_PARM__BAS
		 * 
		 */
		return configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__BAS, type, FossConstants.ROOT_ORG_CODE);

	}

	/**
	 * 重量校验
	 * 
	 * @param billWeight
	 * @param bean
	 * @return
	 */
	public static String validateGoodsWeightTotal(BigDecimal goodsWeightTotal, WaybillPanelVo bean) {
		IConfigurationParamsService configurationParamsService = GuiceContextFactroy.getInjector().getInstance(ConfigurationParamsService.class);

		BigDecimal maxWeight = new BigDecimal(NUM_300000);

		if (bean.getIsWholeVehicle()!=null && bean.getIsWholeVehicle()) {

			/**
			 * 获取最大重量配置参数
			 */
			ConfigurationParamsEntity weight = CommonUtils.getBaseConfigurationParamsEntity(configurationParamsService, ConfigurationParamsConstants.BAS_PARM__MAX_WEIGHT_VEHICLE);

			if (weight != null && StringUtils.isNotEmpty(weight.getConfValue())) {
				maxWeight = new BigDecimal(weight.getConfValue());
			}

			if (goodsWeightTotal.compareTo(maxWeight) > 0) {
				return i18n.get("foss.gui.common.commonUtils.constantsName.WholeVehicle.Weight.error") + maxWeight + (weight != null ? weight.getUnit() : "");
			}
		}

		return WaybillConstants.SUCCESS;
	}

	/**
	 * 体积校验
	 * 
	 * @param billWeight
	 * @param bean
	 * @return
	 */
	public static String validateGoodsVolumeTotal(BigDecimal goodsVolumeTotal, WaybillPanelVo bean) {
		IConfigurationParamsService configurationParamsService = GuiceContextFactroy.getInjector().getInstance(ConfigurationParamsService.class);
		BigDecimal maxVolume = new BigDecimal(NUM_300000);
		if (bean.getIsWholeVehicle()!=null && bean.getIsWholeVehicle()) {

			/**
			 * 获取最大重量配置参数
			 */
			ConfigurationParamsEntity volume = CommonUtils.getBaseConfigurationParamsEntity(configurationParamsService, ConfigurationParamsConstants.BAS_PARM__MAX_VOLUME_VEHICLE);

			if (volume != null && StringUtils.isNotEmpty(volume.getConfValue())) {
				maxVolume = new BigDecimal(volume.getConfValue());
			}

			if (goodsVolumeTotal.compareTo(maxVolume) > 0) {
				// 整车体积不能超过最大配置值
				return i18n.get("foss.gui.common.commonUtils.constantsName.WholeVehicle.Volum.error") + maxVolume + (volume != null ? volume.getUnit() : "");
			}
		}

		return WaybillConstants.SUCCESS;
	}

	/**
	 * 体积提示
	 * 
	 * @param billWeight
	 * @param bean
	 * @return
	 */
	public static String promptGoodsVolumeTotal(WaybillPanelVo bean) {
		IConfigurationParamsService configurationParamsService = GuiceContextFactroy.getInjector().getInstance(ConfigurationParamsService.class);
		BigDecimal maxVolume = new BigDecimal(NumberConstants.NUMBER_50);

		if (bean.getIsWholeVehicle()) {

			/**
			 * 获取需要提示重量配置参数
			 */
			ConfigurationParamsEntity volume = CommonUtils.getBaseConfigurationParamsEntity(configurationParamsService, ConfigurationParamsConstants.BAS_PARM__MIN_VOLUME_VEHICLE);

			if (volume != null && StringUtils.isNotEmpty(volume.getConfValue())) {
				maxVolume = new BigDecimal(volume.getConfValue());
			}

			if (bean.getGoodsVolumeTotal().compareTo(maxVolume) > 0) {
				return i18n.get("foss.gui.common.commonUtils.constantsName.WholeVehicle.Volum.prompt") + maxVolume + (volume != null ? volume.getUnit() : "");
			}
		}

		return WaybillConstants.SUCCESS;
	}

	/**
	 * 重量提示
	 * 
	 * @param billWeight
	 * @param bean
	 * @return
	 */
	public static String promptGoodsWeightTotal(WaybillPanelVo bean) {
		IConfigurationParamsService configurationParamsService = GuiceContextFactroy.getInjector().getInstance(ConfigurationParamsService.class);
		BigDecimal maxWeight = new BigDecimal(NumberConstants.NUMBER_50);

		if (bean.getIsWholeVehicle()) {

			/**
			 * 获取需要提示重量配置参数
			 */
			ConfigurationParamsEntity weight = CommonUtils.getBaseConfigurationParamsEntity(configurationParamsService, ConfigurationParamsConstants.BAS_PARM__MIN_WEIGHT_VEHICLE);

			if (weight != null && StringUtils.isNotEmpty(weight.getConfValue())) {
				maxWeight = new BigDecimal(weight.getConfValue());
			}

			if (bean.getGoodsWeightTotal().compareTo(maxWeight) > 0) {
				return i18n.get("foss.gui.common.commonUtils.constantsName.WholeVehicle.Weight.prompt") + maxWeight + (weight != null ? weight.getUnit() : "");
			}
		}

		return WaybillConstants.SUCCESS;
	}

	/**
	 * 判断是否为离线状态
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-6-28 上午8:06:46
	 */
	public static boolean isOnline() {
		if ("ONLINE_LOGIN".equals(SessionContext.get("user_loginType").toString())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 校验提货网点是否有效
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-7-9 上午8:27:34
	 */
	public static void validatePickupOrgCode(ValidateVo bean) {
		// 部门编码
		String deptCode = StringUtil.defaultIfNull(bean.getCustomerPickupOrgCode());
		// 判断是否是外发
		if (CommonUtils.isAgentDept(bean.getProductCode())) {
			// 对外发网点进行校验
			CommonUtils.validateAgentGisDeptForNew(bean, CommonUtils.queryAgentDeptByCode(deptCode));
		} else {
			BaseDataService vBaseDataService = BaseDataServiceFactory.getBaseDataService();
			// 对自有网点进行校验
			CommonUtils.validateOwerGisDeptForNew(bean, vBaseDataService.querySaleDepartmentByCodeOnline(deptCode));
		}
	}

	/**
	 * 校验电子地图传过的代理网点部门是否与运单界面相关信息符合（如可到达、可派送等）
	 * 
	 * @author WangQianJin
	 * @date 2013-7-10 下午3:07:49
	 */
	public static void validateAgentGisDeptForNew(ValidateVo bean, OuterBranchEntity dept) {
		// 传入对象非空
		if (bean == null || dept == null) {
			return;
		}

		// 是否自提
		if (bean.getReceiveMethod() != null && CommonUtils.verdictPickUpSelf(bean.getReceiveMethod())) {
			// 是否支持自提
			if (!BooleanConvertYesOrNo.stringToBoolean(dept.getPickupSelf())) {
				throw new WaybillValidateException("对不起，您所选网点不正确！\n该网点不支持[自提]");
			}
		}

		// 是否派送
		if (bean.getReceiveMethod() != null && CommonUtils.verdictPickUpDoor(bean.getReceiveMethod())) {
			// 是否支持送货上门
			if (!BooleanConvertYesOrNo.stringToBoolean(dept.getPickupToDoor())) {
				throw new WaybillValidateException("对不起，您所选网点不正确！\n该网点不支持[派送]");
			}
		}

		// 是否到达，若不做到达，则校验不通过
		if (!BooleanConvertYesOrNo.stringToBoolean(dept.getArrive()) && !FossConstants.NO.equals(bean.getIsNeedValCus())) {
			throw new WaybillValidateException("对不起，您所选网点不正确！\n该网点不支持[到达]");
		}

		/**
		 * 判断是否为空运，且配载类型是否为单独开单，如果是，则提货网点只显示机场
		 */
		validateAirFreightAndIsNeedValcus(bean, dept);

	}

	private static void validateAirFreightAndIsNeedValcus(ValidateVo bean,
			OuterBranchEntity dept) {
		if (bean.getProductCode() != null && ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(bean.getProductCode()) && !FossConstants.NO.equals(bean.getIsNeedValCus())) {
			/**
			 * 单独开单只能选择机场、合大票只能选择代理
			 */
			if (bean.getFreightMethod() != null) {
				// 对不起，您所选网点不正确！单独开单只能选择机场!
				if (ProductEntityConstants.PRICING_PRODUCT_FREIGNT_DDKD.equals(bean.getFreightMethod()) && !BooleanConvertYesOrNo.stringToBoolean(dept.getIsAirport())) {
					throw new WaybillValidateException("对不起，您所选网点不正确！\n单独开单只能选择机场!");
				}
				// 对不起，您所选网点不正确！合大票只能选择代理!
				else if (ProductEntityConstants.PRICING_PRODUCT_FREIGNT_HDP.equals(bean.getFreightMethod()) && BooleanConvertYesOrNo.stringToBoolean(dept.getIsAirport())) {
					throw new WaybillValidateException("对不起，您所选网点不正确！\n合大票只能选择代理!");
				}
			}
		}
	}

	/**
	 * 校验电子地图传过的自有网点部门是否与运单界面相关信息符合（如可到达、可派送等）
	 * 
	 * @author WangQianJin
	 * @date 2013-7-10 下午3:07:45
	 */
	public static void validateOwerGisDeptForNew(ValidateVo bean, SaleDepartmentEntity dept) {
		// 传入对象非空
		if (bean == null || dept == null) {
			return;
		}

		// 判断营业部是否开业
		if (dept.getOpeningDate() != null && dept.getOpeningDate().after(new Date()) && !FossConstants.NO.equals(bean.getIsNeedValCus())) {
			// 对不起，您所选网点还没有开始营业！
			throw new WaybillValidateException(i18n.get("foss.gui.common.commonutils.noopeningdate"));
		}

		// 是否自提
		if (bean.getReceiveMethod() != null && CommonUtils.verdictPickUpSelf(bean.getReceiveMethod())) {
			// 是否支持自提
			if (!BooleanConvertYesOrNo.stringToBoolean(dept.getPickupSelf())) {
				throw new WaybillValidateException("对不起，您所选网点不正确！\n该网点不支持[自提]！");
			}
		}

		// 是否派送
		if (bean.getReceiveMethod() != null && CommonUtils.verdictPickUpDoor(bean.getReceiveMethod())) {
			// 是否支持送货上门
			if (!BooleanConvertYesOrNo.stringToBoolean(dept.getDelivery())) {
				throw new WaybillValidateException("对不起，您所选网点不正确！\n该网点不支持[派送]！");
			}
		}

		// 是否到达，若不做到达，则校验不通过
		if (!BooleanConvertYesOrNo.stringToBoolean(dept.getArrive()) && !FossConstants.NO.equals(bean.getIsNeedValCus())) {
			throw new WaybillValidateException("对不起，您所选网点不正确！\n该网点不支持[到达]");
		}
	}

	/**
	 * 根据客户编码查询客户所属部门名称
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-7-11 下午8:29:50
	 */
	public static String getDeptNameByCustCode(String custCode) {
		// 判断客户编码是否为空
		if (StringUtils.isEmpty(custCode)) {
			return "";
		} else {
			// 获得本地客户服务方法，再去远程访问服务器端数据
			CustomerService customerService = GuiceContextFactroy.getInjector().getInstance(CustomerService.class);
			// 根据客户编码查询客户集合信息
			List<QueryMemberDialogVo> custList = customerService.queryCustInfoByCode(custCode);
			// 集合非空判断
			if (CollectionUtils.isNotEmpty(custList)) {
				return StringUtil.defaultIfNull(custList.get(0).getDeptName());
			} else {
				return "";
			}
		}
	}

	/**
	 * 
	 * 获取其他费用查询参数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:02:10
	 */
	public static QueryBillCacilateValueAddDto getQueryOtherChargeParam(WaybillPanelVo bean) {
		QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
		// 判断bean是否为空
		if (null != bean) {
			// 出发部门CODE
			queryDto.setOriginalOrgCode(bean.getReceiveOrgCode());
			// 到达部门CODE
			if (null != bean.getCustomerPickupOrgCode()) {
				queryDto.setDestinationOrgCode(bean.getCustomerPickupOrgCode().getCode());
			}
			// 产品CODE
			if (null != bean.getProductCode()) {
				queryDto.setProductCode(bean.getProductCode().getCode());
			}
			// 设置CRM渠道
			queryDto.setChannelCode(bean.getOrderChannel());
			// 长途 还是短途
			queryDto.setLongOrShort(bean.getLongOrShort());
		} else {
			UserEntity user = (UserEntity) SessionContext.getCurrentUser();
			OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
			// 出发部门CODE
			queryDto.setOriginalOrgCode(dept.getCode());
			// 到达部门CODE
			queryDto.setDestinationOrgCode(null);
			// 产品CODE
			queryDto.setProductCode(null);
			// 设置CRM渠道
			queryDto.setChannelCode(null);
			// 长途 还是短途
			queryDto.setLongOrShort(null);
		}
		// 货物类型CODE
		queryDto.setGoodsTypeCode(null);
		queryDto.setReceiveDate(new Date());// 营业部收货日期（可选，无则表示当前日期）
		queryDto.setWeight(BigDecimal.ZERO);// 重量
		queryDto.setVolume(BigDecimal.ZERO);// 体积
		queryDto.setOriginnalCost(BigDecimal.ZERO);// 原始费用
		queryDto.setFlightShift(null);// 航班号
		queryDto.setSubType(null);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_QT);// 计价条目CODE
		queryDto.setPricingEntryName(null);// 计价名称
		return queryDto;
	}

	/**
	 * 
	 * 设置送货费
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-3-16 上午08:15:55
	 */
	public static void setDeliverCharge(WaybillPanelVo vo, String code, WaybillCHDtlPendingEntity entity) {
		List<DeliverChargeEntity> deliverCharge = vo.getDeliverList();
		if (deliverCharge == null) {
			deliverCharge = new ArrayList<DeliverChargeEntity>();
		}

		// 送货费
		if (PriceEntityConstants.PRICING_CODE_SH.equals(code)
				|| PriceEntityConstants.PRICING_CODE_SHSL.equals(code) // 添加送货上楼
				|| PriceEntityConstants.PRICING_CODE_SHJC.equals(code) // 添加送货进仓
				|| PriceEntityConstants.PRICING_CODE_CY.equals(code) // 添加超远派送费
				) {
			// 添加送货费
			deliverCharge.add(getDeliverCharge(vo, code, entity));
		} 
		vo.setDeliverList(deliverCharge);
	}

	/**
	 * 
	 * 将查询出来的送货费添加到送货费集合中，并且对送货费进行合计
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-3-16 上午08:36:20
	 */
	public static DeliverChargeEntity getDeliverCharge(WaybillPanelVo vo, String code, WaybillCHDtlPendingEntity entity) {
		DeliverChargeEntity deliverChargeEntity = new DeliverChargeEntity();
		BigDecimal deliverFee = vo.getDeliveryGoodsFee();
		if (deliverFee == null) {
			deliverFee = BigDecimal.ZERO;
		}
		// 是否有效
		deliverChargeEntity.setActive(FossConstants.ACTIVE);
		// 金额
		deliverChargeEntity.setAmount(entity.getAmount());
		// 币种
		deliverChargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		// 费用ID
		deliverChargeEntity.setId(entity.getId());
		// 运单号
		deliverChargeEntity.setWaybillNo(vo.getWaybillNo());
		// 费用编码
		deliverChargeEntity.setCode(code);
		// 送货费(不能对送货费进行累加，只能对非送货费进行累，参见BUG-7877)
		if (!PriceEntityConstants.PRICING_CODE_SH.equals(code)) {
			deliverFee = deliverFee.add(entity.getAmount());
		}
		// 送货费
		vo.setDeliveryGoodsFee(deliverFee);
		// 画布-送货费
		vo.setDeliveryGoodsFeeCanvas(deliverFee.toString());
		return deliverChargeEntity;
	}

	/**
	 * 
	 * 将查询出的其他费用设置到表格list中
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:00:49
	 */
	public static List<OtherChargeVo> getOtherChargeList(List<ValueAddDto> list) {
		List<OtherChargeVo> voList = new ArrayList<OtherChargeVo>();

		if (list != null) {
			for (ValueAddDto dto : list) {
				// 开单的时候不能增加更改费
				if (PriceEntityConstants.PRICING_CODE_GGF.equals(dto.getSubType())) {
					continue;
				}

				OtherChargeVo vo = new OtherChargeVo();
				if (dto.getCandelete() != null && !BooleanConvertYesOrNo.stringToBoolean(dto.getCandelete())) {
					// 费用编码
					vo.setCode(dto.getSubType());
					// 名称
					vo.setChargeName(dto.getSubTypeName());
					// 归集类别
					vo.setType(dto.getBelongToPriceEntityName());
					// 描述
					vo.setDescrition(dto.getPriceEntityCode());
					// 金额
					vo.setMoney(dto.getCaculateFee().toString());
					// 上限
					vo.setUpperLimit(dto.getMaxFee().toString());
					// 下限
					vo.setLowerLimit(dto.getMinFee().toString());
					// 是否可修改
					vo.setIsUpdate(BooleanConvertYesOrNo.stringToBoolean(dto.getCanmodify()));
					// 是否可删除
					vo.setIsDelete(BooleanConvertYesOrNo.stringToBoolean(dto.getCandelete()));
					vo.setId(dto.getId());
					voList.add(vo);
				}
			}
		}
		return voList;
	}

	/**
	 * 判断是否是淘宝订单
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-13 上午11:32:33
	 */
	public static boolean isTaoBaoOrder(WaybillPanelVo bean) {
		// 非空判断
		if (null == bean) {
			return false;
		}

		// 订单类型
		String orderType = bean.getOrderChannel();
		if (WaybillConstants.CRM_ORDER_CHANNEL_TAOBAO.equals(orderType)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 
	 * <p>获取当前登录人员所在部门信息</p> 
	 * @author foss-sunrui
	 * @date 2013-1-8 下午1:13:34
	 * @return
	 * @see
	 */
	public static OrgAdministrativeInfoEntity getLoginDept() {
		OrgAdministrativeInfoEntity org = null;
		UserEntity user = (UserEntity)SessionContext.getCurrentUser();
		if(user!=null){
			EmployeeEntity emp = (EmployeeEntity)user.getEmployee();
			if(emp!=null){
				org = emp.getDepartment();
			}
		}
		return org;
	}
	
	/**
	 * 根据部门组织或组织编码查询试点营业部信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-23 下午5:18:27
	 */
	public static SalesDepartmentCityDto getSalesDepartmentCityDtoByCode(String code){
		// 营业部服务类
		SalesDepartmentService salesDepartmentService = DownLoadDataServiceFactory.getSalesDepartmentService();
		// 设置查询条件
		SalesDepartmentCityDto queryDto = new SalesDepartmentCityDto();
		queryDto.setSalesDepartmentCode(code);
		return salesDepartmentService.querySalesDepartmentCityInfo(queryDto);
	}
	/**
	 * @author 200945 - wutao
	 * @param customerQueryConditionDto
	 * @param cusBargainEntity
	 * @param panelVo
	 */
	public static void setExpDevliveryCusomterSettler(CustomerQueryConditionDto customerQueryConditionDto,CusBargainEntity cusBargainEntity,WaybillPanelVo panelVo){
		//判断CRM维护了此客户的信息
		if (cusBargainEntity==null ||("".equals(cusBargainEntity.getAsyntakegoodsCode())
				&& "".equals(cusBargainEntity.getUnifiedCode())
				&& "".equals(cusBargainEntity.getHastenfunddeptCode()))) {
			panelVo.setStartCentralizedSettlement(WaybillConstants.IS_NULL_FOR_AI);
			panelVo.setStartContractOrgCode(null);
			panelVo.setStartContractOrgName(null);
			panelVo.setStartReminderOrgCode(null);
		} else {
			
			//从CRM那维护的客户资料中获取的时候统一结算为【是】的时候；
			//【是否统一结算】设置为【是】，【合同部门】设置为查询出来的对应的部门
			if (WaybillConstants.YES.equals(cusBargainEntity.getAsyntakegoodsCode())) {
				panelVo.setStartCentralizedSettlement(WaybillConstants.IS_NOT_NULL_FOR_AI);
				panelVo.setStartContractOrgCode(cusBargainEntity.getUnifiedCode());
				panelVo.setStartContractOrgName(queryContractOrgName(cusBargainEntity.getUnifiedCode()));
				panelVo.setStartReminderOrgCode(cusBargainEntity.getHastenfunddeptCode());
			} else {
				//从CRM那维护的客户资料中获取的时候统一结算为【是】的时候；
				//【是否统一结算】设置为【否】，【合同部门】设置为【null】
				panelVo.setStartCentralizedSettlement(WaybillConstants.IS_NULL_FOR_AI);
				panelVo.setStartContractOrgCode(null);
				panelVo.setStartContractOrgName(null);
				panelVo.setStartReminderOrgCode(null);
			}
			
			
		}
		//wutao == end 
	}
	
	//设置待补录信息
	public static void setPendingExpDevliveryCusomterSettler(CustomerQueryConditionDto customerQueryConditionDto,CusBargainEntity cusBargainEntity,WaybillPendingEntity panelVo){
		//判断CRM维护了此客户的信息
		if (cusBargainEntity==null ||("".equals(cusBargainEntity.getAsyntakegoodsCode())
				&& "".equals(cusBargainEntity.getUnifiedCode())
				&& "".equals(cusBargainEntity.getHastenfunddeptCode()))) {
			panelVo.setStartCentralizedSettlement(WaybillConstants.IS_NULL_FOR_AI);
			panelVo.setStartContractOrgCode(null);
			panelVo.setStartContractOrgName(null);
			panelVo.setStartReminderOrgCode(null);
		} else {
			
				//从CRM那维护的客户资料中获取的时候统一结算为【是】的时候；
				//【是否统一结算】设置为【是】，【合同部门】设置为查询出来的对应的部门
				if (WaybillConstants.YES.equals(cusBargainEntity.getAsyntakegoodsCode())) {
					panelVo.setStartCentralizedSettlement(WaybillConstants.IS_NOT_NULL_FOR_AI);
					panelVo.setStartContractOrgCode(cusBargainEntity.getUnifiedCode());
					panelVo.setStartContractOrgName(queryContractOrgName(cusBargainEntity.getUnifiedCode()));
					panelVo.setStartReminderOrgCode(cusBargainEntity.getHastenfunddeptCode());
				} else {
					//从CRM那维护的客户资料中获取的时候统一结算为【是】的时候；
					//【是否统一结算】设置为【否】，【合同部门】设置为【null】
					panelVo.setStartCentralizedSettlement(WaybillConstants.IS_NULL_FOR_AI);
					panelVo.setStartContractOrgCode(null);
					panelVo.setStartContractOrgName(null);
					panelVo.setStartReminderOrgCode(null);
				}
			
		}
		//wutao == end 
	}

/**
 * @author 200945 - wutao
 * @param customerQueryConditionDto
 * @param cusBargainEntity
 * @param panelVo
 */
	public static void setExpReciveCusomterSettler(CustomerQueryConditionDto customerQueryConditionDto,CusBargainEntity cusBargainEntity,WaybillPanelVo panelVo){
			//判断CRM维护了此客户的信息
			if (cusBargainEntity==null  || ("".equals(cusBargainEntity.getAsyntakegoodsCode())
					&& "".equals(cusBargainEntity.getUnifiedCode())
					&& "".equals(cusBargainEntity.getHastenfunddeptCode()))) {
				panelVo.setArriveCentralizedSettlement(WaybillConstants.IS_NULL_FOR_AI);
				panelVo.setArriveContractOrgCode(null);
				panelVo.setArriveContractOrgName(null);
				panelVo.setArriveReminderOrgCode(null);
			} else {
				
					//从CRM那维护的客户资料中获取的时候统一结算为【是】的时候；
					//【是否统一结算】设置为【是】，【合同部门】设置为查询出来的对应的部门
					if (WaybillConstants.YES.equals(cusBargainEntity.getAsyntakegoodsCode())) {
						panelVo.setArriveCentralizedSettlement(WaybillConstants.IS_NOT_NULL_FOR_AI);
						panelVo.setArriveContractOrgCode(cusBargainEntity.getUnifiedCode());
						panelVo.setArriveContractOrgName(queryContractOrgName(cusBargainEntity.getUnifiedCode()));
						panelVo.setArriveReminderOrgCode(cusBargainEntity.getHastenfunddeptCode());
					} else {
						//从CRM那维护的客户资料中获取的时候统一结算为【是】的时候；
						//【是否统一结算】设置为【否】，【合同部门】设置为【null】
						panelVo.setArriveCentralizedSettlement(WaybillConstants.IS_NULL_FOR_AI);
						panelVo.setArriveContractOrgCode(null);
						panelVo.setArriveContractOrgName(null);
						panelVo.setArriveReminderOrgCode(null);
					}
				
			}
			//wutao == end 
		}

	public static void setPendingExpReciveCusomterSettler(CustomerQueryConditionDto customerQueryConditionDto,CusBargainEntity cusBargainEntity,WaybillPendingEntity panelVo){
		//判断CRM维护了此客户的信息
		if (cusBargainEntity==null  || ("".equals(cusBargainEntity.getAsyntakegoodsCode())
				&& "".equals(cusBargainEntity.getUnifiedCode())
				&& "".equals(cusBargainEntity.getHastenfunddeptCode()))) {
			panelVo.setArriveCentralizedSettlement(WaybillConstants.IS_NULL_FOR_AI);
			panelVo.setArriveContractOrgCode(null);
			panelVo.setArriveContractOrgName(null);
			panelVo.setArriveReminderOrgCode(null);
		} else {
			
				//从CRM那维护的客户资料中获取的时候统一结算为【是】的时候；
				//【是否统一结算】设置为【是】，【合同部门】设置为查询出来的对应的部门
				if (WaybillConstants.YES.equals(cusBargainEntity.getAsyntakegoodsCode())) {
					panelVo.setArriveCentralizedSettlement(WaybillConstants.IS_NOT_NULL_FOR_AI);
					panelVo.setArriveContractOrgCode(cusBargainEntity.getUnifiedCode());
					panelVo.setArriveContractOrgName(queryContractOrgName(cusBargainEntity.getUnifiedCode()));
					panelVo.setArriveReminderOrgCode(cusBargainEntity.getHastenfunddeptCode());
				} else {
					//从CRM那维护的客户资料中获取的时候统一结算为【是】的时候；
					//【是否统一结算】设置为【否】，【合同部门】设置为【null】
					panelVo.setArriveCentralizedSettlement(WaybillConstants.IS_NULL_FOR_AI);
					panelVo.setArriveContractOrgCode(null);
					panelVo.setArriveContractOrgName(null);
					panelVo.setArriveReminderOrgCode(null);
				}
			
		}
		//wutao == end 
	}
	
	/**
	 * @author 200945 - wutao
	 * @param customerCode
	 * @param date
	 * @param code
	 * @param volume
	 * @return
	 */
	public static BigDecimal validateWeightBubbleRate(String customerCode,Date date,String code,BigDecimal volume,String originalOrgCode){
		BigDecimal volumeWeight = null;
		//先查詢折扣信息
		PreferentialInfoDto entity = baseDataService.queryPreferentialInfoForBubbleRate(
				customerCode, date, code);
		//查詢關聯的部門信息，判斷是否可以用重泡比
		if (null != entity) {
			List<String> unifiedCodeList=new ArrayList<String>();
			String unCode=baseDataService.queryUnifiedCodeByCode(originalOrgCode);
			if(unCode!=null){
				unifiedCodeList.add(unCode);
			}
			List<BargainAppOrgEntity> bargainAppOrgEntities = baseDataService
					.queryAppOrgByBargainCrmId(entity.getCusBargainId(),unifiedCodeList);
			boolean isDiscount = false;
			if (CollectionUtils.isNotEmpty(bargainAppOrgEntities)) {
				for (int i = 0; i < bargainAppOrgEntities.size(); i++) {
					BargainAppOrgEntity bargainAppOrgEntity = bargainAppOrgEntities
							.get(i);
					String unifiedCode = bargainAppOrgEntity.getUnifiedCode();
					OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = baseDataService
							.queryOrgAdministrativeInfoByUnifiedCodeClean(unifiedCode);
					if (orgAdministrativeInfoEntity == null) {
						continue;
					}
					String orgCode = orgAdministrativeInfoEntity.getCode();
					if (StringUtils.equals(originalOrgCode, orgCode)) {
						isDiscount = true;
					}
				}
			}
			
			//如果是關聯的營業部那麼就按照新的邏輯重新計算体积 ---> 体积重量 公式
			if(isDiscount){
				if (entity.getWeightBubbleRate() != null) {
					volumeWeight = (volume.multiply(new BigDecimal(NumberConstants.NUMBER_1000)))
							.divide((entity.getWeightBubbleRate()
									.divide(new BigDecimal(NumberConstants.NUMBER_1000))),NumberConstants.NUMBER_3,BigDecimal.ROUND_HALF_UP);
				} else {
					volumeWeight = volume.multiply(
							FossConstants.VOLUME_TO_WEIGHT).setScale(THREE,
							BigDecimal.ROUND_HALF_UP);
				}
			}else{ //否则按照之前的进行计算
				volumeWeight = volume.multiply(
						FossConstants.VOLUME_TO_WEIGHT).setScale(THREE,
						BigDecimal.ROUND_HALF_UP);
			}
		}else{
			volumeWeight = volume.multiply(
					FossConstants.VOLUME_TO_WEIGHT).setScale(THREE,
					BigDecimal.ROUND_HALF_UP);
		}
		return volumeWeight;
	}
	
	
	
}
