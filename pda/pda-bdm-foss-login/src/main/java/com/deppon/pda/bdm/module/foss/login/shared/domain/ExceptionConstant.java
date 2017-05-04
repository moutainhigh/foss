package com.deppon.pda.bdm.module.foss.login.shared.domain;

/**
 * 异常码常量类
 * 
 * @author chengang
 * @version 1.0
 * @created 2012-9-6 下午03:30:59
 */

public class ExceptionConstant {

	/**
	 * 异常基码
	 */
	public static final String ERRCODE_BASE = "BDM-0000";

	/**
	 * 系统异常
	 */
	public static final String ERRCODE_SYS = "BDM-1000";

	/********************************************************
	 * 通用系统异常
	 ********************************************************/
	/**
	 * 无效参数
	 */
	public static final String ERRCODE_SYS_INVALID_ARG = "BDM-1101";

	/**
	 * 无效参数-非空
	 */
	public static final String ERRCODE_SYS_INVALID_NOT_NULL = "BDM-1102";

	/**
	 * 缓存服务异常
	 */
	public static final String ERRCODE_SYS_MEMCACHE_ERR = "BDM-1103";

	/********************************************************
	 * 工具类系统异常
	 ********************************************************/
	/**
	 * JSON解析异常
	 */
	public static final String ERRCODE_SYS_JSON_PARSE_ERR = "BDM-1201";
	/**
	 * JSON格式异常
	 */
	public static final String ERRCODE_SYS_JSON_FORMAT_ERR = "BDM-1202";
	/**
	 * JSON转换到BEAN异常
	 */
	public static final String ERRCODE_SYS_JSON_MAPPING_ERR = "BDM-1203";
	/**
	 * JSON封装异常
	 */
	public static final String ERRCODE_SYS_JSON_ENCAP_ERR = "BDM-1204";

	/********************************************************
	 * 公共模块系统异常（已BDM-21开头）
	 ********************************************************/

	/********************************************************
	 * 系统功能模块系统异常（已BDM-22开头）
	 ********************************************************/

	/********************************************************
	 * 接货模块系统异常（已BDM-23开头）
	 ********************************************************/
	/**
	 * 连接超时
	 */
	public static final String ERRCODE_BUSI_ERP_CONNECTiON_TIMEOUT = "BDM_2301";
	/********************************************************
	 * 送货模块系统异常（已BDM-24开头）
	 ********************************************************/

	/********************************************************
	 * 装车模块系统异常（已BDM-25开头）
	 ********************************************************/
	/********************************************************
	 * 卸车模块系统异常（已BDM-26开头）
	 ********************************************************/

	/********************************************************
	 * 出发到达模块系统异常（已BDM-27开头）
	 ********************************************************/

	/********************************************************
	 * 清仓模块系统异常（已BDM-28开头）
	 ********************************************************/
	/********************************************************
	 *货物登记模块系统异常（已BDM-29开头）
	 ********************************************************/
	/********************************************************
	 * 综合查询模块系统异常（已BDM-30开头）
	 ********************************************************/

	/**
	 * 业务异常
	 */
	public static final String ERRCODE_BUSI = "BDM-5000";

	/*************************************************************
	 * 通用业务异常
	 *************************************************************/
	/**
	 * ERP调用异常
	 */
	public static final String ERRCODE_BUSI_ERP_INTERFACE_ERR = "BDM-5001";

	/**
	 * 扫描数据同步异常（需要重新同步）
	 */
	public static final String ERRCODE_BUSI_SYNC_REDO_ERR = "BDM-5002";

	/**
	 * 扫描数据同步异常（不需要重新同步）
	 */
	public static final String ERRCODE_BUSI_SYNC_NON_REDO_ERR = "BDM-5003";

	/**
	 * 扫描数据不存在异常
	 */
	public static final String ERRCODE_BUSI_SYNC_NOT_EXIST_ERR = "BDM-5004";

	/*************************************************************
	 * 公共模块业务异常（已BDM-61开头）
	 * ***********************************************************
	 */

	/*************************************************************
	 * 系统功能模块业务异常（已BDM-62开头）
	 * ***********************************************************
	 */
	/**
	 * 用户不存在异常
	 */
	public static final String ERRCODE_BUSI_LOGIN_ERR = "BDM-6201";
	
	/**
	 * 密码异常
	 */
	public static final String ERRCODE_BUSI_PASSWORD_ERR = "BDM-6202";

	/**
	 * 部门不匹配异常
	 */
	public static final String ERRCODE_BUSI_DEPT_ERR = "BDM-6203";

	/**
	 * 设备信息不存在异常
	 */
	public static final String ERRCODE_BUSI_DEVICE_ERR = "BDM-6204";

	/**
	 * 设备已禁用异常
	 */
	public static final String ERRCODE_BUSI_DIS_DEVICE_ERR = "BDM-6205";

	/**
	 * 设备所属部门不匹配
	 */
	public static final String ERRCODE_BUSI_DEPTUNMATCH_ERR = "BDM_6206";

	/**
	 * 设备所属人不匹配
	 */
	public static final String ERRCODE_BUSI_USERUNMATCH_ERR = "BDM_6208";

	/**
	 * 未找到程序版本信息
	 */
	public static final String ERRCODE_BUSI_PGMVER_NOTFOUND_ERR = "BDM_6207";

	/**
	 * 未找到数据版本信息
	 */
	public static final String ERRCODE_BUSI_DATAVER_NOTFOUND_ERR = "BDM_6208";
	/**
	 * 存在未完成任务异常
	 */
	public static final String ERRCODE_BUSI_LOGIN_TASKNOFNSH = "BDM_6209";
	/**
	 * 存在未提交任务异常
	 */
	public static final String ERRCODE_BUSI_LOGIN_TASKNOSMT = "BDM_6210";

	/**
	 * 用户类型异常
	 */
	public static final String ERRCODE_BUSI_LOGIN_USERTYPE = "BDM-6211";

	/**
	 * 数据库无版本信息
	 */
	public static final String ERRCODE_BUSI_PGMVER_DATABASE = "BDM-6212";
	/**
	 * 用户已登录异常
	 */
	public static final String ERRCODE_BUSI_HAS_LOGIN = "BDM-6213";
	
	/**
	 * 数据库无更新数据异常
	 */
	public static final String ERRCODE_BUSI_DATA_NOT_FIND = "BDM-6214";
	
	/**
	 * 连接服务器超时异常
	 */
	public static final String ERRCODE_BUSI_TIME_OUT = "BDM-6215";

	/**
	 * 连接服务器超时异常
	 */
	public static final String ERRCODE_BUSI_USERINFO_NOTWHOLE = "BDM-6216";
	
	/**
	 * 快递车辆表中不存在用户
	 */
	public static final String EXPRESS_VEHICLE_NOT_EXIST_USER = "BDM-6217";
	
	/**
	 * 快递员营业部无法获取快递配载外场部门!
	 */
	public static final String DEPARTASSEMBLYDEPT_NOT_EXIST = "BDM-6218";
	
	/**
	 * 快递员营业部无法获取出发部门!
	 */
	public static final String SOURCEDEPT_NOT_EXIST = "BDM-6219";
	
	/**
	 * 司机没有对应的顶级车队!
	 */
	public static final String TOPFLEETDEPT_NOT_EXIST = "BDM-6220";
	/**
	 * 司机没有对应的始发驻地营业部
	 */
	public static final String ERRCODE_BUSI_STATION_NOTFOUND_ERR = "BDM-6221";
	
	/*************************************************************
	 * 接货模块业务异常（已BDM-63开头）
	 * ***********************************************************
	 */
	/**
	 * 未找到开单信息异常
	 */
	public static final String ERRCODE_BUSI_BILL_NOTFOUND_ERR = "BDM_6301";
	/**
	 * 未找到开单标签信息
	 */
	public static final String ERRCODE_BUSI_BILLLABEL_NOTFOUND_ERR = "BDM_6302";

	/*************************************************************
	 * 送货模块业务异常（已BDM-64开头）
	 * ***********************************************************
	 */
	/*************************************************************
	 * 装车模块业务异常（已BDM-65开头）
	 * ***********************************************************
	 */
	/**
	 *装车任务不存在异常
	 */
	public static final String ERRCODE_BUSI_LOAD_LOADTASKNOEXIST_ERR = "BDM-6501";
	/**
	 *装车任务已开始异常
	 */
	public static final String ERRCODE_BUSI_LOAD_LOADTASKHASSTART_ERR = "BDM-6502";
	/**
	 *完成装车存在没完成用户
	 */
	public static final String ERRCODE_BUSI_LOAD_LOADNOFNSHEXIST_ERR = "BDM-6503";
	/**
	 *提交任务用户非创建用户异常
	 */
	public static final String ERRCODE_BUSI_LOAD_SMTISNOTADMIN_ERR = "BDM-6504";
	/**
	 *任务已提交异常
	 */
	public static final String ERRCODE_BUSI_LOAD_LOADTASKHASSMT_ERR = "BDM-6505";
	/**
	 *任务已建立异常
	 */
	public static final String ERRCODE_BUSI_LOAD_LOADTASKHASCRT_ERR = "BDM-6506";
	/**
	 * 任务需要任务号异常
	 */
	public static final String ERRCODE_BUSI_LOAD_TASKNEEDCODE_ERR = "BDM-6507";
	/**
	 * 任务号与车牌号不匹配
	 */
	public static final String ERRCODE_BUSI_LOAD_TASKNOMACTHTRUCK_ERR = "BDM-6508";
	/**
	 * 任务号与任务类型不匹配
	 */
	public static final String ERRCODE_BUSI_LOAD_TASKCODENOTMACTHTASKTYPE_ERR = "BDM-6509";
	/*************************************************************
	 * 卸车模块业务异常（已BDM-66开头）
	 * ***********************************************************
	 */
	/**
	 *卸车任务已开始异常
	 */
	public static final String ERRCODE_BUSI_UNLOAD_UNLDTASKHASSTART_ERR = "BDM-6601";
	/**
	 *卸车任务不存在异常
	 */
	public static final String ERRCODE_BUSI_UNLOAD_UNLDTASKNOEXIST_ERR = "BDM-6602";
	/**
	 *完成卸车存在没完成用户
	 */
	public static final String ERRCODE_BUSI_UNLOAD_UNLDNOFNSHEXIST_ERR = "BDM-6603";
	/**
	 *提交任务用户非创建用户异常
	 */
	public static final String ERRCODE_BUSI_UNLOAD_SMTISNOTADMIN_ERR = "BDM-6604";
	/**
	 *任务已提交异常
	 */
	public static final String ERRCODE_BUSI_UNLOAD_UNLDTASKHASSMT_ERR = "BDM-6605";
	/**
	 *任务已建立异常
	 */
	public static final String ERRCODE_BUSI_UNLOAD_UNLDTASKHASCRT_ERR = "BDM-6606";

	/**
	 * 建立任务需要任务号
	 */
	public static final String ERRCODE_BUSI_UNLOAD_TASKNEEDCODE_ERR = "BDM-6607";
	/**
	 * 任务号与车牌号不匹配
	 */
	public static final String ERRCODE_BUSI_UNLOAD_TASKNOMACTHTRUCK_ERR = "BDM-6608";
	/**
	 * 存在未补录重量体积异常
	 */
	public static final String ERRCODE_BUSI_UNLOAD_UNLDTASKHASNOMKP_ERR = "BDM-6609";
	/**
	 * 存在未扫描的货物
	 */
	public static final String ERRCODE_BUSI_UNLOAD_CRGNOSCAN_ERR = "BDM-6610";
	/**
	 * 已经补录过体积重量
	 */
	public static final String ERRCODE_BUSI_UNLOAD_HASMKP_ERR = "BDM-6611";
	/**
	 * 创建员工与任务号不匹配
	 */
	public static final String ERRCODE_BUSI_UNLOAD_USERNOMACTHTASK_ERR = "BDM-6612";
	/*************************************************************
	 * 出发到达模块业务异常（已BDM-67开头）
	 * ***********************************************************
	 */
	/*************************************************************
	 * 清仓模块业务异常（已BDM-68开头）
	 * ***********************************************************
	 */
	/**
	 * 任务已完成
	 */
	public static final String ERRCODE_BUSI_CLEAR_FINISHED_TASK_ERR = "BDM_6801";

	/**
	 * 任务已提交
	 */
	public static final String ERRCODE_BUSI_CLEAR_SUBMITED_TASK_ERR = "BDM_6802";

	/**
	 * 任务已取消
	 */
	public static final String ERRCODE_BUSI_CLEAR_CANCELED_TASK_ERR = "BDM_6803";

	/**
	 * 任务未完成
	 */
	public static final String ERRCODE_BUSI_CLEAR_UNFINISHED_TASK_ERR = "BDM_6804";

	/**
	 * 任务已扫描
	 */
	public static final String ERRCODE_BUSI_CLEAR_SCANED_TASK_ERR = "BDM_6805";
	/*************************************************************
	 * 货物登记模块业务异常（已BDM-69开头）
	 * ***********************************************************
	 */
	/*************************************************************
	 * 综合查询模块业务异常（已BDM-70开头）
	 * ***********************************************************
	 */

}
