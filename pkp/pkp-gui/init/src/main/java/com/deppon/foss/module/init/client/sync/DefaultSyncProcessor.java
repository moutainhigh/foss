/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.init.client.sync;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.java.plugin.Plugin;
import org.java.plugin.registry.Extension;
import org.java.plugin.registry.ExtensionPoint;

import com.deppon.foss.framework.client.commons.task.ITaskContext;
import com.deppon.foss.framework.client.component.dataaccess.GuiceContextFactroy;
import com.deppon.foss.framework.client.component.networkstatus.NetworkMonitor;
import com.deppon.foss.framework.client.component.networkstatus.NetworkStatus;
import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.client.component.remote.IRemoteInfo;
import com.deppon.foss.framework.client.component.remote.IRemoteServer;
import com.deppon.foss.framework.client.component.sync.AbstractSyncDataProcessor;
import com.deppon.foss.framework.client.component.sync.ISyncDataSaver;
import com.deppon.foss.framework.client.component.sync.ISyncDataStatusConvert;
import com.deppon.foss.framework.client.component.sync.domain.SyncDataBaseLine;
import com.deppon.foss.framework.client.component.sync.service.ISyncDataBaseLineService;
import com.deppon.foss.framework.client.component.sync.service.imp.SyncDataBaseLineService;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.client.core.workbench.IPluginAware;
import com.deppon.foss.framework.entity.SyncDataRequest;
import com.deppon.foss.framework.service.ISyncDataRemoting;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.boot.client.autorun.IAutoRunner;
import com.deppon.foss.module.init.client.sync.dao.ISynSaleDepartmentDao;
import com.deppon.foss.module.init.client.sync.dao.impl.SynSaleDepartmentDao;
import com.deppon.foss.module.init.client.sync.listener.SyncDataStatusListener;
import com.deppon.foss.module.init.client.sync.service.IDownloadTokenService;
import com.deppon.foss.module.init.client.sync.service.impl.DownloadTokenService;
import com.deppon.foss.module.login.shared.hessian.ILoginHessianRemoting;
import com.deppon.foss.module.login.shared.vo.DownloadConfig;
import com.deppon.foss.module.login.shared.vo.DownloadTokenEntity;
import com.deppon.foss.util.define.FossConstants;


/**
 * 默认的同步经常
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午11:42:40,content:
 * @author dp-yangtong
 * @date 2012-10-12 上午11:42:40
 * @since
 * @version
 */
@SuppressWarnings("rawtypes")
public class DefaultSyncProcessor extends AbstractSyncDataProcessor implements
		IAutoRunner, IPluginAware {
	/**
	 * 1秒
	 */
	private static final int SECOND = 0;

	//log
	private static final Log LOG = LogFactory.getLog(DefaultSyncProcessor.class);
	
	//下载水位线service
	private ISyncDataBaseLineService syncDataBaseLineService;
	
	private ISynSaleDepartmentDao synSaleDepartmentDao;
	//
	
	//插件
	private Plugin plugin;
	
	//zxy 20140328 MANA-2018  start 新增
	//下载令牌服务
	private IDownloadTokenService downloadHeartbeatTask = null;
	
	//用户登录状态key
	private static String USER_LOGIN_TYPE = "user_loginType";
	
	//在线状态value
	private static String ONLINE_LOGIN = "ONLINE_LOGIN";
	
	//远程foss服务
	IRemoteServer remoteServer = DefaultRemoteServiceFactory.getInstance().getRemoteServer();
	//网络监控器
	NetworkMonitor networkMonitor = remoteServer.getTransport().getNetworkMonitor();
	//zxy 20140328 MANA-2018  end 新增

	/**
	 * 
	 * 功能：parseSyncDataConfigs 解析同步数据配置
	 * 
	 * @param:
	 * @return:
	 * @since:1.6
	 */
	protected List<SyncDataRequest> parseSyncDataConfigs() {
		return parseSyncDataConfigsAfter(false);
		
	}
	
	/**
	 * 
	 * 功能：parseSyncDataConfigs 解析同步数据配置
	 * 
	 * @param:
	 * @return:
	 * @since:1.6
	 */
	public List<SyncDataRequest> parseSyncDataConfigsAfter(boolean after) {
		List<SyncDataRequest> requests = new ArrayList<SyncDataRequest>();
		
		String userOrgCode = null;
		String empCode = null;
		List<String> userOrgCodes = new ArrayList<String> ();
		UserEntity user = (UserEntity)SessionContext.getCurrentUser();
		if(user!=null){
			EmployeeEntity emp = (EmployeeEntity)user.getEmployee();
			if(emp!=null){
				empCode = emp.getEmpCode();
				OrgAdministrativeInfoEntity org = emp.getDepartment();
				if(org!=null){
					userOrgCode = emp.getDepartment().getCode();
				}
			}
		}
		userOrgCodes.add(userOrgCode);
		
		if(after){
			List<SaleDepartmentEntity> list = synSaleDepartmentDao.querySaleDepartmentByBillGroup(userOrgCode);
			
			for(SaleDepartmentEntity sd:list){
				if(sd!=null  &&  StringUtils.isNotEmpty(sd.getCode()) && !userOrgCode.equals(sd.getCode()) ){
					userOrgCodes.add(sd.getCode());
				}
			}
		}
		
		List<SyncDataBaseLine> baselines = parseDataSaver(userOrgCodes, after);

		//用于保存这个class类型的数据是否已经有过产生下载request (只用于region id需要不同的org code分量下载的情况)
		List<Class> checkList = new ArrayList<Class>();
		for (SyncDataBaseLine sdbl : baselines) {
			
			boolean hasMadeRequest = false;
			
			for(Class clazz : checkList){
				try {
					if(clazz.getName().equals(Class.forName(sdbl.getEntityClsName()).getName())){
						hasMadeRequest = true;
					}
				} catch (ClassNotFoundException e) {
					LOG.error("parseSyncDataConfigs exception", e);
				}
			}
			
			if(sdbl.getRegionID() == null || "".equals(sdbl.getRegionID()) ){
				//对于没有region id的数据  进行简单的增量下载
				
				if(!hasMadeRequest){
					SyncDataRequest sdr = new SyncDataRequest();
					sdr.setFromDate(reduceDate(sdbl.getSyncDate()));
					sdr.setRegionID(sdbl.getRegionID());
					sdr.setUserID(empCode);
					sdr.setOrgCode(sdbl.getOrgCode());
					sdr.setNeedOrgSearch(sdbl.getNeedOrgSearch());
					sdr.setPagination(sdbl.getPagination());
					sdr.setSyncPage(sdbl.getSyncPage());
					try {
						sdr.setSyncKey(Class.forName(sdbl.getEntityClsName()));
						requests.add(sdr);
						hasMadeRequest = true;
					} catch (ClassNotFoundException e) {
						LOG.error("parseSyncDataConfigs exception", e);
					}
				}
				
			}else{
				//对于有region id的数据  需要根据不同orgcode分区别不同的org code对于一个version no 增量下载
				String baseLineOrgCode = sdbl.getOrgCode();
				if(empCode!=null){
					
					if(baseLineOrgCode!=null 
							&& baseLineOrgCode.equals(userOrgCode)){
						if(!hasMadeRequest){
							SyncDataRequest sdr = new SyncDataRequest();
							sdr.setFromDate(reduceDate(sdbl.getSyncDate()));
							sdr.setRegionID(sdbl.getRegionID());
							sdr.setOrgCode(userOrgCode);
							sdr.setPagination(sdbl.getPagination());
							sdr.setSyncPage(sdbl.getSyncPage());
							try {
								sdr.setSyncKey(Class.forName(sdbl.getEntityClsName()));
								requests.add(sdr);
								checkList.add(Class.forName(sdbl.getEntityClsName()));
								hasMadeRequest = true;
							} catch (ClassNotFoundException e) {
								LOG.error("parseSyncDataConfigs exception", e);
							}
						}
						
					}else{
						
						//这是一个新的org code 下载数据
						//要把新的org code 基线保存到本地
						if(!hasMadeRequest){
							
							SyncDataBaseLine baseLineDb = syncDataBaseLineService
									.getSyncDataBaseLineByClassAndOrgCode(sdbl.getEntityClsName(),userOrgCode );
						
							Date zeroDate = new Date(0);
							SyncDataBaseLine baseLine = new SyncDataBaseLine();
							baseLine.setEntityClsName(sdbl.getEntityClsName());
							baseLine.setSyncDate(reduceDate(zeroDate));
							baseLine.setOrgCode(userOrgCode);
							if(baseLineDb==null){
								syncDataBaseLineService.saveBaseLine(baseLine);
								baseLineDb = baseLine;
							}
							SyncDataRequest sdr = new SyncDataRequest();
							sdr.setFromDate(reduceDate(baseLineDb.getSyncDate()));
							sdr.setRegionID(baseLineDb.getRegionID());
							sdr.setPagination(sdbl.getPagination());
							sdr.setSyncPage(sdbl.getSyncPage());
							sdr.setOrgCode(userOrgCode);
							try {
								sdr.setSyncKey(Class.forName(sdbl.getEntityClsName()));
								requests.add(sdr);
								checkList.add(Class.forName(sdbl.getEntityClsName()));
								hasMadeRequest = true;
							} catch (ClassNotFoundException e) {
								LOG.error("parseSyncDataConfigs exception", e);
							}
						}
					}
						
					
				}
				
				
				
			}

		}
		return requests;

	}

	/**
	 * 防止精度丢失 导致某些数据无法下载
	 * @return
	 */
	private Date reduceDate(Date date){
		if(date==null){
			return null;
		}
		
		long time = date.getTime();
		//提前1秒钟 防止精度丢失 无法下载
		if(time>=SECOND){
			time = time - SECOND; 
		}else{
			time = 0;
		}
		
		//必须设置客户端是北京时间 否则可能会出错 （如果客户端乱改时区 会导致time发生变化）
		Calendar c =Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		c.setTimeInMillis(time);
	
		return c.getTime();
	}

	/**
	 * 
	 * 功能：parseDataSaver 解析数据保存器
	 * 
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	private List<SyncDataBaseLine>  parseDataSaver(List<String> userOrgCodes,  boolean after) {
		//zxy 20131010 BUG-55198 start 修改：添加try-catch，增加开关功能
		//			 					规则：1.plugin要设置switchType，若未设置则默认不加入开关管理，
		//									  2.参数表不配置开关则默认表示开启(N\Y)，如果要关闭则必须在参数表加入组织机构的开关且设置为N，
		//									  3.父机构设置成N，则所有子集全部当N处理
		//		 							  4.三种开关类型：SYNDATA_SWITCH_BASE基础 SYNDATE_SWITCH_EXPRESS快递 SYNDATE_SWITCH_WAYBILL运单
		try{
			//zxy 20131010 BUG-55198 start 新增：向服务器请求开关状态
			//开启远程服务
			ILoginHessianRemoting loginHessianRemoting = DefaultRemoteServiceFactory.getService(ILoginHessianRemoting.class);
//			String orgCode = userOrgCodes.get(0);	//是不是取第一个code
			UserEntity user = (UserEntity) SessionContext.getCurrentUser();
			EmployeeEntity emp = null;
			OrgAdministrativeInfoEntity loginOrg = null;
			String loginOrgCode = null;
			if(user != null)
				emp = (EmployeeEntity)user.getEmployee();
			if(emp != null)
				loginOrg = emp.getDepartment();
			if(loginOrg != null)
				loginOrgCode = loginOrg.getCode();
			boolean bOffSwitchBase = true;
			boolean bOnSwitchExp = false;
			boolean bOnSwitchWaybill = false;
			if(StringUtils.isNotBlank(loginOrgCode)){
				//基础数据下载，查询是否设置关闭状态，如果设置则表示关闭，否则开启(包括设置开启和无数据) 不配置时默认开启
				bOffSwitchBase = loginHessianRemoting.isOffSwitchBySwtOrgCode(loginOrgCode,"SYNDATA_SWITCH_BASE");
				//快递下载，查询是否设置开启状态，如果设置则表示开启，否则关闭(包括设置关闭和无数据)     不配置时默认关闭
				bOnSwitchExp = loginHessianRemoting.isOnSwitchBySwtOrgCode(loginOrgCode,"SYNDATE_SWITCH_EXPRESS");
				//零担运单下载，查询是否设置开启状态，如果设置则表示开启，否则关闭(包括设置开启和无数据) 不配置时默认关闭
				bOnSwitchWaybill = loginHessianRemoting.isOnSwitchBySwtOrgCode(loginOrgCode,"SYNDATE_SWITCH_WAYBILL");
			}
			//zxy 20131010 BUG-55198 end 新增：向服务器请求开关状态
			
			ExtensionPoint extensionPoint = plugin.getDescriptor().getExtensionPoint("syncDataSaver");
			List<SyncDataBaseLine> list = new ArrayList<SyncDataBaseLine>();
			List<Extension> extensions = new ArrayList<Extension>(extensionPoint.getConnectedExtensions());
				for (Extension extension : extensions) {
					String clsString = extension.getParameter("saver-class").valueAsString();
					String needOrgSearch = extension.getParameter("need-org-search").valueAsString();
					//String needRegionSearch=extension.getParameter("need-region-search").valueAsString();
					String pagination = extension.getParameter("pagination").valueAsString();
					
					//zxy 20131010 BUG-55198 start 新增：增加开关功能，如果关闭则continue跳过
					String switchType = null;
					if(extension.getParameter("switchType") != null)
						switchType = extension.getParameter("switchType").valueAsString();
					//如果开关类型匹配，需判断是否关闭，如果关闭则跳过
					if("SYNDATA_SWITCH_BASE".equals(switchType)){
						if(bOffSwitchBase)
							continue;
					}else if("SYNDATE_SWITCH_EXPRESS".equals(switchType)){
						if(!bOnSwitchExp)
							continue;
					}else if("SYNDATE_SWITCH_WAYBILL".equals(switchType)){
						if(!bOnSwitchWaybill)
							continue;
					}
					//zxy 20131010 BUG-55198 end 新增：增加开关功能，如果关闭则continue跳过
					
					ClassLoader classLoader = plugin.getManager().getPluginClassLoader(
							extension.getDeclaringPluginDescriptor());
					try {
		
						Class cls = classLoader.loadClass(clsString);
						registeData(userOrgCodes, after, list, needOrgSearch,
								pagination, cls);
		
					} catch (ClassNotFoundException e) {
						LOG.error("ClassNotFoundException", e);
					}
		
				}
			
			return list;
		}finally{
//			DefaultRemoteServiceFactory.destroy();
		}
		//zxy 20131010 BUG-55198 start 修改：添加try-catch，增加开关功能
		//			 					规则：1.plugin要设置switchType，若未设置则默认不加入开关管理，
		//									  2.参数表不配置开关则默认表示开启(N\Y)，如果要关闭则必须在参数表加入组织机构的开关且设置为N，
		//									  3.父机构设置成N，则所有子集全部当N处理
		//		 							  4.三种开关类型：SYNDATA_SWITCH_BASE基础 SYNDATE_SWITCH_EXPRESS快递 SYNDATE_SWITCH_WAYBILL运单
	}

	private void registeData(List<String> userOrgCodes, boolean after,
			List<SyncDataBaseLine> list, String needOrgSearch,
			String pagination, Class cls) {
		try {
			ISyncDataSaver dataSaver = (ISyncDataSaver) cls
					.newInstance();
			Class saveType = dataSaver.getSaveType();
			
			//普通的增量下载
			if(!FossConstants.YES.equals(needOrgSearch ) /**&& !FossConstants.YES.equals(needRegionSearch)*/ ){
				
				if(!after){
					SyncDataBaseLine baseLine = syncDataBaseLineService
							.getSyncDataBaseLineByClass(saveType.getName());

					if (baseLine == null) {
						baseLine = new SyncDataBaseLine();
						baseLine.setEntityClsName(saveType.getName());
						baseLine.setSyncDate(new Date(0));
						baseLine.setOrgCode(userOrgCodes.get(0));
						syncDataBaseLineService.saveBaseLine(baseLine);
					}
					baseLine.setPagination(pagination);
					list.add(baseLine);
				}
				
			//如果是需要更具org来进行增量下载	
			}else if(FossConstants.YES.equals(needOrgSearch)){
				if(after){
					for(String userOrgCode : userOrgCodes ){
						
						SyncDataBaseLine baseLine = syncDataBaseLineService
								.getSyncDataBaseLineByClassAndOrgCode(saveType.getName(), userOrgCode);

						if (baseLine == null) {
							baseLine = new SyncDataBaseLine();
							baseLine.setEntityClsName(saveType.getName());
							baseLine.setSyncDate(new Date(0));
							baseLine.setOrgCode(userOrgCode);
							syncDataBaseLineService.saveBaseLine(baseLine);
						}
						baseLine.setNeedOrgSearch(FossConstants.YES);
						list.add(baseLine);
					}
					
				}
			}

			this.registeDataSaver(dataSaver);

		} catch (InstantiationException e) {
			LOG.error("InstantiationException", e);
		} catch (IllegalAccessException e) {
			LOG.error("IllegalAccessException", e);
		}
	}

	/**
	 * 
	 * 功能：execute 执行任务
	 * 
	 * @param:
	 * @return:
	 * @since:1.6
	 */
	@Override
	public void execute(ITaskContext context) throws Exception {

		try {
			// 由于同步在登录后进行，获取登录状态，判断是否在线登录 修改人：linws 时间：2012/4/25
			String loginType = (String) SessionContext.get(USER_LOGIN_TYPE);
			if (loginType != null && loginType.equals(ONLINE_LOGIN)) {
				start();
				this.setContext(context);
				//zxy 20140306 MANA-2018 start 修改:增加下载令牌管理，直到取到令牌后才执行下载
				context.setTitle("离线下载任务");
				ILoginHessianRemoting loginHessianRemoting = DefaultRemoteServiceFactory.getService(ILoginHessianRemoting.class);
				DownloadConfig downloadConf = loginHessianRemoting.getDownloadConfig();
				if(downloadConf != null){
					int beatInterval = downloadConf.getBeatInterval();
					int requestInterval = downloadConf.getRequestInterval();
					int maxPageSize = downloadConf.getMaxPageSize();
					this.setMaxPageSize(maxPageSize);
					LOG.info("下载配置[beatInterval:" + beatInterval + ",requestInterval:" + requestInterval + ",maxPageSize:" + maxPageSize + "]");
					downloadHeartbeatTask = new DownloadTokenService(context,"离线下载任务","正在请求服务器资源",requestInterval, beatInterval);
					downloadHeartbeatTaskStart();
				}
				//zxy 20140306 MANA-2018 end 修改:增加下载令牌管理，直到取到令牌后才执行下载
			}

		} catch (Exception t) {
			LOG.error(t.getMessage(), t);
		}
	}

	private void downloadHeartbeatTaskStart() throws Exception {
		try{
			downloadHeartbeatTask.run();
			DownloadTokenEntity tokenEntity = downloadHeartbeatTask.getTokenEntity();
			//当取到了令牌，且令牌状态有效时才执行下载。如果取到令牌状态无效，说明无权下载
			if(tokenEntity != null && tokenEntity.isActive()){
				LOG.info("获取到令牌[tokenEntity:" + tokenEntity.getDownloadToken() + "],准备开始下载...");
				this.process();
				if(downloadHeartbeatTask.isConnectSuccess()){  //令牌服务正常，则表示下载一切正常 ，否则可能有异常就不进行正常令牌的释放，通过强制令牌释放
					downloadHeartbeatTask.releaseTokenEntity();
					LOG.info("下载正常结束,令牌正常释放");
				}
			}
		}finally{
			LOG.info("准备取消下载心跳服务...");
			downloadHeartbeatTask.cancelBeat();
			LOG.info("下载心跳服务停止"); 
		}
	}

	
	
	/**
	 * 
	 * 功能：start 开始操作
	 * 
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	private void start() {
		synSaleDepartmentDao=GuiceContextFactroy.getInjector().getInstance(SynSaleDepartmentDao .class);
		syncDataBaseLineService = GuiceContextFactroy.getInjector().getInstance(SyncDataBaseLineService.class);
		// 这里可以添加DataSaver

		this.registSyncListener(new SyncDataStatusListener());
		this.setSyncStatusMessageConvert(new ISyncDataStatusConvert() {
			
			public String converMessage(Class<?> target) {
				return null;
			}
		});
		this.setSyncRemoteService(DefaultRemoteServiceFactory.getInstance()
				.getRemoteServer(IRemoteInfo.DOWNLOAD_NAME).getService(
						ISyncDataRemoting.class, "sync/syncDataService"));
		this.init();
	}

	/**
	 * 
	 * 功能：setPlugin 设置插件
	 * 
	 * @param:
	 * @return:
	 * @since:1.6
	 */
	public void setPlugin(Plugin arg0) {
		this.plugin = arg0;
	}

	@Override
	protected boolean isActive() {
		boolean isActive = false;
		try{
			if (NetworkStatus.ONLINE != networkMonitor.getRefreshStatus()) {
				LOG.info("foss服务网络不稳定,再次发起连接请求...");
				downloadHeartbeatTask.beat();		//如果session失效了，则立即检查心跳
			}
			isActive = downloadHeartbeatTask.isConnectSuccess();
			if(!isActive)
				LOG.info("foss服务中断,准备停止下载");
			//zxy 20140423 MANA-2018 start 新增:客户端强制终止
			if(this.getContext().isForceCancel()){
				isActive = false;
				LOG.info("客户端强制终止下载");
			}
			//zxy 20140423 MANA-2018 end 新增:客户端强制终止
		}catch(Exception e){
			e.printStackTrace();
		}
		return isActive;
	}
}
