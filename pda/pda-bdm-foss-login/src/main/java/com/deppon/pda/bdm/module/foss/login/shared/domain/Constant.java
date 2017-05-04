package com.deppon.pda.bdm.module.foss.login.shared.domain;

/**
 * 常量类
 * 
 * @author chengang
 * @version 1.0
 * @created 2012-9-6 下午06:52:39
 */

public class Constant {

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
	public final static String LOADTYPE_DERY = "8";
	public final static String LOADTYPE_LONG = "2";
	public final static String LOADTYPE_SHORT = "3";

	// 卸车
	public final static String TASKTYPE_UNLD = "4";
	//清仓
	public final static String TASKTYPE_CLEAR = "10";
	/**
	 * 调用接口返回结果状态(1表示成功，0表示失败重发，2表示失败不重发)
	 */
	// 成功
	public final static int SUCCESS = 1;
	// 失败重发
	public final static int FAILURE = 0;
	// 失败不重发
	public final static int FAILURE_NORET = 2;
	//运单号不存在
	public final static int WBL_NOTEXEIST=3;
	/**
	 * 设备状态
	 */
	// 启用
	public final static String ENABLE_DEVICE = "1";
	// 禁用
	public final static String DISABLE_DEVICE = "0";

	/**
	 * 登录用户类型
	 */
	// 司机
	public final static String DRIVER_LOGIN_USERTYPE = "1";

	/**
	 * 订单状态
	 */
	// 已接收
	public final static String ORDER_STATUS_RCV = "0";
	// 已读取
	public final static String ORDER_STATUS_READ = "1";
	// 已退回
	public final static String ORDER_STATUS_BACK = "3";
	// 已处理
	public final static String ORDER_STATUS_PROCESS = "2";

	/**
	 * PDA程序版本标识
	 */
	public final static String PGM_VERSION_KEY = "pgmVer";

	/**
	 * PDA设备标识
	 */
	public final static String DEVICE_KEY = "pda_";
	
	/**
	 * 用户标识
	 */
	public final static String USER_KEY = "user_";
	
	/**
	 * 部门标识
	 */
	public final static String DEPT_KEY = "dept_";

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
	 * 程序版本更新标志-更新
	 */
	public final static String PGM_VERSION_UPD = "3";

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
	 * 空格
	 */
	public final static String BLANK = " ";

	/**
	 * 竖线分隔符
	 */
	public final static String VERTICAL_LINE = "|";

	/**
	 * 回车换行
	 */
	public final static String NEWLINE = "\r\n";

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

	/**
	 * 字段
	 */
	public final static String FILED = "filed";

	/**
	 * 类型
	 */
	public final static String TYPE = "type";

	/**
	 * 表名
	 */
	// 省
	public final static String TABLE_NAME_PROVINCE = "province";

	// 市
	public final static String TABLE_NAME_CITY = "city";

	// 区
	public final static String TABLE_NAME_COUNTY = "county";

	public final static String TABLE_NAME_ROUTE = "route";

	public final static String TABLE_NAME_ROUTE_DETAIL = "route_detail";

	public final static String TABLE_NAME_TRANSPORT = "transport";

	public final static String TABLE_NAME_STORE = "store";

	public final static String TABLE_NAME_STOREDEPT = "storeDept";

	public final static String TABLE_NAME_DEPARTDEPT = "departassemblyDept";

	public final static String TABLE_NAME_LADINGSTATION = "ladingStation";

	public final static String TABLE_NAME_USER = "user";

	public final static String TABLE_NAME_DEPT = "dept";

	public final static String TABLE_NAME_CODE = "code";

	public final static String TABLE_NAME_LINE = "specialline";

	/**
	 * 扫描数据同步状态
	 */
	/**
	 * 0:未导出
	 */
	public static String SYNC_STATUS_INIT = "0";

	/**
	 * 1:已读取
	 */
	public static String SYNC_STATUS_READED = "1";

	/**
	 * 2:成功
	 */
	public static String SYNC_STATUS_SYNCED = "2";

	/**
	 * 3:导出时出现异常
	 */
	public static String SYNC_STATUS_REPEAT = "3";

	/**
	 * 重复数据
	 */
	public static String SYNC_STATUS_DUPLICATE = "4";
	/**
	 * 异常数据
	 */
	public static String SYNC_STATUS_EXCE = "4";

	/**
	 * 一天的毫秒数
	 */
	public static long DAY_MILLIS = 86400000;

	/**
	 * 
	 */
	public static int MEMCACHED_MILLISECOND = 60 * 1000;

	/**
	 * 不删除文件
	 */
	public static String NO_DEL_FILE = "0";

	/**
	 * 删除文件并备份文件
	 */
	public static String DEL_FILE_BACKUP = "1";
	/**
	 * 正确标示
	 */
	public static String SYS_TRUE_FLAG = "1";
	/**
	 * 错误标示
	 */
	public static String SYS_FALSE_FLAG = "0";

	/**
	 * 1970-01-01 00:00:00
	 */
	public static long FIRST_ORACLE_TIME = -28800000;

	/**
	 * 基础数据新增数据标志
	 */
	public static String ORACLE_ADD = "1";

	/**
	 * 基础数据修改标志
	 */

	public static String ORACLE_UPDATE = "2";
	/**
	 * 
	 * 基础数据删除标志
	 */
	public static String ORACLE_DELETE = "3";
	/**
	 *扫描
	 */
	public static String BY_SCAN = "1";
	/**
	 * 手输
	 */
	public static String BY_HAND = "0";
	
	/**
	 * 文件锁文件
	 */
	public final static String GEN_LCK_FILE = "genFile.lck";
}
