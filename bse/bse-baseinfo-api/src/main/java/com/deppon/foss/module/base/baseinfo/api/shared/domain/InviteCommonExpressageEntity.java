package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 零担外请车单票费用返回结果
 * 
 * @author 332219-foss
 * @date 2017-03-11 下午2:35:52
 */
public class InviteCommonExpressageEntity  extends BaseEntity {
	
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = -1L;

    /**
    * 组织ID
    */	
    private String id;
    /**
    *快递分部和点部编码
    */	
    private String expressageCode;
    /**
    *快递分部和点部名称
    */	
    private String expressageName;
    /**
     * 是否有效
     */
    private String active;
    
    /**
  	 *getter
  	 */
	public String getId() {
		return id;
	}
	/**
	 *setter
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 *getter
	 */
	public String getExpressageCode() {
		return expressageCode;
	}
	/**
	 *setter
	 */
	public void setExpressageCode(String expressageCode) {
		this.expressageCode = expressageCode;
	}
	/**
	 *getter
	 */
	public String getExpressageName() {
		return expressageName;
	}
	/**
	 *setter
	 */
	public void setExpressageName(String expressageName) {
		this.expressageName = expressageName;
	}
	/**
	 *getter
	 */
	public String getActive() {
		return active;
	}
	/**
	 *setter
	 */
	public void setActive(String active) {
		this.active = active;
	}
	
}