package com.deppon.foss.module.transfer.lostwarning.api.server.define;

import java.util.HashMap;
import java.util.Map;

import com.deppon.foss.module.transfer.lostwarning.api.shared.dto.TransferMotorcadeDto;

/**
 * 丢货预警项目常量类
 * 
 * 项目名称：tfr-lostwarning-api
 * 
 * 类名称：LostWarningConstant
 * 
 * 创建人：lyz 263072
 * 
 * 创建时间：2015-7-18 下午4:29:56
 * 
 * 版权所有：上海德邦物流有限公司
 */
public class LostWarningConstant {
	
	//找到处理内容模板
	public final static String dealFindContentStr = "系统自动处理";
	
	//JMS-ESB服务CODE
	public final static String ESB_JMS_SERVICE_CODE = "ESB_QMS2ESB_CARGO_VIRTUAL";
	
	//JMS服务请求描述
	public final static String ESB_JMS_SERVICE_DESC = "转移丢货库存信息至虚拟库存组织";
	
	//JMS ESB服务请求头版本信息
	public static final String ESB_ACCESSHEADER_VERSION = "1.0";
	
	//捞取丢货数据的起始时间
	public static String SQL_QUERY_STARTTIME = "";
	
	//特殊组织入库的部门编码
	public static String INVALID_ORG_CODE = "W01000301050203";
	
	/**
	 * 存放部门和所属车队对应关系————key: 部门编码    value:部门、车队信息
	 */
	public static Map<String,TransferMotorcadeDto> motorCadeMap = new HashMap<String,TransferMotorcadeDto>();
	
	/**
	 * 丢货预警类型和其处理方法名 对应关系
	 */
	public static Map<String,String> warningTypeMethodNameMap = new HashMap<String, String>(){
		{
			put("1", "analyWarningData_startDtpStock");//1、出发库存丢货上报
			put("2", "analyWarningData_JZReceive");//2、集中接货丢货上报
			put("3", "analyWarningData_transfer");//3、运输丢货上报
			put("4", "analyWarningData_alreadyArrive");//4、已到达丢货上报
			put("6", "analyWarningData_transferStore");//6、中转库存丢货上报
			put("7", "analyWarningData_handover");//7、已交接丢货上报
			put("8", "analyWarningData_differStock");//8、到达库存丢货上报
			put("9", "analyWarningData_deliver");//9、派送丢货上报
			put("10", "analyWarningData_airTransfer");//10、空运丢货上报 
			put("11", "analyWarningData_expressExternal");//11、快递外发丢货上报
		}
	};
	
	
	/**
	 * 丢货预警类型和其处理方法名 对应关系
	 */
	public static Map<String,Double> timeLimitMap = new HashMap<String, Double>(){
		{
			// 集中接货丢货时间限制（单位：小时）
			put("TIMELIMIT_JZRECEIVE", 168.0);
			// 运输丢货时间限制——快递 短途（单位：小时）
			put("TIMELIMIT_TRANSPORT_KD_SHORTDISTANCE", 12.0);
			// 运输丢货时间限制——快递 长途（单位：小时）
			put("TIMELIMIT_TRANSPORT_KD_LONGDISTANCE", 24.0);
			// 运输丢货时间限制——零担 短途（单位：小时）
			put("TIMELIMIT_TRANSPORT_LD_SHORTDISTANCE", 48.0);
			// 运输丢货时间限制——零担 长途（单位：小时）
			put("TIMELIMIT_TRANSPORT_LD_LONGDISTANCE", 48.0);
			// 已到达丢货时间限制——快递（单位：小时）
			put("TIMELIMIT_ALARRIVE_KD", 36.0);
			// 已到达丢货时间限制——零担（单位：小时）
			put("TIMELIMIT_ALARRIVE_LD", 168.0);
			// 中转库存丢货时间限制——快递（单位：小时）
			put("TIMELIMIT_TRANSFERSTORE_KD", 48.0);
			// 中转库存丢货时间限制——零担 快车（单位：小时）
			put("TIMELIMIT_TRANSFERSTORE_LD_FAST", 74.0);
			// 中转库存丢货时间限制——零担 慢车（单位：小时）
			put("TIMELIMIT_TRANSFERSTORE_LD_SLOW", 98.0);
			// 已交接丢货时间限制——正常装车（单位：小时）
			put("TIMELIMIT_HANDOVER", 168.0);
			// 已交接丢货时间限制——派送装车（单位：小时）
			put("TIMELIMIT_HANDOVER_DELIVER", 60.0);
			// 到达库存丢货差异报告次数限制——快递（单位：次数）
			put("COUNTLIMIT_DIFFERSTOCK_KD", 1.0);
			// 到达库存丢货差异报告次数限制——零担（单位：次数）
			put("COUNTLIMIT_DIFFERSTOCK_LD", 3.0);
			// 空运丢货时间限制（单位：小时）
			put("TIMELIMIT_AIRTRANSPORT", 168.0);
			// 外发丢货时间限制（单位：小时）
			put("TIMELIMIT_EXPRESSEXTERNAL", 168.0);
		}
	};
	
	/**
	 * 读取数据限制条数
	 */
	public static int dataLimitCount=1000;
	
	public static final String LOADDATA_COUNTLIMIT = "LOADDATA_COUNTLIMIT";
	
	public static final Map<String,String> LOSTTYPE_MAP_LD =  new HashMap<String, String>(){
		{
			put("1", "出发库存丢货");
			put("2", "运输丢货");
			put("3", "已到达丢货");
			put("4", "卸车丢货");
			put("5", "中转库存丢货");
			put("6", "已交接丢货");
			put("7", "到达库存丢货");
			put("8", "接货丢货");
			put("9", "派送丢货");
			put("10", "空运丢货");
		}
	};
	
	public static final Map<String,String> LOSTTYPE_MAP_KD =  new HashMap<String, String>(){
		{
			put("1", "出发库存丢货");
			put("2", "运输丢货");
			put("3", "卸车丢货");
			put("4", "卸车校验丢货");
			put("5", "中转库存丢货");
			put("6", "到达库存丢货");
			put("7", "派送丢货");
			put("8", "外发丢货");
		}
	};
	/**
	 * 找到场景-卸车丢货找到
	 */
	public static final String FIND_SCENE_UNLOAD = "4";
	
	/**sonar_constants_number*/
	public final static int SONAR_NUMBER_0 = 0;
	public final static int SONAR_NUMBER_1 = 1;
	public final static int SONAR_NUMBER_2 = 2;
	public final static int SONAR_NUMBER_3 = 3;
	public final static int SONAR_NUMBER_4 = 4;
	public final static int SONAR_NUMBER_5 = 5;
	public final static int SONAR_NUMBER_6 = 6;
	public final static int SONAR_NUMBER_7 = 7;
	public final static int SONAR_NUMBER_8 = 8;
	public final static int SONAR_NUMBER_9 = 9;
	public final static int SONAR_NUMBER_10 = 10;
	public static final int SONAR_NUMBER_100 = 100;
	public static final int SONAR_NUMBER_150 = 150;
	public static final int SONAR_NUMBER_200 = 200;
	public static final int SONAR_NUMBER_500 = 500;
	public static final int SONAR_NUMBER_650 = 650;
	public static final int SONAR_NUMBER_1000 = 1000;
	/**
	 * 入库表同步到新建的入库信息表中循环次数限制
	 */
	public static int INSTOCK_TO_NEWRECORD = 15;

}
