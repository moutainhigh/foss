package com.deppon.pda.bdm.module.foss.upgrade.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

  
/**     
 *      
 *     
 * @author chengang       
 * @version 1.0     
 * @created 2012-10-25 下午06:37:15    
 */

public class DnldBaseData extends BaseEntity{
	
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 版本号
	 */
	private String dataVer;
	
	/**
	 * 更新路径
	 */
	private String url;
	
	/**
	 * 是否有增量数据文件
	 */
	private boolean flag;
	
	/**
	 * 用户类型
	 */
	private String userType;

	public String getDataVer() {
		return dataVer;
	}

	public void setDataVer(String dataVer) {
		this.dataVer = dataVer;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	
	
}
