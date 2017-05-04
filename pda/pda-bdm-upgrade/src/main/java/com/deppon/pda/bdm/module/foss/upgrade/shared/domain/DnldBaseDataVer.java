package com.deppon.pda.bdm.module.foss.upgrade.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * TODO(基础数据版本下载实体类)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:chengang,date:2013-4-10 下午2:31:47,content:TODO </p>
 * @author chengang
 * @date 2013-4-10 下午2:31:47
 * @since
 * @version
 */
public class DnldBaseDataVer extends BaseEntity{
	
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 是否数据更新
	 */
	private boolean reqUpgrade;
	/**
	 * 更新类型
	 */
	private String updType;
	/**
	 * 更新路径
	 */
	private String updUrl;
	
	/**
	 * 最新版本号
	 */
	private String newDataVer;
	
	
	public boolean isReqUpgrade() {
		return reqUpgrade;
	}
	public void setReqUpgrade(boolean reqUpgrade) {
		this.reqUpgrade = reqUpgrade;
	}
	public String getUpdType() {
		return updType;
	}
	public void setUpdType(String updType) {
		this.updType = updType;
	}
	public String getUpdUrl() {
		return updUrl;
	}
	public void setUpdUrl(String updUrl) {
		this.updUrl = updUrl;
	}
	public String getNewDataVer() {
		return newDataVer;
	}
	public void setNewDataVer(String newDataVer) {
		this.newDataVer = newDataVer;
	}
	
	

	

}