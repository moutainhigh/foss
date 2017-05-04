package com.deppon.foss.module.login.server.hessian;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hsqldb.lib.StringUtil;
import com.deppon.foss.framework.cache.CacheManager;
import com.deppon.foss.framework.cache.ICache;
import com.deppon.foss.framework.cache.storage.RedisCacheStorage;
import com.deppon.foss.framework.entity.IUser;
import com.deppon.foss.framework.server.Definitions;
import com.deppon.foss.framework.server.components.security.SecurityNonCheckRequired;
import com.deppon.foss.framework.server.context.SessionContext;
import com.deppon.foss.framework.server.context.WebApplicationContextHolder;
import com.deppon.foss.framework.server.deploy.hessian.annotation.Remote;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.frameworkimpl.server.util.TokenMarshal;
import com.deppon.foss.module.frameworkimpl.shared.domain.Cookie;
import com.deppon.foss.module.frameworkimpl.shared.domain.Token;
import com.deppon.foss.module.login.server.dao.IGuiMonitorDao;
import com.deppon.foss.module.login.server.downloadtoken.DownloadTokenManager;
import com.deppon.foss.module.login.server.downloadtoken.DownloadTokenManagerFactory;
import com.deppon.foss.module.login.server.service.ILoginService;
import com.deppon.foss.module.login.shared.domain.GuiMonitorEntity;
import com.deppon.foss.module.login.shared.domain.GuiMonitorLogEntity;
import com.deppon.foss.module.login.shared.domain.LoginForGUIInfo;
import com.deppon.foss.module.login.shared.domain.LoginInfo;
import com.deppon.foss.module.login.shared.domain.TokenDto;
import com.deppon.foss.module.login.shared.hessian.ILoginHessianRemoting;
import com.deppon.foss.module.login.shared.vo.DownloadConfig;
import com.deppon.foss.module.login.shared.vo.DownloadTokenEntity;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.common.FossTTLCache;
import com.deppon.foss.util.define.FossConstants;

/**
 * 客户端登录的gui service
 *
 */
@SecurityNonCheckRequired
@Remote
public class LoginHessianService implements ILoginHessianRemoting{
	
	private ILoginService loginService;
	
	private IGuiMonitorDao guiMonitorDao;
	
	private static Properties clientVersionProperties;
	
	//zxy 20131010 BUG-55198 参数配置服务
	@Resource
	private IConfigurationParamsService configurationParamsService;
	
	/**
	 * 日志初始化
	 */
	private static final Log LOG = LogFactory.getLog(LoginHessianService.class);
	
	static {
		
		//如果配置信息为空
		if(clientVersionProperties==null){
			//输入流
			InputStream in = null;
			try {
				//从配置文件中 根据class path来读取配置文件
				in = Thread.currentThread().getContextClassLoader() .getResourceAsStream("clientVersion.properties");
				//创建properties对象
				clientVersionProperties = new Properties();
				clientVersionProperties.load(in);//从流中读取properties对象信息
			} catch (Exception e) {
				// 异常处理
				LOG.error("未找到clientVersion.properties配置文件", e);
			} finally {
				if (in != null) {//判断流不是null
					try {
						//关闭流
						in.close();//再finally中关闭流
					} catch (IOException e) {
						//异常处理
						LOG.error("文件流关闭失败", e);
					}
				}
			}
		}
		
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public LoginInfo userLogin(LoginForGUIInfo loginForGUIInfo) {
		UserEntity user = loginService.userLogin(loginForGUIInfo.getUsername(), loginForGUIInfo.getPwd());
		String hostName = loginForGUIInfo.getHostName();
		String macAddress = loginForGUIInfo.getMacAddress();
		
		//内网地址不正确 需要改为外网地址
		String ipAddress = (String)(SessionContext.getSession().getObject("IP_ADDRESS"));
		if(StringUtils.isEmpty(ipAddress)){
			ipAddress = loginForGUIInfo.getIpAddress();
		}
		
		//zxy 20140312 MANA-2018 start 修改:统一uuid
		String uuid = UUIDUtils.getUUID();
		String clientVersion=loginForGUIInfo.getClientVersion();
		addGuiLoginInfo(uuid, user, hostName, macAddress, ipAddress , clientVersion);
		//一个月之后去掉
		//addGuiLoginInfoLog(uuid, user,loginForGUIInfo);
		//zxy 20140312 MANA-2018 end 修改:统一uuid
		List<ResourceEntity> list = loginService.queryGUIResourceByUserCode(user.getEmployee().getEmpCode(), user.getEmployee().getDepartment().getCode());
		
		LoginInfo loginInfo = new LoginInfo();
		//组装菜单
		loginInfo.setResources(list);
		loginInfo.setUser(user);
		String tokenStr = Cookie.getTokenParam();
		
		
		RedisCacheStorage storage =(RedisCacheStorage) WebApplicationContextHolder.getWebApplicationContext().getBean("storage3");
		storage.set(FossTTLCache.GUILOGON_TOKEN_CACHE_UUID+"_"+uuid, tokenStr, 60*30);
		
		loginInfo.setUuid(uuid);
		loginInfo.setToken(tokenStr);
		loginInfo.setDate(new Date());
		return loginInfo;
	}
	
	/**
	 * 
	 * @param uuid  唯一身份标示
	 * @param user
	 * @param hostName
	 * @param macAddress
	 * @param ipAddress
	 * @param clientVersion
	 */
	private void addGuiLoginInfo(String uuid, UserEntity user, String hostName, String macAddress, String ipAddress,
			String clientVersion) {
		GuiMonitorEntity guiMonitorEntity = new GuiMonitorEntity();
		guiMonitorEntity.setId(uuid);
		guiMonitorEntity.setCode(user.getEmployee().getEmpCode());
		guiMonitorEntity.setName(user.getEmployee().getEmpName());
		guiMonitorEntity.setIpAddress(ipAddress);
		guiMonitorEntity.setMacAddress(macAddress);
		guiMonitorEntity.setHostName(hostName);
		guiMonitorEntity.setPgmVersion(clientVersion);
		guiMonitorEntity.setLoginTime(new Date());
		guiMonitorEntity.setDownloadStatus(FossConstants.DOWNLOAD_STATUS_UNDO); //zxy 20140312 MANA-2018 设置下载状态
		guiMonitorDao.insert(guiMonitorEntity); 
	}
	
	/**
	 * (非 Javadoc) addGuiLoginInfoLog
		* @author 270293-foss-zhangfeng
		* @date 2015-8-31 上午10:24:00 
	    * <p>Title: addGuiLoginInfoLog</p> 
	    * <p>Description:TODO</p> 
	    * @param 
	    * @return void
	 */
	private void addGuiLoginInfoLog(String uuid, UserEntity user,LoginForGUIInfo loginForGUIInfo){
		GuiMonitorLogEntity logEntity=new GuiMonitorLogEntity();
		logEntity.setId(uuid);
		logEntity.setCode(user.getEmployee().getEmpCode());
		logEntity.setName(user.getEmployee().getEmpName());
		logEntity.setIpAddress(loginForGUIInfo.getIpAddress());
		logEntity.setMacAddress(loginForGUIInfo.getMacAddress());
		logEntity.setHostName(loginForGUIInfo.getHostName());
		logEntity.setLoginTime(new Date());
		logEntity.setPc_ip(loginForGUIInfo.getPc_ip());
		logEntity.setPc_mac(loginForGUIInfo.getPc_mac());
		logEntity.setPc_host(loginForGUIInfo.getPc_hostName());
		guiMonitorDao.insertLog(logEntity);
	}
	
	
	

	
	@Override
	public void userLogout() {
		this.loginService.userLogout();
		SessionContext.setCurrentUser(null);
		Cookie.invalidateCookie();
	}
	@Resource
	public void setLoginService(ILoginService loginService) {
		this.loginService = loginService;
	}
	@Resource
	public void setGuiMonitorDao(IGuiMonitorDao guiMonitorDao) {
		this.guiMonitorDao = guiMonitorDao;
	}

	
	
	/**
	 * 不用执行任何代码 定时调用 防止客户端session 过期 
	 * 刷新一下session里面的对象 防止超时
	 */
	@SuppressWarnings("unchecked")
	public TokenDto keepSession(String tokenStr) {
		SessionContext.getSession().getObject(Definitions.KEY_USER);
		String clientVersion = clientVersionProperties.getProperty("clientVersion");
		TokenDto dto = new TokenDto();
		try{
			Token token = TokenMarshal.unMarshal(tokenStr);
			if (token != null && !token.expired()) {
				SessionContext.getSession().setObject("FRAMEWORK_KEY_EMPCODE",
						token.getEmpCode());
				SessionContext.getSession().setObject(Definitions.KEY_USER,
						token.getUserId());
				SessionContext.getSession().setObject("FOSS_KEY_CURRENT_DEPTCODE", token.getCurrentDeptCode());
				// 存入默认部门名称
				SessionContext.getSession().setObject("FOSS_KEY_CURRENT_DEPTNAME",
						token.getCurrentDeptName());
				ICache<String, IUser> userCache = CacheManager.getInstance().getCache(
						IUser.class.getName());
//				UserContext.setCurrentUser(userCache.get(token.getUserId()));
				String tokenParam = Cookie.getTokenParam();
				dto.setTokenParam(tokenParam);
				dto.setClientVersion(clientVersion);
				return dto;
			
			} else {
				dto.setClientVersion(clientVersion);
				return dto;
			}
		}catch(Exception e){
			dto.setClientVersion(clientVersion);
			return dto;
		}

	}
	
	
	/**
	 * 不用执行任何代码 定时调用 防止客户端session 过期 
	 * 刷新一下session里面的对象 防止超时
	 */
	@SuppressWarnings("unchecked")
	public TokenDto keepSessionUuid(String tokenStr, String uuid) {
		
		if(StringUtil.isEmpty(tokenStr)){
			tokenStr=null;
		}
		@SuppressWarnings("rawtypes")
		
		RedisCacheStorage storage =(RedisCacheStorage) WebApplicationContextHolder.getWebApplicationContext().getBean("storage3");
		if(StringUtils.isNotEmpty(tokenStr)){
			storage.set(FossTTLCache.GUILOGON_TOKEN_CACHE_UUID+"_"+uuid, tokenStr, 60*30);
		}else{
			storage.remove(FossTTLCache.GUILOGON_TOKEN_CACHE_UUID+"_"+uuid);
		}
		
		SessionContext.getSession().getObject(Definitions.KEY_USER);
		String clientVersion = clientVersionProperties.getProperty("clientVersion");
		TokenDto dto = new TokenDto();
		try{
			Token token = TokenMarshal.unMarshal(tokenStr);
			if (token != null && !token.expired()) {
				SessionContext.getSession().setObject("FRAMEWORK_KEY_EMPCODE",
						token.getEmpCode());
				SessionContext.getSession().setObject(Definitions.KEY_USER,
						token.getUserId());
				SessionContext.getSession().setObject("FOSS_KEY_CURRENT_DEPTCODE", token.getCurrentDeptCode());
				// 存入默认部门名称
				SessionContext.getSession().setObject("FOSS_KEY_CURRENT_DEPTNAME",
						token.getCurrentDeptName());
				ICache<String, IUser> userCache = CacheManager.getInstance().getCache(
						IUser.class.getName());
//				UserContext.setCurrentUser(userCache.get(token.getUserId()));
				String tokenParam = Cookie.getTokenParam();
				dto.setTokenParam(tokenParam);
				dto.setClientVersion(clientVersion);
				return dto;
			
			} else {
				dto.setClientVersion(clientVersion);
				return dto;
			}
		}catch(Exception e){
			dto.setClientVersion(clientVersion);
			return dto;
		}

	}
	
	/**
     * 判断开关是否处于关闭状态：只要其中(...-父机构-当前机构)有一个关闭的开关，则表示关闭 
	  * Description: 离线下载功能的开关配置 BUG-55198
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:37
	  * @param orgCode	机构
	  * @param swtichCode 开关类型
	  * @return
	 */
	@Override
	public boolean isOffSwitchBySwtOrgCode(String orgCode,String swtichCode){
		ConfigurationParamsEntity entity = new ConfigurationParamsEntity();
		entity.setActive(FossConstants.YES);
		entity.setOrgCode(orgCode);
		entity.setCode(swtichCode);
		entity.setConfValue(FossConstants.NO);
		List lst = configurationParamsService.queryConfigurationParamsByOrgCode(entity);
		if(lst != null && lst.size() > 0)
			return true;
		else
			return false;
	}
	
	/**
     * 判断开关是否处于开启状态： 
	  * Description: 离线下载功能的开关配置 BUG-55198
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:37
	  * @param orgCode	机构
	  * @param swtichCode 开关类型
	  * @return
	 */
	@Override
	public boolean isOnSwitchBySwtOrgCode(String orgCode,String swtichCode){
		//如果已关闭则直接返回false，如果未关闭再查是否有开启开关，如果无表示未开启
		if(!isOffSwitchBySwtOrgCode(orgCode,swtichCode)){
			ConfigurationParamsEntity entity = new ConfigurationParamsEntity();
			entity.setActive(FossConstants.YES);
			entity.setOrgCode(orgCode);
			entity.setCode(swtichCode);
			entity.setConfValue(FossConstants.YES);
			List lst = configurationParamsService.queryConfigurationParamsByOrgCode(entity);
			if(lst != null && lst.size() > 0)
				return true;
			else
				return false;
		}else
			return false;
	}

	@Override
	public boolean beat(String token){
		boolean bSurvival = false; 
		try{
			DownloadTokenManagerFactory tokenManagerFactory = DownloadTokenManagerFactory.getInstance();
			DownloadTokenManager downloadTokenManager = tokenManagerFactory.getDownloadTokenManager();
			DownloadTokenEntity indentity = downloadTokenManager.getTokenByMac(token);
			if(indentity != null){
				downloadTokenManager.updateTokenBeatDate(indentity);
				//将心跳时间保存到数据库
				GuiMonitorEntity guiMonitorEntity = new GuiMonitorEntity();
				guiMonitorEntity.setId(indentity.getMac());
				guiMonitorEntity.setLastBeatTime(new Date());
				guiMonitorDao.updateGuiMonitor(guiMonitorEntity);
				bSurvival = true;
			}
		}catch(Exception e){
			LOG.error("下载心跳异常", e);
		}
		return bSurvival;
	}
	
	@Override
	public DownloadTokenEntity requestDownloadToken(String tokenUuid){
		try{
			DownloadTokenManagerFactory tokenManagerFactory = DownloadTokenManagerFactory.getInstance();
			DownloadTokenManager downloadTokenManager = tokenManagerFactory.getDownloadTokenManager();
			DownloadTokenEntity indentity = downloadTokenManager.requestToken(tokenUuid);
			return indentity;
		}catch(Exception e){
			LOG.error("获取下载令牌异常", e);
		}
		return null;
	}
	
	@Override
	public void releaseDownloadToken(String tokenUuid){
		DownloadTokenManagerFactory tokenManagerFactory = DownloadTokenManagerFactory.getInstance();
		DownloadTokenManager downloadTokenManager = tokenManagerFactory.getDownloadTokenManager();
		downloadTokenManager.releaseToken(tokenUuid,false);
	}
	
	/**
	 * 强制释放令牌
	 * @param tokenUuid
	 */
	public void forceReleaseDownloadToken(String tokenUuid){
		DownloadTokenManagerFactory tokenManagerFactory = DownloadTokenManagerFactory.getInstance();
		DownloadTokenManager downloadTokenManager = tokenManagerFactory.getDownloadTokenManager();
		downloadTokenManager.releaseToken(tokenUuid,true);
	}

	@Override
	public DownloadConfig getDownloadConfig() {
		DownloadConfig conf = new DownloadConfig();
		//最大页数
		try{
			ConfigurationParamsEntity downloadMaxPageSize = configurationParamsService.queryConfigurationParamsByOrgCodeNoCache
					(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,
						WaybillConstants.DOWNLOAD_MAX_PAGE_SIZE,
							FossConstants.ROOT_ORG_CODE);
			if(downloadMaxPageSize != null)
				conf.setMaxPageSize(Integer.parseInt(downloadMaxPageSize.getConfValue()));
		}catch(Exception e){
			
		}
		
		//请求令牌轮询间隔
		try{
			ConfigurationParamsEntity downloadTokenInterval = configurationParamsService.queryConfigurationParamsByOrgCodeNoCache
					(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,
						WaybillConstants.DOWNLOAD_TOKEN_INTERVAL,
							FossConstants.ROOT_ORG_CODE);
			if(downloadTokenInterval != null)
				conf.setMaxPageSize(Integer.parseInt(downloadTokenInterval.getConfValue()));
		}catch(Exception e){
			
		}
		
		//心跳间隔
		try{
			ConfigurationParamsEntity downloadBeatTokenInterval = configurationParamsService.queryConfigurationParamsByOrgCodeNoCache
					(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,
						WaybillConstants.DOWNLOAD_BEAT_TOKEN_INTERVAL,
							FossConstants.ROOT_ORG_CODE);
			if(downloadBeatTokenInterval != null)
				conf.setMaxPageSize(Integer.parseInt(downloadBeatTokenInterval.getConfValue()));
		}catch(Exception e){
			
		}
		
		return conf;
	}
	
	/**
	 * 获取下载开关配置
	 * @return
	 */
	@Override
	public boolean getDownloadSwitch(String orgCode){
		boolean downSwitch = true;
		try{
			downSwitch = isOnSwitchBySwtOrgCode(orgCode,WaybillConstants.FOSS_DOWNLOAD_SWITCH);
		}catch(Exception e){
			downSwitch = false;
		}
		return downSwitch;
	}
	
}
