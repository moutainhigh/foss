package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;


import com.deppon.foss.module.base.baseinfo.api.shared.domain.AllAgentEntity;

/**
 * (代理VO)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:078838-foss-zhangbin,date:2013-4-25
 * </p>
 * 
 * @author 078838-foss-lifanghong
 * @date 2013-5-09
 * @since
 * @version
 */
public class CommonAllAgentVo implements Serializable {
	/**
    *
    */
	private static final long serialVersionUID = 3875916350889197349L;
	/**
	 * 代理信息列表
	 */
	private List<AllAgentEntity> allAgentEntitys;
	/**
	 * 代理信息
	 * @return
	 */
	private AllAgentEntity allAgentEntity;
	
	public List<AllAgentEntity> getAllAgentEntitys() {
		return allAgentEntitys;
	}
	public void setAllAgentEntitys(List<AllAgentEntity> allAgentEntitys) {
		this.allAgentEntitys = allAgentEntitys;
	}
	public AllAgentEntity getAllAgentEntity() {
		return allAgentEntity;
	}
	public void setAllAgentEntity(AllAgentEntity allAgentEntity) {
		this.allAgentEntity = allAgentEntity;
	}
	
	
	
	
	

}
