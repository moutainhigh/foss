package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressPrintStarEntity;

/**
 * TODO(ExpressPrintStar的Vo类)
 * @author 187862-dujunhui
 * @date 2014-5-21 上午9:49:12
 * @since
 * @version
 */
public class ExpressPrintStarVo implements Serializable {

	private static final long serialVersionUID = -4860359152300980336L;
	
	//库区实体
    private ExpressPrintStarEntity expressPrintStarEntity;
    
    //库区实体链表
    private List<ExpressPrintStarEntity> expressPrintStarEntityList;
    
    //批量作废虚拟编码
    private List<String> expressPrintStarVirtualCodes;

	/**
	 * @return  the expressPrintStarEntity
	 */
	public ExpressPrintStarEntity getExpressPrintStarEntity() {
		return expressPrintStarEntity;
	}

	/**
	 * @param expressPrintStarEntity the expressPrintStarEntity to set
	 */
	public void setExpressPrintStarEntity(
			ExpressPrintStarEntity expressPrintStarEntity) {
		this.expressPrintStarEntity = expressPrintStarEntity;
	}

	/**
	 * @return  the expressPrintStarEntityList
	 */
	public List<ExpressPrintStarEntity> getExpressPrintStarEntityList() {
		return expressPrintStarEntityList;
	}

	/**
	 * @param expressPrintStarEntityList the expressPrintStarEntityList to set
	 */
	public void setExpressPrintStarEntityList(
			List<ExpressPrintStarEntity> expressPrintStarEntityList) {
		this.expressPrintStarEntityList = expressPrintStarEntityList;
	}

	/**
	 * @return  the expressPrintStarVirtualCodes
	 */
	public List<String> getExpressPrintStarVirtualCodes() {
		return expressPrintStarVirtualCodes;
	}

	/**
	 * @param expressPrintStarVirtualCodes the expressPrintStarVirtualCodes to set
	 */
	public void setExpressPrintStarVirtualCodes(
			List<String> expressPrintStarVirtualCodes) {
		this.expressPrintStarVirtualCodes = expressPrintStarVirtualCodes;
	}

	

}
