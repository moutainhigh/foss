package com.deppon.pda.bdm.module.core.shared.constants;

/**
 * 常量类
 * 
 * @author chengang
 * @version 1.0
 * @created 2012-9-6 下午06:52:39
 */

public class Constant {
	
	/**
	 * 
	  * @ClassName MONITOR 
	  * @Description TODO 监控类型
	  * @author mt hyssmt@vip.qq.com
	  * @date 2013-10-3 上午11:15:09
	 */
	public final static class MONITOR{
		/**
		 * 异常
		 */
		public static final String ERROR = "ERROR";
		/**
		 * 正常
		 */
		public static final String NORMAL = "NORMAL";
		/**
		 * 监控时间
		 */
		public static final String MONITOR_MSGTIME = "MONITOR_MSGTIME";
		
		/**
		 * 监控队列bean名称
		 */
		public static final String MONITOR_QUEUE_NAME = "monitorQueue";
		/**
		 * 监控JOB，定时处理数据名称
		 */
		public static final String MONITOR_JOB_NAME = "monitorJob";
	}

	/**
	 * 请求类型
	 * 
	 * @author wanghongling
	 * @date 2012-09-07
	 * @version 1.0
	 * 
	 */
	public final static class REQTYPE {
		/**
		 * 单个请求
		 */
		public final static String REQTYPE_SINGLE = "S";

		/**
		 * 多个请求（数组）
		 */
		public final static String REQTYPE_ARRAY = "A";
	}
	
	/**
	 * 
	  * @ClassName USER_TYPE 快递用户类型, 用于基础资料分用户类型下载
	  * @Description TODO 
	  * @author mt 
	  * @date 2013-8-27 上午9:06:59
	 */
	public final static class USER_TYPE{
		/**
		 * 理货员
		 */
		public static final String TALLYPERSON = "TALLYPERSON";
		
		/**
		 * 司机
		 */
		public static final String DRIVER = "DRIVER";
		
		/**
		 * 快递员
		 */
		public static final String COURIER = "COURIER";
		
		/**
		 * 所有用户都要下载
		 */
		public static final String ALL = "ALL";
	}

	public final static String REQ_JSON_BODY = "jsonStr";

	/**
	 * 验证服务常量
	 */
	public final static String VALIDATESERVICE = "validateService";

	/**
	 * 操作工厂常量
	 */
	public final static String FACTORY = "operationFactory";

	/**
	 * 装车类型
	 */

	// 派送装车
	public final static String LOADTYPE_DERY = "2";

	// 中转装车
	public final static String LOADTYPE_TRANSIT = "4";

	/**
	 * 调用接口返回结果状态(1表示成功，0表示失败重发，2表示失败不重发)
	 */
	// 成功
	public final static int SUCCESS = 1;
	// 失败重发
	public final static int FAILURE = 0;
	// 失败不重发
	public final static int FAILURE_NORET = 2;
	/**
	 * 设备状态
	 */
	// 禁用
	public final static String DISABLE_DEVICE = "0";
	// 启用
	public final static String ENABLE_DEVICE = "1";

	/**
	 * 登录用户类型
	 */
	// 司机
	public final static String DRIVER_LOGIN_USERTYPE = "1";
	
	/**
	 * 用户类型 ：理货员
	 */
	public static final String USER_TYPE_TALLYPERSON = "TALLYPERSON";
	
	/**
	 * 用户类型：司机
	 */
	public static final String USER_TYPE_DRIVER = "DRIVER";

	/**
	 * PDA程序版本标识
	 */
	public final static String PGM_VERSION_KEY = "pgmVer";

	/**
	 * 基础数据版本标识
	 */
	public final static String BASE_DATA_VERSION_KEY = "baseDataVer";

	/**
	 * 程序版本更新标志-不更新
	 */
	public final static String PGM_VERSION_NOT_UPDATE = "0";

	/**
	 * 程序版本更新标志-不强制更新
	 */
	public final static String PGM_VERSION_UPDATE = "1";

	/**
	 * 程序版本更新标志-强制更新
	 */
	public final static String PGM_VERSION_UPDATE_FORCE = "2";

	/**
	 * 非强制更新
	 */
	public final static String VERSION_UPDATE_NOT_FORCE = "0";

	/**
	 * 强制更新
	 */
	public final static String VERSION_UPDATE_FORCE = "1";

	/**
	 * 完整更新
	 */
	public final static String BASE_DATA_VERSION_ALL = "All";

	/**
	 * 增量更新
	 */
	public final static String BASE_DATA_VERSION_INC = "Inc";

	/**
	 * 数据生效
	 */
	// 0生效
	public final static String BASE_DATA_ISACTIVE = "0";

	/**
	 * 下划线分隔符
	 */
	public final static String UNDERLINE_DELIMITER = "_";

	/**
	 * 竖线分隔符
	 */
	public final static String VERTICAL_LINE = "|";
	
	/**
	 * 空格
	 */
	public final static String BLANK_SPACE = " ";

	/**
	 * 换行
	 */
	public final static String NEWLINE = "\r\n";

	/**
	 * 接入模块日期格式
	 */
	public final static String PDA_HTTP_SERVICE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 提交是强制提交
	 */
	public final static String TASK_ISFORCESMT = "1";

	/**
	 * 表常量
	 */
	public final static String TABLE_PDA = "T_PDA";
	
	public final static String TABLE_PDA_DEL = "__T_PDA";

	/**
	 * 字段
	 */
	public final static String FILED = "filed";

	/**
	 * 类型
	 */
	public final static String TYPE = "type";


	/**基础数据表-快递员负责行政区域**/
	public final static String TABLE_NAME_EXPRESS_EMP_DISTRICT = "express_emp_district";
	/**基础数据表-快递车辆**/
	public final static String TABLE_NAME_EXPRESS_VEHICLE = "express_vehicle";
	/**基础数据表-落地配、试点城市**/
	public final static String TABLE_NAME_EXPRESS_CITY = "express_city";
	/**基础数据表-快递点部与营业部映射**/
	public final static String TABLE_NAME_EXPRESS_PART_SALES_DEPT = "express_part_sales_dept";
	
	/**基础数据表-省份**/
	public final static String TABLE_NAME_PROVINCE = "province";
	/**基础数据表-城市**/
	public final static String TABLE_NAME_CITY = "city";
	/**基础数据表-省市区域**/
	public final static String TABLE_NAME_ADDRESS = "address";
	/**基础数据表-区县**/
	public final static String TABLE_NAME_COUNTY = "county";
	/**基础数据表-路由**/
	public final static String TABLE_NAME_ROUTE = "route";
	/**基础数据表-路由明细 **/
	public final static String TABLE_NAME_ROUTE_DETAIL = "route_detail";
	/**基础数据表-运输性质**/
	public final static String TABLE_NAME_TRANSPORT = "transport";
	/**基础数据表-库位**/
	public final static String TABLE_NAME_STORE = "store";
	/**基础数据表-库位对应网点**/
	public final static String TABLE_NAME_STOREDEPT = "storeDept";
	/**基础数据表-编码**/
	public final static String TABLE_NAME_CODE = "code";
	/**基础数据表-提货网点**/
	public final static String TABLE_NAME_LADINGSTATION = "ladingStation";
	/**基础数据表-用户**/
	public final static String TABLE_NAME_USER = "user";
	/**基础数据表-部门**/
	public final static String TABLE_NAME_DEPT = "dept";
	/**基础数据表-专线**/
	public final static String TABLE_NAME_LINE = "specialline";
	/**基础数据表-始发配载部门**/
	public final static String TABLE_NAME_DEPARTDEPT = "departassemblyDept";
	/**基础数据表-AB货**/
	public final static String TABLE_NAME_ABGOODS = "abproject";
	/**基础数据表-违禁品**/
	public final static String TABLE_NAME_PROHIBITED_GOODS = "danproject";
	/**基础数据表-增值服务**/
	public final static String TABLE_NAME_ADD_SERVICE = "addservice";
	/**基础数据表-包装**/
	public final static String TABLE_NAME_PACKAGE_TYPE = "packagetype";

	public final static String TABLE_NAME_DISCOUNT_ORG = "discount_org";
	public final static String TABLE_NAME_DISCOUNT_PRIORITY = "discount_priority";
	/**基础数据表-零担时效关系**/
	public final static String TABLE_NAME_EFFECTIVE_REGION = "effective_region";
	public final static String TABLE_NAME_EFFECTIVE_REGION_ORG = "effective_region_org";
	public final static String TABLE_NAME_EFFECTIVE_PLAN_DETAIL = "effective_plan_detail";
	/**基础数据表-快递时效关系**//*
	public final static String TABLE_NAME_EXPRESS_EFFECTIVE_REGION = "express_effective_region";
	public final static String TABLE_NAME_EXPRESS_EFFECTIVE_REGION_ORG = "express_effective_region_org";
	public final static String TABLE_NAME_EXPRESS_EFFECTIVE_PLAN_DETAIL = "express_effective_plan_detail";
	*/
	public final static String TABLE_NAME_GOODSTYPE = "goodstype";
	public final static String TABLE_NAME_MARKETING_EVENT = "marketing_event";
	public final static String TABLE_NAME_MARKETING_EVENT_CHANEL = "marketing_event_chanel";
	public final static String TABLE_NAME_PRICE_PLAN = "price_plan";
	public final static String TABLE_NAME_PRICE_REGION = "price_region";
	public final static String TABLE_NAME_PRICE_REGION_ORG = "price_region_org";
	public final static String TABLE_NAME_PRICE_RULE = "price_rule";
	public final static String TABLE_NAME_PRICING_CRITERIA_DETAIL = "pricing_criteria_detail";
	public final static String TABLE_NAME_PRICING_ENTRY = "pricing_entry";
	public final static String TABLE_NAME_PRICING_VALUATION = "pricing_valuation";
	public final static String TABLE_NAME_PRODUCT = "product";
	public final static String TABLE_NAME_PRODUCT_ITEM = "product_item";
	/**基础数据表-开单组与营业部开单出发部门**/
	public final static String TABLE_NAME_SALESDEPT_BILLINGGROUP = "salesdept_billinggroup";
	
	/**基础数据表-网点组**/
	public final static String TABLE_NAME_NET_GROUP = "net_group";
	/**基础数据表-星号部门**/
	public final static String TABLE_NAME_SALESDEPT_ASTERISK = "salesDept_asterisk";
	
	/**基础数据-AB货**/
	public final static String  DATA_AB = "com.deppon.pda.bdm.module.foss.upgrade.shared.domain.ABGoodsEntity";
	/**基础数据-增值服务**/
	public final static String  DATA_ADD = "com.deppon.pda.bdm.module.foss.upgrade.shared.domain.AddServiceEntity";
	/**基础数据-违禁品**/
	public final static String  DATA_PROHIBITED_GOODS = "com.deppon.pda.bdm.module.foss.upgrade.shared.domain.ProhibitedGoodsEntity";
	/**基础数据-AB货**/
	public final static String  DATA_PACKAGE_TYPE = "com.deppon.pda.bdm.module.foss.upgrade.shared.domain.PackageTypeEntity";
	/**
	 * 扫描数据同步状态
	 */
	/**
	 * 0:未导出
	 */
	public static int SYNC_STATUS_INIT = 0;

	/**
	 * 1:已读取
	 */
	public static int SYNC_STATUS_READED = 1;

	/**
	 * 2:成功
	 */
	public static int SYNC_STATUS_SYNCED = 2;
	/**
	 * 3:同步系统异常
	 */
	public static int SYNC_STATUS_FAILED = 3;
	
	/**
	 * 3:同步业务异常
	 */
	public static int SYNC_STATUS_BUSSINESS_FAILED = 4;
	
	public static String SYS_SUCCESS = "success";

	/**
	 * 一天的毫秒数
	 */
	public static final long DAY_MILLIS = 86400000;

	/**
	 * 不删除文件
	 */
	public static String NO_DEL_FILE = "0";

	public final static String BLANK = " ";

	/**
	 * 回车符
	 */
	public final static String FORMAT_ENTER = "\r";

	/**
	 * 换行符
	 */
	public final static String FORMAT_NEWLINE = "\n";

	/**
	 * 转换符号
	 */
	public final static String FORMAT_TRANSFORM = "^";
	/**
	 * 转换符号
	 */
	public final static String FORMAT_VIRGULE = "/";

	/**
	 * 文件锁文件
	 */
	public final static String GEN_LCK_FILE = "genFile.lck";


	/**
	 * 正确标示
	 */
	public static String SYS_TRUE_FLAG = "1";
	/**
	 * 错误标示
	 */
	public static String SYS_FALSE_FLAG = "0";

	
	
	public final static class WAYBILLTYPE{
		/**
		 * 普通运单
		 */
		public static final String  NORMAL_WAYBILL= "0";
		/**
		 * 快递运单
		 */
		public static final String EXP_WAYBILL = "1";
		
		/**
		 * 大客户电子运单
		 */
		public static final String  E_BIGCUSTOMER_WAYBILL= "2";
		/**
		 * 散客电子运单
		 */
		public static final String E_INDIVIDUALCUSTOMER_WAYBILL = "3";

		/**
		 * 电子运单二期 （菜鸟项目）
		 */
		public static final String E_WAYBILL = "4";
	}
	
	

	
	public static final String UPLOAD_UPLOADJSONPART = "uploadJsonPart";
	
	public static final String UPLOAD_PDAINFOJSONPART= "pdaInfoJsonPart";

	//linux 图片上传碎片保存地址
	public static final String UPLOAD_IMAGE_SNIPPET_PATH = "/app01/ocb/temp/";
	//windows 图片上传碎片保存地址
//	public static String UPLOAD_IMAGE_SNIPPET_PATH = "e:/App01/ocb/temp/";

	//Linux 下运单图片保存地址
	public static final String UPLOAD_IMAGE_PATH = "/app01/ocb";
	//windows下运单图片保存地址
//	public static String UPLOAD_IMAGE_PATH = "e:/App01/ocb";
	
	/**
	 * 删除以前的图片 此时为30天前的图片
	 */
	public static final Integer PICTURE_OVER_DAYS = 30;
	
	//Linux 电子小票存放路径
	public static final String ELECTRONIC_TICKET_PATH = "/app01/swipeimage_new/";
	
	//Linux 电子小票存放路径
//	public static final String ELECTRONIC_TICKET_PATH = "D:/app01/swipeimage/";
	
	//基础资料版本常量     245955     SONAR
	public static final int DATAVER_BASE_DATA=8;
	//数据下载服务类 增量或全量 天数
	public static final int dayDate=30;
	//装车用到流水号
	public final static int SERIALNUM = 32; 
	//外请车司机所使用工号
	public final static String EXTERNAL_DRIVER = "000000";

}
