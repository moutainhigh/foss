package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * @author 218392 张永雪
 *
 * 2014-11-18下午3:09:03
 */
public class HeavyBubbleRatioEntity extends BaseEntity{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 重泡比参数
	 */
	private String heavyBubbleParam;
	
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 是否有效  Y:有效  N：无效
	 */
	private String active;
	
	/**
	 * 外场
	 */
	private String outfield;
	
	/**
	 * 创建日期
	 * @return
	 */
	private Date createDate;
	
	/**
	 * 外场名字
	 */
	private String outfieldName;
	
	
	public String getOutfieldName() {
		return outfieldName;
	}

	public void setOutfieldName(String outfieldName) {
		this.outfieldName = outfieldName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getHeavyBubbleParam() {
		return heavyBubbleParam;
	}

	public void setHeavyBubbleParam(String heavyBubbleParam) {
		this.heavyBubbleParam = heavyBubbleParam;
	}


	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getOutfield() {
		return outfield;
	}

	public void setOutfield(String outfield) {
		this.outfield = outfield;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
