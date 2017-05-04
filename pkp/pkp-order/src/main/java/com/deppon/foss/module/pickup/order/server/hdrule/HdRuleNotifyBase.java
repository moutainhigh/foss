package com.deppon.foss.module.pickup.order.server.hdrule;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.jfree.util.Log;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.utils.StringUtils;

/**
 * FOSS切流量到RPS灰度规则
 * 
 */
public class HdRuleNotifyBase{

	
	private static String percentPath;//百分比路径
	private static String timePath;//次数路径
	private static CuratorFramework zkClient;//zk客户端
	private static boolean started = false;
	private static volatile int TIME ;//次数
	private static volatile int PERCENT ;//百分比
	private static boolean isNoticeZk=true;//是否通知zk更改了次数
	public static final Logger logger = Logger.getLogger(HdRuleNotifyBase.class);
	//private static Object obj=new Object();
	
	/**
	 * 初始化操作
	 * @throws Exception
	 */
	private static boolean init()  {
		if (!HdRuleNotifyBase.started) {
			try {
				HdRuleNotifyBase.started = true;
				HdRuleNotifyBase.zkClient=HdRuleNotifyBase.setVariable();//设置并启动zk
				HdRuleNotifyBase.setPercentPathAndListen(HdRuleNotifyBase.zkClient,HdRuleNotifyBase.percentPath);//设置百分比路径属性，及数据变化监听
				HdRuleNotifyBase.setTimePathAndListen(HdRuleNotifyBase.zkClient, HdRuleNotifyBase.timePath);//设置次数路径属性，及数据变化监听
			}catch (Exception e) {
				logger.error("初始化灰度规则失败:"+e.getMessage());
				HdRuleNotifyBase.started = false;
			}
			logger.error("初始化灰度规则成功！");
		}
		return HdRuleNotifyBase.started;
	}
	/**
	 * 设置并启动zk
	 * @param prop
	 * @return
	 * @throws UnknownHostException 
	 */
	private static  CuratorFramework  setVariable() throws UnknownHostException {
		String hostName = InetAddress.getLocalHost().getHostName();
		String ip = InetAddress.getLocalHost().getHostAddress();
		String path = "/" + hostName + "::" + ip;
		HdRuleNotifyBase.PERCENT=Integer.parseInt(PropertyConstant.RULE_PERCENT_DEFAULT);
		HdRuleNotifyBase.TIME=Integer.parseInt(PropertyConstant.RULE_TIME_DEFAULT);
		HdRuleNotifyBase.percentPath=PropertyConstant.RULE_PERCENT_PATH;
		HdRuleNotifyBase.timePath=path+PropertyConstant.RULE_TIME_PATH;
		URL url = URL.valueOf(PropertyConstant.ZK_ADDRESS);
		String backup = url.getParameter("backup");
		String connectStr = url.getHost() + ":" + url.getPort();
		if (!StringUtils.isBlank(backup)) {
			connectStr += "," + url.getParameter("backup");
		}
		CuratorFramework zkClient = CuratorFrameworkFactory
				.builder()
				.connectString(connectStr)
				.retryPolicy( new ExponentialBackoffRetry(Integer.parseInt(PropertyConstant.RULE_RETYR_TIMESPACE), Integer.MAX_VALUE)) // 10秒重连一次
				.namespace(PropertyConstant.RULE_NAMESPACE)
				.defaultData(null)
				.build();
		zkClient.start();
		return zkClient;
	}
	/**
	 * 设置百分比路径属性，及数据变化监听
	 * @param zkClient
	 * @param path
	 * @throws Exception
	 */
	private static void setPercentPathAndListen(CuratorFramework zkClient,String path) throws Exception{
		Stat stat;
		try {
			stat = zkClient.checkExists().forPath(path);
			if (stat == null) {
				zkClient.create()
						.withMode(CreateMode.PERSISTENT)
						.forPath(path, (PERCENT+"").getBytes(PropertyConstant.ENCODING));
				zkClient.setData().forPath(path, (PERCENT+"").getBytes(PropertyConstant.ENCODING));
			} 
			final NodeCache nodeCache = new NodeCache(zkClient, path);
			nodeCache.getListenable().addListener(new NodeCacheListener() {
				public void nodeChanged() throws Exception {
					String data = new String(nodeCache.getCurrentData().getData(), PropertyConstant.ENCODING);
					PERCENT = Integer.parseInt(data);
				}
			});
			nodeCache.start();
		} catch (Exception e) {
			throw e;
		}
	}
	/**
	 * 设置次数路径属性，及数据变化监听
	 * @param zkClient
	 * @param path
	 * @throws Exception
	 */
	private static void setTimePathAndListen(CuratorFramework zkClient,String path) throws Exception{
		Stat stat;
		try {
			stat = zkClient.checkExists().forPath(path);
			if (stat == null) {
				zkClient.create()
				.withMode(CreateMode.PERSISTENT)
				.forPath(path, (TIME+"").getBytes(PropertyConstant.ENCODING));
				zkClient.setData().forPath(path, (TIME+"").getBytes(PropertyConstant.ENCODING));
			}
			final NodeCache nodeCache = new NodeCache(zkClient, path);
			nodeCache.getListenable().addListener(new NodeCacheListener() {
				public void nodeChanged() throws Exception {
					String data = new String(nodeCache.getCurrentData().getData(), PropertyConstant.ENCODING);
					TIME = Integer.parseInt(data);
					if (TIME!=0) {
						HdRuleNotifyBase.isNoticeZk=true;
					}
				}
			});
			nodeCache.start();
		} catch (Exception e) {
			throw e;
		}
	}
	/**
	 * 初始化次数路径的属性
	 * @param zkClient
	 * @param path
	 * @throws Exception
	 */
	private static void noticeZkChangeTime() throws Exception{
		Stat stat;
		try {
			stat = HdRuleNotifyBase.zkClient.checkExists().forPath(HdRuleNotifyBase.timePath);
			if (stat == null) {
				zkClient.create()
				.withMode(CreateMode.PERSISTENT)
				.forPath(HdRuleNotifyBase.timePath, PropertyConstant.RULE_TIME_DEFAULT.getBytes(PropertyConstant.ENCODING));
				zkClient.setData().forPath(HdRuleNotifyBase.timePath, PropertyConstant.RULE_TIME_DEFAULT.getBytes(PropertyConstant.ENCODING));
			}else{
				zkClient.setData().forPath(HdRuleNotifyBase.timePath, PropertyConstant.RULE_TIME_DEFAULT.getBytes(PropertyConstant.ENCODING));
			}
		} catch (Exception e) {
			throw e;
		}
	}
	/**
	 * 根据订单号所占比例，判断改运单号是否要发送到RPS系统
	 * @param orderNo 运单号  eg:"8900012123230"
	 * @return
	 */
	public static boolean isSendToRPSByPer(String orderNo){
		boolean flag=init();
		if (flag) {
			flag=false;
			if (HdRuleNotifyBase.PERCENT!=0 && !orderNo.isEmpty()) {
				String lastTwoDigitStr=orderNo.substring(orderNo.length()-2);//截取运单号后两位
				try {
					Integer lastTwoDigit=new Integer(lastTwoDigitStr);
					if(lastTwoDigit<HdRuleNotifyBase.PERCENT){
						flag=true;
					}
				} catch (Exception e) {
					logger.error(e);
				}
			}
		}
		logger.info("根据订单号所占比例判断是否发送给RPS系统:"+flag);
		return flag;
	}
	/**
	 * 根据次数判断是否要发送到RPS系统
	 * @return
	 * @throws Exception 
	 */
	public static boolean isSendToRPSByTime() {
		boolean flag=init();
		if (flag) {//如果初始化成功
			if (HdRuleNotifyBase.TIME<=0) {
				flag=false;
				try {
					if(HdRuleNotifyBase.isNoticeZk){
						logger.info("通知zk将次数改变为:0 ");
						HdRuleNotifyBase.noticeZkChangeTime();
						HdRuleNotifyBase.isNoticeZk=false;
					}
				} catch (Exception e) {
					Log.error(e);
				}
			}else{
				HdRuleNotifyBase.TIME--;
			}
		}
		logger.info("根据次数判断是否要发送到RPS系统:"+flag);
		return flag;
	}
	
	

}
