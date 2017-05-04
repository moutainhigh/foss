package com.deppon.foss.module.login.shared.hessian;

import com.deppon.foss.framework.service.IHessianService;
import com.deppon.foss.module.login.shared.domain.LoginForGUIInfo;
import com.deppon.foss.module.login.shared.domain.LoginInfo;
import com.deppon.foss.module.login.shared.domain.TokenDto;
import com.deppon.foss.module.login.shared.vo.DownloadConfig;
import com.deppon.foss.module.login.shared.vo.DownloadTokenEntity;

public interface ILoginHessianRemoting extends IHessianService {

	LoginInfo userLogin(LoginForGUIInfo loginForGUIInfo);
	void userLogout();
	TokenDto keepSession(String token);
	TokenDto keepSessionUuid(String token, String uuid);
	
	/**
     * 判断开关是否处于关闭状态：只要其中(...-父机构-当前机构)有一个关闭的开关，则表示关闭 
	  * Description: 离线下载功能的开关配置 BUG-55198
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:37
	  * @param orgCode	机构
	  * @param swtichCode 开关类型
	  * @return
	 */
	public boolean isOffSwitchBySwtOrgCode(String orgCode,String swtichCode);
	
	/**
     * 判断开关是否处于开启状态： 
	  * Description: 离线下载功能的开关配置 BUG-55198
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:37
	  * @param orgCode	机构
	  * @param swtichCode 开关类型
	  * @return
	 */
	public boolean isOnSwitchBySwtOrgCode(String orgCode,String swtichCode);
	
	/**
	 * 下载令牌心跳  MANA-2018
	 * @author deppon-157229-zxy
	 * @version 1.0 2014-03-06 
	 * @param token 身份标示
	 * @return
	 */
	public boolean beat(String token);
	
	/**
	 * 获取下载令牌  MANA-2018
	 * @author deppon-157229-zxy
	 * @version 1.0 2014-03-06
	 * @param token 身份标示
	 * @return
	 */
	public DownloadTokenEntity requestDownloadToken(String tokenUuid);
	
	/**
	 * 释放下载令牌 MANA-2018
	 * @author deppon-157229-zxy
	 * @version 1.0 2014-03-06
	 * @param token
	 */
	public void releaseDownloadToken(String tokenUuid);
	
	/**
	 * 获取下载配置信息  MANA-2018
	 * @author deppon-157229-zxy
	 * @version 1.0 2014-03-11
	 */
	public DownloadConfig getDownloadConfig();
	
	/**
	 * 强制释放令牌  MANA-2018
	 * @author deppon-157229-zxy
	 * @version 1.0 2014-03-06
	 * @param tokenUuid
	 */
	public void forceReleaseDownloadToken(String tokenUuid);
	
	/**
	 * 获取下载开关
	 * @return 开关状态:true打开,false关闭
	 */
	public boolean getDownloadSwitch(String orgCode);
}
