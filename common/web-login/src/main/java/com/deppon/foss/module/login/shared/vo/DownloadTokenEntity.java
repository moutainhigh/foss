
package com.deppon.foss.module.login.shared.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 令牌对象  
 * zxy 20140306  MANA-2018
 * 
 * @author 157229-zxy
 *
 */
public class DownloadTokenEntity implements Serializable{
	private static final long serialVersionUID = -3068326734807071405L;
	
	private String downloadToken;		//令牌表示
	private String mac;					//用户身份
	private Date updateTime;			//更新时间
	private boolean active = true;  //下载令牌有效性
	
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getDownloadToken() {
		return downloadToken;
	}
	public void setDownloadToken(String downloadToken) {
		this.downloadToken = downloadToken;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	

}
