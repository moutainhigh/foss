package com.deppon.foss.module.login.shared.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;

/**
 * (描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:ningyu,date:2013-3-8 上午11:06:43, </p>
 * @author ningyu
 * @date 2013-3-8 上午11:06:43
 * @since
 * @version
 */
public class LoginInfo implements Serializable{

	/**
	 * serialVersionUID:类唯一标识
	 */
	private static final long serialVersionUID = 6586635903305099436L;
	
	private UserEntity user;
	
	/**
	 * key是role Code,value是actionUri
	 * @deprecated
	 */
	private Map<String, String> userResourcesCodes;
	
	private String token;
	
	/**
	 * 用于在缓存中查询token
	 */
	private String uuid;
	
	/**
	 * GUI菜单：给GUI组装菜单用
	 */
	private List<ResourceEntity>  resources;
	
	
	private Date date;


	/**
	 * GUI菜单：给GUI组装菜单用
	 * @deprecated
	 */
	private List<ResourceEntity>  userResources;
	
	/**
	 * @return the userResources
	 * @deprecated
	 */
	public List<ResourceEntity> getUserResources() {
		return userResources;
	}

	/**
	 * @param userResources the userResources to set
	 * @deprecated
	 */
	public void setUserResources(List<ResourceEntity> userResources) {
		this.userResources = userResources;
	}

	/**
	 * @return the resources
	 */
	public List<ResourceEntity> getResources() {
		return resources;
	}

	/**
	 * @param resources the resources to set
	 */
	public void setResources(List<ResourceEntity> resources) {
		this.resources = resources;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}
	
	/**
	 * @deprecated
	 */
	public Map<String, String> getUserResourcesCodes() {
		return userResourcesCodes;
	}

	/**
	 * @deprecated
	 */
	public void setUserResourcesCodes(Map<String, String> userResourcesCodes) {
		this.userResourcesCodes = userResourcesCodes;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
}
