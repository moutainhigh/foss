package com.deppon.foss.module.pickup.order.server.hdrule;



/**
 * 
 * 配置文件读取类
 * 
 * @author chengang
 * @version 1.0
 * @created 2012-9-20 下午03:57:54
 */

public class PropertyConstant {


	private static final String[] confFile = { "/dubbo.properties"};

	public static PropertyManager confPropertyManager = PropertyManager.load( confFile, PropertyConstant.class);
	
	/**
	 * 灰度规则基本参数
	 *   	1.按照百分比percent
	 *   	2.按照次数 time
	 */
	public static final String RULE_NAMESPACE = confPropertyManager.getString("rule.namespace")==null?confPropertyManager.getString("rule.namespace"):"foss-flow-rps-rule";
	public static final String ZK_ADDRESS = confPropertyManager.getString("zk.address")==null?confPropertyManager.getString("zk.address"):"zookeeper://10.230.28.67:2181";
	public static final String ENCODING = "UTF-8";
	public static final String RULE_PERCENT_DEFAULT = confPropertyManager.getString("rule.percent.default")==null?confPropertyManager.getString("rule.percent.default"):"0";
	public static final String RULE_TIME_DEFAULT = confPropertyManager.getString("rule.time.default")==null?confPropertyManager.getString("rule.time.default"):"0";
	public static final String RULE_RETYR_TIMESPACE =confPropertyManager.getString( "rule.retry.timespace")==null?confPropertyManager.getString( "rule.retry.timespace"):"10000";
	public static final String RULE_PERCENT_PATH = confPropertyManager.getString("rule.percent.path")==null?confPropertyManager.getString("rule.percent.path"):"/percent";
	public static final String RULE_TIME_PATH = confPropertyManager.getString("rule.time.path")==null?confPropertyManager.getString("rule.time.path"):"::time";
	
}
