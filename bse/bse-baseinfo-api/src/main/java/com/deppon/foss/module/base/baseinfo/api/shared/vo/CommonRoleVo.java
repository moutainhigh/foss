package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonRoleEntity;
/**
 * (司代理人VO)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:078838-foss-lifanghong,date:2013-4-26
 * </p>
 * 
 * @author 078838-foss-lifanghong
 * @date 2013-4-26
 * @since
 * @version
 */
public class CommonRoleVo implements Serializable{
	/**
    *
    */
	private static final long serialVersionUID = 3875914350859197349L;
	/**
	 * 航空公司代理人实体
	 */
	private CommonRoleEntity commonRoleEntity;
	/**
	 * 航空公司代理人实体List
	 */
	private List<CommonRoleEntity> commonRoleEntitys;
	
	
	
	public CommonRoleEntity getCommonRoleEntity() {
		return commonRoleEntity;
	}
	public void setCommonRoleEntity(CommonRoleEntity commonRoleEntity) {
		this.commonRoleEntity = commonRoleEntity;
	}
	public List<CommonRoleEntity> getCommonRoleEntitys() {
		return commonRoleEntitys;
	}
	public void setCommonRoleEntitys(List<CommonRoleEntity> commonRoleEntitys) {
		this.commonRoleEntitys = commonRoleEntitys;
	}	

}
