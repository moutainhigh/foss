package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
/**
 * 
 *密码初始化的vo
 *
 */
public class PasswordRestVo {
	//用户信息实体（查询条件）
	private UserEntity userEntity;
	//用户信息实体集合
	private List<UserEntity> userEntitys;
	/**
	 * this is get method
	 * @return
	 */
	public UserEntity getUserEntity() {
		return userEntity;
	}
	/**
	 * this is set method
	 * @return
	 */
	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}
	/**
	 * this is get method
	 * @return
	 */
	public List<UserEntity> getUserEntitys() {
		return userEntitys;
	}
	/**
	 * this is set method
	 * @return
	 */
	public void setUserEntitys(List<UserEntity> userEntitys) {
		this.userEntitys = userEntitys;
	}
	

}
