package com.deppon.pda.bdm.module.core.shared.util;

import java.util.HashMap;
import java.util.Map;

import com.deppon.pda.bdm.module.core.shared.constants.Constant;

/**
 * 
 * 配置文件读取类
 * 
 * @author chengang
 * @version 1.0
 * @created 2012-9-20 下午03:57:54
 */

public class PropertyConstant {

	private static final String[] fileNames = { "/config.properties" };

	private static final String[] dataFileName = { "/dataExport.properties" };

	private static PropertyManager propertyManager = PropertyManager.load(
			fileNames, PropertyConstant.class);

	public static PropertyManager dataPropertyManager = PropertyManager.load(
			dataFileName, PropertyConstant.class);

	// 分钟(基础数据)
	public static final int DATABASE_MINUTE = propertyManager
			.getInt("database_minute");
	// 目标路径
	public static final String DATAVER_DIR = propertyManager
			.getString("dataVer_url:dir");
	
	//DLL路径 2013年8月22日17:19:48
	public static final String DLL_DIR = propertyManager
			.getString("dllVer_url:dir");
	
	//zip路径 2013年8月23日10:42:38
	public static final String ZIP_DIR = propertyManager
			.getString("zipVer_url:dir");


	// 数据虚拟路径
	public static final String DATA_PATH = propertyManager
			.getString("data_path");

	// 程序虚拟路径
	public static final String PGM_PATH = propertyManager.getString("pgm_path");
	
	//模块DLL压缩包虚拟路径
	public static final String ZIP_PATH = propertyManager.getString("zip_path");
	
	// 程序版本更新路径
	public static final String PGMVER_DIR = propertyManager
			.getString("pgmVer_url:dir");
	// 程序版本名
	public static final String PGM_NAME = propertyManager
			.getString("pda_client");

	//读取配置文件dataExport.properties中的address_filed 获取省市区域字段属性
	public static final String ADDRESS_FILED = dataPropertyManager
	.getString(Constant.TABLE_NAME_ADDRESS
	+ Constant.UNDERLINE_DELIMITER + Constant.FILED);

	//读取配置文件dataExport.properties中的address_type 获取省市区域字段类型
	public static final String ADDRESS_TYPE = dataPropertyManager
	.getString(Constant.TABLE_NAME_ADDRESS
	+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);
	
	/** 获取省份字段属性 **/
	public static final String PROVINCE_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_PROVINCE
					+ Constant.UNDERLINE_DELIMITER + Constant.FILED);

	/** 获取省份字段类型 **/
	public static final String PROVINCE_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_PROVINCE
					+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);

	// 读取配置文件中city_filed
	public static final String CITY_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_CITY + Constant.UNDERLINE_DELIMITER
					+ Constant.FILED);

	// 读取配置文件中city_type
	public static final String CITY_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_CITY + Constant.UNDERLINE_DELIMITER
					+ Constant.TYPE);

	// 读取配置文件中county_filed
	public static final String COUNTY_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_COUNTY
					+ Constant.UNDERLINE_DELIMITER + Constant.FILED);

	// 读取配置文件中county_type
	public static final String COUNTY_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_COUNTY
					+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);

	// 读取配置文件中route_filed
	public static final String ROUTE_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_ROUTE + Constant.UNDERLINE_DELIMITER
					+ Constant.FILED);

	// 读取配置文件中route_type
	public static final String ROUTE_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_ROUTE + Constant.UNDERLINE_DELIMITER
					+ Constant.TYPE);

	// 读取配置文件中route_detail_filed
	public static final String ROUTE_DETAIL_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_ROUTE_DETAIL
					+ Constant.UNDERLINE_DELIMITER + Constant.FILED);

	// 读取配置文件中route_dateil_type
	public static final String ROUTE_DETAIL_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_ROUTE_DETAIL
					+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);

	// 读取配置文件中transport_filed
	public static final String TRANSPORT_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_TRANSPORT
					+ Constant.UNDERLINE_DELIMITER + Constant.FILED);

	// 读取配置文件中transport_type
	public static final String TRANSPORT_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_TRANSPORT
					+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);

	// 读取配置文件中store_filed
	public static final String STORE_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_STORE + Constant.UNDERLINE_DELIMITER
					+ Constant.FILED);

	// 读取配置文件中store_type
	public static final String STORE_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_STORE + Constant.UNDERLINE_DELIMITER
					+ Constant.TYPE);

	// 读取配置文件中storeDept_filed
	public static final String STOREDEPT_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_STOREDEPT
					+ Constant.UNDERLINE_DELIMITER + Constant.FILED);

	// 读取配置文件中storeDept_type
	public static final String STOREDEPT_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_STOREDEPT
					+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);

	// 读取配置文件中departassemblydept_filed
	public static final String DEPARDEPT_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_DEPARTDEPT
					+ Constant.UNDERLINE_DELIMITER + Constant.FILED);

	// 读取配置文件中departassemblydept_type
	public static final String DEPARDEPT_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_DEPARTDEPT
					+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);

	// 读取配置文件中ladingStation_filed
	public static final String LADINGSTATION_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_LADINGSTATION
					+ Constant.UNDERLINE_DELIMITER + Constant.FILED);

	// 读取配置文件中ladingStation_type
	public static final String LADINGSTATION_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_LADINGSTATION
					+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);

	// 读取配置文件中user_filed
	public static final String USER_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_USER + Constant.UNDERLINE_DELIMITER
					+ Constant.FILED);

	// 读取配置文件中user_type
	public static final String USER_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_USER + Constant.UNDERLINE_DELIMITER
					+ Constant.TYPE);

	// 读取配置文件中dept_filed
	public static final String DEPT_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_DEPT + Constant.UNDERLINE_DELIMITER
					+ Constant.FILED);

	// 读取配置文件中dept_type
	public static final String DEPT_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_DEPT + Constant.UNDERLINE_DELIMITER
					+ Constant.TYPE);

	// 读取配置文件中code_filed
	public static final String CODE_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_CODE + Constant.UNDERLINE_DELIMITER
					+ Constant.FILED);

	// 读取配置文件中code_type
	public static final String CODE_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_CODE + Constant.UNDERLINE_DELIMITER
					+ Constant.TYPE);

	// 读取配置文件中specialline_filed
	public static final String LINE_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_LINE + Constant.UNDERLINE_DELIMITER
					+ Constant.FILED);
	// 读取配置文件中specialline_type
	public static final String LINE_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_LINE + Constant.UNDERLINE_DELIMITER
					+ Constant.TYPE);

	/** 获取AB货字段属性 **/
	public static final String ABGOODS_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_ABGOODS
					+ Constant.UNDERLINE_DELIMITER + Constant.FILED);
	/** 获取AB货字段类型 **/
	public static final String ABGOODS_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_ABGOODS
					+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);

	/** 获取违禁品字段属性 **/
	public static final String PROHIBITED_GOODS_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_PROHIBITED_GOODS
					+ Constant.UNDERLINE_DELIMITER + Constant.FILED);
	/** 获取违禁品字段类型 **/
	public static final String PROHIBITED_GOODS_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_PROHIBITED_GOODS
					+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);

	/** 获取增值服务字段属性 **/
	public static final String ADD_SERVICE_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_ADD_SERVICE
					+ Constant.UNDERLINE_DELIMITER + Constant.FILED);
	/** 获取增值服务字段类型 **/
	public static final String ADD_SERVICE_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_ADD_SERVICE
					+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);

	/** 获取包装类型字段属性 **/
	public static final String PACKAGE_TYPE_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_PACKAGE_TYPE
					+ Constant.UNDERLINE_DELIMITER + Constant.FILED);
	/** 获取包装类型字段类型 **/
	public static final String PACKAGE_TYPE_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_PACKAGE_TYPE
					+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);

	public static final String DISCOUNT_ORG_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_DISCOUNT_ORG
					+ Constant.UNDERLINE_DELIMITER + Constant.FILED);
	public static final String DISCOUNT_ORG_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_DISCOUNT_ORG
					+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);

	public static final String DISCOUNT_PRIORITY_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_DISCOUNT_PRIORITY
					+ Constant.UNDERLINE_DELIMITER + Constant.FILED);
	public static final String DISCOUNT_PRIORITY_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_DISCOUNT_PRIORITY
					+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);

	/** 零担时效关系 **/
	public static final String EFFECTIVE_REGION_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_EFFECTIVE_REGION
					+ Constant.UNDERLINE_DELIMITER + Constant.FILED);
	public static final String EFFECTIVE_REGION_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_EFFECTIVE_REGION
					+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);

	public static final String EFFECTIVE_REGION_ORG_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_EFFECTIVE_REGION_ORG
					+ Constant.UNDERLINE_DELIMITER + Constant.FILED);
	public static final String EFFECTIVE_REGION_ORG_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_EFFECTIVE_REGION_ORG
					+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);
	
	public static final String EFFECTIVE_PLAN_DETAIL_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_EFFECTIVE_PLAN_DETAIL
					+ Constant.UNDERLINE_DELIMITER + Constant.FILED);
	public static final String EFFECTIVE_PLAN_DETAIL_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_EFFECTIVE_PLAN_DETAIL
					+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);
	/** 快递时效关系 **/
	/*public static final String EXPRESS_EFFECTIVE_REGION_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_EXPRESS_EFFECTIVE_REGION
					+ Constant.UNDERLINE_DELIMITER + Constant.FILED);
	public static final String EXPRESS_EFFECTIVE_REGION_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_EXPRESS_EFFECTIVE_REGION
					+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);

	public static final String EXPRESS_EFFECTIVE_REGION_ORG_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_EXPRESS_EFFECTIVE_REGION_ORG
					+ Constant.UNDERLINE_DELIMITER + Constant.FILED);
	public static final String EXPRESS_EFFECTIVE_REGION_ORG_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_EXPRESS_EFFECTIVE_REGION_ORG
					+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);
	
	public static final String EXPRESS_EFFECTIVE_PLAN_DETAIL_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_EXPRESS_EFFECTIVE_PLAN_DETAIL
					+ Constant.UNDERLINE_DELIMITER + Constant.FILED);
	public static final String EXPRESS_EFFECTIVE_PLAN_DETAIL_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_EXPRESS_EFFECTIVE_PLAN_DETAIL
					+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);*/
	
	
	public static final String GOODSTYPE_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_GOODSTYPE
					+ Constant.UNDERLINE_DELIMITER + Constant.FILED);
	public static final String GOODSTYPE_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_GOODSTYPE
					+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);

	public static final String MARKETING_EVENT_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_MARKETING_EVENT
					+ Constant.UNDERLINE_DELIMITER + Constant.FILED);
	public static final String MARKETING_EVENT_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_MARKETING_EVENT
					+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);

	public static final String MARKETING_EVENT_CHANEL_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_MARKETING_EVENT_CHANEL
					+ Constant.UNDERLINE_DELIMITER + Constant.FILED);
	public static final String MARKETING_EVENT_CHANEL_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_MARKETING_EVENT_CHANEL
					+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);

	public static final String PRICE_PLAN_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_PRICE_PLAN
					+ Constant.UNDERLINE_DELIMITER + Constant.FILED);
	public static final String PRICE_PLAN_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_PRICE_PLAN
					+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);

	public static final String PRICE_REGION_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_PRICE_REGION
					+ Constant.UNDERLINE_DELIMITER + Constant.FILED);
	public static final String PRICE_REGION_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_PRICE_REGION
					+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);

	public static final String PRICE_REGION_ORG_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_PRICE_REGION_ORG
					+ Constant.UNDERLINE_DELIMITER + Constant.FILED);
	public static final String PRICE_REGION_ORG_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_PRICE_REGION_ORG
					+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);

	public static final String PRICE_RULE_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_PRICE_RULE
					+ Constant.UNDERLINE_DELIMITER + Constant.FILED);
	public static final String PRICE_RULE_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_PRICE_RULE
					+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);

	public static final String PRICING_CRITERIA_DETAIL_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_PRICING_CRITERIA_DETAIL
					+ Constant.UNDERLINE_DELIMITER + Constant.FILED);
	public static final String PRICING_CRITERIA_DETAIL_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_PRICING_CRITERIA_DETAIL
					+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);

	public static final String PRICING_ENTRY_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_PRICING_ENTRY
					+ Constant.UNDERLINE_DELIMITER + Constant.FILED);
	public static final String PRICING_ENTRY_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_PRICING_ENTRY
					+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);

	public static final String PRICING_VALUATION_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_PRICING_VALUATION
					+ Constant.UNDERLINE_DELIMITER + Constant.FILED);
	public static final String PRICING_VALUATION_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_PRICING_VALUATION
					+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);

	public static final String PRODUCT_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_PRODUCT
					+ Constant.UNDERLINE_DELIMITER + Constant.FILED);
	public static final String PRODUCT_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_PRODUCT
					+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);

	public static final String PRODUCT_ITEM_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_PRODUCT_ITEM
					+ Constant.UNDERLINE_DELIMITER + Constant.FILED);
	public static final String PRODUCT_ITEM_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_PRODUCT_ITEM
					+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);
	
	/**基础数据-网点组字段属性**/
	public static final String NET_GROUP_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_NET_GROUP
					+ Constant.UNDERLINE_DELIMITER + Constant.FILED);
	/**基础数据-网点组字段类型**/
	public static final String NET_GROUP_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_NET_GROUP
					+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);
	
	/**基础数据-星号部门字段属性**/
	public static final String SALESDEPT_ASTERISK_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_SALESDEPT_ASTERISK
					+ Constant.UNDERLINE_DELIMITER + Constant.FILED);
	/**基础数据-星号部门字段类型**/
	public static final String SALESDEPT_ASTERISK_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_SALESDEPT_ASTERISK
					+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);
	

	
	
	/**基础数据-开单组与营业部开单出发部门字段属性*/
	public static final String TABLE_NAME_SALESDEPT_BILLINGGROUP_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_SALESDEPT_BILLINGGROUP
					+ Constant.UNDERLINE_DELIMITER + Constant.FILED);
	/**基础数据-开单组与营业部开单出发部门字段类型**/
	public static final String TABLE_NAME_SALESDEPT_BILLINGGROUP_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_SALESDEPT_BILLINGGROUP
					+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);
	/***快递开始啦 2013年8月16日17:32:28***/
	/**基础数据-快递员负责行政区域字段属性**/
	public static final String EXPRESS_EMP_DISTRICT_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_EXPRESS_EMP_DISTRICT
					+ Constant.UNDERLINE_DELIMITER + Constant.FILED);
	/**基础数据-快递员负责行政区域字段类型**/
	public static final String EXPRESS_EMP_DISTRICT_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_EXPRESS_EMP_DISTRICT
					+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);
	
	/**基础数据-快递车辆字段属性**/
	public static final String EXPRESS_VEHICLE_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_EXPRESS_VEHICLE
					+ Constant.UNDERLINE_DELIMITER + Constant.FILED);
	/**基础数据-快递车辆字段类型**/
	public static final String EXPRESS_VEHICLE_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_EXPRESS_VEHICLE
					+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);
	
	/**基础数据-落地配、试点城市字段属性**/
	public static final String EXPRESS_CITY_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_EXPRESS_CITY
					+ Constant.UNDERLINE_DELIMITER + Constant.FILED);
	/**基础数据-落地配、试点城市字段类型**/
	public static final String EXPRESS_CITY_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_EXPRESS_CITY
					+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);
	
	/**基础数据-快递点部与营业部映射字段属性**/
	public static final String EXPRESS_PART_SALES_DEPT_FILED = dataPropertyManager
			.getString(Constant.TABLE_NAME_EXPRESS_PART_SALES_DEPT
					+ Constant.UNDERLINE_DELIMITER + Constant.FILED);
	/**基础数据-快递点部与营业部映射字段类型**/
	public static final String EXPRESS_PART_SALES_DEPT_TYPE = dataPropertyManager
			.getString(Constant.TABLE_NAME_EXPRESS_PART_SALES_DEPT
					+ Constant.UNDERLINE_DELIMITER + Constant.TYPE);
	
	public final static Map<String, String> PRO_DATA_MAP = new HashMap<String, String>();
	static {
		PRO_DATA_MAP.put(Constant.TABLE_NAME_ADDRESS
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED, ADDRESS_FILED);
				PRO_DATA_MAP.put(Constant.TABLE_NAME_ADDRESS
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE, ADDRESS_TYPE);
		PRO_DATA_MAP
				.put(Constant.TABLE_NAME_PROVINCE
						+ Constant.UNDERLINE_DELIMITER + Constant.FILED,
						PROVINCE_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_PROVINCE
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE, PROVINCE_TYPE);

		PRO_DATA_MAP.put(Constant.TABLE_NAME_CITY
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED, CITY_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_CITY
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE, CITY_TYPE);

		PRO_DATA_MAP.put(Constant.TABLE_NAME_COUNTY
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED, COUNTY_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_COUNTY
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE, COUNTY_TYPE);

		PRO_DATA_MAP.put(Constant.TABLE_NAME_ROUTE
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED, ROUTE_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_ROUTE
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE, ROUTE_TYPE);

		PRO_DATA_MAP.put(Constant.TABLE_NAME_ROUTE_DETAIL
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED,
				ROUTE_DETAIL_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_ROUTE_DETAIL
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE,
				ROUTE_DETAIL_TYPE);

		PRO_DATA_MAP.put(Constant.TABLE_NAME_TRANSPORT
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED,
				TRANSPORT_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_TRANSPORT
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE, TRANSPORT_TYPE);

		PRO_DATA_MAP.put(Constant.TABLE_NAME_STORE
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED, STORE_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_STORE
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE, STORE_TYPE);

		PRO_DATA_MAP.put(Constant.TABLE_NAME_STOREDEPT
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED,
				STOREDEPT_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_STOREDEPT
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE, STOREDEPT_TYPE);

		PRO_DATA_MAP.put(Constant.TABLE_NAME_DEPARTDEPT
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED,
				DEPARDEPT_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_DEPARTDEPT
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE, DEPARDEPT_TYPE);

		PRO_DATA_MAP.put(Constant.TABLE_NAME_LADINGSTATION
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED,
				LADINGSTATION_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_LADINGSTATION
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE,
				LADINGSTATION_TYPE);

		PRO_DATA_MAP.put(Constant.TABLE_NAME_USER
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED, USER_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_USER
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE, USER_TYPE);

		PRO_DATA_MAP.put(Constant.TABLE_NAME_DEPT
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED, DEPT_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_DEPT
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE, DEPT_TYPE);

		PRO_DATA_MAP.put(Constant.TABLE_NAME_CODE
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED, CODE_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_CODE
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE, CODE_TYPE);

		PRO_DATA_MAP.put(Constant.TABLE_NAME_LINE
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED, LINE_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_LINE
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE, LINE_TYPE);

		PRO_DATA_MAP.put(Constant.TABLE_NAME_ABGOODS
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED, ABGOODS_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_ABGOODS
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE, ABGOODS_TYPE);

		PRO_DATA_MAP.put(Constant.TABLE_NAME_PROHIBITED_GOODS
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED,
				PROHIBITED_GOODS_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_PROHIBITED_GOODS
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE,
				PROHIBITED_GOODS_TYPE);

		PRO_DATA_MAP.put(Constant.TABLE_NAME_ADD_SERVICE
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED,
				ADD_SERVICE_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_ADD_SERVICE
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE,
				ADD_SERVICE_TYPE);

		PRO_DATA_MAP.put(Constant.TABLE_NAME_PACKAGE_TYPE
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED,
				PACKAGE_TYPE_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_PACKAGE_TYPE
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE,
				PACKAGE_TYPE_TYPE);
		
		PRO_DATA_MAP.put(Constant.TABLE_NAME_DISCOUNT_ORG
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED,
				DISCOUNT_ORG_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_DISCOUNT_ORG
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE,
				DISCOUNT_ORG_TYPE);
		
		PRO_DATA_MAP.put(Constant.TABLE_NAME_DISCOUNT_PRIORITY
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED,
				DISCOUNT_PRIORITY_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_DISCOUNT_PRIORITY
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE,
				DISCOUNT_PRIORITY_TYPE);
		
		/**零担时效区域**/
		
		PRO_DATA_MAP.put(Constant.TABLE_NAME_EFFECTIVE_REGION
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED,
				EFFECTIVE_REGION_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_EFFECTIVE_REGION
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE,
				EFFECTIVE_REGION_TYPE);
		
		PRO_DATA_MAP.put(Constant.TABLE_NAME_EFFECTIVE_REGION_ORG
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED,
				EFFECTIVE_REGION_ORG_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_EFFECTIVE_REGION_ORG
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE,
				EFFECTIVE_REGION_ORG_TYPE);
		
		PRO_DATA_MAP.put(Constant.TABLE_NAME_EFFECTIVE_PLAN_DETAIL
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED,
				EFFECTIVE_PLAN_DETAIL_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_EFFECTIVE_PLAN_DETAIL
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE,
				EFFECTIVE_PLAN_DETAIL_TYPE);
		
		/**快递时效区域**/
		
		/*PRO_DATA_MAP.put(Constant.TABLE_NAME_EXPRESS_EFFECTIVE_REGION
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED,
				EXPRESS_EFFECTIVE_REGION_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_EXPRESS_EFFECTIVE_REGION
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE,
				EXPRESS_EFFECTIVE_REGION_TYPE);
		
		PRO_DATA_MAP.put(Constant.TABLE_NAME_EXPRESS_EFFECTIVE_REGION_ORG
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED,
				EXPRESS_EFFECTIVE_REGION_ORG_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_EXPRESS_EFFECTIVE_REGION_ORG
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE,
				EXPRESS_EFFECTIVE_REGION_ORG_TYPE);
		
		PRO_DATA_MAP.put(Constant.TABLE_NAME_EXPRESS_EFFECTIVE_PLAN_DETAIL
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED,
				EXPRESS_EFFECTIVE_PLAN_DETAIL_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_EXPRESS_EFFECTIVE_PLAN_DETAIL
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE,
				EXPRESS_EFFECTIVE_PLAN_DETAIL_TYPE);*/
		
		
		PRO_DATA_MAP.put(Constant.TABLE_NAME_GOODSTYPE
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED,
				GOODSTYPE_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_GOODSTYPE
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE,
				GOODSTYPE_TYPE);
		
		PRO_DATA_MAP.put(Constant.TABLE_NAME_MARKETING_EVENT
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED,
				MARKETING_EVENT_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_MARKETING_EVENT
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE,
				MARKETING_EVENT_TYPE);
		
		
		PRO_DATA_MAP.put(Constant.TABLE_NAME_MARKETING_EVENT_CHANEL
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED,
				MARKETING_EVENT_CHANEL_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_MARKETING_EVENT_CHANEL
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE,
				MARKETING_EVENT_CHANEL_TYPE);
		
		PRO_DATA_MAP.put(Constant.TABLE_NAME_PRICE_PLAN
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED,
				PRICE_PLAN_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_PRICE_PLAN
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE,
				PRICE_PLAN_TYPE);
		
		PRO_DATA_MAP.put(Constant.TABLE_NAME_PRICE_REGION
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED,
				PRICE_REGION_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_PRICE_REGION
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE,
				PRICE_REGION_TYPE);
		
		PRO_DATA_MAP.put(Constant.TABLE_NAME_PRICE_REGION_ORG
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED,
				PRICE_REGION_ORG_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_PRICE_REGION_ORG
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE,
				PRICE_REGION_ORG_TYPE);
		
		PRO_DATA_MAP.put(Constant.TABLE_NAME_PRICE_RULE
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED,
				PRICE_RULE_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_PRICE_RULE
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE,
				PRICE_RULE_TYPE);
		
		PRO_DATA_MAP.put(Constant.TABLE_NAME_PRICING_CRITERIA_DETAIL
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED,
				PRICING_CRITERIA_DETAIL_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_PRICING_CRITERIA_DETAIL
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE,
				PRICING_CRITERIA_DETAIL_TYPE);
		
		PRO_DATA_MAP.put(Constant.TABLE_NAME_PRICING_ENTRY
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED,
				PRICING_ENTRY_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_PRICING_ENTRY
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE,
				PRICING_ENTRY_TYPE);
		
		PRO_DATA_MAP.put(Constant.TABLE_NAME_PRICING_VALUATION
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED,
				PRICING_VALUATION_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_PRICING_VALUATION
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE,
				PRICING_VALUATION_TYPE);
		
		PRO_DATA_MAP.put(Constant.TABLE_NAME_PRODUCT
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED,
				PRODUCT_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_PRODUCT
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE,
				PRODUCT_TYPE);
		
		PRO_DATA_MAP.put(Constant.TABLE_NAME_PRODUCT_ITEM
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED,
				PRODUCT_ITEM_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_PRODUCT_ITEM
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE,
				PRODUCT_ITEM_TYPE);
		
		PRO_DATA_MAP.put(Constant.TABLE_NAME_NET_GROUP
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED,
				NET_GROUP_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_NET_GROUP
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE,
				NET_GROUP_TYPE);
		
		PRO_DATA_MAP.put(Constant.TABLE_NAME_SALESDEPT_ASTERISK
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED,
				SALESDEPT_ASTERISK_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_SALESDEPT_ASTERISK
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE,
				SALESDEPT_ASTERISK_TYPE);
		
		
		PRO_DATA_MAP.put(Constant.TABLE_NAME_SALESDEPT_BILLINGGROUP
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED,
				TABLE_NAME_SALESDEPT_BILLINGGROUP_FILED);
	
		PRO_DATA_MAP.put(Constant.TABLE_NAME_SALESDEPT_BILLINGGROUP
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE,
				TABLE_NAME_SALESDEPT_BILLINGGROUP_TYPE);
		//快递开始啦...2013年8月16日16:44:27
		/**快递员负责行政区域**/
		PRO_DATA_MAP.put(Constant.TABLE_NAME_EXPRESS_EMP_DISTRICT
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED,
				EXPRESS_EMP_DISTRICT_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_EXPRESS_EMP_DISTRICT
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE,
				EXPRESS_EMP_DISTRICT_TYPE);
		
		/**快递车辆**/
		PRO_DATA_MAP.put(Constant.TABLE_NAME_EXPRESS_VEHICLE
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED,
				EXPRESS_VEHICLE_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_EXPRESS_VEHICLE
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE,
				EXPRESS_VEHICLE_TYPE);
		
		/**落地配、试点城市**/
		PRO_DATA_MAP.put(Constant.TABLE_NAME_EXPRESS_CITY
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED,
				EXPRESS_CITY_FILED);
		PRO_DATA_MAP.put(Constant.TABLE_NAME_EXPRESS_CITY
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE,
				EXPRESS_CITY_TYPE);
		
		/**快递点部与营业部映射**/
		PRO_DATA_MAP.put(Constant.TABLE_NAME_EXPRESS_PART_SALES_DEPT
				+ Constant.UNDERLINE_DELIMITER + Constant.FILED,
				EXPRESS_PART_SALES_DEPT_FILED);
		
		PRO_DATA_MAP.put(Constant.TABLE_NAME_EXPRESS_PART_SALES_DEPT
				+ Constant.UNDERLINE_DELIMITER + Constant.TYPE,
				EXPRESS_PART_SALES_DEPT_TYPE);
	}
}
