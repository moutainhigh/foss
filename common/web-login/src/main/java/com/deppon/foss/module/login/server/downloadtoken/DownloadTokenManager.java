
package com.deppon.foss.module.login.server.downloadtoken;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import com.deppon.foss.framework.server.context.WebApplicationContextHolder;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.login.server.dao.IGuiMonitorDao;
import com.deppon.foss.module.login.shared.domain.GuiMonitorEntity;
import com.deppon.foss.module.login.shared.vo.DownloadTokenEntity;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.util.define.FossConstants;

/**
 * 下载令牌管理器 
 * zxy 20140306 MANA-2018
 * 
 * @author 157229-zxy
 * 
 */
public class DownloadTokenManager {
	private int maxThreadSize = 150; //默认最大令牌数
	private Object lock = new Object();
	private int maxTimeInterval = 600000;	//默认间隔时间
	/**
	 * 系统常量service
	 */
	private IConfigurationParamsService configurationParamsService;
	
	/**
	 * 监控service
	 */
	private IGuiMonitorDao guiMonitorDao;
	
	public DownloadTokenManager(int maxThreadSize,int maxTimeInterval){
		this.maxThreadSize = maxThreadSize;
		this.maxTimeInterval = maxTimeInterval;
		guiMonitorDao = WebApplicationContextHolder.getWebApplicationContext().getBean(IGuiMonitorDao.class);
		configurationParamsService = WebApplicationContextHolder.getWebApplicationContext().getBean(IConfigurationParamsService.class);
	}
	
	public DownloadTokenManager(){
		guiMonitorDao = WebApplicationContextHolder.getWebApplicationContext().getBean(IGuiMonitorDao.class);
		configurationParamsService = WebApplicationContextHolder.getWebApplicationContext().getBean(IConfigurationParamsService.class);
		ConfigurationParamsEntity downloadTokenTotal = null;
		try {
			//读取令牌池数量
			downloadTokenTotal = configurationParamsService.queryConfigurationParamsByOrgCode
					(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,
						WaybillConstants.DOWNLOAD_TOKEN_TOTAL,
							FossConstants.ROOT_ORG_CODE);
		}catch (Exception e) {
			
		}
		if(downloadTokenTotal != null){
			maxThreadSize = Integer.parseInt(downloadTokenTotal.getConfValue());
		}
		//读取令牌存活时间
		maxTimeInterval = getDownloadTokenSurvivalInt();
	}
	
	private int getDownloadTokenSurvivalInt(){
		int maxTimeInterval = this.maxTimeInterval;
		try{
			ConfigurationParamsEntity downloadTokenSurvivalInt 
				= configurationParamsService.queryConfigurationParamsByOrgCode
					(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,
						WaybillConstants.DOWNLOAD_TOKEN_SURVIVAL_TIME,
							FossConstants.ROOT_ORG_CODE);
			if(downloadTokenSurvivalInt != null){
				maxTimeInterval = Integer.parseInt(downloadTokenSurvivalInt.getConfValue());
			}
		}catch(Exception e){
			
		}
		return maxTimeInterval;
	}
	
	/**
	 * 请求一个令牌:如果已经存在，则返回原令牌
	 * @param mac 用户身份
	 * @return
	 */
	public DownloadTokenEntity requestToken(String mac){
		DownloadTokenEntity indentity = createToken(mac);
		return indentity;
	}
	
	/**
	 * 刷新令牌池
	 */
	public void refresh(){
		//读取令牌存活时间
		maxTimeInterval = getDownloadTokenSurvivalInt();
		Date curDate = Calendar.getInstance().getTime();
		//查询正在下载的用户信息
		GuiMonitorEntity paramEntity = new GuiMonitorEntity();
		paramEntity.setDownloadStatus(FossConstants.DOWNLOAD_STATUS_DOING);
		List<GuiMonitorEntity> guiMonitorEntityLst = guiMonitorDao.selectGuiMonitorByPara(paramEntity);
 		if(guiMonitorEntityLst != null){
			for(int i = 0; i < guiMonitorEntityLst.size(); i++){
				GuiMonitorEntity guiMonitorEntity = guiMonitorEntityLst.get(i);
				if(guiMonitorEntity.getLastBeatTime() == null || (curDate.getTime()-guiMonitorEntity.getLastBeatTime().getTime()) > maxTimeInterval){
					//设置下载异常
					if(guiMonitorEntity != null){
						guiMonitorEntity.setDownloadStatus(FossConstants.DOWNLOAD_STATUS_EXCEPTION);
						guiMonitorDao.updateGuiMonitor(guiMonitorEntity);
					}
				}
			}
		}
	}
	
	/**
	 * 生成令牌
	 * @param mac
	 * @return
	 */
	public DownloadTokenEntity createToken(String mac){
		synchronized(lock){
			if(getClusterCurTokenSize() < getMaxThreadSize()){
				GuiMonitorEntity guiMonitorEntity = guiMonitorDao.selectById(mac);
				if(FossConstants.DOWNLOAD_STATUS_UNDO.equals(guiMonitorEntity.getDownloadStatus())){
					guiMonitorEntity.setDownloadStartTime(new Date());
					guiMonitorEntity.setLastBeatTime(guiMonitorEntity.getDownloadStartTime());
					guiMonitorEntity.setDownloadStatus(FossConstants.DOWNLOAD_STATUS_DOING);
					guiMonitorDao.updateGuiMonitor(guiMonitorEntity);
				}
				return transformDownloadTokenEntity(guiMonitorEntity);
			}
			return null;
		}
	}
	
	public DownloadTokenEntity getTokenByMac(String mac){
		GuiMonitorEntity guiMonitorEntity = guiMonitorDao.selectById(mac);
		return transformDownloadTokenEntity(guiMonitorEntity);
	}
	
	public DownloadTokenEntity transformDownloadTokenEntity(GuiMonitorEntity guiMonitorEntity){
		if(guiMonitorEntity == null)
			return null;
		DownloadTokenEntity indentity = new DownloadTokenEntity();
		indentity.setMac(guiMonitorEntity.getId());
		indentity.setDownloadToken(guiMonitorEntity.getId());
		indentity.setUpdateTime(guiMonitorEntity.getLastBeatTime());
		return indentity;
	}
	
	/**
	 * 更新令牌时间
	 * @param indentity
	 */
	public void updateTokenBeatDate(DownloadTokenEntity indentity){
		if(indentity != null && indentity.isActive()){
			GuiMonitorEntity guiMonitorEntity = guiMonitorDao.selectById(indentity.getMac());
			if(guiMonitorEntity != null){
				guiMonitorEntity.setLastBeatTime(new Date());
				guiMonitorDao.updateGuiMonitor(guiMonitorEntity);
			}
		}
	}
	
	/**
	 * 肆放令牌
	 * @param tokenUuid 令牌唯一标示
	 * @param force 强制释放
	 */
	public void releaseToken(String tokenUuid,boolean force){
		synchronized(lock){
			GuiMonitorEntity guiMonitorEntity = guiMonitorDao.selectById(tokenUuid);
			if(guiMonitorEntity != null){
				//如果正在运行中的，强制终止，状态置成异常
				guiMonitorEntity.setDownloadEndTime(new Date());
				if(force && FossConstants.DOWNLOAD_STATUS_DOING.equals(guiMonitorEntity.getDownloadStatus()))
					guiMonitorEntity.setDownloadStatus(FossConstants.DOWNLOAD_STATUS_EXCEPTION);	//强制释放,并且正在执行，则标志为异常
				else
					guiMonitorEntity.setDownloadStatus(FossConstants.DOWNLOAD_STATUS_DONE);
				guiMonitorDao.updateGuiMonitor(guiMonitorEntity);
			}
		}
	}

	public int getMaxTimeInterval() {
		return maxTimeInterval;
	}

	/**
	 * 获取令牌池数量
	 * @return
	 */
	public int getMaxThreadSize() {
		ConfigurationParamsEntity downloadTokenTotal = null;
		try {
			//读取令牌池数量
			downloadTokenTotal = configurationParamsService.queryConfigurationParamsByOrgCode
					(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,
						WaybillConstants.DOWNLOAD_TOKEN_TOTAL,
							FossConstants.ROOT_ORG_CODE);
		}catch (Exception e) {
			
		}
		if(downloadTokenTotal != null){
			maxThreadSize = Integer.parseInt(downloadTokenTotal.getConfValue());
		}
		return maxThreadSize;
	}
	
	/**
	 * 获取集群的当前下载数量
	 * @return
	 */
	public int getClusterCurTokenSize(){
		GuiMonitorEntity guiMonitorEntity = new GuiMonitorEntity();
		guiMonitorEntity.setDownloadStatus(FossConstants.DOWNLOAD_STATUS_DOING);
		Long curCount = guiMonitorDao.selectCountByPara(guiMonitorEntity);
		if(curCount != null)
			return curCount.intValue();
		else
			return 0;
	}

	public IConfigurationParamsService getConfigurationParamsService() {
		return configurationParamsService;
	}

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	

}
