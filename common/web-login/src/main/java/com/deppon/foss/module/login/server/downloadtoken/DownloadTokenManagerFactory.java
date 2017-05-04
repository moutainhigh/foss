package com.deppon.foss.module.login.server.downloadtoken;

/**
 * 下载令牌管理器工厂
 * zxy 20140306 MANA-2018
 * @author 157229-zxy
 *
 */
public class DownloadTokenManagerFactory {
	
	private static DownloadTokenManagerFactory instance = new DownloadTokenManagerFactory();
	private DownloadTokenManager downloadTokenManager = null;
	
	private DownloadTokenManagerFactory(){
		
	}

	public static DownloadTokenManagerFactory getInstance() {
		return instance;
	}

	/**
	 * 获取下载令牌管理器
	 * @return
	 */
	public DownloadTokenManager getDownloadTokenManager(){
		synchronized(instance){
			if(downloadTokenManager == null){
				downloadTokenManager = new DownloadTokenManager();
			} 
			return downloadTokenManager;
		}
		
	}

}
