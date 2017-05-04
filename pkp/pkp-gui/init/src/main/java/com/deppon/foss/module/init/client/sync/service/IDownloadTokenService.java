package com.deppon.foss.module.init.client.sync.service;

import com.deppon.foss.module.login.shared.vo.DownloadTokenEntity;


/**
 * 

  * @ClassName: IDownloadTokenService

  * @Description: 离线数据下载心跳检测接口 MANA-2018

  * @author deppon-157229-zxy

  * @date 2014-2-21 下午6:25:06

  *
 */
public interface IDownloadTokenService {

	/**
	 * 开启令牌功能
	  * Description: 
	  * @author deppon-157229-zxy
	  * @version 1.0 2014-2-21 下午6:24:33
	  * @return
	 */
	public void run() throws Exception;
	
	/**
	 * 单次心跳检测
	  * Description: 
	  * @author deppon-157229-zxy
	  * @version 1.0 2014-2-21 下午6:24:33
	  * @return
	 */
	public void beat();
	
	
	/**
	 * 取消心跳检测
	  * Description: 
	  * @author deppon-157229-zxy
	  * @version 1.0 2014-2-21 下午6:24:36
	  * @param force 强制停止:强制则记异常
	  * @return
	 */
	public void cancelBeat();
	
	/**
	 * 当前令牌
	 * @return
	 */
	public DownloadTokenEntity getTokenEntity();
	
	/**
	 * 释放令牌
	 */
	public void releaseTokenEntity();
	
	/**
	 * 是否连接成功
	 * @return
	 */
	public boolean isConnectSuccess();
	
}
