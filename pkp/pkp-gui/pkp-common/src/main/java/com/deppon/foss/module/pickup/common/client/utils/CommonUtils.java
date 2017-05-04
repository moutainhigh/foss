/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/utils/CommonUtils.java
 * 
 * FILE NAME        	: CommonUtils.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-common
 * PACKAGE NAME: com.deppon.foss.module.pickup.common.client.utils
 * FILE    NAME: CommonUtils.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.common.client.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.math.BigDecimal;
import java.util.*;

import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JComboBox;

import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.commons.util.ImageUtil;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.widget.msgbox.MsgBox;
import com.deppon.foss.framework.client.widget.validatewidget.JTextFieldValidate;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesProductEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
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
import com.deppon.foss.module.pickup.common.client.service.IWaybillInfoService;
import com.deppon.foss.module.pickup.common.client.service.impl.AddressService;
import com.deppon.foss.module.pickup.common.client.service.impl.BaseDataService;
import com.deppon.foss.module.pickup.common.client.service.impl.CustomerService;
import com.deppon.foss.module.pickup.common.client.service.impl.DataDictionaryValueService;
import com.deppon.foss.module.pickup.common.client.service.impl.OrgInfoService;
import com.deppon.foss.module.pickup.common.client.service.impl.OuterBranchService;
import com.deppon.foss.module.pickup.common.client.service.impl.SalesDepartmentService;
import com.deppon.foss.module.pickup.common.client.service.impl.UserService;
import com.deppon.foss.module.pickup.common.client.service.impl.WaybillInfoService;
import com.deppon.foss.module.pickup.common.client.vo.DataDictionaryValueVo;
import com.deppon.foss.module.pickup.common.client.vo.DeliverChargeEntity;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.QueryMemberDialogVo;
import com.deppon.foss.module.pickup.common.client.vo.ValidateVo;
import com.deppon.foss.module.pickup.common.client.vo.ValueCopy;
import com.deppon.foss.module.pickup.common.client.vo.WaybillDiscountVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.InempDiscountPlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PriceDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.waybill.shared.define.GisConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillCHDtlPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmReceiveMethodEnum;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmTransportTypeEnum;
import com.deppon.foss.module.pickup.waybill.shared.dto.SalesDepartmentCityDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Injector;


/**
 * 提货给运单、更改单模块公用的工具方法
 * @author 026123-foss-lifengteng
 * @date 2013-1-26 下午3:38:41
 */
public class CommonUtils {
	
	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(CommonUtils.class); 
	
	/**
	 * 运单更改单背景颜色
	 */
	public final static int COLOR_RED = 199;
	public final static int COLOR_GREEN = 237;
	public final static int COLOR_BLUE = 204;
//	private static final int SIX = 6;

	private static final int NUM_300000 = 300000;

	private static final int TEN = 10;

	private static final int FIVE = 5;
	
	// 客户服务接口
	private static ICustomerService customerService = GuiceContextFactroy.getInjector().getInstance(CustomerService.class);
	/**
	 * 运单基础数据服务
	 */
	private static IBaseDataService baseDataService = GuiceContextFactroy.getInjector().getInstance(BaseDataService.class);


	/**
	 * author306486
	 * 通过动态代理获取对象
	 */
	private static IWaybillHessianRemoting waybillHessianRemoting =DefaultRemoteServiceFactory.getService(IWaybillHessianRemoting.class);


	/**
	 * 运单信息service
	 */
	private static IWaybillInfoService waybillInfoService = GuiceContextFactroy.getInjector().getInstance(WaybillInfoService.class);
	private static IDataDictionaryValueService dataDictionaryValueService = GuiceContextFactroy.getInjector().getInstance(DataDictionaryValueService.class);
	
	/**
	 * 快递产品类型
	 */
	public static List<ProductEntity> expressProductList = null;
	
	/**
	 * 零担汽运产品
	 */
	public static List<ProductEntity> transVcehicleList = null;
	
	/**
	 * 零担空运产品
	 */
	public static List<ProductEntity> transAirCraftList = null;
	
	public static List<String> expressProductCodeList = null;
	
	public static List<String> transVehicleProductCodeList = null;
	
	public static List<String> airFreightProductCodeList = null;
	
	static{
		//快递产品类型
		expressProductList = baseDataService.getAllProductInfoByLevels1(WaybillConstants.TYPE_OF_EXPRESS);
		if(null != expressProductList && expressProductList.size() >= 0){
			expressProductCodeList = new ArrayList<String>();
			for(ProductEntity productEntity : expressProductList){
				expressProductCodeList.add(productEntity.getCode());
			}
		}
		//汽运产品类型
		transVcehicleList = baseDataService.getAllProductInfoByLevels1(WaybillConstants.TRANS_VEHICLE);
		if(null != transVcehicleList && transVcehicleList.size() >= 0){
			transVehicleProductCodeList = new ArrayList<String>();
			for(ProductEntity productEntity : transVcehicleList){
				transVehicleProductCodeList.add(productEntity.getCode());
			}
		}
		//空运产品类型
		transAirCraftList = baseDataService.getAllProductInfoByLevels1(WaybillConstants.AIR_FREIGHT);
		if(null != transAirCraftList && transAirCraftList.size() >= 0){
			airFreightProductCodeList = new ArrayList<String>();
			for(ProductEntity productEntity : transAirCraftList){
				airFreightProductCodeList.add(productEntity.getCode());
			}
		}
	}
	/**
	 * 把三级产品类型转换成始发和到达线路的运输类型(汽运，空运) : 1、精准卡航、 精准城运 精准汽运（长途）、 精准汽运（短途）、 汽运偏线 =>
	 * 的汽运 2、精准空运 => 的空运
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-27 下午7:23:24
	 */
	public static String convertProductCodeToTransType(String productCode) {
		setBaseProductInfo();
		// 根据三级产品判断是否是汽运
		if (transVehicleProductCodeList != null && transVehicleProductCodeList.size() > 0 && transVehicleProductCodeList.contains(productCode)) {
			return DictionaryValueConstants.BSE_LINE_TRANSTYPE_QIYUN;
		}
		// 根据三级产品判断是否是空运
		else if (airFreightProductCodeList != null && airFreightProductCodeList.size() > 0 && airFreightProductCodeList.contains(productCode)) {
			return DictionaryValueConstants.BSE_LINE_TRANSTYPE_KONGYUN;
		}
		// 根据三级产品判断是否是快递
		else if (expressProductCodeList != null && expressProductCodeList.size() > 0 && expressProductCodeList.contains(productCode)) {
			return ProductEntityConstants.PRICING_PRODUCT_C1_C20003;
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
		setBaseProductInfo();
		// 根据三级产品判断是否是汽运
		if (transVehicleProductCodeList != null && transVehicleProductCodeList.size() > 0 && transVehicleProductCodeList.contains(productCode)) {
			return GisConstants.GIS_TRANS_HIGHWAYS;
		}
		// 根据三级产品判断是否是空运
		if (airFreightProductCodeList != null && airFreightProductCodeList.size() > 0 && airFreightProductCodeList.contains(productCode)) {
			return GisConstants.GIS_TRANS_AIR;
		}
		return null;
	}
	
	/**
	 * 设置产品类型是否为空
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-4 09:44:20
	 */
	private static void setBaseProductInfo() {
		if(null == expressProductCodeList || expressProductCodeList.size() <= 0){
			if(null == expressProductList || expressProductList.size() <= 0){
				//快递产品类型
				expressProductList = baseDataService.getAllProductInfoByLevels1(WaybillConstants.TYPE_OF_EXPRESS);
				if(null != expressProductList && expressProductList.size() > 0){
					expressProductCodeList = new ArrayList<String>();
					for(ProductEntity productEntity : expressProductList){
						expressProductCodeList.add(productEntity.getCode());
					}
				}
			}else{
				for(ProductEntity productEntity : expressProductList){
					expressProductCodeList.add(productEntity.getCode());
				}
			}
		}
		if(null == transVehicleProductCodeList || transVehicleProductCodeList.size() <= 0){
			if(null == transVcehicleList || transVcehicleList.size() <= 0){
				//快递产品类型
				transVcehicleList = baseDataService.getAllProductInfoByLevels1(WaybillConstants.TYPE_OF_EXPRESS);
				if(null != transVcehicleList && transVcehicleList.size() > 0){
					transVehicleProductCodeList = new ArrayList<String>();
					for(ProductEntity productEntity : transVcehicleList){
						transVehicleProductCodeList.add(productEntity.getCode());
					}
				}
			}else{
				if(transVehicleProductCodeList == null){
					transVehicleProductCodeList = new ArrayList<String>();
				}
				for(ProductEntity productEntity : transVcehicleList){
					transVehicleProductCodeList.add(productEntity.getCode());
				}
			}
		}
		if(null == airFreightProductCodeList || airFreightProductCodeList.size() <= 0){
			if(null == transAirCraftList || transAirCraftList.size() <= 0){
				//快递产品类型
				transAirCraftList = baseDataService.getAllProductInfoByLevels1(WaybillConstants.TYPE_OF_EXPRESS);
				if(null != transAirCraftList && transAirCraftList.size() > 0){
					airFreightProductCodeList = new ArrayList<String>();
					for(ProductEntity productEntity : transAirCraftList){
						airFreightProductCodeList.add(productEntity.getCode());
					}
				}
			}else{
				if(airFreightProductCodeList == null){
					airFreightProductCodeList = new ArrayList<String>();
				}
				for(ProductEntity productEntity : transAirCraftList){
					airFreightProductCodeList.add(productEntity.getCode());
				}
			}
		}
	}

	/**
	 * 判断是否为精准汽运
	 * @author 026123-foss-lifengteng
	 * @date 2013-6-24 下午4:38:26
	 */
	public static boolean isTransFreight(String productCode){
		String code = StringUtil.defaultIfNull(productCode);
		// 根据三级产品判断是否是精准汽运
		if (StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT, code)
				|| StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT, code)
				|| StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT, code)
				|| StringUtil.equals(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT, code)){
			return true;
		}else{
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
		if (rm.equals(WaybillConstants.SELF_PICKUP) 
				|| rm.equals(WaybillConstants.INNER_PICKUP) 
				|| rm.equals(WaybillConstants.AIR_SELF_PICKUP) 
				|| rm.equals(WaybillConstants.AIR_PICKUP_FREE) 
				|| rm.equals(WaybillConstants.AIRPORT_PICKUP)) {
			return GisConstants.GIS_MATCH_PICKUP;
		}
		//update by huangwei 262036  新增提货方式的判断条件
		// 根据提货方式判断是不是送货
		return getReceiveMethod(rm);

//		return null;
	}

	private static String getReceiveMethod(String rm) {
		if (rm.equals(WaybillConstants.DELIVER_FREE) 
				|| rm.equals(WaybillConstants.DELIVER_NOUP) 
				|| rm.equals(WaybillConstants.DELIVER_STORAGE) 
				|| rm.equals(WaybillConstants.DELIVER_UP)
				|| rm.equals(WaybillConstants.DELIVER_FREE_AIR)
				|| rm.equals(WaybillConstants.DELIVER_NOUP_AIR)
				|| rm.equals(WaybillConstants.DELIVER_UP_AIR)
				|| rm.equals(WaybillConstants.DELIVER_INGA_AIR)
				//POP-163开单经常出现网点参数【提货方式】不允许为空的提示，可实际上网点和提货方式都有填
				|| rm.equals(WaybillConstants.LARGE_DELIVER_UP)
				|| rm.equals(WaybillConstants.LARGE_DELIVER_UP_AIR)
				|| rm.equals(WaybillConstants.SEND_UPSTAIRS_EQUIP)
				|| rm.equals(WaybillConstants.SEND_AND_EQUIP)
				|| rm.equals(WaybillConstants.SEND_NO_UPSTAIRS)) {
			return GisConstants.GIS_MATCH_DELIVER;
		}
		return null ;
	}
	
	/**
	 * 根据提货方式判断：是否送货上门 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-26 下午3:32:48
	 */
	public static boolean verdictPickUpDoor(String pickupType) {
		String type = StringUtil.defaultIfNull(pickupType);
		/**
		 * DEFECT-6897，根据业务要求增加判断条件，其它不变
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-01-19下午18:07
		 */
		if (WaybillConstants.DELIVER_FREE.equals(type) 
				|| WaybillConstants.DELIVER_STORAGE.equals(type) 
				|| WaybillConstants.DELIVER_UP.equals(type)
				|| WaybillConstants.DELIVER_NOUP.equals(type) 
				|| WaybillConstants.DELIVER_FREE_AIR.equals(type)
				|| WaybillConstants.DELIVER_NOUP_AIR.equals(type) 
				|| WaybillConstants.DELIVER_UP_AIR.equals(type)
				|| WaybillConstants.DELIVER_INGA_AIR.equals(type)
				|| WaybillConstants.LARGE_DELIVER_UP.equals(type)
				|| WaybillConstants.LARGE_DELIVER_UP_AIR.equals(type)
				|| WaybillConstants.SEND_UPSTAIRS_EQUIP.equals(type)
				|| WaybillConstants.SEND_AND_EQUIP.equals(type)
				|| WaybillConstants.SEND_NO_UPSTAIRS.equals(type)) 
		{
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 根据提货方式判断是否是特殊增值服务
	 * @author 026123-foss-lifengteng
	 * @date 2015-10-27 下午3:32:48
	 */
	public static boolean validateIsHome(String receiveMethod) {
		String type = StringUtil.defaultIfNull(receiveMethod);
		if (WaybillConstants.SEND_UPSTAIRS_EQUIP.equals(type) 
				|| WaybillConstants.SEND_AND_EQUIP.equals(type) 
				|| WaybillConstants.SEND_NO_UPSTAIRS.equals(type))
		{
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 根据提货方式判断：是否自提
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-26 下午3:52:17
	 */
	public static boolean verdictPickUpSelf(String pickupType){
		String type = StringUtil.defaultIfNull(pickupType);
		if (WaybillConstants.SELF_PICKUP.equals(type)
				|| WaybillConstants.INNER_PICKUP.equals(type)
				|| WaybillConstants.AIR_PICKUP_FREE.equals(type)
				|| WaybillConstants.AIRPORT_PICKUP.equals(type)
				|| WaybillConstants.AIR_SELF_PICKUP.equals(type)) 
		{
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 根据常量值获得常量名
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-28 下午3:21:50
	 */
	public static String getConstantsName(String value){
		if(WaybillConstants.CRM_ORDER_WAIT_ACCEPT.equals(value)){
			return i18n.get("foss.gui.common.commonUtils.constantsName.waitAccept");
		}else if(WaybillConstants.CRM_ORDER_ACCEPT.equals(value)){
			return i18n.get("foss.gui.common.commonUtils.constantsName.accepted");
		}else if(WaybillConstants.CRM_ORDER_WAYBILL.equals(value)){
			return i18n.get("foss.gui.common.commonUtils.constantsName.waybillOrdered");
		}else if(WaybillConstants.CRM_ORDER_CHANNEL_ONLINE.equals(value)){
			return i18n.get("foss.gui.common.commonUtils.constantsName.channelOnline");
		}else if(WaybillConstants.CRM_ORDER_PAYMENT_ONLINE.equals(value)){
			return i18n.get("foss.gui.common.commonUtils.constantsName.paymentOnline");
		}else{
			return value;
		}
	}
	
	/**
	 * 根据产品来判断是查询自有网点还是代理网点
	 * @author 026123-foss-lifengteng
	 * @date 2013-2-1 下午3:33:17
	 */
	public static boolean isAgentDept(String productCode) {
		if (!ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(productCode)
				&& !ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productCode)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 
	 * 数据转换 将Y或N转换成中文是和否
	 * @author 025000-FOSS-helong
	 * @date 2013-2-19 上午09:14:06
	 * @param data
	 * @return
	 */
	public static String dataTransform(String data)
	{
		if(FossConstants.YES.equals(data))
		{
			return i18n.get("foss.gui.common.waybillEditUI.common.yes");
		}else
		{
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
		/*if (contactList == null || contactList.size() == 0) {
			return null;
		}

		List<QueryMemberDialogVo> memberList = new ArrayList<QueryMemberDialogVo>();
		for (CustomerQueryConditionDto contact : contactList) {
			QueryMemberDialogVo dialogVo = new QueryMemberDialogVo();
			// 客户ID
			dialogVo.setCustId(StringUtil.defaultIfNull(contact.getCustId()));
			// 客户编码
			dialogVo.setCustomerCode(StringUtil.defaultIfNull(contact.getCustCode()));
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
			// 地址
			dialogVo.setCustAddrRemark(StringUtil.defaultIfNull(contact.getCustAddrRemark()));
			// 信用额度
			dialogVo.setCreditAmount(contact.getCreditAmount() == null ? BigDecimal.valueOf(0) : contact.getCreditAmount());
			// 结算方式
			dialogVo.setChargeMode(DictionaryValueConstants.CLEARING_TYPE_MONTH.equals(contact.getChargeType()) ? true : false);
			// 合同编号
			dialogVo.setAuditNo(contact.getBargainCode());
			// 生效日期
			dialogVo.setValidTime(contact.getBeginTime());
			// 失效日期
			dialogVo.setInvalidTime(contact.getEndTime());
			//　客户类型
			dialogVo.setCustomerType(contact.getCustomerType());
			// 区域编码
			dialogVo.setCountyCode(StringUtil.defaultIfNull(contact.getCountyCode()));
			// 城市编码
			dialogVo.setCityCode(StringUtil.defaultIfNull(contact.getCityCode()));
			// 省份编码
			dialogVo.setProvinceCode(StringUtil.defaultIfNull(contact.getProvinceCode()));
			// 接送货地址ID
			dialogVo.setAddressId(StringUtil.defaultIfNull(contact.getAddressId()));
			//优惠类型
			dialogVo.setPreferentialType(StringUtil.defaultIfNull(contact.getPreferentialType()));
			//所属部门
			dialogVo.setDeptName(StringUtil.defaultIfNull(contact.getDeptName()));
			*//**
			 * 根据DMANA-10888发票标记不再从这里获取
			 * @author:218371-foss-zhaoyanjun
			 * @date:2015-01-06上午10:26
			 *//*
			//发票标记
//			if(WaybillConstants.INVOICE_01.equals(contact.getInvoiceType())){
//				dialogVo.setInvoice(WaybillConstants.INVOICE_01);
//			}else{
//				dialogVo.setInvoice(WaybillConstants.INVOICE_02);
//			}
			//大客户标记
			dialogVo.setIsBigCustomer(StringUtil.defaultIfNull(contact.getIsLargeCustomers()));
			//客户地址备注信息
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
			dialogVo.setCustAddrRemark(StringUtil.defaultIfNull(contact.getCustAddrRemark()));
			String flabelleavemonth = contact.getFlabelleavemonth();
			if(StringUtil.isEmpty(flabelleavemonth)){
				//如果为空默认新客户分群
				flabelleavemonth="NEWCUST";
			}

			
			dialogVo.setFlabelleavemonth(flabelleavemonth);
			//是否電商尊享
			dialogVo.setIsElectricityToEnjoy(StringUtil.defaultIfNull(contact.getIfElecEnjoy()));
			memberList.add(dialogVo);
		}*/
		return convertToMemberVo(contactList, null);
	}
	
	
	/**
	 * 将CustomerQueryConditionDto对象转换成QueryMemberDialogVo对象
	 * @autor Foss-278328-hujinyang
	 * @time 2016-4-23
	 * @param contactList 要转换的集合
	 * @param type		    是发货客户还是接受客户  空位发货客户，非空问接受客户   该类型防止发票标记被改变
	 * @return
	 */
	public static List<QueryMemberDialogVo> convertToMemberVo(List<CustomerQueryConditionDto> contactList, String type) {
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
			// 弹窗显示地址
			dialogVo.setAddressInfo(StringUtil.defaultIfNull(contact.getAddressInfo()));
			// 地址
			dialogVo.setCustAddrRemark(StringUtil.defaultIfNull(contact.getCustAddrRemark()));
			// 信用额度
			dialogVo.setCreditAmount(contact.getCreditAmount() == null ? BigDecimal.valueOf(0) : contact.getCreditAmount());
			// 结算方式
			dialogVo.setChargeMode(DictionaryValueConstants.CLEARING_TYPE_MONTH.equals(contact.getChargeType()) ? true : false);
			// 合同编号
			dialogVo.setAuditNo(contact.getBargainCode());
			// 生效日期
			dialogVo.setValidTime(contact.getBeginTime());
			// 失效日期
			dialogVo.setInvalidTime(contact.getEndTime());
			//　客户类型
			dialogVo.setCustomerType(contact.getCustomerType());
			// 区域编码
			dialogVo.setCountyCode(StringUtil.defaultIfNull(contact.getCountyCode()));
			// 城市编码
			dialogVo.setCityCode(StringUtil.defaultIfNull(contact.getCityCode()));
			// 省份编码
			dialogVo.setProvinceCode(StringUtil.defaultIfNull(contact.getProvinceCode()));
			// 接送货地址ID
			dialogVo.setAddressId(StringUtil.defaultIfNull(contact.getAddressId()));
			//优惠类型
			dialogVo.setPreferentialType(StringUtil.defaultIfNull(contact.getPreferentialType()));
			//所属部门
			dialogVo.setDeptName(StringUtil.defaultIfNull(contact.getDeptName()));
			/**
			 * 根据DMANA-10888发票标记不再从这里获取
			 * @author:218371-foss-zhaoyanjun
			 * @date:2015-01-06上午10:26
			 * 
			 * 修改发票标记的转换
			 * @author Foss-278328-hujinyang
			 * @date: 2016-04-23
			 */
			
			if(type == null){//发货客户信息的发票标记设置  收货客户的发票信息依赖于发货客户的发票信息，所以转换时候不设置
				String customerCode = dialogVo.getCustomerCode();
				dialogVo.setInvoice(CommonUtils.setInvoice(customerCode));
			}
			
			//大客户标记
			dialogVo.setIsBigCustomer(StringUtil.defaultIfNull(contact.getIsLargeCustomers()));
			//客户地址备注信息
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
			dialogVo.setCustAddrRemark(StringUtil.defaultIfNull(contact.getCustAddrRemark()));
			String flabelleavemonth = contact.getFlabelleavemonth();
			if(StringUtil.isEmpty(flabelleavemonth)){
				//如果为空默认新客户分群
				flabelleavemonth="NEWCUST";
			}

			
			dialogVo.setFlabelleavemonth(flabelleavemonth);
			//是否電商尊享
			dialogVo.setIsElectricityToEnjoy(StringUtil.defaultIfNull(contact.getIfElecEnjoy()));
			//是否精准包裹
			dialogVo.setIsAccuratePackage(StringUtil.defaultIfNull(contact.getIsAccuratePackage()));
			memberList.add(dialogVo);
		}
		return memberList;
	}
	

	/**
	 * 业务逻辑：
	 * 因为所需要【是否统一结算】【合同部门标杆编码】【催款部门编码】
	 */
	public static CusBargainEntity queryCusBargainByCustCode(CustomerQueryConditionDto contact){
		CusBargainEntity cusBargainEntity = customerService.queryCusBargainByCustCode(contact.getCustCode());
		if(cusBargainEntity ==null){
			return null;
		}else{
			return cusBargainEntity;
		}
	}
	/**
	 * 判断
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
	public  static void add(PriceDiscountDto dto, List<WaybillDiscountVo> data , WaybillPanelVo bean) {
		//定价体系优化项目---修复bug-pop-263 更改单优惠金额合计会再次优惠
		boolean islive = false;
		for (int i = 0; i < data.size(); i++) {
			//当已经存在相同的费用类型折扣时，则退出循环
			//定价体系优化项目---修复bug-POP-351 333  319 更改单优惠金额减少
			if(StringUtil.equals(dto.getChargeDetailId(), data.get(i).getChargeDetailId())
					&&StringUtil.equals(dto.getDiscountId(), data.get(i).getDiscountId())){
				if(PriceEntityConstants.PRICING_CODE_QT.equals(dto.getPriceEntryCode()) 
						|| PriceEntityConstants.PRICING_CODE_BZ.equals(dto.getPriceEntryCode())){
					if(StringUtil.isEmpty(dto.getSubType())){
						if(StringUtil.equals(dto.getPriceEntryCode(), data.get(i).getFavorableItemCode())){
							islive = true;
							break;
						}
					}else{
						if(StringUtil.equals(dto.getSubType(),data.get(i).getFavorableItemSubTypeCode())){
							islive = true;
							break;
						}
					}
				}else{
					if(StringUtil.equals(dto.getPriceEntryCode(), data.get(i).getFavorableItemCode())){
						islive = true;
						break;
					}
				}
				
				
			}
		}
		if(!islive){
			WaybillDiscountVo vo = new WaybillDiscountVo();
			// 折扣ID
			vo.setDiscountId(dto.getDiscountId());
			// 费用类型id
			vo.setChargeDetailId(dto.getChargeDetailId());
			// 优惠折扣项目
			vo.setFavorableItemName(dto.getPriceEntryName());
			// 优惠项目CODE
			vo.setFavorableItemCode(dto.getPriceEntryCode());
			//优惠项目子类型code
			vo.setFavorableItemSubTypeCode(dto.getSubType());
			
			//如果是其他费用，则显示子类型-----定价体系优化项目POP-475
			if(PriceEntityConstants.PRICING_CODE_QT.equals(dto.getPriceEntryCode())
					|| PriceEntityConstants.PRICING_CODE_BZ.equals(dto.getPriceEntryCode())){
				if(StringUtil.isNotEmpty(dto.getSubType())){
					// 优惠折扣项目
					vo.setFavorableItemName(dto.getSubTypeName());
					// 优惠项目CODE
					vo.setFavorableItemCode(dto.getSubType());
				}else{
					// 优惠折扣项目
					vo.setFavorableItemName(dto.getPriceEntryName());
					// 优惠项目CODE
					vo.setFavorableItemCode(dto.getPriceEntryCode());
				}
			}else{
				// 优惠折扣项目
				vo.setFavorableItemName(dto.getPriceEntryName());
				// 优惠项目CODE
				vo.setFavorableItemCode(dto.getPriceEntryCode());
			}
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
				vo.setFavorableDiscount(i18n.get("foss.gui.creating.calculateAction.msgBox.nullFavorableDiscount"));
			}
			// 优惠折扣金额
			if (dto.getReduceFee() != null) {
				//优惠金额
				vo.setFavorableAmount(dto.getReduceFee().toString());			
			} else {
				vo.setFavorableAmount(i18n.get("foss.gui.creating.calculateAction.msgBox.nullFavorableAmount"));
			}
			//原始费用
			if(dto.getOriginnalCost()!=null){
				vo.setFavorableBeforeAmount(dto.getOriginnalCost().toString());
			}
			data.add(vo);
		}

	}
	

	
	/**
	 * 根据运单状态判断是否为已开单
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-16 下午12:50:11
	 */
	public static boolean isHandleActive(String type){
		//是否为运单已开单
		if(WaybillConstants.WAYBILL_STATUS_PC_ACTIVE.equals(type) || WaybillConstants.WAYBILL_STATUS_PENDING_ACTIVE.equals(type)
				|| WaybillConstants.WAYBILL_STATUS_RFC_ACTIVE.equals(type) || WaybillConstants.WAYBILL_STATUS_RFC_PENDING.equals(type)){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 
	 * 获取省份编码
	 * @param orgCode
	 * @return
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-27 下午4:36:48
	 */
	public static String getReceiveOrgProvCode(String orgCode) {
		Injector injector = GuiceContextFactroy.getInjector();
		IOrgInfoService orgInfoService = injector.getInstance(OrgInfoService.class);
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgInfoService.queryByCode(orgCode);
		if(null != orgAdministrativeInfoEntity){
			return orgAdministrativeInfoEntity.getProvCode();
		}else{
			return "";
		}
		
	}
	
	/**
	 * 若为null，则设置默认值
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-2 下午4:34:48
	 */
	public static BigDecimal defaultIfNull(BigDecimal num){
		//若对象为空，则返回0值
		if(null == num){
			//返回0值 
			return BigDecimal.valueOf(0);
		}
		/**
		 * 返回原值
		 * 不能四舍五入，否则体积为0.01的，在导入或补录时就变成0了
		 */
		//return num.setScale(2, BigDecimal.ROUND_HALF_UP);
		return num;
	}
	
	/**
	 * 将空字符串转换为数字
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-2 下午4:34:48
	 */
	public static int strToNum(String num){
		//若对象为空，则返回0值
		if(null == num || "".equals(num.trim())){
			return Integer.parseInt("0");
		}
		return Integer.parseInt(num);
	}
	
	/**
	 * 若为null，则设置默认值
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-2 下午4:34:48
	 */
	public static Integer defaultIfNull(Integer num){
		//若对象为空，则返回0值
		if(null == num){
			//返回0值 
			return Integer.valueOf(0);
		}
		return num;
	}
	
	/**
	 * 若为null，则设置默认值
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-2 下午4:34:48
	 */
	public static Long defaultIfNull(Long num){
		//若对象为空，则返回0值
		if(null == num){
			//返回0值 
			return Long.valueOf(0);
		}
		return num;
	}
	
	/**
	 * 若为null，则设置默认值
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-2 下午4:34:48
	 */
	public static Double defaultIfNull(Double num){
		//若对象为空，则返回0值
		if(null == num){
			//返回0值 
			return Double.valueOf(0);
		}
		return num;
	}
	
	/**
	 * 若为null，则设置默认值
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-2 下午4:34:48
	 */
	public static String defaultIfNull(String str){
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
	public static Boolean defaultIfNull(Boolean num){
		//若对象为空，则返回0值
		if(null == num){
			//返回0值 
			return Boolean.FALSE;
		}
		return num;
	}
	
	/**
	 * 根据部门编码查询自有网点信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-6-24 下午3:28:06
	 */
	public static OrgAdministrativeInfoEntity queryOwerDeptByCode(String code){
		//判断部门编码是否为空
		if(StringUtils.isEmpty(code)){
			return null;
		}
		//获得部门服务接口
		IOrgInfoService orgService = GuiceContextFactroy.getInjector().getInstance(OrgInfoService.class);
		//获得组织对象
		OrgAdministrativeInfoEntity e = orgService.queryByCode(code);
		//判断组织部门是否为空
		if(null == e){
			return null;
		}else{
			return e;
		}
	}
	
	/**
	 * 根据部门编码查询外发网点信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-6-24 下午3:30:43
	 */
	public static OuterBranchEntity queryAgentDeptByCode(String code) {
		// 判断部门编码是否为空
		if (StringUtils.isEmpty(code)) {
			return null;
		}
		//获得代理服务接口
		IOuterBranchService agentService = GuiceContextFactroy.getInjector().getInstance(OuterBranchService.class);
		//查询是否为代理网点
		OuterBranchEntity agent = agentService.queryOuterBranchByCode(code,new Date());
		// 判断组织部门是否为空
		if (null == agent) {
			return null;
		} else {
			return agent;
		}
	}

	/**
	 * 根据部门编码查询部门名称
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-7 下午5:22:53
	 */
	public static String getDeptNameByCode(String code) {
		OrgAdministrativeInfoEntity e = queryOwerDeptByCode(code);
		//对象非空判断
		if (e != null) {
			//返回部门名称
			return e.getName();
		} else {
			OuterBranchEntity agent = queryAgentDeptByCode(code);
			//对象非空判断
			if(null != agent){
				return StringUtil.defaultIfNull(agent.getAgentDeptName());
			}else{
				return "";
			}
		}
	}
	

	/**
	 * 根据城市编码获得城市名称
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-7 下午5:30:56
	 */
	public static String getCityNameByCode(String code) {
		//获得地址对象
		IAddressService addService = GuiceContextFactroy.getInjector().getInstance(AddressService.class);
		//获得地址名称
		String name = addService.queryCityByCode(code);
		//对象非空判断
		if (StringUtil.isNotEmpty(name)) {
			return name;
		} else {
			return i18n.get("foss.gui.common.commonUtils.constantsName.errorCityCode");
		}
	}
	
	/**
	 * 根据组织编码获得城市名 
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

		//return i18n.get("foss.gui.common.commonUtils.constantsName.errorOrgDeptCode");
		return "";
	}
	
	/**
	 * 根据组织编码获得代理部门所属城市名
	 * @author 026123-foss-lifengteng
	 * @date 2013-6-13 上午10:54:23
	 */
	public static String getAgentCityNameByOrgCode(String orgCode){
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
	public static String getDestCityNameByCode(String code){
		String cityName = CommonUtils.getCityNameByOrgCode(code);
		if(StringUtil.isEmpty(cityName)){
			return CommonUtils.getAgentCityNameByOrgCode(code);
		}
		
		return cityName;
	}
	
	
	/**
	 * 根据数据字典的值代码和词条代码，获得值代码对应的名称
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-7 下午6:30:21
	 */
	public static String getNameFromDict(String activeTmp, String termsCode) {
		//从本地获得数据字典接口
		IDataDictionaryValueService dataService = GuiceContextFactroy.getInjector().getInstance(DataDictionaryValueService.class);
		//词条值的名称
		String valueName = "";
		//通过词条代码获取数据集合
		List<DataDictionaryValueEntity> list = dataService.queryByTermsCode(StringUtil.defaultIfNull(termsCode));
		//集合非空判断
		if (CollectionUtils.isNotEmpty(list)) {
			//遍历集合
			for (Iterator<DataDictionaryValueEntity> iterator = list.iterator(); iterator.hasNext();) {
				//获得数据字典对象
				DataDictionaryValueEntity dataDictionaryValueEntity = iterator.next();
				//判断值代码是否一致
				if (dataDictionaryValueEntity.getValueCode().equals(StringUtil.defaultIfNull(activeTmp))) {
					//返回值值代码对应的值名称
					valueName = StringUtil.defaultIfNull(dataDictionaryValueEntity.getValueName());
					return valueName;
				}
			}
		}
		return valueName;
	}
	
	/**
	 * 根据产品类型编码获得产品名称，包括一级产品、二级产品、三级产品
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-7 下午8:39:38
	 */
	//update date2014-09-30 获得门到门 场到场的三级和二级产品
	public static String getNameByProductCode(String code){
		//获得产品编号
		ProductEntity productEntity = baseDataService.getProductByCache(code, new Date());
		return productEntity == null ? code : productEntity.getName();
	}
	
	/**
	 * 根据产品类型、值编码、货物类型编码查询名称
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-22 上午9:53:11
	 */
	public static String getGoodsTypeByCode(String productCode,String valueCode,String goodsTypeCode){
		//若产品类型或货物类型为空，则返回空
		if(StringUtil.isEmpty(productCode) || StringUtil.isEmpty(goodsTypeCode)){
			return "";
		}else{
			String valueName = CommonUtils.getNameFromDict(valueCode, goodsTypeCode);
			//货物类型判断
			if(ProductEntityConstants.PRICING_PRODUCT_C1_C20001.equals(productCode)){
				//汽运
				return valueName;
			}else{
				//空运
				return "H00001".equals(valueCode) ? i18n.get("foss.gui.common.waybillEditUI.common.all") : "" ; 
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
	    		||CrmReceiveMethodEnum.DELIVER_UP.getCrmCode().equals(crmReceiveMethod)) {
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
     * @author 026123-foss-lifengteng
     * @date 2013-6-15 下午3:07:45
     */
	public static void validateOwerGisDept(WaybillPanelVo bean, SaleDepartmentEntity dept) {
		// 传入对象非空
		if (bean == null || dept == null) {
			return;
		}
		
		// 判断营业部是否开业
		if(dept.getOpeningDate()!=null && dept.getOpeningDate().after(new Date())){
			//对不起，您所选网点还没有开始营业！
			throw new WaybillValidateException(i18n.get("foss.gui.common.commonutils.noopeningdate"));	
		}

		// 是否自提
		if (bean.getReceiveMethod()!=null && CommonUtils.verdictPickUpSelf(bean.getReceiveMethod().getValueCode())) {
			// 是否支持自提
			if (!BooleanConvertYesOrNo.stringToBoolean(dept.getPickupSelf())) {
				throw new WaybillValidateException("对不起，您所选网点不正确！\n该网点不支持[自提]！");
			}
		}
			
		// 是否派送
		if (bean.getReceiveMethod()!=null && CommonUtils.verdictPickUpDoor(bean.getReceiveMethod().getValueCode())) {
			// 是否支持送货上门
			if (!validateIsHome(bean.getReceiveMethod().getValueCode()) && !BooleanConvertYesOrNo.stringToBoolean(dept.getDelivery())) {
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
     * @author 026123-foss-lifengteng
     * @date 2013-6-15 下午3:07:49
     */
	public static void validateAgentGisDept(WaybillPanelVo bean, OuterBranchEntity dept) {
		// 传入对象非空
		if (bean == null || dept == null) {
			return;
		}

		// 是否自提
		if (bean.getReceiveMethod()!=null && CommonUtils.verdictPickUpSelf(bean.getReceiveMethod().getValueCode())) {
			// 是否支持自提
			if (!BooleanConvertYesOrNo.stringToBoolean(dept.getPickupSelf())) {
				throw new WaybillValidateException("对不起，您所选网点不正确！\n该网点不支持[自提]");
			}
		}

		// 是否派送
		if (bean.getReceiveMethod()!=null && CommonUtils.verdictPickUpDoor(bean.getReceiveMethod().getValueCode())) {
			// 是否支持送货上门
			if (!BooleanConvertYesOrNo.stringToBoolean(dept.getPickupToDoor())) {
				throw new WaybillValidateException("对不起，您所选网点不正确！\n该网点不支持[派送]" );
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
		if (bean.getProductCode()!=null && ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(bean.getProductCode().getCode())) {
			/**
			 * 单独开单只能选择机场、合大票只能选择代理
			 */
			if (bean.getFreightMethod()!=null) {			
				//对不起，您所选网点不正确！单独开单只能选择机场!
				if(ProductEntityConstants.PRICING_PRODUCT_FREIGNT_DDKD.equals(bean.getFreightMethod().getValueCode())
						&& !BooleanConvertYesOrNo.stringToBoolean(dept.getIsAirport())){
					throw new WaybillValidateException("对不起，您所选网点不正确！\n单独开单只能选择机场!");
				}
				//对不起，您所选网点不正确！合大票只能选择代理!
				else if(ProductEntityConstants.PRICING_PRODUCT_FREIGNT_HDP.equals(bean.getFreightMethod().getValueCode())
						&& BooleanConvertYesOrNo.stringToBoolean(dept.getIsAirport())){
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
	public static  ConfigurationParamsEntity getBaseConfigurationParamsEntity(IConfigurationParamsService configurationParamsService,String type) {
		/**
		 * 根据组织的配置参数查询系统参数实体
		 * 
		 *  组织配置参数-综合模块使用：DictionaryConstants.SYSTEM_CONFIG_PARM__BAS
		 *  
		 */
		return baseDataService.queryConfigurationParamsByEntity(DictionaryConstants.SYSTEM_CONFIG_PARM__BAS, type,FossConstants.ROOT_ORG_CODE);		

	}
	
	
	/**
	 * 
	 * 获取配置参数
	 * @author Foss-278328-hujinyang
	 * @Date 2015-12-15 08:27:03
	 * @param model		模块
	 * @param config	配置项
	 * @param orgCode	机构
	 * @return  配置实体   如果参数有空直接返回null  查不到返回null
	 */
	public static  ConfigurationParamsEntity getConfigurationParamsEntity(String model,String config,String orgCode) {
		
		if(StringUtils.isEmpty(model)||StringUtils.isEmpty(config)||StringUtils.isEmpty(orgCode)){
			return null;
		}
		
		return baseDataService.queryConfigurationParamsByEntity(model, config, orgCode);		

	}
	
	
	
	
	
	/**
	 * 重量校验
	 * @param billWeight
	 * @param bean
	 * @return
	 */
	public static String validateGoodsWeightTotal(BigDecimal goodsWeightTotal, WaybillPanelVo bean) {
		IConfigurationParamsService configurationParamsService = GuiceContextFactroy
				.getInjector().getInstance(ConfigurationParamsService.class);

		BigDecimal maxWeight=new BigDecimal(NUM_300000);
		
		if (bean.getIsWholeVehicle()!=null && bean.getIsWholeVehicle()) {

			/**
			 * 获取最大重量配置参数
			 */
			ConfigurationParamsEntity weight = CommonUtils
					.getBaseConfigurationParamsEntity(
							configurationParamsService,
							ConfigurationParamsConstants.BAS_PARM__MAX_WEIGHT_VEHICLE);
			
			if(weight!=null&&StringUtils.isNotEmpty(weight.getConfValue()))
			 {
				maxWeight= new BigDecimal(weight.getConfValue());
			 }
			
			if(goodsWeightTotal.compareTo(maxWeight)>0)
			{
				return i18n.get("foss.gui.common.commonUtils.constantsName.WholeVehicle.Weight.error")+maxWeight+(weight != null ? weight.getUnit() : "");
			}
		}

		return WaybillConstants.SUCCESS;
	}
	
	
	
	
	
	/**
	 * 体积校验
	 * @param billWeight
	 * @param bean
	 * @return
	 */
	public static String validateGoodsVolumeTotal(BigDecimal goodsVolumeTotal,
			WaybillPanelVo bean) {
		IConfigurationParamsService configurationParamsService = GuiceContextFactroy
				.getInjector().getInstance(ConfigurationParamsService.class);
		BigDecimal maxVolume = new BigDecimal(NUM_300000);

		if (bean.getIsWholeVehicle()!=null && bean.getIsWholeVehicle()) {

			/**
			 * 获取最大重量配置参数
			 */
			ConfigurationParamsEntity volume = CommonUtils
					.getBaseConfigurationParamsEntity(
							configurationParamsService,
							ConfigurationParamsConstants.BAS_PARM__MAX_VOLUME_VEHICLE);

			if (volume != null && StringUtils.isNotEmpty(volume.getConfValue())) {
				maxVolume = new BigDecimal(volume.getConfValue());
			}

			if (goodsVolumeTotal.compareTo(maxVolume) > 0) {
				return i18n.get("foss.gui.common.commonUtils.constantsName.WholeVehicle.Volum.error") + maxVolume+(volume != null ? volume.getUnit() : "");
			}
		}

		return WaybillConstants.SUCCESS;
	}
	
	/**
	 * 体积提示
	 * @param billWeight
	 * @param bean
	 * @return
	 */
	public static String promptGoodsVolumeTotal(
			WaybillPanelVo bean) {
		IConfigurationParamsService configurationParamsService = GuiceContextFactroy
				.getInjector().getInstance(ConfigurationParamsService.class);
		BigDecimal maxVolume = new BigDecimal(NumberConstants.NUMBER_50);

		if (bean.getIsWholeVehicle()) {

			/**
			 * 获取需要提示重量配置参数
			 */
			ConfigurationParamsEntity volume = CommonUtils
					.getBaseConfigurationParamsEntity(
							configurationParamsService,
							ConfigurationParamsConstants.BAS_PARM__MIN_VOLUME_VEHICLE);

			if (volume != null && StringUtils.isNotEmpty(volume.getConfValue())) {
				maxVolume = new BigDecimal(volume.getConfValue());
			}

			if (bean.getGoodsVolumeTotal().compareTo(maxVolume) > 0) {
				return i18n.get("foss.gui.common.commonUtils.constantsName.WholeVehicle.Volum.prompt")+ maxVolume+(volume != null ? volume.getUnit() : "");
			}
		}

		return WaybillConstants.SUCCESS;
	}
	
	/**
	 * 重量提示
	 * @param billWeight
	 * @param bean
	 * @return
	 */
	public static String promptGoodsWeightTotal(
			WaybillPanelVo bean) {
		IConfigurationParamsService configurationParamsService = GuiceContextFactroy
				.getInjector().getInstance(ConfigurationParamsService.class);
		BigDecimal maxWeight = new BigDecimal(NumberConstants.NUMBER_50);

		if (bean.getIsWholeVehicle()) {

			/**
			 * 获取需要提示重量配置参数
			 */
			ConfigurationParamsEntity weight = CommonUtils
					.getBaseConfigurationParamsEntity(
							configurationParamsService,
							ConfigurationParamsConstants.BAS_PARM__MIN_WEIGHT_VEHICLE);

			if (weight != null && StringUtils.isNotEmpty(weight.getConfValue())) {
				maxWeight = new BigDecimal(weight.getConfValue());
			}

			if (bean.getGoodsWeightTotal().compareTo(maxWeight) > 0) {
				return i18n.get("foss.gui.common.commonUtils.constantsName.WholeVehicle.Weight.prompt")+ maxWeight+(weight != null ? weight.getUnit() : "");
			}
		}

		return WaybillConstants.SUCCESS;
	}
	
	/**
	 * 判断是否为离线状态
	 * @author 026123-foss-lifengteng
	 * @date 2013-6-28 上午8:06:46
	 */
	public static boolean isOnline(){
		if("ONLINE_LOGIN".equals(SessionContext.get("user_loginType").toString())){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 校验提货网点是否有效
	 * @author 026123-foss-lifengteng
	 * @date 2013-7-9 上午8:27:34
	 */
	public static void validatePickupOrgCode(ValidateVo bean){
		//部门编码
		String deptCode = StringUtil.defaultIfNull(bean.getCustomerPickupOrgCode());
		//判断是否是外发
		if(CommonUtils.isAgentDept(bean.getProductCode())){			
			//对外发网点进行校验
			CommonUtils.validateAgentGisDeptForNew(bean, CommonUtils.queryAgentDeptByCode(deptCode));
		}else{
			BaseDataService vBaseDataService = BaseDataServiceFactory.getBaseDataService();
			//对自有网点进行校验，只查询营业部基本信息校验，不关联各种名称
			CommonUtils.validateOwerGisDeptForNew(bean, vBaseDataService.querySaleDepartmentNoAttachOnline(deptCode));
			//校验提货网点的到达适用产品
			validateProductAndCode(bean,vBaseDataService);
		}
		
	}	
	
	/**
     * 校验电子地图传过的目的站与运输性质是否符合要求
     * @author WangQianJin
     * @date 2014-06-24
     */
	public static void validateProductAndCode(ValidateVo bean,BaseDataService baseDataService) {
		SalesProductEntity entity=new SalesProductEntity();
		entity.setActive(FossConstants.YES);
		entity.setSalesType(DictionaryValueConstants.ORG_ARRIVE);
		entity.setSalesDeptCode(bean.getCustomerPickupOrgCode());
		entity.setProductCode(bean.getProductCode());
		//根据部门编码与运输性质获取信息数量
		int count=baseDataService.queryCountByCodeAndProduct(entity);		
		if (count==0) {
			throw new WaybillValidateException("对不起，您所选网点不正确！\n该网点不支持您选择的运输性质！");
		}
	}
			
	/**
     * 校验电子地图传过的代理网点部门是否与运单界面相关信息符合（如可到达、可派送等）
     * @author WangQianJin
     * @date 2013-7-10 下午3:07:49
     */
	public static void validateAgentGisDeptForNew(ValidateVo bean, OuterBranchEntity dept) {
		// 传入对象非空
		if (bean == null || dept == null) {
			return;
		}

		// 是否自提
		if (bean.getReceiveMethod()!=null && CommonUtils.verdictPickUpSelf(bean.getReceiveMethod())) {
			// 是否支持自提
			if (!BooleanConvertYesOrNo.stringToBoolean(dept.getPickupSelf())) {
				throw new WaybillValidateException("对不起，您所选网点不正确！\n该网点不支持[自提]");
			}
		}

		// 是否派送
		if (bean.getReceiveMethod()!=null && CommonUtils.verdictPickUpDoor(bean.getReceiveMethod())) {
			// 是否支持送货上门
			if (!BooleanConvertYesOrNo.stringToBoolean(dept.getPickupToDoor())) {
				throw new WaybillValidateException("对不起，您所选网点不正确！\n该网点不支持[派送]" );
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
		if (bean.getProductCode()!=null && ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(bean.getProductCode())
				 && !FossConstants.NO.equals(bean.getIsNeedValCus())) {
			/**
			 * 单独开单只能选择机场、合大票只能选择代理
			 */
			if (bean.getFreightMethod()!=null) {			
				//对不起，您所选网点不正确！单独开单只能选择机场!
				if(ProductEntityConstants.PRICING_PRODUCT_FREIGNT_DDKD.equals(bean.getFreightMethod())
						&& !BooleanConvertYesOrNo.stringToBoolean(dept.getIsAirport())){
					throw new WaybillValidateException("对不起，您所选网点不正确！\n单独开单只能选择机场!");
				}
				//对不起，您所选网点不正确！合大票只能选择代理!
				else if(ProductEntityConstants.PRICING_PRODUCT_FREIGNT_HDP.equals(bean.getFreightMethod())
						&& BooleanConvertYesOrNo.stringToBoolean(dept.getIsAirport())){
					throw new WaybillValidateException("对不起，您所选网点不正确！\n合大票只能选择代理!");
				}
			}
		}
	}
	
	
	/**
     * 校验电子地图传过的自有网点部门是否与运单界面相关信息符合（如可到达、可派送等）
     * @author WangQianJin
     * @date 2013-7-10 下午3:07:45
     */
	public static void validateOwerGisDeptForNew(ValidateVo bean, SaleDepartmentEntity dept) {
		// 传入对象非空
		if (bean == null || dept == null) {
			return;
		}
		
		// 判断营业部是否开业
		if(dept.getOpeningDate()!=null && dept.getOpeningDate().after(new Date()) && !FossConstants.NO.equals(bean.getIsNeedValCus())){
			//对不起，您所选网点还没有开始营业！
			throw new WaybillValidateException(i18n.get("foss.gui.common.commonutils.noopeningdate"));	
		}

		// 是否自提
		if (bean.getReceiveMethod()!=null && CommonUtils.verdictPickUpSelf(bean.getReceiveMethod())) {
			// 是否支持自提
			if (!BooleanConvertYesOrNo.stringToBoolean(dept.getPickupSelf())) {
				throw new WaybillValidateException("对不起，您所选网点不正确！\n该网点不支持[自提]！");
			}
		}
			
		// 是否派送
		if (bean.getReceiveMethod()!=null && CommonUtils.verdictPickUpDoor(bean.getReceiveMethod())) {
			// 是否支持送货上门
			if (!validateIsHome(bean.getReceiveMethod()) && !BooleanConvertYesOrNo.stringToBoolean(dept.getDelivery())){
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
	 * @author 026123-foss-lifengteng
	 * @date 2013-7-11 下午8:29:50
	 */
	public static String getDeptNameByCustCode(String custCode){
		//判断客户编码是否为空
		if(StringUtils.isEmpty(custCode)){
			return "";
		}else{
			//获得本地客户服务方法，再去远程访问服务器端数据
			CustomerService customerService = GuiceContextFactroy.getInjector().getInstance(CustomerService.class);
			//根据客户编码查询客户集合信息
			List<QueryMemberDialogVo> custList = customerService.queryCustInfoByCode(custCode);
			//集合非空判断
			if(CollectionUtils.isNotEmpty(custList)){
				return StringUtil.defaultIfNull(custList.get(0).getDeptName());
			}else{
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
		//判断bean是否为空
		if(null != bean){
			queryDto.setWaybillNo(bean.getWaybillNo());
			// 出发部门CODE
			queryDto.setOriginalOrgCode(bean.getReceiveOrgCode());
			// 到达部门CODE
			if(null != bean.getCustomerPickupOrgCode()){
				queryDto.setDestinationOrgCode(bean.getCustomerPickupOrgCode().getCode());
			}
			// 产品CODE
			if(null != bean.getProductCode()){
				queryDto.setProductCode(bean.getProductCode().getCode());
			}
			//设置CRM渠道
			queryDto.setChannelCode(bean.getOrderChannel());
			// 长途 还是短途
			queryDto.setLongOrShort(bean.getLongOrShort());
			//发货客户
			queryDto.setCustomerCode(bean.getDeliveryCustomerCode());
			queryDto.setBillTime(bean.getBillTime());//开单时间
			//定价体系优化项目POP-548
			queryDto.setWeight(bean.getGoodsWeightTotal());// 重量
			queryDto.setVolume(bean.getGoodsVolumeTotal());// 体积
		}else{
			UserEntity user = (UserEntity) SessionContext.getCurrentUser();
			OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
			// 出发部门CODE
			queryDto.setOriginalOrgCode(dept.getCode());
			// 到达部门CODE
			queryDto.setDestinationOrgCode(null);
			// 产品CODE
			queryDto.setProductCode(null);
			//设置CRM渠道
			queryDto.setChannelCode(null);
			// 长途 还是短途
			queryDto.setLongOrShort(null);
		}
		// 货物类型CODE
		queryDto.setGoodsTypeCode(null);
		queryDto.setReceiveDate(new Date());// 营业部收货日期（可选，无则表示当前日期）
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
	 * 获取其他费用查询参数
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:02:10
	 */
	public static QueryBillCacilateValueAddDto getQueryOtherChargeParamPic(WaybillPanelVo bean) {
		QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
		//判断bean是否为空
		if(null != bean){
			// 出发部门CODE
			queryDto.setOriginalOrgCode(bean.getReceiveOrgCode());
			// 到达部门CODE
			if(null != bean.getCustomerPickupOrgCode()){
				queryDto.setDestinationOrgCode(bean.getCustomerPickupOrgCode().getCode());
			}
			// 产品CODE
			if(null != bean.getProductCode()){
				queryDto.setProductCode(bean.getProductCode().getCode());
			}
			//设置CRM渠道
			queryDto.setChannelCode(bean.getOrderChannel());
			// 长途 还是短途
			queryDto.setLongOrShort(bean.getLongOrShort());
			//发货客户
			queryDto.setCustomerCode(bean.getDeliveryCustomerCode());
			queryDto.setBillTime(bean.getBillTime());//开单时间
			//定价体系优化项目POP-548
			queryDto.setWeight(bean.getGoodsWeightTotal());// 重量
			queryDto.setVolume(bean.getGoodsVolumeTotal());// 体积
		}else{
			UserEntity user = (UserEntity) SessionContext.getCurrentUser();
			OrgAdministrativeInfoEntity dept = user.getEmployee().getDepartment();
			// 出发部门CODE
			queryDto.setOriginalOrgCode(dept.getCode());
			// 到达部门CODE
			queryDto.setDestinationOrgCode(null);
			// 产品CODE
			queryDto.setProductCode(null);
			//设置CRM渠道
			queryDto.setChannelCode(null);
			// 长途 还是短途
			queryDto.setLongOrShort(null);
		}
		// 货物类型CODE
		queryDto.setGoodsTypeCode(null);
		queryDto.setReceiveDate(new Date());// 营业部收货日期（可选，无则表示当前日期）
	
		queryDto.setOriginnalCost(BigDecimal.ZERO);// 原始费用
		queryDto.setFlightShift(null);// 航班号
		queryDto.setSubType(null);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_QT);// 计价条目CODE
		queryDto.setPricingEntryName(null);// 计价名称
		queryDto.setIsPicWayBill(true);//是否图片开单
		return queryDto;
	}
	
	/**
	 * 
	 * 设置送货费
	 * @author 025000-FOSS-helong
	 * @date 2013-3-16 上午08:15:55
	 */
	public static void setDeliverCharge(WaybillPanelVo vo,String code,WaybillCHDtlPendingEntity entity){
		List<DeliverChargeEntity> deliverCharge = vo.getDeliverList();
		if(deliverCharge == null)
		{
			deliverCharge = new ArrayList<DeliverChargeEntity>();
		}
		
		//送货费
		if(PriceEntityConstants.PRICING_CODE_SH.equals(code)
				||PriceEntityConstants.PRICING_CODE_SHSL.equals(code) //添加送货上楼
				||PriceEntityConstants.PRICING_CODE_SHJC.equals(code) //添加送货进仓
				||PriceEntityConstants.PRICING_CODE_CY.equals(code)   //添加超远派送费
				||PriceEntityConstants.PRICING_CODE_DJSL.equals(code) //大件上楼费
			){
			//大件上楼费
			deliverCharge.add(getDeliverCharge(vo,code,entity));
		}
		vo.setDeliverList(deliverCharge);
	}
	
	/**
	 * 
	 * 将查询出来的送货费添加到送货费集合中，并且对送货费进行合计
	 * @author 025000-FOSS-helong
	 * @date 2013-3-16 上午08:36:20
	 */
	public static DeliverChargeEntity getDeliverCharge(WaybillPanelVo vo,String code,WaybillCHDtlPendingEntity entity){
		DeliverChargeEntity deliverChargeEntity = new DeliverChargeEntity();
		BigDecimal deliverFee = vo.getDeliveryGoodsFee();
		if(deliverFee == null)
		{
			deliverFee = BigDecimal.ZERO;
		}
		//是否有效
		deliverChargeEntity.setActive(FossConstants.ACTIVE);
		//金额
		deliverChargeEntity.setAmount(entity.getAmount());
		//币种
		deliverChargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		//费用ID
		deliverChargeEntity.setId(entity.getId());
		//运单号
		deliverChargeEntity.setWaybillNo(vo.getWaybillNo());
		//费用编码
		deliverChargeEntity.setCode(code);
		//送货费(不能对送货费进行累加，只能对非送货费进行累，参见BUG-7877)
//		if(!PriceEntityConstants.PRICING_CODE_SH.equals(code)){
//			deliverFee = deliverFee.add(entity.getAmount());
//		}
		//送货费
		vo.setDeliveryGoodsFee(deliverFee);
		//画布-送货费
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
	 * 
	 * 将查询出的其他费用设置到表格list中
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-16 上午11:00:49
	 */
	public static List<OtherChargeVo> getOtherChargeListPic(List<ValueAddDto> list) {
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
					vo.setMoney(dto.getCaculateFee().divide(new BigDecimal("100")).toString());
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
	 * 
	 * 将查询出的其他费用设置到表格list中
	 * 
	 * @author 025000-FOSS-PGY
	 * @date 2014-01-01上午11:00:49
	 */
	public static OtherChargeVo getOtherChargeListCZFY(List<ValueAddDto> list) {
		OtherChargeVo vo = new OtherChargeVo();

		if (list != null) {
			for (ValueAddDto dto : list) {
				// 开单的时候不能增加更改费
				if (!PriceEntityConstants.QT_CODE_CZHCZFWF.equals(dto.getSubType())) {
					continue;
				}
				vo = new OtherChargeVo();
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
				vo.setIsDelete(false);
				vo.setId(dto.getId());
					 
			}
		}
		return vo;
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
	 * 根据运单号来判断是否为经济快递运单
	 * @author 026123-foss-lifengteng
	 * @date 2013-10-6 上午11:30:19
	 */
	public static boolean isExpressWaybill(String waybillNo){
		//非空
		if(StringUtils.isNotEmpty(waybillNo)){
			//10位数字且首位大于等于5
			if(waybillNo.length() >= TEN && Integer.valueOf(waybillNo.substring(0, 1)) >= FIVE){
				return true;
			}
		}
		
		return false;
	}
	
	
	/**
	 * 设置快递背景色
	 * @author 026123-foss-lifengteng
	 * @date 2013-10-25 下午8:47:09
	 */
	public static Color getExpressColor(){
		return new Color(COLOR_RED,COLOR_GREEN,COLOR_BLUE);
	}
	
	/**
	 * 获取字符串中某个字符的第N次出现的索引
	 * @author WangQianJin
	 * @date 2013-12-14
	 */
	public static int searchStrIndex(String str,String searchStr,int num){
		//若对象为空，则返回0值
		int index=0;
		int count=0;
		if(str!=null && !"".equals(str)){
			for(int i =0 ; i< str.length() ; i++){
			   String temp = str.charAt(i)+"";
			   if(temp.equals(searchStr)){
				   count++;
				   if(count==num){
					   index=i;
					   break;
				   }				   
			   }
			}
		}
		return index;
	}
	
	
	public static void setInvoiceType(WaybillPanelVo bean,Date date){
		
		DataDictionaryValueVo mvo =bean.getInvoiceMode();
		if(null==mvo && bean.getIsWholeVehicle()){
			throw new WaybillValidateException("发票标记不能为空");
		}
		/**
		 * 根据Dmana-10888修改此发票标记获取方法
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-01-20
		 */
		String invoice;
		if(WaybillConstants.WAYBILLINFOVO.equals(bean.getBeanState())&&bean.getDeliveryCustomerCode().equals(bean.getBackupDeliveryCustomerCode())){
			invoice = bean.getBackupInvoice();
			
		}else{
			invoice = setInvoice(bean.getDeliveryCustomerCode());
		}
		bean.setInvoice(invoice);
		if(WaybillConstants.INVOICE_01.equals(invoice)){
			bean.setInvoiceTab(WaybillConstants.INVOICE_TYPE_01);
		}else{
			bean.setInvoiceTab(WaybillConstants.INVOICE_TYPE_02);
		}
		// 判断是否内部带货自提
		if(WaybillConstants.INNER_PICKUP.equals(bean.getReceiveMethod().getValueCode())
				|| WaybillConstants.DELIVER_INNER_PICKUP.equals(bean.getReceiveMethod().getValueCode())){
			//发票标记
			bean.setInvoiceTab(WaybillConstants.INVOICE_TYPE_02);
			bean.setInvoice(WaybillConstants.INVOICE_02);
		}
		if(bean.getIsWholeVehicle()){
			DataDictionaryValueVo vo =bean.getInvoiceMode();
			if(null!=vo){
				bean.setInvoice(vo.getValueCode());
				bean.setInvoiceTab(vo.getValueName());
			}
		}
	}
	
	/**
	 * 
	 * 其他費用累計
	 * @author 025000-FOSS-pgy
	 * @date 2014-01-02下午04:17:58
	 */
	public static void otherSum(List<OtherChargeVo> data,WaybillPanelVo bean)
	{
		BigDecimal otherCharge = BigDecimal.ZERO;
		if (data != null && !data.isEmpty()) {
			for (int i = 0; i < data.size(); i++) {
				OtherChargeVo otherVo = data.get(i);
				otherCharge = otherCharge.add(new BigDecimal(otherVo.getMoney()));
			}
		}
		bean.setOtherFee(otherCharge);
		bean.setOtherFeeCanvas(otherCharge.toString());
	}
	
		/**
	 * 生成图片信息
	 * 配合CRM2期项目需求：大客户标识
	 * 2014-03-12  026123
	 */
	@SuppressWarnings("rawtypes")
	public static Icon createIcon(Class class1, String path,final int offsetX,final int offsetY) {
		final Icon icon = ImageUtil.getImageIcon(class1, path);
		Icon icon2 = new Icon() {
			public void paintIcon(Component c, Graphics g, int x, int y) {
				icon.paintIcon(c, g, x + offsetX, y + offsetY);
			}

			public int getIconWidth() {
				return icon.getIconWidth();
			}

			public int getIconHeight() {
				return icon.getIconHeight();
			}
		};
		return icon2;
	}
	
	/**
	 * 根据费用类型编码获取对应中文名
	 * MANA-1961 营销活动与优惠券编码关联
	 * 2014-04-10 026123
	 */
	public static String convertFeeToName(String feeCode){
		//费用编码
		String code = CommonUtils.defaultIfNull(feeCode);
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
	 * 封装运单部分数据
	 * @author 026123-foss-lifengteng
	 * @date 2014-2-28 下午7:21:05
	 */
	public static WaybillEntity getWaybillEntity(WaybillPanelVo vo) {
		WaybillEntity entity = new WaybillEntity();
		entity.setWaybillNo(vo.getWaybillNo());// 运单号
		entity.setOrderNo(vo.getOrderNo());// 订单号
		entity.setDeliveryCustomerCode(vo.getDeliveryCustomerCode());// 发货客户编码
		entity.setDeliveryCustomerName(vo.getDeliveryCustomerName());// 发货客户名称
		entity.setDeliveryCustomerMobilephone(vo.getDeliveryCustomerMobilephone());// 发货客户手机
		entity.setDeliveryCustomerPhone(vo.getDeliveryCustomerPhone());// 发货客户电话
		entity.setBillTime(vo.getBillTime());//开单时间
		entity.setReceiveOrgCode(vo.getReceiveOrgCode());//收货部门编码
		entity.setReceiveOrgName(vo.getReceiveOrgName());//收货部门名称
		entity.setActive(FossConstants.YES);
		return entity;
	}
	
	/**
	 * 判断要使用优惠券的费用是否符合要求
	 * @创建时间 2014-5-7 上午8:44:26   
	 * @创建人： WangQianJin
	 */
	public static void validateFeeIsZero(BigDecimal fee,String type){
		//费用名称
		String feeName=convertFeeToName(type);
		if(null == fee){			
			//feeName为空，无法使用优惠券！
			throw new WaybillValidateException(feeName+i18n.get("foss.gui.common.commonutils.couponfee.isnull"));	
		}
		if(fee.compareTo(BigDecimal.ZERO)==0){			
			//feeName等于，无法使用优惠券！
			throw new WaybillValidateException(feeName+i18n.get("foss.gui.common.commonutils.couponfee.iszero"));	
		}
		if(fee.compareTo(BigDecimal.ZERO)<0){			
			//feeName小于0，无法使用优惠券！
			throw new WaybillValidateException(feeName+i18n.get("foss.gui.common.commonutils.couponfee.lesszero"));	
		}
	}
	
	/**
	 * 判断要使用优惠券的费用是否符合要求
	 * @创建时间 2014-5-7 上午8:44:26   
	 * @创建人： WangQianJin
	 */
	public static void validateOtherFeeIsZero(List<OtherChargeVo> data,String type){
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
		if(fee == null){
			//feeName为空，无法使用优惠券！
			throw new WaybillValidateException(feeName+i18n.get("foss.gui.common.commonutils.couponfee.isnull"));
		}
		if(fee.compareTo(BigDecimal.ZERO)==0){			
			//feeName等于，无法使用优惠券！
			throw new WaybillValidateException(feeName+i18n.get("foss.gui.common.commonutils.couponfee.iszero"));	
		}
		if(fee.compareTo(BigDecimal.ZERO)<0){			
			//feeName小于0，无法使用优惠券！
			throw new WaybillValidateException(feeName+i18n.get("foss.gui.common.commonutils.couponfee.lesszero"));	
		}
		
	}
	
	/**
	 * 判断要使用优惠券的类型是否符合要求（快递）
	 * @创建时间 2014-5-7 上午8:44:26   
	 * @创建人： WangQianJin
	 */
	public static void validateCouponTypeExpress(String type){
		//费用名称
		String feeName=convertFeeToName(type);
		if(PriceEntityConstants.PRICING_CODE_BF.equals(type) 
				|| PriceEntityConstants.PRICING_CODE_BZ.equals(type)){			
			//您输入的优惠券类型-feeName无法冲减，请手动调整费用！
			throw new WaybillValidateException(i18n.get("foss.gui.common.commonutils.couponfee.validate.type.express",new Object[]{feeName}));	
		}else if(PriceEntityConstants.PRICING_CODE_JH.equals(type) 
				|| PriceEntityConstants.PRICING_CODE_SH.equals(type)			
				|| PriceEntityConstants.PRICING_CODE_ZHXX.equals(type)){
			//您输入的优惠券类型-feeName无法冲减，请选择营销活动！
			throw new WaybillValidateException(i18n.get("foss.gui.common.commonutils.couponfee.validate.type",new Object[]{feeName}));
		}		
	}
	
	/**
	 * 判断要使用优惠券的类型是否符合要求
	 * @创建时间 2014-5-7 上午8:44:26   
	 * @创建人： WangQianJin
	 */
	public static void validateCouponType(String type){
		//费用名称
		String feeName=convertFeeToName(type);
		if(PriceEntityConstants.PRICING_CODE_JH.equals(type) 
				|| PriceEntityConstants.PRICING_CODE_SH.equals(type)
				|| PriceEntityConstants.PRICING_CODE_BF.equals(type)
				|| PriceEntityConstants.PRICING_CODE_BZ.equals(type)
				|| PriceEntityConstants.PRICING_CODE_HK.equals(type)
				|| PriceEntityConstants.PRICING_CODE_ZHXX.equals(type)){			
			//您输入的优惠券类型-feeName无法冲减，请选择营销活动！
			throw new WaybillValidateException(i18n.get("foss.gui.common.commonutils.couponfee.validate.type",new Object[]{feeName}));
		}		
	}
	
	/**
	 * 根据优惠券类型判断是否要添加到优惠费用中
	 * @创建时间 2014-5-7 上午8:44:26   
	 * @创建人： WangQianJin
	 */
	public static boolean isAddPromotionsFeeByType(String type){
		boolean isAdd=true;				
		if(PriceEntityConstants.PRICING_CODE_JH.equals(type) 
				|| PriceEntityConstants.PRICING_CODE_SH.equals(type)
				|| PriceEntityConstants.PRICING_CODE_BF.equals(type)
				|| PriceEntityConstants.PRICING_CODE_BZ.equals(type)
				|| PriceEntityConstants.PRICING_CODE_HK.equals(type)
				|| PriceEntityConstants.PRICING_CODE_ZHXX.equals(type)){			
			//零担不处理增值优惠券
			isAdd=false;
		}		
		return isAdd;
	}
	
	/**
	 * 根据优惠券类型判断是否要添加到优惠费用中(快递)
	 * @创建时间 2014-5-7 上午8:44:26   
	 * @创建人： WangQianJin
	 */
	public static boolean isAddPromotionsFeeByTypeExpress(String type){		
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
	* 判断货物类型（强制设置货物为B的判定）
	 * @author foss-liyongfei
	 * @param bean
	 */
	public static boolean getGoodsTypeIsB(WaybillPanelVo bean){
		//货物的总重量
		BigDecimal goodsWeightTotal = bean.getGoodsWeightTotal();
		//货物的总件数
		Integer goodsQtyTotal = bean.getGoodsQtyTotal();
		if(goodsWeightTotal!=null && goodsQtyTotal!=null){
			//若货物的总重量和货物的总件数没有录入值，不进行判定,返回FALSE，若都有录入值则判定
			if(goodsWeightTotal.compareTo(BigDecimal.ZERO)>0 && goodsQtyTotal.intValue()>0){
				//货物的平均重量大于50Kg 设置为B货，返回true
				if(goodsWeightTotal.doubleValue()/goodsQtyTotal>NumberConstants.NUMBER_50){
					return true;
				}else{//货物平均重量小于50Kg，则进行一下判断
					//若包装带有木或者托，则强制B货，返回TRUE
					if (bean.getWood().intValue() > 0 || bean.getSalver().intValue() > 0) {
						return true;
					}
					return false;
				}
			}else{
				return false;
			}
		}
		return false;
		
	}
	/**
	 * 精准大票运输性质过滤
	 * filterBigGoodsProductEntity: <br/>
	 * 
	 * Date:2014-6-26上午9:41:35
	 * @author 157229-zxy
	 * @param productEntityLst	所有运输性质
	 * @param isBigGoods	是否精准大票
	 * @return
	 * @since JDK 1.6
	 */
	public static List<ProductEntity> filterBigGoodsProductEntity(List<ProductEntity> productEntityLst, boolean isBigGoods){
		Iterator<ProductEntity> itr = productEntityLst.iterator();
		
		while(itr.hasNext()){
			ProductEntity productEntity = itr.next();
			//是否精准大票
			if(isBigGoods){
				//过滤掉非大票的运输性质
				if(!ProductEntityConstants.PRICING_PRODUCT_C2_C20006.equals(productEntity.getParentCode())
						&& !ProductEntityConstants.PRICING_PRODUCT_C2_C20007.equals(productEntity.getParentCode())
						&& !ProductEntityConstants.PRICING_PRODUCT_C2_C20008.equals(productEntity.getParentCode())
						&& !ProductEntityConstants.PRICING_PRODUCT_C2_C20009.equals(productEntity.getParentCode())){
					itr.remove();
				}
			}else{
				//过滤掉大票的运输性质
				if(ProductEntityConstants.PRICING_PRODUCT_C2_C20006.equals(productEntity.getParentCode())
						|| ProductEntityConstants.PRICING_PRODUCT_C2_C20007.equals(productEntity.getParentCode())
						|| ProductEntityConstants.PRICING_PRODUCT_C2_C20008.equals(productEntity.getParentCode())
						|| ProductEntityConstants.PRICING_PRODUCT_C2_C20009.equals(productEntity.getParentCode())){
					itr.remove();
				}
			}
		}
		return productEntityLst;
	}
	
	/**
	 * 通过产品类型判断是否精准大票运输性质
	 * isBigGoodsByProductCode: <br/>
	 * 
	 * Date:2014-6-26上午10:11:49
	 * @author 157229-zxy
	 * @param productCode
	 * @return
	 * @since JDK 1.6
	 */
	public static boolean isBigGoodsByProductCode(String productCode){
		boolean bBigGoods = false;
		if(ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT_BG.equals(productCode)
				|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT_BG.equals(productCode)
				|| ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_ROAD_FREIGHT_BG.equals(productCode)
				|| ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT_BG.equals(productCode)
				|| ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR.equals(productCode)
				|| ProductEntityConstants.PRICING_PRODUCT_YARD_TO_YARD.equals(productCode)){
			bBigGoods = true;
		}
		return bBigGoods;
	}
	
	/**
	 * 是否超重货
	 * isHeavyWeight: <br/>
	 * 
	 * Date:2014-6-26下午5:05:56
	 * @author 157229-zxy
	 * @param bean
	 * @return
	 * @since JDK 1.6
	 */
	public static boolean isHeavyWeight(WaybillPanelVo bean){
		boolean bHeavyWeight = false;	//超重货标志
		ProductEntityVo productEntity =bean.getProductCode();
		if(productEntity!=null && !ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productEntity.getCode())){
			if(bean.getGoodsWeightTotal()==null
				||bean.getGoodsQtyTotal()==null){
				return bHeavyWeight;
			}
			BigDecimal goodsWeightTotal=bean.getGoodsWeightTotal();
			int goodsQtyTotal=bean.getGoodsQtyTotal();
			if(goodsQtyTotal == 0){
				return bHeavyWeight;
			}
			BigDecimal unitWeight=goodsWeightTotal.divide(new BigDecimal(goodsQtyTotal),1,BigDecimal.ROUND_HALF_UP);
			if(unitWeight.compareTo(new BigDecimal(NumberConstants.NUMBER_500))>0){
				bHeavyWeight = true;
			}
		}
		return bHeavyWeight;
	}
	
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.common.client.utils.CommonUtils.filterChangeBigGoodsProductEntity
	 * @Description:更改单转运模块调用过滤精准大票运输性质   新产品的转运中需要增加精准城运   精准汽运(短途)
	 *
	 * @param productEntityLst
	 * @param isBigGoods
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2014-10-14 上午8:47:47
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-10-14    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	public static List<ProductEntity> filterChangingBigGoodsProductEntity(List<ProductEntity> productEntityLst, boolean isBigGoods){
		Iterator<ProductEntity> itr = productEntityLst.iterator();
		
		while(itr.hasNext()){
			ProductEntity productEntity = itr.next();
			//是否精准大票
			if(isBigGoods){
				//过滤掉非大票的运输性质
				/**
				 *author  yangkang
				 *DMANA-4978    新产品开发项目——FOSS支持新产品更改单
				 *需要添加精准城运、精准汽运（短）运输性质
				 */
				if(!ProductEntityConstants.PRICING_PRODUCT_C2_C20006.equals(productEntity.getParentCode())
						&& !ProductEntityConstants.PRICING_PRODUCT_C2_C20007.equals(productEntity.getParentCode())
						&& !ProductEntityConstants.PRICING_PRODUCT_C2_C20008.equals(productEntity.getParentCode())
						&& !ProductEntityConstants.PRICING_PRODUCT_C2_C20009.equals(productEntity.getParentCode())
						&& !ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT.equals(productEntity.getCode())
						&& !ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_ROAD_FREIGHT.equals(productEntity.getCode())){
					itr.remove();
				}
			}else{
				//过滤掉大票的运输性质
				if(ProductEntityConstants.PRICING_PRODUCT_C2_C20006.equals(productEntity.getParentCode())
						|| ProductEntityConstants.PRICING_PRODUCT_C2_C20007.equals(productEntity.getParentCode())
						|| ProductEntityConstants.PRICING_PRODUCT_C2_C20008.equals(productEntity.getParentCode())
						|| ProductEntityConstants.PRICING_PRODUCT_C2_C20009.equals(productEntity.getParentCode())){
					itr.remove();
				}
			}
		}
		return productEntityLst;
	}
	
	/**
	 * 获取其他费用查询参数修改版（1）:只用于纸纤包装计算费用QueryBillCacilateValueAddDto的初始化
	 * @author:218371-foss-zhaoyanjun
	 * @date:2014-11-26下午14:20
	 */
	public static QueryBillCacilateValueAddDto getQueryOtherChargeParamByZQBZ(WaybillPanelVo bean) {
		QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
		queryDto.setOriginalOrgCode(bean.getReceiveOrgCode());// 出发部门CODE
		if(bean.getCustomerPickupOrgCode()!=null){
			queryDto.setDestinationOrgCode(bean.getCustomerPickupOrgCode().getCode());// 到达部门CODE	
		}
		if(bean.getProductCode()!=null){
			queryDto.setProductCode(bean.getProductCode().getCode());// 产品CODE
		}		
		queryDto.setGoodsTypeCode(null);// 货物类型CODE
		queryDto.setReceiveDate(bean.getBillTime());// 营业部收货日期（可选，无则表示当前日期）
		queryDto.setWeight(bean.getGoodsWeightTotal());// 重量
		queryDto.setVolume(bean.getGoodsVolumeTotal());// 体积
		queryDto.setOriginnalCost(null);// 原始费用
		queryDto.setFlightShift(null);// 航班号
		queryDto.setLongOrShort(bean.getLongOrShort());// 长途 还是短途
		queryDto.setSubType(null);// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_ZQBZ);// 计价条目CODE
		queryDto.setPricingEntryName(null);// 计价名称
		queryDto.setCustomerCode(bean.getDeliveryCustomerCode());// 发货客户编码	
		queryDto.setBillTime(bean.getBillTime());//开单时间
		queryDto.setWaybillNo(bean.getWaybillNo());
		return queryDto;
	}
	/**
	 * @author 200945 - wutao
	 * @param customerQueryConditionDto
	 * @param cusBargainEntity
	 * @param panelVo
	 */
	public static void setDevliveryCusomterSettler(CustomerQueryConditionDto customerQueryConditionDto,CusBargainEntity cusBargainEntity,WaybillPanelVo panelVo){
		//判断CRM维护了此客户的信息
		if (cusBargainEntity == null ||("".equals(cusBargainEntity.getAsyntakegoodsCode())
				&& "".equals(cusBargainEntity.getUnifiedCode())
				&& "".equals(cusBargainEntity.getHastenfunddeptCode()))) {
			panelVo.setStartCentralizedSettlement(WaybillConstants.IS_NULL_FOR_AI);
			panelVo.setStartContractOrgCode(null);
			panelVo.setStartContractOrgName(null);
			panelVo.setStartReminderOrgCode(null);
		} else {
			//发票标记 与 统一结算 无关联 --sangwenhao-272311
			//当发票标记为02时
//			if (WaybillConstants.INVOICE_02.equals(customerQueryConditionDto.getInvoiceType())) {
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
			/*} else {
				//当发票标记为1的时候，【是否统一结算】设置【否】，【合同部门】设置为【null】
				panelVo.setStartCentralizedSettlement(WaybillConstants.IS_NULL_FOR_AI);
				panelVo.setStartContractOrgCode(null);
				panelVo.setStartContractOrgName(null);
				panelVo.setStartReminderOrgCode(null);
			}*/
		}
		//wutao == end 
	}

/**
 * @author 200945 - wutao
 * @param customerQueryConditionDto
 * @param cusBargainEntity
 * @param panelVo
 */
	public static void setReciveCusomterSettler(CustomerQueryConditionDto customerQueryConditionDto,CusBargainEntity cusBargainEntity,WaybillPanelVo panelVo){
			//判断CRM维护了此客户的信息
			if (cusBargainEntity == null ||("".equals(cusBargainEntity.getAsyntakegoodsCode())
					&& "".equals(cusBargainEntity.getUnifiedCode())
					&& "".equals(cusBargainEntity.getHastenfunddeptCode()))) {
				panelVo.setArriveCentralizedSettlement(WaybillConstants.IS_NULL_FOR_AI);
				panelVo.setArriveContractOrgCode(null);
				panelVo.setArriveContractOrgName(null);
				panelVo.setArriveReminderOrgCode(null);
			} else {
				//发票标记 与 统一结算 无关联 --sangwenhao-272311
				//当发票标记为02时
//				if (WaybillConstants.INVOICE_02.equals(customerQueryConditionDto.getInvoiceType())) {
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
				/*} else {
					//当发票标记为1的时候，【是否统一结算】设置【否】，【合同部门】设置为【null】
					panelVo.setArriveCentralizedSettlement(WaybillConstants.IS_NULL_FOR_AI);
					panelVo.setArriveContractOrgCode(null);
					panelVo.setArriveContractOrgName(null);
					panelVo.setArriveReminderOrgCode(null);
				}*/
			}
			//wutao == end 
		}
	//待补录 设置统一结算
	//设置待补录
	//发货
	public static void setPendingExpDevliveryCusomterSettler(CustomerQueryConditionDto customerQueryConditionDto,CusBargainEntity cusBargainEntity,WaybillPendingEntity panelVo){
	//判断CRM维护了此客户的信息
	if (cusBargainEntity == null ||("".equals(cusBargainEntity.getAsyntakegoodsCode())
	&& "".equals(cusBargainEntity.getUnifiedCode())
	&& "".equals(cusBargainEntity.getHastenfunddeptCode()))) {
	panelVo.setStartCentralizedSettlement(WaybillConstants.IS_NULL_FOR_AI);
	panelVo.setStartContractOrgCode(null);
	panelVo.setStartContractOrgName(null);
	panelVo.setStartReminderOrgCode(null);
	} else {
	//当发票标记为02时 --发票标记 与 统一结算 无关联 --sangwenhao-272311
//	if (WaybillConstants.INVOICE_02.equals(customerQueryConditionDto.getInvoiceType())) {
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
	/*} else {
	//当发票标记为1的时候，【是否统一结算】设置【否】，【合同部门】设置为【null】
	panelVo.setStartCentralizedSettlement(WaybillConstants.IS_NULL_FOR_AI);
	panelVo.setStartContractOrgCode(null);
	panelVo.setStartContractOrgName(null);
	panelVo.setStartReminderOrgCode(null);
	}*/
	}
	//wutao == end 
	}
	//收货
	public static void setPendingExpReciveCusomterSettler(CustomerQueryConditionDto customerQueryConditionDto,CusBargainEntity cusBargainEntity,WaybillPendingEntity panelVo){
	//判断CRM维护了此客户的信息
	if (cusBargainEntity == null ||("".equals(cusBargainEntity.getAsyntakegoodsCode())
	&& "".equals(cusBargainEntity.getUnifiedCode())
	&& "".equals(cusBargainEntity.getHastenfunddeptCode()))) {
	panelVo.setArriveCentralizedSettlement(WaybillConstants.IS_NULL_FOR_AI);
	panelVo.setArriveContractOrgCode(null);
	panelVo.setArriveContractOrgName(null);
	panelVo.setArriveReminderOrgCode(null);
	} else {
	//当发票标记为02时 --发票标记 与 统一结算 无关联 --sangwenhao-272311
//	if (WaybillConstants.INVOICE_02.equals(customerQueryConditionDto.getInvoiceType())) {
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
	/*} else {
	//当发票标记为1的时候，【是否统一结算】设置【否】，【合同部门】设置为【null】
	panelVo.setArriveCentralizedSettlement(WaybillConstants.IS_NULL_FOR_AI);
	panelVo.setArriveContractOrgCode(null);
	panelVo.setArriveContractOrgName(null);
	panelVo.setArriveReminderOrgCode(null);
	}*/
	}
	//wutao == end 
	}
	/**
	 * 根据Dmana-10888重新获取发票标记字段的值的方法(这里不考虑自提和整车情况)
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-01-20下午14:47
	 */
	public static String setInvoice(String code){
		String invoice=null;
		//合伙人开单所有的发票全部为2
		if(BZPartnersJudge.IS_PARTENER){
			return WaybillConstants.INVOICE_02;
		}
		if(code!=null){
			//根据客户编码只查询发票标记，不关联客户其他信息
			invoice=customerService.queryInvoiceTypeByCusCode(code);
		}
		if(WaybillConstants.INVOICE_01.equals(invoice)){
			return WaybillConstants.INVOICE_01;
		}
		return WaybillConstants.INVOICE_02;
	}
	/**
	 * 内部发货的公共校验零担、快递开单更改单公用
	 * dp-foss-dongjialing
	 * 225131
	 */
	/**
	 * 1.工号基本校验
	 * @param jobNumber
	 * @param bean
	 * @return
	 */
	public static String validateEmployeeNo(String jobNumber,WaybillPanelVo bean) {
		if(bean.getInternalDeliveryType()==null || StringUtil.isBlank(bean.getInternalDeliveryType().getValueCode())) {
			return WaybillConstants.SUCCESS;
		}
	return jobNumberValidate(jobNumber,bean);
		//员工每月额度校验
		//dp-foss-dongjialing
	}
	/**
	 * 工号是否存在和额度校验
	 * dp-foss-dongjialing
	 * @param jobNumber
	 * @param bean
	 * @return
	 */
	public static String jobNumberValidate(String jobNumber,WaybillPanelVo bean) {
		if(StringUtil.isBlank(jobNumber)) {
			return i18n
					.get("foss.gui.common.commonutils.employeeNo");
		}
		EmployeeEntity employee = 
				customerService.queryEmployeeByEmpCode(jobNumber);
		if (null == employee) {
			return i18n
					.get("foss.gui.common.commonutils.employeeNo.exist");
		} /*else {
			UserEntity user = (UserEntity) SessionContext.getCurrentUser();
			if (null != user
					&& !user.getEmployee().getEmpCode()
							.equals(employee.getEmpCode())) {
				return i18n
						.get("foss.gui.creating.waybillDescriptor.jobNumber.notMatched");
			}
		}*/
		//内部员工价格方案
		//封装条件
		List<InempDiscountPlanEntity> list = waybillInfoService.queryInempDiscountPlanEntity(bean.getBillTime());
		BigDecimal differenceValue = BigDecimal.ZERO;
		BigDecimal amount = BigDecimal.ZERO;
		if (CollectionUtils.isNotEmpty(list)) {
			InempDiscountPlanEntity inempDiscountPlanEntity = list.get(0);
			if (inempDiscountPlanEntity.getHighstPreferentialLimit() != null
					&& inempDiscountPlanEntity.getHighstPreferentialLimit()
							.compareTo(BigDecimal.ZERO) > 0) {
				 amount = waybillInfoService
						.queryDiscountFeeByEmployeeNo(jobNumber,
								bean.getBillTime());
				if (amount == null) {
					amount = BigDecimal.ZERO;
				}
				 differenceValue = inempDiscountPlanEntity
						.getHighstPreferentialLimit().subtract(
								amount.divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
				if (differenceValue.compareTo(BigDecimal.ZERO) <= 0) {
					return i18n
							.get("foss.gui.common.commonutils.employeeHighstPreferentialLimitNotEnable");
				} 
			} else {
				return i18n
						.get("foss.gui.common.commonutils.employeeHighstPreferentialLimitNotEnable");
			}
		} else {
			return i18n
					.get("foss.gui.common.commonutils.employeeHighstPreferentialLimitNotEnable");
		}
		return WaybillConstants.SUCCESS;
	}
	

	/**
	 * @需求：大件上楼优化需求
	 * @功能：“大件上楼”与“送货上楼”的对内备注
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-04-02下午15:15
	 */
	public static void inwardRemark(WaybillPanelVo bean){
		// 对内备注
		String innerNs = bean.getInnerNotes();
		String[] strs = null;
		if (StringUtil.isNotEmpty(innerNs)) {
			strs = innerNs.split(";");
		}
//		String remark = "";
		if ("大件上楼".equals(bean.getReceiveMethod().getValueName())) {
			if (strs != null) {
				StringBuilder newInnerNotes = new StringBuilder();      
				for (String str : strs) {
					if (str.equals(i18n
							.get("foss.gui.common.waybillDescriptor.largeDeliveryUpstairs.innerNotes"))) {
						continue;
					}
					newInnerNotes.append(str).append(";");
				}

				bean.setInnerNotes(newInnerNotes
						+ i18n.get("foss.gui.common.waybillDescriptor.largeDeliveryUpstairs.innerNotes")
						+ ";");

			} else {
				bean.setInnerNotes(i18n
						.get("foss.gui.common.waybillDescriptor.largeDeliveryUpstairs.innerNotes")
						+ ";");
			}
		} else {
			if ("送货上楼".equals(bean.getReceiveMethod().getValueName())) {
				MsgBox.showInfo(i18n
						.get("foss.gui.common.waybillDescriptor.largeDeliveryUpstairs.speace"));
			}
			if (strs != null) {
				StringBuilder innerNsBuiler = new StringBuilder();
				for (String str : strs) {
					if (str.equals(i18n
							.get("foss.gui.common.waybillDescriptor.largeDeliveryUpstairs.innerNotes"))) {
						continue;
					}
					 innerNsBuiler.append(str).append(";");
				}
				bean.setInnerNotes(innerNsBuiler.toString());
			}
		}
	}

	
	/**
	 * 校验：工号和客户信息
	 * 调用此方法默认设置标记‘是否设置内部发货’为false;
	 * 如果想为true，直接调用validateLinkMan(WaybillPanelVo bean,Boolean isSetInternalDeliveryType);
	 * @param bean
	 */
	public static void validateLinkMan(WaybillPanelVo bean) {
		//调用此方法默认设置标记‘是否设置内部发货’为false；
		//如果想为true，直接调用validateLinkMan(bean, isSetInternalDeliveryType);
		
		Boolean isSetInternalDeliveryType = Boolean.FALSE;
		validateLinkMan(bean, isSetInternalDeliveryType);
	}
	
	/**
	 * lianhe--2017年1月7日10:49:46---新增(此处可能有bug TODO)
	 * 校验：工号和客户信息
	 * isSetInternalDeliveryType 是否赋值 ‘内部发货’标记
	 */
	public static void validateLinkMan(WaybillPanelVo bean,Boolean isSetInternalDeliveryType){
		//判断输入工号如果为空，不校验(解决快递调用时，空工号也走此逻辑的bug)
		if (StringUtils.isBlank(bean.getEmployeeNo())) {
			return;
		}
		//根据工号查询人员信息
		EmployeeEntity employeeEntity = customerService.queryEmployeeByEmpCode(bean.getEmployeeNo());
		//获取页面选择的付款方式
		String paidMethodCode = bean.getPaidMethod().getValueCode();
		//校验人员信息不为空
		if (employeeEntity == null) {
			//提示错误
			throw new WaybillValidateException(i18n.get("foss.gui.common.commonUtils.msgBox.wrongEmployeeCode"));
		}
		//判断付款方式
		if (StringUtils.equals(paidMethodCode, WaybillConstants.CASH_PAYMENT) ||
				StringUtils.equals(paidMethodCode, WaybillConstants.CREDIT_CARD_PAYMENT) ||
				StringUtils.equals(paidMethodCode, WaybillConstants.ONLINE_PAYMENT) ||
				StringUtils.equals(paidMethodCode, WaybillConstants.MONTH_PAYMENT)
				) {
			//判断发货姓名或者手机号是否与OA不同,不同则提醒“发货联系人姓名与工号所属员工姓名不符，请核实。”;
			if (!StringUtils.equals(employeeEntity.getEmpName(), bean.getDeliveryCustomerContact())
					|| !StringUtils.equals(employeeEntity.getMobilePhone(), bean.getDeliveryCustomerMobilephone())) {
				//提醒
				throw new WaybillValidateException(i18n.get("foss.gui.common.commonUtils.msgBox.wrongDeliveryInfo"));
			}else {
				//校验合格，判断是否需要赋值'内部发货'标记
				if (isSetInternalDeliveryType) {
					//查询数据字典，获取‘内部发货’信息 ，发货方付
					DataDictionaryValueVo internalDeliveryType = dataDictionaryValueEntityToVo(WaybillConstants.INTERNAL_DELIVERY_TYPE, WaybillConstants.DELIVERY_PAY);
					//查询对应‘内部发货信息’，封装入bean
					if (internalDeliveryType != null) {
						bean.setInternalDeliveryType(internalDeliveryType);
					}else {
						throw new WaybillValidateException(i18n.get("foss.gui.common.commonUtils.msgBox.failToSelectDeliveryInfo"));
					}
				}
			}
			
		} else if (StringUtils.equals(paidMethodCode, WaybillConstants.ARRIVE_PAYMENT)) {
			//收货姓名或者手机号是否与OA不同,不同则提醒“收货联系人姓名与工号所属员工姓名不符，请核实。”
			if (!StringUtils.equals(employeeEntity.getEmpName(), bean.getReceiveCustomerContact())
					|| !StringUtils.equals(employeeEntity.getMobilePhone(), bean.getReceiveCustomerMobilephone())) {
				//提醒
				throw new WaybillValidateException(i18n.get("foss.gui.common.commonUtils.msgBox.wrongReceiveInfo"));
			}else {
				//校验合格，判断是否需要赋值'内部发货'标记
				if (isSetInternalDeliveryType) {
					//查询数据字典，获取‘内部发货’信息 ，收货方付
					DataDictionaryValueVo internalDeliveryType = dataDictionaryValueEntityToVo(WaybillConstants.INTERNAL_DELIVERY_TYPE, WaybillConstants.RECIVE_PAY);
					//查询对应‘内部发货信息’，封装入bean
					if (internalDeliveryType != null) {
						bean.setInternalDeliveryType(internalDeliveryType);
					}else {
						throw new WaybillValidateException(i18n.get("foss.gui.common.commonUtils.msgBox.wrongDeliveryInfo"));
					}
				}
			}
		}
	}
	/**
	 * 将查询的内部发货标记赋值给bean lianhe
	 * 2017年1月7日10:49:46
	 * @param bean
	 * @param internalDeliveryType
	 */
	private static DataDictionaryValueVo dataDictionaryValueEntityToVo(
			String termsCode, String valueCode){
		if (StringUtil.isNotEmpty(valueCode)) {
			DataDictionaryValueEntity entity = dataDictionaryValueService
					.queryDataDictoryValueByCode(termsCode, valueCode);
			if (entity == null){
				return null;
			}
			DataDictionaryValueVo vo = new DataDictionaryValueVo();
			try {
				PropertyUtils.copyProperties(vo, entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return vo;
		} else {
			return null;
		}
	}

	/**
	 * 付款方式的改变
	 * 
	 */
	public static void internalDelivery(WaybillPanelVo bean,JComboBox internalDeliveryType,DefaultComboBoxModel internalDeliveryTypeModel,JTextFieldValidate txtStaffNumber
			,JComboBox comBox,DefaultComboBoxModel model,boolean isPicWaybillOrNot) {		
			if (null == bean.getInternalDeliveryType()
					|| StringUtils.isBlank(bean.getInternalDeliveryType()
							.getValueCode())) {

				bean.setEmployeeNo(null);
				txtStaffNumber.setText(null);
				txtStaffNumber.setBackground(new Color(NumberConstants.NUMBER_225,NumberConstants.NUMBER_225,NumberConstants.NUMBER_225));
				
				txtStaffNumber.setEnabled(false);
				
			} else {
				txtStaffNumber.setBackground(Color.WHITE);
				txtStaffNumber.setEnabled(true);
			}
		
		// 根据内部发货的改变，改变付款方式
		changePayWay(bean, comBox,model,isPicWaybillOrNot);
	}
	/**
	 * 内部发货改变付款方式
	 * dp-foss-dongjialing
	 * 225131
	 * @param customerCode
	 * @param vo
	 * @param type
	 */
	public static void changePayWay(WaybillPanelVo bean,JComboBox comBox,DefaultComboBoxModel model,boolean isPicWaybillOrNot) {
		model.removeAllElements();
		DataDictionaryValueVo vo1 = new DataDictionaryValueVo();
		List<DataDictionaryValueEntity> list = dataDictionaryValueService.queryPaymentMode();
		for (DataDictionaryValueEntity dataDictionary : list) {
			if(!WaybillConstants.TELEGRAPHIC_TRANSFER.equals(dataDictionary.getValueCode())
				&& !WaybillConstants.CHECK.equals(dataDictionary.getValueCode()))
			{
				DataDictionaryValueVo vo = new DataDictionaryValueVo();
				ValueCopy.valueCopy(dataDictionary, vo);
				model.addElement(vo);
			}
			if (WaybillConstants.ARRIVE_PAYMENT.equals(dataDictionary.getValueCode())) {
				ValueCopy.valueCopy(dataDictionary, vo1);
			}
		}
		
		//liding add for NCI 图片开单时屏蔽银行卡的付款方式
		if (isPicWaybillOrNot) {
			for (int i = 0; i < model.getSize(); i++) {
				DataDictionaryValueVo ddv = (DataDictionaryValueVo) model
						.getElementAt(i);
				if (WaybillConstants.CREDIT_CARD_PAYMENT.equals(ddv
						.getValueCode())) {
					model.removeElementAt(i);
				}
			}
		}
		
		// 内部发货为“发货方付”，付款方式去掉“到付”选项
		if(bean.getInternalDeliveryType() != null) {
			if ("DELIVERY_PAY".equals(bean.getInternalDeliveryType().getValueCode())) {
				for(int i = 0 ; i < model.getSize(); i++){
					DataDictionaryValueVo ddv = (DataDictionaryValueVo) model.getElementAt(i);
					if(WaybillConstants.ARRIVE_PAYMENT.equals(ddv.getValueCode())
							|| WaybillConstants.TEMPORARY_DEBT.equals(ddv.getValueCode())){
						model.removeElementAt(i);
					}
				}			
				// 内部发货为“收货方付”，付款方式只有“到付”选项
			} else if ("RECIVE_PAY".equals(bean.getInternalDeliveryType()
					.getValueCode())) {
				model.removeAllElements();
				model.addElement(vo1);
				comBox.setEditable(false);
			} 
		}
		if (null == bean.getInternalDeliveryType()
				|| (null != bean.getInternalDeliveryType() && StringUtil
						.isBlank(bean.getInternalDeliveryType().getValueCode()))) {
			for (int i = 0; i < model.getSize(); i++) {
				DataDictionaryValueVo ddv = (DataDictionaryValueVo) model
						.getElementAt(i);
				if (WaybillConstants.TEMPORARY_DEBT.equals(ddv.getValueCode())) {
					model.removeElementAt(i);
				}
			}
		}
		
	}
	
	public static void  checkAddress(WaybillPanelVo bean){
		//新需求所有的提货方式都要校验，不能含有特殊字符 只能为'中文、英文、数字、-' - sangwenhao
		if(StringUtil.isNotEmpty(bean.getReceiveCustomerAddress())){
//			String a = "^[\\s\u4e00-\u9fa5A-Za-z0-9]+$";
			String a="^[\\-\u4e00-\u9fa5A-Za-z0-9]+$";
			if (!bean.getReceiveCustomerAddress().matches(a)) {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillDescriptor.Address"));
			}
		}else{
			throw new WaybillValidateException(i18n.get("foss.gui.common.WaybillValidate.receiverCustomerAddress.detailAddressNotEmpty"));
		}
		
		// 判断收货方式是否为自提 如果不是自提 就判断收货人地址输入的是否含有特殊字符 可输入中文、英文、数字、空格
		/*if (null != bean.getReceiveMethod() 
				&& 
			(WaybillConstants.SELF_PICKUP.equals(bean.getReceiveMethod().getValueCode()) 
					||
			 WaybillConstants.INNER_PICKUP.equals(bean.getReceiveMethod().getValueCode())
			        ||
			 WaybillConstants.AIR_PICKUP_FREE.equals(bean.getReceiveMethod().getValueCode())
			 		||
			 WaybillConstants.AIR_SELF_PICKUP.equals(bean.getReceiveMethod().getValueCode())
			 		||
			 WaybillConstants.AIRPORT_PICKUP.equals(bean.getReceiveMethod().getValueCode()))
			 ){
		  }else {
			if(StringUtil.isNotEmpty(bean.getReceiveCustomerAddress())){
//				String a = "^[\\s\u4e00-\u9fa5A-Za-z0-9]+$";
				String a="^[\\-\u4e00-\u9fa5A-Za-z0-9]+$";
				if (bean.getReceiveCustomerAddress().matches(a)) {
				} else {
					throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillDescriptor.Address"));
				}
			}
			
		}*/
		
		//DN201508070010 开单收货人地址优化 取消五级地址备注特殊检验
		// 判断收货方式是否为自提 如果不是自提 就判断收货人地址输入的是否含有特殊字符 可输入中文、英文、数字、空格
//		if (null != bean.getReceiveMethod() 
//				&& 
//			  (WaybillConstants.SELF_PICKUP.equals(bean.getReceiveMethod().getValueCode()) 
//						||
//				 WaybillConstants.INNER_PICKUP.equals(bean.getReceiveMethod().getValueCode())
//				        ||
//				 WaybillConstants.AIR_PICKUP_FREE.equals(bean.getReceiveMethod().getValueCode())
//				 		||
//				 WaybillConstants.AIR_SELF_PICKUP.equals(bean.getReceiveMethod().getValueCode())
//				 		||
//				 WaybillConstants.AIRPORT_PICKUP.equals(bean.getReceiveMethod().getValueCode()))
//			   ){
//		  }else{
//			String b = "^[\\s\u4e00-\u9fa5A-Za-z0-9]+$";
//			if(StringUtils.isEmpty(bean.getReceiveCustomerAddressNote())){
//			}else{
//				if(!bean.getReceiveCustomerAddressNote().matches(b)){
//					throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillDescriptor.Address"));
//				}
//			}
//		}
	}
	
	/**
	 * 根据传递过来的对应的三级产品编码判定是否快递
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-6-3 19:01:13
	 */
	public static boolean directDetermineIsExpressByProductCode(String productCode){
		//判定数据是否为空
		if(productCode == null || "".equals(productCode)){
			return false;
		}
		//如果这里抛错,需要人为在线查询
		if(expressProductList == null || expressProductList.size() <= 0){
			expressProductList = baseDataService.getAllProductInfoByLevels1(WaybillConstants.TYPE_OF_EXPRESS);
		}
		if(expressProductList == null || expressProductList.size() <= 0){
			throw new IllegalArgumentException("查询快递产品失败,请关闭系统后重新进入");
		}
		//判定是否快递
		boolean isExpress = false;
		for(ProductEntity productEntity : expressProductList){
			if(productEntity != null && productCode.equals(productEntity.getCode())){
				isExpress = true;
				break;
			}
		}
		return isExpress;
	}

	/**
	 * author 306486
	 * 2017年3月23日
	 * 根据运输性质设置分拣模式
	 * 运输性质校验：校验是否为"精准卡航","精准城运", "精准汽运（长途）" ,"精准汽运（短途）","汽运偏线","精准包裹"
	 *如果是的话则满足零担轻货上分拣条件之一
	 */
	public static String getSortingFlagFromProductCode(String productCode) {
		if(WaybillConstants.TRUCK_FLIGHT.equals(productCode)||
				WaybillConstants.FSF_FLIGHT.equals(productCode)||
				WaybillConstants.LRF_FLIGHT.equals(productCode)||
				WaybillConstants.SRF_FLIGHT.equals(productCode)||
				WaybillConstants.HIGHWAYS_REFERRALS.equals(productCode)||
				WaybillConstants.EC_GOODS.equals(productCode)){
			return WaybillConstants.YES;
		}else{
			return WaybillConstants.NO;
		}
  }

	/**
	 * author 30648
	 * 2017年3月24日
	 * 新增分拣数据 木 托
	 * 假如非木非托的话则满足零担轻货上分拣的条件之一
	 */
	public static String getSortingFlagFromWoodAndSalver(Integer num){
			if(num>WaybillConstants.NUM_ZERO){
				return WaybillConstants.NO;
			}else{
				return WaybillConstants.YES;
			}
	}

	/**
	 * author 306486
	 * 2017年3月24日
	 * 根据其他包装信息新增分拣数据
	 * 假如其他包装信息不含"航空箱,桶,铁,木,框,托"这些文字,则满足零担轻货上分拣条件之一
	 */
	public static String getSortingFlagFromOtherPackage(String otherPackage) {
		if(otherPackage==null||"".equals(otherPackage)){
			return WaybillConstants.YES;
		}
		if(otherPackage.contains(WaybillConstants.CH_FLY_CASE)||
				otherPackage.contains(WaybillConstants.CH_PAIL)||
				otherPackage.contains(WaybillConstants.CH_IRON)||
				otherPackage.contains(WaybillConstants.CH_WOOD)||
				otherPackage.contains(WaybillConstants.CH_CASE)||
				otherPackage.contains(WaybillConstants.CH_SALVER)){
			return WaybillConstants.NO;
		}else{
			return WaybillConstants.YES;
		}
	}
	/**
	 * author 306486
	 * 2017年3月24日
	 * 根据总件数新增分拣数据
	 * 假如总件数小于等于5则满足零担轻货上分拣条件之一
	 */
	public static String getSortingFlagFromGoodsQtyTotal(Integer goodsQtyTotal) {
		if(goodsQtyTotal<=Integer.parseInt(readConfValue(WaybillConstants.GOODS_QTY_TOTAL_CONF))){
			return WaybillConstants.YES;
		}else {
			return WaybillConstants.NO;
		}
	}
	/**
	 * author 306486
	 * 2017年3月24日
	 * 根据总重量新增分拣数据
	 * 假如总重量小于等于30则满足零担轻货上分拣条件之一
	 */
	public static String getSortingFlagFromGoodsWeightTotal(BigDecimal goodsWeightTotal,Integer goodsQtyTotal) {
		if(goodsWeightTotal==null){
			return WaybillConstants.YES;
		}
		if(goodsWeightTotal.compareTo(BigDecimal.valueOf(Integer.parseInt(readConfValue(WaybillConstants.GOODS_WEIGHT_TOTAL_CONF))*goodsQtyTotal))<=0){
			return WaybillConstants.YES;
		}else{
			return WaybillConstants.NO;
		}
	}

	/**
	 * 350909         郭倩云        
	 * 从配置参数中获得零担轻货上分拣开关是否开启或者总件数或者总重量的值
	 */
	public static String readConfValue(String code){
		String confValue = "";
		String[] codes = new String[1];
		codes[0]=code;
		List<ConfigurationParamsEntity> configurationParamsEntitys = waybillHessianRemoting.queryConfigurationParamsBatchByCode(codes);
		if(CollectionUtils.isNotEmpty(configurationParamsEntitys)){
			confValue = configurationParamsEntitys.get(0).getConfValue();
		}
		return confValue;
	}

	/**
	 * lianhe/新增校验方法/2017年1月9日
	 * 校验：内部发货时，工号有效，并和客户信息一致
	 * 只有内部调货的时候才调用此方法
	 * @param bean
	 */
	public static void validateInternalDelivery(WaybillPanelVo bean){
		//只有内部调货的时候才调用此方法
		if (bean.getInternalDeliveryType() != null &&
				(StringUtils.equals(WaybillConstants.DELIVERY_PAY, bean.getInternalDeliveryType().getValueCode())
						|| StringUtils.equals(WaybillConstants.RECIVE_PAY, bean.getInternalDeliveryType().getValueCode()))) {
			//内部带货时，若工号为空，需重新计算
			if (StringUtils.isBlank(bean.getEmployeeNo())) {
				throw new WaybillValidateException("内部发货计价，工号不能为空，请重新计算运费！");
			}
			//判断工号有效，并和客户信息一致
			validateLinkMan(bean);
		}
	}
	
	/**
	 * 设置提货方式 zhangdianhao
	 * @date 2017-03-21 上午 10:10:00
	 * @param rm
	 * @return
	 */
	public static String setReceiveMethod(String val) {
		//送货
		if (val.equals(WaybillConstants.DELIVER_UP)
				|| val.equals(WaybillConstants.DELIVER_UP_AIR) 
				|| val.equals(WaybillConstants.DELIVER_NOUP_AIR) 
				|| val.equals(WaybillConstants.DELIVER_NOUP)
				|| val.equals(WaybillConstants.LARGE_DELIVER_UP_AIR)
				|| val.equals(WaybillConstants.LARGE_DELIVER_UP)){
			return GisConstants.GIS_DELIVER;
		}
		//进仓
		if (val.equals(WaybillConstants.DELIVER_STORAGE)){
			return GisConstants.DELIVER_INGA;
		}
		//自提
		if (val.equals(WaybillConstants.SELF_PICKUP)
				|| val.equals(WaybillConstants.INNER_PICKUP) ){
			return GisConstants.GIS_PICKUP;
		}
		return null ;
	}
	
}