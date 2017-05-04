package com.deppon.pda.bdm.module.core.shared.constants;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 操作常量类
 * 
 * @author chengang
 * @version 1.0
 * @created 2012-9-6 下午03:09:18
 */
public class OperationConstant {
	/*****************************************************************
	 * 
	 * 服务名常量
	 * 
	 ****************************************************************/
	/**
	 * 基础数据下载
	 */
	public final static String SERVICE_SYS_DATAVER = "baseDataUpdGenService";

	/**
	 * 程序版本下载
	 */
	public final static String SERVICE_SYS_PGMVER = "pgmUpgradeService";

	/**
	 * 登录服务
	 */
	public final static String SERVICE_SYS_LOGIN = "pdaLoginService";
	/**
	 * 登出服务
	 */
	public final static String SERVICE_SYS_LOGINOUT = "pdaLoginOutService";

	/**
	 * 接收接货订单服务
	 */
	public final static String SERVICE_ACCT_ORDER = "acctOrderService";

	/**
	 * 订单已接收服务
	 */
	public final static String SERVICE_RVC_ORDER = "rvcOrderService";

	/**
	 * 订单已读取服务
	 */
	public final static String SERVICE_READ_ORDER = "readOrderService";

	/**
	 * 订单已处理
	 */
	public final static String SERVICE_PROCESS_ORDER = "processOrderService";

	/**
	 * 退回订单
	 */
	public final static String SERVICE_BACK_ORDER = "backOrderService";

	/**
	 * 现场开单
	 */
	public final static String SERVICE_BILLING = "billingService";

	/**
	 * 重打标签
	 */
	public final static String SERVICE_REPRT = "reprtLabelService";

	/**
	 * 完成接货
	 */
	public final static String SERVICE_FINISH = "acctFinishService";
	
	/**
	 * 建立清仓任务
	 */
	public final static String SERVICE_CLEAR_TASK_CREATE = "createClearTaskService";

	/**
	 * 刷新清仓任务
	 */
	public final static String SERVICE_CLEAR_TASK_REFRESH = "refreshClearTaskService";

	/**
	 * 清仓扫描
	 */
	public final static String SERVICE_CLEAR_SCAN = "clearScanService";

	/**
	 * 提交清仓任务
	 */
	public final static String SERVICE_CLEAR_TASK_SUBMIT = "smtClearTaskService";

	/**
	 * 完成清仓任务
	 */
	public final static String SERVICE_CLEAR_TASK_FINISH = "finishClearTaskService";

	/**
	 * 撤销清仓任务
	 */
	public final static String SERVICE_CLEAR_TASK_CANCEL = "cancelClearTaskService";

	/**
	 * 单票入库
	 */
	public final static String SERVICE_SINGLE_VOTE_ININVT = "singleVoteScanService";

	/**
	 * 车辆到达
	 */
	public final static String SERVICE_TRUCK_ARR = "truckArrService";

	/**
	 * 车辆出发
	 */
	public final static String SERVICE_TRUCK_DPRT = "truckDprtService";

	/**
	 * 货物追踪
	 */
	public final static String SERVICE_CRG_TRACE = "crgTraceService";

	/**
	 * 客户自提
	 */
	public final static String SERVICE_DERY_SELF = "selfdeliveryService";

	/**
	 * 派送异常
	 */
	public final static String SERVICE_DERY_EXCP = "deryExceptionService";

	/**
	 * 异常签收
	 */
	public final static String SERVICE_DERY_EXCPSIGN = "excpSignService";

	/**
	 * 按件正常签收
	 */
	public final static String SERVICE_DERY_SIGNBYPC = "normSignByPcService";

	/**
	 * 按票正常签收
	 */
	public final static String SERVICE_DERY_SIGNBYVOTE = "normSignByVoteService";

	/**
	 * 达到联校验
	 */
	public final static String SERVICE_DERY_WAYBILLINFO = "selfdeliveryWayBillInfoService";

	/**
	 * 获取派送任务
	 */

	public final static String SERVICE_DERY_GETDERYTASK = "getDeliveryTaskService";

	/**
	 * 建立卸车任务
	 */

	public final static String SERVICE_UNLD_CRTUNLDTASK = "crtUnldTaskService";
	/**
	 * 撤销卸车任务
	 */

	public final static String SERVICE_UNLD_CACLUNLDTASK = "caclUnldTaskService";
	/**
	 * 撤销卸车扫描
	 */

	public final static String SERVICE_UNLD_CACLUNLDSCAN = "caclUnldService";
	/**
	 * 完成卸车任务
	 */

	public final static String SERVICE_UNLD_FNSHUNLDTASK = "fnshUnldTaskService";
	/**
	 * 补录体积重量
	 */

	public final static String SERVICE_UNLD_MKPWGTVOL = "mkpWgtVolService";
	/**
	 * 刷新卸车任务
	 */

	public final static String SERVICE_UNLD_RSHUNLDTASK = "refreshUnloadTaskService";
	/**
	 * 提交卸车任务
	 */

	public final static String SERVICE_UNLD_SMTUNLDTASK = "smtUnldTaskService";
	/**
	 * 卸车扫描
	 */

	public final static String SERVICE_UNLD_UNLDSCAN = "unloadScanService";
	/**
	 * 建立装车任务
	 */

	public final static String SERVICE_LOAD_CRTLOADTASK = "createLoadTaskService";
	/**
	 * 取消装车扫描
	 */

	public final static String SERVICE_LOAD_CACLSCAN = "caclLoadService";
	/**
	 * 撤销装车任务
	 */

	public final static String SERVICE_LOAD_CACLLOADTASK = "caclLoadTaskService";
	/**
	 * 完成装车任务
	 */

	public final static String SERVICE_LOAD_FNSHLOADTASK = "fnshLoadTaskService";
	/**
	 * 装车封签
	 */

	public final static String SERVICE_LOAD_LOADLABEL = "loadLabelService";
	/**
	 * 装车扫描
	 */

	public final static String SERVICE_LOAD_LOADSCAN = "loadScanService";
	/**
	 * 刷新装车任务
	 */

	public final static String SERVICE_LOAD_RFSLOADTASK = "rfshLoadTaskService";
	/**
	 * 提交装车任务
	 */

	public final static String SERVICE_LOAD_SMTLOADTASK = "smtLoadTaskService";
	/**
	 * 异步处理服务类
	 */
	public final static String SERVICE_ASYNMSG_UPLOAD = "asyncMsgUploadService";
	/*****************************************************************
	 * 
	 * 操作类型与服务映射关系
	 * 
	 ****************************************************************/

	/**
	 * 操作类型与服务映射MAP
	 */
	public final static Map<String, String> OPER_SERVICE_MAP = new HashMap<String, String>();

	public final static Set<String> ASYNC_OPER_TYPE_LIST = new HashSet<String>();

}
